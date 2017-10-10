package org.heyaoyu.tutorials;

import com.couchbase.client.CouchbaseClient;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import net.spy.memcached.internal.OperationFuture;

public class App {

  public static void main(String[] args)
      throws IOException, ExecutionException, InterruptedException {
    URI uri = URI.create("http://10.153.72.243:8091");
    List<URI> uriList = new ArrayList<URI>() {{
      this.add(uri);
    }};
    CouchbaseClient client = new CouchbaseClient(uriList, "mbd-toutiao", "");
    System.out.println(new String((byte[]) client.get("vote:deviceId:aaa:newsId:406808200570")));
    OperationFuture<Map<String, String>> future = client
        .getKeyStats("vote:deviceId:aaa:newsId:406808200570");
    Map<String, String> map = future.get();
    for (Map.Entry<String, String> entry : map.entrySet()) {
      System.out.println("Key:" + entry.getKey());
      System.out.println("Value:" + entry.getValue());
    }
    client.shutdown();
  }

}
