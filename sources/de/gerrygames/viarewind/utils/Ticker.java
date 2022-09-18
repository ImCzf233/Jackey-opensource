package de.gerrygames.viarewind.utils;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.StorableObject;
import java.util.Objects;
import java.util.stream.Stream;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/utils/Ticker.class */
public class Ticker {
    private static boolean init = false;

    public static void init() {
        if (init) {
            return;
        }
        synchronized (Ticker.class) {
            if (init) {
                return;
            }
            init = true;
            Via.getPlatform().runRepeatingSync(() -> {
                Via.getManager().getConnectionManager().getConnections().forEach(user -> {
                    Stream<StorableObject> stream = user.getStoredObjects().values().stream();
                    Objects.requireNonNull(Tickable.class);
                    Stream<StorableObject> filter = stream.filter((v1) -> {
                        return r1.isInstance(v1);
                    });
                    Objects.requireNonNull(Tickable.class);
                    filter.map((v1) -> {
                        return r1.cast(v1);
                    }).forEach((v0) -> {
                        v0.tick();
                    });
                });
            }, 1L);
        }
    }
}
