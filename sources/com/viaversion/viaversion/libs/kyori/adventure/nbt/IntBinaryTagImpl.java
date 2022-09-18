package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.stream.Stream;
import kotlin.jvm.internal.CharCompanionObject;
import org.jetbrains.annotations.Debug;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: IntBinaryTag.java */
@Debug.Renderer(text = "String.valueOf(this.value) + \"i\"", hasChildren = "false")
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/IntBinaryTagImpl.class */
public final class IntBinaryTagImpl extends AbstractBinaryTag implements IntBinaryTag {
    private final int value;

    public IntBinaryTagImpl(final int value) {
        this.value = value;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.IntBinaryTag
    public int value() {
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
        return (short) (this.value & CharCompanionObject.MAX_VALUE);
    }

    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        IntBinaryTagImpl that = (IntBinaryTagImpl) other;
        return this.value == that.value;
    }

    public int hashCode() {
        return Integer.hashCode(this.value);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.m93of("value", this.value));
    }
}
