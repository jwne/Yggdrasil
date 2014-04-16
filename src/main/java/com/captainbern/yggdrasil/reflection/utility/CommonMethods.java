package com.captainbern.yggdrasil.reflection.utility;

import com.captainbern.yggdrasil.reflection.MethodAccessor;
import com.captainbern.yggdrasil.reflection.SafeMethod;
import com.google.common.base.Preconditions;
import org.bukkit.entity.Entity;

public class CommonMethods {

    private CommonMethods() {
        super();
    }

    public static Object getVanillaObject(Entity entity) {
        Preconditions.checkNotNull(entity);

        MethodAccessor<Object> getHandle = new SafeMethod<Object>(CommonReflection.getCraftEntityClass(), "getHandle");

        return getHandle.invoke(entity);
    }
}
