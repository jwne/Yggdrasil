package com.captainbern.yggdrasil.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
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

    public ClassUtils() {
        super();
    }

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

    private static final HashMap<Class, Character> typeToDescriptor = new HashMap<Class, Character>();
    static {
        typeToDescriptor.put(Integer.TYPE, 'I');
        typeToDescriptor.put(Void.TYPE, 'V');
        typeToDescriptor.put(Boolean.TYPE, 'Z');
        typeToDescriptor.put(Byte.TYPE, 'B');
        typeToDescriptor.put(Character.TYPE, 'C');
        typeToDescriptor.put(Short.TYPE, 'S');
        typeToDescriptor.put(Double.TYPE, 'D');
        typeToDescriptor.put(Float.TYPE, 'F');
        typeToDescriptor.put(Long.TYPE, 'J');
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

    public static String getClassName(Class<?> clazz) {
        return clazz.getName().replace(PACKAGE_SEPARATOR_CHAR, IOUtils.DIR_SEPARATOR_CHAR) + CLASS_FILE_SUFFIX;
    }

    /**
     * Converts a class to a byte-array or bytecode.
     * @param source
     * @return
     * @throws IOException
     */
    public static byte[] classToBytes(String source) throws IOException {
        return IOUtils.toByteArray(ClassLoader.getSystemResourceAsStream(source.replace(PACKAGE_SEPARATOR_CHAR, IOUtils.DIR_SEPARATOR_CHAR) + CLASS_FILE_SUFFIX));
    }

    /**
     * Returns the location of the Jar of a given class.
     * @param clazz
     * @return
     */
    public static File getJarLocation(Class clazz) {
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
     * @param bytes
     * @return
     * @throws IOException
     */
    public static int getMagic(final byte[] bytes) throws IOException {
        if(bytes == null) {
            return 0;
        }

        return IOUtils.readInt(bytes, 0);
    }

    /**
     * Returns the minor version of a class file.
     * @param bytes
     * @return
     * @throws IOException
     */
    public static int getMinorVersion(final byte[] bytes) throws IOException {
        if(bytes == null) {
            return 0;
        }

        return IOUtils.readShort(bytes, 4);
    }

    /**
     * Returns the major version of a class file.
     * @param bytes
     * @return
     * @throws IOException
     */
    public static int getMajorVersion(final byte[] bytes) throws IOException {
        if(bytes == null) {
            return 0;
        }

        return IOUtils.readShort(bytes, 6);
    }

    /**
     * Returns the descriptor of a given class
     * @param clazz
     * @return
     */
    public static String getClassDescriptor(final Class<?> clazz) {
        StringBuffer buffer = new StringBuffer();
        getDescriptor(buffer, clazz);
        return buffer.toString();
    }

    /**
     * Returns a constructor descriptor
     * @param constructor
     * @return
     */
    public static String getConstructorDescriptor(final Constructor constructor) {
        StringBuffer buffer = new StringBuffer();
        buffer.append('(');

        Class[] parameters = constructor.getParameterTypes();
        for(Class<?> param : parameters) {
            getDescriptor(buffer, param);
        }

        return buffer.append(")V").toString();
    }

    /**
     * Returns the method-descriptor of a method
     * @param method
     * @return
     */
    public static String getMethodDescriptor(final Method method) {
        Class<?>[] parameters = method.getParameterTypes();
        StringBuffer buf = new StringBuffer();
        buf.append('(');
        for (int i = 0; i < parameters.length; ++i) {
            getDescriptor(buf, parameters[i]);
        }
        buf.append(')');
        getDescriptor(buf, method.getReturnType());
        return buf.toString();
    }

    /**
     * Returns the descriptor of a given class.
     * @param buffer
     * @param clazz
     */
    public static void getDescriptor(final StringBuffer buffer, final Class<?> clazz) {
        Class<?> d = clazz;
        while (true) {
            if (d.isPrimitive()) {
                char car = typeToDescriptor.get(clazz);
                buffer.append(car);
                return;
            } else if (d.isArray()) {
                buffer.append('[');
                d = d.getComponentType();
            } else {
                buffer.append('L');
                String name = d.getName();
                int len = name.length();
                for (int i = 0; i < len; ++i) {
                    char car = name.charAt(i);
                    buffer.append(car == '.' ? '/' : car);
                }
                buffer.append(';');
                return;
            }
        }
    }
}
