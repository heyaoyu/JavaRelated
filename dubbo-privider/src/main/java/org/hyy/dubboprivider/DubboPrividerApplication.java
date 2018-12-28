package org.hyy.dubboprivider;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(value = {"classpath:dubbo-provider.xml"})
public class DubboPrividerApplication {

  public static void main(String[] args) {
    SpringApplication.run(DubboPrividerApplication.class, args);
  }

  @Bean
  public ServletRegistrationBean registration() {
    ServletRegistrationBean registrationBean = new ServletRegistrationBean(
        new HystrixMetricsStreamServlet());
    registrationBean.addUrlMappings("/hystrix.stream");
    return registrationBean;
  }
}

