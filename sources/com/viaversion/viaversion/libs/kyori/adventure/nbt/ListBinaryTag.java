package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/ListBinaryTag.class */
public interface ListBinaryTag extends ListTagSetter<ListBinaryTag, BinaryTag>, BinaryTag, Iterable<BinaryTag> {

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/ListBinaryTag$Builder.class */
    public interface Builder<T extends BinaryTag> extends ListTagSetter<Builder<T>, T> {
        @NotNull
        ListBinaryTag build();
    }

    @NotNull
    BinaryTagType<? extends BinaryTag> elementType();

    int size();

    @NotNull
    BinaryTag get(final int index);

    @NotNull
    ListBinaryTag set(final int index, @NotNull final BinaryTag tag, @Nullable final Consumer<? super BinaryTag> removed);

    @NotNull
    ListBinaryTag remove(final int index, @Nullable final Consumer<? super BinaryTag> removed);

    @NotNull
    Stream<BinaryTag> stream();

    @NotNull
    static ListBinaryTag empty() {
        return ListBinaryTagImpl.EMPTY;
    }

    @NotNull
    static ListBinaryTag from(@NotNull final Iterable<? extends BinaryTag> tags) {
        return builder().add(tags).build();
    }

    @NotNull
    static Builder<BinaryTag> builder() {
        return new ListTagBuilder();
    }

    @NotNull
    static <T extends BinaryTag> Builder<T> builder(@NotNull final BinaryTagType<T> type) {
        if (type == BinaryTagTypes.END) {
            throw new IllegalArgumentException("Cannot create a list of " + BinaryTagTypes.END);
        }
        return new ListTagBuilder(type);
    }

    @NotNull
    /* renamed from: of */
    static ListBinaryTag m131of(@NotNull final BinaryTagType<? extends BinaryTag> type, @NotNull final List<BinaryTag> tags) {
        if (tags.isEmpty()) {
            return empty();
        }
        if (type != BinaryTagTypes.END) {
            return new ListBinaryTagImpl(type, tags);
        }
        throw new IllegalArgumentException("Cannot create a list of " + BinaryTagTypes.END);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag
    @NotNull
    default BinaryTagType<ListBinaryTag> type() {
        return BinaryTagTypes.LIST;
    }

    @Deprecated
    @NotNull
    default BinaryTagType<? extends BinaryTag> listType() {
        return elementType();
    }

    default byte getByte(final int index) {
        return getByte(index, (byte) 0);
    }

    default byte getByte(final int index, final byte defaultValue) {
        BinaryTag tag = get(index);
        if (tag.type().numeric()) {
            return ((NumberBinaryTag) tag).byteValue();
        }
        return defaultValue;
    }

    default short getShort(final int index) {
        return getShort(index, (short) 0);
    }

    default short getShort(final int index, final short defaultValue) {
        BinaryTag tag = get(index);
        if (tag.type().numeric()) {
            return ((NumberBinaryTag) tag).shortValue();
        }
        return defaultValue;
    }

    default int getInt(final int index) {
        return getInt(index, 0);
    }

    default int getInt(final int index, final int defaultValue) {
        BinaryTag tag = get(index);
        if (tag.type().numeric()) {
            return ((NumberBinaryTag) tag).intValue();
        }
        return defaultValue;
    }

    default long getLong(final int index) {
        return getLong(index, 0L);
    }

    default long getLong(final int index, final long defaultValue) {
        BinaryTag tag = get(index);
        if (tag.type().numeric()) {
            return ((NumberBinaryTag) tag).longValue();
        }
        return defaultValue;
    }

    default float getFloat(final int index) {
        return getFloat(index, 0.0f);
    }

    default float getFloat(final int index, final float defaultValue) {
        BinaryTag tag = get(index);
        if (tag.type().numeric()) {
            return ((NumberBinaryTag) tag).floatValue();
        }
        return defaultValue;
    }

    default double getDouble(final int index) {
        return getDouble(index, 0.0d);
    }

    default double getDouble(final int index, final double defaultValue) {
        BinaryTag tag = get(index);
        if (tag.type().numeric()) {
            return ((NumberBinaryTag) tag).doubleValue();
        }
        return defaultValue;
    }

    default byte[] getByteArray(final int index) {
        BinaryTag tag = get(index);
        if (tag.type() == BinaryTagTypes.BYTE_ARRAY) {
            return ((ByteArrayBinaryTag) tag).value();
        }
        return new byte[0];
    }

    default byte[] getByteArray(final int index, final byte[] defaultValue) {
        BinaryTag tag = get(index);
        if (tag.type() == BinaryTagTypes.BYTE_ARRAY) {
            return ((ByteArrayBinaryTag) tag).value();
        }
        return defaultValue;
    }

    @NotNull
    default String getString(final int index) {
        return getString(index, "");
    }

    @NotNull
    default String getString(final int index, @NotNull final String defaultValue) {
        BinaryTag tag = get(index);
        if (tag.type() == BinaryTagTypes.STRING) {
            return ((StringBinaryTag) tag).value();
        }
        return defaultValue;
    }

    @NotNull
    default ListBinaryTag getList(final int index) {
        return getList(index, null, empty());
    }

    @NotNull
    default ListBinaryTag getList(final int index, @Nullable final BinaryTagType<?> elementType) {
        return getList(index, elementType, empty());
    }

    @NotNull
    default ListBinaryTag getList(final int index, @NotNull final ListBinaryTag defaultValue) {
        return getList(index, null, defaultValue);
    }

    @NotNull
    default ListBinaryTag getList(final int index, @Nullable final BinaryTagType<?> elementType, @NotNull final ListBinaryTag defaultValue) {
        BinaryTag tag = get(index);
        if (tag.type() == BinaryTagTypes.LIST) {
            ListBinaryTag list = (ListBinaryTag) tag;
            if (elementType == null || list.elementType() == elementType) {
                return list;
            }
        }
        return defaultValue;
    }

    @NotNull
    default CompoundBinaryTag getCompound(final int index) {
        return getCompound(index, CompoundBinaryTag.empty());
    }

    @NotNull
    default CompoundBinaryTag getCompound(final int index, @NotNull final CompoundBinaryTag defaultValue) {
        BinaryTag tag = get(index);
        if (tag.type() == BinaryTagTypes.COMPOUND) {
            return (CompoundBinaryTag) tag;
        }
        return defaultValue;
    }

    default int[] getIntArray(final int index) {
        BinaryTag tag = get(index);
        if (tag.type() == BinaryTagTypes.INT_ARRAY) {
            return ((IntArrayBinaryTag) tag).value();
        }
        return new int[0];
    }

    default int[] getIntArray(final int index, final int[] defaultValue) {
        BinaryTag tag = get(index);
        if (tag.type() == BinaryTagTypes.INT_ARRAY) {
            return ((IntArrayBinaryTag) tag).value();
        }
        return defaultValue;
    }

    default long[] getLongArray(final int index) {
        BinaryTag tag = get(index);
        if (tag.type() == BinaryTagTypes.LONG_ARRAY) {
            return ((LongArrayBinaryTag) tag).value();
        }
        return new long[0];
    }

    default long[] getLongArray(final int index, final long[] defaultValue) {
        BinaryTag tag = get(index);
        if (tag.type() == BinaryTagTypes.LONG_ARRAY) {
            return ((LongArrayBinaryTag) tag).value();
        }
        return defaultValue;
    }
}
