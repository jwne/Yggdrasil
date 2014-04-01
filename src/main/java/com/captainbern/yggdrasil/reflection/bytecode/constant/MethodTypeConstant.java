package com.captainbern.yggdrasil.reflection.bytecode.constant;

import com.captainbern.yggdrasil.utils.IOUtils;

import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.TAG_METHOD_TYPE;

public class MethodTypeConstant extends Constant {

    private int descriptor;

    public MethodTypeConstant(byte[] bytes, int index) throws IOException {
        this(IOUtils.readUnsignedShort(bytes, index), index);
    }

    public MethodTypeConstant(int descriptor, int index) {
        super(index);
        this.descriptor = descriptor;
    }

    public int getDescriptor() {
        return this.descriptor;
    }

    @Override
    public int getTag() {
        return TAG_METHOD_TYPE;
    }
}
