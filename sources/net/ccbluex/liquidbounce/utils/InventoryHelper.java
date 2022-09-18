package net.ccbluex.liquidbounce.utils;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.ClickWindowEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

/* compiled from: InventoryHelper.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n��\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018��2\u00020\u00012\u00020\u0002B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0006\u0010\u000e\u001a\u00020\u000fJ\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u000e\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0014J\u0016\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u001cJ\u0010\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u001fH\u0007J\u0010\u0010 \u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020!H\u0007J\u0006\u0010\"\u001a\u00020\u000fR\u001f\u0010\u0004\u001a\u0010\u0012\f\u0012\n \u0007*\u0004\u0018\u00010\u00060\u00060\u0005¢\u0006\b\n��\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n��\u001a\u0004\b\f\u0010\r¨\u0006#"}, m53d2 = {"Lnet/ccbluex/liquidbounce/utils/InventoryHelper;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "BLOCK_BLACKLIST", "", "Lnet/minecraft/block/Block;", "kotlin.jvm.PlatformType", "getBLOCK_BLACKLIST", "()Ljava/util/List;", "CLICK_TIMER", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "getCLICK_TIMER", "()Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "closePacket", "", "handleEvents", "", "isBlockListBlock", "itemBlock", "Lnet/minecraft/item/ItemBlock;", "isPositivePotion", "item", "Lnet/minecraft/item/ItemPotion;", "stack", "Lnet/minecraft/item/ItemStack;", "isPositivePotionEffect", "id", "", "onClickWindow", "event", "Lnet/ccbluex/liquidbounce/event/ClickWindowEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "openPacket", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/InventoryHelper.class */
public final class InventoryHelper extends MinecraftInstance implements Listenable {
    @NotNull
    public static final InventoryHelper INSTANCE = new InventoryHelper();
    @NotNull
    private static final MSTimer CLICK_TIMER = new MSTimer();
    @NotNull
    private static final List<Block> BLOCK_BLACKLIST = CollectionsKt.listOf((Object[]) new Block[]{Blocks.field_150381_bn, (Block) Blocks.field_150486_ae, Blocks.field_150477_bB, Blocks.field_150447_bR, Blocks.field_150467_bQ, (Block) Blocks.field_150354_m, Blocks.field_150321_G, Blocks.field_150478_aa, Blocks.field_150462_ai, Blocks.field_150460_al, Blocks.field_150392_bi, Blocks.field_150367_z, Blocks.field_150456_au, Blocks.field_150452_aw, Blocks.field_150323_B, Blocks.field_150409_cd, Blocks.field_150335_W, Blocks.field_180393_cK, Blocks.field_180394_cL, Blocks.field_150429_aA, Blocks.field_150351_n, (Block) Blocks.field_150434_aF, Blocks.field_150324_C, Blocks.field_150442_at, Blocks.field_150472_an, Blocks.field_150444_as, Blocks.field_150421_aI, Blocks.field_180407_aO, Blocks.field_180408_aP, Blocks.field_180404_aQ, Blocks.field_180403_aR, Blocks.field_180406_aS, Blocks.field_180390_bo, Blocks.field_180391_bp, Blocks.field_180392_bq, Blocks.field_180386_br, Blocks.field_180385_bs, Blocks.field_150386_bk, Blocks.field_150415_aT, Blocks.field_150440_ba, Blocks.field_150382_bo, (Block) Blocks.field_150383_bp, (Block) Blocks.field_150465_bP, (Block) Blocks.field_150438_bZ, Blocks.field_150404_cg, (Block) Blocks.field_150488_af, Blocks.field_150445_bS, Blocks.field_150443_bT, (Block) Blocks.field_150453_bW});

    private InventoryHelper() {
    }

    @NotNull
    public final MSTimer getCLICK_TIMER() {
        return CLICK_TIMER;
    }

    @NotNull
    public final List<Block> getBLOCK_BLACKLIST() {
        return BLOCK_BLACKLIST;
    }

    public final boolean isBlockListBlock(@NotNull ItemBlock itemBlock) {
        Intrinsics.checkNotNullParameter(itemBlock, "itemBlock");
        Block block = itemBlock.func_179223_d();
        return BLOCK_BLACKLIST.contains(block) || !block.func_149686_d();
    }

    @EventTarget
    public final void onClickWindow(@NotNull ClickWindowEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        CLICK_TIMER.reset();
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet packet = event.getPacket();
        if (packet instanceof C08PacketPlayerBlockPlacement) {
            CLICK_TIMER.reset();
        }
    }

    public final void openPacket() {
        MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
    }

    public final void closePacket() {
        MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C0DPacketCloseWindow());
    }

    public final boolean isPositivePotionEffect(int id) {
        if (id == Potion.field_76428_l.field_76415_H || id == Potion.field_76424_c.field_76415_H || id == Potion.field_76432_h.field_76415_H || id == Potion.field_76439_r.field_76415_H || id == Potion.field_76430_j.field_76415_H || id == Potion.field_76441_p.field_76415_H || id == Potion.field_76429_m.field_76415_H || id == Potion.field_76427_o.field_76415_H || id == Potion.field_76444_x.field_76415_H || id == Potion.field_76422_e.field_76415_H || id == Potion.field_76420_g.field_76415_H || id == Potion.field_180152_w.field_76415_H || id == Potion.field_76426_n.field_76415_H) {
            return true;
        }
        return false;
    }

    public final boolean isPositivePotion(@NotNull ItemPotion item, @NotNull ItemStack stack) {
        Intrinsics.checkNotNullParameter(item, "item");
        Intrinsics.checkNotNullParameter(stack, "stack");
        Iterable func_77832_l = item.func_77832_l(stack);
        Intrinsics.checkNotNullExpressionValue(func_77832_l, "item.getEffects(stack)");
        Iterable $this$forEach$iv = func_77832_l;
        for (Object element$iv : $this$forEach$iv) {
            PotionEffect it = (PotionEffect) element$iv;
            if (INSTANCE.isPositivePotionEffect(it.func_76456_a())) {
                return true;
            }
        }
        return false;
    }

    @Override // net.ccbluex.liquidbounce.event.Listenable
    public boolean handleEvents() {
        return true;
    }
}
