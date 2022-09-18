package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.lang.Throwable;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/util/Codec.class */
public interface Codec<D, E, DX extends Throwable, EX extends Throwable> {

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/util/Codec$Decoder.class */
    public interface Decoder<D, E, X extends Throwable> {
        @NotNull
        D decode(@NotNull final E encoded) throws Throwable;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/util/Codec$Encoder.class */
    public interface Encoder<D, E, X extends Throwable> {
        @NotNull
        E encode(@NotNull final D decoded) throws Throwable;
    }

    @NotNull
    D decode(@NotNull final E encoded) throws Throwable;

    @NotNull
    E encode(@NotNull final D decoded) throws Throwable;

    @NotNull
    /* renamed from: of */
    static <D, E, DX extends Throwable, EX extends Throwable> Codec<D, E, DX, EX> m102of(@NotNull final Decoder<D, E, DX> decoder, @NotNull final Encoder<D, E, EX> encoder) {
        return (Codec<D, E, DX, EX>) new Codec<D, E, DX, EX>() { // from class: com.viaversion.viaversion.libs.kyori.adventure.util.Codec.1
            @Override // com.viaversion.viaversion.libs.kyori.adventure.util.Codec
            @NotNull
            public D decode(@NotNull final E encoded) throws Throwable {
                return (D) decoder.decode(encoded);
            }

            @Override // com.viaversion.viaversion.libs.kyori.adventure.util.Codec
            @NotNull
            public E encode(@NotNull final D decoded) throws Throwable {
                return (E) encoder.encode(decoded);
            }
        };
    }
}
