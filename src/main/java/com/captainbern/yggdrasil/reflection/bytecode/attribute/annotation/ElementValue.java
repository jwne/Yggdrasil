package com.captainbern.yggdrasil.reflection.bytecode.attribute.annotation;

import com.captainbern.yggdrasil.reflection.bytecode.ConstantPool;
import com.captainbern.yggdrasil.reflection.bytecode.Opcode;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * The ElementValue;
 *
 * For info about it's structure: http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.7.16.1
 */
public class ElementValue implements Opcode {

    private char tag;
    private ConstantPool constantPool;

    public ElementValue(char type, ConstantPool constantPool) {
        this.tag = type;
        this.constantPool = constantPool;
    }

    public final char getTag() {
        return this.tag;
    }

    public final void setTag(char tag) {
        this.tag = tag;
    }

    public final ConstantPool getConstantPool() {
        return this.constantPool;
    }

    public final void setConstantPool(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    public static final char TYPE_STRING = 's';
    public static final char TYPE_ENUM = 'e';
    public static final char TYPE_CLASS = 'c';
    public static final char TYPE_ANNOTATION = '@';
    public static final char TYPE_BYTE = 'B';
    public static final char TYPE_CHAR = 'C';
    public static final char TYPE_DOUBLE = 'D';
    public static final char TYPE_FLOAT = 'F';
    public static final char TYPE_INT = 'I';
    public static final char TYPE_LONG = 'J';
    public static final char TYPE_SHORT = 'S';
    public static final char TYPE_BOOLEAN = 'Z';
    public static final char TYPE_ARRAY = '[';

    public static ElementValue read(DataInputStream codeStream, ConstantPool constantPool) throws IOException {
        char tag = (char) codeStream.readByte();
        switch (tag) {
            case TYPE_STRING:
                return new StringElementValue(codeStream, constantPool);
            case TYPE_ENUM:
                return new EnumElementValue(codeStream, constantPool);
            case TYPE_CLASS:
                return new ClassElementValue(codeStream, constantPool);
            case TYPE_ANNOTATION:
                return new AnnotationElementValue(Annotation.read(codeStream, constantPool, false), constantPool);
            case TYPE_BYTE:
                return new ByteElementValue(codeStream, constantPool);
            case TYPE_CHAR:
                return new CharElementValue(codeStream, constantPool);
            case TYPE_DOUBLE:
                return new DoubleElementValue(codeStream, constantPool);
            case TYPE_FLOAT:
                return new FloatElementValue(codeStream, constantPool);
            case TYPE_INT:
                return new IntegerElementValue(codeStream, constantPool);
            case TYPE_LONG:
                return new LongElementValue(codeStream, constantPool);
            case TYPE_SHORT:
                return new ShortElementValue(codeStream, constantPool);
            case TYPE_BOOLEAN:
                return new BooleanElementValue(codeStream, constantPool);
            case TYPE_ARRAY:
                return ArrayElementValue.read(codeStream, constantPool);
           default:
               throw new RuntimeException("Wrong tag-type: " + tag);
        }
    }
}
