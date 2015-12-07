name := "elasticsearch-indexer"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies += "com.amazonaws" % "aws-java-sdk-s3" % "1.10.34"
libraryDependencies += "org.elasticsearch" % "elasticsearch" % "2.0.0"
libraryDependencies += "com.couchbase.client" % "java-client" % "2.1.5"
libraryDependencies += "org.yaml" % "snakeyaml" % "1.16"
