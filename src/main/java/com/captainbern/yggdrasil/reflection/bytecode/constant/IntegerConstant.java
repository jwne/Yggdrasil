package com.captainbern.yggdrasil.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.TAG_INTEGER;

public class IntegerConstant extends Constant {

    private int cint;

    public IntegerConstant(DataInput stream) throws IOException {
        this(stream.readInt());
    }

    public IntegerConstant(int cint) {
        super(TAG_INTEGER);
        this.cint = cint;
    }

    public int getInt() {
        return this.cint;
    }
}
