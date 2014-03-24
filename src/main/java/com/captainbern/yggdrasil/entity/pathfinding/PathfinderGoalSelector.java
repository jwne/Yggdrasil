package com.captainbern.yggdrasil.entity.pathfinding;

import com.captainbern.yggdrasil.reflection.refs.entity.pathfinder.PathfinderGoalRef;
import com.captainbern.yggdrasil.reflection.refs.entity.pathfinder.PathfinderGoalSelectorRef;

import java.util.ArrayList;
import java.util.List;

public class PathfinderGoalSelector {

    protected final Object nmsHandle;

    public PathfinderGoalSelector(final Object nmsHandle) {
        if(nmsHandle == null) {
            throw new IllegalArgumentException("Can't create a PathfinderGoalSelector with a NULL handle!");
        }

        if(!nmsHandle.getClass().isAssignableFrom(PathfinderGoalSelectorRef.TEMPLATE.getType())) {
            throw new IllegalArgumentException("Class: \"" + nmsHandle.getClass() + "\" is not a valid PathfinderGoalSelector class!");
        }

        this.nmsHandle = nmsHandle;
    }

    public final Object getHandle() {
        return this.nmsHandle;
    }

    public List<Object> getGoalsAsVanilla() {
        return PathfinderGoalSelectorRef.ALL_GOALS.get(this.nmsHandle);
    }

    public List<PathfinderGoal> getGoals() {
        List<PathfinderGoal> goals = new ArrayList<PathfinderGoal>();
        for(Object goal : getGoalsAsVanilla()) {
            goals.add(new PathfinderGoal(goal));
        }

        return goals;
    }

    public List<Object> getActiveGoalsAsVanilla() {
        return PathfinderGoalSelectorRef.ACTIVE_GOALS.get(this.nmsHandle);
    }

    public List<PathfinderGoal> getActiveGoals() {
        List<PathfinderGoal> goals = new ArrayList<PathfinderGoal>();
        for(Object goal : getActiveGoalsAsVanilla()) {
            goals.add(new PathfinderGoal(goal));
        }
        return goals;
    }

    public int getDelay() {
        return PathfinderGoalSelectorRef.DELAY.get(this.nmsHandle);
    }

    public void setDelay(final int delay) {
        PathfinderGoalSelectorRef.DELAY.set(this.nmsHandle, delay);
    }

    public int getDelayRemainder() {
        return PathfinderGoalSelectorRef.DELAY_REMAINDER.get(this.nmsHandle);
    }

    public void setDelayRemainder(final int delayRemainder) {
        PathfinderGoalSelectorRef.DELAY_REMAINDER.set(this.nmsHandle, delayRemainder);
    }

    public void addGoal(final int priority, final Object goal) {
        if(goal.getClass().isAssignableFrom(PathfinderGoalRef.TEMPLATE.getType())) {
            throw new IllegalArgumentException("Class: \"" + goal.getClass().getName() + "\" is not a valid PathfinderGoal!");
        }

        PathfinderGoalSelectorRef.ADD_GOAL.invoke(this.nmsHandle, priority, goal);
    }

    public void addGoal(final int priority, final PathfinderGoal goal) {
        this.addGoal(priority, goal.getHandle());
    }

    public void removeGoal(final Object goal) {
        if(goal.getClass().isAssignableFrom(PathfinderGoalRef.TEMPLATE.getType())) {
            throw new IllegalArgumentException("Class: \"" + goal.getClass().getName() + "\" is not a valid PathfinderGoal!");
        }

        PathfinderGoalSelectorRef.REMOVE_GOAL.invoke(this.nmsHandle, goal);
    }

    public void removeGoal(final PathfinderGoal goal) {
        this.removeGoal(goal.getHandle());
    }

    public void updateGoals() {
        PathfinderGoalSelectorRef.UPDATE_GOALS.invoke(this.nmsHandle);
    }

    public boolean canContinue(final Object goal) {
        if(goal.getClass().isAssignableFrom(PathfinderGoalRef.TEMPLATE.getType())) {
            throw new IllegalArgumentException("Class: \"" + goal.getClass().getName() + "\" is not a valid PathfinderGoal!");
        }

        return PathfinderGoalSelectorRef.CAN_CONTINUE.invoke(this.nmsHandle, goal);
    }

    public boolean canContinue(final PathfinderGoal goal) {
        return this.canContinue(goal.getHandle());
    }

    public boolean canUse(final Object goal) {
        if(goal.getClass().isAssignableFrom(PathfinderGoalRef.TEMPLATE.getType())) {
            throw new IllegalArgumentException("Class: \"" + goal.getClass().getName() + "\" is not a valid PathfinderGoal!");
        }

        return PathfinderGoalSelectorRef.CAN_USE.invoke(this.nmsHandle, goal);
    }

    public boolean canUse(final PathfinderGoal goal) {
        return this.canUse(goal.getHandle());
    }

    public boolean areCompatible(final Object goal1, final Object goal2) {
        if(goal1.getClass().isAssignableFrom(PathfinderGoalRef.TEMPLATE.getType())) {
            throw new IllegalArgumentException("Class: \"" + goal1.getClass().getName() + "\" is not a valid PathfinderGoal!");
        }

        if(goal2.getClass().isAssignableFrom(PathfinderGoalRef.TEMPLATE.getType())) {
            throw new IllegalArgumentException("Class: \"" + goal2.getClass().getName() + "\" is not a valid PathfinderGoal!");
        }

        return PathfinderGoalSelectorRef.ARE_COMPATIBLE.invoke(this.nmsHandle, goal1, goal2);
    }

    public boolean areCompatible(PathfinderGoal goal1, PathfinderGoal goal2) {
        return this.areCompatible(goal1.getHandle(), goal2.getHandle());
    }
}
