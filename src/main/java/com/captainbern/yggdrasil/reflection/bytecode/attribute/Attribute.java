package com.captainbern.yggdrasil.reflection.bytecode.attribute;

import com.captainbern.yggdrasil.reflection.bytecode.ConstantPool;
import com.captainbern.yggdrasil.reflection.bytecode.constant.Utf8Constant;
import com.captainbern.yggdrasil.reflection.bytecode.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.*;

public class Attribute {

    protected int index;
    protected int length;
    protected byte tag;
    protected ConstantPool constantPool;

    public static final Attribute readAttribute(DataInputStream codeStream, ConstantPool constantPool) throws IOException, ClassFormatException {
        byte tag = ATTR_UNKNOWN;

        int index = codeStream.readUnsignedShort();
        Utf8Constant constant = (Utf8Constant) constantPool.getConstant(index, CONSTANT_Utf8);
        String name = constant.getString();

        int length = codeStream.readInt();

        for(byte i = 0; i < KNOWN_ATTRIBUTES; i++) {
            if(ATTRIBUTE_NAMES[i].equalsIgnoreCase(name)) {
                tag = i;
                break;
            }
        }

        switch (tag) {
            // Do stuff
            /**
             * Unknown
             *
             */
        }

        return null;
    }
}
