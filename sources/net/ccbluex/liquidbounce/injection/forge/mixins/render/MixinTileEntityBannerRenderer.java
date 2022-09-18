package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.LayeredColorMaskTexture;
import net.minecraft.client.renderer.tileentity.TileEntityBannerRenderer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({TileEntityBannerRenderer.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinTileEntityBannerRenderer.class */
public class MixinTileEntityBannerRenderer {
    @Shadow
    @Final
    private static Map<String, TileEntityBannerRenderer.TimedBannerTexture> field_178466_c;
    @Shadow
    @Final
    private static ResourceLocation field_178464_d;

    @Overwrite
    private ResourceLocation func_178463_a(TileEntityBanner banner) {
        String texture = banner.func_175116_e();
        if (texture.isEmpty()) {
            return null;
        }
        TileEntityBannerRenderer.TimedBannerTexture timedTexture = field_178466_c.get(texture);
        if (timedTexture == null) {
            if (field_178466_c.size() >= 256 && !freeCacheSlot()) {
                return field_178464_d;
            }
            List<TileEntityBanner.EnumBannerPattern> patternList = banner.func_175114_c();
            List<EnumDyeColor> colorList = banner.func_175110_d();
            List<String> patternPath = Lists.newArrayList();
            for (TileEntityBanner.EnumBannerPattern pattern : patternList) {
                patternPath.add("textures/entity/banner/" + pattern.func_177271_a() + ".png");
            }
            timedTexture = new TileEntityBannerRenderer.TimedBannerTexture();
            timedTexture.field_178471_b = new ResourceLocation(texture);
            Minecraft.func_71410_x().func_110434_K().func_110579_a(timedTexture.field_178471_b, new LayeredColorMaskTexture(field_178464_d, patternPath, colorList));
            field_178466_c.put(texture, timedTexture);
        }
        timedTexture.field_178472_a = System.currentTimeMillis();
        return timedTexture.field_178471_b;
    }

    @Unique
    private boolean freeCacheSlot() {
        long start = System.currentTimeMillis();
        Iterator<String> iterator = field_178466_c.keySet().iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            TileEntityBannerRenderer.TimedBannerTexture timedTexture = field_178466_c.get(next);
            if (start - timedTexture.field_178472_a > 5000) {
                Minecraft.func_71410_x().func_110434_K().func_147645_c(timedTexture.field_178471_b);
                iterator.remove();
                return true;
            }
        }
        return field_178466_c.size() < 256;
    }

    @Redirect(method = {"renderTileEntityAt(Lnet/minecraft/tileentity/TileEntityBanner;DDDFI)V"}, m17at = @AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/world/World;getTotalWorldTime()J"))
    private long resolveOverflow(World world) {
        return world.func_82737_E() % 100;
    }
}
