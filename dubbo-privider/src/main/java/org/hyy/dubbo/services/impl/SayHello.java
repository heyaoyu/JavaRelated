package org.hyy.dubbo.services.impl;

import com.netflix.hystrix.HystrixCommand;
import org.hyy.dubbo.services.ISayHello;
import org.hyy.dubbo.services.cmd.GetStringCommand;

/**
 * Created by heyaoyu on 2018/12/24.
 */
public class SayHello implements ISayHello {

  @Override
  public String sayHello(String name) {
    HystrixCommand<String> getStringCmd = new GetStringCommand(name);
    String ret = "";
    try {
      ret = getStringCmd.execute();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return ret;
  }
}
