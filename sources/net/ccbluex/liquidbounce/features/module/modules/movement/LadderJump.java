package net.ccbluex.liquidbounce.features.module.modules.movement;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;

@ModuleInfo(name = "LadderJump", spacedName = "Ladder Jump", description = "Boosts you up when touching a ladder.", category = ModuleCategory.MOVEMENT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/LadderJump.class */
public class LadderJump extends Module {
    static boolean jumped;

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if (f362mc.field_71439_g.field_70122_E) {
            if (f362mc.field_71439_g.func_70617_f_()) {
                f362mc.field_71439_g.field_70181_x = 1.5d;
                jumped = true;
                return;
            }
            jumped = false;
        } else if (!f362mc.field_71439_g.func_70617_f_() && jumped) {
            f362mc.field_71439_g.field_70181_x += 0.059d;
        }
    }
}
