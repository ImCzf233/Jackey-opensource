package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.NamedTextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/serializer/legacy/LegacyFormat.class */
public final class LegacyFormat implements Examinable {
    static final LegacyFormat RESET = new LegacyFormat(true);
    @Nullable
    private final NamedTextColor color;
    @Nullable
    private final TextDecoration decoration;
    private final boolean reset;

    public LegacyFormat(@Nullable final NamedTextColor color) {
        this.color = color;
        this.decoration = null;
        this.reset = false;
    }

    public LegacyFormat(@Nullable final TextDecoration decoration) {
        this.color = null;
        this.decoration = decoration;
        this.reset = false;
    }

    private LegacyFormat(final boolean reset) {
        this.color = null;
        this.decoration = null;
        this.reset = reset;
    }

    @Nullable
    public TextColor color() {
        return this.color;
    }

    @Nullable
    public TextDecoration decoration() {
        return this.decoration;
    }

    public boolean reset() {
        return this.reset;
    }

    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        LegacyFormat that = (LegacyFormat) other;
        return this.color == that.color && this.decoration == that.decoration && this.reset == that.reset;
    }

    public int hashCode() {
        int result = Objects.hashCode(this.color);
        return (31 * ((31 * result) + Objects.hashCode(this.decoration))) + Boolean.hashCode(this.reset);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m91of("color", this.color), ExaminableProperty.m91of("decoration", this.decoration), ExaminableProperty.m88of("reset", this.reset)});
    }
}
