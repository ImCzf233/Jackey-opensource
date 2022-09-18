package com.viaversion.viaversion.libs.kyori.adventure.util;

import com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/util/HSVLikeImpl.class */
public final class HSVLikeImpl implements HSVLike {

    /* renamed from: h */
    private final float f187h;

    /* renamed from: s */
    private final float f188s;

    /* renamed from: v */
    private final float f189v;

    public HSVLikeImpl(final float h, final float s, final float v) {
        this.f187h = h;
        this.f188s = s;
        this.f189v = v;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.util.HSVLike
    /* renamed from: h */
    public float mo100h() {
        return this.f187h;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.util.HSVLike
    /* renamed from: s */
    public float mo99s() {
        return this.f188s;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.util.HSVLike
    /* renamed from: v */
    public float mo98v() {
        return this.f189v;
    }

    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HSVLikeImpl)) {
            return false;
        }
        HSVLikeImpl that = (HSVLikeImpl) other;
        return ShadyPines.equals(that.f187h, this.f187h) && ShadyPines.equals(that.f188s, this.f188s) && ShadyPines.equals(that.f189v, this.f189v);
    }

    public int hashCode() {
        return Objects.hash(Float.valueOf(this.f187h), Float.valueOf(this.f188s), Float.valueOf(this.f189v));
    }

    public String toString() {
        return (String) examine(StringExaminer.simpleEscaping());
    }
}
