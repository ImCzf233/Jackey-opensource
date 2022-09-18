package com.viaversion.viaversion.bukkit.platform;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.platform.PlatformTask;
import org.bukkit.scheduler.BukkitTask;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bukkit/platform/BukkitViaTask.class */
public class BukkitViaTask implements PlatformTask<BukkitTask> {
    private final BukkitTask task;

    public BukkitViaTask(BukkitTask task) {
        this.task = task;
    }

    @Override // com.viaversion.viaversion.api.platform.PlatformTask
    public BukkitTask getObject() {
        return this.task;
    }

    @Override // com.viaversion.viaversion.api.platform.PlatformTask
    public void cancel() {
        Preconditions.checkArgument(this.task != null, "Task cannot be cancelled");
        this.task.cancel();
    }
}
