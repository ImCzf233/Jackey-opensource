package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.stream.Stream;
import org.jetbrains.annotations.Debug;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LongBinaryTag.java */
@Debug.Renderer(text = "String.valueOf(this.value) + \"l\"", hasChildren = "false")
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/LongBinaryTagImpl.class */
public final class LongBinaryTagImpl extends AbstractBinaryTag implements LongBinaryTag {
    private final long value;

    public LongBinaryTagImpl(final long value) {
        this.value = value;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.LongBinaryTag
    public long value() {
        return this.value;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.NumberBinaryTag
    public byte byteValue() {
        return (byte) (this.value & 255);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.NumberBinaryTag
    public double doubleValue() {
        return this.value;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.NumberBinaryTag
    public float floatValue() {
        return (float) this.value;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.NumberBinaryTag
    public int intValue() {
        return (int) this.value;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.NumberBinaryTag
    public long longValue() {
        return this.value;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.NumberBinaryTag
    public short shortValue() {
        return (short) (this.value & 65535);
    }

    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        LongBinaryTagImpl that = (LongBinaryTagImpl) other;
        return this.value == that.value;
    }

    public int hashCode() {
        return Long.hashCode(this.value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.m92of("value", this.value));
    }
}
