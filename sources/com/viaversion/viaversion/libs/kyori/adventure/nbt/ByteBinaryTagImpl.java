package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.stream.Stream;
import org.jetbrains.annotations.Debug;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ByteBinaryTag.java */
@Debug.Renderer(text = "\"0x\" + Integer.toString(this.value, 16)", hasChildren = "false")
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/ByteBinaryTagImpl.class */
public final class ByteBinaryTagImpl extends AbstractBinaryTag implements ByteBinaryTag {
    private final byte value;

    public ByteBinaryTagImpl(final byte value) {
        this.value = value;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.ByteBinaryTag
    public byte value() {
        return this.value;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.NumberBinaryTag
    public byte byteValue() {
        return this.value;
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
        ByteBinaryTagImpl that = (ByteBinaryTagImpl) other;
        return this.value == that.value;
    }

    public int hashCode() {
        return Byte.hashCode(this.value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.m97of("value", this.value));
    }
}
