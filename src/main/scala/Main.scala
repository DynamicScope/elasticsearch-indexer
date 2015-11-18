import java.io.{BufferedReader, InputStream, InputStreamReader}

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.{GetObjectRequest, ListObjectsRequest, ObjectListing}
import org.elasticsearch.client.transport.TransportClient

import scala.util.control.Breaks._

/**
  * Created by DynamicScope on 2015. 11. 18..
  */
object Main {
  def main (args: Array[String]): Unit = {
    val s3Client = new AmazonS3Client(new ProfileCredentialsProvider())
    val listObjectsRequest = new ListObjectsRequest().withBucketName("userhabit-jake-test").withPrefix("2015/11/8/")
    var objectListing : ObjectListing = new ObjectListing()

    val client = TransportClient.builder().build()

    do {
      objectListing = s3Client.listObjects(listObjectsRequest)
      val objectSummaries = objectListing.getObjectSummaries
      println(objectSummaries.size())
      for (i <- 0 to objectSummaries.size() - 1) {
        val key = objectSummaries.get(i).getKey
        val s3Object = s3Client.getObject(new GetObjectRequest("userhabit-jake-test", key))
//        displayTextInputStream(s3Object.getObjectContent)
        val json = scala.io.Source.fromInputStream(s3Object.getObjectContent).mkString
        val response = client.prepareIndex("userhabit", "jake", "1")
          .setSource(json)
          .get()
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
