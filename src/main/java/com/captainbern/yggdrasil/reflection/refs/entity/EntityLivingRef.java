package com.captainbern.yggdrasil.reflection.refs.entity;

import com.captainbern.yggdrasil.reflection.ClassTemplate;
import com.captainbern.yggdrasil.reflection.FieldAccessor;
import com.captainbern.yggdrasil.reflection.NMSClassTemplate;

import java.util.HashMap;

public class EntityLivingRef extends EntityRef {

    public static final ClassTemplate TEMPLATE = NMSClassTemplate.create("EntityLiving");

    public static final FieldAccessor<Object> ATTRIBUTE_MODIFIER = TEMPLATE.getField("c");
    public static final FieldAccessor<Object> ATTRIBUTE_MAP_BASE = TEMPLATE.getField("d");
    public static final FieldAccessor<Object> COMBAT_TRACKER = TEMPLATE.getField("combatTracker");
    public static final FieldAccessor<HashMap> EFFECTS = TEMPLATE.getField("effects");
    public static final FieldAccessor<Object[]> INVENTORY = TEMPLATE.getField("g");
    /*
    The other fields in here are easier to access/change trough the methods provided by Bukkit.
     */
}
