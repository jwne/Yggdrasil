package com.captainbern.yggdrasil.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.TAG_FLOAT;

public class FloatConstant extends Constant {

    private float cfloat;

    public FloatConstant(DataInput stream) throws IOException {
        this(stream.readFloat());
    }

    public FloatConstant(float cfloat) {
        super(TAG_FLOAT);
        this.cfloat = cfloat;
    }

    public float getFloat() {
        return this.cfloat;
    }
}
