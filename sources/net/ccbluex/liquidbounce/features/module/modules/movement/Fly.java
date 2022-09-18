package net.ccbluex.liquidbounce.features.module.modules.movement;

import com.viaversion.viaversion.libs.javassist.compiler.TokenId;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TickTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockSlime;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@ModuleInfo(name = "Fly", description = "Allows you to fly in survival mode.", category = ModuleCategory.MOVEMENT, keyBind = 33)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/Fly.class */
public class Fly extends Module {
    private BlockPos lastPosition;
    private double startY;
    private boolean shouldFakeJump;
    private boolean noPacketModify;
    private boolean noFlag;
    private boolean wasDead;
    private int boostTicks;
    public int wdState;
    private boolean verusDmged;
    private float lastYaw;
    private float lastPitch;
    private double aacJump;
    private int aac3delay;
    private int aac3glideDelay;
    private long minesuchtTP;
    private double lastDistance;
    private float freeHypixelYaw;
    private float freeHypixelPitch;
    public final ListValue modeValue = new ListValue("Mode", new String[]{"Motion", "Creative", "Damage", "Pearl", "NCP", "OldNCP", "AAC1.9.10", "AAC3.0.5", "AAC3.1.6-Gomme", "AAC3.3.12", "AAC3.3.12-Glide", "AAC3.3.13", "AAC5-Vanilla", "CubeCraft", "Rewinside", "TeleportRewinside", "FunCraft", "Mineplex", "NeruxVace", "Minesucht", "Verus", "VerusLowHop", "Spartan", "Spartan2", "BugSpartan", "Hypixel", "BoostHypixel", "FreeHypixel", "MineSecure", "HawkEye", "HAC", "WatchCat", "Watchdog", "Jetpack", "KeepAlive", "Flag", "Clip", "Jump", "Derp", "Collide"}, "Motion");
    private final FloatValue vanillaSpeedValue = new FloatValue("Speed", 2.0f, 0.0f, 5.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("motion") || this.modeValue.get().equalsIgnoreCase("damage") || this.modeValue.get().equalsIgnoreCase("pearl") || this.modeValue.get().equalsIgnoreCase("aac5-vanilla") || this.modeValue.get().equalsIgnoreCase("bugspartan") || this.modeValue.get().equalsIgnoreCase("keepalive") || this.modeValue.get().equalsIgnoreCase("derp"));
    });
    private final FloatValue vanillaVSpeedValue = new FloatValue("V-Speed", 2.0f, 0.0f, 5.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("motion"));
    });
    private final FloatValue vanillaMotionYValue = new FloatValue("Y-Motion", 0.0f, -1.0f, 1.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("motion"));
    });
    private final BoolValue vanillaKickBypassValue = new BoolValue("KickBypass", false, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("motion") || this.modeValue.get().equalsIgnoreCase("creative"));
    });
    private final BoolValue groundSpoofValue = new BoolValue("GroundSpoof", false, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("motion") || this.modeValue.get().equalsIgnoreCase("creative"));
    });
    private final FloatValue ncpMotionValue = new FloatValue("NCPMotion", 0.0f, 0.0f, 1.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("ncp"));
    });
    private final ListValue verusDmgModeValue = new ListValue("Verus-DamageMode", new String[]{"None", "Instant", "InstantC06", "Jump"}, "None", () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("verus"));
    });
    private final ListValue verusBoostModeValue = new ListValue("Verus-BoostMode", new String[]{"Static", "Gradual"}, "Gradual", () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("verus") && !this.verusDmgModeValue.get().equalsIgnoreCase("none"));
    });
    private final BoolValue verusReDamageValue = new BoolValue("Verus-ReDamage", true, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("verus") && !this.verusDmgModeValue.get().equalsIgnoreCase("none") && !this.verusDmgModeValue.get().equalsIgnoreCase("jump"));
    });
    private final IntegerValue verusReDmgTickValue = new IntegerValue("Verus-ReDamage-Ticks", 20, 0, (int) TokenId.ABSTRACT, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("verus") && !this.verusDmgModeValue.get().equalsIgnoreCase("none") && !this.verusDmgModeValue.get().equalsIgnoreCase("jump") && this.verusReDamageValue.get().booleanValue());
    });
    private final BoolValue verusVisualValue = new BoolValue("Verus-VisualPos", false, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("verus"));
    });
    private final FloatValue verusVisualHeightValue = new FloatValue("Verus-VisualHeight", 0.42f, 0.0f, 1.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("verus") && this.verusVisualValue.get().booleanValue());
    });
    private final FloatValue verusSpeedValue = new FloatValue("Verus-Speed", 5.0f, 0.0f, 10.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("verus") && !this.verusDmgModeValue.get().equalsIgnoreCase("none"));
    });
    private final FloatValue verusTimerValue = new FloatValue("Verus-Timer", 1.0f, 0.1f, 10.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("verus") && !this.verusDmgModeValue.get().equalsIgnoreCase("none"));
    });
    private final IntegerValue verusDmgTickValue = new IntegerValue("Verus-Ticks", 20, 0, (int) TokenId.ABSTRACT, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("verus") && !this.verusDmgModeValue.get().equalsIgnoreCase("none"));
    });
    private final BoolValue verusSpoofGround = new BoolValue("Verus-SpoofGround", false, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("verus"));
    });
    private final BoolValue aac5NoClipValue = new BoolValue("AAC5-NoClip", true, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("aac5-vanilla"));
    });
    private final BoolValue aac5NofallValue = new BoolValue("AAC5-NoFall", true, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("aac5-vanilla"));
    });
    private final BoolValue aac5UseC04Packet = new BoolValue("AAC5-UseC04", true, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("aac5-vanilla"));
    });
    private final ListValue aac5Packet = new ListValue("AAC5-Packet", new String[]{"Original", "Rise", "Other"}, "Original", () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("aac5-vanilla"));
    });
    private final IntegerValue aac5PursePacketsValue = new IntegerValue("AAC5-Purse", 7, 3, 20, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("aac5-vanilla"));
    });
    private final IntegerValue clipDelay = new IntegerValue("Clip-DelayTick", 25, 1, 50, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("clip"));
    });
    private final FloatValue clipH = new FloatValue("Clip-Horizontal", 7.9f, 0.0f, 10.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("clip"));
    });
    private final FloatValue clipV = new FloatValue("Clip-Vertical", 1.75f, -10.0f, 10.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("clip"));
    });
    private final FloatValue clipMotionY = new FloatValue("Clip-MotionY", 0.0f, -2.0f, 2.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("clip"));
    });
    private final FloatValue clipTimer = new FloatValue("Clip-Timer", 1.0f, 0.08f, 10.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("clip"));
    });
    private final BoolValue clipGroundSpoof = new BoolValue("Clip-GroundSpoof", true, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("clip"));
    });
    private final BoolValue clipCollisionCheck = new BoolValue("Clip-CollisionCheck", true, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("clip"));
    });
    private final BoolValue clipNoMove = new BoolValue("Clip-NoMove", true, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("clip"));
    });
    private final ListValue pearlActivateCheck = new ListValue("PearlActiveCheck", new String[]{"Teleport", "Damage"}, "Teleport", () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("pearl"));
    });
    private final FloatValue aacSpeedValue = new FloatValue("AAC1.9.10-Speed", 0.3f, 0.0f, 1.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("aac1.9.10"));
    });
    private final BoolValue aacFast = new BoolValue("AAC3.0.5-Fast", true, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("aac3.0.5"));
    });
    private final FloatValue aacMotion = new FloatValue("AAC3.3.12-Motion", 10.0f, 0.1f, 10.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("aac3.3.12"));
    });
    private final FloatValue aacMotion2 = new FloatValue("AAC3.3.13-Motion", 10.0f, 0.1f, 10.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("aac3.3.13"));
    });
    private final ListValue hypixelBoostMode = new ListValue("BoostHypixel-Mode", new String[]{"Default", "MorePackets", "NCP"}, "Default", () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("boosthypixel"));
    });
    private final BoolValue hypixelVisualY = new BoolValue("BoostHypixel-VisualY", true, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("boosthypixel"));
    });
    private final BoolValue hypixelC04 = new BoolValue("BoostHypixel-MoreC04s", false, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("boosthypixel"));
    });
    private final BoolValue hypixelBoost = new BoolValue("Hypixel-Boost", true, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("hypixel"));
    });
    private final IntegerValue hypixelBoostDelay = new IntegerValue("Hypixel-BoostDelay", 1200, 0, 2000, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("hypixel"));
    });
    private final FloatValue hypixelBoostTimer = new FloatValue("Hypixel-BoostTimer", 1.0f, 0.0f, 5.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("hypixel"));
    });
    private final FloatValue mineplexSpeedValue = new FloatValue("MineplexSpeed", 1.0f, 0.5f, 10.0f, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("mineplex"));
    });
    private final IntegerValue neruxVaceTicks = new IntegerValue("NeruxVace-Ticks", 6, 0, 20, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("neruxvace"));
    });
    private final BoolValue resetMotionValue = new BoolValue("ResetMotion", true);
    private final BoolValue fakeDmgValue = new BoolValue("FakeDamage", true);
    private final BoolValue bobbingValue = new BoolValue("Bobbing", true);
    private final FloatValue bobbingAmountValue = new FloatValue("BobbingAmount", 0.2f, 0.0f, 1.0f, () -> {
        return this.bobbingValue.get();
    });
    private final BoolValue markValue = new BoolValue("Mark", true);
    private final MSTimer flyTimer = new MSTimer();
    private final MSTimer groundTimer = new MSTimer();
    private final MSTimer boostTimer = new MSTimer();
    private final MSTimer wdTimer = new MSTimer();
    private final MSTimer mineSecureVClipTimer = new MSTimer();
    private final MSTimer mineplexTimer = new MSTimer();
    private final TickTimer spartanTimer = new TickTimer();
    private final TickTimer verusTimer = new TickTimer();
    private final TickTimer hypixelTimer = new TickTimer();
    private final TickTimer cubecraftTeleportTickTimer = new TickTimer();
    private final TickTimer freeHypixelTimer = new TickTimer();
    private boolean shouldActive = false;
    private boolean isBoostActive = false;
    private int pearlState = 0;
    private int dmgCooldown = 0;
    private int verusJumpTimes = 0;
    public int wdTick = 0;
    private boolean shouldActiveDmg = false;
    private double moveSpeed = 0.0d;
    private int expectItemStack = -1;
    private int boostHypixelState = 1;
    private boolean failedStart = false;
    private final ArrayList<C03PacketPlayer> aac5C03List = new ArrayList<>();

    private void doMove(double h, double v) {
        if (f362mc.field_71439_g == null) {
            return;
        }
        double x = f362mc.field_71439_g.field_70165_t;
        double y = f362mc.field_71439_g.field_70163_u;
        double z = f362mc.field_71439_g.field_70161_v;
        double yaw = Math.toRadians(f362mc.field_71439_g.field_70177_z);
        double expectedX = x + ((-Math.sin(yaw)) * h);
        double expectedY = y + v;
        double expectedZ = z + (Math.cos(yaw) * h);
        f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(expectedX, expectedY, expectedZ, f362mc.field_71439_g.field_70122_E));
        f362mc.field_71439_g.func_70107_b(expectedX, expectedY, expectedZ);
    }

    private void hClip(double x, double y, double z) {
        if (f362mc.field_71439_g == null) {
            return;
        }
        double expectedX = f362mc.field_71439_g.field_70165_t + x;
        double expectedY = f362mc.field_71439_g.field_70163_u + y;
        double expectedZ = f362mc.field_71439_g.field_70161_v + z;
        f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(expectedX, expectedY, expectedZ, f362mc.field_71439_g.field_70122_E));
        f362mc.field_71439_g.func_70107_b(expectedX, expectedY, expectedZ);
    }

    private double[] getMoves(double h, double v) {
        if (f362mc.field_71439_g == null) {
            return new double[]{0.0d, 0.0d, 0.0d};
        }
        double yaw = Math.toRadians(f362mc.field_71439_g.field_70177_z);
        double expectedX = (-Math.sin(yaw)) * h;
        double expectedZ = Math.cos(yaw) * h;
        return new double[]{expectedX, v, expectedZ};
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        if (f362mc.field_71439_g == null) {
            return;
        }
        this.noPacketModify = true;
        this.verusTimer.reset();
        this.flyTimer.reset();
        this.shouldFakeJump = false;
        this.shouldActive = true;
        this.isBoostActive = false;
        this.expectItemStack = -1;
        double x = f362mc.field_71439_g.field_70165_t;
        double y = f362mc.field_71439_g.field_70163_u;
        double z = f362mc.field_71439_g.field_70161_v;
        this.lastYaw = f362mc.field_71439_g.field_70177_z;
        this.lastPitch = f362mc.field_71439_g.field_70125_A;
        String mode = this.modeValue.get();
        this.boostTicks = 0;
        this.dmgCooldown = 0;
        this.pearlState = 0;
        this.verusJumpTimes = 0;
        this.verusDmged = false;
        this.moveSpeed = 0.0d;
        this.wdState = 0;
        this.wdTick = 0;
        String lowerCase = mode.toLowerCase();
        boolean z2 = true;
        switch (lowerCase.hashCode()) {
            case -1693125473:
                if (lowerCase.equals("bugspartan")) {
                    z2 = true;
                    break;
                }
                break;
            case -1014303276:
                if (lowerCase.equals("oldncp")) {
                    z2 = true;
                    break;
                }
                break;
            case 108891:
                if (lowerCase.equals("ncp")) {
                    z2 = false;
                    break;
                }
                break;
            case 112097665:
                if (lowerCase.equals("verus")) {
                    z2 = true;
                    break;
                }
                break;
            case 545151501:
                if (lowerCase.equals("watchdog")) {
                    z2 = true;
                    break;
                }
                break;
            case 1380871169:
                if (lowerCase.equals("funcraft")) {
                    z2 = true;
                    break;
                }
                break;
            case 1814517522:
                if (lowerCase.equals("boosthypixel")) {
                    z2 = true;
                    break;
                }
                break;
        }
        switch (z2) {
            case false:
                f362mc.field_71439_g.field_70181_x = -this.ncpMotionValue.get().floatValue();
                if (f362mc.field_71474_y.field_74311_E.func_151470_d()) {
                    f362mc.field_71439_g.field_70181_x = -0.5d;
                }
                MovementUtils.strafe();
                break;
            case true:
                if (this.startY > f362mc.field_71439_g.field_70163_u) {
                    f362mc.field_71439_g.field_70181_x = -1.0E-33d;
                }
                if (f362mc.field_71474_y.field_74311_E.func_151470_d()) {
                    f362mc.field_71439_g.field_70181_x = -0.2d;
                }
                if (f362mc.field_71474_y.field_74314_A.func_151470_d() && f362mc.field_71439_g.field_70163_u < this.startY - 0.1d) {
                    f362mc.field_71439_g.field_70181_x = 0.2d;
                }
                MovementUtils.strafe();
                break;
            case true:
                if (this.verusDmgModeValue.get().equalsIgnoreCase("Instant")) {
                    if (f362mc.field_71439_g.field_70122_E && f362mc.field_71441_e.func_72945_a(f362mc.field_71439_g, f362mc.field_71439_g.func_174813_aQ().func_72317_d(0.0d, 4.0d, 0.0d).func_72314_b(0.0d, 0.0d, 0.0d)).isEmpty()) {
                        PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(f362mc.field_71439_g.field_70165_t, y + 4.0d, f362mc.field_71439_g.field_70161_v, false));
                        PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(f362mc.field_71439_g.field_70165_t, y, f362mc.field_71439_g.field_70161_v, false));
                        PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(f362mc.field_71439_g.field_70165_t, y, f362mc.field_71439_g.field_70161_v, true));
                        EntityPlayerSP entityPlayerSP = f362mc.field_71439_g;
                        f362mc.field_71439_g.field_70179_y = 0.0d;
                        entityPlayerSP.field_70159_w = 0.0d;
                        if (this.verusReDamageValue.get().booleanValue()) {
                            this.dmgCooldown = this.verusReDmgTickValue.get().intValue();
                        }
                    }
                } else if (this.verusDmgModeValue.get().equalsIgnoreCase("InstantC06")) {
                    if (f362mc.field_71439_g.field_70122_E && f362mc.field_71441_e.func_72945_a(f362mc.field_71439_g, f362mc.field_71439_g.func_174813_aQ().func_72317_d(0.0d, 4.0d, 0.0d).func_72314_b(0.0d, 0.0d, 0.0d)).isEmpty()) {
                        PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(f362mc.field_71439_g.field_70165_t, y + 4.0d, f362mc.field_71439_g.field_70161_v, f362mc.field_71439_g.field_70177_z, f362mc.field_71439_g.field_70125_A, false));
                        PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(f362mc.field_71439_g.field_70165_t, y, f362mc.field_71439_g.field_70161_v, f362mc.field_71439_g.field_70177_z, f362mc.field_71439_g.field_70125_A, false));
                        PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(f362mc.field_71439_g.field_70165_t, y, f362mc.field_71439_g.field_70161_v, f362mc.field_71439_g.field_70177_z, f362mc.field_71439_g.field_70125_A, true));
                        EntityPlayerSP entityPlayerSP2 = f362mc.field_71439_g;
                        f362mc.field_71439_g.field_70179_y = 0.0d;
                        entityPlayerSP2.field_70159_w = 0.0d;
                        if (this.verusReDamageValue.get().booleanValue()) {
                            this.dmgCooldown = this.verusReDmgTickValue.get().intValue();
                        }
                    }
                } else if (this.verusDmgModeValue.get().equalsIgnoreCase("Jump")) {
                    if (f362mc.field_71439_g.field_70122_E) {
                        f362mc.field_71439_g.func_70664_aZ();
                        this.verusJumpTimes = 1;
                    }
                } else {
                    this.verusDmged = true;
                }
                if (this.verusVisualValue.get().booleanValue()) {
                    f362mc.field_71439_g.func_70107_b(f362mc.field_71439_g.field_70165_t, y + this.verusVisualHeightValue.get().floatValue(), f362mc.field_71439_g.field_70161_v);
                }
                this.shouldActiveDmg = this.dmgCooldown > 0;
                break;
            case true:
                for (int i = 0; i < 65; i++) {
                    f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.049d, z, false));
                    f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, false));
                }
                f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.1d, z, true));
                f362mc.field_71439_g.field_70159_w *= 0.1d;
                f362mc.field_71439_g.field_70179_y *= 0.1d;
                f362mc.field_71439_g.func_71038_i();
                break;
            case true:
                if (f362mc.field_71439_g.field_70122_E) {
                    f362mc.field_71439_g.func_70664_aZ();
                }
                this.moveSpeed = 1.0d;
                break;
            case true:
                this.expectItemStack = getSlimeSlot();
                if (this.expectItemStack == -1) {
                    LiquidBounce.hud.addNotification(new Notification("The fly requires slime blocks to be activated properly."));
                    break;
                } else if (f362mc.field_71439_g.field_70122_E) {
                    f362mc.field_71439_g.func_70664_aZ();
                    this.wdState = 1;
                    break;
                }
                break;
            case true:
                if (f362mc.field_71439_g.field_70122_E) {
                    if (this.hypixelC04.get().booleanValue()) {
                        for (int i2 = 0; i2 < 10; i2++) {
                            f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u, f362mc.field_71439_g.field_70161_v, true));
                        }
                    }
                    if (this.hypixelBoostMode.get().equalsIgnoreCase("ncp")) {
                        for (int i3 = 0; i3 < 65; i3++) {
                            f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u + 0.049d, f362mc.field_71439_g.field_70161_v, false));
                            f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u, f362mc.field_71439_g.field_70161_v, false));
                        }
                    } else {
                        double d = this.hypixelBoostMode.get().equalsIgnoreCase("morepackets") ? 3.4025d : 3.0125d;
                        while (true) {
                            double fallDistance = d;
                            if (fallDistance > 0.0d) {
                                f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u + 0.0624986421d, f362mc.field_71439_g.field_70161_v, false));
                                f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u + 0.0625d, f362mc.field_71439_g.field_70161_v, false));
                                f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u + 0.0624986421d, f362mc.field_71439_g.field_70161_v, false));
                                f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u + 1.3579E-6d, f362mc.field_71439_g.field_70161_v, false));
                                d = fallDistance - 0.0624986421d;
                            }
                        }
                    }
                    f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u, f362mc.field_71439_g.field_70161_v, true));
                    if (this.hypixelVisualY.get().booleanValue()) {
                        f362mc.field_71439_g.func_70664_aZ();
                        f362mc.field_71439_g.field_70163_u += 0.41999998688697815d;
                    }
                    this.boostHypixelState = 1;
                    this.moveSpeed = 0.1d;
                    this.lastDistance = 0.0d;
                    this.failedStart = false;
                    break;
                }
                break;
        }
        this.startY = f362mc.field_71439_g.field_70163_u;
        this.noPacketModify = false;
        this.aacJump = -3.8d;
        if (mode.equalsIgnoreCase("freehypixel")) {
            this.freeHypixelTimer.reset();
            f362mc.field_71439_g.func_70634_a(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u + 0.42d, f362mc.field_71439_g.field_70161_v);
            this.freeHypixelYaw = f362mc.field_71439_g.field_70177_z;
            this.freeHypixelPitch = f362mc.field_71439_g.field_70125_A;
        }
        if (!mode.equalsIgnoreCase("watchdog") && !mode.equalsIgnoreCase("bugspartan") && !mode.equalsIgnoreCase("verus") && !mode.equalsIgnoreCase("damage") && !mode.toLowerCase().contains("hypixel") && this.fakeDmgValue.get().booleanValue()) {
            f362mc.field_71439_g.func_70103_a((byte) 2);
        }
        super.onEnable();
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        this.wasDead = false;
        if (f362mc.field_71439_g == null) {
            return;
        }
        this.noFlag = false;
        String mode = this.modeValue.get();
        if ((this.resetMotionValue.get().booleanValue() && !mode.toUpperCase().startsWith("AAC") && !mode.equalsIgnoreCase("Hypixel") && !mode.equalsIgnoreCase("CubeCraft") && !mode.equalsIgnoreCase("Collide") && !mode.equalsIgnoreCase("Verus") && !mode.equalsIgnoreCase("Jump") && !mode.equalsIgnoreCase("creative")) || (mode.equalsIgnoreCase("pearl") && this.pearlState != -1)) {
            f362mc.field_71439_g.field_70159_w = 0.0d;
            f362mc.field_71439_g.field_70181_x = 0.0d;
            f362mc.field_71439_g.field_70179_y = 0.0d;
        }
        if (this.resetMotionValue.get().booleanValue() && this.boostTicks > 0 && mode.equalsIgnoreCase("Verus")) {
            f362mc.field_71439_g.field_70159_w = 0.0d;
            f362mc.field_71439_g.field_70179_y = 0.0d;
        }
        if (mode.equalsIgnoreCase("AAC5-Vanilla") && !f362mc.func_71387_A()) {
            sendAAC5Packets();
        }
        f362mc.field_71439_g.field_71075_bZ.field_75100_b = false;
        f362mc.field_71428_T.field_74278_d = 1.0f;
        f362mc.field_71439_g.field_71102_ce = 0.02f;
    }

    /* JADX WARN: Removed duplicated region for block: B:148:0x06cd  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x06ec  */
    /* JADX WARN: Removed duplicated region for block: B:265:0x0ee0  */
    /* JADX WARN: Removed duplicated region for block: B:266:0x0ee6  */
    /* JADX WARN: Type inference failed for: r0v34, types: [double, net.minecraft.client.entity.EntityPlayerSP] */
    /* JADX WARN: Type inference failed for: r0v84, types: [double, net.minecraft.client.entity.EntityPlayerSP] */
    /* JADX WARN: Type inference failed for: r3v0, types: [net.minecraft.client.entity.EntityPlayerSP] */
    /* JADX WARN: Type inference failed for: r3v14, types: [net.minecraft.client.entity.EntityPlayerSP] */
    @net.ccbluex.liquidbounce.event.EventTarget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onUpdate(net.ccbluex.liquidbounce.event.UpdateEvent r18) {
        /*
            Method dump skipped, instructions count: 6381
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.features.module.modules.movement.Fly.onUpdate(net.ccbluex.liquidbounce.event.UpdateEvent):void");
    }

    @EventTarget
    public void onMotion(MotionEvent event) {
        if (f362mc.field_71439_g == null) {
            return;
        }
        if (this.bobbingValue.get().booleanValue()) {
            f362mc.field_71439_g.field_71109_bG = this.bobbingAmountValue.get().floatValue();
            f362mc.field_71439_g.field_71107_bF = this.bobbingAmountValue.get().floatValue();
        }
        if (this.modeValue.get().equalsIgnoreCase("boosthypixel")) {
            switch (event.getEventState()) {
                case PRE:
                    this.hypixelTimer.update();
                    if (this.hypixelTimer.hasTimePassed(2)) {
                        f362mc.field_71439_g.func_70107_b(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u + 1.0E-5d, f362mc.field_71439_g.field_70161_v);
                        this.hypixelTimer.reset();
                    }
                    if (!this.failedStart) {
                        f362mc.field_71439_g.field_70181_x = 0.0d;
                        break;
                    }
                    break;
                case POST:
                    double xDist = f362mc.field_71439_g.field_70165_t - f362mc.field_71439_g.field_70169_q;
                    double zDist = f362mc.field_71439_g.field_70161_v - f362mc.field_71439_g.field_70166_s;
                    this.lastDistance = Math.sqrt((xDist * xDist) + (zDist * zDist));
                    break;
            }
        }
        String lowerCase = this.modeValue.get().toLowerCase();
        boolean z = true;
        switch (lowerCase.hashCode()) {
            case 545151501:
                if (lowerCase.equals("watchdog")) {
                    z = true;
                    break;
                }
                break;
            case 1380871169:
                if (lowerCase.equals("funcraft")) {
                    z = false;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                event.setOnGround(true);
                if (!MovementUtils.isMoving()) {
                    this.moveSpeed = 0.25d;
                }
                if (this.moveSpeed > 0.25d) {
                    this.moveSpeed -= this.moveSpeed / 159.0d;
                }
                if (event.getEventState() == EventState.PRE) {
                    f362mc.field_71439_g.field_71075_bZ.field_75100_b = false;
                    f362mc.field_71439_g.field_70181_x = 0.0d;
                    f362mc.field_71439_g.field_70159_w = 0.0d;
                    f362mc.field_71439_g.field_70179_y = 0.0d;
                    MovementUtils.strafe((float) this.moveSpeed);
                    f362mc.field_71439_g.func_70107_b(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u - 8.0E-6d, f362mc.field_71439_g.field_70161_v);
                    return;
                }
                return;
            case true:
                int current = f362mc.field_71439_g.field_71071_by.field_70461_c;
                if (event.getEventState() == EventState.PRE) {
                    if (this.wdState == 1 && f362mc.field_71441_e.func_72945_a(f362mc.field_71439_g, f362mc.field_71439_g.func_174813_aQ().func_72317_d(0.0d, -1.0d, 0.0d).func_72314_b(0.0d, 0.0d, 0.0d)).isEmpty()) {
                        PacketUtils.sendPacketNoEvent(new C09PacketHeldItemChange(this.expectItemStack));
                        this.wdState = 2;
                    }
                    f362mc.field_71428_T.field_74278_d = 1.0f;
                    if (this.wdState == 3 && this.expectItemStack != -1) {
                        PacketUtils.sendPacketNoEvent(new C09PacketHeldItemChange(current));
                        this.expectItemStack = -1;
                    }
                    if (this.wdState == 4) {
                        if (MovementUtils.isMoving()) {
                            MovementUtils.strafe(((float) MovementUtils.getBaseMoveSpeed()) * 0.938f);
                        } else {
                            MovementUtils.strafe(0.0f);
                        }
                        f362mc.field_71439_g.field_70181_x = -0.001500000013038516d;
                        return;
                    } else if (this.wdState < 3) {
                        Rotation rot = RotationUtils.getRotationFromPosition(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70161_v, ((int) f362mc.field_71439_g.field_70163_u) - 1);
                        RotationUtils.setTargetRotation(rot);
                        event.setYaw(rot.getYaw());
                        event.setPitch(rot.getPitch());
                        return;
                    } else {
                        event.setY(event.getY() - 0.08d);
                        return;
                    }
                } else if (this.wdState == 2) {
                    if (f362mc.field_71442_b.func_178890_a(f362mc.field_71439_g, f362mc.field_71441_e, f362mc.field_71439_g.field_71069_bz.func_75139_a(this.expectItemStack).func_75211_c(), new BlockPos(f362mc.field_71439_g.field_70165_t, ((int) f362mc.field_71439_g.field_70163_u) - 2, f362mc.field_71439_g.field_70161_v), EnumFacing.UP, RotationUtils.getVectorForRotation(RotationUtils.getRotationFromPosition(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70161_v, ((int) f362mc.field_71439_g.field_70163_u) - 1)))) {
                        f362mc.func_147114_u().func_147297_a(new C0APacketAnimation());
                    }
                    this.wdState = 3;
                    return;
                } else {
                    return;
                }
            default:
                return;
        }
    }

    public float coerceAtMost(double value, double max) {
        return (float) Math.min(value, max);
    }

    @EventTarget
    public void onRender3D(Render3DEvent event) {
        String mode = this.modeValue.get();
        if (!this.markValue.get().booleanValue() || mode.equalsIgnoreCase("Motion") || mode.equalsIgnoreCase("Creative") || mode.equalsIgnoreCase("Damage") || mode.equalsIgnoreCase("AAC5-Vanilla") || mode.equalsIgnoreCase("Derp") || mode.equalsIgnoreCase("KeepAlive")) {
            return;
        }
        double y = this.startY + 2.0d;
        RenderUtils.drawPlatform(y, f362mc.field_71439_g.func_174813_aQ().field_72337_e < y ? new Color(0, 255, 0, 90) : new Color(255, 0, 0, 90), 1.0d);
        String lowerCase = mode.toLowerCase();
        boolean z = true;
        switch (lowerCase.hashCode()) {
            case 1435059604:
                if (lowerCase.equals("aac1.9.10")) {
                    z = false;
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
                RenderUtils.drawPlatform(this.startY + this.aacJump, new Color(0, 0, 255, 90), 1.0d);
                return;
            case true:
                RenderUtils.drawPlatform(-70.0d, new Color(0, 0, 255, 90), 1.0d);
                return;
            default:
                return;
        }
    }

    @EventTarget
    public void onRender2D(Render2DEvent event) {
        String mode = this.modeValue.get();
        ScaledResolution scaledRes = new ScaledResolution(f362mc);
        if (mode.equalsIgnoreCase("Verus") && this.boostTicks > 0) {
            float width = ((this.verusDmgTickValue.get().intValue() - this.boostTicks) / this.verusDmgTickValue.get().intValue()) * 60.0f;
            RenderUtils.drawRect((scaledRes.func_78326_a() / 2.0f) - 31.0f, (scaledRes.func_78328_b() / 2.0f) + 14.0f, (scaledRes.func_78326_a() / 2.0f) + 31.0f, (scaledRes.func_78328_b() / 2.0f) + 18.0f, -1610612736);
            RenderUtils.drawRect((scaledRes.func_78326_a() / 2.0f) - 30.0f, (scaledRes.func_78328_b() / 2.0f) + 15.0f, ((scaledRes.func_78326_a() / 2.0f) - 30.0f) + width, (scaledRes.func_78328_b() / 2.0f) + 17.0f, -1);
        }
        if (mode.equalsIgnoreCase("Verus") && this.shouldActiveDmg) {
            float width2 = ((this.verusReDmgTickValue.get().intValue() - this.dmgCooldown) / this.verusReDmgTickValue.get().intValue()) * 60.0f;
            RenderUtils.drawRect((scaledRes.func_78326_a() / 2.0f) - 31.0f, (scaledRes.func_78328_b() / 2.0f) + 14.0f + 10.0f, (scaledRes.func_78326_a() / 2.0f) + 31.0f, (scaledRes.func_78328_b() / 2.0f) + 18.0f + 10.0f, -1610612736);
            RenderUtils.drawRect((scaledRes.func_78326_a() / 2.0f) - 30.0f, (scaledRes.func_78328_b() / 2.0f) + 15.0f + 10.0f, ((scaledRes.func_78326_a() / 2.0f) - 30.0f) + width2, (scaledRes.func_78328_b() / 2.0f) + 17.0f + 10.0f, -57569);
        }
    }

    @EventTarget
    public void onPacket(PacketEvent event) {
        C03PacketPlayer packet = event.getPacket();
        String mode = this.modeValue.get();
        if (this.noPacketModify) {
            return;
        }
        if ((packet instanceof C09PacketHeldItemChange) && mode.equalsIgnoreCase("watchdog") && this.wdState < 4) {
            event.cancelEvent();
        }
        if (packet instanceof S08PacketPlayerPosLook) {
            if (mode.equalsIgnoreCase("watchdog") && this.wdState == 3) {
                this.wdState = 4;
                if (this.fakeDmgValue.get().booleanValue() && f362mc.field_71439_g != null) {
                    f362mc.field_71439_g.func_70103_a((byte) 2);
                }
            }
            if (mode.equalsIgnoreCase("pearl") && this.pearlActivateCheck.get().equalsIgnoreCase("teleport") && this.pearlState == 1) {
                this.pearlState = 2;
            }
            if (mode.equalsIgnoreCase("BoostHypixel")) {
                this.failedStart = true;
                ClientUtils.displayChatMessage("§8[§c§lBoostHypixel-§a§lFly§8] §cSetback detected.");
            }
        }
        if (packet instanceof C03PacketPlayer) {
            C03PacketPlayer packetPlayer = packet;
            boolean z = packetPlayer.field_149474_g;
            if (mode.equalsIgnoreCase("NCP") || mode.equalsIgnoreCase("Rewinside") || ((mode.equalsIgnoreCase("Mineplex") && f362mc.field_71439_g.field_71071_by.func_70448_g() == null) || (mode.equalsIgnoreCase("Verus") && this.verusSpoofGround.get().booleanValue() && this.verusDmged))) {
                packetPlayer.field_149474_g = true;
            }
            if (mode.equalsIgnoreCase("Hypixel") || mode.equalsIgnoreCase("BoostHypixel")) {
                packetPlayer.field_149474_g = false;
            }
            if (mode.equalsIgnoreCase("Derp")) {
                packetPlayer.field_149476_e = RandomUtils.nextFloat(0.0f, 360.0f);
                packetPlayer.field_149473_f = RandomUtils.nextFloat(-90.0f, 90.0f);
            }
            if (mode.equalsIgnoreCase("AAC5-Vanilla") && !f362mc.func_71387_A()) {
                if (this.aac5NofallValue.get().booleanValue()) {
                    packetPlayer.field_149474_g = true;
                }
                this.aac5C03List.add(packetPlayer);
                event.cancelEvent();
                if (this.aac5C03List.size() > this.aac5PursePacketsValue.get().intValue()) {
                    sendAAC5Packets();
                }
            }
            if (mode.equalsIgnoreCase("clip") && this.clipGroundSpoof.get().booleanValue()) {
                packetPlayer.field_149474_g = true;
            }
            if ((mode.equalsIgnoreCase("motion") || mode.equalsIgnoreCase("creative")) && this.groundSpoofValue.get().booleanValue()) {
                packetPlayer.field_149474_g = true;
            }
            if (this.verusDmgModeValue.get().equalsIgnoreCase("Jump") && this.verusJumpTimes < 5 && mode.equalsIgnoreCase("Verus")) {
                packetPlayer.field_149474_g = false;
            }
        }
    }

    private void sendAAC5Packets() {
        float yaw = f362mc.field_71439_g.field_70177_z;
        float pitch = f362mc.field_71439_g.field_70125_A;
        Iterator<C03PacketPlayer> it = this.aac5C03List.iterator();
        while (it.hasNext()) {
            C03PacketPlayer packet = it.next();
            PacketUtils.sendPacketNoEvent(packet);
            if (packet.func_149466_j()) {
                if (packet.func_149463_k()) {
                    yaw = packet.field_149476_e;
                    pitch = packet.field_149473_f;
                }
                String str = this.aac5Packet.get();
                boolean z = true;
                switch (str.hashCode()) {
                    case 2547433:
                        if (str.equals("Rise")) {
                            z = true;
                            break;
                        }
                        break;
                    case 76517104:
                        if (str.equals("Other")) {
                            z = true;
                            break;
                        }
                        break;
                    case 1443687921:
                        if (str.equals("Original")) {
                            z = false;
                            break;
                        }
                        break;
                }
                switch (z) {
                    case false:
                        if (this.aac5UseC04Packet.get().booleanValue()) {
                            PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(packet.field_149479_a, 1.0E159d, packet.field_149478_c, true));
                            PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(packet.field_149479_a, packet.field_149477_b, packet.field_149478_c, true));
                            continue;
                        } else {
                            PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(packet.field_149479_a, 1.0E159d, packet.field_149478_c, yaw, pitch, true));
                            PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(packet.field_149479_a, packet.field_149477_b, packet.field_149478_c, yaw, pitch, true));
                            break;
                        }
                    case true:
                        if (this.aac5UseC04Packet.get().booleanValue()) {
                            PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(packet.field_149479_a, -1.0E159d, packet.field_149478_c + 10.0d, true));
                            PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(packet.field_149479_a, packet.field_149477_b, packet.field_149478_c, true));
                            continue;
                        } else {
                            PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(packet.field_149479_a, -1.0E159d, packet.field_149478_c + 10.0d, yaw, pitch, true));
                            PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(packet.field_149479_a, packet.field_149477_b, packet.field_149478_c, yaw, pitch, true));
                            break;
                        }
                    case true:
                        if (this.aac5UseC04Packet.get().booleanValue()) {
                            PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(packet.field_149479_a, Double.MAX_VALUE, packet.field_149478_c, true));
                            PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(packet.field_149479_a, packet.field_149477_b, packet.field_149478_c, true));
                            continue;
                        } else {
                            PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(packet.field_149479_a, Double.MAX_VALUE, packet.field_149478_c, yaw, pitch, true));
                            PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(packet.field_149479_a, packet.field_149477_b, packet.field_149478_c, yaw, pitch, true));
                            break;
                        }
                }
            }
        }
        this.aac5C03List.clear();
    }

    @EventTarget
    public void onMove(MoveEvent event) {
        String lowerCase = this.modeValue.get().toLowerCase();
        boolean z = true;
        switch (lowerCase.hashCode()) {
            case -1031473397:
                if (lowerCase.equals("cubecraft")) {
                    z = true;
                    break;
                }
                break;
            case -385327063:
                if (lowerCase.equals("freehypixel")) {
                    z = true;
                    break;
                }
                break;
            case 3056464:
                if (lowerCase.equals("clip")) {
                    z = true;
                    break;
                }
                break;
            case 80926006:
                if (lowerCase.equals("veruslowhop")) {
                    z = true;
                    break;
                }
                break;
            case 106540102:
                if (lowerCase.equals("pearl")) {
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
            case 545151501:
                if (lowerCase.equals("watchdog")) {
                    z = true;
                    break;
                }
                break;
            case 1814517522:
                if (lowerCase.equals("boosthypixel")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                if (this.pearlState != 2 && this.pearlState != -1) {
                    event.cancelEvent();
                    return;
                }
                return;
            case true:
                if (!this.verusDmged) {
                    if (this.verusDmgModeValue.get().equalsIgnoreCase("Jump")) {
                        event.zeroXZ();
                        return;
                    } else {
                        event.cancelEvent();
                        return;
                    }
                }
                return;
            case true:
                if (this.clipNoMove.get().booleanValue()) {
                    event.zeroXZ();
                    return;
                }
                return;
            case true:
                if (!f362mc.field_71439_g.field_70134_J && !f362mc.field_71439_g.func_180799_ab() && !f362mc.field_71439_g.func_70090_H() && !f362mc.field_71439_g.func_70617_f_() && !f362mc.field_71474_y.field_74314_A.func_151470_d() && f362mc.field_71439_g.field_70154_o == null && MovementUtils.isMoving()) {
                    f362mc.field_71474_y.field_74314_A.field_74513_e = false;
                    if (f362mc.field_71439_g.field_70122_E) {
                        f362mc.field_71439_g.func_70664_aZ();
                        f362mc.field_71439_g.field_70181_x = 0.0d;
                        MovementUtils.strafe(0.61f);
                        event.setY(0.41999998688698d);
                    }
                    MovementUtils.strafe();
                    return;
                }
                return;
            case true:
                if (this.wdState < 4) {
                    event.zeroXZ();
                    return;
                }
                return;
            case true:
                double yaw = Math.toRadians(f362mc.field_71439_g.field_70177_z);
                if (this.cubecraftTeleportTickTimer.hasTimePassed(2)) {
                    event.setX((-Math.sin(yaw)) * 2.4d);
                    event.setZ(Math.cos(yaw) * 2.4d);
                    this.cubecraftTeleportTickTimer.reset();
                    return;
                }
                event.setX((-Math.sin(yaw)) * 0.2d);
                event.setZ(Math.cos(yaw) * 0.2d);
                return;
            case true:
                if (!MovementUtils.isMoving()) {
                    event.setX(0.0d);
                    event.setZ(0.0d);
                    return;
                } else if (!this.failedStart) {
                    double amplifier = 1.0d + (f362mc.field_71439_g.func_70644_a(Potion.field_76424_c) ? 0.2d * (f362mc.field_71439_g.func_70660_b(Potion.field_76424_c).func_76458_c() + 1) : 0.0d);
                    double baseSpeed = 0.29d * amplifier;
                    switch (this.boostHypixelState) {
                        case 1:
                            this.moveSpeed = (f362mc.field_71439_g.func_70644_a(Potion.field_76424_c) ? 1.56d : 2.034d) * baseSpeed;
                            this.boostHypixelState = 2;
                            break;
                        case 2:
                            this.moveSpeed *= 2.16d;
                            this.boostHypixelState = 3;
                            break;
                        case 3:
                            this.moveSpeed = this.lastDistance - ((f362mc.field_71439_g.field_70173_aa % 2 == 0 ? 0.0103d : 0.0123d) * (this.lastDistance - baseSpeed));
                            this.boostHypixelState = 4;
                            break;
                        default:
                            this.moveSpeed = this.lastDistance - (this.lastDistance / 159.8d);
                            break;
                    }
                    this.moveSpeed = Math.max(this.moveSpeed, 0.3d);
                    double yaw2 = MovementUtils.getDirection();
                    event.setX((-Math.sin(yaw2)) * this.moveSpeed);
                    event.setZ(Math.cos(yaw2) * this.moveSpeed);
                    f362mc.field_71439_g.field_70159_w = event.getX();
                    f362mc.field_71439_g.field_70179_y = event.getZ();
                    return;
                } else {
                    return;
                }
            case true:
                if (!this.freeHypixelTimer.hasTimePassed(10)) {
                    event.zero();
                    return;
                }
                return;
            default:
                return;
        }
    }

    @EventTarget
    public void onBB(BlockBBEvent event) {
        if (f362mc.field_71439_g == null) {
            return;
        }
        String mode = this.modeValue.get();
        if ((event.getBlock() instanceof BlockAir) && mode.equalsIgnoreCase("Jump") && event.getY() < this.startY) {
            event.setBoundingBox(AxisAlignedBB.func_178781_a(event.getX(), event.getY(), event.getZ(), event.getX() + 1, this.startY, event.getZ() + 1));
        }
        if ((event.getBlock() instanceof BlockAir) && ((mode.equalsIgnoreCase("collide") && !f362mc.field_71439_g.func_70093_af()) || mode.equalsIgnoreCase("veruslowhop"))) {
            event.setBoundingBox(new AxisAlignedBB(-2.0d, -1.0d, -2.0d, 2.0d, 1.0d, 2.0d).func_72317_d(event.getX(), event.getY(), event.getZ()));
        }
        if (event.getBlock() instanceof BlockAir) {
            if (!mode.equalsIgnoreCase("Hypixel") && !mode.equalsIgnoreCase("BoostHypixel") && !mode.equalsIgnoreCase("Rewinside") && (!mode.equalsIgnoreCase("Mineplex") || f362mc.field_71439_g.field_71071_by.func_70448_g() != null)) {
                if (mode.equalsIgnoreCase("Verus")) {
                    if (!this.verusDmgModeValue.get().equalsIgnoreCase("none") && !this.verusDmged) {
                        return;
                    }
                } else {
                    return;
                }
            }
            if (event.getY() < f362mc.field_71439_g.field_70163_u) {
                event.setBoundingBox(AxisAlignedBB.func_178781_a(event.getX(), event.getY(), event.getZ(), event.getX() + 1, f362mc.field_71439_g.field_70163_u, event.getZ() + 1));
            }
        }
    }

    @EventTarget
    public void onJump(JumpEvent e) {
        String mode = this.modeValue.get();
        if (mode.equalsIgnoreCase("Hypixel") || mode.equalsIgnoreCase("BoostHypixel") || mode.equalsIgnoreCase("Rewinside") || ((mode.equalsIgnoreCase("Mineplex") && f362mc.field_71439_g.field_71071_by.func_70448_g() == null) || ((mode.equalsIgnoreCase("FunCraft") && this.moveSpeed > 0.0d) || (mode.equalsIgnoreCase("watchdog") && this.wdState >= 1)))) {
            e.cancelEvent();
        }
    }

    @EventTarget
    public void onStep(StepEvent e) {
        String mode = this.modeValue.get();
        if (mode.equalsIgnoreCase("Hypixel") || mode.equalsIgnoreCase("BoostHypixel") || mode.equalsIgnoreCase("Rewinside") || ((mode.equalsIgnoreCase("Mineplex") && f362mc.field_71439_g.field_71071_by.func_70448_g() == null) || mode.equalsIgnoreCase("FunCraft") || mode.equalsIgnoreCase("watchdog"))) {
            e.setStepHeight(0.0f);
        }
    }

    private void handleVanillaKickBypass() {
        if (!this.vanillaKickBypassValue.get().booleanValue() || !this.groundTimer.hasTimePassed(1000L)) {
            return;
        }
        double ground = calculateGround();
        double d = f362mc.field_71439_g.field_70163_u;
        while (true) {
            double posY = d;
            if (posY <= ground) {
                break;
            }
            f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(f362mc.field_71439_g.field_70165_t, posY, f362mc.field_71439_g.field_70161_v, true));
            if (posY - 8.0d < ground) {
                break;
            }
            d = posY - 8.0d;
        }
        f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(f362mc.field_71439_g.field_70165_t, ground, f362mc.field_71439_g.field_70161_v, true));
        double d2 = ground;
        while (true) {
            double posY2 = d2;
            if (posY2 >= f362mc.field_71439_g.field_70163_u) {
                break;
            }
            f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(f362mc.field_71439_g.field_70165_t, posY2, f362mc.field_71439_g.field_70161_v, true));
            if (posY2 + 8.0d > f362mc.field_71439_g.field_70163_u) {
                break;
            }
            d2 = posY2 + 8.0d;
        }
        f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u, f362mc.field_71439_g.field_70161_v, true));
        this.groundTimer.reset();
    }

    private double calculateGround() {
        AxisAlignedBB playerBoundingBox = f362mc.field_71439_g.func_174813_aQ();
        double blockHeight = 1.0d;
        double d = f362mc.field_71439_g.field_70163_u;
        while (true) {
            double ground = d;
            if (ground > 0.0d) {
                AxisAlignedBB customBox = new AxisAlignedBB(playerBoundingBox.field_72336_d, ground + blockHeight, playerBoundingBox.field_72334_f, playerBoundingBox.field_72340_a, ground, playerBoundingBox.field_72339_c);
                if (f362mc.field_71441_e.func_72829_c(customBox)) {
                    if (blockHeight <= 0.05d) {
                        return ground + blockHeight;
                    }
                    ground += blockHeight;
                    blockHeight = 0.05d;
                }
                d = ground - blockHeight;
            } else {
                return 0.0d;
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

    private int getSlimeSlot() {
        for (int i = 36; i < 45; i++) {
            ItemStack stack = f362mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
            if (stack != null && stack.func_77973_b() != null && (stack.func_77973_b() instanceof ItemBlock)) {
                ItemBlock itemBlock = stack.func_77973_b();
                if (itemBlock.func_179223_d() instanceof BlockSlime) {
                    return i - 36;
                }
            }
        }
        return -1;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public String getTag() {
        return this.modeValue.get();
    }
}
