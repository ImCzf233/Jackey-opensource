package net.ccbluex.liquidbounce.utils.extensions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��\u0012\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\u001a\f\u0010��\u001a\u0004\u0018\u00010\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0004*\u00020\u0002¨\u0006\u0005"}, m53d2 = {"getBlock", "Lnet/minecraft/block/Block;", "Lnet/minecraft/util/BlockPos;", "getVec", "Lnet/minecraft/util/Vec3;", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.utils.extensions.BlockExtensionKt */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/extensions/BlockExtensionKt.class */
public final class BlockExtension {
    @Nullable
    public static final Block getBlock(@NotNull BlockPos $this$getBlock) {
        Intrinsics.checkNotNullParameter($this$getBlock, "<this>");
        return BlockUtils.getBlock($this$getBlock);
    }

    @NotNull
    public static final Vec3 getVec(@NotNull BlockPos $this$getVec) {
        Intrinsics.checkNotNullParameter($this$getVec, "<this>");
        return new Vec3($this$getVec.func_177958_n() + 0.5d, $this$getVec.func_177956_o() + 0.5d, $this$getVec.func_177952_p() + 0.5d);
    }
}
