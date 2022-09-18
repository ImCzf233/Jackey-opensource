package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.stream.Stream;
import kotlin.jvm.internal.CharCompanionObject;
import org.jetbrains.annotations.Debug;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DoubleBinaryTag.java */
@Debug.Renderer(text = "String.valueOf(this.value) + \"d\"", hasChildren = "false")
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/DoubleBinaryTagImpl.class */
public final class DoubleBinaryTagImpl extends AbstractBinaryTag implements DoubleBinaryTag {
    private final double value;

    public DoubleBinaryTagImpl(final double value) {
        this.value = value;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.DoubleBinaryTag
    public double value() {
        return this.value;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.NumberBinaryTag
    public byte byteValue() {
        return (byte) (ShadyPines.floor(this.value) & 255);
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
        return ShadyPines.floor(this.value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.NumberBinaryTag
    public long longValue() {
        return (long) Math.floor(this.value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.NumberBinaryTag
    public short shortValue() {
        return (short) (ShadyPines.floor(this.value) & CharCompanionObject.MAX_VALUE);
    }

    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        DoubleBinaryTagImpl that = (DoubleBinaryTagImpl) other;
        return Double.doubleToLongBits(this.value) == Double.doubleToLongBits(that.value);
    }

    public int hashCode() {
        return Double.hashCode(this.value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.m95of("value", this.value));
    }
}
