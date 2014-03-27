package com.captainbern.yggdrasil.utils.bytecode;

public class Constant {

    protected byte tag;

    protected int size;

    public Constant(final byte tag) {
        this.tag = tag;
    }

    public byte getTag() {
        return this.tag;
    }

    public int getSize() {
        return this.size;
    }

    public static Constant readConstant(final int tag, final byte[] bytes) {
        switch (tag) {
            case Opcode.TAG_CLASS:
                //return new ConstantClass(file);
            case Opcode.TAG_FIELD:
              //  return new ConstantFieldref(file);
            case Opcode.TAG_METHOD:
             //   return new ConstantMethodref(file);
            case Opcode.TAG_INTERFACE_METHOD:
             //   return new ConstantInterfaceMethodref(file);
            case Opcode.TAG_STRING:
               // return new ConstantString(file);
            case Opcode.TAG_INTEGER:
               // return new ConstantInteger(file);
            case Opcode.TAG_FLOAT:
              //  return new ConstantFloat(file);
            case Opcode.TAG_LONG:
              //  return new ConstantLong(file);
            case Opcode.TAG_DOUBLE:
              //  return new ConstantDouble(file);
            case Opcode.TAG_DESCRIPTOR:
              //  return new ConstantNameAndType(file);
            case Opcode.TAG_UTF_STRING:
              //  return ConstantUtf8.getInstance(file);
            case Opcode.TAG_METHOD_HANDLE:
              //  return new ConstantMethodHandle(file);
            case Opcode.TAG_METHOD_TYPE:
              //  return new ConstantMethodType(file);
            case Opcode.INVOKEDYNAMIC:
              //  return new ConstantInvokeDynamic(file);
            default:
                throw new RuntimeException("Invalid byte tag in constant pool: " + tag);
        }
    }
}
