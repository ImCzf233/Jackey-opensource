package org.spongepowered.asm.mixin.transformer.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/transformer/meta/MixinInner.class */
public @interface MixinInner {
    String mixin();

    String name();
}
