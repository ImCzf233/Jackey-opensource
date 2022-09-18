package net.ccbluex.liquidbounce.features.module.modules.movement;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.minecraft.block.BlockLiquid;

@ModuleInfo(name = "WaterSpeed", spacedName = "Water Speed", description = "Allows you to swim faster.", category = ModuleCategory.MOVEMENT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/WaterSpeed.class */
public class WaterSpeed extends Module {
    private final FloatValue speedValue = new FloatValue("Speed", 1.2f, 1.1f, 1.5f);

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if (f362mc.field_71439_g.func_70090_H() && (BlockUtils.getBlock(f362mc.field_71439_g.func_180425_c()) instanceof BlockLiquid)) {
            float speed = this.speedValue.get().floatValue();
            f362mc.field_71439_g.field_70159_w *= speed;
            f362mc.field_71439_g.field_70179_y *= speed;
        }
    }
}
