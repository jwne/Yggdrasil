package com.captainbern.yggdrasil.reflection;

import java.lang.reflect.Field;

public interface FieldAccessor<T> {

    public Field getField();

    public ClassTemplate getType();

    boolean set(Object instance, T value);

    T get(Object instance);

    T transfer(Object from, Object to);

    boolean isPublic();

    boolean isReadOnly();

    public void setFinalStatic(T newValue);

}
