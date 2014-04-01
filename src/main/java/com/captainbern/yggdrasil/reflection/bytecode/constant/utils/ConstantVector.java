package com.captainbern.yggdrasil.reflection.bytecode.constant.utils;

import com.captainbern.yggdrasil.reflection.bytecode.constant.Constant;

public class ConstantVector {
    static final int ASIZE = 128;
    static final int ABITS = 7;  // ASIZE = 2^ABITS
    static final int VSIZE = 8;
    private Constant[][] objects;
    private int elements;

    public ConstantVector() {
        objects = new Constant[VSIZE][];
        elements = 0;
    }

    public ConstantVector(int initialSize) {
        int vsize = ((initialSize >> ABITS) & ~(VSIZE - 1)) + VSIZE;
        objects = new Constant[vsize][];
        elements = 0;
    }

    public int size() { return elements; }

    public int capacity() { return objects.length * ASIZE; }

    public Constant elementAt(int i) {
        if (i < 0 || elements <= i)
            return null;

        return objects[i >> ABITS][i & (ASIZE - 1)];
    }

    public void addElement(Constant value) {
        int nth = elements >> ABITS;
        int offset = elements & (ASIZE - 1);
        int len = objects.length;
        if (nth >= len) {
            Constant[][] newObj = new Constant[len + VSIZE][];
            System.arraycopy(objects, 0, newObj, 0, len);
            objects = newObj;
        }

        if (objects[nth] == null)
            objects[nth] = new Constant[ASIZE];

        objects[nth][offset] = value;
        elements++;
    }
}
