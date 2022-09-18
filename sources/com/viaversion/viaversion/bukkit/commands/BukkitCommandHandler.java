package com.viaversion.viaversion.bukkit.commands;

import com.viaversion.viaversion.commands.ViaCommandHandler;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bukkit/commands/BukkitCommandHandler.class */
public class BukkitCommandHandler extends ViaCommandHandler implements CommandExecutor, TabExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return onCommand(new BukkitCommandSender(sender), args);
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return onTabComplete(new BukkitCommandSender(sender), args);
    }
}
