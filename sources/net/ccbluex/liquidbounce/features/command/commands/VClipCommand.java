package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.command.Command;

/* compiled from: VClipCommand.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/VClipCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/command/commands/VClipCommand.class */
public final class VClipCommand extends Command {
    public VClipCommand() {
        super("vclip", new String[0]);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockSplitter
        jadx.core.utils.exceptions.JadxRuntimeException: Unexpected missing predecessor for block: B:4:0x000c
        	at jadx.core.dex.visitors.blocks.BlockSplitter.addTempConnectionsForExcHandlers(BlockSplitter.java:240)
        	at jadx.core.dex.visitors.blocks.BlockSplitter.visit(BlockSplitter.java:54)
        */
    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@org.jetbrains.annotations.NotNull java.lang.String[] r9) {
        /*
            r8 = this;
            r0 = r9
            java.lang.String r1 = "args"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r0, r1)
            r0 = r9
            int r0 = r0.length
            r1 = 1
            if (r0 <= r1) goto L5c
        Ld:
            r0 = r9
            r1 = 1
            r0 = r0[r1]     // Catch: java.lang.NumberFormatException -> L56
            double r0 = java.lang.Double.parseDouble(r0)     // Catch: java.lang.NumberFormatException -> L56
            r10 = r0
            net.minecraft.client.Minecraft r0 = net.ccbluex.liquidbounce.utils.MinecraftInstance.f362mc     // Catch: java.lang.NumberFormatException -> L56
            net.minecraft.client.entity.EntityPlayerSP r0 = r0.field_71439_g     // Catch: java.lang.NumberFormatException -> L56
            boolean r0 = r0.func_70115_ae()     // Catch: java.lang.NumberFormatException -> L56
            if (r0 == 0) goto L2c
            net.minecraft.client.Minecraft r0 = net.ccbluex.liquidbounce.utils.MinecraftInstance.f362mc     // Catch: java.lang.NumberFormatException -> L56
            net.minecraft.client.entity.EntityPlayerSP r0 = r0.field_71439_g     // Catch: java.lang.NumberFormatException -> L56
            net.minecraft.entity.Entity r0 = r0.field_70154_o     // Catch: java.lang.NumberFormatException -> L56
            goto L35
        L2c:
            net.minecraft.client.Minecraft r0 = net.ccbluex.liquidbounce.utils.MinecraftInstance.f362mc     // Catch: java.lang.NumberFormatException -> L56
            net.minecraft.client.entity.EntityPlayerSP r0 = r0.field_71439_g     // Catch: java.lang.NumberFormatException -> L56
            net.minecraft.entity.Entity r0 = (net.minecraft.entity.Entity) r0     // Catch: java.lang.NumberFormatException -> L56
        L35:
            r12 = r0
            r0 = r12
            r1 = r12
            double r1 = r1.field_70165_t     // Catch: java.lang.NumberFormatException -> L56
            r2 = r12
            double r2 = r2.field_70163_u     // Catch: java.lang.NumberFormatException -> L56
            r3 = r10
            double r2 = r2 + r3
            r3 = r12
            double r3 = r3.field_70161_v     // Catch: java.lang.NumberFormatException -> L56
            r0.func_70107_b(r1, r2, r3)     // Catch: java.lang.NumberFormatException -> L56
            r0 = r8
            java.lang.String r1 = "You were teleported."
            r0.chat(r1)     // Catch: java.lang.NumberFormatException -> L56
            goto L5b
        L56:
            r10 = move-exception
            r0 = r8
            r0.chatSyntaxError()
        L5b:
            return
        L5c:
            r0 = r8
            java.lang.String r1 = "vclip <value>"
            r0.chatSyntax(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.features.command.commands.VClipCommand.execute(java.lang.String[]):void");
    }
}
