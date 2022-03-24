package com.floriceps.rpc_client.test;

import com.floriceps.rpc_client.client.RpcClient;
import com.floriceps.rpc_service_api.service.PrintService;

public class TestClient {
    public static void main(String[] args) {
        PrintService service = RpcClient.getProxyService(PrintService.class);
        System.out.println(service.print("haha"));
        System.out.println(service.print("hehe"));
    }
}
