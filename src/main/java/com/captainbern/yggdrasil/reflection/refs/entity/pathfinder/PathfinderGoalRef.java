package com.captainbern.yggdrasil.reflection.refs.entity.pathfinder;

import com.captainbern.yggdrasil.reflection.ClassTemplate;
import com.captainbern.yggdrasil.reflection.MethodAccessor;
import com.captainbern.yggdrasil.reflection.NMSClassTemplate;

public class PathfinderGoalRef {

    public static final ClassTemplate TEMPLATE = NMSClassTemplate.create("PathfinderGoal");

    public static final MethodAccessor<Boolean> SHOULD_START = TEMPLATE.getMethod("a");
    public static final MethodAccessor<Boolean> SHOULD_CONTINUE = TEMPLATE.getMethod("b");

    public static final MethodAccessor<Void> START = TEMPLATE.getMethod("c");
    public static final MethodAccessor<Void> FINISH = TEMPLATE.getMethod("d");

    public static final MethodAccessor<Void> TICK = TEMPLATE.getMethod("e");
}
