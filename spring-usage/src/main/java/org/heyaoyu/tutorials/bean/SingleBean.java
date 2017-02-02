package org.heyaoyu.tutorials.bean;

import org.springframework.stereotype.Component;

/**
 * Created by heyaoyu on 2017/2/2.
 */
@Component
public class SingleBean {

  private String name = "";

  public SingleBean() {
  }

  public SingleBean(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
