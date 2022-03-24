package com.floriceps.rpc_core.config;

import com.floriceps.rpc_core.protocol.SerializerCollection;
import com.floriceps.rpc_core.protocol.serializer.Serializer;

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

    /**
     * 连接超时时间。
     */
    public static final int CONNECT_TIMEOUT_MILLIS = Config.getConnectTimeout();

    /**
     * 数据传输的最大帧数。
     */
    public static final int MAX_FRAME_LENGTH = Config.getMaxFrameLength();
}
