package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import org.jetbrains.annotations.NotNull;

/* compiled from: AACHop438.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0004H\u0016J\u0010\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\u0004H\u0016¨\u0006\n"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AACHop438;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "onDisable", "", "onMotion", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onUpdate", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AACHop438.class */
public final class AACHop438 extends SpeedMode {
    public AACHop438() {
        super("AACHop4.3.8");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
        EntityPlayerSP thePlayer = SpeedMode.f362mc.field_71439_g;
        if (thePlayer == null) {
            return;
        }
        SpeedMode.f362mc.field_71428_T.field_74278_d = 1.0f;
        if (!MovementUtils.isMoving() || thePlayer.func_70090_H() || thePlayer.func_180799_ab() || thePlayer.func_70617_f_() || thePlayer.func_70115_ae()) {
            return;
        }
        if (thePlayer.field_70122_E) {
            thePlayer.func_70664_aZ();
        } else if (thePlayer.field_70143_R <= 0.1d) {
            SpeedMode.f362mc.field_71428_T.field_74278_d = 1.5f;
        } else if (thePlayer.field_70143_R < 1.3d) {
            SpeedMode.f362mc.field_71428_T.field_74278_d = 0.7f;
        } else {
            SpeedMode.f362mc.field_71428_T.field_74278_d = 1.0f;
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onDisable() {
    }
}
