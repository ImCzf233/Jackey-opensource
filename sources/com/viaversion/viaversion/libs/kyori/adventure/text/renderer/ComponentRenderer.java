package com.viaversion.viaversion.libs.kyori.adventure.text.renderer;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/renderer/ComponentRenderer.class */
public interface ComponentRenderer<C> {
    @NotNull
    Component render(@NotNull final Component component, @NotNull final C context);

    default <T> ComponentRenderer<T> mapContext(final Function<T, C> transformer) {
        return component, ctx -> {
            return render(transformer, transformer.apply(ctx));
        };
    }
}
