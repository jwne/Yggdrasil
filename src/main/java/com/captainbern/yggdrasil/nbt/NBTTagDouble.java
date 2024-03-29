package com.captainbern.yggdrasil.nbt;

import com.captainbern.yggdrasil.nbt.exception.NBTReadException;
import com.captainbern.yggdrasil.nbt.exception.NBTWriteException;
import com.captainbern.yggdrasil.reflection.refs.nbt.NBTRef;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagDouble extends NBTNumber<Double> {

    private double data;

    public NBTTagDouble() {}

    public NBTTagDouble(double d) {
        this.data = d;
    }

    @Override
    public byte getTypeId() {
        return 6;
    }

    @Override
    public Double getValue() {
        return this.data;
    }

    @Override
    public String toString() {
        return "" + this.data + "d";
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagDouble nbttagdouble = (NBTTagDouble) object;

            return this.data == nbttagdouble.data;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        long i = Double.doubleToLongBits(this.data);

        return super.hashCode() ^ (int) (i ^ i >>> 32);
    }

    @Override
    public Object convertToVanilla() {
        Object nmsHandle = NBTRef.NBT_TAG_DOUBLE.newInstance();
        NBTRef.NBT_TAG_DOUBLE.getField("data").set(nmsHandle, getValue());
        return nmsHandle;
    }

    @Override
    void write(DataOutput dataoutput) {
        try {
            dataoutput.writeDouble(this.data);
        } catch (IOException e) {
            throw new NBTWriteException(e);
        }
    }

    @Override
    void load(DataInput datainput, int depth) {
        try {
            this.data = datainput.readDouble();
        } catch (IOException e) {
            throw new NBTReadException(e);
        }
    }

    @Override
    public NBTBase clone() {
        return new NBTTagDouble(this.data);
    }
}
