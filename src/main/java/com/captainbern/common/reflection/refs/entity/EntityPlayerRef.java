package com.captainbern.common.reflection.refs.entity;

import com.captainbern.common.reflection.ClassTemplate;
import com.captainbern.common.reflection.FieldAccessor;
import com.captainbern.common.reflection.NMSClassTemplate;

import java.util.List;

public class EntityPlayerRef extends EntityHumanRef {

    public static final ClassTemplate TEMPLATE = NMSClassTemplate.create("EntityPlayer");

    public static final FieldAccessor<String> LOCALE = TEMPLATE.getField("locale");
    public static final FieldAccessor<Object> PLAYER_CONNECTION = TEMPLATE.getField("playerConnection");
    public static final FieldAccessor<Object> MINECRAFT_SERVER = TEMPLATE.getField("server");
    public static final FieldAccessor<Object> INTERACT_MANAGER = TEMPLATE.getField("playerInteractManager");
    public static final FieldAccessor<Double> d_FIELD = TEMPLATE.getField("d");
    public static final FieldAccessor<Double> e_FIELD = TEMPLATE.getField("e");
    public static final FieldAccessor<List> CHUNK_COORD_INT_PAIR_QUEUE = TEMPLATE.getField("chunkCoordIntPairQueue");
    public static final FieldAccessor<List> REMOVE_QUEUE = TEMPLATE.getField("removeQueue");
    public static final FieldAccessor<Object> SEVER_STATISTICS_MANAGER = TEMPLATE.getField("bO");
    public static final FieldAccessor<Float> bP_FIELD = TEMPLATE.getField("bP");
    public static final FieldAccessor<Float> bQ_FIELD = TEMPLATE.getField("bQ");
    public static final FieldAccessor<Integer> bR_FIELD = TEMPLATE.getField("bR");
    public static final FieldAccessor<Boolean> bS_FIELD = TEMPLATE.getField("bS");
    public static final FieldAccessor<Integer> LAST_SENT_EXPERIENCE = TEMPLATE.getField("lastSentExp");
    public static final FieldAccessor<Integer> INVULNERABLE_TICKS = TEMPLATE.getField("invulnerableTicks");
    public static final FieldAccessor<Integer> bV_FIELD = TEMPLATE.getField("bV");
    public static final FieldAccessor<Object> ENUM_CHAT_VISIBILITY = TEMPLATE.getField("bW");
    public static final FieldAccessor<Boolean> bX_FIELD = TEMPLATE.getField("bX");
    public static final FieldAccessor<Long> bY_FIELD = TEMPLATE.getField("bY");
    public static final FieldAccessor<Integer> CONTAINER_COUNTER = TEMPLATE.getField("containerCounter");
    public static final FieldAccessor<Boolean> h_FIELD = TEMPLATE.getField("h");
    public static final FieldAccessor<Integer> PING = TEMPLATE.getField("ping");
    public static final FieldAccessor<Boolean> VIEWING_CREDITS = TEMPLATE.getField("viewingCredits");
}
