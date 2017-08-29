package org.heyaoyu.tutorials;

/**
 * Created by heyaoyu on 2017/8/23.
 */
public class TryFinally {

  public static void main(String[] args) {
    tryFinallyThrowException();
  }

  private static void tryFinallyThrowException() {
    try {
      try {
        throw new RuntimeException("test");
      } finally {

      }
    } finally {

    }
  }
}
