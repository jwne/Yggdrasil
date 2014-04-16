package com.captainbern.yggdrasil.reflection;

import com.captainbern.yggdrasil.core.Yggdrasil;
import com.captainbern.yggdrasil.reflection.utility.CommonReflection;

import java.util.logging.Level;

public class NMSClassTemplate extends ClassTemplate {

    protected NMSClassTemplate() {
        setNMSClass(getClass().getSimpleName());
    }

    public NMSClassTemplate(String className) {
        setNMSClass(className);
    }

    protected void setNMSClass(String name) {
        Class clazz = CommonReflection.getMinecraftClass(name);
        if(clazz == null){
            Yggdrasil.LOGGER_REFLECTION.log(Level.WARNING, "Failed to find a valid class for: {0}!", name);
        }
        setClass(clazz);
    }

    public static NMSClassTemplate create(String className) {
        return new NMSClassTemplate(className);
    }
}
