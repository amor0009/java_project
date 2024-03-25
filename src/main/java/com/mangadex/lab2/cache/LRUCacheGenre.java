package com.mangadex.lab2.cache;

import com.mangadex.lab2.cache.impl.LRUCache;
import com.mangadex.lab2.model.Genre;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LRUCacheGenre extends LRUCache<String, Genre> {
    public LRUCacheGenre(@Value("${LRUCache.capacity}") final int capacity) {
        super(capacity);
    }
}
