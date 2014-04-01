package com.captainbern.yggdrasil.reflection.bytecode.constant;

public abstract class Constant {

    protected int index;

    public Constant(final int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public abstract int getTag();

}
