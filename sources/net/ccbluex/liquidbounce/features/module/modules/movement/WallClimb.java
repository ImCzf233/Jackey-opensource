package net.ccbluex.liquidbounce.features.module.modules.movement;

import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

@ModuleInfo(name = "WallClimb", spacedName = "Wall Climb", description = "Allows you to climb up walls like a spider.", category = ModuleCategory.MOVEMENT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/WallClimb.class */
public class WallClimb extends Module {
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Simple", "CheckerClimb", "Clip", "AAC3.3.12", "AACGlide", "Verus"}, "Simple");
    private final ListValue clipMode = new ListValue("ClipMode", new String[]{"Jump", "Fast"}, "Fast", () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("clip"));
    });
    private final FloatValue checkerClimbMotionValue = new FloatValue("CheckerClimbMotion", 0.0f, 0.0f, 1.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("checkerclimb"));
    });
    private final FloatValue verusClimbSpeed = new FloatValue("VerusClimbSpeed", 0.0f, 0.0f, 1.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("verus"));
    });
    private boolean glitch;
    private boolean canClimb;
    private int waited;

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        this.glitch = false;
        this.canClimb = false;
        this.waited = 0;
    }

    @EventTarget
    public void onMove(MoveEvent event) {
        if (f362mc.field_71439_g.field_70123_F && !f362mc.field_71439_g.func_70617_f_() && !f362mc.field_71439_g.func_70090_H() && !f362mc.field_71439_g.func_180799_ab() && "simple".equalsIgnoreCase(this.modeValue.get())) {
            event.setY(0.2d);
            f362mc.field_71439_g.field_70181_x = 0.0d;
        }
    }

    @EventTarget
    public void onJump(JumpEvent event) {
        if (this.modeValue.get().equalsIgnoreCase("verus") && this.canClimb) {
            event.cancelEvent();
        }
    }

    @EventTarget
    public void onUpdate(MotionEvent event) {
        if (event.getEventState() != EventState.POST) {
            return;
        }
        String lowerCase = this.modeValue.get().toLowerCase();
        boolean z = true;
        switch (lowerCase.hashCode()) {
            case 3056464:
                if (lowerCase.equals("clip")) {
                    z = false;
                    break;
                }
                break;
            case 112097665:
                if (lowerCase.equals("verus")) {
                    z = true;
                    break;
                }
                break;
            case 375151938:
                if (lowerCase.equals("aacglide")) {
                    z = true;
                    break;
                }
                break;
            case 1076723744:
                if (lowerCase.equals("checkerclimb")) {
                    z = true;
                    break;
                }
                break;
            case 1492139162:
                if (lowerCase.equals("aac3.3.12")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                if (f362mc.field_71439_g.field_70181_x < 0.0d) {
                    this.glitch = true;
                }
                if (f362mc.field_71439_g.field_70123_F) {
                    String lowerCase2 = this.clipMode.get().toLowerCase();
                    boolean z2 = true;
                    switch (lowerCase2.hashCode()) {
                        case 3135580:
                            if (lowerCase2.equals("fast")) {
                                z2 = true;
                                break;
                            }
                            break;
                        case 3273774:
                            if (lowerCase2.equals("jump")) {
                                z2 = false;
                                break;
                            }
                            break;
                    }
                    switch (z2) {
                        case false:
                            if (f362mc.field_71439_g.field_70122_E) {
                                f362mc.field_71439_g.func_70664_aZ();
                                return;
                            }
                            return;
                        case true:
                            if (f362mc.field_71439_g.field_70122_E) {
                                f362mc.field_71439_g.field_70181_x = 0.42d;
                                return;
                            } else if (f362mc.field_71439_g.field_70181_x < 0.0d) {
                                f362mc.field_71439_g.field_70181_x = -0.3d;
                                return;
                            } else {
                                return;
                            }
                        default:
                            return;
                    }
                }
                return;
            case true:
                boolean isInsideBlock = BlockUtils.collideBlockIntersects(f362mc.field_71439_g.func_174813_aQ(), block -> {
                    return Boolean.valueOf(!(block instanceof BlockAir));
                });
                float motion = this.checkerClimbMotionValue.get().floatValue();
                if (isInsideBlock && motion != 0.0f) {
                    f362mc.field_71439_g.field_70181_x = motion;
                    return;
                }
                return;
            case true:
                if (f362mc.field_71439_g.field_70123_F && !f362mc.field_71439_g.func_70617_f_()) {
                    this.waited++;
                    if (this.waited == 1) {
                        f362mc.field_71439_g.field_70181_x = 0.43d;
                    }
                    if (this.waited == 12) {
                        f362mc.field_71439_g.field_70181_x = 0.43d;
                    }
                    if (this.waited == 23) {
                        f362mc.field_71439_g.field_70181_x = 0.43d;
                    }
                    if (this.waited == 29) {
                        f362mc.field_71439_g.func_70107_b(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u + 0.5d, f362mc.field_71439_g.field_70161_v);
                    }
                    if (this.waited >= 30) {
                        this.waited = 0;
                        return;
                    }
                    return;
                } else if (f362mc.field_71439_g.field_70122_E) {
                    this.waited = 0;
                    return;
                } else {
                    return;
                }
            case true:
                if (!f362mc.field_71439_g.field_70123_F || f362mc.field_71439_g.func_70617_f_()) {
                    return;
                }
                f362mc.field_71439_g.field_70181_x = -0.189d;
                return;
            case true:
                if (!f362mc.field_71439_g.field_70123_F || f362mc.field_71439_g.func_70090_H() || f362mc.field_71439_g.func_180799_ab() || f362mc.field_71439_g.func_70617_f_() || f362mc.field_71439_g.field_70134_J || f362mc.field_71439_g.func_70617_f_()) {
                    this.canClimb = false;
                    return;
                }
                this.canClimb = true;
                f362mc.field_71439_g.field_70181_x = this.verusClimbSpeed.get().floatValue();
                f362mc.field_71439_g.field_70122_E = true;
                return;
            default:
                return;
        }
    }

    @EventTarget
    public void onPacket(PacketEvent event) {
        C03PacketPlayer packet = event.getPacket();
        if (packet instanceof C03PacketPlayer) {
            C03PacketPlayer packetPlayer = packet;
            if (this.glitch) {
                float yaw = (float) MovementUtils.getDirection();
                packetPlayer.field_149479_a -= MathHelper.func_76126_a(yaw) * 1.0E-8d;
                packetPlayer.field_149478_c += MathHelper.func_76134_b(yaw) * 1.0E-8d;
                this.glitch = false;
            }
            if (this.canClimb) {
                packetPlayer.field_149474_g = true;
            }
        }
    }

    @EventTarget
    public void onBlockBB(BlockBBEvent event) {
        if (f362mc.field_71439_g == null) {
            return;
        }
        String mode = this.modeValue.get();
        String lowerCase = mode.toLowerCase();
        boolean z = true;
        switch (lowerCase.hashCode()) {
            case 3056464:
                if (lowerCase.equals("clip")) {
                    z = true;
                    break;
                }
                break;
            case 1076723744:
                if (lowerCase.equals("checkerclimb")) {
                    z = false;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                if (event.getY() > f362mc.field_71439_g.field_70163_u) {
                    event.setBoundingBox(null);
                    return;
                }
                return;
            case true:
                if (event.getBlock() != null && f362mc.field_71439_g != null && (event.getBlock() instanceof BlockAir) && event.getY() < f362mc.field_71439_g.field_70163_u && f362mc.field_71439_g.field_70123_F && !f362mc.field_71439_g.func_70617_f_() && !f362mc.field_71439_g.func_70090_H() && !f362mc.field_71439_g.func_180799_ab()) {
                    event.setBoundingBox(new AxisAlignedBB(0.0d, 0.0d, 0.0d, 1.0d, 1.0d, 1.0d).func_72317_d(f362mc.field_71439_g.field_70165_t, ((int) f362mc.field_71439_g.field_70163_u) - 1, f362mc.field_71439_g.field_70161_v));
                    return;
                }
                return;
            default:
                return;
        }
    }
}
