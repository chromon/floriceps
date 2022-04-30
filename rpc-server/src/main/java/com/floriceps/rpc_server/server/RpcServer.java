package com.floriceps.rpc_server.server;

import com.floriceps.rpc_core.Register.ZooKeeperRegistryService;
import com.floriceps.rpc_core.codec.MessageCodecSharable;
import com.floriceps.rpc_core.config.Global;
import com.floriceps.rpc_core.protocol.ProtocolFrameDecoder;
import static com.floriceps.rpc_core.utils.LogUtils.Log;

import com.floriceps.rpc_server.handler.RpcRequestMessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * RPC Server.
 */
public class RpcServer {
    /**
     * 初始化服务器。
     */
    private static void initServer() throws Exception {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.INFO);
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        ZooKeeperRegistryService service = null;

        // RPC 请求消息处理器
        RpcRequestMessageHandler RPC_REQUEST_HANDLER = new RpcRequestMessageHandler();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.group(boss, worker);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ProtocolFrameDecoder());
                    ch.pipeline().addLast(LOGGING_HANDLER);
                    ch.pipeline().addLast(MESSAGE_CODEC);
                    ch.pipeline().addLast(RPC_REQUEST_HANDLER);
                }
            });

            // 服务注册
            service = new ZooKeeperRegistryService(
                    Global.ZK_SERVICE_NAME, Global.SERVER_HOST,
                    Global.SERVER_PORT, "rpc server");;
            service.register();

            Channel channel = serverBootstrap.bind(Global.SERVER_PORT).sync().channel();
            channel.closeFuture().sync();
        } catch (Exception e) {
            Log.error("server error", e);
        } finally {
            service.unRegister();
            service.destroy();

            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    /**
     * 启动服务器
     */
    public static void startServer() {
        try {
            initServer();
        } catch (Exception e) {
            Log.error("start server err:", e);
        }
    }
}
