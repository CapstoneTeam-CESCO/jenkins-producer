package capstone.tcp.server.config;

import java.util.Properties;

public class KafkaConfig {
    public Properties init() {
        final String BOOTSTRAPSERVER = "34.64.120.38:9092";
    
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAPSERVER);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("acks","0");//0, 1, all == -1
        props.put("retries",1);
        props.put("batch.size",20000);
        props.put("linger.ms",1);
        props.put("buffer.memory",24568545);

        return props;
    }
}