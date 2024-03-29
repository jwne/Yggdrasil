package com.captainbern.yggdrasil.wrappers;

import com.captainbern.yggdrasil.reflection.NMSClassTemplate;
import com.captainbern.yggdrasil.reflection.refs.entity.DataWatcherRef;
import com.captainbern.yggdrasil.reflection.refs.entity.EntityRef;
import com.captainbern.yggdrasil.utils.EntityUtils;
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

        setHandle(DataWatcherRef.CONSTRUCTOR.newInstance(EntityUtils.getHandle(entity)));
    }

    public static WrappedDataWatcher withEntity(Entity entity) {
        return new WrappedDataWatcher(entity);
    }
}
