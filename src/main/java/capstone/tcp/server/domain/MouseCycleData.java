package capstone.tcp.server.domain;

import java.io.Serializable;

public class MouseCycleData implements Serializable {

    private static final long serialVersionUID = 5310938180797935537L;

    public MouseCycleData() {}
    
    private String captureHex;
    private int capture;
    
    private String voltageHex;
    private String voltage;
    
    private String rssiHex;
    private int rssi;
    
    private String statusCodeHex;
    private int statusCode;
    
    private String seqNum;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MouseCycleData [captureHex=");
        builder.append(captureHex);
        builder.append(", capture=");
        builder.append(capture);
        builder.append(", voltageHex=");
        builder.append(voltageHex);
        builder.append(", voltage=");
        builder.append(voltage);
        builder.append(", rssiHex=");
        builder.append(rssiHex);
        builder.append(", rssi=");
        builder.append(rssi);
        builder.append(", statusCodeHex=");
        builder.append(statusCodeHex);
        builder.append(", statusCode=");
        builder.append(statusCode);
        builder.append(", seqNum=");
        builder.append(seqNum);
        builder.append("]");
        return builder.toString();
    }
    
    public int getCapture() {
        return capture;
    }
    public void setCapture(int capture) {
        this.capture = capture;
    }
    public String getVoltage() {
        return voltage;
    }
    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }
    public int getRssi() {
        return rssi;
    }
    public void setRssi(int rssi) {
        this.rssi = rssi;
    }
    public int getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
    public String getSeqNum() {
        return seqNum;
    }
    public void setSeqNum(String seqNum) {
        this.seqNum = seqNum;
    }

    public String getCaptureHex() {
        return captureHex;
    }

    public void setCaptureHex(String captureHex) {
        this.captureHex = captureHex;
    }

    public String getVoltageHex() {
        return voltageHex;
    }

    public void setVoltageHex(String voltageHex) {
        this.voltageHex = voltageHex;
    }

    public String getRssiHex() {
        return rssiHex;
    }

    public void setRssiHex(String rssiHex) {
        this.rssiHex = rssiHex;
    }

    public String getStatusCodeHex() {
        return statusCodeHex;
    }

    public void setStatusCodeHex(String statusCodeHex) {
        this.statusCodeHex = statusCodeHex;
    }

}
