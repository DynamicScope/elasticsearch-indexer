import java.io.{BufferedReader, InputStream, InputStreamReader}
import java.net.InetAddress

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.{GetObjectRequest, ListObjectsRequest, ObjectListing}
import com.amazonaws.util.json.{JSONException, JSONObject}
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.joda.time.DateTime

import scala.util.control.Breaks._

/**
  * Created by DynamicScope on 2015. 11. 18..
  */
object Main {
  def main (args: Array[String]): Unit = {
    val s3Client = new AmazonS3Client(new ProfileCredentialsProvider())
    val listObjectsRequest = new ListObjectsRequest().withBucketName("userhabit-jake-test")
    var objectListing : ObjectListing = new ObjectListing()

    val settings = Settings.settingsBuilder()
      .put("cluster.name", "uh-elasticsearch")
      .put("client.transport.sniff", true).build()

    val client = TransportClient.builder().settings(settings).build()
      .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.31.1.101"), 9300))
      .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.31.1.100"), 9300))

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
            // json.remove("viewFlow")

            val appId = json.getInt("appId")
            val localTime = json.getLong("localTime")
            val timestamp = new DateTime(localTime)

            val indexName = s"uh-${appId}-${timestamp.toString("yyyyMMdd")}"

//            val file = new JSONObject()
//            file.put("name", key)
//            file.put("offset", byteOffset)
//            json.put("file", file)
//
//            byteOffset = sessionJson.getBytes().length + newLine.getBytes().length
//
//            println(key)
//            println(byteOffset)

            val response = client.prepareIndex(indexName, "raw")
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
