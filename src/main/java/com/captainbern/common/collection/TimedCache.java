package com.captainbern.common.collection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TimedCache<K, V> extends Cache<K, V> {

    protected final ConcurrentHashMap<K, V> cache = new ConcurrentHashMap<K, V>();
    protected final ConcurrentHashMap<K, CachedItem<V>> toCachedItem = new ConcurrentHashMap<K, CachedItem<V>>();

    protected long seconds;
    protected CacheWorker worker;

    protected ExecutorService service = Executors.newSingleThreadExecutor();

    public TimedCache(long secondsToKeepStuffInMemory) {
        super();
        this.seconds = secondsToKeepStuffInMemory;
        worker = new CacheWorker(this, this.seconds);
        this.service.submit(this.worker);
    }

    public long getCleanUpDelay() {
        return this.seconds;
    }

    public synchronized void poke() {
        Iterator<K> iterator = this.toCachedItem.keySet().iterator();
        while(iterator.hasNext()) {
            K key = iterator.next();
            CachedItem tw = (CachedItem) this.toCachedItem.get(key);
            if((tw != null) && (tw.getAge() > this.getCleanUpDelay())) {
                remove(key);
            }
        }
        System.gc();
    }

    @Override
    public void clear() {
        this.cache.clear();
        this.toCachedItem.clear();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.cache.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.cache.containsValue(value);
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return this.cache.entrySet();
    }

    @Override
    public V get(Object key) {
        return this.cache.get(key);
    }

    @Override
    public boolean isEmpty() {
        return this.cache.isEmpty();
    }

    @Override
    public Set<K> keySet() {
        return this.cache.keySet();
    }

    @Override
    public V cache(K key, V value) {
        this.toCachedItem.put(key, new CachedItem<V>(value));
        return this.cache.put(key, value);
    }

    @Override
    public void cacheAll(Map<? extends K, ? extends V> map) {
        for(Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            this.cache(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public V remove(Object key) {
        this.toCachedItem.remove(key);
        return this.cache.remove(key);
    }

    @Override
    public int size() {
        return this.cache.size();
    }

    @Override
    public Collection<V> values() {
        return this.cache.values();
    }

    /**
     * Represents a cached entry.
     */
    public class CachedItem<V> {

        private long age;
        private V object;

        public CachedItem(final V object) {
            this.object = object;
        }

        private void updateAge() {
            this.age = System.currentTimeMillis();
        }

        public long getAge() {
            return(System.currentTimeMillis() - this.age);
        }
        public V getObject() {
            updateAge();
            return(this.object);
        }
    }

    /**
     * The CacheWorker, will ping the cache and make sure the items get GC'd on time.
     */
    private class CacheWorker extends Thread {
        private long sleepTime;
        private TimedCache target;

        public CacheWorker(TimedCache target, long sleepTime ) {
            this.target = target;
            this.sleepTime = sleepTime;
        }

        @Override
        public void run() {
            while(true) {
                try {
                    sleep(sleepTime);
                } catch(InterruptedException e) {
                    // ignore it!
                }
                // ping target
                this.target.poke();
            }
        }
    }
}
