package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

/* compiled from: BlockOverlay.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��6\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0007J\u0010\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0015H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u0013\u0010\t\u001a\u0004\u0018\u00010\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u0007¢\u0006\b\n��\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0016"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/BlockOverlay;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "colorBlueValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "colorGreenValue", "colorRainbow", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "colorRedValue", "currentBlock", "Lnet/minecraft/util/BlockPos;", "getCurrentBlock", "()Lnet/minecraft/util/BlockPos;", "infoValue", "getInfoValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "LiquidBounce"})
@ModuleInfo(name = "BlockOverlay", spacedName = "Block Overlay", description = "Allows you to change the design of the block overlay.", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/BlockOverlay.class */
public final class BlockOverlay extends Module {
    @NotNull
    private final IntegerValue colorRedValue = new IntegerValue("R", 68, 0, 255);
    @NotNull
    private final IntegerValue colorGreenValue = new IntegerValue("G", 117, 0, 255);
    @NotNull
    private final IntegerValue colorBlueValue = new IntegerValue("B", 255, 0, 255);
    @NotNull
    private final BoolValue colorRainbow = new BoolValue("Rainbow", false);
    @NotNull
    private final BoolValue infoValue = new BoolValue("Info", false);

    @NotNull
    public final BoolValue getInfoValue() {
        return this.infoValue;
    }

    @Nullable
    public final BlockPos getCurrentBlock() {
        MovingObjectPosition movingObjectPosition = MinecraftInstance.f362mc.field_71476_x;
        BlockPos func_178782_a = movingObjectPosition == null ? null : movingObjectPosition.func_178782_a();
        if (func_178782_a == null) {
            return null;
        }
        BlockPos blockPos = func_178782_a;
        if (BlockUtils.canBeClicked(blockPos) && MinecraftInstance.f362mc.field_71441_e.func_175723_af().func_177746_a(blockPos)) {
            return blockPos;
        }
        return null;
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Block block;
        Intrinsics.checkNotNullParameter(event, "event");
        BlockPos blockPos = getCurrentBlock();
        if (blockPos == null || (block = MinecraftInstance.f362mc.field_71441_e.func_180495_p(blockPos).func_177230_c()) == null) {
            return;
        }
        float partialTicks = event.getPartialTicks();
        Color color = this.colorRainbow.get().booleanValue() ? ColorUtils.rainbow(0.4f) : new Color(this.colorRedValue.get().intValue(), this.colorGreenValue.get().intValue(), this.colorBlueValue.get().intValue(), 102);
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        RenderUtils.glColor(color);
        GL11.glLineWidth(2.0f);
        GlStateManager.func_179090_x();
        GlStateManager.func_179132_a(false);
        block.func_180654_a(MinecraftInstance.f362mc.field_71441_e, blockPos);
        double x = MinecraftInstance.f362mc.field_71439_g.field_70142_S + ((MinecraftInstance.f362mc.field_71439_g.field_70165_t - MinecraftInstance.f362mc.field_71439_g.field_70142_S) * partialTicks);
        double y = MinecraftInstance.f362mc.field_71439_g.field_70137_T + ((MinecraftInstance.f362mc.field_71439_g.field_70163_u - MinecraftInstance.f362mc.field_71439_g.field_70137_T) * partialTicks);
        double z = MinecraftInstance.f362mc.field_71439_g.field_70136_U + ((MinecraftInstance.f362mc.field_71439_g.field_70161_v - MinecraftInstance.f362mc.field_71439_g.field_70136_U) * partialTicks);
        AxisAlignedBB axisAlignedBB = block.func_180646_a(MinecraftInstance.f362mc.field_71441_e, blockPos).func_72314_b(0.0020000000949949026d, 0.0020000000949949026d, 0.0020000000949949026d).func_72317_d(-x, -y, -z);
        RenderUtils.drawSelectionBoundingBox(axisAlignedBB);
        RenderUtils.drawFilledBox(axisAlignedBB);
        GlStateManager.func_179132_a(true);
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179117_G();
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        BlockPos blockPos;
        Block block;
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.infoValue.get().booleanValue() || (blockPos = getCurrentBlock()) == null || (block = BlockUtils.getBlock(blockPos)) == null) {
            return;
        }
        String info = ((Object) block.func_149732_F()) + " §7ID: " + Block.func_149682_b(block);
        ScaledResolution scaledResolution = new ScaledResolution(MinecraftInstance.f362mc);
        GlStateManager.func_179117_G();
        Fonts.fontSFUI40.drawCenteredString(info, scaledResolution.func_78326_a() / 2.0f, (scaledResolution.func_78328_b() / 2.0f) + 6.0f, Color.WHITE.getRGB());
    }
}
