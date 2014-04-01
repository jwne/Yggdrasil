package com.captainbern.yggdrasil.reflection.bytecode.constant;

import com.captainbern.yggdrasil.utils.IOUtils;

import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.TAG_DOUBLE;

public class DoubleConstant extends Constant {

    private double cdouble;

    public DoubleConstant(byte[] bytes, int index) throws IOException {
        this(IOUtils.readDouble(bytes, index), index);
    }

    public DoubleConstant(double cdouble, int index) {
        super(index);
        this.cdouble = cdouble;
    }

    public double getDouble() {
        return this.cdouble;
    }

    @Override
    public int getTag() {
        return TAG_DOUBLE;
    }
}
