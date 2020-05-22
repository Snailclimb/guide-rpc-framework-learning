package github.javaguide.netty.kyro.codec;

import github.javaguide.netty.kyro.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;

/**
 * @author shuang.kou
 * @createTime 2020年05月13日 19:43:00
 */
@AllArgsConstructor
public class NettyKryoEncoder extends MessageToByteEncoder<Object> {
    private Serializer serializer;
    private Class<?> genericClass;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) {
        if (genericClass.isInstance(o)) {
            // 1. 将对象转换为byte
            byte[] body = serializer.serialize(o);
            // 2. 读取消息的长度
            int dataLength = body.length;
            // 3. 先将消息长度写入，也就是消息头
            byteBuf.writeInt(dataLength);
            byteBuf.writeBytes(body);
        }
    }
}
