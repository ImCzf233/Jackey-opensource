package jdk.nashorn.internal.runtime.logging;

import jdk.nashorn.internal.runtime.Context;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/logging/Loggable.class */
public interface Loggable {
    DebugLogger initLogger(Context context);

    DebugLogger getLogger();
}
