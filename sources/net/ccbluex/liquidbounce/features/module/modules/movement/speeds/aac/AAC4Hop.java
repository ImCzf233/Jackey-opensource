package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import org.jetbrains.annotations.NotNull;

/* compiled from: AAC4Hop.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0004H\u0016J\b\u0010\u0006\u001a\u00020\u0004H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u0004H\u0016J\b\u0010\u000b\u001a\u00020\u0004H\u0016¨\u0006\f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AAC4Hop;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "onDisable", "", "onEnable", "onMotion", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onTick", "onUpdate", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AAC4Hop.class */
public final class AAC4Hop extends SpeedMode {
    public AAC4Hop() {
        super("AAC4Hop");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onDisable() {
        SpeedMode.f362mc.field_71428_T.field_74278_d = 1.0f;
        EntityPlayerSP entityPlayerSP = SpeedMode.f362mc.field_71439_g;
        Intrinsics.checkNotNull(entityPlayerSP);
        entityPlayerSP.field_71102_ce = 0.02f;
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onTick() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
        EntityPlayerSP entityPlayerSP = SpeedMode.f362mc.field_71439_g;
        Intrinsics.checkNotNull(entityPlayerSP);
        if (entityPlayerSP.func_70090_H()) {
            return;
        }
        if (MovementUtils.isMoving()) {
            EntityPlayerSP entityPlayerSP2 = SpeedMode.f362mc.field_71439_g;
            Intrinsics.checkNotNull(entityPlayerSP2);
            if (entityPlayerSP2.field_70122_E) {
                EntityPlayerSP entityPlayerSP3 = SpeedMode.f362mc.field_71439_g;
                Intrinsics.checkNotNull(entityPlayerSP3);
                entityPlayerSP3.func_70664_aZ();
                EntityPlayerSP entityPlayerSP4 = SpeedMode.f362mc.field_71439_g;
                Intrinsics.checkNotNull(entityPlayerSP4);
                entityPlayerSP4.field_71102_ce = 0.0201f;
                SpeedMode.f362mc.field_71428_T.field_74278_d = 0.94f;
            }
            EntityPlayerSP entityPlayerSP5 = SpeedMode.f362mc.field_71439_g;
            Intrinsics.checkNotNull(entityPlayerSP5);
            if (entityPlayerSP5.field_70143_R > 0.7d) {
                EntityPlayerSP entityPlayerSP6 = SpeedMode.f362mc.field_71439_g;
                Intrinsics.checkNotNull(entityPlayerSP6);
                if (entityPlayerSP6.field_70143_R < 1.3d) {
                    EntityPlayerSP entityPlayerSP7 = SpeedMode.f362mc.field_71439_g;
                    Intrinsics.checkNotNull(entityPlayerSP7);
                    entityPlayerSP7.field_71102_ce = 0.02f;
                    SpeedMode.f362mc.field_71428_T.field_74278_d = 1.8f;
                }
            }
            EntityPlayerSP entityPlayerSP8 = SpeedMode.f362mc.field_71439_g;
            Intrinsics.checkNotNull(entityPlayerSP8);
            if (entityPlayerSP8.field_70143_R >= 1.3d) {
                SpeedMode.f362mc.field_71428_T.field_74278_d = 1.0f;
                EntityPlayerSP entityPlayerSP9 = SpeedMode.f362mc.field_71439_g;
                Intrinsics.checkNotNull(entityPlayerSP9);
                entityPlayerSP9.field_71102_ce = 0.02f;
                return;
            }
            return;
        }
        EntityPlayerSP entityPlayerSP10 = SpeedMode.f362mc.field_71439_g;
        Intrinsics.checkNotNull(entityPlayerSP10);
        entityPlayerSP10.field_70159_w = 0.0d;
        EntityPlayerSP entityPlayerSP11 = SpeedMode.f362mc.field_71439_g;
        Intrinsics.checkNotNull(entityPlayerSP11);
        entityPlayerSP11.field_70179_y = 0.0d;
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onEnable() {
    }
}
