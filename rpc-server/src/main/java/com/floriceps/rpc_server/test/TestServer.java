package com.floriceps.rpc_server.test;

import com.floriceps.rpc_server.server.RpcServer;
import com.floriceps.rpc_core.service.Service;
import com.floriceps.rpc_core.service.ServiceRegister;

import static com.floriceps.rpc_core.utils.LogUtils.Log;

public class TestServer {
    public static void main(String[] args) {
        Service serviceImpl = new Service(
                "com.floriceps.rpc_provider.service.impl.ConsolePrintService");
        if (!serviceImpl.classIsAvailable()) {
            Log.error("Can not create service, check the service name.");
        } else {
            String interfaceName = "com.floriceps.rpc_service_api.service.PrintService";
            // 注册服务
            ServiceRegister.registerService(interfaceName, serviceImpl);
        }

        // 启动服务器
        RpcServer.startServer();
    }
}
