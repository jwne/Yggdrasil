package com.captainbern.yggdrasil.reflection.bytecode.constant;

import com.captainbern.yggdrasil.utils.IOUtils;

import java.io.IOException;

public abstract class MemberConstant extends Constant {

    private int classIndex;
    private int nameAndType;

    public MemberConstant(byte[] bytes, int index) throws IOException {
        this(IOUtils.readUnsignedShort(bytes, index), IOUtils.readUnsignedShort(bytes, index + 2), index);
    }

    public MemberConstant(int cindex, int nameAndType, int index) {
        super(index);
        this.classIndex = cindex;
        this.nameAndType = nameAndType;
    }

    public int getClassIndex() {
        return this.classIndex;
    }

    public int getNameAndType() {
        return this.nameAndType;
    }

    public abstract String getTagName();
}
