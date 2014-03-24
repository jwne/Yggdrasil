package com.captainbern.yggdrasil.entity.pathfinding;

import com.captainbern.yggdrasil.reflection.refs.entity.pathfinder.PathfinderGoalRef;
import com.captainbern.yggdrasil.reflection.refs.entity.pathfinder.PathfinderGoalSelectorItemRef;
import com.captainbern.yggdrasil.reflection.refs.entity.pathfinder.PathfinderGoalSelectorRef;

public class PathfinderGoalSelectorItem {

    private final Object nmsHandle;

    public PathfinderGoalSelectorItem(final Object nmsHandle) {
        if(nmsHandle == null) {
            throw new IllegalArgumentException("Can't create a PathfinderGoalSelectorItem with a NULL handle!");
        }

        if(!nmsHandle.getClass().isAssignableFrom(PathfinderGoalSelectorItemRef.TEMPLATE.getType())) {
            throw new IllegalArgumentException("Class: \"" + nmsHandle.getClass().getName() + "\" is not a valid PathfinderGoalSelectorItem class!");
        }
        this.nmsHandle = nmsHandle;
    }

    public final Object getHandle() {
        return this.nmsHandle;
    }

    public Object getPathfinderGoal() {
        return PathfinderGoalSelectorItemRef.PATHFINDER_GOAL.get(this.nmsHandle);
    }

    public void setPathfinderGoal(final Object goal) {
        if(!goal.getClass().isAssignableFrom(PathfinderGoalRef.TEMPLATE.getType())) {
            throw new IllegalArgumentException("Class: \"" + goal.getClass().getName() + "\" is not a valid PathfinderGoal!");
        }

        PathfinderGoalSelectorItemRef.PATHFINDER_GOAL.set(this.nmsHandle, goal);
    }

    public void setPathfinderGoal(final PathfinderGoal goal) {
        this.setPathfinderGoal(goal.getHandle());
    }

    public int getPriority() {
        return PathfinderGoalSelectorItemRef.PRIORITY.get(this.nmsHandle);
    }

    public void setPriority(final int priority) {
        PathfinderGoalSelectorItemRef.PRIORITY.set(this.nmsHandle, priority);
    }

    public Object getPathfinderGoalSelector() {
        return PathfinderGoalSelectorItemRef.PATHFINDER_GOAL_SELECTOR.get(this.nmsHandle);
    }

    public void setPathfinderGoalSelector(final Object pathfinderGoalSelector) {
        if(!pathfinderGoalSelector.getClass().isAssignableFrom(PathfinderGoalSelectorRef.TEMPLATE.getType())) {
            throw new IllegalArgumentException("Class: \"" + pathfinderGoalSelector.getClass().getName() + "\" is not a valid PathfinderGoalSelector!");
        }

        PathfinderGoalSelectorItemRef.PATHFINDER_GOAL_SELECTOR.set(this.nmsHandle, pathfinderGoalSelector);
    }

    public void setPathfinderGoalSelector(final PathfinderGoalSelector selector) {
        this.setPathfinderGoalSelector(selector.getHandle());
    }
}
