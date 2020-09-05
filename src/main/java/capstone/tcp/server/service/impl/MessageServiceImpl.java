package capstone.tcp.server.service.impl;

import java.util.Arrays;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import capstone.tcp.server.common.CapstoneConstant;
import capstone.tcp.server.common.ConvertUtil;
import capstone.tcp.server.common.BCDConverter;
import capstone.tcp.server.common.LogUtil;
import capstone.tcp.server.domain.DamWarningData;
import capstone.tcp.server.domain.MPU;
import capstone.tcp.server.domain.MouseCycleData;
import capstone.tcp.server.domain.MouseWarningData;
import capstone.tcp.server.domain.PestData;
import capstone.tcp.server.domain.SPU;
import capstone.tcp.server.domain.SnapShotData;
import capstone.tcp.server.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

    @Override
    public SPU getHeader(byte[] dst) throws Exception {

        SPU command = new SPU();
        int offset = 0;

        command.setStx(ConvertUtil.getByte2String(dst, offset, 1));
        offset += 1;
        command.setVer(ConvertUtil.getByte2String(dst, offset, 1));
        offset += 1;
        command.setDamId(ConvertUtil.getByte2HexStringUpper(dst, offset, 6));
        command.setDamIdType(ConvertUtil.getByte2String(dst, offset, 1));
        offset += 6;
        command.setsLen(ConvertUtil.byteArrayToInt(dst, offset));

        command.setsIndex(ConvertUtil.getByte2String(dst, dst.length - 3, 1));
        command.setChkSum(NumberUtils.toInt(ConvertUtil.getByte2String(dst, dst.length - 2, 1)));
        command.setEtx(ConvertUtil.getByte2String(dst, dst.length - 1, 1));

        command.setRawData(ConvertUtil.getByte2HexStringUpper(dst, 0, dst.length));

        return command;
    }

    @Override
    public void run(byte[] dst, SPU command) throws Exception {
    	getBody(command, dst);
    	//command 에 모두 파싱 완료
    }
    
    /**
     * MPU DATA 조회
     * @param command
     * @param dst
     * @throws Exception
     */
    private void getBody(SPU command, byte[] dst) throws Exception {
        int offset = CapstoneConstant.OFFSET;
        MPU mpu = null;

        while (offset < command.getsLen()) {
            mpu = new MPU();
            
            // 년 (1byte)
            String year = BCDConverter.BCDtoString(dst[offset]);
            offset += 1;

            // 월 (1byte)
            String month = BCDConverter.BCDtoString(dst[offset]);
            offset += 1;

            // 일 (1byte)
            String day = BCDConverter.BCDtoString(dst[offset]);
            offset += 1;

            // 시 (1byte)
            String hour = BCDConverter.BCDtoString(dst[offset]);
            offset += 1;

            // 분 (1byte)
            String min = BCDConverter.BCDtoString(dst[offset]);
            offset += 1;

            // 시간전체 합침 처리 (5byte)
            mpu.setTime(year + month + day + hour + min);

            // RSSI / RF 수신 세기 정보 (1byte -127 ~ 127)
            mpu.setRssi(ConvertUtil.getSignedByte(ConvertUtil.getByte2String(dst, offset, 1)));
            offset += 1;

            // TRAPID / TRAP의 고유한 ID (6byte)
            mpu.setTrapId(ConvertUtil.getByte2HexStringUpper(dst, offset, 6));
            mpu.setTrapIdType(ConvertUtil.getByte2String(dst, offset, 1));
            offset += 6;

            // LEN / CMD부터 Index 까지의 Size (1byte)
            mpu.setmLen(ConvertUtil.getByte2String(dst, offset, 1));
            offset += 1;

            // CMD (1byte)
            mpu.setCmd(ConvertUtil.getByte2String(dst, offset, 1));
            offset += 1;

            // ITEM (1byte)
            mpu.setItem(ConvertUtil.getByte2String(dst, offset, 1));
            offset += 1;

            LogUtil.traceLogInfo(command, mpu.toString());

            String device = mpu.getTrapId().substring(0, 2);
            if (Arrays.asList(CapstoneConstant.DEVICE_PEST).contains(device)) {
                LogUtil.traceLogInfo(command, "PEST");

                switch (mpu.getItem()) {
                    case CapstoneConstant.ITEM_WARNING:
                        LogUtil.traceLogInfo(command, "Warning Data");
                        offset = setPestData(command, dst, offset, mpu);
                        break;
                    case CapstoneConstant.ITEM_CYCLE:
                        LogUtil.traceLogInfo(command, "Cycle Data");
                        offset = setPestData(command, dst, offset, mpu);
                        break;
                    case CapstoneConstant.ITEM_SNAPSHOT:
                        LogUtil.traceLogInfo(command, "Snap Shot Data");
                        offset = setSnapShotData(command, dst, offset, mpu);
                        break;
                    default:
                        LogUtil.traceLogInfo(command, "Do Not Support ITEM:" + mpu.getItem());
                        throw new Exception("Do Not Support ITEM:" + mpu.getItem());
                }// switch

            } else if (Arrays.asList(CapstoneConstant.DEVICE_MOUSE).contains(device)) {
                LogUtil.traceLogInfo(command, "MOUSE");

                switch (mpu.getItem()) {

                    case CapstoneConstant.ITEM_WARNING:
                        LogUtil.traceLogInfo(command, "Warning Data");
                        offset = setMouseWarningData(command, dst, offset, mpu);
                        break;
                    case CapstoneConstant.ITEM_CYCLE:
                        LogUtil.traceLogInfo(command, "Cycle Data");
                        offset = setMouseCycleData(command, dst, offset, mpu);
                        break;
                    case CapstoneConstant.ITEM_SNAPSHOT:
                        LogUtil.traceLogInfo(command, "Snap Shot Data");
                        offset = setSnapShotData(command, dst, offset, mpu);
                        break;
                    default:
                        LogUtil.traceLogInfo(command, "Do not Support ITEM:" + mpu.getItem());
                        throw new Exception("Do Not Support ITEM:" + mpu.getItem());
                }// switch
            } else if (Arrays.asList(CapstoneConstant.DEVICE_NETWORK).contains(device)) {
                LogUtil.traceLogInfo(command, "Network Device");
                offset = setDamWarning(command, dst, offset, mpu);
                // DAM 처리 안함 ; 
                mpu.setDevice(true);
            } else {
                LogUtil.traceLogInfo(command, "Do not Support Trap:" + mpu.getTrapId());
                throw new Exception("Do not Support Trap:" + mpu.getTrapId());
            }

            mpu.setmIndex(ConvertUtil.getByte2String(dst, offset, 1));
            offset += 1;
            LogUtil.traceLogInfo(command, "mIndex=" + mpu.getmIndex());

            command.getMpuList().add(mpu);

        } // while

    }

    private int setDamWarning(SPU command, byte[] dst, int offset, MPU mpu) throws Exception {
        DamWarningData damWarning = new DamWarningData();

        damWarning.setTemperatureHex(ConvertUtil.getByte2HexString(dst, offset, 1));
        damWarning.setTemperature(ConvertUtil.getByte2String(dst, offset, 1));
        offset += 1;
        damWarning.setVibrationHex(ConvertUtil.getByte2HexString(dst, offset, 1));
        damWarning.setVibration(ConvertUtil.getByte2String(dst, offset, 1));
        offset += 1;
        damWarning.setPowerHex(ConvertUtil.getByte2HexString(dst, offset, 1));
        damWarning.setPower(ConvertUtil.getByte2String(dst, offset, 1));
        offset += 1;

        LogUtil.traceLogInfo(command, damWarning.toString());

        mpu.setDamWarning(damWarning);
        return offset;
    }

    private int setMouseCycleData(SPU command, byte[] dst, int offset, MPU mpu) throws Exception {
        MouseCycleData mouseCycle = new MouseCycleData();

        mouseCycle.setCaptureHex(ConvertUtil.getByte2HexString(dst, offset, 2));
        mouseCycle.setCapture(ConvertUtil.byteArrayToInt(dst, offset));
        offset += 2;
        mouseCycle.setVoltageHex(ConvertUtil.getByte2HexString(dst, offset, 1));
        mouseCycle.setVoltage(ConvertUtil.getByte2String(dst, offset, 1));
        offset += 1;
        mouseCycle.setRssiHex(ConvertUtil.getByte2HexString(dst, offset, 1));
        mouseCycle.setRssi(ConvertUtil.getSignedByte(ConvertUtil.getByte2String(dst, offset, 1)));
        offset += 1;
        mouseCycle.setStatusCodeHex(ConvertUtil.getByte2HexString(dst, offset, 2));
        mouseCycle.setStatusCode(ConvertUtil.byteArrayToInt(dst, offset));
        offset += 2;

        LogUtil.traceLogInfo(command, mouseCycle.toString());

        mpu.setMouseCycle(mouseCycle);
        return offset;
    }

    private int setMouseWarningData(SPU command, byte[] dst, int offset, MPU mpu) throws Exception {
        MouseWarningData mouseWarning = new MouseWarningData();

        mouseWarning.setInclinationHex(ConvertUtil.getByte2HexString(dst, offset, 1));
        mouseWarning.setInclination(ConvertUtil.getByte2String(dst, offset, 1));
        offset += 1;
        mouseWarning.setPowerHex(ConvertUtil.getByte2HexString(dst, offset, 1));
        mouseWarning.setPower(ConvertUtil.getByte2String(dst, offset, 1));
        offset += 1;
        mouseWarning.setVoltageHex(ConvertUtil.getByte2HexString(dst, offset, 1));
        mouseWarning.setVoltage(ConvertUtil.getByte2String(dst, offset, 1));
        offset += 1;
        mouseWarning.setFlagHex(ConvertUtil.getByte2HexString(dst, offset, 1));
        mouseWarning.setFlag(ConvertUtil.getByte2String(dst, offset, 1));
        offset += 1;
        mouseWarning.setCaptureHex(ConvertUtil.getByte2HexString(dst, offset, 1));
        mouseWarning.setCapture(ConvertUtil.getByte2String(dst, offset, 1));
        offset += 1;

        LogUtil.traceLogInfo(command, mouseWarning.toString());

        mpu.setMouseWarning(mouseWarning);
        return offset;
    }

    /**
     * 1번째 데이터 index ; cmd 2, item 8 </br>
     * 2번째 이후 데이터 data ; cmd 80, item 8
     * @param command
     * @param dst
     * @param offset
     * @param mpu
     * @return
     * @throws Exception
     */
    private int setSnapShotData(SPU command, byte[] dst, int offset, MPU mpu) throws Exception {
        SnapShotData snapShot = new SnapShotData();

        if (CapstoneConstant.CMD_REQUEST_READ.equals(mpu.getCmd())
                || CapstoneConstant.CMD_REQUEST_EVENT.equals(mpu.getCmd())) {
            // 1번째 데이터

            snapShot.setImageIndex(ConvertUtil.byteArrayToInt4(dst, offset));
            offset += 4;

        } else { 
            // 2번째 이후 데이터
            
            int mLen = NumberUtils.toInt(mpu.getmLen()) - 3; // cmd, item, index
            byte[] tmp = new byte[mLen];
            System.arraycopy(dst, offset, tmp, 0, mLen);
            snapShot.setImageData(tmp);
            snapShot.setImageDataHex(ConvertUtil.getByte2HexString(tmp, 0, mLen));
            offset += mLen;
        }

        LogUtil.traceLogInfo(command, snapShot.toString());
        mpu.setSnapShotData(snapShot);
        return offset;
    }

    private int setPestData(SPU command, byte[] dst, int offset, MPU mpu) throws Exception {
        PestData pest = new PestData();

        pest.setCaptureHex(ConvertUtil.getByte2HexString(dst, offset, 4));
        pest.setCapture(ConvertUtil.byteArrayToInt4(dst, offset));
        offset += 4;
        pest.setTemperatureHex(ConvertUtil.getByte2HexString(dst, offset, 1));
        pest.setTemperature(ConvertUtil.getByte2String(dst, offset, 1));
        offset += 1;
        pest.setSensorDutyHex(ConvertUtil.getByte2HexString(dst, offset, 1));
        pest.setSensorDuty(ConvertUtil.getByte2String(dst, offset, 1));
        offset += 1;
        pest.setPanDutyHex(ConvertUtil.getByte2HexString(dst, offset, 1));
        pest.setPanDuty(ConvertUtil.getByte2String(dst, offset, 1));
        offset += 1;
        pest.setPanRpmHex(ConvertUtil.getByte2HexString(dst, offset, 2));
        pest.setPanRpm(ConvertUtil.byteArrayToInt(dst, offset));
        offset += 2;
        pest.setStatusCodeHex(ConvertUtil.getByte2HexString(dst, offset, 2));
        pest.setStatusCode(ConvertUtil.byteArrayToInt(dst, offset));
        offset += 2;

        LogUtil.traceLogInfo(command, pest.toString());

        mpu.setPestData(pest);
        return offset;
    }
    
    
}