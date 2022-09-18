package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.color.ColorMixer;
import net.ccbluex.liquidbounce.p004ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.item.ItemUtils;
import net.ccbluex.liquidbounce.utils.render.BlendUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

@ModuleInfo(name = "ESP2D", description = "autumn skid.", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/ESP2D.class */
public final class ESP2D extends Module {
    public static List collectedEntities = new ArrayList();
    public final BoolValue outline = new BoolValue("Outline", true);
    public final ListValue boxMode = new ListValue("Mode", new String[]{"Box", "Corners"}, "Box");
    public final BoolValue healthBar = new BoolValue("Health-bar", true);
    public final ListValue hpBarMode = new ListValue("HBar-Mode", new String[]{"Dot", "Line"}, "Dot", () -> {
        return this.healthBar.get();
    });
    public final BoolValue absorption = new BoolValue("Render-Absorption", true, () -> {
        return Boolean.valueOf(this.healthBar.get().booleanValue() && this.hpBarMode.get().equalsIgnoreCase("line"));
    });
    public final BoolValue armorBar = new BoolValue("Armor-bar", true);
    public final ListValue armorBarMode = new ListValue("ABar-Mode", new String[]{"Total", "Items"}, "Total", () -> {
        return this.armorBar.get();
    });
    public final BoolValue healthNumber = new BoolValue("HealthNumber", true, () -> {
        return this.healthBar.get();
    });
    public final ListValue hpMode = new ListValue("HP-Mode", new String[]{"Health", "Percent"}, "Health", () -> {
        return Boolean.valueOf(this.healthBar.get().booleanValue() && this.healthNumber.get().booleanValue());
    });
    public final BoolValue armorNumber = new BoolValue("ItemArmorNumber", true, () -> {
        return this.armorBar.get();
    });
    public final BoolValue armorItems = new BoolValue("ArmorItems", true);
    public final BoolValue armorDur = new BoolValue("ArmorDurability", true, () -> {
        return this.armorItems.get();
    });
    public final BoolValue hoverValue = new BoolValue("Details-HoverOnly", false);
    public final BoolValue tagsValue = new BoolValue("Tags", true);
    public final BoolValue tagsBGValue = new BoolValue("Tags-Background", true, () -> {
        return this.tagsValue.get();
    });
    public final BoolValue itemTagsValue = new BoolValue("Item-Tags", true);
    public final BoolValue clearNameValue = new BoolValue("Use-Clear-Name", false);
    public final BoolValue localPlayer = new BoolValue("Local-Player", true);
    public final BoolValue droppedItems = new BoolValue("Dropped-Items", false);
    private final ListValue colorModeValue = new ListValue("Color", new String[]{"Custom", "Rainbow", "Sky", "LiquidSlowly", "Fade", "Mixer"}, "Custom");
    private final IntegerValue colorRedValue = new IntegerValue("Red", 255, 0, 255);
    private final IntegerValue colorGreenValue = new IntegerValue("Green", 255, 0, 255);
    private final IntegerValue colorBlueValue = new IntegerValue("Blue", 255, 0, 255);
    private final FloatValue saturationValue = new FloatValue("Saturation", 1.0f, 0.0f, 1.0f);
    private final FloatValue brightnessValue = new FloatValue("Brightness", 1.0f, 0.0f, 1.0f);
    private final IntegerValue mixerSecondsValue = new IntegerValue("Seconds", 2, 1, 10);
    private final FloatValue fontScaleValue = new FloatValue("Font-Scale", 0.5f, 0.0f, 1.0f, "x");
    private final BoolValue colorTeam = new BoolValue("Team", false);
    private final DecimalFormat dFormat = new DecimalFormat("0.0");
    private final IntBuffer viewport = GLAllocation.func_74527_f(16);
    private final FloatBuffer modelview = GLAllocation.func_74529_h(16);
    private final FloatBuffer projection = GLAllocation.func_74529_h(16);
    private final FloatBuffer vector = GLAllocation.func_74529_h(4);
    private final int backgroundColor = new Color(0, 0, 0, 120).getRGB();
    private final int black = Color.BLACK.getRGB();

    public final Color getColor(Entity entity) {
        int index;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
            if (entityLivingBase.field_70737_aN > 0) {
                return Color.RED;
            }
            if (EntityUtils.isFriend(entityLivingBase)) {
                return Color.BLUE;
            }
            if (this.colorTeam.get().booleanValue()) {
                char[] chars = entityLivingBase.func_145748_c_().func_150254_d().toCharArray();
                int color = Integer.MAX_VALUE;
                int i = 0;
                while (true) {
                    if (i < chars.length) {
                        if (chars[i] != 167 || i + 1 >= chars.length || (index = GameFontRenderer.getColorIndex(chars[i + 1])) < 0 || index > 15) {
                            i++;
                        } else {
                            color = ColorUtils.hexColors[index];
                            break;
                        }
                    } else {
                        break;
                    }
                }
                return new Color(color);
            }
        }
        String str = this.colorModeValue.get();
        boolean z = true;
        switch (str.hashCode()) {
            case -1656737386:
                if (str.equals("Rainbow")) {
                    z = true;
                    break;
                }
                break;
            case -884013110:
                if (str.equals("LiquidSlowly")) {
                    z = true;
                    break;
                }
                break;
            case 83201:
                if (str.equals("Sky")) {
                    z = true;
                    break;
                }
                break;
            case 74357737:
                if (str.equals("Mixer")) {
                    z = true;
                    break;
                }
                break;
            case 2029746065:
                if (str.equals("Custom")) {
                    z = false;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                return new Color(this.colorRedValue.get().intValue(), this.colorGreenValue.get().intValue(), this.colorBlueValue.get().intValue());
            case true:
                return new Color(RenderUtils.getRainbowOpaque(this.mixerSecondsValue.get().intValue(), this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue(), 0));
            case true:
                return RenderUtils.skyRainbow(0, this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
            case true:
                return ColorUtils.LiquidSlowly(System.nanoTime(), 0, this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
            case true:
                return ColorMixer.getMixedColor(0, this.mixerSecondsValue.get().intValue());
            default:
                return ColorUtils.fade(new Color(this.colorRedValue.get().intValue(), this.colorGreenValue.get().intValue(), this.colorBlueValue.get().intValue()), 0, 100);
        }
    }

    public static boolean shouldCancelNameTag(EntityLivingBase entity) {
        return LiquidBounce.moduleManager.getModule(ESP2D.class) != null && LiquidBounce.moduleManager.getModule(ESP2D.class).getState() && ((ESP2D) LiquidBounce.moduleManager.getModule(ESP2D.class)).tagsValue.get().booleanValue() && collectedEntities.contains(entity);
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        collectedEntities.clear();
    }

    @EventTarget
    public void onRender2D(Render2DEvent event) {
        float itemDurability;
        GL11.glPushMatrix();
        collectEntities();
        float partialTicks = event.getPartialTicks();
        ScaledResolution scaledResolution = new ScaledResolution(f362mc);
        int scaleFactor = scaledResolution.func_78325_e();
        double scaling = scaleFactor / Math.pow(scaleFactor, 2.0d);
        GL11.glScaled(scaling, scaling, scaling);
        int black = this.black;
        int background = this.backgroundColor;
        float f = 1.0f / 0.65f;
        FontRenderer fontRenderer = f362mc.field_71466_p;
        RenderManager renderMng = f362mc.func_175598_ae();
        EntityRenderer entityRenderer = f362mc.field_71460_t;
        boolean outline = this.outline.get().booleanValue();
        boolean health = this.healthBar.get().booleanValue();
        boolean armor = this.armorBar.get().booleanValue();
        int collectedEntitiesSize = collectedEntities.size();
        for (int i = 0; i < collectedEntitiesSize; i++) {
            EntityPlayerSP entityPlayerSP = (Entity) collectedEntities.get(i);
            int color = getColor(entityPlayerSP).getRGB();
            if (RenderUtils.isInViewFrustrum((Entity) entityPlayerSP)) {
                double x = RenderUtils.interpolate(((Entity) entityPlayerSP).field_70165_t, ((Entity) entityPlayerSP).field_70142_S, partialTicks);
                double y = RenderUtils.interpolate(((Entity) entityPlayerSP).field_70163_u, ((Entity) entityPlayerSP).field_70137_T, partialTicks);
                double z = RenderUtils.interpolate(((Entity) entityPlayerSP).field_70161_v, ((Entity) entityPlayerSP).field_70136_U, partialTicks);
                double width = ((Entity) entityPlayerSP).field_70130_N / 1.5d;
                double height = ((Entity) entityPlayerSP).field_70131_O + (entityPlayerSP.func_70093_af() ? -0.3d : 0.2d);
                AxisAlignedBB aabb = new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width);
                List<Vector3d> vectors = Arrays.asList(new Vector3d(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72339_c), new Vector3d(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72339_c), new Vector3d(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72339_c), new Vector3d(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72339_c), new Vector3d(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72334_f), new Vector3d(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72334_f), new Vector3d(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72334_f), new Vector3d(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72334_f));
                entityRenderer.func_78479_a(partialTicks, 0);
                Vector4d position = null;
                for (Vector3d vector : vectors) {
                    Vector3d vector2 = project2D(scaleFactor, vector.x - renderMng.field_78730_l, vector.y - renderMng.field_78731_m, vector.z - renderMng.field_78728_n);
                    if (vector2 != null && vector2.z >= 0.0d && vector2.z < 1.0d) {
                        if (position == null) {
                            position = new Vector4d(vector2.x, vector2.y, vector2.z, 0.0d);
                        }
                        position.x = Math.min(vector2.x, position.x);
                        position.y = Math.min(vector2.y, position.y);
                        position.z = Math.max(vector2.x, position.z);
                        position.w = Math.max(vector2.y, position.w);
                    }
                }
                if (position != null) {
                    entityRenderer.func_78478_c();
                    double posX = position.x;
                    double posY = position.y;
                    double endPosX = position.z;
                    double endPosY = position.w;
                    if (outline) {
                        if (this.boxMode.get() == "Box") {
                            RenderUtils.newDrawRect(posX - 1.0d, posY, posX + 0.5d, endPosY + 0.5d, black);
                            RenderUtils.newDrawRect(posX - 1.0d, posY - 0.5d, endPosX + 0.5d, posY + 0.5d + 0.5d, black);
                            RenderUtils.newDrawRect((endPosX - 0.5d) - 0.5d, posY, endPosX + 0.5d, endPosY + 0.5d, black);
                            RenderUtils.newDrawRect(posX - 1.0d, (endPosY - 0.5d) - 0.5d, endPosX + 0.5d, endPosY + 0.5d, black);
                            RenderUtils.newDrawRect(posX - 0.5d, posY, (posX + 0.5d) - 0.5d, endPosY, color);
                            RenderUtils.newDrawRect(posX, endPosY - 0.5d, endPosX, endPosY, color);
                            RenderUtils.newDrawRect(posX - 0.5d, posY, endPosX, posY + 0.5d, color);
                            RenderUtils.newDrawRect(endPosX - 0.5d, posY, endPosX, endPosY, color);
                        } else {
                            RenderUtils.newDrawRect(posX + 0.5d, posY, posX - 1.0d, posY + ((endPosY - posY) / 4.0d) + 0.5d, black);
                            RenderUtils.newDrawRect(posX - 1.0d, endPosY, posX + 0.5d, (endPosY - ((endPosY - posY) / 4.0d)) - 0.5d, black);
                            RenderUtils.newDrawRect(posX - 1.0d, posY - 0.5d, posX + ((endPosX - posX) / 3.0d) + 0.5d, posY + 1.0d, black);
                            RenderUtils.newDrawRect((endPosX - ((endPosX - posX) / 3.0d)) - 0.5d, posY - 0.5d, endPosX, posY + 1.0d, black);
                            RenderUtils.newDrawRect(endPosX - 1.0d, posY, endPosX + 0.5d, posY + ((endPosY - posY) / 4.0d) + 0.5d, black);
                            RenderUtils.newDrawRect(endPosX - 1.0d, endPosY, endPosX + 0.5d, (endPosY - ((endPosY - posY) / 4.0d)) - 0.5d, black);
                            RenderUtils.newDrawRect(posX - 1.0d, endPosY - 1.0d, posX + ((endPosX - posX) / 3.0d) + 0.5d, endPosY + 0.5d, black);
                            RenderUtils.newDrawRect((endPosX - ((endPosX - posX) / 3.0d)) - 0.5d, endPosY - 1.0d, endPosX + 0.5d, endPosY + 0.5d, black);
                            RenderUtils.newDrawRect(posX, posY, posX - 0.5d, posY + ((endPosY - posY) / 4.0d), color);
                            RenderUtils.newDrawRect(posX, endPosY, posX - 0.5d, endPosY - ((endPosY - posY) / 4.0d), color);
                            RenderUtils.newDrawRect(posX - 0.5d, posY, posX + ((endPosX - posX) / 3.0d), posY + 0.5d, color);
                            RenderUtils.newDrawRect(endPosX - ((endPosX - posX) / 3.0d), posY, endPosX, posY + 0.5d, color);
                            RenderUtils.newDrawRect(endPosX - 0.5d, posY, endPosX, posY + ((endPosY - posY) / 4.0d), color);
                            RenderUtils.newDrawRect(endPosX - 0.5d, endPosY, endPosX, endPosY - ((endPosY - posY) / 4.0d), color);
                            RenderUtils.newDrawRect(posX, endPosY - 0.5d, posX + ((endPosX - posX) / 3.0d), endPosY, color);
                            RenderUtils.newDrawRect(endPosX - ((endPosX - posX) / 3.0d), endPosY - 0.5d, endPosX - 0.5d, endPosY, color);
                        }
                    }
                    boolean living = entityPlayerSP instanceof EntityLivingBase;
                    boolean z2 = entityPlayerSP instanceof EntityPlayer;
                    if (living) {
                        EntityLivingBase entityLivingBase = (EntityLivingBase) entityPlayerSP;
                        if (health) {
                            float armorValue = entityLivingBase.func_110143_aJ();
                            float itemDurability2 = entityLivingBase.func_110138_aP();
                            if (armorValue > itemDurability2) {
                                armorValue = itemDurability2;
                            }
                            double textWidth = (endPosY - posY) * (armorValue / itemDurability2);
                            String healthDisplay = this.dFormat.format(entityLivingBase.func_110143_aJ()) + " §c❤";
                            String healthPercent = ((int) ((entityLivingBase.func_110143_aJ() / itemDurability2) * 100.0f)) + "%";
                            if (this.healthNumber.get().booleanValue() && (!this.hoverValue.get().booleanValue() || entityPlayerSP == f362mc.field_71439_g || isHovering(posX, endPosX, posY, endPosY, scaledResolution))) {
                                drawScaledString(this.hpMode.get().equalsIgnoreCase("health") ? healthDisplay : healthPercent, (posX - 4.0d) - (f362mc.field_71466_p.func_78256_a(this.hpMode.get().equalsIgnoreCase("health") ? healthDisplay : healthPercent) * this.fontScaleValue.get().floatValue()), (endPosY - textWidth) - ((f362mc.field_71466_p.field_78288_b / 2.0f) * this.fontScaleValue.get().floatValue()), this.fontScaleValue.get().floatValue(), -1);
                            }
                            RenderUtils.newDrawRect(posX - 3.5d, posY - 0.5d, posX - 1.5d, endPosY + 0.5d, background);
                            if (armorValue > 0.0f) {
                                int healthColor = BlendUtils.getHealthColor(armorValue, itemDurability2).getRGB();
                                double deltaY = endPosY - posY;
                                if (this.hpBarMode.get().equalsIgnoreCase("dot") && deltaY >= 60.0d) {
                                    double d = 0.0d;
                                    while (true) {
                                        double k = d;
                                        if (k >= 10.0d) {
                                            break;
                                        }
                                        double reratio = MathHelper.func_151237_a(armorValue - (k * (itemDurability2 / 10.0d)), 0.0d, itemDurability2 / 10.0d) / (itemDurability2 / 10.0d);
                                        double hei = ((deltaY / 10.0d) - 0.5d) * reratio;
                                        RenderUtils.newDrawRect(posX - 3.0d, endPosY - (((deltaY + 0.5d) / 10.0d) * k), posX - 2.0d, (endPosY - (((deltaY + 0.5d) / 10.0d) * k)) - hei, healthColor);
                                        d = k + 1.0d;
                                    }
                                } else {
                                    RenderUtils.newDrawRect(posX - 3.0d, endPosY, posX - 2.0d, endPosY - textWidth, healthColor);
                                    float tagY = entityLivingBase.func_110139_bj();
                                    if (this.absorption.get().booleanValue() && tagY > 0.0f) {
                                        RenderUtils.newDrawRect(posX - 3.0d, endPosY, posX - 2.0d, endPosY - ((((endPosY - posY) / 6.0d) * tagY) / 2.0d), new Color(Potion.field_76444_x.func_76401_j()).getRGB());
                                    }
                                }
                            }
                        }
                    }
                    if (armor) {
                        if (living) {
                            EntityLivingBase entityLivingBase2 = (EntityLivingBase) entityPlayerSP;
                            if (this.armorBarMode.get().equalsIgnoreCase("items")) {
                                double constHeight = (endPosY - posY) / 4.0d;
                                for (int m = 4; m > 0; m--) {
                                    ItemStack armorStack = entityLivingBase2.func_71124_b(m);
                                    double theHeight = constHeight + 0.25d;
                                    if (armorStack != null && armorStack.func_77973_b() != null) {
                                        RenderUtils.newDrawRect(endPosX + 1.5d, (endPosY + 0.5d) - (theHeight * m), endPosX + 3.5d, (endPosY + 0.5d) - (theHeight * (m - 1)), background);
                                        RenderUtils.newDrawRect(endPosX + 2.0d, ((endPosY + 0.5d) - (theHeight * (m - 1))) - 0.25d, endPosX + 3.0d, (((endPosY + 0.5d) - (theHeight * (m - 1))) - 0.25d) - ((constHeight - 0.25d) * MathHelper.func_151237_a(ItemUtils.getItemDurability(armorStack) / armorStack.func_77958_k(), 0.0d, 1.0d)), new Color(0, 255, 255).getRGB());
                                    }
                                }
                            } else {
                                float armorValue2 = entityLivingBase2.func_70658_aO();
                                double armorWidth = ((endPosY - posY) * armorValue2) / 20.0d;
                                RenderUtils.newDrawRect(endPosX + 1.5d, posY - 0.5d, endPosX + 3.5d, endPosY + 0.5d, background);
                                if (armorValue2 > 0.0f) {
                                    RenderUtils.newDrawRect(endPosX + 2.0d, endPosY, endPosX + 3.0d, endPosY - armorWidth, new Color(0, 255, 255).getRGB());
                                }
                            }
                        } else if (entityPlayerSP instanceof EntityItem) {
                            ItemStack itemStack = ((EntityItem) entityPlayerSP).func_92059_d();
                            if (itemStack.func_77984_f()) {
                                int maxDamage = itemStack.func_77958_k();
                                double durabilityWidth = ((endPosY - posY) * (maxDamage - itemStack.func_77952_i())) / maxDamage;
                                if (this.armorNumber.get().booleanValue() && (!this.hoverValue.get().booleanValue() || entityPlayerSP == f362mc.field_71439_g || isHovering(posX, endPosX, posY, endPosY, scaledResolution))) {
                                    drawScaledString(((int) itemDurability) + "", endPosX + 4.0d, (endPosY - durabilityWidth) - ((f362mc.field_71466_p.field_78288_b / 2.0f) * this.fontScaleValue.get().floatValue()), this.fontScaleValue.get().floatValue(), -1);
                                }
                                RenderUtils.newDrawRect(endPosX + 1.5d, posY - 0.5d, endPosX + 3.5d, endPosY + 0.5d, background);
                                RenderUtils.newDrawRect(endPosX + 2.0d, endPosY, endPosX + 3.0d, endPosY - durabilityWidth, new Color(0, 255, 255).getRGB());
                            }
                        }
                    }
                    if (living && this.armorItems.get().booleanValue() && (!this.hoverValue.get().booleanValue() || entityPlayerSP == f362mc.field_71439_g || isHovering(posX, endPosX, posY, endPosY, scaledResolution))) {
                        EntityLivingBase entityLivingBase3 = (EntityLivingBase) entityPlayerSP;
                        double yDist = (endPosY - posY) / 4.0d;
                        for (int j = 4; j > 0; j--) {
                            ItemStack armorStack2 = entityLivingBase3.func_71124_b(j);
                            if (armorStack2 != null && armorStack2.func_77973_b() != null) {
                                renderItemStack(armorStack2, endPosX + (armor ? 4.0d : 2.0d), ((posY + (yDist * (4 - j))) + (yDist / 2.0d)) - 5.0d);
                                if (this.armorDur.get().booleanValue()) {
                                    drawScaledCenteredString(ItemUtils.getItemDurability(armorStack2) + "", endPosX + (armor ? 4.0d : 2.0d) + 4.5d, posY + (yDist * (4 - j)) + (yDist / 2.0d) + 4.0d, this.fontScaleValue.get().floatValue(), -1);
                                }
                            }
                        }
                    }
                    if (living && this.tagsValue.get().booleanValue()) {
                        EntityLivingBase entityLivingBase4 = (EntityLivingBase) entityPlayerSP;
                        String entName = this.clearNameValue.get().booleanValue() ? entityLivingBase4.func_70005_c_() : entityLivingBase4.func_145748_c_().func_150254_d();
                        if (this.tagsBGValue.get().booleanValue()) {
                            RenderUtils.newDrawRect((posX + ((endPosX - posX) / 2.0d)) - (((f362mc.field_71466_p.func_78256_a(entName) / 2.0f) + 2.0f) * this.fontScaleValue.get().floatValue()), (posY - 1.0d) - ((f362mc.field_71466_p.field_78288_b + 2.0f) * this.fontScaleValue.get().floatValue()), posX + ((endPosX - posX) / 2.0d) + (((f362mc.field_71466_p.func_78256_a(entName) / 2.0f) + 2.0f) * this.fontScaleValue.get().floatValue()), (posY - 1.0d) + (2.0f * this.fontScaleValue.get().floatValue()), -1610612736);
                        }
                        drawScaledCenteredString(entName, posX + ((endPosX - posX) / 2.0d), (posY - 1.0d) - (f362mc.field_71466_p.field_78288_b * this.fontScaleValue.get().floatValue()), this.fontScaleValue.get().floatValue(), -1);
                    }
                    if (this.itemTagsValue.get().booleanValue()) {
                        if (living) {
                            EntityLivingBase entityLivingBase5 = (EntityLivingBase) entityPlayerSP;
                            if (entityLivingBase5.func_70694_bm() != null && entityLivingBase5.func_70694_bm().func_77973_b() != null) {
                                String itemName = entityLivingBase5.func_70694_bm().func_82833_r();
                                if (this.tagsBGValue.get().booleanValue()) {
                                    RenderUtils.newDrawRect((posX + ((endPosX - posX) / 2.0d)) - (((f362mc.field_71466_p.func_78256_a(itemName) / 2.0f) + 2.0f) * this.fontScaleValue.get().floatValue()), (endPosY + 1.0d) - (2.0f * this.fontScaleValue.get().floatValue()), posX + ((endPosX - posX) / 2.0d) + (((f362mc.field_71466_p.func_78256_a(itemName) / 2.0f) + 2.0f) * this.fontScaleValue.get().floatValue()), endPosY + 1.0d + ((f362mc.field_71466_p.field_78288_b + 2.0f) * this.fontScaleValue.get().floatValue()), -1610612736);
                                }
                                drawScaledCenteredString(itemName, posX + ((endPosX - posX) / 2.0d), endPosY + 1.0d, this.fontScaleValue.get().floatValue(), -1);
                            }
                        } else if (entityPlayerSP instanceof EntityItem) {
                            String entName2 = ((EntityItem) entityPlayerSP).func_92059_d().func_82833_r();
                            if (this.tagsBGValue.get().booleanValue()) {
                                RenderUtils.newDrawRect((posX + ((endPosX - posX) / 2.0d)) - (((f362mc.field_71466_p.func_78256_a(entName2) / 2.0f) + 2.0f) * this.fontScaleValue.get().floatValue()), (endPosY + 1.0d) - (2.0f * this.fontScaleValue.get().floatValue()), posX + ((endPosX - posX) / 2.0d) + (((f362mc.field_71466_p.func_78256_a(entName2) / 2.0f) + 2.0f) * this.fontScaleValue.get().floatValue()), endPosY + 1.0d + ((f362mc.field_71466_p.field_78288_b + 2.0f) * this.fontScaleValue.get().floatValue()), -1610612736);
                            }
                            drawScaledCenteredString(entName2, posX + ((endPosX - posX) / 2.0d), endPosY + 1.0d, this.fontScaleValue.get().floatValue(), -1);
                        }
                    }
                }
            }
        }
        GL11.glPopMatrix();
        GlStateManager.func_179147_l();
        entityRenderer.func_78478_c();
    }

    private boolean isHovering(double minX, double maxX, double minY, double maxY, ScaledResolution sc) {
        return ((double) (sc.func_78326_a() / 2)) >= minX && ((double) (sc.func_78326_a() / 2)) < maxX && ((double) (sc.func_78328_b() / 2)) >= minY && ((double) (sc.func_78328_b() / 2)) < maxY;
    }

    private void drawScaledString(String text, double x, double y, double scale, int color) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179137_b(x, y, x);
        GlStateManager.func_179139_a(scale, scale, scale);
        f362mc.field_71466_p.func_175063_a(text, 0.0f, 0.0f, color);
        GlStateManager.func_179121_F();
    }

    private void drawScaledCenteredString(String text, double x, double y, double scale, int color) {
        drawScaledString(text, x - ((f362mc.field_71466_p.func_78256_a(text) / 2.0f) * scale), y, scale, color);
    }

    private void renderItemStack(ItemStack stack, double x, double y) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179137_b(x, y, x);
        GlStateManager.func_179139_a(0.5d, 0.5d, 0.5d);
        GlStateManager.func_179091_B();
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        RenderHelper.func_74520_c();
        f362mc.func_175599_af().func_180450_b(stack, 0, 0);
        f362mc.func_175599_af().func_175030_a(f362mc.field_71466_p, stack, 0, 0);
        RenderHelper.func_74518_a();
        GlStateManager.func_179101_C();
        GlStateManager.func_179084_k();
        GlStateManager.func_179121_F();
    }

    private void collectEntities() {
        collectedEntities.clear();
        List playerEntities = f362mc.field_71441_e.field_72996_f;
        int playerEntitiesSize = playerEntities.size();
        for (int i = 0; i < playerEntitiesSize; i++) {
            Entity entity = (Entity) playerEntities.get(i);
            if (EntityUtils.isSelected(entity, false) || ((this.localPlayer.get().booleanValue() && (entity instanceof EntityPlayerSP) && f362mc.field_71474_y.field_74320_O != 0) || (this.droppedItems.get().booleanValue() && (entity instanceof EntityItem)))) {
                collectedEntities.add(entity);
            }
        }
    }

    private Vector3d project2D(int scaleFactor, double x, double y, double z) {
        GL11.glGetFloat(2982, this.modelview);
        GL11.glGetFloat(2983, this.projection);
        GL11.glGetInteger(2978, this.viewport);
        if (GLU.gluProject((float) x, (float) y, (float) z, this.modelview, this.projection, this.viewport, this.vector)) {
            return new Vector3d(this.vector.get(0) / scaleFactor, (Display.getHeight() - this.vector.get(1)) / scaleFactor, this.vector.get(2));
        }
        return null;
    }
}
