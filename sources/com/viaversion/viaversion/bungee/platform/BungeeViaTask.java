package com.viaversion.viaversion.bungee.platform;

import com.viaversion.viaversion.api.platform.PlatformTask;
import net.md_5.bungee.api.scheduler.ScheduledTask;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bungee/platform/BungeeViaTask.class */
public class BungeeViaTask implements PlatformTask<ScheduledTask> {
    private final ScheduledTask task;

    public BungeeViaTask(ScheduledTask task) {
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
