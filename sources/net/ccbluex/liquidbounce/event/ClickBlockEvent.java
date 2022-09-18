package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.Nullable;

/* compiled from: Events.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018��2\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n��\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n��\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, m53d2 = {"Lnet/ccbluex/liquidbounce/event/ClickBlockEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "clickedBlock", "Lnet/minecraft/util/BlockPos;", "enumFacing", "Lnet/minecraft/util/EnumFacing;", "(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)V", "getClickedBlock", "()Lnet/minecraft/util/BlockPos;", "getEnumFacing", "()Lnet/minecraft/util/EnumFacing;", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/event/ClickBlockEvent.class */
public final class ClickBlockEvent extends Event {
    @Nullable
    private final BlockPos clickedBlock;
    @Nullable
    private final EnumFacing enumFacing;

    public ClickBlockEvent(@Nullable BlockPos clickedBlock, @Nullable EnumFacing enumFacing) {
        this.clickedBlock = clickedBlock;
        this.enumFacing = enumFacing;
    }

    @Nullable
    public final BlockPos getClickedBlock() {
        return this.clickedBlock;
    }

    @Nullable
    public final EnumFacing getEnumFacing() {
        return this.enumFacing;
    }
}
