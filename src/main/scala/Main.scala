import java.io.{BufferedReader, InputStream, InputStreamReader}
import java.net.InetAddress

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.{GetObjectRequest, ListObjectsRequest, ObjectListing}
import com.amazonaws.util.json.{JSONException, JSONObject}
import com.couchbase.client.java.CouchbaseCluster
import com.couchbase.client.java.view.{ViewRow, ViewQuery}
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.elasticsearch.common.xcontent.{XContentBuilder, XContentType, XContentFactory}
import org.joda.time.DateTime

import scala.util.control.Breaks._

/**
  * Created by DynamicScope on 2015. 11. 18..
  */
object Main {
  def main (args: Array[String]): Unit = {
   couchbase()
  }

  def couchbase() : Unit = {
    val cluster = CouchbaseCluster.create("172.31.15.189", "172.31.15.190", "172.31.15.191")
    val bucket = cluster.openBucket("userhabit", "6etx[9nHC^874n")

    val result = bucket.query(ViewQuery.from("admin", "daily_session_count").limit(10))

    for (row : ViewRow <- result) {
      val doc = row.document()

      println(doc.id())
    }

    cluster.disconnect()
  }

  def elasticSearch() : Unit = {
    val s3Client = new AmazonS3Client(new ProfileCredentialsProvider())
    val listObjectsRequest = new ListObjectsRequest().withBucketName("userhabit-jake-test")
    var objectListing : ObjectListing = new ObjectListing()

    val settings = Settings.settingsBuilder().build()

    val client = TransportClient.builder().settings(settings).build()
      .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.31.1.101"), 9300))

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

            val indexName = s"uh-${appId}-${timestamp.toString("yyyyMMdd")}"
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
            //println(response)
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
