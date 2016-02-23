import java.net.InetAddress

import helper.ConfigHelper
import io.userhabit.library.db.ElasticUtils
import io.userhabit.library.orm.Mapper
import io.userhabit.library.v2.model.analysis.AnalyzedSession
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.elasticsearch.common.unit.TimeValue

/**
  * Created by DynamicScope on 2016. 2. 22..
  */
object EsReIndexer {
  ConfigHelper.load()

  val settings = Settings.settingsBuilder
    .put("cluster.name", "Avengers")
    .put("client.transport.sniff", true).build

  val es_src = TransportClient.builder
    .settings(settings).build
    .addTransportAddresses(new InetSocketTransportAddress(InetAddress.getByName("172.31.9.135"), 9300))

  val es_dst = new ElasticUtils
  es_dst.connect(new InetSocketTransportAddress(InetAddress.getByName(ConfigHelper.elasticHost), 9300))

  val mapper = new Mapper

  def main (args: Array[String]): Unit = {

    reIndexAnalyzedSessions("uh-as-*-*")
    reIndexSessions("uh-s-*-*")

    if (es_dst != null) es_dst.close()
    if (es_src != null) es_src.close()
  }

  private def getListOfIndices(indexPattern: String): Array[String] = {
    try {
      val resp = es_src.admin().indices().prepareGetIndex().addIndices(indexPattern).get()
      if (resp != null) {
        resp.getIndices
      } else {
        new Array[String](0)
      }
    } catch {
      case e: Exception =>
        new Array[String](0)
    }
  }

  def reIndexAnalyzedSessions(indexPattern: String): Unit = {
    println("Re-indexing analyzed sessions...")
    val indices = getListOfIndices(indexPattern)
    if (indices != null) {
      indices.foreach(index => {
        println(s"Computing $index")
        var scrollResp = es_src.prepareSearch(index)
          .setScroll(new TimeValue(60000))
          .setSize(100)
          .get()

        while (scrollResp.getHits.getHits.nonEmpty) {
          scrollResp.getHits.getHits.foreach(hit => {
            val as = mapper.readValue(hit.getSourceAsString, classOf[AnalyzedSession])
            es_dst.bulkIndexAfterBatch(as, 540)
          })
          scrollResp = es_src.prepareSearchScroll(scrollResp.getScrollId)
            .setScroll(new TimeValue(60000))
            .get()
        }
      })
    }
  }

  def reIndexSessions(indexPattern: String): Unit = {
    println("Re-indexing non-analyzed sessions...")
    val indices = getListOfIndices(indexPattern)
    if (indices != null) {
      indices.foreach(index => {
        println(s"Computing $index")
        var scrollResp = es_src.prepareSearch(index)
          .setScroll(new TimeValue(60000))
          .setSize(100)
          .get()

        while (scrollResp.getHits.getHits.nonEmpty) {
          scrollResp.getHits.getHits.foreach(hit => {
            val s = mapper.readValue(hit.getSourceAsString, classOf[AnalyzedSession])
            es_dst.bulkIndexBeforeBatch(s)
          })
          scrollResp = es_src.prepareSearchScroll(scrollResp.getScrollId)
            .setScroll(new TimeValue(60000))
            .get()
        }
      })
    }
  }
}
