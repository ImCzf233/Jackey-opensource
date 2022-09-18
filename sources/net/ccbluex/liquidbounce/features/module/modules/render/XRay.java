package net.ccbluex.liquidbounce.features.module.modules.render;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.init.Blocks;
import org.jetbrains.annotations.NotNull;

/* compiled from: XRay.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n��\n\u0002\u0010\u000b\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n��\u001a\u0004\b\u0006\u0010\u0007¨\u0006\f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/XRay;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "xrayBlocks", "", "Lnet/minecraft/block/Block;", "getXrayBlocks", "()Ljava/util/List;", "onToggle", "", "state", "", "LiquidBounce"})
@ModuleInfo(name = "XRay", description = "Allows you to see ores through walls.", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/XRay.class */
public final class XRay extends Module {
    @NotNull
    private final List<Block> xrayBlocks;

    public XRay() {
        Block coal_ore = Blocks.field_150365_q;
        Intrinsics.checkNotNullExpressionValue(coal_ore, "coal_ore");
        Block iron_ore = Blocks.field_150366_p;
        Intrinsics.checkNotNullExpressionValue(iron_ore, "iron_ore");
        Block gold_ore = Blocks.field_150352_o;
        Intrinsics.checkNotNullExpressionValue(gold_ore, "gold_ore");
        Block redstone_ore = Blocks.field_150450_ax;
        Intrinsics.checkNotNullExpressionValue(redstone_ore, "redstone_ore");
        Block lapis_ore = Blocks.field_150369_x;
        Intrinsics.checkNotNullExpressionValue(lapis_ore, "lapis_ore");
        Block diamond_ore = Blocks.field_150482_ag;
        Intrinsics.checkNotNullExpressionValue(diamond_ore, "diamond_ore");
        Block emerald_ore = Blocks.field_150412_bA;
        Intrinsics.checkNotNullExpressionValue(emerald_ore, "emerald_ore");
        Block quartz_ore = Blocks.field_150449_bY;
        Intrinsics.checkNotNullExpressionValue(quartz_ore, "quartz_ore");
        Block clay = Blocks.field_150435_aG;
        Intrinsics.checkNotNullExpressionValue(clay, "clay");
        Block glowstone = Blocks.field_150426_aN;
        Intrinsics.checkNotNullExpressionValue(glowstone, "glowstone");
        Block crafting_table = Blocks.field_150462_ai;
        Intrinsics.checkNotNullExpressionValue(crafting_table, "crafting_table");
        Block torch = Blocks.field_150478_aa;
        Intrinsics.checkNotNullExpressionValue(torch, "torch");
        Block ladder = Blocks.field_150468_ap;
        Intrinsics.checkNotNullExpressionValue(ladder, "ladder");
        Block tnt = Blocks.field_150335_W;
        Intrinsics.checkNotNullExpressionValue(tnt, "tnt");
        Block coal_block = Blocks.field_150402_ci;
        Intrinsics.checkNotNullExpressionValue(coal_block, "coal_block");
        Block iron_block = Blocks.field_150339_S;
        Intrinsics.checkNotNullExpressionValue(iron_block, "iron_block");
        Block gold_block = Blocks.field_150340_R;
        Intrinsics.checkNotNullExpressionValue(gold_block, "gold_block");
        Block diamond_block = Blocks.field_150484_ah;
        Intrinsics.checkNotNullExpressionValue(diamond_block, "diamond_block");
        Block emerald_block = Blocks.field_150475_bE;
        Intrinsics.checkNotNullExpressionValue(emerald_block, "emerald_block");
        Block redstone_block = Blocks.field_150451_bX;
        Intrinsics.checkNotNullExpressionValue(redstone_block, "redstone_block");
        Block lapis_block = Blocks.field_150368_y;
        Intrinsics.checkNotNullExpressionValue(lapis_block, "lapis_block");
        BlockFire fire = Blocks.field_150480_ab;
        Intrinsics.checkNotNullExpressionValue(fire, "fire");
        Block mossy_cobblestone = Blocks.field_150341_Y;
        Intrinsics.checkNotNullExpressionValue(mossy_cobblestone, "mossy_cobblestone");
        Block mob_spawner = Blocks.field_150474_ac;
        Intrinsics.checkNotNullExpressionValue(mob_spawner, "mob_spawner");
        Block end_portal_frame = Blocks.field_150378_br;
        Intrinsics.checkNotNullExpressionValue(end_portal_frame, "end_portal_frame");
        Block enchanting_table = Blocks.field_150381_bn;
        Intrinsics.checkNotNullExpressionValue(enchanting_table, "enchanting_table");
        Block bookshelf = Blocks.field_150342_X;
        Intrinsics.checkNotNullExpressionValue(bookshelf, "bookshelf");
        Block command_block = Blocks.field_150483_bI;
        Intrinsics.checkNotNullExpressionValue(command_block, "command_block");
        BlockStaticLiquid lava = Blocks.field_150353_l;
        Intrinsics.checkNotNullExpressionValue(lava, "lava");
        BlockDynamicLiquid flowing_lava = Blocks.field_150356_k;
        Intrinsics.checkNotNullExpressionValue(flowing_lava, "flowing_lava");
        BlockStaticLiquid water = Blocks.field_150355_j;
        Intrinsics.checkNotNullExpressionValue(water, "water");
        BlockDynamicLiquid flowing_water = Blocks.field_150358_i;
        Intrinsics.checkNotNullExpressionValue(flowing_water, "flowing_water");
        Block furnace = Blocks.field_150460_al;
        Intrinsics.checkNotNullExpressionValue(furnace, "furnace");
        Block lit_furnace = Blocks.field_150470_am;
        Intrinsics.checkNotNullExpressionValue(lit_furnace, "lit_furnace");
        this.xrayBlocks = CollectionsKt.mutableListOf(coal_ore, iron_ore, gold_ore, redstone_ore, lapis_ore, diamond_ore, emerald_ore, quartz_ore, clay, glowstone, crafting_table, torch, ladder, tnt, coal_block, iron_block, gold_block, diamond_block, emerald_block, redstone_block, lapis_block, (Block) fire, mossy_cobblestone, mob_spawner, end_portal_frame, enchanting_table, bookshelf, command_block, (Block) lava, (Block) flowing_lava, (Block) water, (Block) flowing_water, furnace, lit_furnace);
        LiquidBounce.INSTANCE.getCommandManager().registerCommand(new Command(new String[0]) { // from class: net.ccbluex.liquidbounce.features.module.modules.render.XRay.1
            /*  JADX ERROR: JadxRuntimeException in pass: BlockSplitter
                jadx.core.utils.exceptions.JadxRuntimeException: Unexpected missing predecessor for block: B:8:0x001e
                	at jadx.core.dex.visitors.blocks.BlockSplitter.addTempConnectionsForExcHandlers(BlockSplitter.java:240)
                	at jadx.core.dex.visitors.blocks.BlockSplitter.visit(BlockSplitter.java:54)
                */
            @Override // net.ccbluex.liquidbounce.features.command.Command
            public void execute(@org.jetbrains.annotations.NotNull java.lang.String[] r6) {
                /*
                    Method dump skipped, instructions count: 542
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.features.module.modules.render.XRay.C16791.execute(java.lang.String[]):void");
            }
        });
    }

    @NotNull
    public final List<Block> getXrayBlocks() {
        return this.xrayBlocks;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onToggle(boolean state) {
        MinecraftInstance.f362mc.field_71438_f.func_72712_a();
    }
}
