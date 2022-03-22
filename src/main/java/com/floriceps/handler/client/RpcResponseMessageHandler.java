package com.floriceps.handler.client;

import com.floriceps.message.RpcResponseMessage;
import static com.floriceps.utils.LogUtils.Log;

import com.floriceps.promise.Promises;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Promise;

/**
 * RPC 响应消息处理器，处理客户端从服务器端收到的 RPC 响应。
 */
@ChannelHandler.Sharable
public class RpcResponseMessageHandler extends SimpleChannelInboundHandler<RpcResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                RpcResponseMessage rpcResponseMessage) throws Exception {
        Log.info(rpcResponseMessage);

        // 从服务器返回的结果中获取相关信息并设置到对应 promise 中。
        // 根据序列号获取对应的 promise，并设置返回结果。
        Promise<Object> promise = Promises.PROMISE_MAP.remove(rpcResponseMessage.getSequenceId());

        if (promise != null) {
            Object returnValue = rpcResponseMessage.getReturnValue();
            Exception exceptionValue = rpcResponseMessage.getExceptionValue();

            if (exceptionValue != null) {
                promise.setFailure(exceptionValue);
            } else {
                promise.setSuccess(returnValue);
            }
        }
    }
}
