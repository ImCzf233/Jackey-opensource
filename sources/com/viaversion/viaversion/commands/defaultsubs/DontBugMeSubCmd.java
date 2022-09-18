package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/commands/defaultsubs/DontBugMeSubCmd.class */
public class DontBugMeSubCmd extends ViaSubCommand {
    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public String name() {
        return "dontbugme";
    }

    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public String description() {
        return "Toggle checking for updates";
    }

    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public boolean execute(ViaCommandSender sender, String[] args) {
        ConfigurationProvider provider = Via.getPlatform().getConfigurationProvider();
        boolean newValue = !Via.getConfig().isCheckForUpdates();
        Via.getConfig().setCheckForUpdates(newValue);
        provider.saveConfig();
        Object[] objArr = new Object[1];
        objArr[0] = newValue ? "&a" : "&cnot ";
        sendMessage(sender, "&6We will %snotify you about updates.", objArr);
        return true;
    }
}
