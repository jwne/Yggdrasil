package com.captainbern.yggdrasil.reflection.bytecode;

import com.captainbern.yggdrasil.reflection.bytecode.constant.ClassConstant;
import com.captainbern.yggdrasil.reflection.bytecode.constant.Constant;
import com.captainbern.yggdrasil.reflection.bytecode.constant.Utf8Constant;
import com.captainbern.yggdrasil.reflection.bytecode.exception.ClassFormatException;
import com.sun.org.apache.bcel.internal.Constants;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstantPool {

    private int size;
    private Constant[] constantPool;

    public ConstantPool(DataInputStream codeStream) throws IOException, ClassFormatException {
        read(codeStream);
    }

    protected void read(DataInputStream inputStream) throws IOException, ClassFormatException {
        byte tag;
        size = inputStream.readUnsignedShort();
        constantPool = new Constant[size];

        for (int i = 1; i < size; i++) {
            constantPool[i] = Constant.readConstant(inputStream);
            tag = constantPool[i].getTag();
            if ((tag == Constants.CONSTANT_Double) || (tag == Constants.CONSTANT_Long)) {
                i++;
            }
        }
    }

    public int getTag(int index) {
        return getItem(index).getTag();
    }

    public Constant getItem(int index) {
        return constantPool[index];
    }

    public String getClassConstant(int index) {
        ClassConstant constant = (ClassConstant)getItem(index);
        if (constant == null) {
            return null;
        } else {
            return getUtf8(constant.getName());
        }
    }

    public String getUtf8(int index) {
        Utf8Constant constant = (Utf8Constant) getItem(index);
        return constant.getString();
    }
}
