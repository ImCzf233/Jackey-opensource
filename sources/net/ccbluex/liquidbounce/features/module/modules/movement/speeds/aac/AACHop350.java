package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AACHop350.class */
public class AACHop350 extends SpeedMode implements Listenable {
    public AACHop350() {
        super("AACHop3.5.0");
        LiquidBounce.eventManager.registerListener(this);
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    @EventTarget
    public void onMotion(MotionEvent event) {
        if (event.getEventState() == EventState.POST && MovementUtils.isMoving() && !f362mc.field_71439_g.func_70090_H() && !f362mc.field_71439_g.func_180799_ab()) {
            f362mc.field_71439_g.field_70747_aH += 0.00208f;
            if (f362mc.field_71439_g.field_70143_R <= 1.0f) {
                if (f362mc.field_71439_g.field_70122_E) {
                    f362mc.field_71439_g.func_70664_aZ();
                    f362mc.field_71439_g.field_70159_w *= 1.0118000507354736d;
                    f362mc.field_71439_g.field_70179_y *= 1.0118000507354736d;
                    return;
                }
                f362mc.field_71439_g.field_70181_x -= 0.014700000174343586d;
                f362mc.field_71439_g.field_70159_w *= 1.0013799667358398d;
                f362mc.field_71439_g.field_70179_y *= 1.0013799667358398d;
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onEnable() {
        if (f362mc.field_71439_g.field_70122_E) {
            EntityPlayerSP entityPlayerSP = f362mc.field_71439_g;
            f362mc.field_71439_g.field_70179_y = 0.0d;
            entityPlayerSP.field_70159_w = 0.0d;
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onDisable() {
        f362mc.field_71439_g.field_70747_aH = 0.02f;
    }

    @Override // net.ccbluex.liquidbounce.event.Listenable
    public boolean handleEvents() {
        return isActive();
    }
}
