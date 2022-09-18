package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/commands/defaultsubs/HelpSubCmd.class */
public class HelpSubCmd extends ViaSubCommand {
    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public String name() {
        return "help";
    }

    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public String description() {
        return "You are looking at it right now!";
    }

    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public boolean execute(ViaCommandSender sender, String[] args) {
        Via.getManager().getCommandHandler().showHelp(sender);
        return true;
    }
}
