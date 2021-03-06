package com.floriceps.rpc_core.message;

import java.io.Serializable;

/**
 * 用于传输的消息父类。
 */
public abstract class Message implements Serializable {

    /**
     * 消息序列号。
     */
    private int sequenceId;

    /**
     * 消息类型编号。
     */
    private int messageType;

    /**
     * 抽象方法用于获取当前消息类的类型。
     * @return 消息类的类型。
     */
    public abstract int getMessageType();

    /**
     * 根据消息类型，获取对应的消息 Class。
     * @param messageType 消息类型（字节）。
     * @return 消息类 Class。
     */
    public static Class<? extends Message> getMessageClass(int messageType) {
        return MessageType.messageClasses.get(messageType);
    }

    /**
     * 获取当前消息的序列号。
     * @return 消息的序列号。
     */
    public int getSequenceId() {
        return sequenceId;
    }

    /**
     * 设置当前消息的序列号
     * @param sequenceId 序列号。
     */
    public void setSequenceId(int sequenceId) {
        this.sequenceId = sequenceId;
    }
}
