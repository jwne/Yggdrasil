/**
 * Used to provide functionality with MCPC+, as MCPC+ remaps the classes in order to
 * let Mods and Plugins work together in harmony. This is however a downside for Plugins
 * since the Plugins will be remapped and the mods remain untouched.
 *
 * For more info/details about this:
 *
 *     The PluginClassLoader:
 *     https://github.com/MinecraftPortCentral/MCPC-Plus/blob/master/patches/org/bukkit/plugin/java/PluginClassLoader.java.patch
 *
 *     The Remapper is one provided by md_5, it's part of his "SpecialSource" project.
 *     It uses some basic ASM to remap the classed of a jar. Fortunately for us, this remapper is easy reachable
 *     and easy to use since we can get the remapped-classnames fairly easy
 *     ( https://github.com/llbit/ow2-asm/blob/master/src/org/objectweb/asm/commons/Remapper.java#L220 )
 */
package com.captainbern.yggdrasil.reflection.utility;

import com.captainbern.yggdrasil.reflection.ClassTemplate;
import com.captainbern.yggdrasil.reflection.MethodAccessor;
import org.bukkit.Bukkit;

public class RemappedClassHandler extends ClassHandler {

    protected ClassLoader classLoader;
    protected Object remapper;
    protected MethodAccessor<String> map;

    public RemappedClassHandler() {
        this(RemappedClassHandler.class.getClassLoader());
    }

    public RemappedClassHandler(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    protected RemappedClassHandler initialize() throws UnsupportedOperationException, IllegalStateException {
        if (Bukkit.getServer() == null || !Bukkit.getServer().getVersion().contains("MCPC-Plus")) {
            throw new UnsupportedOperationException("Remapper not available!");
        }

        this.remapper = ClassTemplate.create(this.classLoader.getClass()).getField("remapper").get(getClass().getClassLoader());

        if (this.remapper == null)
            throw new IllegalStateException("Remapper is NULL!");

        Class<?> remapperClass = this.remapper.getClass();
        this.map = ClassTemplate.create(remapperClass).getMethod("map", String.class);
        return this;
    }

    public String getRemappedName(String className) {
        return map.invoke(remapper, className.replace('.', '/')).replace('/', '.');
    }

    @Override
    public Class<?> loadClass(String className) throws ClassNotFoundException {
        try {
            return this.classLoader.loadClass(getRemappedName(className));
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Failed to find class: " + className + " (Remapped class-name: " + getRemappedName(className) + ")");
        }
    }
}
