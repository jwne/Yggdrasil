package com.captainbern.yggdrasil.utils.bytecode;

import com.captainbern.yggdrasil.utils.IOUtils;

public class ConstantPool {

    private int constantPoolSize;
    private Constant[] pool;

    public ConstantPool(Constant[] pool) {
        setPool(pool);
    }

    public ConstantPool(final byte[] bytes, int offset) {
        byte tag;

        constantPoolSize = IOUtils.readUnsignedShort(bytes, offset);
        pool = new Constant[constantPoolSize];

        int index = offset + 10;
        int size;
        for (int i = 1; i < constantPoolSize; i++) {
            pool[i] = Constant.readConstant(bytes[index], bytes);

            tag = pool[i].getTag();

            size = pool[i].getSize();
            index += size;

            if ((tag == Opcode.TAG_DOUBLE) || (tag == Opcode.TAG_LONG)) {
                i++;
            }


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
