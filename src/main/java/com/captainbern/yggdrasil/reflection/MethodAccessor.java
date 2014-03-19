package com.captainbern.yggdrasil.reflection;

public interface MethodAccessor<T> {

    T invoke(Object instance, Object... args);

}
