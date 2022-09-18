package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.api.debug.DebugHandler;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/commands/defaultsubs/DebugSubCmd.class */
public class DebugSubCmd extends ViaSubCommand {
    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public String name() {
        return "debug";
    }

    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public String description() {
        return "Toggle debug mode";
    }

    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public boolean execute(ViaCommandSender sender, String[] args) {
        DebugHandler debug = Via.getManager().debugHandler();
        if (args.length == 0) {
            Via.getManager().debugHandler().setEnabled(!Via.getManager().debugHandler().enabled());
            Object[] objArr = new Object[1];
            objArr[0] = Via.getManager().debugHandler().enabled() ? "&aenabled" : "&cdisabled";
            sendMessage(sender, "&6Debug mode is now %s", objArr);
            return true;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("clear")) {
                debug.clearPacketTypesToLog();
                sendMessage(sender, "&6Cleared packet types to log", new Object[0]);
                return true;
            } else if (args[0].equalsIgnoreCase("logposttransform")) {
                debug.setLogPostPacketTransform(!debug.logPostPacketTransform());
                Object[] objArr2 = new Object[1];
                objArr2[0] = debug.logPostPacketTransform() ? "&aenabled" : "&cdisabled";
                sendMessage(sender, "&6Post transform packet logging is now %s", objArr2);
                return true;
            } else {
                return false;
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add")) {
                debug.addPacketTypeNameToLog(args[1].toUpperCase(Locale.ROOT));
                sendMessage(sender, "&6Added packet type %s to debug logging", args[1]);
                return true;
            } else if (args[0].equalsIgnoreCase("remove")) {
                debug.removePacketTypeNameToLog(args[1].toUpperCase(Locale.ROOT));
                sendMessage(sender, "&6Removed packet type %s from debug logging", args[1]);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public List<String> onTabComplete(ViaCommandSender sender, String[] args) {
        return args.length == 1 ? Arrays.asList("clear", "logposttransform", "add", "remove") : Collections.emptyList();
    }
}
