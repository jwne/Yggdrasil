package com.captainbern.yggdrasil.nbt;

import java.io.DataInput;
import java.io.DataOutput;

public abstract class NBTBase implements Cloneable {

    public static final String[] TAG_NAME_LOOKUP = new String[] { "END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]"};

    protected NBTBase() {}

    public abstract byte getTypeId();

    public abstract String toString();

    abstract void write(DataOutput dataoutput);

    abstract void load(DataInput datainput, int depth);

    protected static NBTBase createTagById(byte id) {
        switch (id) {
            case 0:
                return new NBTTagEnd();

            case 1:
                return new NBTTagByte();

            case 2:
                return new NBTTagShort();

            case 3:
                return new NBTTagInt();

            case 4:
                return new NBTTagLong();

            case 5:
                return new NBTTagFloat();

            case 6:
                return new NBTTagDouble();

            case 7:
                return new NBTTagByteArray();

            case 8:
                return new NBTTagString();

            case 9:
                return new NBTTagList();

            case 10:
                return new NBTTagCompound();

            case 11:
                return new NBTTagIntArray();

            default:
                return null;
        }
    }

    protected static String getTagNameById(byte id) {
        switch (id) {
            case 0:
                return "TAG_End";

            case 1:
                return "TAG_Byte";

            case 2:
                return "TAG_Short";

            case 3:
                return "TAG_Int";

            case 4:
                return "TAG_Long";

            case 5:
                return "TAG_Float";

            case 6:
                return "TAG_Double";

            case 7:
                return "TAG_Byte_Array";

            case 8:
                return "TAG_String";

            case 9:
                return "TAG_List";

            case 10:
                return "TAG_Compound";

            case 11:
                return "TAG_Int_Array";

            case 99:
                return "Any Numeric Tag";

            default:
                return "UNKNOWN";
        }
    }


    public abstract NBTBase clone();

    public boolean equals(Object object) {
        if (!(object instanceof NBTBase)) {
            return false;
        } else {
            NBTBase nbtbase = (NBTBase) object;

            return this.getTypeId() == nbtbase.getTypeId();
        }
    }

    public int hashCode() {
        return this.getTypeId();
    }

    public abstract Object convertToVanilla();
}
