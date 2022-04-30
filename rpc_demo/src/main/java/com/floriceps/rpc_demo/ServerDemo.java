package com.floriceps.rpc_demo;

import com.floriceps.rpc_provider.provide.Provider;

public class ServerDemo {
    public static void main(String[] args) {
        String serviceInterface = "com.floriceps.rpc_service_api.service.PrintService";
        String serviceImpl = "com.floriceps.rpc_provider.service.impl.ConsolePrintService";

        Provider provider = new Provider();
        provider.registry(serviceInterface, serviceImpl);
        provider.start();
    }
}
