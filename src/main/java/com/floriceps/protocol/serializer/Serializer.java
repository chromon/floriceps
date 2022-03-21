package com.floriceps.protocol.serializer;

/**
 * 扩展序列化，反序列化接口。
 */
public interface Serializer {

    /**
     * 反序列化。
     *
     * @param clazz 反序列化的目标类型。
     * @param bytes 待反序列化的字节数组。
     * @param <T> 反序列化的目标类型。
     * @return 反序列化后的目标对象。
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);

    /**
     * 序列化。
     *
     * @param object 待序列化的对象。
     * @param <T> 待序列化对象类型。
     * @return 序列化后的字节数组。
     */
    <T> byte[] serialize(T object);
}
