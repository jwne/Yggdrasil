package com.captainbern.yggdrasil.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.TAG_METHOD;

public class MethodConstant extends MemberConstant {

    public MethodConstant(DataInput stream) throws IOException {
        super(TAG_METHOD, stream);
    }

    public MethodConstant(int cindex, int nameAndType) {
        super(TAG_METHOD, cindex, nameAndType);
    }

    @Override
    public String getTagName() {
        return "Method";
    }
}
