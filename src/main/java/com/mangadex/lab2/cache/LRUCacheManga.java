package com.mangadex.lab2.cache;

import com.mangadex.lab2.cache.impl.LRUCache;
import com.mangadex.lab2.model.Manga;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LRUCacheManga extends LRUCache<String, Manga> {
    public LRUCacheManga(@Value("${LRUCache.capacity}") final int capacity) {
        super(capacity);
    }
}
