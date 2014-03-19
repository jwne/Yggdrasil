package com.captainbern.yggdrasil.nbt;

import com.captainbern.yggdrasil.nbt.exception.NBTReadException;
import com.captainbern.yggdrasil.nbt.exception.NBTWriteException;
import com.captainbern.yggdrasil.reflection.refs.nbt.NBTRef;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagByte extends NBTNumber<Byte> {

    private byte data;

    public NBTTagByte() {}

    public NBTTagByte(byte b) {
        this.data = b;
    }

    @Override
    public byte getTypeId() {
        return 1;
    }

    @Override
    public Byte getValue() {
        return this.data;
    }

    @Override
    public String toString() {
        return "" + this.data + "b";
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagByte nbttagbyte = (NBTTagByte) object;

            return this.data == nbttagbyte.data;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.data;
    }

    @Override
    public Object convertToVanilla() {
        Object nmsHandle = NBTRef.NBT_TAG_BYTE.newInstance();
        NBTRef.NBT_TAG_BYTE.getField("data").set(nmsHandle, getValue());
        return nmsHandle;
    }

    @Override
    void write(DataOutput dataoutput) {
        try {
            dataoutput.writeByte(this.data);
        } catch (IOException e) {
            throw new NBTWriteException(e);
        }
    }

    @Override
    void load(DataInput datainput, int depth) {
        try {
            this.data = datainput.readByte();
        } catch (IOException e) {
            throw new NBTReadException(e);
        }
    }

    @Override
    public NBTBase clone() {
        return new NBTTagByte(this.data);
    }
}
