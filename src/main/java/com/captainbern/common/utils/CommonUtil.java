package com.captainbern.common.utils;

import com.captainbern.common.internal.Yggdrasil;

public class CommonUtil {

    public static Class<?> getNMSClass(String className){
        return Yggdrasil.getCommonServer().getNMSClass(className);
    }

    public static Class<?> getCBClass(String className){
        return Yggdrasil.getCommonServer().getCBClass(className);
    }

    public static Class<?> getClass(String className) {
        return Yggdrasil.getCommonServer().getClass(className);
    }
}
