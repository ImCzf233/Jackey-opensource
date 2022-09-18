package org.spongepowered.asm.mixin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/Unique.class */
public @interface Unique {
    boolean silent() default false;
}
