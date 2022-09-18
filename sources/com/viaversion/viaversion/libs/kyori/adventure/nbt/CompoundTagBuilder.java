package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/CompoundTagBuilder.class */
public final class CompoundTagBuilder implements CompoundBinaryTag.Builder {
    @Nullable
    private Map<String, BinaryTag> tags;

    private Map<String, BinaryTag> tags() {
        if (this.tags == null) {
            this.tags = new HashMap();
        }
        return this.tags;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundTagSetter
    public CompoundBinaryTag.Builder put(@NotNull final String key, @NotNull final BinaryTag tag) {
        tags().put(key, tag);
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundTagSetter
    public CompoundBinaryTag.Builder put(@NotNull final CompoundBinaryTag tag) {
        Map<String, BinaryTag> tags = tags();
        for (String key : tag.keySet()) {
            tags.put(key, tag.get(key));
        }
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundTagSetter
    public CompoundBinaryTag.Builder put(@NotNull final Map<String, ? extends BinaryTag> tags) {
        tags().putAll(tags);
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundTagSetter
    public CompoundBinaryTag.Builder remove(@NotNull final String key, @Nullable final Consumer<? super BinaryTag> removed) {
        if (this.tags != null) {
            BinaryTag tag = this.tags.remove(key);
            if (removed != null) {
                removed.accept(tag);
            }
        }
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag.Builder
    @NotNull
    public CompoundBinaryTag build() {
        return this.tags == null ? CompoundBinaryTag.empty() : new CompoundBinaryTagImpl(new HashMap(this.tags));
    }
}
