package com.captainbern.yggdrasil.reflection.bytecode.constant;

import com.captainbern.yggdrasil.reflection.bytecode.exception.ClassFormatException;

import java.io.DataInputStream;
import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.*;

public abstract class Constant {

    protected byte tag;

    public Constant(final byte tag) {
        this.tag = tag;
    }

    public final byte getTag() {
        return this.tag;
    }

    public static Constant readConstant(DataInputStream codeStream) throws IOException, ClassFormatException {
        byte tag = codeStream.readByte();
        switch (tag) {
            case TAG_UTF_STRING:
                return new Utf8Constant(codeStream);
            case 3:
                return new IntegerConstant(codeStream);
            case 4:
                return new FloatConstant(codeStream);
            case 5:
                return new LongConstant(codeStream);
            case 6:
                return new DoubleConstant(codeStream);
            case 7:
                return new ClassConstant(codeStream);
            case 8:
                return new StringConstant(codeStream);
            case 9:
                return new FieldConstant(codeStream);
            case 10:
                return new MethodConstant(codeStream);
            case 11:
                return new InterfaceMethodConstant(codeStream);
            case 12:
                return new DescriptorConstant(codeStream);
            case 15:
                return new MethodHandleConstant(codeStream);
            case 16:
                return new MethodTypeConstant(codeStream);
            case 18:
                return new InvokeDynamicConstant(codeStream);
            default:
                throw new ClassFormatException("Invalid tag type: " + tag);
        }
    }
}
