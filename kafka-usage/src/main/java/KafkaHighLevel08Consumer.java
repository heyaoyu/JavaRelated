import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

/**
 * Created by heyaoyu on 2017/3/30. topicCountMap - a map of (topic, #streams) pair Returns: a map
 * of (topic, list of KafkaStream) pairs. The number of items in the list is #streams. Each stream
 * supports an iterator over message/metadata pairs.
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
    int localConsumerCount = 2;
    topicCountMap.put("test_topic", localConsumerCount);
    Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConnector
        .createMessageStreams(topicCountMap);
    List<KafkaStream<byte[], byte[]>> streams = consumerMap.get("test_topic");

    ExecutorService es = Executors.newFixedThreadPool(localConsumerCount);
    for (final KafkaStream<byte[], byte[]> stream : streams) {
      es.submit(new Runnable() {
        @Override
        public void run() {
          ConsumerIterator<byte[], byte[]> it = stream.iterator();
          while (it.hasNext()) {
            MessageAndMetadata<byte[], byte[]> mam = it.next();
            System.out.println(
                "Consume value:" + new String(mam.message()) + ", Partition:" + mam.partition());
          }
        }
      });
    }
  }

}
