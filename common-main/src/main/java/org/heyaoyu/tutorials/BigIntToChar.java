package org.heyaoyu.tutorials;

/**
 * Created by heyaoyu on 2017/1/30.
 */
public class BigIntToChar {

  public static void main(String[] args) {
//    int i = 10000;
//    char c = (char) i;
//    System.out.println(i);
//    System.out.println(c);
    String h5Url = "http://m.toutiao.iqiyi.com/top_11uthn42bi.html?inBase=1&entry=daily_info4";
    System.out.println(extractNewsIdFromH5Url(h5Url));
  }

  private static String extractNewsIdFromH5Url(String h5Url) {
    int top_ = h5Url.indexOf("top_");
    int dotHtml = h5Url.lastIndexOf(".html");
    if (dotHtml > top_ + 4) {
      return h5Url.substring(top_ + 4, dotHtml);
    }
    return "";
  }

}
