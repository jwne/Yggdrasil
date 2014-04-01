package com.captainbern.yggdrasil.reflection;

import com.captainbern.yggdrasil.core.Yggdrasil;
import com.captainbern.yggdrasil.utils.CommonUtils;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class ClassTemplate<T> {

    private Class<T> type;
    private List<SafeField> fields;

    public ClassTemplate(){ }

    public ClassTemplate(Class<T> clazz) {
        setClass(clazz);
    }

    protected void setClass(Class<T> clazz) {
        if(clazz == null) {
            throw new IllegalArgumentException("Can't create a ClassTemplate with a NULL-class!");
        }
        this.type = clazz;
    }

    public List<SafeField> getFields() {
        if (type == null) {
            return Collections.emptyList();
        }
        if (fields == null) {
            fields = populateFieldList(new ArrayList<SafeField>(), type);
        }
        return Collections.unmodifiableList(fields);
    }

    private static List<SafeField> populateFieldList(List<SafeField> fields, Class<?> clazz) {
        if (clazz == null) {
            return fields;
        }
        Field[] declared = clazz.getDeclaredFields();
        ArrayList<SafeField<?>> newFields = new ArrayList<SafeField<?>>(declared.length);
        for (Field field : declared) {
            if (!Modifier.isStatic(field.getModifiers())) {
                newFields.add(new SafeField<Object>(field));
            }
        }
        fields.addAll(0, newFields);
        return populateFieldList(fields, clazz.getSuperclass());
    }

    public T newInstance() {
        if(this.type == null){
            Yggdrasil.LOGGER_REFLECTION.warning("Class not set!");
            return null;
        }

        try {
            return  getType().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public T new0Instance(Class... args) {
        if(this.type == null){
            Yggdrasil.LOGGER_REFLECTION.warning("Class not set!");
            return null;
        }

        try {
            Object[] params = new Object[args.length];
            Arrays.fill(params, null);
            return  getType().getConstructor(args).newInstance(params);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Class<T> getType() {
        return this.type;
    }

    public static ClassTemplate<?> create(Class<?> type) {
        if(type == null){
            Yggdrasil.LOGGER_REFLECTION.log(Level.WARNING, "Cannot set class type to NULL!");
            return null;
        }
        return new ClassTemplate(type);
    }

    public void transfer(Object from, Object to) {
        for (FieldAccessor<?> field : this.getFields()) {
            field.transfer(from, to);
        }
    }

    public static ClassTemplate<?> create(String className) {
        Class clazz = CommonUtils.getClass(className);

        if(clazz == null){
            Yggdrasil.LOGGER_REFLECTION.log(Level.WARNING, "Failed to find a valid class for: {0}!", className);
            return null;
        }
        return new ClassTemplate<Object>(clazz);
    }

    public boolean isAssignableFrom(Class<?> clazz) {
        return this.getType().isAssignableFrom(clazz);
    }

    public boolean isInstanceOf(Object object) {
        return this.getType().isInstance(object);
    }

    public boolean isType(Object object) {
        return object != null && isType(object.getClass());
    }

    public boolean isType(Class<?> clazz) {
        return clazz != null && this.type.equals(clazz);
    }

    public <T> T cast(Class<T> clazz) {
        if(isAssignableFrom(clazz))
            return (T) getType().asSubclass(clazz);
        return null;
    }

    public <T> T cast(Object object) {
        if(isAssignableFrom(object.getClass()))
            return (T) getType().cast(object);
        return null;
    }

    public <K> SafeMethod<K> getMethod(String methodName, Class<?>... params) {
        return new SafeMethod<K>(this.getType(), methodName, params);
    }

    public <K> FieldAccessor<K> getField(String fieldName) {
        return new SafeField<K>(getType(), fieldName);
    }

    public <K> FieldAccessor<K> getField(int index) {
        if(index < 0 || index >= this.fields.size()) {
            throw new IndexOutOfBoundsException("Size: " + this.fields.size() + ". Requested: " + index);
        }

        return this.fields.get(index);
    }

    public <K> SafeConstructor<K> getConstructor(Class<?>... params) {
        return new SafeConstructor<K>(getType(), params);
    }

    public <K> K getStaticFieldValue(String name) {
        return SafeField.get(getType(), name);
    }

    public <K> void setStaticFieldValue(String name, K value) {
        SafeField.setStatic(getType(), name, value);
    }

    private static final BiMap<Class, Class> boxedTypes = HashBiMap.create();
    static {
        boxedTypes.put(byte.class, Byte.class);
        boxedTypes.put(boolean.class, Boolean.class);
        boxedTypes.put(short.class, Short.class);
        boxedTypes.put(char.class, Character.class);
        boxedTypes.put(int.class, Integer.class);
        boxedTypes.put(float.class, Float.class);
        boxedTypes.put(long.class, Long.class);
        boxedTypes.put(double.class, Double.class);
    }

    public static Class getUnBoxedType(Class clazz) {
        return boxedTypes.inverse().get(clazz);
    }

    public static Class getBoxedType(Class clazz) {
        return boxedTypes.get(clazz);
    }

    public static boolean hasBoxedType(Class clazz) {
        return getBoxedType(clazz) != null;
    }

    public static boolean hasUnBoxedType(Class clazz) {
        return getUnBoxedType(clazz) != null;
    }
}
