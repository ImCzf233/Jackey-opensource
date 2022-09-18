package net.ccbluex.liquidbounce.injection.forge.mixins.patcher.performance;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin({BlockPos.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/patcher/performance/MixinBlockPos.class */
public abstract class MixinBlockPos extends Vec3i {
    public /* bridge */ /* synthetic */ int compareTo(Object x0) {
        return super.compareTo((Vec3i) x0);
    }

    public MixinBlockPos(int xIn, int yIn, int zIn) {
        super(xIn, yIn, zIn);
    }

    @Overwrite
    public BlockPos func_177984_a() {
        return new BlockPos(func_177958_n(), func_177956_o() + 1, func_177952_p());
    }

    @Overwrite
    public BlockPos func_177981_b(int offset) {
        return offset == 0 ? (BlockPos) this : new BlockPos(func_177958_n(), func_177956_o() + offset, func_177952_p());
    }

    @Overwrite
    public BlockPos func_177977_b() {
        return new BlockPos(func_177958_n(), func_177956_o() - 1, func_177952_p());
    }

    @Overwrite
    public BlockPos func_177979_c(int offset) {
        return offset == 0 ? (BlockPos) this : new BlockPos(func_177958_n(), func_177956_o() - offset, func_177952_p());
    }

    @Overwrite
    public BlockPos func_177978_c() {
        return new BlockPos(func_177958_n(), func_177956_o(), func_177952_p() - 1);
    }

    @Overwrite
    public BlockPos func_177964_d(int offset) {
        return offset == 0 ? (BlockPos) this : new BlockPos(func_177958_n(), func_177956_o(), func_177952_p() - offset);
    }

    @Overwrite
    public BlockPos func_177968_d() {
        return new BlockPos(func_177958_n(), func_177956_o(), func_177952_p() + 1);
    }

    @Overwrite
    public BlockPos func_177970_e(int offset) {
        return offset == 0 ? (BlockPos) this : new BlockPos(func_177958_n(), func_177956_o(), func_177952_p() + offset);
    }

    @Overwrite
    public BlockPos func_177976_e() {
        return new BlockPos(func_177958_n() - 1, func_177956_o(), func_177952_p());
    }

    @Overwrite
    public BlockPos func_177985_f(int offset) {
        return offset == 0 ? (BlockPos) this : new BlockPos(func_177958_n() - offset, func_177956_o(), func_177952_p());
    }

    @Overwrite
    public BlockPos func_177974_f() {
        return new BlockPos(func_177958_n() + 1, func_177956_o(), func_177952_p());
    }

    @Overwrite
    public BlockPos func_177965_g(int offset) {
        return offset == 0 ? (BlockPos) this : new BlockPos(func_177958_n() + offset, func_177956_o(), func_177952_p());
    }

    @Overwrite
    public BlockPos func_177972_a(EnumFacing direction) {
        return new BlockPos(func_177958_n() + direction.func_82601_c(), func_177956_o() + direction.func_96559_d(), func_177952_p() + direction.func_82599_e());
    }
}
