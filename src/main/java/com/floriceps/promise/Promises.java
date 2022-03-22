package com.floriceps.promise;

import io.netty.util.concurrent.Promise;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Promise 用于异步通信过程中接收返回的结果，promise 与消息序列号一一对应。
 */
public class Promises {
    public static final Map<Integer, Promise<Object>> PROMISE_MAP = new ConcurrentHashMap<>();
}
