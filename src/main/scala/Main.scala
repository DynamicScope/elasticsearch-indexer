import java.io.{InputStreamReader, BufferedReader, InputStream}

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.{ObjectListing, S3ObjectSummary, ListObjectsRequest, GetObjectRequest}
import org.elasticsearch.action.index.IndexResponse
import org.elasticsearch.common.xcontent.XContentFactory._
import org.elasticsearch.node.NodeBuilder._
import scala.util.control.Breaks._

/**
  * Created by DynamicScope on 2015. 11. 18..
  */
object Main {
  def main (args: Array[String]): Unit = {
    val s3Client = new AmazonS3Client(new ProfileCredentialsProvider())
    val listObjectsRequest = new ListObjectsRequest().withBucketName("userhabit-jake-test").withPrefix("2015/11/8/")
    var objectListing : ObjectListing = new ObjectListing()

    val node = nodeBuilder().node()
    val client = node.client()

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

    node.close()
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
