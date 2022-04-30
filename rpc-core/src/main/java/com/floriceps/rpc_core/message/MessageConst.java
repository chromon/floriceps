package com.floriceps.rpc_core.message;

/**
 * 消息编码中的常量。
 */
public class MessageConst {

    /**
     * 魔数
     */
    public static final int MAGIC_NUM = 0xacebabe;

    /**
     * 版本号
     */
    public static final byte VERSION = 0x1;

    /**
     * 对齐填充
     */
    public static final byte PADDING = 0x7f;
}
