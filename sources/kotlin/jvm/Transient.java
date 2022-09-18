package kotlin.jvm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.MustBeDocumented;
import kotlin.annotation.Retention;

/* compiled from: JvmFlagAnnotations.kt */
@Target({ElementType.FIELD})
@kotlin.annotation.Target(allowedTargets = {AnnotationTarget.FIELD})
@Retention(AnnotationRetention.SOURCE)
@java.lang.annotation.Retention(RetentionPolicy.SOURCE)
@MustBeDocumented
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n��\b\u0087\u0002\u0018��2\u00020\u0001B��¨\u0006\u0002"}, m53d2 = {"Lkotlin/jvm/Transient;", "", "kotlin-stdlib"})
@Documented
/* loaded from: Jackey Client b2.jar:kotlin/jvm/Transient.class */
public @interface Transient {
}
