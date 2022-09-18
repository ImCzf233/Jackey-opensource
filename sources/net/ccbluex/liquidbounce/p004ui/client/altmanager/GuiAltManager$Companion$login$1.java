package net.ccbluex.liquidbounce.p004ui.client.altmanager;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;
import me.liuli.elixir.account.MinecraftAccount;

/* compiled from: GuiAltManager.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\b\n��\n\u0002\u0010\u0002\n��\u0010��\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, m53d2 = {"<anonymous>", "", "invoke"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager$Companion$login$1 */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager$Companion$login$1.class */
public final class GuiAltManager$Companion$login$1 extends Lambda implements Functions<Unit> {
    final /* synthetic */ Function1<Exception, Unit> $error;
    final /* synthetic */ MinecraftAccount $minecraftAccount;
    final /* synthetic */ Functions<Unit> $success;
    final /* synthetic */ Functions<Unit> $done;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public GuiAltManager$Companion$login$1(Function1<? super Exception, Unit> function1, MinecraftAccount $minecraftAccount, Functions<Unit> functions, Functions<Unit> functions2) {
        super(0);
        this.$error = function1;
        this.$minecraftAccount = $minecraftAccount;
        this.$success = functions;
        this.$done = functions2;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockSplitter
        jadx.core.utils.exceptions.JadxRuntimeException: Unexpected missing predecessor for block: B:4:0x000f
        	at jadx.core.dex.visitors.blocks.BlockSplitter.addTempConnectionsForExcHandlers(BlockSplitter.java:240)
        	at jadx.core.dex.visitors.blocks.BlockSplitter.visit(BlockSplitter.java:54)
        */
    @Override // kotlin.jvm.functions.Functions
    public final void invoke() {
        /*
            r8 = this;
            net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager$Companion r0 = net.ccbluex.liquidbounce.p004ui.client.altmanager.GuiAltManager.Companion
            com.thealtening.AltService r0 = r0.getAltService()
            com.thealtening.AltService$EnumAltService r0 = r0.getCurrentService()
            com.thealtening.AltService$EnumAltService r1 = com.thealtening.AltService.EnumAltService.MOJANG
            if (r0 == r1) goto L56
        L10:
            net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager$Companion r0 = net.ccbluex.liquidbounce.p004ui.client.altmanager.GuiAltManager.Companion     // Catch: java.lang.NoSuchFieldException -> L1f java.lang.IllegalAccessException -> L3c
            com.thealtening.AltService r0 = r0.getAltService()     // Catch: java.lang.NoSuchFieldException -> L1f java.lang.IllegalAccessException -> L3c
            com.thealtening.AltService$EnumAltService r1 = com.thealtening.AltService.EnumAltService.MOJANG     // Catch: java.lang.NoSuchFieldException -> L1f java.lang.IllegalAccessException -> L3c
            r0.switchService(r1)     // Catch: java.lang.NoSuchFieldException -> L1f java.lang.IllegalAccessException -> L3c
            goto L56
        L1f:
            r9 = move-exception
            r0 = r8
            kotlin.jvm.functions.Function1<java.lang.Exception, kotlin.Unit> r0 = r0.$error
            r1 = r9
            java.lang.Object r0 = r0.invoke(r1)
            org.apache.logging.log4j.Logger r0 = net.ccbluex.liquidbounce.utils.ClientUtils.getLogger()
            java.lang.String r1 = "Something went wrong while trying to switch alt service."
            r2 = r9
            java.lang.Throwable r2 = (java.lang.Throwable) r2
            r0.error(r1, r2)
            goto L56
        L3c:
            r9 = move-exception
            r0 = r8
            kotlin.jvm.functions.Function1<java.lang.Exception, kotlin.Unit> r0 = r0.$error
            r1 = r9
            java.lang.Object r0 = r0.invoke(r1)
            org.apache.logging.log4j.Logger r0 = net.ccbluex.liquidbounce.utils.ClientUtils.getLogger()
            java.lang.String r1 = "Something went wrong while trying to switch alt service."
            r2 = r9
            java.lang.Throwable r2 = (java.lang.Throwable) r2
            r0.error(r1, r2)
        L56:
            r0 = r8
            me.liuli.elixir.account.MinecraftAccount r0 = r0.$minecraftAccount     // Catch: java.lang.Exception -> Lab
            r0.update()     // Catch: java.lang.Exception -> Lab
            net.minecraft.client.Minecraft r0 = net.minecraft.client.Minecraft.func_71410_x()     // Catch: java.lang.Exception -> Lab
            net.minecraft.util.Session r1 = new net.minecraft.util.Session     // Catch: java.lang.Exception -> Lab
            r2 = r1
            r3 = r8
            me.liuli.elixir.account.MinecraftAccount r3 = r3.$minecraftAccount     // Catch: java.lang.Exception -> Lab
            me.liuli.elixir.compat.Session r3 = r3.getSession()     // Catch: java.lang.Exception -> Lab
            java.lang.String r3 = r3.getUsername()     // Catch: java.lang.Exception -> Lab
            r4 = r8
            me.liuli.elixir.account.MinecraftAccount r4 = r4.$minecraftAccount     // Catch: java.lang.Exception -> Lab
            me.liuli.elixir.compat.Session r4 = r4.getSession()     // Catch: java.lang.Exception -> Lab
            java.lang.String r4 = r4.getUuid()     // Catch: java.lang.Exception -> Lab
            r5 = r8
            me.liuli.elixir.account.MinecraftAccount r5 = r5.$minecraftAccount     // Catch: java.lang.Exception -> Lab
            me.liuli.elixir.compat.Session r5 = r5.getSession()     // Catch: java.lang.Exception -> Lab
            java.lang.String r5 = r5.getToken()     // Catch: java.lang.Exception -> Lab
            java.lang.String r6 = "mojang"
            r2.<init>(r3, r4, r5, r6)     // Catch: java.lang.Exception -> Lab
            r0.field_71449_j = r1     // Catch: java.lang.Exception -> Lab
            net.ccbluex.liquidbounce.LiquidBounce r0 = net.ccbluex.liquidbounce.LiquidBounce.INSTANCE     // Catch: java.lang.Exception -> Lab
            net.ccbluex.liquidbounce.event.EventManager r0 = r0.getEventManager()     // Catch: java.lang.Exception -> Lab
            net.ccbluex.liquidbounce.event.SessionEvent r1 = new net.ccbluex.liquidbounce.event.SessionEvent     // Catch: java.lang.Exception -> Lab
            r2 = r1
            r2.<init>()     // Catch: java.lang.Exception -> Lab
            net.ccbluex.liquidbounce.event.Event r1 = (net.ccbluex.liquidbounce.event.Event) r1     // Catch: java.lang.Exception -> Lab
            r0.callEvent(r1)     // Catch: java.lang.Exception -> Lab
            r0 = r8
            kotlin.jvm.functions.Function0<kotlin.Unit> r0 = r0.$success     // Catch: java.lang.Exception -> Lab
            java.lang.Object r0 = r0.invoke()     // Catch: java.lang.Exception -> Lab
            goto Lb7
        Lab:
            r9 = move-exception
            r0 = r8
            kotlin.jvm.functions.Function1<java.lang.Exception, kotlin.Unit> r0 = r0.$error
            r1 = r9
            java.lang.Object r0 = r0.invoke(r1)
        Lb7:
            r0 = r8
            kotlin.jvm.functions.Function0<kotlin.Unit> r0 = r0.$done
            java.lang.Object r0 = r0.invoke()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.p004ui.client.altmanager.GuiAltManager$Companion$login$1.invoke():void");
    }
}
