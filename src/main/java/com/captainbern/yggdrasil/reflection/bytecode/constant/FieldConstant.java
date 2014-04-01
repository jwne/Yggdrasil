package com.captainbern.yggdrasil.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.TAG_FIELD;

public class FieldConstant extends MemberConstant {

    public FieldConstant(DataInput stream) throws IOException {
        super(TAG_FIELD, stream);
    }

    public FieldConstant(int classIndex, int nameAndType) {
        super(TAG_FIELD, classIndex, nameAndType);
    }

    @Override
    public String getTagName() {
        return "Field";
    }
}
