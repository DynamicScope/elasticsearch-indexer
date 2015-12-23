import java.sql.Blob
import javax.sql.rowset.serial.SerialBlob

import helper.DBHelper
import io.userhabit.library.v2.tool.V1ToV2Migrator.CrashSessionHandler
import model.Tables._
import slick.driver.MySQLDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by DynamicScope on 2015. 12. 23..
  */
class AddCrashLogToSession extends CrashSessionHandler {

  override def processCrashSession(appId: Int, versionId: Int, key: String): String = {

    val sessionId: Option[Blob] = key match {
      case "" => None
      case p: String =>
        val dec = DBHelper.decrypt(p)
        val dec_byte:Array[Byte] = DBHelper.uuidStringToArray(dec)
        Some(new SerialBlob(dec_byte))
    }

    val query = Crashes.filter(f = p => p.versionId === versionId && p.uniqueId === sessionId).map(_.stacktrace).result
    val result = DBHelper.database.run(query)
    result.foreach(r => {
      println(r)
    })
    ""
  }
}
