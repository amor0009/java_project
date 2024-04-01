package com.mangadex.lab2.cache;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.LinkedHashMap;

@Component
public class Cache {
    private final Map<String, Object> myCache;
    private static final Integer MAX_CACHE_SIZE = 50;
    public Cache() {
        this.myCache = new LinkedHashMap<>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Object> eldest) {
                return size() > MAX_CACHE_SIZE;
            }
        };
    }
    public void put(String key, Object value) {
        myCache.put(key, value);
    }

    public Object get(String key) {
        return myCache.get(key);
    }
    public boolean containsKey(String key) {
        return myCache.containsKey(key);
    }
    public void remove(String key) {
        myCache.remove(key);
    }

    public int size() {
        return myCache.size();
    }
}
