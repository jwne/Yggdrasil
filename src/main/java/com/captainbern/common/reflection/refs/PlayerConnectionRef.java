package com.captainbern.common.reflection.refs;

import com.captainbern.common.reflection.ClassTemplate;
import com.captainbern.common.reflection.FieldAccessor;
import com.captainbern.common.reflection.MethodAccessor;
import com.captainbern.common.reflection.NMSClassTemplate;

import java.util.Random;

public class PlayerConnectionRef {

    public static final ClassTemplate TEMPLATE = NMSClassTemplate.create("PlayerConnection");

    public static final FieldAccessor NETWORK_MANAGER = TEMPLATE.getField("networkManager");
    public static final FieldAccessor<Random> RANDOM = TEMPLATE.getField("random");

    public static final MethodAccessor<Void> SEND_PACKET = TEMPLATE.getMethod("sendPacket", NMSClassTemplate.create("Packet").getType());
}
