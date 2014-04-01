package com.captainbern.yggdrasil.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.TAG_INTERFACE_METHOD;

public class InterfaceMethodConstant extends MemberConstant {

    public InterfaceMethodConstant(DataInput stream) throws IOException {
        super(TAG_INTERFACE_METHOD, stream);
    }

    public InterfaceMethodConstant(int cindex, int nameAndType) {
        super(TAG_INTERFACE_METHOD, cindex, nameAndType);
    }

    @Override
    public String getTagName() {
        return "Interface";
    }
}
