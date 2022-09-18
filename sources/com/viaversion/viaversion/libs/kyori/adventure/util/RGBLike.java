package com.viaversion.viaversion.libs.kyori.adventure.util;

import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/util/RGBLike.class */
public interface RGBLike {
    int red();

    int green();

    int blue();

    @NotNull
    default HSVLike asHSV() {
        return HSVLike.fromRGB(red(), green(), blue());
    }
}
