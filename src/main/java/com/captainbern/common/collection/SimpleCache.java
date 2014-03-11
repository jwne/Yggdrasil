package com.captainbern.common.collection;

import java.util.concurrent.ConcurrentHashMap;

public class SimpleCache<K, V> extends Cache<K, V> {

    protected final ConcurrentHashMap<K, V> cache = new ConcurrentHashMap<K, V>();

    @Override
    public boolean replace(K key, V expectedValue, V updatedValue) {
        if (expectedValue == null) {
            return cache.putIfAbsent(key, updatedValue) == null;
        }
        return cache.replace(key, expectedValue, updatedValue);
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public Cache<K, V> cache(K key, V value) {
        cache.putIfAbsent(key, value);
        return this;
    }
}
