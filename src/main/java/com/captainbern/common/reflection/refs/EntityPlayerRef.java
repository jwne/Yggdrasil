package com.captainbern.common.reflection.refs;

import com.captainbern.common.reflection.ClassTemplate;
import com.captainbern.common.reflection.FieldAccessor;
import com.captainbern.common.reflection.NMSClassTemplate;

public class EntityPlayerRef {

    public static final ClassTemplate TEMPLATE = NMSClassTemplate.create("EntityPlayer");

    public static final FieldAccessor<String> LOCALE = TEMPLATE.getField("locale");
    public static final FieldAccessor<Object> PLAYER_CONNECTION = TEMPLATE.getField("playerConnection");
}
