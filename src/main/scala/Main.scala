import java.io._
import java.net.InetAddress
import java.nio.file.Files
import java.util.concurrent.{TimeUnit, TimeoutException}

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.{GetObjectRequest, ListObjectsRequest, ObjectListing}
import com.amazonaws.util.json.JSONException
import com.couchbase.client.java.document.json.JsonArray
import com.couchbase.client.java.view.ViewQuery
import com.couchbase.client.java.{Bucket, CouchbaseCluster}
import helper.ConfigHelper
import io.userhabit.library.db.ElasticUtils
import io.userhabit.library.io.RollingFileWriter
import io.userhabit.library.orm.Mapper
import io.userhabit.library.util.S3Utils
import io.userhabit.library.v1.model.Session
import io.userhabit.library.v2.model.analysis.{AnalyzedSession, S3Location}
import io.userhabit.library.v2.tool.V1ToV2Migrator
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.elasticsearch.common.unit.{TimeValue, ByteSizeValue, ByteSizeUnit}
import org.joda.time.{DateTime, Seconds}

import scala.collection.JavaConversions._
import scala.collection.mutable
import scala.util.control.Breaks._

/**
  * Created by DynamicScope on 2015. 11. 18..
  */
object Main {

  var couchbaseCluster: CouchbaseCluster = null
  var couchbaseBucket: Bucket = null
  var logger: BufferedWriter = null

  lazy val mapper = new Mapper
  lazy val migrator = new V1ToV2Migrator

  lazy val s3 = new S3Utils(new ProfileCredentialsProvider().getCredentials, ConfigHelper.s3Bucket, "")
  var esUtils: ElasticUtils = null

  val s3KeyPrefix = "raw/"

  def main (args: Array[String]): Unit = {
    try {
      ConfigHelper.load()

      val dir = new File(s"./log")
      if (!dir.exists && !dir.isDirectory) {
        dir.mkdirs()
      }

      val file = new File(s"${dir.getCanonicalPath}/logging")
      Files.deleteIfExists(file.toPath)
      logger = new BufferedWriter(new FileWriter(file))

      // Initialize utils
      migrator.setCrashSessionHandler(new AddCrashLogToSession)
      esUtils = new ElasticUtils(ConfigHelper.esBulkActions, new ByteSizeValue(10, ByteSizeUnit.MB), TimeValue.timeValueSeconds(10), 1)
      esUtils.connect(new InetSocketTransportAddress(InetAddress.getByName(ConfigHelper.elasticHost), 9300))

      openCouchbase()
      migrateFromCouchbase()
      closeCouchbase()

    } catch {
      case e: Exception =>
        println(e.getMessage)
    } finally {
      if (logger != null) logger.close()
      if (esUtils != null) esUtils.close()
    }
  }

  def openCouchbase() : Unit = {
    couchbaseCluster = CouchbaseCluster.create(ConfigHelper.cbNodes)
    couchbaseBucket = couchbaseCluster.openBucket(ConfigHelper.cbBucket, ConfigHelper.cbBucketPassword)
  }

  def closeCouchbase() : Unit = {
    couchbaseCluster.disconnect()
  }

  def migrateFromCouchbase(): Unit = {

    if (couchbaseBucket == null) return

    var appList = ConfigHelper.appIds
    if (appList.isEmpty) {
      appList = getAppListFromCouchbase
    }
    logger.write(s"[INFO] Processing following appIds: ${appList.toString}")
    logger.newLine()

    val fromDate = ConfigHelper.fromDate.minusHours(9)
    val toDate = ConfigHelper.toDate.minusHours(9)

    var totalSessionsPerApp = 0

    appList.foreach(appId => {
      val startTime = DateTime.now()
      logger.write(s"[INFO] Processing appId: $appId")
      logger.newLine()
      var workingDate = toDate
      while (workingDate.getMillis >= fromDate.getMillis) {

        val totalSessionsResponse = couchbaseBucket.query(ViewQuery.from("admin", "daily_session_count")
          .startKey(JsonArray.fromJson(s"[$appId,${workingDate.getMillis}]"))
          .endKey(JsonArray.fromJson(s"[$appId,${workingDate.plusDays(1).getMillis}]"))
          .reduce(true))

        if (totalSessionsResponse.success()) {
          var totalSessions = 0
          totalSessionsResponse.foreach(viewRow => {
            if (totalSessions == 0) totalSessions = viewRow.value().toString.toInt
          })

          if (totalSessions > 0) {
            totalSessionsPerApp += totalSessions
            val rfw = new RollingFileWriter(s"$appId-${workingDate.plusHours(9).toString("YYYYMMdd")}", ByteSizeUnit.GB.toBytes(5), ConfigHelper.dirExport)
            processData(appId, workingDate, totalSessions, rfw)
            rfw.close()
            uploadToS3(rfw)
          }
        }

        workingDate = workingDate.minusDays(1)
      }

      logger.write(s"[INFO] Total sessions: $totalSessionsPerApp")
      logger.newLine()

      val endTime = DateTime.now()
      val seconds = Seconds.secondsBetween(startTime, endTime)
      logger.write(s"------------------ AppId($appId) Job took ${seconds.getSeconds} seconds ------------------")
      logger.newLine()

      logger.newLine()
    })
  }

  private def processData(appId: Integer, workingDate: DateTime, totalSessions: Int, rfw: RollingFileWriter): Unit = {
    val limit = ConfigHelper.cbBulkLimit

    def queryDocs(skip: Int, timeout: Long): Unit = {
      if (timeout >= 5) return
      try {
        val cbQueryResponse = couchbaseBucket.query(ViewQuery.from("admin", "daily_session_count")
          .startKey(JsonArray.fromJson(s"[$appId,${workingDate.getMillis}]"))
          .endKey(JsonArray.fromJson(s"[$appId,${workingDate.plusDays(1).getMillis}]"))
          .skip(skip)
          .limit(limit)
          .reduce(false), timeout, TimeUnit.MINUTES)

        if (cbQueryResponse.success()) {
          cbQueryResponse.foreach(viewRow => {
            val v1Session = mapper.readValue(viewRow.document().content().toString, classOf[Session])
            val v2Session = migrator.migrateToV2Session(v1Session)
            val as = new AnalyzedSession(v2Session)

            val offsetStart = rfw.getFileSize
            rfw.writeAnalyzedSessionWithMPack(as)
            val offsetEnd = rfw.getFileSize - 1

            as.removeActionFlows()
            val s3Key = getS3Key(rfw.getCurrentFile.getName)
            as.setS3Location(new S3Location(s3Key, offsetStart, offsetEnd))
            as.setAnalyzedType(AnalyzedSession.FULL_ANALYZED)

            esUtils.addToBulkIndex(as)

            println(as.getSessionInfo.getId)
          })
        }
      } catch {
        case te: TimeoutException =>
          logger.write(s"[WARN] queryDocs: Increasing timeout to $timeout")
          logger.newLine()
          queryDocs(skip, timeout + 1)
        case e: Exception =>
          e.printStackTrace()
          logger.write(s"[Error] appId: $appId, workingDate: ${workingDate.toString}, timeout: $timeout")
          logger.newLine()
      }
    }

    var skip = 0
    while (skip < totalSessions) {
      queryDocs(skip, 1)
      skip += limit
    }
  }

  def getAppListFromCouchbase : List[Integer] = {
    val appList = couchbaseBucket.query(ViewQuery.from("admin", "daily_session_count").groupLevel(1).reduce(true))
    val appIds = mutable.ListBuffer[Integer]()
    if (appList.success()) {
      appList.foreach(appListRow => {
        val appId = appListRow.key().asInstanceOf[JsonArray].get(0).toString
        appIds += appId.toInt
      })
    }
    appIds.toList
  }

  private def getS3Key(fileName: String): String = {
    val names = fileName.split("-")
    val appId = names(0)
    val datetime = names(1)
    val year = datetime.substring(0, 4)
    val month = datetime.substring(4, 6)
    val day = datetime.substring(6, 8)
    val rollingNumber = names(2)
    s"$s3KeyPrefix$appId/$year/$month/$day-$rollingNumber"
  }

  private def uploadToS3(rollingFileWriter: RollingFileWriter): Unit = {
    for (file <- rollingFileWriter.getResultFiles) {
      val key = getS3Key(file.getName)
      try {
        logger.write(s"[INFO] Uploading file to S3: $key")
        logger.newLine()
        // If upload fails, it throws an exception.
        s3.storeForPrivate(key, file)
      } catch {
        case e: Exception =>
          println(e.getMessage)
      } finally {
        Files.deleteIfExists(file.toPath)
      }
    }
  }

//  def couchbaseToS3() : Unit = {
//    var fDateTime = ConfigHelper.fromDate
//    val tDateTime = ConfigHelper.toDate
//
//    val s3Client = new AmazonS3Client(new ProfileCredentialsProvider())
//
//    val esSettings = Settings.settingsBuilder().build()
//    val esClient = TransportClient.builder().settings(esSettings).build()
//      .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.31.1.101"), 9300))
//
//    if (appList.isEmpty) {
//      appList = getAppListFromCouchbase
//    }
//
//    appList.foreach(intAppId => {
//
//      println(s"----------${intAppId.toString}----------")
//
//      val fromDateResponse = couchbaseBucket.query(ViewQuery.from("admin", "daily_session_count")
//        .startKey(JsonArray.fromJson(s"[$intAppId]"))
//        .endKey(JsonArray.fromJson(s"[${intAppId+1}]"))
//        .reduce(false))
//      val fromDateResponseItrRows = fromDateResponse.rows()
//
//      if (fromDateResponse.success()) {
//        if (fromDateResponseItrRows.hasNext) {
//          val fromDateViewRow = fromDateResponseItrRows.next()
//          if (fromDateViewRow != null) {
//            val fromDateMillis = fromDateViewRow.key().asInstanceOf[JsonArray].get(1).toString.toLong
//            fDateTime = new DateTime(fromDateMillis).withHourOfDay(0).withMinuteOfHour(0)
//          }
//
//          println(s"fromDateTime: $fDateTime")
//          var eDate = tDateTime
//
//          while (eDate.getMillis > fDateTime.getMillis) {
//            val sDate = eDate.minusHours(1)
//
//            val startKey = JsonArray.fromJson(s"[$intAppId,${sDate.getMillis}]")
//            val endKey = JsonArray.fromJson(s"[$intAppId,${eDate.getMillis}]")
//
//            val totalSessionsResult = couchbaseBucket.query(ViewQuery.from("admin", "daily_session_count").startKey(startKey).endKey(endKey).reduce(true))
//            if (totalSessionsResult.success()) {
//              var totalSessions = 0
//              totalSessionsResult.foreach(r => {
//                if (totalSessions == 0) totalSessions = r.value().toString.toInt
//              })
//
//              if (totalSessions > 0) {
//                //toS3(s3Client, intAppId, sDate, startKey, endKey, totalSessions)
//                migrateToElasticSearch(esClient, intAppId, sDate, startKey, endKey, totalSessions)
//              }
//            }
//            eDate = sDate
//          }
//        }
//      }
//    })
//
//    esClient.close()
//  }

//  def migrateToElasticSearch(client: TransportClient, intAppId: Integer, sDate: DateTime, startKey: JsonArray, endKey: JsonArray, totalSessions: Int): Unit = {
//    val limit = couchbaseBulkLimit
//    var skip = 0
//
//    val year = sDate.toString("yyyy")
//    val month = sDate.toString("MM")
//    val day = sDate.toString("dd")
//
//    while (skip < totalSessions) {
//      val result = couchbaseBucket.query(ViewQuery.from("admin", "daily_session_count").startKey(startKey).endKey(endKey).reduce(false).skip(skip).limit(limit))
//      if (result.success()) {
//        result.foreach(row => {
//          try {
//            val sessionJson = row.document().content()
//            val json = new JSONObject(sessionJson)
//            json.remove("appViewActivity")
//            json.remove("location")
//            json.remove("phoneNumber")
//
//            val indexName = s"uh-$intAppId-$year$month$day"
//            val indexType = "session"
//
//            val isIndexExists = client.admin().indices().prepareExists(indexName).execute().actionGet().isExists
//            if (!isIndexExists) {
//              val res = client.admin().indices().prepareCreate(indexName).addMapping(indexType, getMapping(indexType)).execute().actionGet()
//              println(res.toString)
//            }
//
//            val response = client.prepareIndex(indexName, indexType)
//              .setSource(json.toString)
//              .get()
//            println(response)
//          } catch {
//            case e: JSONException => println(e.getMessage)
//            case e: TimeoutException =>
//              logger.write(row.id())
//              logger.newLine()
//          }
//        })
//      }
//      skip += limit
//    }
//  }

//  def migrateToS3(s3Client: AmazonS3Client, intAppId: Integer, sDate: DateTime, startKey: JsonArray, endKey: JsonArray, totalSessions: Int): Boolean = {
//    val limit = couchbaseBulkLimit
//    var skip = 0
//
//    val year = sDate.toString("yyyy")
//    val month = sDate.toString("MM")
//    val day = sDate.toString("dd")
//    val hour = sDate.toString("HH")
//
//    val dir = new File(s"./tmp/$intAppId/$year/$month/$day")
//    if (!dir.exists && !dir.isDirectory) {
//      dir.mkdirs()
//    }
//
//    val file = new File(s"${dir.getCanonicalPath}/$hour")
//    val bw = new BufferedWriter(new FileWriter(file))
//
//    while (skip < totalSessions) {
//      val result = couchbaseBucket.query(ViewQuery.from("admin", "daily_session_count").startKey(startKey).endKey(endKey).reduce(false).skip(skip).limit(limit))
//      if (result.success()) {
//        result.foreach(row => {
//          val doc = row.document().content()
//          bw.write(doc.toString)
//          bw.newLine()
//        })
//      }
//      skip += limit
//    }
//
//    bw.close()
//
//    val key = s"$intAppId/$year/$month/$day/$hour"
//    val putResult = s3Client.putObject(new PutObjectRequest(ConfigHelper.s3Bucket, key, file))
//    println(putResult.getETag)
//
//    // Remove file when S3 upload succeeds
//    file.delete()
//  }

//  def couchbaseToFile(): Unit = {
//
//    var fDateTime = ConfigHelper.fromDate
//    val tDateTime = ConfigHelper.toDate
//
//    if (appList.isEmpty) {
//      appList = getAppListFromCouchbase
//    }
//
//    val rfw = new RollingFileWriter("", ByteSizeUnit.GB.toBytes(5))
//
//    def queryDocs(startKey: JsonArray, endKey: JsonArray, limit: Integer, skip: Int, timeout: Long): Unit = {
//      if (timeout >= 5) return
//      val query = ViewQuery.from("admin", "daily_session_count").startKey(startKey).endKey(endKey).reduce(false).skip(skip).limit(limit)
//      try {
//        val result = couchbaseBucket.query(query, timeout, TimeUnit.MINUTES)
//        if (result.success()) {
//          result.foreach(row => {
//
//          })
//        }
//      } catch {
//        case te: TimeoutException =>
//          println(s"queryDocs: Increasing timeout to $timeout")
//          queryDocs(startKey, endKey, limit, skip, timeout + 1)
//        case e: Exception =>
//          println(e.getMessage)
//          logger.write(query.toString)
//          logger.newLine()
//      }
//    }
//
//    appList.foreach(intAppId => {
//
//      println(s"----------${intAppId.toString}----------")
//
//      val fromDateResponse = couchbaseBucket.query(ViewQuery.from("admin", "daily_session_count")
//        .startKey(JsonArray.fromJson(s"[$intAppId]"))
//        .endKey(JsonArray.fromJson(s"[${intAppId+1}]"))
//        .reduce(false))
//      val fromDateResponseItrRows = fromDateResponse.rows()
//
//      if (fromDateResponse.success()) {
//        if (fromDateResponseItrRows.hasNext) {
//          val fromDateViewRow = fromDateResponseItrRows.next()
//          if (fromDateViewRow != null) {
//            val fromDateMillis = fromDateViewRow.key().asInstanceOf[JsonArray].get(1).toString.toLong
//            fDateTime = new DateTime(fromDateMillis).withHourOfDay(0).withMinuteOfHour(0)
//          }
//
//          println(s"fromDateTime: $fDateTime")
//          var eDate = tDateTime
//
//          while (eDate.getMillis > fDateTime.getMillis) {
//            val sDate = eDate.minusDays(1)
//
//            val startKey = JsonArray.fromJson(s"[$intAppId,${sDate.getMillis}]")
//            val endKey = JsonArray.fromJson(s"[$intAppId,${eDate.getMillis}]")
//
//            val totalSessionsResult = couchbaseBucket.query(ViewQuery.from("admin", "daily_session_count").startKey(startKey).endKey(endKey).reduce(true))
//            if (totalSessionsResult.success()) {
//              var totalSessions = 0
//              totalSessionsResult.foreach(r => {
//                if (totalSessions == 0) totalSessions = r.value().toString.toInt
//              })
//
//              if (totalSessions > 0) {
//
//                val limit = couchbaseBulkLimit
//                var skip = 0
//
//                val year = sDate.toString("yyyy")
//                val month = sDate.toString("MM")
//                val day = sDate.toString("dd")
//
//                val dir = new File(exportDir)
//                if (!dir.exists && !dir.isDirectory) {
//                  dir.mkdirs()
//                }
//
//                val rfw = new RollingFileWriter(s"${dir.getCanonicalPath}/$intAppId-$year$month$day")
//
//                while (skip < totalSessions) {
//                  queryDocs(startKey, endKey, limit, skip, rfw, 1)
//                  skip += limit
//                }
//
//                rfw.close()
//              }
//            }
//            eDate = sDate
//          }
//        }
//      }
//    })
//  }

  def s3ToElasticSearch() : Unit = {
    // Read credentials from ./.aws/credentials
    val s3Client = new AmazonS3Client(new ProfileCredentialsProvider())
    val listObjectsRequest = new ListObjectsRequest().withBucketName(ConfigHelper.s3Bucket)
    listObjectsRequest.setPrefix("raw/502/")
    var objectListing: ObjectListing = new ObjectListing()

    val esUtils = new ElasticUtils()
    esUtils.connect(new InetSocketTransportAddress(InetAddress.getByName(ConfigHelper.elasticHost), 9300))

    val mapper = new Mapper()

    do {
      objectListing = s3Client.listObjects(listObjectsRequest)
      val objectSummaries = objectListing.getObjectSummaries
      val newLine = "\n"
      println(objectSummaries.size())
      for (i <- 0 until objectSummaries.size()) {
        val key = objectSummaries.get(i).getKey
        println(key)
        val s3Object = s3Client.getObject(new GetObjectRequest(ConfigHelper.s3Bucket, key))

        val reader = new BufferedReader(new InputStreamReader(s3Object.getObjectContent))
        var byteOffset = 0
        var sessionJson = reader.readLine()
        while (sessionJson != null) {
          try {
            // displayTextInputStream(s3Object.getObjectContent)
            // val strJson = scala.io.Source.fromInputStream(s3Object.getObjectContent).mkString

            val as = mapper.readValue(sessionJson, classOf[AnalyzedSession])
            esUtils.addToBulkIndex(as, ElasticUtils.INDEX_TYPE_SESSION)

            byteOffset = sessionJson.getBytes().length + newLine.getBytes().length
          } catch {
            case e: JSONException => println(e.getMessage)
          }
          sessionJson = reader.readLine()
        }
      }
      listObjectsRequest.setMarker(objectListing.getNextMarker)
    } while (objectListing.isTruncated)

    esUtils.close()
  }

//  def fileToElasticSearch(): Unit = {
//
//    val esUtils = new ElasticUtils()
//    esUtils.connect(new InetSocketTransportAddress(InetAddress.getByName(ConfigHelper.elasticHost), 9300))
//
//    val d = new File(exportDir)
//    if (d.exists() && d.isDirectory) {
//
//      val mapper = new Mapper()
//      val migrator = new V1ToV2Migrator()
//      migrator.setCrashSessionHandler(new AddCrashLogToSession)
//
//      val files = d.listFiles.filter(_.isFile).toList
//      for (file <- files) {
//        if (file.isFile) {
//          val names = file.getName.split("-")
//          if (names.length > 2) {
//            val appId = names(0)
//            val YYYYMMDD = names(1)
//            println(s"$appId-$YYYYMMDD")
//
//            try {
//              for (line <- Source.fromFile(file.getCanonicalPath, "UTF-8").getLines) {
//
//                val key = new JSONObject(line).get("sessionId").toString
//                val v1session = mapper.readValue(line, classOf[v1.model.Session])
//                val v2session = migrator.migrateToV2Session(key, v1session)
//                esUtils.addToBulkIndex(v2session)
//              }
//            } catch {
//              case e: Exception => e.printStackTrace()
//            }
//          }
//        }
//      }
//    }
//
//    esUtils.close()
//  }

  def displayTextInputStream(input : InputStream) : Unit = {
    // Read one text line at a time and display.
    val reader = new BufferedReader(new
        InputStreamReader(input))
    breakable(while (true) {
      val line = reader.readLine()
      if (line == null) break
      println("    " + line)
    })
    println()
  }
}
