package jdk.nashorn.internal.objects.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/objects/annotations/Constructor.class */
public @interface Constructor {
    String name() default "";

    int arity() default -2;
}
