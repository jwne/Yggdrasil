package com.captainbern.common.collection;

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
