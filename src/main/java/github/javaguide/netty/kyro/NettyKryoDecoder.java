package github.javaguide.netty.kyro;

import github.javaguide.netty.kyro.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * @author shuang.kou
 * @createTime 2020年05月13日 19:42:00
 */
@AllArgsConstructor
public class NettyKryoDecoder extends ByteToMessageDecoder {
    private Serializer serializer;
    private Class<?> genericClass;
    /**
     * 表示数据流（头部是消息长度）头部的字节数
     */
    private static final int HEAD_LENGTH = 4;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < HEAD_LENGTH) {
            return;
        }
        // 标记当前readIndex的位置
        byteBuf.markReaderIndex();
        // 读取传送过来的消息长度，ByteBuf的 readInt() 方法会让它的readIndex+4
        int dataLength = byteBuf.readInt();
        // 如果读到的消息长度不大于0，这是不应该出现的情况，关闭连接
        if (dataLength <= 0) {
            channelHandlerContext.close();
        }
        // 说明是不完整的报文，重置readIndex
        if (byteBuf.readableBytes() < dataLength) {
            byteBuf.resetReaderIndex();
            return;
        }
        // 至此，读取到一条完整报文
        byte[] body = new byte[dataLength];
        byteBuf.readBytes(body);
        // 将bytes数组转换为我们需要的对象
        Object obj  = serializer.deserialize(body,genericClass);
        list.add(obj);
    }
}
