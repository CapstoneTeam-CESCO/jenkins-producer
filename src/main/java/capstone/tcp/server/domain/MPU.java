package capstone.tcp.server.domain;

import capstone.tcp.server.common.CapstoneConstant;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class MPU implements Serializable {

    private static final long serialVersionUID = 2182643655037063226L;

    public MPU() {}
    
    private String time;
    private int rssi;
    private String trapId;
    private String trapIdType;
    private String mLen;
    private String cmd;
    private String item;
    private String dataType;
    
    private PestData pest = new PestData();
    private MouseCycleData mouseCycle = new MouseCycleData();
    private MouseWarningData mouseWarning = new MouseWarningData();
    private SnapShotData snapShot = new SnapShotData();
    private DamWarningData damWarning = new DamWarningData();
    
    private String mIndex;
    
    // DAM 은 DB처리 안함 ; 기본값 false
    private boolean isDevice = false;
    
    public boolean isDevice() {
        return isDevice;
    }

    public void setDevice(boolean isDevice) {
        this.isDevice = isDevice;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MPU [time=");
        builder.append(time);
        builder.append(", rssi=");
        builder.append(rssi);
        builder.append(", trapId=");
        builder.append(trapId);
        builder.append(", trapIdType=");
        builder.append(trapIdType);
        builder.append(", mLen=");
        builder.append(mLen);
        builder.append(", cmd=");
        builder.append(cmd);
        builder.append(", item=");
        builder.append(item);
        builder.append(", mIndex=");
        builder.append(mIndex);
        builder.append(", isDevice=");
        builder.append(isDevice);
        builder.append("]");
        return builder.toString();
    }

    public Map<String, Object> toJsonMap() {
        Map<String, Object> jsonMap = new LinkedHashMap<>();
        jsonMap.put("MPUIndex", mIndex);
        jsonMap.put("time", time);
        jsonMap.put("rssi", rssi);
        jsonMap.put("device", isDevice);
        jsonMap.put("trapId", trapId);
        jsonMap.put("trapIdType", trapIdType);
        jsonMap.put("cmd", cmd);
        jsonMap.put("item", item);
        jsonMap.put("dataType", dataType);
        jsonMap.put("pest", pest.toJsonMap());
        jsonMap.put("mouseCycle", mouseCycle.toJsonMap());
        jsonMap.put("mouseWarning", mouseWarning.toJsonMap());
        jsonMap.put("snapShot", snapShot.toJsonMap());
        jsonMap.put("damWarning", damWarning.toJsonMap());
        return jsonMap;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public String getTrapId() {
        return trapId;
    }

    public void setTrapId(String trapId) {
        this.trapId = trapId;
    }


    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getmLen() {
        return mLen;
    }

    public void setmLen(String mLen) {
        this.mLen = mLen;
    }

    public String getmIndex() {
        return mIndex;
    }

    public void setmIndex(String mIndex) {
        this.mIndex = mIndex;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }


    public PestData getPestData() {
        return pest;
    }

    public void setPestData(PestData pestData) {
        this.pest = pestData;
    }

    public SnapShotData getSnapShotData() {
        return snapShot;
    }

    public void setSnapShotData(SnapShotData snapShotData) {
        this.snapShot = snapShotData;
    }

    public MouseWarningData getMouseWarning() {
        return mouseWarning;
    }

    public void setMouseWarning(MouseWarningData mouseWarning) {
        this.mouseWarning = mouseWarning;
    }

    public MouseCycleData getMouseCycle() {
        return mouseCycle;
    }

    public void setMouseCycle(MouseCycleData mouseCycle) {
        this.mouseCycle = mouseCycle;
    }

    public DamWarningData getDamWarning() {
        return damWarning;
    }

    public void setDamWarning(DamWarningData damWarning) {
        this.damWarning = damWarning;
    }

    public String getTrapIdType() {
        return trapIdType;
    }

    public void setTrapIdType(String trapIdType) {
        this.trapIdType = trapIdType;
    }



}
