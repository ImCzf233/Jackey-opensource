package com.viaversion.viaversion.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import com.viaversion.viaversion.commands.ViaCommandHandler;
import com.viaversion.viaversion.velocity.command.subs.ProbeSubCmd;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/velocity/command/VelocityCommandHandler.class */
public class VelocityCommandHandler extends ViaCommandHandler implements SimpleCommand {
    public VelocityCommandHandler() {
        try {
            registerSubCommand(new ProbeSubCmd());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void execute(SimpleCommand.Invocation invocation) {
        onCommand(new VelocityCommandSender(invocation.source()), (String[]) invocation.arguments());
    }

    public List<String> suggest(SimpleCommand.Invocation invocation) {
        return onTabComplete(new VelocityCommandSender(invocation.source()), (String[]) invocation.arguments());
    }
}
