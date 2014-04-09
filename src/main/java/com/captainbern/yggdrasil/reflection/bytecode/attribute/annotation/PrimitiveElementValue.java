package com.captainbern.yggdrasil.reflection.bytecode.attribute.annotation;

import com.captainbern.yggdrasil.reflection.bytecode.ConstantPool;
import com.captainbern.yggdrasil.reflection.bytecode.constant.*;
import com.captainbern.yggdrasil.reflection.bytecode.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.IOException;

public class PrimitiveElementValue extends ElementValue {

    private int constValueIndex;

    public PrimitiveElementValue(PrimitiveElementValue primitiveElementValue) {
        this(primitiveElementValue.getTag(), primitiveElementValue.getIndex(), primitiveElementValue.getConstantPool());
    }

    public PrimitiveElementValue(char type, DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        this(type, codeStream.readUnsignedShort(), constantPool);
    }

    public PrimitiveElementValue(char type, int constValueIndex, ConstantPool constantPool) {
        super(type, constantPool);
        this.constValueIndex = constValueIndex;
    }

    public final int getIndex() {
        return this.constValueIndex;
    }

    public final void setIndex(int index) {
        this.constValueIndex = index;
    }

    public final String asString() throws ClassFormatException {
        if(getTag() != TYPE_STRING)
            throw new RuntimeException("Invalid type!");
        Utf8Constant constant = (Utf8Constant) getConstantPool().getConstant(this.constValueIndex, CONSTANT_Utf8);
        return constant.getString();
    }

    public final int asByte() throws ClassFormatException {
        if(getTag() != TYPE_BYTE)
            throw new RuntimeException("Invalid type!");
        IntegerConstant constant = (IntegerConstant) getConstantPool().getConstant(this.constValueIndex, CONSTANT_Integer);
        return constant.getInt();
    }

    public final char asChar() throws ClassFormatException {
        if(getTag() != TYPE_CHAR)
            throw new RuntimeException("Invalid type!");
        IntegerConstant constant = (IntegerConstant) getConstantPool().getConstant(this.constValueIndex, CONSTANT_Integer);
        return (char) constant.getInt();
    }

    public final double asDouble() throws ClassFormatException {
        if(getTag() != TYPE_DOUBLE)
            throw new RuntimeException("Invalid type!");
        DoubleConstant constant = (DoubleConstant) getConstantPool().getConstant(this.constValueIndex, CONSTANT_Double);
        return constant.getDouble();
    }

    public final float asFloat() throws ClassFormatException {
        if(getTag() != TYPE_FLOAT)
            throw new RuntimeException("Invalid type!");
        FloatConstant constant = (FloatConstant) getConstantPool().getConstant(this.constValueIndex, CONSTANT_Float);
        return constant.getFloat();
    }

    public final int asInt() throws ClassFormatException {
        if(getTag() != TYPE_INT)
            throw new RuntimeException("Invalid type!");
        IntegerConstant constant = (IntegerConstant) getConstantPool().getConstant(this.constValueIndex, CONSTANT_Integer);
        return constant.getInt();
    }

    public final long asLong() throws ClassFormatException {
        if(getTag() != TYPE_LONG)
            throw new RuntimeException("Invalid type!");
        LongConstant constant = (LongConstant) getConstantPool().getConstant(this.constValueIndex, CONSTANT_Long);
        return constant.getLong();
    }

    public final short asShort() throws ClassFormatException {
        if(getTag() != TYPE_SHORT)
            throw new RuntimeException("Invalid type!");
        IntegerConstant constant = (IntegerConstant) getConstantPool().getConstant(this.constValueIndex, CONSTANT_Integer);
        return (short) constant.getInt();
    }

    public final boolean asBoolean() throws ClassFormatException {
        if(getTag() != TYPE_BOOLEAN)
            throw new RuntimeException("Invalid type!");
        IntegerConstant constant = (IntegerConstant) getConstantPool().getConstant(this.constValueIndex, CONSTANT_Integer);
        return constant.getInt() != 0;
    }
}
