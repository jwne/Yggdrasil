package com.captainbern.yggdrasil.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.CONSTANT_Float;

public class FloatConstant extends Constant {

    private float cfloat;

    public FloatConstant(DataInput stream) throws IOException {
        this(stream.readFloat());
    }

    public FloatConstant(float cfloat) {
        super(CONSTANT_Float);
        this.cfloat = cfloat;
    }

    public float getFloat() {
        return this.cfloat;
    }
}
