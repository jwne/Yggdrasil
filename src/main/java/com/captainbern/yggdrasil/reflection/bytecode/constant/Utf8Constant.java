package com.captainbern.yggdrasil.reflection.bytecode.constant;

import java.io.DataInputStream;
import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.TAG_UTF_STRING;

public class Utf8Constant extends Constant {

    private String cstring;

    public Utf8Constant(DataInputStream inputStream, int index) throws IOException {
        this(inputStream.readUTF(), index);
    }

    public Utf8Constant(String cstring, int index) {
        super(index);
        this.cstring = cstring;
    }

    public String getString() {
        return this.cstring;
    }

    @Override
    public int getTag() {
        return TAG_UTF_STRING;
    }
}
