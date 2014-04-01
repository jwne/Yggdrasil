package com.captainbern.yggdrasil.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.TAG_DESCRIPTOR;

public class DescriptorConstant extends Constant {

    private int memberName;
    private int typeDescriptor;

    public DescriptorConstant(DataInput stream) throws IOException {
        this(stream.readUnsignedShort(), stream.readUnsignedShort());
    }

    public DescriptorConstant(int memberName, int typeDescriptor) {
        super(TAG_DESCRIPTOR);
        this.memberName = memberName;
        this.typeDescriptor = typeDescriptor;
    }

    public int getMemberName() {
        return this.memberName;
    }

    public int getTypeDescriptor() {
        return this.typeDescriptor;
    }
}
