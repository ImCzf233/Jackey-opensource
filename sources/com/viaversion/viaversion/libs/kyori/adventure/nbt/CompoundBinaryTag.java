package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/CompoundBinaryTag.class */
public interface CompoundBinaryTag extends BinaryTag, CompoundTagSetter<CompoundBinaryTag>, Iterable<Map.Entry<String, ? extends BinaryTag>> {

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/CompoundBinaryTag$Builder.class */
    public interface Builder extends CompoundTagSetter<Builder> {
        @NotNull
        CompoundBinaryTag build();
    }

    @NotNull
    Set<String> keySet();

    @Nullable
    BinaryTag get(final String key);

    byte getByte(@NotNull final String key, final byte defaultValue);

    short getShort(@NotNull final String key, final short defaultValue);

    int getInt(@NotNull final String key, final int defaultValue);

    long getLong(@NotNull final String key, final long defaultValue);

    float getFloat(@NotNull final String key, final float defaultValue);

    double getDouble(@NotNull final String key, final double defaultValue);

    byte[] getByteArray(@NotNull final String key);

    byte[] getByteArray(@NotNull final String key, final byte[] defaultValue);

    @NotNull
    String getString(@NotNull final String key, @NotNull final String defaultValue);

    @NotNull
    ListBinaryTag getList(@NotNull final String key, @NotNull final ListBinaryTag defaultValue);

    @NotNull
    ListBinaryTag getList(@NotNull final String key, @NotNull final BinaryTagType<? extends BinaryTag> expectedType, @NotNull final ListBinaryTag defaultValue);

    @NotNull
    CompoundBinaryTag getCompound(@NotNull final String key, @NotNull final CompoundBinaryTag defaultValue);

    int[] getIntArray(@NotNull final String key);

    int[] getIntArray(@NotNull final String key, final int[] defaultValue);

    long[] getLongArray(@NotNull final String key);

    long[] getLongArray(@NotNull final String key, final long[] defaultValue);

    @NotNull
    static CompoundBinaryTag empty() {
        return CompoundBinaryTagImpl.EMPTY;
    }

    @NotNull
    static CompoundBinaryTag from(@NotNull final Map<String, ? extends BinaryTag> tags) {
        return tags.isEmpty() ? empty() : new CompoundBinaryTagImpl(new HashMap(tags));
    }

    @NotNull
    static Builder builder() {
        return new CompoundTagBuilder();
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag
    @NotNull
    default BinaryTagType<CompoundBinaryTag> type() {
        return BinaryTagTypes.COMPOUND;
    }

    default boolean getBoolean(@NotNull final String key) {
        return getBoolean(key, false);
    }

    default boolean getBoolean(@NotNull final String key, final boolean defaultValue) {
        return getByte(key) != 0 || defaultValue;
    }

    default byte getByte(@NotNull final String key) {
        return getByte(key, (byte) 0);
    }

    default short getShort(@NotNull final String key) {
        return getShort(key, (short) 0);
    }

    default int getInt(@NotNull final String key) {
        return getInt(key, 0);
    }

    default long getLong(@NotNull final String key) {
        return getLong(key, 0L);
    }

    default float getFloat(@NotNull final String key) {
        return getFloat(key, 0.0f);
    }

    default double getDouble(@NotNull final String key) {
        return getDouble(key, 0.0d);
    }

    @NotNull
    default String getString(@NotNull final String key) {
        return getString(key, "");
    }

    @NotNull
    default ListBinaryTag getList(@NotNull final String key) {
        return getList(key, ListBinaryTag.empty());
    }

    @NotNull
    default ListBinaryTag getList(@NotNull final String key, @NotNull final BinaryTagType<? extends BinaryTag> expectedType) {
        return getList(key, expectedType, ListBinaryTag.empty());
    }

    @NotNull
    default CompoundBinaryTag getCompound(@NotNull final String key) {
        return getCompound(key, empty());
    }
}
