package capstone.tcp.server.domain;

import java.util.LinkedHashMap;
import java.util.Map;

public class KafkaMessage {
    private SPU command;

    public KafkaMessage(SPU command) {
        this.command = command;
    }

    public Map<String, Object> toJsonMap() {
        Map<String, Object> jsonMap = new LinkedHashMap<>();
        jsonMap.put("SPU", command.toJsonMap());
        return jsonMap;
    }
}


//MPU => time, rssi, trapId, trapIdType, cmd, item