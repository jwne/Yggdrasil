package com.captainbern.yggdrasil.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.CONSTANT_Double;

public class DoubleConstant extends Constant {

    private double cdouble;

    public DoubleConstant(DataInput stream) throws IOException {
        this(stream.readDouble());
    }

    public DoubleConstant(double cdouble) {
        super(CONSTANT_Double);
        this.cdouble = cdouble;
    }

    public double getDouble() {
        return this.cdouble;
    }
}
