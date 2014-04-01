package com.captainbern.yggdrasil.reflection.bytecode.constant;

import com.captainbern.yggdrasil.utils.IOUtils;

import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.TAG_CLASS;

public class ClassConstant extends Constant {

    private int cname;

    public ClassConstant(byte[] bytes, int index) throws IOException {
        this(IOUtils.readUnsignedShort(bytes, index), index);
    }

    public ClassConstant(int cname, int index) {
        super(index);
        this.cname = cname;
    }

    public int getName() {
        return this.cname;
    }

    @Override
    public int getTag() {
        return TAG_CLASS;
    }
}
