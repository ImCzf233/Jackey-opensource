package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.jetbrains.annotations.Debug;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Debug.Renderer(text = "\"CompoundBinaryTag[length=\" + this.tags.size() + \"]\"", childrenArray = "this.tags.entrySet().toArray()", hasChildren = "!this.tags.isEmpty()")
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/CompoundBinaryTagImpl.class */
public final class CompoundBinaryTagImpl extends AbstractBinaryTag implements CompoundBinaryTag {
    static final CompoundBinaryTag EMPTY = new CompoundBinaryTagImpl(Collections.emptyMap());
    private final Map<String, BinaryTag> tags;
    private final int hashCode;

    public CompoundBinaryTagImpl(final Map<String, BinaryTag> tags) {
        this.tags = Collections.unmodifiableMap(tags);
        this.hashCode = tags.hashCode();
    }

    public boolean contains(@NotNull final String key, @NotNull final BinaryTagType<?> type) {
        BinaryTag tag = this.tags.get(key);
        return tag != null && type.test(tag.type());
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag
    @NotNull
    public Set<String> keySet() {
        return Collections.unmodifiableSet(this.tags.keySet());
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag
    @Nullable
    public BinaryTag get(final String key) {
        return this.tags.get(key);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundTagSetter
    @NotNull
    public CompoundBinaryTag put(@NotNull final String key, @NotNull final BinaryTag tag) {
        return edit(map -> {
            map.put(key, tag);
        });
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundTagSetter
    @NotNull
    public CompoundBinaryTag put(@NotNull final CompoundBinaryTag tag) {
        return edit(map -> {
            for (String key : tag.keySet()) {
                map.put(key, tag.get(key));
            }
        });
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundTagSetter
    @NotNull
    public CompoundBinaryTag put(@NotNull final Map<String, ? extends BinaryTag> tags) {
        return edit(map -> {
            map.putAll(tags);
        });
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundTagSetter
    @NotNull
    public CompoundBinaryTag remove(@NotNull final String key, @Nullable final Consumer<? super BinaryTag> removed) {
        if (!this.tags.containsKey(key)) {
            return this;
        }
        return edit(map -> {
            BinaryTag tag = (BinaryTag) map.remove(key);
            if (removed != null) {
                removed.accept(tag);
            }
        });
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag
    public byte getByte(@NotNull final String key, final byte defaultValue) {
        if (contains(key, BinaryTagTypes.BYTE)) {
            return ((NumberBinaryTag) this.tags.get(key)).byteValue();
        }
        return defaultValue;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag
    public short getShort(@NotNull final String key, final short defaultValue) {
        if (contains(key, BinaryTagTypes.SHORT)) {
            return ((NumberBinaryTag) this.tags.get(key)).shortValue();
        }
        return defaultValue;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag
    public int getInt(@NotNull final String key, final int defaultValue) {
        if (contains(key, BinaryTagTypes.INT)) {
            return ((NumberBinaryTag) this.tags.get(key)).intValue();
        }
        return defaultValue;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag
    public long getLong(@NotNull final String key, final long defaultValue) {
        if (contains(key, BinaryTagTypes.LONG)) {
            return ((NumberBinaryTag) this.tags.get(key)).longValue();
        }
        return defaultValue;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag
    public float getFloat(@NotNull final String key, final float defaultValue) {
        if (contains(key, BinaryTagTypes.FLOAT)) {
            return ((NumberBinaryTag) this.tags.get(key)).floatValue();
        }
        return defaultValue;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag
    public double getDouble(@NotNull final String key, final double defaultValue) {
        if (contains(key, BinaryTagTypes.DOUBLE)) {
            return ((NumberBinaryTag) this.tags.get(key)).doubleValue();
        }
        return defaultValue;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag
    public byte[] getByteArray(@NotNull final String key) {
        if (contains(key, BinaryTagTypes.BYTE_ARRAY)) {
            return ((ByteArrayBinaryTag) this.tags.get(key)).value();
        }
        return new byte[0];
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag
    public byte[] getByteArray(@NotNull final String key, final byte[] defaultValue) {
        if (contains(key, BinaryTagTypes.BYTE_ARRAY)) {
            return ((ByteArrayBinaryTag) this.tags.get(key)).value();
        }
        return defaultValue;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag
    @NotNull
    public String getString(@NotNull final String key, @NotNull final String defaultValue) {
        if (contains(key, BinaryTagTypes.STRING)) {
            return ((StringBinaryTag) this.tags.get(key)).value();
        }
        return defaultValue;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag
    @NotNull
    public ListBinaryTag getList(@NotNull final String key, @NotNull final ListBinaryTag defaultValue) {
        if (contains(key, BinaryTagTypes.LIST)) {
            return (ListBinaryTag) this.tags.get(key);
        }
        return defaultValue;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag
    @NotNull
    public ListBinaryTag getList(@NotNull final String key, @NotNull final BinaryTagType<? extends BinaryTag> expectedType, @NotNull final ListBinaryTag defaultValue) {
        if (contains(key, BinaryTagTypes.LIST)) {
            ListBinaryTag tag = (ListBinaryTag) this.tags.get(key);
            if (expectedType.test(tag.elementType())) {
                return tag;
            }
        }
        return defaultValue;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag
    @NotNull
    public CompoundBinaryTag getCompound(@NotNull final String key, @NotNull final CompoundBinaryTag defaultValue) {
        if (contains(key, BinaryTagTypes.COMPOUND)) {
            return (CompoundBinaryTag) this.tags.get(key);
        }
        return defaultValue;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag
    public int[] getIntArray(@NotNull final String key) {
        if (contains(key, BinaryTagTypes.INT_ARRAY)) {
            return ((IntArrayBinaryTag) this.tags.get(key)).value();
        }
        return new int[0];
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag
    public int[] getIntArray(@NotNull final String key, final int[] defaultValue) {
        if (contains(key, BinaryTagTypes.INT_ARRAY)) {
            return ((IntArrayBinaryTag) this.tags.get(key)).value();
        }
        return defaultValue;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag
    public long[] getLongArray(@NotNull final String key) {
        if (contains(key, BinaryTagTypes.LONG_ARRAY)) {
            return ((LongArrayBinaryTag) this.tags.get(key)).value();
        }
        return new long[0];
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag
    public long[] getLongArray(@NotNull final String key, final long[] defaultValue) {
        if (contains(key, BinaryTagTypes.LONG_ARRAY)) {
            return ((LongArrayBinaryTag) this.tags.get(key)).value();
        }
        return defaultValue;
    }

    private CompoundBinaryTag edit(final Consumer<Map<String, BinaryTag>> consumer) {
        Map<String, BinaryTag> tags = new HashMap<>(this.tags);
        consumer.accept(tags);
        return new CompoundBinaryTagImpl(tags);
    }

    public boolean equals(final Object that) {
        return this == that || ((that instanceof CompoundBinaryTagImpl) && this.tags.equals(((CompoundBinaryTagImpl) that).tags));
    }

    public int hashCode() {
        return this.hashCode;
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.m91of("tags", this.tags));
    }

    @Override // java.lang.Iterable
    @NotNull
    public Iterator<Map.Entry<String, ? extends BinaryTag>> iterator() {
        return this.tags.entrySet().iterator();
    }

    @Override // java.lang.Iterable
    public void forEach(@NotNull final Consumer<? super Map.Entry<String, ? extends BinaryTag>> action) {
        this.tags.entrySet().forEach((Consumer) Objects.requireNonNull(action, "action"));
    }
}
