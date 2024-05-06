package com.mangadex.lab2.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CounterService {
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    private CounterService() {

    }

    public static synchronized int incrementAndGetCount() {
        return COUNTER.incrementAndGet();
    }
}