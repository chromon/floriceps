package com.floriceps.client;

import com.floriceps.codec.MessageCodecSharable;
import com.floriceps.config.Global;
import com.floriceps.protocol.ProtocolFrameDecoder;

import static com.floriceps.utils.LogUtils.Log;

import com.floriceps.handler.client.RpcResponseMessageHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Rpc 客户端
 */
public class RpcClient {

    private static volatile Channel channel = null;
    private static final Object LOCK = new Object();

    /**
     * 获取唯一 Channel （单例模式，双重校验锁）。
     * @return channel。
     */
    public static Channel getChannel() {
        if (channel != null) {
            return channel;
        }
        synchronized (LOCK) {
            if (channel != null) {
                return channel;
            }
            initChannel();
            return channel;
        }
    }

    /**
     * 初始化 Channel，channel 为长链接，可以处理多次 RPC 请求。
     */
    private static void initChannel() {
        NioEventLoopGroup group = new NioEventLoopGroup();

        // 日志 Handler
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.INFO);
        // 消息编码 Handler
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();

        // RPC 响应消息处理器
        RpcResponseMessageHandler RPC_RESPONSE_HANDLER = new RpcResponseMessageHandler();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(group);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ProtocolFrameDecoder());
                ch.pipeline().addLast(LOGGING_HANDLER);
                ch.pipeline().addLast(MESSAGE_CODEC);
                ch.pipeline().addLast(RPC_RESPONSE_HANDLER);
            }
        });

        try {
            // 建立连接
            channel = bootstrap.connect(Global.SERVER_HOST, Global.SERVER_PORT).sync().channel();
            // 使用异步关闭连接模式，sync() 方法为同步关闭，会一直阻塞直到连接关闭。
            channel.closeFuture().addListener(future -> {
                group.shutdownGracefully();
            });
        } catch (Exception e) {
            Log.error("Client error:", e);
        }
    }
}
