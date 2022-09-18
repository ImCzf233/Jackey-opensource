package com.viaversion.viaversion.velocity.platform;

import com.velocitypowered.api.scheduler.ScheduledTask;
import com.viaversion.viaversion.api.platform.PlatformTask;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/velocity/platform/VelocityViaTask.class */
public class VelocityViaTask implements PlatformTask<ScheduledTask> {
    private final ScheduledTask task;

    public VelocityViaTask(ScheduledTask task) {
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
