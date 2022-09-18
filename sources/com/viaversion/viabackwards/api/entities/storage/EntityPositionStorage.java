package com.viaversion.viabackwards.api.entities.storage;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/api/entities/storage/EntityPositionStorage.class */
public abstract class EntityPositionStorage {

    /* renamed from: x */
    private double f10x;

    /* renamed from: y */
    private double f11y;

    /* renamed from: z */
    private double f12z;

    public double getX() {
        return this.f10x;
    }

    public double getY() {
        return this.f11y;
    }

    public double getZ() {
        return this.f12z;
    }

    public void setCoordinates(double x, double y, double z, boolean relative) {
        if (relative) {
            this.f10x += x;
            this.f11y += y;
            this.f12z += z;
            return;
        }
        this.f10x = x;
        this.f11y = y;
        this.f12z = z;
    }
}
