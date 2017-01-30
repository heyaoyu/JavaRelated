package org.heyaoyu.tutorials;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heyaoyu on 2017/1/30.
 *
 * Will throw ConcurrentModificationException
 *
 */
public class WrongIteratorRemove {

  public static void main(String[] args) {
    List<String> list = new ArrayList<String>() {{
      this.add("a");
      this.add("b");
      this.add("c");
    }};
    for (String str : list) {
      list.remove(str);
    }
  }

}
