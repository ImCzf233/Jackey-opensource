package net.ccbluex.liquidbounce.features.module.modules.render;

import co.p000uk.hexeption.utils.OutlineUtils;
import java.awt.Color;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.world.ChestAura;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.shader.FramebufferShader;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.GlowShader;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.OutlineShader;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import org.lwjgl.opengl.GL11;

@ModuleInfo(name = "StorageESP", spacedName = "Storage ESP", description = "Allows you to see chests, dispensers, etc. through walls.", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/StorageESP.class */
public class StorageESP extends Module {
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Box", "OtherBox", "Outline", "ShaderOutline", "ShaderGlow", "2D", "WireFrame"}, "Outline");
    private final BoolValue chestValue = new BoolValue("Chest", true);
    private final BoolValue enderChestValue = new BoolValue("EnderChest", true);
    private final BoolValue furnaceValue = new BoolValue("Furnace", true);
    private final BoolValue dispenserValue = new BoolValue("Dispenser", true);
    private final BoolValue hopperValue = new BoolValue("Hopper", true);

    @EventTarget
    public void onRender3D(Render3DEvent event) {
        try {
            String mode = this.modeValue.get();
            if (mode.equalsIgnoreCase("outline")) {
                ClientUtils.disableFastRender();
                OutlineUtils.checkSetupFBO();
            }
            float gamma = f362mc.field_71474_y.field_74333_Y;
            f362mc.field_71474_y.field_74333_Y = 100000.0f;
            for (TileEntity tileEntity : f362mc.field_71441_e.field_147482_g) {
                Color color = null;
                if (this.chestValue.get().booleanValue() && (tileEntity instanceof TileEntityChest) && !ChestAura.INSTANCE.getClickedBlocks().contains(tileEntity.func_174877_v())) {
                    color = new Color(0, 66, 255);
                }
                if (this.enderChestValue.get().booleanValue() && (tileEntity instanceof TileEntityEnderChest) && !ChestAura.INSTANCE.getClickedBlocks().contains(tileEntity.func_174877_v())) {
                    color = Color.MAGENTA;
                }
                if (this.furnaceValue.get().booleanValue() && (tileEntity instanceof TileEntityFurnace)) {
                    color = Color.BLACK;
                }
                if (this.dispenserValue.get().booleanValue() && (tileEntity instanceof TileEntityDispenser)) {
                    color = Color.BLACK;
                }
                if (this.hopperValue.get().booleanValue() && (tileEntity instanceof TileEntityHopper)) {
                    color = Color.GRAY;
                }
                if (color != null) {
                    if (!(tileEntity instanceof TileEntityChest) && !(tileEntity instanceof TileEntityEnderChest)) {
                        RenderUtils.drawBlockBox(tileEntity.func_174877_v(), color, !mode.equalsIgnoreCase("otherbox"));
                    } else {
                        String lowerCase = mode.toLowerCase();
                        boolean z = true;
                        switch (lowerCase.hashCode()) {
                            case -1171135301:
                                if (lowerCase.equals("otherbox")) {
                                    z = false;
                                    break;
                                }
                                break;
                            case -1106245566:
                                if (lowerCase.equals("outline")) {
                                    z = true;
                                    break;
                                }
                                break;
                            case -941784056:
                                if (lowerCase.equals("wireframe")) {
                                    z = true;
                                    break;
                                }
                                break;
                            case 1650:
                                if (lowerCase.equals("2d")) {
                                    z = true;
                                    break;
                                }
                                break;
                            case 97739:
                                if (lowerCase.equals("box")) {
                                    z = true;
                                    break;
                                }
                                break;
                        }
                        switch (z) {
                            case false:
                            case true:
                                RenderUtils.drawBlockBox(tileEntity.func_174877_v(), color, !mode.equalsIgnoreCase("otherbox"));
                                break;
                            case true:
                                RenderUtils.draw2D(tileEntity.func_174877_v(), color.getRGB(), Color.BLACK.getRGB());
                                break;
                            case true:
                                RenderUtils.glColor(color);
                                OutlineUtils.renderOne(3.0f);
                                TileEntityRendererDispatcher.field_147556_a.func_180546_a(tileEntity, event.getPartialTicks(), -1);
                                OutlineUtils.renderTwo();
                                TileEntityRendererDispatcher.field_147556_a.func_180546_a(tileEntity, event.getPartialTicks(), -1);
                                OutlineUtils.renderThree();
                                TileEntityRendererDispatcher.field_147556_a.func_180546_a(tileEntity, event.getPartialTicks(), -1);
                                OutlineUtils.renderFour(color);
                                TileEntityRendererDispatcher.field_147556_a.func_180546_a(tileEntity, event.getPartialTicks(), -1);
                                OutlineUtils.renderFive();
                                OutlineUtils.setColor(Color.WHITE);
                                break;
                            case true:
                                GL11.glPushMatrix();
                                GL11.glPushAttrib(1048575);
                                GL11.glPolygonMode(1032, 6913);
                                GL11.glDisable(3553);
                                GL11.glDisable(2896);
                                GL11.glDisable(2929);
                                GL11.glEnable(2848);
                                GL11.glEnable(3042);
                                GL11.glBlendFunc(770, 771);
                                TileEntityRendererDispatcher.field_147556_a.func_180546_a(tileEntity, event.getPartialTicks(), -1);
                                RenderUtils.glColor(color);
                                GL11.glLineWidth(1.5f);
                                TileEntityRendererDispatcher.field_147556_a.func_180546_a(tileEntity, event.getPartialTicks(), -1);
                                GL11.glPopAttrib();
                                GL11.glPopMatrix();
                                break;
                        }
                    }
                }
            }
            for (Entity entity : f362mc.field_71441_e.field_72996_f) {
                if (entity instanceof EntityMinecartChest) {
                    String lowerCase2 = mode.toLowerCase();
                    boolean z2 = true;
                    switch (lowerCase2.hashCode()) {
                        case -1171135301:
                            if (lowerCase2.equals("otherbox")) {
                                z2 = false;
                                break;
                            }
                            break;
                        case -1106245566:
                            if (lowerCase2.equals("outline")) {
                                z2 = true;
                                break;
                            }
                            break;
                        case -941784056:
                            if (lowerCase2.equals("wireframe")) {
                                z2 = true;
                                break;
                            }
                            break;
                        case 1650:
                            if (lowerCase2.equals("2d")) {
                                z2 = true;
                                break;
                            }
                            break;
                        case 97739:
                            if (lowerCase2.equals("box")) {
                                z2 = true;
                                break;
                            }
                            break;
                    }
                    switch (z2) {
                        case false:
                        case true:
                            RenderUtils.drawEntityBox(entity, new Color(0, 66, 255), !mode.equalsIgnoreCase("otherbox"));
                            continue;
                        case true:
                            RenderUtils.draw2D(entity.func_180425_c(), new Color(0, 66, 255).getRGB(), Color.BLACK.getRGB());
                            continue;
                        case true:
                            boolean entityShadow = f362mc.field_71474_y.field_181151_V;
                            f362mc.field_71474_y.field_181151_V = false;
                            RenderUtils.glColor(new Color(0, 66, 255));
                            OutlineUtils.renderOne(3.0f);
                            f362mc.func_175598_ae().func_147936_a(entity, f362mc.field_71428_T.field_74281_c, true);
                            OutlineUtils.renderTwo();
                            f362mc.func_175598_ae().func_147936_a(entity, f362mc.field_71428_T.field_74281_c, true);
                            OutlineUtils.renderThree();
                            f362mc.func_175598_ae().func_147936_a(entity, f362mc.field_71428_T.field_74281_c, true);
                            OutlineUtils.renderFour(new Color(0, 66, 255));
                            f362mc.func_175598_ae().func_147936_a(entity, f362mc.field_71428_T.field_74281_c, true);
                            OutlineUtils.renderFive();
                            OutlineUtils.setColor(Color.WHITE);
                            f362mc.field_71474_y.field_181151_V = entityShadow;
                            continue;
                        case true:
                            boolean entityShadow2 = f362mc.field_71474_y.field_181151_V;
                            f362mc.field_71474_y.field_181151_V = false;
                            GL11.glPushMatrix();
                            GL11.glPushAttrib(1048575);
                            GL11.glPolygonMode(1032, 6913);
                            GL11.glDisable(3553);
                            GL11.glDisable(2896);
                            GL11.glDisable(2929);
                            GL11.glEnable(2848);
                            GL11.glEnable(3042);
                            GL11.glBlendFunc(770, 771);
                            RenderUtils.glColor(new Color(0, 66, 255));
                            f362mc.func_175598_ae().func_147936_a(entity, f362mc.field_71428_T.field_74281_c, true);
                            RenderUtils.glColor(new Color(0, 66, 255));
                            GL11.glLineWidth(1.5f);
                            f362mc.func_175598_ae().func_147936_a(entity, f362mc.field_71428_T.field_74281_c, true);
                            GL11.glPopAttrib();
                            GL11.glPopMatrix();
                            f362mc.field_71474_y.field_181151_V = entityShadow2;
                            continue;
                    }
                }
            }
            RenderUtils.glColor(new Color(255, 255, 255, 255));
            f362mc.field_71474_y.field_74333_Y = gamma;
        } catch (Exception e) {
        }
    }

    @EventTarget
    public void onRender2D(Render2DEvent event) {
        FramebufferShader framebufferShader;
        String mode = this.modeValue.get();
        if (mode.equalsIgnoreCase("shaderoutline")) {
            framebufferShader = OutlineShader.OUTLINE_SHADER;
        } else {
            framebufferShader = mode.equalsIgnoreCase("shaderglow") ? GlowShader.GLOW_SHADER : null;
        }
        FramebufferShader shader = framebufferShader;
        if (shader == null) {
            return;
        }
        shader.startDraw(event.getPartialTicks());
        try {
            RenderManager renderManager = f362mc.func_175598_ae();
            for (TileEntity entity : f362mc.field_71441_e.field_147482_g) {
                if ((entity instanceof TileEntityChest) && !ChestAura.INSTANCE.getClickedBlocks().contains(entity.func_174877_v())) {
                    TileEntityRendererDispatcher.field_147556_a.func_147549_a(entity, entity.func_174877_v().func_177958_n() - renderManager.field_78725_b, entity.func_174877_v().func_177956_o() - renderManager.field_78726_c, entity.func_174877_v().func_177952_p() - renderManager.field_78723_d, event.getPartialTicks());
                }
            }
            for (Entity entity2 : f362mc.field_71441_e.field_72996_f) {
                if (entity2 instanceof EntityMinecartChest) {
                    renderManager.func_147936_a(entity2, event.getPartialTicks(), true);
                }
            }
        } catch (Exception ex) {
            ClientUtils.getLogger().error("An error occurred while rendering all storages for shader esp", ex);
        }
        shader.stopDraw(new Color(0, 66, 255), mode.equalsIgnoreCase("shaderglow") ? 2.5f : 1.5f, 1.0f);
    }
}
