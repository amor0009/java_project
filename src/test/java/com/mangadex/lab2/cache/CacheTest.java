package com.mangadex.lab2.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CacheTest {
    private Cache cache;
    private static final String key = "Test key";
    private static final String object = "Object";

    @BeforeEach
    void initCache() {
        cache = new Cache();
    }

    @Test
    void testPutAndContainsKey() {
        cache.put(key, object);
        Assertions.assertTrue(cache.containsKey(key));
    }

    @Test
    void testGet(){
        cache.put(key, object);
        Assertions.assertEquals("Object", cache.get(key));
    }

    @Test
    void testRemove() {
        cache.put(key, object);
        cache.remove(key);
        Assertions.assertFalse(cache.containsKey(key));
    }

    @Test
    void testSize() {
        for (int i = 0; i < 34; i++) {
            cache.put(key + ' ' + i, object + ' ' + i);
        }
        Assertions.assertEquals(34, cache.size());
    }
}
