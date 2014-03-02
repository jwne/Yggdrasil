package com.captainbern.common.reflection.refs;

import com.captainbern.common.reflection.ClassTemplate;
import com.captainbern.common.reflection.MethodAccessor;
import com.captainbern.common.reflection.NMSClassTemplate;

import java.util.Map;

public class EnumProtocolRef {

    public static final ClassTemplate TEMPLATE = NMSClassTemplate.create("EnumProtocol");
    private static final MethodAccessor<Map<Integer, Class<?>>> getInIdToPacketMap = TEMPLATE.getMethod("a");
    private static final MethodAccessor<Map<Integer, Class<?>>> getOutIdToPacketMap = TEMPLATE.getMethod("b");

    public static Map<Integer, Class<?>> getClientPackets(Object protocolType) {
        return getInIdToPacketMap.invoke(protocolType);
    }

    public static Map<Integer, Class<?>> getServerPackets(Object protocolType) {
        return getOutIdToPacketMap.invoke(protocolType);
    }
}
