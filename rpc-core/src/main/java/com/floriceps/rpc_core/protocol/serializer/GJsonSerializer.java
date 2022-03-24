package com.floriceps.rpc_core.protocol.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.charset.StandardCharsets;

/**
 * 使用 gson 处理序列化操作。
 */
public class GJsonSerializer implements Serializer {

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
        Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new ClassCodec()).create();
        String json = new String(bytes, StandardCharsets.UTF_8);
        return gson.fromJson(json, clazz);
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
        Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new ClassCodec()).create();
        String json = gson.toJson(object);
        return json.getBytes(StandardCharsets.UTF_8);
    }
}
