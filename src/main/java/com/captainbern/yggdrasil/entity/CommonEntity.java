package com.captainbern.yggdrasil.entity;

import com.captainbern.yggdrasil.reflection.refs.entity.craft.CraftEntityRef;
import com.captainbern.yggdrasil.wrappers.WrappedDataWatcher;
import org.bukkit.entity.LivingEntity;

import java.util.Random;

public abstract class CommonEntity<T extends LivingEntity> {

    protected final T bukkitHandle;
    protected final Object nmsHandle;

    public CommonEntity(T handle) {
        if(handle == null) {
            throw new IllegalArgumentException("Can't create a CommonEntity with a NULL bukkit-handle!");
        }

        this.bukkitHandle = handle;
        this.nmsHandle = CraftEntityRef.getHandle(handle);
    }

    public T getBukkitHandle() {
        if(this.bukkitHandle == null) {
            throw new RuntimeException("Bukkit-handle is NULL!");
        }

        return this.bukkitHandle;
    }

    public Object getNMSHandle() {
        if(this.nmsHandle == null) {
            throw new RuntimeException("NMS-handle is NULL!");
        }

        return this.nmsHandle;
    }

    public int getId() {
        return 0;
    }

    public float getWidth() {
        return 0;
    }

    public float getHeight() {
        return 0;
    }

    public CommonEntityType getEntityType() {
        return null;
    }

    public boolean hasSpawned() {
        return false;
    }

    public double getLastX() {
        return 0;
    }

    public double getLastY() {
        return 0;
    }

    public double getLastZ() {
        return 0;
    }

    public double getMotX() {
        return 0;
    }

    public double getMotY() {
        return 0;
    }

    public double getMotZ() {
        return 0;
    }

    public boolean isInWater() {
        return false;
    }

    public boolean isInvulnerable() {
        return false;
    }

    public boolean isJustCreated() {
        return false;
    }

    public boolean isFireProof() {
        return false;
    }

    public int getDimension() {
        return 0;
    }

    public Random getRandom() {
        return null;
    }

    public WrappedDataWatcher getDataWatcher() {
        return null;
    }
}
