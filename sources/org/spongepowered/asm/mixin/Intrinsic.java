package org.spongepowered.asm.mixin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/Intrinsic.class */
public @interface Intrinsic {
    boolean displace() default false;
}
