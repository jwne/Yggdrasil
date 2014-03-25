package com.captainbern.yggdrasil.utils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.HashMap;

public class ClassUtils {

    public static final String CLASS_FILE_SUFFIX = ".class";

    public static final char INNER_SEPARATOR_CHAR = '$';

    public static final String INNER_SEPARATOR = String.valueOf(INNER_SEPARATOR_CHAR);

    public static final char PACKAGE_SEPARATOR_CHAR = '.';

    public static final String PACKAGE_SEPARATOR = String.valueOf(PACKAGE_SEPARATOR_CHAR);

    private static final HashMap<Class, Class> wrapperToPrimitive = new HashMap<Class, Class>();

    static {
        wrapperToPrimitive.put(Boolean.class, boolean.class);
        wrapperToPrimitive.put(Byte.class, byte.class);
        wrapperToPrimitive.put(Character.class, char.class);
        wrapperToPrimitive.put(Double.class, double.class);
        wrapperToPrimitive.put(Float.class, float.class);
        wrapperToPrimitive.put(Integer.class, int.class);
        wrapperToPrimitive.put(Long.class, long.class);
        wrapperToPrimitive.put(Short.class, short.class);
        wrapperToPrimitive.put(Void.class, void.class);
    }

    private static final HashMap<Class, Class> primitiveToWrapper = new HashMap<Class, Class>();
    static {
        for(final Class<?> wrapper : wrapperToPrimitive.keySet()) {
            final Class<?> primitive = wrapperToPrimitive.get(wrapper);
            if(primitive != null && !primitive.equals(wrapper)) {
                primitiveToWrapper.put(primitive, wrapper);
            }
        }
    }

    /**
     * Returns whether or not the given class is a primitive
     * @param clazz
     * @return
     */
    public static boolean isPrimitive(final Class clazz) {
        return primitiveToWrapper.containsKey(clazz);
    }

    /**
     * Returns whether or not the given class is a wrapper for a primitive type
     * @param clazz
     * @return
     */
    public static boolean isWrapper(final Class clazz) {
        return wrapperToPrimitive.containsKey(clazz);
    }

    /**
     * Returns whether or not the given class is a primitive or wrapper
     * @param clazz
     * @return
     */
    public static boolean isPrimitiveOrWrapper(final Class clazz) {
        if(clazz == null)
            return false;
        return isPrimitive(clazz) || isWrapper(clazz);
    }

    /**
     * Converts a primitive to it's wrapper.
     * @param clazz
     * @return
     */
    public static Class primitiveToWrapper(final Class clazz) {
        if(clazz == null) {
            return null;
        }

        Class converted = clazz;
        if(isPrimitive(clazz)) {
            converted = primitiveToWrapper.get(clazz);
        }
        return converted;
    }

    /**
     * Converts a primitive to it's wrapper.
     * @param clazz
     * @return
     */
    public static Class wrapperToPrimitive(final Class clazz) {
        if(clazz == null) {
            return null;
        }

        Class converted = clazz;
        if(isWrapper(clazz)) {
            converted = wrapperToPrimitive.get(clazz);
        }
        return converted;
    }

    /**
     * Returns the current ClassLoader
     * @return
     */
    public static ClassLoader getCurrentClassLoader() {
        ClassLoader loader = null;

        try {
            loader = Thread.currentThread().getContextClassLoader();
        } catch (Exception e) {

        }

        if(loader == null) {
            loader = ClassUtils.class.getClassLoader();
        }

        return loader;
    }

    /**
     * Converts a class to a byte-array or bytecode.
     * @param source
     * @return
     * @throws IOException
     */
    public static byte[] classToBytes(String source) throws IOException {
        return IOUtils.toByteArray(ClassLoader.getSystemResourceAsStream(source.replace(PACKAGE_SEPARATOR, IOUtils.DIR_SEPARATOR) + CLASS_FILE_SUFFIX));
    }

    public static byte[] classToBytes(Class clazz) throws IOException {
        return classToBytes(clazz.getCanonicalName());
    }

    /**
     * Returns the location of the Jar of a given class.
     * @param clazz
     * @return
     */
    public static final File getJarLocation(Class clazz) {
        CodeSource source = clazz.getProtectionDomain().getCodeSource();

        try {
            URI location = source.getLocation().toURI();
            File file = new File(location);

            if(file.exists()) {
                return file;
            }
        } catch (URISyntaxException e) {
        }
        return null;
    }

    /**
     * Returns the magic of a class file. (Default: 0xCAFEBABE)
     * @param clazz
     * @return
     * @throws IOException
     */
    public static int getMagic(final Class clazz) throws IOException {
        if(clazz == null) {
            return 0;
        }

        byte[] bytes = classToBytes(clazz);

        return IOUtils.readUnsignedShort(bytes, 0);
    }

    /**
     * Returns the minor version of a class file.
     * @param clazz
     * @return
     * @throws IOException
     */
    public static int getMinorVersion(Class clazz) throws IOException {
        if(clazz == null) {
            return 0;
        }

        byte[] bytes = classToBytes(clazz);

        return IOUtils.readUnsignedShort(bytes, 4);
    }

    /**
     * Returns the major version of a class file.
     * @param clazz
     * @return
     * @throws IOException
     */
    public static final int getMajorVersion(Class clazz) throws IOException {
        if(clazz == null) {
            return 0;
        }

        byte[] bytes = classToBytes(clazz);

        return IOUtils.readUnsignedShort(bytes, 6);
    }
}
