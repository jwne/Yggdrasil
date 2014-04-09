package com.captainbern.yggdrasil.reflection.bytecode.attribute;

import com.captainbern.yggdrasil.reflection.bytecode.ConstantPool;
import com.captainbern.yggdrasil.reflection.bytecode.Opcode;

import java.io.DataInputStream;

public class StackMap extends Attribute implements Opcode {

    private int mapLength;
    private StackMapEntry[] entries;

    public StackMap(int index, int length, DataInputStream codeStream, ConstantPool constantPool) {
        super(ATTR_STACK_MAP, index, length, constantPool);
    }
}
