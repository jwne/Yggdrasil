package com.captainbern.yggdrasil.reflection.bytecode.constant;

import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.TAG_METHOD;

public class MethodConstant extends MemberConstant {

    public MethodConstant(byte[] bytes, int index) throws IOException {
        super(bytes, index);
    }

    public MethodConstant(int cindex, int nameAndType, int index) {
        super(cindex, nameAndType, index);
    }

    @Override
    public String getTagName() {
        return "Method";
    }

    @Override
    public int getTag() {
        return TAG_METHOD;
    }
}
