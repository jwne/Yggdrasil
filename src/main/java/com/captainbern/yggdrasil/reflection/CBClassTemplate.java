package com.captainbern.yggdrasil.reflection;

import com.captainbern.yggdrasil.core.Yggdrasil;
import com.captainbern.yggdrasil.utils.CommonUtil;

import java.util.logging.Level;

public class CBClassTemplate extends ClassTemplate<Object> {

    public CBClassTemplate() {
        setCBClass(getClass().getSimpleName());
    }

    public CBClassTemplate(String className) {
        setCBClass(className);
    }

    protected void setCBClass(String name) {
        Class clazz = CommonUtil.getCBClass(name);
        if(clazz == null){
            Yggdrasil.LOGGER_REFLECTION.log(Level.WARNING, "Failed to find a valid class for: {0}!", name);
        }
        setClass(clazz);
    }

    public static CBClassTemplate create(String className) {
        return new CBClassTemplate(className);
    }
}
