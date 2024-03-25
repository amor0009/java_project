package com.mangadex.lab2.cache;

import java.util.Optional;

public interface Cache<K, V> {
    Optional<V> get(K key);
    int size();
    void put(K key, V value);
    boolean containsKey(K key);
}
