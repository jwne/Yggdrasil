package com.captainbern.yggdrasil.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.TAG_STRING;

public class StringConstant extends Constant {

    private int cstring;

    public StringConstant(DataInput stream) throws IOException {
        this(stream.readUnsignedShort());
    }

    public StringConstant(int cstring) {
        super(TAG_STRING);
        this.cstring = cstring;
    }

    public int getString() {
        return this.cstring;
    }
}
