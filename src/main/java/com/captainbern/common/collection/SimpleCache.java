package com.captainbern.common.collection;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleCache<K, V> extends Cache<K, V> {

    protected final ConcurrentHashMap<K, V> cache = new ConcurrentHashMap<K, V>();

    @Override
    public void clear() {
        this.cache.clear();
    }

    @Override
    public Cache<K, V> clone() {
        SimpleCache<K, V> clone = new SimpleCache<K, V>();
        clone.cacheAll(this.cache);
        return clone;
    }

    @Override
    public boolean containsKey(Object key) {
        return cache.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return cache.containsValue(value);
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return cache.entrySet();
    }

    @Override
    public V get(Object key) {
        return cache.get(key);
    }

    @Override
    public boolean isEmpty() {
        return cache.isEmpty();
    }

    @Override
    public Set<K> keySet() {
        return cache.keySet();
    }

    @Override
    public V cache(K key, V value) {
        return cache.putIfAbsent(key, value);
    }

    @Override
    public void cacheAll(Map<? extends K, ? extends V> map) {
         cache.putAll(map);
    }

    @Override
    public V remove(Object key) {
        return cache.remove(key);
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public Collection<V> values() {
        return cache.values();
    }
}
