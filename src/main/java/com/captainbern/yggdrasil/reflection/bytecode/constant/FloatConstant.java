package com.captainbern.yggdrasil.reflection.bytecode.constant;

import com.captainbern.yggdrasil.utils.IOUtils;

import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.TAG_FLOAT;

public class FloatConstant extends Constant {

    private float cfloat;

    public FloatConstant(byte[] bytes, int index) throws IOException {
        this(IOUtils.readFloat(bytes, index), index);
    }

    public FloatConstant(float cfloat, int index) {
        super(index);
        this.cfloat = cfloat;
    }

    public float getFloat() {
        return this.cfloat;
    }

    @Override
    public int getTag() {
        return TAG_FLOAT;
    }
}
