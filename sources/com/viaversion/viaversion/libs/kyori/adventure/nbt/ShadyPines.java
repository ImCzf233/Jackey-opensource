package com.viaversion.viaversion.libs.kyori.adventure.nbt;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/ShadyPines.class */
final class ShadyPines {
    private ShadyPines() {
    }

    public static int floor(final double dv) {
        int iv = (int) dv;
        return dv < ((double) iv) ? iv - 1 : iv;
    }

    public static int floor(final float fv) {
        int iv = (int) fv;
        return fv < ((float) iv) ? iv - 1 : iv;
    }
}
