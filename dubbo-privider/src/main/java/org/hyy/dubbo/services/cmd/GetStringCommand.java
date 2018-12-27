package org.hyy.dubbo.services.cmd;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import java.util.concurrent.TimeUnit;

/**
 * Created by heyaoyu on 2018/12/27.
 */
public class GetStringCommand extends HystrixCommand<String> {

  private String name = "";

  public GetStringCommand(String name) {
    super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HystrixCommandGroup"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("HystrixCommand"))
            .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("HystrixThreadPool"))
            .andCommandPropertiesDefaults(
                HystrixCommandProperties.Setter()
                    .withExecutionTimeoutInMilliseconds(500))
            .andThreadPoolPropertiesDefaults(
                HystrixThreadPoolProperties.Setter()
                    .withCoreSize(1000)
            )
    );
    this.name = name;
  }

  @Override
  protected String run() throws Exception {
//    TimeUnit.SECONDS.sleep(3);
    return "Hello, " + name;
  }

  @Override
  protected String getFallback() {
    return "fallback..." + name;
  }

}
