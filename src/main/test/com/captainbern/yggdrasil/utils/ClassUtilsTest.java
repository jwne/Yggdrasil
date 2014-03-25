package com.captainbern.yggdrasil.utils;

import org.junit.Test;

import java.io.IOException;

public class ClassUtilsTest {

    @Test
    public void onTestClass() {
        try {

            byte[] bytes = IOUtils.toByteArray(ClassLoader.getSystemResourceAsStream(IOUtils.class.getName().replace('.', '/') + ".class"));

            System.out.println(ClassUtils.getMagic(bytes) == 0xCAFEBABE ? "YAY" : "AWW");
            System.out.println(ClassUtils.getMinorVersion(bytes));
            System.out.println(ClassUtils.getMajorVersion(bytes));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
