package net.ccbluex.liquidbounce.features.module.modules.render;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PosLookInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FreeCam.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0016H\u0016J\u0010\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u001aH\u0007J\u0010\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u001cH\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n��R\u0011\u0010\u0012\u001a\u00020\u0006¢\u0006\b\n��\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u001d"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/FreeCam;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "fakePlayer", "Lnet/minecraft/client/entity/EntityOtherPlayerMP;", "flyValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "lastOnGround", "", "noClipValue", "oldX", "", "oldY", "oldZ", "posLook", "Lnet/ccbluex/liquidbounce/utils/PosLookInstance;", "speedValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "undetectableValue", "getUndetectableValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "onDisable", "", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "FreeCam", spacedName = "Free Cam", description = "Allows you to move out of your body.", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/FreeCam.class */
public final class FreeCam extends Module {
    @Nullable
    private EntityOtherPlayerMP fakePlayer;
    private double oldX;
    private double oldY;
    private double oldZ;
    private boolean lastOnGround;
    @NotNull
    private final FloatValue speedValue = new FloatValue("Speed", 0.8f, 0.1f, 2.0f, "m");
    @NotNull
    private final BoolValue flyValue = new BoolValue("Fly", true);
    @NotNull
    private final BoolValue noClipValue = new BoolValue("NoClip", true);
    @NotNull
    private final BoolValue undetectableValue = new BoolValue("Undetectable", true);
    @NotNull
    private final PosLookInstance posLook = new PosLookInstance();

    @NotNull
    public final BoolValue getUndetectableValue() {
        return this.undetectableValue;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        if (MinecraftInstance.f362mc.field_71439_g == null) {
            return;
        }
        this.oldX = MinecraftInstance.f362mc.field_71439_g.field_70165_t;
        this.oldY = MinecraftInstance.f362mc.field_71439_g.field_70163_u;
        this.oldZ = MinecraftInstance.f362mc.field_71439_g.field_70161_v;
        this.lastOnGround = MinecraftInstance.f362mc.field_71439_g.field_70122_E;
        this.fakePlayer = new EntityOtherPlayerMP(MinecraftInstance.f362mc.field_71441_e, MinecraftInstance.f362mc.field_71439_g.func_146103_bH());
        EntityOtherPlayerMP entityOtherPlayerMP = this.fakePlayer;
        Intrinsics.checkNotNull(entityOtherPlayerMP);
        entityOtherPlayerMP.func_71049_a(MinecraftInstance.f362mc.field_71439_g, true);
        EntityOtherPlayerMP entityOtherPlayerMP2 = this.fakePlayer;
        Intrinsics.checkNotNull(entityOtherPlayerMP2);
        entityOtherPlayerMP2.field_70759_as = MinecraftInstance.f362mc.field_71439_g.field_70759_as;
        EntityOtherPlayerMP entityOtherPlayerMP3 = this.fakePlayer;
        Intrinsics.checkNotNull(entityOtherPlayerMP3);
        entityOtherPlayerMP3.func_82149_j(MinecraftInstance.f362mc.field_71439_g);
        WorldClient worldClient = MinecraftInstance.f362mc.field_71441_e;
        Entity entity = this.fakePlayer;
        Intrinsics.checkNotNull(entity);
        worldClient.func_73027_a(-1000, entity);
        if (this.noClipValue.get().booleanValue()) {
            MinecraftInstance.f362mc.field_71439_g.field_70145_X = true;
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        if (MinecraftInstance.f362mc.field_71439_g == null || this.fakePlayer == null) {
            return;
        }
        EntityPlayerSP entityPlayerSP = MinecraftInstance.f362mc.field_71439_g;
        EntityOtherPlayerMP entityOtherPlayerMP = this.fakePlayer;
        Intrinsics.checkNotNull(entityOtherPlayerMP);
        entityPlayerSP.field_70165_t = entityOtherPlayerMP.field_70165_t;
        EntityPlayerSP entityPlayerSP2 = MinecraftInstance.f362mc.field_71439_g;
        EntityOtherPlayerMP entityOtherPlayerMP2 = this.fakePlayer;
        Intrinsics.checkNotNull(entityOtherPlayerMP2);
        entityPlayerSP2.field_70163_u = entityOtherPlayerMP2.field_70163_u;
        EntityPlayerSP entityPlayerSP3 = MinecraftInstance.f362mc.field_71439_g;
        EntityOtherPlayerMP entityOtherPlayerMP3 = this.fakePlayer;
        Intrinsics.checkNotNull(entityOtherPlayerMP3);
        entityPlayerSP3.field_70161_v = entityOtherPlayerMP3.field_70161_v;
        WorldClient worldClient = MinecraftInstance.f362mc.field_71441_e;
        EntityOtherPlayerMP entityOtherPlayerMP4 = this.fakePlayer;
        Intrinsics.checkNotNull(entityOtherPlayerMP4);
        worldClient.func_73028_b(entityOtherPlayerMP4.func_145782_y());
        this.fakePlayer = null;
        MinecraftInstance.f362mc.field_71439_g.field_70159_w = 0.0d;
        MinecraftInstance.f362mc.field_71439_g.field_70181_x = 0.0d;
        MinecraftInstance.f362mc.field_71439_g.field_70179_y = 0.0d;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.noClipValue.get().booleanValue()) {
            MinecraftInstance.f362mc.field_71439_g.field_70145_X = true;
        }
        MinecraftInstance.f362mc.field_71439_g.field_70143_R = 0.0f;
        if (this.flyValue.get().booleanValue()) {
            MinecraftInstance.f362mc.field_71439_g.field_70181_x = 0.0d;
            MinecraftInstance.f362mc.field_71439_g.field_70159_w = 0.0d;
            MinecraftInstance.f362mc.field_71439_g.field_70179_y = 0.0d;
            if (MinecraftInstance.f362mc.field_71474_y.field_74314_A.func_151470_d()) {
                MinecraftInstance.f362mc.field_71439_g.field_70181_x += this.speedValue.get().doubleValue();
            }
            if (MinecraftInstance.f362mc.field_71474_y.field_74311_E.func_151470_d()) {
                MinecraftInstance.f362mc.field_71439_g.field_70181_x -= this.speedValue.get().doubleValue();
            }
            MovementUtils.strafe(this.speedValue.get().floatValue());
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.fakePlayer == null) {
            return;
        }
        C03PacketPlayer.C06PacketPlayerPosLook packet = event.getPacket();
        if (this.undetectableValue.get().booleanValue()) {
            if ((packet instanceof C03PacketPlayer.C04PacketPlayerPosition) || (packet instanceof C03PacketPlayer.C05PacketPlayerLook)) {
                event.cancelEvent();
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer(this.lastOnGround));
            } else if (packet instanceof C03PacketPlayer.C06PacketPlayerPosLook) {
                if (this.posLook.equalFlag(packet)) {
                    EntityOtherPlayerMP entityOtherPlayerMP = this.fakePlayer;
                    Intrinsics.checkNotNull(entityOtherPlayerMP);
                    entityOtherPlayerMP.func_70107_b(packet.field_149479_a, packet.field_149477_b, packet.field_149478_c);
                    EntityOtherPlayerMP entityOtherPlayerMP2 = this.fakePlayer;
                    Intrinsics.checkNotNull(entityOtherPlayerMP2);
                    entityOtherPlayerMP2.field_70122_E = packet.field_149474_g;
                    this.lastOnGround = packet.field_149474_g;
                    EntityOtherPlayerMP entityOtherPlayerMP3 = this.fakePlayer;
                    Intrinsics.checkNotNull(entityOtherPlayerMP3);
                    entityOtherPlayerMP3.field_70177_z = packet.field_149476_e;
                    EntityOtherPlayerMP entityOtherPlayerMP4 = this.fakePlayer;
                    Intrinsics.checkNotNull(entityOtherPlayerMP4);
                    entityOtherPlayerMP4.field_70125_A = packet.field_149473_f;
                    EntityOtherPlayerMP entityOtherPlayerMP5 = this.fakePlayer;
                    Intrinsics.checkNotNull(entityOtherPlayerMP5);
                    entityOtherPlayerMP5.field_70759_as = packet.field_149476_e;
                    this.posLook.reset();
                } else if (MinecraftInstance.f362mc.field_71439_g.field_175168_bP >= 20) {
                    EntityOtherPlayerMP entityOtherPlayerMP6 = this.fakePlayer;
                    Intrinsics.checkNotNull(entityOtherPlayerMP6);
                    packet.field_149479_a = entityOtherPlayerMP6.field_70165_t;
                    EntityOtherPlayerMP entityOtherPlayerMP7 = this.fakePlayer;
                    Intrinsics.checkNotNull(entityOtherPlayerMP7);
                    packet.field_149477_b = entityOtherPlayerMP7.field_70163_u;
                    EntityOtherPlayerMP entityOtherPlayerMP8 = this.fakePlayer;
                    Intrinsics.checkNotNull(entityOtherPlayerMP8);
                    packet.field_149478_c = entityOtherPlayerMP8.field_70161_v;
                    packet.field_149474_g = this.lastOnGround;
                    EntityOtherPlayerMP entityOtherPlayerMP9 = this.fakePlayer;
                    Intrinsics.checkNotNull(entityOtherPlayerMP9);
                    packet.field_149476_e = entityOtherPlayerMP9.field_70177_z;
                    EntityOtherPlayerMP entityOtherPlayerMP10 = this.fakePlayer;
                    Intrinsics.checkNotNull(entityOtherPlayerMP10);
                    packet.field_149473_f = entityOtherPlayerMP10.field_70125_A;
                } else {
                    event.cancelEvent();
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer(this.lastOnGround));
                }
            }
        } else if (packet instanceof C03PacketPlayer) {
            event.cancelEvent();
        }
        if (packet instanceof C0BPacketEntityAction) {
            event.cancelEvent();
        }
        if (packet instanceof S08PacketPlayerPosLook) {
            event.cancelEvent();
            this.posLook.set((S08PacketPlayerPosLook) packet);
        }
    }
}
