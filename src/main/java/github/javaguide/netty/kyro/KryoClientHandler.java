package github.javaguide.netty.kyro;

import github.javaguide.netty.kyro.dto.RpcRequest;
import github.javaguide.netty.kyro.dto.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @author shuang.kou
 * @createTime 2020年05月13日 20:50:00
 */
public class KryoClientHandler extends ChannelInboundHandlerAdapter {
    private RpcRequest rpcRequest;

    public KryoClientHandler(RpcRequest rpcRequest) {
        this.rpcRequest = rpcRequest;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("client send message: " + rpcRequest.toString());
        ctx.writeAndFlush(rpcRequest);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            RpcResponse body = (RpcResponse) msg;
            System.out.println("client receive msg: " + body);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

