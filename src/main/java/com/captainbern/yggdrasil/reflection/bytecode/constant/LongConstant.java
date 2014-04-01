package com.captainbern.yggdrasil.reflection.bytecode.constant;

import com.captainbern.yggdrasil.utils.IOUtils;

import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.TAG_LONG;

public class LongConstant extends Constant {

    private long clong;

    public LongConstant(byte[] bytes, int index) throws IOException {
        this(IOUtils.readLong(bytes, index), index);
    }

    public LongConstant(long clong, int index) {
        super(index);
        this.clong = clong;
    }

    public long getLong() {
        return this.clong;
    }

    @Override
    public int getTag() {
        return TAG_LONG;
    }
}
