package com.viaversion.viaversion.sponge.commands;

import com.viaversion.viaversion.SpongePlugin;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import java.util.UUID;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/sponge/commands/SpongePlayer.class */
public class SpongePlayer implements ViaCommandSender {
    private final ServerPlayer player;

    public SpongePlayer(ServerPlayer player) {
        this.player = player;
    }

    @Override // com.viaversion.viaversion.api.command.ViaCommandSender
    public boolean hasPermission(String permission) {
        return this.player.hasPermission(permission);
    }

    @Override // com.viaversion.viaversion.api.command.ViaCommandSender
    public void sendMessage(String msg) {
        this.player.sendMessage(SpongePlugin.LEGACY_SERIALIZER.deserialize(msg));
    }

    @Override // com.viaversion.viaversion.api.command.ViaCommandSender
    public UUID getUUID() {
        return this.player.uniqueId();
    }

    @Override // com.viaversion.viaversion.api.command.ViaCommandSender
    public String getName() {
        return (String) this.player.friendlyIdentifier().orElse(this.player.identifier());
    }
}
