package org.heyaoyu.tutorials;

import com.couchbase.client.CouchbaseClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
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

  public static void main(String[] args) throws IOException {
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
    client.shutdown();
  }

}