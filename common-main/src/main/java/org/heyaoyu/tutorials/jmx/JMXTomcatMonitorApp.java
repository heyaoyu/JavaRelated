package org.heyaoyu.tutorials.jmx;

import java.util.HashMap;
import java.util.Map;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * Created by heyaoyu on 2017/1/30.
 *
 * @description tomcat通过修改catalina.sh设置jmxremote启动参数
 * 之后通过下面api获取*jconsole*能获取的参数值
 */
public class JMXTomcatMonitorApp {

  public static void main(String[] args) throws Exception {
    //tomcat jmxurl
    String jmxURL = "service:jmx:rmi:///jndi/rmi://127.0.0.1:9999/jmxrmi";
    JMXServiceURL serviceURL = new JMXServiceURL(jmxURL);
    Map map = new HashMap();
    JMXConnector connector = JMXConnectorFactory.connect(serviceURL, map);
    MBeanServerConnection mbsc = connector.getMBeanServerConnection();
    ObjectName threadObjName = new ObjectName("Catalina:type=ThreadPool,name=\"http-bio-8080\"");
    String attrName = "maxThreads";
    System.out.println("maxThreads:" + mbsc.getAttribute(threadObjName, attrName));
    attrName = "currentThreadsBusy";
    System.out.println("currentThreadsBusy:" + mbsc.getAttribute(threadObjName, attrName));

  }
}
