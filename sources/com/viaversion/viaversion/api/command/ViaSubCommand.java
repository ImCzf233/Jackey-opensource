package com.viaversion.viaversion.api.command;

import com.viaversion.viaversion.util.ChatColorUtil;
import java.util.Collections;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/command/ViaSubCommand.class */
public abstract class ViaSubCommand {
    public abstract String name();

    public abstract String description();

    public abstract boolean execute(ViaCommandSender viaCommandSender, String[] strArr);

    public String usage() {
        return name();
    }

    public String permission() {
        return "viaversion.admin";
    }

    public List<String> onTabComplete(ViaCommandSender sender, String[] args) {
        return Collections.emptyList();
    }

    public static String color(String s) {
        return ChatColorUtil.translateAlternateColorCodes(s);
    }

    public static void sendMessage(ViaCommandSender sender, String message, Object... args) {
        sender.sendMessage(color(args == null ? message : String.format(message, args)));
    }
}
