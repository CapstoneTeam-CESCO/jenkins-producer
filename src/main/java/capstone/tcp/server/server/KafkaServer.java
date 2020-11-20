package capstone.tcp.server.server;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

public class KafkaServer {
    private Producer<String, String> producer;

    public KafkaServer() {
        final String BOOTSTRAPSERVER = "34.64.155.139:9092";

        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAPSERVER);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("acks","0");//0, 1, all == -1
        props.put("retries",1);
        props.put("batch.size",20000);
        props.put("linger.ms",1);
        props.put("buffer.memory",24568545);

        this.producer = new KafkaProducer<>(props);
    }

    public Producer<String, String> getProducer() { return this.producer; }
}