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
        byte[] b = new byte[74];
        while(true) {
            ByteBuf message = Unpooled.buffer(EchoClient.MESSAGE_SIZE);

            Thread.sleep(5000);
            // 샘플 데이터
            // byte[] str = "000000000201021750470000006420200207001001010059000a00ca002d001d".getBytes();
            // DEAD FF000201 00000000 00000012 123456 00 00 00 00 00 0000 0000 0000 00 00 00 00 00 00 00 0000 0000 00 0000000000000000000000 00 00 00 00 0000 000000000000000000000000
            // String prefixData = "00000000020102175047000000642020020700100101";
            b[0] = (byte) 0xde;
            b[1] = (byte) 0xad;

            // type
            b[2] = (byte) 0xff;
            b[3] = (byte) 0x00;
            b[4] = (byte) 0x02;
            b[5] = (byte) 0x01;

            // timestamp
            b[6] = (byte) 0xd3;
            b[7] = (byte) 0xd5;
            b[8] = (byte) 0x22;
            b[9] = (byte) 0x16;

            // 패킷 동작 ID
            b[10] = (byte) 0x00;
            b[11] = (byte) 0x00;
            b[12] = (byte) 0x00;
            b[13] = (byte) 0x12;

            // 콘솔 device ID
            b[14] = (byte) 0x31;
            b[15] = (byte) 0x32;
            b[16] = (byte) 0x33;
            b[17] = (byte) 0x34;
            b[18] = (byte) 0x35;
            b[19] = (byte) 0x36;

            // sensor idx
            b[20] = (byte) 0x00;

            // sensor device number => 100
            b[21] = (byte) 0x64;

            // sen_seq
            b[22] = (byte) 0x00;

            // sen_cmd
            b[23] = (byte) 0x50;

            // error code
            b[24] = (byte) 0x00;

            // temperature sensor value
            b[25] = (byte) 0x01;
            b[26] = (byte) 0x10;

            // gas sensor value
            b[27] = (byte) 0x00;
            b[28] = (byte) 0x03;

            // smog sensor value
            b[29] = (byte) 0x01;
            b[30] = (byte) 0xCE;

            // temperature alarm
            b[31] = (byte) 0x30;
            // gas alarm
            b[32] = (byte) 0x30;

            // smog alarm
            b[33] = (byte) 0x30;

            // manual alarm
            b[34] = (byte) 0x30;

            // autoAlarm
            b[35] = (byte) 0x30;

            // auto alarm enable
            b[36] = (byte) 0x31;

            // ext pwr
            b[37] = (byte) 0x01;

            // smog sensor base value
            b[38] = (byte) 0x01;
            b[39] = (byte) 0xd0;

            // frame sensor value
            b[40] = (byte) 0x00;
            b[41] = (byte) 0x00;

            // frame alarm
            b[42] = (byte) 0x30;

            // RFU
            b[43] = (byte) 0x00;
            b[44] = (byte) 0x00;
            b[45] = (byte) 0x00;
            b[46] = (byte) 0x00;
            b[47] = (byte) 0x00;
            b[48] = (byte) 0x00;
            b[49] = (byte) 0x00;
            b[50] = (byte) 0x00;
            b[51] = (byte) 0x00;
            b[52] = (byte) 0x00;
            b[53] = (byte) 0x00;

            // sensor rssl
            b[54] = (byte) 0x00;

            // ap rssl
            b[55] = (byte) 0x00;

            // temperature val
            b[56] = (byte) 0xFF;

            // smog val
            b[57] = (byte) 0xFF;

            // rsv
            b[58] = (byte) 0x00;
            b[59] = (byte) 0x00;

            // packet_str
            b[60] = (byte) 0x5b;
            b[61] = (byte) 0x53;
            b[62] = (byte) 0x45;
            b[63] = (byte) 0x4e;
            b[64] = (byte) 0x53;
            b[65] = (byte) 0x4f;
            b[66] = (byte) 0x52;
            b[67] = (byte) 0x44;
            b[68] = (byte) 0x41;
            b[69] = (byte) 0x54;
            b[70] = (byte) 0x41;
            b[71] = (byte) 0x5d;

            b[72] = (byte) 0xfa;
            b[73] = (byte) 0x11;

            message.writeBytes(b);

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
