package com.floriceps.rpc_demo;

import com.floriceps.rpc_consumer.consume.Consumer;
import com.floriceps.rpc_service_api.service.PrintService;

public class ClientDemo {
    public static void main(String[] args) {
        PrintService service = Consumer.getService(PrintService.class);
        System.out.println(service.print("haha"));
        System.out.println(service.print("hehe"));
    }
}
