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
        jsonMap.put("ver", command.getVer());
        jsonMap.put("damId", command.getDamId());
        jsonMap.put("damIdType", command.getDamIdType());
        for(MPU mpu : command.getMpuList()) {
            jsonMap.put("mIndex", mpu.getmIndex());
            jsonMap.put("MPU", mpu.toJsonMap());
        }
        return jsonMap;
    }
}


//MPU => time, rssi, trapId, trapIdType, cmd, item