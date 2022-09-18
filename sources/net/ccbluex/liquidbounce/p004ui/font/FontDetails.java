package net.ccbluex.liquidbounce.p004ui.font;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
/* renamed from: net.ccbluex.liquidbounce.ui.font.FontDetails */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/font/FontDetails.class */
public @interface FontDetails {
    String fontName();

    int fontSize() default -1;
}
