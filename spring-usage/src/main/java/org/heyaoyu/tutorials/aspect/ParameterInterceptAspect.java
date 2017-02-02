package org.heyaoyu.tutorials.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by heyaoyu on 2017/2/2.
 */
@Component
@Aspect
public class ParameterInterceptAspect {

  @Pointcut("execution(* *.say(String)) && args(sth)")
  public void sayMethod(String sth) {
  }

  @Before("sayMethod(sth)")
  public void beforeSay(String sth) {
    System.out.println(sth);
  }
}
