package com.captainbern.yggdrasil.reflection.bytecode.constant;

public class PhantomConstant extends Constant {
    public PhantomConstant(int index) {
        super(index);
    }

    @Override
    public int getTag() {
        return -1; // A "phantom" tag.
    }
}
