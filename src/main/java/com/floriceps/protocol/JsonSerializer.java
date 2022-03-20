package com.floriceps.protocol;

import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;

/**
 * 使用 gson 处理序列化操作。
 */
public class JsonSerializer implements Serializer {

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
        String json = new String(bytes, StandardCharsets.UTF_8);
        return new Gson().fromJson(json, clazz);
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
        String json = new Gson().toJson(object);
        return json.getBytes(StandardCharsets.UTF_8);
    }
}
