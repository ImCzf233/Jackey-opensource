package net.ccbluex.liquidbounce.utils.block;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BlockUtils.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n��\n\u0002\u0010\b\n��\n\u0002\u0010\u0006\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0002\bÆ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007J&\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0014\u0010\n\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\f\u0012\u0004\u0012\u00020\u00040\u000bH\u0007J&\u0010\r\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0014\u0010\n\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\f\u0012\u0004\u0012\u00020\u00040\u000bH\u0007J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0007J\u0014\u0010\u0011\u001a\u0004\u0018\u00010\f2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0007J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0014\u0010\u0018\u001a\u0004\u0018\u00010\u00192\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007J\u0012\u0010\u001a\u001a\u00020\u001b2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007J\u0012\u0010\u001c\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007J\u0012\u0010\u001d\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007J\u001c\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\f0\u001f2\u0006\u0010 \u001a\u00020\u0015H\u0007¨\u0006!"}, m53d2 = {"Lnet/ccbluex/liquidbounce/utils/block/BlockUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "canBeClicked", "", "blockPos", "Lnet/minecraft/util/BlockPos;", "collideBlock", "axisAlignedBB", "Lnet/minecraft/util/AxisAlignedBB;", "collide", "Lkotlin/Function1;", "Lnet/minecraft/block/Block;", "collideBlockIntersects", "floorVec3", "Lnet/minecraft/util/Vec3;", "vec3", "getBlock", "getBlockName", "", "id", "", "getCenterDistance", "", "getMaterial", "Lnet/minecraft/block/material/Material;", "getState", "Lnet/minecraft/block/state/IBlockState;", "isFullBlock", "isReplaceable", "searchBlocks", "", "radius", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/block/BlockUtils.class */
public final class BlockUtils extends MinecraftInstance {
    @NotNull
    public static final BlockUtils INSTANCE = new BlockUtils();

    private BlockUtils() {
    }

    @JvmStatic
    @Nullable
    public static final Block getBlock(@Nullable BlockPos blockPos) {
        WorldClient worldClient = MinecraftInstance.f362mc.field_71441_e;
        if (worldClient == null) {
            return null;
        }
        IBlockState func_180495_p = worldClient.func_180495_p(blockPos);
        if (func_180495_p != null) {
            return func_180495_p.func_177230_c();
        }
        return null;
    }

    @JvmStatic
    @Nullable
    public static final Material getMaterial(@Nullable BlockPos blockPos) {
        BlockUtils blockUtils = INSTANCE;
        Block block = getBlock(blockPos);
        if (block == null) {
            return null;
        }
        return block.func_149688_o();
    }

    @JvmStatic
    public static final boolean isReplaceable(@Nullable BlockPos blockPos) {
        BlockUtils blockUtils = INSTANCE;
        Material material = getMaterial(blockPos);
        if (material == null) {
            return false;
        }
        return material.func_76222_j();
    }

    @JvmStatic
    @NotNull
    public static final IBlockState getState(@Nullable BlockPos blockPos) {
        IBlockState func_180495_p = MinecraftInstance.f362mc.field_71441_e.func_180495_p(blockPos);
        Intrinsics.checkNotNullExpressionValue(func_180495_p, "mc.theWorld.getBlockState(blockPos)");
        return func_180495_p;
    }

    @JvmStatic
    public static final boolean canBeClicked(@Nullable BlockPos blockPos) {
        boolean z;
        BlockUtils blockUtils = INSTANCE;
        Block block = getBlock(blockPos);
        if (block == null) {
            z = false;
        } else {
            BlockUtils blockUtils2 = INSTANCE;
            z = block.func_176209_a(getState(blockPos), false);
        }
        return z && MinecraftInstance.f362mc.field_71441_e.func_175723_af().func_177746_a(blockPos);
    }

    @JvmStatic
    @NotNull
    public static final String getBlockName(int id) {
        String func_149732_F = Block.func_149729_e(id).func_149732_F();
        Intrinsics.checkNotNullExpressionValue(func_149732_F, "getBlockById(id).localizedName");
        return func_149732_F;
    }

    @JvmStatic
    public static final boolean isFullBlock(@Nullable BlockPos blockPos) {
        AxisAlignedBB axisAlignedBB;
        BlockUtils blockUtils = INSTANCE;
        Block block = getBlock(blockPos);
        if (block == null) {
            axisAlignedBB = null;
        } else {
            BlockUtils blockUtils2 = INSTANCE;
            axisAlignedBB = block.func_180640_a(MinecraftInstance.f362mc.field_71441_e, blockPos, getState(blockPos));
        }
        AxisAlignedBB axisAlignedBB2 = axisAlignedBB;
        if (axisAlignedBB2 != null) {
            if (axisAlignedBB2.field_72336_d - axisAlignedBB2.field_72340_a == 1.0d) {
                if (axisAlignedBB2.field_72337_e - axisAlignedBB2.field_72338_b == 1.0d) {
                    if (axisAlignedBB2.field_72334_f - axisAlignedBB2.field_72339_c == 1.0d) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    @JvmStatic
    public static final double getCenterDistance(@NotNull BlockPos blockPos) {
        Intrinsics.checkNotNullParameter(blockPos, "blockPos");
        return MinecraftInstance.f362mc.field_71439_g.func_70011_f(blockPos.func_177958_n() + 0.5d, blockPos.func_177956_o() + 0.5d, blockPos.func_177952_p() + 0.5d);
    }

    @JvmStatic
    @NotNull
    public static final Map<BlockPos, Block> searchBlocks(int radius) {
        int x;
        int y;
        int z;
        Map blocks = new LinkedHashMap();
        int i = radius;
        int i2 = (-radius) + 1;
        if (i2 <= i) {
            do {
                x = i;
                i--;
                int i3 = radius;
                int i4 = (-radius) + 1;
                if (i4 <= i3) {
                    do {
                        y = i3;
                        i3--;
                        int i5 = radius;
                        int i6 = (-radius) + 1;
                        if (i6 <= i5) {
                            do {
                                z = i5;
                                i5--;
                                BlockPos blockPos = new BlockPos(((int) MinecraftInstance.f362mc.field_71439_g.field_70165_t) + x, ((int) MinecraftInstance.f362mc.field_71439_g.field_70163_u) + y, ((int) MinecraftInstance.f362mc.field_71439_g.field_70161_v) + z);
                                BlockUtils blockUtils = INSTANCE;
                                Block block = getBlock(blockPos);
                                if (block != null) {
                                    blocks.put(blockPos, block);
                                }
                            } while (z != i6);
                        }
                    } while (y != i4);
                }
            } while (x != i2);
            return blocks;
        }
        return blocks;
    }

    @JvmStatic
    public static final boolean collideBlock(@NotNull AxisAlignedBB axisAlignedBB, @NotNull Function1<? super Block, Boolean> collide) {
        Intrinsics.checkNotNullParameter(axisAlignedBB, "axisAlignedBB");
        Intrinsics.checkNotNullParameter(collide, "collide");
        int func_76128_c = MathHelper.func_76128_c(MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().field_72340_a);
        int func_76128_c2 = MathHelper.func_76128_c(MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().field_72336_d) + 1;
        while (func_76128_c < func_76128_c2) {
            int x = func_76128_c;
            func_76128_c++;
            int func_76128_c3 = MathHelper.func_76128_c(MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().field_72339_c);
            int func_76128_c4 = MathHelper.func_76128_c(MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().field_72334_f) + 1;
            while (func_76128_c3 < func_76128_c4) {
                int z = func_76128_c3;
                func_76128_c3++;
                BlockUtils blockUtils = INSTANCE;
                Block block = getBlock(new BlockPos(x, axisAlignedBB.field_72338_b, z));
                if (!collide.invoke(block).booleanValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    @JvmStatic
    public static final boolean collideBlockIntersects(@NotNull AxisAlignedBB axisAlignedBB, @NotNull Function1<? super Block, Boolean> collide) {
        AxisAlignedBB axisAlignedBB2;
        Intrinsics.checkNotNullParameter(axisAlignedBB, "axisAlignedBB");
        Intrinsics.checkNotNullParameter(collide, "collide");
        int func_76128_c = MathHelper.func_76128_c(MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().field_72340_a);
        int func_76128_c2 = MathHelper.func_76128_c(MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().field_72336_d) + 1;
        while (func_76128_c < func_76128_c2) {
            int x = func_76128_c;
            func_76128_c++;
            int func_76128_c3 = MathHelper.func_76128_c(MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().field_72339_c);
            int func_76128_c4 = MathHelper.func_76128_c(MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().field_72334_f) + 1;
            while (func_76128_c3 < func_76128_c4) {
                int z = func_76128_c3;
                func_76128_c3++;
                BlockPos blockPos = new BlockPos(x, axisAlignedBB.field_72338_b, z);
                BlockUtils blockUtils = INSTANCE;
                Block block = getBlock(blockPos);
                if (collide.invoke(block).booleanValue()) {
                    if (block == null) {
                        axisAlignedBB2 = null;
                    } else {
                        BlockUtils blockUtils2 = INSTANCE;
                        axisAlignedBB2 = block.func_180640_a(MinecraftInstance.f362mc.field_71441_e, blockPos, getState(blockPos));
                    }
                    AxisAlignedBB boundingBox = axisAlignedBB2;
                    if (boundingBox != null && MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().func_72326_a(boundingBox)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @JvmStatic
    @NotNull
    public static final Vec3 floorVec3(@NotNull Vec3 vec3) {
        Intrinsics.checkNotNullParameter(vec3, "vec3");
        return new Vec3(Math.floor(vec3.field_72450_a), Math.floor(vec3.field_72448_b), Math.floor(vec3.field_72449_c));
    }
}
