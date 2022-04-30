package com.floriceps.rpc_provider.provide;

import com.floriceps.rpc_core.service.Service;
import com.floriceps.rpc_core.service.ServiceRegister;
import com.floriceps.rpc_server.server.RpcServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务提供者。
 */
public class Provider {

    public static final Logger log = LoggerFactory.getLogger(Provider.class);

    /**
     * 提供服务
     */
    public void start() {
        // 启动服务器
        RpcServer.startServer();
    }

    /**
     * 将服务接口的实现注册到服务器中。
     * @param serviceInterface 服务接口
     * @param serviceImplName 服务实现
     */
    public void registry(String serviceInterface, String serviceImplName) {
        Service serviceImpl = new Service(serviceImplName);
        if (!serviceImpl.classIsAvailable()) {
            log.error("Can not create service, check the service name.");
        } else {
            // 注册服务
            ServiceRegister.registerService(serviceInterface, serviceImpl);
        }
    }
}
