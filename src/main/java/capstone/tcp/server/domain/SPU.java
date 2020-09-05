package capstone.tcp.server.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SPU implements Serializable {

    private static final long serialVersionUID = -6964444284583189847L;

    public SPU() {}
    
    private String stx;
    private String ver;
    private String damId;
    private String damIdType;
    private int sLen;
    
    private List<MPU> mpuList = new ArrayList<MPU>();
    
    private String sIndex;
    private int chkSum;
    private String etx;
    
    private String rawData;
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SPU [stx=");
        builder.append(stx);
        builder.append(", ver=");
        builder.append(ver);
        builder.append(", damId=");
        builder.append(damId);
        builder.append(", damIdType=");
        builder.append(damIdType);
        builder.append(", sLen=");
        builder.append(getsLen());
        builder.append(", sIndex=");
        builder.append(getsIndex());
        builder.append(", chkSum=");
        builder.append(chkSum);
        builder.append(", etx=");
        builder.append(etx);
        builder.append("]");
        return builder.toString();
    }

    public String getStx() {
        return stx;
    }
    public void setStx(String stx) {
        this.stx = stx;
    }
    public String getVer() {
        return ver;
    }
    public void setVer(String ver) {
        this.ver = ver;
    }
    public String getDamId() {
        return damId;
    }
    public void setDamId(String damId) {
        this.damId = damId;
    }

    public int getChkSum() {
        return chkSum;
    }

    public void setChkSum(int chkSum) {
        this.chkSum = chkSum;
    }

    public String getEtx() {
        return etx;
    }

    public void setEtx(String etx) {
        this.etx = etx;
    }

    public String getsIndex() {
        return sIndex;
    }

    public void setsIndex(String sIndex) {
        this.sIndex = sIndex;
    }

    public int getsLen() {
        return sLen;
    }

    public void setsLen(int sLen) {
        this.sLen = sLen;
    }

    public List<MPU> getMpuList() {
        return mpuList;
    }

    public void setMpuList(List<MPU> mpuList) {
        this.mpuList = mpuList;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getDamIdType() {
        return damIdType;
    }

    public void setDamIdType(String damIdType) {
        this.damIdType = damIdType;
    }

}
