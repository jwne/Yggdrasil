package com.captainbern.yggdrasil.reflection.bytecode.constant;

import com.captainbern.yggdrasil.utils.IOUtils;

import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.TAG_DESCRIPTOR;

public class DescriptorConstant extends Constant {

    private int memberName;
    private int typeDescriptor;

    public DescriptorConstant(byte[] bytes, int index) throws IOException {
        this(IOUtils.readUnsignedShort(bytes, index), IOUtils.readUnsignedShort(bytes, index + 2), index);
    }

    public DescriptorConstant(int memberName, int typeDescriptor, int index) {
        super(index);
        this.memberName = memberName;
        this.typeDescriptor = typeDescriptor;
    }

    public int getMemberName() {
        return this.memberName;
    }

    public int getTypeDescriptor() {
        return this.typeDescriptor;
    }

    @Override
    public int getTag() {
        return TAG_DESCRIPTOR;
    }
}
