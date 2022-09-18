package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.SlowDownEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: NoSlow.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0007\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\b\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010/\u001a\u0002002\b\u00101\u001a\u0004\u0018\u0001022\u0006\u00103\u001a\u00020\u0017H\u0002J\b\u00104\u001a\u000205H\u0016J\b\u00106\u001a\u000205H\u0016J\u0010\u00107\u001a\u0002052\u0006\u00108\u001a\u000209H\u0007J\u0010\u0010:\u001a\u0002052\u0006\u00108\u001a\u00020;H\u0007J\u0010\u0010<\u001a\u0002052\u0006\u00108\u001a\u00020=H\u0007JB\u0010>\u001a\u0002052\u0006\u00108\u001a\u0002092\u0006\u0010?\u001a\u00020\u00172\u0006\u0010@\u001a\u00020\u00172\u0006\u0010A\u001a\u00020\u00172\u0006\u0010B\u001a\u00020&2\u0006\u0010C\u001a\u00020\u00172\b\b\u0002\u0010D\u001a\u00020\u0017H\u0002R\u001a\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\n\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000e\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000f\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0012\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0013\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0014\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0015\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0018\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u001b\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u001c\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n��R\u0011\u0010\u001d\u001a\u00020\r¢\u0006\b\n��\u001a\u0004\b\u001e\u0010\u001fR\u000e\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\"\u001a\u00020#X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010$\u001a\u00020!X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010%\u001a\u00020&X\u0082\u000e¢\u0006\u0002\n��R\u0011\u0010'\u001a\u00020\r¢\u0006\b\n��\u001a\u0004\b(\u0010\u001fR\u0016\u0010)\u001a\u0004\u0018\u00010*8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b+\u0010,R\u000e\u0010-\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010.\u001a\u00020#X\u0082\u0004¢\u0006\u0002\n��¨\u0006E"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/NoSlow;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "blinkPackets", "", "Lnet/minecraft/network/Packet;", "Lnet/minecraft/network/play/INetHandlerPlayServer;", "blockForwardMultiplier", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "blockStrafeMultiplier", "bowForwardMultiplier", "bowStrafeMultiplier", "ciucValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "consumeForwardMultiplier", "consumeStrafeMultiplier", "customDelayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "customOnGround", "customPlace", "customRelease", "debugValue", "fasterDelay", "", "lastOnGround", "lastX", "", "lastY", "lastZ", "liquidPushValue", "getLiquidPushValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "msTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "packetTriggerValue", "placeDelay", "", "soulsandValue", "getSoulsandValue", "tag", "", "getTag", "()Ljava/lang/String;", "testValue", "timer", "getMultiplier", "", "item", "Lnet/minecraft/item/Item;", "isForward", "onDisable", "", "onEnable", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onSlowDown", "Lnet/ccbluex/liquidbounce/event/SlowDownEvent;", "sendPacket", "sendC07", "sendC08", "delay", "delayValue", "onGround", "watchDog", "LiquidBounce"})
@ModuleInfo(name = "NoSlow", spacedName = "No Slow", description = "Prevent you from getting slowed down by items (swords, foods, etc.) and liquids.", category = ModuleCategory.MOVEMENT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/NoSlow.class */
public final class NoSlow extends Module {
    private double lastX;
    private double lastY;
    private double lastZ;
    private boolean lastOnGround;
    private boolean fasterDelay;
    private long placeDelay;
    @NotNull
    private final MSTimer msTimer = new MSTimer();
    @NotNull
    private final ListValue modeValue = new ListValue("PacketMode", new String[]{"Vanilla", "Watchdog", "OldWatchdog", "OldHypixel", "Blink", "Experimental", "NCP", "AAC", "AAC5", "AAC4", "Matrix", "Spartan", "Vulcan", "Custom"}, "Vanilla");
    @NotNull
    private final FloatValue blockForwardMultiplier = new FloatValue("BlockForwardMultiplier", 1.0f, 0.2f, 1.0f, "x");
    @NotNull
    private final FloatValue blockStrafeMultiplier = new FloatValue("BlockStrafeMultiplier", 1.0f, 0.2f, 1.0f, "x");
    @NotNull
    private final FloatValue consumeForwardMultiplier = new FloatValue("ConsumeForwardMultiplier", 1.0f, 0.2f, 1.0f, "x");
    @NotNull
    private final FloatValue consumeStrafeMultiplier = new FloatValue("ConsumeStrafeMultiplier", 1.0f, 0.2f, 1.0f, "x");
    @NotNull
    private final FloatValue bowForwardMultiplier = new FloatValue("BowForwardMultiplier", 1.0f, 0.2f, 1.0f, "x");
    @NotNull
    private final FloatValue bowStrafeMultiplier = new FloatValue("BowStrafeMultiplier", 1.0f, 0.2f, 1.0f, "x");
    @NotNull
    private final BoolValue customRelease = new BoolValue("CustomReleasePacket", false, new NoSlow$customRelease$1(this));
    @NotNull
    private final BoolValue customPlace = new BoolValue("CustomPlacePacket", false, new NoSlow$customPlace$1(this));
    @NotNull
    private final BoolValue customOnGround = new BoolValue("CustomOnGround", false, new NoSlow$customOnGround$1(this));
    @NotNull
    private final IntegerValue customDelayValue = new IntegerValue("CustomDelay", 60, 0, 1000, "ms", new NoSlow$customDelayValue$1(this));
    @NotNull
    private final BoolValue testValue = new BoolValue("SendPacket", false, new NoSlow$testValue$1(this));
    @NotNull
    private final BoolValue ciucValue = new BoolValue("CheckInUseCount", true, new NoSlow$ciucValue$1(this));
    @NotNull
    private final ListValue packetTriggerValue = new ListValue("PacketTrigger", new String[]{"PreRelease", "PostRelease"}, "PostRelease", new NoSlow$packetTriggerValue$1(this));
    @NotNull
    private final BoolValue debugValue = new BoolValue("Debug", false, new NoSlow$debugValue$1(this));
    @NotNull
    private final BoolValue soulsandValue = new BoolValue("Soulsand", true);
    @NotNull
    private final BoolValue liquidPushValue = new BoolValue("LiquidPush", true);
    @NotNull
    private final List<Packet<INetHandlerPlayServer>> blinkPackets = new ArrayList();
    @NotNull
    private final MSTimer timer = new MSTimer();

    @NotNull
    public final BoolValue getSoulsandValue() {
        return this.soulsandValue;
    }

    @NotNull
    public final BoolValue getLiquidPushValue() {
        return this.liquidPushValue;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        this.blinkPackets.clear();
        this.msTimer.reset();
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        Iterable $this$forEach$iv = this.blinkPackets;
        for (Object element$iv : $this$forEach$iv) {
            Packet it = (Packet) element$iv;
            PacketUtils.sendPacketNoEvent(it);
        }
        this.blinkPackets.clear();
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    @Nullable
    public String getTag() {
        return this.modeValue.get();
    }

    static /* synthetic */ void sendPacket$default(NoSlow noSlow, MotionEvent motionEvent, boolean z, boolean z2, boolean z3, long j, boolean z4, boolean z5, int i, Object obj) {
        if ((i & 64) != 0) {
            z5 = false;
        }
        noSlow.sendPacket(motionEvent, z, z2, z3, j, z4, z5);
    }

    private final void sendPacket(MotionEvent event, boolean sendC07, boolean sendC08, boolean delay, long delayValue, boolean onGround, boolean watchDog) {
        Packet c07PacketPlayerDigging = new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-1, -1, -1), EnumFacing.DOWN);
        Packet c08PacketPlayerBlockPlacement = new C08PacketPlayerBlockPlacement(MinecraftInstance.f362mc.field_71439_g.field_71071_by.func_70448_g());
        Packet c08PacketPlayerBlockPlacement2 = new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, MinecraftInstance.f362mc.field_71439_g.field_71071_by.func_70448_g(), 0.0f, 0.0f, 0.0f);
        if (onGround && !MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
            return;
        }
        if (sendC07 && event.getEventState() == EventState.PRE) {
            if (delay && this.msTimer.hasTimePassed(delayValue)) {
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(c07PacketPlayerDigging);
            } else if (!delay) {
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(c07PacketPlayerDigging);
            }
        }
        if (sendC08 && event.getEventState() == EventState.POST) {
            if (delay && this.msTimer.hasTimePassed(delayValue) && !watchDog) {
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(c08PacketPlayerBlockPlacement);
                this.msTimer.reset();
            } else if (!delay && !watchDog) {
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(c08PacketPlayerBlockPlacement);
            } else if (watchDog) {
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(c08PacketPlayerBlockPlacement2);
            }
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet packet = event.getPacket();
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(KillAura.class);
        Intrinsics.checkNotNull(module);
        KillAura killAura = (KillAura) module;
        if (StringsKt.equals(this.modeValue.get(), "watchdog", true) && (packet instanceof S30PacketWindowItems) && (MinecraftInstance.f362mc.field_71439_g.func_71039_bw() || MinecraftInstance.f362mc.field_71439_g.func_70632_aY())) {
            event.cancelEvent();
            if (this.debugValue.get().booleanValue()) {
                ClientUtils.displayChatMessage("detected reset item packet");
            }
        }
        if (StringsKt.equals(this.modeValue.get(), "blink", true)) {
            if ((!killAura.getState() || !killAura.getBlockingStatus()) && MinecraftInstance.f362mc.field_71439_g.func_71011_bu() != null && MinecraftInstance.f362mc.field_71439_g.func_71011_bu().func_77973_b() != null) {
                Item item = MinecraftInstance.f362mc.field_71439_g.func_71011_bu().func_77973_b();
                if (!MinecraftInstance.f362mc.field_71439_g.func_71039_bw()) {
                    return;
                }
                if (!(item instanceof ItemFood) && !(item instanceof ItemBucketMilk) && !(item instanceof ItemPotion)) {
                    return;
                }
                if (!this.ciucValue.get().booleanValue() || MinecraftInstance.f362mc.field_71439_g.field_71072_f >= 1) {
                    if ((packet instanceof C03PacketPlayer.C04PacketPlayerPosition) || (packet instanceof C03PacketPlayer.C06PacketPlayerPosLook)) {
                        if (MinecraftInstance.f362mc.field_71439_g.field_175168_bP >= 20 && StringsKt.equals(this.packetTriggerValue.get(), "postrelease", true)) {
                            ((C03PacketPlayer) packet).field_149479_a = this.lastX;
                            ((C03PacketPlayer) packet).field_149477_b = this.lastY;
                            ((C03PacketPlayer) packet).field_149478_c = this.lastZ;
                            ((C03PacketPlayer) packet).field_149474_g = this.lastOnGround;
                            if (this.debugValue.get().booleanValue()) {
                                ClientUtils.displayChatMessage("pos update reached 20");
                            }
                        } else {
                            event.cancelEvent();
                            if (StringsKt.equals(this.packetTriggerValue.get(), "postrelease", true)) {
                                PacketUtils.sendPacketNoEvent(new C03PacketPlayer(this.lastOnGround));
                            }
                            this.blinkPackets.add(packet);
                            if (this.debugValue.get().booleanValue()) {
                                ClientUtils.displayChatMessage(Intrinsics.stringPlus("packet player (movement) added at ", Integer.valueOf(this.blinkPackets.size() - 1)));
                            }
                        }
                    } else if (packet instanceof C03PacketPlayer.C05PacketPlayerLook) {
                        event.cancelEvent();
                        if (StringsKt.equals(this.packetTriggerValue.get(), "postrelease", true)) {
                            PacketUtils.sendPacketNoEvent(new C03PacketPlayer(this.lastOnGround));
                        }
                        this.blinkPackets.add(packet);
                        if (this.debugValue.get().booleanValue()) {
                            ClientUtils.displayChatMessage(Intrinsics.stringPlus("packet player (rotation) added at ", Integer.valueOf(this.blinkPackets.size() - 1)));
                        }
                    } else if ((packet instanceof C03PacketPlayer) && (StringsKt.equals(this.packetTriggerValue.get(), "prerelease", true) || ((C03PacketPlayer) packet).field_149474_g != this.lastOnGround)) {
                        event.cancelEvent();
                        this.blinkPackets.add(packet);
                        if (this.debugValue.get().booleanValue()) {
                            ClientUtils.displayChatMessage(Intrinsics.stringPlus("packet player (idle) added at ", Integer.valueOf(this.blinkPackets.size() - 1)));
                        }
                    }
                    if (packet instanceof C0BPacketEntityAction) {
                        event.cancelEvent();
                        this.blinkPackets.add(packet);
                        if (this.debugValue.get().booleanValue()) {
                            ClientUtils.displayChatMessage(Intrinsics.stringPlus("packet action added at ", Integer.valueOf(this.blinkPackets.size() - 1)));
                        }
                    }
                    if ((packet instanceof C07PacketPlayerDigging) && StringsKt.equals(this.packetTriggerValue.get(), "prerelease", true) && this.blinkPackets.size() > 0) {
                        Iterable $this$forEach$iv = this.blinkPackets;
                        for (Object element$iv : $this$forEach$iv) {
                            Packet it = (Packet) element$iv;
                            PacketUtils.sendPacketNoEvent(it);
                        }
                        if (this.debugValue.get().booleanValue()) {
                            ClientUtils.displayChatMessage("sent " + this.blinkPackets.size() + " packets.");
                        }
                        this.blinkPackets.clear();
                    }
                }
            }
        }
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!MovementUtils.isMoving() && !StringsKt.equals(this.modeValue.get(), "blink", true)) {
            return;
        }
        MinecraftInstance.f362mc.field_71439_g.func_70694_bm();
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(KillAura.class);
        Intrinsics.checkNotNull(module);
        KillAura killAura = (KillAura) module;
        String lowerCase = this.modeValue.get().toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -404562712:
                if (lowerCase.equals("experimental")) {
                    if ((MinecraftInstance.f362mc.field_71439_g.func_71039_bw() || MinecraftInstance.f362mc.field_71439_g.func_70632_aY()) && this.timer.hasTimePassed(this.placeDelay)) {
                        MinecraftInstance.f362mc.field_71442_b.func_78750_j();
                        MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, EnumFacing.DOWN));
                        if (event.getEventState() == EventState.POST) {
                            this.placeDelay = 200L;
                            if (this.fasterDelay) {
                                this.placeDelay = 100L;
                                this.fasterDelay = false;
                            } else {
                                this.fasterDelay = true;
                            }
                            this.timer.reset();
                            return;
                        }
                        return;
                    }
                    return;
                }
                break;
            case 2986066:
                if (lowerCase.equals("aac5")) {
                    if (event.getEventState() != EventState.POST) {
                        return;
                    }
                    if (MinecraftInstance.f362mc.field_71439_g.func_71039_bw() || MinecraftInstance.f362mc.field_71439_g.func_70632_aY() || killAura.getBlockingStatus()) {
                        MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, MinecraftInstance.f362mc.field_71439_g.field_71071_by.func_70448_g(), 0.0f, 0.0f, 0.0f));
                        return;
                    }
                    return;
                }
                break;
            case 93826908:
                if (lowerCase.equals("blink")) {
                    if (event.getEventState() == EventState.PRE && !MinecraftInstance.f362mc.field_71439_g.func_71039_bw() && !MinecraftInstance.f362mc.field_71439_g.func_70632_aY()) {
                        this.lastX = event.getX();
                        this.lastY = event.getY();
                        this.lastZ = event.getZ();
                        this.lastOnGround = event.getOnGround();
                        if (this.blinkPackets.size() > 0 && StringsKt.equals(this.packetTriggerValue.get(), "postrelease", true)) {
                            Iterable $this$forEach$iv = this.blinkPackets;
                            for (Object element$iv : $this$forEach$iv) {
                                Packet it = (Packet) element$iv;
                                PacketUtils.sendPacketNoEvent(it);
                            }
                            if (this.debugValue.get().booleanValue()) {
                                ClientUtils.displayChatMessage("sent " + this.blinkPackets.size() + " packets.");
                            }
                            this.blinkPackets.clear();
                            return;
                        }
                        return;
                    }
                    return;
                }
                break;
            case 545151501:
                if (lowerCase.equals("watchdog")) {
                    if (this.testValue.get().booleanValue()) {
                        if ((!killAura.getState() || !killAura.getBlockingStatus()) && event.getEventState() == EventState.PRE && MinecraftInstance.f362mc.field_71439_g.func_71011_bu() != null && MinecraftInstance.f362mc.field_71439_g.func_71011_bu().func_77973_b() != null) {
                            Item item = MinecraftInstance.f362mc.field_71439_g.func_71011_bu().func_77973_b();
                            if (!MinecraftInstance.f362mc.field_71439_g.func_71039_bw()) {
                                return;
                            }
                            if (((item instanceof ItemFood) || (item instanceof ItemBucketMilk) || (item instanceof ItemPotion)) && MinecraftInstance.f362mc.field_71439_g.func_71052_bv() >= 1) {
                                PacketUtils.sendPacketNoEvent(new C09PacketHeldItemChange(MinecraftInstance.f362mc.field_71439_g.field_71071_by.field_70461_c));
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    return;
                }
                break;
        }
        if (!MinecraftInstance.f362mc.field_71439_g.func_70632_aY() && !killAura.getBlockingStatus()) {
            return;
        }
        String lowerCase2 = this.modeValue.get().toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase2, "this as java.lang.String).toLowerCase()");
        switch (lowerCase2.hashCode()) {
            case -1453395660:
                if (lowerCase2.equals("oldwatchdog")) {
                    if (MinecraftInstance.f362mc.field_71439_g.field_70173_aa % 2 == 0) {
                        sendPacket$default(this, event, true, false, false, 50L, true, false, 64, null);
                        return;
                    } else {
                        sendPacket(event, false, true, false, 0L, true, true);
                        return;
                    }
                }
                return;
            case -1349088399:
                if (lowerCase2.equals("custom")) {
                    sendPacket$default(this, event, this.customRelease.get().booleanValue(), this.customPlace.get().booleanValue(), this.customDelayValue.get().intValue() > 0, this.customDelayValue.get().intValue(), this.customOnGround.get().booleanValue(), false, 64, null);
                    return;
                }
                return;
            case 96323:
                if (lowerCase2.equals("aac")) {
                    if (MinecraftInstance.f362mc.field_71439_g.field_70173_aa % 3 == 0) {
                        sendPacket$default(this, event, true, false, false, 0L, false, false, 64, null);
                        return;
                    } else {
                        sendPacket$default(this, event, false, true, false, 0L, false, false, 64, null);
                        return;
                    }
                }
                return;
            case 108891:
                if (lowerCase2.equals("ncp")) {
                    sendPacket$default(this, event, true, true, false, 0L, false, false, 64, null);
                    return;
                }
                return;
            case 1594535950:
                if (lowerCase2.equals("oldhypixel")) {
                    if (event.getEventState() == EventState.PRE) {
                        MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-1, -1, -1), EnumFacing.DOWN));
                        return;
                    } else {
                        MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, (ItemStack) null, 0.0f, 0.0f, 0.0f));
                        return;
                    }
                }
                return;
            default:
                return;
        }
    }

    @EventTarget
    public final void onSlowDown(@NotNull SlowDownEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        ItemStack func_70694_bm = MinecraftInstance.f362mc.field_71439_g.func_70694_bm();
        Item heldItem = func_70694_bm == null ? null : func_70694_bm.func_77973_b();
        event.setForward(getMultiplier(heldItem, true));
        event.setStrafe(getMultiplier(heldItem, false));
    }

    private final float getMultiplier(Item item, boolean isForward) {
        if (item instanceof ItemFood ? true : item instanceof ItemPotion ? true : item instanceof ItemBucketMilk) {
            return isForward ? this.consumeForwardMultiplier.get().floatValue() : this.consumeStrafeMultiplier.get().floatValue();
        } else if (item instanceof ItemSword) {
            return isForward ? this.blockForwardMultiplier.get().floatValue() : this.blockStrafeMultiplier.get().floatValue();
        } else if (!(item instanceof ItemBow)) {
            return 0.2f;
        } else {
            return isForward ? this.bowForwardMultiplier.get().floatValue() : this.bowStrafeMultiplier.get().floatValue();
        }
    }
}
