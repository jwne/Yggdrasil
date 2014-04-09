package com.captainbern.yggdrasil.reflection.bytecode.attribute.annotation;

import com.captainbern.yggdrasil.reflection.bytecode.ConstantPool;

public class AnnotationElementValue extends ElementValue {

    public AnnotationElementValue(byte type, ConstantPool constantPool) {
        super(ElementValue.TYPE_ANNOTATION, constantPool);
    }
}
