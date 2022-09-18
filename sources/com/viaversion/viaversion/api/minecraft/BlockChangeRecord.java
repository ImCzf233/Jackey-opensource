package com.viaversion.viaversion.api.minecraft;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/BlockChangeRecord.class */
public interface BlockChangeRecord {
    byte getSectionX();

    byte getSectionY();

    byte getSectionZ();

    short getY(int i);

    int getBlockId();

    void setBlockId(int i);

    @Deprecated
    default short getY() {
        return getY(-1);
    }
}
