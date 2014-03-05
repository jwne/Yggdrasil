package com.captainbern.common.reflection.refs;

import com.captainbern.common.reflection.CBClassTemplate;
import com.captainbern.common.reflection.ClassTemplate;
import com.captainbern.common.reflection.MethodAccessor;

public class CraftPlayerRef {

    public static final ClassTemplate TEMPLATE = CBClassTemplate.create("entity.CraftPlayer");

    public static MethodAccessor<Object> GET_HANDLE = TEMPLATE.getMethod("getHandle");
}
