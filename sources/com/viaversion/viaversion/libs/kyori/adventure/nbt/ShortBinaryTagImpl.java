package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.stream.Stream;
import org.jetbrains.annotations.Debug;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ShortBinaryTag.java */
@Debug.Renderer(text = "String.valueOf(this.value) + \"s\"", hasChildren = "false")
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/ShortBinaryTagImpl.class */
public final class ShortBinaryTagImpl extends AbstractBinaryTag implements ShortBinaryTag {
    private final short value;

    public ShortBinaryTagImpl(final short value) {
        this.value = value;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.ShortBinaryTag
    public short value() {
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
        return this.value;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.NumberBinaryTag
    public int intValue() {
        return this.value;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.NumberBinaryTag
    public long longValue() {
        return this.value;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.NumberBinaryTag
    public short shortValue() {
        return this.value;
    }

    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        ShortBinaryTagImpl that = (ShortBinaryTagImpl) other;
        return this.value == that.value;
    }

    public int hashCode() {
        return Short.hashCode(this.value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.m89of("value", this.value));
    }
}
