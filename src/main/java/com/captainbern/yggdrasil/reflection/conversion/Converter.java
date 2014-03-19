package com.captainbern.yggdrasil.reflection.conversion;

public abstract class Converter<F, T> {

    public abstract T convert(F clazz);
}
