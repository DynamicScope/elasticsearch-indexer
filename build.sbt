name := "elasticsearch-indexer"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies += "com.amazonaws" % "aws-java-sdk-s3" % "1.10.34"
libraryDependencies += "org.elasticsearch" % "elasticsearch" % "2.0.0"
libraryDependencies += "com.couchbase.client" % "java-client" % "2.1.5"
libraryDependencies += "org.yaml" % "snakeyaml" % "1.16"
libraryDependencies += "io.userhabit.library" % "userhabitlib" % "1.0.0-SNAPSHOT" withSources()

credentials += Credentials("Sonatype Nexus Repository Manager", "team.andbut.com", "deployment", "JPw}eb4)7fdR2kgB")

resolvers += "Sonatype Nexus Repository Manager" at "https://team.andbut.com/repo/content/repositories/snapshots/"