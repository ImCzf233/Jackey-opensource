package net.ccbluex.liquidbounce.features.module.modules.movement;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.PosLookInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@ModuleInfo(name = "LongJump", spacedName = "Long Jump", description = "Allows you to jump further.", category = ModuleCategory.MOVEMENT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/LongJump.class */
public class LongJump extends Module {
    private boolean jumped;
    private boolean canBoost;
    private boolean teleported;
    private boolean canMineplexBoost;
    private boolean verusDmged;
    private boolean hpxDamage;
    private double lastMotX;
    private double lastMotY;
    private double lastMotZ;
    private final ListValue modeValue = new ListValue("Mode", new String[]{"NCP", "Damage", "AACv1", "AACv2", "AACv3", "AACv4", "Mineplex", "Mineplex2", "Mineplex3", "RedeskyMaki", "Redesky", "InfiniteRedesky", "MatrixFlag", "VerusDmg", "Pearl"}, "NCP");
    private final BoolValue autoJumpValue = new BoolValue("AutoJump", false);
    private final FloatValue ncpBoostValue = new FloatValue("NCPBoost", 4.25f, 1.0f, 10.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("ncp"));
    });
    private final FloatValue matrixBoostValue = new FloatValue("MatrixFlag-Boost", 1.95f, 0.0f, 10.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("matrixflag"));
    });
    private final FloatValue matrixHeightValue = new FloatValue("MatrixFlag-Height", 5.0f, 0.0f, 10.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("matrixflag"));
    });
    private final BoolValue matrixKeepAliveValue = new BoolValue("MatrixFlag-KeepAlive", true, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("matrixflag"));
    });
    private final BoolValue matrixJBAValue = new BoolValue("MatrixFlag-JumpBeforeActivation", true, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("matrixflag"));
    });
    private final BoolValue matrixJumpValue = new BoolValue("MatrixFlag-KeepJump", true, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("matrixflag"));
    });
    private final BoolValue redeskyTimerBoostValue = new BoolValue("Redesky-TimerBoost", false, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("redesky"));
    });
    private final BoolValue redeskyGlideAfterTicksValue = new BoolValue("Redesky-GlideAfterTicks", false, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("redesky"));
    });
    private final IntegerValue redeskyTickValue = new IntegerValue("Redesky-Ticks", 21, 1, 25, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("redesky"));
    });
    private final FloatValue redeskyYMultiplier = new FloatValue("Redesky-YMultiplier", 0.77f, 0.1f, 1.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("redesky"));
    });
    private final FloatValue redeskyXZMultiplier = new FloatValue("Redesky-XZMultiplier", 0.9f, 0.1f, 1.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("redesky"));
    });
    private final FloatValue redeskyTimerBoostStartValue = new FloatValue("Redesky-TimerBoostStart", 1.85f, 0.05f, 10.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("redesky") && this.redeskyTimerBoostValue.get().booleanValue());
    });
    private final FloatValue redeskyTimerBoostEndValue = new FloatValue("Redesky-TimerBoostEnd", 1.0f, 0.05f, 10.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("redesky") && this.redeskyTimerBoostValue.get().booleanValue());
    });
    private final IntegerValue redeskyTimerBoostSlowDownSpeedValue = new IntegerValue("Redesky-TimerBoost-SlowDownSpeed", 2, 1, 10, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("redesky") && this.redeskyTimerBoostValue.get().booleanValue());
    });
    private final ListValue verusDmgModeValue = new ListValue("VerusDmg-DamageMode", new String[]{"Instant", "InstantC06", "Jump"}, "None", () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("verusdmg"));
    });
    private final FloatValue verusBoostValue = new FloatValue("VerusDmg-Boost", 4.25f, 0.0f, 10.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("verusdmg"));
    });
    private final FloatValue verusHeightValue = new FloatValue("VerusDmg-Height", 0.42f, 0.0f, 10.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("verusdmg"));
    });
    private final FloatValue verusTimerValue = new FloatValue("VerusDmg-Timer", 1.0f, 0.05f, 10.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("verusdmg"));
    });
    private final FloatValue pearlBoostValue = new FloatValue("Pearl-Boost", 4.25f, 0.0f, 10.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("pearl"));
    });
    private final FloatValue pearlHeightValue = new FloatValue("Pearl-Height", 0.42f, 0.0f, 10.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("pearl"));
    });
    private final FloatValue pearlTimerValue = new FloatValue("Pearl-Timer", 1.0f, 0.05f, 10.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("pearl"));
    });
    private final FloatValue damageBoostValue = new FloatValue("Damage-Boost", 4.25f, 0.0f, 10.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("damage"));
    });
    private final FloatValue damageHeightValue = new FloatValue("Damage-Height", 0.42f, 0.0f, 10.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("damage"));
    });
    private final FloatValue damageTimerValue = new FloatValue("Damage-Timer", 1.0f, 0.05f, 10.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("damage"));
    });
    private final BoolValue damageNoMoveValue = new BoolValue("Damage-NoMove", false, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("damage"));
    });
    private final BoolValue damageARValue = new BoolValue("Damage-AutoReset", false, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("damage"));
    });
    private int ticks = 0;
    private float currentTimer = 1.0f;
    private boolean damaged = false;
    private int verusJumpTimes = 0;
    private int pearlState = 0;
    private boolean flagged = false;
    private boolean hasFell = false;
    private MSTimer dmgTimer = new MSTimer();
    private PosLookInstance posLookInstance = new PosLookInstance();

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        if (f362mc.field_71439_g == null) {
            return;
        }
        if (this.modeValue.get().equalsIgnoreCase("redesky") && this.redeskyTimerBoostValue.get().booleanValue()) {
            this.currentTimer = this.redeskyTimerBoostStartValue.get().floatValue();
        }
        this.ticks = 0;
        this.verusDmged = false;
        this.hpxDamage = false;
        this.damaged = false;
        this.flagged = false;
        this.hasFell = false;
        this.pearlState = 0;
        this.verusJumpTimes = 0;
        this.dmgTimer.reset();
        this.posLookInstance.reset();
        double d = f362mc.field_71439_g.field_70165_t;
        double y = f362mc.field_71439_g.field_70163_u;
        double d2 = f362mc.field_71439_g.field_70161_v;
        if (this.modeValue.get().equalsIgnoreCase("verusdmg")) {
            if (this.verusDmgModeValue.get().equalsIgnoreCase("Instant")) {
                if (f362mc.field_71439_g.field_70122_E && f362mc.field_71441_e.func_72945_a(f362mc.field_71439_g, f362mc.field_71439_g.func_174813_aQ().func_72317_d(0.0d, 4.0d, 0.0d).func_72314_b(0.0d, 0.0d, 0.0d)).isEmpty()) {
                    PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(f362mc.field_71439_g.field_70165_t, y + 4.0d, f362mc.field_71439_g.field_70161_v, false));
                    PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(f362mc.field_71439_g.field_70165_t, y, f362mc.field_71439_g.field_70161_v, false));
                    PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(f362mc.field_71439_g.field_70165_t, y, f362mc.field_71439_g.field_70161_v, true));
                    EntityPlayerSP entityPlayerSP = f362mc.field_71439_g;
                    f362mc.field_71439_g.field_70179_y = 0.0d;
                    entityPlayerSP.field_70159_w = 0.0d;
                }
            } else if (this.verusDmgModeValue.get().equalsIgnoreCase("InstantC06")) {
                if (f362mc.field_71439_g.field_70122_E && f362mc.field_71441_e.func_72945_a(f362mc.field_71439_g, f362mc.field_71439_g.func_174813_aQ().func_72317_d(0.0d, 4.0d, 0.0d).func_72314_b(0.0d, 0.0d, 0.0d)).isEmpty()) {
                    PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(f362mc.field_71439_g.field_70165_t, y + 4.0d, f362mc.field_71439_g.field_70161_v, f362mc.field_71439_g.field_70177_z, f362mc.field_71439_g.field_70125_A, false));
                    PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(f362mc.field_71439_g.field_70165_t, y, f362mc.field_71439_g.field_70161_v, f362mc.field_71439_g.field_70177_z, f362mc.field_71439_g.field_70125_A, false));
                    PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(f362mc.field_71439_g.field_70165_t, y, f362mc.field_71439_g.field_70161_v, f362mc.field_71439_g.field_70177_z, f362mc.field_71439_g.field_70125_A, true));
                    EntityPlayerSP entityPlayerSP2 = f362mc.field_71439_g;
                    f362mc.field_71439_g.field_70179_y = 0.0d;
                    entityPlayerSP2.field_70159_w = 0.0d;
                }
            } else if (this.verusDmgModeValue.get().equalsIgnoreCase("Jump") && f362mc.field_71439_g.field_70122_E) {
                f362mc.field_71439_g.func_70664_aZ();
                this.verusJumpTimes = 1;
            }
        }
        if (this.modeValue.get().equalsIgnoreCase("matrixflag")) {
            if (this.matrixJBAValue.get().booleanValue()) {
                if (f362mc.field_71439_g.field_70122_E) {
                    f362mc.field_71439_g.func_70664_aZ();
                    return;
                }
                return;
            }
            this.hasFell = true;
        }
    }

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if (this.modeValue.get().equalsIgnoreCase("matrixflag")) {
            if (this.hasFell) {
                if (!this.flagged) {
                    if (this.matrixJumpValue.get().booleanValue()) {
                        f362mc.field_71439_g.func_70664_aZ();
                    }
                    MovementUtils.strafe(this.matrixBoostValue.get().floatValue());
                    f362mc.field_71439_g.field_70181_x = this.matrixHeightValue.get().floatValue();
                    if (this.matrixKeepAliveValue.get().booleanValue()) {
                        f362mc.func_147114_u().func_147297_a(new C00PacketKeepAlive());
                        return;
                    }
                    return;
                }
                return;
            }
            f362mc.field_71439_g.field_70159_w *= 0.2d;
            f362mc.field_71439_g.field_70179_y *= 0.2d;
            if (f362mc.field_71439_g.field_70143_R > 0.0f) {
                this.hasFell = true;
            }
        } else if (this.modeValue.get().equalsIgnoreCase("verusdmg")) {
            if (f362mc.field_71439_g.field_70737_aN > 0 && !this.verusDmged) {
                this.verusDmged = true;
                MovementUtils.strafe(this.verusBoostValue.get().floatValue());
                f362mc.field_71439_g.field_70181_x = this.verusHeightValue.get().floatValue();
            }
            if (this.verusDmgModeValue.get().equalsIgnoreCase("Jump") && this.verusJumpTimes < 5) {
                if (f362mc.field_71439_g.field_70122_E) {
                    f362mc.field_71439_g.func_70664_aZ();
                    this.verusJumpTimes++;
                }
            } else if (this.verusDmged) {
                f362mc.field_71428_T.field_74278_d = this.verusTimerValue.get().floatValue();
            } else {
                f362mc.field_71439_g.field_71158_b.field_78900_b = 0.0f;
                f362mc.field_71439_g.field_71158_b.field_78902_a = 0.0f;
                if (!this.verusDmgModeValue.get().equalsIgnoreCase("Jump")) {
                    f362mc.field_71439_g.field_70181_x = 0.0d;
                }
            }
        } else if (this.modeValue.get().equalsIgnoreCase("damage")) {
            if (f362mc.field_71439_g.field_70737_aN > 0 && !this.damaged) {
                this.damaged = true;
                MovementUtils.strafe(this.damageBoostValue.get().floatValue());
                f362mc.field_71439_g.field_70181_x = this.damageHeightValue.get().floatValue();
            }
            if (this.damaged) {
                f362mc.field_71428_T.field_74278_d = this.damageTimerValue.get().floatValue();
                if (!this.damageARValue.get().booleanValue() || f362mc.field_71439_g.field_70737_aN > 0) {
                    return;
                }
                this.damaged = false;
            }
        } else if (this.modeValue.get().equalsIgnoreCase("pearl")) {
            int enderPearlSlot = getPearlSlot();
            if (this.pearlState == 0) {
                if (enderPearlSlot == -1) {
                    LiquidBounce.hud.addNotification(new Notification("You don't have any ender pearl!", Notification.Type.ERROR));
                    this.pearlState = -1;
                    setState(false);
                    return;
                }
                if (f362mc.field_71439_g.field_71071_by.field_70461_c != enderPearlSlot) {
                    f362mc.field_71439_g.field_71174_a.func_147297_a(new C09PacketHeldItemChange(enderPearlSlot));
                }
                f362mc.field_71439_g.field_71174_a.func_147297_a(new C03PacketPlayer.C05PacketPlayerLook(f362mc.field_71439_g.field_70177_z, 90.0f, f362mc.field_71439_g.field_70122_E));
                f362mc.field_71439_g.field_71174_a.func_147297_a(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, f362mc.field_71439_g.field_71069_bz.func_75139_a(enderPearlSlot + 36).func_75211_c(), 0.0f, 0.0f, 0.0f));
                if (enderPearlSlot != f362mc.field_71439_g.field_71071_by.field_70461_c) {
                    f362mc.field_71439_g.field_71174_a.func_147297_a(new C09PacketHeldItemChange(f362mc.field_71439_g.field_71071_by.field_70461_c));
                }
                this.pearlState = 1;
            }
            if (this.pearlState == 1 && f362mc.field_71439_g.field_70737_aN > 0) {
                this.pearlState = 2;
                MovementUtils.strafe(this.pearlBoostValue.get().floatValue());
                f362mc.field_71439_g.field_70181_x = this.pearlHeightValue.get().floatValue();
            }
            if (this.pearlState == 2) {
                f362mc.field_71428_T.field_74278_d = this.pearlTimerValue.get().floatValue();
            }
        } else {
            if (this.jumped) {
                String mode = this.modeValue.get();
                if (f362mc.field_71439_g.field_70122_E || f362mc.field_71439_g.field_71075_bZ.field_75100_b) {
                    this.jumped = false;
                    this.canMineplexBoost = false;
                    if (mode.equalsIgnoreCase("NCP")) {
                        f362mc.field_71439_g.field_70159_w = 0.0d;
                        f362mc.field_71439_g.field_70179_y = 0.0d;
                        return;
                    }
                    return;
                }
                String lowerCase = mode.toLowerCase();
                boolean z = true;
                switch (lowerCase.hashCode()) {
                    case -1362669950:
                        if (lowerCase.equals("mineplex")) {
                            z = true;
                            break;
                        }
                        break;
                    case -1339390439:
                        if (lowerCase.equals("infiniteredesky")) {
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
                    case 92570110:
                        if (lowerCase.equals("aacv1")) {
                            z = true;
                            break;
                        }
                        break;
                    case 92570111:
                        if (lowerCase.equals("aacv2")) {
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
                    case 92570113:
                        if (lowerCase.equals("aacv4")) {
                            z = true;
                            break;
                        }
                        break;
                    case 373462559:
                        if (lowerCase.equals("redeskymaki")) {
                            z = true;
                            break;
                        }
                        break;
                    case 706904560:
                        if (lowerCase.equals("mineplex2")) {
                            z = true;
                            break;
                        }
                        break;
                    case 706904561:
                        if (lowerCase.equals("mineplex3")) {
                            z = true;
                            break;
                        }
                        break;
                    case 1083223725:
                        if (lowerCase.equals("redesky")) {
                            z = true;
                            break;
                        }
                        break;
                }
                switch (z) {
                    case false:
                        MovementUtils.strafe(MovementUtils.getSpeed() * (this.canBoost ? this.ncpBoostValue.get().floatValue() : 1.0f));
                        this.canBoost = false;
                        break;
                    case true:
                        f362mc.field_71439_g.field_70181_x += 0.05999d;
                        MovementUtils.strafe(MovementUtils.getSpeed() * 1.08f);
                        break;
                    case true:
                    case true:
                        f362mc.field_71439_g.field_70747_aH = 0.09f;
                        f362mc.field_71439_g.field_70181_x += 0.01321d;
                        f362mc.field_71439_g.field_70747_aH = 0.08f;
                        MovementUtils.strafe();
                        break;
                    case true:
                        EntityPlayerSP player = f362mc.field_71439_g;
                        if (player.field_70143_R > 0.5f && !this.teleported) {
                            EnumFacing horizontalFacing = player.func_174811_aO();
                            double x = 0.0d;
                            double z2 = 0.0d;
                            switch (C16691.$SwitchMap$net$minecraft$util$EnumFacing[horizontalFacing.ordinal()]) {
                                case 1:
                                    z2 = -3.0d;
                                    break;
                                case 2:
                                    x = 3.0d;
                                    break;
                                case 3:
                                    z2 = 3.0d;
                                    break;
                                case 4:
                                    x = -3.0d;
                                    break;
                            }
                            player.func_70107_b(player.field_70165_t + x, player.field_70163_u, player.field_70161_v + z2);
                            this.teleported = true;
                            break;
                        }
                        break;
                    case true:
                        f362mc.field_71439_g.field_70181_x += 0.01321d;
                        f362mc.field_71439_g.field_70747_aH = 0.08f;
                        MovementUtils.strafe();
                        break;
                    case true:
                        if (this.canMineplexBoost) {
                            f362mc.field_71439_g.field_70747_aH = 0.1f;
                            if (f362mc.field_71439_g.field_70143_R > 1.5f) {
                                f362mc.field_71439_g.field_70747_aH = 0.0f;
                                f362mc.field_71439_g.field_70181_x = -10.0d;
                            }
                            MovementUtils.strafe();
                            break;
                        }
                        break;
                    case true:
                        f362mc.field_71439_g.field_70747_aH = 0.05837456f;
                        f362mc.field_71428_T.field_74278_d = 0.5f;
                        break;
                    case true:
                        f362mc.field_71439_g.field_70747_aH = 0.15f;
                        f362mc.field_71439_g.field_70181_x += 0.05000000074505806d;
                        break;
                    case true:
                        if (this.redeskyTimerBoostValue.get().booleanValue()) {
                            f362mc.field_71428_T.field_74278_d = this.currentTimer;
                        }
                        if (this.ticks < this.redeskyTickValue.get().intValue()) {
                            f362mc.field_71439_g.func_70664_aZ();
                            f362mc.field_71439_g.field_70181_x *= this.redeskyYMultiplier.get().floatValue();
                            f362mc.field_71439_g.field_70159_w *= this.redeskyXZMultiplier.get().floatValue();
                            f362mc.field_71439_g.field_70179_y *= this.redeskyXZMultiplier.get().floatValue();
                        } else {
                            if (this.redeskyGlideAfterTicksValue.get().booleanValue()) {
                                f362mc.field_71439_g.field_70181_x += 0.029999999329447746d;
                            }
                            if (this.redeskyTimerBoostValue.get().booleanValue() && this.currentTimer > this.redeskyTimerBoostEndValue.get().floatValue()) {
                                this.currentTimer = Math.max(0.08f, this.currentTimer - (0.05f * this.redeskyTimerBoostSlowDownSpeedValue.get().intValue()));
                            }
                        }
                        this.ticks++;
                        break;
                    case true:
                        if (f362mc.field_71439_g.field_70143_R > 0.6f) {
                            f362mc.field_71439_g.field_70181_x += 0.019999999552965164d;
                        }
                        MovementUtils.strafe((float) Math.min(0.85d, Math.max(0.25d, MovementUtils.getSpeed() * 1.05878d)));
                        break;
                }
            }
            if (this.autoJumpValue.get().booleanValue() && f362mc.field_71439_g.field_70122_E && MovementUtils.isMoving()) {
                this.jumped = true;
                f362mc.field_71439_g.func_70664_aZ();
            }
        }
    }

    /* renamed from: net.ccbluex.liquidbounce.features.module.modules.movement.LongJump$1 */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/LongJump$1.class */
    static /* synthetic */ class C16691 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$util$EnumFacing = new int[EnumFacing.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.NORTH.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.EAST.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.SOUTH.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.WEST.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    @EventTarget
    public void onMove(MoveEvent event) {
        String mode = this.modeValue.get();
        if (mode.equalsIgnoreCase("mineplex3")) {
            if (f362mc.field_71439_g.field_70143_R != 0.0f) {
                f362mc.field_71439_g.field_70181_x += 0.037d;
            }
        } else if (mode.equalsIgnoreCase("ncp") && !MovementUtils.isMoving() && this.jumped) {
            f362mc.field_71439_g.field_70159_w = 0.0d;
            f362mc.field_71439_g.field_70179_y = 0.0d;
            event.zeroXZ();
        }
        if ((mode.equalsIgnoreCase("damage") && this.damageNoMoveValue.get().booleanValue() && !this.damaged) || (mode.equalsIgnoreCase("verusdmg") && !this.verusDmged)) {
            event.zeroXZ();
        }
        if (mode.equalsIgnoreCase("pearl") && this.pearlState != 2) {
            event.cancelEvent();
        }
    }

    @EventTarget
    public void onPacket(PacketEvent event) {
        String mode = this.modeValue.get();
        if (event.getPacket() instanceof C03PacketPlayer) {
            C03PacketPlayer packetPlayer = event.getPacket();
            if (mode.equalsIgnoreCase("verusdmg") && this.verusDmgModeValue.get().equalsIgnoreCase("Jump") && this.verusJumpTimes < 5) {
                packetPlayer.field_149474_g = false;
            }
        }
        if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            this.flagged = true;
            this.posLookInstance.set((S08PacketPlayerPosLook) event.getPacket());
            this.lastMotX = f362mc.field_71439_g.field_70159_w;
            this.lastMotY = f362mc.field_71439_g.field_70181_x;
            this.lastMotZ = f362mc.field_71439_g.field_70179_y;
        }
        if ((event.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook) && this.posLookInstance.equalFlag((C03PacketPlayer.C06PacketPlayerPosLook) event.getPacket())) {
            this.posLookInstance.reset();
            ClientUtils.displayChatMessage("§a§lLaunched!");
            f362mc.field_71439_g.field_70159_w = this.lastMotX;
            f362mc.field_71439_g.field_70181_x = this.lastMotY;
            f362mc.field_71439_g.field_70179_y = this.lastMotZ;
        }
    }

    @EventTarget(ignoreCondition = true)
    public void onJump(JumpEvent event) {
        this.jumped = true;
        this.canBoost = true;
        this.teleported = false;
        if (getState()) {
            String lowerCase = this.modeValue.get().toLowerCase();
            boolean z = true;
            switch (lowerCase.hashCode()) {
                case -1362669950:
                    if (lowerCase.equals("mineplex")) {
                        z = false;
                        break;
                    }
                    break;
                case 92570113:
                    if (lowerCase.equals("aacv4")) {
                        z = true;
                        break;
                    }
                    break;
                case 706904560:
                    if (lowerCase.equals("mineplex2")) {
                        z = true;
                        break;
                    }
                    break;
            }
            switch (z) {
                case false:
                    event.setMotion(event.getMotion() * 4.08f);
                    return;
                case true:
                    if (f362mc.field_71439_g.field_70123_F) {
                        event.setMotion(2.31f);
                        this.canMineplexBoost = true;
                        f362mc.field_71439_g.field_70122_E = false;
                        return;
                    }
                    return;
                case true:
                    event.setMotion(event.getMotion() * 1.0799f);
                    return;
                default:
                    return;
            }
        }
    }

    private int getPearlSlot() {
        for (int i = 36; i < 45; i++) {
            ItemStack stack = f362mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
            if (stack != null && (stack.func_77973_b() instanceof ItemEnderPearl)) {
                return i - 36;
            }
        }
        return -1;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        f362mc.field_71428_T.field_74278_d = 1.0f;
        f362mc.field_71439_g.field_71102_ce = 0.02f;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public String getTag() {
        return this.modeValue.get();
    }
}
