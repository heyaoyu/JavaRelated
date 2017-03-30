import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

/**
 * Created by heyaoyu on 2017/3/30.
 */
public class KafkaHighLevel08Consumer {

  public static void main(String[] args) {
    Properties props = new Properties();
    props.put("zookeeper.connect", "localhost:2181");
    props.put("auto.offset.reset", "smallest");
    props.put("group.id", "test-group");
    props.put("enable.auto.commit", "true");
    props.put("zookeeper.session.timeout.ms", "400");
    props.put("zookeeper.sync.time.ms", "200");
    props.put("auto.commit.interval.ms", "1000");
    ConsumerConfig consumerConfig = new kafka.consumer.ConsumerConfig(props);
    ConsumerConnector consumerConnector = kafka.consumer.Consumer
        .createJavaConsumerConnector(consumerConfig);

    Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
    int localConsumerCount = 1;
    topicCountMap.put("test_topic", localConsumerCount);
    Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConnector
        .createMessageStreams(topicCountMap);
    List<KafkaStream<byte[], byte[]>> streams = consumerMap.get("test_topic");
    ConsumerIterator<byte[], byte[]> it = streams.get(0).iterator();
    while (it.hasNext()) {
      MessageAndMetadata<byte[], byte[]> mam = it.next();
      System.out.println("value:" + new String(mam.message()));
    }
  }

}
