package kotlin;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.Retention;
import kotlin.experimental.inferenceMarker;

/* compiled from: Inference.kt */
@Target({ElementType.METHOD})
@SinceKotlin(version = "1.4")
@inferenceMarker
@kotlin.annotation.Target(allowedTargets = {AnnotationTarget.FUNCTION})
@Retention(AnnotationRetention.BINARY)
@java.lang.annotation.Retention(RetentionPolicy.CLASS)
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n��\b\u0087\u0002\u0018��2\u00020\u0001B��¨\u0006\u0002"}, m53d2 = {"Lkotlin/OverloadResolutionByLambdaReturnType;", "", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/OverloadResolutionByLambdaReturnType.class */
public @interface OverloadResolutionByLambdaReturnType {
}
