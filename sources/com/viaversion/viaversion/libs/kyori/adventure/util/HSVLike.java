package com.viaversion.viaversion.libs.kyori.adventure.util;

import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/util/HSVLike.class */
public interface HSVLike extends Examinable {
    /* renamed from: h */
    float mo100h();

    /* renamed from: s */
    float mo99s();

    /* renamed from: v */
    float mo98v();

    @NotNull
    /* renamed from: of */
    static HSVLike m101of(final float h, final float s, final float v) {
        return new HSVLikeImpl(h, s, v);
    }

    @NotNull
    static HSVLike fromRGB(final int red, final int green, final int blue) {
        float s;
        float h;
        float r = red / 255.0f;
        float g = green / 255.0f;
        float b = blue / 255.0f;
        float min = Math.min(r, Math.min(g, b));
        float max = Math.max(r, Math.max(g, b));
        float delta = max - min;
        if (max != 0.0f) {
            s = delta / max;
        } else {
            s = 0.0f;
        }
        if (s == 0.0f) {
            return new HSVLikeImpl(0.0f, s, max);
        }
        if (r == max) {
            h = (g - b) / delta;
        } else if (g == max) {
            h = 2.0f + ((b - r) / delta);
        } else {
            h = 4.0f + ((r - g) / delta);
        }
        float h2 = h * 60.0f;
        if (h2 < 0.0f) {
            h2 += 360.0f;
        }
        return new HSVLikeImpl(h2 / 360.0f, s, max);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m94of("h", mo100h()), ExaminableProperty.m94of("s", mo99s()), ExaminableProperty.m94of("v", mo98v())});
    }
}
