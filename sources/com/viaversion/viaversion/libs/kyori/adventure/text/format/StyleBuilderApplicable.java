package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilderApplicable;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/format/StyleBuilderApplicable.class */
public interface StyleBuilderApplicable extends ComponentBuilderApplicable {
    @Contract(mutates = "param")
    void styleApply(final Style.Builder style);

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilderApplicable
    default void componentBuilderApply(@NotNull final ComponentBuilder<?, ?> component) {
        component.style(this::styleApply);
    }
}
