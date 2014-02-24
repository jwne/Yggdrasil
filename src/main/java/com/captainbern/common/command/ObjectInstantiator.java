package com.captainbern.common.command;

import com.captainbern.common.internal.CBCommonLib;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class ObjectInstantiator {

    private Object[] argInstances;
    private Class[] argClasses;

    public ObjectInstantiator(Object... args) {
        this.argInstances = args;
        this.argClasses = new Class[args.length];

        for(int i = 0; i < args.length; i++) {
            argClasses[i] = argInstances[i].getClass();
        }
    }

    public Object instantiate(Class<?> clazz) {
        try {
            Constructor constructor = clazz.getConstructor(this.argClasses);
            constructor.setAccessible(true);
            return constructor.newInstance(this.argInstances);
        } catch (InvocationTargetException e) {
            CBCommonLib.LOGGER_REFLECTION.warning("Failed to instantiate commands class: " + clazz.getName());
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e) {
            CBCommonLib.LOGGER_REFLECTION.warning("Failed to instantiate commands class: " + clazz.getName());
            e.printStackTrace();
            return null;
        } catch (InstantiationException e) {
            CBCommonLib.LOGGER_REFLECTION.warning("Failed to instantiate commands class: " + clazz.getName());
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            CBCommonLib.LOGGER_REFLECTION.warning("Failed to instantiate commands class: " + clazz.getName());
            e.printStackTrace();
            return null;
        }
    }
}
