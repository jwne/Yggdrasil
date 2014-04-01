package com.captainbern.yggdrasil.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.TAG_CLASS;

public class ClassConstant extends Constant {

    private int name;

    public ClassConstant(DataInput stream) throws IOException {
        this(stream.readUnsignedShort());
    }

    public ClassConstant(int name) {
        super(TAG_CLASS);
        this.name = name;
    }

    public int getName() {
        return this.name;
    }

}
