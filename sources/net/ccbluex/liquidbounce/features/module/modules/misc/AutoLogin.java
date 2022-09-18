package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.util.ArrayList;
import java.util.Iterator;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.TextValue;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.util.IChatComponent;
import org.apache.log4j.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AutoLogin.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u001bH\u0007J\u0010\u0010\u001c\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u001dH\u0007J\u0010\u0010\u001e\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u001fH\u0007J\b\u0010 \u001a\u00020\u0018H\u0002J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u0014H\u0002J\u0010\u0010$\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u0014H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u001e\u0010\t\u001a\u0012\u0012\u0004\u0012\u00020\u000b0\nj\b\u0012\u0004\u0012\u00020\u000b`\fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000e\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000f\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0010\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0011\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u001e\u0010\u0012\u001a\u0012\u0012\u0004\u0012\u00020\u000b0\nj\b\u0012\u0004\u0012\u00020\u000b`\fX\u0082\u0004¢\u0006\u0002\n��R\u0016\u0010\u0013\u001a\u0004\u0018\u00010\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006%"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/AutoLogin;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "logTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "loginCmd", "Lnet/ccbluex/liquidbounce/value/TextValue;", "loginPackets", "Ljava/util/ArrayList;", "Lnet/minecraft/network/play/client/C01PacketChatMessage;", "Lkotlin/collections/ArrayList;", "loginRegex", "password", "regCmd", "regRegex", "regTimer", "registerPackets", "tag", "", "getTag", "()Ljava/lang/String;", "onEnable", "", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "resetEverything", "sendLogin", "", AsmConstants.STR, "sendRegister", "LiquidBounce"})
@ModuleInfo(name = "AutoLogin", spacedName = "Auto Login", description = "Automatically login into some servers for you.", category = ModuleCategory.MISC)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/misc/AutoLogin.class */
public final class AutoLogin extends Module {
    @NotNull
    private final TextValue password = new TextValue("Password", "example@01");
    @NotNull
    private final TextValue regRegex = new TextValue("Register-Regex", "/register");
    @NotNull
    private final TextValue loginRegex = new TextValue("Login-Regex", "/login");
    @NotNull
    private final TextValue regCmd = new TextValue("Register-Cmd", "/register %p %p");
    @NotNull
    private final TextValue loginCmd = new TextValue("Login-Cmd", "/login %p");
    @NotNull
    private final IntegerValue delayValue = new IntegerValue("Delay", (int) Level.TRACE_INT, 0, (int) Level.TRACE_INT, "ms");
    @NotNull
    private final ArrayList<C01PacketChatMessage> loginPackets = new ArrayList<>();
    @NotNull
    private final ArrayList<C01PacketChatMessage> registerPackets = new ArrayList<>();
    @NotNull
    private final MSTimer regTimer = new MSTimer();
    @NotNull
    private final MSTimer logTimer = new MSTimer();

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        resetEverything();
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        resetEverything();
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.registerPackets.isEmpty()) {
            this.regTimer.reset();
        } else if (this.regTimer.hasTimePassed(this.delayValue.get().intValue())) {
            Iterator<C01PacketChatMessage> it = this.registerPackets.iterator();
            while (it.hasNext()) {
                PacketUtils.sendPacketNoEvent((C01PacketChatMessage) it.next());
            }
            LiquidBounce.INSTANCE.getHud().addNotification(new Notification("Successfully registered.", Notification.Type.SUCCESS));
            this.registerPackets.clear();
            this.regTimer.reset();
        }
        if (this.loginPackets.isEmpty()) {
            this.logTimer.reset();
        } else if (this.logTimer.hasTimePassed(this.delayValue.get().intValue())) {
            Iterator<C01PacketChatMessage> it2 = this.loginPackets.iterator();
            while (it2.hasNext()) {
                PacketUtils.sendPacketNoEvent((C01PacketChatMessage) it2.next());
            }
            LiquidBounce.INSTANCE.getHud().addNotification(new Notification("Successfully logined.", Notification.Type.SUCCESS));
            this.loginPackets.clear();
            this.logTimer.reset();
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.f362mc.field_71439_g == null) {
            return;
        }
        S45PacketTitle packet = event.getPacket();
        if (packet instanceof S45PacketTitle) {
            IChatComponent messageOrigin = packet.func_179805_b();
            if (messageOrigin == null) {
                return;
            }
            String message = messageOrigin.func_150260_c();
            Intrinsics.checkNotNullExpressionValue(message, "messageOrigin.getUnformattedText()");
            if (StringsKt.contains((CharSequence) message, (CharSequence) this.loginRegex.get(), true)) {
                sendLogin(StringsKt.replace(this.loginCmd.get(), "%p", this.password.get(), true));
            }
            if (StringsKt.contains((CharSequence) message, (CharSequence) this.regRegex.get(), true)) {
                sendRegister(StringsKt.replace(this.regCmd.get(), "%p", this.password.get(), true));
            }
        }
        if (packet instanceof S02PacketChat) {
            String message2 = ((S02PacketChat) packet).func_148915_c().func_150260_c();
            Intrinsics.checkNotNullExpressionValue(message2, "packet.getChatComponent().getUnformattedText()");
            if (StringsKt.contains((CharSequence) message2, (CharSequence) this.loginRegex.get(), true)) {
                sendLogin(StringsKt.replace(this.loginCmd.get(), "%p", this.password.get(), true));
            }
            if (StringsKt.contains((CharSequence) message2, (CharSequence) this.regRegex.get(), true)) {
                sendRegister(StringsKt.replace(this.regCmd.get(), "%p", this.password.get(), true));
            }
        }
    }

    private final boolean sendLogin(String str) {
        return this.loginPackets.add(new C01PacketChatMessage(str));
    }

    private final boolean sendRegister(String str) {
        return this.registerPackets.add(new C01PacketChatMessage(str));
    }

    private final void resetEverything() {
        this.registerPackets.clear();
        this.loginPackets.clear();
        this.regTimer.reset();
        this.logTimer.reset();
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    @Nullable
    public String getTag() {
        return this.password.get();
    }
}
