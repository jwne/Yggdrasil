package com.captainbern.common.reflection;

import java.util.HashMap;
import java.util.LinkedList;

public class StructureModifier<T> {

    protected Class type;
    protected Object handle;

    protected LinkedList<FieldAccessor<T>> fields;

    public StructureModifier(Class type, Object instance, HashMap<StructureModifier, Integer> cache) {
        // TODO add several methods etc
    }

    public Class getType() {
        return this.type;
    }

    public Object getHandle() {
        return this.handle;
    }

    public int size() {
        return this.fields.size();
    }

    public FieldAccessor<T> getFieldAt(int index) {
        return this.fields.get(index);
    }

    public void set(int index, Object handle, T value) {
        FieldAccessor<T> fieldAccessor = getFieldAt(index);

        fieldAccessor.set(handle, value);
    }

    public StructureModifier<T> getFieldsWithType(Class type) {
        LinkedList<FieldAccessor<T>> returnList = new LinkedList<FieldAccessor<T>>();

        int index = 0;

      /*  for(FieldAccessor fieldAccessor : getFields()) {
            if(fieldAccessor.getType().isAssignableFrom(type)) {

                if(!returnList.contains(fieldAccessor)) {
                    returnList.add(index, fieldAccessor);
                }

                index++;
            }
        }
        */
        return null;
    }
}
