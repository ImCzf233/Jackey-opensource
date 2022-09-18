package org.spongepowered.asm.mixin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/Mixin.class */
public @interface Mixin {
    Class<?>[] value() default {};

    String[] targets() default {};

    int priority() default 1000;

    boolean remap() default true;
}
