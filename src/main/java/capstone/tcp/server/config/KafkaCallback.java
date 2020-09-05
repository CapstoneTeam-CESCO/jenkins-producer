package capstone.tcp.server.config;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

public class KafkaCallback implements Callback {
    @Override
    public void onCompletion(RecordMetadata metadata, Exception exception) {

    }
}