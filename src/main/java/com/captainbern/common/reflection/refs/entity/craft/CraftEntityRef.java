package com.captainbern.common.reflection.refs.entity.craft;

import com.captainbern.common.reflection.CBClassTemplate;
import com.captainbern.common.reflection.ClassTemplate;
import com.captainbern.common.reflection.MethodAccessor;
import org.bukkit.entity.Entity;

public class CraftEntityRef {

    public static ClassTemplate TEMPLATE = CBClassTemplate.create("entity.CraftEntity");

    public static MethodAccessor<Object> GET_HANDLE = TEMPLATE.getMethod("getHandle");

    public static Object getHandle(Entity entity) {
        return GET_HANDLE.invoke(entity);
    }
}
