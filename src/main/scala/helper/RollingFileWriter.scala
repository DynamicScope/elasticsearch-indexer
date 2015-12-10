package helper

import java.io._

/**
  * Created by DynamicScope on 2015. 12. 9..
  */
class RollingFileWriter(fileName: String) {

  var fileSizeLimit: Long = 5368709120L

  private var rollingNumber: Integer = -1
  private var fileSize: Long = 0L

  private var rollingFile = getNewRollingFile
  private var streamWriter = new OutputStreamWriter(new FileOutputStream(rollingFile), "UTF-8")
  private var bufferedWriter = new BufferedWriter(streamWriter)

  def writeLine(line: String): Unit = {
    val lineByteSize = line.getBytes("UTF-8").length

    if (lineByteSize > fileSizeLimit) {
      //println(s"Session data size $lineByteSize bytes exceeds single file size limit $fileSizeLimit bytes")
      return
    }

    val peekFileSize = fileSize + lineByteSize + System.lineSeparator().getBytes("UTF-8").length
    if (peekFileSize > fileSizeLimit) {
      //println(s"fileSize is greater than fileSizeLimit: $fileSize")
      bufferedWriter = getNewBufferedWriter
    }
    //println(s"fileSize: $fileSize")
    fileSize = peekFileSize
    bufferedWriter.write(line)
    bufferedWriter.newLine()
  }

  def close(): Unit = {
    //println("closing...")
    if (bufferedWriter != null) bufferedWriter.close()
    if (streamWriter != null) streamWriter.close()
  }

  private def getNewRollingFile: File = {
    rollingNumber += 1
    new File(s"$fileName-${String.format("%02d", rollingNumber)}")
  }

  private def getNewBufferedWriter: BufferedWriter = {
    this.close()
    fileSize = 0L
    rollingFile = getNewRollingFile
    streamWriter = new OutputStreamWriter(new FileOutputStream(rollingFile), "UTF-8")
    new BufferedWriter(streamWriter)
  }
}
