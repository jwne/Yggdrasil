package com.captainbern.yggdrasil.utils.bytecode;

import com.captainbern.yggdrasil.utils.IOUtils;

import java.io.UnsupportedEncodingException;

import static com.captainbern.yggdrasil.utils.bytecode.Opcode.*;

public class ClassReader {

    protected final byte[] bytes;

    protected final int magic;

    protected final int minor;

    protected final int major;

    protected int[] items;

    protected String[] strings;

    protected int maxStringLength;

    protected int header;

    private String className;

    public ClassReader(final byte[] bytes) {
        this(bytes, 0);
    }

    public ClassReader(final byte[] bytes, int offset) {
        this(bytes, offset, bytes.length);
    }

    public ClassReader(final byte[] bytes, int offset, int length) {
        this.bytes = bytes;

        this.magic = IOUtils.readInt(bytes, offset);

        this.minor = IOUtils.readShort(bytes, offset + 4);
        this.major = IOUtils.readShort(bytes, offset + 6);

        if(major > Opcode.JDK_8) {
            throw new IllegalArgumentException("Major version too high!");
        }

        // Define the constant pool
        items = new int[IOUtils.readUnsignedShort(bytes, offset + 8)];
        int n = items.length;
        strings = new String[n];
        int max = 0;
        int index = offset + 10;
        for (int i = 1; i < n; ++i) {
            items[i] = index + 1;
            int size;
            switch (bytes[index]) {
                case TAG_FIELD:
                case TAG_METHOD:
                case TAG_INTERFACE_METHOD:
                case TAG_INTEGER:
                case TAG_FLOAT:
                case TAG_DESCRIPTOR:
                case TAG_INVOKEDYNAMIC:
                    size = 5;
                    break;
                case TAG_LONG:
                case TAG_DOUBLE:
                    size = 9;
                    ++i;
                    break;
                case TAG_UTF_STRING:
                    size = 3 + IOUtils.readUnsignedShort(bytes, index + 1);
                    if (size > max) {
                        max = size;
                    }
                    break;
                case TAG_METHOD_HANDLE:
                    size = 4;
                    break;
                // case ClassWriter.CLASS:
                // case ClassWriter.STR:
                // case ClassWriter.MTYPE
                default:
                    size = 3;
                    break;
            }
            index += size;
        }
        maxStringLength = max;
        // the class header information starts just after the constant pool
        header = index;

        // Define the class name
        int classNameIndex = items[IOUtils.readUnsignedShort(this.bytes, header + 2)];
        int item = IOUtils.readUnsignedShort(this.bytes, classNameIndex);
        int stringLength = IOUtils.readUnsignedShort(this.bytes, item);
        int stringReadIndex = classNameIndex + 2;

        byte[] nameBytes = new byte[stringLength];

        System.arraycopy(this.bytes, stringReadIndex, nameBytes, 0, stringLength);

        try {
            System.out.println(new String(nameBytes, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public final byte[] getBytes() {
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

    public int[] getConstantPool() {
        return this.items;
    }

    public int readItem(int index) {
        return getConstantPool()[index];
    }

    public int getAccess() {
        return IOUtils.readUnsignedShort(this.bytes, header);
    }
}
