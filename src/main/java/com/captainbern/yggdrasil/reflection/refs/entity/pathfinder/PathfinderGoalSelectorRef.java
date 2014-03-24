package com.captainbern.yggdrasil.reflection.refs.entity.pathfinder;

import com.captainbern.yggdrasil.reflection.ClassTemplate;
import com.captainbern.yggdrasil.reflection.FieldAccessor;
import com.captainbern.yggdrasil.reflection.MethodAccessor;
import com.captainbern.yggdrasil.reflection.NMSClassTemplate;

import java.util.List;

public class PathfinderGoalSelectorRef {

    public static final ClassTemplate TEMPLATE = NMSClassTemplate.create("PathfinderGoalSelector");

    public static final FieldAccessor<List<Object>> ALL_GOALS = TEMPLATE.getField("b");
    public static final FieldAccessor<List<Object>> ACTIVE_GOALS = TEMPLATE.getField("c");
    public static final FieldAccessor<Integer> DELAY = TEMPLATE.getField("e");
    public static final FieldAccessor<Integer> DELAY_REMAINDER = TEMPLATE.getField("f"); // Default 3, Minecraft does (delay++ % 3 == 0) { // run pathfinders }

    public static final MethodAccessor<Void> ADD_GOAL = TEMPLATE.getMethod("a", int.class, PathfinderGoalRef.TEMPLATE.getType());
    public static final MethodAccessor<Void> REMOVE_GOAL = TEMPLATE.getMethod("a", PathfinderGoalRef.TEMPLATE.getType());
    public static final MethodAccessor<Void> UPDATE_GOALS = TEMPLATE.getMethod("a");

    public static final MethodAccessor<Boolean> CAN_CONTINUE = TEMPLATE.getMethod("a", PathfinderGoalSelectorItemRef.TEMPLATE.getType());
    public static final MethodAccessor<Boolean> CAN_USE = TEMPLATE.getMethod("b", PathfinderGoalSelectorItemRef.TEMPLATE.getType());
    public static final MethodAccessor<Boolean> ARE_COMPATIBLE = TEMPLATE.getMethod("a", PathfinderGoalSelectorItemRef.TEMPLATE.getType(), PathfinderGoalSelectorItemRef.TEMPLATE.getType());
}
