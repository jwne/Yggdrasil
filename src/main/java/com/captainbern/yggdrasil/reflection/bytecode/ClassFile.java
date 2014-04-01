package com.captainbern.yggdrasil.reflection.bytecode;

import com.captainbern.yggdrasil.utils.IOUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.JDK_1_1;
import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.JDK_8;

public class ClassFile {

    protected byte[] bytes;
    protected DataInputStream codeStream;
    private int magic;
    private int minor;
    private int major;
    private ConstantPool constantPool;
    private int thisClass;
    private int accessFlags;
    private int superClass;
    private int[] interfaces;

    private String thisClassName;

    protected int index;

    public ClassFile(final byte[] bytes) throws IOException {
        this(bytes, 0, bytes.length);
    }

    public ClassFile(final byte[] bytes, final int offset) throws IOException {
        this(bytes, offset, bytes.length);
    }

    public ClassFile(final byte[] bytes, final int offset, final int length) throws IOException {
        this.index = offset;
        this.magic = IOUtils.readInt(bytes, offset);

        if(magic != 0xCAFEBABE) {
            throw new IOException("Invalid ClassFile! Magic returned: \'" + Integer.toHexString(magic) + "\'");
        }

        this.minor = IOUtils.readUnsignedShort(bytes, index += 2);
        this.major = IOUtils.readUnsignedShort(bytes, index += 2);

        if(major > JDK_8 || major < JDK_1_1) {
            throw new IllegalArgumentException("Unsupported ClassFile!");
        }

        this.constantPool = new ConstantPool(bytes, offset + 8);

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

        this.thisClassName = constantPool.getClassConstant(this.thisClass);
    }

    public void write(DataOutputStream codeStream) {

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

    public String getClassName() {
        return this.thisClassName;
    }
}
