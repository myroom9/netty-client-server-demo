import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Random;

public class EchoClientHandler extends ChannelInboundHandlerAdapter {
    // private final ByteBuf message;

    private String getTempValue(int size) {
        Random random = new Random();

        return StringUtils.leftPad(Integer.toHexString(random.nextInt(250)), size, '0');
    }

    // 초기화
    public EchoClientHandler() throws InterruptedException {
        /*message = Unpooled.buffer(EchoClient.MESSAGE_SIZE);
        // 샘플 데이터
        // byte[] str = "000000000201021750470000006420200207001001010059000a00ca002d001d".getBytes();
        String prefixData = "00000000020102175047000000642020020700100101";
        String temperatureValue = getTempValue();
        String gasValue = getTempValue();
        String smokeValue = getTempValue();
        String smokeDefaultValue = getTempValue();
        String frameValue = getTempValue();

        String tempTotalData = prefixData + temperatureValue + gasValue + smokeValue + smokeDefaultValue + frameValue;
        System.out.println("tempTotalData = " + tempTotalData);

        message.writeBytes(tempTotalData.getBytes());
        message.writeByte((byte) 0x11);*/
    }

    // 채널이 활성화 되면 동작할 코드를 정의합니다.
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        while(true) {
            ByteBuf message = Unpooled.buffer(EchoClient.MESSAGE_SIZE);

            Thread.sleep(5000);
            // 샘플 데이터
            // byte[] str = "000000000201021750470000006420200207001001010059000a00ca002d001d".getBytes();
            // DEAD FF000201 00000000 00000012 123456 00 00 00 00 00 0000 0000 0000 00 00 00 00 00 00 00 0000 0000 00 0000000000000000000000 00 00 00 00 0000 000000000000000000000000
            // String prefixData = "00000000020102175047000000642020020700100101";
            String prefixData = "DEADFF00020100000000000000121234560000000000";
            String temperatureSensorValue = getTempValue(4);
            String gasSensorValue = getTempValue(4);
            String smogSensorValue = getTempValue(4);
            String temperatureAlarm = getTempValue(2);
            String gasAlarm = getTempValue(2);
            String smogAlarm = getTempValue(2);
            String manualAlarm = getTempValue(2);
            String autoAlarm = getTempValue(2);
            String autoAlarmEnable = getTempValue(2);
            String externalPower = getTempValue(2);
            String smogSensorBaseValue = getTempValue(4);
            String frameSensorValue = getTempValue(4);
            String frameAlarm = getTempValue(2);
            String reserveData = getTempValue(22);
            String sensorRssl = getTempValue(2);
            String apRssl = getTempValue(2);
            String temperatureValue = getTempValue(2);
            String smogValue = getTempValue(2);
            String reservedData2 = getTempValue(4);
            String packetStr = getTempValue(24);

            /*String temperatureValue = getTempValue();
            String gasValue = getTempValue();
            String smokeValue = getTempValue();
            String smokeDefaultValue = getTempValue();
            String frameValue = getTempValue();*/

            String tempTotalData = prefixData + temperatureSensorValue + gasSensorValue + smogSensorValue + temperatureAlarm + gasAlarm + smogAlarm
                    + manualAlarm + autoAlarm + autoAlarmEnable + externalPower + smogSensorBaseValue + frameSensorValue + frameAlarm + reserveData
                    + sensorRssl + apRssl + temperatureValue + smogValue + reservedData2 + packetStr + "FA11";
            System.out.println("tempTotalData = " + tempTotalData);

            message.writeBytes(tempTotalData.getBytes());
            message.writeByte((byte) 0x11);

            // 메시지를 쓴 후 플러쉬합니다.
            ctx.writeAndFlush(message);
        }
    }

    public byte[] getMessageID(byte[] body) {

        int readCount = 0;
        // ByteBuf buf = Unpooled.buffer(body.length);
        int bodySize = body.length;

        for (int i = 0; i < bodySize; i++) {
            if (body[i] == (byte)0x11) {
                break;
            }
            readCount++;
        }

        byte[] messageID = new byte[readCount];

        messageID = Arrays.copyOfRange(body, 0, readCount);

        return messageID;

    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)throws Exception {
        // 받은 메시지를 ByteBuf형으로 캐스팅합니다.
        ByteBuf byteBufMessage = (ByteBuf) msg;
        // 읽을 수 있는 바이트의 길이를 가져옵니다.
        int size = byteBufMessage.readableBytes();

        // 읽을 수 있는 바이트의 길이만큼 바이트 배열을 초기화합니다.
        byte [] byteMessage = new byte[size];
        // for문을 돌며 가져온 바이트 값을 연결합니다.
        for(int i = 0 ; i < size; i++){
            byteMessage[i] = byteBufMessage.getByte(i);
        }
        byte[] message =getMessageID(byteMessage);
        System.err.println(new String(message));
        // 바이트를 String 형으로 변환합니다.
//        String str = new String(byteMessage);

        // 결과를 콘솔에 출력합니다.
//        System.out.println(str);

        // 그후 컨텍스트를 종료합니다.
        ctx.close();
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
