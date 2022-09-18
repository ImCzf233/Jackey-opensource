package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/commands/defaultsubs/AutoTeamSubCmd.class */
public class AutoTeamSubCmd extends ViaSubCommand {
    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public String name() {
        return "autoteam";
    }

    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public String description() {
        return "Toggle automatically teaming to prevent colliding.";
    }

    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public boolean execute(ViaCommandSender sender, String[] args) {
        ConfigurationProvider provider = Via.getPlatform().getConfigurationProvider();
        boolean newValue = !Via.getConfig().isAutoTeam();
        provider.set("auto-team", Boolean.valueOf(newValue));
        provider.saveConfig();
        Object[] objArr = new Object[1];
        objArr[0] = newValue ? "&aautomatically team players" : "&cno longer auto team players";
        sendMessage(sender, "&6We will %s", objArr);
        sendMessage(sender, "&6All players will need to re-login for the change to take place.", new Object[0]);
        return true;
    }
}
