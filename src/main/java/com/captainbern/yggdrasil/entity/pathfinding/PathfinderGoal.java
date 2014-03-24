package com.captainbern.yggdrasil.entity.pathfinding;

import com.captainbern.yggdrasil.reflection.refs.entity.pathfinder.PathfinderGoalRef;

public class PathfinderGoal {

    protected final Object nmsHandle;

    public PathfinderGoal(final Object nmsHandle) {
        if(nmsHandle == null) {
            throw new IllegalArgumentException("Can't create a PathfinderGoal with a NULL handle!");
        }

        if(!nmsHandle.getClass().isAssignableFrom(PathfinderGoalRef.TEMPLATE.getType())) {
            throw new IllegalArgumentException("Class: \"" + nmsHandle.getClass().getName() + "\" is not a valid PathfinderGoal class!");
        }

        this.nmsHandle = nmsHandle;
    }

    public final Object getHandle() {
        return this.nmsHandle;
    }

    public boolean shouldStart() {
        return PathfinderGoalRef.SHOULD_START.invoke(this.nmsHandle);
    }

    public boolean shouldContinue() {
        return PathfinderGoalRef.SHOULD_CONTINUE.invoke(this.nmsHandle);
    }

    public void start() {
        PathfinderGoalRef.START.invoke(this.nmsHandle);
    }

    public void finish() {
        PathfinderGoalRef.FINISH.invoke(this.nmsHandle);
    }
}
