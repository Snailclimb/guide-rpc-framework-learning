package github.javaguide.netty.echo;

/**
 * @author shuang.kou
 * @createTime 2020年05月14日 20:46:00
 */

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * Sends one message when a connection is open and echoes back any received
 * data to the server.  Simply put, the echo client initiates the ping-pong
 * traffic between the echo client and server by sending the first message to
 * the server.
 */
public final class HelloClient {

    private final String host;
    private final int port;
    private final String message;

    public HelloClient(String host, int port, String message) {
        this.host = host;
        this.port = port;
        this.message = message;
    }

    private void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //创建客户端启动引导/辅助类：Bootstrap
            Bootstrap b = new Bootstrap();
            //指定线程模型
            b.group(group)
                    //指定 IO 模型
                    //和 BIO 编程模型中 Socket相对应
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            // 这里可以自定义消息的业务处理逻辑
                            p.addLast(new HelloClientHandler(message))
                            .addLast(new LineBasedFrameDecoder(1024));
                        }
                    });
            // 尝试建立连接
            ChannelFuture f = b.connect(host, port).sync();
            // 等待连接关闭
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws Exception {
        new HelloClient("127.0.0.1",8080, "你好,你真帅啊！哥哥！\n").start();
    }
}
