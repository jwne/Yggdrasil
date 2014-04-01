package com.captainbern.yggdrasil.reflection.bytecode;

import com.captainbern.yggdrasil.reflection.bytecode.constant.*;
import com.captainbern.yggdrasil.utils.IOUtils;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.LinkedList;

import static com.captainbern.yggdrasil.reflection.bytecode.Opcode.*;

public class ConstantPool {

    private byte[] bytes;
    private int index;
    private LinkedList<Constant> constants;
    private int numOfItems;
    private int thisClass;

    public ConstantPool(byte[] bytes, int index) throws IOException {
        this.bytes = bytes;
        this.index = index;
    }

    protected void read(byte[] bytes, int index) {
        int poolSize = IOUtils.readUnsignedShort(bytes, index);

        constants = new LinkedList<Constant>();

        index += 2; // the PoolSize is a short (which has a length of 2 bytes)
        for(int i = 1; i < poolSize; i++) {  // the ConstantPool starts at 1

        }
    }

    protected void read(DataInputStream inputStream) throws IOException {
        int n = inputStream.readUnsignedShort();

        //constants = new ConstantVector(n);
        numOfItems = 0;
        addItem0(null);          // index 0 is reserved by the JVM.

        while (--n > 0) {       // index 0 is reserved by JVM
            Constant constant = getConstant(inputStream);
            if ((constant.getTag() == TAG_LONG) || (constant.getTag() == TAG_DOUBLE)) {
                addConstantInfoPadding();
                --n;
            }
        }
    }

    private Constant getConstant(DataInputStream stream) throws IOException {
        int tag = stream.readUnsignedByte();
        Constant constant;
        switch (tag) {
            case 1:
                constant = new Utf8Constant(stream, numOfItems);
                break;
            case 3:
                constant = new IntegerConstant(stream, numOfItems);
                break;
            case 4:
                constant = new FloatConstant(stream, numOfItems);
                break;
            case 5:
                constant = new LongConstant(stream, numOfItems);
                break;
            case 6:
                constant = new DoubleConstant(stream, numOfItems);
                break;
            case 7:
                constant = new ClassConstant(stream, numOfItems);
                break;
            case 8:
                constant = new StringConstant(stream, numOfItems);
                break;
            case 9:
                constant = new FieldConstant(stream, numOfItems);
                break;
            case 10:
                constant = new MethodConstant(stream, numOfItems);
                break;
            case 11:
                constant = new InterfaceMethodConstant(stream, numOfItems);
                break;
            case 12:
                constant = new DescriptorConstant(stream, numOfItems);
                break;
            case 15:
                constant = new MethodHandleConstant(stream, numOfItems);
                break;
            case 16:
                constant = new MethodTypeConstant(stream, numOfItems);
                break;
            case 18:
                constant = new InvokeDynamicConstant(stream, numOfItems);
                break;
            default:
                throw new IOException("Wrong constant type: \'" + tag + "\' at: \'" + numOfItems + "\'!");
        }
        return constant;
    }

    public int getTag(int index) {
        return getItem(index).getTag();
    }

    public Constant getItem(int index) {
        return constants.get(index);
    }

    private int addItem0(Constant info) {
        constants.add(info);
        return numOfItems++;
    }

    public void addConstantInfoPadding() {
        addItem0(new PhantomConstant(numOfItems));
    }

    public String getClassConstant(int index) {
        ClassConstant constant = (ClassConstant)getItem(index);
        if (constant == null) {
            return null;
        } else {
            return getUtf8(constant.getName());
        }
    }

    /*****************************************************
     *
     * Several utils
     *
     *****************************************************/
    public String getUtf8(int index) {
        Utf8Constant constant = (Utf8Constant) getItem(index);
        return constant.getString();
    }
}
