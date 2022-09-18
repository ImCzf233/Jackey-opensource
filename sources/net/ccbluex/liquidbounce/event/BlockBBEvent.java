package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Events.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0007\u0018��2\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n��\u001a\u0004\b\t\u0010\nR\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n��\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u0010¢\u0006\b\n��\u001a\u0004\b\u0014\u0010\u0012R\u0011\u0010\u0015\u001a\u00020\u0010¢\u0006\b\n��\u001a\u0004\b\u0016\u0010\u0012¨\u0006\u0017"}, m53d2 = {"Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "blockPos", "Lnet/minecraft/util/BlockPos;", "block", "Lnet/minecraft/block/Block;", "boundingBox", "Lnet/minecraft/util/AxisAlignedBB;", "(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/Block;Lnet/minecraft/util/AxisAlignedBB;)V", "getBlock", "()Lnet/minecraft/block/Block;", "getBoundingBox", "()Lnet/minecraft/util/AxisAlignedBB;", "setBoundingBox", "(Lnet/minecraft/util/AxisAlignedBB;)V", "x", "", "getX", "()I", "y", "getY", "z", "getZ", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/event/BlockBBEvent.class */
public final class BlockBBEvent extends Event {
    @NotNull
    private final Block block;
    @Nullable
    private AxisAlignedBB boundingBox;

    /* renamed from: x */
    private final int f322x;

    /* renamed from: y */
    private final int f323y;

    /* renamed from: z */
    private final int f324z;

    public BlockBBEvent(@NotNull BlockPos blockPos, @NotNull Block block, @Nullable AxisAlignedBB boundingBox) {
        Intrinsics.checkNotNullParameter(blockPos, "blockPos");
        Intrinsics.checkNotNullParameter(block, "block");
        this.block = block;
        this.boundingBox = boundingBox;
        this.f322x = blockPos.func_177958_n();
        this.f323y = blockPos.func_177956_o();
        this.f324z = blockPos.func_177952_p();
    }

    @NotNull
    public final Block getBlock() {
        return this.block;
    }

    @Nullable
    public final AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    public final void setBoundingBox(@Nullable AxisAlignedBB axisAlignedBB) {
        this.boundingBox = axisAlignedBB;
    }

    public final int getX() {
        return this.f322x;
    }

    public final int getY() {
        return this.f323y;
    }

    public final int getZ() {
        return this.f324z;
    }
}
