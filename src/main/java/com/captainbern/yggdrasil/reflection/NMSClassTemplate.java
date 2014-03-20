package com.captainbern.yggdrasil.reflection;

import com.captainbern.yggdrasil.internal.Yggdrasil;
import com.captainbern.yggdrasil.utils.CommonUtil;

import java.util.logging.Level;

public class NMSClassTemplate extends ClassTemplate {

    protected NMSClassTemplate() {
        setNMSClass(getClass().getSimpleName());
    }

    public NMSClassTemplate(String className) {
        setNMSClass(className);
    }

    protected void setNMSClass(String name) {
        Class clazz = CommonUtil.getNMSClass(name);
        if(clazz == null){
            Yggdrasil.LOGGER_REFLECTION.log(Level.WARNING, "Failed to find a valid class for: {0}!", name);
        }
        setClass(clazz);
    }

    public static NMSClassTemplate create(String className) {
        return new NMSClassTemplate(className);
    }
}
