package org.hyy.dubbo.services.impl;

import org.hyy.dubbo.services.ISayHello;

/**
 * Created by heyaoyu on 2018/12/24.
 */
public class SayHello implements ISayHello {

  @Override
  public String sayHello(String name) {
    return "Hello, " + name;
  }
}
