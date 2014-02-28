package com.captainbern.common.utils;

import com.captainbern.common.internal.CBCommonLib;

public class CommonUtil {

    public static Class<?> getNMSClass(String className){
        return CBCommonLib.getCommonServer().getNMSClass(className);
    }

    public static Class<?> getCBClass(String className){
        return CBCommonLib.getCommonServer().getCBClass(className);
    }

    public static Class<?> getClass(String className) {
        return CBCommonLib.getCommonServer().getClass(className);
    }
}
