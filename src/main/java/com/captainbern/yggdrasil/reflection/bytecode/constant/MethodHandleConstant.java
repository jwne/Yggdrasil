package com.captainbern.yggdrasil.reflection.bytecode.constant;

import com.captainbern.yggdrasil.utils.IOUtils;

import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.TAG_METHOD_HANDLE;

public class MethodHandleConstant extends Constant {

    private int ckind;
    private int cindex;

    public MethodHandleConstant(byte[] bytes, int index) throws IOException {
        this(IOUtils.readByte(bytes, index), IOUtils.readUnsignedShort(bytes, index + 2), index);
    }

    public MethodHandleConstant(int ckind, int cindex, int index) {
        super(index);
        this.ckind = ckind;
        this.cindex = cindex;
    }

    public int getKind() {
        return this.cindex;
    }

    public int getMethodIndex() {
        return this.cindex;
    }

    @Override
    public int getTag() {
        return TAG_METHOD_HANDLE;
    }
}
