package com.viaversion.viaversion.sponge.listeners;

import com.viaversion.viaversion.SpongePlugin;
import com.viaversion.viaversion.ViaListener;
import com.viaversion.viaversion.api.protocol.Protocol;
import java.lang.reflect.Field;
import org.spongepowered.api.Sponge;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/sponge/listeners/ViaSpongeListener.class */
public class ViaSpongeListener extends ViaListener {
    private static Field entityIdField;
    private final SpongePlugin plugin;

    public ViaSpongeListener(SpongePlugin plugin, Class<? extends Protocol> requiredPipeline) {
        super(requiredPipeline);
        this.plugin = plugin;
    }

    @Override // com.viaversion.viaversion.ViaListener
    public void register() {
        if (isRegistered()) {
            return;
        }
        Sponge.eventManager().registerListeners(this.plugin.container(), this);
        setRegistered(true);
    }
}
