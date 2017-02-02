package org.heyaoyu.tutorials.bean;

import org.springframework.stereotype.Component;

/**
 * Created by heyaoyu on 2017/2/2.
 */
@Component("paraBean")
public class ParameterBean {

  public void say(String sth) {
    System.out.println("say:" + sth);
  }

}
