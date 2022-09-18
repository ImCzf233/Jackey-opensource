package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import com.google.common.base.Predicates;
import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.player.Reach;
import net.ccbluex.liquidbounce.features.module.modules.render.CameraClip;
import net.ccbluex.liquidbounce.features.module.modules.render.NoHurtCam;
import net.ccbluex.liquidbounce.features.module.modules.render.TargetMark;
import net.ccbluex.liquidbounce.features.module.modules.render.Tracers;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({EntityRenderer.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinEntityRenderer.class */
public abstract class MixinEntityRenderer {
    @Shadow
    private Entity field_78528_u;
    @Shadow
    private Minecraft field_78531_r;
    @Shadow
    private float field_78491_C;
    @Shadow
    private float field_78490_B;
    @Shadow
    private boolean field_78500_U;

    @Shadow
    public abstract void func_175069_a(ResourceLocation resourceLocation);

    @Shadow
    public abstract void func_78479_a(float f, int i);

    @Inject(method = {"renderStreamIndicator"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void cancelStreamIndicator(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = {"renderWorldPass"}, slice = {@Slice(from = @AbstractC1790At(value = "FIELD", target = "Lnet/minecraft/util/EnumWorldBlockLayer;TRANSLUCENT:Lnet/minecraft/util/EnumWorldBlockLayer;"))}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;renderBlockLayer(Lnet/minecraft/util/EnumWorldBlockLayer;DILnet/minecraft/entity/Entity;)I", ordinal = 0)})
    private void enablePolygonOffset(CallbackInfo ci) {
        GlStateManager.func_179088_q();
        GlStateManager.func_179136_a(-0.325f, -0.325f);
    }

    @Inject(method = {"renderWorldPass"}, slice = {@Slice(from = @AbstractC1790At(value = "FIELD", target = "Lnet/minecraft/util/EnumWorldBlockLayer;TRANSLUCENT:Lnet/minecraft/util/EnumWorldBlockLayer;"))}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;renderBlockLayer(Lnet/minecraft/util/EnumWorldBlockLayer;DILnet/minecraft/entity/Entity;)I", ordinal = 0, shift = AbstractC1790At.Shift.AFTER)})
    private void disablePolygonOffset(CallbackInfo ci) {
        GlStateManager.func_179113_r();
    }

    @Inject(method = {"renderWorldPass"}, m23at = {@AbstractC1790At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand:Z", shift = AbstractC1790At.Shift.BEFORE)})
    private void renderWorldPass(int pass, float partialTicks, long finishTimeNano, CallbackInfo callbackInfo) {
        LiquidBounce.eventManager.callEvent(new Render3DEvent(partialTicks));
    }

    @Inject(method = {"hurtCameraEffect"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void injectHurtCameraEffect(CallbackInfo callbackInfo) {
        if (LiquidBounce.moduleManager.getModule(NoHurtCam.class).getState()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = {"orientCamera"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/util/Vec3;distanceTo(Lnet/minecraft/util/Vec3;)D")}, cancellable = true)
    private void cameraClip(float partialTicks, CallbackInfo callbackInfo) {
        if (LiquidBounce.moduleManager.getModule(CameraClip.class).getState()) {
            callbackInfo.cancel();
            EntityLivingBase func_175606_aa = this.field_78531_r.func_175606_aa();
            float f = func_175606_aa.func_70047_e();
            if ((func_175606_aa instanceof EntityLivingBase) && func_175606_aa.func_70608_bn()) {
                f = (float) (f + 1.0d);
                GlStateManager.func_179109_b(0.0f, 0.3f, 0.0f);
                if (!this.field_78531_r.field_71474_y.field_74325_U) {
                    BlockPos blockpos = new BlockPos(func_175606_aa);
                    IBlockState iblockstate = this.field_78531_r.field_71441_e.func_180495_p(blockpos);
                    ForgeHooksClient.orientBedCamera(this.field_78531_r.field_71441_e, blockpos, iblockstate, func_175606_aa);
                    GlStateManager.func_179114_b(((Entity) func_175606_aa).field_70126_B + ((((Entity) func_175606_aa).field_70177_z - ((Entity) func_175606_aa).field_70126_B) * partialTicks) + 180.0f, 0.0f, -1.0f, 0.0f);
                    GlStateManager.func_179114_b(((Entity) func_175606_aa).field_70127_C + ((((Entity) func_175606_aa).field_70125_A - ((Entity) func_175606_aa).field_70127_C) * partialTicks), -1.0f, 0.0f, 0.0f);
                }
            } else if (this.field_78531_r.field_71474_y.field_74320_O > 0) {
                double d3 = this.field_78491_C + ((this.field_78490_B - this.field_78491_C) * partialTicks);
                if (this.field_78531_r.field_71474_y.field_74325_U) {
                    GlStateManager.func_179109_b(0.0f, 0.0f, (float) (-d3));
                } else {
                    float f1 = ((Entity) func_175606_aa).field_70177_z;
                    float f2 = ((Entity) func_175606_aa).field_70125_A;
                    if (this.field_78531_r.field_71474_y.field_74320_O == 2) {
                        f2 += 180.0f;
                    }
                    if (this.field_78531_r.field_71474_y.field_74320_O == 2) {
                        GlStateManager.func_179114_b(180.0f, 0.0f, 1.0f, 0.0f);
                    }
                    GlStateManager.func_179114_b(((Entity) func_175606_aa).field_70125_A - f2, 1.0f, 0.0f, 0.0f);
                    GlStateManager.func_179114_b(((Entity) func_175606_aa).field_70177_z - f1, 0.0f, 1.0f, 0.0f);
                    GlStateManager.func_179109_b(0.0f, 0.0f, (float) (-d3));
                    GlStateManager.func_179114_b(f1 - ((Entity) func_175606_aa).field_70177_z, 0.0f, 1.0f, 0.0f);
                    GlStateManager.func_179114_b(f2 - ((Entity) func_175606_aa).field_70125_A, 1.0f, 0.0f, 0.0f);
                }
            } else {
                GlStateManager.func_179109_b(0.0f, 0.0f, -0.1f);
            }
            if (!this.field_78531_r.field_71474_y.field_74325_U) {
                float yaw = ((Entity) func_175606_aa).field_70126_B + ((((Entity) func_175606_aa).field_70177_z - ((Entity) func_175606_aa).field_70126_B) * partialTicks) + 180.0f;
                float pitch = ((Entity) func_175606_aa).field_70127_C + ((((Entity) func_175606_aa).field_70125_A - ((Entity) func_175606_aa).field_70127_C) * partialTicks);
                if (func_175606_aa instanceof EntityAnimal) {
                    EntityAnimal entityanimal = (EntityAnimal) func_175606_aa;
                    yaw = entityanimal.field_70758_at + ((entityanimal.field_70759_as - entityanimal.field_70758_at) * partialTicks) + 180.0f;
                }
                Block block = ActiveRenderInfo.func_180786_a(this.field_78531_r.field_71441_e, func_175606_aa, partialTicks);
                EntityViewRenderEvent.CameraSetup event = new EntityViewRenderEvent.CameraSetup((EntityRenderer) this, func_175606_aa, block, partialTicks, yaw, pitch, 0.0f);
                MinecraftForge.EVENT_BUS.post(event);
                GlStateManager.func_179114_b(event.roll, 0.0f, 0.0f, 1.0f);
                GlStateManager.func_179114_b(event.pitch, 1.0f, 0.0f, 0.0f);
                GlStateManager.func_179114_b(event.yaw, 0.0f, 1.0f, 0.0f);
            }
            GlStateManager.func_179109_b(0.0f, -f, 0.0f);
            double d0 = ((Entity) func_175606_aa).field_70169_q + ((((Entity) func_175606_aa).field_70165_t - ((Entity) func_175606_aa).field_70169_q) * partialTicks);
            double d1 = ((Entity) func_175606_aa).field_70167_r + ((((Entity) func_175606_aa).field_70163_u - ((Entity) func_175606_aa).field_70167_r) * partialTicks) + f;
            double d2 = ((Entity) func_175606_aa).field_70166_s + ((((Entity) func_175606_aa).field_70161_v - ((Entity) func_175606_aa).field_70166_s) * partialTicks);
            this.field_78500_U = this.field_78531_r.field_71438_f.func_72721_a(d0, d1, d2, partialTicks);
        }
    }

    @Inject(method = {"setupCameraTransform"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;setupViewBobbing(F)V", shift = AbstractC1790At.Shift.BEFORE)})
    private void setupCameraViewBobbingBefore(CallbackInfo callbackInfo) {
        TargetMark targetMark = (TargetMark) LiquidBounce.moduleManager.getModule(TargetMark.class);
        KillAura aura = (KillAura) LiquidBounce.moduleManager.getModule(KillAura.class);
        if ((targetMark == null || aura == null || !targetMark.modeValue.get().equalsIgnoreCase("tracers") || aura.getTargetModeValue().get().equalsIgnoreCase("multi") || aura.getTarget() == null) && !LiquidBounce.moduleManager.getModule(Tracers.class).getState()) {
            return;
        }
        GL11.glPushMatrix();
    }

    @Inject(method = {"setupCameraTransform"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;setupViewBobbing(F)V", shift = AbstractC1790At.Shift.AFTER)})
    private void setupCameraViewBobbingAfter(CallbackInfo callbackInfo) {
        TargetMark targetMark = (TargetMark) LiquidBounce.moduleManager.getModule(TargetMark.class);
        KillAura aura = (KillAura) LiquidBounce.moduleManager.getModule(KillAura.class);
        if ((targetMark == null || aura == null || !targetMark.modeValue.get().equalsIgnoreCase("tracers") || aura.getTargetModeValue().get().equalsIgnoreCase("multi") || aura.getTarget() == null) && !LiquidBounce.moduleManager.getModule(Tracers.class).getState()) {
            return;
        }
        GL11.glPopMatrix();
    }

    @Inject(method = {"getMouseOver"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void getMouseOver(float p_getMouseOver_1_, CallbackInfo ci) {
        Entity entity = this.field_78531_r.func_175606_aa();
        if (entity != null && this.field_78531_r.field_71441_e != null) {
            this.field_78531_r.field_71424_I.func_76320_a("pick");
            this.field_78531_r.field_147125_j = null;
            Reach reach = (Reach) LiquidBounce.moduleManager.getModule(Reach.class);
            double d0 = reach.getState() ? reach.getMaxRange() : this.field_78531_r.field_71442_b.func_78757_d();
            this.field_78531_r.field_71476_x = entity.func_174822_a(reach.getState() ? reach.getBuildReachValue().get().floatValue() : d0, p_getMouseOver_1_);
            double d1 = d0;
            Vec3 vec3 = entity.func_174824_e(p_getMouseOver_1_);
            boolean flag = false;
            if (this.field_78531_r.field_71442_b.func_78749_i()) {
                d0 = 6.0d;
                d1 = 6.0d;
            } else if (d0 > 3.0d) {
                flag = true;
            }
            if (this.field_78531_r.field_71476_x != null) {
                d1 = this.field_78531_r.field_71476_x.field_72307_f.func_72438_d(vec3);
            }
            if (reach.getState()) {
                d1 = reach.getCombatReachValue().get().floatValue();
                MovingObjectPosition movingObjectPosition = entity.func_174822_a(d1, p_getMouseOver_1_);
                if (movingObjectPosition != null) {
                    d1 = movingObjectPosition.field_72307_f.func_72438_d(vec3);
                }
            }
            Vec3 vec31 = entity.func_70676_i(p_getMouseOver_1_);
            Vec3 vec32 = vec3.func_72441_c(vec31.field_72450_a * d0, vec31.field_72448_b * d0, vec31.field_72449_c * d0);
            this.field_78528_u = null;
            Vec3 vec33 = null;
            List<Entity> list = this.field_78531_r.field_71441_e.func_175674_a(entity, entity.func_174813_aQ().func_72321_a(vec31.field_72450_a * d0, vec31.field_72448_b * d0, vec31.field_72449_c * d0).func_72314_b(1.0f, 1.0f, 1.0f), Predicates.and(EntitySelectors.field_180132_d, p_apply_1_ -> {
                return p_apply_1_.func_70067_L();
            }));
            double d2 = d1;
            for (int j = 0; j < list.size(); j++) {
                Entity entity1 = list.get(j);
                float f1 = entity1.func_70111_Y();
                AxisAlignedBB axisalignedbb = entity1.func_174813_aQ().func_72314_b(f1, f1, f1);
                MovingObjectPosition movingobjectposition = axisalignedbb.func_72327_a(vec3, vec32);
                if (axisalignedbb.func_72318_a(vec3)) {
                    if (d2 >= 0.0d) {
                        this.field_78528_u = entity1;
                        vec33 = movingobjectposition == null ? vec3 : movingobjectposition.field_72307_f;
                        d2 = 0.0d;
                    }
                } else if (movingobjectposition != null) {
                    double d3 = vec3.func_72438_d(movingobjectposition.field_72307_f);
                    if (d3 < d2 || d2 == 0.0d) {
                        if (entity1 == entity.field_70154_o && !entity.canRiderInteract()) {
                            if (d2 == 0.0d) {
                                this.field_78528_u = entity1;
                                vec33 = movingobjectposition.field_72307_f;
                            }
                        } else {
                            this.field_78528_u = entity1;
                            vec33 = movingobjectposition.field_72307_f;
                            d2 = d3;
                        }
                    }
                }
            }
            if (this.field_78528_u != null && flag) {
                if (vec3.func_72438_d(vec33) > (reach.getState() ? reach.getCombatReachValue().get().floatValue() : 3.0d)) {
                    this.field_78528_u = null;
                    this.field_78531_r.field_71476_x = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec33, (EnumFacing) null, new BlockPos(vec33));
                }
            }
            if (this.field_78528_u != null && (d2 < d1 || this.field_78531_r.field_71476_x == null)) {
                this.field_78531_r.field_71476_x = new MovingObjectPosition(this.field_78528_u, vec33);
                if ((this.field_78528_u instanceof EntityLivingBase) || (this.field_78528_u instanceof EntityItemFrame)) {
                    this.field_78531_r.field_147125_j = this.field_78528_u;
                }
            }
            this.field_78531_r.field_71424_I.func_76319_b();
        }
        ci.cancel();
    }
}
