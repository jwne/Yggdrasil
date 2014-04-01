package com.captainbern.yggdrasil.reflection.bytecode.constant;

import com.captainbern.yggdrasil.utils.IOUtils;

import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.TAG_INVOKEDYNAMIC;

public class InvokeDynamicConstant extends Constant {

    private int bootstrap;
    private int nameAndType;

    public InvokeDynamicConstant(byte[] bytes, int index) throws IOException {
        this(IOUtils.readUnsignedShort(bytes, index), IOUtils.readUnsignedShort(bytes, index + 2), index);
    }

    public InvokeDynamicConstant(int bootstrap, int nameAndType, int index) {
        super(index);
        this.bootstrap = bootstrap;
        this.nameAndType = nameAndType;
    }

    public int getBootstrap() {
        return this.bootstrap;
    }

    public int getNameAndType() {
        return this.nameAndType;
    }

    @Override
    public int getTag() {
        return TAG_INVOKEDYNAMIC;
    }

}
