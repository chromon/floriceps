package com.floriceps.rpc_server.handler;

import com.floriceps.rpc_core.message.RpcRequestMessage;
import com.floriceps.rpc_core.message.RpcResponseMessage;
import static com.floriceps.rpc_core.utils.LogUtils.Log;

import com.floriceps.rpc_core.service.Service;
import com.floriceps.rpc_core.service.ServiceRegister;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * RPC 请求消息处理器，处理服务器端收到的 RPC 请求。
 */
@ChannelHandler.Sharable
public class RpcRequestMessageHandler extends SimpleChannelInboundHandler<RpcRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                RpcRequestMessage rpcRequestMessage) throws Exception {
        RpcResponseMessage responseMessage = new RpcResponseMessage();
        responseMessage.setSequenceId(rpcRequestMessage.getSequenceId());

        try {
            // 接收到的接口类
            String serviceName = rpcRequestMessage.getServiceName();
            // 根据接口名查找到对应服务实例
            Service service = ServiceRegister.findService(serviceName);
            // 新建服务实例（如果没有的话）
            service.newInstance();
            // 调用方法
            Object result = service.invokeMethod(rpcRequestMessage.getMethodName(), rpcRequestMessage.getParameterTypes(),
                    rpcRequestMessage.getParameterValues());

            responseMessage.setReturnValue(result);
        } catch (Exception e) {
            // 设置的消息最大传输帧为 1024，如果有异常，则异常的长度很有可能超过 1024
            // 所以只返回部分异常消息。
            responseMessage.setExceptionValue(new Exception("RPC request message handler: "
                    + e.getCause().getMessage()));
            e.printStackTrace();
        }

        channelHandlerContext.writeAndFlush(responseMessage).addListener(promise -> {
            if (!promise.isSuccess()) {
                Log.error(promise.cause());
            }
        });
    }
}
