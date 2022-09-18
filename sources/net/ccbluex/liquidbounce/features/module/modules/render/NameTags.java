package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiBot;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.p004ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Timer;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

/* compiled from: NameTags.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0007J\u0018\u0010 \u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0010\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0014\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0015\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0018\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0019\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n��¨\u0006%"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/NameTags;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "armorValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "backgroundColorAlphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "backgroundColorBlueValue", "backgroundColorGreenValue", "backgroundColorRedValue", "borderColorAlphaValue", "borderColorBlueValue", "borderColorGreenValue", "borderColorRedValue", "borderValue", "clearNamesValue", "distanceValue", "fontShadowValue", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "healthBarValue", "healthValue", "inventoryBackground", "Lnet/minecraft/util/ResourceLocation;", "pingValue", "potionValue", "scaleValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "renderNameTag", "entity", "Lnet/minecraft/entity/EntityLivingBase;", "tag", "", "LiquidBounce"})
@ModuleInfo(name = "NameTags", spacedName = "Name Tags", description = "Changes the scale of the nametags so you can always read them.", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/NameTags.class */
public final class NameTags extends Module {
    @NotNull
    private final FontValue fontValue;
    @NotNull
    private final BoolValue healthValue = new BoolValue("Health", true);
    @NotNull
    private final BoolValue healthBarValue = new BoolValue("Bar", true);
    @NotNull
    private final BoolValue pingValue = new BoolValue("Ping", true);
    @NotNull
    private final BoolValue distanceValue = new BoolValue("Distance", false);
    @NotNull
    private final BoolValue armorValue = new BoolValue("Armor", true);
    @NotNull
    private final BoolValue potionValue = new BoolValue("Potions", true);
    @NotNull
    private final BoolValue clearNamesValue = new BoolValue("ClearNames", false);
    @NotNull
    private final BoolValue fontShadowValue = new BoolValue("Shadow", true);
    @NotNull
    private final BoolValue borderValue = new BoolValue("Border", true);
    @NotNull
    private final IntegerValue backgroundColorRedValue = new IntegerValue("Background-R", 0, 0, 255);
    @NotNull
    private final IntegerValue backgroundColorGreenValue = new IntegerValue("Background-G", 0, 0, 255);
    @NotNull
    private final IntegerValue backgroundColorBlueValue = new IntegerValue("Background-B", 0, 0, 255);
    @NotNull
    private final IntegerValue backgroundColorAlphaValue = new IntegerValue("Background-Alpha", 0, 0, 255);
    @NotNull
    private final IntegerValue borderColorRedValue = new IntegerValue("Border-R", 0, 0, 255);
    @NotNull
    private final IntegerValue borderColorGreenValue = new IntegerValue("Border-G", 0, 0, 255);
    @NotNull
    private final IntegerValue borderColorBlueValue = new IntegerValue("Border-B", 0, 0, 255);
    @NotNull
    private final IntegerValue borderColorAlphaValue = new IntegerValue("Border-Alpha", 0, 0, 255);
    @NotNull
    private final FloatValue scaleValue = new FloatValue("Scale", 1.0f, 1.0f, 4.0f, "x");
    @NotNull
    private final ResourceLocation inventoryBackground = new ResourceLocation("textures/gui/container/inventory.png");

    public NameTags() {
        GameFontRenderer font40 = Fonts.font40;
        Intrinsics.checkNotNullExpressionValue(font40, "font40");
        this.fontValue = new FontValue("Font", font40);
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        String func_150260_c;
        Intrinsics.checkNotNullParameter(event, "event");
        GL11.glPushAttrib(8192);
        GL11.glPushMatrix();
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        GL11.glEnable(2848);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        for (Entity entity : MinecraftInstance.f362mc.field_71441_e.field_72996_f) {
            if (EntityUtils.isSelected(entity, false)) {
                if (entity == null) {
                    throw new NullPointerException("null cannot be cast to non-null type net.minecraft.entity.EntityLivingBase");
                }
                EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
                if (this.clearNamesValue.get().booleanValue()) {
                    func_150260_c = ColorUtils.stripColor(entity.func_145748_c_().func_150260_c());
                    if (func_150260_c == null) {
                    }
                } else {
                    func_150260_c = entity.func_145748_c_().func_150260_c();
                }
                String str = func_150260_c;
                Intrinsics.checkNotNullExpressionValue(str, "if (clearNamesValue.get(…layName().unformattedText");
                renderNameTag(entityLivingBase, str);
            }
        }
        GL11.glPopMatrix();
        GL11.glPopAttrib();
        GlStateManager.func_179117_G();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private final void renderNameTag(EntityLivingBase entity, String tag) {
        FontRenderer fontRenderer = this.fontValue.get();
        boolean bot = AntiBot.isBot(entity);
        String nameColor = bot ? "§3" : entity.func_82150_aj() ? "§6" : entity.func_70093_af() ? "§4" : "§7";
        int ping = entity instanceof EntityPlayer ? EntityUtils.getPing((EntityPlayer) entity) : 0;
        String distanceText = this.distanceValue.get().booleanValue() ? "§7 [§a" + MathKt.roundToInt(MinecraftInstance.f362mc.field_71439_g.func_70032_d((Entity) entity)) + "§7]" : "";
        String pingText = (!this.pingValue.get().booleanValue() || !(entity instanceof EntityPlayer)) ? "" : " §7[" + (ping > 200 ? "§c" : ping > 100 ? "§e" : "§a") + ping + "ms§7]";
        String healthText = this.healthValue.get().booleanValue() ? "§7 [§f" + ((int) entity.func_110143_aJ()) + "§c❤§7]" : "";
        String botText = bot ? " §7[§6§lBot§7]" : "";
        String text = nameColor + tag + healthText + distanceText + pingText + botText;
        GL11.glPushMatrix();
        Timer timer = MinecraftInstance.f362mc.field_71428_T;
        RenderManager renderManager = MinecraftInstance.f362mc.func_175598_ae();
        GL11.glTranslated((entity.field_70142_S + ((entity.field_70165_t - entity.field_70142_S) * timer.field_74281_c)) - renderManager.field_78725_b, ((entity.field_70137_T + ((entity.field_70163_u - entity.field_70137_T) * timer.field_74281_c)) - renderManager.field_78726_c) + entity.func_70047_e() + 0.55d, (entity.field_70136_U + ((entity.field_70161_v - entity.field_70136_U) * timer.field_74281_c)) - renderManager.field_78723_d);
        GL11.glRotatef(-MinecraftInstance.f362mc.func_175598_ae().field_78735_i, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(MinecraftInstance.f362mc.func_175598_ae().field_78732_j, 1.0f, 0.0f, 0.0f);
        float distance = MinecraftInstance.f362mc.field_71439_g.func_70032_d((Entity) entity) * 0.25f;
        if (distance < 1.0f) {
            distance = 1.0f;
        }
        float scale = (distance / 100.0f) * this.scaleValue.get().floatValue();
        GL11.glScalef(-scale, -scale, scale);
        float width = fontRenderer.func_78256_a(text) * 0.5f;
        float dist = (width + 4.0f) - ((-width) - 2.0f);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        Color bgColor = new Color(this.backgroundColorRedValue.get().intValue(), this.backgroundColorGreenValue.get().intValue(), this.backgroundColorBlueValue.get().intValue(), this.backgroundColorAlphaValue.get().intValue());
        Color borderColor = new Color(this.borderColorRedValue.get().intValue(), this.borderColorGreenValue.get().intValue(), this.borderColorBlueValue.get().intValue(), this.borderColorAlphaValue.get().intValue());
        if (this.borderValue.get().booleanValue()) {
            RenderUtils.quickDrawBorderedRect((-width) - 2.0f, -2.0f, width + 4.0f, fontRenderer.field_78288_b + 2.0f + (this.healthBarValue.get().booleanValue() ? 2.0f : 0.0f), 2.0f, borderColor.getRGB(), bgColor.getRGB());
        } else {
            RenderUtils.quickDrawRect((-width) - 2.0f, -2.0f, width + 4.0f, fontRenderer.field_78288_b + 2.0f + (this.healthBarValue.get().booleanValue() ? 2.0f : 0.0f), bgColor.getRGB());
        }
        if (this.healthBarValue.get().booleanValue()) {
            RenderUtils.quickDrawRect((-width) - 2.0f, fontRenderer.field_78288_b + 3.0f, ((-width) - 2.0f) + dist, fontRenderer.field_78288_b + 4.0f, new Color(10, 155, 10).getRGB());
            RenderUtils.quickDrawRect((-width) - 2.0f, fontRenderer.field_78288_b + 3.0f, ((-width) - 2.0f) + (dist * RangesKt.coerceIn(entity.func_110143_aJ() / entity.func_110138_aP(), 0.0f, 1.0f)), fontRenderer.field_78288_b + 4.0f, new Color(10, 255, 10).getRGB());
        }
        GL11.glEnable(3553);
        fontRenderer.func_175065_a(text, 1.0f + (-width), Intrinsics.areEqual(fontRenderer, Fonts.minecraftFont) ? 1.0f : 1.5f, 16777215, this.fontShadowValue.get().booleanValue());
        boolean foundPotion = false;
        if (this.potionValue.get().booleanValue() && (entity instanceof EntityPlayer)) {
            Iterable func_70651_bq = entity.func_70651_bq();
            if (func_70651_bq == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.Collection<net.minecraft.potion.PotionEffect>");
            }
            Iterable $this$map$iv = func_70651_bq;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                PotionEffect it = (PotionEffect) item$iv$iv;
                destination$iv$iv.add(Potion.field_76425_a[it.func_76456_a()]);
            }
            Iterable $this$filter$iv = (List) destination$iv$iv;
            Collection destination$iv$iv2 = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                Potion it2 = (Potion) element$iv$iv;
                if (it2.func_76400_d()) {
                    destination$iv$iv2.add(element$iv$iv);
                }
            }
            List<Potion> potions = (List) destination$iv$iv2;
            if (!potions.isEmpty()) {
                foundPotion = true;
                GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.func_179140_f();
                GlStateManager.func_179098_w();
                int minX = (potions.size() * (-20)) / 2;
                int index = 0;
                GL11.glPushMatrix();
                GlStateManager.func_179091_B();
                for (Potion potion : potions) {
                    GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                    MinecraftInstance.f362mc.func_110434_K().func_110577_a(this.inventoryBackground);
                    int i1 = potion.func_76392_e();
                    RenderUtils.drawTexturedModalRect(minX + (index * 20), -22, 0 + ((i1 % 8) * 18), 198 + ((i1 / 8) * 18), 18, 18, 0.0f);
                    index++;
                }
                GlStateManager.func_179101_C();
                GL11.glPopMatrix();
                GlStateManager.func_179141_d();
                GlStateManager.func_179084_k();
                GlStateManager.func_179098_w();
            }
        }
        if (this.armorValue.get().booleanValue() && (entity instanceof EntityPlayer)) {
            int i = 0;
            while (i < 5) {
                int index2 = i;
                i++;
                if (entity.func_71124_b(index2) != null) {
                    MinecraftInstance.f362mc.func_175599_af().field_77023_b = -147.0f;
                    MinecraftInstance.f362mc.func_175599_af().func_180450_b(entity.func_71124_b(index2), (-50) + (index2 * 20), (!this.potionValue.get().booleanValue() || !foundPotion) ? -22 : -42);
                }
            }
            GlStateManager.func_179141_d();
            GlStateManager.func_179084_k();
            GlStateManager.func_179098_w();
        }
        GL11.glPopMatrix();
    }
}
