package com.captainbern.yggdrasil.utils.bytecode;

import com.captainbern.yggdrasil.utils.IOUtils;

import static com.captainbern.yggdrasil.utils.bytecode.Opcode.*;

public class ClassReader {

    /**
     * Default class-structure:
     *
      ClassFile {
          u4             magic;
          u2             minor_version;
          u2             major_version;
          u2             constant_pool_count;
          cp_info        constant_pool[constant_pool_count-1];
          u2             access_flags;
          u2             this_class;
          u2             super_class;
          u2             interfaces_count;
          u2             interfaces[interfaces_count];
          u2             fields_count;
          field_info     fields[fields_count];
          u2             methods_count;
          method_info    methods[methods_count];
          u2             attributes_count;
          attribute_info attributes[attributes_count];
      }
     */

    protected final byte[] bytes;

    protected final int magic;

    protected final int minor;

    protected final int major;

    protected int[] items;

    protected String[] strings;

    protected int maxStringLength;

    protected int header;

    public ClassReader(final byte[] bytes) {
        this(bytes, 0);
    }

    public ClassReader(final byte[] bytes, int offset) {
        this(bytes, offset, bytes.length);
    }

    public ClassReader(final byte[] bytes, int offset, int length) {
        this.bytes = bytes;

        // We don't really care about the magic, it should be 0xcafebabe by default.
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
        header = index;
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

    public String getClassName() {
        return readClass(header + 2, new char[maxStringLength]);
    }

    public String getSuperName() {
        return readClass(header + 4, new char[maxStringLength]);
    }

    public int getInterfacesCount() {
        return IOUtils.readUnsignedShort(this.bytes, header + 6);
    }

    public String[] getInterfaces() {
        int index = header + 6;
        String[] interfaces = new String[getInterfacesCount()];
        if(getInterfacesCount() > 0) {
            char[] buffer = new char[maxStringLength];
            for(int i = 0; i < getInterfacesCount(); i++) {
                index += 2;
                interfaces[i] = readClass(index, buffer);
            }
        }

        return interfaces;
    }

    /**************************************************************/

    public String readClass(final int index, final char[] buf) {
        // computes the start index of the CONSTANT_Class item in b
        // and reads the CONSTANT_Utf8 item designated by
        // the first two bytes of this CONSTANT_Class item
        //return IOUtils.readString(items[IOUtils.readUnsignedShort(bytes, index)]);
        return readUTF8(items[IOUtils.readUnsignedShort(this.bytes, index)], buf);
    }

    private String readUTF(byte[] bytes, int index, final int utfLen, final char[] buf) {
        int endIndex = index + utfLen;
        int strLen = 0;
        int c;
        int st = 0;
        char cc = 0;
        while (index < endIndex) {
            c = bytes[index++];
            switch (st) {
                case 0:
                    c = c & 0xFF;
                    if (c < 0x80) { // 0xxxxxxx
                        buf[strLen++] = (char) c;
                    } else if (c < 0xE0 && c > 0xBF) { // 110x xxxx 10xx xxxx
                        cc = (char) (c & 0x1F);
                        st = 1;
                    } else { // 1110 xxxx 10xx xxxx 10xx xxxx
                        cc = (char) (c & 0x0F);
                        st = 2;
                    }
                    break;

                case 1: // byte 2 of 2-byte char or byte 3 of 3-byte char
                    buf[strLen++] = (char) ((cc << 6) | (c & 0x3F));
                    st = 0;
                    break;

                case 2: // byte 2 of 3-byte char
                    cc = (char) ((cc << 6) | (c & 0x3F));
                    st = 1;
                    break;
            }
        }
        return new String(buf, 0, strLen);
    }

    private String readUTF8(int index, final char[] buf) {
        int item = IOUtils.readUnsignedShort(this.bytes, index);
        if (index == 0 || item == 0) {
            return null;
        }
        String s = strings[item];
        if (s != null) {
            return s;
        }
        index = items[item];
        return strings[item] = readUTF(this.bytes, index + 2, IOUtils.readUnsignedShort(this.bytes, index), buf);
    }
}
