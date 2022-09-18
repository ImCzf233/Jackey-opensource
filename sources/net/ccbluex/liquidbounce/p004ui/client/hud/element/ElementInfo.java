package net.ccbluex.liquidbounce.p004ui.client.hud.element;

import java.lang.annotation.RetentionPolicy;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.Retention;

/* compiled from: Element.kt */
@Retention(AnnotationRetention.RUNTIME)
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"�� \n\u0002\u0018\u0002\n\u0002\u0010\u001b\n��\n\u0002\u0010\u000e\n��\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\b\u0087\u0002\u0018��2\u00020\u0001B:\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u0005R\u000f\u0010\u0007\u001a\u00020\u0005¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u000bR\u000f\u0010\u0006\u001a\u00020\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u000bR\u000f\u0010\u0002\u001a\u00020\u0003¢\u0006\u0006\u001a\u0004\b\u0002\u0010\fR\u000f\u0010\b\u001a\u00020\t¢\u0006\u0006\u001a\u0004\b\b\u0010\rR\u000f\u0010\n\u001a\u00020\u0005¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u000f\u0010\u0004\u001a\u00020\u0005¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u000b¨\u0006\u000e"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/ElementInfo;", "", "name", "", "single", "", "force", "disableScale", "priority", "", "retrieveDamage", "()Z", "()Ljava/lang/String;", "()I", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/ElementInfo.class */
public @interface ElementInfo {
    String name();

    boolean single() default false;

    boolean force() default false;

    boolean disableScale() default false;

    int priority() default 0;

    boolean retrieveDamage() default false;
}
