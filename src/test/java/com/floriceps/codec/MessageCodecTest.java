package com.floriceps.codec;

import com.floriceps.message.RpcRequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.junit.Test;

public class MessageCodecTest {
    @Test
    public void testEncoder() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LoggingHandler(LogLevel.INFO),
                // 最大长度，长度字段偏移量，长度字段，长度是否需要调整，是否需要去掉前面部分
                new LengthFieldBasedFrameDecoder(1024, 12,
                        4, 0, 0),
                new MessageCodec());
        RpcRequestMessage request = new RpcRequestMessage(1, "com.test.Hello", "sayHello", String.class, new Class[] {String.class}, new Object[] {"ellery"});
        // 出站写 encode
        channel.writeOutbound(request);

        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null, request, byteBuf);
        //channel.writeInbound(byteBuf);

        ByteBuf b1 = byteBuf.slice(0, 100);
        ByteBuf b2 = byteBuf.slice(100, byteBuf.readableBytes() - 100);
        b1.retain();
        channel.writeInbound(b1);
        channel.writeInbound(b2);
    }
}
