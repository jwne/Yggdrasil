package com.captainbern.yggdrasil.utils.bytecode;

import com.captainbern.yggdrasil.utils.IOUtils;
import static com.captainbern.yggdrasil.utils.bytecode.Opcode.*;

public class ConstantPool {

    private int constantPoolSize;
    private Constant[] pool;

    public ConstantPool(Constant[] pool) {
        setPool(pool);
    }

    public ConstantPool(final byte[] bytes) {
        this(bytes, 0);
    }

    public ConstantPool(final byte[] bytes, int offset) {

        // Size of this pool
        constantPoolSize = IOUtils.readUnsignedShort(bytes, offset + 8);
        pool = new Constant[constantPoolSize];

        int[] items = new int[constantPoolSize];

        int max = 0;

        int index = offset + 10;
        int size;

        for (int i = 1; i < constantPoolSize; i++) {
            items[i] = index + 1;

            // create a new constant

            Constant constant;
            switch (bytes[index]) {
                case TAG_FIELD:
                case TAG_METHOD:
                case TAG_INTERFACE_METHOD:
                case TAG_INTEGER:
                case TAG_FLOAT:
                case TAG_DESCRIPTOR:
                case TAG_INVOKEDYNAMIC:
                    size = 5;
                    constant = new Constant(IOUtils.readByte(bytes, index + 4));
                    break;
                case TAG_LONG:
                case TAG_DOUBLE:
                    size = 9;
                    ++i;
                    break;
                case TAG_UTF_STRING:
                    size = 3 + IOUtils.readUnsignedShort(bytes, index + 1);
                    if(size > max) {
                        max = size;
                    }
                    break;
                case TAG_METHOD_HANDLE:
                    size = 4;
                    constant = new Constant(IOUtils.readByte(bytes, index + 3));
                    break;
                case TAG_CLASS:
                case TAG_STRING:
                case TAG_METHOD_TYPE:
                    size = 3;
                    constant = new Constant(IOUtils.readByte(bytes, index + 2));
                    break;
                default:
                    size = 3;
                    break;
            }
            index+= size;
        }
    }

    public Constant[] getPool() {
        return this.pool;
    }

    public void setPool(Constant[] pool) {
        this.pool = pool;
        this.constantPoolSize = pool == null? 0 : pool.length;
    }

    public void setConstant(final Constant constant, final int index) {
        this.pool[index] = constant;
    }

    public Constant getConstant(final int index) {
        if(index > this.constantPoolSize || index < 0) {
            throw new IndexOutOfBoundsException("Size: " + this.constantPoolSize + ". Requested index: " + index + ".");
        }

        return pool[index];
    }

    public int length() {
        return constantPoolSize;
    }
}
