package com.captainbern.yggdrasil.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.CONSTANT_Integer;

public class IntegerConstant extends Constant {

    private int cint;

    public IntegerConstant(DataInput stream) throws IOException {
        this(stream.readInt());
    }

    public IntegerConstant(int cint) {
        super(CONSTANT_Integer);
        this.cint = cint;
    }

    public int getInt() {
        return this.cint;
    }
}
