package com.captainbern.yggdrasil.reflection.bytecode;

import com.captainbern.yggdrasil.reflection.bytecode.constant.*;
import com.captainbern.yggdrasil.reflection.bytecode.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstantPool implements Opcode {

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
            if ((tag == CONSTANT_Double) || (tag == CONSTANT_Long)) {
                i++;
            }
        }
    }

    public Constant[] getConstantPool() {
        return this.constantPool;
    }

    public int getSize() {
        return this.size;
    }

    public Constant getConstant(int index) {
        if (index >= constantPool.length || index < 0) {
            throw new IndexOutOfBoundsException("Pool size: \'" + this.constantPool.length + "\'. Referenced index: \'" + index);
        }
        return constantPool[index];
    }

    public Constant getConstant(int index, byte tag) throws ClassFormatException {
        Constant constant;
        constant = getConstant(index);
        if (constant == null) {
            throw new ClassFormatException("Constant pool at index \'" + index + "\' is NULL.");
        }
        if (constant.getTag() != tag) {
            throw new ClassFormatException("Expected class \'" + CONSTANT_NAMES[tag]
                    + "\' at index \'" + index + "\' and got \'" + constant + "\'");
        }
        return constant;
    }

    public String getConstantString(int index, byte tag) throws ClassFormatException {
        Constant constant = getConstant(index, tag);
        int stringIndex;
        switch (tag) {
            case CONSTANT_Class:
               stringIndex = ((ClassConstant) constant).getNameIndex();
                break;
            case CONSTANT_String:
                stringIndex = ((StringConstant) constant).getStringIndex();
                break;
            default:
                throw new IllegalArgumentException("Invalid tag: " + tag);
        }

        constant = getConstant(stringIndex, CONSTANT_Utf8);
        return ((Utf8Constant) constant).getString();
    }

    public String getUtf8StringConstant(int index) throws ClassFormatException {
        Utf8Constant constant = (Utf8Constant) getConstant(index, CONSTANT_Utf8);
        return constant.getString();
    }

    public double getDoubleConstant(int index) throws ClassFormatException {
        DoubleConstant constant = (DoubleConstant) getConstant(index, CONSTANT_Double);
        return constant.getDouble();
    }

    public float getFloatConstant(int index) throws ClassFormatException {
        FloatConstant constant = (FloatConstant) getConstant(index, CONSTANT_Float);
        return constant.getFloat();
    }

    public int getIntegerConstant(int index) throws ClassFormatException {
        IntegerConstant constant = (IntegerConstant) getConstant(index, CONSTANT_Integer);
        return constant.getInt();
    }

    public long getLongConstant(int index) throws ClassFormatException {
        LongConstant constant = (LongConstant) getConstant(index, CONSTANT_Long);
        return constant.getLong();
    }

    public String getStringConstant(int index) throws ClassFormatException {
        StringConstant constant = (StringConstant) getConstant(index, CONSTANT_String);
        return getUtf8StringConstant(constant.getStringIndex());
    }
}
