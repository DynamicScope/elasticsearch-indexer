package helper

import com.typesafe.config.ConfigFactory
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

import scala.collection.JavaConversions._

/**
  * Created by DynamicScope on 2016. 1. 31..
  */
object ConfigHelper {

  var appIds: List[Integer] = null
  var fromDate: DateTime = DateTime.now()
  var toDate = DateTime.now()
  var dirExport = "./export"

  var cbNodes = "127.0.0.1"
  var cbBucket = ""
  var cbBucketPassword = ""
  var cbBulkLimit = 500

  var elasticHost: String = ""
  var esBulkActions = 100
  var esBulkConcurrentRequest = 1

  var s3Bucket: String = ""

  def load(): Unit = {
    try {
      val config = ConfigFactory.load()

      appIds = config.getIntList("userhabit.app_ids").toList

      val formatter = DateTimeFormat.forPattern("YYYYMMdd")
      fromDate = formatter.parseDateTime(config.getString("userhabit.from_date"))
      toDate = formatter.parseDateTime(config.getString("userhabit.to_date"))

      dirExport = config.getString("userhabit.dir_export")

      cbNodes = config.getStringList("couchbase.nodes").toList.mkString(",")
      cbBucket = config.getString("couchbase.bucket")
      cbBucketPassword = config.getString("couchbase.bucket_password")
      cbBulkLimit = config.getInt("couchbase.bulk_limit")

      elasticHost = config.getString("elastic.host")
      esBulkActions = config.getInt("elastic.bulk_actions")
      esBulkConcurrentRequest = config.getInt("elastic.bulk_concurrent_request")

      s3Bucket = config.getString("s3.bucket")
    } catch {
      case e: Exception =>
        throw e
    }
  }
}
