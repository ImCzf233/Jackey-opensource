package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.potion.Potion;
import org.jetbrains.annotations.NotNull;

/* compiled from: Sprint.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0007J\u0010\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u001aH\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u000e\u0010\u0006R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n��R\u0011\u0010\u0011\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u0012\u0010\u0006R\u0011\u0010\u0013\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u0014\u0010\u0006¨\u0006\u001b"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/Sprint;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "allDirectionsValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getAllDirectionsValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "blindnessValue", "getBlindnessValue", "checkServerSide", "getCheckServerSide", "checkServerSideGround", "getCheckServerSideGround", "foodValue", "getFoodValue", "modified", "", "moveDirPatchValue", "getMoveDirPatchValue", "noPacketPatchValue", "getNoPacketPatchValue", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "Sprint", description = "Automatically sprints all the time.", category = ModuleCategory.MOVEMENT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/Sprint.class */
public final class Sprint extends Module {
    @NotNull
    private final BoolValue allDirectionsValue = new BoolValue("AllDirections", true);
    @NotNull
    private final BoolValue noPacketPatchValue = new BoolValue("AllDir-NoPacketsPatch", true, new Sprint$noPacketPatchValue$1(this));
    @NotNull
    private final BoolValue moveDirPatchValue = new BoolValue("AllDir-MoveDirPatch", false, new Sprint$moveDirPatchValue$1(this));
    @NotNull
    private final BoolValue blindnessValue = new BoolValue("Blindness", true);
    @NotNull
    private final BoolValue foodValue = new BoolValue("Food", true);
    @NotNull
    private final BoolValue checkServerSide = new BoolValue("CheckServerSide", false);
    @NotNull
    private final BoolValue checkServerSideGround = new BoolValue("CheckServerSideOnlyGround", false);
    private boolean modified;

    @NotNull
    public final BoolValue getAllDirectionsValue() {
        return this.allDirectionsValue;
    }

    @NotNull
    public final BoolValue getNoPacketPatchValue() {
        return this.noPacketPatchValue;
    }

    @NotNull
    public final BoolValue getMoveDirPatchValue() {
        return this.moveDirPatchValue;
    }

    @NotNull
    public final BoolValue getBlindnessValue() {
        return this.blindnessValue;
    }

    @NotNull
    public final BoolValue getFoodValue() {
        return this.foodValue;
    }

    @NotNull
    public final BoolValue getCheckServerSide() {
        return this.checkServerSide;
    }

    @NotNull
    public final BoolValue getCheckServerSideGround() {
        return this.checkServerSideGround;
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        C0BPacketEntityAction packet = event.getPacket();
        if (!this.allDirectionsValue.get().booleanValue() || !this.noPacketPatchValue.get().booleanValue() || !(packet instanceof C0BPacketEntityAction)) {
            return;
        }
        if (packet.func_180764_b() == C0BPacketEntityAction.Action.STOP_SPRINTING || packet.func_180764_b() == C0BPacketEntityAction.Action.START_SPRINTING) {
            event.cancelEvent();
        }
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(KillAura.class);
        Intrinsics.checkNotNull(module);
        KillAura killAura = (KillAura) module;
        if (!MovementUtils.isMoving() || MinecraftInstance.f362mc.field_71439_g.func_70093_af() || ((this.blindnessValue.get().booleanValue() && MinecraftInstance.f362mc.field_71439_g.func_70644_a(Potion.field_76440_q)) || ((this.foodValue.get().booleanValue() && MinecraftInstance.f362mc.field_71439_g.func_71024_bL().func_75116_a() <= 6.0f && !MinecraftInstance.f362mc.field_71439_g.field_71075_bZ.field_75101_c) || (this.checkServerSide.get().booleanValue() && ((MinecraftInstance.f362mc.field_71439_g.field_70122_E || !this.checkServerSideGround.get().booleanValue()) && !this.allDirectionsValue.get().booleanValue() && RotationUtils.targetRotation != null && RotationUtils.getRotationDifference(new Rotation(MinecraftInstance.f362mc.field_71439_g.field_70177_z, MinecraftInstance.f362mc.field_71439_g.field_70125_A)) > 30.0d))))) {
            MinecraftInstance.f362mc.field_71439_g.func_70031_b(false);
            return;
        }
        if (this.allDirectionsValue.get().booleanValue() || MinecraftInstance.f362mc.field_71439_g.field_71158_b.field_78900_b >= 0.8f) {
            MinecraftInstance.f362mc.field_71439_g.func_70031_b(true);
        }
        if (this.allDirectionsValue.get().booleanValue() && this.moveDirPatchValue.get().booleanValue() && killAura.getTarget() == null) {
            RotationUtils.setTargetRotation(new Rotation(MovementUtils.getRawDirection(), MinecraftInstance.f362mc.field_71439_g.field_70125_A));
        }
    }
}
