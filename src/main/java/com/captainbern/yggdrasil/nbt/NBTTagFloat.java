package com.captainbern.yggdrasil.nbt;

import com.captainbern.yggdrasil.nbt.exception.NBTReadException;
import com.captainbern.yggdrasil.nbt.exception.NBTWriteException;
import com.captainbern.yggdrasil.reflection.refs.nbt.NBTRef;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagFloat extends NBTNumber<Float> {

    private float data;

    public NBTTagFloat() {}

    public NBTTagFloat(float f) {
        this.data = f;
    }

    @Override
    public byte getTypeId() {
        return 5;
    }

    @Override
    public Float getValue() {
        return this.data;
    }

    @Override
    public String toString() {
        return "" + this.data + "f";
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagFloat nbttagfloat = (NBTTagFloat) object;

            return this.data == nbttagfloat.data;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ Float.floatToIntBits(this.data);
    }

    @Override
    public Object convertToVanilla() {
        Object nmsHandle = NBTRef.NBT_TAG_FLOAT.newInstance();
        NBTRef.NBT_TAG_FLOAT.getField("data").set(nmsHandle, getValue());
        return nmsHandle;
    }

    @Override
    void write(DataOutput dataoutput) {
        try {
            dataoutput.writeFloat(this.data);
        } catch (IOException e) {
            throw new NBTWriteException(e);
        }
    }

    @Override
    void load(DataInput datainput, int depth) {
        try {
            this.data = datainput.readFloat();
        } catch (IOException e) {
            throw new NBTReadException(e);
        }
    }

    @Override
    public NBTBase clone() {
        return new NBTTagFloat(this.data);
    }
}
