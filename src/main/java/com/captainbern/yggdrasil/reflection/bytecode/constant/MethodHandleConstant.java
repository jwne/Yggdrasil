package com.captainbern.yggdrasil.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.TAG_METHOD_HANDLE;

public class MethodHandleConstant extends Constant {

    private int ckind;
    private int cindex;

    public MethodHandleConstant(DataInput stream) throws IOException {
        this(stream.readUnsignedByte(), stream.readUnsignedShort());
    }

    public MethodHandleConstant(int ckind, int cindex) {
        super(TAG_METHOD_HANDLE);
        this.ckind = ckind;
        this.cindex = cindex;
    }

    public int getKind() {
        return this.cindex;
    }

    public int getMethodIndex() {
        return this.cindex;
    }
}
