package org.heyaoyu.tutorials.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by heyaoyu on 2017/1/30.
 */
public class SLF4JMain {
  public static void main(String[] args) {
    Logger logger = LoggerFactory.getLogger(SLF4JMain.class);
    String world = "world";
    logger.info("hello {}", world);
  }
}
