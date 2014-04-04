package com.captainbern.yggdrasil.reflection.utility;

import com.captainbern.yggdrasil.core.Yggdrasil;
import com.captainbern.yggdrasil.reflection.ClassTemplate;
import com.captainbern.yggdrasil.reflection.MethodAccessor;
import com.captainbern.yggdrasil.server.ServerBrand;

public class MapperClassFinder {

    protected ClassLoader classLoader;
    protected Object remapper;
    protected MethodAccessor<String> map;

    public MapperClassFinder() {
        this(MapperClassFinder.class.getClassLoader());
    }

    public MapperClassFinder(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    protected void initialize() {
        if(Yggdrasil.getCommonServer().getServerBrand() != ServerBrand.MCPC_PLUS)
            throw new UnsupportedOperationException("The current brand doesn't support Remmapers!");

        this.remapper = ClassTemplate.create(this.classLoader.getClass()).getField("remapper");

        if(this.remapper == null)
            throw new IllegalStateException("Remapper is NULL!");

        Class<?> remapperClass = this.remapper.getClass();

        this.map = ClassTemplate.create(remapperClass).getMethod("map", String.class);
    }

    public String getRemappedName(String className) {
        return map.invoke(remapper, className.replace('.', '/')).replace('/', '.');
    }
}
