package com.viaversion.viaversion.libs.kyori.adventure.nbt.api;

import com.viaversion.viaversion.libs.kyori.adventure.util.Codec;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/api/BinaryTagHolderImpl.class */
public final class BinaryTagHolderImpl implements BinaryTagHolder {
    private final String string;

    public BinaryTagHolderImpl(final String string) {
        this.string = (String) Objects.requireNonNull(string, "string");
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.api.BinaryTagHolder
    @NotNull
    public String string() {
        return this.string;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.nbt.api.BinaryTagHolder
    @NotNull
    public <T, DX extends Exception> T get(@NotNull final Codec<T, String, DX, ?> codec) throws Exception {
        return codec.decode(this.string);
    }

    public int hashCode() {
        return 31 * this.string.hashCode();
    }

    public boolean equals(final Object that) {
        if (!(that instanceof BinaryTagHolderImpl)) {
            return false;
        }
        return this.string.equals(((BinaryTagHolderImpl) that).string);
    }

    public String toString() {
        return this.string;
    }
}
