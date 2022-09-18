package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.stream.Stream;
import org.jetbrains.annotations.Debug;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: StringBinaryTag.java */
@Debug.Renderer(text = "\"\\\"\" + this.value + \"\\\"\"", hasChildren = "false")
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/StringBinaryTagImpl.class */
public final class StringBinaryTagImpl extends AbstractBinaryTag implements StringBinaryTag {
    private final String value;

    public StringBinaryTagImpl(final String value) {
        this.value = value;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.StringBinaryTag
    @NotNull
    public String value() {
        return this.value;
    }

    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        StringBinaryTagImpl that = (StringBinaryTagImpl) other;
        return this.value.equals(that.value);
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.m90of("value", this.value));
    }
}
