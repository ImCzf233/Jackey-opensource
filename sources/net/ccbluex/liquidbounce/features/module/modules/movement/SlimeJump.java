package net.ccbluex.liquidbounce.features.module.modules.movement;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.BlockSlime;

@ModuleInfo(name = "SlimeJump", spacedName = "Slime Jump", description = "Allows you to to jump higher on slime blocks.", category = ModuleCategory.MOVEMENT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/SlimeJump.class */
public class SlimeJump extends Module {
    private final FloatValue motionValue = new FloatValue("Motion", 0.42f, 0.2f, 1.0f);
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Set", "Add"}, "Add");

    @EventTarget
    public void onJump(JumpEvent event) {
        if (f362mc.field_71439_g != null && f362mc.field_71441_e != null && (BlockUtils.getBlock(f362mc.field_71439_g.func_180425_c().func_177977_b()) instanceof BlockSlime)) {
            event.cancelEvent();
            String lowerCase = this.modeValue.get().toLowerCase();
            boolean z = true;
            switch (lowerCase.hashCode()) {
                case 96417:
                    if (lowerCase.equals("add")) {
                        z = true;
                        break;
                    }
                    break;
                case 113762:
                    if (lowerCase.equals(PropertyDescriptor.SET)) {
                        z = false;
                        break;
                    }
                    break;
            }
            switch (z) {
                case false:
                    f362mc.field_71439_g.field_70181_x = this.motionValue.get().floatValue();
                    return;
                case true:
                    f362mc.field_71439_g.field_70181_x += this.motionValue.get().floatValue();
                    return;
                default:
                    return;
            }
        }
    }
}
