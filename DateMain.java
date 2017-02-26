import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.commons.io.IOUtils;

/**
 * Created by heyaoyu on 2017/2/18.
 */
public class DateMain {

  private final static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

  public static void main(String[] args) throws Exception {
    List<String> lines = IOUtils
        .readLines(new FileInputStream("/Users/heyaoyu/Downloads/cmts.txt"));
    int i = 1;
    for (String line : lines) {
      String dateStr = line.substring(0, 23);
      long date = getSeconds(dateStr);
      String output =
          i + "curl -d " + "\"" + line.substring(24) + "&publishTime=" + date + "&admin=admin"
              + "\" http://10.153.138.3:8910/sns-comment-api/comment/publish";
      i++;
      System.out.println(output);
    }
  }

  private static long getSeconds(String dateStr) {
    DateFormat df = new SimpleDateFormat(TIME_FORMAT);
    try {
      long time = df.parse(dateStr).getTime() / 1000;
      return time;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return System.currentTimeMillis() / 1000;
  }

}