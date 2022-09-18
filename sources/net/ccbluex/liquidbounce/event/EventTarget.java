package net.ccbluex.liquidbounce.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.Retention;

/* compiled from: Listenable.kt */
@Target({ElementType.METHOD})
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n��\n\u0002\u0010\u000b\n��\n\u0002\u0010\b\n\u0002\b\u0003\b\u0087\u0002\u0018��2\u00020\u0001B\u0014\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005R\u000f\u0010\u0002\u001a\u00020\u0003¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0006R\u000f\u0010\u0004\u001a\u00020\u0005¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0007¨\u0006\b"}, m53d2 = {"Lnet/ccbluex/liquidbounce/event/EventTarget;", "", "ignoreCondition", "", "priority", "", "()Z", "()I", "LiquidBounce"})
@kotlin.annotation.Target(allowedTargets = {AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER})
@Retention(AnnotationRetention.RUNTIME)
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/event/EventTarget.class */
public @interface EventTarget {
    boolean ignoreCondition() default false;

    int priority() default 0;
}
