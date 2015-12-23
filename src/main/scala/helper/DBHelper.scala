package helper

import java.nio.{ByteOrder, ByteBuffer}
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.{UUID, Date, TimeZone}
import javax.crypto.Cipher
import javax.crypto.spec.{IvParameterSpec, SecretKeySpec}

import com.amazonaws.util.Base64
import com.typesafe.config.ConfigFactory
import slick.driver.MySQLDriver.api._

/**
  * Created by DynamicScope on 2015. 12. 23..
  */
object DBHelper {
  object DBConfig {
    private val config = ConfigFactory.load()
    val url = config.getString("mysql.url")
    val username = config.getString("mysql.username")
    val password = config.getString("mysql.password")
    val driver = config.getString("mysql.driver")
    val slickDriver = config.getString("mysql.slick_driver")
  }

//  lazy val database = {
//    val ds = new ComboPooledDataSource
//    ds.setDriverClass(DBConfig.driver)
//    ds.setJdbcUrl(DBConfig.url)
//    ds.setUser(DBConfig.username)
//    ds.setPassword(DBConfig.password)
//    Database.forDataSource(ds)
//  }

  lazy val database = Database.forConfig("mysql")

  def gen_model() = {
    slick.codegen.SourceCodeGenerator.main(
      Array(DBConfig.slickDriver,
        DBConfig.driver,
        DBConfig.url,
        "src/main/scala",
        "model",
        DBConfig.username,
        DBConfig.password
      )
    )

  }

  def getTimestamp(t:String): Timestamp = {
    val sdf:SimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss")
    sdf.setTimeZone(TimeZone.getTimeZone("GMT"))
    val d:Date = sdf.parse(t)

    new Timestamp(d.getTime)
  }

  def getTimeLong(t:String): Long = {
    val sdf:SimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss")
    sdf.setTimeZone(TimeZone.getTimeZone("GMT"))
    val d:Date = sdf.parse(t)

    d.getTime
  }

  val secretKey =  Base64.decode(ConfigFactory.load().getString("userhabit.key").getBytes("UTF-8"))

  def decrypt(content:String):String = {
    val arrContents = content.split("--")
    val bData = Base64.decode(arrContents(0))
    val bIV = Base64.decode(arrContents(1))
    val cipher:Cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    val key:SecretKeySpec = new SecretKeySpec(secretKey, "AES")
    cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(bIV))
    new String(cipher.doFinal(bData),"UTF-8")
  }

  def uuidStringToArray(uniqueId: String): Array[Byte] = {
    val uuid: UUID = UUID.fromString(uniqueId)
    val bb:ByteBuffer = ByteBuffer.allocate(16)
    bb.order(ByteOrder.BIG_ENDIAN)
    bb.putLong(uuid.getMostSignificantBits)
    bb.putLong(8, uuid.getLeastSignificantBits)
    bb.array()
  }

}
