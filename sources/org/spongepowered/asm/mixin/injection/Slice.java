package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/Slice.class */
public @interface Slice {
    /* renamed from: id */
    String m16id() default "";

    AbstractC1790At from() default @AbstractC1790At("HEAD");

    /* renamed from: to */
    AbstractC1790At m15to() default @AbstractC1790At("TAIL");
}
