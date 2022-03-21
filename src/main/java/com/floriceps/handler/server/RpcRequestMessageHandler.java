package com.floriceps.handler.server;

import com.floriceps.message.RpcRequestMessage;
import com.floriceps.message.RpcResponseMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import static com.floriceps.utils.LogUtils.Log;

/**
 * RPC 请求消息处理器，处理服务器端收到的 RPC 请求。
 */
@ChannelHandler.Sharable
public class RpcRequestMessageHandler extends SimpleChannelInboundHandler<RpcRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                RpcRequestMessage rpcRequestMessage) throws Exception {
        RpcResponseMessage responseMessage = new RpcResponseMessage();

        try {
            // 反射调用服务
            Class<?> clazz = Class.forName(rpcRequestMessage.getServiceName());
            Constructor<?> constructor = clazz.getConstructor();
            Object instance = constructor.newInstance();
            Method method = clazz.getMethod(rpcRequestMessage.getMethodName(), rpcRequestMessage.getParameterTypes());
            Object invoke = method.invoke(instance, rpcRequestMessage.getParameterValues());

            responseMessage.setSequenceId(rpcRequestMessage.getSequenceId());
            responseMessage.setReturnValue(invoke);
        } catch (Exception e) {
            // 设置的消息最大传输帧为 1024，如果有异常，则异常的长度很有可能超过 1024
            // 所以只返回部分异常消息。
            responseMessage.setExceptionValue(
                    new Exception("RPC request message handler: " + e.getMessage()));
            e.printStackTrace();
        }

        channelHandlerContext.writeAndFlush(responseMessage).addListener(promise -> {
            if (!promise.isSuccess()) {
                Log.error(promise.cause());
            }
        });
    }
}
