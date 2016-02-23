import java.net.InetAddress

import helper.ConfigHelper
import io.userhabit.library.db.ElasticUtils
import io.userhabit.library.orm.Mapper
import io.userhabit.library.v2.model.analysis.AnalyzedSession
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.elasticsearch.common.unit.TimeValue

/**
  * Created by DynamicScope on 2016. 2. 22..
  */
object EsReIndexer {
  ConfigHelper.load()

  val es = new ElasticUtils
  es.connect(new InetSocketTransportAddress(InetAddress.getByName(ConfigHelper.elasticHost), 9300))
  val mapper = new Mapper

  def main (args: Array[String]): Unit = {

    reIndexAnalyzedSessions("uh-as-*-*")
    reIndexSessions("uh-s-*-*")

    if (es != null) es.close()
  }

  def reIndexAnalyzedSessions(indexPattern: String): Unit = {
    println("Re-indexing analyzed sessions...")
    val indices = es.getListOfIndices(indexPattern)
    if (indices != null) {
      indices.foreach(index => {
        println(s"Computing $index")
        var scrollResp = es.getClient.prepareSearch(index)
          .setScroll(new TimeValue(60000))
          .setSize(100)
          .get()

        while (scrollResp.getHits.getHits.nonEmpty) {
          scrollResp.getHits.getHits.foreach(hit => {
            val as = mapper.readValue(hit.getSourceAsString, classOf[AnalyzedSession])
            es.bulkIndexAfterBatch(as, 540)
          })
          scrollResp = es.getClient.prepareSearchScroll(scrollResp.getScrollId)
            .setScroll(new TimeValue(60000))
            .get()
        }
      })
    }
  }

  def reIndexSessions(indexPattern: String): Unit = {
    println("Re-indexing non-analyzed sessions...")
    val indices = es.getListOfIndices(indexPattern)
    if (indices != null) {
      indices.foreach(index => {
        println(s"Computing $index")
        var scrollResp = es.getClient.prepareSearch(index)
          .setScroll(new TimeValue(60000))
          .setSize(100)
          .get()

        while (scrollResp.getHits.getHits.nonEmpty) {
          scrollResp.getHits.getHits.foreach(hit => {
            val s = mapper.readValue(hit.getSourceAsString, classOf[AnalyzedSession])
            es.bulkIndexBeforeBatch(s)
          })
          scrollResp = es.getClient.prepareSearchScroll(scrollResp.getScrollId)
            .setScroll(new TimeValue(60000))
            .get()
        }
      })
    }
  }
}
