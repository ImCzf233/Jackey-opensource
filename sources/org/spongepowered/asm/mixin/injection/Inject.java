package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/Inject.class */
public @interface Inject {
    /* renamed from: id */
    String m22id() default "";

    String[] method();

    Slice[] slice() default {};

    /* renamed from: at */
    AbstractC1790At[] m23at();

    boolean cancellable() default false;

    LocalCapture locals() default LocalCapture.NO_CAPTURE;

    boolean remap() default true;

    int require() default -1;

    int expect() default 1;

    int allow() default -1;

    String constraints() default "";
}
