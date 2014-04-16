package com.captainbern.yggdrasil.reflection.bytecode.attribute.annotation;

import com.captainbern.yggdrasil.reflection.bytecode.ConstantPool;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.LinkedList;

public class Annotation {

    private int index;
    private ConstantPool constantPool;
    private LinkedList<ElementValuePair> members = new LinkedList<>();

    private boolean visible;

    public Annotation(int index, ConstantPool constantPool, boolean isVisible) {
        this.index = index;
        this.constantPool = constantPool;
        this.visible = isVisible;
    }

    public final int getIndex() {
        return this.index;
    }

    public final ConstantPool getConstantPool() {
        return this.constantPool;
    }

    public final boolean isRuntimeVisible() {
        return this.visible;
    }

    public final int getElementValuePairsSize() {
        return this.members.size();
    }

    public final ElementValuePair[] getElementValuePairs() {
        return this.members.toArray(new ElementValuePair[this.members.size()]);
    }

    public final void addElementValue(ElementValuePair elementValuePair) {
        this.members.add(elementValuePair);
    }

    public static Annotation read(DataInputStream codeStream, ConstantPool constantPool, boolean visible) throws IOException {
        final Annotation annotation = new Annotation(codeStream.readUnsignedShort(), constantPool, visible);
        final int valuePairs = codeStream.readUnsignedShort();

        for(int i = 0; i < valuePairs; i++) {
            annotation.addElementValue(new ElementValuePair(codeStream.readUnsignedShort(), ElementValue.read(codeStream, constantPool), constantPool));
        }

        return annotation;
    }
}
