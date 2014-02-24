package com.captainbern.common.utils;

import com.captainbern.common.CommonPlugin;

public class CommonUtil {

    public static Class<?> getNMSClass(String className){
        return CommonPlugin.COMMON_SERVER.getNMSClass(className);
    }

    public static Class<?> getCBClass(String className){
        return CommonPlugin.COMMON_SERVER.getCBClass(className);
    }

    public static Class<?> getClass(String className) {
        return CommonPlugin.COMMON_SERVER.getClass(className);
    }
}
