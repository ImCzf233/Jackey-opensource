package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.command.Command;

/* compiled from: HClipCommand.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/HClipCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/command/commands/HClipCommand.class */
public final class HClipCommand extends Command {
    public HClipCommand() {
        super("hclip", new String[0]);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockSplitter
        jadx.core.utils.exceptions.JadxRuntimeException: Unexpected missing predecessor for block: B:4:0x000c
        	at jadx.core.dex.visitors.blocks.BlockSplitter.addTempConnectionsForExcHandlers(BlockSplitter.java:240)
        	at jadx.core.dex.visitors.blocks.BlockSplitter.visit(BlockSplitter.java:54)
        */
    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@org.jetbrains.annotations.NotNull java.lang.String[] r4) {
        /*
            r3 = this;
            r0 = r4
            java.lang.String r1 = "args"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r0, r1)
            r0 = r4
            int r0 = r0.length
            r1 = 1
            if (r0 <= r1) goto L25
        Ld:
            r0 = r4
            r1 = 1
            r0 = r0[r1]     // Catch: java.lang.NumberFormatException -> L1f
            double r0 = java.lang.Double.parseDouble(r0)     // Catch: java.lang.NumberFormatException -> L1f
            net.ccbluex.liquidbounce.utils.MovementUtils.forward(r0)     // Catch: java.lang.NumberFormatException -> L1f
            r0 = r3
            java.lang.String r1 = "You were teleported."
            r0.chat(r1)     // Catch: java.lang.NumberFormatException -> L1f
            goto L24
        L1f:
            r5 = move-exception
            r0 = r3
            r0.chatSyntaxError()
        L24:
            return
        L25:
            r0 = r3
            java.lang.String r1 = "hclip <value>"
            r0.chatSyntax(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.features.command.commands.HClipCommand.execute(java.lang.String[]):void");
    }
}
