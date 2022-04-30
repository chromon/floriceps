package com.floriceps.rpc_consumer.consume;

import com.floriceps.rpc_client.client.RpcClient;

/**
 * 服务消费者。
 */
public class Consumer {
    /**
     * 提供服务。
     *
     * @param serviceClass
     * @param <T>
     * @return
     */
    public static <T> T getService(Class<T> serviceClass) {
        return RpcClient.getProxyService(serviceClass);
    }
}
