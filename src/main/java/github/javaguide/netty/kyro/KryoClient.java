package github.javaguide.netty.kyro;

import github.javaguide.netty.kyro.dto.RpcRequest;
import github.javaguide.netty.kyro.dto.RpcResponse;
import github.javaguide.netty.kyro.serialize.KryoSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author shuang.kou
 * @createTime 2020年05月13日 20:48:00
 */
public class KryoClient {
    private String host;
    private int port;
    private RpcRequest rpcRequest;

    public KryoClient(String host, int port, RpcRequest rpcRequest) {
        this.host = host;
        this.port = port;
        this.rpcRequest = rpcRequest;
    }

    public void send() {
        // 配置客户端NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 配置启动辅助类
            Bootstrap b = new Bootstrap();
            KryoSerializer kryoSerializer = new KryoSerializer();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new NettyKryoDecoder(kryoSerializer, RpcResponse.class));
                            ch.pipeline().addLast(new NettyKryoEncoder(kryoSerializer, RpcRequest.class));
                            ch.pipeline().addLast(new KryoClientHandler(rpcRequest));
                        }
                    });
            // 异步连接服务器，同步等待连接成功
            ChannelFuture f = b.connect(host, port).sync();
            // 等待连接关闭
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName("interface")
                .methodName("hello").build();
        KryoClient kryoClient = new KryoClient("127.0.0.1", 8889, rpcRequest);
        kryoClient.send();
    }
}
