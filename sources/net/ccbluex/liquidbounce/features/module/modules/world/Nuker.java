package net.ccbluex.liquidbounce.features.module.modules.world;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.player.AutoTool;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.TickTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Nuker.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000b\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018�� #2\u00020\u0001:\u0001#B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0007J\u0010\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001eH\u0007J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0002R\u001e\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n��R\u0010\u0010\t\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000e\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u000f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0012\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0017\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0018\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��¨\u0006$"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/Nuker;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "attackedBlocks", "Ljava/util/ArrayList;", "Lnet/minecraft/util/BlockPos;", "Lkotlin/collections/ArrayList;", "blockHitDelay", "", "currentBlock", "hitDelayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "layerValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "nuke", "nukeDelay", "nukeTimer", "Lnet/ccbluex/liquidbounce/utils/timer/TickTimer;", "nukeValue", "priorityValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "radiusValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "rotationsValue", "throughWallsValue", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "validBlock", "", "block", "Lnet/minecraft/block/Block;", "Companion", "LiquidBounce"})
@ModuleInfo(name = "Nuker", description = "Breaks all blocks around you.", category = ModuleCategory.WORLD)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/world/Nuker.class */
public final class Nuker extends Module {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @Nullable
    private BlockPos currentBlock;
    private int blockHitDelay;
    private int nuke;
    private static float currentDamage;
    @NotNull
    private final FloatValue radiusValue = new FloatValue("Radius", 5.2f, 1.0f, 6.0f);
    @NotNull
    private final BoolValue throughWallsValue = new BoolValue("ThroughWalls", false);
    @NotNull
    private final ListValue priorityValue = new ListValue("Priority", new String[]{"Distance", "Hardness"}, "Distance");
    @NotNull
    private final BoolValue rotationsValue = new BoolValue("Rotations", true);
    @NotNull
    private final BoolValue layerValue = new BoolValue("Layer", false);
    @NotNull
    private final IntegerValue hitDelayValue = new IntegerValue("HitDelay", 4, 0, 20);
    @NotNull
    private final IntegerValue nukeValue = new IntegerValue("Nuke", 1, 1, 20);
    @NotNull
    private final IntegerValue nukeDelay = new IntegerValue("NukeDelay", 1, 1, 20);
    @NotNull
    private final ArrayList<BlockPos> attackedBlocks = new ArrayList<>();
    @NotNull
    private TickTimer nukeTimer = new TickTimer();

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        boolean z;
        Map.Entry entry;
        Object obj;
        Object obj2;
        boolean z2;
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.blockHitDelay > 0) {
            Module module = LiquidBounce.INSTANCE.getModuleManager().get(FastBreak.class);
            Intrinsics.checkNotNull(module);
            if (!module.getState()) {
                this.blockHitDelay--;
                return;
            }
        }
        this.nukeTimer.update();
        if (this.nukeTimer.hasTimePassed(this.nukeDelay.get().intValue())) {
            this.nuke = 0;
            this.nukeTimer.reset();
        }
        this.attackedBlocks.clear();
        EntityPlayer entityPlayer = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNull(entityPlayer);
        if (MinecraftInstance.f362mc.field_71442_b.func_78758_h()) {
            ItemStack func_70694_bm = entityPlayer.func_70694_bm();
            if ((func_70694_bm == null ? null : func_70694_bm.func_77973_b()) instanceof ItemSword) {
                return;
            }
            Map $this$filter$iv = BlockUtils.searchBlocks(MathKt.roundToInt(this.radiusValue.get().floatValue()) + 1);
            Map destination$iv$iv = new LinkedHashMap();
            for (Map.Entry element$iv$iv : $this$filter$iv.entrySet()) {
                BlockPos pos = element$iv$iv.getKey();
                Block block = element$iv$iv.getValue();
                if (BlockUtils.getCenterDistance(pos) > this.radiusValue.get().floatValue() || !validBlock(block)) {
                    z = false;
                } else if (this.layerValue.get().booleanValue() && pos.func_177956_o() < ((EntityPlayerSP) entityPlayer).field_70163_u) {
                    z = false;
                } else if (!this.throughWallsValue.get().booleanValue()) {
                    Vec3 eyesPos = new Vec3(((EntityPlayerSP) entityPlayer).field_70165_t, entityPlayer.func_174813_aQ().field_72338_b + ((EntityPlayerSP) entityPlayer).eyeHeight, ((EntityPlayerSP) entityPlayer).field_70161_v);
                    Vec3 blockVec = new Vec3(pos.func_177958_n() + 0.5d, pos.func_177956_o() + 0.5d, pos.func_177952_p() + 0.5d);
                    WorldClient worldClient = MinecraftInstance.f362mc.field_71441_e;
                    Intrinsics.checkNotNull(worldClient);
                    MovingObjectPosition rayTrace = worldClient.func_147447_a(eyesPos, blockVec, false, true, false);
                    z = rayTrace != null && Intrinsics.areEqual(rayTrace.func_178782_a(), pos);
                } else {
                    z = true;
                }
                if (z) {
                    destination$iv$iv.put(element$iv$iv.getKey(), element$iv$iv.getValue());
                }
            }
            for (Map.Entry element$iv : destination$iv$iv.entrySet()) {
                BlockPos pos2 = (BlockPos) element$iv.getKey();
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos2, EnumFacing.DOWN));
                entityPlayer.func_71038_i();
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos2, EnumFacing.DOWN));
                this.attackedBlocks.add(pos2);
            }
            return;
        }
        Map $this$filter$iv2 = BlockUtils.searchBlocks(MathKt.roundToInt(this.radiusValue.get().floatValue()) + 1);
        Map destination$iv$iv2 = new LinkedHashMap();
        for (Map.Entry element$iv$iv2 : $this$filter$iv2.entrySet()) {
            BlockPos pos3 = element$iv$iv2.getKey();
            Block block2 = element$iv$iv2.getValue();
            if (BlockUtils.getCenterDistance(pos3) > this.radiusValue.get().floatValue() || !validBlock(block2)) {
                z2 = false;
            } else if (this.layerValue.get().booleanValue() && pos3.func_177956_o() < ((EntityPlayerSP) entityPlayer).field_70163_u) {
                z2 = false;
            } else if (!this.throughWallsValue.get().booleanValue()) {
                Vec3 eyesPos2 = new Vec3(((EntityPlayerSP) entityPlayer).field_70165_t, entityPlayer.func_174813_aQ().field_72338_b + ((EntityPlayerSP) entityPlayer).eyeHeight, ((EntityPlayerSP) entityPlayer).field_70161_v);
                Vec3 blockVec2 = new Vec3(pos3.func_177958_n() + 0.5d, pos3.func_177956_o() + 0.5d, pos3.func_177952_p() + 0.5d);
                WorldClient worldClient2 = MinecraftInstance.f362mc.field_71441_e;
                Intrinsics.checkNotNull(worldClient2);
                MovingObjectPosition rayTrace2 = worldClient2.func_147447_a(eyesPos2, blockVec2, false, true, false);
                z2 = rayTrace2 != null && Intrinsics.areEqual(rayTrace2.func_178782_a(), pos3);
            } else {
                z2 = true;
            }
            if (z2) {
                destination$iv$iv2.put(element$iv$iv2.getKey(), element$iv$iv2.getValue());
            }
        }
        Map validBlocks = MapsKt.toMutableMap(destination$iv$iv2);
        do {
            String str = this.priorityValue.get();
            if (Intrinsics.areEqual(str, "Distance")) {
                Iterator it = validBlocks.entrySet().iterator();
                if (!it.hasNext()) {
                    obj2 = null;
                } else {
                    Object next = it.next();
                    if (!it.hasNext()) {
                        obj2 = next;
                    } else {
                        Map.Entry $dstr$pos$block = (Map.Entry) next;
                        BlockPos pos4 = (BlockPos) $dstr$pos$block.getKey();
                        Block block3 = (Block) $dstr$pos$block.getValue();
                        double distance = BlockUtils.getCenterDistance(pos4);
                        BlockPos safePos = new BlockPos(((EntityPlayerSP) entityPlayer).field_70165_t, ((EntityPlayerSP) entityPlayer).field_70163_u - 1, ((EntityPlayerSP) entityPlayer).field_70161_v);
                        double d = (pos4.func_177958_n() == safePos.func_177958_n() && safePos.func_177956_o() <= pos4.func_177956_o() && pos4.func_177952_p() == safePos.func_177952_p()) ? Double.MAX_VALUE - distance : distance;
                        do {
                            Object next2 = it.next();
                            Map.Entry $dstr$pos$block2 = (Map.Entry) next2;
                            BlockPos pos5 = (BlockPos) $dstr$pos$block2.getKey();
                            Block block4 = (Block) $dstr$pos$block2.getValue();
                            double distance2 = BlockUtils.getCenterDistance(pos5);
                            BlockPos safePos2 = new BlockPos(((EntityPlayerSP) entityPlayer).field_70165_t, ((EntityPlayerSP) entityPlayer).field_70163_u - 1, ((EntityPlayerSP) entityPlayer).field_70161_v);
                            double d2 = (pos5.func_177958_n() == safePos2.func_177958_n() && safePos2.func_177956_o() <= pos5.func_177956_o() && pos5.func_177952_p() == safePos2.func_177952_p()) ? Double.MAX_VALUE - distance2 : distance2;
                            if (Double.compare(d, d2) > 0) {
                                next = next2;
                                d = d2;
                            }
                        } while (it.hasNext());
                        obj2 = next;
                    }
                }
                entry = (Map.Entry) obj2;
            } else if (!Intrinsics.areEqual(str, "Hardness")) {
                return;
            } else {
                Iterator it2 = validBlocks.entrySet().iterator();
                if (!it2.hasNext()) {
                    obj = null;
                } else {
                    Object next3 = it2.next();
                    if (!it2.hasNext()) {
                        obj = next3;
                    } else {
                        Map.Entry $dstr$pos$block3 = (Map.Entry) next3;
                        BlockPos pos6 = (BlockPos) $dstr$pos$block3.getKey();
                        Block block5 = (Block) $dstr$pos$block3.getValue();
                        World world = MinecraftInstance.f362mc.field_71441_e;
                        Intrinsics.checkNotNull(world);
                        double hardness = block5.func_180647_a(entityPlayer, world, pos6);
                        BlockPos safePos3 = new BlockPos(((EntityPlayerSP) entityPlayer).field_70165_t, ((EntityPlayerSP) entityPlayer).field_70163_u - 1, ((EntityPlayerSP) entityPlayer).field_70161_v);
                        double d3 = (pos6.func_177958_n() == safePos3.func_177958_n() && safePos3.func_177956_o() <= pos6.func_177956_o() && pos6.func_177952_p() == safePos3.func_177952_p()) ? Double.MIN_VALUE + hardness : hardness;
                        do {
                            Object next4 = it2.next();
                            Map.Entry $dstr$pos$block4 = (Map.Entry) next4;
                            BlockPos pos7 = (BlockPos) $dstr$pos$block4.getKey();
                            Block block6 = (Block) $dstr$pos$block4.getValue();
                            World world2 = MinecraftInstance.f362mc.field_71441_e;
                            Intrinsics.checkNotNull(world2);
                            double hardness2 = block6.func_180647_a(entityPlayer, world2, pos7);
                            BlockPos safePos4 = new BlockPos(((EntityPlayerSP) entityPlayer).field_70165_t, ((EntityPlayerSP) entityPlayer).field_70163_u - 1, ((EntityPlayerSP) entityPlayer).field_70161_v);
                            double d4 = (pos7.func_177958_n() == safePos4.func_177958_n() && safePos4.func_177956_o() <= pos7.func_177956_o() && pos7.func_177952_p() == safePos4.func_177952_p()) ? Double.MIN_VALUE + hardness2 : hardness2;
                            if (Double.compare(d3, d4) < 0) {
                                next3 = next4;
                                d3 = d4;
                            }
                        } while (it2.hasNext());
                        obj = next3;
                    }
                }
                entry = (Map.Entry) obj;
            }
            Map.Entry entry2 = entry;
            if (entry2 == null) {
                return;
            }
            BlockPos blockPos = (BlockPos) entry2.getKey();
            Block block7 = (Block) entry2.getValue();
            if (!Intrinsics.areEqual(blockPos, this.currentBlock)) {
                Companion companion = Companion;
                currentDamage = 0.0f;
            }
            if (this.rotationsValue.get().booleanValue()) {
                VecRotation rotation = RotationUtils.faceBlock(blockPos);
                if (rotation == null) {
                    return;
                }
                RotationUtils.setTargetRotation(rotation.getRotation());
            }
            this.currentBlock = blockPos;
            this.attackedBlocks.add(blockPos);
            Module module2 = LiquidBounce.INSTANCE.getModuleManager().getModule(AutoTool.class);
            if (module2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.player.AutoTool");
            }
            AutoTool autoTool = (AutoTool) module2;
            if (autoTool.getState()) {
                autoTool.switchSlot(blockPos);
            }
            if (currentDamage == 0.0f) {
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.DOWN));
                World world3 = MinecraftInstance.f362mc.field_71441_e;
                Intrinsics.checkNotNull(world3);
                if (block7.func_180647_a(entityPlayer, world3, blockPos) >= 1.0f) {
                    Companion companion2 = Companion;
                    currentDamage = 0.0f;
                    entityPlayer.func_71038_i();
                    MinecraftInstance.f362mc.field_71442_b.func_178888_a(blockPos, EnumFacing.DOWN);
                    this.blockHitDelay = this.hitDelayValue.get().intValue();
                    validBlocks.remove(blockPos);
                    this.nuke++;
                }
            }
            entityPlayer.func_71038_i();
            Companion companion3 = Companion;
            float f = currentDamage;
            World world4 = MinecraftInstance.f362mc.field_71441_e;
            Intrinsics.checkNotNull(world4);
            currentDamage = f + block7.func_180647_a(entityPlayer, world4, blockPos);
            WorldClient worldClient3 = MinecraftInstance.f362mc.field_71441_e;
            Intrinsics.checkNotNull(worldClient3);
            worldClient3.func_175715_c(entityPlayer.func_145782_y(), blockPos, ((int) (currentDamage * 10.0f)) - 1);
            if (currentDamage < 1.0f) {
                return;
            }
            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.DOWN));
            MinecraftInstance.f362mc.field_71442_b.func_178888_a(blockPos, EnumFacing.DOWN);
            this.blockHitDelay = this.hitDelayValue.get().intValue();
            Companion companion4 = Companion;
            currentDamage = 0.0f;
            return;
        } while (this.nuke < this.nukeValue.get().intValue());
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.layerValue.get().booleanValue()) {
            EntityPlayerSP entityPlayerSP = MinecraftInstance.f362mc.field_71439_g;
            Intrinsics.checkNotNull(entityPlayerSP);
            double d = entityPlayerSP.field_70165_t;
            EntityPlayerSP entityPlayerSP2 = MinecraftInstance.f362mc.field_71439_g;
            Intrinsics.checkNotNull(entityPlayerSP2);
            double d2 = entityPlayerSP2.field_70163_u - 1;
            EntityPlayerSP entityPlayerSP3 = MinecraftInstance.f362mc.field_71439_g;
            Intrinsics.checkNotNull(entityPlayerSP3);
            BlockPos safePos = new BlockPos(d, d2, entityPlayerSP3.field_70161_v);
            Block safeBlock = BlockUtils.getBlock(safePos);
            if (safeBlock != null && validBlock(safeBlock)) {
                RenderUtils.drawBlockBox(safePos, Color.GREEN, true);
            }
        }
        Iterator<BlockPos> it = this.attackedBlocks.iterator();
        while (it.hasNext()) {
            BlockPos blockPos = it.next();
            RenderUtils.drawBlockBox(blockPos, Color.RED, true);
        }
    }

    private final boolean validBlock(Block block) {
        return !Intrinsics.areEqual(block, Blocks.field_150350_a) && !(block instanceof BlockLiquid) && !Intrinsics.areEqual(block, Blocks.field_150357_h);
    }

    /* compiled from: Nuker.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0014\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/Nuker$Companion;", "", "()V", "currentDamage", "", "getCurrentDamage", "()F", "setCurrentDamage", "(F)V", "LiquidBounce"})
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/world/Nuker$Companion.class */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }

        public final float getCurrentDamage() {
            return Nuker.currentDamage;
        }

        public final void setCurrentDamage(float f) {
            Nuker.currentDamage = f;
        }
    }
}
