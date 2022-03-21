package com.floriceps.codec;

import com.floriceps.config.Global;
import com.floriceps.message.Message;
import com.floriceps.protocol.SerializerCollection;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.MessageToMessageCodec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import static com.floriceps.utils.LogUtils.Log;

/**
 * 消息信息编解码（Sharable 版）。
 * 由于当前 handler 不保存状态，可以安全地在多线程下被共享。
 * 必须和 LengthFIeldBasedFrameDecoder 结合使用。
 *
 * 消息格式：
 *     MagicNum:        4 字节；
 *     Version:         1 字节；
 *     SerializerType:  1 字节；
 *     MessageType:     1 字节；
 *     SequenceId:      4 字节；
 *     Blank:           1 字节；
 *     MessageLength:   4 字节；
 *
 *     MessageContent
 */
@ChannelHandler.Sharable
public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, Message> {

    /**
     * 出站，将 Message 编码为 ByteBuf。
     *
     * @param channelHandlerContext ctx
     * @param message 待编码的消息对象。
     * @param list 将编码之后的 ByteBuf 对象存储，在其他 handler 中使用。
     * @throws Exception 编码异常。
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message,
                          List<Object> list) throws Exception {
        ByteBuf byteBuf = channelHandlerContext.alloc().buffer();

        // 魔数
        byteBuf.writeInt(0xacebabe);
        // 版本号
        byteBuf.writeByte(1);
        // 序列化方式
        byteBuf.writeByte(Global.SERIALIZER_TYPE);
        // 消息类型
        byteBuf.writeByte(message.getMessageType());
        // 消息序列号
        byteBuf.writeInt(message.getSequenceId());
        // 对齐填充
        byteBuf.writeByte(0xff);

        // 序列化消息
        byte[] bytes = Global.SERIALIZER.serialize(message);
        // 消息长度
        byteBuf.writeInt(bytes.length);

        // 消息内容
        byteBuf.writeBytes(bytes);
        list.add(byteBuf);
    }

    /**
     * 入站，将 ByteBuf 解码为 Message。
     *
     * @param channelHandlerContext ctx
     * @param byteBuf 入站 ByteBuf。
     * @param list 保存解析后数据的列表。
     * @throws Exception 解码异常。
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf,
                          List<Object> list) throws Exception {
        // 魔数
        int magicNum = byteBuf.readInt();
        // 版本号
        byte version = byteBuf.readByte();
        // 序列化类型
        byte serializerType = byteBuf.readByte();
        // 消息类型
        byte messageType = byteBuf.readByte();
        // 消息序列号
        int sequenceId = byteBuf.readInt();
        // 对齐填充
        byteBuf.readByte();
        // 消息长度
        int length = byteBuf.readInt();

        // 消息内容
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes, 0, length);

        // 获取具体的消息类型
        Class<? extends Message> messageClass = Message.getMessageClass(messageType);
        // 反序列化
        Message message = (Message) SerializerCollection.getSerializerByType(serializerType)
                .deserialize(messageClass, bytes);

        Log.info(magicNum + ", " + version + ", " + serializerType + ", "
                + messageType + ", " + sequenceId + ", " + length);

        // 解析的数据需要保存到 list 中，提供给下一个 handler 使用。
        list.add(message);
    }
}
