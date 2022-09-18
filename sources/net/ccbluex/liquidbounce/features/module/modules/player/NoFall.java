package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.Fly;
import net.ccbluex.liquidbounce.features.module.modules.render.FreeCam;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.misc.NewFallingPlayer;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TickTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: NoFall.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u009c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\b\n��\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010>\u001a\u00020\u00042\u0006\u0010?\u001a\u00020%2\u0006\u0010@\u001a\u00020%H\u0002J\b\u0010A\u001a\u00020\u0004H\u0002J\b\u0010B\u001a\u00020CH\u0016J\b\u0010D\u001a\u00020CH\u0016J\u0010\u0010E\u001a\u00020C2\u0006\u0010F\u001a\u00020GH\u0007J\u0010\u0010H\u001a\u00020C2\u0006\u0010F\u001a\u00020IH\u0007J\u0010\u0010J\u001a\u00020C2\u0006\u0010F\u001a\u00020KH\u0007J\u0010\u0010L\u001a\u00020C2\u0006\u0010F\u001a\u00020MH\u0007J\u0010\u0010N\u001a\u00020C2\u0006\u0010F\u001a\u00020OH\u0007J\u0010\u0010P\u001a\u00020C2\u0006\u0010F\u001a\u00020QH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n��R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0013\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n��R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0016\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u0011\u0010\u0017\u001a\u00020\u0010¢\u0006\b\n��\u001a\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001c\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001d\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u001e\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u001f\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010 \u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010!\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\"\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010#\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010$\u001a\u00020%X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010&\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010'\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010(\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010)\u001a\u00020*X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010+\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010,\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010-\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010.\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010/\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n��R\u000e\u00100\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u00101\u001a\u000202X\u0082\u0004¢\u0006\u0002\n��R\u000e\u00103\u001a\u00020*X\u0082\u0004¢\u0006\u0002\n��R\u0014\u00104\u001a\u0002058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b6\u00107R\u0011\u00108\u001a\u00020\u0010¢\u0006\b\n��\u001a\u0004\b9\u0010\u0019R\u000e\u0010:\u001a\u00020;X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010<\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010=\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��¨\u0006R"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/NoFall;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "aac4Fakelag", "", "aac4FlagCooldown", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "aac4FlagCount", "", "aac4Packets", "", "Lnet/minecraft/network/play/client/C03PacketPlayer;", "aac5Check", "aac5Timer", "aac5doFlag", "aacMode", "Lnet/ccbluex/liquidbounce/value/ListValue;", "currentMlgBlock", "Lnet/minecraft/util/BlockPos;", "currentMlgItemIndex", "currentMlgRotation", "Lnet/ccbluex/liquidbounce/utils/VecRotation;", "doSpoof", "editMode", "getEditMode", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "flySpeedValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "hypixelMode", "isDmgFalling", "jumped", "lastFallDistRounded", "matrixCanSpoof", "matrixFallTicks", "matrixFalling", "matrixFlagWait", "matrixLastMotionY", "", "matrixMode", "matrixSend", "minFallDistanceValue", "mlgTimer", "Lnet/ccbluex/liquidbounce/utils/timer/TickTimer;", "modifiedTimer", "needSpoof", "nextSpoof", "oldaacState", "packetMode", "packetModify", "phaseOffsetValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "spartanTimer", "tag", "", "getTag", "()Ljava/lang/String;", "typeValue", "getTypeValue", "voidCheckValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "vulcanNoFall", "vulcantNoFall", "inAir", "height", "plus", "inVoid", "onDisable", "", "onEnable", "onJump", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "LiquidBounce"})
@ModuleInfo(name = "NoFall", spacedName = "No Fall", description = "Prevents you from taking fall damage.", category = ModuleCategory.PLAYER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/player/NoFall.class */
public final class NoFall extends Module {
    private int oldaacState;
    private boolean aac4Fakelag;
    private int aac4FlagCount;
    private boolean aac5doFlag;
    private boolean aac5Check;
    private int aac5Timer;
    @Nullable
    private VecRotation currentMlgRotation;
    private int currentMlgItemIndex;
    @Nullable
    private BlockPos currentMlgBlock;
    private boolean matrixFalling;
    private boolean matrixCanSpoof;
    private int matrixFallTicks;
    private double matrixLastMotionY;
    private int matrixFlagWait;
    private boolean matrixSend;
    private boolean isDmgFalling;
    private boolean jumped;
    private boolean modifiedTimer;
    private boolean packetModify;
    private boolean needSpoof;
    private boolean doSpoof;
    private boolean nextSpoof;
    private boolean vulcanNoFall;
    private int lastFallDistRounded;
    @NotNull
    private final ListValue typeValue = new ListValue("Type", new String[]{"Edit", "Packet", "MLG", "AAC", "Spartan", "CubeCraft", "Hypixel", "Phase", "Verus", "Medusa", "Motion", "Matrix", "Vulcan"}, "Edit");
    @NotNull
    private final ListValue editMode = new ListValue("Edit-Mode", new String[]{"Always", "Default", "Smart", "NoGround", "Damage"}, "Always", new NoFall$editMode$1(this));
    @NotNull
    private final ListValue packetMode = new ListValue("Packet-Mode", new String[]{"Default", "Smart"}, "Default", new NoFall$packetMode$1(this));
    @NotNull
    private final ListValue aacMode = new ListValue("AAC-Mode", new String[]{"Default", "LAAC", "3.3.11", "3.3.15", "4.x", "4.4.x", "Loyisa4.4.2", "5.0.4", "5.0.14"}, "Default", new NoFall$aacMode$1(this));
    @NotNull
    private final ListValue hypixelMode = new ListValue("Hypixel-Mode", new String[]{"Default", "Packet", "New"}, "Default", new NoFall$hypixelMode$1(this));
    @NotNull
    private final ListValue matrixMode = new ListValue("Matrix-Mode", new String[]{"Old", "6.2.x", "6.6.3"}, "Old", new NoFall$matrixMode$1(this));
    @NotNull
    private final IntegerValue phaseOffsetValue = new IntegerValue("PhaseOffset", 1, 0, 5, new NoFall$phaseOffsetValue$1(this));
    @NotNull
    private final FloatValue minFallDistanceValue = new FloatValue("MinMLGHeight", 5.0f, 2.0f, 50.0f, "m", new NoFall$minFallDistanceValue$1(this));
    @NotNull
    private final FloatValue flySpeedValue = new FloatValue("MotionSpeed", -0.01f, -5.0f, 5.0f, new NoFall$flySpeedValue$1(this));
    @NotNull
    private final BoolValue voidCheckValue = new BoolValue("Void-Check", true);
    @NotNull
    private final MSTimer aac4FlagCooldown = new MSTimer();
    @NotNull
    private final TickTimer spartanTimer = new TickTimer();
    @NotNull
    private final TickTimer mlgTimer = new TickTimer();
    @NotNull
    private final List<C03PacketPlayer> aac4Packets = new ArrayList();
    private boolean vulcantNoFall = true;

    @NotNull
    public final ListValue getTypeValue() {
        return this.typeValue;
    }

    @NotNull
    public final ListValue getEditMode() {
        return this.editMode;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        this.aac4FlagCount = 0;
        this.aac4Fakelag = false;
        this.aac5Check = false;
        this.packetModify = false;
        this.aac4Packets.clear();
        this.needSpoof = false;
        this.aac5doFlag = false;
        this.aac5Timer = 0;
        this.lastFallDistRounded = 0;
        this.oldaacState = 0;
        this.matrixFalling = false;
        this.matrixCanSpoof = false;
        this.matrixFallTicks = 0;
        this.matrixLastMotionY = 0.0d;
        this.isDmgFalling = false;
        this.matrixFlagWait = 0;
        this.aac4FlagCooldown.reset();
        this.nextSpoof = false;
        this.doSpoof = false;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        this.matrixSend = false;
        this.aac4FlagCount = 0;
        this.aac4Fakelag = false;
        this.aac5Check = false;
        this.packetModify = false;
        this.aac4Packets.clear();
        this.needSpoof = false;
        this.aac5doFlag = false;
        this.aac5Timer = 0;
        this.lastFallDistRounded = 0;
        this.oldaacState = 0;
        this.matrixFalling = false;
        this.matrixCanSpoof = false;
        this.matrixFallTicks = 0;
        this.matrixLastMotionY = 0.0d;
        this.isDmgFalling = false;
        this.matrixFlagWait = 0;
        this.aac4FlagCooldown.reset();
        MinecraftInstance.f362mc.field_71428_T.field_74278_d = 1.0f;
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.vulcantNoFall = true;
        this.vulcanNoFall = false;
    }

    @EventTarget(ignoreCondition = true)
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.modifiedTimer) {
            MinecraftInstance.f362mc.field_71428_T.field_74278_d = 1.0f;
            this.modifiedTimer = false;
        }
        if (MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
            this.jumped = false;
        }
        if (MinecraftInstance.f362mc.field_71439_g.field_70181_x > 0.0d) {
            this.jumped = true;
        }
        if (getState()) {
            Module module = LiquidBounce.INSTANCE.getModuleManager().get(FreeCam.class);
            Intrinsics.checkNotNull(module);
            if (module.getState() || MinecraftInstance.f362mc.field_71439_g.func_175149_v() || MinecraftInstance.f362mc.field_71439_g.field_71075_bZ.field_75101_c || MinecraftInstance.f362mc.field_71439_g.field_71075_bZ.field_75102_a) {
                return;
            }
            Module module2 = LiquidBounce.INSTANCE.getModuleManager().get(Fly.class);
            Intrinsics.checkNotNull(module2);
            if (!module2.getState() && this.voidCheckValue.get().booleanValue() && !MovementUtils.isBlockUnder()) {
                return;
            }
            AxisAlignedBB func_174813_aQ = MinecraftInstance.f362mc.field_71439_g.func_174813_aQ();
            Intrinsics.checkNotNullExpressionValue(func_174813_aQ, "mc.thePlayer.entityBoundingBox");
            if (BlockUtils.collideBlock(func_174813_aQ, NoFall$onUpdate$1.INSTANCE) || BlockUtils.collideBlock(new AxisAlignedBB(MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().field_72336_d, MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().field_72337_e, MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().field_72334_f, MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().field_72340_a, MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().field_72338_b - 0.01d, MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().field_72339_c), NoFall$onUpdate$2.INSTANCE)) {
                return;
            }
            if (this.matrixFlagWait > 0) {
                int i = this.matrixFlagWait;
                this.matrixFlagWait = i - 1;
                if (i == 0) {
                    MinecraftInstance.f362mc.field_71428_T.field_74278_d = 1.0f;
                }
            }
            String lowerCase = this.typeValue.get().toLowerCase();
            Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
            switch (lowerCase.hashCode()) {
                case -2011701869:
                    if (lowerCase.equals("spartan")) {
                        this.spartanTimer.update();
                        if (MinecraftInstance.f362mc.field_71439_g.field_70143_R > 1.5f && this.spartanTimer.hasTimePassed(10)) {
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.f362mc.field_71439_g.field_70165_t, MinecraftInstance.f362mc.field_71439_g.field_70163_u + 10, MinecraftInstance.f362mc.field_71439_g.field_70161_v, true));
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.f362mc.field_71439_g.field_70165_t, MinecraftInstance.f362mc.field_71439_g.field_70163_u - 10, MinecraftInstance.f362mc.field_71439_g.field_70161_v, true));
                            this.spartanTimer.reset();
                            return;
                        }
                        return;
                    }
                    return;
                case -1081239615:
                    if (lowerCase.equals("matrix")) {
                        String lowerCase2 = this.matrixMode.get().toLowerCase();
                        Intrinsics.checkNotNullExpressionValue(lowerCase2, "this as java.lang.String).toLowerCase()");
                        switch (lowerCase2.hashCode()) {
                            case 110119:
                                if (lowerCase2.equals("old") && MinecraftInstance.f362mc.field_71439_g.field_70143_R > 3.0f) {
                                    this.isDmgFalling = true;
                                    return;
                                }
                                return;
                            case 51290116:
                                if (lowerCase2.equals("6.2.x")) {
                                    if (this.matrixFalling) {
                                        MinecraftInstance.f362mc.field_71439_g.field_70159_w = 0.0d;
                                        MinecraftInstance.f362mc.field_71439_g.field_70179_y = 0.0d;
                                        MinecraftInstance.f362mc.field_71439_g.field_70747_aH = 0.0f;
                                        if (MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
                                            this.matrixFalling = false;
                                        }
                                    }
                                    if (MinecraftInstance.f362mc.field_71439_g.field_70143_R - MinecraftInstance.f362mc.field_71439_g.field_70181_x > 3.0d) {
                                        this.matrixFalling = true;
                                        if (this.matrixFallTicks == 0) {
                                            this.matrixLastMotionY = MinecraftInstance.f362mc.field_71439_g.field_70181_x;
                                        }
                                        MinecraftInstance.f362mc.field_71439_g.field_70181_x = 0.0d;
                                        MinecraftInstance.f362mc.field_71439_g.field_70159_w = 0.0d;
                                        MinecraftInstance.f362mc.field_71439_g.field_70179_y = 0.0d;
                                        MinecraftInstance.f362mc.field_71439_g.field_70747_aH = 0.0f;
                                        MinecraftInstance.f362mc.field_71439_g.field_70143_R = 3.2f;
                                        int i2 = this.matrixFallTicks;
                                        if (8 <= i2 ? i2 < 10 : false) {
                                            this.matrixCanSpoof = true;
                                        }
                                        this.matrixFallTicks++;
                                    }
                                    if (this.matrixFallTicks > 12 && !MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
                                        MinecraftInstance.f362mc.field_71439_g.field_70181_x = this.matrixLastMotionY;
                                        MinecraftInstance.f362mc.field_71439_g.field_70143_R = 0.0f;
                                        this.matrixFallTicks = 0;
                                        this.matrixCanSpoof = false;
                                        return;
                                    }
                                    return;
                                }
                                return;
                            case 51293891:
                                if (lowerCase2.equals("6.6.3") && MinecraftInstance.f362mc.field_71439_g.field_70143_R - MinecraftInstance.f362mc.field_71439_g.field_70181_x > 3.0d) {
                                    MinecraftInstance.f362mc.field_71439_g.field_70143_R = 0.0f;
                                    this.matrixSend = true;
                                    MinecraftInstance.f362mc.field_71428_T.field_74278_d = 0.5f;
                                    this.modifiedTimer = true;
                                    return;
                                }
                                return;
                            default:
                                return;
                        }
                    }
                    return;
                case -1068318794:
                    if (lowerCase.equals("motion") && MinecraftInstance.f362mc.field_71439_g.field_70143_R > 3.0f) {
                        MinecraftInstance.f362mc.field_71439_g.field_70181_x = this.flySpeedValue.get().floatValue();
                        return;
                    }
                    return;
                case -1031473397:
                    if (lowerCase.equals("cubecraft") && MinecraftInstance.f362mc.field_71439_g.field_70143_R > 2.0f) {
                        MinecraftInstance.f362mc.field_71439_g.field_70122_E = true;
                        MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer(true));
                        return;
                    }
                    return;
                case -995865464:
                    if (lowerCase.equals("packet")) {
                        String lowerCase3 = this.packetMode.get().toLowerCase();
                        Intrinsics.checkNotNullExpressionValue(lowerCase3, "this as java.lang.String).toLowerCase()");
                        if (Intrinsics.areEqual(lowerCase3, "default")) {
                            if (MinecraftInstance.f362mc.field_71439_g.field_70143_R > 2.0f) {
                                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer(true));
                                return;
                            }
                            return;
                        } else if (Intrinsics.areEqual(lowerCase3, "smart") && MinecraftInstance.f362mc.field_71439_g.field_70143_R - MinecraftInstance.f362mc.field_71439_g.field_70181_x > 3.0d) {
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer(true));
                            MinecraftInstance.f362mc.field_71439_g.field_70143_R = 0.0f;
                            return;
                        } else {
                            return;
                        }
                    }
                    return;
                case -805359837:
                    if (lowerCase.equals("vulcan")) {
                        if (!this.vulcanNoFall && MinecraftInstance.f362mc.field_71439_g.field_70143_R > 3.25d) {
                            this.vulcanNoFall = true;
                        }
                        if (this.vulcanNoFall && this.vulcantNoFall && MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
                            this.vulcantNoFall = false;
                        }
                        if (this.vulcantNoFall) {
                            return;
                        }
                        if (this.nextSpoof) {
                            MinecraftInstance.f362mc.field_71439_g.field_70181_x = -0.1d;
                            MinecraftInstance.f362mc.field_71439_g.field_70143_R = -0.1f;
                            MovementUtils.strafe(0.3f);
                            this.nextSpoof = false;
                        }
                        if (MinecraftInstance.f362mc.field_71439_g.field_70143_R > 3.5625f) {
                            MinecraftInstance.f362mc.field_71439_g.field_70143_R = 0.0f;
                            this.doSpoof = true;
                            this.nextSpoof = true;
                            return;
                        }
                        return;
                    }
                    return;
                case 96323:
                    if (lowerCase.equals("aac")) {
                        String lowerCase4 = this.aacMode.get().toLowerCase();
                        Intrinsics.checkNotNullExpressionValue(lowerCase4, "this as java.lang.String).toLowerCase()");
                        switch (lowerCase4.hashCode()) {
                            case -672366723:
                                if (!lowerCase4.equals("loyisa4.4.2")) {
                                    return;
                                }
                                break;
                            case 3313751:
                                if (lowerCase4.equals("laac") && !this.jumped && MinecraftInstance.f362mc.field_71439_g.field_70122_E && !MinecraftInstance.f362mc.field_71439_g.func_70617_f_() && !MinecraftInstance.f362mc.field_71439_g.func_70090_H() && !MinecraftInstance.f362mc.field_71439_g.field_70134_J) {
                                    MinecraftInstance.f362mc.field_71439_g.field_70181_x = -6.0d;
                                    return;
                                }
                                return;
                            case 50364605:
                                if (!lowerCase4.equals("5.0.4")) {
                                    return;
                                }
                                break;
                            case 1504133782:
                                if (lowerCase4.equals("3.3.11") && MinecraftInstance.f362mc.field_71439_g.field_70143_R > 2.0f) {
                                    MinecraftInstance.f362mc.field_71439_g.field_70159_w = 0.0d;
                                    MinecraftInstance.f362mc.field_71439_g.field_70179_y = 0.0d;
                                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.f362mc.field_71439_g.field_70165_t, MinecraftInstance.f362mc.field_71439_g.field_70163_u - 0.001d, MinecraftInstance.f362mc.field_71439_g.field_70161_v, MinecraftInstance.f362mc.field_71439_g.field_70122_E));
                                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer(true));
                                    return;
                                }
                                return;
                            case 1504133786:
                                if (lowerCase4.equals("3.3.15") && MinecraftInstance.f362mc.field_71439_g.field_70143_R > 2.0f) {
                                    if (!MinecraftInstance.f362mc.func_71387_A()) {
                                        MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.f362mc.field_71439_g.field_70165_t, Double.NaN, MinecraftInstance.f362mc.field_71439_g.field_70161_v, false));
                                    }
                                    MinecraftInstance.f362mc.field_71439_g.field_70143_R = -9999.0f;
                                    return;
                                }
                                return;
                            case 1544803905:
                                if (lowerCase4.equals("default")) {
                                    if (MinecraftInstance.f362mc.field_71439_g.field_70143_R > 2.0f) {
                                        MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer(true));
                                        this.oldaacState = 2;
                                    } else if (this.oldaacState == 2 && MinecraftInstance.f362mc.field_71439_g.field_70143_R < 2.0f) {
                                        MinecraftInstance.f362mc.field_71439_g.field_70181_x = 0.1d;
                                        this.oldaacState = 3;
                                        return;
                                    }
                                    int i3 = this.oldaacState;
                                    if (3 <= i3 ? i3 < 6 : false) {
                                        MinecraftInstance.f362mc.field_71439_g.field_70181_x = 0.1d;
                                        if (this.oldaacState == 5) {
                                            this.oldaacState = 1;
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                }
                                return;
                            case 1561302714:
                                if (lowerCase4.equals("5.0.14")) {
                                    double offsetYs = 0.0d;
                                    this.aac5Check = false;
                                    while (MinecraftInstance.f362mc.field_71439_g.field_70181_x - 1.5d < offsetYs) {
                                        BlockPos blockPos = new BlockPos(MinecraftInstance.f362mc.field_71439_g.field_70165_t, MinecraftInstance.f362mc.field_71439_g.field_70163_u + offsetYs, MinecraftInstance.f362mc.field_71439_g.field_70161_v);
                                        Block block = BlockUtils.getBlock(blockPos);
                                        Intrinsics.checkNotNull(block);
                                        AxisAlignedBB axisAlignedBB = block.func_180640_a(MinecraftInstance.f362mc.field_71441_e, blockPos, BlockUtils.getState(blockPos));
                                        if (axisAlignedBB != null) {
                                            offsetYs = -999.9d;
                                            this.aac5Check = true;
                                        }
                                        offsetYs -= 0.5d;
                                    }
                                    if (MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
                                        MinecraftInstance.f362mc.field_71439_g.field_70143_R = -2.0f;
                                        this.aac5Check = false;
                                    }
                                    if (this.aac5Timer > 0) {
                                        this.aac5Timer--;
                                    }
                                    if (this.aac5Check && MinecraftInstance.f362mc.field_71439_g.field_70143_R > 2.5d && !MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
                                        this.aac5doFlag = true;
                                        this.aac5Timer = 18;
                                    } else if (this.aac5Timer < 2) {
                                        this.aac5doFlag = false;
                                    }
                                    if (this.aac5doFlag) {
                                        MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.f362mc.field_71439_g.field_70165_t, MinecraftInstance.f362mc.field_71439_g.field_70163_u + (MinecraftInstance.f362mc.field_71439_g.field_70122_E ? 0.5d : 0.42d), MinecraftInstance.f362mc.field_71439_g.field_70161_v, true));
                                        return;
                                    }
                                    return;
                                }
                                return;
                            default:
                                return;
                        }
                        if (MinecraftInstance.f362mc.field_71439_g.field_70143_R > 3.0f) {
                            this.isDmgFalling = true;
                        }
                        if (!StringsKt.equals(this.aacMode.get(), "loyisa4.4.2", true) || this.aac4FlagCount >= 3 || this.aac4FlagCooldown.hasTimePassed(1500L) || this.aac4FlagCooldown.hasTimePassed(1500L)) {
                            return;
                        }
                        if (MinecraftInstance.f362mc.field_71439_g.field_70122_E || MinecraftInstance.f362mc.field_71439_g.field_70143_R < 0.5d) {
                            MinecraftInstance.f362mc.field_71439_g.field_70159_w = 0.0d;
                            MinecraftInstance.f362mc.field_71439_g.field_70179_y = 0.0d;
                            MinecraftInstance.f362mc.field_71439_g.field_70122_E = false;
                            MinecraftInstance.f362mc.field_71439_g.field_70747_aH = 0.0f;
                            return;
                        }
                        return;
                    }
                    return;
                case 3108362:
                    if (lowerCase.equals("edit") && StringsKt.equals(this.editMode.get(), "smart", true)) {
                        if (((int) MinecraftInstance.f362mc.field_71439_g.field_70143_R) / 3 > this.lastFallDistRounded) {
                            this.lastFallDistRounded = ((int) MinecraftInstance.f362mc.field_71439_g.field_70143_R) / 3;
                            this.packetModify = true;
                        }
                        if (MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
                            this.lastFallDistRounded = 0;
                            return;
                        }
                        return;
                    }
                    return;
                case 106629499:
                    if (lowerCase.equals("phase") && MinecraftInstance.f362mc.field_71439_g.field_70143_R > 3 + this.phaseOffsetValue.get().intValue()) {
                        EntityPlayer entityPlayer = MinecraftInstance.f362mc.field_71439_g;
                        Intrinsics.checkNotNullExpressionValue(entityPlayer, "mc.thePlayer");
                        final BlockPos fallPos = new NewFallingPlayer(entityPlayer).findCollision(5);
                        if (fallPos != null && fallPos.func_177956_o() - (MinecraftInstance.f362mc.field_71439_g.field_70181_x / 20.0d) < MinecraftInstance.f362mc.field_71439_g.field_70163_u) {
                            MinecraftInstance.f362mc.field_71428_T.field_74278_d = 0.05f;
                            new Timer().schedule(new TimerTask() { // from class: net.ccbluex.liquidbounce.features.module.modules.player.NoFall$onUpdate$$inlined$schedule$1
                                @Override // java.util.TimerTask, java.lang.Runnable
                                public void run() {
                                    Minecraft minecraft;
                                    Minecraft minecraft2;
                                    Minecraft minecraft3;
                                    try {
                                        minecraft2 = MinecraftInstance.f362mc;
                                        minecraft2.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(fallPos.func_177958_n(), fallPos.func_177956_o(), fallPos.func_177952_p(), true));
                                        minecraft3 = MinecraftInstance.f362mc;
                                        minecraft3.field_71439_g.func_70107_b(fallPos.func_177958_n(), fallPos.func_177956_o(), fallPos.func_177952_p());
                                    } catch (Exception e) {
                                    }
                                    minecraft = MinecraftInstance.f362mc;
                                    minecraft.field_71428_T.field_74278_d = 1.0f;
                                }
                            }, 100L);
                            return;
                        }
                        return;
                    }
                    return;
                case 112097665:
                    if (lowerCase.equals("verus") && MinecraftInstance.f362mc.field_71439_g.field_70143_R - MinecraftInstance.f362mc.field_71439_g.field_70181_x > 3.0d) {
                        MinecraftInstance.f362mc.field_71439_g.field_70181_x = 0.0d;
                        MinecraftInstance.f362mc.field_71439_g.field_70159_w *= 0.5d;
                        MinecraftInstance.f362mc.field_71439_g.field_70159_w *= 0.5d;
                        MinecraftInstance.f362mc.field_71439_g.field_70143_R = 0.0f;
                        this.needSpoof = true;
                        return;
                    }
                    return;
                case 1381910549:
                    if (lowerCase.equals("hypixel") && StringsKt.equals(this.hypixelMode.get(), "packet", true)) {
                        if (!MinecraftInstance.f362mc.field_71439_g.field_70122_E && MinecraftInstance.f362mc.field_71439_g.field_70143_R - (this.matrixFallTicks * 2.5d) >= 0.0d) {
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer(true));
                            this.matrixFallTicks++;
                            return;
                        } else if (MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
                            this.matrixFallTicks = 1;
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
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(Fly.class);
        Intrinsics.checkNotNull(module);
        if (module.getState() || !this.voidCheckValue.get().booleanValue() || MovementUtils.isBlockUnder()) {
            if (StringsKt.equals(this.typeValue.get(), "aac", true) && StringsKt.equals(this.aacMode.get(), "4.x", true) && event.getEventState() == EventState.PRE) {
                if (!inVoid()) {
                    if (this.aac4Fakelag) {
                        this.aac4Fakelag = false;
                        if (this.aac4Packets.size() > 0) {
                            Iterator<C03PacketPlayer> it = this.aac4Packets.iterator();
                            while (it.hasNext()) {
                                MinecraftInstance.f362mc.field_71439_g.field_71174_a.func_147297_a((C03PacketPlayer) it.next());
                            }
                            this.aac4Packets.clear();
                        }
                    }
                } else if (MinecraftInstance.f362mc.field_71439_g.field_70122_E && this.aac4Fakelag) {
                    this.aac4Fakelag = false;
                    if (this.aac4Packets.size() > 0) {
                        Iterator<C03PacketPlayer> it2 = this.aac4Packets.iterator();
                        while (it2.hasNext()) {
                            MinecraftInstance.f362mc.field_71439_g.field_71174_a.func_147297_a((C03PacketPlayer) it2.next());
                        }
                        this.aac4Packets.clear();
                    }
                } else {
                    if (MinecraftInstance.f362mc.field_71439_g.field_70143_R > 2.5d && this.aac4Fakelag) {
                        this.packetModify = true;
                        MinecraftInstance.f362mc.field_71439_g.field_70143_R = 0.0f;
                    }
                    if (!inAir(4.0d, 1.0d) && !this.aac4Fakelag) {
                        this.aac4Fakelag = true;
                    }
                }
            } else if (StringsKt.equals(this.typeValue.get(), "mlg", true)) {
                if (event.getEventState() == EventState.PRE) {
                    this.currentMlgRotation = null;
                    this.mlgTimer.update();
                    if (this.mlgTimer.hasTimePassed(10) && MinecraftInstance.f362mc.field_71439_g.field_70143_R > this.minFallDistanceValue.get().floatValue()) {
                        EntityPlayer entityPlayer = MinecraftInstance.f362mc.field_71439_g;
                        Intrinsics.checkNotNullExpressionValue(entityPlayer, "mc.thePlayer");
                        NewFallingPlayer NewFallingPlayer = new NewFallingPlayer(entityPlayer);
                        double maxDist = MinecraftInstance.f362mc.field_71442_b.func_78757_d() + 1.5d;
                        Vec3i findCollision = NewFallingPlayer.findCollision((int) Math.ceil((1.0d / MinecraftInstance.f362mc.field_71439_g.field_70181_x) * (-maxDist)));
                        if (findCollision == null) {
                            return;
                        }
                        boolean ok = new Vec3(MinecraftInstance.f362mc.field_71439_g.field_70165_t, MinecraftInstance.f362mc.field_71439_g.field_70163_u + ((double) MinecraftInstance.f362mc.field_71439_g.eyeHeight), MinecraftInstance.f362mc.field_71439_g.field_70161_v).func_72438_d(new Vec3(findCollision).func_72441_c(0.5d, 0.5d, 0.5d)) < ((double) MinecraftInstance.f362mc.field_71442_b.func_78757_d()) + Math.sqrt(0.75d);
                        if (MinecraftInstance.f362mc.field_71439_g.field_70181_x < (findCollision.func_177956_o() + 1) - MinecraftInstance.f362mc.field_71439_g.field_70163_u) {
                            ok = true;
                        }
                        if (!ok) {
                            return;
                        }
                        int index = -1;
                        int i = 36;
                        while (i < 45) {
                            int i2 = i;
                            i++;
                            ItemStack itemStack = MinecraftInstance.f362mc.field_71439_g.field_71069_bz.func_75139_a(i2).func_75211_c();
                            if (itemStack != null) {
                                if (!Intrinsics.areEqual(itemStack.func_77973_b(), Items.field_151131_as)) {
                                    if (!(itemStack.func_77973_b() instanceof ItemBlock)) {
                                        continue;
                                    } else {
                                        ItemBlock func_77973_b = itemStack.func_77973_b();
                                        if (func_77973_b == null) {
                                            throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemBlock");
                                        }
                                        if (!Intrinsics.areEqual(func_77973_b.field_150939_a, Blocks.field_150321_G)) {
                                            continue;
                                        }
                                    }
                                }
                                index = i2 - 36;
                                if (MinecraftInstance.f362mc.field_71439_g.field_71071_by.field_70461_c == index) {
                                    break;
                                }
                            }
                        }
                        if (index == -1) {
                            return;
                        }
                        this.currentMlgItemIndex = index;
                        this.currentMlgBlock = findCollision;
                        if (MinecraftInstance.f362mc.field_71439_g.field_71071_by.field_70461_c != index) {
                            MinecraftInstance.f362mc.field_71439_g.field_71174_a.func_147297_a(new C09PacketHeldItemChange(index));
                        }
                        this.currentMlgRotation = RotationUtils.faceBlock(findCollision);
                        VecRotation vecRotation = this.currentMlgRotation;
                        Intrinsics.checkNotNull(vecRotation);
                        Rotation rotation = vecRotation.getRotation();
                        EntityPlayerSP entityPlayerSP = MinecraftInstance.f362mc.field_71439_g;
                        Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
                        rotation.toPlayer((EntityPlayer) entityPlayerSP);
                    }
                } else if (this.currentMlgRotation != null) {
                    ItemStack stack = MinecraftInstance.f362mc.field_71439_g.field_71071_by.field_70462_a[this.currentMlgItemIndex];
                    if (stack.func_77973_b() instanceof ItemBucket) {
                        MinecraftInstance.f362mc.field_71442_b.func_78769_a(MinecraftInstance.f362mc.field_71439_g, MinecraftInstance.f362mc.field_71441_e, stack);
                    } else {
                        PlayerControllerMP playerControllerMP = MinecraftInstance.f362mc.field_71442_b;
                        EntityPlayerSP entityPlayerSP2 = MinecraftInstance.f362mc.field_71439_g;
                        WorldClient worldClient = MinecraftInstance.f362mc.field_71441_e;
                        BlockPos blockPos = this.currentMlgBlock;
                        EnumFacing enumFacing = EnumFacing.UP;
                        Vec3 vec3 = new Vec3(0.0d, 0.5d, 0.0d);
                        Vec3i vec3i = this.currentMlgBlock;
                        if (vec3i == null) {
                            return;
                        }
                        if (playerControllerMP.func_178890_a(entityPlayerSP2, worldClient, stack, blockPos, enumFacing, vec3.func_178787_e(new Vec3(vec3i)))) {
                            this.mlgTimer.reset();
                        }
                    }
                    if (MinecraftInstance.f362mc.field_71439_g.field_71071_by.field_70461_c != this.currentMlgItemIndex) {
                        MinecraftInstance.f362mc.field_71439_g.field_71174_a.func_147297_a(new C09PacketHeldItemChange(MinecraftInstance.f362mc.field_71439_g.field_71071_by.field_70461_c));
                    }
                }
            }
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.f362mc.field_71439_g == null) {
            return;
        }
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(Fly.class);
        Intrinsics.checkNotNull(module);
        if (!module.getState() && this.voidCheckValue.get().booleanValue() && !MovementUtils.isBlockUnder()) {
            return;
        }
        S12PacketEntityVelocity packet = event.getPacket();
        if ((packet instanceof S12PacketEntityVelocity) && StringsKt.equals(this.typeValue.get(), "aac", true) && StringsKt.equals(this.aacMode.get(), "4.4.x", true) && MinecraftInstance.f362mc.field_71439_g.field_70143_R > 1.8d) {
            packet.field_149416_c = (int) (packet.field_149416_c * (-0.1d));
        }
        if (packet instanceof S08PacketPlayerPosLook) {
            if (StringsKt.equals(this.typeValue.get(), "aac", true) && StringsKt.equals(this.aacMode.get(), "loyisa4.4.2", true)) {
                this.aac4FlagCount++;
                if (this.matrixFlagWait > 0) {
                    this.aac4FlagCooldown.reset();
                    this.aac4FlagCount = 1;
                    event.cancelEvent();
                }
            }
            if (StringsKt.equals(this.typeValue.get(), "matrix", true) && StringsKt.equals(this.matrixMode.get(), "old", true) && this.matrixFlagWait > 0) {
                this.matrixFlagWait = 0;
                MinecraftInstance.f362mc.field_71428_T.field_74278_d = 1.0f;
                event.cancelEvent();
            }
        }
        if (packet instanceof C03PacketPlayer) {
            if (this.matrixSend) {
                this.matrixSend = false;
                event.cancelEvent();
                PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(((C03PacketPlayer) packet).field_149479_a, ((C03PacketPlayer) packet).field_149477_b, ((C03PacketPlayer) packet).field_149478_c, true));
                PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(((C03PacketPlayer) packet).field_149479_a, ((C03PacketPlayer) packet).field_149477_b, ((C03PacketPlayer) packet).field_149478_c, false));
            }
            if (this.doSpoof) {
                ((C03PacketPlayer) packet).field_149474_g = true;
                this.doSpoof = false;
                ((C03PacketPlayer) packet).field_149477_b = Math.round(MinecraftInstance.f362mc.field_71439_g.field_70163_u * 2) / 2;
                MinecraftInstance.f362mc.field_71439_g.func_70107_b(MinecraftInstance.f362mc.field_71439_g.field_70165_t, ((C03PacketPlayer) packet).field_149477_b, MinecraftInstance.f362mc.field_71439_g.field_70161_v);
            }
            if (StringsKt.equals(this.typeValue.get(), "edit", true)) {
                String edits = this.editMode.get();
                if (StringsKt.equals(edits, "always", true) || ((StringsKt.equals(edits, "default", true) && MinecraftInstance.f362mc.field_71439_g.field_70143_R > 2.5f) || ((StringsKt.equals(edits, "damage", true) && MinecraftInstance.f362mc.field_71439_g.field_70143_R > 3.5f) || (StringsKt.equals(edits, "smart", true) && this.packetModify)))) {
                    ((C03PacketPlayer) packet).field_149474_g = true;
                    this.packetModify = false;
                }
                if (StringsKt.equals(edits, "noground", true)) {
                    ((C03PacketPlayer) packet).field_149474_g = false;
                }
            }
            if (StringsKt.equals(this.typeValue.get(), "medusa", true) && MinecraftInstance.f362mc.field_71439_g.field_70143_R > 2.3f) {
                event.cancelEvent();
                PacketUtils.sendPacketNoEvent(new C03PacketPlayer(true));
                MinecraftInstance.f362mc.field_71439_g.field_70143_R = 0.0f;
            }
            if (StringsKt.equals(this.typeValue.get(), "hypixel", true)) {
                String lowerCase = this.hypixelMode.get().toLowerCase();
                Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
                if (Intrinsics.areEqual(lowerCase, "default")) {
                    if (MinecraftInstance.f362mc.field_71439_g.field_70143_R > 1.5d) {
                        ((C03PacketPlayer) packet).field_149474_g = MinecraftInstance.f362mc.field_71439_g.field_70173_aa % 2 == 0;
                    }
                } else if (Intrinsics.areEqual(lowerCase, "new") && MinecraftInstance.f362mc.field_71439_g.field_70143_R > 2.5f && MinecraftInstance.f362mc.field_71439_g.field_70173_aa % 2 == 0) {
                    ((C03PacketPlayer) packet).field_149474_g = true;
                    ((C03PacketPlayer) packet).func_149469_a(false);
                }
            }
            if (StringsKt.equals(this.typeValue.get(), "verus", true) && this.needSpoof) {
                ((C03PacketPlayer) packet).field_149474_g = true;
                this.needSpoof = false;
            }
            if (StringsKt.equals(this.typeValue.get(), "aac", true)) {
                String lowerCase2 = this.aacMode.get().toLowerCase();
                Intrinsics.checkNotNullExpressionValue(lowerCase2, "this as java.lang.String).toLowerCase()");
                switch (lowerCase2.hashCode()) {
                    case 51518:
                        if (lowerCase2.equals("4.x") && this.aac4Fakelag) {
                            event.cancelEvent();
                            if (this.packetModify) {
                                ((C03PacketPlayer) packet).field_149474_g = true;
                                this.packetModify = false;
                            }
                            this.aac4Packets.add(packet);
                            break;
                        }
                        break;
                    case 49444996:
                        if (lowerCase2.equals("4.4.x") && MinecraftInstance.f362mc.field_71439_g.field_70143_R > 1.6d) {
                            ((C03PacketPlayer) packet).field_149474_g = true;
                            break;
                        }
                        break;
                    case 50364605:
                        if (lowerCase2.equals("5.0.4") && this.isDmgFalling && ((C03PacketPlayer) packet).field_149474_g && MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
                            this.isDmgFalling = false;
                            ((C03PacketPlayer) packet).field_149474_g = true;
                            MinecraftInstance.f362mc.field_71439_g.field_70122_E = false;
                            ((C03PacketPlayer) packet).field_149477_b += 1.0d;
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(((C03PacketPlayer) packet).field_149479_a, ((C03PacketPlayer) packet).field_149477_b - 1.0784d, ((C03PacketPlayer) packet).field_149478_c, false));
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(((C03PacketPlayer) packet).field_149479_a, ((C03PacketPlayer) packet).field_149477_b - 0.5d, ((C03PacketPlayer) packet).field_149478_c, true));
                            break;
                        }
                        break;
                }
            }
            if (StringsKt.equals(this.typeValue.get(), "matrix", true) && StringsKt.equals(this.matrixMode.get(), "6.2.x", true) && this.matrixCanSpoof) {
                ((C03PacketPlayer) packet).field_149474_g = true;
                this.matrixCanSpoof = false;
            }
            if (this.isDmgFalling) {
                if (((StringsKt.equals(this.typeValue.get(), "matrix", true) && StringsKt.equals(this.matrixMode.get(), "old", true)) || (StringsKt.equals(this.typeValue.get(), "aac", true) && StringsKt.equals(this.aacMode.get(), "loyisa4.4.2", true))) && ((C03PacketPlayer) packet).field_149474_g && MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
                    this.matrixFlagWait = 2;
                    this.isDmgFalling = false;
                    event.cancelEvent();
                    MinecraftInstance.f362mc.field_71439_g.field_70122_E = false;
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(((C03PacketPlayer) packet).field_149479_a, ((C03PacketPlayer) packet).field_149477_b - 256, ((C03PacketPlayer) packet).field_149478_c, false));
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(((C03PacketPlayer) packet).field_149479_a, -10.0d, ((C03PacketPlayer) packet).field_149478_c, true));
                    MinecraftInstance.f362mc.field_71428_T.field_74278_d = 0.18f;
                    this.modifiedTimer = true;
                }
            }
        }
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(Fly.class);
        Intrinsics.checkNotNull(module);
        if (module.getState() || !this.voidCheckValue.get().booleanValue() || MovementUtils.isBlockUnder()) {
            AxisAlignedBB func_174813_aQ = MinecraftInstance.f362mc.field_71439_g.func_174813_aQ();
            Intrinsics.checkNotNullExpressionValue(func_174813_aQ, "mc.thePlayer.entityBoundingBox");
            if (!BlockUtils.collideBlock(func_174813_aQ, NoFall$onMove$1.INSTANCE) && !BlockUtils.collideBlock(new AxisAlignedBB(MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().field_72336_d, MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().field_72337_e, MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().field_72334_f, MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().field_72340_a, MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().field_72338_b - 0.01d, MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().field_72339_c), NoFall$onMove$2.INSTANCE) && StringsKt.equals(this.typeValue.get(), "aac", true) && StringsKt.equals(this.aacMode.get(), "laac", true) && !this.jumped && !MinecraftInstance.f362mc.field_71439_g.field_70122_E && !MinecraftInstance.f362mc.field_71439_g.func_70617_f_() && !MinecraftInstance.f362mc.field_71439_g.func_70090_H() && !MinecraftInstance.f362mc.field_71439_g.field_70134_J && MinecraftInstance.f362mc.field_71439_g.field_70181_x < 0.0d) {
                event.setX(0.0d);
                event.setZ(0.0d);
            }
        }
    }

    @EventTarget(ignoreCondition = true)
    public final void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.jumped = true;
    }

    private final boolean inVoid() {
        if (MinecraftInstance.f362mc.field_71439_g.field_70163_u < 0.0d) {
            return false;
        }
        for (int off = 0; off < MinecraftInstance.f362mc.field_71439_g.field_70163_u + 2; off += 2) {
            AxisAlignedBB bb = new AxisAlignedBB(MinecraftInstance.f362mc.field_71439_g.field_70165_t, MinecraftInstance.f362mc.field_71439_g.field_70163_u, MinecraftInstance.f362mc.field_71439_g.field_70161_v, MinecraftInstance.f362mc.field_71439_g.field_70165_t, off, MinecraftInstance.f362mc.field_71439_g.field_70161_v);
            if (!MinecraftInstance.f362mc.field_71441_e.func_72945_a(MinecraftInstance.f362mc.field_71439_g, bb).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private final boolean inAir(double height, double plus) {
        if (MinecraftInstance.f362mc.field_71439_g.field_70163_u < 0.0d) {
            return false;
        }
        int i = 0;
        while (true) {
            int off = i;
            if (off < height) {
                AxisAlignedBB bb = new AxisAlignedBB(MinecraftInstance.f362mc.field_71439_g.field_70165_t, MinecraftInstance.f362mc.field_71439_g.field_70163_u, MinecraftInstance.f362mc.field_71439_g.field_70161_v, MinecraftInstance.f362mc.field_71439_g.field_70165_t, MinecraftInstance.f362mc.field_71439_g.field_70163_u - off, MinecraftInstance.f362mc.field_71439_g.field_70161_v);
                if (!MinecraftInstance.f362mc.field_71441_e.func_72945_a(MinecraftInstance.f362mc.field_71439_g, bb).isEmpty()) {
                    return true;
                }
                i = off + ((int) plus);
            } else {
                return false;
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    @NotNull
    public String getTag() {
        return this.typeValue.get();
    }
}
