package net.ccbluex.liquidbounce.features.module.modules.movement;

import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
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
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

@ModuleInfo(name = "LiquidWalk", spacedName = "Liquid Walk", description = "Allows you to walk on water.", category = ModuleCategory.MOVEMENT, keyBind = 36)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/LiquidWalk.class */
public class LiquidWalk extends Module {
    public final ListValue modeValue = new ListValue("Mode", new String[]{"Vanilla", "NCP", "AAC", "AAC3.3.11", "AACFly", "AAC4.2.1", "Horizon1.4.6", "Twillight", "Matrix", "Dolphin", "Swim"}, "NCP");
    private final BoolValue noJumpValue = new BoolValue("NoJump", false);
    private final FloatValue aacFlyValue = new FloatValue("AACFlyMotion", 0.5f, 0.1f, 1.0f);
    private final FloatValue matrixSpeedValue = new FloatValue("MatrixSpeed", 1.15f, 0.1f, 1.15f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("matrix"));
    });
    private boolean nextTick;

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if (f362mc.field_71439_g == null || f362mc.field_71439_g.func_70093_af()) {
            return;
        }
        String lowerCase = this.modeValue.get().toLowerCase();
        boolean z = true;
        switch (lowerCase.hashCode()) {
            case -1081239615:
                if (lowerCase.equals("matrix")) {
                    z = true;
                    break;
                }
                break;
            case -376762896:
                if (lowerCase.equals("twillight")) {
                    z = true;
                    break;
                }
                break;
            case 96323:
                if (lowerCase.equals("aac")) {
                    z = true;
                    break;
                }
                break;
            case 108891:
                if (lowerCase.equals("ncp")) {
                    z = false;
                    break;
                }
                break;
            case 233102203:
                if (lowerCase.equals("vanilla")) {
                    z = true;
                    break;
                }
                break;
            case 326150744:
                if (lowerCase.equals("aac4.2.1")) {
                    z = true;
                    break;
                }
                break;
            case 1492139161:
                if (lowerCase.equals("aac3.3.11")) {
                    z = true;
                    break;
                }
                break;
            case 1837070814:
                if (lowerCase.equals("dolphin")) {
                    z = true;
                    break;
                }
                break;
            case 2008740100:
                if (lowerCase.equals("horizon1.4.6")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
            case true:
                if (BlockUtils.collideBlock(f362mc.field_71439_g.func_174813_aQ(), block -> {
                    return Boolean.valueOf(block instanceof BlockLiquid);
                }) && f362mc.field_71439_g.func_70055_a(Material.field_151579_a) && !f362mc.field_71439_g.func_70093_af()) {
                    f362mc.field_71439_g.field_70181_x = 0.08d;
                    return;
                }
                return;
            case true:
                BlockPos blockPos = f362mc.field_71439_g.func_180425_c().func_177977_b();
                if ((!f362mc.field_71439_g.field_70122_E && BlockUtils.getBlock(blockPos) == Blocks.field_150355_j) || f362mc.field_71439_g.func_70090_H()) {
                    if (!f362mc.field_71439_g.func_70051_ag()) {
                        f362mc.field_71439_g.field_70159_w *= 0.99999d;
                        f362mc.field_71439_g.field_70181_x *= 0.0d;
                        f362mc.field_71439_g.field_70179_y *= 0.99999d;
                        if (f362mc.field_71439_g.field_70123_F) {
                            f362mc.field_71439_g.field_70181_x = ((int) (f362mc.field_71439_g.field_70163_u - ((int) (f362mc.field_71439_g.field_70163_u - 1.0d)))) / 8.0f;
                        }
                    } else {
                        f362mc.field_71439_g.field_70159_w *= 0.99999d;
                        f362mc.field_71439_g.field_70181_x *= 0.0d;
                        f362mc.field_71439_g.field_70179_y *= 0.99999d;
                        if (f362mc.field_71439_g.field_70123_F) {
                            f362mc.field_71439_g.field_70181_x = ((int) (f362mc.field_71439_g.field_70163_u - ((int) (f362mc.field_71439_g.field_70163_u - 1.0d)))) / 8.0f;
                        }
                    }
                    if (f362mc.field_71439_g.field_70143_R >= 4.0f) {
                        f362mc.field_71439_g.field_70181_x = -0.004d;
                    } else if (f362mc.field_71439_g.func_70090_H()) {
                        f362mc.field_71439_g.field_70181_x = 0.09d;
                    }
                }
                if (f362mc.field_71439_g.field_70737_aN != 0) {
                    f362mc.field_71439_g.field_70122_E = false;
                    return;
                }
                return;
            case true:
                if (f362mc.field_71439_g.func_70090_H()) {
                    f362mc.field_71474_y.field_74314_A.field_74513_e = false;
                    if (f362mc.field_71439_g.field_70123_F) {
                        f362mc.field_71439_g.field_70181_x = 0.09d;
                        return;
                    }
                    Block block2 = BlockUtils.getBlock(new BlockPos(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u + 1.0d, f362mc.field_71439_g.field_70161_v));
                    Block blockUp = BlockUtils.getBlock(new BlockPos(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u + 1.1d, f362mc.field_71439_g.field_70161_v));
                    if (blockUp instanceof BlockLiquid) {
                        f362mc.field_71439_g.field_70181_x = 0.1d;
                    } else if (block2 instanceof BlockLiquid) {
                        f362mc.field_71439_g.field_70181_x = 0.0d;
                    }
                    f362mc.field_71439_g.field_70159_w *= this.matrixSpeedValue.get().floatValue();
                    f362mc.field_71439_g.field_70179_y *= this.matrixSpeedValue.get().floatValue();
                    return;
                }
                return;
            case true:
                if (f362mc.field_71439_g.func_70090_H()) {
                    f362mc.field_71439_g.field_70159_w *= 1.17d;
                    f362mc.field_71439_g.field_70179_y *= 1.17d;
                    if (f362mc.field_71439_g.field_70123_F) {
                        f362mc.field_71439_g.field_70181_x = 0.24d;
                        return;
                    } else if (f362mc.field_71441_e.func_180495_p(new BlockPos(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u + 1.0d, f362mc.field_71439_g.field_70161_v)).func_177230_c() != Blocks.field_150350_a) {
                        f362mc.field_71439_g.field_70181_x += 0.04d;
                        return;
                    } else {
                        return;
                    }
                }
                return;
            case true:
                if (f362mc.field_71439_g.func_70090_H()) {
                    f362mc.field_71439_g.field_70181_x += 0.03999999910593033d;
                    return;
                }
                return;
            case true:
                BlockPos blockPos2 = f362mc.field_71439_g.func_180425_c().func_177977_b();
                if ((!f362mc.field_71439_g.field_70122_E && BlockUtils.getBlock(blockPos2) == Blocks.field_150355_j) || f362mc.field_71439_g.func_70090_H()) {
                    f362mc.field_71439_g.field_70181_x *= 0.0d;
                    f362mc.field_71439_g.field_70747_aH = 0.08f;
                    if (f362mc.field_71439_g.field_70143_R <= 0.0f && f362mc.field_71439_g.func_70090_H()) {
                        f362mc.field_71474_y.field_74314_A.field_74513_e = true;
                        return;
                    }
                    return;
                }
                return;
            case true:
                if (f362mc.field_71439_g.func_70090_H()) {
                    MovementUtils.strafe();
                    f362mc.field_71474_y.field_74314_A.field_74513_e = true;
                    if (MovementUtils.isMoving() && !f362mc.field_71439_g.field_70122_E) {
                        f362mc.field_71439_g.field_70181_x += 0.13d;
                        return;
                    }
                    return;
                }
                return;
            case true:
                if (f362mc.field_71439_g.func_70090_H()) {
                    f362mc.field_71439_g.field_70159_w *= 1.04d;
                    f362mc.field_71439_g.field_70179_y *= 1.04d;
                    MovementUtils.strafe();
                    return;
                }
                return;
            default:
                return;
        }
    }

    @EventTarget
    public void onMove(MoveEvent event) {
        if ("aacfly".equals(this.modeValue.get().toLowerCase()) && f362mc.field_71439_g.func_70090_H()) {
            event.setY(this.aacFlyValue.get().floatValue());
            f362mc.field_71439_g.field_70181_x = this.aacFlyValue.get().floatValue();
        }
        if ("twillight".equals(this.modeValue.get().toLowerCase()) && f362mc.field_71439_g.func_70090_H()) {
            event.setY(0.01d);
            f362mc.field_71439_g.field_70181_x = 0.01d;
        }
    }

    @EventTarget
    public void onBlockBB(BlockBBEvent event) {
        if (f362mc.field_71439_g != null && f362mc.field_71439_g.func_174813_aQ() != null && (event.getBlock() instanceof BlockLiquid) && !BlockUtils.collideBlock(f362mc.field_71439_g.func_174813_aQ(), block -> {
            return Boolean.valueOf(block instanceof BlockLiquid);
        }) && !f362mc.field_71439_g.func_70093_af()) {
            String lowerCase = this.modeValue.get().toLowerCase();
            boolean z = true;
            switch (lowerCase.hashCode()) {
                case 108891:
                    if (lowerCase.equals("ncp")) {
                        z = false;
                        break;
                    }
                    break;
                case 233102203:
                    if (lowerCase.equals("vanilla")) {
                        z = true;
                        break;
                    }
                    break;
            }
            switch (z) {
                case false:
                case true:
                    event.setBoundingBox(AxisAlignedBB.func_178781_a(event.getX(), event.getY(), event.getZ(), event.getX() + 1, event.getY() + 1, event.getZ() + 1));
                    return;
                default:
                    return;
            }
        }
    }

    @EventTarget
    public void onPacket(PacketEvent event) {
        if (f362mc.field_71439_g != null && this.modeValue.get().equalsIgnoreCase("NCP") && (event.getPacket() instanceof C03PacketPlayer)) {
            C03PacketPlayer packetPlayer = event.getPacket();
            if (BlockUtils.collideBlock(new AxisAlignedBB(f362mc.field_71439_g.func_174813_aQ().field_72336_d, f362mc.field_71439_g.func_174813_aQ().field_72337_e, f362mc.field_71439_g.func_174813_aQ().field_72334_f, f362mc.field_71439_g.func_174813_aQ().field_72340_a, f362mc.field_71439_g.func_174813_aQ().field_72338_b - 0.01d, f362mc.field_71439_g.func_174813_aQ().field_72339_c), block -> {
                return Boolean.valueOf(block instanceof BlockLiquid);
            })) {
                this.nextTick = !this.nextTick;
                if (this.nextTick) {
                    packetPlayer.field_149477_b -= 0.001d;
                }
            }
        }
    }

    @EventTarget
    public void onJump(JumpEvent event) {
        if (f362mc.field_71439_g == null) {
            return;
        }
        Block block = BlockUtils.getBlock(new BlockPos(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u - 0.01d, f362mc.field_71439_g.field_70161_v));
        if (this.noJumpValue.get().booleanValue() && (block instanceof BlockLiquid)) {
            event.cancelEvent();
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public String getTag() {
        return this.modeValue.get();
    }
}
