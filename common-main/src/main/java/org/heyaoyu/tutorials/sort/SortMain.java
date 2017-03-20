package org.heyaoyu.tutorials.sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by heyaoyu on 2017/3/20.
 */
public class SortMain {

  public static void main(String[] args) {
    List<Integer> list = Arrays.asList(4, 1, 5, 2, 7, 8);
    printList(list);
    Collections.sort(list, new Comparator<Integer>() {
      @Override
      public int compare(Integer o1, Integer o2) {
        return Integer.compare(o1, o2);
      }
    });
    printList(list);
    Collections.sort(list, new Comparator<Integer>() {
      @Override
      public int compare(Integer o1, Integer o2) {
        return Integer.compare(o2, o1);
      }
    });
    printList(list);
  }

  private static void printList(List<Integer> list) {
    for (Integer ele : list) {
      System.out.print(ele);
    }
    System.out.println();
  }

}
