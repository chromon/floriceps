package com.floriceps.rpc_provider.service.impl;

import com.floriceps.rpc_service_api.service.PrintService;

public class ConsolePrintService implements PrintService {
    public String print(String text) {
        return "Console: " + text;
    }
}
