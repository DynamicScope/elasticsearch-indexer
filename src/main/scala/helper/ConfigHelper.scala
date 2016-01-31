package helper

import com.typesafe.config.ConfigFactory

/**
  * Created by DynamicScope on 2016. 1. 31..
  */
object ConfigHelper {

  var elasticHost: String = ""
  var s3Bucket: String = ""

  def load(): Unit = {
    val config = ConfigFactory.load()

    elasticHost = config.getString("elastic.host")
    s3Bucket = config.getString("s3.bucket")
  }
}
