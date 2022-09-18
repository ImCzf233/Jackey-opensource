package com.viaversion.viaversion.sponge.platform;

import com.viaversion.viaversion.api.platform.PlatformTask;
import org.spongepowered.api.scheduler.ScheduledTask;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/sponge/platform/SpongeViaTask.class */
public class SpongeViaTask implements PlatformTask<ScheduledTask> {
    private final ScheduledTask task;

    public SpongeViaTask(ScheduledTask task) {
        this.task = task;
    }

    @Override // com.viaversion.viaversion.api.platform.PlatformTask
    public ScheduledTask getObject() {
        return this.task;
    }

    @Override // com.viaversion.viaversion.api.platform.PlatformTask
    public void cancel() {
        this.task.cancel();
    }
}
