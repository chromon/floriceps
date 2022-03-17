package com.floriceps.message;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息类型。
 */
public class MessageType {

    /**
     * 消息类型编号与消息类映射 map。
     */
    public static final Map<Integer, Class<? extends Message>> messageClasses = new HashMap<>();

    /**
     * RPC 请求消息。
     */
    public static final int RPC_MESSAGE_TYPE_REQUEST = 101;

    /**
     * RPC 响应消息。
     */
    public static final int RPC_MESSAGE_TYPE_RESPONSE = 102;

    static {
        messageClasses.put(RPC_MESSAGE_TYPE_REQUEST, RpcRequestMessage.class);
        messageClasses.put(RPC_MESSAGE_TYPE_RESPONSE, RpcResponseMessage.class);
    }
}
