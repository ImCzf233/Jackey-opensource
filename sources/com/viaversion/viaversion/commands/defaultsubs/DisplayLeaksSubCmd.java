package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import io.netty.util.ResourceLeakDetector;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/commands/defaultsubs/DisplayLeaksSubCmd.class */
public class DisplayLeaksSubCmd extends ViaSubCommand {
    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public String name() {
        return "displayleaks";
    }

    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public String description() {
        return "Try to hunt memory leaks!";
    }

    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public boolean execute(ViaCommandSender sender, String[] args) {
        if (ResourceLeakDetector.getLevel() != ResourceLeakDetector.Level.PARANOID) {
            ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.PARANOID);
        } else {
            ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.DISABLED);
        }
        Object[] objArr = new Object[1];
        objArr[0] = ResourceLeakDetector.getLevel() == ResourceLeakDetector.Level.PARANOID ? "&aenabled" : "&cdisabled";
        sendMessage(sender, "&6Leak detector is now %s", objArr);
        return true;
    }
}
