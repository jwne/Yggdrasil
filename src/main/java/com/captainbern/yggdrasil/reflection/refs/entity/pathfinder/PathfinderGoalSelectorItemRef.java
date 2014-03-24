package com.captainbern.yggdrasil.reflection.refs.entity.pathfinder;

import com.captainbern.yggdrasil.reflection.ClassTemplate;
import com.captainbern.yggdrasil.reflection.FieldAccessor;
import com.captainbern.yggdrasil.reflection.NMSClassTemplate;

public class PathfinderGoalSelectorItemRef {

    public static final ClassTemplate TEMPLATE = NMSClassTemplate.create("PathfinderGoalSelectorItem");

    public static final FieldAccessor<Object> PATHFINDER_GOAL = TEMPLATE.getField("a");
    public static final FieldAccessor<Integer> PRIORITY = TEMPLATE.getField("b");
    public static final FieldAccessor<Object> PATHFINDER_GOAL_SELECTOR = TEMPLATE.getField("c");
}
