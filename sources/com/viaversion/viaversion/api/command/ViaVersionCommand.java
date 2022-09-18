package com.viaversion.viaversion.api.command;

import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/command/ViaVersionCommand.class */
public interface ViaVersionCommand {
    void registerSubCommand(ViaSubCommand viaSubCommand) throws Exception;

    boolean hasSubCommand(String str);

    ViaSubCommand getSubCommand(String str);

    boolean onCommand(ViaCommandSender viaCommandSender, String[] strArr);

    List<String> onTabComplete(ViaCommandSender viaCommandSender, String[] strArr);

    void showHelp(ViaCommandSender viaCommandSender);
}
