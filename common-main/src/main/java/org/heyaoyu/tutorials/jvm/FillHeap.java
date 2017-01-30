package org.heyaoyu.tutorials.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heyaoyu on 2017/1/30.
 */
public class FillHeap {

  static class OOMObject {

  }

  public static void main(String[] args) throws InterruptedException {
    fillHeap(1000);
  }

  public static void fillHeap(int num) throws InterruptedException {
    List<OOMObject> list = new ArrayList<OOMObject>();
    for (int i = 0; i < num; i++) {
      Thread.sleep(50);
      list.add(new OOMObject());
    }
    System.gc();
  }

}