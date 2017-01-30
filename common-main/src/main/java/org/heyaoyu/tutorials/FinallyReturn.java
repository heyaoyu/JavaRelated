package org.heyaoyu.tutorials;

/**
 * Created by heyaoyu on 2017/1/30.
 */
public class FinallyReturn {

  public static void main(String[] args) {
    int i = exec();
    System.out.println(i);
  }

  private static int exec() {
    try {
      System.out.println("try");
      throw new Exception("exception");
    } catch (Exception ex) {
      System.out.println("catch exception");
      return 0;
    } finally {
      System.out.println("finally");
      return 1;
    }
  }

}
