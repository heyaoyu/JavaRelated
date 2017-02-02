package org.heyaoyu.tutorials;

import org.heyaoyu.tutorials.bean.SingleBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

  public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
    SingleBean singleBean = (SingleBean) context.getBean("singleBean");
    System.out.println(singleBean.getName());
  }
}
