package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.JoinConfigurationImpl;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ApiStatus.NonExtendable
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/JoinConfiguration.class */
public interface JoinConfiguration extends Buildable<JoinConfiguration, Builder>, Examinable {

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/JoinConfiguration$Builder.class */
    public interface Builder extends Buildable.Builder<JoinConfiguration> {
        @Contract("_ -> this")
        @NotNull
        Builder prefix(@Nullable final ComponentLike prefix);

        @Contract("_ -> this")
        @NotNull
        Builder suffix(@Nullable final ComponentLike suffix);

        @Contract("_ -> this")
        @NotNull
        Builder separator(@Nullable final ComponentLike separator);

        @Contract("_ -> this")
        @NotNull
        Builder lastSeparator(@Nullable final ComponentLike lastSeparator);

        @Contract("_ -> this")
        @NotNull
        Builder lastSeparatorIfSerial(@Nullable final ComponentLike lastSeparatorIfSerial);

        @Contract("_ -> this")
        @NotNull
        Builder convertor(@NotNull final Function<ComponentLike, Component> convertor);

        @Contract("_ -> this")
        @NotNull
        Builder predicate(@NotNull final Predicate<ComponentLike> predicate);
    }

    @Nullable
    Component prefix();

    @Nullable
    Component suffix();

    @Nullable
    Component separator();

    @Nullable
    Component lastSeparator();

    @Nullable
    Component lastSeparatorIfSerial();

    @NotNull
    Function<ComponentLike, Component> convertor();

    @NotNull
    Predicate<ComponentLike> predicate();

    @NotNull
    static Builder builder() {
        return new JoinConfigurationImpl.BuilderImpl();
    }

    @NotNull
    static JoinConfiguration noSeparators() {
        return JoinConfigurationImpl.NULL;
    }

    @NotNull
    static JoinConfiguration separator(@Nullable final ComponentLike separator) {
        return separator == null ? JoinConfigurationImpl.NULL : builder().separator(separator).build();
    }

    @NotNull
    static JoinConfiguration separators(@Nullable final ComponentLike separator, @Nullable final ComponentLike lastSeparator) {
        return (separator == null && lastSeparator == null) ? JoinConfigurationImpl.NULL : builder().separator(separator).lastSeparator(lastSeparator).build();
    }
}
