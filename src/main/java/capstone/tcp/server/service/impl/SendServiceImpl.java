package capstone.tcp.server.service.impl;

import capstone.tcp.server.common.LogUtil;
import capstone.tcp.server.service.SendService;

import org.apache.kafka.clients.producer.*;
import org.springframework.stereotype.Service;

@Service
public class SendServiceImpl implements SendService {

    @Override
    public void sendMsg(Producer producer, String msg) {
        String topic = "test-par3";

        try {
            LogUtil.traceLog.info("========== START to send message to Kafka ==========");

//             RecordMetadata metadata = producer.send(new ProducerRecord<String, String>(topic, msg)).get();  // 동기전송
            producer.send(new ProducerRecord<String, String>(topic, msg),
                    (metadata, exception) -> {
                        LogUtil.traceLog.info(msg);
                        LogUtil.traceLog.info("topic " + metadata.topic() +
                                              ", partition " + metadata.partition() +
                                              ", offset " + metadata.offset());
                        LogUtil.traceLog.info("========== END to send message to Kafka ==========");
                    });  // 비동기전송
        } catch (Exception exception) {
            LogUtil.errorLog.error("::KafkaHandler Error::", exception);
        } finally {
//            producer.close();
        }
    }
}
