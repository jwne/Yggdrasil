package com.captainbern.common.protocol.event;

import com.captainbern.common.protocol.PacketType;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PacketTypeSet implements Iterable<PacketType> {

    private final Set<PacketType> delegate = new HashSet<PacketType>();

    public int size() {
        return delegate().size();
    }

    public boolean isEmpty() {
        return delegate().isEmpty();
    }

    public boolean contains(PacketType packetType) {
        return delegate().contains(packetType);
    }

    public Iterator<PacketType> iterator() {
        return delegate().iterator();
    }

    public PacketType[] toArray() {
        return (PacketType[]) delegate().toArray();
    }

    public <T> T[] toArray(T[] a) {
        return delegate().toArray(a);
    }

    public boolean add(PacketType packetType) {
        return delegate().add(packetType);
    }

    public boolean remove(PacketType packetType) {
        return delegate().remove(packetType);
    }

    public boolean containsAll(Collection<PacketType> collection) {
        return delegate().containsAll(collection);
    }

    public boolean addAll(Collection<? extends PacketType> c) {
        return delegate().addAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return delegate().retainAll(c);
    }

    public boolean removeAll(Collection<?> c) {
        return delegate().retainAll(c);
    }

    public void clear() {
        delegate().clear();
    }

    protected Set<PacketType> delegate() {
        return this.delegate;
    }
}
