package org.heyaoyu.tutorials;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by heyaoyu on 2017/11/8.
 */
public class SortedMapUsage {

  private static int getInt(SortedMap<Integer, Integer> ruleMap, int days) {
    if (ruleMap.containsKey(days)) {
      return ruleMap.get(days);
    } else {
      return ruleMap.get(ruleMap.headMap(days).lastKey());
    }
  }

  public static void main(String[] args) {
    SortedMap<Integer, Integer> map = new TreeMap<Integer, Integer>() {{
      this.put(1, 1);
      this.put(2, 2);
      this.put(5, 3);
    }};
    System.out.println(getInt(map, 1));
    System.out.println(getInt(map, 3));
    System.out.println(getInt(map, 6));
    System.out.println(getInt(map, 100));
//    System.out.println(getInt(map, -1));
  }
}
