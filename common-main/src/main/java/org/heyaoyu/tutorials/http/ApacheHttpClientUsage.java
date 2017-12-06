package org.heyaoyu.tutorials.http;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * Created by heyaoyu on 2017/12/6.
 */
public class ApacheHttpClientUsage {

  public static void main(String[] args) {
    httpGet();
  }

  private static void httpGet() {
    CloseableHttpClient httpClient = null;
    HttpGet httpGet = new HttpGet(
        "http://fundgz.1234567.com.cn/js/003704.js?rt=" + System.currentTimeMillis());
    CloseableHttpResponse response = null;
    try {
      httpClient = HttpClients.createDefault();
      response = httpClient.execute(httpGet);
      System.out.println(response.getStatusLine());
      HttpEntity entity = response.getEntity();
      System.out.println(EntityUtils.toString(entity, Charset.forName("UTF-8")));
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      try {
        if (response != null) {
          response.close();
        }
        if (httpClient != null) {
          httpClient.close();
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  private static void httpPost() {
    CloseableHttpClient httpClient = null;
    HttpPost httpPost = new HttpPost("url");
    CloseableHttpResponse response = null;
    try {
      httpClient = HttpClients.createDefault();
      List<BasicNameValuePair> nvps = new ArrayList<>();
      nvps.add(new BasicNameValuePair("username", "vip"));
      nvps.add(new BasicNameValuePair("password", "secret"));
      httpPost.setEntity(new UrlEncodedFormEntity(nvps));
      response = httpClient.execute(httpPost);

      System.out.println(response.getStatusLine());
      HttpEntity entity = response.getEntity();
      EntityUtils.consume(entity);
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      try {
        if (response != null) {
          response.close();
        }
        if (httpClient != null) {
          httpClient.close();
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  private static void httpPostJson(String json) {
    CloseableHttpClient httpClient = null;
    HttpPost httpPost = new HttpPost("url");
    CloseableHttpResponse response = null;
    try {
      httpClient = HttpClients.createDefault();
      StringEntity stringEntity = new StringEntity(json);
      stringEntity.setContentEncoding("UTF-8");
      stringEntity.setContentType("application/json");
      httpPost.setEntity(stringEntity);
      response = httpClient.execute(httpPost);
      System.out.println(response.getStatusLine());
      HttpEntity entity = response.getEntity();
      EntityUtils.consume(entity);
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      try {
        if (response != null) {
          response.close();
        }
        if (httpClient != null) {
          httpClient.close();
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }
}
