package com.viaversion.viaversion.sponge.commands;

import com.viaversion.viaversion.SpongePlugin;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import java.util.UUID;
import net.kyori.adventure.identity.Identity;
import org.spongepowered.api.command.CommandCause;
import org.spongepowered.api.util.Identifiable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/sponge/commands/SpongeCommandSender.class */
public class SpongeCommandSender implements ViaCommandSender {
    private final CommandCause source;

    public SpongeCommandSender(CommandCause source) {
        this.source = source;
    }

    @Override // com.viaversion.viaversion.api.command.ViaCommandSender
    public boolean hasPermission(String permission) {
        return this.source.hasPermission(permission);
    }

    @Override // com.viaversion.viaversion.api.command.ViaCommandSender
    public void sendMessage(String msg) {
        this.source.sendMessage(Identity.nil(), SpongePlugin.LEGACY_SERIALIZER.deserialize(msg));
    }

    @Override // com.viaversion.viaversion.api.command.ViaCommandSender
    public UUID getUUID() {
        if (this.source instanceof Identifiable) {
            return this.source.uniqueId();
        }
        return UUID.fromString(getName());
    }

    @Override // com.viaversion.viaversion.api.command.ViaCommandSender
    public String getName() {
        return (String) this.source.friendlyIdentifier().orElse(this.source.identifier());
    }
}
