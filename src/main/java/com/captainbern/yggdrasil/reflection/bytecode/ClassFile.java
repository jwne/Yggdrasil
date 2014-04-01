package com.captainbern.yggdrasil.reflection.bytecode;

import com.captainbern.yggdrasil.reflection.bytecode.exception.ClassFormatException;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.JDK_8;
import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.CONSTANT_Class;

public class ClassFile {

    protected byte[] bytes;
    protected DataInputStream codeStream;
    private int magic;
    private int minor;
    private int major;
    private ConstantPool constantPool;
    private int accessFlags;
    private int thisClass;
    private int superClass;
    private int[] interfaces;

    public ClassFile(final byte[] bytes) throws IOException, ClassFormatException {
        DataInputStream codeStream = new DataInputStream(new ByteArrayInputStream(bytes));
        this.magic = codeStream.readInt();

        if(magic != 0xCAFEBABE) {
            throw new IOException("Invalid ClassFile! Magic returned: \'" + Integer.toHexString(magic) + "\'");
        }

        this.minor = codeStream.readUnsignedShort();
        this.major = codeStream.readUnsignedShort();

        if(major > JDK_8) {
            throw new IllegalArgumentException("Unsupported ClassFile!");
        }

        this.constantPool = new ConstantPool(codeStream);

        this.accessFlags = codeStream.readUnsignedShort();
        this.thisClass = codeStream.readUnsignedShort();
        this.superClass = codeStream.readUnsignedShort();

        // Interfaces
        int interfacesCount = codeStream.readUnsignedShort();
        if(interfacesCount == 0) {
            this.interfaces = null;
        } else {
            this.interfaces = new int[interfacesCount];
            for(int i = 0; i < interfacesCount; i++) {
                interfaces[i] = codeStream.readUnsignedShort();
            }
        }

        // Fields

        // Methods
    }

    public byte[] getByteCode() {
        return this.bytes;
    }

    public int getMagic() {
        return this.magic;
    }

    public int getMinor() {
        return this.minor;
    }

    public int getMajor() {
        return this.major;
    }

    public ConstantPool getConstantPool() {
        return this.constantPool;
    }

    public int getAccessFlags() {
        return this.accessFlags;
    }

    public String getClassName() {
        try {
            return this.constantPool.getConstantString(this.thisClass, CONSTANT_Class);
        } catch (ClassFormatException e) {
            e.printStackTrace();
        }
        return "<Unknown>";
    }

    public String getSuperClassName() {
        try {
            return this.constantPool.getConstantString(this.superClass, CONSTANT_Class);
        } catch (ClassFormatException e) {
            e.printStackTrace();
        }
        return "<Unknown>";
    }
}
