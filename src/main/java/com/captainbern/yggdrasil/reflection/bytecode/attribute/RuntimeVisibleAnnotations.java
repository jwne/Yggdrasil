package com.captainbern.yggdrasil.reflection.bytecode.attribute;

import com.captainbern.yggdrasil.reflection.bytecode.ConstantPool;
import com.captainbern.yggdrasil.reflection.bytecode.Opcode;

public class RuntimeVisibleAnnotations extends Attribute implements Opcode {

    public RuntimeVisibleAnnotations(int index, int length, ConstantPool constantPool) {
        super(ATTR_RUNTIME_VISIBLE_ANNOTATIONS, index, length, constantPool);
    }
}
