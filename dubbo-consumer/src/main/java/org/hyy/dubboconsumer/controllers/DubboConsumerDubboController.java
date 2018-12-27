package org.hyy.dubboconsumer.controllers;

import org.hyy.dubbo.services.ISayHello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by heyaoyu on 2018/12/24.
 */
@RestController
public class DubboConsumerDubboController {

  @Autowired
  private ISayHello sayHello;

  @RequestMapping(value = "/hello", method = RequestMethod.GET)
  public String sayHello(@RequestParam(name = "name", defaultValue = "defaultName") String name) {
    return sayHello.sayHello(name);
  }
}
