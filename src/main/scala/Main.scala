import java.io._
import java.net.InetAddress
import java.text.SimpleDateFormat
import java.util
import java.util.logging.Logger

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.{PutObjectRequest, GetObjectRequest, ListObjectsRequest, ObjectListing}
import com.amazonaws.util.json.{JSONException, JSONObject}
import com.couchbase.client.java.{Bucket, CouchbaseCluster}
import com.couchbase.client.java.document.JsonDocument
import com.couchbase.client.java.document.json.JsonArray
import com.couchbase.client.java.view.ViewQuery
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.elasticsearch.common.xcontent.{XContentBuilder, XContentFactory, XContentType}
import org.joda.time.DateTime
import org.yaml.snakeyaml.Yaml

import scala.collection.mutable
import scala.util.control.Breaks._
import scala.collection.JavaConversions._

/**
  * Created by DynamicScope on 2015. 11. 18..
  */
object Main {

  var appList : util.ArrayList[Integer] = new util.ArrayList[Integer]()
  var couchbaseCluster : CouchbaseCluster = null
  var couchbaseNodes : util.List[String] = new util.ArrayList[String]
  var couchbaseBucket : Bucket = null
  var couchbaseBucketName = ""
  var couchbaseBucketPassword = ""

  def main (args: Array[String]): Unit = {
    loadConfig()

    println(couchbaseBucketName)
    println(couchbaseNodes)
    println(couchbaseBucketPassword)

    openCouchbase()
    couchbaseToS3()
    closeCouchbase()
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
      appList = data.getOrDefault("app_ids", new util.ArrayList[Integer]()).asInstanceOf[util.ArrayList[Integer]]

      val couchbase = data.get("couchbase").asInstanceOf[util.LinkedHashMap[String, Object]]
      if (couchbase != null) {
        couchbaseNodes = couchbase.get("nodes").asInstanceOf[util.List[String]]
        val bucket = couchbase.get("bucket").asInstanceOf[util.LinkedHashMap[String, Object]]
        couchbaseBucketName = bucket.get("name").asInstanceOf[String]
        couchbaseBucketPassword = bucket.get("password").asInstanceOf[String]
      }
    } catch {
      case e: FileNotFoundException => println(s"${file.getCanonicalPath} : was not found.")
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
    var fromDateTime = new DateTime(2015, 1, 1, 0, 0)
    val toDateTime = new DateTime(2015, 10, 18, 0, 0)

    val s3Client = new AmazonS3Client(new ProfileCredentialsProvider())

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
            fromDateTime = new DateTime(fromDateMillis).withHourOfDay(0).withMinuteOfHour(0)
          }

          println(s"----------${fromDateTime}----------")
          var eDate = toDateTime

          while (eDate.getMillis > fromDateTime.getMillis) {
            val sDate = eDate.minusHours(1)

            val startKey = JsonArray.fromJson(s"[$intAppId,${sDate.getMillis}]")
            val endKey = JsonArray.fromJson(s"[$intAppId,${eDate.getMillis}]")

            val totalSessionsResult = couchbaseBucket.query(ViewQuery.from("admin", "daily_session_count").startKey(startKey).endKey(endKey).reduce(true))
            if (totalSessionsResult.success()) {
              var totalSessions = 0
              totalSessionsResult.foreach(r => {
                totalSessions = r.value().toString.toInt
              })

              if (totalSessions > 0) {
                val limit = 500
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
                println(putResult.getMetadata)

                // Remove file when S3 upload succeeds
                file.delete()
              }
            }

            eDate = sDate
          }
        }
      }
    })
  }

  //  def elasticSearch() : Unit = {
  //    val s3Client = new AmazonS3Client(new ProfileCredentialsProvider())
  //    val listObjectsRequest = new ListObjectsRequest().withBucketName("userhabit-jake-test")
  //    var objectListing : ObjectListing = new ObjectListing()
  //
  //    val settings = Settings.settingsBuilder().build()
  //
  //    val client = TransportClient.builder().settings(settings).build()
  //      .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.31.1.101"), 9300))
  //
  //    do {
  //      objectListing = s3Client.listObjects(listObjectsRequest)
  //      val objectSummaries = objectListing.getObjectSummaries
  //      val newLine = "\n"
  //      println(objectSummaries.size())
  //      for (i <- 0 to objectSummaries.size() - 1) {
  //        val key = objectSummaries.get(i).getKey
  //        val s3Object = s3Client.getObject(new GetObjectRequest("userhabit-jake-test", key))
  //
  //        val reader = new BufferedReader(new InputStreamReader(s3Object.getObjectContent))
  //        var byteOffset = 0
  //        var sessionJson = reader.readLine()
  //        while (sessionJson != null) {
  //          try {
  //            // displayTextInputStream(s3Object.getObjectContent)
  //            // val strJson = scala.io.Source.fromInputStream(s3Object.getObjectContent).mkString
  //            val json = new JSONObject(sessionJson)
  //            json.remove("appViewActivity")
  //            json.remove("location")
  //            json.remove("phoneNumber")
  //            // json.remove("viewFlow")
  //
  //            val appId = json.getInt("appId")
  //            val localTime = json.getLong("localTime")
  //            val timestamp = new DateTime(localTime)
  //
  //            val indexName = s"uh-${appId}-${timestamp.toString("yyyyMMdd")}"
  //            val indexType = "session"
  //
  //            val file = new JSONObject()
  //            file.put("name", key)
  //            file.put("offset", byteOffset)
  //            json.put("file", file)
  //
  //            byteOffset = sessionJson.getBytes().length + newLine.getBytes().length
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
  //            //println(response)
  //          } catch {
  //            case e: JSONException => println(e.getMessage)
  //          }
  //          sessionJson = reader.readLine()
  //        }
  //      }
  //
  //      listObjectsRequest.setMarker(objectListing.getNextMarker)
  //    } while (objectListing.isTruncated)
  //
  //    client.close()
  //  }

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
