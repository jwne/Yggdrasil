package com.captainbern.yggdrasil.reflection.bytecode.constant;

import java.io.DataInputStream;
import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.TAG_STRING;

public class StringConstant extends Constant {

    private int cstring;

    public StringConstant(DataInputStream stream, int index) throws IOException {
        this(stream.readUnsignedShort(), index);
    }

    public StringConstant(int cstring, int index) {
        super(index);
        this.cstring = cstring;
    }

    public int getString() {
        return this.cstring;
    }

    @Override
    public int getTag() {
        return TAG_STRING;
    }
}
