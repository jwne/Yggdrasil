package com.captainbern.common.collection;

public abstract class Cache<K, V> {

    protected Cache() {}

    public abstract boolean replace(final K key, final V expectedValue, V updatedValue);

    public abstract V get(final K key);

    public abstract Cache<K, V> cache(final K key, final V value);

    public static <K, V> SimpleCache<K, V> createSimpleCache() {
        return new SimpleCache<K, V>();
    }
}
