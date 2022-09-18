package net.ccbluex.liquidbounce.injection.forge.mixins.client;

import de.enzaxd.viaforge.ViaForge;
import de.enzaxd.viaforge.util.AttackOrder;
import java.awt.Component;
import java.awt.HeadlessException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.swing.JOptionPane;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.ClickBlockEvent;
import net.ccbluex.liquidbounce.event.KeyEvent;
import net.ccbluex.liquidbounce.event.ScreenEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoClicker;
import net.ccbluex.liquidbounce.features.module.modules.exploit.AbortBreaking;
import net.ccbluex.liquidbounce.features.module.modules.exploit.MultiActions;
import net.ccbluex.liquidbounce.features.module.modules.misc.Patcher;
import net.ccbluex.liquidbounce.features.module.modules.world.FastPlace;
import net.ccbluex.liquidbounce.injection.forge.mixins.accessors.MinecraftForgeClientAccessor;
import net.ccbluex.liquidbounce.utils.CPSCounter;
import net.ccbluex.liquidbounce.utils.misc.HWIDUtils;
import net.ccbluex.liquidbounce.utils.misc.WebUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.stream.IStream;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.MinecraftForgeClient;
import org.apache.commons.lang3.SystemUtils;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Minecraft.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/client/MixinMinecraft.class */
public abstract class MixinMinecraft {
    @Shadow
    public GuiScreen field_71462_r;
    @Shadow
    private boolean field_71431_Q;
    @Shadow
    public boolean field_71454_w;
    @Shadow
    private int field_71429_W;
    @Shadow
    public MovingObjectPosition field_71476_x;
    @Shadow
    public WorldClient field_71441_e;
    @Shadow
    public EntityPlayerSP field_71439_g;
    @Shadow
    public EffectRenderer field_71452_i;
    @Shadow
    public EntityRenderer field_71460_t;
    @Shadow
    public PlayerControllerMP field_71442_b;
    @Shadow
    public int field_71443_c;
    @Shadow
    public int field_71440_d;
    @Shadow
    public int field_71467_ac;
    @Shadow
    public GameSettings field_71474_y;
    private long lastFrame = getTime();

    @Shadow
    public abstract IResourceManager func_110442_L();

    @Inject(method = {"<init>"}, m23at = {@AbstractC1790At("RETURN")})
    public void injectConstructor(GameConfiguration p_i45547_1_, CallbackInfo ci) {
        try {
            ViaForge.getInstance().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Inject(method = {"run"}, m23at = {@AbstractC1790At("HEAD")})
    private void init(CallbackInfo callbackInfo) {
        if (this.field_71443_c < 1067) {
            this.field_71443_c = 1067;
        }
        if (this.field_71440_d < 622) {
            this.field_71440_d = 622;
        }
        try {
            if (!WebUtils.get("https://gitee.com/shen-jiaqiyyds/jackey-j/raw/master/Hwid").contains(HWIDUtils.getHWID())) {
                JOptionPane.showInputDialog((Component) null, "验证失败 请联系QQ826685288", HWIDUtils.getHWID());
                System.exit(0);
            }
        } catch (HeadlessException | IOException | NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog((Component) null, "网络获取错误 请联系QQ826685288");
            System.exit(0);
        }
    }

    @Inject(method = {"startGame"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;checkGLError(Ljava/lang/String;)V", ordinal = 2, shift = AbstractC1790At.Shift.AFTER)})
    private void startGame(CallbackInfo callbackInfo) {
        LiquidBounce.INSTANCE.startClient();
    }

    @Inject(method = {"createDisplay"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V", shift = AbstractC1790At.Shift.AFTER)})
    private void createDisplay(CallbackInfo callbackInfo) {
        Display.setTitle("Jackey Client  b2");
    }

    @Inject(method = {"loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V"}, m23at = {@AbstractC1790At("HEAD")})
    private void clearLoadedMaps(WorldClient worldClientIn, String loadingMessage, CallbackInfo ci) {
        if (worldClientIn != this.field_71441_e) {
            this.field_71460_t.func_147701_i().func_148249_a();
        }
    }

    @Inject(method = {"loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V"}, m23at = {@AbstractC1790At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;theWorld:Lnet/minecraft/client/multiplayer/WorldClient;", opcode = 181, shift = AbstractC1790At.Shift.AFTER)})
    private void clearRenderCache(CallbackInfo ci) {
        MinecraftForgeClient.getRenderPass();
        MinecraftForgeClientAccessor.getRegionCache().invalidateAll();
        MinecraftForgeClientAccessor.getRegionCache().cleanUp();
    }

    @Redirect(method = {"runGameLoop"}, m17at = @AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/client/stream/IStream;func_152935_j()V"))
    private void skipTwitchCode1(IStream instance) {
    }

    @Redirect(method = {"runGameLoop"}, m17at = @AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/client/stream/IStream;func_152922_k()V"))
    private void skipTwitchCode2(IStream instance) {
    }

    @Inject(method = {"displayGuiScreen"}, m23at = {@AbstractC1790At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;currentScreen:Lnet/minecraft/client/gui/GuiScreen;", shift = AbstractC1790At.Shift.AFTER)})
    private void displayGuiScreen(CallbackInfo callbackInfo) {
        if ((this.field_71462_r instanceof GuiMainMenu) || (this.field_71462_r != null && this.field_71462_r.getClass().getName().startsWith("net.labymod") && this.field_71462_r.getClass().getSimpleName().equals("ModGuiMainMenu"))) {
            this.field_71462_r = new net.ccbluex.liquidbounce.p004ui.client.GuiMainMenu();
            ScaledResolution scaledResolution = new ScaledResolution(Minecraft.func_71410_x());
            this.field_71462_r.func_146280_a(Minecraft.func_71410_x(), scaledResolution.func_78326_a(), scaledResolution.func_78328_b());
            this.field_71454_w = false;
        }
        LiquidBounce.eventManager.callEvent(new ScreenEvent(this.field_71462_r));
    }

    @Inject(method = {"runGameLoop"}, m23at = {@AbstractC1790At("HEAD")})
    private void runGameLoop(CallbackInfo callbackInfo) {
        long currentTime = getTime();
        int deltaTime = (int) (currentTime - this.lastFrame);
        this.lastFrame = currentTime;
        RenderUtils.deltaTime = deltaTime;
    }

    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    @Inject(method = {"runTick"}, m23at = {@AbstractC1790At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;joinPlayerCounter:I", shift = AbstractC1790At.Shift.BEFORE)})
    private void onTick(CallbackInfo callbackInfo) {
        LiquidBounce.eventManager.callEvent(new TickEvent());
    }

    @Inject(method = {"runTick"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;dispatchKeypresses()V", shift = AbstractC1790At.Shift.AFTER)})
    private void onKey(CallbackInfo callbackInfo) {
        if (Keyboard.getEventKeyState() && this.field_71462_r == null) {
            LiquidBounce.eventManager.callEvent(new KeyEvent(Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey()));
        }
    }

    @Inject(method = {"sendClickBlockToController"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/util/MovingObjectPosition;getBlockPos()Lnet/minecraft/util/BlockPos;")})
    private void onClickBlock(CallbackInfo callbackInfo) {
        if (this.field_71429_W == 0 && this.field_71441_e.func_180495_p(this.field_71476_x.func_178782_a()).func_177230_c().func_149688_o() != Material.field_151579_a) {
            LiquidBounce.eventManager.callEvent(new ClickBlockEvent(this.field_71476_x.func_178782_a(), this.field_71476_x.field_178784_b));
        }
    }

    @Inject(method = {"shutdown"}, m23at = {@AbstractC1790At("HEAD")})
    private void shutdown(CallbackInfo callbackInfo) {
        LiquidBounce.INSTANCE.stopClient();
    }

    @Inject(method = {"clickMouse"}, m23at = {@AbstractC1790At("HEAD")})
    private void clickMouse(CallbackInfo callbackInfo) {
        CPSCounter.registerClick(CPSCounter.MouseButton.LEFT);
        if (Patcher.noHitDelay.get().booleanValue() || LiquidBounce.moduleManager.getModule(AutoClicker.class).getState()) {
            this.field_71429_W = 0;
        }
    }

    @Redirect(method = {"clickMouse"}, m17at = @AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;swingItem()V"))
    private void fixAttackOrder_VanillaSwing() {
        AttackOrder.sendConditionalSwing(this.field_71476_x);
    }

    @Redirect(method = {"clickMouse"}, m17at = @AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;attackEntity(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/entity/Entity;)V"))
    private void fixAttackOrder_VanillaAttack() {
        AttackOrder.sendFixedAttack(this.field_71439_g, this.field_71476_x.field_72308_g);
    }

    @Inject(method = {"middleClickMouse"}, m23at = {@AbstractC1790At("HEAD")})
    private void middleClickMouse(CallbackInfo ci) {
        CPSCounter.registerClick(CPSCounter.MouseButton.MIDDLE);
    }

    @Inject(method = {"rightClickMouse"}, m23at = {@AbstractC1790At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;rightClickDelayTimer:I", shift = AbstractC1790At.Shift.AFTER)})
    private void rightClickMouse(CallbackInfo callbackInfo) {
        CPSCounter.registerClick(CPSCounter.MouseButton.RIGHT);
        FastPlace fastPlace = (FastPlace) LiquidBounce.moduleManager.getModule(FastPlace.class);
        if (fastPlace.getState()) {
            this.field_71467_ac = fastPlace.getSpeedValue().get().intValue();
        }
    }

    @Inject(method = {"loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V"}, m23at = {@AbstractC1790At("HEAD")})
    private void loadWorld(WorldClient p_loadWorld_1_, String p_loadWorld_2_, CallbackInfo callbackInfo) {
        LiquidBounce.eventManager.callEvent(new WorldEvent(p_loadWorld_1_));
    }

    @Inject(method = {"toggleFullscreen"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setFullscreen(Z)V", remap = false)})
    private void resolveScreenState(CallbackInfo ci) {
        if (!this.field_71431_Q && SystemUtils.IS_OS_WINDOWS) {
            Display.setResizable(false);
            Display.setResizable(true);
        }
    }

    @Redirect(method = {"dispatchKeypresses"}, m17at = @AbstractC1790At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;getEventCharacter()C", remap = false))
    private char resolveForeignKeyboards() {
        return (char) (Keyboard.getEventCharacter() + 256);
    }

    @Overwrite
    private void func_147115_a(boolean leftClick) {
        if (!leftClick) {
            this.field_71429_W = 0;
        }
        if (this.field_71429_W <= 0) {
            if (!this.field_71439_g.func_71039_bw() || LiquidBounce.moduleManager.getModule(MultiActions.class).getState()) {
                if (!leftClick || this.field_71476_x == null || this.field_71476_x.field_72313_a != MovingObjectPosition.MovingObjectType.BLOCK) {
                    if (!LiquidBounce.moduleManager.getModule(AbortBreaking.class).getState()) {
                        this.field_71442_b.func_78767_c();
                        return;
                    }
                    return;
                }
                BlockPos blockPos = this.field_71476_x.func_178782_a();
                if (this.field_71429_W == 0) {
                    LiquidBounce.eventManager.callEvent(new ClickBlockEvent(blockPos, this.field_71476_x.field_178784_b));
                }
                if (this.field_71441_e.func_180495_p(blockPos).func_177230_c().func_149688_o() != Material.field_151579_a && this.field_71442_b.func_180512_c(blockPos, this.field_71476_x.field_178784_b)) {
                    this.field_71452_i.func_180532_a(blockPos, this.field_71476_x.field_178784_b);
                    this.field_71439_g.func_71038_i();
                }
            }
        }
    }

    @ModifyConstant(method = {"getLimitFramerate"}, constant = {@Constant(intValue = 30)})
    public int getLimitFramerate(int constant) {
        return 60;
    }
}
