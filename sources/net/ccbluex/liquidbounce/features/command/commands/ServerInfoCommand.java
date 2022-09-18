package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.handshake.client.C00Handshake;
import org.jetbrains.annotations.NotNull;

/* compiled from: ServerInfoCommand.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��8\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n��\n\u0002\u0010\b\n��\n\u0002\u0010\u0002\n��\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\u0018��2\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u001b\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bH\u0016¢\u0006\u0002\u0010\fJ\b\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n��¨\u0006\u0012"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/ServerInfoCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "ip", "", "port", "", "execute", "", "args", "", "([Ljava/lang/String;)V", "handleEvents", "", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/command/commands/ServerInfoCommand.class */
public final class ServerInfoCommand extends Command implements Listenable {
    @NotNull

    /* renamed from: ip */
    private String f334ip = "";
    private int port;

    public ServerInfoCommand() {
        super("serverinfo", new String[0]);
        LiquidBounce.INSTANCE.getEventManager().registerListener(this);
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) {
        Intrinsics.checkNotNullParameter(args, "args");
        if (MinecraftInstance.f362mc.func_147104_D() == null) {
            chat("This command does not work in single player.");
            return;
        }
        ServerData data = MinecraftInstance.f362mc.func_147104_D();
        chat("Server infos:");
        chat(Intrinsics.stringPlus("§7Name: §8", data.field_78847_a));
        chat("§7IP: §8" + this.f334ip + ':' + this.port);
        chat(Intrinsics.stringPlus("§7Players: §8", data.field_78846_c));
        chat(Intrinsics.stringPlus("§7MOTD: §8", data.field_78843_d));
        chat(Intrinsics.stringPlus("§7ServerVersion: §8", data.field_82822_g));
        chat(Intrinsics.stringPlus("§7ProtocolVersion: §8", Integer.valueOf(data.field_82821_f)));
        chat(Intrinsics.stringPlus("§7Ping: §8", Long.valueOf(data.field_78844_e)));
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        C00Handshake packet = event.getPacket();
        if (packet instanceof C00Handshake) {
            String str = packet.field_149598_b;
            Intrinsics.checkNotNullExpressionValue(str, "packet.ip");
            this.f334ip = str;
            this.port = packet.field_149599_c;
        }
    }

    @Override // net.ccbluex.liquidbounce.event.Listenable
    public boolean handleEvents() {
        return true;
    }
}
