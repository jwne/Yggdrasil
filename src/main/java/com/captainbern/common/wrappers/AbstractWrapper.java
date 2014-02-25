package com.captainbern.common.wrappers;

import com.captainbern.common.internal.CBCommonLib;
import com.captainbern.common.utils.LogicUtil;

public class AbstractWrapper {

    protected Object handle;
    protected Class<?> handleType;

    public AbstractWrapper(final Class<?> handleType) {
        this.handleType = LogicUtil.notNull(handleType, "HandleType may not be null!", CBCommonLib.LOGGER_REFLECTION);
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
