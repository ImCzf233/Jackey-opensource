package net.ccbluex.liquidbounce.features.module;

import java.lang.annotation.RetentionPolicy;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.Retention;

/* compiled from: ModuleInfo.kt */
@Retention(AnnotationRetention.RUNTIME)
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��&\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n��\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0010\b\n��\n\u0002\u0010\u000b\n\u0002\b\b\b\u0087\u0002\u0018��2\u00020\u0001BT\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u000b\u0012\b\b\u0002\u0010\r\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000bR\u000f\u0010\u000e\u001a\u00020\u000b¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u000f\u0010\n\u001a\u00020\u000b¢\u0006\u0006\u001a\u0004\b\n\u0010\u000fR\u000f\u0010\u0006\u001a\u00020\u0007¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0010R\u000f\u0010\u0005\u001a\u00020\u0003¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0011R\u000f\u0010\r\u001a\u00020\u000b¢\u0006\u0006\u001a\u0004\b\r\u0010\u000fR\u000f\u0010\b\u001a\u00020\t¢\u0006\u0006\u001a\u0004\b\b\u0010\u0012R\u000f\u0010\u0002\u001a\u00020\u0003¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0011R\u000f\u0010\f\u001a\u00020\u000b¢\u0006\u0006\u001a\u0004\b\f\u0010\u000fR\u000f\u0010\u0004\u001a\u00020\u0003¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0011¨\u0006\u0013"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/ModuleInfo;", "", "name", "", "spacedName", "description", "category", "Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;", "keyBind", "", "canEnable", "", "onlyEnable", "forceNoSound", "array", "()Z", "()Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;", "()Ljava/lang/String;", "()I", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/ModuleInfo.class */
public @interface ModuleInfo {
    String name();

    String spacedName() default "";

    String description();

    ModuleCategory category();

    int keyBind() default 0;

    boolean canEnable() default true;

    boolean onlyEnable() default false;

    boolean forceNoSound() default false;

    boolean array() default true;
}
