package com.captainbern.yggdrasil.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.CONSTANT_Fieldref;

public class FieldConstant extends MemberConstant {

    public FieldConstant(DataInput stream) throws IOException {
        super(CONSTANT_Fieldref, stream);
    }

    public FieldConstant(int classIndex, int nameAndType) {
        super(CONSTANT_Fieldref, classIndex, nameAndType);
    }

    @Override
    public String getTagName() {
        return "Field";
    }
}
