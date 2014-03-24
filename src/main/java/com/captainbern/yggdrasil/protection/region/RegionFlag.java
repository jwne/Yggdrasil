package com.captainbern.yggdrasil.protection.region;

public abstract class RegionFlag<T> {

    protected T handle;
    protected String name;
    protected Region region;
    protected boolean state;

    public abstract String getName();

    public abstract void setName(String name);

    public abstract Region getRegion();

    public abstract void setRegion(Region region);

    public abstract void toggle();
}
