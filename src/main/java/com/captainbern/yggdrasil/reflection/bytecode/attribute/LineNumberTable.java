package com.captainbern.yggdrasil.reflection.bytecode.attribute;

import com.captainbern.yggdrasil.reflection.bytecode.ConstantPool;

public class LineNumberTable extends Attribute {
    public LineNumberTable(byte tag, int index, int length, ConstantPool constantPool) {
        super(tag, index, length, constantPool);
    }
}
