package github.javaguide.netty.kyro.serialize;

/**
 * @author shuang.kou
 * @createTime 2020年05月13日 19:29:00
 */
public  interface Serializer {
    byte[] serialize(Object obj);

    <T> T deserialize(byte[] bytes,Class<T> clazz);
}
