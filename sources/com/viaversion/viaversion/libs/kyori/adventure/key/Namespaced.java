package com.viaversion.viaversion.libs.kyori.adventure.key;

import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/key/Namespaced.class */
public interface Namespaced {
    @Pattern("[a-z0-9_\\-.]+")
    @NotNull
    String namespace();
}
