package de.enzaxd.viaforge.util;

import com.viaversion.viaversion.api.platform.PlatformTask;
import java.util.concurrent.Future;

/* loaded from: Jackey Client b2.jar:de/enzaxd/viaforge/util/FutureTaskId.class */
public class FutureTaskId implements PlatformTask<Future<?>> {
    private final Future<?> object;

    public FutureTaskId(Future<?> object) {
        this.object = object;
    }

    @Override // com.viaversion.viaversion.api.platform.PlatformTask
    public Future<?> getObject() {
        return this.object;
    }

    @Override // com.viaversion.viaversion.api.platform.PlatformTask
    public void cancel() {
        this.object.cancel(false);
    }
}
