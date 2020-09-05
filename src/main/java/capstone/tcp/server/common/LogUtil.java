package capstone.tcp.server.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import capstone.tcp.server.domain.SPU;

public class LogUtil {

    private LogUtil() {}

    /**
     * Trace log
     */
    public static Logger traceLog = LoggerFactory.getLogger("trace");

    /**
     * Error log
     */
    public static Logger errorLog = LoggerFactory.getLogger("error");
    
    /**
     * binary log
     */
    public static Logger binaryLog = LoggerFactory.getLogger("binary");
    
    /**
     * System log
     */
    public static Logger systemLog = LoggerFactory.getLogger("system");

    /**
     * Trace Info log
     * @param command command
     * @param msg msg
     */
    public static void traceLogInfo(SPU command, String msg) {
        StringBuffer buf = new StringBuffer();
        buf.append("[").append(command.getDamId()).append("]");
        buf.append(msg);
        
        traceLog.info(buf.toString());
    }
    
    /**
     * Trace Debug Log
     * @param command command
     * @param msg msg
     */
    public static void traceLogDebug(SPU command, String msg) {
        StringBuffer buf = new StringBuffer();
        buf.append("[").append(command.getDamId()).append("]");
        buf.append(msg);
        
        traceLog.debug(buf.toString());
    }
    
}
