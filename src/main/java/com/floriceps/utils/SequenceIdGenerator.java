package com.floriceps.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 消息序列号生成器。
 */
public class SequenceIdGenerator {

    private static final AtomicInteger id = new AtomicInteger();

    public static int nextId() {
        return id.incrementAndGet();
    }
}
