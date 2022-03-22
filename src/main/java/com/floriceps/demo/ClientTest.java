package com.floriceps.demo;

import com.floriceps.client.RpcClient;
import com.floriceps.demo.service.PrintService;

public class ClientTest {
    public static void main(String[] args) {
        PrintService service = RpcClient.getProxyService(PrintService.class);
        System.out.println(service.print("haha"));
        System.out.println(service.print("hehe"));
    }
}
