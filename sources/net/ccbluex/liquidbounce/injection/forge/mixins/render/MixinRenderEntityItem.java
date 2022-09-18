package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.Chams;
import net.ccbluex.liquidbounce.features.module.modules.render.ItemPhysics;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({RenderEntityItem.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinRenderEntityItem.class */
public abstract class MixinRenderEntityItem extends Render<EntityItem> {
    @Shadow
    protected abstract int func_177078_a(ItemStack itemStack);

    @Shadow
    protected abstract boolean shouldBob();

    protected MixinRenderEntityItem(RenderManager p_i46179_1_) {
        super(p_i46179_1_);
    }

    @Inject(method = {"doRender"}, m23at = {@AbstractC1790At("HEAD")})
    private void injectChamsPre(CallbackInfo callbackInfo) {
        Chams chams = (Chams) LiquidBounce.moduleManager.getModule(Chams.class);
        if (chams.getState() && chams.getItemsValue().get().booleanValue()) {
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0f, -1000000.0f);
        }
    }

    @Inject(method = {"doRender"}, m23at = {@AbstractC1790At("RETURN")})
    private void injectChamsPost(CallbackInfo callbackInfo) {
        Chams chams = (Chams) LiquidBounce.moduleManager.getModule(Chams.class);
        if (chams.getState() && chams.getItemsValue().get().booleanValue()) {
            GL11.glPolygonOffset(1.0f, 1000000.0f);
            GL11.glDisable(32823);
        }
    }

    @Overwrite
    private int func_177077_a(EntityItem itemIn, double p_177077_2_, double p_177077_4_, double p_177077_6_, float p_177077_8_, IBakedModel p_177077_9_) {
        ItemPhysics itemPhysics = (ItemPhysics) LiquidBounce.moduleManager.getModule(ItemPhysics.class);
        ItemStack itemstack = itemIn.func_92059_d();
        Item item = itemstack.func_77973_b();
        if (item == null || itemPhysics == null) {
            return 0;
        }
        boolean flag = p_177077_9_.func_177556_c();
        int i = func_177078_a(itemstack);
        float f1 = (MathHelper.func_76126_a(((itemIn.func_174872_o() + p_177077_8_) / 10.0f) + itemIn.field_70290_d) * 0.1f) + 0.1f;
        if (itemPhysics.getState()) {
            f1 = 0.0f;
        }
        float f2 = p_177077_9_.func_177552_f().func_181688_b(ItemCameraTransforms.TransformType.GROUND).field_178363_d.y;
        GlStateManager.func_179109_b((float) p_177077_2_, ((float) p_177077_4_) + f1 + (0.25f * f2), (float) p_177077_6_);
        if (flag || this.field_76990_c.field_78733_k != null) {
            float f3 = (((itemIn.func_174872_o() + p_177077_8_) / 20.0f) + itemIn.field_70290_d) * 57.295776f;
            if (itemPhysics.getState()) {
                if (itemIn.field_70122_E) {
                    GL11.glRotatef(itemIn.field_70177_z, 0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(itemIn.field_70125_A + 90.0f, 1.0f, 0.0f, 0.0f);
                } else {
                    for (int a = 0; a < 10; a++) {
                        GL11.glRotatef(f3, itemPhysics.getItemWeight().get().floatValue(), itemPhysics.getItemWeight().get().floatValue(), 0.0f);
                    }
                }
            } else {
                GlStateManager.func_179114_b(f3, 0.0f, 1.0f, 0.0f);
            }
        }
        if (!flag) {
            float f6 = (-0.0f) * (i - 1) * 0.5f;
            float f4 = (-0.0f) * (i - 1) * 0.5f;
            float f5 = (-0.046875f) * (i - 1) * 0.5f;
            GlStateManager.func_179109_b(f6, f4, f5);
        }
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        return i;
    }
}
