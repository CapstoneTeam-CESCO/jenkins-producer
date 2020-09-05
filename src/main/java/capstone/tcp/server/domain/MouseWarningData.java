package capstone.tcp.server.domain;

import java.io.Serializable;

public class MouseWarningData implements Serializable {

    private static final long serialVersionUID = 6851347875554517506L;

    public MouseWarningData() {}
    
    private String inclinationHex;
    private String inclination;
    
    private String powerHex;
    private String power;
    
    private String voltageHex;
    private String voltage;
    
    private String flagHex;
    private String flag;
    
    private String captureHex;
    private String capture;
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MouseWarningData [inclinationHex=");
        builder.append(inclinationHex);
        builder.append(", inclination=");
        builder.append(inclination);
        builder.append(", powerHex=");
        builder.append(powerHex);
        builder.append(", power=");
        builder.append(power);
        builder.append(", voltageHex=");
        builder.append(voltageHex);
        builder.append(", voltage=");
        builder.append(voltage);
        builder.append(", flagHex=");
        builder.append(flagHex);
        builder.append(", flag=");
        builder.append(flag);
        builder.append(", captureHex=");
        builder.append(captureHex);
        builder.append(", capture=");
        builder.append(capture);
        builder.append("]");
        return builder.toString();
    }
    
    public String getInclination() {
        return inclination;
    }
    public void setInclination(String inclination) {
        this.inclination = inclination;
    }
    public String getPower() {
        return power;
    }
    public void setPower(String power) {
        this.power = power;
    }
    public String getVoltage() {
        return voltage;
    }
    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }
    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    public String getCapture() {
        return capture;
    }
    public void setCapture(String capture) {
        this.capture = capture;
    }
    public String getInclinationHex() {
        return inclinationHex;
    }
    public void setInclinationHex(String inclinationHex) {
        this.inclinationHex = inclinationHex;
    }
    public String getPowerHex() {
        return powerHex;
    }
    public void setPowerHex(String powerHex) {
        this.powerHex = powerHex;
    }
    public String getVoltageHex() {
        return voltageHex;
    }
    public void setVoltageHex(String voltageHex) {
        this.voltageHex = voltageHex;
    }
    public String getFlagHex() {
        return flagHex;
    }
    public void setFlagHex(String flagHex) {
        this.flagHex = flagHex;
    }
    public String getCaptureHex() {
        return captureHex;
    }
    public void setCaptureHex(String captureHex) {
        this.captureHex = captureHex;
    }

    


}
