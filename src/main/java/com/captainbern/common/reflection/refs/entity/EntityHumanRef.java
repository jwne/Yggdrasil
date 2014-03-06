package com.captainbern.common.reflection.refs.entity;

import com.captainbern.common.reflection.ClassTemplate;
import com.captainbern.common.reflection.FieldAccessor;
import com.captainbern.common.reflection.NMSClassTemplate;
import net.minecraft.util.com.mojang.authlib.GameProfile;

public class EntityHumanRef extends EntityLivingRef {

    public static final ClassTemplate TEMPLATE = NMSClassTemplate.create("EntityHuman");

    public static final FieldAccessor<Object> INVENTORY = TEMPLATE.getField("inventory");
    public static final FieldAccessor<Object> INVENTORY_ENDERCHEST = TEMPLATE.getField("enderChest");
    public static final FieldAccessor<Object> DEFAULT_CONTAINER = TEMPLATE.getField("defaultContainer");
    public static final FieldAccessor<Object> CURRENT_CONTAINER = TEMPLATE.getField("currentContainer");
    public static final FieldAccessor<Object> FOOD_DATA = TEMPLATE.getField("foodData");
    public static final FieldAccessor<Integer> br_FIELD = TEMPLATE.getField("br");
    public static final FieldAccessor<Float> bs_FIELD = TEMPLATE.getField("bs");
    public static final FieldAccessor<Float> bt_FIELD = TEMPLATE.getField("bt");
    public static final FieldAccessor<Integer> bu_FIELD = TEMPLATE.getField("bu");
    public static final FieldAccessor<Double> bv_FIELD = TEMPLATE.getField("bv");
    public static final FieldAccessor<Double> bw_FIELD = TEMPLATE.getField("bw");
    public static final FieldAccessor<Double> bx_FIELD = TEMPLATE.getField("bx");
    public static final FieldAccessor<Double> by_FIELD = TEMPLATE.getField("by");
    public static final FieldAccessor<Double> bz_FIELD = TEMPLATE.getField("bz");
    public static final FieldAccessor<Double> bA_FIELD = TEMPLATE.getField("bA");
    public static final FieldAccessor<Boolean> SLEEPING = TEMPLATE.getField("sleeping");
    public static final FieldAccessor<Object> CHUNK_COORDINATES_bC_FIELD = TEMPLATE.getField("bC");
    public static final FieldAccessor<Integer> SLEEP_TICKS = TEMPLATE.getField("sleepTicks");
    public static final FieldAccessor<Float> bD_FIELD = TEMPLATE.getField("bD");
    public static final FieldAccessor<Float> bE_FIELD = TEMPLATE.getField("bE");
    public static final FieldAccessor<Object> CHUNK_COORDINATES_c_FIELD = TEMPLATE.getField("c");
    public static final FieldAccessor<Boolean> d_FIELD = TEMPLATE.getField("d");
    public static final FieldAccessor<Object> CHUNK_COORDINATES_e_FIELD = TEMPLATE.getField("e");
    public static final FieldAccessor<Object> PLAYER_ABILITIES = TEMPLATE.getField("abilities");
    public static final FieldAccessor<Integer> EXP_LEVEl = TEMPLATE.getField("expLevel");
    public static final FieldAccessor<Integer> EXP_TOTAL = TEMPLATE.getField("expTotal");
    public static final FieldAccessor<Float> EXPERIENCE = TEMPLATE.getField("exp");
    public static final FieldAccessor<Object> f_FIELD_ITEMSTACK = TEMPLATE.getField("f");
    public static final FieldAccessor<Integer> g_FIELD = TEMPLATE.getField("g");
    public static final FieldAccessor<Float> bJ_FIELD = TEMPLATE.getField("bJ");
    public static final FieldAccessor<Float> bK_FIELD = TEMPLATE.getField("bK");
    public static final FieldAccessor<Integer> h_FIELD = TEMPLATE.getField("h");
    public static final FieldAccessor<GameProfile> GAME_PROFILE = TEMPLATE.getField("i");
    public static final FieldAccessor<Object> HOOKED_FISH = TEMPLATE.getField("hookedFish");
}
