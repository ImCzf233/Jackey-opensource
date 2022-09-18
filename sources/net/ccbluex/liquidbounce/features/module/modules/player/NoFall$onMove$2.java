package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: NoFall.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\u0010\n��\n\u0002\u0010\u000b\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010��\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, m53d2 = {"<anonymous>", "", "it", "Lnet/minecraft/block/Block;", "invoke", "(Lnet/minecraft/block/Block;)Ljava/lang/Boolean;"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/player/NoFall$onMove$2.class */
final class NoFall$onMove$2 extends Lambda implements Function1<Block, Boolean> {
    public static final NoFall$onMove$2 INSTANCE = new NoFall$onMove$2();

    NoFall$onMove$2() {
        super(1);
    }

    @NotNull
    public final Boolean invoke(@Nullable Block it) {
        return Boolean.valueOf(it instanceof BlockLiquid);
    }
}
