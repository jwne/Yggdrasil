package com.captainbern.yggdrasil.reflection.bytecode.attribute.annotation;

import com.captainbern.yggdrasil.reflection.bytecode.ConstantPool;

public class AnnotationElementValue extends ElementValue {

    private Annotation annotation;

    public AnnotationElementValue(Annotation annotation, ConstantPool constantPool) {
        super(ElementValue.TYPE_ANNOTATION, constantPool);
        this.annotation = annotation;
    }

    public final Annotation getAnnotation() {
        return this.annotation;
    }
}
