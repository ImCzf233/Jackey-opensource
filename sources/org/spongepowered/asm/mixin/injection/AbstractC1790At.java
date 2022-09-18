package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
/* renamed from: org.spongepowered.asm.mixin.injection.At */
/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/At.class */
public @interface AbstractC1790At {

    /* renamed from: org.spongepowered.asm.mixin.injection.At$Shift */
    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/At$Shift.class */
    public enum Shift {
        NONE,
        BEFORE,
        AFTER,
        BY
    }

    /* renamed from: id */
    String m24id() default "";

    String value();

    String slice() default "";

    Shift shift() default Shift.NONE;

    /* renamed from: by */
    int m25by() default 0;

    String[] args() default {};

    String target() default "";

    int ordinal() default -1;

    int opcode() default -1;

    boolean remap() default true;
}
