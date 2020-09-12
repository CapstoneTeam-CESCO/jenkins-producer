package capstone.tcp.server.service;

import org.apache.kafka.clients.producer.Producer;
public interface SendService {
    public void sendMsg(Producer producer, String msg);
}
