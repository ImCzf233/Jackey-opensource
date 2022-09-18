package com.viaversion.viaversion.libs.kyori.adventure.util;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/util/ComponentMessageThrowable.class */
public interface ComponentMessageThrowable {
    @Nullable
    Component componentMessage();

    @Nullable
    static Component getMessage(@Nullable final Throwable throwable) {
        if (throwable instanceof ComponentMessageThrowable) {
            return ((ComponentMessageThrowable) throwable).componentMessage();
        }
        return null;
    }

    @Nullable
    static Component getOrConvertMessage(@Nullable final Throwable throwable) {
        String message;
        if (throwable instanceof ComponentMessageThrowable) {
            return ((ComponentMessageThrowable) throwable).componentMessage();
        }
        if (throwable != null && (message = throwable.getMessage()) != null) {
            return Component.text(message);
        }
        return null;
    }
}
