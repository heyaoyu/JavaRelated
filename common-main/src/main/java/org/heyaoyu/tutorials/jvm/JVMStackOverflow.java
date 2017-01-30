package org.heyaoyu.tutorials.jvm;

/**
 * Created by heyaoyu on 2017/1/30.
 */
public class JVMStackOverflow {

  public int stackLength = 1;

  public void stackOF() {
    stackLength += 1;
    stackOF();
  }

  public static void main(String[] args) throws Throwable {
    JVMStackOverflow sof = new JVMStackOverflow();
    try {
      sof.stackOF();
    } catch (Throwable e) {
      System.out.println("Stack Length:" + sof.stackLength);
      throw e;
    }
  }

}
