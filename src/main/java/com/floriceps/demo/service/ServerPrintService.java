package com.floriceps.demo.service;

public class ServerPrintService implements PrintService{
    @Override
    public String print(String text) {
        return "Server: " + text;
    }
}
