package com.captainbern.yggdrasil.utils.bytecode;

public class Constant {

    protected byte tag;

    public Constant(final byte tag) {
        this.tag = tag;
    }

    public byte getTag() {
        return this.tag;
    }
}
