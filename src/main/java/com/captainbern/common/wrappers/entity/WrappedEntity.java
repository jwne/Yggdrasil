package com.captainbern.common.wrappers.entity;

import com.captainbern.common.reflection.refs.entity.EntityRef;
import com.captainbern.common.reflection.refs.entity.craft.CraftEntityRef;
import com.captainbern.common.wrappers.WrappedDataWatcher;
import org.bukkit.entity.Entity;

import java.util.Random;

public class WrappedEntity {

    protected final Entity BUKKIT_HANDLE;
    protected final Object NMS_HANDLE;

    public WrappedEntity(Entity entity) {
        this.BUKKIT_HANDLE = entity;
        this.NMS_HANDLE = CraftEntityRef.getHandle(entity);
    }

    public Entity getHandle() {
        return this.BUKKIT_HANDLE;
    }

    public Object getNMSHandle() {
        return this.NMS_HANDLE;
    }

    public int getId() {
        return EntityRef.ID.get(getNMSHandle());
    }

    public double getLastX() {
        return EntityRef.LAST_X.get(getNMSHandle());
    }

    public double getLastY() {
        return EntityRef.LAST_Y.get(getNMSHandle());
    }

    public double getLastZ() {
        return EntityRef.LAST_Z.get(getNMSHandle());
    }

    public double getMotX() {
        return EntityRef.MOT_X.get(getNMSHandle());
    }

    public double getMotY() {
        return EntityRef.MOT_Y.get(getNMSHandle());
    }

    public double getMotZ() {
        return EntityRef.MOT_Z.get(getNMSHandle());
    }

    public float getWidth() {
        return EntityRef.WIDTH.get(getNMSHandle());
    }

    public float getHeight() {
        return EntityRef.WIDTH.get(getNMSHandle());
    }

    public float getLength() {
        return EntityRef.LENGTH.get(getNMSHandle());
    }

    public boolean isInWater() {
        return EntityRef.IN_WATER.get(getNMSHandle());
    }

    public boolean isInvulnerable() {
        return EntityRef.INVULNERABLE.get(getNMSHandle());
    }

    public boolean isJustCreated() {
        return EntityRef.JUST_CREATED.get(getNMSHandle());
    }

    public boolean isFireProof() {
        return EntityRef.FIRE_PROOF.get(getNMSHandle());
    }

    public int getDimension() {
        return EntityRef.DIMENSION.get(getNMSHandle());
    }

    public Random getRandom() {
        return EntityRef.RANDOM.get(getNMSHandle());
    }

    public WrappedDataWatcher getDataWatcher() {
        return new WrappedDataWatcher(EntityRef.DATAWATCHER.get(getNMSHandle()));
    }
}
