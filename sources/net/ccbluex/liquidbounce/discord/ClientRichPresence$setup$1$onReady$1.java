package net.ccbluex.liquidbounce.discord;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;

/* compiled from: ClientRichPresence.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\b\n��\n\u0002\u0010\u0002\n��\u0010��\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, m53d2 = {"<anonymous>", "", "invoke"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/discord/ClientRichPresence$setup$1$onReady$1.class */
final class ClientRichPresence$setup$1$onReady$1 extends Lambda implements Functions<Unit> {
    final /* synthetic */ ClientRichPresence this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClientRichPresence$setup$1$onReady$1(ClientRichPresence $receiver) {
        super(0);
        this.this$0 = $receiver;
    }

    /* JADX WARN: Incorrect condition in loop: B:3:0x0007 */
    @Override // kotlin.jvm.functions.Functions
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void invoke() {
        /*
            r3 = this;
        L0:
            r0 = r3
            net.ccbluex.liquidbounce.discord.ClientRichPresence r0 = r0.this$0
            boolean r0 = net.ccbluex.liquidbounce.discord.ClientRichPresence.access$getRunning$p(r0)
            if (r0 == 0) goto L1f
            r0 = r3
            net.ccbluex.liquidbounce.discord.ClientRichPresence r0 = r0.this$0
            r0.update()
            r0 = 1000(0x3e8, double:4.94E-321)
            java.lang.Thread.sleep(r0)     // Catch: java.lang.InterruptedException -> L1b
            goto L0
        L1b:
            r4 = move-exception
            goto L0
        L1f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.discord.ClientRichPresence$setup$1$onReady$1.invoke():void");
    }
}
