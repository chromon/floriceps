package com.floriceps.demo;

import com.floriceps.client.RpcClient;
import com.floriceps.message.RpcRequestMessage;
import io.netty.channel.Channel;

import static com.floriceps.utils.LogUtils.Log;

public class ClientTest {
    public static void main(String[] args) {
        RpcRequestMessage req = new RpcRequestMessage(1, "com.floriceps.demo.service.ServerPrintService", "print", String.class, new Class[]{String.class}, new Object[]{"success!"});

        Channel channel = RpcClient.getChannel();
        channel.writeAndFlush(req).addListener(promise -> {
            if (!promise.isSuccess()) {
                Log.error(promise.cause());
            }
        });
    }
}
