package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.util.Timer;
import java.util.TimerTask;
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
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TickTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.TextValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import org.jetbrains.annotations.NotNull;

/* compiled from: AutoKit.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000b\n��\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0012H\u0002J\b\u0010\u001a\u001a\u00020\u0018H\u0016J\u0010\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u001dH\u0007J\u0010\u0010\u001e\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u001fH\u0007J\u0010\u0010 \u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020!H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n��R\u0014\u0010\u0011\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n��¨\u0006\""}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/AutoKit;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "clickStage", "", "debugValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "delayTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "editMode", "expectSlot", "kitNameValue", "Lnet/ccbluex/liquidbounce/value/TextValue;", "kitTimeOutValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "listening", "", "tag", "", "getTag", "()Ljava/lang/String;", "timeoutTimer", "Lnet/ccbluex/liquidbounce/utils/timer/TickTimer;", "debug", "", "s", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "LiquidBounce"})
@ModuleInfo(name = "AutoKit", spacedName = "Auto Kit", description = "Automatically selects kits for you in BlocksMC Skywars.", category = ModuleCategory.MISC)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/misc/AutoKit.class */
public final class AutoKit extends Module {
    private int clickStage;
    private boolean listening;
    @NotNull
    private final TextValue kitNameValue = new TextValue("Kit-Name", "Armorer");
    @NotNull
    private final IntegerValue kitTimeOutValue = new IntegerValue("Timeout-After", 40, 40, 100, " tick");
    @NotNull
    private final BoolValue editMode = new BoolValue() { // from class: net.ccbluex.liquidbounce.features.module.modules.misc.AutoKit$editMode$1
        @Override // net.ccbluex.liquidbounce.value.Value
        public /* bridge */ /* synthetic */ void onChanged(Boolean bool, Boolean bool2) {
            onChanged(bool.booleanValue(), bool2.booleanValue());
        }

        protected void onChanged(boolean oldValue, boolean newValue) {
            if (newValue) {
                LiquidBounce.INSTANCE.getHud().addNotification(new Notification("Change default kit by right clicking the kit selector and select.", Notification.Type.INFO));
            }
        }
    };
    @NotNull
    private final BoolValue debugValue = new BoolValue("Debug", false);
    private int expectSlot = -1;
    @NotNull
    private TickTimer timeoutTimer = new TickTimer();
    @NotNull
    private MSTimer delayTimer = new MSTimer();

    public final void debug(String s) {
        if (this.debugValue.get().booleanValue()) {
            ClientUtils.displayChatMessage(Intrinsics.stringPlus("§7[§4§lAuto Kit§7] §r", s));
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        this.clickStage = 0;
        this.listening = false;
        this.expectSlot = -1;
        this.timeoutTimer.reset();
        this.delayTimer.reset();
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.clickStage == 1) {
            if (!this.delayTimer.hasTimePassed(1000L)) {
                return;
            }
            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C09PacketHeldItemChange(this.expectSlot - 36));
            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C08PacketPlayerBlockPlacement(MinecraftInstance.f362mc.field_71439_g.field_71069_bz.func_75139_a(this.expectSlot).func_75211_c()));
            this.clickStage = 2;
            this.delayTimer.reset();
            debug("clicked kit selector");
        } else if (!this.listening) {
            this.delayTimer.reset();
        }
        if (this.clickStage == 2) {
            this.timeoutTimer.update();
            if (this.timeoutTimer.hasTimePassed(this.kitTimeOutValue.get().intValue())) {
                this.clickStage = 0;
                this.listening = false;
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C0DPacketCloseWindow());
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C09PacketHeldItemChange(MinecraftInstance.f362mc.field_71439_g.field_71071_by.field_70461_c));
                LiquidBounce.INSTANCE.getHud().addNotification(new Notification("Kit checker timed out. Please use the right kit name.", Notification.Type.ERROR));
                debug("can't find any kit with that name");
                return;
            }
            return;
        }
        this.timeoutTimer.reset();
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        S2FPacketSetSlot packet = event.getPacket();
        if (!this.editMode.get().booleanValue() && this.listening && (packet instanceof S2DPacketOpenWindow)) {
            event.cancelEvent();
            debug("listening so cancel open window packet");
        } else if ((packet instanceof C0DPacketCloseWindow) && this.editMode.get().booleanValue()) {
            this.editMode.set(false);
            LiquidBounce.INSTANCE.getHud().addNotification(new Notification("Edit mode aborted.", Notification.Type.INFO));
            debug("abort edit mode");
        } else {
            if (packet instanceof S2FPacketSetSlot) {
                final ItemStack item = packet.func_149174_e();
                if (item == null) {
                    return;
                }
                final int windowId = packet.func_149175_c();
                final int slot = packet.func_149173_d();
                String itemName = item.func_77977_a();
                String displayName = item.func_82833_r();
                if (this.clickStage == 0 && windowId == 0 && slot >= 36 && slot <= 44) {
                    Intrinsics.checkNotNullExpressionValue(itemName, "itemName");
                    if (StringsKt.contains((CharSequence) itemName, (CharSequence) "bow", true)) {
                        Intrinsics.checkNotNullExpressionValue(displayName, "displayName");
                        if (StringsKt.contains((CharSequence) displayName, (CharSequence) "kit selector", true)) {
                            if (this.editMode.get().booleanValue()) {
                                this.listening = true;
                                debug("found item, listening to kit selection cuz of edit mode");
                                return;
                            }
                            this.listening = true;
                            this.clickStage = 1;
                            this.expectSlot = slot;
                            debug("found item, sent trigger");
                            return;
                        }
                    }
                }
                if (this.clickStage == 2) {
                    Intrinsics.checkNotNullExpressionValue(displayName, "displayName");
                    if (StringsKt.contains((CharSequence) displayName, (CharSequence) this.kitNameValue.get(), true)) {
                        this.timeoutTimer.reset();
                        this.clickStage = 3;
                        debug("detected kit selection");
                        new Timer().schedule(new TimerTask() { // from class: net.ccbluex.liquidbounce.features.module.modules.misc.AutoKit$onPacket$$inlined$schedule$1
                            @Override // java.util.TimerTask, java.lang.Runnable
                            public void run() {
                                Minecraft minecraft;
                                Minecraft minecraft2;
                                Minecraft minecraft3;
                                Minecraft minecraft4;
                                minecraft = MinecraftInstance.f362mc;
                                minecraft.func_147114_u().func_147297_a(new C0EPacketClickWindow(windowId, slot, 0, 0, item, (short) 1919));
                                minecraft2 = MinecraftInstance.f362mc;
                                minecraft2.func_147114_u().func_147297_a(new C0DPacketCloseWindow(windowId));
                                minecraft3 = MinecraftInstance.f362mc;
                                NetHandlerPlayClient func_147114_u = minecraft3.func_147114_u();
                                minecraft4 = MinecraftInstance.f362mc;
                                func_147114_u.func_147297_a(new C09PacketHeldItemChange(minecraft4.field_71439_g.field_71071_by.field_70461_c));
                                this.debug("selected");
                            }
                        }, 250L);
                        return;
                    }
                }
            }
            if (packet instanceof S02PacketChat) {
                String text = ((S02PacketChat) packet).func_148915_c().func_150260_c();
                Intrinsics.checkNotNullExpressionValue(text, "text");
                if (StringsKt.contains((CharSequence) text, (CharSequence) "kit has been selected", true)) {
                    if (this.editMode.get().booleanValue()) {
                        String kitName = StringsKt.replace$default(text, " kit has been selected!", "", false, 4, (Object) null);
                        this.kitNameValue.set(kitName);
                        this.editMode.set(false);
                        this.clickStage = 0;
                        this.listening = false;
                        LiquidBounce.INSTANCE.getHud().addNotification(new Notification("Successfully detected and added " + kitName + " kit.", Notification.Type.SUCCESS));
                        debug("finished detecting kit");
                        return;
                    }
                    this.listening = false;
                    event.cancelEvent();
                    LiquidBounce.INSTANCE.getHud().addNotification(new Notification("Successfully selected " + this.kitNameValue.get() + " kit.", Notification.Type.SUCCESS));
                    debug("finished");
                }
            }
        }
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.clickStage = 0;
        this.listening = false;
        this.expectSlot = -1;
        this.timeoutTimer.reset();
        this.delayTimer.reset();
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    @NotNull
    public String getTag() {
        return (!this.editMode.get().booleanValue() || !this.listening) ? this.kitNameValue.get() : "Listening...";
    }
}
