package capstone.tcp.server.server;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import capstone.tcp.server.config.KafkaConfig;
import capstone.tcp.server.config.KafkaCallback;

public class KafkaProducer1 implements Runnable {
    private String str;

    public KafkaProducer1(String str) {
        this.str = str;
    }

    @Override
    public void run() {
        final String TOPIC = "test-par3";

        Properties props = new KafkaConfig().init();
        Producer<String, String> producer = new KafkaProducer<>(props);
        
        try {
            // RecordMetadata metadata = producer.send(new ProducerRecord<String, String>("test-par3", str)).get();  // 동기전송
            producer.send(new ProducerRecord<String, String>(TOPIC, str), new KafkaCallback());  // 비동기전송
            // System.out.printf("Partition: %d, Offset: %d", metadata.partition(), metadata.offset());
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            producer.close();
        }
    }
}