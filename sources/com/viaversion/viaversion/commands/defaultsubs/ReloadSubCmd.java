package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/commands/defaultsubs/ReloadSubCmd.class */
public class ReloadSubCmd extends ViaSubCommand {
    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public String name() {
        return "reload";
    }

    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public String description() {
        return "Reload the config from the disk";
    }

    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public boolean execute(ViaCommandSender sender, String[] args) {
        Via.getPlatform().getConfigurationProvider().reloadConfig();
        sendMessage(sender, "&6Configuration successfully reloaded! Some features may need a restart.", new Object[0]);
        return true;
    }
}
