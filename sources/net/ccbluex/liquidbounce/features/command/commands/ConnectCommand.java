package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.p004ui.client.GuiMainMenu;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import org.jetbrains.annotations.NotNull;

/* compiled from: ConnectCommand.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/ConnectCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/command/commands/ConnectCommand.class */
public final class ConnectCommand extends Command {
    public ConnectCommand() {
        super("connect", new String[0]);
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) {
        Intrinsics.checkNotNullParameter(args, "args");
        if (args.length == 3 && args[2].equals("silent")) {
            chat("Connecting to §a§l" + args[1] + " §7(Silent mode)");
            MinecraftInstance.f362mc.func_147108_a(new GuiConnecting(new GuiMultiplayer(new GuiMainMenu()), MinecraftInstance.f362mc, new ServerData("", args[1], false)));
        } else if (args.length == 2) {
            chat(Intrinsics.stringPlus("Connecting to §a§l", args[1]));
            MinecraftInstance.f362mc.field_71441_e.func_72882_A();
            MinecraftInstance.f362mc.func_147108_a(new GuiConnecting(new GuiMultiplayer(new GuiMainMenu()), MinecraftInstance.f362mc, new ServerData("", args[1], false)));
        } else {
            chatSyntax("connect <ip:port> (silent)");
        }
    }
}
