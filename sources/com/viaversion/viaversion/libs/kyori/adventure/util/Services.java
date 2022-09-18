package com.viaversion.viaversion.libs.kyori.adventure.util;

import com.viaversion.viaversion.libs.kyori.adventure.Adventure;
import java.util.Iterator;
import java.util.Optional;
import java.util.ServiceLoader;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/util/Services.class */
public final class Services {
    private static final boolean SERVICE_LOAD_FAILURES_ARE_FATAL = Boolean.parseBoolean(System.getProperty(String.join(".", "net", "kyori", Adventure.NAMESPACE, "serviceLoadFailuresAreFatal"), String.valueOf(true)));

    private Services() {
    }

    @NotNull
    public static <P> Optional<P> service(@NotNull final Class<P> type) {
        boolean z;
        IllegalStateException illegalStateException;
        ServiceLoader<P> loader = Services0.loader(type);
        Iterator<P> it = loader.iterator();
        while (it.hasNext()) {
            try {
                P instance = it.next();
                if (it.hasNext()) {
                    throw new IllegalStateException("Expected to find one service " + type + ", found multiple");
                }
                return Optional.of(instance);
            } finally {
                if (z) {
                }
            }
        }
        return Optional.empty();
    }
}
