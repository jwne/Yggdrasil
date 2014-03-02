package com.captainbern.common.wrappers;

import com.captainbern.common.reflection.NMSClassTemplate;

public class WrappedDataWatcher extends AbstractWrapper {

    public WrappedDataWatcher() {
        super(NMSClassTemplate.create("DataWatcher").getType());

        this.setHandle(NMSClassTemplate.create(this.getHandleType()).new0Instance(NMSClassTemplate.create("Entity").getType()));
    }
}
