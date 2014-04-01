package com.captainbern.yggdrasil.reflection.bytecode.constant;

import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.TAG_FIELD;

public class FieldConstant extends MemberConstant {

    public FieldConstant(byte[] bytes, int index) throws IOException {
        super(bytes, index);
    }

    public FieldConstant(int classIndex, int nameAndType, int index) {
        super(classIndex, nameAndType, index);
    }

    @Override
    public String getTagName() {
        return "Field";
    }

    @Override
    public int getTag() {
        return TAG_FIELD;
    }
}
