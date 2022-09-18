package org.spongepowered.asm.mixin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/Overwrite.class */
public @interface Overwrite {
    String constraints() default "";

    String[] aliases() default {};

    boolean remap() default true;
}
