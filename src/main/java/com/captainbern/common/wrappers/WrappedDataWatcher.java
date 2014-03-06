package com.captainbern.common.wrappers;

import com.captainbern.common.reflection.NMSClassTemplate;
import com.captainbern.common.reflection.refs.entity.DataWatcherRef;
import com.captainbern.common.reflection.refs.entity.EntityRef;
import com.captainbern.common.utils.EntityUtil;
import org.bukkit.entity.Entity;

public class WrappedDataWatcher extends AbstractWrapper {

    public WrappedDataWatcher() {
        super(DataWatcherRef.TEMPLATE.getType());
        this.setHandle(NMSClassTemplate.create(this.getHandleType()).new0Instance(EntityRef.TEMPLATE.getType()));

    }

    public WrappedDataWatcher(Object handle) {
        super(DataWatcherRef.TEMPLATE.getType());
        setHandle(handle);
    }

    public WrappedDataWatcher(Entity entity) {
        super(DataWatcherRef.TEMPLATE.getType());

        setHandle(DataWatcherRef.CONSTRUCTOR.newInstance(EntityUtil.getHandle(entity)));
    }

    public static WrappedDataWatcher withEntity(Entity entity) {
        return new WrappedDataWatcher(entity);
    }
}
