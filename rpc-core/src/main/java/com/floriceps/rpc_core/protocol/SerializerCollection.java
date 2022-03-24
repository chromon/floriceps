package com.floriceps.rpc_core.protocol;

import com.floriceps.rpc_core.config.Config;
import com.floriceps.rpc_core.protocol.serializer.GJsonSerializer;
import com.floriceps.rpc_core.protocol.serializer.JavaSerializer;
import com.floriceps.rpc_core.protocol.serializer.Serializer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 序列化实现集合。
 */
public class SerializerCollection {

    /**
     * 序列化实现映射。
     */
    private static Map<String, Serializer> serializerMap;

    /**
     * 序列化类型映射。
     */
    private static Map<String, Integer> serializerTypeMap;

    /**
     * 序列化类型与序列化方法映射。
     */
    private static Map<Integer, Serializer> typeSerializerMap;

    private static AtomicInteger type = new AtomicInteger();

    static {
        serializerMap = new HashMap<>();
        serializerTypeMap = new HashMap<>();
        typeSerializerMap = new HashMap<>();

        // Java IO
        addSerializer(type.incrementAndGet(), "Java", new JavaSerializer());

        // Gson Json
        addSerializer(type.incrementAndGet(), "Json", new GJsonSerializer());
    }

    /**
     * 根据类型、名称和序列化实现添加序列化操作。
     * @param type 序列化类型。
     * @param name 序列化名称。
     * @param serializer 序列化实现。
     */
    private static void addSerializer(int type, String name, Serializer serializer) {
        serializerMap.put(name, serializer);
        serializerTypeMap.put(name, type);
        typeSerializerMap.put(type, serializer);
    }

    /**
     * 获取序列化实现对象。
     * @return 序列化实现对象。
     */
    public static Serializer getSerializer() {
        String key = Config.getSerializer();
        if (key == null) {
            return serializerMap.get("Java");
        }
        if (!serializerMap.containsKey(key)) {
            throw new RuntimeException("The Serializer key not exists!");
        }
        return serializerMap.get(key);
    }

    /**
     * 获取序列化实现对象。
     * @return 序列化实现对象。
     */
    public static int getSerializerType () {
        String key = Config.getSerializer();
        if (key == null) {
            return serializerTypeMap.get("Java");
        }
        if (!serializerTypeMap.containsKey(key)) {
            throw new RuntimeException("The Serializer type key not exists!");
        }
        return serializerTypeMap.get(key);
    }

    /**
     * 根据类型获取相关序列化方法。
     * @param type 序列化类型。
     * @return 序列化实现方法。
     */
    public static Serializer getSerializerByType(int type) {
        if (!typeSerializerMap.containsKey(type)) {
            throw new RuntimeException("The Serializer type key not exists!");
        }
        return typeSerializerMap.get(type);
    }
}
