package capstone.tcp.server.common;

public class CapstoneConstant {
    
    private CapstoneConstant() {}
    
    /**
     * Start of Message (byte)
     */
    public static final byte BYTE_STX = (byte) 0xf1;

    /**
     * End of Message (byte)
     */
    public static final byte BYTE_ETX = 0x1f;
    
    /**
     * Start of Message (int)
     */
    public static final String STX = "241";

    /**
     * End of Message (int)
     */
    public static final String ETX = "31";
    
    /**
     * Offset
     */
    public static final int OFFSET = 10;
    
    /**
     * 비래 해충군 장비코드 들
     */
    public static final String[] DEVICE_PEST = {"01", "02", "03" , "04"};

    /**
     * 구서 장비 장비코드 들
     */
    public static final String[] DEVICE_MOUSE = {"11", "12", "13"};

    /**
     * 네트워크 Device 장비코드 들
     */
    public static final String[] DEVICE_NETWORK = {"A1", "A2", "A3", "B1", "B2"};
    
    /**
     * 피닉스 장비코드
     */
    public static final String DEVICE_PHOENIX = "01";

    /**
     * 바이퍼 장비코드
     */
    public static final String DEVICE_VIPER = "11";

    /**
     * 블루치즈 장비코드
     */
    public static final String DEVICE_BLUE_CHEESE = "12";

    /**
     * DAM 장비코드
     */
    public static final String DEVICE_DAM = "A1";
    
    /**
     * Item / 경보 / DEVICE에서 사용되는 경보에 대한 ITEM 
     */
    public static final String ITEM_WARNING = "4";

    /**
     * Item / 주기 / DEVICE에서 사용되는 경보에 대한 ITEM
     */
    public static final String ITEM_CYCLE = "5";
    
    /**
     * Item / Snapshot / SNAPSHOT에 관련된 ITEM
     */
    public static final String ITEM_SNAPSHOT = "8";
    
    /**
     * Command / Read / Request
     */
    public static final String CMD_REQUEST_READ = "0";

    /**
     * Command / Event / Request
     */
    public static final String CMD_REQUEST_EVENT = "2";

    /**
     * Command / Read / Response
     */
    public static final String CMD_RESPONSE_READ = "80";

    /**
     * for setDataType() in MPU.java
     */
    public static final String PEST_DATA = "pestData";

    public static final String MOUSE_CYCLE_DATA = "mouseCycleData";

    public static final String MOUSE_WARNING_DATA = "mouseWarningData";

    public static final String SNAPSHOT_DATA = "snapshotData";

    public static final String DAM_WARNING_DATA = "damWarningData";
}
