package kotlin;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.Retention;

/* compiled from: Experimental.kt */
@Target({ElementType.ANNOTATION_TYPE})
@SinceKotlin(version = "1.2")
@Annotations(message = "Please use RequiresOptIn instead.")
@kotlin.annotation.Target(allowedTargets = {AnnotationTarget.ANNOTATION_CLASS})
@Retention(AnnotationRetention.BINARY)
@java.lang.annotation.Retention(RetentionPolicy.CLASS)
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\u0002\u0018��2\u00020\u0001:\u0001\u0005B\n\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003R\u000f\u0010\u0002\u001a\u00020\u0003¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004ø\u0001��\u0082\u0002\u0007\n\u0005\b\u009120\u0001¨\u0006\u0006"}, m53d2 = {"Lkotlin/Experimental;", "", "level", "Lkotlin/Experimental$Level;", "()Lkotlin/Experimental$Level;", "Level", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/Experimental.class */
public @interface Experimental {

    /* compiled from: Experimental.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018��2\b\u0012\u0004\u0012\u00020��0\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, m53d2 = {"Lkotlin/Experimental$Level;", "", "(Ljava/lang/String;I)V", "WARNING", "ERROR", "kotlin-stdlib"})
    /* loaded from: Jackey Client b2.jar:kotlin/Experimental$Level.class */
    public enum Level {
        WARNING,
        ERROR
    }

    Level level() default Level.ERROR;
}
