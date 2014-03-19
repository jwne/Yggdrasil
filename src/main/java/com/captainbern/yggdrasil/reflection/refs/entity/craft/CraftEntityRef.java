package com.captainbern.yggdrasil.reflection.refs.entity.craft;

import com.captainbern.yggdrasil.reflection.CBClassTemplate;
import com.captainbern.yggdrasil.reflection.ClassTemplate;
import com.captainbern.yggdrasil.reflection.MethodAccessor;
import org.bukkit.entity.Entity;

public class CraftEntityRef {

    public static ClassTemplate TEMPLATE = CBClassTemplate.create("entity.CraftEntity");

    public static MethodAccessor<Object> GET_HANDLE = TEMPLATE.getMethod("getHandle");

    public static Object getHandle(Entity entity) {
        return GET_HANDLE.invoke(entity);
    }
}
