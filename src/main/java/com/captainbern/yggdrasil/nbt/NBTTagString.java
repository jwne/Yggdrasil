package com.captainbern.yggdrasil.nbt;

import com.captainbern.yggdrasil.nbt.exception.NBTReadException;
import com.captainbern.yggdrasil.nbt.exception.NBTWriteException;
import com.captainbern.yggdrasil.reflection.refs.nbt.NBTRef;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagString extends NBTReturnable<String> {

    private String data;

    public NBTTagString() {
        this(" ");
    }

    public NBTTagString(String name) {
        this.data = name;
        if(name == null)
            throw new IllegalArgumentException("Empty string not allowed!");
    }

    @Override
    public byte getTypeId() {
        return 8;
    }

    @Override
    public String getValue() {
        return this.data;
    }

    @Override
    public String toString() {
        return "\"" + this.data + "\"";
    }

    @Override
    void write(DataOutput dataoutput) {
        try {
            dataoutput.writeUTF(this.data);
        } catch (IOException e) {
            throw new NBTWriteException(e);
        }
    }

    @Override
    void load(DataInput datainput, int depth) {
        try {
            this.data = datainput.readUTF();
        } catch (IOException e) {
            throw new NBTReadException(e);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) {
            return false;
        } else {
            NBTTagString nbttagstring = (NBTTagString) object;

            return this.data == null && nbttagstring.data == null || this.data != null && this.data.equals(nbttagstring.data);
        }
    }

    @Override
    public Object convertToVanilla() {
        Object nmsHandle = NBTRef.NBT_TAG_STRING.newInstance();
        NBTRef.NBT_TAG_STRING.getField("data").set(nmsHandle, getValue());
        return nmsHandle;
    }

    @Override
    public NBTBase clone() {
        return new NBTTagString(this.data);
    }
}
