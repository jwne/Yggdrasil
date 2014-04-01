package com.captainbern.yggdrasil.reflection.bytecode.constant;

import com.captainbern.yggdrasil.utils.IOUtils;

import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.TAG_INTEGER;

public class IntegerConstant extends Constant {

    private int cint;

    public IntegerConstant(byte[] bytes, int index) throws IOException {
        this(IOUtils.readInt(bytes, index), index);
    }

    public IntegerConstant(int cint, int index) {
        super(index);
        this.cint = cint;
    }

    public int getInt() {
        return this.cint;
    }

    @Override
    public int getTag() {
        return TAG_INTEGER;
    }
}
