package net.ccbluex.liquidbounce.features.module.modules.movement.speeds;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode.class */
public abstract class SpeedMode extends MinecraftInstance {
    public final String modeName;

    public abstract void onMotion();

    public abstract void onUpdate();

    public abstract void onMove(MoveEvent moveEvent);

    public SpeedMode(String modeName) {
        this.modeName = modeName;
    }

    public boolean isActive() {
        Speed speed = (Speed) LiquidBounce.moduleManager.getModule(Speed.class);
        return speed != null && !f362mc.field_71439_g.func_70093_af() && speed.getState() && speed.getModeName().equals(this.modeName);
    }

    public void onMotion(MotionEvent eventMotion) {
    }

    public void onJump(JumpEvent event) {
    }

    public void onTick() {
    }

    public void onEnable() {
    }

    public void onDisable() {
    }
}
