package net.ccbluex.liquidbounce.features.module.modules.render;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateModelEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

@ModuleInfo(name = "Skeletal", description = "idk", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/Skeletal.class */
public class Skeletal extends Module {
    private final Map playerRotationMap = new WeakHashMap();
    private final IntegerValue red = new IntegerValue("Red", 255, 0, 255);
    private final IntegerValue green = new IntegerValue("Green", 255, 0, 255);
    private final IntegerValue blue = new IntegerValue("Blue", 255, 0, 255);
    private final BoolValue smoothLines = new BoolValue("SmoothLines", true);

    /* JADX WARN: Multi-variable type inference failed */
    @EventTarget
    public final void onModelUpdate(UpdateModelEvent event) {
        ModelPlayer model = event.getModel();
        this.playerRotationMap.put(event.getPlayer(), new float[]{new float[]{model.field_78116_c.field_78795_f, model.field_78116_c.field_78796_g, model.field_78116_c.field_78808_h}, new float[]{model.field_178723_h.field_78795_f, model.field_178723_h.field_78796_g, model.field_178723_h.field_78808_h}, new float[]{model.field_178724_i.field_78795_f, model.field_178724_i.field_78796_g, model.field_178724_i.field_78808_h}, new float[]{model.field_178721_j.field_78795_f, model.field_178721_j.field_78796_g, model.field_178721_j.field_78808_h}, new float[]{model.field_178722_k.field_78795_f, model.field_178722_k.field_78796_g, model.field_178722_k.field_78808_h}});
    }

    @EventTarget
    public void onRender(Render3DEvent event) {
        setupRender(true);
        GL11.glEnable(2903);
        GL11.glDisable(2848);
        this.playerRotationMap.keySet().removeIf(var0 -> {
            return contain((EntityPlayer) var0);
        });
        Map playerRotationMap = this.playerRotationMap;
        List list = f362mc.field_71441_e.field_73010_i;
        Object[] players = playerRotationMap.keySet().toArray();
        for (Object obj : players) {
            EntityPlayerSP entityPlayerSP = (EntityPlayer) obj;
            float[][] entPos = (float[][]) playerRotationMap.get(entityPlayerSP);
            if (entPos != null && entityPlayerSP.func_145782_y() != -1488 && entityPlayerSP.func_70089_S() && RenderUtils.isInViewFrustrum((Entity) entityPlayerSP) && !((EntityPlayer) entityPlayerSP).field_70128_L && entityPlayerSP != f362mc.field_71439_g && !entityPlayerSP.func_70608_bn() && !entityPlayerSP.func_82150_aj()) {
                GL11.glPushMatrix();
                float[][] modelRotations = (float[][]) playerRotationMap.get(entityPlayerSP);
                GL11.glLineWidth(1.0f);
                GL11.glColor4f(this.red.get().intValue() / 255.0f, this.green.get().intValue() / 255.0f, this.blue.get().intValue() / 255.0f, 1.0f);
                double x = interpolate(((EntityPlayer) entityPlayerSP).field_70165_t, ((EntityPlayer) entityPlayerSP).field_70142_S, event.getPartialTicks()) - f362mc.func_175598_ae().field_78725_b;
                double y = interpolate(((EntityPlayer) entityPlayerSP).field_70163_u, ((EntityPlayer) entityPlayerSP).field_70137_T, event.getPartialTicks()) - f362mc.func_175598_ae().field_78726_c;
                double z = interpolate(((EntityPlayer) entityPlayerSP).field_70161_v, ((EntityPlayer) entityPlayerSP).field_70136_U, event.getPartialTicks()) - f362mc.func_175598_ae().field_78723_d;
                GL11.glTranslated(x, y, z);
                float bodyYawOffset = ((EntityPlayer) entityPlayerSP).field_70760_ar + ((((EntityPlayer) entityPlayerSP).field_70761_aq - ((EntityPlayer) entityPlayerSP).field_70760_ar) * f362mc.field_71428_T.field_74281_c);
                GL11.glRotatef(-bodyYawOffset, 0.0f, 1.0f, 0.0f);
                GL11.glTranslated(0.0d, 0.0d, entityPlayerSP.func_70093_af() ? -0.235d : 0.0d);
                float legHeight = entityPlayerSP.func_70093_af() ? 0.6f : 0.75f;
                GL11.glPushMatrix();
                GL11.glTranslated(-0.125d, legHeight, 0.0d);
                if (modelRotations[3][0] != 0.0f) {
                    GL11.glRotatef(modelRotations[3][0] * 57.29578f, 1.0f, 0.0f, 0.0f);
                }
                if (modelRotations[3][1] != 0.0f) {
                    GL11.glRotatef(modelRotations[3][1] * 57.29578f, 0.0f, 1.0f, 0.0f);
                }
                if (modelRotations[3][2] != 0.0f) {
                    GL11.glRotatef(modelRotations[3][2] * 57.29578f, 0.0f, 0.0f, 1.0f);
                }
                GL11.glBegin(3);
                GL11.glVertex3d(0.0d, 0.0d, 0.0d);
                GL11.glVertex3d(0.0d, -legHeight, 0.0d);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glTranslated(0.125d, legHeight, 0.0d);
                if (modelRotations[4][0] != 0.0f) {
                    GL11.glRotatef(modelRotations[4][0] * 57.29578f, 1.0f, 0.0f, 0.0f);
                }
                if (modelRotations[4][1] != 0.0f) {
                    GL11.glRotatef(modelRotations[4][1] * 57.29578f, 0.0f, 1.0f, 0.0f);
                }
                if (modelRotations[4][2] != 0.0f) {
                    GL11.glRotatef(modelRotations[4][2] * 57.29578f, 0.0f, 0.0f, 1.0f);
                }
                GL11.glBegin(3);
                GL11.glVertex3d(0.0d, 0.0d, 0.0d);
                GL11.glVertex3d(0.0d, -legHeight, 0.0d);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glTranslated(0.0d, 0.0d, entityPlayerSP.func_70093_af() ? 0.25d : 0.0d);
                GL11.glPushMatrix();
                GL11.glTranslated(0.0d, entityPlayerSP.func_70093_af() ? -0.05d : 0.0d, entityPlayerSP.func_70093_af() ? -0.01725d : 0.0d);
                GL11.glPushMatrix();
                GL11.glTranslated(-0.375d, legHeight + 0.55d, 0.0d);
                if (modelRotations[1][0] != 0.0f) {
                    GL11.glRotatef(modelRotations[1][0] * 57.29578f, 1.0f, 0.0f, 0.0f);
                }
                if (modelRotations[1][1] != 0.0f) {
                    GL11.glRotatef(modelRotations[1][1] * 57.29578f, 0.0f, 1.0f, 0.0f);
                }
                if (modelRotations[1][2] != 0.0f) {
                    GL11.glRotatef((-modelRotations[1][2]) * 57.29578f, 0.0f, 0.0f, 1.0f);
                }
                GL11.glBegin(3);
                GL11.glVertex3d(0.0d, 0.0d, 0.0d);
                GL11.glVertex3d(0.0d, -0.5d, 0.0d);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glTranslated(0.375d, legHeight + 0.55d, 0.0d);
                if (modelRotations[2][0] != 0.0f) {
                    GL11.glRotatef(modelRotations[2][0] * 57.29578f, 1.0f, 0.0f, 0.0f);
                }
                if (modelRotations[2][1] != 0.0f) {
                    GL11.glRotatef(modelRotations[2][1] * 57.29578f, 0.0f, 1.0f, 0.0f);
                }
                if (modelRotations[2][2] != 0.0f) {
                    GL11.glRotatef((-modelRotations[2][2]) * 57.29578f, 0.0f, 0.0f, 1.0f);
                }
                GL11.glBegin(3);
                GL11.glVertex3d(0.0d, 0.0d, 0.0d);
                GL11.glVertex3d(0.0d, -0.5d, 0.0d);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glRotatef(bodyYawOffset - ((EntityPlayer) entityPlayerSP).field_70759_as, 0.0f, 1.0f, 0.0f);
                GL11.glPushMatrix();
                GL11.glTranslated(0.0d, legHeight + 0.55d, 0.0d);
                if (modelRotations[0][0] != 0.0f) {
                    GL11.glRotatef(modelRotations[0][0] * 57.29578f, 1.0f, 0.0f, 0.0f);
                }
                GL11.glBegin(3);
                GL11.glVertex3d(0.0d, 0.0d, 0.0d);
                GL11.glVertex3d(0.0d, 0.3d, 0.0d);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glPopMatrix();
                GL11.glRotatef(entityPlayerSP.func_70093_af() ? 25.0f : 0.0f, 1.0f, 0.0f, 0.0f);
                GL11.glTranslated(0.0d, entityPlayerSP.func_70093_af() ? -0.16175d : 0.0d, entityPlayerSP.func_70093_af() ? -0.48025d : 0.0d);
                GL11.glPushMatrix();
                GL11.glTranslated(0.0d, legHeight, 0.0d);
                GL11.glBegin(3);
                GL11.glVertex3d(-0.125d, 0.0d, 0.0d);
                GL11.glVertex3d(0.125d, 0.0d, 0.0d);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glTranslated(0.0d, legHeight, 0.0d);
                GL11.glBegin(3);
                GL11.glVertex3d(0.0d, 0.0d, 0.0d);
                GL11.glVertex3d(0.0d, 0.55d, 0.0d);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glTranslated(0.0d, legHeight + 0.55d, 0.0d);
                GL11.glBegin(3);
                GL11.glVertex3d(-0.375d, 0.0d, 0.0d);
                GL11.glVertex3d(0.375d, 0.0d, 0.0d);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glPopMatrix();
            }
        }
        setupRender(false);
    }

    private void setupRender(boolean start) {
        boolean smooth = this.smoothLines.get().booleanValue();
        if (start) {
            if (smooth) {
                RenderUtils.startSmooth();
            } else {
                GL11.glDisable(2848);
            }
            GL11.glDisable(2929);
            GL11.glDisable(3553);
        } else {
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            if (smooth) {
                RenderUtils.endSmooth();
            }
        }
        GL11.glDepthMask(!start);
    }

    private boolean contain(EntityPlayer var0) {
        return !f362mc.field_71441_e.field_73010_i.contains(var0);
    }

    public static double interpolate(double current, double old, double scale) {
        return old + ((current - old) * scale);
    }
}
