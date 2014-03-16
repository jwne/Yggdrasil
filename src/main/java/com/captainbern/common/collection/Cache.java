package com.captainbern.common.collection;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class Cache<K, V> {

    protected Cache() {}

    public abstract void clear();

    public abstract boolean containsKey(Object key);

    public abstract boolean containsValue(Object value);

    public abstract Set<Map.Entry<K,V>> entrySet();

    public abstract V get(Object key);

    public abstract boolean isEmpty();

    public abstract Set<K> keySet();

    public abstract V cache(final K key, final V value);

    public abstract void cacheAll(Map<? extends K,? extends V> map);

    public abstract V remove(Object key);

    public abstract int size();

    public abstract Collection<V> values();

    public static <K, V> SimpleCache<K, V> createSimpleCache() {
        return new SimpleCache<K, V>();
    }

    public static <K, V> TimedCache<K, V> createTimedCache(long time) {
        return new TimedCache<K, V>(time);
    }
}
