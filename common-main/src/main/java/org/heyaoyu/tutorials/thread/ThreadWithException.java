package org.heyaoyu.tutorials.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by heyaoyu on 2017/5/28.
 */
public class ThreadWithException {

  public static void main(String[] args) {
    ExecutorService es = Executors.newSingleThreadExecutor();
    try {
//      es.execute(new RunnableWithRuntimeException());
      es.submit(new RunnableWithRuntimeException()); // swallow
    } catch (Exception ex) {
      System.out.println("custom ex:" + ex);
    }
    System.out.println("done");
    es.shutdown();
  }
}


class RunnableWithRuntimeException implements Runnable {

  @Override
  public void run() {
    int i = Integer.parseInt("");
    System.out.println(i);
  }
}