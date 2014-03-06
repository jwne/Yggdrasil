package com.captainbern.common.reflection.refs.entity;

import com.captainbern.common.reflection.ClassTemplate;
import com.captainbern.common.reflection.MethodAccessor;
import com.captainbern.common.reflection.NMSClassTemplate;
import com.captainbern.common.reflection.SafeConstructor;

import java.util.List;

public class DataWatcherRef {

    public static final ClassTemplate TEMPLATE = NMSClassTemplate.create("DataWatcher");

    public static final MethodAccessor<Void> WRITE = TEMPLATE.getMethod("a", int.class, Object.class);
    public static final MethodAccessor<Void> WATCH = TEMPLATE.getMethod("watch", int.class, Object.class);
    public static final MethodAccessor<List<Object>> GET_WATCHABLE_OBJECTS = TEMPLATE.getMethod("c");
    public static final MethodAccessor<List<Object>> UNWATCH_GET_WATCHABLE_OBJECTS = TEMPLATE.getMethod("b");
    public static final MethodAccessor<Object> READ = TEMPLATE.getMethod("i", int.class);
    public static final MethodAccessor<Boolean> IS_CHANGED = TEMPLATE.getMethod("a");
    public static final MethodAccessor<Boolean> IS_EMPTY = TEMPLATE.getMethod("d");
    public static final SafeConstructor<Object> CONSTRUCTOR = TEMPLATE.getConstructor(EntityRef.TEMPLATE.getType());
}
