package com.viaversion.viaversion.bukkit.commands;

import com.viaversion.viaversion.api.command.ViaCommandSender;
import java.util.UUID;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bukkit/commands/BukkitCommandSender.class */
public class BukkitCommandSender implements ViaCommandSender {
    private final CommandSender sender;

    public BukkitCommandSender(CommandSender sender) {
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
        if (this.sender instanceof Player) {
            return this.sender.getUniqueId();
        }
        return UUID.fromString(getName());
    }

    @Override // com.viaversion.viaversion.api.command.ViaCommandSender
    public String getName() {
        return this.sender.getName();
    }
}
