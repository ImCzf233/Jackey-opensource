package com.viaversion.viaversion.api.minecraft;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/Environment.class */
public enum Environment {
    NORMAL(0),
    NETHER(-1),
    END(1);
    

    /* renamed from: id */
    private final int f32id;

    Environment(int id) {
        this.f32id = id;
    }

    /* renamed from: id */
    public int m232id() {
        return this.f32id;
    }

    @Deprecated
    public int getId() {
        return this.f32id;
    }

    public static Environment getEnvironmentById(int id) {
        switch (id) {
            case -1:
            default:
                return NETHER;
            case 0:
                return NORMAL;
            case 1:
                return END;
        }
    }
}
