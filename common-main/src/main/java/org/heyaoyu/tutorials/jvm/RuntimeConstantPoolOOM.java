package org.heyaoyu.tutorials.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heyaoyu on 2017/1/30.
 *
 * Java 1.6,1.7 String store policy changed.
 */
public class RuntimeConstantPoolOOM {

  public static void main(String[] args) {
    List<String> list = new ArrayList<String>();
    int i = 0;
    while (true) {
      list.add(String.valueOf(i++).intern());
    }
  }
}
