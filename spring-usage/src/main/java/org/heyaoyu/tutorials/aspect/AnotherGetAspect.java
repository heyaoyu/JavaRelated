package org.heyaoyu.tutorials.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by heyaoyu on 2017/2/2.
 */
@Component
@Aspect
public class AnotherGetAspect {

  @Pointcut("execution(* *.get*(..))")
  public void targetMethod() {
  }

  @Before("targetMethod()")
  public void beforeGet() {
    System.out.println("another before get");
  }

  @AfterReturning("targetMethod()")
  public void afterGet() {
    System.out.println("another after get");
  }

  @Around("targetMethod()")
  public Object aroundGet(ProceedingJoinPoint joinPoint) throws Throwable {
    System.out.println("args:" + joinPoint.getArgs().toString());
    System.out.println("1");
    Object o = joinPoint.proceed();
    System.out.println("2");
    return o;
  }

}
