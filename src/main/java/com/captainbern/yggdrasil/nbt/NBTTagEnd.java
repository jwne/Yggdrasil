package com.captainbern.yggdrasil.nbt;

import com.captainbern.yggdrasil.reflection.refs.nbt.NBTRef;

import java.io.DataInput;
import java.io.DataOutput;

public class NBTTagEnd extends NBTBase {
    @Override
    public byte getTypeId() {
        return 0;
    }

    @Override
    public String toString() {
        return "END";
    }

    @Override
    void write(DataOutput dataoutput) {}

    @Override
    void load(DataInput datainput, int depth) {}

    @Override
    public NBTBase clone() {
        return new NBTTagEnd();
    }

    @Override
    public Object convertToVanilla() {
        return NBTRef.NBT_TAG_END.newInstance();
    }
}
