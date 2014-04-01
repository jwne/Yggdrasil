package com.captainbern.yggdrasil.reflection.bytecode.constant;

import java.io.DataInput;
import java.io.IOException;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.TAG_UTF_STRING;

public class Utf8Constant extends Constant {

    private String cstring;

    public Utf8Constant(DataInput inputStream) throws IOException {
        this(inputStream.readUTF());
    }

    public Utf8Constant(String cstring) {
        super(TAG_UTF_STRING);
        this.cstring = cstring;
    }

    public String getString() {
        return this.cstring;
    }
}
