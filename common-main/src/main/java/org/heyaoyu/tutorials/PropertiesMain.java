package org.heyaoyu.tutorials;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by heyaoyu on 2017/7/19.
 */
public class PropertiesMain {

  public static void main(String[] args) throws IOException {
    Properties props = new Properties();
    props.load(PropertiesMain.class.getClassLoader().getResourceAsStream("profile.properties"));
    props.load(PropertiesMain.class.getClassLoader().getResourceAsStream("log4j.properties"));
    System.out.println(props.getProperty("dev"));
  }

}
