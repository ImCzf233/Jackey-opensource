package net.ccbluex.liquidbounce.features.module.modules.world;

import java.awt.Color;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.injection.access.StaticStorage;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PlaceRotation;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.block.PlaceInfo;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.render.BlurUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TickTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

@ModuleInfo(name = "Scaffold", description = "Automatically places blocks beneath your feet.", category = ModuleCategory.WORLD, keyBind = 23)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/world/Scaffold.class */
public class Scaffold extends Module {
    private PlaceInfo targetPlace;
    private PlaceInfo towerPlace;
    private int launchY;
    private boolean faceBlock;
    private Rotation lockRotation;
    private Rotation lookupRotation;
    private Rotation speenRotation;
    private int slot;
    private int lastSlot;
    private boolean zitterDirection;
    private long delay;
    private boolean eagleSneaking;
    private final BoolValue towerEnabled = new BoolValue("EnableTower", false);
    private final ListValue towerModeValue = new ListValue("TowerMode", new String[]{"Jump", "Motion", "ConstantMotion", "MotionTP", "Packet", "Teleport", "AAC3.3.9", "AAC3.6.4", "Verus"}, "Motion", () -> {
        return this.towerEnabled.get();
    });
    private final ListValue towerPlaceModeValue = new ListValue("Tower-PlaceTiming", new String[]{"Pre", "Post"}, "Post");
    private final BoolValue stopWhenBlockAbove = new BoolValue("StopWhenBlockAbove", false, () -> {
        return this.towerEnabled.get();
    });
    private final BoolValue onJumpValue = new BoolValue("OnJump", false, () -> {
        return this.towerEnabled.get();
    });
    private final BoolValue noMoveOnlyValue = new BoolValue("NoMove", true, () -> {
        return this.towerEnabled.get();
    });
    private final BoolValue noMoveFreezeValue = new BoolValue("NoMoveFreezePlayer", true, () -> {
        return Boolean.valueOf(this.towerEnabled.get().booleanValue() && this.noMoveOnlyValue.get().booleanValue());
    });
    private final FloatValue towerTimerValue = new FloatValue("TowerTimer", 1.0f, 0.1f, 10.0f, () -> {
        return this.towerEnabled.get();
    });
    private final FloatValue jumpMotionValue = new FloatValue("JumpMotion", 0.42f, 0.3681289f, 0.79f, () -> {
        return Boolean.valueOf(this.towerEnabled.get().booleanValue() && this.towerModeValue.get().equalsIgnoreCase("Jump"));
    });
    private final IntegerValue jumpDelayValue = new IntegerValue("JumpDelay", 0, 0, 20, () -> {
        return Boolean.valueOf(this.towerEnabled.get().booleanValue() && this.towerModeValue.get().equalsIgnoreCase("Jump"));
    });
    private final FloatValue constantMotionValue = new FloatValue("ConstantMotion", 0.42f, 0.1f, 1.0f, () -> {
        return Boolean.valueOf(this.towerEnabled.get().booleanValue() && this.towerModeValue.get().equalsIgnoreCase("ConstantMotion"));
    });
    private final FloatValue constantMotionJumpGroundValue = new FloatValue("ConstantMotionJumpGround", 0.79f, 0.76f, 1.0f, () -> {
        return Boolean.valueOf(this.towerEnabled.get().booleanValue() && this.towerModeValue.get().equalsIgnoreCase("ConstantMotion"));
    });
    private final FloatValue teleportHeightValue = new FloatValue("TeleportHeight", 1.15f, 0.1f, 5.0f, () -> {
        return Boolean.valueOf(this.towerEnabled.get().booleanValue() && this.towerModeValue.get().equalsIgnoreCase("Teleport"));
    });
    private final IntegerValue teleportDelayValue = new IntegerValue("TeleportDelay", 0, 0, 20, () -> {
        return Boolean.valueOf(this.towerEnabled.get().booleanValue() && this.towerModeValue.get().equalsIgnoreCase("Teleport"));
    });
    private final BoolValue teleportGroundValue = new BoolValue("TeleportGround", true, () -> {
        return Boolean.valueOf(this.towerEnabled.get().booleanValue() && this.towerModeValue.get().equalsIgnoreCase("Teleport"));
    });
    private final BoolValue teleportNoMotionValue = new BoolValue("TeleportNoMotion", false, () -> {
        return Boolean.valueOf(this.towerEnabled.get().booleanValue() && this.towerModeValue.get().equalsIgnoreCase("Teleport"));
    });
    public final ListValue modeValue = new ListValue("Mode", new String[]{"Normal", "Rewinside", "Expand"}, "Normal");
    private final BoolValue placeableDelay = new BoolValue("PlaceableDelay", false);
    private final IntegerValue maxDelayValue = new IntegerValue("MaxDelay", 0, 0, 1000, "ms") { // from class: net.ccbluex.liquidbounce.features.module.modules.world.Scaffold.1
        public void onChanged(Integer oldValue, Integer newValue) {
            int i = Scaffold.this.minDelayValue.get().intValue();
            if (i > newValue.intValue()) {
                set((C16801) Integer.valueOf(i));
            }
        }
    };
    private final IntegerValue minDelayValue = new IntegerValue("MinDelay", 0, 0, 1000, "ms") { // from class: net.ccbluex.liquidbounce.features.module.modules.world.Scaffold.2
        public void onChanged(Integer oldValue, Integer newValue) {
            int i = Scaffold.this.maxDelayValue.get().intValue();
            if (i < newValue.intValue()) {
                set((C16812) Integer.valueOf(i));
            }
        }
    };
    private final BoolValue smartDelay = new BoolValue("SmartDelay", true);
    private final ListValue autoBlockMode = new ListValue("AutoBlock", new String[]{"Spoof", "Switch", "Off"}, "Spoof");
    private final BoolValue stayAutoBlock = new BoolValue("StayAutoBlock", false, () -> {
        return Boolean.valueOf(!this.autoBlockMode.get().equalsIgnoreCase("off"));
    });
    public final ListValue sprintModeValue = new ListValue("SprintMode", new String[]{"Same", "Ground", "Air", "Off"}, "Off");
    private final BoolValue swingValue = new BoolValue("Swing", true);
    private final BoolValue downValue = new BoolValue("Down", false);
    private final BoolValue searchValue = new BoolValue("Search", true);
    private final ListValue placeModeValue = new ListValue("PlaceTiming", new String[]{"Pre", "Post"}, "Post");
    private final BoolValue eagleValue = new BoolValue("Eagle", false);
    private final BoolValue eagleSilentValue = new BoolValue("EagleSilent", false, () -> {
        return this.eagleValue.get();
    });
    private final IntegerValue blocksToEagleValue = new IntegerValue("BlocksToEagle", 0, 0, 10, () -> {
        return this.eagleValue.get();
    });
    private final FloatValue eagleEdgeDistanceValue = new FloatValue("EagleEdgeDistance", 0.2f, 0.0f, 0.5f, "m", () -> {
        return this.eagleValue.get();
    });
    private final BoolValue omniDirectionalExpand = new BoolValue("OmniDirectionalExpand", true, () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("expand"));
    });
    private final IntegerValue expandLengthValue = new IntegerValue("ExpandLength", 5, 1, 6, " blocks", () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("expand"));
    });
    private final BoolValue rotationsValue = new BoolValue("Rotations", true);
    private final BoolValue noHitCheckValue = new BoolValue("NoHitCheck", false, () -> {
        return this.rotationsValue.get();
    });
    public final ListValue rotationModeValue = new ListValue("RotationMode", new String[]{"Normal", "AAC", "Static", "Static2", "Static3", "Spin", "Custom"}, "Normal");
    public final ListValue rotationLookupValue = new ListValue("RotationLookup", new String[]{"Normal", "AAC", "Same"}, "Normal");
    private final FloatValue maxTurnSpeed = new FloatValue("MaxTurnSpeed", 180.0f, 0.0f, 180.0f, "°", () -> {
        return this.rotationsValue.get();
    }) { // from class: net.ccbluex.liquidbounce.features.module.modules.world.Scaffold.3
        public void onChanged(Float oldValue, Float newValue) {
            float i = Scaffold.this.minTurnSpeed.get().floatValue();
            if (i > newValue.floatValue()) {
                set((C16823) Float.valueOf(i));
            }
        }
    };
    private final FloatValue minTurnSpeed = new FloatValue("MinTurnSpeed", 180.0f, 0.0f, 180.0f, "°", () -> {
        return this.rotationsValue.get();
    }) { // from class: net.ccbluex.liquidbounce.features.module.modules.world.Scaffold.4
        public void onChanged(Float oldValue, Float newValue) {
            float i = Scaffold.this.maxTurnSpeed.get().floatValue();
            if (i < newValue.floatValue()) {
                set((C16834) Float.valueOf(i));
            }
        }
    };
    private final FloatValue staticPitchValue = new FloatValue("Static-Pitch", 86.0f, 80.0f, 90.0f, "°", () -> {
        return Boolean.valueOf(this.rotationModeValue.get().toLowerCase().startsWith("static"));
    });
    private final FloatValue customYawValue = new FloatValue("Custom-Yaw", 135.0f, -180.0f, 180.0f, "°", () -> {
        return Boolean.valueOf(this.rotationModeValue.get().equalsIgnoreCase("custom"));
    });
    private final FloatValue customPitchValue = new FloatValue("Custom-Pitch", 86.0f, -90.0f, 90.0f, "°", () -> {
        return Boolean.valueOf(this.rotationModeValue.get().equalsIgnoreCase("custom"));
    });
    private final FloatValue speenSpeedValue = new FloatValue("Spin-Speed", 5.0f, -90.0f, 90.0f, "°", () -> {
        return Boolean.valueOf(this.rotationModeValue.get().equalsIgnoreCase("spin"));
    });
    private final FloatValue speenPitchValue = new FloatValue("Spin-Pitch", 90.0f, -90.0f, 90.0f, "°", () -> {
        return Boolean.valueOf(this.rotationModeValue.get().equalsIgnoreCase("spin"));
    });
    private final BoolValue keepRotOnJumpValue = new BoolValue("KeepRotOnJump", true, () -> {
        return Boolean.valueOf(!this.rotationModeValue.get().equalsIgnoreCase("normal") && !this.rotationModeValue.get().equalsIgnoreCase("aac"));
    });
    private final BoolValue keepRotationValue = new BoolValue("KeepRotation", false, () -> {
        return this.rotationsValue.get();
    });
    private final IntegerValue keepLengthValue = new IntegerValue("KeepRotationLength", 0, 0, 20, () -> {
        return Boolean.valueOf(this.rotationsValue.get().booleanValue() && !this.keepRotationValue.get().booleanValue());
    });
    private final ListValue placeConditionValue = new ListValue("Place-Condition", new String[]{"Air", "FallDown", "NegativeMotion", "Always"}, "Always");
    private final BoolValue rotationStrafeValue = new BoolValue("RotationStrafe", false);
    private final BoolValue zitterValue = new BoolValue("Zitter", false, () -> {
        return Boolean.valueOf(!isTowerOnly());
    });
    private final ListValue zitterModeValue = new ListValue("ZitterMode", new String[]{"Teleport", "Smooth"}, "Teleport", () -> {
        return Boolean.valueOf(!isTowerOnly() && this.zitterValue.get().booleanValue());
    });
    private final FloatValue zitterSpeed = new FloatValue("ZitterSpeed", 0.13f, 0.1f, 0.3f, () -> {
        return Boolean.valueOf(!isTowerOnly() && this.zitterValue.get().booleanValue() && this.zitterModeValue.get().equalsIgnoreCase("teleport"));
    });
    private final FloatValue zitterStrength = new FloatValue("ZitterStrength", 0.072f, 0.05f, 0.2f, () -> {
        return Boolean.valueOf(!isTowerOnly() && this.zitterValue.get().booleanValue() && this.zitterModeValue.get().equalsIgnoreCase("teleport"));
    });
    private final IntegerValue zitterDelay = new IntegerValue("ZitterDelay", 100, 0, 500, "ms", () -> {
        return Boolean.valueOf(!isTowerOnly() && this.zitterValue.get().booleanValue() && this.zitterModeValue.get().equalsIgnoreCase("smooth"));
    });
    private final FloatValue timerValue = new FloatValue("Timer", 1.0f, 0.1f, 10.0f, () -> {
        return Boolean.valueOf(!isTowerOnly());
    });
    public final FloatValue speedModifierValue = new FloatValue("SpeedModifier", 1.0f, 0.0f, 2.0f, "x");
    public final FloatValue xzMultiplier = new FloatValue("XZ-Multiplier", 1.0f, 0.0f, 4.0f, "x");
    private final BoolValue customSpeedValue = new BoolValue("CustomSpeed", false);
    private final FloatValue customMoveSpeedValue = new FloatValue("CustomMoveSpeed", 0.3f, 0.0f, 5.0f, () -> {
        return this.customSpeedValue.get();
    });
    private final BoolValue sameYValue = new BoolValue("SameY", false, () -> {
        return Boolean.valueOf(!this.towerEnabled.get().booleanValue());
    });
    private final BoolValue autoJumpValue = new BoolValue("AutoJump", false, () -> {
        return Boolean.valueOf(!isTowerOnly());
    });
    private final BoolValue smartSpeedValue = new BoolValue("SmartSpeed", false, () -> {
        return Boolean.valueOf(!isTowerOnly());
    });
    private final BoolValue safeWalkValue = new BoolValue("SafeWalk", true);
    private final BoolValue airSafeValue = new BoolValue("AirSafe", false, () -> {
        return this.safeWalkValue.get();
    });
    private final BoolValue autoDisableSpeedValue = new BoolValue("AutoDisable-Speed", true);
    public final ListValue counterDisplayValue = new ListValue("Counter", new String[]{"Off", "Simple", "Advanced", "Sigma", "Novoline"}, "Simple");
    private final BoolValue markValue = new BoolValue("Mark", false);
    private final IntegerValue redValue = new IntegerValue("Red", 0, 0, 255, () -> {
        return this.markValue.get();
    });
    private final IntegerValue greenValue = new IntegerValue("Green", 120, 0, 255, () -> {
        return this.markValue.get();
    });
    private final IntegerValue blueValue = new IntegerValue("Blue", 255, 0, 255, () -> {
        return this.markValue.get();
    });
    private final IntegerValue alphaValue = new IntegerValue("Alpha", 120, 0, 255, () -> {
        return this.markValue.get();
    });
    private final BoolValue blurValue = new BoolValue("Blur-Advanced", false, () -> {
        return Boolean.valueOf(this.counterDisplayValue.get().equalsIgnoreCase("advanced"));
    });
    private final FloatValue blurStrength = new FloatValue("Blur-Strength", 1.0f, 0.0f, 30.0f, "x", () -> {
        return Boolean.valueOf(this.counterDisplayValue.get().equalsIgnoreCase("advanced"));
    });
    private final MSTimer delayTimer = new MSTimer();
    private final MSTimer zitterTimer = new MSTimer();
    private int placedBlocksWithoutEagle = 0;
    private boolean shouldGoDown = false;
    private float progress = 0.0f;
    private float spinYaw = 0.0f;
    private long lastMS = 0;
    private final TickTimer timer = new TickTimer();
    private double jumpGround = 0.0d;
    private int verusState = 0;
    private boolean verusJumped = false;

    public boolean isTowerOnly() {
        return this.towerEnabled.get().booleanValue() && !this.onJumpValue.get().booleanValue();
    }

    public boolean towerActivation() {
        return this.towerEnabled.get().booleanValue() && (!this.onJumpValue.get().booleanValue() || f362mc.field_71474_y.field_74314_A.func_151470_d()) && (!this.noMoveOnlyValue.get().booleanValue() || !MovementUtils.isMoving());
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        if (f362mc.field_71439_g == null) {
            return;
        }
        this.progress = 0.0f;
        this.spinYaw = 0.0f;
        this.launchY = (int) f362mc.field_71439_g.field_70163_u;
        this.lastSlot = f362mc.field_71439_g.field_71071_by.field_70461_c;
        this.slot = f362mc.field_71439_g.field_71071_by.field_70461_c;
        if (this.autoDisableSpeedValue.get().booleanValue() && LiquidBounce.moduleManager.getModule(Speed.class).getState()) {
            LiquidBounce.moduleManager.getModule(Speed.class).setState(false);
            LiquidBounce.hud.addNotification(new Notification("Speed is disabled to prevent flags/errors.", Notification.Type.WARNING));
        }
        this.faceBlock = false;
        this.lastMS = System.currentTimeMillis();
    }

    private void fakeJump() {
        f362mc.field_71439_g.field_70160_al = true;
        f362mc.field_71439_g.func_71029_a(StatList.field_75953_u);
    }

    private void move(MotionEvent event) {
        String lowerCase = this.towerModeValue.get().toLowerCase();
        boolean z = true;
        switch (lowerCase.hashCode()) {
            case -1360201941:
                if (lowerCase.equals("teleport")) {
                    z = true;
                    break;
                }
                break;
            case -1068318794:
                if (lowerCase.equals("motion")) {
                    z = true;
                    break;
                }
                break;
            case -995865464:
                if (lowerCase.equals("packet")) {
                    z = true;
                    break;
                }
                break;
            case -157173582:
                if (lowerCase.equals("motiontp")) {
                    z = true;
                    break;
                }
                break;
            case 3273774:
                if (lowerCase.equals("jump")) {
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
            case 325228192:
                if (lowerCase.equals("aac3.3.9")) {
                    z = true;
                    break;
                }
                break;
            case 325231070:
                if (lowerCase.equals("aac3.6.4")) {
                    z = true;
                    break;
                }
                break;
            case 792877146:
                if (lowerCase.equals("constantmotion")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                if (f362mc.field_71439_g.field_70122_E && this.timer.hasTimePassed(this.jumpDelayValue.get().intValue())) {
                    fakeJump();
                    f362mc.field_71439_g.field_70181_x = this.jumpMotionValue.get().floatValue();
                    this.timer.reset();
                    return;
                }
                return;
            case true:
                if (f362mc.field_71439_g.field_70122_E) {
                    fakeJump();
                    f362mc.field_71439_g.field_70181_x = 0.42d;
                    return;
                } else if (f362mc.field_71439_g.field_70181_x < 0.1d) {
                    f362mc.field_71439_g.field_70181_x = -0.3d;
                    return;
                } else {
                    return;
                }
            case true:
                if (f362mc.field_71439_g.field_70122_E) {
                    fakeJump();
                    f362mc.field_71439_g.field_70181_x = 0.42d;
                    return;
                } else if (f362mc.field_71439_g.field_70181_x < 0.23d) {
                    f362mc.field_71439_g.func_70107_b(f362mc.field_71439_g.field_70165_t, (int) f362mc.field_71439_g.field_70163_u, f362mc.field_71439_g.field_70161_v);
                    return;
                } else {
                    return;
                }
            case true:
                if (f362mc.field_71439_g.field_70122_E && this.timer.hasTimePassed(2)) {
                    fakeJump();
                    f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u + 0.42d, f362mc.field_71439_g.field_70161_v, false));
                    f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u + 0.76d, f362mc.field_71439_g.field_70161_v, false));
                    f362mc.field_71439_g.func_70107_b(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u + 1.08d, f362mc.field_71439_g.field_70161_v);
                    this.timer.reset();
                    return;
                }
                return;
            case true:
                if (this.teleportNoMotionValue.get().booleanValue()) {
                    f362mc.field_71439_g.field_70181_x = 0.0d;
                }
                if ((f362mc.field_71439_g.field_70122_E || !this.teleportGroundValue.get().booleanValue()) && this.timer.hasTimePassed(this.teleportDelayValue.get().intValue())) {
                    fakeJump();
                    f362mc.field_71439_g.func_70634_a(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u + this.teleportHeightValue.get().floatValue(), f362mc.field_71439_g.field_70161_v);
                    this.timer.reset();
                    return;
                }
                return;
            case true:
                if (f362mc.field_71439_g.field_70122_E) {
                    fakeJump();
                    this.jumpGround = f362mc.field_71439_g.field_70163_u;
                    f362mc.field_71439_g.field_70181_x = this.constantMotionValue.get().floatValue();
                }
                if (f362mc.field_71439_g.field_70163_u > this.jumpGround + this.constantMotionJumpGroundValue.get().floatValue()) {
                    fakeJump();
                    f362mc.field_71439_g.func_70107_b(f362mc.field_71439_g.field_70165_t, (int) f362mc.field_71439_g.field_70163_u, f362mc.field_71439_g.field_70161_v);
                    f362mc.field_71439_g.field_70181_x = this.constantMotionValue.get().floatValue();
                    this.jumpGround = f362mc.field_71439_g.field_70163_u;
                    return;
                }
                return;
            case true:
                if (f362mc.field_71439_g.field_70122_E) {
                    fakeJump();
                    f362mc.field_71439_g.field_70181_x = 0.4001d;
                }
                f362mc.field_71428_T.field_74278_d = 1.0f;
                if (f362mc.field_71439_g.field_70181_x < 0.0d) {
                    f362mc.field_71439_g.field_70181_x -= 9.45E-6d;
                    f362mc.field_71428_T.field_74278_d = 1.6f;
                    return;
                }
                return;
            case true:
                if (f362mc.field_71439_g.field_70173_aa % 4 == 1) {
                    f362mc.field_71439_g.field_70181_x = 0.4195464d;
                    f362mc.field_71439_g.func_70107_b(f362mc.field_71439_g.field_70165_t - 0.035d, f362mc.field_71439_g.field_70163_u, f362mc.field_71439_g.field_70161_v);
                    return;
                } else if (f362mc.field_71439_g.field_70173_aa % 4 == 0) {
                    f362mc.field_71439_g.field_70181_x = -0.5d;
                    f362mc.field_71439_g.func_70107_b(f362mc.field_71439_g.field_70165_t + 0.035d, f362mc.field_71439_g.field_70163_u, f362mc.field_71439_g.field_70161_v);
                    return;
                } else {
                    return;
                }
            case true:
                if (!f362mc.field_71441_e.func_72945_a(f362mc.field_71439_g, f362mc.field_71439_g.func_174813_aQ().func_72317_d(0.0d, -0.01d, 0.0d)).isEmpty() && f362mc.field_71439_g.field_70122_E && f362mc.field_71439_g.field_70124_G) {
                    this.verusState = 0;
                    this.verusJumped = true;
                }
                if (this.verusJumped) {
                    MovementUtils.strafe();
                    switch (this.verusState) {
                        case 0:
                            fakeJump();
                            f362mc.field_71439_g.field_70181_x = 0.41999998688697815d;
                            this.verusState++;
                            break;
                        case 1:
                            this.verusState++;
                            break;
                        case 2:
                            this.verusState++;
                            break;
                        case 3:
                            event.setOnGround(true);
                            f362mc.field_71439_g.field_70181_x = 0.0d;
                            this.verusState++;
                            break;
                        case 4:
                            this.verusState++;
                            break;
                    }
                    this.verusJumped = false;
                }
                this.verusJumped = true;
                return;
            default:
                return;
        }
    }

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if (towerActivation()) {
            this.shouldGoDown = false;
            f362mc.field_71474_y.field_74311_E.field_74513_e = false;
            f362mc.field_71439_g.func_70031_b(false);
            return;
        }
        f362mc.field_71428_T.field_74278_d = this.timerValue.get().floatValue();
        this.shouldGoDown = this.downValue.get().booleanValue() && !this.sameYValue.get().booleanValue() && GameSettings.func_100015_a(f362mc.field_71474_y.field_74311_E) && getBlocksAmount() > 1;
        if (this.shouldGoDown) {
            f362mc.field_71474_y.field_74311_E.field_74513_e = false;
        }
        if (this.customSpeedValue.get().booleanValue()) {
            MovementUtils.strafe(this.customMoveSpeedValue.get().floatValue());
        }
        if (f362mc.field_71439_g.field_70122_E) {
            String mode = this.modeValue.get();
            if (mode.equalsIgnoreCase("Rewinside")) {
                MovementUtils.strafe(0.2f);
                f362mc.field_71439_g.field_70181_x = 0.0d;
            }
            if (this.zitterValue.get().booleanValue() && this.zitterModeValue.get().equalsIgnoreCase("smooth")) {
                if (!GameSettings.func_100015_a(f362mc.field_71474_y.field_74366_z)) {
                    f362mc.field_71474_y.field_74366_z.field_74513_e = false;
                }
                if (!GameSettings.func_100015_a(f362mc.field_71474_y.field_74370_x)) {
                    f362mc.field_71474_y.field_74370_x.field_74513_e = false;
                }
                if (this.zitterTimer.hasTimePassed(this.zitterDelay.get().intValue())) {
                    this.zitterDirection = !this.zitterDirection;
                    this.zitterTimer.reset();
                }
                if (this.zitterDirection) {
                    f362mc.field_71474_y.field_74366_z.field_74513_e = true;
                    f362mc.field_71474_y.field_74370_x.field_74513_e = false;
                } else {
                    f362mc.field_71474_y.field_74366_z.field_74513_e = false;
                    f362mc.field_71474_y.field_74370_x.field_74513_e = true;
                }
            }
            if (this.eagleValue.get().booleanValue() && !this.shouldGoDown) {
                double dif = 0.5d;
                if (this.eagleEdgeDistanceValue.get().floatValue() > 0.0f) {
                    int i = 0;
                    while (i < 4) {
                        BlockPos blockPos = new BlockPos(f362mc.field_71439_g.field_70165_t + (i == 0 ? -1 : i == 1 ? 1 : 0), f362mc.field_71439_g.field_70163_u - (f362mc.field_71439_g.field_70163_u == ((double) ((int) f362mc.field_71439_g.field_70163_u)) + 0.5d ? 0.0d : 1.0d), f362mc.field_71439_g.field_70161_v + (i == 2 ? -1 : i == 3 ? 1 : 0));
                        PlaceInfo placeInfo = PlaceInfo.get(blockPos);
                        if (BlockUtils.isReplaceable(blockPos) && placeInfo != null) {
                            double calcDif = (i > 1 ? f362mc.field_71439_g.field_70161_v - blockPos.func_177952_p() : f362mc.field_71439_g.field_70165_t - blockPos.func_177958_n()) - 0.5d;
                            if (calcDif < 0.0d) {
                                calcDif *= -1.0d;
                            }
                            double calcDif2 = calcDif - 0.5d;
                            if (calcDif2 < dif) {
                                dif = calcDif2;
                            }
                        }
                        i++;
                    }
                }
                if (this.placedBlocksWithoutEagle >= this.blocksToEagleValue.get().intValue()) {
                    boolean shouldEagle = f362mc.field_71441_e.func_180495_p(new BlockPos(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u - 1.0d, f362mc.field_71439_g.field_70161_v)).func_177230_c() == Blocks.field_150350_a || dif < ((double) this.eagleEdgeDistanceValue.get().floatValue());
                    if (this.eagleSilentValue.get().booleanValue()) {
                        if (this.eagleSneaking != shouldEagle) {
                            f362mc.func_147114_u().func_147297_a(new C0BPacketEntityAction(f362mc.field_71439_g, shouldEagle ? C0BPacketEntityAction.Action.START_SNEAKING : C0BPacketEntityAction.Action.STOP_SNEAKING));
                        }
                        this.eagleSneaking = shouldEagle;
                    } else {
                        f362mc.field_71474_y.field_74311_E.field_74513_e = shouldEagle;
                    }
                    this.placedBlocksWithoutEagle = 0;
                } else {
                    this.placedBlocksWithoutEagle++;
                }
            }
            if (this.zitterValue.get().booleanValue() && this.zitterModeValue.get().equalsIgnoreCase("teleport")) {
                MovementUtils.strafe(this.zitterSpeed.get().floatValue());
                double yaw = Math.toRadians(f362mc.field_71439_g.field_70177_z + (this.zitterDirection ? 90.0d : -90.0d));
                f362mc.field_71439_g.field_70159_w -= Math.sin(yaw) * this.zitterStrength.get().floatValue();
                f362mc.field_71439_g.field_70179_y += Math.cos(yaw) * this.zitterStrength.get().floatValue();
                this.zitterDirection = !this.zitterDirection;
            }
        }
        if (this.sprintModeValue.get().equalsIgnoreCase("off") || ((this.sprintModeValue.get().equalsIgnoreCase("ground") && !f362mc.field_71439_g.field_70122_E) || (this.sprintModeValue.get().equalsIgnoreCase("air") && f362mc.field_71439_g.field_70122_E))) {
            f362mc.field_71439_g.func_70031_b(false);
        }
        if (this.shouldGoDown) {
            this.launchY = ((int) f362mc.field_71439_g.field_70163_u) - 1;
        } else if (!this.sameYValue.get().booleanValue()) {
            if ((!this.autoJumpValue.get().booleanValue() && (!this.smartSpeedValue.get().booleanValue() || !LiquidBounce.moduleManager.getModule(Speed.class).getState())) || GameSettings.func_100015_a(f362mc.field_71474_y.field_74314_A) || f362mc.field_71439_g.field_70163_u < this.launchY) {
                this.launchY = (int) f362mc.field_71439_g.field_70163_u;
            }
            if (this.autoJumpValue.get().booleanValue() && !LiquidBounce.moduleManager.getModule(Speed.class).getState() && MovementUtils.isMoving() && f362mc.field_71439_g.field_70122_E && f362mc.field_71439_g.field_70773_bE == 0) {
                f362mc.field_71439_g.func_70664_aZ();
                f362mc.field_71439_g.field_70773_bE = 10;
            }
        }
    }

    @EventTarget
    public void onPacket(PacketEvent event) {
        if (f362mc.field_71439_g == null) {
            return;
        }
        C09PacketHeldItemChange packet = event.getPacket();
        if (packet instanceof C09PacketHeldItemChange) {
            C09PacketHeldItemChange packetHeldItemChange = packet;
            this.slot = packetHeldItemChange.func_149614_c();
        }
    }

    @EventTarget
    public void onStrafe(StrafeEvent event) {
        if (this.lookupRotation != null && this.rotationStrafeValue.get().booleanValue()) {
            int dif = (int) ((MathHelper.func_76142_g(((f362mc.field_71439_g.field_70177_z - this.lookupRotation.getYaw()) - 23.5f) - 135.0f) + 180.0f) / 45.0f);
            float yaw = this.lookupRotation.getYaw();
            float strafe = event.getStrafe();
            float forward = event.getForward();
            float friction = event.getFriction();
            float calcForward = 0.0f;
            float calcStrafe = 0.0f;
            switch (dif) {
                case 0:
                    calcForward = forward;
                    calcStrafe = strafe;
                    break;
                case 1:
                    float calcForward2 = 0.0f + forward;
                    float calcStrafe2 = 0.0f - forward;
                    calcForward = calcForward2 + strafe;
                    calcStrafe = calcStrafe2 + strafe;
                    break;
                case 2:
                    calcForward = strafe;
                    calcStrafe = -forward;
                    break;
                case 3:
                    float calcForward3 = 0.0f - forward;
                    float calcStrafe3 = 0.0f - forward;
                    calcForward = calcForward3 + strafe;
                    calcStrafe = calcStrafe3 - strafe;
                    break;
                case 4:
                    calcForward = -forward;
                    calcStrafe = -strafe;
                    break;
                case 5:
                    float calcForward4 = 0.0f - forward;
                    float calcStrafe4 = 0.0f + forward;
                    calcForward = calcForward4 - strafe;
                    calcStrafe = calcStrafe4 - strafe;
                    break;
                case 6:
                    calcForward = -strafe;
                    calcStrafe = forward;
                    break;
                case 7:
                    float calcForward5 = 0.0f + forward;
                    float calcStrafe5 = 0.0f + forward;
                    calcForward = calcForward5 - strafe;
                    calcStrafe = calcStrafe5 + strafe;
                    break;
            }
            if (calcForward > 1.0f || ((calcForward < 0.9f && calcForward > 0.3f) || calcForward < -1.0f || (calcForward > -0.9f && calcForward < -0.3f))) {
                calcForward *= 0.5f;
            }
            if (calcStrafe > 1.0f || ((calcStrafe < 0.9f && calcStrafe > 0.3f) || calcStrafe < -1.0f || (calcStrafe > -0.9f && calcStrafe < -0.3f))) {
                calcStrafe *= 0.5f;
            }
            float f = (calcStrafe * calcStrafe) + (calcForward * calcForward);
            if (f >= 1.0E-4f) {
                float f2 = MathHelper.func_76129_c(f);
                if (f2 < 1.0f) {
                    f2 = 1.0f;
                }
                float f3 = friction / f2;
                float calcStrafe6 = calcStrafe * f3;
                float calcForward6 = calcForward * f3;
                float yawSin = MathHelper.func_76126_a((float) ((yaw * 3.141592653589793d) / 180.0d));
                float yawCos = MathHelper.func_76134_b((float) ((yaw * 3.141592653589793d) / 180.0d));
                f362mc.field_71439_g.field_70159_w += (calcStrafe6 * yawCos) - (calcForward6 * yawSin);
                f362mc.field_71439_g.field_70179_y += (calcForward6 * yawCos) + (calcStrafe6 * yawSin);
            }
            event.cancelEvent();
        }
    }

    private boolean shouldPlace() {
        boolean placeWhenAir = this.placeConditionValue.get().equalsIgnoreCase("air");
        boolean placeWhenFall = this.placeConditionValue.get().equalsIgnoreCase("falldown");
        boolean placeWhenNegativeMotion = this.placeConditionValue.get().equalsIgnoreCase("negativemotion");
        boolean alwaysPlace = this.placeConditionValue.get().equalsIgnoreCase("always");
        return towerActivation() || alwaysPlace || (placeWhenAir && !f362mc.field_71439_g.field_70122_E) || ((placeWhenFall && f362mc.field_71439_g.field_70143_R > 0.0f) || (placeWhenNegativeMotion && f362mc.field_71439_g.field_70181_x < 0.0d));
    }

    @EventTarget
    public void onMotion(MotionEvent event) {
        VecRotation vecRotation;
        f362mc.field_71439_g.field_70159_w *= this.xzMultiplier.get().floatValue();
        f362mc.field_71439_g.field_70179_y *= this.xzMultiplier.get().floatValue();
        if (this.rotationsValue.get().booleanValue() && this.keepRotationValue.get().booleanValue() && this.lockRotation != null) {
            if (this.rotationModeValue.get().equalsIgnoreCase("spin")) {
                this.spinYaw += this.speenSpeedValue.get().floatValue();
                this.spinYaw = MathHelper.func_76142_g(this.spinYaw);
                this.speenRotation = new Rotation(this.spinYaw, this.speenPitchValue.get().floatValue());
                RotationUtils.setTargetRotation(this.speenRotation);
            } else if (this.lockRotation != null) {
                RotationUtils.setTargetRotation(RotationUtils.limitAngleChange(RotationUtils.serverRotation, this.lockRotation, RandomUtils.nextFloat(this.minTurnSpeed.get().floatValue(), this.maxTurnSpeed.get().floatValue())));
            }
        }
        String mode = this.modeValue.get();
        EventState eventState = event.getEventState();
        for (int i = 0; i < 8; i++) {
            if (f362mc.field_71439_g.field_71071_by.field_70462_a[i] != null && f362mc.field_71439_g.field_71071_by.field_70462_a[i].field_77994_a <= 0) {
                f362mc.field_71439_g.field_71071_by.field_70462_a[i] = null;
            }
        }
        if ((!this.rotationsValue.get().booleanValue() || this.noHitCheckValue.get().booleanValue() || this.faceBlock) && this.placeModeValue.get().equalsIgnoreCase(eventState.getStateName()) && !towerActivation()) {
            place(false);
        }
        if (eventState == EventState.PRE && !towerActivation()) {
            if (!shouldPlace()) {
                return;
            }
            if (!this.autoBlockMode.get().equalsIgnoreCase("Off")) {
                if (InventoryUtils.findAutoBlockBlock() == -1) {
                    return;
                }
            } else if (f362mc.field_71439_g.func_70694_bm() == null || !(f362mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock)) {
                return;
            }
            findBlock(mode.equalsIgnoreCase("expand") && !towerActivation());
        }
        if (this.targetPlace == null && this.placeableDelay.get().booleanValue()) {
            this.delayTimer.reset();
        }
        if (!towerActivation()) {
            this.verusState = 0;
            this.towerPlace = null;
            return;
        }
        f362mc.field_71428_T.field_74278_d = this.towerTimerValue.get().floatValue();
        if (this.noMoveOnlyValue.get().booleanValue() && this.noMoveFreezeValue.get().booleanValue()) {
            EntityPlayerSP entityPlayerSP = f362mc.field_71439_g;
            f362mc.field_71439_g.field_70179_y = 0.0d;
            entityPlayerSP.field_70159_w = 0.0d;
        }
        if (this.towerPlaceModeValue.get().equalsIgnoreCase(eventState.getStateName())) {
            place(true);
        }
        if (eventState == EventState.PRE) {
            this.towerPlace = null;
            this.timer.update();
            boolean isHeldItemBlock = f362mc.field_71439_g.func_70694_bm() != null && (f362mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock);
            if (InventoryUtils.findAutoBlockBlock() != -1 || isHeldItemBlock) {
                this.launchY = (int) f362mc.field_71439_g.field_70163_u;
                if (this.towerModeValue.get().equalsIgnoreCase("verus") || !this.stopWhenBlockAbove.get().booleanValue() || (BlockUtils.getBlock(new BlockPos(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u + 2.0d, f362mc.field_71439_g.field_70161_v)) instanceof BlockAir)) {
                    move(event);
                }
                BlockPos blockPos = new BlockPos(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u - 1.0d, f362mc.field_71439_g.field_70161_v);
                if ((f362mc.field_71441_e.func_180495_p(blockPos).func_177230_c() instanceof BlockAir) && search(blockPos, true, true) && this.rotationsValue.get().booleanValue() && (vecRotation = RotationUtils.faceBlock(blockPos)) != null) {
                    RotationUtils.setTargetRotation(RotationUtils.limitAngleChange(RotationUtils.serverRotation, vecRotation.getRotation(), RandomUtils.nextFloat(this.minTurnSpeed.get().floatValue(), this.maxTurnSpeed.get().floatValue())));
                    this.towerPlace.setVec3(vecRotation.getVec());
                }
            }
        }
    }

    private void findBlock(boolean expand) {
        BlockPos blockPos;
        if (this.shouldGoDown) {
            blockPos = f362mc.field_71439_g.field_70163_u == ((double) ((int) f362mc.field_71439_g.field_70163_u)) + 0.5d ? new BlockPos(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u - 0.6d, f362mc.field_71439_g.field_70161_v) : new BlockPos(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u - 0.6d, f362mc.field_71439_g.field_70161_v).func_177977_b();
        } else if (towerActivation() || ((!this.sameYValue.get().booleanValue() && ((!this.autoJumpValue.get().booleanValue() && (!this.smartSpeedValue.get().booleanValue() || !LiquidBounce.moduleManager.getModule(Speed.class).getState())) || GameSettings.func_100015_a(f362mc.field_71474_y.field_74314_A))) || this.launchY > f362mc.field_71439_g.field_70163_u)) {
            blockPos = f362mc.field_71439_g.field_70163_u == ((double) ((int) f362mc.field_71439_g.field_70163_u)) + 0.5d ? new BlockPos(f362mc.field_71439_g) : new BlockPos(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.field_70163_u, f362mc.field_71439_g.field_70161_v).func_177977_b();
        } else {
            blockPos = new BlockPos(f362mc.field_71439_g.field_70165_t, this.launchY - 1, f362mc.field_71439_g.field_70161_v);
        }
        BlockPos blockPosition = blockPos;
        if (!expand) {
            if (!BlockUtils.isReplaceable(blockPosition)) {
                return;
            }
            if (search(blockPosition, !this.shouldGoDown, false)) {
                return;
            }
        }
        if (expand) {
            double yaw = Math.toRadians(f362mc.field_71439_g.field_70177_z);
            int x = this.omniDirectionalExpand.get().booleanValue() ? (int) Math.round(-Math.sin(yaw)) : f362mc.field_71439_g.func_174811_aO().func_176730_m().func_177958_n();
            int z = this.omniDirectionalExpand.get().booleanValue() ? (int) Math.round(Math.cos(yaw)) : f362mc.field_71439_g.func_174811_aO().func_176730_m().func_177952_p();
            for (int i = 0; i < this.expandLengthValue.get().intValue() && !search(blockPosition.func_177982_a(x * i, 0, z * i), false, false); i++) {
            }
        } else if (this.searchValue.get().booleanValue()) {
            for (int x2 = -1; x2 <= 1; x2++) {
                for (int z2 = -1; z2 <= 1; z2++) {
                    if (search(blockPosition.func_177982_a(x2, 0, z2), !this.shouldGoDown, false)) {
                        return;
                    }
                }
            }
        }
    }

    private void place(boolean towerActive) {
        if ((towerActive ? this.towerPlace : this.targetPlace) == null) {
            if (this.placeableDelay.get().booleanValue()) {
                this.delayTimer.reset();
                return;
            }
            return;
        }
        if (!towerActivation()) {
            if (!this.delayTimer.hasTimePassed(this.delay)) {
                return;
            }
            if (this.smartDelay.get().booleanValue() && f362mc.field_71467_ac > 0) {
                return;
            }
            if (this.sameYValue.get().booleanValue() || ((this.autoJumpValue.get().booleanValue() || (this.smartSpeedValue.get().booleanValue() && LiquidBounce.moduleManager.getModule(Speed.class).getState())) && !GameSettings.func_100015_a(f362mc.field_71474_y.field_74314_A))) {
                if (this.launchY - 1 != ((int) (towerActive ? this.towerPlace : this.targetPlace).getVec3().field_72448_b)) {
                    return;
                }
            }
        }
        int blockSlot = -1;
        ItemStack itemStack = f362mc.field_71439_g.func_70694_bm();
        if (f362mc.field_71439_g.func_70694_bm() == null || !(f362mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock)) {
            if (this.autoBlockMode.get().equalsIgnoreCase("Off")) {
                return;
            }
            blockSlot = InventoryUtils.findAutoBlockBlock();
            if (blockSlot == -1) {
                return;
            }
            if (this.autoBlockMode.get().equalsIgnoreCase("Spoof")) {
                f362mc.func_147114_u().func_147297_a(new C09PacketHeldItemChange(blockSlot - 36));
                itemStack = f362mc.field_71439_g.field_71069_bz.func_75139_a(blockSlot).func_75211_c();
            } else {
                f362mc.field_71439_g.field_71071_by.field_70461_c = blockSlot - 36;
                f362mc.field_71442_b.func_78765_e();
            }
        }
        if (itemStack != null && itemStack.func_77973_b() != null && (itemStack.func_77973_b() instanceof ItemBlock)) {
            Block block = itemStack.func_77973_b().func_179223_d();
            if (InventoryUtils.BLOCK_BLACKLIST.contains(block) || !block.func_149686_d() || itemStack.field_77994_a <= 0) {
                return;
            }
        }
        if (f362mc.field_71442_b.func_178890_a(f362mc.field_71439_g, f362mc.field_71441_e, itemStack, (towerActive ? this.towerPlace : this.targetPlace).getBlockPos(), (towerActive ? this.towerPlace : this.targetPlace).getEnumFacing(), (towerActive ? this.towerPlace : this.targetPlace).getVec3())) {
            this.delayTimer.reset();
            this.delay = !this.placeableDelay.get().booleanValue() ? 0L : TimeUtils.randomDelay(this.minDelayValue.get().intValue(), this.maxDelayValue.get().intValue());
            if (f362mc.field_71439_g.field_70122_E) {
                float modifier = this.speedModifierValue.get().floatValue();
                f362mc.field_71439_g.field_70159_w *= modifier;
                f362mc.field_71439_g.field_70179_y *= modifier;
            }
            if (this.swingValue.get().booleanValue()) {
                f362mc.field_71439_g.func_71038_i();
            } else {
                f362mc.func_147114_u().func_147297_a(new C0APacketAnimation());
            }
        }
        if (towerActive) {
            this.towerPlace = null;
        } else {
            this.targetPlace = null;
        }
        if (!this.stayAutoBlock.get().booleanValue() && blockSlot >= 0 && !this.autoBlockMode.get().equalsIgnoreCase("Switch")) {
            f362mc.func_147114_u().func_147297_a(new C09PacketHeldItemChange(f362mc.field_71439_g.field_71071_by.field_70461_c));
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        if (f362mc.field_71439_g == null) {
            return;
        }
        if (!GameSettings.func_100015_a(f362mc.field_71474_y.field_74311_E)) {
            f362mc.field_71474_y.field_74311_E.field_74513_e = false;
            if (this.eagleSneaking) {
                f362mc.func_147114_u().func_147297_a(new C0BPacketEntityAction(f362mc.field_71439_g, C0BPacketEntityAction.Action.STOP_SNEAKING));
            }
        }
        if (!GameSettings.func_100015_a(f362mc.field_71474_y.field_74366_z)) {
            f362mc.field_71474_y.field_74366_z.field_74513_e = false;
        }
        if (!GameSettings.func_100015_a(f362mc.field_71474_y.field_74370_x)) {
            f362mc.field_71474_y.field_74370_x.field_74513_e = false;
        }
        this.lockRotation = null;
        this.lookupRotation = null;
        f362mc.field_71428_T.field_74278_d = 1.0f;
        this.shouldGoDown = false;
        this.faceBlock = false;
        if (this.lastSlot != f362mc.field_71439_g.field_71071_by.field_70461_c && this.autoBlockMode.get().equalsIgnoreCase("switch")) {
            f362mc.field_71439_g.field_71071_by.field_70461_c = this.lastSlot;
            f362mc.field_71442_b.func_78765_e();
        }
        if (this.slot != f362mc.field_71439_g.field_71071_by.field_70461_c && this.autoBlockMode.get().equalsIgnoreCase("spoof")) {
            f362mc.func_147114_u().func_147297_a(new C09PacketHeldItemChange(f362mc.field_71439_g.field_71071_by.field_70461_c));
        }
    }

    @EventTarget
    public void onMove(MoveEvent event) {
        if (!this.safeWalkValue.get().booleanValue() || this.shouldGoDown) {
            return;
        }
        if (this.airSafeValue.get().booleanValue() || f362mc.field_71439_g.field_70122_E) {
            event.setSafeWalk(true);
        }
    }

    @EventTarget
    public void onJump(JumpEvent event) {
        if (towerActivation()) {
            event.cancelEvent();
        }
    }

    @EventTarget
    public void onRender2D(Render2DEvent event) {
        this.progress = ((float) (System.currentTimeMillis() - this.lastMS)) / 100.0f;
        if (this.progress >= 1.0f) {
            this.progress = 1.0f;
        }
        String counterMode = this.counterDisplayValue.get();
        ScaledResolution scaledResolution = new ScaledResolution(f362mc);
        String info = getBlocksAmount() + " blocks";
        int infoWidth = Fonts.fontSFUI40.func_78256_a(info);
        int infoWidth2 = Fonts.minecraftFont.func_78256_a(getBlocksAmount() + "");
        if (counterMode.equalsIgnoreCase("simple")) {
            Fonts.minecraftFont.func_175065_a(getBlocksAmount() + "", ((scaledResolution.func_78326_a() / 2) - (infoWidth2 / 2)) - 1, (scaledResolution.func_78328_b() / 2) - 36, -16777216, false);
            Fonts.minecraftFont.func_175065_a(getBlocksAmount() + "", ((scaledResolution.func_78326_a() / 2) - (infoWidth2 / 2)) + 1, (scaledResolution.func_78328_b() / 2) - 36, -16777216, false);
            Fonts.minecraftFont.func_175065_a(getBlocksAmount() + "", (scaledResolution.func_78326_a() / 2) - (infoWidth2 / 2), (scaledResolution.func_78328_b() / 2) - 35, -16777216, false);
            Fonts.minecraftFont.func_175065_a(getBlocksAmount() + "", (scaledResolution.func_78326_a() / 2) - (infoWidth2 / 2), (scaledResolution.func_78328_b() / 2) - 37, -16777216, false);
            Fonts.minecraftFont.func_175065_a(getBlocksAmount() + "", (scaledResolution.func_78326_a() / 2) - (infoWidth2 / 2), (scaledResolution.func_78328_b() / 2) - 36, -1, false);
        }
        if (counterMode.equalsIgnoreCase("advanced")) {
            boolean canRenderStack = this.slot >= 0 && this.slot < 9 && f362mc.field_71439_g.field_71071_by.field_70462_a[this.slot] != null && f362mc.field_71439_g.field_71071_by.field_70462_a[this.slot].func_77973_b() != null && (f362mc.field_71439_g.field_71071_by.field_70462_a[this.slot].func_77973_b() instanceof ItemBlock);
            if (this.blurValue.get().booleanValue()) {
                BlurUtils.blurArea(((scaledResolution.func_78326_a() / 2) - (infoWidth / 2)) - 4, (scaledResolution.func_78328_b() / 2) - 39, (scaledResolution.func_78326_a() / 2) + (infoWidth / 2) + 4, (scaledResolution.func_78328_b() / 2) - (canRenderStack ? 5 : 26), this.blurStrength.get().floatValue());
            }
            RenderUtils.drawRect(((scaledResolution.func_78326_a() / 2) - (infoWidth / 2)) - 4, (scaledResolution.func_78328_b() / 2) - 40, (scaledResolution.func_78326_a() / 2) + (infoWidth / 2) + 4, (scaledResolution.func_78328_b() / 2) - 39, getBlocksAmount() > 1 ? -1 : -61424);
            RenderUtils.drawRect(((scaledResolution.func_78326_a() / 2) - (infoWidth / 2)) - 4, (scaledResolution.func_78328_b() / 2) - 39, (scaledResolution.func_78326_a() / 2) + (infoWidth / 2) + 4, (scaledResolution.func_78328_b() / 2) - 26, -1610612736);
            if (canRenderStack) {
                RenderUtils.drawRect(((scaledResolution.func_78326_a() / 2) - (infoWidth / 2)) - 4, (scaledResolution.func_78328_b() / 2) - 26, (scaledResolution.func_78326_a() / 2) + (infoWidth / 2) + 4, (scaledResolution.func_78328_b() / 2) - 5, -1610612736);
                GlStateManager.func_179094_E();
                GlStateManager.func_179109_b((scaledResolution.func_78326_a() / 2) - 8, (scaledResolution.func_78328_b() / 2) - 25, (scaledResolution.func_78326_a() / 2) - 8);
                renderItemStack(f362mc.field_71439_g.field_71071_by.field_70462_a[this.slot], 0, 0);
                GlStateManager.func_179121_F();
            }
            GlStateManager.func_179117_G();
            Fonts.fontSFUI40.drawCenteredString(info, scaledResolution.func_78326_a() / 2, (scaledResolution.func_78328_b() / 2) - 36, -1);
        }
        if (counterMode.equalsIgnoreCase("sigma")) {
            GlStateManager.func_179109_b(0.0f, (-14.0f) - (this.progress * 4.0f), 0.0f);
            GL11.glEnable(3042);
            GL11.glDisable(3553);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(2848);
            GL11.glColor4f(0.15f, 0.15f, 0.15f, this.progress);
            GL11.glBegin(6);
            GL11.glVertex2d((scaledResolution.func_78326_a() / 2) - 3, scaledResolution.func_78328_b() - 60);
            GL11.glVertex2d(scaledResolution.func_78326_a() / 2, scaledResolution.func_78328_b() - 57);
            GL11.glVertex2d((scaledResolution.func_78326_a() / 2) + 3, scaledResolution.func_78328_b() - 60);
            GL11.glEnd();
            GL11.glEnable(3553);
            GL11.glDisable(3042);
            GL11.glDisable(2848);
            RenderUtils.drawRoundedRect(((scaledResolution.func_78326_a() / 2) - (infoWidth / 2)) - 4, scaledResolution.func_78328_b() - 60, (scaledResolution.func_78326_a() / 2) + (infoWidth / 2) + 4, scaledResolution.func_78328_b() - 74, 2.0f, new Color(0.15f, 0.15f, 0.15f, this.progress).getRGB());
            GlStateManager.func_179117_G();
            Fonts.fontSFUI35.drawCenteredString(info, (scaledResolution.func_78326_a() / 2) + 0.1f, scaledResolution.func_78328_b() - 70, new Color(1.0f, 1.0f, 1.0f, 0.8f * this.progress).getRGB(), false);
            GlStateManager.func_179109_b(0.0f, 14.0f + (this.progress * 4.0f), 0.0f);
        }
        if (counterMode.equalsIgnoreCase("novoline")) {
            if (this.slot >= 0 && this.slot < 9 && f362mc.field_71439_g.field_71071_by.field_70462_a[this.slot] != null && f362mc.field_71439_g.field_71071_by.field_70462_a[this.slot].func_77973_b() != null && (f362mc.field_71439_g.field_71071_by.field_70462_a[this.slot].func_77973_b() instanceof ItemBlock)) {
                GlStateManager.func_179094_E();
                GlStateManager.func_179109_b((scaledResolution.func_78326_a() / 2) - 22, (scaledResolution.func_78328_b() / 2) + 16, (scaledResolution.func_78326_a() / 2) - 22);
                renderItemStack(f362mc.field_71439_g.field_71071_by.field_70462_a[this.slot], 0, 0);
                GlStateManager.func_179121_F();
            }
            GlStateManager.func_179117_G();
            Fonts.minecraftFont.func_175065_a(getBlocksAmount() + " blocks", scaledResolution.func_78326_a() / 2, (scaledResolution.func_78328_b() / 2) + 20, -1, true);
        }
    }

    private void renderItemStack(ItemStack stack, int x, int y) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179091_B();
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        RenderHelper.func_74520_c();
        f362mc.func_175599_af().func_180450_b(stack, x, y);
        f362mc.func_175599_af().func_175030_a(f362mc.field_71466_p, stack, x, y);
        RenderHelper.func_74518_a();
        GlStateManager.func_179101_C();
        GlStateManager.func_179084_k();
        GlStateManager.func_179121_F();
    }

    @EventTarget
    public void onRender3D(Render3DEvent event) {
        double d;
        if (!this.markValue.get().booleanValue()) {
            return;
        }
        double yaw = Math.toRadians(f362mc.field_71439_g.field_70177_z);
        int x = this.omniDirectionalExpand.get().booleanValue() ? (int) Math.round(-Math.sin(yaw)) : f362mc.field_71439_g.func_174811_aO().func_176730_m().func_177958_n();
        int z = this.omniDirectionalExpand.get().booleanValue() ? (int) Math.round(Math.cos(yaw)) : f362mc.field_71439_g.func_174811_aO().func_176730_m().func_177952_p();
        int i = 0;
        while (true) {
            if (i < ((!this.modeValue.get().equalsIgnoreCase("Expand") || towerActivation()) ? 2 : this.expandLengthValue.get().intValue() + 1)) {
                double d2 = f362mc.field_71439_g.field_70165_t + (x * i);
                if (towerActivation() || ((!this.sameYValue.get().booleanValue() && ((!this.autoJumpValue.get().booleanValue() && (!this.smartSpeedValue.get().booleanValue() || !LiquidBounce.moduleManager.getModule(Speed.class).getState())) || GameSettings.func_100015_a(f362mc.field_71474_y.field_74314_A))) || this.launchY > f362mc.field_71439_g.field_70163_u)) {
                    d = (f362mc.field_71439_g.field_70163_u - (f362mc.field_71439_g.field_70163_u == ((double) ((int) f362mc.field_71439_g.field_70163_u)) + 0.5d ? 0.0d : 1.0d)) - (this.shouldGoDown ? 1.0d : 0.0d);
                } else {
                    d = this.launchY - 1;
                }
                BlockPos blockPos = new BlockPos(d2, d, f362mc.field_71439_g.field_70161_v + (z * i));
                PlaceInfo placeInfo = PlaceInfo.get(blockPos);
                if (!BlockUtils.isReplaceable(blockPos) || placeInfo == null) {
                    i++;
                } else {
                    RenderUtils.drawBlockBox(blockPos, new Color(this.redValue.get().intValue(), this.greenValue.get().intValue(), this.blueValue.get().intValue(), this.alphaValue.get().intValue()), false);
                    return;
                }
            } else {
                return;
            }
        }
    }

    private boolean search(BlockPos blockPosition, boolean checks) {
        return search(blockPosition, checks, false);
    }

    private boolean search(BlockPos blockPosition, boolean checks, boolean towerActive) {
        EnumFacing[] facings;
        this.faceBlock = false;
        if (!BlockUtils.isReplaceable(blockPosition)) {
            return false;
        }
        boolean staticYawMode = this.rotationLookupValue.get().equalsIgnoreCase("AAC") || (this.rotationLookupValue.get().equalsIgnoreCase("same") && (this.rotationModeValue.get().equalsIgnoreCase("AAC") || (this.rotationModeValue.get().contains("Static") && !this.rotationModeValue.get().equalsIgnoreCase("static3"))));
        Vec3 eyesPos = new Vec3(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.func_174813_aQ().field_72338_b + f362mc.field_71439_g.func_70047_e(), f362mc.field_71439_g.field_70161_v);
        PlaceRotation placeRotation = null;
        for (EnumFacing side : StaticStorage.facings()) {
            BlockPos neighbor = blockPosition.func_177972_a(side);
            if (BlockUtils.canBeClicked(neighbor)) {
                Vec3 dirVec = new Vec3(side.func_176730_m());
                double d = 0.1d;
                while (true) {
                    double xSearch = d;
                    if (xSearch < 0.9d) {
                        double d2 = 0.1d;
                        while (true) {
                            double ySearch = d2;
                            if (ySearch < 0.9d) {
                                double d3 = 0.1d;
                                while (true) {
                                    double zSearch = d3;
                                    if (zSearch < 0.9d) {
                                        Vec3 posVec = new Vec3(blockPosition).func_72441_c(xSearch, ySearch, zSearch);
                                        double distanceSqPosVec = eyesPos.func_72436_e(posVec);
                                        Vec3 hitVec = posVec.func_178787_e(new Vec3(dirVec.field_72450_a * 0.5d, dirVec.field_72448_b * 0.5d, dirVec.field_72449_c * 0.5d));
                                        if (!checks || (eyesPos.func_72436_e(hitVec) <= 18.0d && distanceSqPosVec <= eyesPos.func_72436_e(posVec.func_178787_e(dirVec)) && f362mc.field_71441_e.func_147447_a(eyesPos, hitVec, false, true, false) == null)) {
                                            int i = 0;
                                            while (true) {
                                                if (i < (staticYawMode ? 2 : 1)) {
                                                    double diffX = (!staticYawMode || i != 0) ? hitVec.field_72450_a - eyesPos.field_72450_a : 0.0d;
                                                    double diffY = hitVec.field_72448_b - eyesPos.field_72448_b;
                                                    double diffZ = (!staticYawMode || i != 1) ? hitVec.field_72449_c - eyesPos.field_72449_c : 0.0d;
                                                    double diffXZ = MathHelper.func_76133_a((diffX * diffX) + (diffZ * diffZ));
                                                    Rotation rotation = new Rotation(MathHelper.func_76142_g(((float) Math.toDegrees(Math.atan2(diffZ, diffX))) - 90.0f), MathHelper.func_76142_g((float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)))));
                                                    this.lookupRotation = rotation;
                                                    if (this.rotationModeValue.get().equalsIgnoreCase("static") && (this.keepRotOnJumpValue.get().booleanValue() || !f362mc.field_71474_y.field_74314_A.func_151470_d())) {
                                                        rotation = new Rotation(MovementUtils.getScaffoldRotation(f362mc.field_71439_g.field_70177_z, f362mc.field_71439_g.field_70702_br), this.staticPitchValue.get().floatValue());
                                                    }
                                                    if ((this.rotationModeValue.get().equalsIgnoreCase("static2") || this.rotationModeValue.get().equalsIgnoreCase("static3")) && (this.keepRotOnJumpValue.get().booleanValue() || !f362mc.field_71474_y.field_74314_A.func_151470_d())) {
                                                        rotation = new Rotation(rotation.getYaw(), this.staticPitchValue.get().floatValue());
                                                    }
                                                    if (this.rotationModeValue.get().equalsIgnoreCase("custom") && (this.keepRotOnJumpValue.get().booleanValue() || !f362mc.field_71474_y.field_74314_A.func_151470_d())) {
                                                        rotation = new Rotation(f362mc.field_71439_g.field_70177_z + this.customYawValue.get().floatValue(), this.customPitchValue.get().floatValue());
                                                    }
                                                    if (this.rotationModeValue.get().equalsIgnoreCase("spin") && this.speenRotation != null && (this.keepRotOnJumpValue.get().booleanValue() || !f362mc.field_71474_y.field_74314_A.func_151470_d())) {
                                                        rotation = this.speenRotation;
                                                    }
                                                    Vec3 rotationVector = RotationUtils.getVectorForRotation(this.rotationLookupValue.get().equalsIgnoreCase("same") ? rotation : this.lookupRotation);
                                                    Vec3 vector = eyesPos.func_72441_c(rotationVector.field_72450_a * 4.0d, rotationVector.field_72448_b * 4.0d, rotationVector.field_72449_c * 4.0d);
                                                    MovingObjectPosition obj = f362mc.field_71441_e.func_147447_a(eyesPos, vector, false, false, true);
                                                    if (obj.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK && obj.func_178782_a().equals(neighbor) && (placeRotation == null || RotationUtils.getRotationDifference(rotation) < RotationUtils.getRotationDifference(placeRotation.getRotation()))) {
                                                        placeRotation = new PlaceRotation(new PlaceInfo(neighbor, side.func_176734_d(), hitVec), rotation);
                                                    }
                                                    i++;
                                                }
                                            }
                                        }
                                        d3 = zSearch + 0.1d;
                                    }
                                }
                                d2 = ySearch + 0.1d;
                            }
                        }
                        d = xSearch + 0.1d;
                    }
                }
            }
        }
        if (placeRotation == null) {
            return false;
        }
        if (this.rotationsValue.get().booleanValue()) {
            if (this.minTurnSpeed.get().floatValue() < 180.0f) {
                Rotation limitedRotation = RotationUtils.limitAngleChange(RotationUtils.serverRotation, placeRotation.getRotation(), RandomUtils.nextFloat(this.minTurnSpeed.get().floatValue(), this.maxTurnSpeed.get().floatValue()));
                if (((int) (10.0f * MathHelper.func_76142_g(limitedRotation.getYaw()))) == ((int) (10.0f * MathHelper.func_76142_g(placeRotation.getRotation().getYaw()))) && ((int) (10.0f * MathHelper.func_76142_g(limitedRotation.getPitch()))) == ((int) (10.0f * MathHelper.func_76142_g(placeRotation.getRotation().getPitch())))) {
                    RotationUtils.setTargetRotation(placeRotation.getRotation(), this.keepLengthValue.get().intValue());
                    this.lockRotation = placeRotation.getRotation();
                    this.faceBlock = true;
                } else {
                    RotationUtils.setTargetRotation(limitedRotation, this.keepLengthValue.get().intValue());
                    this.lockRotation = limitedRotation;
                    this.faceBlock = false;
                }
            } else {
                RotationUtils.setTargetRotation(placeRotation.getRotation(), this.keepLengthValue.get().intValue());
                this.lockRotation = placeRotation.getRotation();
                this.faceBlock = true;
            }
            if (this.rotationLookupValue.get().equalsIgnoreCase("same")) {
                this.lookupRotation = this.lockRotation;
            }
        }
        if (towerActive) {
            this.towerPlace = placeRotation.getPlaceInfo();
            return true;
        }
        this.targetPlace = placeRotation.getPlaceInfo();
        return true;
    }

    private int getBlocksAmount() {
        int amount = 0;
        for (int i = 36; i < 45; i++) {
            ItemStack itemStack = f362mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
            if (itemStack != null && (itemStack.func_77973_b() instanceof ItemBlock)) {
                Block block = itemStack.func_77973_b().func_179223_d();
                if (!InventoryUtils.BLOCK_BLACKLIST.contains(block) && block.func_149686_d()) {
                    amount += itemStack.field_77994_a;
                }
            }
        }
        return amount;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public String getTag() {
        return towerActivation() ? "Tower, " + this.towerPlaceModeValue.get() : this.placeModeValue.get();
    }
}
