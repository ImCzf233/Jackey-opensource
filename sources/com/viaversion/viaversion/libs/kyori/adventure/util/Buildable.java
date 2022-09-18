package com.viaversion.viaversion.libs.kyori.adventure.util;

import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable.Builder;
import java.util.function.Consumer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/util/Buildable.class */
public interface Buildable<R, B extends Builder<R>> {

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/util/Buildable$Builder.class */
    public interface Builder<R> {
        @Contract(value = "-> new", pure = true)
        @NotNull
        R build();
    }

    @Contract(value = "-> new", pure = true)
    @NotNull
    B toBuilder();

    @Contract(mutates = "param1")
    @NotNull
    static Buildable configureAndBuild(@NotNull final Builder builder, @Nullable final Consumer consumer) {
        if (consumer != null) {
            consumer.accept(builder);
        }
        return (Buildable) builder.build();
    }
}
