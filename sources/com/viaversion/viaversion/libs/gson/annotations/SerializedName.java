package com.viaversion.viaversion.libs.gson.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/gson/annotations/SerializedName.class */
public @interface SerializedName {
    String value();

    String[] alternate() default {};
}
