package com.mangadex.lab2.cache;

import com.mangadex.lab2.cache.impl.LRUCache;
import com.mangadex.lab2.model.Author;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LRUCacheAuthor extends LRUCache<String, Author> {
    public LRUCacheAuthor(@Value("${LRUCache.capacity}") final int capacity) {
        super(capacity);
    }
}
