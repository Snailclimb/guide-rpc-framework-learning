package github.javaguide.netty.kyro.serialize;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import de.javakaffee.kryoserializers.SynchronizedCollectionsSerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author shuang.kou
 * @createTime 2020年05月13日 19:29:00
 */
public class KryoSerializer implements Serializer {
    private final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(()->{
        Kryo kryo = new Kryo();
        SynchronizedCollectionsSerializer.registerSerializers(kryo);
        return kryo;
    });

    public KryoSerializer() {
    }

    @Override
    public byte[] serialize(Object obj) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             Output output = new Output(byteArrayOutputStream)) {
            Kryo kryo = kryoThreadLocal.get();
            kryo.writeClassAndObject(output, obj);
            return output.toBytes();
        } catch (Exception e) {
            throw new SerializeException(e);
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes,Class<T> clazz) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             Input input = new Input(byteArrayInputStream)) {
            Kryo kryo = kryoThreadLocal.get();
            Object o = kryo.readClassAndObject(input);
            return clazz.cast(o);
        } catch (Exception e) {
            throw new SerializeException(e);
        }
    }
}
