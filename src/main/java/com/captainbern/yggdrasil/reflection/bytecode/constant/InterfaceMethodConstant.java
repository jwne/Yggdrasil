package com.captainbern.yggdrasil.reflection.bytecode.constant;

import com.captainbern.yggdrasil.utils.IOUtils;

import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.TAG_INTERFACE_METHOD;

public class InterfaceMethodConstant extends MemberConstant {

    public InterfaceMethodConstant(byte[] bytes, int index) throws IOException {
        super(IOUtils.readUnsignedShort(bytes, index), IOUtils.readUnsignedShort(bytes, index + 2), index);
    }

    public InterfaceMethodConstant(int cindex, int nameAndType, int index) {
        super(cindex, nameAndType, index);
    }

    @Override
    public String getTagName() {
        return "Interface";
    }

    @Override
    public int getTag() {
        return TAG_INTERFACE_METHOD;
    }
}
