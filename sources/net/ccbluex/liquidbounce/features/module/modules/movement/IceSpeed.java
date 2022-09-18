package net.ccbluex.liquidbounce.features.module.modules.movement;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

@ModuleInfo(name = "IceSpeed", spacedName = "Ice Speed", description = "Allows you to walk faster on ice.", category = ModuleCategory.MOVEMENT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/IceSpeed.class */
public class IceSpeed extends Module {
    private final ListValue modeValue = new ListValue("Mode", new String[]{"NCP", "AAC", "Spartan"}, "NCP");

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        if (this.modeValue.get().equalsIgnoreCase("NCP")) {
            Blocks.field_150432_aD.field_149765_K = 0.39f;
            Blocks.field_150403_cj.field_149765_K = 0.39f;
        }
        super.onEnable();
    }

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        Material material;
        String mode = this.modeValue.get();
        if (mode.equalsIgnoreCase("NCP")) {
            Blocks.field_150432_aD.field_149765_K = 0.39f;
            Blocks.field_150403_cj.field_149765_K = 0.39f;
        } else {
            Blocks.field_150432_aD.field_149765_K = 0.98f;
            Blocks.field_150403_cj.field_149765_K = 0.98f;
        }
        if (f362mc.field_71439_g.field_70122_E && !f362mc.field_71439_g.func_70617_f_() && !f362mc.field_71439_g.func_70093_af() && f362mc.field_71439_g.func_70051_ag() && f362mc.field_71439_g.field_71158_b.field_78900_b > 0.0d) {
            if (mode.equalsIgnoreCase("AAC") && ((material = BlockUtils.getMaterial(f362mc.field_71439_g.func_180425_c().func_177977_b())) == Material.field_151588_w || material == Material.field_151598_x)) {
                f362mc.field_71439_g.field_70159_w *= 1.342d;
                f362mc.field_71439_g.field_70179_y *= 1.342d;
                Blocks.field_150432_aD.field_149765_K = 0.6f;
                Blocks.field_150403_cj.field_149765_K = 0.6f;
            }
            if (mode.equalsIgnoreCase("Spartan")) {
                Material material2 = BlockUtils.getMaterial(f362mc.field_71439_g.func_180425_c().func_177977_b());
                if (material2 == Material.field_151588_w || material2 == Material.field_151598_x) {
                    Block upBlock = BlockUtils.getBlock(new BlockPos(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u + 2.0d, f362mc.field_71439_g.field_70161_v));
                    if (!(upBlock instanceof BlockAir)) {
                        f362mc.field_71439_g.field_70159_w *= 1.342d;
                        f362mc.field_71439_g.field_70179_y *= 1.342d;
                    } else {
                        f362mc.field_71439_g.field_70159_w *= 1.18d;
                        f362mc.field_71439_g.field_70179_y *= 1.18d;
                    }
                    Blocks.field_150432_aD.field_149765_K = 0.6f;
                    Blocks.field_150403_cj.field_149765_K = 0.6f;
                }
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        Blocks.field_150432_aD.field_149765_K = 0.98f;
        Blocks.field_150403_cj.field_149765_K = 0.98f;
        super.onDisable();
    }
}
