import java.io._
import java.net.InetAddress
import java.util
import java.util.concurrent.{TimeUnit, TimeoutException}

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.{GetObjectRequest, ListObjectsRequest, ObjectListing, PutObjectRequest}
import com.amazonaws.util.json.{JSONException, JSONObject}
import com.couchbase.client.java.document.json.JsonArray
import com.couchbase.client.java.view.{ViewQuery, ViewRow}
import com.couchbase.client.java.{Bucket, CouchbaseCluster}
import helper.RollingFileWriter
import io.userhabit.library.db.ElasticHelper
import io.userhabit.library.orm.Mapper
import io.userhabit.library._
import io.userhabit.library.v2.tool.V1ToV2Migrator
import io.userhabit.library.v2.tool.V1ToV2Migrator.CrashSessionHandler
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.elasticsearch.common.xcontent.{XContentBuilder, XContentFactory, XContentType}
import org.joda.time.DateTime
import org.yaml.snakeyaml.Yaml

import scala.collection.JavaConversions._
import scala.io.Source
import scala.util.control.Breaks._

/**
  * Created by DynamicScope on 2015. 11. 18..
  */
object Main {

  var appList: util.ArrayList[Integer] = new util.ArrayList[Integer]()
  var couchbaseCluster: CouchbaseCluster = null
  var couchbaseNodes: util.List[String] = new util.ArrayList[String]
  var couchbaseBucket: Bucket = null
  var couchbaseBucketName = ""
  var couchbaseBucketPassword = ""
  var couchbaseBulkLimit: Integer = 0
  var fromDateTime: IndexedSeq[Integer] = IndexedSeq.empty
  var toDateTime: IndexedSeq[Integer] = IndexedSeq.empty
  var logger: BufferedWriter = null
  var exportDir = "./export"
  var elasticNodeIp = "127.0.0.1"

  def main (args: Array[String]): Unit = {
    try {
      loadConfig()
    } catch {
      case e: Exception =>
        println("----------------------------------------")
        println(e.getMessage)
        println("----------------------------------------")
        return
    }

    val dir = new File(s"./log")
    if (!dir.exists && !dir.isDirectory) {
      dir.mkdirs()
    }

    val file = new File(s"${dir.getCanonicalPath}/logging")
    logger = new BufferedWriter(new FileWriter(file))

//    openCouchbase()
//    couchbaseToFile()
    fileToElasticSearch()
//    closeCouchbase()

    logger.close()
  }

  def openCouchbase() : Unit = {
    couchbaseCluster = CouchbaseCluster.create(couchbaseNodes)
    couchbaseBucket = couchbaseCluster.openBucket(couchbaseBucketName, couchbaseBucketPassword)
  }

  def closeCouchbase() : Unit = {
    couchbaseCluster.disconnect()
  }

  def loadConfig() : Unit = {
    val yaml = new Yaml()
    val file = new File("./config.yml")
    try {
      val input = new FileInputStream(file)
      val data = yaml.load(input).asInstanceOf[util.LinkedHashMap[String, Object]]

      appList = data.getOrDefault("app-ids", new util.ArrayList[Integer]()).asInstanceOf[util.ArrayList[Integer]]

      var tmp : Object = null
      tmp = data.get("from-datetime")
      if (tmp == null) throw new Exception("from-datetime option is required.")
      fromDateTime = tmp.asInstanceOf[util.List[Integer]].toIndexedSeq
      if (fromDateTime.length != 5) throw new Exception("from-datetime must be [year, month, day, hour, minute]")

      tmp = null
      tmp = data.get("to-datetime")
      if (tmp == null) throw new Exception("to-datetime option is required.")
      toDateTime = tmp.asInstanceOf[util.List[Integer]].toIndexedSeq
      if (toDateTime.length != 5) throw new Exception("to-datetime must be [year, month, day, hour, minute]")

      val couchbase = data.get("couchbase").asInstanceOf[util.LinkedHashMap[String, Object]]
      if (couchbase != null) {
        couchbaseNodes = couchbase.get("nodes").asInstanceOf[util.List[String]]

        val bucket = couchbase.get("bucket").asInstanceOf[util.LinkedHashMap[String, Object]]
        couchbaseBucketName = bucket.get("name").asInstanceOf[String]
        couchbaseBucketPassword = bucket.get("password").asInstanceOf[String]

        couchbaseBulkLimit = couchbase.getOrDefault("bulk-limit", 500.asInstanceOf[Integer]).asInstanceOf[Integer]
      } else throw new Exception("couchbase option is required.")

      exportDir = data.getOrDefault("dir.export", "./export").toString

      elasticNodeIp = data.getOrDefault("elastic.node.ip", elasticNodeIp).toString
    } catch {
      case e: FileNotFoundException => throw new Exception(s"${file.getCanonicalPath} : was not found.")
    }
  }

  def getAppListFromCouchbase : util.ArrayList[Integer] = {
    val appList = couchbaseBucket.query(ViewQuery.from("admin", "daily_session_count").groupLevel(1).reduce(true))
    val appIds = new util.ArrayList[Integer]()
    if (appList.success()) {
      appList.foreach(appListRow => {
        val appId = appListRow.key().asInstanceOf[JsonArray].get(0).toString
        appIds.add(appId.toInt)
      })
    }
    appIds
  }

  def couchbaseToS3() : Unit = {
    var fDateTime = new DateTime(fromDateTime(0), fromDateTime(1), fromDateTime(2), fromDateTime(3), fromDateTime(4))
    val tDateTime = new DateTime(toDateTime(0), toDateTime(1), toDateTime(2), toDateTime(3), toDateTime(4))

    val s3Client = new AmazonS3Client(new ProfileCredentialsProvider())

    val esSettings = Settings.settingsBuilder().build()
    val esClient = TransportClient.builder().settings(esSettings).build()
      .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.31.1.101"), 9300))

    if (appList.isEmpty) {
      appList = getAppListFromCouchbase
    }

    appList.foreach(intAppId => {

      println(s"----------${intAppId.toString}----------")

      val fromDateResponse = couchbaseBucket.query(ViewQuery.from("admin", "daily_session_count")
        .startKey(JsonArray.fromJson(s"[$intAppId]"))
        .endKey(JsonArray.fromJson(s"[${intAppId+1}]"))
        .reduce(false))
      val fromDateResponseItrRows = fromDateResponse.rows()

      if (fromDateResponse.success()) {
        if (fromDateResponseItrRows.hasNext) {
          val fromDateViewRow = fromDateResponseItrRows.next()
          if (fromDateViewRow != null) {
            val fromDateMillis = fromDateViewRow.key().asInstanceOf[JsonArray].get(1).toString.toLong
            fDateTime = new DateTime(fromDateMillis).withHourOfDay(0).withMinuteOfHour(0)
          }

          println(s"fromDateTime: $fDateTime")
          var eDate = tDateTime

          while (eDate.getMillis > fDateTime.getMillis) {
            val sDate = eDate.minusHours(1)

            val startKey = JsonArray.fromJson(s"[$intAppId,${sDate.getMillis}]")
            val endKey = JsonArray.fromJson(s"[$intAppId,${eDate.getMillis}]")

            val totalSessionsResult = couchbaseBucket.query(ViewQuery.from("admin", "daily_session_count").startKey(startKey).endKey(endKey).reduce(true))
            if (totalSessionsResult.success()) {
              var totalSessions = 0
              totalSessionsResult.foreach(r => {
                if (totalSessions == 0) totalSessions = r.value().toString.toInt
              })

              if (totalSessions > 0) {
                //toS3(s3Client, intAppId, sDate, startKey, endKey, totalSessions)
                migrateToElasticSearch(esClient, intAppId, sDate, startKey, endKey, totalSessions)
              }
            }
            eDate = sDate
          }
        }
      }
    })

    esClient.close()
  }

  def migrateToElasticSearch(client: TransportClient, intAppId: Integer, sDate: DateTime, startKey: JsonArray, endKey: JsonArray, totalSessions: Int): Unit = {
    val limit = couchbaseBulkLimit
    var skip = 0

    val year = sDate.toString("yyyy")
    val month = sDate.toString("MM")
    val day = sDate.toString("dd")

    while (skip < totalSessions) {
      val result = couchbaseBucket.query(ViewQuery.from("admin", "daily_session_count").startKey(startKey).endKey(endKey).reduce(false).skip(skip).limit(limit))
      if (result.success()) {
        result.foreach(row => {
          try {
            val sessionJson = row.document().content()
            val json = new JSONObject(sessionJson)
            json.remove("appViewActivity")
            json.remove("location")
            json.remove("phoneNumber")

            val indexName = s"uh-$intAppId-$year$month$day"
            val indexType = "session"

            val isIndexExists = client.admin().indices().prepareExists(indexName).execute().actionGet().isExists
            if (!isIndexExists) {
              val res = client.admin().indices().prepareCreate(indexName).addMapping(indexType, getMapping(indexType)).execute().actionGet()
              println(res.toString)
            }

            val response = client.prepareIndex(indexName, indexType)
              .setSource(json.toString)
              .get()
            println(response)
          } catch {
            case e: JSONException => println(e.getMessage)
            case e: TimeoutException =>
              logger.write(row.id())
              logger.newLine()
          }
        })
      }
      skip += limit
    }
  }

  def migrateToS3(s3Client: AmazonS3Client, intAppId: Integer, sDate: DateTime, startKey: JsonArray, endKey: JsonArray, totalSessions: Int): Boolean = {
    val limit = couchbaseBulkLimit
    var skip = 0

    val year = sDate.toString("yyyy")
    val month = sDate.toString("MM")
    val day = sDate.toString("dd")
    val hour = sDate.toString("HH")

    val dir = new File(s"./tmp/$intAppId/$year/$month/$day")
    if (!dir.exists && !dir.isDirectory) {
      dir.mkdirs()
    }

    val file = new File(s"${dir.getCanonicalPath}/$hour")
    val bw = new BufferedWriter(new FileWriter(file))

    while (skip < totalSessions) {
      val result = couchbaseBucket.query(ViewQuery.from("admin", "daily_session_count").startKey(startKey).endKey(endKey).reduce(false).skip(skip).limit(limit))
      if (result.success()) {
        result.foreach(row => {
          val doc = row.document().content()
          bw.write(doc.toString)
          bw.newLine()
        })
      }
      skip += limit
    }

    bw.close()

    val key = s"$intAppId/$year/$month/$day/$hour"
    val putResult = s3Client.putObject(new PutObjectRequest("userhabit-jake-test", key, file))
    println(putResult.getETag)

    // Remove file when S3 upload succeeds
    file.delete()
  }

  def couchbaseToFile(): Unit = {

    var fDateTime = new DateTime(fromDateTime(0), fromDateTime(1), fromDateTime(2), fromDateTime(3), fromDateTime(4))
    val tDateTime = new DateTime(toDateTime(0), toDateTime(1), toDateTime(2), toDateTime(3), toDateTime(4))

    if (appList.isEmpty) {
      appList = getAppListFromCouchbase
    }

    def writeToFile(rfw: RollingFileWriter, row: ViewRow, timeout: Long): Unit = {
      if (timeout >= 5) return
      try {
        val doc = row.document(timeout, TimeUnit.MINUTES).content()
          .put("sessionId", row.id())
        rfw.writeLine(doc.toString)
      } catch {
        case te: TimeoutException =>
          println(s"writeToFile: Increasing timeout to $timeout")
          writeToFile(rfw, row, timeout + 1)
        case e: Exception =>
          println("----------------------------------------")
          println(e.getMessage)
          println("----------------------------------------")
      }
    }

    def queryDocs(startKey: JsonArray, endKey: JsonArray, limit: Integer, skip: Int, rfw: RollingFileWriter, timeout: Long): Unit = {
      if (timeout >= 5) return
      val query = ViewQuery.from("admin", "daily_session_count").startKey(startKey).endKey(endKey).reduce(false).skip(skip).limit(limit)
      try {
        val result = couchbaseBucket.query(query, timeout, TimeUnit.MINUTES)
        if (result.success()) {
          result.foreach(row => {
            //println(file.length() + " bytes")
            writeToFile(rfw, row, 1)
          })
        }
      } catch {
        case te: TimeoutException =>
          println(s"queryDocs: Increasing timeout to $timeout")
          queryDocs(startKey, endKey, limit, skip, rfw, timeout + 1)
        case e: Exception =>
          println("----------------------------------------")
          println(e.getMessage)
          println("----------------------------------------")
          logger.write(query.toString)
          logger.newLine()
      }
    }

    appList.foreach(intAppId => {

      println(s"----------${intAppId.toString}----------")

      val fromDateResponse = couchbaseBucket.query(ViewQuery.from("admin", "daily_session_count")
        .startKey(JsonArray.fromJson(s"[$intAppId]"))
        .endKey(JsonArray.fromJson(s"[${intAppId+1}]"))
        .reduce(false))
      val fromDateResponseItrRows = fromDateResponse.rows()

      if (fromDateResponse.success()) {
        if (fromDateResponseItrRows.hasNext) {
          val fromDateViewRow = fromDateResponseItrRows.next()
          if (fromDateViewRow != null) {
            val fromDateMillis = fromDateViewRow.key().asInstanceOf[JsonArray].get(1).toString.toLong
            fDateTime = new DateTime(fromDateMillis).withHourOfDay(0).withMinuteOfHour(0)
          }

          println(s"fromDateTime: $fDateTime")
          var eDate = tDateTime

          while (eDate.getMillis > fDateTime.getMillis) {
            val sDate = eDate.minusDays(1)

            val startKey = JsonArray.fromJson(s"[$intAppId,${sDate.getMillis}]")
            val endKey = JsonArray.fromJson(s"[$intAppId,${eDate.getMillis}]")

            val totalSessionsResult = couchbaseBucket.query(ViewQuery.from("admin", "daily_session_count").startKey(startKey).endKey(endKey).reduce(true))
            if (totalSessionsResult.success()) {
              var totalSessions = 0
              totalSessionsResult.foreach(r => {
                if (totalSessions == 0) totalSessions = r.value().toString.toInt
              })

              if (totalSessions > 0) {

                val limit = couchbaseBulkLimit
                var skip = 0

                val year = sDate.toString("yyyy")
                val month = sDate.toString("MM")
                val day = sDate.toString("dd")

                val dir = new File(exportDir)
                if (!dir.exists && !dir.isDirectory) {
                  dir.mkdirs()
                }

                val rfw = new RollingFileWriter(s"${dir.getCanonicalPath}/$intAppId-$year$month$day")

                while (skip < totalSessions) {
                  queryDocs(startKey, endKey, limit, skip, rfw, 1)
                  skip += limit
                }

                rfw.close()
              }
            }
            eDate = sDate
          }
        }
      }
    })
  }

  def s3ToElasticSearch() : Unit = {
    val s3Client = new AmazonS3Client(new ProfileCredentialsProvider())
    val listObjectsRequest = new ListObjectsRequest().withBucketName("userhabit-jake-test")
    var objectListing : ObjectListing = new ObjectListing()

    val settings = Settings.settingsBuilder().build()

    val client = TransportClient.builder().settings(settings).build()
      .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(elasticNodeIp), 9300))

    do {
      objectListing = s3Client.listObjects(listObjectsRequest)
      val objectSummaries = objectListing.getObjectSummaries
      val newLine = "\n"
      println(objectSummaries.size())
      for (i <- 0 to objectSummaries.size() - 1) {
        val key = objectSummaries.get(i).getKey
        val s3Object = s3Client.getObject(new GetObjectRequest("userhabit-jake-test", key))

        val reader = new BufferedReader(new InputStreamReader(s3Object.getObjectContent))
        var byteOffset = 0
        var sessionJson = reader.readLine()
        while (sessionJson != null) {
          try {
            // displayTextInputStream(s3Object.getObjectContent)
            // val strJson = scala.io.Source.fromInputStream(s3Object.getObjectContent).mkString
            val json = new JSONObject(sessionJson)
            json.remove("appViewActivity")
            json.remove("location")
            json.remove("phoneNumber")
            // json.remove("viewFlow")

            val appId = json.getInt("appId")
            val localTime = json.getLong("localTime")
            val timestamp = new DateTime(localTime)

            val indexName = s"uh-$appId-${timestamp.toString("yyyyMMdd")}"
            val indexType = "session"

            val file = new JSONObject()
            file.put("name", key)
            file.put("offset", byteOffset)
            json.put("file", file)

            byteOffset = sessionJson.getBytes().length + newLine.getBytes().length

            val isIndexExists = client.admin().indices().prepareExists(indexName).execute().actionGet().isExists
            if (!isIndexExists) {
              val res = client.admin().indices().prepareCreate(indexName).addMapping(indexType, getMapping(indexType)).execute().actionGet()
              println(res.toString)
            }

            val response = client.prepareIndex(indexName, indexType)
              .setSource(json.toString)
              .get()
            println(response)
          } catch {
            case e: JSONException => println(e.getMessage)
          }
          sessionJson = reader.readLine()
        }
      }

      listObjectsRequest.setMarker(objectListing.getNextMarker)
    } while (objectListing.isTruncated)

    client.close()
  }

  def fileToElasticSearch(): Unit = {

    val c = ElasticHelper.getInstance()
    c.connect(InetAddress.getByName(elasticNodeIp))

//    val settings = Settings.settingsBuilder()
//      .put("cluster.name", "Avengers")
//      .put("client.transport.sniff", true).build()
//
//    val client = TransportClient.builder().settings(settings).build()
//      .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(elasticNodeIp), 9300))

    val d = new File(exportDir)
    if (d.exists() && d.isDirectory) {

      val mapper = new Mapper()
      val migrator = new V1ToV2Migrator()
      migrator.setCrashSessionHandler(new AddCrashLogToSession)

      val files = d.listFiles.filter(_.isFile).toList
      for (file <- files) {
        if (file.isFile) {
          val names = file.getName.split("-")
          if (names.length > 2) {
            val indexName = s"uh-${names(0)}-${names(1)}"
//            val indexType = "session"
            println(indexName)
//            val isIndexExists = client.admin().indices().prepareExists(indexName).get().isExists
//            if (!isIndexExists) {
//              val res = client.admin().indices().prepareCreate(indexName).addMapping(indexType, getMappingJson).get()
//              println(res.toString)
//            }

            try {
              for (line <- Source.fromFile(file.getCanonicalPath, "UTF-8").getLines) {

                val key = new JSONObject(line).get("sessionId").toString
                val v1session = mapper.readValue(line, classOf[v1.model.Session])
                val v2session = migrator.migrateToV2Session(key, v1session)
//                val data = mapper.writeValueAsString(v2session)

                c.insertSessionData(indexName, v2session)

//                val response = client.prepareIndex(indexName, indexType, key)
//                  .setSource(data)
//                  .get()
//                println(response)
              }
            } catch {
              case e: Exception => e.printStackTrace()
            }
          }
        }
      }
    }

//    client.close()
    c.close()
  }

  def getMappingJson: String = {
    Source.fromFile(getClass.getResource("/sessionMapping.json").getFile, "UTF-8").mkString
  }

  def getMapping(indexType : String) : XContentBuilder = {
    XContentFactory.contentBuilder(XContentType.JSON)
      .startObject()
      .startObject(indexType)
      .startObject("properties")
      .startObject("action")
      .field("type", "string")
      .field("index", "not_analyzed")
      .endObject()
      .startObject("addId")
      .field("type", "integer")
      .endObject()
      .startObject("appVersionCode")
      .field("type", "string")
      .field("index", "not_analyzed")
      .endObject()
      .startObject("deletionTime")
      .field("type", "date")
      .endObject()
      .startObject("device")
      .startObject("properties")
      .startObject("brand")
      .field("type", "string")
      .field("index", "not_analyzed")
      .endObject()
      .startObject("device")
      .field("type", "string")
      .field("index", "not_analyzed")
      .endObject()
      .startObject("height")
      .field("type", "string")
      .field("index", "not_analyzed")
      .endObject()
      .startObject("name")
      .field("type", "string")
      .field("index", "not_analyzed")
      .endObject()
      .startObject("width")
      .field("type", "string")
      .field("index", "not_analyzed")
      .endObject()
      .endObject()
      .endObject()
      .startObject("device_id")
      .field("type", "string")
      .field("index", "not_analyzed")
      .endObject()
      .startObject("endAt")
      .field("type", "date")
      .endObject()
      .startObject("ipLocation")
      .startObject("properties")
      .startObject("cityName")
      .field("type", "string")
      .field("index", "not_analyzed")
      .endObject()
      .startObject("continentCode")
      .field("type", "string")
      .field("index", "not_analyzed")
      .endObject()
      .startObject("latitude")
      .field("type", "double")
      .endObject()
      .startObject("longitude")
      .field("type", "double")
      .endObject()
      .startObject("subdivisionCode")
      .field("type", "string")
      .field("index", "not_analyzed")
      .endObject()
      .endObject()
      .endObject()
      .startObject("issuedAt")
      .field("type", "date")
      .endObject()
      .startObject("localTime")
      .field("type", "date")
      .endObject()
      .startObject("localTimeTz")
      .field("type", "string")
      .field("index", "not_analyzed")
      .endObject()
      .startObject("remoteIp")
      .field("type", "ip")
      .endObject()
      .startObject("sdkVersion")
      .field("type", "string")
      .field("index", "not_analyzed")
      .endObject()
      .startObject("sessionCount")
      .field("type", "long")
      .endObject()
      .startObject("t")
      .field("type", "string")
      .field("index", "not_analyzed")
      .endObject()
      .startObject("td")
      .startObject("properties")
      .startObject("locale")
      .field("type", "string")
      .field("index", "not_analyzed")
      .endObject()
      .startObject("os_version")
      .field("type", "string")
      .field("index", "not_analyzed")
      .endObject()
      .startObject("platform")
      .field("type", "string")
      .field("index", "not_analyzed")
      .endObject()
      .startObject("unique_id")
      .field("type", "string")
      .field("index", "not_analyzed")
      .endObject()
      .endObject()
      .endObject()
      .startObject("testerDeviceId")
      .field("type", "long")
      .endObject()
      .startObject("transportMerhod")
      .field("type", "integer")
      .endObject()
      .startObject("transportState")
      .field("type", "integer")
      .endObject()
      .startObject("uuid")
      .field("type", "string")
      .field("index", "not_analyzed")
      .endObject()
      .startObject("v")
      .field("type", "integer")
      .endObject()
      .startObject("versionId")
      .field("type", "long")
      .endObject()
      .startObject("viewFlow")
      .field("enabled", "false")
      .endObject()
      .startObject("file")
      .startObject("properties")
      .startObject("name")
      .field("type", "string")
      .field("index", "not_analyzed")
      .endObject()
      .startObject("offset")
      .field("type", "long")
      .endObject()
      .endObject()
      .endObject()
      .endObject()
      .endObject()
      .endObject()
  }

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
