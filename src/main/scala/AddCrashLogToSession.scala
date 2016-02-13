import io.userhabit.library.v2.tool.V1ToV2Migrator.CrashSessionHandler

/**
  * Created by DynamicScope on 2015. 12. 23..
  */
class AddCrashLogToSession extends CrashSessionHandler {

  override def processCrashSession(appId: Int, versionId: Int, key: String): String = {
//    val crashService = new CrashServiceImpl
//    val crash = crashService.findByUniqueId(key.getBytes)
//    if (crash != null)
//      crash.getStacktrace
//    else
//      "No Stacktrace"
    ""
  }
}
