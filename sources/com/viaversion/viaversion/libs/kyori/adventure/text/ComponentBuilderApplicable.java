package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/ComponentBuilderApplicable.class */
public interface ComponentBuilderApplicable {
    @Contract(mutates = "param")
    void componentBuilderApply(@NotNull final ComponentBuilder<?, ?> component);
}
