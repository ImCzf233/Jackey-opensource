package net.ccbluex.liquidbounce.features.module.modules.movement;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Disabler;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AAC2BHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AAC3BHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AAC4BHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AAC4Hop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AAC4SlowHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AAC5BHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AAC6BHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AAC7BHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACBHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACGround;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACGround2;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACHop3313;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACHop350;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACHop438;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACLowHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACLowHop2;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACLowHop3;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACPort;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACYPort;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACYPort2;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACv4BHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.OldAACBHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.hypixel.HypixelBoost;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.hypixel.HypixelCustom;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.hypixel.HypixelStable;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.Boost;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.Frame;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.MiJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.NCPBHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.NCPFHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.NCPHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.NCPYPort;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.OnGround;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.SNCPBHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.YPort;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.YPort2;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other.AEMine;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other.CustomSpeed;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other.GWEN;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other.HiveHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other.Jump;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other.Legit;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other.MineplexGround;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other.SlowHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other.TeleportCubeCraft;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.spartan.SpartanYPort;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.spectre.SpectreBHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.spectre.SpectreLowHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.spectre.SpectreOnGround;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.verus.VerusHard;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.verus.VulcanHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.verus.VulcanLowHop;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.settings.GameSettings;

@ModuleInfo(name = "Speed", description = "Allows you to move faster.", category = ModuleCategory.MOVEMENT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/Speed.class */
public class Speed extends Module {
    public final SpeedMode[] speedModes = {new NCPBHop(), new NCPFHop(), new SNCPBHop(), new NCPHop(), new NCPYPort(), new AAC4Hop(), new AAC4SlowHop(), new AACv4BHop(), new AACBHop(), new AAC2BHop(), new AAC3BHop(), new AAC4BHop(), new AAC5BHop(), new AAC6BHop(), new AAC7BHop(), new OldAACBHop(), new AACPort(), new AACLowHop(), new AACLowHop2(), new AACLowHop3(), new AACGround(), new AACGround2(), new AACHop350(), new AACHop3313(), new AACHop438(), new AACYPort(), new AACYPort2(), new HypixelBoost(), new HypixelStable(), new HypixelCustom(), new SpartanYPort(), new SpectreBHop(), new SpectreLowHop(), new SpectreOnGround(), new SlowHop(), new CustomSpeed(), new Jump(), new Legit(), new AEMine(), new GWEN(), new Boost(), new Frame(), new MiJump(), new OnGround(), new YPort(), new YPort2(), new HiveHop(), new MineplexGround(), new TeleportCubeCraft(), new VulcanHop(), new VulcanLowHop(), new VerusHard()};
    public final ListValue typeValue = new ListValue("Type", new String[]{"NCP", "AAC", "Spartan", "Spectre", "Hypixel", "Verus", "Custom", "Other"}, "NCP") { // from class: net.ccbluex.liquidbounce.features.module.modules.movement.Speed.1
        public void onChange(String oldValue, String newValue) {
            if (Speed.this.getState()) {
                Speed.this.onDisable();
            }
        }

        public void onChanged(String oldValue, String newValue) {
            if (Speed.this.getState()) {
                Speed.this.onEnable();
            }
        }
    };
    public final ListValue ncpModeValue = new ListValue("NCP-Mode", new String[]{"BHop", "FHop", "SBHop", "Hop", "YPort"}, "BHop", () -> {
        return Boolean.valueOf(this.typeValue.get().equalsIgnoreCase("ncp"));
    }) { // from class: net.ccbluex.liquidbounce.features.module.modules.movement.Speed.2
        public void onChange(String oldValue, String newValue) {
            if (Speed.this.getState()) {
                Speed.this.onDisable();
            }
        }

        public void onChanged(String oldValue, String newValue) {
            if (Speed.this.getState()) {
                Speed.this.onEnable();
            }
        }
    };
    public final ListValue aacModeValue = new ListValue("AAC-Mode", new String[]{"4Hop", "4SlowHop", "v4BHop", "BHop", "2BHop", "3BHop", "4BHop", "5BHop", "6BHop", "7BHop", "OldBHop", "Port", "LowHop", "LowHop2", "LowHop3", "Ground", "Ground2", "Hop3.5.0", "Hop3.3.13", "Hop4.3.8", "YPort", "YPort2"}, "4Hop", () -> {
        return Boolean.valueOf(this.typeValue.get().equalsIgnoreCase("aac"));
    }) { // from class: net.ccbluex.liquidbounce.features.module.modules.movement.Speed.3
        public void onChange(String oldValue, String newValue) {
            if (Speed.this.getState()) {
                Speed.this.onDisable();
            }
        }

        public void onChanged(String oldValue, String newValue) {
            if (Speed.this.getState()) {
                Speed.this.onEnable();
            }
        }
    };
    public final ListValue hypixelModeValue = new ListValue("Hypixel-Mode", new String[]{"Boost", "Stable", "Custom"}, "Stable", () -> {
        return Boolean.valueOf(this.typeValue.get().equalsIgnoreCase("hypixel"));
    }) { // from class: net.ccbluex.liquidbounce.features.module.modules.movement.Speed.4
        public void onChange(String oldValue, String newValue) {
            if (Speed.this.getState()) {
                Speed.this.onDisable();
            }
        }

        public void onChanged(String oldValue, String newValue) {
            if (Speed.this.getState()) {
                Speed.this.onEnable();
            }
        }
    };
    public final ListValue spectreModeValue = new ListValue("Spectre-Mode", new String[]{"BHop", "LowHop", "OnGround"}, "BHop", () -> {
        return Boolean.valueOf(this.typeValue.get().equalsIgnoreCase("spectre"));
    }) { // from class: net.ccbluex.liquidbounce.features.module.modules.movement.Speed.5
        public void onChange(String oldValue, String newValue) {
            if (Speed.this.getState()) {
                Speed.this.onDisable();
            }
        }

        public void onChanged(String oldValue, String newValue) {
            if (Speed.this.getState()) {
                Speed.this.onEnable();
            }
        }
    };
    public final ListValue otherModeValue = new ListValue("Other-Mode", new String[]{"YPort", "YPort2", "Boost", "Frame", "MiJump", "OnGround", "SlowHop", "Jump", "Legit", "AEMine", "GWEN", "HiveHop", "MineplexGround", "TeleportCubeCraft"}, "Boost", () -> {
        return Boolean.valueOf(this.typeValue.get().equalsIgnoreCase("other"));
    }) { // from class: net.ccbluex.liquidbounce.features.module.modules.movement.Speed.6
        public void onChange(String oldValue, String newValue) {
            if (Speed.this.getState()) {
                Speed.this.onDisable();
            }
        }

        public void onChanged(String oldValue, String newValue) {
            if (Speed.this.getState()) {
                Speed.this.onEnable();
            }
        }
    };
    public final ListValue verusModeValue = new ListValue("Verus-Mode", new String[]{"Hop", "LowHop", "Hard"}, "Hop", () -> {
        return Boolean.valueOf(this.typeValue.get().equalsIgnoreCase("verus"));
    }) { // from class: net.ccbluex.liquidbounce.features.module.modules.movement.Speed.7
        public void onChange(String oldValue, String newValue) {
            if (Speed.this.getState()) {
                Speed.this.onDisable();
            }
        }

        public void onChanged(String oldValue, String newValue) {
            if (Speed.this.getState()) {
                Speed.this.onEnable();
            }
        }
    };
    public final BoolValue modifySprint = new BoolValue("ModifySprinting", true);
    public final BoolValue timerValue = new BoolValue("UseTimer", true, () -> {
        return Boolean.valueOf(getModeName().equalsIgnoreCase("hypixelcustom"));
    });
    public final BoolValue smoothStrafe = new BoolValue("SmoothStrafe", true, () -> {
        return Boolean.valueOf(getModeName().equalsIgnoreCase("hypixelcustom"));
    });
    public final FloatValue customSpeedValue = new FloatValue("StrSpeed", 0.42f, 0.2f, 2.0f, () -> {
        return Boolean.valueOf(getModeName().equalsIgnoreCase("hypixelcustom"));
    });
    public final FloatValue motionYValue = new FloatValue("MotionY", 0.42f, 0.0f, 2.0f, () -> {
        return Boolean.valueOf(getModeName().equalsIgnoreCase("hypixelcustom"));
    });
    public final FloatValue verusTimer = new FloatValue("Verus-Timer", 1.0f, 0.1f, 10.0f, () -> {
        return Boolean.valueOf(getModeName().equalsIgnoreCase("verushard"));
    });
    public final FloatValue speedValue = new FloatValue("CustomSpeed", 1.6f, 0.2f, 2.0f, () -> {
        return Boolean.valueOf(this.typeValue.get().equalsIgnoreCase("custom"));
    });
    public final FloatValue launchSpeedValue = new FloatValue("CustomLaunchSpeed", 1.6f, 0.2f, 2.0f, () -> {
        return Boolean.valueOf(this.typeValue.get().equalsIgnoreCase("custom"));
    });
    public final FloatValue addYMotionValue = new FloatValue("CustomAddYMotion", 0.0f, 0.0f, 2.0f, () -> {
        return Boolean.valueOf(this.typeValue.get().equalsIgnoreCase("custom"));
    });
    public final FloatValue yValue = new FloatValue("CustomY", 0.0f, 0.0f, 4.0f, () -> {
        return Boolean.valueOf(this.typeValue.get().equalsIgnoreCase("custom"));
    });
    public final FloatValue upTimerValue = new FloatValue("CustomUpTimer", 1.0f, 0.1f, 2.0f, () -> {
        return Boolean.valueOf(this.typeValue.get().equalsIgnoreCase("custom"));
    });
    public final FloatValue downTimerValue = new FloatValue("CustomDownTimer", 1.0f, 0.1f, 2.0f, () -> {
        return Boolean.valueOf(this.typeValue.get().equalsIgnoreCase("custom"));
    });
    public final ListValue strafeValue = new ListValue("CustomStrafe", new String[]{"Strafe", "Boost", "Plus", "PlusOnlyUp", "Non-Strafe"}, "Boost", () -> {
        return Boolean.valueOf(this.typeValue.get().equalsIgnoreCase("custom"));
    });
    public final IntegerValue groundStay = new IntegerValue("CustomGroundStay", 0, 0, 10, () -> {
        return Boolean.valueOf(this.typeValue.get().equalsIgnoreCase("custom"));
    });
    public final BoolValue groundResetXZValue = new BoolValue("CustomGroundResetXZ", false, () -> {
        return Boolean.valueOf(this.typeValue.get().equalsIgnoreCase("custom"));
    });
    public final BoolValue resetXZValue = new BoolValue("CustomResetXZ", false, () -> {
        return Boolean.valueOf(this.typeValue.get().equalsIgnoreCase("custom"));
    });
    public final BoolValue resetYValue = new BoolValue("CustomResetY", false, () -> {
        return Boolean.valueOf(this.typeValue.get().equalsIgnoreCase("custom"));
    });
    public final BoolValue doLaunchSpeedValue = new BoolValue("CustomDoLaunchSpeed", true, () -> {
        return Boolean.valueOf(this.typeValue.get().equalsIgnoreCase("custom"));
    });
    public final BoolValue jumpStrafe = new BoolValue("JumpStrafe", false, () -> {
        return Boolean.valueOf(this.typeValue.get().equalsIgnoreCase("other"));
    });
    public final BoolValue sendJumpValue = new BoolValue("SendJump", true, () -> {
        return Boolean.valueOf(this.typeValue.get().equalsIgnoreCase("hypixel") && !getModeName().equalsIgnoreCase("hypixelcustom"));
    });
    public final BoolValue recalcValue = new BoolValue("ReCalculate", true, () -> {
        return Boolean.valueOf(this.typeValue.get().equalsIgnoreCase("hypixel") && this.sendJumpValue.get().booleanValue() && !getModeName().equalsIgnoreCase("hypixelcustom"));
    });
    public final FloatValue glideStrengthValue = new FloatValue("GlideStrength", 0.03f, 0.0f, 0.05f, () -> {
        return Boolean.valueOf(this.typeValue.get().equalsIgnoreCase("hypixel") && !getModeName().equalsIgnoreCase("hypixelcustom"));
    });
    public final FloatValue moveSpeedValue = new FloatValue("MoveSpeed", 1.47f, 1.0f, 1.7f, () -> {
        return Boolean.valueOf(this.typeValue.get().equalsIgnoreCase("hypixel") && !getModeName().equalsIgnoreCase("hypixelcustom"));
    });
    public final FloatValue jumpYValue = new FloatValue("JumpY", 0.42f, 0.0f, 1.0f, () -> {
        return Boolean.valueOf(this.typeValue.get().equalsIgnoreCase("hypixel") && !getModeName().equalsIgnoreCase("hypixelcustom"));
    });
    public final FloatValue baseStrengthValue = new FloatValue("BaseMultiplier", 1.0f, 0.5f, 1.0f, () -> {
        return Boolean.valueOf(this.typeValue.get().equalsIgnoreCase("hypixel") && !getModeName().equalsIgnoreCase("hypixelcustom"));
    });
    public final FloatValue baseTimerValue = new FloatValue("BaseTimer", 1.5f, 1.0f, 3.0f, () -> {
        return Boolean.valueOf(getModeName().equalsIgnoreCase("hypixelboost"));
    });
    public final FloatValue baseMTimerValue = new FloatValue("BaseMultiplierTimer", 1.0f, 0.0f, 3.0f, () -> {
        return Boolean.valueOf(getModeName().equalsIgnoreCase("hypixelboost"));
    });
    public final BoolValue bypassWarning = new BoolValue("BypassWarning", true, () -> {
        return Boolean.valueOf(this.typeValue.get().equalsIgnoreCase("hypixel") && !getModeName().equalsIgnoreCase("hypixelcustom"));
    });
    public final FloatValue portMax = new FloatValue("AAC-PortLength", 1.0f, 1.0f, 20.0f, () -> {
        return Boolean.valueOf(this.typeValue.get().equalsIgnoreCase("aac"));
    });
    public final FloatValue aacGroundTimerValue = new FloatValue("AACGround-Timer", 3.0f, 1.1f, 10.0f, () -> {
        return Boolean.valueOf(this.typeValue.get().equalsIgnoreCase("aac"));
    });
    public final FloatValue cubecraftPortLengthValue = new FloatValue("CubeCraft-PortLength", 1.0f, 0.1f, 2.0f, () -> {
        return Boolean.valueOf(getModeName().equalsIgnoreCase("teleportcubecraft"));
    });
    public final FloatValue mineplexGroundSpeedValue = new FloatValue("MineplexGround-Speed", 0.5f, 0.1f, 1.0f, () -> {
        return Boolean.valueOf(getModeName().equalsIgnoreCase("mineplexground"));
    });
    public final ListValue tagDisplay = new ListValue("Tag", new String[]{"Type", "FullName", "All"}, "Type");

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if (f362mc.field_71439_g.func_70093_af()) {
            return;
        }
        if (MovementUtils.isMoving() && this.modifySprint.get().booleanValue()) {
            f362mc.field_71439_g.func_70031_b(!getModeName().equalsIgnoreCase("verushard"));
        }
        SpeedMode speedMode = getMode();
        if (speedMode != null) {
            speedMode.onUpdate();
        }
    }

    @EventTarget
    public void onMotion(MotionEvent event) {
        if (f362mc.field_71439_g.func_70093_af() || event.getEventState() != EventState.PRE) {
            return;
        }
        if (MovementUtils.isMoving() && this.modifySprint.get().booleanValue()) {
            f362mc.field_71439_g.func_70031_b(!getModeName().equalsIgnoreCase("verushard"));
        }
        SpeedMode speedMode = getMode();
        if (speedMode != null) {
            speedMode.onMotion(event);
            speedMode.onMotion();
        }
    }

    @EventTarget
    public void onMove(MoveEvent event) {
        SpeedMode speedMode;
        if (!f362mc.field_71439_g.func_70093_af() && (speedMode = getMode()) != null) {
            speedMode.onMove(event);
        }
    }

    @EventTarget
    public void onTick(TickEvent event) {
        SpeedMode speedMode;
        if (!f362mc.field_71439_g.func_70093_af() && (speedMode = getMode()) != null) {
            speedMode.onTick();
        }
    }

    @EventTarget
    public void onJump(JumpEvent event) {
        SpeedMode speedMode = getMode();
        if (speedMode != null) {
            speedMode.onJump(event);
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        if (f362mc.field_71439_g == null) {
            return;
        }
        if (this.bypassWarning.get().booleanValue() && this.typeValue.get().equalsIgnoreCase("hypixel") && !LiquidBounce.moduleManager.getModule(Disabler.class).getState()) {
            LiquidBounce.hud.addNotification(new Notification("Disabler is OFF! Disable this notification in settings.", Notification.Type.WARNING, 3000L));
        }
        f362mc.field_71428_T.field_74278_d = 1.0f;
        SpeedMode speedMode = getMode();
        if (speedMode != null) {
            speedMode.onEnable();
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        if (f362mc.field_71439_g == null) {
            return;
        }
        f362mc.field_71428_T.field_74278_d = 1.0f;
        f362mc.field_71474_y.field_74314_A.field_74513_e = f362mc.field_71439_g != null && (f362mc.field_71415_G || LiquidBounce.moduleManager.getModule(InvMove.class).getState()) && !(f362mc.field_71462_r instanceof GuiIngameMenu) && !(f362mc.field_71462_r instanceof GuiChat) && GameSettings.func_100015_a(f362mc.field_71474_y.field_74314_A);
        SpeedMode speedMode = getMode();
        if (speedMode != null) {
            speedMode.onDisable();
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public String getTag() {
        if (this.tagDisplay.get().equalsIgnoreCase("type")) {
            return this.typeValue.get();
        }
        if (this.tagDisplay.get().equalsIgnoreCase("fullname")) {
            return getModeName();
        }
        return this.typeValue.get() == "Other" ? this.otherModeValue.get() : this.typeValue.get() == "Custom" ? "Custom" : this.typeValue.get() + ", " + getOnlySingleName();
    }

    private String getOnlySingleName() {
        String mode = "";
        String str = this.typeValue.get();
        boolean z = true;
        switch (str.hashCode()) {
            case -1248403467:
                if (str.equals("Hypixel")) {
                    z = true;
                    break;
                }
                break;
            case -347048589:
                if (str.equals("Spartan")) {
                    z = true;
                    break;
                }
                break;
            case -343800852:
                if (str.equals("Spectre")) {
                    z = true;
                    break;
                }
                break;
            case 64547:
                if (str.equals("AAC")) {
                    z = true;
                    break;
                }
                break;
            case 77115:
                if (str.equals("NCP")) {
                    z = false;
                    break;
                }
                break;
            case 82544993:
                if (str.equals("Verus")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                mode = this.ncpModeValue.get();
                break;
            case true:
                mode = this.aacModeValue.get();
                break;
            case true:
                mode = "Spartan";
                break;
            case true:
                mode = this.spectreModeValue.get();
                break;
            case true:
                mode = this.hypixelModeValue.get();
                break;
            case true:
                mode = this.verusModeValue.get();
                break;
        }
        return mode;
    }

    public String getModeName() {
        String mode = "";
        String str = this.typeValue.get();
        boolean z = true;
        switch (str.hashCode()) {
            case -1248403467:
                if (str.equals("Hypixel")) {
                    z = true;
                    break;
                }
                break;
            case -347048589:
                if (str.equals("Spartan")) {
                    z = true;
                    break;
                }
                break;
            case -343800852:
                if (str.equals("Spectre")) {
                    z = true;
                    break;
                }
                break;
            case 64547:
                if (str.equals("AAC")) {
                    z = true;
                    break;
                }
                break;
            case 77115:
                if (str.equals("NCP")) {
                    z = false;
                    break;
                }
                break;
            case 76517104:
                if (str.equals("Other")) {
                    z = true;
                    break;
                }
                break;
            case 82544993:
                if (str.equals("Verus")) {
                    z = true;
                    break;
                }
                break;
            case 2029746065:
                if (str.equals("Custom")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                if (!this.ncpModeValue.get().equalsIgnoreCase("SBHop")) {
                    mode = "NCP" + this.ncpModeValue.get();
                    break;
                } else {
                    mode = "SNCPBHop";
                    break;
                }
            case true:
                if (!this.aacModeValue.get().equalsIgnoreCase("oldbhop")) {
                    mode = "AAC" + this.aacModeValue.get();
                    break;
                } else {
                    mode = "OldAACBHop";
                    break;
                }
            case true:
                mode = "SpartanYPort";
                break;
            case true:
                mode = "Spectre" + this.spectreModeValue.get();
                break;
            case true:
                mode = "Hypixel" + this.hypixelModeValue.get();
                break;
            case true:
                mode = "Verus" + this.verusModeValue.get();
                break;
            case true:
                mode = "Custom";
                break;
            case true:
                mode = this.otherModeValue.get();
                break;
        }
        return mode;
    }

    public SpeedMode getMode() {
        SpeedMode[] speedModeArr;
        for (SpeedMode speedMode : this.speedModes) {
            if (speedMode.modeName.equalsIgnoreCase(getModeName())) {
                return speedMode;
            }
        }
        return null;
    }
}
