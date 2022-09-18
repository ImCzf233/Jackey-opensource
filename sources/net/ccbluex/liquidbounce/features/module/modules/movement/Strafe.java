package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.minecraft.client.entity.EntityPlayerSP;
import org.jetbrains.annotations.NotNull;

/* compiled from: Strafe.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n��\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\f\u001a\u00020\rH\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0007J\u0010\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0014H\u0007J\u0010\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0016H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��¨\u0006\u0017"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/Strafe;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "allDirectionsJumpValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "jump", "", "noMoveStopValue", "onGroundStrafeValue", "strengthValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "wasDown", "getMoveYaw", "", "onEnable", "", "onJump", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onStrafe", "Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "Strafe", description = "Allows you to freely move in mid air.", category = ModuleCategory.MOVEMENT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/Strafe.class */
public final class Strafe extends Module {
    @NotNull
    private FloatValue strengthValue = new FloatValue("Strength", 0.5f, 0.0f, 1.0f, "x");
    @NotNull
    private BoolValue noMoveStopValue = new BoolValue("NoMoveStop", false);
    @NotNull
    private BoolValue onGroundStrafeValue = new BoolValue("OnGroundStrafe", false);
    @NotNull
    private BoolValue allDirectionsJumpValue = new BoolValue("AllDirectionsJump", false);
    private boolean wasDown;
    private boolean jump;

    @EventTarget
    public final void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.jump) {
            event.cancelEvent();
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        this.wasDown = false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x006c, code lost:
        if ((r0.field_71158_b.field_78902_a == 0.0f) == false) goto L18;
     */
    @net.ccbluex.liquidbounce.event.EventTarget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onUpdate(@org.jetbrains.annotations.NotNull net.ccbluex.liquidbounce.event.UpdateEvent r4) {
        /*
            Method dump skipped, instructions count: 305
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.features.module.modules.movement.Strafe.onUpdate(net.ccbluex.liquidbounce.event.UpdateEvent):void");
    }

    @EventTarget
    public final void onStrafe(@NotNull StrafeEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        EntityPlayerSP entityPlayerSP = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNull(entityPlayerSP);
        double d = entityPlayerSP.field_70159_w;
        EntityPlayerSP entityPlayerSP2 = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNull(entityPlayerSP2);
        double d2 = d * entityPlayerSP2.field_70159_w;
        EntityPlayerSP entityPlayerSP3 = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNull(entityPlayerSP3);
        double d3 = entityPlayerSP3.field_70179_y;
        EntityPlayerSP entityPlayerSP4 = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNull(entityPlayerSP4);
        double shotSpeed = Math.sqrt(d2 + (d3 * entityPlayerSP4.field_70179_y));
        double speed = shotSpeed * this.strengthValue.get().doubleValue();
        EntityPlayerSP entityPlayerSP5 = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNull(entityPlayerSP5);
        double motionX = entityPlayerSP5.field_70159_w * (1 - this.strengthValue.get().floatValue());
        EntityPlayerSP entityPlayerSP6 = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNull(entityPlayerSP6);
        double motionZ = entityPlayerSP6.field_70179_y * (1 - this.strengthValue.get().floatValue());
        EntityPlayerSP entityPlayerSP7 = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNull(entityPlayerSP7);
        if (entityPlayerSP7.field_71158_b.field_78900_b == 0.0f) {
            EntityPlayerSP entityPlayerSP8 = MinecraftInstance.f362mc.field_71439_g;
            Intrinsics.checkNotNull(entityPlayerSP8);
            if (entityPlayerSP8.field_71158_b.field_78902_a == 0.0f) {
                if (this.noMoveStopValue.get().booleanValue()) {
                    EntityPlayerSP entityPlayerSP9 = MinecraftInstance.f362mc.field_71439_g;
                    Intrinsics.checkNotNull(entityPlayerSP9);
                    entityPlayerSP9.field_70159_w = 0.0d;
                    EntityPlayerSP entityPlayerSP10 = MinecraftInstance.f362mc.field_71439_g;
                    Intrinsics.checkNotNull(entityPlayerSP10);
                    entityPlayerSP10.field_70179_y = 0.0d;
                    return;
                }
                return;
            }
        }
        EntityPlayerSP entityPlayerSP11 = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNull(entityPlayerSP11);
        if (!entityPlayerSP11.field_70122_E || this.onGroundStrafeValue.get().booleanValue()) {
            float yaw = getMoveYaw();
            EntityPlayerSP entityPlayerSP12 = MinecraftInstance.f362mc.field_71439_g;
            Intrinsics.checkNotNull(entityPlayerSP12);
            entityPlayerSP12.field_70159_w = ((-Math.sin(Math.toRadians(yaw))) * speed) + motionX;
            EntityPlayerSP entityPlayerSP13 = MinecraftInstance.f362mc.field_71439_g;
            Intrinsics.checkNotNull(entityPlayerSP13);
            entityPlayerSP13.field_70179_y = (Math.cos(Math.toRadians(yaw)) * speed) + motionZ;
        }
    }

    private final float getMoveYaw() {
        float moveYaw;
        EntityPlayerSP entityPlayerSP = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNull(entityPlayerSP);
        float moveYaw2 = entityPlayerSP.field_70177_z;
        EntityPlayerSP entityPlayerSP2 = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNull(entityPlayerSP2);
        if (!(entityPlayerSP2.field_70701_bs == 0.0f)) {
            EntityPlayerSP entityPlayerSP3 = MinecraftInstance.f362mc.field_71439_g;
            Intrinsics.checkNotNull(entityPlayerSP3);
            if (entityPlayerSP3.field_70702_br == 0.0f) {
                EntityPlayerSP entityPlayerSP4 = MinecraftInstance.f362mc.field_71439_g;
                Intrinsics.checkNotNull(entityPlayerSP4);
                moveYaw2 += entityPlayerSP4.field_70701_bs > 0.0f ? 0 : 180;
                return moveYaw2;
            }
        }
        EntityPlayerSP entityPlayerSP5 = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNull(entityPlayerSP5);
        if (!(entityPlayerSP5.field_70701_bs == 0.0f)) {
            EntityPlayerSP entityPlayerSP6 = MinecraftInstance.f362mc.field_71439_g;
            Intrinsics.checkNotNull(entityPlayerSP6);
            if (!(entityPlayerSP6.field_70702_br == 0.0f)) {
                EntityPlayerSP entityPlayerSP7 = MinecraftInstance.f362mc.field_71439_g;
                Intrinsics.checkNotNull(entityPlayerSP7);
                if (entityPlayerSP7.field_70701_bs > 0.0f) {
                    EntityPlayerSP entityPlayerSP8 = MinecraftInstance.f362mc.field_71439_g;
                    Intrinsics.checkNotNull(entityPlayerSP8);
                    moveYaw = moveYaw2 + (entityPlayerSP8.field_70702_br > 0.0f ? -45 : 45);
                } else {
                    EntityPlayerSP entityPlayerSP9 = MinecraftInstance.f362mc.field_71439_g;
                    Intrinsics.checkNotNull(entityPlayerSP9);
                    moveYaw = moveYaw2 - (entityPlayerSP9.field_70702_br > 0.0f ? -45 : 45);
                }
                float f = moveYaw;
                EntityPlayerSP entityPlayerSP10 = MinecraftInstance.f362mc.field_71439_g;
                Intrinsics.checkNotNull(entityPlayerSP10);
                moveYaw2 = f + (entityPlayerSP10.field_70701_bs > 0.0f ? 0 : 180);
                return moveYaw2;
            }
        }
        EntityPlayerSP entityPlayerSP11 = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNull(entityPlayerSP11);
        if (!(entityPlayerSP11.field_70702_br == 0.0f)) {
            EntityPlayerSP entityPlayerSP12 = MinecraftInstance.f362mc.field_71439_g;
            Intrinsics.checkNotNull(entityPlayerSP12);
            if (entityPlayerSP12.field_70701_bs == 0.0f) {
                EntityPlayerSP entityPlayerSP13 = MinecraftInstance.f362mc.field_71439_g;
                Intrinsics.checkNotNull(entityPlayerSP13);
                moveYaw2 += entityPlayerSP13.field_70702_br > 0.0f ? -90 : 90;
            }
        }
        return moveYaw2;
    }
}
