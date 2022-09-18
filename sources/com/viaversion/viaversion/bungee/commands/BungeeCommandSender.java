package com.viaversion.viaversion.bungee.commands;

import com.viaversion.viaversion.api.command.ViaCommandSender;
import java.util.UUID;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bungee/commands/BungeeCommandSender.class */
public class BungeeCommandSender implements ViaCommandSender {
    private final CommandSender sender;

    public BungeeCommandSender(CommandSender sender) {
        this.sender = sender;
    }

    @Override // com.viaversion.viaversion.api.command.ViaCommandSender
    public boolean hasPermission(String permission) {
        return this.sender.hasPermission(permission);
    }

    @Override // com.viaversion.viaversion.api.command.ViaCommandSender
    public void sendMessage(String msg) {
        this.sender.sendMessage(msg);
    }

    @Override // com.viaversion.viaversion.api.command.ViaCommandSender
    public UUID getUUID() {
        if (this.sender instanceof ProxiedPlayer) {
            return this.sender.getUniqueId();
        }
        return UUID.fromString(getName());
    }

    @Override // com.viaversion.viaversion.api.command.ViaCommandSender
    public String getName() {
        return this.sender.getName();
    }
}
