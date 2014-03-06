package com.captainbern.common.reflection.refs.entity;

import com.captainbern.common.reflection.ClassTemplate;
import com.captainbern.common.reflection.FieldAccessor;
import com.captainbern.common.reflection.NMSClassTemplate;

import java.util.Random;
import java.util.UUID;

public class EntityRef {

    public static final ClassTemplate TEMPLATE = NMSClassTemplate.create("Entity");

    // static fields
    public static final FieldAccessor<Integer> ENTITY_COUNT = TEMPLATE.getField("entityCount");

    // non-static fields
    public static final FieldAccessor<Integer> ID = TEMPLATE.getField("id");
    public static final FieldAccessor<Double> k_FIELD = TEMPLATE.getField("k");   // Need to find out what this is
    public static final FieldAccessor<Double> l_FIELD = TEMPLATE.getField("l");   // ^ idem
    public static final FieldAccessor<Object> PASSENGER = TEMPLATE.getField("passenger");
    public static final FieldAccessor<Object> VEHICLE = TEMPLATE.getField("vehicle");
    public static final FieldAccessor<Boolean> O_FIELD = TEMPLATE.getField("o");  // Need to find out what this is
    public static final FieldAccessor<Object> WORLD = TEMPLATE.getField("world");
    public static final FieldAccessor<Double> LAST_X = TEMPLATE.getField("lastX");
    public static final FieldAccessor<Double> LAST_Y = TEMPLATE.getField("lastY");
    public static final FieldAccessor<Double> LAST_Z = TEMPLATE.getField("lastZ");
    public static final FieldAccessor<Double> LOC_X = TEMPLATE.getField("locX");
    public static final FieldAccessor<Double> LOC_Y = TEMPLATE.getField("locY");
    public static final FieldAccessor<Double> LOC_Z = TEMPLATE.getField("locZ");
    public static final FieldAccessor<Double> MOT_X = TEMPLATE.getField("motX");
    public static final FieldAccessor<Double> MOT_Y = TEMPLATE.getField("motY");
    public static final FieldAccessor<Double> MOT_Z = TEMPLATE.getField("motZ");
    public static final FieldAccessor<Float> YAW = TEMPLATE.getField("yaw");
    public static final FieldAccessor<Float> PITCH = TEMPLATE.getField("pitch");
    public static final FieldAccessor<Float> LAST_YAW = TEMPLATE.getField("lastYaw");
    public static final FieldAccessor<Float> LAST_PITCH = TEMPLATE.getField("lastPitch");
    public static final FieldAccessor<Object> BOUNDING_BOX = TEMPLATE.getField("AxisAlignedBB");
    public static final FieldAccessor<Boolean> ON_GROUND = TEMPLATE.getField("onGround");
    public static final FieldAccessor<Boolean> POSITION_CHANGED = TEMPLATE.getField("positionChanged");
    public static final FieldAccessor<Boolean> G_FIELD = TEMPLATE.getField("G");
    public static final FieldAccessor<Boolean> H_FIELD = TEMPLATE.getField("H");
    public static final FieldAccessor<Boolean> VELOCITY_CHANGED = TEMPLATE.getField("velocityChanged");
    public static final FieldAccessor<Boolean> J_FIELD = TEMPLATE.getField("J");
    public static final FieldAccessor<Boolean> K_FIELD = TEMPLATE.getField("K");
    public static final FieldAccessor<Boolean> DEAD = TEMPLATE.getField("dead");
    public static final FieldAccessor<Float> HEIGHT = TEMPLATE.getField("height");
    public static final FieldAccessor<Float> WIDTH = TEMPLATE.getField("width");
    public static final FieldAccessor<Float> LENGTH = TEMPLATE.getField("length");
    public static final FieldAccessor<Float> P_FIELD = TEMPLATE.getField("P");
    public static final FieldAccessor<Float> Q_FIELD = TEMPLATE.getField("Q");
    public static final FieldAccessor<Float> R_FIELD = TEMPLATE.getField("R");
    public static final FieldAccessor<Float> FALL_DISTANCE = TEMPLATE.getField("fallDistance");
    public static final FieldAccessor<Integer> d_FIELD = TEMPLATE.getField("d");
    public static final FieldAccessor<Double> T_FIELD = TEMPLATE.getField("T");
    public static final FieldAccessor<Double> U_FIELD = TEMPLATE.getField("U");
    public static final FieldAccessor<Double> V_FIELD = TEMPLATE.getField("V");
    public static final FieldAccessor<Float> W_FIELD = TEMPLATE.getField("W");
    public static final FieldAccessor<Float> X_FIELD = TEMPLATE.getField("X");
    public static final FieldAccessor<Boolean> Y_FIELD = TEMPLATE.getField("Y");
    public static final FieldAccessor<Float> Z_FIELD = TEMPLATE.getField("Z");
    public static final FieldAccessor<Random> RANDOM = TEMPLATE.getField("random");
    public static final FieldAccessor<Integer> TICKS_LIVED = TEMPLATE.getField("ticksLived");
    public static final FieldAccessor<Integer> MAX_FIRE_TICKS = TEMPLATE.getField("maxFireTicks");
    public static final FieldAccessor<Integer> FIRE_TICKS = TEMPLATE.getField("fireTicks");
    public static final FieldAccessor<Boolean> IN_WATER = TEMPLATE.getField("inWater");
    public static final FieldAccessor<Integer> NO_DAMAGE_TICKS = TEMPLATE.getField("noDamageTicks");
    public static final FieldAccessor<Boolean> JUST_CREATED = TEMPLATE.getField("justCreated");
    public static final FieldAccessor<Boolean> FIRE_PROOF = TEMPLATE.getField("fireProof");
    public static final FieldAccessor<Object> DATAWATCHER = TEMPLATE.getField("datawatcher");
    public static final FieldAccessor<Double> g_FIELD = TEMPLATE.getField("g");
    public static final FieldAccessor<Double> h_FIELD = TEMPLATE.getField("h");
    public static final FieldAccessor<Boolean> ah_FIELD = TEMPLATE.getField("ah");
    public static final FieldAccessor<Integer> ai_FIELD = TEMPLATE.getField("ai");
    public static final FieldAccessor<Integer> aj_FIELD = TEMPLATE.getField("aj");
    public static final FieldAccessor<Integer> ak_FIELD = TEMPLATE.getField("ak");
    public static final FieldAccessor<Boolean> al_FIELD = TEMPLATE.getField("al");
    public static final FieldAccessor<Boolean> am_FIELD = TEMPLATE.getField("am");
    public static final FieldAccessor<Integer> PORTAL_COOLDOWN = TEMPLATE.getField("portalCooldown");
    public static final FieldAccessor<Boolean> ao_FIELD = TEMPLATE.getField("ao");
    public static final FieldAccessor<Integer> ap_FIELD = TEMPLATE.getField("ap");
    public static final FieldAccessor<Integer> DIMENSION = TEMPLATE.getField("dimension");
    public static final FieldAccessor<Integer> ar_FIELD = TEMPLATE.getField("ar");
    public static final FieldAccessor<Boolean> INVULNERABLE = TEMPLATE.getField("invulnerable");
    public static final FieldAccessor<UUID> UNIQUE_ID = TEMPLATE.getField("uniqueID");
    public static final FieldAccessor<Object> ENUM_ENTITY_SIZE = TEMPLATE.getField("at");

}