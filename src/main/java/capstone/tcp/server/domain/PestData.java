package capstone.tcp.server.domain;

import java.io.Serializable;

public class PestData implements Serializable {

    private static final long serialVersionUID = -5415106201779684386L;

    public PestData() {}
    
    private String captureHex;
    private int capture;
    
    private String temperatureHex;
    private String temperature;
    
    private String sensorDutyHex;
    private String sensorDuty;
    
    private String panDutyHex;
    private String panDuty;
    
    private String panRpmHex;
    private int panRpm;
    
    private String statusCodeHex;
    private int statusCode;
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PestData [captureHex=");
        builder.append(captureHex);
        builder.append(", capture=");
        builder.append(capture);
        builder.append(", temperatureHex=");
        builder.append(temperatureHex);
        builder.append(", temperature=");
        builder.append(temperature);
        builder.append(", sensorDutyHex=");
        builder.append(sensorDutyHex);
        builder.append(", sensorDuty=");
        builder.append(sensorDuty);
        builder.append(", panDutyHex=");
        builder.append(panDutyHex);
        builder.append(", panDuty=");
        builder.append(panDuty);
        builder.append(", panRpmHex=");
        builder.append(panRpmHex);
        builder.append(", panRpm=");
        builder.append(panRpm);
        builder.append(", statusCodeHex=");
        builder.append(statusCodeHex);
        builder.append(", statusCode=");
        builder.append(statusCode);
        builder.append("]");
        return builder.toString();
    }
    public String getCaptureHex() {
        return captureHex;
    }
    public void setCaptureHex(String captureHex) {
        this.captureHex = captureHex;
    }
    public String getTemperatureHex() {
        return temperatureHex;
    }
    public void setTemperatureHex(String temperatureHex) {
        this.temperatureHex = temperatureHex;
    }
    public String getSensorDutyHex() {
        return sensorDutyHex;
    }
    public void setSensorDutyHex(String sensorDutyHex) {
        this.sensorDutyHex = sensorDutyHex;
    }
    public String getPanDutyHex() {
        return panDutyHex;
    }
    public void setPanDutyHex(String panDutyHex) {
        this.panDutyHex = panDutyHex;
    }
    public String getPanRpmHex() {
        return panRpmHex;
    }
    public void setPanRpmHex(String panRpmHex) {
        this.panRpmHex = panRpmHex;
    }
    public String getStatusCodeHex() {
        return statusCodeHex;
    }
    public void setStatusCodeHex(String statusCodeHex) {
        this.statusCodeHex = statusCodeHex;
    }

    public int getCapture() {
        return capture;
    }
    public void setCapture(int capture) {
        this.capture = capture;
    }
    public String getTemperature() {
        return temperature;
    }
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
    public String getSensorDuty() {
        return sensorDuty;
    }
    public void setSensorDuty(String sensorDuty) {
        this.sensorDuty = sensorDuty;
    }
    public String getPanDuty() {
        return panDuty;
    }
    public void setPanDuty(String panDuty) {
        this.panDuty = panDuty;
    }
    public int getPanRpm() {
        return panRpm;
    }
    public void setPanRpm(int panRpm) {
        this.panRpm = panRpm;
    }
    public int getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

}
