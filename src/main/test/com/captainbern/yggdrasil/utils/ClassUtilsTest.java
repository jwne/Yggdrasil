package com.captainbern.yggdrasil.utils;

import com.captainbern.yggdrasil.reflection.bytecode.ClassFile;
import com.captainbern.yggdrasil.reflection.bytecode.exception.ClassFormatException;
import org.junit.Test;

import java.io.IOException;

public class ClassUtilsTest {

    @Test
    public void onTestClass() {
        try {

            byte[] bytes = ClassUtils.classToBytes(IOUtils.class.getName());

            ClassFile classFile = new ClassFile(bytes);

            print("Magic: " + Integer.toHexString(classFile.getMagic()));
            print("Minor: " + classFile.getMinor());
            print("Major: " + classFile.getMagic());
            print("ClassName: " + classFile.getClassName());
            print("SuperClass: " + classFile.getSuperClassName());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassFormatException e) {
            e.printStackTrace();
        }
    }

    protected void print(Object message) {
        System.out.println(message);
    }
}
