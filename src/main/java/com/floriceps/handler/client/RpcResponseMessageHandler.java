package com.floriceps.handler.client;

import com.floriceps.message.RpcResponseMessage;
import static com.floriceps.utils.LogUtils.Log;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * RPC 响应消息处理器，处理客户端从服务器端收到的 RPC 响应。
 */
@ChannelHandler.Sharable
public class RpcResponseMessageHandler extends SimpleChannelInboundHandler<RpcResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                RpcResponseMessage rpcResponseMessage) throws Exception {
        Log.info(rpcResponseMessage);
    }
}
