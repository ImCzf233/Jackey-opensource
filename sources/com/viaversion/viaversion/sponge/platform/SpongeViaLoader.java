package com.viaversion.viaversion.sponge.platform;

import com.viaversion.viaversion.SpongePlugin;
import com.viaversion.viaversion.api.platform.PlatformTask;
import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
import com.viaversion.viaversion.sponge.listeners.UpdateListener;
import java.util.HashSet;
import java.util.Set;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.EventManager;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/sponge/platform/SpongeViaLoader.class */
public class SpongeViaLoader implements ViaPlatformLoader {
    private final SpongePlugin plugin;
    private final Set<Object> listeners = new HashSet();
    private final Set<PlatformTask> tasks = new HashSet();

    public SpongeViaLoader(SpongePlugin plugin) {
        this.plugin = plugin;
    }

    private void registerListener(Object listener) {
        Sponge.eventManager().registerListeners(this.plugin.container(), storeListener(listener));
    }

    private <T> T storeListener(T listener) {
        this.listeners.add(listener);
        return listener;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatformLoader
    public void load() {
        registerListener(new UpdateListener());
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatformLoader
    public void unload() {
        Set<Object> set = this.listeners;
        EventManager eventManager = Sponge.eventManager();
        eventManager.getClass();
        set.forEach(this::unregisterListeners);
        this.listeners.clear();
        this.tasks.forEach((v0) -> {
            v0.cancel();
        });
        this.tasks.clear();
    }
}
