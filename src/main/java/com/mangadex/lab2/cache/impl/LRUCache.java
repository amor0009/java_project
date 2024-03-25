package com.mangadex.lab2.cache.impl;

import com.mangadex.lab2.aspects.AspectAnnotation;
import com.mangadex.lab2.cache.Cache;
import lombok.Getter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;

@Getter
public class LRUCache<K, V> implements Cache<K ,V> {
    private final int capacity;

    private HashMap<K, Node<K, V>> hashMap;

    private LinkedList<Node<K, V>> linkedList;

    public LRUCache(final int maxSize) {
        this.capacity = maxSize;
        hashMap = new HashMap<>();
        linkedList = new LinkedList<>();
    }

    @Override
    public Optional<V> get(final K key) {
        Optional<V> result = Optional.empty();
        if (containsKey(key)) {
            final Node<K, V> node = hashMap.get(key);
            result = Optional.of(node.value);
            linkedList.remove(node);
            linkedList.addFirst(node);
        }
        return result;
    }

    @Override
    public int size() {
        return linkedList.size();
    }

    @Override
    public void put(final K key, final V value) {
        if (containsKey(key)) {
            linkedList.remove(hashMap.get(key));
        } else {
            ensureCapacity();
        }
        final Node<K, V> newNode = new Node<>(key, value);
        hashMap.put(key, newNode);
        linkedList.addFirst(newNode);
    }

    private boolean isSizeExceeded() {
        return size() == capacity;
    }

    @Override
    public boolean containsKey(final K key) {
        return hashMap.containsKey(key);
    }

    private void ensureCapacity() {
        if (isSizeExceeded()) {
            final Node<K, V> executeNode = linkedList.removeLast();
            hashMap.remove(executeNode.key);
        }
    }

    public void remove(final K key) {
        if (containsKey(key)) {
            linkedList.remove(hashMap.get(key));
            hashMap.remove(key);
        }
    }

    public record Node<K, V>(K key, V value) { }
}
