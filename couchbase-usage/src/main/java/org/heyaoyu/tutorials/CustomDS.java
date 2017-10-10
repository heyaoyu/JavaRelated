package org.heyaoyu.tutorials;

import com.couchbase.client.CouchbaseClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by heyaoyu on 2017/10/10.
 */
public class CustomDS {

  private static Map<String, Long> idTsMap = new HashMap<String, Long>() {{
    this.put("1", 1l);
    this.put("2", 2l);
  }};

  private final static ObjectMapper mapper = new ObjectMapper();

  // docker start cb_server
  // to run this example
  public static void main(String[] args) throws Exception {
    // mapper usage
    String bucket = "default";
    URI uri = URI.create("http://127.0.0.1:8091");
    List<URI> uriList = new ArrayList<URI>() {{
      this.add(uri);
    }};
    CouchbaseClient client = new CouchbaseClient(uriList, bucket, "");
    byte[] ret = mapper.writeValueAsBytes(idTsMap);
    client.set("testKey", 100, ret);
    ret = (byte[]) client.get("testKey");
    Map<?, ?> restore = mapper.readValue(ret, Map.class);
    for (Map.Entry<?, ?> entry : restore.entrySet()) {
      System.out.println(entry.getKey() + ", " + entry.getValue());
    }
    // multiple set expire-->effective
    client.set("testKey2", 5, "exist");
    System.out.println(client.get("testKey2"));
    Thread.sleep(2000);
    client.set("testKey2", 5, "exist2");
    Thread.sleep(4000);
    System.out.println(client.get("testKey2"));
    client.shutdown();
  }

}