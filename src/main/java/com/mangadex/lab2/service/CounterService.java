package com.mangadex.lab2.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class CounterService {
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    public static synchronized int incrementAndGetCount() {
        return COUNTER.incrementAndGet();
    }
}