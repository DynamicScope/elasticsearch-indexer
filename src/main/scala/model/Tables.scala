package model
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.MySQLDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  import slick.collection.heterogeneous._
  import slick.collection.heterogeneous.syntax._
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Array(Accounts.schema, AccountsRoles.schema, AppObjectAliases.schema, Apps.schema, AppViewAnalyses.schema, AppViews.schema, BaseApps.schema, BootsyImageGalleries.schema, BootsyImages.schema, Campaigns.schema, ChangeLogs.schema, CheckpointPassCountReferences.schema, CheckpointPasses.schema, CheckpointPassLogs.schema, CheckpointRelations.schema, Checkpoints.schema, CheckpointTypes.schema, CkeditorAssets.schema, Crashes.schema, DailySessionStats.schema, DelayedJobs.schema, DeviceModels.schema, DeviceUsageAggregations.schema, DeviseUsageLogs.schema, Events.schema, HeadAppViews.schema, InnerData.schema, MonthlySessionStats.schema, Notices.schema, ObfuscationMappings.schema, OsUsageAggregations.schema, OsVersions.schema, PublishedApps.schema, RedactorAssets.schema, Roles.schema, ScenarioPaths.schema, ScenarioProgresses.schema, Scenarios.schema, SchemaMigrations.schema, Sessions.schema, SurveyChoices.schema, SurveyDailyCounters.schema, SurveyExposures.schema, SurveyResults.schema, Surveys.schema, SurveyTypes.schema, TesterActivities.schema, TesterDevices.schema, TesterMetaData.schema, TesterMetaDataTypes.schema, TesterSessions.schema, Users.schema, UsersComments.schema, Versions.schema, ViewFlows.schema, WeeklySessionStats.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Accounts
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(VARCHAR), Length(255,true)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME)
   *  @param timeZone Database column time_zone SqlType(VARCHAR), Length(255,true), Default(None) */
  case class AccountsRow(id: Int, name: String, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp, timeZone: Option[String] = None)
  /** GetResult implicit for fetching AccountsRow objects using plain SQL queries */
  implicit def GetResultAccountsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp], e3: GR[Option[String]]): GR[AccountsRow] = GR{
    prs => import prs._
    AccountsRow.tupled((<<[Int], <<[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<?[String]))
  }
  /** Table description of table accounts. Objects of this class serve as prototypes for rows in queries. */
  class Accounts(_tableTag: Tag) extends Table[AccountsRow](_tableTag, "accounts") {
    def * = (id, name, createdAt, updatedAt, timeZone) <> (AccountsRow.tupled, AccountsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name), Rep.Some(createdAt), Rep.Some(updatedAt), timeZone).shaped.<>({r=>import r._; _1.map(_=> AccountsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
    /** Database column time_zone SqlType(VARCHAR), Length(255,true), Default(None) */
    val timeZone: Rep[Option[String]] = column[Option[String]]("time_zone", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table Accounts */
  lazy val Accounts = new TableQuery(tag => new Accounts(tag))

  /** Entity class storing rows of table AccountsRoles
   *  @param accountId Database column account_id SqlType(INT), Default(None)
   *  @param roleId Database column role_id SqlType(INT), Default(None) */
  case class AccountsRolesRow(accountId: Option[Int] = None, roleId: Option[Int] = None)
  /** GetResult implicit for fetching AccountsRolesRow objects using plain SQL queries */
  implicit def GetResultAccountsRolesRow(implicit e0: GR[Option[Int]]): GR[AccountsRolesRow] = GR{
    prs => import prs._
    AccountsRolesRow.tupled((<<?[Int], <<?[Int]))
  }
  /** Table description of table accounts_roles. Objects of this class serve as prototypes for rows in queries. */
  class AccountsRoles(_tableTag: Tag) extends Table[AccountsRolesRow](_tableTag, "accounts_roles") {
    def * = (accountId, roleId) <> (AccountsRolesRow.tupled, AccountsRolesRow.unapply)

    /** Database column account_id SqlType(INT), Default(None) */
    val accountId: Rep[Option[Int]] = column[Option[Int]]("account_id", O.Default(None))
    /** Database column role_id SqlType(INT), Default(None) */
    val roleId: Rep[Option[Int]] = column[Option[Int]]("role_id", O.Default(None))

    /** Index over (accountId,roleId) (database name index_accounts_roles_on_account_id_and_role_id) */
    val index1 = index("index_accounts_roles_on_account_id_and_role_id", (accountId, roleId))
  }
  /** Collection-like TableQuery object for table AccountsRoles */
  lazy val AccountsRoles = new TableQuery(tag => new AccountsRoles(tag))

  /** Entity class storing rows of table AppObjectAliases
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param headAppViewId Database column head_app_view_id SqlType(INT), Default(None)
   *  @param uniqueObjectName Database column unique_object_name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param alias Database column alias SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME) */
  case class AppObjectAliasesRow(id: Int, headAppViewId: Option[Int] = None, uniqueObjectName: Option[String] = None, alias: Option[String] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching AppObjectAliasesRow objects using plain SQL queries */
  implicit def GetResultAppObjectAliasesRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[String]], e3: GR[java.sql.Timestamp]): GR[AppObjectAliasesRow] = GR{
    prs => import prs._
    AppObjectAliasesRow.tupled((<<[Int], <<?[Int], <<?[String], <<?[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table app_object_aliases. Objects of this class serve as prototypes for rows in queries. */
  class AppObjectAliases(_tableTag: Tag) extends Table[AppObjectAliasesRow](_tableTag, "app_object_aliases") {
    def * = (id, headAppViewId, uniqueObjectName, alias, createdAt, updatedAt) <> (AppObjectAliasesRow.tupled, AppObjectAliasesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), headAppViewId, uniqueObjectName, alias, Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> AppObjectAliasesRow.tupled((_1.get, _2, _3, _4, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column head_app_view_id SqlType(INT), Default(None) */
    val headAppViewId: Rep[Option[Int]] = column[Option[Int]]("head_app_view_id", O.Default(None))
    /** Database column unique_object_name SqlType(VARCHAR), Length(255,true), Default(None) */
    val uniqueObjectName: Rep[Option[String]] = column[Option[String]]("unique_object_name", O.Length(255,varying=true), O.Default(None))
    /** Database column alias SqlType(VARCHAR), Length(255,true), Default(None) */
    val alias: Rep[Option[String]] = column[Option[String]]("alias", O.Length(255,varying=true), O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Uniqueness Index over (headAppViewId,uniqueObjectName) (database name head_app_view_id_unique_object_name_index) */
    val index1 = index("head_app_view_id_unique_object_name_index", (headAppViewId, uniqueObjectName), unique=true)
    /** Index over (headAppViewId) (database name index_app_object_aliases_on_head_app_view_id) */
    val index2 = index("index_app_object_aliases_on_head_app_view_id", headAppViewId)
  }
  /** Collection-like TableQuery object for table AppObjectAliases */
  lazy val AppObjectAliases = new TableQuery(tag => new AppObjectAliases(tag))

  /** Entity class storing rows of table Apps
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(VARCHAR), Length(255,true)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME)
   *  @param osPlatform Database column os_platform SqlType(INT), Default(None)
   *  @param packageName Database column package_name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param apiKey Database column api_key SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param recentSdkVersion Database column recent_sdk_version SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param isVisible Database column is_visible SqlType(BIT), Default(None)
   *  @param iconFileName Database column icon_file_name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param iconContentType Database column icon_content_type SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param iconFileSize Database column icon_file_size SqlType(INT), Default(None)
   *  @param iconUpdatedAt Database column icon_updated_at SqlType(DATETIME), Default(None)
   *  @param transportMethod Database column transport_method SqlType(INT), Default(Some(1))
   *  @param publishedAppId Database column published_app_id SqlType(INT), Default(None)
   *  @param appType Database column app_type SqlType(INT), Default(None)
   *  @param baseAppId Database column base_app_id SqlType(INT), Default(None) */
  case class AppsRow(id: Int, name: String, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp, osPlatform: Option[Int] = None, packageName: Option[String] = None, apiKey: Option[String] = None, recentSdkVersion: Option[String] = None, isVisible: Option[Boolean] = None, iconFileName: Option[String] = None, iconContentType: Option[String] = None, iconFileSize: Option[Int] = None, iconUpdatedAt: Option[java.sql.Timestamp] = None, transportMethod: Option[Int] = Some(1), publishedAppId: Option[Int] = None, appType: Option[Int] = None, baseAppId: Option[Int] = None)
  /** GetResult implicit for fetching AppsRow objects using plain SQL queries */
  implicit def GetResultAppsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp], e3: GR[Option[Int]], e4: GR[Option[String]], e5: GR[Option[Boolean]], e6: GR[Option[java.sql.Timestamp]]): GR[AppsRow] = GR{
    prs => import prs._
    AppsRow.tupled((<<[Int], <<[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<?[Int], <<?[String], <<?[String], <<?[String], <<?[Boolean], <<?[String], <<?[String], <<?[Int], <<?[java.sql.Timestamp], <<?[Int], <<?[Int], <<?[Int], <<?[Int]))
  }
  /** Table description of table apps. Objects of this class serve as prototypes for rows in queries. */
  class Apps(_tableTag: Tag) extends Table[AppsRow](_tableTag, "apps") {
    def * = (id, name, createdAt, updatedAt, osPlatform, packageName, apiKey, recentSdkVersion, isVisible, iconFileName, iconContentType, iconFileSize, iconUpdatedAt, transportMethod, publishedAppId, appType, baseAppId) <> (AppsRow.tupled, AppsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name), Rep.Some(createdAt), Rep.Some(updatedAt), osPlatform, packageName, apiKey, recentSdkVersion, isVisible, iconFileName, iconContentType, iconFileSize, iconUpdatedAt, transportMethod, publishedAppId, appType, baseAppId).shaped.<>({r=>import r._; _1.map(_=> AppsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16, _17)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
    /** Database column os_platform SqlType(INT), Default(None) */
    val osPlatform: Rep[Option[Int]] = column[Option[Int]]("os_platform", O.Default(None))
    /** Database column package_name SqlType(VARCHAR), Length(255,true), Default(None) */
    val packageName: Rep[Option[String]] = column[Option[String]]("package_name", O.Length(255,varying=true), O.Default(None))
    /** Database column api_key SqlType(VARCHAR), Length(255,true), Default(None) */
    val apiKey: Rep[Option[String]] = column[Option[String]]("api_key", O.Length(255,varying=true), O.Default(None))
    /** Database column recent_sdk_version SqlType(VARCHAR), Length(255,true), Default(None) */
    val recentSdkVersion: Rep[Option[String]] = column[Option[String]]("recent_sdk_version", O.Length(255,varying=true), O.Default(None))
    /** Database column is_visible SqlType(BIT), Default(None) */
    val isVisible: Rep[Option[Boolean]] = column[Option[Boolean]]("is_visible", O.Default(None))
    /** Database column icon_file_name SqlType(VARCHAR), Length(255,true), Default(None) */
    val iconFileName: Rep[Option[String]] = column[Option[String]]("icon_file_name", O.Length(255,varying=true), O.Default(None))
    /** Database column icon_content_type SqlType(VARCHAR), Length(255,true), Default(None) */
    val iconContentType: Rep[Option[String]] = column[Option[String]]("icon_content_type", O.Length(255,varying=true), O.Default(None))
    /** Database column icon_file_size SqlType(INT), Default(None) */
    val iconFileSize: Rep[Option[Int]] = column[Option[Int]]("icon_file_size", O.Default(None))
    /** Database column icon_updated_at SqlType(DATETIME), Default(None) */
    val iconUpdatedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("icon_updated_at", O.Default(None))
    /** Database column transport_method SqlType(INT), Default(Some(1)) */
    val transportMethod: Rep[Option[Int]] = column[Option[Int]]("transport_method", O.Default(Some(1)))
    /** Database column published_app_id SqlType(INT), Default(None) */
    val publishedAppId: Rep[Option[Int]] = column[Option[Int]]("published_app_id", O.Default(None))
    /** Database column app_type SqlType(INT), Default(None) */
    val appType: Rep[Option[Int]] = column[Option[Int]]("app_type", O.Default(None))
    /** Database column base_app_id SqlType(INT), Default(None) */
    val baseAppId: Rep[Option[Int]] = column[Option[Int]]("base_app_id", O.Default(None))

    /** Uniqueness Index over (packageName) (database name index_apps_on_account_id_and_package_name) */
    val index1 = index("index_apps_on_account_id_and_package_name", packageName, unique=true)
    /** Index over (apiKey) (database name index_apps_on_api_key) */
    val index2 = index("index_apps_on_api_key", apiKey)
    /** Index over (baseAppId,appType) (database name index_apps_on_base_app_id_and_app_type) */
    val index3 = index("index_apps_on_base_app_id_and_app_type", (baseAppId, appType))
    /** Index over (publishedAppId) (database name index_apps_on_published_app_id) */
    val index4 = index("index_apps_on_published_app_id", publishedAppId)
  }
  /** Collection-like TableQuery object for table Apps */
  lazy val Apps = new TableQuery(tag => new Apps(tag))

  /** Entity class storing rows of table AppViewAnalyses
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param appViewId Database column app_view_id SqlType(INT)
   *  @param analysisDate Database column analysis_date SqlType(INT)
   *  @param previousViewCount Database column previous_view_count SqlType(TEXT), Default(None)
   *  @param nextViewCount Database column next_view_count SqlType(TEXT), Default(None)
   *  @param objectClickCount Database column object_click_count SqlType(TEXT), Default(None)
   *  @param duration Database column duration SqlType(INT), Default(None)
   *  @param sessionCount Database column session_count SqlType(INT), Default(None)
   *  @param interactionCount Database column interaction_count SqlType(INT), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME)
   *  @param visitCount Database column visit_count SqlType(INT), Default(None)
   *  @param coordinateClickCount Database column coordinate_click_count SqlType(LONGBLOB), Default(None)
   *  @param unresponsiveCoordinateCount Database column unresponsive_coordinate_count SqlType(LONGBLOB), Default(None)
   *  @param gesturesJson Database column gestures_json SqlType(TEXT), Default(None)
   *  @param swipeCoordinateList Database column swipe_coordinate_list SqlType(LONGBLOB), Default(None)
   *  @param longPressCoordinateJson Database column long_press_coordinate_json SqlType(LONGBLOB), Default(None)
   *  @param doubleTapCoordinateJson Database column double_tap_coordinate_json SqlType(LONGBLOB), Default(None)
   *  @param scrolledObjectCount Database column scrolled_object_count SqlType(LONGBLOB), Default(None) */
  case class AppViewAnalysesRow(id: Int, appViewId: Int, analysisDate: Int, previousViewCount: Option[String] = None, nextViewCount: Option[String] = None, objectClickCount: Option[String] = None, duration: Option[Int] = None, sessionCount: Option[Int] = None, interactionCount: Option[Int] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp, visitCount: Option[Int] = None, coordinateClickCount: Option[java.sql.Blob] = None, unresponsiveCoordinateCount: Option[java.sql.Blob] = None, gesturesJson: Option[String] = None, swipeCoordinateList: Option[java.sql.Blob] = None, longPressCoordinateJson: Option[java.sql.Blob] = None, doubleTapCoordinateJson: Option[java.sql.Blob] = None, scrolledObjectCount: Option[java.sql.Blob] = None)
  /** GetResult implicit for fetching AppViewAnalysesRow objects using plain SQL queries */
  implicit def GetResultAppViewAnalysesRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Int]], e3: GR[java.sql.Timestamp], e4: GR[Option[java.sql.Blob]]): GR[AppViewAnalysesRow] = GR{
    prs => import prs._
    AppViewAnalysesRow.tupled((<<[Int], <<[Int], <<[Int], <<?[String], <<?[String], <<?[String], <<?[Int], <<?[Int], <<?[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<?[Int], <<?[java.sql.Blob], <<?[java.sql.Blob], <<?[String], <<?[java.sql.Blob], <<?[java.sql.Blob], <<?[java.sql.Blob], <<?[java.sql.Blob]))
  }
  /** Table description of table app_view_analyses. Objects of this class serve as prototypes for rows in queries. */
  class AppViewAnalyses(_tableTag: Tag) extends Table[AppViewAnalysesRow](_tableTag, "app_view_analyses") {
    def * = (id, appViewId, analysisDate, previousViewCount, nextViewCount, objectClickCount, duration, sessionCount, interactionCount, createdAt, updatedAt, visitCount, coordinateClickCount, unresponsiveCoordinateCount, gesturesJson, swipeCoordinateList, longPressCoordinateJson, doubleTapCoordinateJson, scrolledObjectCount) <> (AppViewAnalysesRow.tupled, AppViewAnalysesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(appViewId), Rep.Some(analysisDate), previousViewCount, nextViewCount, objectClickCount, duration, sessionCount, interactionCount, Rep.Some(createdAt), Rep.Some(updatedAt), visitCount, coordinateClickCount, unresponsiveCoordinateCount, gesturesJson, swipeCoordinateList, longPressCoordinateJson, doubleTapCoordinateJson, scrolledObjectCount).shaped.<>({r=>import r._; _1.map(_=> AppViewAnalysesRow.tupled((_1.get, _2.get, _3.get, _4, _5, _6, _7, _8, _9, _10.get, _11.get, _12, _13, _14, _15, _16, _17, _18, _19)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column app_view_id SqlType(INT) */
    val appViewId: Rep[Int] = column[Int]("app_view_id")
    /** Database column analysis_date SqlType(INT) */
    val analysisDate: Rep[Int] = column[Int]("analysis_date")
    /** Database column previous_view_count SqlType(TEXT), Default(None) */
    val previousViewCount: Rep[Option[String]] = column[Option[String]]("previous_view_count", O.Default(None))
    /** Database column next_view_count SqlType(TEXT), Default(None) */
    val nextViewCount: Rep[Option[String]] = column[Option[String]]("next_view_count", O.Default(None))
    /** Database column object_click_count SqlType(TEXT), Default(None) */
    val objectClickCount: Rep[Option[String]] = column[Option[String]]("object_click_count", O.Default(None))
    /** Database column duration SqlType(INT), Default(None) */
    val duration: Rep[Option[Int]] = column[Option[Int]]("duration", O.Default(None))
    /** Database column session_count SqlType(INT), Default(None) */
    val sessionCount: Rep[Option[Int]] = column[Option[Int]]("session_count", O.Default(None))
    /** Database column interaction_count SqlType(INT), Default(None) */
    val interactionCount: Rep[Option[Int]] = column[Option[Int]]("interaction_count", O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
    /** Database column visit_count SqlType(INT), Default(None) */
    val visitCount: Rep[Option[Int]] = column[Option[Int]]("visit_count", O.Default(None))
    /** Database column coordinate_click_count SqlType(LONGBLOB), Default(None) */
    val coordinateClickCount: Rep[Option[java.sql.Blob]] = column[Option[java.sql.Blob]]("coordinate_click_count", O.Default(None))
    /** Database column unresponsive_coordinate_count SqlType(LONGBLOB), Default(None) */
    val unresponsiveCoordinateCount: Rep[Option[java.sql.Blob]] = column[Option[java.sql.Blob]]("unresponsive_coordinate_count", O.Default(None))
    /** Database column gestures_json SqlType(TEXT), Default(None) */
    val gesturesJson: Rep[Option[String]] = column[Option[String]]("gestures_json", O.Default(None))
    /** Database column swipe_coordinate_list SqlType(LONGBLOB), Default(None) */
    val swipeCoordinateList: Rep[Option[java.sql.Blob]] = column[Option[java.sql.Blob]]("swipe_coordinate_list", O.Default(None))
    /** Database column long_press_coordinate_json SqlType(LONGBLOB), Default(None) */
    val longPressCoordinateJson: Rep[Option[java.sql.Blob]] = column[Option[java.sql.Blob]]("long_press_coordinate_json", O.Default(None))
    /** Database column double_tap_coordinate_json SqlType(LONGBLOB), Default(None) */
    val doubleTapCoordinateJson: Rep[Option[java.sql.Blob]] = column[Option[java.sql.Blob]]("double_tap_coordinate_json", O.Default(None))
    /** Database column scrolled_object_count SqlType(LONGBLOB), Default(None) */
    val scrolledObjectCount: Rep[Option[java.sql.Blob]] = column[Option[java.sql.Blob]]("scrolled_object_count", O.Default(None))

    /** Index over (appViewId) (database name index_app_view_analyses_on_app_view_id) */
    val index1 = index("index_app_view_analyses_on_app_view_id", appViewId)
    /** Index over (appViewId,analysisDate) (database name index_app_view_analyses_on_app_view_id_and_analysis_date) */
    val index2 = index("index_app_view_analyses_on_app_view_id_and_analysis_date", (appViewId, analysisDate))
  }
  /** Collection-like TableQuery object for table AppViewAnalyses */
  lazy val AppViewAnalyses = new TableQuery(tag => new AppViewAnalyses(tag))

  /** Entity class storing rows of table AppViews
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param deviceModelId Database column device_model_id SqlType(INT)
   *  @param appVersionName Database column app_version_name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME)
   *  @param screenshotFileName Database column screenshot_file_name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param screenshotContentType Database column screenshot_content_type SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param screenshotFileSize Database column screenshot_file_size SqlType(INT), Default(None)
   *  @param screenshotUpdatedAt Database column screenshot_updated_at SqlType(DATETIME), Default(None)
   *  @param objectList Database column object_list SqlType(TEXT), Default(None)
   *  @param visitCount Database column visit_count SqlType(INT), Default(None)
   *  @param headAppViewId Database column head_app_view_id SqlType(INT), Default(None) */
  case class AppViewsRow(id: Int, deviceModelId: Int, appVersionName: Option[String] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp, screenshotFileName: Option[String] = None, screenshotContentType: Option[String] = None, screenshotFileSize: Option[Int] = None, screenshotUpdatedAt: Option[java.sql.Timestamp] = None, objectList: Option[String] = None, visitCount: Option[Int] = None, headAppViewId: Option[Int] = None)
  /** GetResult implicit for fetching AppViewsRow objects using plain SQL queries */
  implicit def GetResultAppViewsRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[java.sql.Timestamp], e3: GR[Option[Int]], e4: GR[Option[java.sql.Timestamp]]): GR[AppViewsRow] = GR{
    prs => import prs._
    AppViewsRow.tupled((<<[Int], <<[Int], <<?[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<?[String], <<?[String], <<?[Int], <<?[java.sql.Timestamp], <<?[String], <<?[Int], <<?[Int]))
  }
  /** Table description of table app_views. Objects of this class serve as prototypes for rows in queries. */
  class AppViews(_tableTag: Tag) extends Table[AppViewsRow](_tableTag, "app_views") {
    def * = (id, deviceModelId, appVersionName, createdAt, updatedAt, screenshotFileName, screenshotContentType, screenshotFileSize, screenshotUpdatedAt, objectList, visitCount, headAppViewId) <> (AppViewsRow.tupled, AppViewsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(deviceModelId), appVersionName, Rep.Some(createdAt), Rep.Some(updatedAt), screenshotFileName, screenshotContentType, screenshotFileSize, screenshotUpdatedAt, objectList, visitCount, headAppViewId).shaped.<>({r=>import r._; _1.map(_=> AppViewsRow.tupled((_1.get, _2.get, _3, _4.get, _5.get, _6, _7, _8, _9, _10, _11, _12)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column device_model_id SqlType(INT) */
    val deviceModelId: Rep[Int] = column[Int]("device_model_id")
    /** Database column app_version_name SqlType(VARCHAR), Length(255,true), Default(None) */
    val appVersionName: Rep[Option[String]] = column[Option[String]]("app_version_name", O.Length(255,varying=true), O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
    /** Database column screenshot_file_name SqlType(VARCHAR), Length(255,true), Default(None) */
    val screenshotFileName: Rep[Option[String]] = column[Option[String]]("screenshot_file_name", O.Length(255,varying=true), O.Default(None))
    /** Database column screenshot_content_type SqlType(VARCHAR), Length(255,true), Default(None) */
    val screenshotContentType: Rep[Option[String]] = column[Option[String]]("screenshot_content_type", O.Length(255,varying=true), O.Default(None))
    /** Database column screenshot_file_size SqlType(INT), Default(None) */
    val screenshotFileSize: Rep[Option[Int]] = column[Option[Int]]("screenshot_file_size", O.Default(None))
    /** Database column screenshot_updated_at SqlType(DATETIME), Default(None) */
    val screenshotUpdatedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("screenshot_updated_at", O.Default(None))
    /** Database column object_list SqlType(TEXT), Default(None) */
    val objectList: Rep[Option[String]] = column[Option[String]]("object_list", O.Default(None))
    /** Database column visit_count SqlType(INT), Default(None) */
    val visitCount: Rep[Option[Int]] = column[Option[Int]]("visit_count", O.Default(None))
    /** Database column head_app_view_id SqlType(INT), Default(None) */
    val headAppViewId: Rep[Option[Int]] = column[Option[Int]]("head_app_view_id", O.Default(None))

    /** Index over (headAppViewId,screenshotFileSize,deviceModelId) (database name app_view_id_screenshot_device_model_index) */
    val index1 = index("app_view_id_screenshot_device_model_index", (headAppViewId, screenshotFileSize, deviceModelId))
    /** Index over (deviceModelId) (database name index_app_views_on_device_model_id) */
    val index2 = index("index_app_views_on_device_model_id", deviceModelId)
    /** Index over (headAppViewId) (database name index_app_views_on_head_app_view_id) */
    val index3 = index("index_app_views_on_head_app_view_id", headAppViewId)
    /** Index over (headAppViewId,deviceModelId) (database name index_app_views_on_head_app_view_id_and_device_model_id) */
    val index4 = index("index_app_views_on_head_app_view_id_and_device_model_id", (headAppViewId, deviceModelId))
    /** Index over (headAppViewId,screenshotFileSize) (database name index_app_views_on_head_app_view_id_and_screenshot_file_size) */
    val index5 = index("index_app_views_on_head_app_view_id_and_screenshot_file_size", (headAppViewId, screenshotFileSize))
  }
  /** Collection-like TableQuery object for table AppViews */
  lazy val AppViews = new TableQuery(tag => new AppViews(tag))

  /** Entity class storing rows of table BaseApps
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param osPlatform Database column os_platform SqlType(INT), Default(None)
   *  @param isVisible Database column is_visible SqlType(BIT), Default(None)
   *  @param appType Database column app_type SqlType(INT), Default(Some(1))
   *  @param publishedAppId Database column published_app_id SqlType(INT), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME) */
  case class BaseAppsRow(id: Int, name: Option[String] = None, osPlatform: Option[Int] = None, isVisible: Option[Boolean] = None, appType: Option[Int] = Some(1), publishedAppId: Option[Int] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching BaseAppsRow objects using plain SQL queries */
  implicit def GetResultBaseAppsRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Int]], e3: GR[Option[Boolean]], e4: GR[java.sql.Timestamp]): GR[BaseAppsRow] = GR{
    prs => import prs._
    BaseAppsRow.tupled((<<[Int], <<?[String], <<?[Int], <<?[Boolean], <<?[Int], <<?[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table base_apps. Objects of this class serve as prototypes for rows in queries. */
  class BaseApps(_tableTag: Tag) extends Table[BaseAppsRow](_tableTag, "base_apps") {
    def * = (id, name, osPlatform, isVisible, appType, publishedAppId, createdAt, updatedAt) <> (BaseAppsRow.tupled, BaseAppsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), name, osPlatform, isVisible, appType, publishedAppId, Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> BaseAppsRow.tupled((_1.get, _2, _3, _4, _5, _6, _7.get, _8.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(255,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(255,varying=true), O.Default(None))
    /** Database column os_platform SqlType(INT), Default(None) */
    val osPlatform: Rep[Option[Int]] = column[Option[Int]]("os_platform", O.Default(None))
    /** Database column is_visible SqlType(BIT), Default(None) */
    val isVisible: Rep[Option[Boolean]] = column[Option[Boolean]]("is_visible", O.Default(None))
    /** Database column app_type SqlType(INT), Default(Some(1)) */
    val appType: Rep[Option[Int]] = column[Option[Int]]("app_type", O.Default(Some(1)))
    /** Database column published_app_id SqlType(INT), Default(None) */
    val publishedAppId: Rep[Option[Int]] = column[Option[Int]]("published_app_id", O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (publishedAppId) (database name index_base_apps_on_published_app_id) */
    val index1 = index("index_base_apps_on_published_app_id", publishedAppId)
  }
  /** Collection-like TableQuery object for table BaseApps */
  lazy val BaseApps = new TableQuery(tag => new BaseApps(tag))

  /** Entity class storing rows of table BootsyImageGalleries
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param bootsyResourceId Database column bootsy_resource_id SqlType(INT), Default(None)
   *  @param bootsyResourceType Database column bootsy_resource_type SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME), Default(None)
   *  @param updatedAt Database column updated_at SqlType(DATETIME), Default(None) */
  case class BootsyImageGalleriesRow(id: Int, bootsyResourceId: Option[Int] = None, bootsyResourceType: Option[String] = None, createdAt: Option[java.sql.Timestamp] = None, updatedAt: Option[java.sql.Timestamp] = None)
  /** GetResult implicit for fetching BootsyImageGalleriesRow objects using plain SQL queries */
  implicit def GetResultBootsyImageGalleriesRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[String]], e3: GR[Option[java.sql.Timestamp]]): GR[BootsyImageGalleriesRow] = GR{
    prs => import prs._
    BootsyImageGalleriesRow.tupled((<<[Int], <<?[Int], <<?[String], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp]))
  }
  /** Table description of table bootsy_image_galleries. Objects of this class serve as prototypes for rows in queries. */
  class BootsyImageGalleries(_tableTag: Tag) extends Table[BootsyImageGalleriesRow](_tableTag, "bootsy_image_galleries") {
    def * = (id, bootsyResourceId, bootsyResourceType, createdAt, updatedAt) <> (BootsyImageGalleriesRow.tupled, BootsyImageGalleriesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), bootsyResourceId, bootsyResourceType, createdAt, updatedAt).shaped.<>({r=>import r._; _1.map(_=> BootsyImageGalleriesRow.tupled((_1.get, _2, _3, _4, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column bootsy_resource_id SqlType(INT), Default(None) */
    val bootsyResourceId: Rep[Option[Int]] = column[Option[Int]]("bootsy_resource_id", O.Default(None))
    /** Database column bootsy_resource_type SqlType(VARCHAR), Length(255,true), Default(None) */
    val bootsyResourceType: Rep[Option[String]] = column[Option[String]]("bootsy_resource_type", O.Length(255,varying=true), O.Default(None))
    /** Database column created_at SqlType(DATETIME), Default(None) */
    val createdAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("created_at", O.Default(None))
    /** Database column updated_at SqlType(DATETIME), Default(None) */
    val updatedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("updated_at", O.Default(None))
  }
  /** Collection-like TableQuery object for table BootsyImageGalleries */
  lazy val BootsyImageGalleries = new TableQuery(tag => new BootsyImageGalleries(tag))

  /** Entity class storing rows of table BootsyImages
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param imageFile Database column image_file SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param imageGalleryId Database column image_gallery_id SqlType(INT), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME), Default(None)
   *  @param updatedAt Database column updated_at SqlType(DATETIME), Default(None) */
  case class BootsyImagesRow(id: Int, imageFile: Option[String] = None, imageGalleryId: Option[Int] = None, createdAt: Option[java.sql.Timestamp] = None, updatedAt: Option[java.sql.Timestamp] = None)
  /** GetResult implicit for fetching BootsyImagesRow objects using plain SQL queries */
  implicit def GetResultBootsyImagesRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Int]], e3: GR[Option[java.sql.Timestamp]]): GR[BootsyImagesRow] = GR{
    prs => import prs._
    BootsyImagesRow.tupled((<<[Int], <<?[String], <<?[Int], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp]))
  }
  /** Table description of table bootsy_images. Objects of this class serve as prototypes for rows in queries. */
  class BootsyImages(_tableTag: Tag) extends Table[BootsyImagesRow](_tableTag, "bootsy_images") {
    def * = (id, imageFile, imageGalleryId, createdAt, updatedAt) <> (BootsyImagesRow.tupled, BootsyImagesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), imageFile, imageGalleryId, createdAt, updatedAt).shaped.<>({r=>import r._; _1.map(_=> BootsyImagesRow.tupled((_1.get, _2, _3, _4, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column image_file SqlType(VARCHAR), Length(255,true), Default(None) */
    val imageFile: Rep[Option[String]] = column[Option[String]]("image_file", O.Length(255,varying=true), O.Default(None))
    /** Database column image_gallery_id SqlType(INT), Default(None) */
    val imageGalleryId: Rep[Option[Int]] = column[Option[Int]]("image_gallery_id", O.Default(None))
    /** Database column created_at SqlType(DATETIME), Default(None) */
    val createdAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("created_at", O.Default(None))
    /** Database column updated_at SqlType(DATETIME), Default(None) */
    val updatedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("updated_at", O.Default(None))
  }
  /** Collection-like TableQuery object for table BootsyImages */
  lazy val BootsyImages = new TableQuery(tag => new BootsyImages(tag))

  /** Entity class storing rows of table Campaigns
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param appId Database column app_id SqlType(INT)
   *  @param version Database column version SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME)
   *  @param apiKey Database column api_key SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param status Database column status SqlType(INT), Default(1)
   *  @param revision Database column revision SqlType(INT), Default(None)
   *  @param permissions Database column permissions SqlType(TEXT), Default(None)
   *  @param description Database column description SqlType(TEXT), Default(None) */
  case class CampaignsRow(id: Int, appId: Int, version: Option[String] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp, apiKey: Option[String] = None, status: Int = 1, revision: Option[Int] = None, permissions: Option[String] = None, description: Option[String] = None)
  /** GetResult implicit for fetching CampaignsRow objects using plain SQL queries */
  implicit def GetResultCampaignsRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[java.sql.Timestamp], e3: GR[Option[Int]]): GR[CampaignsRow] = GR{
    prs => import prs._
    CampaignsRow.tupled((<<[Int], <<[Int], <<?[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<?[String], <<[Int], <<?[Int], <<?[String], <<?[String]))
  }
  /** Table description of table campaigns. Objects of this class serve as prototypes for rows in queries. */
  class Campaigns(_tableTag: Tag) extends Table[CampaignsRow](_tableTag, "campaigns") {
    def * = (id, appId, version, createdAt, updatedAt, apiKey, status, revision, permissions, description) <> (CampaignsRow.tupled, CampaignsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(appId), version, Rep.Some(createdAt), Rep.Some(updatedAt), apiKey, Rep.Some(status), revision, permissions, description).shaped.<>({r=>import r._; _1.map(_=> CampaignsRow.tupled((_1.get, _2.get, _3, _4.get, _5.get, _6, _7.get, _8, _9, _10)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column app_id SqlType(INT) */
    val appId: Rep[Int] = column[Int]("app_id")
    /** Database column version SqlType(VARCHAR), Length(255,true), Default(None) */
    val version: Rep[Option[String]] = column[Option[String]]("version", O.Length(255,varying=true), O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
    /** Database column api_key SqlType(VARCHAR), Length(255,true), Default(None) */
    val apiKey: Rep[Option[String]] = column[Option[String]]("api_key", O.Length(255,varying=true), O.Default(None))
    /** Database column status SqlType(INT), Default(1) */
    val status: Rep[Int] = column[Int]("status", O.Default(1))
    /** Database column revision SqlType(INT), Default(None) */
    val revision: Rep[Option[Int]] = column[Option[Int]]("revision", O.Default(None))
    /** Database column permissions SqlType(TEXT), Default(None) */
    val permissions: Rep[Option[String]] = column[Option[String]]("permissions", O.Default(None))
    /** Database column description SqlType(TEXT), Default(None) */
    val description: Rep[Option[String]] = column[Option[String]]("description", O.Default(None))

    /** Index over (apiKey) (database name index_campaigns_on_api_key) */
    val index1 = index("index_campaigns_on_api_key", apiKey)
    /** Index over (appId) (database name index_campaigns_on_app_id) */
    val index2 = index("index_campaigns_on_app_id", appId)
    /** Uniqueness Index over (appId,revision) (database name index_campaigns_on_app_id_and_revision) */
    val index3 = index("index_campaigns_on_app_id_and_revision", (appId, revision), unique=true)
  }
  /** Collection-like TableQuery object for table Campaigns */
  lazy val Campaigns = new TableQuery(tag => new Campaigns(tag))

  /** Entity class storing rows of table ChangeLogs
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param noticeId Database column notice_id SqlType(INT), Default(None)
   *  @param locale Database column locale SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param log Database column log SqlType(TEXT), Default(None) */
  case class ChangeLogsRow(id: Int, noticeId: Option[Int] = None, locale: Option[String] = None, log: Option[String] = None)
  /** GetResult implicit for fetching ChangeLogsRow objects using plain SQL queries */
  implicit def GetResultChangeLogsRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[String]]): GR[ChangeLogsRow] = GR{
    prs => import prs._
    ChangeLogsRow.tupled((<<[Int], <<?[Int], <<?[String], <<?[String]))
  }
  /** Table description of table change_logs. Objects of this class serve as prototypes for rows in queries. */
  class ChangeLogs(_tableTag: Tag) extends Table[ChangeLogsRow](_tableTag, "change_logs") {
    def * = (id, noticeId, locale, log) <> (ChangeLogsRow.tupled, ChangeLogsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), noticeId, locale, log).shaped.<>({r=>import r._; _1.map(_=> ChangeLogsRow.tupled((_1.get, _2, _3, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column notice_id SqlType(INT), Default(None) */
    val noticeId: Rep[Option[Int]] = column[Option[Int]]("notice_id", O.Default(None))
    /** Database column locale SqlType(VARCHAR), Length(255,true), Default(None) */
    val locale: Rep[Option[String]] = column[Option[String]]("locale", O.Length(255,varying=true), O.Default(None))
    /** Database column log SqlType(TEXT), Default(None) */
    val log: Rep[Option[String]] = column[Option[String]]("log", O.Default(None))

    /** Index over (noticeId) (database name index_change_logs_on_notice_id) */
    val index1 = index("index_change_logs_on_notice_id", noticeId)
  }
  /** Collection-like TableQuery object for table ChangeLogs */
  lazy val ChangeLogs = new TableQuery(tag => new ChangeLogs(tag))

  /** Entity class storing rows of table CheckpointPassCountReferences
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param checkpointId Database column checkpoint_id SqlType(INT), Default(None)
   *  @param parentId Database column parent_id SqlType(INT), Default(None)
   *  @param attr Database column attr SqlType(INT), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME) */
  case class CheckpointPassCountReferencesRow(id: Int, checkpointId: Option[Int] = None, parentId: Option[Int] = None, attr: Option[Int] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching CheckpointPassCountReferencesRow objects using plain SQL queries */
  implicit def GetResultCheckpointPassCountReferencesRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[java.sql.Timestamp]): GR[CheckpointPassCountReferencesRow] = GR{
    prs => import prs._
    CheckpointPassCountReferencesRow.tupled((<<[Int], <<?[Int], <<?[Int], <<?[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table checkpoint_pass_count_references. Objects of this class serve as prototypes for rows in queries. */
  class CheckpointPassCountReferences(_tableTag: Tag) extends Table[CheckpointPassCountReferencesRow](_tableTag, "checkpoint_pass_count_references") {
    def * = (id, checkpointId, parentId, attr, createdAt, updatedAt) <> (CheckpointPassCountReferencesRow.tupled, CheckpointPassCountReferencesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), checkpointId, parentId, attr, Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> CheckpointPassCountReferencesRow.tupled((_1.get, _2, _3, _4, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column checkpoint_id SqlType(INT), Default(None) */
    val checkpointId: Rep[Option[Int]] = column[Option[Int]]("checkpoint_id", O.Default(None))
    /** Database column parent_id SqlType(INT), Default(None) */
    val parentId: Rep[Option[Int]] = column[Option[Int]]("parent_id", O.Default(None))
    /** Database column attr SqlType(INT), Default(None) */
    val attr: Rep[Option[Int]] = column[Option[Int]]("attr", O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (checkpointId) (database name index_checkpoint_pass_count_references_on_checkpoint_id) */
    val index1 = index("index_checkpoint_pass_count_references_on_checkpoint_id", checkpointId)
    /** Index over (parentId) (database name index_checkpoint_pass_count_references_on_parent_id) */
    val index2 = index("index_checkpoint_pass_count_references_on_parent_id", parentId)
  }
  /** Collection-like TableQuery object for table CheckpointPassCountReferences */
  lazy val CheckpointPassCountReferences = new TableQuery(tag => new CheckpointPassCountReferences(tag))

  /** Entity class storing rows of table CheckpointPasses
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param checkpointId Database column checkpoint_id SqlType(INT)
   *  @param testerActivityId Database column tester_activity_id SqlType(INT)
   *  @param count Database column count SqlType(INT), Default(1)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME) */
  case class CheckpointPassesRow(id: Int, checkpointId: Int, testerActivityId: Int, count: Int = 1, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching CheckpointPassesRow objects using plain SQL queries */
  implicit def GetResultCheckpointPassesRow(implicit e0: GR[Int], e1: GR[java.sql.Timestamp]): GR[CheckpointPassesRow] = GR{
    prs => import prs._
    CheckpointPassesRow.tupled((<<[Int], <<[Int], <<[Int], <<[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table checkpoint_passes. Objects of this class serve as prototypes for rows in queries. */
  class CheckpointPasses(_tableTag: Tag) extends Table[CheckpointPassesRow](_tableTag, "checkpoint_passes") {
    def * = (id, checkpointId, testerActivityId, count, createdAt, updatedAt) <> (CheckpointPassesRow.tupled, CheckpointPassesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(checkpointId), Rep.Some(testerActivityId), Rep.Some(count), Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> CheckpointPassesRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column checkpoint_id SqlType(INT) */
    val checkpointId: Rep[Int] = column[Int]("checkpoint_id")
    /** Database column tester_activity_id SqlType(INT) */
    val testerActivityId: Rep[Int] = column[Int]("tester_activity_id")
    /** Database column count SqlType(INT), Default(1) */
    val count: Rep[Int] = column[Int]("count", O.Default(1))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (checkpointId) (database name index_checkpoint_passes_on_checkpoint_id) */
    val index1 = index("index_checkpoint_passes_on_checkpoint_id", checkpointId)
    /** Index over (testerActivityId) (database name index_checkpoint_passes_on_tester_activity_id) */
    val index2 = index("index_checkpoint_passes_on_tester_activity_id", testerActivityId)
  }
  /** Collection-like TableQuery object for table CheckpointPasses */
  lazy val CheckpointPasses = new TableQuery(tag => new CheckpointPasses(tag))

  /** Entity class storing rows of table CheckpointPassLogs
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param testerActivityId Database column tester_activity_id SqlType(INT), Default(None)
   *  @param testerSessionId Database column tester_session_id SqlType(INT), Default(None)
   *  @param checkpointId Database column checkpoint_id SqlType(INT), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME)
   *  @param deviceTime Database column device_time SqlType(DATETIME), Default(None)
   *  @param deviceTimezone Database column device_timezone SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param seqNum Database column seq_num SqlType(INT), Default(None) */
  case class CheckpointPassLogsRow(id: Int, testerActivityId: Option[Int] = None, testerSessionId: Option[Int] = None, checkpointId: Option[Int] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp, deviceTime: Option[java.sql.Timestamp] = None, deviceTimezone: Option[String] = None, seqNum: Option[Int] = None)
  /** GetResult implicit for fetching CheckpointPassLogsRow objects using plain SQL queries */
  implicit def GetResultCheckpointPassLogsRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[java.sql.Timestamp], e3: GR[Option[java.sql.Timestamp]], e4: GR[Option[String]]): GR[CheckpointPassLogsRow] = GR{
    prs => import prs._
    CheckpointPassLogsRow.tupled((<<[Int], <<?[Int], <<?[Int], <<?[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<?[java.sql.Timestamp], <<?[String], <<?[Int]))
  }
  /** Table description of table checkpoint_pass_logs. Objects of this class serve as prototypes for rows in queries. */
  class CheckpointPassLogs(_tableTag: Tag) extends Table[CheckpointPassLogsRow](_tableTag, "checkpoint_pass_logs") {
    def * = (id, testerActivityId, testerSessionId, checkpointId, createdAt, updatedAt, deviceTime, deviceTimezone, seqNum) <> (CheckpointPassLogsRow.tupled, CheckpointPassLogsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), testerActivityId, testerSessionId, checkpointId, Rep.Some(createdAt), Rep.Some(updatedAt), deviceTime, deviceTimezone, seqNum).shaped.<>({r=>import r._; _1.map(_=> CheckpointPassLogsRow.tupled((_1.get, _2, _3, _4, _5.get, _6.get, _7, _8, _9)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column tester_activity_id SqlType(INT), Default(None) */
    val testerActivityId: Rep[Option[Int]] = column[Option[Int]]("tester_activity_id", O.Default(None))
    /** Database column tester_session_id SqlType(INT), Default(None) */
    val testerSessionId: Rep[Option[Int]] = column[Option[Int]]("tester_session_id", O.Default(None))
    /** Database column checkpoint_id SqlType(INT), Default(None) */
    val checkpointId: Rep[Option[Int]] = column[Option[Int]]("checkpoint_id", O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
    /** Database column device_time SqlType(DATETIME), Default(None) */
    val deviceTime: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("device_time", O.Default(None))
    /** Database column device_timezone SqlType(VARCHAR), Length(255,true), Default(None) */
    val deviceTimezone: Rep[Option[String]] = column[Option[String]]("device_timezone", O.Length(255,varying=true), O.Default(None))
    /** Database column seq_num SqlType(INT), Default(None) */
    val seqNum: Rep[Option[Int]] = column[Option[Int]]("seq_num", O.Default(None))

    /** Index over (checkpointId) (database name index_checkpoint_pass_logs_on_checkpoint_id) */
    val index1 = index("index_checkpoint_pass_logs_on_checkpoint_id", checkpointId)
    /** Index over (testerActivityId) (database name index_checkpoint_pass_logs_on_tester_activity_id) */
    val index2 = index("index_checkpoint_pass_logs_on_tester_activity_id", testerActivityId)
    /** Index over (testerSessionId) (database name index_checkpoint_pass_logs_on_tester_session_id) */
    val index3 = index("index_checkpoint_pass_logs_on_tester_session_id", testerSessionId)
  }
  /** Collection-like TableQuery object for table CheckpointPassLogs */
  lazy val CheckpointPassLogs = new TableQuery(tag => new CheckpointPassLogs(tag))

  /** Entity class storing rows of table CheckpointRelations
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param fromId Database column from_id SqlType(INT), Default(None)
   *  @param toId Database column to_id SqlType(INT), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME) */
  case class CheckpointRelationsRow(id: Int, fromId: Option[Int] = None, toId: Option[Int] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching CheckpointRelationsRow objects using plain SQL queries */
  implicit def GetResultCheckpointRelationsRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[java.sql.Timestamp]): GR[CheckpointRelationsRow] = GR{
    prs => import prs._
    CheckpointRelationsRow.tupled((<<[Int], <<?[Int], <<?[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table checkpoint_relations. Objects of this class serve as prototypes for rows in queries. */
  class CheckpointRelations(_tableTag: Tag) extends Table[CheckpointRelationsRow](_tableTag, "checkpoint_relations") {
    def * = (id, fromId, toId, createdAt, updatedAt) <> (CheckpointRelationsRow.tupled, CheckpointRelationsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), fromId, toId, Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> CheckpointRelationsRow.tupled((_1.get, _2, _3, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column from_id SqlType(INT), Default(None) */
    val fromId: Rep[Option[Int]] = column[Option[Int]]("from_id", O.Default(None))
    /** Database column to_id SqlType(INT), Default(None) */
    val toId: Rep[Option[Int]] = column[Option[Int]]("to_id", O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
  }
  /** Collection-like TableQuery object for table CheckpointRelations */
  lazy val CheckpointRelations = new TableQuery(tag => new CheckpointRelations(tag))

  /** Entity class storing rows of table Checkpoints
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param campaignId Database column campaign_id SqlType(INT)
   *  @param name Database column name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME)
   *  @param checkpointTypeId Database column checkpoint_type_id SqlType(INT), Default(1)
   *  @param attr Database column attr SqlType(INT), Default(None)
   *  @param parentId Database column parent_id SqlType(INT), Default(None)
   *  @param title Database column title SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param description Database column description SqlType(TEXT), Default(None) */
  case class CheckpointsRow(id: Int, campaignId: Int, name: Option[String] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp, checkpointTypeId: Int = 1, attr: Option[Int] = None, parentId: Option[Int] = None, title: Option[String] = None, description: Option[String] = None)
  /** GetResult implicit for fetching CheckpointsRow objects using plain SQL queries */
  implicit def GetResultCheckpointsRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[java.sql.Timestamp], e3: GR[Option[Int]]): GR[CheckpointsRow] = GR{
    prs => import prs._
    CheckpointsRow.tupled((<<[Int], <<[Int], <<?[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<[Int], <<?[Int], <<?[Int], <<?[String], <<?[String]))
  }
  /** Table description of table checkpoints. Objects of this class serve as prototypes for rows in queries. */
  class Checkpoints(_tableTag: Tag) extends Table[CheckpointsRow](_tableTag, "checkpoints") {
    def * = (id, campaignId, name, createdAt, updatedAt, checkpointTypeId, attr, parentId, title, description) <> (CheckpointsRow.tupled, CheckpointsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(campaignId), name, Rep.Some(createdAt), Rep.Some(updatedAt), Rep.Some(checkpointTypeId), attr, parentId, title, description).shaped.<>({r=>import r._; _1.map(_=> CheckpointsRow.tupled((_1.get, _2.get, _3, _4.get, _5.get, _6.get, _7, _8, _9, _10)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column campaign_id SqlType(INT) */
    val campaignId: Rep[Int] = column[Int]("campaign_id")
    /** Database column name SqlType(VARCHAR), Length(255,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(255,varying=true), O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
    /** Database column checkpoint_type_id SqlType(INT), Default(1) */
    val checkpointTypeId: Rep[Int] = column[Int]("checkpoint_type_id", O.Default(1))
    /** Database column attr SqlType(INT), Default(None) */
    val attr: Rep[Option[Int]] = column[Option[Int]]("attr", O.Default(None))
    /** Database column parent_id SqlType(INT), Default(None) */
    val parentId: Rep[Option[Int]] = column[Option[Int]]("parent_id", O.Default(None))
    /** Database column title SqlType(VARCHAR), Length(255,true), Default(None) */
    val title: Rep[Option[String]] = column[Option[String]]("title", O.Length(255,varying=true), O.Default(None))
    /** Database column description SqlType(TEXT), Default(None) */
    val description: Rep[Option[String]] = column[Option[String]]("description", O.Default(None))

    /** Index over (campaignId) (database name index_checkpoints_on_campaign_id) */
    val index1 = index("index_checkpoints_on_campaign_id", campaignId)
    /** Index over (campaignId,name) (database name index_checkpoints_on_campaign_id_and_name) */
    val index2 = index("index_checkpoints_on_campaign_id_and_name", (campaignId, name))
  }
  /** Collection-like TableQuery object for table Checkpoints */
  lazy val Checkpoints = new TableQuery(tag => new Checkpoints(tag))

  /** Entity class storing rows of table CheckpointTypes
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param description Database column description SqlType(TEXT), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME)
   *  @param humanizedName Database column humanized_name SqlType(VARCHAR), Length(255,true), Default() */
  case class CheckpointTypesRow(id: Int, name: Option[String] = None, description: Option[String] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp, humanizedName: String = "")
  /** GetResult implicit for fetching CheckpointTypesRow objects using plain SQL queries */
  implicit def GetResultCheckpointTypesRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[java.sql.Timestamp], e3: GR[String]): GR[CheckpointTypesRow] = GR{
    prs => import prs._
    CheckpointTypesRow.tupled((<<[Int], <<?[String], <<?[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<[String]))
  }
  /** Table description of table checkpoint_types. Objects of this class serve as prototypes for rows in queries. */
  class CheckpointTypes(_tableTag: Tag) extends Table[CheckpointTypesRow](_tableTag, "checkpoint_types") {
    def * = (id, name, description, createdAt, updatedAt, humanizedName) <> (CheckpointTypesRow.tupled, CheckpointTypesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), name, description, Rep.Some(createdAt), Rep.Some(updatedAt), Rep.Some(humanizedName)).shaped.<>({r=>import r._; _1.map(_=> CheckpointTypesRow.tupled((_1.get, _2, _3, _4.get, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(255,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(255,varying=true), O.Default(None))
    /** Database column description SqlType(TEXT), Default(None) */
    val description: Rep[Option[String]] = column[Option[String]]("description", O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
    /** Database column humanized_name SqlType(VARCHAR), Length(255,true), Default() */
    val humanizedName: Rep[String] = column[String]("humanized_name", O.Length(255,varying=true), O.Default(""))
  }
  /** Collection-like TableQuery object for table CheckpointTypes */
  lazy val CheckpointTypes = new TableQuery(tag => new CheckpointTypes(tag))

  /** Entity class storing rows of table CkeditorAssets
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param dataFileName Database column data_file_name SqlType(VARCHAR), Length(255,true)
   *  @param dataContentType Database column data_content_type SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param dataFileSize Database column data_file_size SqlType(INT), Default(None)
   *  @param assetableId Database column assetable_id SqlType(INT), Default(None)
   *  @param assetableType Database column assetable_type SqlType(VARCHAR), Length(30,true), Default(None)
   *  @param `type` Database column type SqlType(VARCHAR), Length(30,true), Default(None)
   *  @param width Database column width SqlType(INT), Default(None)
   *  @param height Database column height SqlType(INT), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME) */
  case class CkeditorAssetsRow(id: Int, dataFileName: String, dataContentType: Option[String] = None, dataFileSize: Option[Int] = None, assetableId: Option[Int] = None, assetableType: Option[String] = None, `type`: Option[String] = None, width: Option[Int] = None, height: Option[Int] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching CkeditorAssetsRow objects using plain SQL queries */
  implicit def GetResultCkeditorAssetsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]], e3: GR[Option[Int]], e4: GR[java.sql.Timestamp]): GR[CkeditorAssetsRow] = GR{
    prs => import prs._
    CkeditorAssetsRow.tupled((<<[Int], <<[String], <<?[String], <<?[Int], <<?[Int], <<?[String], <<?[String], <<?[Int], <<?[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table ckeditor_assets. Objects of this class serve as prototypes for rows in queries.
   *  NOTE: The following names collided with Scala keywords and were escaped: type */
  class CkeditorAssets(_tableTag: Tag) extends Table[CkeditorAssetsRow](_tableTag, "ckeditor_assets") {
    def * = (id, dataFileName, dataContentType, dataFileSize, assetableId, assetableType, `type`, width, height, createdAt, updatedAt) <> (CkeditorAssetsRow.tupled, CkeditorAssetsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(dataFileName), dataContentType, dataFileSize, assetableId, assetableType, `type`, width, height, Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> CkeditorAssetsRow.tupled((_1.get, _2.get, _3, _4, _5, _6, _7, _8, _9, _10.get, _11.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column data_file_name SqlType(VARCHAR), Length(255,true) */
    val dataFileName: Rep[String] = column[String]("data_file_name", O.Length(255,varying=true))
    /** Database column data_content_type SqlType(VARCHAR), Length(255,true), Default(None) */
    val dataContentType: Rep[Option[String]] = column[Option[String]]("data_content_type", O.Length(255,varying=true), O.Default(None))
    /** Database column data_file_size SqlType(INT), Default(None) */
    val dataFileSize: Rep[Option[Int]] = column[Option[Int]]("data_file_size", O.Default(None))
    /** Database column assetable_id SqlType(INT), Default(None) */
    val assetableId: Rep[Option[Int]] = column[Option[Int]]("assetable_id", O.Default(None))
    /** Database column assetable_type SqlType(VARCHAR), Length(30,true), Default(None) */
    val assetableType: Rep[Option[String]] = column[Option[String]]("assetable_type", O.Length(30,varying=true), O.Default(None))
    /** Database column type SqlType(VARCHAR), Length(30,true), Default(None)
     *  NOTE: The name was escaped because it collided with a Scala keyword. */
    val `type`: Rep[Option[String]] = column[Option[String]]("type", O.Length(30,varying=true), O.Default(None))
    /** Database column width SqlType(INT), Default(None) */
    val width: Rep[Option[Int]] = column[Option[Int]]("width", O.Default(None))
    /** Database column height SqlType(INT), Default(None) */
    val height: Rep[Option[Int]] = column[Option[Int]]("height", O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (assetableType,assetableId) (database name idx_ckeditor_assetable) */
    val index1 = index("idx_ckeditor_assetable", (assetableType, assetableId))
    /** Index over (assetableType,`type`,assetableId) (database name idx_ckeditor_assetable_type) */
    val index2 = index("idx_ckeditor_assetable_type", (assetableType, `type`, assetableId))
  }
  /** Collection-like TableQuery object for table CkeditorAssets */
  lazy val CkeditorAssets = new TableQuery(tag => new CkeditorAssets(tag))

  /** Entity class storing rows of table Crashes
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param stacktrace Database column stacktrace SqlType(TEXT), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME)
   *  @param packageName Database column package_name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param versionId Database column version_id SqlType(INT), Default(None)
   *  @param testerDeviceId Database column tester_device_id SqlType(INT), Default(None)
   *  @param testerSessionId Database column tester_session_id SqlType(INT), Default(None)
   *  @param uniqueId Database column unique_id SqlType(VARBINARY), Default(None) */
  case class CrashesRow(id: Int, stacktrace: Option[String] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp, packageName: Option[String] = None, versionId: Option[Int] = None, testerDeviceId: Option[Int] = None, testerSessionId: Option[Int] = None, uniqueId: Option[java.sql.Blob] = None)
  /** GetResult implicit for fetching CrashesRow objects using plain SQL queries */
  implicit def GetResultCrashesRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[java.sql.Timestamp], e3: GR[Option[Int]], e4: GR[Option[java.sql.Blob]]): GR[CrashesRow] = GR{
    prs => import prs._
    CrashesRow.tupled((<<[Int], <<?[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<?[String], <<?[Int], <<?[Int], <<?[Int], <<?[java.sql.Blob]))
  }
  /** Table description of table crashes. Objects of this class serve as prototypes for rows in queries. */
  class Crashes(_tableTag: Tag) extends Table[CrashesRow](_tableTag, "crashes") {
    def * = (id, stacktrace, createdAt, updatedAt, packageName, versionId, testerDeviceId, testerSessionId, uniqueId) <> (CrashesRow.tupled, CrashesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), stacktrace, Rep.Some(createdAt), Rep.Some(updatedAt), packageName, versionId, testerDeviceId, testerSessionId, uniqueId).shaped.<>({r=>import r._; _1.map(_=> CrashesRow.tupled((_1.get, _2, _3.get, _4.get, _5, _6, _7, _8, _9)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column stacktrace SqlType(TEXT), Default(None) */
    val stacktrace: Rep[Option[String]] = column[Option[String]]("stacktrace", O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
    /** Database column package_name SqlType(VARCHAR), Length(255,true), Default(None) */
    val packageName: Rep[Option[String]] = column[Option[String]]("package_name", O.Length(255,varying=true), O.Default(None))
    /** Database column version_id SqlType(INT), Default(None) */
    val versionId: Rep[Option[Int]] = column[Option[Int]]("version_id", O.Default(None))
    /** Database column tester_device_id SqlType(INT), Default(None) */
    val testerDeviceId: Rep[Option[Int]] = column[Option[Int]]("tester_device_id", O.Default(None))
    /** Database column tester_session_id SqlType(INT), Default(None) */
    val testerSessionId: Rep[Option[Int]] = column[Option[Int]]("tester_session_id", O.Default(None))
    /** Database column unique_id SqlType(VARBINARY), Default(None) */
    val uniqueId: Rep[Option[java.sql.Blob]] = column[Option[java.sql.Blob]]("unique_id", O.Default(None))

    /** Index over (uniqueId) (database name index_crashes_on_unique_id) */
    val index1 = index("index_crashes_on_unique_id", uniqueId)
    /** Index over (versionId,createdAt) (database name version_id_created_at_index) */
    val index2 = index("version_id_created_at_index", (versionId, createdAt))
  }
  /** Collection-like TableQuery object for table Crashes */
  lazy val Crashes = new TableQuery(tag => new Crashes(tag))

  /** Entity class storing rows of table DailySessionStats
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param appId Database column app_id SqlType(INT)
   *  @param date Database column date SqlType(DATETIME)
   *  @param uniqueUserCount Database column unique_user_count SqlType(INT)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME)
   *  @param avgPageView Database column avg_page_view SqlType(INT), Default(0)
   *  @param avgScreenCount Database column avg_screen_count SqlType(INT), Default(0)
   *  @param avgDuration Database column avg_duration SqlType(INT), Default(0) */
  case class DailySessionStatsRow(id: Int, appId: Int, date: java.sql.Timestamp, uniqueUserCount: Int, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp, avgPageView: Int = 0, avgScreenCount: Int = 0, avgDuration: Int = 0)
  /** GetResult implicit for fetching DailySessionStatsRow objects using plain SQL queries */
  implicit def GetResultDailySessionStatsRow(implicit e0: GR[Int], e1: GR[java.sql.Timestamp]): GR[DailySessionStatsRow] = GR{
    prs => import prs._
    DailySessionStatsRow.tupled((<<[Int], <<[Int], <<[java.sql.Timestamp], <<[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<[Int], <<[Int], <<[Int]))
  }
  /** Table description of table daily_session_stats. Objects of this class serve as prototypes for rows in queries. */
  class DailySessionStats(_tableTag: Tag) extends Table[DailySessionStatsRow](_tableTag, "daily_session_stats") {
    def * = (id, appId, date, uniqueUserCount, createdAt, updatedAt, avgPageView, avgScreenCount, avgDuration) <> (DailySessionStatsRow.tupled, DailySessionStatsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(appId), Rep.Some(date), Rep.Some(uniqueUserCount), Rep.Some(createdAt), Rep.Some(updatedAt), Rep.Some(avgPageView), Rep.Some(avgScreenCount), Rep.Some(avgDuration)).shaped.<>({r=>import r._; _1.map(_=> DailySessionStatsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column app_id SqlType(INT) */
    val appId: Rep[Int] = column[Int]("app_id")
    /** Database column date SqlType(DATETIME) */
    val date: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("date")
    /** Database column unique_user_count SqlType(INT) */
    val uniqueUserCount: Rep[Int] = column[Int]("unique_user_count")
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
    /** Database column avg_page_view SqlType(INT), Default(0) */
    val avgPageView: Rep[Int] = column[Int]("avg_page_view", O.Default(0))
    /** Database column avg_screen_count SqlType(INT), Default(0) */
    val avgScreenCount: Rep[Int] = column[Int]("avg_screen_count", O.Default(0))
    /** Database column avg_duration SqlType(INT), Default(0) */
    val avgDuration: Rep[Int] = column[Int]("avg_duration", O.Default(0))

    /** Uniqueness Index over (appId,date) (database name index_daily_session_stats_on_app_id_and_date) */
    val index1 = index("index_daily_session_stats_on_app_id_and_date", (appId, date), unique=true)
  }
  /** Collection-like TableQuery object for table DailySessionStats */
  lazy val DailySessionStats = new TableQuery(tag => new DailySessionStats(tag))

  /** Entity class storing rows of table DelayedJobs
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param priority Database column priority SqlType(INT), Default(0)
   *  @param attempts Database column attempts SqlType(INT), Default(0)
   *  @param handler Database column handler SqlType(TEXT)
   *  @param lastError Database column last_error SqlType(TEXT), Default(None)
   *  @param runAt Database column run_at SqlType(DATETIME), Default(None)
   *  @param lockedAt Database column locked_at SqlType(DATETIME), Default(None)
   *  @param failedAt Database column failed_at SqlType(DATETIME), Default(None)
   *  @param lockedBy Database column locked_by SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param queue Database column queue SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME) */
  case class DelayedJobsRow(id: Int, priority: Int = 0, attempts: Int = 0, handler: String, lastError: Option[String] = None, runAt: Option[java.sql.Timestamp] = None, lockedAt: Option[java.sql.Timestamp] = None, failedAt: Option[java.sql.Timestamp] = None, lockedBy: Option[String] = None, queue: Option[String] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching DelayedJobsRow objects using plain SQL queries */
  implicit def GetResultDelayedJobsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]], e3: GR[Option[java.sql.Timestamp]], e4: GR[java.sql.Timestamp]): GR[DelayedJobsRow] = GR{
    prs => import prs._
    DelayedJobsRow.tupled((<<[Int], <<[Int], <<[Int], <<[String], <<?[String], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp], <<?[String], <<?[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table delayed_jobs. Objects of this class serve as prototypes for rows in queries. */
  class DelayedJobs(_tableTag: Tag) extends Table[DelayedJobsRow](_tableTag, "delayed_jobs") {
    def * = (id, priority, attempts, handler, lastError, runAt, lockedAt, failedAt, lockedBy, queue, createdAt, updatedAt) <> (DelayedJobsRow.tupled, DelayedJobsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(priority), Rep.Some(attempts), Rep.Some(handler), lastError, runAt, lockedAt, failedAt, lockedBy, queue, Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> DelayedJobsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5, _6, _7, _8, _9, _10, _11.get, _12.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column priority SqlType(INT), Default(0) */
    val priority: Rep[Int] = column[Int]("priority", O.Default(0))
    /** Database column attempts SqlType(INT), Default(0) */
    val attempts: Rep[Int] = column[Int]("attempts", O.Default(0))
    /** Database column handler SqlType(TEXT) */
    val handler: Rep[String] = column[String]("handler")
    /** Database column last_error SqlType(TEXT), Default(None) */
    val lastError: Rep[Option[String]] = column[Option[String]]("last_error", O.Default(None))
    /** Database column run_at SqlType(DATETIME), Default(None) */
    val runAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("run_at", O.Default(None))
    /** Database column locked_at SqlType(DATETIME), Default(None) */
    val lockedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("locked_at", O.Default(None))
    /** Database column failed_at SqlType(DATETIME), Default(None) */
    val failedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("failed_at", O.Default(None))
    /** Database column locked_by SqlType(VARCHAR), Length(255,true), Default(None) */
    val lockedBy: Rep[Option[String]] = column[Option[String]]("locked_by", O.Length(255,varying=true), O.Default(None))
    /** Database column queue SqlType(VARCHAR), Length(255,true), Default(None) */
    val queue: Rep[Option[String]] = column[Option[String]]("queue", O.Length(255,varying=true), O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (priority,runAt) (database name delayed_jobs_priority) */
    val index1 = index("delayed_jobs_priority", (priority, runAt))
  }
  /** Collection-like TableQuery object for table DelayedJobs */
  lazy val DelayedJobs = new TableQuery(tag => new DelayedJobs(tag))

  /** Entity class storing rows of table DeviceModels
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param brand Database column brand SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param name Database column name SqlType(VARCHAR), Length(255,true)
   *  @param width Database column width SqlType(INT), Default(None)
   *  @param height Database column height SqlType(INT), Default(None)
   *  @param alias Database column alias SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME)
   *  @param device Database column device SqlType(VARCHAR), Length(255,true), Default(None) */
  case class DeviceModelsRow(id: Int, brand: Option[String] = None, name: String, width: Option[Int] = None, height: Option[Int] = None, alias: Option[String] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp, device: Option[String] = None)
  /** GetResult implicit for fetching DeviceModelsRow objects using plain SQL queries */
  implicit def GetResultDeviceModelsRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[String], e3: GR[Option[Int]], e4: GR[java.sql.Timestamp]): GR[DeviceModelsRow] = GR{
    prs => import prs._
    DeviceModelsRow.tupled((<<[Int], <<?[String], <<[String], <<?[Int], <<?[Int], <<?[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<?[String]))
  }
  /** Table description of table device_models. Objects of this class serve as prototypes for rows in queries. */
  class DeviceModels(_tableTag: Tag) extends Table[DeviceModelsRow](_tableTag, "device_models") {
    def * = (id, brand, name, width, height, alias, createdAt, updatedAt, device) <> (DeviceModelsRow.tupled, DeviceModelsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), brand, Rep.Some(name), width, height, alias, Rep.Some(createdAt), Rep.Some(updatedAt), device).shaped.<>({r=>import r._; _1.map(_=> DeviceModelsRow.tupled((_1.get, _2, _3.get, _4, _5, _6, _7.get, _8.get, _9)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column brand SqlType(VARCHAR), Length(255,true), Default(None) */
    val brand: Rep[Option[String]] = column[Option[String]]("brand", O.Length(255,varying=true), O.Default(None))
    /** Database column name SqlType(VARCHAR), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column width SqlType(INT), Default(None) */
    val width: Rep[Option[Int]] = column[Option[Int]]("width", O.Default(None))
    /** Database column height SqlType(INT), Default(None) */
    val height: Rep[Option[Int]] = column[Option[Int]]("height", O.Default(None))
    /** Database column alias SqlType(VARCHAR), Length(255,true), Default(None) */
    val alias: Rep[Option[String]] = column[Option[String]]("alias", O.Length(255,varying=true), O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
    /** Database column device SqlType(VARCHAR), Length(255,true), Default(None) */
    val device: Rep[Option[String]] = column[Option[String]]("device", O.Length(255,varying=true), O.Default(None))

    /** Uniqueness Index over (name) (database name index_device_models_on_name) */
    val index1 = index("index_device_models_on_name", name, unique=true)
    /** Index over (width,height) (database name index_device_models_on_width_and_height) */
    val index2 = index("index_device_models_on_width_and_height", (width, height))
  }
  /** Collection-like TableQuery object for table DeviceModels */
  lazy val DeviceModels = new TableQuery(tag => new DeviceModels(tag))

  /** Entity class storing rows of table DeviceUsageAggregations
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param monthlyAggregationId Database column monthly_aggregation_id SqlType(INT), Default(None)
   *  @param deviceModelId Database column device_model_id SqlType(INT), Default(None)
   *  @param count Database column count SqlType(INT), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME) */
  case class DeviceUsageAggregationsRow(id: Int, monthlyAggregationId: Option[Int] = None, deviceModelId: Option[Int] = None, count: Option[Int] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching DeviceUsageAggregationsRow objects using plain SQL queries */
  implicit def GetResultDeviceUsageAggregationsRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[java.sql.Timestamp]): GR[DeviceUsageAggregationsRow] = GR{
    prs => import prs._
    DeviceUsageAggregationsRow.tupled((<<[Int], <<?[Int], <<?[Int], <<?[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table device_usage_aggregations. Objects of this class serve as prototypes for rows in queries. */
  class DeviceUsageAggregations(_tableTag: Tag) extends Table[DeviceUsageAggregationsRow](_tableTag, "device_usage_aggregations") {
    def * = (id, monthlyAggregationId, deviceModelId, count, createdAt, updatedAt) <> (DeviceUsageAggregationsRow.tupled, DeviceUsageAggregationsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), monthlyAggregationId, deviceModelId, count, Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> DeviceUsageAggregationsRow.tupled((_1.get, _2, _3, _4, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column monthly_aggregation_id SqlType(INT), Default(None) */
    val monthlyAggregationId: Rep[Option[Int]] = column[Option[Int]]("monthly_aggregation_id", O.Default(None))
    /** Database column device_model_id SqlType(INT), Default(None) */
    val deviceModelId: Rep[Option[Int]] = column[Option[Int]]("device_model_id", O.Default(None))
    /** Database column count SqlType(INT), Default(None) */
    val count: Rep[Option[Int]] = column[Option[Int]]("count", O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (deviceModelId) (database name index_device_usage_aggregations_on_device_model_id) */
    val index1 = index("index_device_usage_aggregations_on_device_model_id", deviceModelId)
    /** Index over (monthlyAggregationId) (database name index_device_usage_aggregations_on_monthly_aggregation_id) */
    val index2 = index("index_device_usage_aggregations_on_monthly_aggregation_id", monthlyAggregationId)
  }
  /** Collection-like TableQuery object for table DeviceUsageAggregations */
  lazy val DeviceUsageAggregations = new TableQuery(tag => new DeviceUsageAggregations(tag))

  /** Entity class storing rows of table DeviseUsageLogs
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param userId Database column user_id SqlType(INT)
   *  @param userIp Database column user_ip SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param role Database column role SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME), Default(None)
   *  @param updatedAt Database column updated_at SqlType(DATETIME), Default(None)
   *  @param username Database column username SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param action Database column action SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param data Database column data SqlType(TEXT), Default(None) */
  case class DeviseUsageLogsRow(id: Int, userId: Int, userIp: Option[String] = None, role: Option[String] = None, createdAt: Option[java.sql.Timestamp] = None, updatedAt: Option[java.sql.Timestamp] = None, username: Option[String] = None, action: Option[String] = None, data: Option[String] = None)
  /** GetResult implicit for fetching DeviseUsageLogsRow objects using plain SQL queries */
  implicit def GetResultDeviseUsageLogsRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[java.sql.Timestamp]]): GR[DeviseUsageLogsRow] = GR{
    prs => import prs._
    DeviseUsageLogsRow.tupled((<<[Int], <<[Int], <<?[String], <<?[String], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table devise_usage_logs. Objects of this class serve as prototypes for rows in queries. */
  class DeviseUsageLogs(_tableTag: Tag) extends Table[DeviseUsageLogsRow](_tableTag, "devise_usage_logs") {
    def * = (id, userId, userIp, role, createdAt, updatedAt, username, action, data) <> (DeviseUsageLogsRow.tupled, DeviseUsageLogsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(userId), userIp, role, createdAt, updatedAt, username, action, data).shaped.<>({r=>import r._; _1.map(_=> DeviseUsageLogsRow.tupled((_1.get, _2.get, _3, _4, _5, _6, _7, _8, _9)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column user_id SqlType(INT) */
    val userId: Rep[Int] = column[Int]("user_id")
    /** Database column user_ip SqlType(VARCHAR), Length(255,true), Default(None) */
    val userIp: Rep[Option[String]] = column[Option[String]]("user_ip", O.Length(255,varying=true), O.Default(None))
    /** Database column role SqlType(VARCHAR), Length(255,true), Default(None) */
    val role: Rep[Option[String]] = column[Option[String]]("role", O.Length(255,varying=true), O.Default(None))
    /** Database column created_at SqlType(DATETIME), Default(None) */
    val createdAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("created_at", O.Default(None))
    /** Database column updated_at SqlType(DATETIME), Default(None) */
    val updatedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("updated_at", O.Default(None))
    /** Database column username SqlType(VARCHAR), Length(255,true), Default(None) */
    val username: Rep[Option[String]] = column[Option[String]]("username", O.Length(255,varying=true), O.Default(None))
    /** Database column action SqlType(VARCHAR), Length(255,true), Default(None) */
    val action: Rep[Option[String]] = column[Option[String]]("action", O.Length(255,varying=true), O.Default(None))
    /** Database column data SqlType(TEXT), Default(None) */
    val data: Rep[Option[String]] = column[Option[String]]("data", O.Default(None))

    /** Index over (createdAt) (database name index_devise_usage_logs_on_created_at) */
    val index1 = index("index_devise_usage_logs_on_created_at", createdAt)
    /** Index over (userId) (database name index_devise_usage_logs_on_user_id) */
    val index2 = index("index_devise_usage_logs_on_user_id", userId)
  }
  /** Collection-like TableQuery object for table DeviseUsageLogs */
  lazy val DeviseUsageLogs = new TableQuery(tag => new DeviseUsageLogs(tag))

  /** Entity class storing rows of table Events
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param content Database column content SqlType(TEXT), Default(None)
   *  @param testerActivityId Database column tester_activity_id SqlType(INT), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME) */
  case class EventsRow(id: Int, content: Option[String] = None, testerActivityId: Option[Int] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching EventsRow objects using plain SQL queries */
  implicit def GetResultEventsRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Int]], e3: GR[java.sql.Timestamp]): GR[EventsRow] = GR{
    prs => import prs._
    EventsRow.tupled((<<[Int], <<?[String], <<?[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table events. Objects of this class serve as prototypes for rows in queries. */
  class Events(_tableTag: Tag) extends Table[EventsRow](_tableTag, "events") {
    def * = (id, content, testerActivityId, createdAt, updatedAt) <> (EventsRow.tupled, EventsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), content, testerActivityId, Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> EventsRow.tupled((_1.get, _2, _3, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column content SqlType(TEXT), Default(None) */
    val content: Rep[Option[String]] = column[Option[String]]("content", O.Default(None))
    /** Database column tester_activity_id SqlType(INT), Default(None) */
    val testerActivityId: Rep[Option[Int]] = column[Option[Int]]("tester_activity_id", O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (testerActivityId) (database name index_events_on_tester_activity_id) */
    val index1 = index("index_events_on_tester_activity_id", testerActivityId)
    /** Index over (updatedAt) (database name index_events_on_updated_at) */
    val index2 = index("index_events_on_updated_at", updatedAt)
  }
  /** Collection-like TableQuery object for table Events */
  lazy val Events = new TableQuery(tag => new Events(tag))

  /** Entity class storing rows of table HeadAppViews
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param uniqueName Database column unique_name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param orientation Database column orientation SqlType(INT), Default(None)
   *  @param alias Database column alias SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param representativeAppViewId Database column representative_app_view_id SqlType(INT), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME)
   *  @param versionId Database column version_id SqlType(INT), Default(None)
   *  @param favorite Database column favorite SqlType(BIT), Default(Some(false)) */
  case class HeadAppViewsRow(id: Int, uniqueName: Option[String] = None, orientation: Option[Int] = None, alias: Option[String] = None, representativeAppViewId: Option[Int] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp, versionId: Option[Int] = None, favorite: Option[Boolean] = Some(false))
  /** GetResult implicit for fetching HeadAppViewsRow objects using plain SQL queries */
  implicit def GetResultHeadAppViewsRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Int]], e3: GR[java.sql.Timestamp], e4: GR[Option[Boolean]]): GR[HeadAppViewsRow] = GR{
    prs => import prs._
    HeadAppViewsRow.tupled((<<[Int], <<?[String], <<?[Int], <<?[String], <<?[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<?[Int], <<?[Boolean]))
  }
  /** Table description of table head_app_views. Objects of this class serve as prototypes for rows in queries. */
  class HeadAppViews(_tableTag: Tag) extends Table[HeadAppViewsRow](_tableTag, "head_app_views") {
    def * = (id, uniqueName, orientation, alias, representativeAppViewId, createdAt, updatedAt, versionId, favorite) <> (HeadAppViewsRow.tupled, HeadAppViewsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), uniqueName, orientation, alias, representativeAppViewId, Rep.Some(createdAt), Rep.Some(updatedAt), versionId, favorite).shaped.<>({r=>import r._; _1.map(_=> HeadAppViewsRow.tupled((_1.get, _2, _3, _4, _5, _6.get, _7.get, _8, _9)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column unique_name SqlType(VARCHAR), Length(255,true), Default(None) */
    val uniqueName: Rep[Option[String]] = column[Option[String]]("unique_name", O.Length(255,varying=true), O.Default(None))
    /** Database column orientation SqlType(INT), Default(None) */
    val orientation: Rep[Option[Int]] = column[Option[Int]]("orientation", O.Default(None))
    /** Database column alias SqlType(VARCHAR), Length(255,true), Default(None) */
    val alias: Rep[Option[String]] = column[Option[String]]("alias", O.Length(255,varying=true), O.Default(None))
    /** Database column representative_app_view_id SqlType(INT), Default(None) */
    val representativeAppViewId: Rep[Option[Int]] = column[Option[Int]]("representative_app_view_id", O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
    /** Database column version_id SqlType(INT), Default(None) */
    val versionId: Rep[Option[Int]] = column[Option[Int]]("version_id", O.Default(None))
    /** Database column favorite SqlType(BIT), Default(Some(false)) */
    val favorite: Rep[Option[Boolean]] = column[Option[Boolean]]("favorite", O.Default(Some(false)))

    /** Index over (versionId) (database name index_head_app_views_on_version_id) */
    val index1 = index("index_head_app_views_on_version_id", versionId)
  }
  /** Collection-like TableQuery object for table HeadAppViews */
  lazy val HeadAppViews = new TableQuery(tag => new HeadAppViews(tag))

  /** Entity class storing rows of table InnerData
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param versionId Database column version_id SqlType(INT), Default(None)
   *  @param osPlatform Database column os_platform SqlType(INT), Default(None)
   *  @param sdkVersion Database column sdk_version SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param osVersion Database column os_version SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param uniqueId Database column unique_id SqlType(VARBINARY), Default(None)
   *  @param contents Database column contents SqlType(MEDIUMTEXT), Length(16777215,true), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME) */
  case class InnerDataRow(id: Int, versionId: Option[Int] = None, osPlatform: Option[Int] = None, sdkVersion: Option[String] = None, osVersion: Option[String] = None, uniqueId: Option[java.sql.Blob] = None, contents: Option[String] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching InnerDataRow objects using plain SQL queries */
  implicit def GetResultInnerDataRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[String]], e3: GR[Option[java.sql.Blob]], e4: GR[java.sql.Timestamp]): GR[InnerDataRow] = GR{
    prs => import prs._
    InnerDataRow.tupled((<<[Int], <<?[Int], <<?[Int], <<?[String], <<?[String], <<?[java.sql.Blob], <<?[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table inner_data. Objects of this class serve as prototypes for rows in queries. */
  class InnerData(_tableTag: Tag) extends Table[InnerDataRow](_tableTag, "inner_data") {
    def * = (id, versionId, osPlatform, sdkVersion, osVersion, uniqueId, contents, createdAt, updatedAt) <> (InnerDataRow.tupled, InnerDataRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), versionId, osPlatform, sdkVersion, osVersion, uniqueId, contents, Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> InnerDataRow.tupled((_1.get, _2, _3, _4, _5, _6, _7, _8.get, _9.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column version_id SqlType(INT), Default(None) */
    val versionId: Rep[Option[Int]] = column[Option[Int]]("version_id", O.Default(None))
    /** Database column os_platform SqlType(INT), Default(None) */
    val osPlatform: Rep[Option[Int]] = column[Option[Int]]("os_platform", O.Default(None))
    /** Database column sdk_version SqlType(VARCHAR), Length(255,true), Default(None) */
    val sdkVersion: Rep[Option[String]] = column[Option[String]]("sdk_version", O.Length(255,varying=true), O.Default(None))
    /** Database column os_version SqlType(VARCHAR), Length(255,true), Default(None) */
    val osVersion: Rep[Option[String]] = column[Option[String]]("os_version", O.Length(255,varying=true), O.Default(None))
    /** Database column unique_id SqlType(VARBINARY), Default(None) */
    val uniqueId: Rep[Option[java.sql.Blob]] = column[Option[java.sql.Blob]]("unique_id", O.Default(None))
    /** Database column contents SqlType(MEDIUMTEXT), Length(16777215,true), Default(None) */
    val contents: Rep[Option[String]] = column[Option[String]]("contents", O.Length(16777215,varying=true), O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (osPlatform,sdkVersion,id) (database name index_inner_data_on_os_platform_and_sdk_version_and_id) */
    val index1 = index("index_inner_data_on_os_platform_and_sdk_version_and_id", (osPlatform, sdkVersion, id))
    /** Index over (versionId,id) (database name index_inner_data_on_version_id_and_id) */
    val index2 = index("index_inner_data_on_version_id_and_id", (versionId, id))
    /** Index over (versionId,uniqueId) (database name index_inner_data_on_version_id_and_unique_id) */
    val index3 = index("index_inner_data_on_version_id_and_unique_id", (versionId, uniqueId))
  }
  /** Collection-like TableQuery object for table InnerData */
  lazy val InnerData = new TableQuery(tag => new InnerData(tag))

  /** Entity class storing rows of table MonthlySessionStats
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param appId Database column app_id SqlType(INT)
   *  @param date Database column date SqlType(DATETIME)
   *  @param uniqueUserCount Database column unique_user_count SqlType(INT)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME)
   *  @param avgPageView Database column avg_page_view SqlType(INT), Default(0)
   *  @param avgScreenCount Database column avg_screen_count SqlType(INT), Default(0)
   *  @param avgDuration Database column avg_duration SqlType(INT), Default(0) */
  case class MonthlySessionStatsRow(id: Int, appId: Int, date: java.sql.Timestamp, uniqueUserCount: Int, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp, avgPageView: Int = 0, avgScreenCount: Int = 0, avgDuration: Int = 0)
  /** GetResult implicit for fetching MonthlySessionStatsRow objects using plain SQL queries */
  implicit def GetResultMonthlySessionStatsRow(implicit e0: GR[Int], e1: GR[java.sql.Timestamp]): GR[MonthlySessionStatsRow] = GR{
    prs => import prs._
    MonthlySessionStatsRow.tupled((<<[Int], <<[Int], <<[java.sql.Timestamp], <<[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<[Int], <<[Int], <<[Int]))
  }
  /** Table description of table monthly_session_stats. Objects of this class serve as prototypes for rows in queries. */
  class MonthlySessionStats(_tableTag: Tag) extends Table[MonthlySessionStatsRow](_tableTag, "monthly_session_stats") {
    def * = (id, appId, date, uniqueUserCount, createdAt, updatedAt, avgPageView, avgScreenCount, avgDuration) <> (MonthlySessionStatsRow.tupled, MonthlySessionStatsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(appId), Rep.Some(date), Rep.Some(uniqueUserCount), Rep.Some(createdAt), Rep.Some(updatedAt), Rep.Some(avgPageView), Rep.Some(avgScreenCount), Rep.Some(avgDuration)).shaped.<>({r=>import r._; _1.map(_=> MonthlySessionStatsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column app_id SqlType(INT) */
    val appId: Rep[Int] = column[Int]("app_id")
    /** Database column date SqlType(DATETIME) */
    val date: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("date")
    /** Database column unique_user_count SqlType(INT) */
    val uniqueUserCount: Rep[Int] = column[Int]("unique_user_count")
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
    /** Database column avg_page_view SqlType(INT), Default(0) */
    val avgPageView: Rep[Int] = column[Int]("avg_page_view", O.Default(0))
    /** Database column avg_screen_count SqlType(INT), Default(0) */
    val avgScreenCount: Rep[Int] = column[Int]("avg_screen_count", O.Default(0))
    /** Database column avg_duration SqlType(INT), Default(0) */
    val avgDuration: Rep[Int] = column[Int]("avg_duration", O.Default(0))

    /** Uniqueness Index over (appId,date) (database name index_monthly_session_stats_on_app_id_and_date) */
    val index1 = index("index_monthly_session_stats_on_app_id_and_date", (appId, date), unique=true)
  }
  /** Collection-like TableQuery object for table MonthlySessionStats */
  lazy val MonthlySessionStats = new TableQuery(tag => new MonthlySessionStats(tag))

  /** Entity class storing rows of table Notices
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param category Database column category SqlType(VARCHAR), Length(255,true)
   *  @param version Database column version SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param url Database column url SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param fileName Database column file_name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param ip Database column ip SqlType(VARCHAR), Length(255,true)
   *  @param isShown Database column is_shown SqlType(BIT), Default(false)
   *  @param createdAt Database column created_at SqlType(DATETIME), Default(None)
   *  @param updatedAt Database column updated_at SqlType(DATETIME), Default(None)
   *  @param startAt Database column start_at SqlType(DATETIME), Default(None)
   *  @param untilAt Database column until_at SqlType(DATETIME), Default(None)
   *  @param platform Database column platform SqlType(INT), Default(None) */
  case class NoticesRow(id: Int, category: String, version: Option[String] = None, url: Option[String] = None, fileName: Option[String] = None, ip: String, isShown: Boolean = false, createdAt: Option[java.sql.Timestamp] = None, updatedAt: Option[java.sql.Timestamp] = None, startAt: Option[java.sql.Timestamp] = None, untilAt: Option[java.sql.Timestamp] = None, platform: Option[Int] = None)
  /** GetResult implicit for fetching NoticesRow objects using plain SQL queries */
  implicit def GetResultNoticesRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]], e3: GR[Boolean], e4: GR[Option[java.sql.Timestamp]], e5: GR[Option[Int]]): GR[NoticesRow] = GR{
    prs => import prs._
    NoticesRow.tupled((<<[Int], <<[String], <<?[String], <<?[String], <<?[String], <<[String], <<[Boolean], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp], <<?[Int]))
  }
  /** Table description of table notices. Objects of this class serve as prototypes for rows in queries. */
  class Notices(_tableTag: Tag) extends Table[NoticesRow](_tableTag, "notices") {
    def * = (id, category, version, url, fileName, ip, isShown, createdAt, updatedAt, startAt, untilAt, platform) <> (NoticesRow.tupled, NoticesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(category), version, url, fileName, Rep.Some(ip), Rep.Some(isShown), createdAt, updatedAt, startAt, untilAt, platform).shaped.<>({r=>import r._; _1.map(_=> NoticesRow.tupled((_1.get, _2.get, _3, _4, _5, _6.get, _7.get, _8, _9, _10, _11, _12)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column category SqlType(VARCHAR), Length(255,true) */
    val category: Rep[String] = column[String]("category", O.Length(255,varying=true))
    /** Database column version SqlType(VARCHAR), Length(255,true), Default(None) */
    val version: Rep[Option[String]] = column[Option[String]]("version", O.Length(255,varying=true), O.Default(None))
    /** Database column url SqlType(VARCHAR), Length(255,true), Default(None) */
    val url: Rep[Option[String]] = column[Option[String]]("url", O.Length(255,varying=true), O.Default(None))
    /** Database column file_name SqlType(VARCHAR), Length(255,true), Default(None) */
    val fileName: Rep[Option[String]] = column[Option[String]]("file_name", O.Length(255,varying=true), O.Default(None))
    /** Database column ip SqlType(VARCHAR), Length(255,true) */
    val ip: Rep[String] = column[String]("ip", O.Length(255,varying=true))
    /** Database column is_shown SqlType(BIT), Default(false) */
    val isShown: Rep[Boolean] = column[Boolean]("is_shown", O.Default(false))
    /** Database column created_at SqlType(DATETIME), Default(None) */
    val createdAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("created_at", O.Default(None))
    /** Database column updated_at SqlType(DATETIME), Default(None) */
    val updatedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("updated_at", O.Default(None))
    /** Database column start_at SqlType(DATETIME), Default(None) */
    val startAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("start_at", O.Default(None))
    /** Database column until_at SqlType(DATETIME), Default(None) */
    val untilAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("until_at", O.Default(None))
    /** Database column platform SqlType(INT), Default(None) */
    val platform: Rep[Option[Int]] = column[Option[Int]]("platform", O.Default(None))

    /** Index over (platform,version) (database name index_notices_on_platform_and_version) */
    val index1 = index("index_notices_on_platform_and_version", (platform, version))
  }
  /** Collection-like TableQuery object for table Notices */
  lazy val Notices = new TableQuery(tag => new Notices(tag))

  /** Entity class storing rows of table ObfuscationMappings
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param versionId Database column version_id SqlType(INT)
   *  @param originalName Database column original_name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param obfuscatedName Database column obfuscated_name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME) */
  case class ObfuscationMappingsRow(id: Int, versionId: Int, originalName: Option[String] = None, obfuscatedName: Option[String] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching ObfuscationMappingsRow objects using plain SQL queries */
  implicit def GetResultObfuscationMappingsRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[java.sql.Timestamp]): GR[ObfuscationMappingsRow] = GR{
    prs => import prs._
    ObfuscationMappingsRow.tupled((<<[Int], <<[Int], <<?[String], <<?[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table obfuscation_mappings. Objects of this class serve as prototypes for rows in queries. */
  class ObfuscationMappings(_tableTag: Tag) extends Table[ObfuscationMappingsRow](_tableTag, "obfuscation_mappings") {
    def * = (id, versionId, originalName, obfuscatedName, createdAt, updatedAt) <> (ObfuscationMappingsRow.tupled, ObfuscationMappingsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(versionId), originalName, obfuscatedName, Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> ObfuscationMappingsRow.tupled((_1.get, _2.get, _3, _4, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column version_id SqlType(INT) */
    val versionId: Rep[Int] = column[Int]("version_id")
    /** Database column original_name SqlType(VARCHAR), Length(255,true), Default(None) */
    val originalName: Rep[Option[String]] = column[Option[String]]("original_name", O.Length(255,varying=true), O.Default(None))
    /** Database column obfuscated_name SqlType(VARCHAR), Length(255,true), Default(None) */
    val obfuscatedName: Rep[Option[String]] = column[Option[String]]("obfuscated_name", O.Length(255,varying=true), O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (versionId) (database name index_obfuscation_mappings_on_version_id) */
    val index1 = index("index_obfuscation_mappings_on_version_id", versionId)
  }
  /** Collection-like TableQuery object for table ObfuscationMappings */
  lazy val ObfuscationMappings = new TableQuery(tag => new ObfuscationMappings(tag))

  /** Entity class storing rows of table OsUsageAggregations
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param monthlyAggregationId Database column monthly_aggregation_id SqlType(INT), Default(None)
   *  @param osVersionId Database column os_version_id SqlType(INT), Default(None)
   *  @param count Database column count SqlType(INT), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME) */
  case class OsUsageAggregationsRow(id: Int, monthlyAggregationId: Option[Int] = None, osVersionId: Option[Int] = None, count: Option[Int] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching OsUsageAggregationsRow objects using plain SQL queries */
  implicit def GetResultOsUsageAggregationsRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[java.sql.Timestamp]): GR[OsUsageAggregationsRow] = GR{
    prs => import prs._
    OsUsageAggregationsRow.tupled((<<[Int], <<?[Int], <<?[Int], <<?[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table os_usage_aggregations. Objects of this class serve as prototypes for rows in queries. */
  class OsUsageAggregations(_tableTag: Tag) extends Table[OsUsageAggregationsRow](_tableTag, "os_usage_aggregations") {
    def * = (id, monthlyAggregationId, osVersionId, count, createdAt, updatedAt) <> (OsUsageAggregationsRow.tupled, OsUsageAggregationsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), monthlyAggregationId, osVersionId, count, Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> OsUsageAggregationsRow.tupled((_1.get, _2, _3, _4, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column monthly_aggregation_id SqlType(INT), Default(None) */
    val monthlyAggregationId: Rep[Option[Int]] = column[Option[Int]]("monthly_aggregation_id", O.Default(None))
    /** Database column os_version_id SqlType(INT), Default(None) */
    val osVersionId: Rep[Option[Int]] = column[Option[Int]]("os_version_id", O.Default(None))
    /** Database column count SqlType(INT), Default(None) */
    val count: Rep[Option[Int]] = column[Option[Int]]("count", O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (monthlyAggregationId) (database name index_os_usage_aggregations_on_monthly_aggregation_id) */
    val index1 = index("index_os_usage_aggregations_on_monthly_aggregation_id", monthlyAggregationId)
    /** Index over (osVersionId) (database name index_os_usage_aggregations_on_os_version_id) */
    val index2 = index("index_os_usage_aggregations_on_os_version_id", osVersionId)
  }
  /** Collection-like TableQuery object for table OsUsageAggregations */
  lazy val OsUsageAggregations = new TableQuery(tag => new OsUsageAggregations(tag))

  /** Entity class storing rows of table OsVersions
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param platform Database column platform SqlType(INT), Default(None)
   *  @param versionName Database column version_name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME) */
  case class OsVersionsRow(id: Int, platform: Option[Int] = None, versionName: Option[String] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching OsVersionsRow objects using plain SQL queries */
  implicit def GetResultOsVersionsRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[String]], e3: GR[java.sql.Timestamp]): GR[OsVersionsRow] = GR{
    prs => import prs._
    OsVersionsRow.tupled((<<[Int], <<?[Int], <<?[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table os_versions. Objects of this class serve as prototypes for rows in queries. */
  class OsVersions(_tableTag: Tag) extends Table[OsVersionsRow](_tableTag, "os_versions") {
    def * = (id, platform, versionName, createdAt, updatedAt) <> (OsVersionsRow.tupled, OsVersionsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), platform, versionName, Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> OsVersionsRow.tupled((_1.get, _2, _3, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column platform SqlType(INT), Default(None) */
    val platform: Rep[Option[Int]] = column[Option[Int]]("platform", O.Default(None))
    /** Database column version_name SqlType(VARCHAR), Length(255,true), Default(None) */
    val versionName: Rep[Option[String]] = column[Option[String]]("version_name", O.Length(255,varying=true), O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
  }
  /** Collection-like TableQuery object for table OsVersions */
  lazy val OsVersions = new TableQuery(tag => new OsVersions(tag))

  /** Row type of table PublishedApps */
  type PublishedAppsRow = HCons[Int,HCons[Option[String],HCons[Option[String],HCons[Option[Int],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[Float],HCons[Option[Int],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[java.sql.Timestamp,HCons[java.sql.Timestamp,HNil]]]]]]]]]]]]]]]]]]]]]]]]
  /** Constructor for PublishedAppsRow providing default values if available in the database schema. */
  def PublishedAppsRow(id: Int, currentVersionName: Option[String] = None, imageUrl: Option[String] = None, storeType: Option[Int] = None, storeId: Option[String] = None, description: Option[String] = None, url: Option[String] = None, latestPublishedAt: Option[String] = None, originPublishedAt: Option[String] = None, installs: Option[String] = None, requires: Option[String] = None, supportDevices: Option[String] = None, offeredBy: Option[String] = None, categories: Option[String] = None, rate: Option[Float] = None, ratingCount: Option[Int] = None, appSize: Option[String] = None, appName: Option[String] = None, pricing: Option[String] = None, contentRating: Option[String] = None, packageId: Option[String] = None, releaseNote: Option[String] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp): PublishedAppsRow = {
    id :: currentVersionName :: imageUrl :: storeType :: storeId :: description :: url :: latestPublishedAt :: originPublishedAt :: installs :: requires :: supportDevices :: offeredBy :: categories :: rate :: ratingCount :: appSize :: appName :: pricing :: contentRating :: packageId :: releaseNote :: createdAt :: updatedAt :: HNil
  }
  /** GetResult implicit for fetching PublishedAppsRow objects using plain SQL queries */
  implicit def GetResultPublishedAppsRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Int]], e3: GR[Option[Float]], e4: GR[java.sql.Timestamp]): GR[PublishedAppsRow] = GR{
    prs => import prs._
    <<[Int] :: <<?[String] :: <<?[String] :: <<?[Int] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[Float] :: <<?[Int] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<[java.sql.Timestamp] :: <<[java.sql.Timestamp] :: HNil
  }
  /** Table description of table published_apps. Objects of this class serve as prototypes for rows in queries. */
  class PublishedApps(_tableTag: Tag) extends Table[PublishedAppsRow](_tableTag, "published_apps") {
    def * = id :: currentVersionName :: imageUrl :: storeType :: storeId :: description :: url :: latestPublishedAt :: originPublishedAt :: installs :: requires :: supportDevices :: offeredBy :: categories :: rate :: ratingCount :: appSize :: appName :: pricing :: contentRating :: packageId :: releaseNote :: createdAt :: updatedAt :: HNil

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column current_version_name SqlType(VARCHAR), Length(255,true), Default(None) */
    val currentVersionName: Rep[Option[String]] = column[Option[String]]("current_version_name", O.Length(255,varying=true), O.Default(None))
    /** Database column image_url SqlType(VARCHAR), Length(4096,true), Default(None) */
    val imageUrl: Rep[Option[String]] = column[Option[String]]("image_url", O.Length(4096,varying=true), O.Default(None))
    /** Database column store_type SqlType(INT), Default(None) */
    val storeType: Rep[Option[Int]] = column[Option[Int]]("store_type", O.Default(None))
    /** Database column store_id SqlType(VARCHAR), Length(255,true), Default(None) */
    val storeId: Rep[Option[String]] = column[Option[String]]("store_id", O.Length(255,varying=true), O.Default(None))
    /** Database column description SqlType(TEXT), Default(None) */
    val description: Rep[Option[String]] = column[Option[String]]("description", O.Default(None))
    /** Database column url SqlType(TEXT), Default(None) */
    val url: Rep[Option[String]] = column[Option[String]]("url", O.Default(None))
    /** Database column latest_published_at SqlType(VARCHAR), Length(255,true), Default(None) */
    val latestPublishedAt: Rep[Option[String]] = column[Option[String]]("latest_published_at", O.Length(255,varying=true), O.Default(None))
    /** Database column origin_published_at SqlType(VARCHAR), Length(255,true), Default(None) */
    val originPublishedAt: Rep[Option[String]] = column[Option[String]]("origin_published_at", O.Length(255,varying=true), O.Default(None))
    /** Database column installs SqlType(VARCHAR), Length(255,true), Default(None) */
    val installs: Rep[Option[String]] = column[Option[String]]("installs", O.Length(255,varying=true), O.Default(None))
    /** Database column requires SqlType(TEXT), Default(None) */
    val requires: Rep[Option[String]] = column[Option[String]]("requires", O.Default(None))
    /** Database column support_devices SqlType(TEXT), Default(None) */
    val supportDevices: Rep[Option[String]] = column[Option[String]]("support_devices", O.Default(None))
    /** Database column offered_by SqlType(VARCHAR), Length(255,true), Default(None) */
    val offeredBy: Rep[Option[String]] = column[Option[String]]("offered_by", O.Length(255,varying=true), O.Default(None))
    /** Database column categories SqlType(VARCHAR), Length(255,true), Default(None) */
    val categories: Rep[Option[String]] = column[Option[String]]("categories", O.Length(255,varying=true), O.Default(None))
    /** Database column rate SqlType(FLOAT), Default(None) */
    val rate: Rep[Option[Float]] = column[Option[Float]]("rate", O.Default(None))
    /** Database column rating_count SqlType(INT), Default(None) */
    val ratingCount: Rep[Option[Int]] = column[Option[Int]]("rating_count", O.Default(None))
    /** Database column app_size SqlType(VARCHAR), Length(255,true), Default(None) */
    val appSize: Rep[Option[String]] = column[Option[String]]("app_size", O.Length(255,varying=true), O.Default(None))
    /** Database column app_name SqlType(VARCHAR), Length(255,true), Default(None) */
    val appName: Rep[Option[String]] = column[Option[String]]("app_name", O.Length(255,varying=true), O.Default(None))
    /** Database column pricing SqlType(VARCHAR), Length(255,true), Default(None) */
    val pricing: Rep[Option[String]] = column[Option[String]]("pricing", O.Length(255,varying=true), O.Default(None))
    /** Database column content_rating SqlType(VARCHAR), Length(255,true), Default(None) */
    val contentRating: Rep[Option[String]] = column[Option[String]]("content_rating", O.Length(255,varying=true), O.Default(None))
    /** Database column package_id SqlType(TEXT), Default(None) */
    val packageId: Rep[Option[String]] = column[Option[String]]("package_id", O.Default(None))
    /** Database column release_note SqlType(MEDIUMTEXT), Length(16777215,true), Default(None) */
    val releaseNote: Rep[Option[String]] = column[Option[String]]("release_note", O.Length(16777215,varying=true), O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Uniqueness Index over (storeType,storeId) (database name index_published_apps_on_store_type_and_store_id) */
    val index1 = index("index_published_apps_on_store_type_and_store_id", storeType :: storeId :: HNil, unique=true)
  }
  /** Collection-like TableQuery object for table PublishedApps */
  lazy val PublishedApps = new TableQuery(tag => new PublishedApps(tag))

  /** Entity class storing rows of table RedactorAssets
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param userId Database column user_id SqlType(INT), Default(None)
   *  @param dataFileName Database column data_file_name SqlType(VARCHAR), Length(255,true)
   *  @param dataContentType Database column data_content_type SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param dataFileSize Database column data_file_size SqlType(INT), Default(None)
   *  @param assetableId Database column assetable_id SqlType(INT), Default(None)
   *  @param assetableType Database column assetable_type SqlType(VARCHAR), Length(30,true), Default(None)
   *  @param `type` Database column type SqlType(VARCHAR), Length(30,true), Default(None)
   *  @param width Database column width SqlType(INT), Default(None)
   *  @param height Database column height SqlType(INT), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME) */
  case class RedactorAssetsRow(id: Int, userId: Option[Int] = None, dataFileName: String, dataContentType: Option[String] = None, dataFileSize: Option[Int] = None, assetableId: Option[Int] = None, assetableType: Option[String] = None, `type`: Option[String] = None, width: Option[Int] = None, height: Option[Int] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching RedactorAssetsRow objects using plain SQL queries */
  implicit def GetResultRedactorAssetsRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[String], e3: GR[Option[String]], e4: GR[java.sql.Timestamp]): GR[RedactorAssetsRow] = GR{
    prs => import prs._
    RedactorAssetsRow.tupled((<<[Int], <<?[Int], <<[String], <<?[String], <<?[Int], <<?[Int], <<?[String], <<?[String], <<?[Int], <<?[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table redactor_assets. Objects of this class serve as prototypes for rows in queries.
   *  NOTE: The following names collided with Scala keywords and were escaped: type */
  class RedactorAssets(_tableTag: Tag) extends Table[RedactorAssetsRow](_tableTag, "redactor_assets") {
    def * = (id, userId, dataFileName, dataContentType, dataFileSize, assetableId, assetableType, `type`, width, height, createdAt, updatedAt) <> (RedactorAssetsRow.tupled, RedactorAssetsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), userId, Rep.Some(dataFileName), dataContentType, dataFileSize, assetableId, assetableType, `type`, width, height, Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> RedactorAssetsRow.tupled((_1.get, _2, _3.get, _4, _5, _6, _7, _8, _9, _10, _11.get, _12.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column user_id SqlType(INT), Default(None) */
    val userId: Rep[Option[Int]] = column[Option[Int]]("user_id", O.Default(None))
    /** Database column data_file_name SqlType(VARCHAR), Length(255,true) */
    val dataFileName: Rep[String] = column[String]("data_file_name", O.Length(255,varying=true))
    /** Database column data_content_type SqlType(VARCHAR), Length(255,true), Default(None) */
    val dataContentType: Rep[Option[String]] = column[Option[String]]("data_content_type", O.Length(255,varying=true), O.Default(None))
    /** Database column data_file_size SqlType(INT), Default(None) */
    val dataFileSize: Rep[Option[Int]] = column[Option[Int]]("data_file_size", O.Default(None))
    /** Database column assetable_id SqlType(INT), Default(None) */
    val assetableId: Rep[Option[Int]] = column[Option[Int]]("assetable_id", O.Default(None))
    /** Database column assetable_type SqlType(VARCHAR), Length(30,true), Default(None) */
    val assetableType: Rep[Option[String]] = column[Option[String]]("assetable_type", O.Length(30,varying=true), O.Default(None))
    /** Database column type SqlType(VARCHAR), Length(30,true), Default(None)
     *  NOTE: The name was escaped because it collided with a Scala keyword. */
    val `type`: Rep[Option[String]] = column[Option[String]]("type", O.Length(30,varying=true), O.Default(None))
    /** Database column width SqlType(INT), Default(None) */
    val width: Rep[Option[Int]] = column[Option[Int]]("width", O.Default(None))
    /** Database column height SqlType(INT), Default(None) */
    val height: Rep[Option[Int]] = column[Option[Int]]("height", O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (assetableType,assetableId) (database name idx_redactor_assetable) */
    val index1 = index("idx_redactor_assetable", (assetableType, assetableId))
    /** Index over (assetableType,`type`,assetableId) (database name idx_redactor_assetable_type) */
    val index2 = index("idx_redactor_assetable_type", (assetableType, `type`, assetableId))
  }
  /** Collection-like TableQuery object for table RedactorAssets */
  lazy val RedactorAssets = new TableQuery(tag => new RedactorAssets(tag))

  /** Entity class storing rows of table Roles
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param resourceId Database column resource_id SqlType(INT), Default(None)
   *  @param resourceType Database column resource_type SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME) */
  case class RolesRow(id: Int, name: Option[String] = None, resourceId: Option[Int] = None, resourceType: Option[String] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching RolesRow objects using plain SQL queries */
  implicit def GetResultRolesRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Int]], e3: GR[java.sql.Timestamp]): GR[RolesRow] = GR{
    prs => import prs._
    RolesRow.tupled((<<[Int], <<?[String], <<?[Int], <<?[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table roles. Objects of this class serve as prototypes for rows in queries. */
  class Roles(_tableTag: Tag) extends Table[RolesRow](_tableTag, "roles") {
    def * = (id, name, resourceId, resourceType, createdAt, updatedAt) <> (RolesRow.tupled, RolesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), name, resourceId, resourceType, Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> RolesRow.tupled((_1.get, _2, _3, _4, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(255,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(255,varying=true), O.Default(None))
    /** Database column resource_id SqlType(INT), Default(None) */
    val resourceId: Rep[Option[Int]] = column[Option[Int]]("resource_id", O.Default(None))
    /** Database column resource_type SqlType(VARCHAR), Length(255,true), Default(None) */
    val resourceType: Rep[Option[String]] = column[Option[String]]("resource_type", O.Length(255,varying=true), O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (name) (database name index_roles_on_name) */
    val index1 = index("index_roles_on_name", name)
    /** Index over (name,resourceType,resourceId) (database name index_roles_on_name_and_resource_type_and_resource_id) */
    val index2 = index("index_roles_on_name_and_resource_type_and_resource_id", (name, resourceType, resourceId))
  }
  /** Collection-like TableQuery object for table Roles */
  lazy val Roles = new TableQuery(tag => new Roles(tag))

  /** Entity class storing rows of table ScenarioPaths
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param scenarioId Database column scenario_id SqlType(INT), Default(None)
   *  @param nextScenarioPathId Database column next_scenario_path_id SqlType(INT), Default(None)
   *  @param checkpointId Database column checkpoint_id SqlType(INT), Default(None)
   *  @param completeCheckpointId Database column complete_checkpoint_id SqlType(INT), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME) */
  case class ScenarioPathsRow(id: Int, scenarioId: Option[Int] = None, nextScenarioPathId: Option[Int] = None, checkpointId: Option[Int] = None, completeCheckpointId: Option[Int] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching ScenarioPathsRow objects using plain SQL queries */
  implicit def GetResultScenarioPathsRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[java.sql.Timestamp]): GR[ScenarioPathsRow] = GR{
    prs => import prs._
    ScenarioPathsRow.tupled((<<[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table scenario_paths. Objects of this class serve as prototypes for rows in queries. */
  class ScenarioPaths(_tableTag: Tag) extends Table[ScenarioPathsRow](_tableTag, "scenario_paths") {
    def * = (id, scenarioId, nextScenarioPathId, checkpointId, completeCheckpointId, createdAt, updatedAt) <> (ScenarioPathsRow.tupled, ScenarioPathsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), scenarioId, nextScenarioPathId, checkpointId, completeCheckpointId, Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> ScenarioPathsRow.tupled((_1.get, _2, _3, _4, _5, _6.get, _7.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column scenario_id SqlType(INT), Default(None) */
    val scenarioId: Rep[Option[Int]] = column[Option[Int]]("scenario_id", O.Default(None))
    /** Database column next_scenario_path_id SqlType(INT), Default(None) */
    val nextScenarioPathId: Rep[Option[Int]] = column[Option[Int]]("next_scenario_path_id", O.Default(None))
    /** Database column checkpoint_id SqlType(INT), Default(None) */
    val checkpointId: Rep[Option[Int]] = column[Option[Int]]("checkpoint_id", O.Default(None))
    /** Database column complete_checkpoint_id SqlType(INT), Default(None) */
    val completeCheckpointId: Rep[Option[Int]] = column[Option[Int]]("complete_checkpoint_id", O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (checkpointId) (database name index_scenario_paths_on_checkpoint_id) */
    val index1 = index("index_scenario_paths_on_checkpoint_id", checkpointId)
    /** Index over (scenarioId) (database name index_scenario_paths_on_scenario_id) */
    val index2 = index("index_scenario_paths_on_scenario_id", scenarioId)
  }
  /** Collection-like TableQuery object for table ScenarioPaths */
  lazy val ScenarioPaths = new TableQuery(tag => new ScenarioPaths(tag))

  /** Entity class storing rows of table ScenarioProgresses
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param testerActivityId Database column tester_activity_id SqlType(INT), Default(None)
   *  @param scenarioPathId Database column scenario_path_id SqlType(INT), Default(None)
   *  @param isEnd Database column is_end SqlType(BIT), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME) */
  case class ScenarioProgressesRow(id: Int, testerActivityId: Option[Int] = None, scenarioPathId: Option[Int] = None, isEnd: Option[Boolean] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching ScenarioProgressesRow objects using plain SQL queries */
  implicit def GetResultScenarioProgressesRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[Boolean]], e3: GR[java.sql.Timestamp]): GR[ScenarioProgressesRow] = GR{
    prs => import prs._
    ScenarioProgressesRow.tupled((<<[Int], <<?[Int], <<?[Int], <<?[Boolean], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table scenario_progresses. Objects of this class serve as prototypes for rows in queries. */
  class ScenarioProgresses(_tableTag: Tag) extends Table[ScenarioProgressesRow](_tableTag, "scenario_progresses") {
    def * = (id, testerActivityId, scenarioPathId, isEnd, createdAt, updatedAt) <> (ScenarioProgressesRow.tupled, ScenarioProgressesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), testerActivityId, scenarioPathId, isEnd, Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> ScenarioProgressesRow.tupled((_1.get, _2, _3, _4, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column tester_activity_id SqlType(INT), Default(None) */
    val testerActivityId: Rep[Option[Int]] = column[Option[Int]]("tester_activity_id", O.Default(None))
    /** Database column scenario_path_id SqlType(INT), Default(None) */
    val scenarioPathId: Rep[Option[Int]] = column[Option[Int]]("scenario_path_id", O.Default(None))
    /** Database column is_end SqlType(BIT), Default(None) */
    val isEnd: Rep[Option[Boolean]] = column[Option[Boolean]]("is_end", O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (scenarioPathId) (database name index_scenario_progresses_on_scenario_path_id) */
    val index1 = index("index_scenario_progresses_on_scenario_path_id", scenarioPathId)
    /** Index over (testerActivityId) (database name index_scenario_progresses_on_tester_activity_id) */
    val index2 = index("index_scenario_progresses_on_tester_activity_id", testerActivityId)
  }
  /** Collection-like TableQuery object for table ScenarioProgresses */
  lazy val ScenarioProgresses = new TableQuery(tag => new ScenarioProgresses(tag))

  /** Entity class storing rows of table Scenarios
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param startScenarioPathId Database column start_scenario_path_id SqlType(INT), Default(None)
   *  @param completeCheckpointId Database column complete_checkpoint_id SqlType(INT), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME)
   *  @param campaignId Database column campaign_id SqlType(INT), Default(None) */
  case class ScenariosRow(id: Int, name: Option[String] = None, startScenarioPathId: Option[Int] = None, completeCheckpointId: Option[Int] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp, campaignId: Option[Int] = None)
  /** GetResult implicit for fetching ScenariosRow objects using plain SQL queries */
  implicit def GetResultScenariosRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Int]], e3: GR[java.sql.Timestamp]): GR[ScenariosRow] = GR{
    prs => import prs._
    ScenariosRow.tupled((<<[Int], <<?[String], <<?[Int], <<?[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<?[Int]))
  }
  /** Table description of table scenarios. Objects of this class serve as prototypes for rows in queries. */
  class Scenarios(_tableTag: Tag) extends Table[ScenariosRow](_tableTag, "scenarios") {
    def * = (id, name, startScenarioPathId, completeCheckpointId, createdAt, updatedAt, campaignId) <> (ScenariosRow.tupled, ScenariosRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), name, startScenarioPathId, completeCheckpointId, Rep.Some(createdAt), Rep.Some(updatedAt), campaignId).shaped.<>({r=>import r._; _1.map(_=> ScenariosRow.tupled((_1.get, _2, _3, _4, _5.get, _6.get, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(255,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(255,varying=true), O.Default(None))
    /** Database column start_scenario_path_id SqlType(INT), Default(None) */
    val startScenarioPathId: Rep[Option[Int]] = column[Option[Int]]("start_scenario_path_id", O.Default(None))
    /** Database column complete_checkpoint_id SqlType(INT), Default(None) */
    val completeCheckpointId: Rep[Option[Int]] = column[Option[Int]]("complete_checkpoint_id", O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
    /** Database column campaign_id SqlType(INT), Default(None) */
    val campaignId: Rep[Option[Int]] = column[Option[Int]]("campaign_id", O.Default(None))
  }
  /** Collection-like TableQuery object for table Scenarios */
  lazy val Scenarios = new TableQuery(tag => new Scenarios(tag))

  /** Entity class storing rows of table SchemaMigrations
   *  @param version Database column version SqlType(VARCHAR), Length(255,true) */
  case class SchemaMigrationsRow(version: String)
  /** GetResult implicit for fetching SchemaMigrationsRow objects using plain SQL queries */
  implicit def GetResultSchemaMigrationsRow(implicit e0: GR[String]): GR[SchemaMigrationsRow] = GR{
    prs => import prs._
    SchemaMigrationsRow(<<[String])
  }
  /** Table description of table schema_migrations. Objects of this class serve as prototypes for rows in queries. */
  class SchemaMigrations(_tableTag: Tag) extends Table[SchemaMigrationsRow](_tableTag, "schema_migrations") {
    def * = version <> (SchemaMigrationsRow, SchemaMigrationsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = Rep.Some(version).shaped.<>(r => r.map(_=> SchemaMigrationsRow(r.get)), (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column version SqlType(VARCHAR), Length(255,true) */
    val version: Rep[String] = column[String]("version", O.Length(255,varying=true))

    /** Uniqueness Index over (version) (database name unique_schema_migrations) */
    val index1 = index("unique_schema_migrations", version, unique=true)
  }
  /** Collection-like TableQuery object for table SchemaMigrations */
  lazy val SchemaMigrations = new TableQuery(tag => new SchemaMigrations(tag))

  /** Entity class storing rows of table Sessions
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param sessionId Database column session_id SqlType(VARCHAR), Length(255,true)
   *  @param data Database column data SqlType(TEXT), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME) */
  case class SessionsRow(id: Int, sessionId: String, data: Option[String] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching SessionsRow objects using plain SQL queries */
  implicit def GetResultSessionsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]], e3: GR[java.sql.Timestamp]): GR[SessionsRow] = GR{
    prs => import prs._
    SessionsRow.tupled((<<[Int], <<[String], <<?[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table sessions. Objects of this class serve as prototypes for rows in queries. */
  class Sessions(_tableTag: Tag) extends Table[SessionsRow](_tableTag, "sessions") {
    def * = (id, sessionId, data, createdAt, updatedAt) <> (SessionsRow.tupled, SessionsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(sessionId), data, Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> SessionsRow.tupled((_1.get, _2.get, _3, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column session_id SqlType(VARCHAR), Length(255,true) */
    val sessionId: Rep[String] = column[String]("session_id", O.Length(255,varying=true))
    /** Database column data SqlType(TEXT), Default(None) */
    val data: Rep[Option[String]] = column[Option[String]]("data", O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (sessionId) (database name index_sessions_on_session_id) */
    val index1 = index("index_sessions_on_session_id", sessionId)
    /** Index over (updatedAt) (database name index_sessions_on_updated_at) */
    val index2 = index("index_sessions_on_updated_at", updatedAt)
  }
  /** Collection-like TableQuery object for table Sessions */
  lazy val Sessions = new TableQuery(tag => new Sessions(tag))

  /** Entity class storing rows of table SurveyChoices
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param surveyId Database column survey_id SqlType(INT)
   *  @param content Database column content SqlType(TEXT)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME)
   *  @param surveyChoiceOrder Database column survey_choice_order SqlType(INT), Default(None)
   *  @param connectedCheckpointId Database column connected_checkpoint_id SqlType(INT), Default(None) */
  case class SurveyChoicesRow(id: Int, surveyId: Int, content: String, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp, surveyChoiceOrder: Option[Int] = None, connectedCheckpointId: Option[Int] = None)
  /** GetResult implicit for fetching SurveyChoicesRow objects using plain SQL queries */
  implicit def GetResultSurveyChoicesRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp], e3: GR[Option[Int]]): GR[SurveyChoicesRow] = GR{
    prs => import prs._
    SurveyChoicesRow.tupled((<<[Int], <<[Int], <<[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<?[Int], <<?[Int]))
  }
  /** Table description of table survey_choices. Objects of this class serve as prototypes for rows in queries. */
  class SurveyChoices(_tableTag: Tag) extends Table[SurveyChoicesRow](_tableTag, "survey_choices") {
    def * = (id, surveyId, content, createdAt, updatedAt, surveyChoiceOrder, connectedCheckpointId) <> (SurveyChoicesRow.tupled, SurveyChoicesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(surveyId), Rep.Some(content), Rep.Some(createdAt), Rep.Some(updatedAt), surveyChoiceOrder, connectedCheckpointId).shaped.<>({r=>import r._; _1.map(_=> SurveyChoicesRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column survey_id SqlType(INT) */
    val surveyId: Rep[Int] = column[Int]("survey_id")
    /** Database column content SqlType(TEXT) */
    val content: Rep[String] = column[String]("content")
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
    /** Database column survey_choice_order SqlType(INT), Default(None) */
    val surveyChoiceOrder: Rep[Option[Int]] = column[Option[Int]]("survey_choice_order", O.Default(None))
    /** Database column connected_checkpoint_id SqlType(INT), Default(None) */
    val connectedCheckpointId: Rep[Option[Int]] = column[Option[Int]]("connected_checkpoint_id", O.Default(None))

    /** Index over (surveyId) (database name index_survey_choices_on_survey_id) */
    val index1 = index("index_survey_choices_on_survey_id", surveyId)
  }
  /** Collection-like TableQuery object for table SurveyChoices */
  lazy val SurveyChoices = new TableQuery(tag => new SurveyChoices(tag))

  /** Entity class storing rows of table SurveyDailyCounters
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param surveyId Database column survey_id SqlType(INT), Default(None)
   *  @param exposedCount Database column exposed_count SqlType(INT), Default(None)
   *  @param responsedCount Database column responsed_count SqlType(INT), Default(None)
   *  @param date Database column date SqlType(INT), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME) */
  case class SurveyDailyCountersRow(id: Int, surveyId: Option[Int] = None, exposedCount: Option[Int] = None, responsedCount: Option[Int] = None, date: Option[Int] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching SurveyDailyCountersRow objects using plain SQL queries */
  implicit def GetResultSurveyDailyCountersRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[java.sql.Timestamp]): GR[SurveyDailyCountersRow] = GR{
    prs => import prs._
    SurveyDailyCountersRow.tupled((<<[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table survey_daily_counters. Objects of this class serve as prototypes for rows in queries. */
  class SurveyDailyCounters(_tableTag: Tag) extends Table[SurveyDailyCountersRow](_tableTag, "survey_daily_counters") {
    def * = (id, surveyId, exposedCount, responsedCount, date, createdAt, updatedAt) <> (SurveyDailyCountersRow.tupled, SurveyDailyCountersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), surveyId, exposedCount, responsedCount, date, Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> SurveyDailyCountersRow.tupled((_1.get, _2, _3, _4, _5, _6.get, _7.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column survey_id SqlType(INT), Default(None) */
    val surveyId: Rep[Option[Int]] = column[Option[Int]]("survey_id", O.Default(None))
    /** Database column exposed_count SqlType(INT), Default(None) */
    val exposedCount: Rep[Option[Int]] = column[Option[Int]]("exposed_count", O.Default(None))
    /** Database column responsed_count SqlType(INT), Default(None) */
    val responsedCount: Rep[Option[Int]] = column[Option[Int]]("responsed_count", O.Default(None))
    /** Database column date SqlType(INT), Default(None) */
    val date: Rep[Option[Int]] = column[Option[Int]]("date", O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (surveyId) (database name index_survey_daily_counters_on_survey_id) */
    val index1 = index("index_survey_daily_counters_on_survey_id", surveyId)
  }
  /** Collection-like TableQuery object for table SurveyDailyCounters */
  lazy val SurveyDailyCounters = new TableQuery(tag => new SurveyDailyCounters(tag))

  /** Entity class storing rows of table SurveyExposures
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param testerActivityId Database column tester_activity_id SqlType(INT), Default(None)
   *  @param surveyId Database column survey_id SqlType(INT), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME)
   *  @param deviceTime Database column device_time SqlType(DATETIME), Default(None)
   *  @param deviceTimezone Database column device_timezone SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param seqNum Database column seq_num SqlType(INT), Default(None) */
  case class SurveyExposuresRow(id: Int, testerActivityId: Option[Int] = None, surveyId: Option[Int] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp, deviceTime: Option[java.sql.Timestamp] = None, deviceTimezone: Option[String] = None, seqNum: Option[Int] = None)
  /** GetResult implicit for fetching SurveyExposuresRow objects using plain SQL queries */
  implicit def GetResultSurveyExposuresRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[java.sql.Timestamp], e3: GR[Option[java.sql.Timestamp]], e4: GR[Option[String]]): GR[SurveyExposuresRow] = GR{
    prs => import prs._
    SurveyExposuresRow.tupled((<<[Int], <<?[Int], <<?[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<?[java.sql.Timestamp], <<?[String], <<?[Int]))
  }
  /** Table description of table survey_exposures. Objects of this class serve as prototypes for rows in queries. */
  class SurveyExposures(_tableTag: Tag) extends Table[SurveyExposuresRow](_tableTag, "survey_exposures") {
    def * = (id, testerActivityId, surveyId, createdAt, updatedAt, deviceTime, deviceTimezone, seqNum) <> (SurveyExposuresRow.tupled, SurveyExposuresRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), testerActivityId, surveyId, Rep.Some(createdAt), Rep.Some(updatedAt), deviceTime, deviceTimezone, seqNum).shaped.<>({r=>import r._; _1.map(_=> SurveyExposuresRow.tupled((_1.get, _2, _3, _4.get, _5.get, _6, _7, _8)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column tester_activity_id SqlType(INT), Default(None) */
    val testerActivityId: Rep[Option[Int]] = column[Option[Int]]("tester_activity_id", O.Default(None))
    /** Database column survey_id SqlType(INT), Default(None) */
    val surveyId: Rep[Option[Int]] = column[Option[Int]]("survey_id", O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
    /** Database column device_time SqlType(DATETIME), Default(None) */
    val deviceTime: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("device_time", O.Default(None))
    /** Database column device_timezone SqlType(VARCHAR), Length(255,true), Default(None) */
    val deviceTimezone: Rep[Option[String]] = column[Option[String]]("device_timezone", O.Length(255,varying=true), O.Default(None))
    /** Database column seq_num SqlType(INT), Default(None) */
    val seqNum: Rep[Option[Int]] = column[Option[Int]]("seq_num", O.Default(None))

    /** Index over (surveyId) (database name index_survey_exposures_on_survey_id) */
    val index1 = index("index_survey_exposures_on_survey_id", surveyId)
    /** Index over (testerActivityId) (database name index_survey_exposures_on_tester_activity_id) */
    val index2 = index("index_survey_exposures_on_tester_activity_id", testerActivityId)
  }
  /** Collection-like TableQuery object for table SurveyExposures */
  lazy val SurveyExposures = new TableQuery(tag => new SurveyExposures(tag))

  /** Entity class storing rows of table SurveyResults
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param surveyId Database column survey_id SqlType(INT)
   *  @param testerActivityId Database column tester_activity_id SqlType(INT)
   *  @param content Database column content SqlType(TEXT)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME)
   *  @param etc Database column etc SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param deviceTime Database column device_time SqlType(DATETIME), Default(None)
   *  @param deviceTimezone Database column device_timezone SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param seqNum Database column seq_num SqlType(INT), Default(None)
   *  @param versionId Database column version_id SqlType(INT), Default(None) */
  case class SurveyResultsRow(id: Int, surveyId: Int, testerActivityId: Int, content: String, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp, etc: Option[String] = None, deviceTime: Option[java.sql.Timestamp] = None, deviceTimezone: Option[String] = None, seqNum: Option[Int] = None, versionId: Option[Int] = None)
  /** GetResult implicit for fetching SurveyResultsRow objects using plain SQL queries */
  implicit def GetResultSurveyResultsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp], e3: GR[Option[String]], e4: GR[Option[java.sql.Timestamp]], e5: GR[Option[Int]]): GR[SurveyResultsRow] = GR{
    prs => import prs._
    SurveyResultsRow.tupled((<<[Int], <<[Int], <<[Int], <<[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<?[String], <<?[java.sql.Timestamp], <<?[String], <<?[Int], <<?[Int]))
  }
  /** Table description of table survey_results. Objects of this class serve as prototypes for rows in queries. */
  class SurveyResults(_tableTag: Tag) extends Table[SurveyResultsRow](_tableTag, "survey_results") {
    def * = (id, surveyId, testerActivityId, content, createdAt, updatedAt, etc, deviceTime, deviceTimezone, seqNum, versionId) <> (SurveyResultsRow.tupled, SurveyResultsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(surveyId), Rep.Some(testerActivityId), Rep.Some(content), Rep.Some(createdAt), Rep.Some(updatedAt), etc, deviceTime, deviceTimezone, seqNum, versionId).shaped.<>({r=>import r._; _1.map(_=> SurveyResultsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7, _8, _9, _10, _11)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column survey_id SqlType(INT) */
    val surveyId: Rep[Int] = column[Int]("survey_id")
    /** Database column tester_activity_id SqlType(INT) */
    val testerActivityId: Rep[Int] = column[Int]("tester_activity_id")
    /** Database column content SqlType(TEXT) */
    val content: Rep[String] = column[String]("content")
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
    /** Database column etc SqlType(VARCHAR), Length(255,true), Default(None) */
    val etc: Rep[Option[String]] = column[Option[String]]("etc", O.Length(255,varying=true), O.Default(None))
    /** Database column device_time SqlType(DATETIME), Default(None) */
    val deviceTime: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("device_time", O.Default(None))
    /** Database column device_timezone SqlType(VARCHAR), Length(255,true), Default(None) */
    val deviceTimezone: Rep[Option[String]] = column[Option[String]]("device_timezone", O.Length(255,varying=true), O.Default(None))
    /** Database column seq_num SqlType(INT), Default(None) */
    val seqNum: Rep[Option[Int]] = column[Option[Int]]("seq_num", O.Default(None))
    /** Database column version_id SqlType(INT), Default(None) */
    val versionId: Rep[Option[Int]] = column[Option[Int]]("version_id", O.Default(None))

    /** Index over (surveyId) (database name index_survey_results_on_survey_id) */
    val index1 = index("index_survey_results_on_survey_id", surveyId)
    /** Index over (testerActivityId) (database name index_survey_results_on_tester_activity_id) */
    val index2 = index("index_survey_results_on_tester_activity_id", testerActivityId)
  }
  /** Collection-like TableQuery object for table SurveyResults */
  lazy val SurveyResults = new TableQuery(tag => new SurveyResults(tag))

  /** Entity class storing rows of table Surveys
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param surveyTypeId Database column survey_type_id SqlType(INT)
   *  @param checkpointId Database column checkpoint_id SqlType(INT)
   *  @param title Database column title SqlType(VARCHAR), Length(255,true)
   *  @param description Database column description SqlType(TEXT)
   *  @param surveyOrder Database column survey_order SqlType(INT), Default(0)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME)
   *  @param shortName Database column short_name SqlType(VARCHAR), Length(255,true), Default(None) */
  case class SurveysRow(id: Int, surveyTypeId: Int, checkpointId: Int, title: String, description: String, surveyOrder: Int = 0, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp, shortName: Option[String] = None)
  /** GetResult implicit for fetching SurveysRow objects using plain SQL queries */
  implicit def GetResultSurveysRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp], e3: GR[Option[String]]): GR[SurveysRow] = GR{
    prs => import prs._
    SurveysRow.tupled((<<[Int], <<[Int], <<[Int], <<[String], <<[String], <<[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<?[String]))
  }
  /** Table description of table surveys. Objects of this class serve as prototypes for rows in queries. */
  class Surveys(_tableTag: Tag) extends Table[SurveysRow](_tableTag, "surveys") {
    def * = (id, surveyTypeId, checkpointId, title, description, surveyOrder, createdAt, updatedAt, shortName) <> (SurveysRow.tupled, SurveysRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(surveyTypeId), Rep.Some(checkpointId), Rep.Some(title), Rep.Some(description), Rep.Some(surveyOrder), Rep.Some(createdAt), Rep.Some(updatedAt), shortName).shaped.<>({r=>import r._; _1.map(_=> SurveysRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column survey_type_id SqlType(INT) */
    val surveyTypeId: Rep[Int] = column[Int]("survey_type_id")
    /** Database column checkpoint_id SqlType(INT) */
    val checkpointId: Rep[Int] = column[Int]("checkpoint_id")
    /** Database column title SqlType(VARCHAR), Length(255,true) */
    val title: Rep[String] = column[String]("title", O.Length(255,varying=true))
    /** Database column description SqlType(TEXT) */
    val description: Rep[String] = column[String]("description")
    /** Database column survey_order SqlType(INT), Default(0) */
    val surveyOrder: Rep[Int] = column[Int]("survey_order", O.Default(0))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
    /** Database column short_name SqlType(VARCHAR), Length(255,true), Default(None) */
    val shortName: Rep[Option[String]] = column[Option[String]]("short_name", O.Length(255,varying=true), O.Default(None))

    /** Index over (checkpointId) (database name index_surveys_on_checkpoint_id) */
    val index1 = index("index_surveys_on_checkpoint_id", checkpointId)
    /** Index over (surveyTypeId) (database name index_surveys_on_survey_type_id) */
    val index2 = index("index_surveys_on_survey_type_id", surveyTypeId)
  }
  /** Collection-like TableQuery object for table Surveys */
  lazy val Surveys = new TableQuery(tag => new Surveys(tag))

  /** Entity class storing rows of table SurveyTypes
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(VARCHAR), Length(255,true)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME)
   *  @param objective Database column objective SqlType(BIT), Default(Some(false)) */
  case class SurveyTypesRow(id: Int, name: String, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp, objective: Option[Boolean] = Some(false))
  /** GetResult implicit for fetching SurveyTypesRow objects using plain SQL queries */
  implicit def GetResultSurveyTypesRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp], e3: GR[Option[Boolean]]): GR[SurveyTypesRow] = GR{
    prs => import prs._
    SurveyTypesRow.tupled((<<[Int], <<[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<?[Boolean]))
  }
  /** Table description of table survey_types. Objects of this class serve as prototypes for rows in queries. */
  class SurveyTypes(_tableTag: Tag) extends Table[SurveyTypesRow](_tableTag, "survey_types") {
    def * = (id, name, createdAt, updatedAt, objective) <> (SurveyTypesRow.tupled, SurveyTypesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name), Rep.Some(createdAt), Rep.Some(updatedAt), objective).shaped.<>({r=>import r._; _1.map(_=> SurveyTypesRow.tupled((_1.get, _2.get, _3.get, _4.get, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
    /** Database column objective SqlType(BIT), Default(Some(false)) */
    val objective: Rep[Option[Boolean]] = column[Option[Boolean]]("objective", O.Default(Some(false)))
  }
  /** Collection-like TableQuery object for table SurveyTypes */
  lazy val SurveyTypes = new TableQuery(tag => new SurveyTypes(tag))

  /** Entity class storing rows of table TesterActivities
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param testerDeviceId Database column tester_device_id SqlType(INT)
   *  @param campaignId Database column campaign_id SqlType(INT)
   *  @param complete Database column complete SqlType(BIT), Default(false)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME) */
  case class TesterActivitiesRow(id: Int, testerDeviceId: Int, campaignId: Int, complete: Boolean = false, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching TesterActivitiesRow objects using plain SQL queries */
  implicit def GetResultTesterActivitiesRow(implicit e0: GR[Int], e1: GR[Boolean], e2: GR[java.sql.Timestamp]): GR[TesterActivitiesRow] = GR{
    prs => import prs._
    TesterActivitiesRow.tupled((<<[Int], <<[Int], <<[Int], <<[Boolean], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table tester_activities. Objects of this class serve as prototypes for rows in queries. */
  class TesterActivities(_tableTag: Tag) extends Table[TesterActivitiesRow](_tableTag, "tester_activities") {
    def * = (id, testerDeviceId, campaignId, complete, createdAt, updatedAt) <> (TesterActivitiesRow.tupled, TesterActivitiesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(testerDeviceId), Rep.Some(campaignId), Rep.Some(complete), Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> TesterActivitiesRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column tester_device_id SqlType(INT) */
    val testerDeviceId: Rep[Int] = column[Int]("tester_device_id")
    /** Database column campaign_id SqlType(INT) */
    val campaignId: Rep[Int] = column[Int]("campaign_id")
    /** Database column complete SqlType(BIT), Default(false) */
    val complete: Rep[Boolean] = column[Boolean]("complete", O.Default(false))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (campaignId) (database name index_tester_activities_on_campaign_id) */
    val index1 = index("index_tester_activities_on_campaign_id", campaignId)
    /** Index over (testerDeviceId) (database name index_tester_activities_on_tester_device_id) */
    val index2 = index("index_tester_activities_on_tester_device_id", testerDeviceId)
    /** Index over (testerDeviceId,campaignId) (database name index_tester_activities_on_tester_device_id_and_campaign_id) */
    val index3 = index("index_tester_activities_on_tester_device_id_and_campaign_id", (testerDeviceId, campaignId))
  }
  /** Collection-like TableQuery object for table TesterActivities */
  lazy val TesterActivities = new TableQuery(tag => new TesterActivities(tag))

  /** Entity class storing rows of table TesterDevices
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param uniqueId Database column unique_id SqlType(VARCHAR), Length(255,true)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME)
   *  @param osVersion Database column os_version SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param osPlatform Database column os_platform SqlType(INT), Default(None)
   *  @param locale Database column locale SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param model Database column model SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param pushRegistrationId Database column push_registration_id SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param deviceModelId Database column device_model_id SqlType(INT), Default(None)
   *  @param appId Database column app_id SqlType(INT), Default(None)
   *  @param width Database column width SqlType(INT), Default(None)
   *  @param height Database column height SqlType(INT), Default(None)
   *  @param sessionCount Database column session_count SqlType(INT), Default(Some(0)) */
  case class TesterDevicesRow(id: Int, uniqueId: String, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp, osVersion: Option[String] = None, osPlatform: Option[Int] = None, locale: Option[String] = None, model: Option[String] = None, pushRegistrationId: Option[String] = None, deviceModelId: Option[Int] = None, appId: Option[Int] = None, width: Option[Int] = None, height: Option[Int] = None, sessionCount: Option[Int] = Some(0))
  /** GetResult implicit for fetching TesterDevicesRow objects using plain SQL queries */
  implicit def GetResultTesterDevicesRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp], e3: GR[Option[String]], e4: GR[Option[Int]]): GR[TesterDevicesRow] = GR{
    prs => import prs._
    TesterDevicesRow.tupled((<<[Int], <<[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<?[String], <<?[Int], <<?[String], <<?[String], <<?[String], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int]))
  }
  /** Table description of table tester_devices. Objects of this class serve as prototypes for rows in queries. */
  class TesterDevices(_tableTag: Tag) extends Table[TesterDevicesRow](_tableTag, "tester_devices") {
    def * = (id, uniqueId, createdAt, updatedAt, osVersion, osPlatform, locale, model, pushRegistrationId, deviceModelId, appId, width, height, sessionCount) <> (TesterDevicesRow.tupled, TesterDevicesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(uniqueId), Rep.Some(createdAt), Rep.Some(updatedAt), osVersion, osPlatform, locale, model, pushRegistrationId, deviceModelId, appId, width, height, sessionCount).shaped.<>({r=>import r._; _1.map(_=> TesterDevicesRow.tupled((_1.get, _2.get, _3.get, _4.get, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column unique_id SqlType(VARCHAR), Length(255,true) */
    val uniqueId: Rep[String] = column[String]("unique_id", O.Length(255,varying=true))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
    /** Database column os_version SqlType(VARCHAR), Length(255,true), Default(None) */
    val osVersion: Rep[Option[String]] = column[Option[String]]("os_version", O.Length(255,varying=true), O.Default(None))
    /** Database column os_platform SqlType(INT), Default(None) */
    val osPlatform: Rep[Option[Int]] = column[Option[Int]]("os_platform", O.Default(None))
    /** Database column locale SqlType(VARCHAR), Length(255,true), Default(None) */
    val locale: Rep[Option[String]] = column[Option[String]]("locale", O.Length(255,varying=true), O.Default(None))
    /** Database column model SqlType(VARCHAR), Length(255,true), Default(None) */
    val model: Rep[Option[String]] = column[Option[String]]("model", O.Length(255,varying=true), O.Default(None))
    /** Database column push_registration_id SqlType(VARCHAR), Length(255,true), Default(None) */
    val pushRegistrationId: Rep[Option[String]] = column[Option[String]]("push_registration_id", O.Length(255,varying=true), O.Default(None))
    /** Database column device_model_id SqlType(INT), Default(None) */
    val deviceModelId: Rep[Option[Int]] = column[Option[Int]]("device_model_id", O.Default(None))
    /** Database column app_id SqlType(INT), Default(None) */
    val appId: Rep[Option[Int]] = column[Option[Int]]("app_id", O.Default(None))
    /** Database column width SqlType(INT), Default(None) */
    val width: Rep[Option[Int]] = column[Option[Int]]("width", O.Default(None))
    /** Database column height SqlType(INT), Default(None) */
    val height: Rep[Option[Int]] = column[Option[Int]]("height", O.Default(None))
    /** Database column session_count SqlType(INT), Default(Some(0)) */
    val sessionCount: Rep[Option[Int]] = column[Option[Int]]("session_count", O.Default(Some(0)))

    /** Index over (appId) (database name index_tester_devices_on_app_id) */
    val index1 = index("index_tester_devices_on_app_id", appId)
    /** Index over (appId,width,height) (database name index_tester_devices_on_app_id_and_width_and_height) */
    val index2 = index("index_tester_devices_on_app_id_and_width_and_height", (appId, width, height))
    /** Index over (deviceModelId) (database name index_tester_devices_on_device_model_id) */
    val index3 = index("index_tester_devices_on_device_model_id", deviceModelId)
    /** Index over (model) (database name index_tester_devices_on_model) */
    val index4 = index("index_tester_devices_on_model", model)
    /** Index over (osPlatform,osVersion) (database name index_tester_devices_on_os_platform_and_os_version) */
    val index5 = index("index_tester_devices_on_os_platform_and_os_version", (osPlatform, osVersion))
    /** Index over (uniqueId) (database name index_tester_devices_on_unique_id) */
    val index6 = index("index_tester_devices_on_unique_id", uniqueId)
  }
  /** Collection-like TableQuery object for table TesterDevices */
  lazy val TesterDevices = new TableQuery(tag => new TesterDevices(tag))

  /** Entity class storing rows of table TesterMetaData
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param content Database column content SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param testerMetaDataTypeId Database column tester_meta_data_type_id SqlType(INT), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME)
   *  @param testerDeviceId Database column tester_device_id SqlType(INT), Default(None) */
  case class TesterMetaDataRow(id: Int, name: Option[String] = None, content: Option[String] = None, testerMetaDataTypeId: Option[Int] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp, testerDeviceId: Option[Int] = None)
  /** GetResult implicit for fetching TesterMetaDataRow objects using plain SQL queries */
  implicit def GetResultTesterMetaDataRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Int]], e3: GR[java.sql.Timestamp]): GR[TesterMetaDataRow] = GR{
    prs => import prs._
    TesterMetaDataRow.tupled((<<[Int], <<?[String], <<?[String], <<?[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<?[Int]))
  }
  /** Table description of table tester_meta_data. Objects of this class serve as prototypes for rows in queries. */
  class TesterMetaData(_tableTag: Tag) extends Table[TesterMetaDataRow](_tableTag, "tester_meta_data") {
    def * = (id, name, content, testerMetaDataTypeId, createdAt, updatedAt, testerDeviceId) <> (TesterMetaDataRow.tupled, TesterMetaDataRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), name, content, testerMetaDataTypeId, Rep.Some(createdAt), Rep.Some(updatedAt), testerDeviceId).shaped.<>({r=>import r._; _1.map(_=> TesterMetaDataRow.tupled((_1.get, _2, _3, _4, _5.get, _6.get, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(255,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(255,varying=true), O.Default(None))
    /** Database column content SqlType(VARCHAR), Length(255,true), Default(None) */
    val content: Rep[Option[String]] = column[Option[String]]("content", O.Length(255,varying=true), O.Default(None))
    /** Database column tester_meta_data_type_id SqlType(INT), Default(None) */
    val testerMetaDataTypeId: Rep[Option[Int]] = column[Option[Int]]("tester_meta_data_type_id", O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
    /** Database column tester_device_id SqlType(INT), Default(None) */
    val testerDeviceId: Rep[Option[Int]] = column[Option[Int]]("tester_device_id", O.Default(None))

    /** Index over (testerDeviceId) (database name index_tester_meta_data_on_app_id_and_tester_device_id) */
    val index1 = index("index_tester_meta_data_on_app_id_and_tester_device_id", testerDeviceId)
    /** Index over (name) (database name index_tester_meta_data_on_campaign_id_and_name) */
    val index2 = index("index_tester_meta_data_on_campaign_id_and_name", name)
  }
  /** Collection-like TableQuery object for table TesterMetaData */
  lazy val TesterMetaData = new TableQuery(tag => new TesterMetaData(tag))

  /** Entity class storing rows of table TesterMetaDataTypes
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param description Database column description SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME) */
  case class TesterMetaDataTypesRow(id: Int, name: Option[String] = None, description: Option[String] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching TesterMetaDataTypesRow objects using plain SQL queries */
  implicit def GetResultTesterMetaDataTypesRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[java.sql.Timestamp]): GR[TesterMetaDataTypesRow] = GR{
    prs => import prs._
    TesterMetaDataTypesRow.tupled((<<[Int], <<?[String], <<?[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table tester_meta_data_types. Objects of this class serve as prototypes for rows in queries. */
  class TesterMetaDataTypes(_tableTag: Tag) extends Table[TesterMetaDataTypesRow](_tableTag, "tester_meta_data_types") {
    def * = (id, name, description, createdAt, updatedAt) <> (TesterMetaDataTypesRow.tupled, TesterMetaDataTypesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), name, description, Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> TesterMetaDataTypesRow.tupled((_1.get, _2, _3, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(255,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(255,varying=true), O.Default(None))
    /** Database column description SqlType(VARCHAR), Length(255,true), Default(None) */
    val description: Rep[Option[String]] = column[Option[String]]("description", O.Length(255,varying=true), O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
  }
  /** Collection-like TableQuery object for table TesterMetaDataTypes */
  lazy val TesterMetaDataTypes = new TableQuery(tag => new TesterMetaDataTypes(tag))

  /** Entity class storing rows of table TesterSessions
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME)
   *  @param localTime Database column local_time SqlType(DATETIME), Default(None)
   *  @param remoteIp Database column remote_ip SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param appViewActivity Database column app_view_activity SqlType(MEDIUMBLOB), Default(None)
   *  @param isAnalyzed Database column is_analyzed SqlType(BIT), Default(None)
   *  @param appVersionName Database column app_version_name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param appVersionCode Database column app_version_code SqlType(INT), Default(None)
   *  @param endAt Database column end_at SqlType(DATETIME), Default(None)
   *  @param localTimeTz Database column local_time_TZ SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param deletionTime Database column deletion_time SqlType(DATETIME), Default(None)
   *  @param appId Database column app_id SqlType(INT), Default(None)
   *  @param testerDeviceId Database column tester_device_id SqlType(INT), Default(None)
   *  @param versionId Database column version_id SqlType(INT), Default(None)
   *  @param viewFlow Database column view_flow SqlType(MEDIUMBLOB), Default(None)
   *  @param crashed Database column crashed SqlType(BIT), Default(None)
   *  @param uniqueId Database column unique_id SqlType(TINYBLOB), Default(None)
   *  @param sdkVersion Database column sdk_version SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param sdkVersionCode Database column sdk_version_code SqlType(INT), Default(Some(0)) */
  case class TesterSessionsRow(id: Int, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp, localTime: Option[java.sql.Timestamp] = None, remoteIp: Option[String] = None, appViewActivity: Option[java.sql.Blob] = None, isAnalyzed: Option[Boolean] = None, appVersionName: Option[String] = None, appVersionCode: Option[Int] = None, endAt: Option[java.sql.Timestamp] = None, localTimeTz: Option[String] = None, deletionTime: Option[java.sql.Timestamp] = None, appId: Option[Int] = None, testerDeviceId: Option[Int] = None, versionId: Option[Int] = None, viewFlow: Option[java.sql.Blob] = None, crashed: Option[Boolean] = None, uniqueId: Option[java.sql.Blob] = None, sdkVersion: Option[String] = None, sdkVersionCode: Option[Int] = Some(0))
  /** GetResult implicit for fetching TesterSessionsRow objects using plain SQL queries */
  implicit def GetResultTesterSessionsRow(implicit e0: GR[Int], e1: GR[java.sql.Timestamp], e2: GR[Option[java.sql.Timestamp]], e3: GR[Option[String]], e4: GR[Option[java.sql.Blob]], e5: GR[Option[Boolean]], e6: GR[Option[Int]]): GR[TesterSessionsRow] = GR{
    prs => import prs._
    TesterSessionsRow.tupled((<<[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<?[java.sql.Timestamp], <<?[String], <<?[java.sql.Blob], <<?[Boolean], <<?[String], <<?[Int], <<?[java.sql.Timestamp], <<?[String], <<?[java.sql.Timestamp], <<?[Int], <<?[Int], <<?[Int], <<?[java.sql.Blob], <<?[Boolean], <<?[java.sql.Blob], <<?[String], <<?[Int]))
  }
  /** Table description of table tester_sessions. Objects of this class serve as prototypes for rows in queries. */
  class TesterSessions(_tableTag: Tag) extends Table[TesterSessionsRow](_tableTag, "tester_sessions") {
    def * = (id, createdAt, updatedAt, localTime, remoteIp, appViewActivity, isAnalyzed, appVersionName, appVersionCode, endAt, localTimeTz, deletionTime, appId, testerDeviceId, versionId, viewFlow, crashed, uniqueId, sdkVersion, sdkVersionCode) <> (TesterSessionsRow.tupled, TesterSessionsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(createdAt), Rep.Some(updatedAt), localTime, remoteIp, appViewActivity, isAnalyzed, appVersionName, appVersionCode, endAt, localTimeTz, deletionTime, appId, testerDeviceId, versionId, viewFlow, crashed, uniqueId, sdkVersion, sdkVersionCode).shaped.<>({r=>import r._; _1.map(_=> TesterSessionsRow.tupled((_1.get, _2.get, _3.get, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16, _17, _18, _19, _20)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
    /** Database column local_time SqlType(DATETIME), Default(None) */
    val localTime: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("local_time", O.Default(None))
    /** Database column remote_ip SqlType(VARCHAR), Length(255,true), Default(None) */
    val remoteIp: Rep[Option[String]] = column[Option[String]]("remote_ip", O.Length(255,varying=true), O.Default(None))
    /** Database column app_view_activity SqlType(MEDIUMBLOB), Default(None) */
    val appViewActivity: Rep[Option[java.sql.Blob]] = column[Option[java.sql.Blob]]("app_view_activity", O.Default(None))
    /** Database column is_analyzed SqlType(BIT), Default(None) */
    val isAnalyzed: Rep[Option[Boolean]] = column[Option[Boolean]]("is_analyzed", O.Default(None))
    /** Database column app_version_name SqlType(VARCHAR), Length(255,true), Default(None) */
    val appVersionName: Rep[Option[String]] = column[Option[String]]("app_version_name", O.Length(255,varying=true), O.Default(None))
    /** Database column app_version_code SqlType(INT), Default(None) */
    val appVersionCode: Rep[Option[Int]] = column[Option[Int]]("app_version_code", O.Default(None))
    /** Database column end_at SqlType(DATETIME), Default(None) */
    val endAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("end_at", O.Default(None))
    /** Database column local_time_TZ SqlType(VARCHAR), Length(255,true), Default(None) */
    val localTimeTz: Rep[Option[String]] = column[Option[String]]("local_time_TZ", O.Length(255,varying=true), O.Default(None))
    /** Database column deletion_time SqlType(DATETIME), Default(None) */
    val deletionTime: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("deletion_time", O.Default(None))
    /** Database column app_id SqlType(INT), Default(None) */
    val appId: Rep[Option[Int]] = column[Option[Int]]("app_id", O.Default(None))
    /** Database column tester_device_id SqlType(INT), Default(None) */
    val testerDeviceId: Rep[Option[Int]] = column[Option[Int]]("tester_device_id", O.Default(None))
    /** Database column version_id SqlType(INT), Default(None) */
    val versionId: Rep[Option[Int]] = column[Option[Int]]("version_id", O.Default(None))
    /** Database column view_flow SqlType(MEDIUMBLOB), Default(None) */
    val viewFlow: Rep[Option[java.sql.Blob]] = column[Option[java.sql.Blob]]("view_flow", O.Default(None))
    /** Database column crashed SqlType(BIT), Default(None) */
    val crashed: Rep[Option[Boolean]] = column[Option[Boolean]]("crashed", O.Default(None))
    /** Database column unique_id SqlType(TINYBLOB), Default(None) */
    val uniqueId: Rep[Option[java.sql.Blob]] = column[Option[java.sql.Blob]]("unique_id", O.Default(None))
    /** Database column sdk_version SqlType(VARCHAR), Length(255,true), Default(None) */
    val sdkVersion: Rep[Option[String]] = column[Option[String]]("sdk_version", O.Length(255,varying=true), O.Default(None))
    /** Database column sdk_version_code SqlType(INT), Default(Some(0)) */
    val sdkVersionCode: Rep[Option[Int]] = column[Option[Int]]("sdk_version_code", O.Default(Some(0)))

    /** Index over (appId,crashed) (database name app_id_crashed_index) */
    val index1 = index("app_id_crashed_index", (appId, crashed))
    /** Index over (appId,createdAt,crashed) (database name app_id_date_crashed_index) */
    val index2 = index("app_id_date_crashed_index", (appId, createdAt, crashed))
    /** Index over (appId) (database name index_tester_sessions_on_app_id) */
    val index3 = index("index_tester_sessions_on_app_id", appId)
    /** Index over (appId,createdAt) (database name index_tester_sessions_on_app_id_and_created_at) */
    val index4 = index("index_tester_sessions_on_app_id_and_created_at", (appId, createdAt))
    /** Index over (sdkVersionCode) (database name index_tester_sessions_on_sdk_version_code) */
    val index5 = index("index_tester_sessions_on_sdk_version_code", sdkVersionCode)
    /** Index over (testerDeviceId) (database name index_tester_sessions_on_tester_device_id) */
    val index6 = index("index_tester_sessions_on_tester_device_id", testerDeviceId)
    /** Index over (uniqueId) (database name index_tester_sessions_on_unique_id) */
    val index7 = index("index_tester_sessions_on_unique_id", uniqueId)
  }
  /** Collection-like TableQuery object for table TesterSessions */
  lazy val TesterSessions = new TableQuery(tag => new TesterSessions(tag))

  /** Row type of table Users */
  type UsersRow = HCons[Int,HCons[String,HCons[String,HCons[Option[String],HCons[Option[java.sql.Timestamp],HCons[Option[java.sql.Timestamp],HCons[Option[Int],HCons[Option[java.sql.Timestamp],HCons[Option[java.sql.Timestamp],HCons[Option[String],HCons[Option[String],HCons[Option[Int],HCons[java.sql.Timestamp,HCons[java.sql.Timestamp,HCons[Option[String],HCons[Boolean,HCons[Option[String],HCons[Option[java.sql.Timestamp],HCons[Option[java.sql.Timestamp],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HNil]]]]]]]]]]]]]]]]]]]]]]]]]
  /** Constructor for UsersRow providing default values if available in the database schema. */
  def UsersRow(id: Int, email: String = "", encryptedPassword: String = "", resetPasswordToken: Option[String] = None, resetPasswordSentAt: Option[java.sql.Timestamp] = None, rememberCreatedAt: Option[java.sql.Timestamp] = None, signInCount: Option[Int] = Some(0), currentSignInAt: Option[java.sql.Timestamp] = None, lastSignInAt: Option[java.sql.Timestamp] = None, currentSignInIp: Option[String] = None, lastSignInIp: Option[String] = None, accountId: Option[Int] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp, name: Option[String] = None, isSkUser: Boolean = false, confirmationToken: Option[String] = None, confirmedAt: Option[java.sql.Timestamp] = None, confirmationSentAt: Option[java.sql.Timestamp] = None, unconfirmedEmail: Option[String] = None, oldEmail: Option[String] = None, locale: Option[String] = None, phone: Option[String] = None, countryCode: Option[String] = None, userType: Option[String] = None): UsersRow = {
    id :: email :: encryptedPassword :: resetPasswordToken :: resetPasswordSentAt :: rememberCreatedAt :: signInCount :: currentSignInAt :: lastSignInAt :: currentSignInIp :: lastSignInIp :: accountId :: createdAt :: updatedAt :: name :: isSkUser :: confirmationToken :: confirmedAt :: confirmationSentAt :: unconfirmedEmail :: oldEmail :: locale :: phone :: countryCode :: userType :: HNil
  }
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]], e3: GR[Option[java.sql.Timestamp]], e4: GR[Option[Int]], e5: GR[java.sql.Timestamp], e6: GR[Boolean]): GR[UsersRow] = GR{
    prs => import prs._
    <<[Int] :: <<[String] :: <<[String] :: <<?[String] :: <<?[java.sql.Timestamp] :: <<?[java.sql.Timestamp] :: <<?[Int] :: <<?[java.sql.Timestamp] :: <<?[java.sql.Timestamp] :: <<?[String] :: <<?[String] :: <<?[Int] :: <<[java.sql.Timestamp] :: <<[java.sql.Timestamp] :: <<?[String] :: <<[Boolean] :: <<?[String] :: <<?[java.sql.Timestamp] :: <<?[java.sql.Timestamp] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: HNil
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends Table[UsersRow](_tableTag, "users") {
    def * = id :: email :: encryptedPassword :: resetPasswordToken :: resetPasswordSentAt :: rememberCreatedAt :: signInCount :: currentSignInAt :: lastSignInAt :: currentSignInIp :: lastSignInIp :: accountId :: createdAt :: updatedAt :: name :: isSkUser :: confirmationToken :: confirmedAt :: confirmationSentAt :: unconfirmedEmail :: oldEmail :: locale :: phone :: countryCode :: userType :: HNil

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column email SqlType(VARCHAR), Length(255,true), Default() */
    val email: Rep[String] = column[String]("email", O.Length(255,varying=true), O.Default(""))
    /** Database column encrypted_password SqlType(VARCHAR), Length(255,true), Default() */
    val encryptedPassword: Rep[String] = column[String]("encrypted_password", O.Length(255,varying=true), O.Default(""))
    /** Database column reset_password_token SqlType(VARCHAR), Length(255,true), Default(None) */
    val resetPasswordToken: Rep[Option[String]] = column[Option[String]]("reset_password_token", O.Length(255,varying=true), O.Default(None))
    /** Database column reset_password_sent_at SqlType(DATETIME), Default(None) */
    val resetPasswordSentAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("reset_password_sent_at", O.Default(None))
    /** Database column remember_created_at SqlType(DATETIME), Default(None) */
    val rememberCreatedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("remember_created_at", O.Default(None))
    /** Database column sign_in_count SqlType(INT), Default(Some(0)) */
    val signInCount: Rep[Option[Int]] = column[Option[Int]]("sign_in_count", O.Default(Some(0)))
    /** Database column current_sign_in_at SqlType(DATETIME), Default(None) */
    val currentSignInAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("current_sign_in_at", O.Default(None))
    /** Database column last_sign_in_at SqlType(DATETIME), Default(None) */
    val lastSignInAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("last_sign_in_at", O.Default(None))
    /** Database column current_sign_in_ip SqlType(VARCHAR), Length(255,true), Default(None) */
    val currentSignInIp: Rep[Option[String]] = column[Option[String]]("current_sign_in_ip", O.Length(255,varying=true), O.Default(None))
    /** Database column last_sign_in_ip SqlType(VARCHAR), Length(255,true), Default(None) */
    val lastSignInIp: Rep[Option[String]] = column[Option[String]]("last_sign_in_ip", O.Length(255,varying=true), O.Default(None))
    /** Database column account_id SqlType(INT), Default(None) */
    val accountId: Rep[Option[Int]] = column[Option[Int]]("account_id", O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
    /** Database column name SqlType(VARCHAR), Length(255,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(255,varying=true), O.Default(None))
    /** Database column is_sk_user SqlType(BIT), Default(false) */
    val isSkUser: Rep[Boolean] = column[Boolean]("is_sk_user", O.Default(false))
    /** Database column confirmation_token SqlType(VARCHAR), Length(255,true), Default(None) */
    val confirmationToken: Rep[Option[String]] = column[Option[String]]("confirmation_token", O.Length(255,varying=true), O.Default(None))
    /** Database column confirmed_at SqlType(DATETIME), Default(None) */
    val confirmedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("confirmed_at", O.Default(None))
    /** Database column confirmation_sent_at SqlType(DATETIME), Default(None) */
    val confirmationSentAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("confirmation_sent_at", O.Default(None))
    /** Database column unconfirmed_email SqlType(VARCHAR), Length(255,true), Default(None) */
    val unconfirmedEmail: Rep[Option[String]] = column[Option[String]]("unconfirmed_email", O.Length(255,varying=true), O.Default(None))
    /** Database column old_email SqlType(VARCHAR), Length(255,true), Default(None) */
    val oldEmail: Rep[Option[String]] = column[Option[String]]("old_email", O.Length(255,varying=true), O.Default(None))
    /** Database column locale SqlType(VARCHAR), Length(5,true), Default(None) */
    val locale: Rep[Option[String]] = column[Option[String]]("locale", O.Length(5,varying=true), O.Default(None))
    /** Database column phone SqlType(VARCHAR), Length(255,true), Default(None) */
    val phone: Rep[Option[String]] = column[Option[String]]("phone", O.Length(255,varying=true), O.Default(None))
    /** Database column country_code SqlType(VARCHAR), Length(255,true), Default(None) */
    val countryCode: Rep[Option[String]] = column[Option[String]]("country_code", O.Length(255,varying=true), O.Default(None))
    /** Database column user_type SqlType(VARCHAR), Length(255,true), Default(None) */
    val userType: Rep[Option[String]] = column[Option[String]]("user_type", O.Length(255,varying=true), O.Default(None))

    /** Uniqueness Index over (confirmationToken) (database name index_users_on_confirmation_token) */
    val index1 = index("index_users_on_confirmation_token", confirmationToken :: HNil, unique=true)
    /** Uniqueness Index over (email) (database name index_users_on_email) */
    val index2 = index("index_users_on_email", email :: HNil, unique=true)
    /** Uniqueness Index over (resetPasswordToken) (database name index_users_on_reset_password_token) */
    val index3 = index("index_users_on_reset_password_token", resetPasswordToken :: HNil, unique=true)
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))

  /** Entity class storing rows of table UsersComments
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param userId Database column user_id SqlType(INT), Default(None)
   *  @param writeUserId Database column write_user_id SqlType(INT), Default(None)
   *  @param content Database column content SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME) */
  case class UsersCommentsRow(id: Int, userId: Option[Int] = None, writeUserId: Option[Int] = None, content: Option[String] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching UsersCommentsRow objects using plain SQL queries */
  implicit def GetResultUsersCommentsRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[String]], e3: GR[java.sql.Timestamp]): GR[UsersCommentsRow] = GR{
    prs => import prs._
    UsersCommentsRow.tupled((<<[Int], <<?[Int], <<?[Int], <<?[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table users_comments. Objects of this class serve as prototypes for rows in queries. */
  class UsersComments(_tableTag: Tag) extends Table[UsersCommentsRow](_tableTag, "users_comments") {
    def * = (id, userId, writeUserId, content, createdAt, updatedAt) <> (UsersCommentsRow.tupled, UsersCommentsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), userId, writeUserId, content, Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> UsersCommentsRow.tupled((_1.get, _2, _3, _4, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column user_id SqlType(INT), Default(None) */
    val userId: Rep[Option[Int]] = column[Option[Int]]("user_id", O.Default(None))
    /** Database column write_user_id SqlType(INT), Default(None) */
    val writeUserId: Rep[Option[Int]] = column[Option[Int]]("write_user_id", O.Default(None))
    /** Database column content SqlType(VARCHAR), Length(255,true), Default(None) */
    val content: Rep[Option[String]] = column[Option[String]]("content", O.Length(255,varying=true), O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (userId) (database name index_users_comments_on_user_id) */
    val index1 = index("index_users_comments_on_user_id", userId)
    /** Index over (writeUserId) (database name index_users_comments_on_write_user_id) */
    val index2 = index("index_users_comments_on_write_user_id", writeUserId)
  }
  /** Collection-like TableQuery object for table UsersComments */
  lazy val UsersComments = new TableQuery(tag => new UsersComments(tag))

  /** Entity class storing rows of table Versions
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param appId Database column app_id SqlType(INT), Default(None)
   *  @param name Database column name SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param code Database column code SqlType(INT), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME) */
  case class VersionsRow(id: Int, appId: Option[Int] = None, name: Option[String] = None, code: Option[Int] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching VersionsRow objects using plain SQL queries */
  implicit def GetResultVersionsRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[String]], e3: GR[java.sql.Timestamp]): GR[VersionsRow] = GR{
    prs => import prs._
    VersionsRow.tupled((<<[Int], <<?[Int], <<?[String], <<?[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table versions. Objects of this class serve as prototypes for rows in queries. */
  class Versions(_tableTag: Tag) extends Table[VersionsRow](_tableTag, "versions") {
    def * = (id, appId, name, code, createdAt, updatedAt) <> (VersionsRow.tupled, VersionsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), appId, name, code, Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> VersionsRow.tupled((_1.get, _2, _3, _4, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column app_id SqlType(INT), Default(None) */
    val appId: Rep[Option[Int]] = column[Option[Int]]("app_id", O.Default(None))
    /** Database column name SqlType(VARCHAR), Length(255,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(255,varying=true), O.Default(None))
    /** Database column code SqlType(INT), Default(None) */
    val code: Rep[Option[Int]] = column[Option[Int]]("code", O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Uniqueness Index over (appId,name) (database name index_versions_on_app_id_and_name) */
    val index1 = index("index_versions_on_app_id_and_name", (appId, name), unique=true)
  }
  /** Collection-like TableQuery object for table Versions */
  lazy val Versions = new TableQuery(tag => new Versions(tag))

  /** Entity class storing rows of table ViewFlows
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param versionId Database column version_id SqlType(INT), Default(None)
   *  @param from Database column from SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param to Database column to SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param count Database column count SqlType(INT), Default(None)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME) */
  case class ViewFlowsRow(id: Int, versionId: Option[Int] = None, from: Option[String] = None, to: Option[String] = None, count: Option[Int] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching ViewFlowsRow objects using plain SQL queries */
  implicit def GetResultViewFlowsRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[String]], e3: GR[java.sql.Timestamp]): GR[ViewFlowsRow] = GR{
    prs => import prs._
    ViewFlowsRow.tupled((<<[Int], <<?[Int], <<?[String], <<?[String], <<?[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table view_flows. Objects of this class serve as prototypes for rows in queries. */
  class ViewFlows(_tableTag: Tag) extends Table[ViewFlowsRow](_tableTag, "view_flows") {
    def * = (id, versionId, from, to, count, createdAt, updatedAt) <> (ViewFlowsRow.tupled, ViewFlowsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), versionId, from, to, count, Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> ViewFlowsRow.tupled((_1.get, _2, _3, _4, _5, _6.get, _7.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column version_id SqlType(INT), Default(None) */
    val versionId: Rep[Option[Int]] = column[Option[Int]]("version_id", O.Default(None))
    /** Database column from SqlType(VARCHAR), Length(255,true), Default(None) */
    val from: Rep[Option[String]] = column[Option[String]]("from", O.Length(255,varying=true), O.Default(None))
    /** Database column to SqlType(VARCHAR), Length(255,true), Default(None) */
    val to: Rep[Option[String]] = column[Option[String]]("to", O.Length(255,varying=true), O.Default(None))
    /** Database column count SqlType(INT), Default(None) */
    val count: Rep[Option[Int]] = column[Option[Int]]("count", O.Default(None))
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")

    /** Index over (versionId) (database name index_view_flows_on_version_id) */
    val index1 = index("index_view_flows_on_version_id", versionId)
    /** Uniqueness Index over (versionId,from,to) (database name index_view_flows_on_version_id_and_from_and_to) */
    val index2 = index("index_view_flows_on_version_id_and_from_and_to", (versionId, from, to), unique=true)
  }
  /** Collection-like TableQuery object for table ViewFlows */
  lazy val ViewFlows = new TableQuery(tag => new ViewFlows(tag))

  /** Entity class storing rows of table WeeklySessionStats
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param appId Database column app_id SqlType(INT)
   *  @param date Database column date SqlType(DATETIME)
   *  @param uniqueUserCount Database column unique_user_count SqlType(INT)
   *  @param createdAt Database column created_at SqlType(DATETIME)
   *  @param updatedAt Database column updated_at SqlType(DATETIME)
   *  @param avgPageView Database column avg_page_view SqlType(INT), Default(0)
   *  @param avgScreenCount Database column avg_screen_count SqlType(INT), Default(0)
   *  @param avgDuration Database column avg_duration SqlType(INT), Default(0) */
  case class WeeklySessionStatsRow(id: Int, appId: Int, date: java.sql.Timestamp, uniqueUserCount: Int, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp, avgPageView: Int = 0, avgScreenCount: Int = 0, avgDuration: Int = 0)
  /** GetResult implicit for fetching WeeklySessionStatsRow objects using plain SQL queries */
  implicit def GetResultWeeklySessionStatsRow(implicit e0: GR[Int], e1: GR[java.sql.Timestamp]): GR[WeeklySessionStatsRow] = GR{
    prs => import prs._
    WeeklySessionStatsRow.tupled((<<[Int], <<[Int], <<[java.sql.Timestamp], <<[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<[Int], <<[Int], <<[Int]))
  }
  /** Table description of table weekly_session_stats. Objects of this class serve as prototypes for rows in queries. */
  class WeeklySessionStats(_tableTag: Tag) extends Table[WeeklySessionStatsRow](_tableTag, "weekly_session_stats") {
    def * = (id, appId, date, uniqueUserCount, createdAt, updatedAt, avgPageView, avgScreenCount, avgDuration) <> (WeeklySessionStatsRow.tupled, WeeklySessionStatsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(appId), Rep.Some(date), Rep.Some(uniqueUserCount), Rep.Some(createdAt), Rep.Some(updatedAt), Rep.Some(avgPageView), Rep.Some(avgScreenCount), Rep.Some(avgDuration)).shaped.<>({r=>import r._; _1.map(_=> WeeklySessionStatsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column app_id SqlType(INT) */
    val appId: Rep[Int] = column[Int]("app_id")
    /** Database column date SqlType(DATETIME) */
    val date: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("date")
    /** Database column unique_user_count SqlType(INT) */
    val uniqueUserCount: Rep[Int] = column[Int]("unique_user_count")
    /** Database column created_at SqlType(DATETIME) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
    /** Database column avg_page_view SqlType(INT), Default(0) */
    val avgPageView: Rep[Int] = column[Int]("avg_page_view", O.Default(0))
    /** Database column avg_screen_count SqlType(INT), Default(0) */
    val avgScreenCount: Rep[Int] = column[Int]("avg_screen_count", O.Default(0))
    /** Database column avg_duration SqlType(INT), Default(0) */
    val avgDuration: Rep[Int] = column[Int]("avg_duration", O.Default(0))

    /** Uniqueness Index over (appId,date) (database name index_weekly_session_stats_on_app_id_and_date) */
    val index1 = index("index_weekly_session_stats_on_app_id_and_date", (appId, date), unique=true)
  }
  /** Collection-like TableQuery object for table WeeklySessionStats */
  lazy val WeeklySessionStats = new TableQuery(tag => new WeeklySessionStats(tag))
}
