package jdk.nashorn.internal.runtime.logging;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/logging/Logger.class */
public @interface Logger {
    String name() default "";
}
