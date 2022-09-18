package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.util.Timer;
import java.util.TimerTask;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.event.ClickEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.util.IChatComponent;
import org.jetbrains.annotations.NotNull;

/* compiled from: AutoPlay.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\b\n��\n\u0002\u0010\u000b\n��\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001aH\u0007J\u0010\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001cH\u0007J \u0010\u001d\u001a\u00020\u00172\b\b\u0002\u0010\u001e\u001a\u00020\u001f2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00170!H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000e\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0010\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u0014\u0010\u0011\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n��¨\u0006\""}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/AutoPlay;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "autoStartValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "bwModeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "clickState", "", "clicking", "", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "modeValue", "queued", "replayWhenKickedValue", "showGuiWhenFailedValue", "tag", "", "getTag", "()Ljava/lang/String;", "waitForLobby", "onEnable", "", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "queueAutoPlay", "delay", "", "runnable", "Lkotlin/Function0;", "LiquidBounce"})
@ModuleInfo(name = "AutoPlay", spacedName = "Auto Play", description = "Automatically move you to another game after finishing it.", category = ModuleCategory.MISC)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/misc/AutoPlay.class */
public final class AutoPlay extends Module {
    private int clickState;
    @NotNull
    private final ListValue modeValue = new ListValue("Server", new String[]{"RedeSky", "BlocksMC", "Minemora", "Hypixel", "Jartex", "MineFC/HeroMC_Bedwars"}, "RedeSky");
    @NotNull
    private final ListValue bwModeValue = new ListValue("Mode", new String[]{"SOLO", "4v4v4v4"}, "4v4v4v4", new AutoPlay$bwModeValue$1(this));
    @NotNull
    private final BoolValue autoStartValue = new BoolValue("AutoStartAtLobby", true, new AutoPlay$autoStartValue$1(this));
    @NotNull
    private final BoolValue replayWhenKickedValue = new BoolValue("ReplayWhenKicked", true, new AutoPlay$replayWhenKickedValue$1(this));
    @NotNull
    private final BoolValue showGuiWhenFailedValue = new BoolValue("ShowGuiWhenFailed", true, new AutoPlay$showGuiWhenFailedValue$1(this));
    @NotNull
    private final IntegerValue delayValue = new IntegerValue("JoinDelay", 3, 0, 7, " seconds");
    private boolean clicking;
    private boolean queued;
    private boolean waitForLobby;

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        this.clickState = 0;
        this.clicking = false;
        this.queued = false;
        this.waitForLobby = false;
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        S2FPacketSetSlot packet = event.getPacket();
        String lowerCase = this.modeValue.get().toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
        if (Intrinsics.areEqual(lowerCase, "redesky")) {
            if (this.clicking && ((packet instanceof C0EPacketClickWindow) || (packet instanceof C07PacketPlayerDigging))) {
                event.cancelEvent();
                return;
            } else if (this.clickState == 2 && (packet instanceof S2DPacketOpenWindow)) {
                event.cancelEvent();
            }
        } else if (Intrinsics.areEqual(lowerCase, "hypixel") && this.clickState == 1 && (packet instanceof S2DPacketOpenWindow)) {
            event.cancelEvent();
        }
        if (packet instanceof S2FPacketSetSlot) {
            final ItemStack item = packet.func_149174_e();
            if (item == null) {
                return;
            }
            final int windowId = packet.func_149175_c();
            final int slot = packet.func_149173_d();
            String itemName = item.func_77977_a();
            String displayName = item.func_82833_r();
            String lowerCase2 = this.modeValue.get().toLowerCase();
            Intrinsics.checkNotNullExpressionValue(lowerCase2, "this as java.lang.String).toLowerCase()");
            switch (lowerCase2.hashCode()) {
                case -664563300:
                    if (!lowerCase2.equals("blocksmc")) {
                        return;
                    }
                    break;
                case 1083223725:
                    if (lowerCase2.equals("redesky")) {
                        if (this.clickState == 0 && windowId == 0 && slot == 42) {
                            Intrinsics.checkNotNullExpressionValue(itemName, "itemName");
                            if (StringsKt.contains((CharSequence) itemName, (CharSequence) "paper", true)) {
                                Intrinsics.checkNotNullExpressionValue(displayName, "displayName");
                                if (StringsKt.contains((CharSequence) displayName, (CharSequence) "Jogar novamente", true)) {
                                    this.clickState = 1;
                                    this.clicking = true;
                                    queueAutoPlay$default(this, 0L, new AutoPlay$onPacket$1(item, this), 1, null);
                                    return;
                                }
                            }
                        }
                        if (this.clickState != 2 || windowId == 0 || slot != 11) {
                            return;
                        }
                        Intrinsics.checkNotNullExpressionValue(itemName, "itemName");
                        if (StringsKt.contains((CharSequence) itemName, (CharSequence) "enderPearl", true)) {
                            new Timer().schedule(new TimerTask() { // from class: net.ccbluex.liquidbounce.features.module.modules.misc.AutoPlay$onPacket$$inlined$schedule$1
                                @Override // java.util.TimerTask, java.lang.Runnable
                                public void run() {
                                    Minecraft minecraft;
                                    AutoPlay.this.clicking = false;
                                    AutoPlay.this.clickState = 0;
                                    minecraft = MinecraftInstance.f362mc;
                                    minecraft.func_147114_u().func_147297_a(new C0EPacketClickWindow(windowId, slot, 0, 0, item, (short) 1919));
                                }
                            }, 500L);
                            return;
                        }
                        return;
                    }
                    return;
                case 1381910549:
                    if (!lowerCase2.equals("hypixel")) {
                        return;
                    }
                    break;
                default:
                    return;
            }
            if (this.clickState == 0 && windowId == 0 && slot == 43) {
                Intrinsics.checkNotNullExpressionValue(itemName, "itemName");
                if (StringsKt.contains((CharSequence) itemName, (CharSequence) "paper", true)) {
                    queueAutoPlay$default(this, 0L, new AutoPlay$onPacket$3(item), 1, null);
                    this.clickState = 1;
                }
            }
            if (this.modeValue.equals("hypixel") && this.clickState == 1 && windowId != 0 && StringsKt.equals(itemName, "item.fireworks", true)) {
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C0EPacketClickWindow(windowId, slot, 0, 0, item, (short) 1919));
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C0DPacketCloseWindow(windowId));
            }
        } else if (packet instanceof S02PacketChat) {
            String text = ((S02PacketChat) packet).func_148915_c().func_150260_c();
            String lowerCase3 = this.modeValue.get().toLowerCase();
            Intrinsics.checkNotNullExpressionValue(lowerCase3, "this as java.lang.String).toLowerCase()");
            switch (lowerCase3.hashCode()) {
                case -1362756060:
                    if (lowerCase3.equals("minemora")) {
                        Intrinsics.checkNotNullExpressionValue(text, "text");
                        if (StringsKt.contains((CharSequence) text, (CharSequence) "Has click en alguna de las siguientes opciones", true)) {
                            queueAutoPlay$default(this, 0L, AutoPlay$onPacket$4.INSTANCE, 1, null);
                            return;
                        }
                        return;
                    }
                    return;
                case -1167184852:
                    if (lowerCase3.equals("jartex")) {
                        IChatComponent component = ((S02PacketChat) packet).func_148915_c();
                        Intrinsics.checkNotNullExpressionValue(text, "text");
                        if (StringsKt.contains((CharSequence) text, (CharSequence) "Click here to play again", true)) {
                            Iterable func_150253_a = component.func_150253_a();
                            Intrinsics.checkNotNullExpressionValue(func_150253_a, "component.siblings");
                            Iterable $this$forEach$iv = func_150253_a;
                            for (Object element$iv : $this$forEach$iv) {
                                IChatComponent sib = (IChatComponent) element$iv;
                                ClickEvent clickEvent = sib.func_150256_b().func_150235_h();
                                if (clickEvent != null && clickEvent.func_150669_a() == ClickEvent.Action.RUN_COMMAND) {
                                    String func_150668_b = clickEvent.func_150668_b();
                                    Intrinsics.checkNotNullExpressionValue(func_150668_b, "clickEvent.value");
                                    if (StringsKt.startsWith$default(func_150668_b, "/", false, 2, (Object) null)) {
                                        queueAutoPlay$default(this, 0L, new AutoPlay$onPacket$6$1(clickEvent), 1, null);
                                    }
                                }
                            }
                            return;
                        }
                        return;
                    }
                    return;
                case -664563300:
                    if (!lowerCase3.equals("blocksmc") || this.clickState != 1) {
                        return;
                    }
                    Intrinsics.checkNotNullExpressionValue(text, "text");
                    if (StringsKt.contains((CharSequence) text, (CharSequence) "Only VIP players can join full servers!", true)) {
                        LiquidBounce.INSTANCE.getHud().addNotification(new Notification("Join failed! trying again...", Notification.Type.WARNING, 3000L));
                        new Timer().schedule(new TimerTask() { // from class: net.ccbluex.liquidbounce.features.module.modules.misc.AutoPlay$onPacket$$inlined$schedule$2
                            @Override // java.util.TimerTask, java.lang.Runnable
                            public void run() {
                                Minecraft minecraft;
                                Minecraft minecraft2;
                                Minecraft minecraft3;
                                minecraft = MinecraftInstance.f362mc;
                                minecraft.func_147114_u().func_147297_a(new C09PacketHeldItemChange(7));
                                int i = 0;
                                while (i < 2) {
                                    i++;
                                    minecraft2 = MinecraftInstance.f362mc;
                                    NetHandlerPlayClient func_147114_u = minecraft2.func_147114_u();
                                    minecraft3 = MinecraftInstance.f362mc;
                                    func_147114_u.func_147297_a(new C08PacketPlayerBlockPlacement(minecraft3.field_71439_g.field_71071_by.func_70448_g()));
                                }
                            }
                        }, 1500L);
                        return;
                    }
                    return;
                case -633949188:
                    if (lowerCase3.equals("minefc/heromc_bedwars")) {
                        Intrinsics.checkNotNullExpressionValue(text, "text");
                        if (StringsKt.contains((CharSequence) text, (CharSequence) "Bạn đã bị loại!", false) || StringsKt.contains((CharSequence) text, (CharSequence) "đã thắng trò chơi", false)) {
                            MinecraftInstance.f362mc.field_71439_g.func_71165_d("/bw leave");
                            this.waitForLobby = true;
                        }
                        if (((this.waitForLobby || this.autoStartValue.get().booleanValue()) && StringsKt.contains((CharSequence) text, (CharSequence) "¡Hiển thị", false)) || (this.replayWhenKickedValue.get().booleanValue() && StringsKt.contains((CharSequence) text, (CharSequence) "[Anticheat] You have been kicked from the server!", false))) {
                            queueAutoPlay$default(this, 0L, new AutoPlay$onPacket$7(this), 1, null);
                            this.waitForLobby = false;
                        }
                        if (this.showGuiWhenFailedValue.get().booleanValue() && StringsKt.contains((CharSequence) text, (CharSequence) "giây", false) && StringsKt.contains((CharSequence) text, (CharSequence) "thất bại", false)) {
                            LiquidBounce.INSTANCE.getHud().addNotification(new Notification("Failed to join, showing GUI...", Notification.Type.ERROR, 1000L));
                            MinecraftInstance.f362mc.field_71439_g.func_71165_d(Intrinsics.stringPlus("/bw gui ", this.bwModeValue.get()));
                            return;
                        }
                        return;
                    }
                    return;
                case 1381910549:
                    if (lowerCase3.equals("hypixel")) {
                        IChatComponent func_148915_c = ((S02PacketChat) packet).func_148915_c();
                        Intrinsics.checkNotNullExpressionValue(func_148915_c, "packet.chatComponent");
                        onPacket$process(this, func_148915_c);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    private static final void onPacket$process(AutoPlay this$0, IChatComponent component) {
        ClickEvent func_150235_h = component.func_150256_b().func_150235_h();
        String value = func_150235_h == null ? null : func_150235_h.func_150668_b();
        if (value != null && StringsKt.startsWith(value, "/play", true)) {
            queueAutoPlay$default(this$0, 0L, new AutoPlay$onPacket$process$1(value), 1, null);
        }
        Iterable func_150253_a = component.func_150253_a();
        Intrinsics.checkNotNullExpressionValue(func_150253_a, "component.siblings");
        Iterable $this$forEach$iv = func_150253_a;
        for (Object element$iv : $this$forEach$iv) {
            IChatComponent it = (IChatComponent) element$iv;
            Intrinsics.checkNotNullExpressionValue(it, "it");
            onPacket$process(this$0, it);
        }
    }

    static /* synthetic */ void queueAutoPlay$default(AutoPlay autoPlay, long j, Functions functions, int i, Object obj) {
        if ((i & 1) != 0) {
            j = autoPlay.delayValue.get().intValue() * 1000;
        }
        autoPlay.queueAutoPlay(j, functions);
    }

    private final void queueAutoPlay(long delay, final Functions<Unit> functions) {
        if (this.queued) {
            return;
        }
        this.queued = true;
        AutoDisable.Companion.handleGameEnd();
        if (getState()) {
            new Timer().schedule(new TimerTask() { // from class: net.ccbluex.liquidbounce.features.module.modules.misc.AutoPlay$queueAutoPlay$$inlined$schedule$1
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    AutoPlay.this.queued = false;
                    if (AutoPlay.this.getState()) {
                        functions.invoke();
                    }
                }
            }, delay);
            LiquidBounce.INSTANCE.getHud().addNotification(new Notification("Sending you to next game in " + this.delayValue.get().intValue() + "s...", Notification.Type.INFO, this.delayValue.get().intValue() * 1000));
        }
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.clicking = false;
        this.clickState = 0;
        this.queued = false;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    @NotNull
    public String getTag() {
        return this.modeValue.get();
    }
}
