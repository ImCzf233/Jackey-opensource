package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/Redirect.class */
public @interface Redirect {
    String[] method();

    Slice slice() default @Slice;

    /* renamed from: at */
    AbstractC1790At m17at();

    boolean remap() default true;

    int require() default -1;

    int expect() default 1;

    int allow() default -1;

    String constraints() default "";
}
