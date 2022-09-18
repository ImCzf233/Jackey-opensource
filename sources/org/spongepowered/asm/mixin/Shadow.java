package org.spongepowered.asm.mixin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/Shadow.class */
public @interface Shadow {
    String prefix() default "shadow$";

    boolean remap() default true;

    String[] aliases() default {};
}
