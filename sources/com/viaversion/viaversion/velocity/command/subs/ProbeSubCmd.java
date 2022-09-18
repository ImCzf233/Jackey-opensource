package com.viaversion.viaversion.velocity.command.subs;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.velocity.platform.VelocityViaConfig;
import com.viaversion.viaversion.velocity.service.ProtocolDetectorService;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/velocity/command/subs/ProbeSubCmd.class */
public class ProbeSubCmd extends ViaSubCommand {
    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public String name() {
        return "probe";
    }

    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public String description() {
        return "Forces ViaVersion to scan server protocol versions " + (((VelocityViaConfig) Via.getConfig()).getVelocityPingInterval() == -1 ? "" : "(Also happens at an interval)");
    }

    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public boolean execute(ViaCommandSender sender, String[] args) {
        ProtocolDetectorService.getInstance().run();
        sendMessage(sender, "&6Started searching for protocol versions", new Object[0]);
        return true;
    }
}
