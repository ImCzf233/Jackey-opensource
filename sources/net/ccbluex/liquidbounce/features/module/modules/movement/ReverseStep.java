package net.ccbluex.liquidbounce.features.module.modules.movement;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.AxisAlignedBB;

@ModuleInfo(name = "ReverseStep", spacedName = "Reverse Step", description = "Allows you to step down blocks faster.", category = ModuleCategory.MOVEMENT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/ReverseStep.class */
public class ReverseStep extends Module {
    private final FloatValue motionValue = new FloatValue("Motion", 1.0f, 0.21f, 1.0f);
    private boolean jumped;

    @EventTarget(ignoreCondition = true)
    public void onUpdate(UpdateEvent event) {
        if (f362mc.field_71439_g.field_70122_E) {
            this.jumped = false;
        }
        if (f362mc.field_71439_g.field_70181_x > 0.0d) {
            this.jumped = true;
        }
        if (getState() && !BlockUtils.collideBlock(f362mc.field_71439_g.func_174813_aQ(), block -> {
            return Boolean.valueOf(block instanceof BlockLiquid);
        }) && !BlockUtils.collideBlock(new AxisAlignedBB(f362mc.field_71439_g.func_174813_aQ().field_72336_d, f362mc.field_71439_g.func_174813_aQ().field_72337_e, f362mc.field_71439_g.func_174813_aQ().field_72334_f, f362mc.field_71439_g.func_174813_aQ().field_72340_a, f362mc.field_71439_g.func_174813_aQ().field_72338_b - 0.01d, f362mc.field_71439_g.func_174813_aQ().field_72339_c), block2 -> {
            return Boolean.valueOf(block2 instanceof BlockLiquid);
        }) && !f362mc.field_71474_y.field_74314_A.func_151470_d() && !f362mc.field_71439_g.field_70122_E && !f362mc.field_71439_g.field_71158_b.field_78901_c && f362mc.field_71439_g.field_70181_x <= 0.0d && f362mc.field_71439_g.field_70143_R <= 1.0f && !this.jumped) {
            f362mc.field_71439_g.field_70181_x = -this.motionValue.get().floatValue();
        }
    }

    @EventTarget(ignoreCondition = true)
    public void onJump(JumpEvent event) {
        this.jumped = true;
    }
}
