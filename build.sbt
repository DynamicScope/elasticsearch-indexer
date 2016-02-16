name := "elasticsearch-indexer"

version := "1.0"

scalaVersion := "2.11.7"

// Amazon S3 provided by Userhabit Library
//libraryDependencies += "com.amazonaws" % "aws-java-sdk-s3" % "1.10.34"

// Elasticsearch
libraryDependencies += "org.elasticsearch" % "elasticsearch" % "2.0.0"

// Couchbase Client
libraryDependencies += "com.couchbase.client" % "java-client" % "2.1.5"

// MySQL - Instead using PostegreSql
//libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.34"
//libraryDependencies += "com.typesafe.slick" %% "slick" % "3.1.1"
//libraryDependencies += "com.typesafe.slick" %% "slick-codegen" % "3.1.1"
//libraryDependencies += "com.typesafe.slick" %% "slick-hikaricp" % "3.1.1"
//libraryDependencies += "com.mchange" % "c3p0" % "0.9.2.1"

// Userhabit Library
libraryDependencies += "io.userhabit.library" % "userhabitlib" % "1.0.0-SNAPSHOT"
credentials += Credentials({
  if (sys.env.isDefinedAt("ANDBUT_PRIVATE_REPO")) {
    new File(sys.env.getOrElse("ANDBUT_PRIVATE_REPO", ""))
  } else {
    Path.userHome / ".ivy2" / ".andbutrepo"
  }})
resolvers += "Sonatype Nexus Repository Manager" at "https://team.andbut.com/repo/content/repositories/snapshots/"

// Hibernate integration with PostgreSql
libraryDependencies += ("org.hibernate" % "hibernate-c3p0" % "5.0.6.Final")
libraryDependencies += ("org.postgresql" % "postgresql" % "9.4-1200-jdbc41")

// Config
libraryDependencies += "com.typesafe" % "config" % "1.3.0"