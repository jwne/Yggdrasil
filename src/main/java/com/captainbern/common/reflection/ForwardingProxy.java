package com.captainbern.common.reflection;

public class ForwardingProxy<T> {

    private T delegate;

    public ForwardingProxy(T handle) {
        this.delegate = handle;
    }

    public T delegate() {
        return this.delegate;
    }
}
