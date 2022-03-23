package com.floriceps.demo;

import com.floriceps.server.RpcServer;
import com.floriceps.service.Service;
import com.floriceps.service.ServiceRegister;

import static com.floriceps.utils.LogUtils.Log;

public class ServerTest {
    public static void main(String[] args) {

        Service serviceImpl = new Service(
                "com.floriceps.demo.service.ServerPrintService");
        if (!serviceImpl.classIsAvailable()) {
            Log.error("Can not create service, check the service name.");
        } else {
            String interfaceName = "com.floriceps.demo.service.PrintService";
            // 注册服务
            ServiceRegister.registerService(interfaceName, serviceImpl);
        }

        // 启动服务器
        RpcServer.startServer();
    }
}
