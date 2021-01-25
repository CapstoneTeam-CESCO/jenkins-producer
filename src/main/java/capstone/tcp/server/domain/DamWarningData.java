package capstone.tcp.server.domain;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class DamWarningData implements Serializable {

    private static final long serialVersionUID = -893705187100014057L;
    
    public DamWarningData() {}
    
    private String temperatureHex;
    private String temperature;
    
    private String vibrationHex;
    private String vibration;
    
    private String powerHex;
    private String power;
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DamWarningData [temperatureHex=");
        builder.append(temperatureHex);
        builder.append(", temperature=");
        builder.append(temperature);
        builder.append(", vibrationHex=");
        builder.append(vibrationHex);
        builder.append(", vibration=");
        builder.append(vibration);
        builder.append(", powerHex=");
        builder.append(powerHex);
        builder.append(", power=");
        builder.append(power);
        builder.append("]");
        return builder.toString();
    }

    public Map<String, Object> toJsonMap() {
        Map<String, Object> jsonMap = new LinkedHashMap<>();
        jsonMap.put("temperature", temperature);
        jsonMap.put("vibration", vibration);
        jsonMap.put("power", power);
        return jsonMap;
    }
    
    public String getTemperature() {
        return temperature;
    }
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
    public String getVibration() {
        return vibration;
    }
    public void setVibration(String vibration) {
        this.vibration = vibration;
    }
    public String getPower() {
        return power;
    }
    public void setPower(String power) {
        this.power = power;
    }

    public String getTemperatureHex() {
        return temperatureHex;
    }

    public void setTemperatureHex(String temperatureHex) {
        this.temperatureHex = temperatureHex;
    }

    public String getVibrationHex() {
        return vibrationHex;
    }

    public void setVibrationHex(String vibrationHex) {
        this.vibrationHex = vibrationHex;
    }

    public String getPowerHex() {
        return powerHex;
    }

    public void setPowerHex(String powerHex) {
        this.powerHex = powerHex;
    }


}
