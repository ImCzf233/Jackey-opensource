package com.viaversion.viabackwards;

import com.viaversion.viabackwards.api.ViaBackwardsPlatform;
import com.viaversion.viaversion.api.Via;
import net.md_5.bungee.api.plugin.Plugin;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/BungeePlugin.class */
public class BungeePlugin extends Plugin implements ViaBackwardsPlatform {
    public void onLoad() {
        Via.getManager().addEnableListener(() -> {
            init(getDataFolder());
        });
    }

    @Override // com.viaversion.viabackwards.api.ViaBackwardsPlatform
    public void disable() {
    }
}
