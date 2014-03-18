package com.captainbern.common.nbt;

import com.captainbern.common.reflection.ClassTemplate;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class NBTConverter {

    public static enum VanillaNBTType {

        ;

        private static final BiMap<Class, NBTBase> values = HashBiMap.create();

        static {
            for(VanillaNBTType type : values()) {
                values.put(type.getClassTemplate().getType(), type.getNBTBase());
            }
        }

        final ClassTemplate classTemplate;
        final NBTBase base;

        private VanillaNBTType(ClassTemplate template, NBTBase base) {
            this.classTemplate = template;
            this.base = base;
        }

        public ClassTemplate getClassTemplate() {
            return this.classTemplate;
        }

        public NBTBase getNBTBase() {
            return this.base;
        }

        public static NBTBase getNBTBaseOfVanilla(Class clazz) {
            return values.get(clazz);
        }

        public static Class getVanillaOfNBT(NBTBase base) {
            return values.inverse().get(base);
        }
    }

    public static Class getVanillaNBTTypeOf(NBTBase base) {
       return VanillaNBTType.getVanillaOfNBT(base);
    }

    public static Object convertToVanillaTag(NBTBase base, VanillaNBTType type) {
        return null;
    }
}
