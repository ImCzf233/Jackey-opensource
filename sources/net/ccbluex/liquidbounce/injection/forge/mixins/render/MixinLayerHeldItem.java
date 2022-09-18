package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({LayerHeldItem.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinLayerHeldItem.class */
public class MixinLayerHeldItem {
    @Shadow
    @Final
    private RendererLivingEntity<?> field_177206_a;

    @Overwrite
    public void func_177141_a(EntityLivingBase entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
        ItemStack itemstack = entitylivingbaseIn.func_70694_bm();
        if (itemstack != null) {
            GlStateManager.func_179094_E();
            if (this.field_177206_a.func_177087_b().field_78091_s) {
                GlStateManager.func_179109_b(0.0f, 0.625f, 0.0f);
                GlStateManager.func_179114_b(-20.0f, -1.0f, 0.0f, 0.0f);
                GlStateManager.func_179152_a(0.5f, 0.5f, 0.5f);
            }
            UUID uuid = entitylivingbaseIn.func_110124_au();
            EntityPlayer entityplayer = Minecraft.func_71410_x().field_71441_e.func_152378_a(uuid);
            if (entityplayer != null && entityplayer.func_70632_aY()) {
                if (entitylivingbaseIn.func_70093_af()) {
                    this.field_177206_a.func_177087_b().func_178718_a(0.0325f);
                    GlStateManager.func_179109_b(-0.58f, 0.3f, -0.2f);
                    GlStateManager.func_179114_b(-24390.0f, 137290.0f, -2009900.0f, -2054900.0f);
                } else {
                    this.field_177206_a.func_177087_b().func_178718_a(0.0325f);
                    GlStateManager.func_179109_b(-0.48f, 0.2f, -0.2f);
                    GlStateManager.func_179114_b(-24390.0f, 137290.0f, -2009900.0f, -2054900.0f);
                }
            } else {
                this.field_177206_a.func_177087_b().func_178718_a(0.0625f);
            }
            GlStateManager.func_179109_b(-0.0625f, 0.4375f, 0.0625f);
            if ((entitylivingbaseIn instanceof EntityPlayer) && ((EntityPlayer) entitylivingbaseIn).field_71104_cf != null) {
                itemstack = new ItemStack(Items.field_151112_aM, 0);
            }
            Item item = itemstack.func_77973_b();
            Minecraft minecraft = Minecraft.func_71410_x();
            if ((item instanceof ItemBlock) && Block.func_149634_a(item).func_149645_b() == 2) {
                GlStateManager.func_179109_b(0.0f, 0.1875f, -0.3125f);
                GlStateManager.func_179114_b(20.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.func_179114_b(45.0f, 0.0f, 1.0f, 0.0f);
                GlStateManager.func_179152_a(-0.375f, -0.375f, 0.375f);
            }
            if (entitylivingbaseIn.func_70093_af()) {
                GlStateManager.func_179109_b(0.0f, 0.203125f, 0.0f);
            }
            minecraft.func_175597_ag().func_178099_a(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON);
            GlStateManager.func_179121_F();
        }
    }
}
