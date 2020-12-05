import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder;
import io.netty.util.CharsetUtil;

/**
 * @see "https://shortstories.gitbooks.io/studybook/content/netty_ae30_bcf8_c608_c81c.html"
 * @see "https://swiftymind.tistory.com/71"
 */
public class NettyClient {

    public static void main(String[] args) {
        NettyClient n = new NettyClient();
        n.connect("localhost", 4200);
    }

    ChannelFuture cf;
    EventLoopGroup group;

    public void connect(String host, int port) {

        group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                    .handler(new NettyHttpChannelInit(group));

            // request
            // this.createRequest(host, port, "localhost");

            cf = b.connect(host, port).sync();
//            cf.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        cf.channel().close();
        group.shutdownGracefully();
    }
}
