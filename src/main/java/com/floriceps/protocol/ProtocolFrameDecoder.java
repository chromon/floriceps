package com.floriceps.protocol;

import com.floriceps.config.Global;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 数据传输协议帧。
 */
public class ProtocolFrameDecoder extends LengthFieldBasedFrameDecoder {

    /**
     * 每个帧的最大长度 1024，长度字段偏移量 12，长度字段长度 4。
     */
    public ProtocolFrameDecoder() {
        this(Global.MAX_FRAME_LENGTH, 12, 4, 0, 0);
    }

    public ProtocolFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }
}
