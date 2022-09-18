package com.viaversion.viaversion.bukkit.tasks.protocol1_12to1_11_1;

import com.viaversion.viaversion.bukkit.providers.BukkitInventoryQuickMoveProvider;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.storage.ItemTransaction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bukkit/tasks/protocol1_12to1_11_1/BukkitInventoryUpdateTask.class */
public class BukkitInventoryUpdateTask implements Runnable {
    private final BukkitInventoryQuickMoveProvider provider;
    private final UUID uuid;
    private final List<ItemTransaction> items = Collections.synchronizedList(new ArrayList());

    public BukkitInventoryUpdateTask(BukkitInventoryQuickMoveProvider provider, UUID uuid) {
        this.provider = provider;
        this.uuid = uuid;
    }

    public void addItem(short windowId, short slotId, short actionId) {
        ItemTransaction storage = new ItemTransaction(windowId, slotId, actionId);
        this.items.add(storage);
    }

    @Override // java.lang.Runnable
    public void run() {
        Player p = Bukkit.getServer().getPlayer(this.uuid);
        if (p == null) {
            this.provider.onTaskExecuted(this.uuid);
            return;
        }
        try {
            synchronized (this.items) {
                for (ItemTransaction storage : this.items) {
                    Object packet = this.provider.buildWindowClickPacket(p, storage);
                    boolean result = this.provider.sendPacketToServer(p, packet);
                    if (!result) {
                        break;
                    }
                }
                this.items.clear();
            }
        } finally {
            this.provider.onTaskExecuted(this.uuid);
        }
    }
}
