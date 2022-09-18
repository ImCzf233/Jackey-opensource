package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import de.enzaxd.viaforge.ViaForge;
import java.util.Iterator;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.misc.Patcher;
import net.ccbluex.liquidbounce.features.module.modules.movement.AirJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.LiquidWalk;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoJumpDelay;
import net.ccbluex.liquidbounce.features.module.modules.movement.Sprint;
import net.ccbluex.liquidbounce.features.module.modules.movement.TargetStrafe;
import net.ccbluex.liquidbounce.features.module.modules.render.Animations;
import net.ccbluex.liquidbounce.features.module.modules.render.AntiBlind;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin({EntityLivingBase.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/entity/MixinEntityLivingBase.class */
public abstract class MixinEntityLivingBase extends MixinEntity {
    @Shadow
    private int field_70773_bE;
    @Shadow
    protected boolean field_70703_bu;
    @Shadow
    public int field_110158_av;
    @Shadow
    public boolean field_82175_bq;
    @Shadow
    public float field_70733_aJ;

    @Shadow
    protected abstract float func_175134_bD();

    @Shadow
    public abstract PotionEffect func_70660_b(Potion potion);

    @Shadow
    public abstract boolean func_70644_a(Potion potion);

    @Shadow
    public abstract void func_180433_a(double d, boolean z, Block block, BlockPos blockPos);

    @Shadow
    public abstract float func_110143_aJ();

    @Shadow
    public abstract ItemStack func_70694_bm();

    @Shadow
    protected abstract void func_70629_bd();

    @Shadow
    public void func_70636_d() {
    }

    @Inject(method = {"updatePotionEffects"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/potion/PotionEffect;onUpdate(Lnet/minecraft/entity/EntityLivingBase;)Z")}, locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void checkPotionEffect(CallbackInfo ci, Iterator<Integer> iterator, Integer integer, PotionEffect potioneffect) {
        if (potioneffect == null) {
            ci.cancel();
        }
    }

    @Overwrite
    protected void func_70664_aZ() {
        JumpEvent jumpEvent = new JumpEvent(func_175134_bD());
        LiquidBounce.eventManager.callEvent(jumpEvent);
        if (jumpEvent.isCancelled()) {
            return;
        }
        this.field_70181_x = jumpEvent.getMotion();
        if (func_70644_a(Potion.field_76430_j)) {
            this.field_70181_x += (func_70660_b(Potion.field_76430_j).func_76458_c() + 1) * 0.1f;
        }
        if (func_70051_ag()) {
            KillAura auraMod = (KillAura) LiquidBounce.moduleManager.getModule(KillAura.class);
            Sprint sprintMod = (Sprint) LiquidBounce.moduleManager.getModule(Sprint.class);
            TargetStrafe tsMod = (TargetStrafe) LiquidBounce.moduleManager.getModule(TargetStrafe.class);
            float yaw = this.field_70177_z;
            if (tsMod.getCanStrafe()) {
                yaw = tsMod.getMovingYaw();
            } else if (Patcher.jumpPatch.get().booleanValue()) {
                if (auraMod.getState() && auraMod.getRotationStrafeValue().get().equalsIgnoreCase("strict") && auraMod.getTarget() != null) {
                    yaw = RotationUtils.targetRotation != null ? RotationUtils.targetRotation.getYaw() : RotationUtils.serverRotation != null ? RotationUtils.serverRotation.getYaw() : yaw;
                } else if (sprintMod.getState() && sprintMod.getAllDirectionsValue().get().booleanValue() && sprintMod.getMoveDirPatchValue().get().booleanValue()) {
                    yaw = MovementUtils.getRawDirection();
                }
            }
            float f = yaw * 0.017453292f;
            this.field_70159_w -= MathHelper.func_76126_a(f) * 0.2f;
            this.field_70179_y += MathHelper.func_76134_b(f) * 0.2f;
        }
        this.field_70160_al = true;
    }

    @Inject(method = {"onLivingUpdate"}, m23at = {@AbstractC1790At("HEAD")})
    private void headLiving(CallbackInfo callbackInfo) {
        if (LiquidBounce.moduleManager.getModule(NoJumpDelay.class).getState()) {
            this.field_70773_bE = 0;
        }
    }

    @ModifyConstant(method = {"onLivingUpdate"}, constant = {@Constant(doubleValue = 0.005d)})
    private double refactor1_9MovementThreshold(double constant) {
        if (ViaForge.getInstance().getVersion() <= 47) {
            return 0.005d;
        }
        return 0.003d;
    }

    @Inject(method = {"onLivingUpdate"}, m23at = {@AbstractC1790At(value = "FIELD", target = "Lnet/minecraft/entity/EntityLivingBase;isJumping:Z", ordinal = 1)})
    private void onJumpSection(CallbackInfo callbackInfo) {
        if (LiquidBounce.moduleManager.getModule(AirJump.class).getState() && this.field_70703_bu && this.field_70773_bE == 0) {
            func_70664_aZ();
            this.field_70773_bE = 10;
        }
        LiquidWalk liquidWalk = (LiquidWalk) LiquidBounce.moduleManager.getModule(LiquidWalk.class);
        if (liquidWalk.getState() && !this.field_70703_bu && !func_70093_af() && func_70090_H() && liquidWalk.modeValue.get().equalsIgnoreCase("Swim")) {
            func_70629_bd();
        }
    }

    @Inject(method = {"getLook"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void getLook(CallbackInfoReturnable<Vec3> callbackInfoReturnable) {
        if (((EntityLivingBase) this) instanceof EntityPlayerSP) {
            callbackInfoReturnable.setReturnValue(func_174806_f(this.field_70125_A, this.field_70177_z));
        }
    }

    @Inject(method = {"isPotionActive(Lnet/minecraft/potion/Potion;)Z"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void isPotionActive(Potion p_isPotionActive_1_, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        AntiBlind antiBlind = (AntiBlind) LiquidBounce.moduleManager.getModule(AntiBlind.class);
        if ((p_isPotionActive_1_ == Potion.field_76431_k || p_isPotionActive_1_ == Potion.field_76440_q) && antiBlind.getState() && antiBlind.getConfusionEffect().get().booleanValue()) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

    @Overwrite
    private int func_82166_i() {
        int speed = LiquidBounce.moduleManager.getModule(Animations.class).getState() ? 2 + (20 - Animations.SpeedSwing.get().intValue()) : 6;
        return func_70644_a(Potion.field_76422_e) ? speed - ((1 + func_70660_b(Potion.field_76422_e).func_76458_c()) * 1) : func_70644_a(Potion.field_76419_f) ? speed + ((1 + func_70660_b(Potion.field_76419_f).func_76458_c()) * 2) : speed;
    }
}
