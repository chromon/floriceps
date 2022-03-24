package com.floriceps.rpc_core.protocol.serializer;

import java.io.*;

/**
 * 使用 JDK IO 流处理序列化操作。
 */
public class JavaSerializer implements Serializer {

    /**
     * 反序列化。
     *
     * @param clazz 反序列化的目标类型。
     * @param bytes 待反序列化的字节数组。
     * @param <T> 反序列化的目标类型。
     * @return 反序列化后的目标对象。
     */
    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        ObjectInputStream ois = null;
        T t = null;

        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            t = (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return t;
    }

    /**
     * 序列化。
     *
     * @param object 待序列化的对象。
     * @param <T> 待序列化对象类型。
     * @return 序列化后的字节数组。
     */
    @Override
    public <T> byte[] serialize(T object) {
        ByteArrayOutputStream bos = null;
        byte[] bytes = null;

        try {
            bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            bytes = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }
}
