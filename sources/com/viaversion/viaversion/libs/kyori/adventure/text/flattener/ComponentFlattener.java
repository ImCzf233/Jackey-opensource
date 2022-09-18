package com.viaversion.viaversion.libs.kyori.adventure.text.flattener;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.flattener.ComponentFlattenerImpl;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/flattener/ComponentFlattener.class */
public interface ComponentFlattener extends Buildable<ComponentFlattener, Builder> {

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/flattener/ComponentFlattener$Builder.class */
    public interface Builder extends Buildable.Builder<ComponentFlattener> {
        @NotNull
        <T extends Component> Builder mapper(@NotNull final Class<T> type, @NotNull final Function<T, String> converter);

        @NotNull
        <T extends Component> Builder complexMapper(@NotNull final Class<T> type, @NotNull final BiConsumer<T, Consumer<Component>> converter);

        @NotNull
        Builder unknownMapper(@Nullable final Function<Component, String> converter);
    }

    void flatten(@NotNull final Component input, @NotNull final FlattenerListener listener);

    @NotNull
    static Builder builder() {
        return new ComponentFlattenerImpl.BuilderImpl();
    }

    @NotNull
    static ComponentFlattener basic() {
        return ComponentFlattenerImpl.BASIC;
    }

    @NotNull
    static ComponentFlattener textOnly() {
        return ComponentFlattenerImpl.TEXT_ONLY;
    }
}
