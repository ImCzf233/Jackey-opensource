package com.viaversion.viaversion.libs.kyori.adventure.nbt.api;

import com.viaversion.viaversion.libs.kyori.adventure.util.Codec;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/nbt/api/BinaryTagHolder.class */
public interface BinaryTagHolder {
    @NotNull
    String string();

    @NotNull
    <T, DX extends Exception> T get(@NotNull final Codec<T, String, DX, ?> codec) throws Exception;

    @NotNull
    static <T, EX extends Exception> BinaryTagHolder encode(@NotNull final T nbt, @NotNull final Codec<? super T, String, ?, EX> codec) throws Exception {
        return new BinaryTagHolderImpl(codec.encode(nbt));
    }

    @NotNull
    /* renamed from: of */
    static BinaryTagHolder m125of(@NotNull final String string) {
        return new BinaryTagHolderImpl(string);
    }
}
