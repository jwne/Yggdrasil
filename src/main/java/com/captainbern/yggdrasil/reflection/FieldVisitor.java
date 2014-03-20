package com.captainbern.yggdrasil.reflection;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * No, this has nothing to do with ASM nor bytecode
 */
public class FieldVisitor<T> {

    protected Object handle;

    protected ClassTemplate<?> template;

    protected List<FieldAccessor> data;

    protected Map<Class, FieldVisitor> cache;

    public FieldVisitor() {
        this(null);
    }

    public FieldVisitor(Object handle) {
        this(handle, ClassTemplate.create(handle.getClass()));
    }

    public FieldVisitor(Object handle, ClassTemplate template) {
        this(handle, template, new ArrayList<FieldAccessor>());
    }

    public FieldVisitor(Object handle, ClassTemplate template, List<FieldAccessor> data) {
        this(handle, template, data, new HashMap<Class, FieldVisitor>());
    }

    public FieldVisitor(Object handle, ClassTemplate template, List<FieldAccessor> data, Map<Class, FieldVisitor> cache) {
        initialize(handle, template, data, cache);
    }

    protected void initialize(Object handle, ClassTemplate template, List<FieldAccessor> data, Map<Class,FieldVisitor> cache) {
        this.handle = handle;
        this.template = template;
        this.data = data;
        this.cache = cache;
    }

    public T read(int index) {
        if(index < 0 || index >= this.data.size()) {
            throw new IndexOutOfBoundsException("Size: " + this.data.size() + ". Requested: " + index);
        }

        if(this.handle == null) {
            throw new RuntimeException("Handle is NULL (aka I don't know what to read of!)!");
        }

        // Should never be null
        if(this.data.get(index) != null) {
            FieldAccessor<T> accessor = this.data.get(index);

            return accessor.get(getHandle());
        }
        return null;
    }

    public FieldAccessor<T> getAsFieldAccessor(int index) {
        if(index < 0 || index >= this.data.size()) {
            throw new IndexOutOfBoundsException("Size: " + this.data.size() + ". Requested: " + index);
        }

        // Should never be null
        if(this.data.get(index) != null) {
            FieldAccessor<T> accessor = this.data.get(index);

            return accessor;
        }
        return null;
    }


    public FieldVisitor<T> write(int index, T value) {
        if(index < 0 || index >= this.data.size()) {
            throw new IndexOutOfBoundsException("Size: " + this.data.size() + ". Requested: " + index);
        }

        if(this.handle == null) {
            throw new RuntimeException("Handle is NULL! (aka I don't know what to write to!)");
        }

        getAsFieldAccessor(index).set(getHandle(), value);

        return this;
    }

    public boolean isPublic(int index) {
        return getAsFieldAccessor(index).isPublic();
    }

    public boolean isReadOnly(int index) {
        return getAsFieldAccessor(index).isReadOnly();
    }

    public FieldVisitor<T> withType(@Nonnull Class type) {
        FieldVisitor<T> visitor = cache.get(type);

        if(visitor == null) {

            List<FieldAccessor> fields = new LinkedList<FieldAccessor>();

            for(SafeField accessor : getTemplate().getFields()) {
                if(type.isAssignableFrom(accessor.getType().getType())) {
                    if(!fields.contains(accessor))
                        fields.add(accessor);
                }
            }

            visitor = constructNewVisitor(type, fields, this.cache);

            this.cache.put(type, visitor);
        }
        return visitor;
    }

    public List<FieldAccessor> getFields() {
        if(this.data == null) {
            throw new RuntimeException("Fields are not initialized! (= NULL)");
        }
        return this.data;
    }

    protected FieldVisitor<T> constructNewVisitor(Class type, List<FieldAccessor> fields, Map<Class, FieldVisitor> visitorMap) {
        FieldVisitor<T> visitor = new FieldVisitor<T>();
        visitor.initialize(getHandle(), ClassTemplate.create(type), fields, visitorMap);
        return visitor;
    }

    public ClassTemplate<?> getTemplate() {
        if(this.template == null) {
            throw new RuntimeException("ClassTemplate is NULL!");
        }
        return this.template;
    }

    public Object getHandle() {
        if(this.handle == null) {
            throw new RuntimeException("Handle is NULL!");
        }
        return this.handle;
    }

    public int size() {
        return this.data.size();
    }
}
