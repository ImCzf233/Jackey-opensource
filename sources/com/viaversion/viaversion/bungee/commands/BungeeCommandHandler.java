package com.viaversion.viaversion.bungee.commands;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.bungee.commands.subs.ProbeSubCmd;
import com.viaversion.viaversion.commands.ViaCommandHandler;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bungee/commands/BungeeCommandHandler.class */
public class BungeeCommandHandler extends ViaCommandHandler {
    public BungeeCommandHandler() {
        try {
            registerSubCommand(new ProbeSubCmd());
        } catch (Exception e) {
            Via.getPlatform().getLogger().severe("Failed to register Bungee subcommands");
            e.printStackTrace();
        }
    }
}
