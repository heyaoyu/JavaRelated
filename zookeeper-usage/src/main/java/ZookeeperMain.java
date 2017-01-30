import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by heyaoyu on 2017/1/30.
 */

public class ZookeeperMain {

  public static CountDownLatch latch = new CountDownLatch(1);

  public static class CertainWatcher implements Watcher {

    @Override
    public void process(WatchedEvent watchedEvent) {
      System.out.println("Received:" + watchedEvent);
      System.out.println("EventState:" + watchedEvent.getState());
      if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
        latch.countDown();
      }
    }
  }

  public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
    ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 2000, new CertainWatcher());
    System.out.println("ZooKeeperState:" + zooKeeper.getState());
    latch.await();
    String path = zooKeeper.create("/test", "test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    System.out.println("created path:" + path);
    Thread.sleep(Integer.MAX_VALUE);
  }
}
