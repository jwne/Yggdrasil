package com.captainbern.common.reflection.conversion;

public abstract class Converter<F, T> {

    public abstract T convert(F clazz);
}
