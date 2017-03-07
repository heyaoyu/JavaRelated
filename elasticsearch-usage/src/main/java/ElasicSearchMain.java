import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.net.InetAddress;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;

/**
 * Created by heyaoyu on 2017/3/2.
 */
class Entity {

  public Entity(String id, long ts) {
    this.id = id;
    this.ts = ts;
  }

  String id;
  long ts;
}


public class ElasicSearchMain {

  public static void main(String[] args) throws IOException {
    Client client = TransportClient.builder().build()
        .addTransportAddress(
            new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
    // index entity
//    Entity entity = new Entity("id1", System.currentTimeMillis());
//    IndexRequestBuilder indexReq = client.prepareIndex("test", "entity");
//    XContentBuilder doc = jsonBuilder().startObject()
//        .field("id", entity.id)
//        .field("ts", entity.ts)
//        .endObject();
//    IndexResponse indexRes = indexReq.setSource(doc).execute().actionGet();
//    String id = indexRes.getId();
//    System.out.println("id:" + id);
    // search entity
    SearchRequestBuilder searchReq = client.prepareSearch("test").setTypes("entity");
    SearchResponse searchRes = searchReq
//        .setQuery(QueryBuilders.matchQuery("id", "id1"))
        .setQuery(QueryBuilders.rangeQuery("ts").to(System.currentTimeMillis()))
        .addSort("ts", SortOrder.DESC)
        .setFrom(0)
        .setSize(2).execute().actionGet();
    SearchHits hits = searchRes.getHits();
    for (SearchHit hit : hits.getHits()) {
      System.out.println(hit.getSourceAsString());
    }
    // delete entity
//    DeleteRequestBuilder deleteReq = client.prepareDelete("test", "entity", id);
//    DeleteResponse deleteRes = deleteReq.execute().actionGet();
  }

}
