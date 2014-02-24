package com.captainbern.common.reflection;

public interface FieldAccessor<T> {

    public ClassTemplate getType();

    boolean set(Object instance, T value);

    T get(Object instance);

    T transfer(Object from, Object to);

    boolean isPublic();

    boolean isReadOnly();

    public void setReadOnly(Object target, boolean value);

}
