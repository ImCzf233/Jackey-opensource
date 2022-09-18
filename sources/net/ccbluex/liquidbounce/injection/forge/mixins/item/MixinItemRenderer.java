package net.ccbluex.liquidbounce.injection.forge.mixins.item;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.render.Animations;
import net.ccbluex.liquidbounce.features.module.modules.render.AntiBlind;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ItemRenderer.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/item/MixinItemRenderer.class */
public abstract class MixinItemRenderer {
    float delay = 0.0f;
    MSTimer rotateTimer = new MSTimer();
    @Shadow
    private float field_78451_d;
    @Shadow
    private float field_78454_c;
    @Shadow
    @Final
    private Minecraft field_78455_a;
    @Shadow
    private ItemStack field_78453_b;

    @Shadow
    protected abstract void func_178101_a(float f, float f2);

    @Shadow
    protected abstract void func_178109_a(AbstractClientPlayer abstractClientPlayer);

    @Shadow
    protected abstract void func_178110_a(EntityPlayerSP entityPlayerSP, float f);

    @Shadow
    protected abstract void func_178097_a(AbstractClientPlayer abstractClientPlayer, float f, float f2, float f3);

    @Shadow
    /* renamed from: func_178096_b */
    protected abstract void m2813func_178096_b(float f, float f2);

    @Shadow
    protected abstract void func_178104_a(AbstractClientPlayer abstractClientPlayer, float f);

    @Shadow
    /* renamed from: func_178103_d */
    protected abstract void m2814func_178103_d();

    @Shadow
    protected abstract void func_178098_a(float f, AbstractClientPlayer abstractClientPlayer);

    @Shadow
    protected abstract void func_178105_d(float f);

    @Shadow
    public abstract void func_178099_a(EntityLivingBase entityLivingBase, ItemStack itemStack, ItemCameraTransforms.TransformType transformType);

    @Shadow
    protected abstract void func_178095_a(AbstractClientPlayer abstractClientPlayer, float f, float f2);

    /* renamed from: func_178096_b */
    private void m2813func_178096_b(float p_178096_1_, float p_178096_2_) {
        GlStateManager.func_179109_b(0.56f, -0.52f, -0.71999997f);
        GlStateManager.func_179109_b(0.0f, p_178096_1_ * (-0.6f), 0.0f);
        GlStateManager.func_179114_b(45.0f, 0.0f, 1.0f, 0.0f);
        float var3 = MathHelper.func_76126_a(p_178096_2_ * p_178096_2_ * 3.1415927f);
        float var4 = MathHelper.func_76126_a(MathHelper.func_76129_c(p_178096_2_) * 3.1415927f);
        GlStateManager.func_179114_b(var3 * (-20.0f), 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(var4 * (-20.0f), 0.0f, 0.0f, 1.0f);
        GlStateManager.func_179114_b(var4 * (-80.0f), 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179152_a(Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue());
    }

    private void test(float i, float i2) {
        GlStateManager.func_179109_b(0.56f, -0.52f, -0.71999997f);
        GlStateManager.func_179109_b(0.0f, i * (-0.6f), 0.0f);
        GlStateManager.func_179114_b(45.0f, 0.0f, 1.0f, 0.0f);
        float var3 = MathHelper.func_76126_a(i2 * i2 * 3.1415927f);
        float var4 = MathHelper.func_76126_a(MathHelper.func_76129_c(i2) * 3.1415927f);
        float var5 = MathHelper.func_76123_f(MathHelper.func_76128_c(i2) * 3.1415927f);
        GlStateManager.func_179114_b(var3 * (-20.0f), 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(var4 * (-20.0f), 0.0f, 0.0f, 1.0f);
        GlStateManager.func_179114_b(var5 * (-80.0f), 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179152_a(Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue());
    }

    private void tap2(float var2, float swing) {
        GlStateManager.func_179109_b(0.56f, -0.52f, -0.71999997f);
        float var3 = MathHelper.func_76126_a(swing * swing * 3.1415927f);
        float var4 = MathHelper.func_76126_a(MathHelper.func_76129_c(swing) * 3.1415927f);
        GlStateManager.func_179109_b(0.56f, -0.42f, -0.71999997f);
        GlStateManager.func_179109_b(0.1f * var4, -0.0f, (-0.21999997f) * var4);
        GlStateManager.func_179109_b(0.0f, var2 * (-0.15f), 0.0f);
        GlStateManager.func_179114_b(var3 * 45.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179152_a(Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue());
    }

    private void avatar(float equipProgress, float swingProgress) {
        GlStateManager.func_179109_b(0.56f, -0.52f, -0.71999997f);
        GlStateManager.func_179109_b(0.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(45.0f, 0.0f, 1.0f, 0.0f);
        float f = MathHelper.func_76126_a(swingProgress * swingProgress * 3.1415927f);
        float f2 = MathHelper.func_76126_a(MathHelper.func_76129_c(swingProgress) * 3.1415927f);
        GlStateManager.func_179114_b(f * (-20.0f), 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(f2 * (-20.0f), 0.0f, 0.0f, 1.0f);
        GlStateManager.func_179114_b(f2 * (-40.0f), 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179152_a(Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue());
    }

    private void tap1(float tap1, float tap2) {
        GlStateManager.func_179109_b(0.56f, -0.52f, -0.71999997f);
        GlStateManager.func_179109_b(0.0f, tap1 * (-0.6f), 0.0f);
        GlStateManager.func_179114_b(45.0f, 0.0f, 1.0f, 0.0f);
        float var3 = MathHelper.func_76126_a(tap2 * tap2 * 3.1415927f);
        float var4 = MathHelper.func_76126_a(MathHelper.func_76129_c(tap2) * 3.1415927f);
        GlStateManager.func_179114_b(var3 * (-40.0f), 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(var4 * 0.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.func_179114_b(var4 * 0.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179152_a(Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue());
    }

    private void stab(float var10, float var9) {
        GlStateManager.func_179109_b(0.56f, -0.52f, -0.71999997f);
        GlStateManager.func_179109_b(0.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(45.0f, 0.0f, 1.0f, 0.0f);
        float var11 = MathHelper.func_76126_a(var9 * var9 * 3.1415927f);
        float var12 = MathHelper.func_76126_a(MathHelper.func_76129_c(var9) * 3.1415927f);
        GlStateManager.func_179114_b(var11 * 20.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(var12 * 0.0f, 0.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(var12 * (-10.0f), 1.0f, 0.0f, -4.0f);
        GlStateManager.func_179152_a(Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue());
    }

    private void slide(float var10, float var9) {
        GlStateManager.func_179109_b(0.56f, -0.52f, -0.71999997f);
        GlStateManager.func_179109_b(0.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(45.0f, 0.0f, 1.0f, 0.0f);
        float var11 = MathHelper.func_76126_a(var9 * var9 * 3.1415927f);
        float var12 = MathHelper.func_76126_a(MathHelper.func_76129_c(var9) * 3.1415927f);
        GlStateManager.func_179114_b(var11 * 0.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(var12 * 0.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.func_179114_b(var12 * (-40.0f), 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179152_a(Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue());
    }

    private void slide2(float var10, float var9) {
        GlStateManager.func_179109_b(0.56f, -0.52f, -0.71999997f);
        GlStateManager.func_179109_b(0.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(45.0f, 0.0f, 1.0f, 0.0f);
        float var11 = MathHelper.func_76126_a(var9 * var9 * 3.1415927f);
        float var12 = MathHelper.func_76126_a(MathHelper.func_76129_c(var9) * 3.1415927f);
        GlStateManager.func_179114_b(var11 * 0.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(var12 * 0.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.func_179114_b(var12 * (-80.0f), 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179152_a(Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue());
    }

    private void jello(float var11, float var12) {
        GlStateManager.func_179109_b(0.56f, -0.52f, -0.71999997f);
        GlStateManager.func_179114_b(48.57f, 0.0f, 0.24f, 0.14f);
        float var13 = MathHelper.func_76126_a(var12 * var12 * 3.1415927f);
        float var14 = MathHelper.func_76126_a(MathHelper.func_76129_c(var12) * 3.1415927f);
        GlStateManager.func_179114_b(var13 * (-35.0f), 0.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(var14 * 0.0f, 0.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(var14 * 20.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.func_179152_a(Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue());
    }

    private void continuity(float var11, float var10) {
        GlStateManager.func_179109_b(0.56f, -0.52f, -0.71999997f);
        GlStateManager.func_179109_b(0.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(45.0f, 0.0f, 1.0f, 0.0f);
        float var12 = -MathHelper.func_76126_a(var10 * var10 * 3.1415927f);
        float var13 = MathHelper.func_76134_b(MathHelper.func_76129_c(var10) * 3.1415927f);
        float var14 = MathHelper.func_76135_e(MathHelper.func_76129_c(var11) * 3.1415927f);
        GlStateManager.func_179114_b(var12 * var14 * 30.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(var13 * 0.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.func_179114_b(var13 * 20.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179152_a(Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue());
    }

    private void poke(float var5, float var6) {
        GlStateManager.func_179109_b(0.56f, -0.52f, -0.71999997f);
        GlStateManager.func_179114_b(45.0f, 0.0f, 1.0f, 0.0f);
        float var7 = MathHelper.func_76126_a(var6 * var6 * 3.1415927f);
        float var8 = MathHelper.func_76126_a(MathHelper.func_76129_c(var6) * 3.1415927f);
        GlStateManager.func_179109_b(0.56f, -0.42f, -0.71999997f);
        GlStateManager.func_179109_b(0.1f * var8, -0.0f, (-0.21999997f) * var8);
        GlStateManager.func_179109_b(0.0f, var5 * (-0.15f), 0.0f);
        GlStateManager.func_179114_b(var7 * 0.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179152_a(Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue());
    }

    private void Zoom(float p_178096_1_, float p_178096_2_) {
        GlStateManager.func_179109_b(0.56f, -0.52f, -0.71999997f);
        GlStateManager.func_179109_b(0.0f, p_178096_1_ * (-0.6f), 0.0f);
        GlStateManager.func_179114_b(45.0f, 0.0f, 1.0f, 0.0f);
        float var3 = MathHelper.func_76126_a(p_178096_2_ * p_178096_2_ * 3.1415927f);
        float var4 = MathHelper.func_76126_a(MathHelper.func_76129_c(p_178096_2_) * 3.1415927f);
        GlStateManager.func_179114_b(var3 * (-20.0f), 0.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(var4 * (-20.0f), 0.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(var4 * (-20.0f), 0.0f, 0.0f, 0.0f);
        GlStateManager.func_179152_a(Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue());
    }

    private void strange(float lul, float lol) {
        GlStateManager.func_179109_b(0.56f, -0.52f, -0.71999997f);
        GlStateManager.func_179114_b(45.0f, 0.0f, 1.0f, 0.0f);
        float var26 = MathHelper.func_76126_a(lol * lul * 3.1415927f);
        float var27 = MathHelper.func_76134_b(MathHelper.func_76133_a(lul) * 3.1415927f);
        float var28 = MathHelper.func_76135_e(MathHelper.func_76129_c(lul) * 3.1415927f);
        GlStateManager.func_179114_b(var26 * var27, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(var28 * 15.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.func_179114_b(var27 * 10.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179152_a(Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue());
    }

    private void move(float test1, float test2) {
        GlStateManager.func_179109_b(0.56f, -0.52f, -0.71999997f);
        GlStateManager.func_179114_b(45.0f, 0.0f, 1.0f, 0.0f);
        float var30 = MathHelper.func_76126_a(test2 * MathHelper.func_76129_c(test1) * 3.1415927f);
        float var31 = MathHelper.func_76134_b(MathHelper.func_76129_c(test2) * 3.1415927f);
        float var29 = -MathHelper.func_76135_e(MathHelper.func_76129_c(test1) * test2 * 3.1415927f);
        GlStateManager.func_179114_b(var30 * var29 * (-90.0f), 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(var29 * var31 * 5.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.func_179114_b(var31 * 5.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179152_a(Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue());
    }

    private void ETB(float equipProgress, float swingProgress) {
        GlStateManager.func_179109_b(0.56f, -0.52f, -0.71999997f);
        GlStateManager.func_179109_b(0.0f, equipProgress * (-0.6f), 0.0f);
        GlStateManager.func_179114_b(45.0f, 0.0f, 1.0f, 0.0f);
        float var3 = MathHelper.func_76126_a(swingProgress * swingProgress * 3.1415927f);
        float var4 = MathHelper.func_76126_a(MathHelper.func_76129_c(swingProgress) * 3.1415927f);
        GlStateManager.func_179114_b(var3 * (-34.0f), 0.0f, 1.0f, 0.2f);
        GlStateManager.func_179114_b(var4 * (-20.7f), 0.2f, 0.1f, 1.0f);
        GlStateManager.func_179114_b(var4 * (-68.6f), 1.3f, 0.1f, 0.2f);
        GlStateManager.func_179152_a(Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue());
    }

    private void sigmaold(float p_178096_1_, float p_178096_2_) {
        GlStateManager.func_179109_b(0.56f, -0.52f, -0.71999997f);
        GlStateManager.func_179109_b(0.0f, p_178096_1_ * (-0.6f), 0.0f);
        GlStateManager.func_179114_b(25.0f, 0.0f, 1.0f, 0.0f);
        float var3 = MathHelper.func_76126_a(p_178096_2_ * p_178096_2_ * 3.1415927f);
        float var4 = MathHelper.func_76126_a(MathHelper.func_76129_c(p_178096_2_) * 3.1415927f);
        GlStateManager.func_179114_b(var3 * (-15.0f), 0.0f, 1.0f, 0.2f);
        GlStateManager.func_179114_b(var4 * (-10.0f), 0.2f, 0.1f, 1.0f);
        GlStateManager.func_179114_b(var4 * (-30.0f), 1.3f, 0.1f, 0.2f);
        GlStateManager.func_179152_a(Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue());
    }

    private void push1(float idk, float idc) {
        GlStateManager.func_179109_b(0.56f, -0.52f, -0.71999997f);
        GlStateManager.func_179109_b(0.0f, idk * (-0.6f), 0.0f);
        GlStateManager.func_179114_b(45.0f, 0.0f, 1.0f, 0.0f);
        float var3 = MathHelper.func_76126_a(idc * idc * 3.1415927f);
        float var4 = MathHelper.func_76126_a(MathHelper.func_76129_c(idc) * 3.1415927f);
        GlStateManager.func_179114_b(var3 * (-10.0f), 1.0f, 1.0f, 1.0f);
        GlStateManager.func_179114_b(var4 * (-10.0f), 1.0f, 1.0f, 1.0f);
        GlStateManager.func_179114_b(var4 * (-10.0f), 1.0f, 1.0f, 1.0f);
        GlStateManager.func_179152_a(Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue());
    }

    private void push2(float idk, float idc) {
        GlStateManager.func_179109_b(0.56f, -0.52f, -0.71999997f);
        GlStateManager.func_179109_b(0.0f, idk * (-0.6f), 0.0f);
        GlStateManager.func_179114_b(45.0f, 0.0f, 1.0f, 0.0f);
        float var3 = MathHelper.func_76126_a(idc * idc * 3.1415927f);
        float var4 = MathHelper.func_76126_a(MathHelper.func_76129_c(idc) * 3.1415927f);
        GlStateManager.func_179114_b(var3 * (-10.0f), 2.0f, 2.0f, 2.0f);
        GlStateManager.func_179114_b(var4 * (-10.0f), 2.0f, 2.0f, 0.0f);
        GlStateManager.func_179114_b(var4 * (-10.0f), 2.0f, 2.0f, 0.0f);
        GlStateManager.func_179152_a(Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue());
    }

    /* renamed from: up */
    private void m32up(float idk, float idc) {
        GlStateManager.func_179109_b(0.56f, -0.52f, -0.71999997f);
        GlStateManager.func_179109_b(0.0f, idk * (-0.6f), 0.0f);
        GlStateManager.func_179114_b(45.0f, 0.0f, 1.0f, 0.0f);
        float var3 = MathHelper.func_76126_a(idc * idc * 3.1415927f);
        float var4 = MathHelper.func_76126_a(MathHelper.func_76129_c(idc) * 3.1415927f);
        GlStateManager.func_179114_b(var3 * (-20.0f), 0.0f, 1.0f, 1.0f);
        GlStateManager.func_179114_b(var4 * (-10.0f), 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(var3 * (-20.0f), 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(var4 * (-10.0f), 1.0f, 0.0f, 1.0f);
        GlStateManager.func_179152_a(Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue(), Animations.Scale.get().floatValue());
    }

    /* renamed from: func_178103_d */
    private void m2814func_178103_d() {
        GlStateManager.func_179109_b(-0.5f, 0.2f, 0.0f);
        GlStateManager.func_179114_b(30.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(-80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(60.0f, 0.0f, 1.0f, 0.0f);
    }

    @Overwrite
    public void func_78440_a(float partialTicks) {
        float f = 1.0f - (this.field_78451_d + ((this.field_78454_c - this.field_78451_d) * partialTicks));
        EntityPlayerSP entityPlayerSP = this.field_78455_a.field_71439_g;
        float f1 = entityPlayerSP.func_70678_g(partialTicks);
        float f2 = ((AbstractClientPlayer) entityPlayerSP).field_70127_C + ((((AbstractClientPlayer) entityPlayerSP).field_70125_A - ((AbstractClientPlayer) entityPlayerSP).field_70127_C) * partialTicks);
        float f3 = ((AbstractClientPlayer) entityPlayerSP).field_70126_B + ((((AbstractClientPlayer) entityPlayerSP).field_70177_z - ((AbstractClientPlayer) entityPlayerSP).field_70126_B) * partialTicks);
        if (LiquidBounce.moduleManager.getModule(Animations.class).getState()) {
            GL11.glTranslated(Animations.itemPosX.get().doubleValue(), Animations.itemPosY.get().doubleValue(), Animations.itemPosZ.get().doubleValue());
        }
        func_178101_a(f2, f3);
        func_178109_a(entityPlayerSP);
        func_178110_a(entityPlayerSP, partialTicks);
        GlStateManager.func_179091_B();
        GlStateManager.func_179094_E();
        if (LiquidBounce.moduleManager.getModule(Animations.class).getState()) {
            GL11.glTranslated(Animations.itemPosX.get().doubleValue(), Animations.itemPosY.get().doubleValue(), Animations.itemPosZ.get().doubleValue());
        }
        if (this.field_78453_b != null) {
            KillAura killAura = (KillAura) LiquidBounce.moduleManager.getModule(KillAura.class);
            boolean canBlockEverything = LiquidBounce.moduleManager.getModule(Animations.class).getState() && Animations.blockEverything.get().booleanValue() && killAura.getTarget() != null && ((this.field_78453_b.func_77973_b() instanceof ItemBucketMilk) || (this.field_78453_b.func_77973_b() instanceof ItemFood) || (this.field_78453_b.func_77973_b() instanceof ItemPotion) || (this.field_78453_b.func_77973_b() instanceof ItemAxe) || this.field_78453_b.func_77973_b().equals(Items.field_151055_y));
            if (this.field_78453_b.func_77973_b() instanceof ItemMap) {
                func_178097_a(entityPlayerSP, f2, f, f1);
            } else if (entityPlayerSP.func_71052_bv() > 0 || (((this.field_78453_b.func_77973_b() instanceof ItemSword) && (killAura.getBlockingStatus() || killAura.getFakeBlock())) || (((this.field_78453_b.func_77973_b() instanceof ItemSword) && LiquidBounce.moduleManager.getModule(Animations.class).getState() && Animations.fakeBlock.get().booleanValue() && killAura.getTarget() != null) || canBlockEverything))) {
                EnumAction enumaction = (killAura.getBlockingStatus() || canBlockEverything) ? EnumAction.BLOCK : this.field_78453_b.func_77975_n();
                switch (C16851.$SwitchMap$net$minecraft$item$EnumAction[enumaction.ordinal()]) {
                    case 1:
                        m2813func_178096_b(f, 0.0f);
                        break;
                    case 2:
                    case 3:
                        func_178104_a(entityPlayerSP, partialTicks);
                        m2813func_178096_b(f, f1);
                        if (LiquidBounce.moduleManager.getModule(Animations.class).getState() && Animations.RotateItems.get().booleanValue()) {
                            rotateItemAnim();
                            break;
                        }
                        break;
                    case 4:
                        if (LiquidBounce.moduleManager.getModule(Animations.class).getState()) {
                            GL11.glTranslated(Animations.blockPosX.get().doubleValue(), Animations.blockPosY.get().doubleValue(), Animations.blockPosZ.get().doubleValue());
                            String z = Animations.Sword.get();
                            boolean z2 = true;
                            switch (z.hashCode()) {
                                case -1955878649:
                                    if (z.equals("Normal")) {
                                        z2 = false;
                                        break;
                                    }
                                    break;
                                case -1819473015:
                                    if (z.equals("Shield")) {
                                        z2 = true;
                                        break;
                                    }
                                    break;
                                case -1595926131:
                                    if (z.equals("Minecraft")) {
                                        z2 = true;
                                        break;
                                    }
                                    break;
                                case -1530467646:
                                    if (z.equals("Reverse")) {
                                        z2 = true;
                                        break;
                                    }
                                    break;
                                case -1236990786:
                                    if (z.equals("SlideDown1")) {
                                        z2 = true;
                                        break;
                                    }
                                    break;
                                case -1236990785:
                                    if (z.equals("SlideDown2")) {
                                        z2 = true;
                                        break;
                                    }
                                    break;
                                case -217344164:
                                    if (z.equals("Strange")) {
                                        z2 = true;
                                        break;
                                    }
                                    break;
                                case 2747:
                                    if (z.equals("Up")) {
                                        z2 = true;
                                        break;
                                    }
                                    break;
                                case 68979:
                                    if (z.equals("ETB")) {
                                        z2 = true;
                                        break;
                                    }
                                    break;
                                case 2404337:
                                    if (z.equals("Move")) {
                                        z2 = true;
                                        break;
                                    }
                                    break;
                                case 2493369:
                                    if (z.equals("Poke")) {
                                        z2 = true;
                                        break;
                                    }
                                    break;
                                case 2587234:
                                    if (z.equals("Stab")) {
                                        z2 = true;
                                        break;
                                    }
                                    break;
                                case 2599182:
                                    if (z.equals("Tap1")) {
                                        z2 = true;
                                        break;
                                    }
                                    break;
                                case 2599183:
                                    if (z.equals("Tap2")) {
                                        z2 = true;
                                        break;
                                    }
                                    break;
                                case 2791411:
                                    if (z.equals("Zoom")) {
                                        z2 = true;
                                        break;
                                    }
                                    break;
                                case 71456692:
                                    if (z.equals("Jello")) {
                                        z2 = true;
                                        break;
                                    }
                                    break;
                                case 73771720:
                                    if (z.equals("Lucky")) {
                                        z2 = true;
                                        break;
                                    }
                                    break;
                                case 77481015:
                                    if (z.equals("Push1")) {
                                        z2 = true;
                                        break;
                                    }
                                    break;
                                case 77481016:
                                    if (z.equals("Push2")) {
                                        z2 = true;
                                        break;
                                    }
                                    break;
                                case 78845737:
                                    if (z.equals("Remix")) {
                                        z2 = true;
                                        break;
                                    }
                                    break;
                                case 79973777:
                                    if (z.equals("Slide")) {
                                        z2 = true;
                                        break;
                                    }
                                    break;
                                case 80307556:
                                    if (z.equals("Swong")) {
                                        z2 = true;
                                        break;
                                    }
                                    break;
                                case 375411170:
                                    if (z.equals("SigmaOld")) {
                                        z2 = true;
                                        break;
                                    }
                                    break;
                                case 754468562:
                                    if (z.equals("Rotate360")) {
                                        z2 = true;
                                        break;
                                    }
                                    break;
                                case 1267843374:
                                    if (z.equals("SmoothFloat")) {
                                        z2 = true;
                                        break;
                                    }
                                    break;
                                case 1649176282:
                                    if (z.equals("VisionFX")) {
                                        z2 = true;
                                        break;
                                    }
                                    break;
                                case 1963211882:
                                    if (z.equals("Akrien")) {
                                        z2 = true;
                                        break;
                                    }
                                    break;
                                case 1972874617:
                                    if (z.equals("Avatar")) {
                                        z2 = true;
                                        break;
                                    }
                                    break;
                            }
                            switch (z2) {
                                case false:
                                    m2813func_178096_b(f + 0.1f, f1);
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                    }
                                    m2814func_178103_d();
                                    GlStateManager.func_179109_b(-0.5f, 0.2f, 0.0f);
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                        break;
                                    }
                                    break;
                                case true:
                                    m2813func_178096_b(0.2f, f1);
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                    }
                                    m2814func_178103_d();
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                        break;
                                    }
                                    break;
                                case true:
                                    slide2(0.1f, f1);
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                    }
                                    m2814func_178103_d();
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                        break;
                                    }
                                    break;
                                case true:
                                    m2813func_178096_b(f, Animations.mcSwordPos.get().floatValue());
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                    }
                                    m2814func_178103_d();
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                        break;
                                    }
                                    break;
                                case true:
                                    m2813func_178096_b(f, f1);
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                    }
                                    m2814func_178103_d();
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                        break;
                                    }
                                    break;
                                case true:
                                    avatar(f, f1);
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                    }
                                    m2814func_178103_d();
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                        break;
                                    }
                                    break;
                                case true:
                                    tap2(0.0f, f1);
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                    }
                                    GlStateManager.func_179152_a(2.0f, 2.0f, 2.0f);
                                    m2814func_178103_d();
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                        break;
                                    }
                                    break;
                                case true:
                                    poke(0.1f, f1);
                                    GlStateManager.func_179152_a(2.5f, 2.5f, 2.5f);
                                    GL11.glTranslated(1.2d, -0.5d, 0.5d);
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                    }
                                    m2814func_178103_d();
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                        break;
                                    }
                                    break;
                                case true:
                                    slide(0.1f, f1);
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                    }
                                    m2814func_178103_d();
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                        break;
                                    }
                                    break;
                                case true:
                                    push1(0.1f, f1);
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                    }
                                    m2814func_178103_d();
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                        break;
                                    }
                                    break;
                                case true:
                                    m32up(f, f1);
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                    }
                                    m2814func_178103_d();
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                        break;
                                    }
                                    break;
                                case true:
                                    jello(0.0f, f1);
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                    }
                                    m2814func_178103_d();
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                        break;
                                    }
                                    break;
                                case true:
                                    m2813func_178096_b(f1, 0.0f);
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                    }
                                    m2814func_178103_d();
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                        break;
                                    }
                                    break;
                                case true:
                                    continuity(0.1f, f1);
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                    }
                                    m2814func_178103_d();
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                        break;
                                    }
                                    break;
                                case true:
                                    strange(f1 + 0.2f, 0.1f);
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                    }
                                    m2814func_178103_d();
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                        break;
                                    }
                                    break;
                                case true:
                                    move(-0.3f, f1);
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                    }
                                    m2814func_178103_d();
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                        break;
                                    }
                                    break;
                                case true:
                                    ETB(f, f1);
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                    }
                                    m2814func_178103_d();
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                        break;
                                    }
                                    break;
                                case true:
                                    m2813func_178096_b(f / 2.0f, 0.0f);
                                    GlStateManager.func_179114_b(((-MathHelper.func_76126_a(MathHelper.func_76129_c(this.field_78455_a.field_71439_g.func_70678_g(partialTicks)) * 3.1415927f)) * 40.0f) / 2.0f, MathHelper.func_76129_c(this.field_78455_a.field_71439_g.func_70678_g(partialTicks)) / 2.0f, -0.0f, 9.0f);
                                    GlStateManager.func_179114_b((-MathHelper.func_76129_c(this.field_78455_a.field_71439_g.func_70678_g(partialTicks))) * 30.0f, 1.0f, MathHelper.func_76129_c(this.field_78455_a.field_71439_g.func_70678_g(partialTicks)) / 2.0f, -0.0f);
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                    }
                                    m2814func_178103_d();
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                        break;
                                    }
                                    break;
                                case true:
                                    float var15 = MathHelper.func_76126_a(MathHelper.func_76129_c(f1) * 3.1415927f);
                                    sigmaold(f * 0.5f, 0.0f);
                                    GlStateManager.func_179114_b(((-var15) * 55.0f) / 2.0f, -8.0f, -0.0f, 9.0f);
                                    GlStateManager.func_179114_b((-var15) * 45.0f, 1.0f, var15 / 2.0f, -0.0f);
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                    }
                                    m2814func_178103_d();
                                    GL11.glTranslated(1.2d, 0.3d, 0.5d);
                                    GL11.glTranslatef(-1.0f, this.field_78455_a.field_71439_g.func_70093_af() ? -0.1f : -0.2f, 0.2f);
                                    GlStateManager.func_179152_a(1.2f, 1.2f, 1.2f);
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                        break;
                                    }
                                    break;
                                case true:
                                    m2813func_178096_b(0.0f, 0.95f);
                                    GlStateManager.func_179114_b(this.delay, 1.0f, 0.0f, 2.0f);
                                    if (this.rotateTimer.hasTimePassed(1L)) {
                                        this.delay += 1.0f;
                                        this.delay += Animations.SpeedRotate.get().floatValue();
                                        this.rotateTimer.reset();
                                    }
                                    if (this.delay > 360.0f) {
                                        this.delay = 0.0f;
                                    }
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                    }
                                    m2814func_178103_d();
                                    GlStateManager.func_179114_b(this.delay, 0.0f, 1.0f, 0.0f);
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                        break;
                                    }
                                    break;
                                case true:
                                    m2813func_178096_b(0.0f, 0.95f);
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                    }
                                    m2814func_178103_d();
                                    GlStateManager.func_179114_b(this.delay, 1.0f, 0.0f, 2.0f);
                                    if (this.rotateTimer.hasTimePassed(1L)) {
                                        this.delay += 1.0f;
                                        this.delay += Animations.SpeedRotate.get().floatValue();
                                        this.rotateTimer.reset();
                                    }
                                    if (this.delay > 360.0f) {
                                        this.delay = 0.0f;
                                    }
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                        break;
                                    }
                                    break;
                                case true:
                                    m2813func_178096_b(f, f1);
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                    }
                                    m2814func_178103_d();
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                        break;
                                    }
                                    break;
                                case true:
                                    Zoom(0.0f, f1);
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                    }
                                    m2814func_178103_d();
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                        break;
                                    }
                                    break;
                                case true:
                                    test(f, f1);
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                    }
                                    m2814func_178103_d();
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                        break;
                                    }
                                    break;
                                case true:
                                    tap1(f, f1);
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                    }
                                    m2814func_178103_d();
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                        break;
                                    }
                                    break;
                                case true:
                                    stab(0.1f, f1);
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                    }
                                    m2814func_178103_d();
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                        break;
                                    }
                                    break;
                                case true:
                                    push2(0.1f, f1);
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                    }
                                    m2814func_178103_d();
                                    if (Animations.RotateItems.get().booleanValue()) {
                                        rotateItemAnim();
                                        break;
                                    }
                                    break;
                                case true:
                                    m2813func_178096_b(0.0f, 0.0f);
                                    m2814func_178103_d();
                                    int alpha = (int) Math.min(255L, (System.currentTimeMillis() % 255 > 127 ? Math.abs((Math.abs(System.currentTimeMillis()) % 255) - 255) : System.currentTimeMillis() % 255) * 2);
                                    float f5 = ((double) f1) > 0.5d ? 1.0f - f1 : f1;
                                    GlStateManager.func_179109_b(0.3f, -0.0f, 0.4f);
                                    GlStateManager.func_179114_b(0.0f, 0.0f, 0.0f, 1.0f);
                                    GlStateManager.func_179109_b(0.0f, 0.5f, 0.0f);
                                    GlStateManager.func_179114_b(90.0f, 1.0f, 0.0f, -1.0f);
                                    GlStateManager.func_179109_b(0.6f, 0.5f, 0.0f);
                                    GlStateManager.func_179114_b(-90.0f, 1.0f, 0.0f, -1.0f);
                                    GlStateManager.func_179114_b(-10.0f, 1.0f, 0.0f, -1.0f);
                                    GlStateManager.func_179114_b((-f5) * 10.0f, 10.0f, 10.0f, -9.0f);
                                    GlStateManager.func_179114_b(10.0f, -1.0f, 0.0f, 0.0f);
                                    GlStateManager.func_179137_b(0.0d, 0.0d, -0.5d);
                                    GlStateManager.func_179114_b(this.field_78455_a.field_71439_g.field_82175_bq ? (-alpha) / 5.0f : 1.0f, 1.0f, -0.0f, 1.0f);
                                    GlStateManager.func_179137_b(0.0d, 0.0d, 0.5d);
                                    break;
                            }
                        } else {
                            m2813func_178096_b(f + 0.1f, f1);
                            m2814func_178103_d();
                            GlStateManager.func_179109_b(-0.5f, 0.2f, 0.0f);
                            break;
                        }
                        break;
                    case 5:
                        m2813func_178096_b(f, f1);
                        if (LiquidBounce.moduleManager.getModule(Animations.class).getState() && Animations.RotateItems.get().booleanValue()) {
                            rotateItemAnim();
                        }
                        func_178098_a(partialTicks, entityPlayerSP);
                        if (LiquidBounce.moduleManager.getModule(Animations.class).getState() && Animations.RotateItems.get().booleanValue()) {
                            rotateItemAnim();
                            break;
                        }
                        break;
                }
            } else {
                func_178105_d(f1);
                m2813func_178096_b(f, f1);
                if (LiquidBounce.moduleManager.getModule(Animations.class).getState() && Animations.RotateItems.get().booleanValue()) {
                    rotateItemAnim();
                }
            }
            func_178099_a(entityPlayerSP, this.field_78453_b, ItemCameraTransforms.TransformType.FIRST_PERSON);
        } else if (!entityPlayerSP.func_82150_aj()) {
            func_178095_a(entityPlayerSP, f, f1);
        }
        GlStateManager.func_179121_F();
        GlStateManager.func_179101_C();
        RenderHelper.func_74518_a();
        if (LiquidBounce.moduleManager.getModule(Animations.class).getState()) {
            GL11.glTranslated(-Animations.itemPosX.get().doubleValue(), -Animations.itemPosY.get().doubleValue(), -Animations.itemPosZ.get().doubleValue());
        }
    }

    /* renamed from: net.ccbluex.liquidbounce.injection.forge.mixins.item.MixinItemRenderer$1 */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/item/MixinItemRenderer$1.class */
    static /* synthetic */ class C16851 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$item$EnumAction = new int[EnumAction.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$item$EnumAction[EnumAction.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$item$EnumAction[EnumAction.EAT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$item$EnumAction[EnumAction.DRINK.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$item$EnumAction[EnumAction.BLOCK.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$item$EnumAction[EnumAction.BOW.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    private void rotateItemAnim() {
        if (Animations.transformFirstPersonRotate.get().equalsIgnoreCase("RotateY")) {
            GlStateManager.func_179114_b(this.delay, 0.0f, 1.0f, 0.0f);
        }
        if (Animations.transformFirstPersonRotate.get().equalsIgnoreCase("RotateXY")) {
            GlStateManager.func_179114_b(this.delay, 1.0f, 1.0f, 0.0f);
        }
        if (Animations.transformFirstPersonRotate.get().equalsIgnoreCase("Custom")) {
            GlStateManager.func_179114_b(this.delay, Animations.customRotate1.get().floatValue(), Animations.customRotate2.get().floatValue(), Animations.customRotate3.get().floatValue());
        }
        if (this.rotateTimer.hasTimePassed(1L)) {
            this.delay += 1.0f;
            this.delay += Animations.SpeedRotate.get().floatValue();
            this.rotateTimer.reset();
        }
        if (this.delay > 360.0f) {
            this.delay = 0.0f;
        }
    }

    @Inject(method = {"renderFireInFirstPerson"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void renderFireInFirstPerson(CallbackInfo callbackInfo) {
        AntiBlind antiBlind = (AntiBlind) LiquidBounce.moduleManager.getModule(AntiBlind.class);
        if (antiBlind.getState() && antiBlind.getFireEffect().get().booleanValue()) {
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 0.9f);
            GlStateManager.func_179143_c(519);
            GlStateManager.func_179132_a(false);
            GlStateManager.func_179147_l();
            GlStateManager.func_179120_a(770, 771, 1, 0);
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.func_179084_k();
            GlStateManager.func_179132_a(true);
            GlStateManager.func_179143_c(515);
            callbackInfo.cancel();
        }
    }
}
