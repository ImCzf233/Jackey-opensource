package com.viaversion.viaversion.api.minecraft;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/EulerAngle.class */
public class EulerAngle {

    /* renamed from: x */
    private final float f33x;

    /* renamed from: y */
    private final float f34y;

    /* renamed from: z */
    private final float f35z;

    public EulerAngle(float x, float y, float z) {
        this.f33x = x;
        this.f34y = y;
        this.f35z = z;
    }

    /* renamed from: x */
    public float m231x() {
        return this.f33x;
    }

    /* renamed from: y */
    public float m230y() {
        return this.f34y;
    }

    /* renamed from: z */
    public float m229z() {
        return this.f35z;
    }

    @Deprecated
    public float getX() {
        return this.f33x;
    }

    @Deprecated
    public float getY() {
        return this.f34y;
    }

    @Deprecated
    public float getZ() {
        return this.f35z;
    }
}
