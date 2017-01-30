package org.heyaoyu.tutorials.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heyaoyu on 2017/1/30.
 */

public class HeapOOM {

  static class OOMObject {

  }

  public static void main(String[] args) {
    List<OOMObject> list = new ArrayList<OOMObject>();
    while (true) {
      list.add(new OOMObject());
    }
  }

}
