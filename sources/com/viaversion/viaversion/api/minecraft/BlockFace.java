package com.viaversion.viaversion.api.minecraft;

import java.util.HashMap;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/BlockFace.class */
public enum BlockFace {
    NORTH((byte) 0, (byte) 0, (byte) -1, EnumAxis.Z),
    SOUTH((byte) 0, (byte) 0, (byte) 1, EnumAxis.Z),
    EAST((byte) 1, (byte) 0, (byte) 0, EnumAxis.X),
    WEST((byte) -1, (byte) 0, (byte) 0, EnumAxis.X),
    TOP((byte) 0, (byte) 1, (byte) 0, EnumAxis.Y),
    BOTTOM((byte) 0, (byte) -1, (byte) 0, EnumAxis.Y);
    
    private final byte modX;
    private final byte modY;
    private final byte modZ;
    private final EnumAxis axis;
    public static final BlockFace[] HORIZONTAL = {NORTH, SOUTH, EAST, WEST};
    private static final Map<BlockFace, BlockFace> opposites = new HashMap();

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/BlockFace$EnumAxis.class */
    public enum EnumAxis {
        X,
        Y,
        Z
    }

    static {
        opposites.put(NORTH, SOUTH);
        opposites.put(SOUTH, NORTH);
        opposites.put(EAST, WEST);
        opposites.put(WEST, EAST);
        opposites.put(TOP, BOTTOM);
        opposites.put(BOTTOM, TOP);
    }

    BlockFace(byte modX, byte modY, byte modZ, EnumAxis axis) {
        this.modX = modX;
        this.modY = modY;
        this.modZ = modZ;
        this.axis = axis;
    }

    public BlockFace opposite() {
        return opposites.get(this);
    }

    public byte modX() {
        return this.modX;
    }

    public byte modY() {
        return this.modY;
    }

    public byte modZ() {
        return this.modZ;
    }

    public EnumAxis axis() {
        return this.axis;
    }

    @Deprecated
    public byte getModX() {
        return this.modX;
    }

    @Deprecated
    public byte getModY() {
        return this.modY;
    }

    @Deprecated
    public byte getModZ() {
        return this.modZ;
    }

    @Deprecated
    public EnumAxis getAxis() {
        return this.axis;
    }
}
