package com.captainbern.yggdrasil.reflection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FieldVisitorTest {

    private Object someObject = null;
    private int lol = 1;
    private Integer int2 = 0x017;
    private String testString = "phrase";
    private List<Object> list = new ArrayList<Object>();
    public String anotherString = "lol";

    @Test
    public void onTest() {
        FieldVisitor visitor = new FieldVisitor<String>(this);

        FieldVisitor<String> strings = visitor.withType(String.class);
        for(FieldAccessor accessor : strings.getFields()) {
            System.out.println(accessor.get(this));
        }

        FieldVisitor<Integer> integers = visitor.withType(int.class);
        for(FieldAccessor accessor : integers.getFields()) {
            System.out.println(accessor.get(this));
        }
    }
}
