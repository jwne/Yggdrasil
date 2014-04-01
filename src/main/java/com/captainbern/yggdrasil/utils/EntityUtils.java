package com.captainbern.yggdrasil.utils;

import com.captainbern.yggdrasil.reflection.refs.entity.craft.CraftEntityRef;
import org.bukkit.entity.Entity;

public class EntityUtils {

    public EntityUtils() {
        super();
    }

    public static Object getHandle(Entity entity) {
        return CraftEntityRef.GET_HANDLE.invoke(entity);
    }
}
