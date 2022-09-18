package net.ccbluex.liquidbounce.features.module.modules.movement;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
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
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSlime;
import net.minecraft.block.BlockStairs;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;

@ModuleInfo(name = "BufferSpeed", spacedName = "Buffer Speed", description = "Allows you to walk faster on slabs and stairs.", category = ModuleCategory.MOVEMENT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/BufferSpeed.class */
public class BufferSpeed extends Module {
    private final BoolValue speedLimitValue = new BoolValue("SpeedLimit", true);
    private final FloatValue maxSpeedValue = new FloatValue("MaxSpeed", 2.0f, 1.0f, 5.0f);
    private final BoolValue bufferValue = new BoolValue("Buffer", true);
    private final BoolValue stairsValue = new BoolValue("Stairs", true);
    private final FloatValue stairsBoostValue = new FloatValue("StairsBoost", 1.87f, 1.0f, 2.0f);
    private final ListValue stairsModeValue = new ListValue("StairsMode", new String[]{"Old", "New"}, "New");
    private final BoolValue slabsValue = new BoolValue("Slabs", true);
    private final FloatValue slabsBoostValue = new FloatValue("SlabsBoost", 1.87f, 1.0f, 2.0f);
    private final ListValue slabsModeValue = new ListValue("SlabsMode", new String[]{"Old", "New"}, "New");
    private final BoolValue iceValue = new BoolValue("Ice", false);
    private final FloatValue iceBoostValue = new FloatValue("IceBoost", 1.342f, 1.0f, 2.0f);
    private final BoolValue snowValue = new BoolValue("Snow", true);
    private final FloatValue snowBoostValue = new FloatValue("SnowBoost", 1.87f, 1.0f, 2.0f);
    private final BoolValue snowPortValue = new BoolValue("SnowPort", true);
    private final BoolValue wallValue = new BoolValue("Wall", true);
    private final FloatValue wallBoostValue = new FloatValue("WallBoost", 1.87f, 1.0f, 2.0f);
    private final ListValue wallModeValue = new ListValue("WallMode", new String[]{"Old", "New"}, "New");
    private final BoolValue headBlockValue = new BoolValue("HeadBlock", true);
    private final FloatValue headBlockBoostValue = new FloatValue("HeadBlockBoost", 1.87f, 1.0f, 2.0f);
    private final BoolValue slimeValue = new BoolValue("Slime", true);
    private final BoolValue airStrafeValue = new BoolValue("AirStrafe", false);
    private final BoolValue noHurtValue = new BoolValue("NoHurt", true);
    private double speed = 0.0d;
    private boolean down;
    private boolean forceDown;
    private boolean fastHop;
    private boolean hadFastHop;
    private boolean legitHop;

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if (LiquidBounce.moduleManager.getModule(Speed.class).getState() || (this.noHurtValue.get().booleanValue() && f362mc.field_71439_g.field_70737_aN > 0)) {
            reset();
            return;
        }
        BlockPos blockPos = new BlockPos(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.func_174813_aQ().field_72338_b, f362mc.field_71439_g.field_70161_v);
        if (this.forceDown || (this.down && f362mc.field_71439_g.field_70181_x == 0.0d)) {
            f362mc.field_71439_g.field_70181_x = -1.0d;
            this.down = false;
            this.forceDown = false;
        }
        if (this.fastHop) {
            f362mc.field_71439_g.field_71102_ce = 0.0211f;
            this.hadFastHop = true;
        } else if (this.hadFastHop) {
            f362mc.field_71439_g.field_71102_ce = 0.02f;
            this.hadFastHop = false;
        }
        if (!MovementUtils.isMoving() || f362mc.field_71439_g.func_70093_af() || f362mc.field_71439_g.func_70090_H() || f362mc.field_71474_y.field_74314_A.func_151470_d()) {
            reset();
        } else if (f362mc.field_71439_g.field_70122_E) {
            this.fastHop = false;
            if (this.slimeValue.get().booleanValue() && ((BlockUtils.getBlock(blockPos.func_177977_b()) instanceof BlockSlime) || (BlockUtils.getBlock(blockPos) instanceof BlockSlime))) {
                f362mc.field_71439_g.func_70664_aZ();
                f362mc.field_71439_g.field_70181_x = 0.08d;
                f362mc.field_71439_g.field_70159_w *= 1.132d;
                f362mc.field_71439_g.field_70179_y *= 1.132d;
                this.down = true;
                return;
            }
            if (this.slabsValue.get().booleanValue() && (BlockUtils.getBlock(blockPos) instanceof BlockSlab)) {
                String lowerCase = this.slabsModeValue.get().toLowerCase();
                boolean z = true;
                switch (lowerCase.hashCode()) {
                    case 108960:
                        if (lowerCase.equals("new")) {
                            z = true;
                            break;
                        }
                        break;
                    case 110119:
                        if (lowerCase.equals("old")) {
                            z = false;
                            break;
                        }
                        break;
                }
                switch (z) {
                    case false:
                        boost(this.slabsBoostValue.get().floatValue());
                        return;
                    case true:
                        this.fastHop = true;
                        if (this.legitHop) {
                            f362mc.field_71439_g.func_70664_aZ();
                            f362mc.field_71439_g.field_70122_E = false;
                            this.legitHop = false;
                            return;
                        }
                        f362mc.field_71439_g.field_70122_E = false;
                        MovementUtils.strafe(0.375f);
                        f362mc.field_71439_g.func_70664_aZ();
                        f362mc.field_71439_g.field_70181_x = 0.41d;
                        return;
                }
            }
            if (this.stairsValue.get().booleanValue() && ((BlockUtils.getBlock(blockPos.func_177977_b()) instanceof BlockStairs) || (BlockUtils.getBlock(blockPos) instanceof BlockStairs))) {
                String lowerCase2 = this.stairsModeValue.get().toLowerCase();
                boolean z2 = true;
                switch (lowerCase2.hashCode()) {
                    case 108960:
                        if (lowerCase2.equals("new")) {
                            z2 = true;
                            break;
                        }
                        break;
                    case 110119:
                        if (lowerCase2.equals("old")) {
                            z2 = false;
                            break;
                        }
                        break;
                }
                switch (z2) {
                    case false:
                        boost(this.stairsBoostValue.get().floatValue());
                        return;
                    case true:
                        this.fastHop = true;
                        if (this.legitHop) {
                            f362mc.field_71439_g.func_70664_aZ();
                            f362mc.field_71439_g.field_70122_E = false;
                            this.legitHop = false;
                            return;
                        }
                        f362mc.field_71439_g.field_70122_E = false;
                        MovementUtils.strafe(0.375f);
                        f362mc.field_71439_g.func_70664_aZ();
                        f362mc.field_71439_g.field_70181_x = 0.41d;
                        return;
                }
            }
            this.legitHop = true;
            if (this.headBlockValue.get().booleanValue() && BlockUtils.getBlock(blockPos.func_177981_b(2)) != Blocks.field_150350_a) {
                boost(this.headBlockBoostValue.get().floatValue());
            } else if (this.iceValue.get().booleanValue() && (BlockUtils.getBlock(blockPos.func_177977_b()) == Blocks.field_150432_aD || BlockUtils.getBlock(blockPos.func_177977_b()) == Blocks.field_150403_cj)) {
                boost(this.iceBoostValue.get().floatValue());
            } else if (this.snowValue.get().booleanValue() && BlockUtils.getBlock(blockPos) == Blocks.field_150431_aC && (this.snowPortValue.get().booleanValue() || f362mc.field_71439_g.field_70163_u - ((int) f362mc.field_71439_g.field_70163_u) >= 0.125d)) {
                if (f362mc.field_71439_g.field_70163_u - ((int) f362mc.field_71439_g.field_70163_u) >= 0.125d) {
                    boost(this.snowBoostValue.get().floatValue());
                    return;
                }
                f362mc.field_71439_g.func_70664_aZ();
                this.forceDown = true;
            } else {
                if (this.wallValue.get().booleanValue()) {
                    String lowerCase3 = this.wallModeValue.get().toLowerCase();
                    boolean z3 = true;
                    switch (lowerCase3.hashCode()) {
                        case 108960:
                            if (lowerCase3.equals("new")) {
                                z3 = true;
                                break;
                            }
                            break;
                        case 110119:
                            if (lowerCase3.equals("old")) {
                                z3 = false;
                                break;
                            }
                            break;
                    }
                    switch (z3) {
                        case false:
                            if ((f362mc.field_71439_g.field_70123_F && isNearBlock()) || !(BlockUtils.getBlock(new BlockPos(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u + 2.0d, f362mc.field_71439_g.field_70161_v)) instanceof BlockAir)) {
                                boost(this.wallBoostValue.get().floatValue());
                                return;
                            }
                            break;
                        case true:
                            if (isNearBlock() && !f362mc.field_71439_g.field_71158_b.field_78901_c) {
                                f362mc.field_71439_g.func_70664_aZ();
                                f362mc.field_71439_g.field_70181_x = 0.08d;
                                f362mc.field_71439_g.field_70159_w *= 0.99d;
                                f362mc.field_71439_g.field_70179_y *= 0.99d;
                                this.down = true;
                                return;
                            }
                            break;
                    }
                }
                float currentSpeed = MovementUtils.getSpeed();
                if (this.speed < currentSpeed) {
                    this.speed = currentSpeed;
                }
                if (this.bufferValue.get().booleanValue() && this.speed > 0.20000000298023224d) {
                    this.speed /= 1.0199999809265137d;
                    MovementUtils.strafe((float) this.speed);
                }
            }
        } else {
            this.speed = 0.0d;
            if (this.airStrafeValue.get().booleanValue()) {
                MovementUtils.strafe();
            }
        }
    }

    @EventTarget
    public void onPacket(PacketEvent event) {
        Packet<?> packet = event.getPacket();
        if (packet instanceof S08PacketPlayerPosLook) {
            this.speed = 0.0d;
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        reset();
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        reset();
    }

    private void reset() {
        if (f362mc.field_71439_g == null) {
            return;
        }
        this.legitHop = true;
        this.speed = 0.0d;
        if (this.hadFastHop) {
            f362mc.field_71439_g.field_71102_ce = 0.02f;
            this.hadFastHop = false;
        }
    }

    private void boost(float boost) {
        f362mc.field_71439_g.field_70159_w *= boost;
        f362mc.field_71439_g.field_70179_y *= boost;
        this.speed = MovementUtils.getSpeed();
        if (this.speedLimitValue.get().booleanValue() && this.speed > this.maxSpeedValue.get().floatValue()) {
            this.speed = this.maxSpeedValue.get().floatValue();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x00a8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean isNearBlock() {
        /*
            Method dump skipped, instructions count: 289
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.features.module.modules.movement.BufferSpeed.isNearBlock():boolean");
    }
}
