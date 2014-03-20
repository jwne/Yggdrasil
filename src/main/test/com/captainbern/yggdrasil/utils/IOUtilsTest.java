package com.captainbern.yggdrasil.utils;

import net.minecraft.util.org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;

public class IOUtilsTest {

    @Test
    public void letsTestApacheAndBern() {
        try {
            int lengthWithApache = IOUtils.toByteArray(ClassLoader.getSystemResourceAsStream(IOUtilsTest.class.getName().replace('.', '/') + ".class")).length;
            int lengthWithBern = com.captainbern.yggdrasil.utils.IOUtils.toByteArray(ClassLoader.getSystemResourceAsStream(IOUtilsTest.class.getName().replace('.', '/') + ".class")).length;

            System.out.println("Length with Apache: " + lengthWithApache);
            System.out.println("Length with Bern: " + lengthWithBern);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
