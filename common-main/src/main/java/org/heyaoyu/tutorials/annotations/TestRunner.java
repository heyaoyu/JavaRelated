package org.heyaoyu.tutorials.annotations;

/**
 * Created by heyaoyu on 2017/1/30.
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by heyaoyu on 2017/1/7. 反射method.invoke()方法第一个参数是null即为static方法
 */
public class TestRunner {

  public static void main(String[] args) {
    run(CertainTest.class);
  }

  private static void run(Class<?> testClass) {
    System.out.println("running test for " + testClass.getName());
    Method[] methods = testClass.getMethods();
    int tests = 0, passes = 0;
    for (Method method : methods) {
      if (method.isAnnotationPresent(Test.class)) {
        System.out.println("running method " + method.getName());
        tests += 1;
        try {
          method.invoke(null);// static
          passes += 1;
        } catch (IllegalAccessException e) {
          System.out.println(method.getName() + " fails for " + e.getMessage());
        } catch (InvocationTargetException e) {
          System.out.println(method.getName() + " fails for " + e.getMessage());
        } catch (Exception ex) {
          System.out.println(method.getName() + " fails for member method.");
        }
      }
    }
    System.out.println("tests:" + tests + " passes:" + passes);
  }

}