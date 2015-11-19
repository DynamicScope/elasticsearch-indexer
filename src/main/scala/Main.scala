import java.io.{BufferedReader, InputStream, InputStreamReader}
import java.net.InetAddress

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.{GetObjectRequest, ListObjectsRequest, ObjectListing}
import com.amazonaws.util.json.{JSONException, JSONObject}
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.transport.InetSocketTransportAddress

import scala.util.control.Breaks._

/**
  * Created by DynamicScope on 2015. 11. 18..
  */
object Main {
  def main (args: Array[String]): Unit = {
    val s3Client = new AmazonS3Client(new ProfileCredentialsProvider())
    val listObjectsRequest = new ListObjectsRequest().withBucketName("userhabit-jake-test")
    var objectListing : ObjectListing = new ObjectListing()

    val client = TransportClient.builder().build()
      .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300))

    do {
      objectListing = s3Client.listObjects(listObjectsRequest)
      val objectSummaries = objectListing.getObjectSummaries
      println(objectSummaries.size())
      for (i <- 0 to objectSummaries.size() - 1) {
        val key = objectSummaries.get(i).getKey
        val s3Object = s3Client.getObject(new GetObjectRequest("userhabit-jake-test", key))

        val reader = new BufferedReader(new InputStreamReader(s3Object.getObjectContent))
        var line = reader.readLine()
        while (line != null) {
          try {
            // displayTextInputStream(s3Object.getObjectContent)
            // val strJson = scala.io.Source.fromInputStream(s3Object.getObjectContent).mkString
            val json = new JSONObject(line)
            json.remove("appViewActivity")
            json.remove("viewFlow")
//            println(json.getString("device_id"))

            val response = client.prepareIndex("userhabit", "raw")
              .setSource(json.toString)
              .get()
            println(response)
          } catch {
            case e: JSONException => println(e.getMessage)
          }
          line = reader.readLine()
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
