package org.heyaoyu.tutorials.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by heyaoyu on 2017/2/5. use log4j additivity=false to customize a logger using different
 * config from root logger;
 */
public class Special {

  private final static Logger logger = LoggerFactory.getLogger(Special.class);

  public static void main(String[] args) {
    logger.info("this is Special...");
  }

}
