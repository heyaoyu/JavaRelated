package org.heyaoyu.tutorials.bean;

import org.springframework.stereotype.Component;

/**
 * Created by heyaoyu on 2017/2/2.
 */
@Component
public class BeanInitByFactoryMethod {

  private static class Holder {
    static BeanInitByFactoryMethod instance = new BeanInitByFactoryMethod();
  }

  public static BeanInitByFactoryMethod getInstance() {
    return Holder.instance;
  }

  public String getName() {
    return "beanInitByFactoryMethod";
  }

}
