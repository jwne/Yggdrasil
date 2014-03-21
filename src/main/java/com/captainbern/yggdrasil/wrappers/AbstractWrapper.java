package com.captainbern.yggdrasil.wrappers;

import com.captainbern.yggdrasil.core.Yggdrasil;
import com.captainbern.yggdrasil.utils.LogicUtil;

public class AbstractWrapper {

    protected Object handle;
    protected Class<?> handleType;

    public AbstractWrapper(final Class<?> handleType) {
        this.handleType = LogicUtil.notNull(handleType, "HandleType may not be null!", Yggdrasil.LOGGER_REFLECTION);
    }

    protected void setHandle(final Object handle) {
        if(handle == null)
            throw new IllegalArgumentException("Handle cannot be null!");
        if(!handleType.isAssignableFrom(handle.getClass()))
            throw new IllegalArgumentException("Handle (" + handle.toString() + ") is not a " + handleType + ", but " + handle.getClass());
        this.handle = handle;
    }

    public Object getHandle() {
        return this.handle;
    }

    public Class<?> getHandleType() {
        return this.handleType;
    }
}
