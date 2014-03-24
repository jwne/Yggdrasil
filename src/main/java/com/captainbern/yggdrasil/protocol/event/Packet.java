package com.captainbern.yggdrasil.protocol.event;

import com.captainbern.yggdrasil.protocol.PacketType;
import com.captainbern.yggdrasil.reflection.ClassTemplate;
import com.captainbern.yggdrasil.reflection.FieldAccessor;
import com.captainbern.yggdrasil.reflection.FieldVisitor;
import com.captainbern.yggdrasil.reflection.NMSClassTemplate;
import net.minecraft.util.io.netty.buffer.ByteBuf;

import java.io.Serializable;

public class Packet implements Serializable {

    protected PacketType type;

    protected transient Object handle;

    protected transient FieldVisitor visitor;

    protected transient ClassTemplate<?> template;

    protected Packet() {}

    public Packet(PacketType type) {
        this(type, NMSClassTemplate.create(type.getPacketClass()).newInstance());
    }

    public Packet(PacketType type, Object handle) {
        this(type, handle, NMSClassTemplate.create(type.getPacketClass()));
    }

    public Packet(PacketType type, Object handle, ClassTemplate template) {
        this(type, handle, template, new FieldVisitor(handle, template));
    }

    public Packet(PacketType type, Object handle, ClassTemplate template, FieldVisitor visitor) {
        if(type == null) {
            throw new IllegalArgumentException("PacketType can't be NULL!");
        }
        if(handle == null) {
            throw new IllegalArgumentException("Handle can't be NULL!");
        }
        if(template == null) {
            throw new IllegalArgumentException("ClassTemplate can't be NULL!");
        }
        if(visitor == null) {
            throw new IllegalArgumentException("FieldVisitor can't be NULL!");
        }

        this.type = type;
        this.handle = handle;
        this.template = template;
        this.visitor = visitor;
    }

    public PacketType getType() {
        if(this.type == null) {
            throw new RuntimeException("PacketType is NULL!");
        }
        return this.type;
    }

    public Object getHandle() {
        if(this.handle == null) {
            throw new RuntimeException("Packet Handle is NULL!");
        }
        return this.handle;
    }

    public FieldVisitor<Object> getVisitor() {
        if(this.visitor == null) {
            throw new RuntimeException("FieldVisitor is NULL!");
        }
        return this.visitor;
    }

    public ClassTemplate getClassTemplate() {
        if(this.template == null) {
            throw new RuntimeException("ClassTemplate is NULL!");
        }
        return this.template;
    }

    public <T> T read(int index) {
        if(this.handle == null) {
            throw new RuntimeException("Handle is NULL (aka I don't know what to read of!)!");
        }

        return (T) this.template.getField(index);
    }

    public <T> T read(String fieldName) {
        return (T) this.template.getField(fieldName);
    }

    public void write(int index, Object value) {
        if(index < 0 || index >= this.template.getFields().size()) {
            throw new IndexOutOfBoundsException("Size: " + this.template.getFields().size() + ". Requested: " + index);
        }

        if(this.handle == null) {
            throw new RuntimeException("Handle is NULL (aka I don't know what to write to!)!");
        }

        FieldAccessor accessor = this.template.getField(index);

        accessor.set(this.handle, value);
    }

    public void write(String fieldName, Object value) {
        if(this.handle == null) {
            throw new RuntimeException("Handle is NULL (aka I don't know what to write to!)!");
        }

        FieldAccessor accessor = this.template.getField(fieldName);

        accessor.set(this.handle, value);
    }

    public <T> FieldVisitor<T> getTypes(Class<T> type) {
        return this.visitor.withType(type);
    }

    public FieldVisitor<String> getStrings() {
        return this.visitor.withType(String.class);
    }

    public FieldVisitor<String[]> getStringArrays() {
        return this.visitor.withType(String[].class);
    }

    public FieldVisitor<Byte> getBytes() {
        return this.visitor.withType(byte.class);
    }

    public FieldVisitor<byte[]> getByteArrays() {
        return this.visitor.withType(byte[].class);
    }

    public FieldVisitor<Boolean> getBooleans() {
        return this.visitor.withType(boolean.class);
    }

    public FieldVisitor<Short> getShorts() {
        return this.visitor.withType(short.class);
    }

    public FieldVisitor<Character> getChars() {
        return this.visitor.withType(char.class);
    }

    public FieldVisitor<Integer> getIntegers() {
        return this.visitor.withType(int.class);
    }

    public FieldVisitor<int[]> getIntegerArrays() {
        return this.visitor.withType(int[].class);
    }

    public FieldVisitor<Float> getFloats() {
        return this.visitor.withType(float.class);
    }

    public FieldVisitor<Long> getLongs() {
        return this.visitor.withType(long.class);
    }

    public FieldVisitor<Double> getDoubles() {
        return this.visitor.withType(double.class);
    }

    public ByteBuf toByteBuf() {
        return null;
    }

    public static void read() {

    }

    public static void write() {

    }
}
