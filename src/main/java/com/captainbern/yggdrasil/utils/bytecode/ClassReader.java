package com.captainbern.yggdrasil.utils.bytecode;

import com.captainbern.yggdrasil.utils.IOUtils;

public class ClassReader {

    protected final byte[] bytes;

    protected final int magic;

    protected final int minor;

    protected final int major;

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
}
