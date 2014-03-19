package com.captainbern.common.reflection;

import com.captainbern.common.internal.Yggdrasil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Level;

public class SafeField<T> implements FieldAccessor<T> {

    private Field field;
    private boolean isStatic;

    public SafeField(Field field){
        setField(field);
    }

    public SafeField(Class<?> coreClass, String fieldName){
        try {
            Field field = coreClass.getDeclaredField(fieldName);
            setField(field);
        } catch (NoSuchFieldException e) {
            Yggdrasil.LOGGER_REFLECTION.log(Level.WARNING, "Failed to find field: {0} in class: {1}!", new Object[]{coreClass.getSimpleName(), fieldName.toString()});
        }
    }

    protected void setField(Field field){
        if(!field.isAccessible()){
            try{
                field.setAccessible(true);
            }catch(Exception e){
                e.printStackTrace();
                field = null;
            }
        }
        this.field = field;
        this.isStatic = Modifier.isStatic(field.getModifiers());
    }

    @Override
    public Field getField() {
        if(this.field == null)
            throw new RuntimeException("Field is NULL!");
        return this.field;
    }

    @Override
    public ClassTemplate getType() {
        return ClassTemplate.create(field.getClass());
    }

    @Override
    public boolean set(Object instance, T value) {
        if(!isStatic && instance == null){
            throw new UnsupportedOperationException("Non-static fields require a valid instance passed in!");
        }

        try {
            this.field.set(instance, value);
            return true;
        } catch (IllegalAccessException e) {
            Yggdrasil.LOGGER_REFLECTION.warning("Failed to set field: " + toString());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public T get(Object instance) {
        if(!isStatic && instance == null){
            throw new UnsupportedOperationException("Non-static fields require a valid instance passed in!");
        }
        try {
            return (T) this.field.get(instance);
        } catch (IllegalAccessException e) {
            Yggdrasil.LOGGER_REFLECTION.warning("Failed to access field: " + toString());
        }
        return null;
    }

    @Override
    public T transfer(Object from, Object to) {
        if (this.field == null) {
            return null;
        }
        T old = get(to);
        set(to, get(from));
        return old;
    }

    public String getName(){
        return this.field.getName();
    }

    public String toString(){
        StringBuilder string = new StringBuilder(75);
        int mod = this.field.getModifiers();
        if(Modifier.isPublic(mod)){
            string.append("public ");
        }else if(Modifier.isPrivate(mod)){
            string.append("private ");
        }else if(Modifier.isProtected(mod)){
            string.append("protected ");
        }

        if(Modifier.isStatic(mod)){
            string.append("static ");
        }

        string.append(this.field.getName());

        return string.toString();
    }

    @Override
    public boolean isPublic() {
        return Modifier.isPublic(field.getModifiers());
    }

    @Override
    public boolean isReadOnly() {
        return Modifier.isFinal(field.getModifiers());
    }

    @Override
    public void setFinalStatic(T newValue) {
        FieldAccessor<Integer> modifierField = new SafeField<Integer>(Field.class, "modifiers");

        modifierField.set(getField(), getField().getModifiers() & ~Modifier.FINAL);

        set(null, newValue);
    }

    public static <T> T get(Class<?> clazz, String fieldName) {
        return new SafeField<T>(clazz, fieldName).get(null);
    }

    public static <T> T get(Object instance, String fieldName){
        return new SafeField<T>(instance.getClass(), fieldName).get(instance);
    }

    public static <T> void set(Object instance, String fieldName, T value){
        new SafeField<T>(instance.getClass(), fieldName).set(instance, value);
    }

    public static <T> void setStatic(Class<?> clazz, String fieldname, T value) {
        new SafeField<T>(clazz, fieldname).set(null, value);
    }
}
