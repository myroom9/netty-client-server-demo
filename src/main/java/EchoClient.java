import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

public class EchoClient {
    // 호스트를 정의합니다. 로컬 루프백 주소를 지정합니다.
    private static final String HOST = "localhost";
    // 접속할 포트를 정의합니다.
    private static final int PORT = 4200;
    // 메시지 사이즈를 결정합니다.
    static final int MESSAGE_SIZE = 256;

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();

        try{
            System.out.println("소켓 통신 시작!");
            Bootstrap b = new Bootstrap();

            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            ChannelPipeline cp = sc.pipeline();
                            cp.addLast(new EchoClientHandler());
                        }
                    });

            // ChannelFuture cf = b.connect(HOST, PORT).sync();
            ChannelFuture cf = b.connect(HOST, PORT).sync();
            // cf.channel().closeFuture().sync();
            cf.channel().closeFuture().sync();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            group.shutdownGracefully();
        }
    }
}