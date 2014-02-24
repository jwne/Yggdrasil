package com.captainbern.common.reflection;

public interface MethodAccessor<T> {

    T invoke(Object instance, Object... args);

}
