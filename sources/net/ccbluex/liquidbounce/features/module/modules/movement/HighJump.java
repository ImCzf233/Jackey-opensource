package net.ccbluex.liquidbounce.features.module.modules.movement;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.util.BlockPos;

@ModuleInfo(name = "HighJump", spacedName = "High Jump", description = "Allows you to jump higher.", category = ModuleCategory.MOVEMENT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/HighJump.class */
public class HighJump extends Module {
    private final FloatValue heightValue = new FloatValue("Height", 2.0f, 1.1f, 10.0f, "m");
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Vanilla", "Damage", "AACv3", "DAC", "Mineplex", "MatrixWater"}, "Vanilla");
    private final BoolValue glassValue = new BoolValue("OnlyGlassPane", false);
    public int tick;

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if (this.glassValue.get().booleanValue() && !(BlockUtils.getBlock(new BlockPos(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u, f362mc.field_71439_g.field_70161_v)) instanceof BlockPane)) {
            return;
        }
        String lowerCase = this.modeValue.get().toLowerCase();
        boolean z = true;
        switch (lowerCase.hashCode()) {
            case -1362669950:
                if (lowerCase.equals("mineplex")) {
                    z = true;
                    break;
                }
                break;
            case -1339126929:
                if (lowerCase.equals("damage")) {
                    z = false;
                    break;
                }
                break;
            case -328540682:
                if (lowerCase.equals("matrixwater")) {
                    z = true;
                    break;
                }
                break;
            case 99206:
                if (lowerCase.equals("dac")) {
                    z = true;
                    break;
                }
                break;
            case 92570112:
                if (lowerCase.equals("aacv3")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                if (f362mc.field_71439_g.field_70737_aN > 0 && f362mc.field_71439_g.field_70122_E) {
                    f362mc.field_71439_g.field_70181_x += 0.42f * this.heightValue.get().floatValue();
                    return;
                }
                return;
            case true:
                if (!f362mc.field_71439_g.field_70122_E) {
                    f362mc.field_71439_g.field_70181_x += 0.059d;
                    return;
                }
                return;
            case true:
                if (!f362mc.field_71439_g.field_70122_E) {
                    f362mc.field_71439_g.field_70181_x += 0.049999d;
                    return;
                }
                return;
            case true:
                if (!f362mc.field_71439_g.field_70122_E) {
                    MovementUtils.strafe(0.35f);
                    return;
                }
                return;
            case true:
                if (f362mc.field_71439_g.func_70090_H()) {
                    if (f362mc.field_71441_e.func_180495_p(new BlockPos(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u + 1.0d, f362mc.field_71439_g.field_70161_v)).func_177230_c() == Block.func_149729_e(9)) {
                        f362mc.field_71439_g.field_70181_x = 0.18d;
                        return;
                    } else if (f362mc.field_71441_e.func_180495_p(new BlockPos(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u, f362mc.field_71439_g.field_70161_v)).func_177230_c() == Block.func_149729_e(9)) {
                        f362mc.field_71439_g.field_70181_x = this.heightValue.get().floatValue();
                        f362mc.field_71439_g.field_70122_E = true;
                        return;
                    } else {
                        return;
                    }
                }
                return;
            default:
                return;
        }
    }

    @EventTarget
    public void onMove(MoveEvent event) {
        if ((!this.glassValue.get().booleanValue() || (BlockUtils.getBlock(new BlockPos(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u, f362mc.field_71439_g.field_70161_v)) instanceof BlockPane)) && !f362mc.field_71439_g.field_70122_E && "mineplex".equals(this.modeValue.get().toLowerCase())) {
            f362mc.field_71439_g.field_70181_x += f362mc.field_71439_g.field_70143_R == 0.0f ? 0.0499d : 0.05d;
        }
    }

    @EventTarget
    public void onJump(JumpEvent event) {
        if (this.glassValue.get().booleanValue() && !(BlockUtils.getBlock(new BlockPos(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u, f362mc.field_71439_g.field_70161_v)) instanceof BlockPane)) {
            return;
        }
        String lowerCase = this.modeValue.get().toLowerCase();
        boolean z = true;
        switch (lowerCase.hashCode()) {
            case -1362669950:
                if (lowerCase.equals("mineplex")) {
                    z = true;
                    break;
                }
                break;
            case 233102203:
                if (lowerCase.equals("vanilla")) {
                    z = false;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                event.setMotion(event.getMotion() * this.heightValue.get().floatValue());
                return;
            case true:
                event.setMotion(0.47f);
                return;
            default:
                return;
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public String getTag() {
        return this.modeValue.get();
    }
}
