/**
  * 클라이언트가 접속 했을 경우 발생
  * channelRegistered
  * channelActive
  * 위에 두개가 출력된다.
  * 
  * 클라이언트가 메시지를 보낼 때 발생
  * channelRead
  * 
  * 모든 메시지를 다 읽고 없을 때 발생
  * channelReadComplete
  * 
  * 클라이언트가 접속이 끊어 졌을 때
  * channelInactive
  * channelUnregistered
  * 
  * 이러식으로 이벤트 핸들러가 발생한다.
  */
package capstone.tcp.server.handler;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import capstone.tcp.server.domain.KafkaMessage;
import capstone.tcp.server.server.KafkaServer;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import capstone.tcp.server.common.CapstoneConstant;
import capstone.tcp.server.common.ConvertUtil;
import capstone.tcp.server.common.LogUtil;
import capstone.tcp.server.domain.SPU;
import capstone.tcp.server.service.MessageService;
import capstone.tcp.server.service.SendService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
@Component
public class ServiceHandler extends ChannelInboundHandlerAdapter {
    
    @Autowired
    private MessageService service;

    @Autowired
    private SendService sService;

    private Producer<String, String> producer;

    public ServiceHandler() {
        this.producer = new KafkaServer().getProducer();
        LogUtil.traceLog.info("Kafka server starts!!");
    }

    /**
     * 채널이 이벤트 루프에 등록되었을 때 발생한다.
     * 이벤트 루프는 네티가 이베트를 실행하는 스레드로써 부트 스트랩에 설정한 이벤트 루프다.
     * @param ctx 
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        LogUtil.traceLog.debug("channelRegistered");
    }
    
    /**
     * channelRegistered 다음에 바로 실행 되는 메소드다. 
     * 이벤트 루프에 등록된 이후에 네티 API를 사용하여 채널 입출력을 수행할 상태다.
     * 다음과 같은 작업을 할 수 있다.
     * 클라이언트 연결 개수를 셀 때
     * 최초 연결 메시지를 보낼 때
     * 연결된 상태에 대한 작업이 필요 할때
     * @param ctx 
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LogUtil.traceLog.debug("channelActive");
    }

    /**
     * 데이터가 수신 되었을 때 알려주는 메소드이다. 
     * 수신된 데이터는 네티의 ByteBuf 객체에 저장되어 있으며 두번째 인자 msg를 통해 접근가능하다. 
     * @param ctx 
     * @param msg 메시지
     * @throws Exception
     */
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LogUtil.traceLog.debug("channelRead");
        
        long start = System.currentTimeMillis();
        ByteBuf buf = (ByteBuf) msg;
        int length = buf.readableBytes();
        LogUtil.traceLog.debug("::length::" + length);
        
        byte[] dst = new byte[length];
        buf.getBytes(0, dst, 0, length);
        
        // binary log 
        LogUtil.binaryLog.info(ConvertUtil.getByte2HexStringUpper(dst, 0, dst.length));
        
        // header parsing
        SPU command = service.getHeader(dst);
        LogUtil.traceLogInfo(command, "=========== START ===========");
        LogUtil.traceLogInfo(command, command.toString());
        
        // check etx 
        if(!CapstoneConstant.ETX.equals(command.getEtx())) {
            LogUtil.traceLogInfo(command, "Not All Packets Received");
            nack(ctx);
            throw new Exception("Not all packets received");
        }
        
//         check sum ; byte sum ; 0 ~ 255
         if( getChkSum(dst, length, command) != command.getChkSum() ) {
             LogUtil.traceLogInfo(command, "Dismatch chkSum");
             nack(ctx);
             throw new Exception("Dismatch chkSum");
         }
        
        //run !!
        service.run(dst, command);
        
        // response ack
        ack(ctx, command);
        
        // 필요시 connection close
        boolean isTest = false;
        if(isTest) {
//            Thread.sleep(300);
            ctx.close();
            LogUtil.traceLogInfo(command, "Connection close");
        }
        
        long end = System.currentTimeMillis();
        LogUtil.traceLogInfo(command, "Elapsed Time :" + (end-start)/1000.0f + "seconds");
        LogUtil.traceLogInfo(command, "===========  END  ===========");
        
        //system log
        Map<String,Object> jsonMap = new HashMap<String,Object>();
        jsonMap.put("result", "true");
        jsonMap.put("damId", command.getDamId());
        jsonMap.put("sIndex", command.getsIndex());
        jsonMap.put("ip", ((InetSocketAddress)ctx.channel().remoteAddress()).getAddress());
        jsonMap.put("packet", command.getRawData());
        jsonMap.put("startTime", getTime(start));    //시작시간
        jsonMap.put("endTime", getTime(end));        //종료시간
        jsonMap.put("time", (end-start)/1000.0f);    //소요시간
        jsonMap.put("errType", "");
        jsonMap.put("errMsg", "");
        
        LogUtil.systemLog.info(getJsonString(jsonMap));

        // send message to Kafka
        sService.sendMsg(producer, getJsonString(new KafkaMessage(command).toJsonMap()));
    }

    /**
     * 데이터 수신이 완료되었을음 알려준다.
     * 즉 데이터를 다 읽어 없을때 발생하는 이벤트가 바로 channelReadComplete이다.
     * @param ctx 
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        LogUtil.traceLog.debug("channelReadComplete");
        ctx.close();
//        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
//                .addListener(ChannelFutureListener.CLOSE);
    }
    
    /**
     * channelActive와 반대로 채널이 비활성화 되었을 때 발생한다. 
     * 해당 이벤트가 발생한 이후에는 채널에 해당 입출력 작업을 수행 못한다.
     * @param ctx 
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LogUtil.traceLog.debug("channelInactive");
    }
    
    /**
     * channelRegistered의 이벤트와 반대로 채널이 이벤트 루프에서 제거 되었을 때 발생한다. 
     * 이거 또한 채널에 입출력 작업을 수행하지 못한다.
     * @param ctx 
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        LogUtil.traceLog.debug("channelUnregistered");
    }
    
    
    /**
     * ChannelPipeline의 다음 ChannelHandler로 전달하기 위해 
     * ChannelHandlerContext.fireExceptionCaught (Throwable)를 호출합니다. 
     * 하위 클래스는이 메서드를 재정 의하여 동작을 변경할 수 있습니다.
     * @param ctx 
     * @param cause 전달된 오류
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        
        LogUtil.traceLog.error("::exceptionCaught error::" + cause.getMessage());
        LogUtil.errorLog.error("::exceptionCaught error::", cause);
        
        //system log
        Map<String,Object> jsonMap = new HashMap<String,Object>();
        jsonMap.put("result", "false");
        jsonMap.put("damId", "");
        jsonMap.put("sIndex", "");
        jsonMap.put("ip", ((InetSocketAddress)ctx.channel().remoteAddress()).getAddress());
        jsonMap.put("packet", "");
        jsonMap.put("startTime", "");
        jsonMap.put("endTime", "");
        jsonMap.put("time", getTime());    //현재시간
        jsonMap.put("errType", cause.getMessage());
        jsonMap.put("errMsg", cause);
        
        LogUtil.systemLog.info(getJsonString(jsonMap));
        
        // 2019.06.25 오류가 발생하면 연결 끊는다 ; NACK 는 별도로 보낸다
        ctx.close();
    }
    
    /**
     * 성공시 기기에 ACK 전달
     * ack </br> 
     * 2019.10.14 update by william ; version & index int형으로 수정
     * @param ctx
     * @param command
     */
    private static void ack(ChannelHandlerContext ctx, SPU command) {
        
        ByteBuf message = Unpooled.buffer(14);
        
        message.writeByte(CapstoneConstant.BYTE_STX);
        message.writeByte(ConvertUtil.getInt2Byte4(NumberUtils.toInt(command.getVer(), 0))); //ver
        message.writeBytes(ConvertUtil.hexToByteArray(command.getDamId()));
        message.writeByte(0x00); //len
        message.writeByte(0x00); //data
        message.writeByte(ConvertUtil.getInt2Byte4(NumberUtils.toInt(command.getsIndex(), 0))); //index
        message.writeByte(0x00); //chksum
        message.writeByte(CapstoneConstant.BYTE_ETX);
        
        ctx.writeAndFlush(message);
        LogUtil.traceLogInfo(command, "ACK OK");
    }

    /**
     * 실패시 기기에 NACK 전달
     * @param ctx ChannelHandlerContext
     */
    private static void nack(ChannelHandlerContext ctx) {
        ByteBuf message = Unpooled.buffer(14);
        final String str = "F102A10000000000010500001F";
        
        int nIndex = 0;
        for (int i = 0; i < str.length() / 2; i ++) {
            String temp = str.substring(nIndex, nIndex + 2);
            nIndex += 2;
            message.writeByte(Integer.valueOf(String.valueOf(temp), 16));
        }
        
        ctx.writeAndFlush(message);
        LogUtil.traceLog.info("===========  NACK  ===========");
    }
    
    /**
     * 체크섬 계산
     * @param dst 데이터
     * @param command SPU 데이터
     * @return 체크섬
     */
    private static int getChkSum(byte[] dst, int length, SPU command) {
        int sum = 0;
        for(int idx = 10; idx < length-2; idx++) sum += dst[idx];

        int chkSum = sum % 256;
        LogUtil.traceLogInfo(command, "chkSum : " + chkSum);

        return chkSum;
    }
    
    /**
     * 시간을 yyyy-MM-dd HH:mm:ss.SSS 형식으로 변환
     * @return 날씨 Format string (yyyy-MM-dd HH:mm:ss.SSS)
     */
    private static String getTime(long time) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return simpledateformat.format(new Date(time));
    }
    
    /**
     * 현재 시간을 yyyy-MM-dd HH:mm:ss.SSS 형식으로 변환
     * @return 2019-01-01 19:00:00.000
     */
    private static String getTime() {
        return getTime(System.currentTimeMillis());
    }
    
    /**
     * Map<String, Object> 형식을 Json 으로 변경
     * @param jsonMap
     * @return
     * @throws Exception
     */
    private static String getJsonString(Map<String, Object> jsonMap) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(Include.ALWAYS);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return objectMapper.writeValueAsString(jsonMap);
    }
    
}
