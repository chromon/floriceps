package com.floriceps.config;

import com.floriceps.protocol.Serializer;
import com.floriceps.protocol.SerializerCollection;

/**
 * 全局变量。
 */
public class Global {

    /**
     * 服务器主机地址。
     */
    public static final String SERVER_HOST = Config.getServerHost();

    /**
     * 服务器端口号。
     */
    public static final int SERVER_PORT = Config.getServerPort();

    /**
     * 消息序列化算法。
     */
    public static final Serializer SERIALIZER = SerializerCollection.getSerializer();

    /**
     * 消息序列化算法类型。
     */
    public static final int SERIALIZER_TYPE = SerializerCollection.getSerializerType();
}
