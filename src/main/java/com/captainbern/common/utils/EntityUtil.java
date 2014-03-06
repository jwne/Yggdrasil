package com.captainbern.common.utils;

import com.captainbern.common.reflection.refs.entity.craft.CraftEntityRef;
import org.bukkit.entity.Entity;

public class EntityUtil {

    public static Object getHandle(Entity entity) {
        return CraftEntityRef.GET_HANDLE.invoke(entity);
    }
}
