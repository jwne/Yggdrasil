package com.captainbern.yggdrasil.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.TAG_LONG;

public class LongConstant extends Constant {

    private long clong;

    public LongConstant(DataInput stream) throws IOException {
        this(stream.readLong());
    }

    public LongConstant(long clong) {
        super(TAG_LONG);
        this.clong = clong;
    }

    public long getLong() {
        return this.clong;
    }
}
