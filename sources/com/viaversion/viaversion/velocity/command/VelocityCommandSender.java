package com.viaversion.viaversion.velocity.command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.viaversion.viaversion.VelocityPlugin;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import java.util.UUID;
import org.apache.log4j.spi.LocationInfo;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/velocity/command/VelocityCommandSender.class */
public class VelocityCommandSender implements ViaCommandSender {
    private final CommandSource source;

    public VelocityCommandSender(CommandSource source) {
        this.source = source;
    }

    @Override // com.viaversion.viaversion.api.command.ViaCommandSender
    public boolean hasPermission(String permission) {
        return this.source.hasPermission(permission);
    }

    @Override // com.viaversion.viaversion.api.command.ViaCommandSender
    public void sendMessage(String msg) {
        this.source.sendMessage(VelocityPlugin.COMPONENT_SERIALIZER.deserialize(msg));
    }

    @Override // com.viaversion.viaversion.api.command.ViaCommandSender
    public UUID getUUID() {
        if (this.source instanceof Player) {
            return this.source.getUniqueId();
        }
        return UUID.fromString(getName());
    }

    @Override // com.viaversion.viaversion.api.command.ViaCommandSender
    public String getName() {
        if (this.source instanceof Player) {
            return this.source.getUsername();
        }
        return LocationInfo.f402NA;
    }
}
