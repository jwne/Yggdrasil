package com.captainbern.common.collection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TimedCache<K, V> extends Cache<K, V> {

    protected final ConcurrentHashMap<K, CachedItem<V>> cache = new ConcurrentHashMap<K, CachedItem<V>>();

    protected long seconds;
    protected CacheWorker worker;

    public TimedCache(long secondsToKeepStuffInMemory) {
        super();
        this.seconds = secondsToKeepStuffInMemory;
        worker = new CacheWorker(this, this.seconds);
    }

    public long getCleanUpDelay() {
        return this.seconds;
    }

    public synchronized void poke() {
        Iterator<K> iterator = this.cache.keySet().iterator();
        while(iterator.hasNext()) {
            K key = iterator.next();
            CachedItem tw = (CachedItem) this.cache.get(key);
            if((tw != null) && (tw.getAge() > this.seconds)) {
                remove(key);
            }
        }
        System.gc();
    }

    @Override
    public void clear() {
        this.cache.clear();
    }

    @Override
    public Cache<K, V> clone() {
        return null;
    }

    @Override
    public boolean containsKey(Object key) {
        return this.cache.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        for(CachedItem item : this.cache.values()) {
            if(item.getObject().equals(value))
                return true;
        }
        return false;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return null;
    }

    @Override
    public V get(Object key) {
        return this.cache.get(key).getObject();
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
        return this.cache.put(key, new CachedItem<V>(value)).getObject();
    }

    @Override
    public void cacheAll(Map<? extends K, ? extends V> map) {
        for(Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            cache(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public V remove(Object key) {
        return this.cache.remove(key).getObject();
    }

    @Override
    public int size() {
        return this.cache.size();
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = new HashSet<V>();
        for(CachedItem<V> value : this.cache.values()) {
            values.add(value.getObject());
        }
        return values;
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
            while( true ) {
                try {
                    sleep( sleepTime );
                } catch( InterruptedException e ) {
                    // ignore it!
                }
                // ping target
                this.target.poke();
            }
        }
    }
}
