package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.thealtening.AltService;
import com.thealtening.api.TheAltening;
import com.thealtening.api.data.AccountData;
import java.net.Proxy;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import me.liuli.elixir.account.CrackedAccount;
import me.liuli.elixir.account.MinecraftAccount;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.features.special.AntiForge;
import net.ccbluex.liquidbounce.features.special.AutoReconnect;
import net.ccbluex.liquidbounce.p004ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.p004ui.client.altmanager.menus.GuiLoginProgress;
import net.ccbluex.liquidbounce.p004ui.client.altmanager.menus.altgenerator.GuiTheAltening;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.ccbluex.liquidbounce.utils.SessionUtils;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.util.Session;
import net.minecraftforge.fml.client.config.GuiSlider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiDisconnected.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiDisconnected.class */
public abstract class MixinGuiDisconnected extends MixinGuiScreen {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#0");
    @Shadow
    private int field_175353_i;
    private GuiButton reconnectButton;
    private GuiSlider autoReconnectDelaySlider;
    private GuiButton forgeBypassButton;
    private int reconnectTimer;

    @Inject(method = {"initGui"}, m23at = {@AbstractC1790At("RETURN")})
    private void initGui(CallbackInfo callbackInfo) {
        this.reconnectTimer = 0;
        SessionUtils.handleConnection();
        List<GuiButton> list = this.field_146292_n;
        GuiButton guiButton = new GuiButton(1, (this.field_146294_l / 2) - 100, (this.field_146295_m / 2) + (this.field_175353_i / 2) + this.field_146289_q.field_78288_b + 22, 98, 20, "Reconnect");
        this.reconnectButton = guiButton;
        list.add(guiButton);
        drawReconnectDelaySlider();
        this.field_146292_n.add(new GuiButton(3, (this.field_146294_l / 2) - 100, (this.field_146295_m / 2) + (this.field_175353_i / 2) + this.field_146289_q.field_78288_b + 44, 98, 20, GuiTheAltening.Companion.getApiKey().isEmpty() ? "Random alt" : "New TheAltening alt"));
        this.field_146292_n.add(new GuiButton(4, (this.field_146294_l / 2) + 2, (this.field_146295_m / 2) + (this.field_175353_i / 2) + this.field_146289_q.field_78288_b + 44, 98, 20, "Random cracked"));
        List<GuiButton> list2 = this.field_146292_n;
        GuiButton guiButton2 = new GuiButton(5, (this.field_146294_l / 2) - 100, (this.field_146295_m / 2) + (this.field_175353_i / 2) + this.field_146289_q.field_78288_b + 66, "AntiForge: " + (AntiForge.enabled ? "On" : "Off"));
        this.forgeBypassButton = guiButton2;
        list2.add(guiButton2);
        updateSliderText();
    }

    @Inject(method = {"actionPerformed"}, m23at = {@AbstractC1790At("HEAD")})
    private void actionPerformed(GuiButton button, CallbackInfo callbackInfo) {
        switch (button.field_146127_k) {
            case 1:
                ServerUtils.connectToLastServer();
                return;
            case 2:
            default:
                return;
            case 3:
                if (!GuiTheAltening.Companion.getApiKey().isEmpty()) {
                    String apiKey = GuiTheAltening.Companion.getApiKey();
                    TheAltening theAltening = new TheAltening(apiKey);
                    try {
                        AccountData account = theAltening.getAccountData();
                        GuiAltManager.Companion.getAltService().switchService(AltService.EnumAltService.THEALTENING);
                        YggdrasilUserAuthentication yggdrasilUserAuthentication = new YggdrasilUserAuthentication(new YggdrasilAuthenticationService(Proxy.NO_PROXY, ""), Agent.MINECRAFT);
                        yggdrasilUserAuthentication.setUsername(account.getToken());
                        yggdrasilUserAuthentication.setPassword(LiquidBounce.CLIENT_NAME);
                        yggdrasilUserAuthentication.logIn();
                        this.field_146297_k.field_71449_j = new Session(yggdrasilUserAuthentication.getSelectedProfile().getName(), yggdrasilUserAuthentication.getSelectedProfile().getId().toString(), yggdrasilUserAuthentication.getAuthenticatedToken(), "mojang");
                        LiquidBounce.eventManager.callEvent(new SessionEvent());
                        ServerUtils.connectToLastServer();
                        return;
                    } catch (Throwable throwable) {
                        ClientUtils.getLogger().error("Failed to login into random account from TheAltening.", throwable);
                    }
                }
                List<MinecraftAccount> accounts = LiquidBounce.fileManager.accountsConfig.getAccounts();
                if (!accounts.isEmpty()) {
                    MinecraftAccount minecraftAccount = accounts.get(new Random().nextInt(accounts.size()));
                    this.field_146297_k.func_147108_a(new GuiLoginProgress(minecraftAccount, () -> {
                        this.field_146297_k.func_152344_a(() -> {
                            LiquidBounce.eventManager.callEvent(new SessionEvent());
                            ServerUtils.connectToLastServer();
                        });
                        return null;
                    }, e -> {
                        this.field_146297_k.func_152344_a(()
                        /*  JADX ERROR: Method code generation error
                            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x000b: INVOKE  
                              (wrap: net.minecraft.client.Minecraft : 0x0001: IGET  (r0v1 net.minecraft.client.Minecraft A[REMOVE]) = 
                              (r4v0 'this' net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGuiDisconnected A[D('this' net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGuiDisconnected), IMMUTABLE_TYPE, THIS])
                             net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGuiDisconnected.field_146297_k net.minecraft.client.Minecraft)
                              (wrap: java.lang.Runnable : 0x0006: INVOKE_CUSTOM (r1v1 java.lang.Runnable A[REMOVE]) = 
                              (r4v0 'this' net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGuiDisconnected A[D('this' net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGuiDisconnected), DONT_INLINE, IMMUTABLE_TYPE, THIS])
                              (r5v0 'e' java.lang.Exception A[D('e' java.lang.Exception), DONT_INLINE])
                            
                             handle type: INVOKE_DIRECT
                             lambda: java.lang.Runnable.run():void
                             call insn: ?: INVOKE  (r1 I:net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGuiDisconnected), (r2 I:java.lang.Exception) type: DIRECT call: net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGuiDisconnected.lambda$null$2(java.lang.Exception):void)
                             type: VIRTUAL call: net.minecraft.client.Minecraft.func_152344_a(java.lang.Runnable):com.google.common.util.concurrent.ListenableFuture in method: net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGuiDisconnected.lambda$actionPerformed$3(java.lang.Exception):kotlin.Unit, file: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiDisconnected.class
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:287)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:91)
                            	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                            	at jadx.core.dex.regions.Region.generate(Region.java:35)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:286)
                            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:265)
                            	at jadx.core.codegen.InsnGen.makeInlinedLambdaMethod(InsnGen.java:957)
                            	at jadx.core.codegen.InsnGen.makeInvokeLambda(InsnGen.java:857)
                            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:791)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:399)
                            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:141)
                            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:117)
                            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:104)
                            	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1029)
                            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:745)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:395)
                            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:141)
                            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:117)
                            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:104)
                            	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1029)
                            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:830)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:399)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:280)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:91)
                            	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                            	at jadx.core.dex.regions.Region.generate(Region.java:35)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:80)
                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:123)
                            	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                            	at jadx.core.dex.regions.Region.generate(Region.java:35)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                            	at jadx.core.dex.regions.Region.generate(Region.java:35)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:80)
                            	at jadx.core.codegen.RegionGen.makeSwitch(RegionGen.java:267)
                            	at jadx.core.dex.regions.SwitchRegion.generate(SwitchRegion.java:79)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                            	at jadx.core.dex.regions.Region.generate(Region.java:35)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:286)
                            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:265)
                            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:369)
                            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
                            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
                            	at java.base/java.util.ArrayList.forEach(Unknown Source)
                            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
                            	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
                            Caused by: java.lang.IndexOutOfBoundsException: Index 1 out of bounds for length 1
                            	at java.base/jdk.internal.util.Preconditions.outOfBounds(Unknown Source)
                            	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Unknown Source)
                            	at java.base/jdk.internal.util.Preconditions.checkIndex(Unknown Source)
                            	at java.base/java.util.Objects.checkIndex(Unknown Source)
                            	at java.base/java.util.ArrayList.get(Unknown Source)
                            	at jadx.core.codegen.InsnGen.makeInlinedLambdaMethod(InsnGen.java:952)
                            	at jadx.core.codegen.InsnGen.makeInvokeLambda(InsnGen.java:857)
                            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:791)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:399)
                            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:141)
                            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:117)
                            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:104)
                            	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1029)
                            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:830)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:399)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:280)
                            	... 54 more
                            */
                        /*
                            this = this;
                            r0 = r4
                            net.minecraft.client.Minecraft r0 = r0.field_146297_k
                            r1 = r4
                            r2 = r5
                            kotlin.Unit r1 = () -> { // java.lang.Runnable.run():void
                                r1.lambda$null$2(r2);
                            }
                            com.google.common.util.concurrent.ListenableFuture r0 = r0.func_152344_a(r1)
                            r0 = 0
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGuiDisconnected.lambda$actionPerformed$3(java.lang.Exception):kotlin.Unit");
                    }, () -> {
                        return null;
                    }));
                    return;
                }
                return;
            case 4:
                CrackedAccount crackedAccount = new CrackedAccount();
                crackedAccount.setName(RandomUtils.randomString(RandomUtils.nextInt(5, 16)));
                crackedAccount.update();
                this.field_146297_k.field_71449_j = new Session(crackedAccount.getSession().getUsername(), crackedAccount.getSession().getUuid(), crackedAccount.getSession().getToken(), crackedAccount.getSession().getType());
                LiquidBounce.eventManager.callEvent(new SessionEvent());
                ServerUtils.connectToLastServer();
                return;
            case 5:
                AntiForge.enabled = !AntiForge.enabled;
                this.forgeBypassButton.field_146126_j = "AntiForge: " + (AntiForge.enabled ? "On" : "Off");
                LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.valuesConfig);
                return;
        }
    }

    @Override // net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGuiScreen
    public void func_73876_c() {
        if (AutoReconnect.INSTANCE.isEnabled()) {
            this.reconnectTimer++;
            if (this.reconnectTimer > AutoReconnect.INSTANCE.getDelay() / 50) {
                ServerUtils.connectToLastServer();
            }
        }
    }

    @Inject(method = {"drawScreen"}, m23at = {@AbstractC1790At("RETURN")})
    private void drawScreen(CallbackInfo callbackInfo) {
        if (AutoReconnect.INSTANCE.isEnabled()) {
            updateReconnectButton();
        }
        Fonts.fontSFUI40.drawCenteredString("Player: §7" + this.field_146297_k.field_71449_j.func_111285_a() + "§r, IP: §7" + ServerUtils.serverData.field_78845_b, this.field_146294_l / 2.0f, (this.field_146295_m / 2.0f) + (this.field_175353_i / 2.0f) + this.field_146289_q.field_78288_b + 96.0f, -1, true);
        Fonts.fontSFUI40.drawCenteredString("Play time before kick: §7" + SessionUtils.getFormatLastSessionTime(), this.field_146294_l / 2.0f, (this.field_146295_m / 2.0f) + (this.field_175353_i / 2.0f) + (this.field_146289_q.field_78288_b * 2.0f) + 98.0f, -1, true);
    }

    private void drawReconnectDelaySlider() {
        List<GuiButton> list = this.field_146292_n;
        GuiSlider guiSlider = new GuiSlider(2, (this.field_146294_l / 2) + 2, (this.field_146295_m / 2) + (this.field_175353_i / 2) + this.field_146289_q.field_78288_b + 22, 98, 20, "Auto: ", "ms", 1000.0d, 60000.0d, AutoReconnect.INSTANCE.getDelay(), false, true, guiSlider2 -> {
            AutoReconnect.INSTANCE.setDelay(guiSlider2.getValueInt());
            this.reconnectTimer = 0;
            updateReconnectButton();
            updateSliderText();
        });
        this.autoReconnectDelaySlider = guiSlider;
        list.add(guiSlider);
    }

    private void updateSliderText() {
        if (this.autoReconnectDelaySlider == null) {
            return;
        }
        if (!AutoReconnect.INSTANCE.isEnabled()) {
            this.autoReconnectDelaySlider.field_146126_j = "Auto: Off";
            return;
        }
        this.autoReconnectDelaySlider.field_146126_j = "Auto: " + DECIMAL_FORMAT.format(AutoReconnect.INSTANCE.getDelay() / 1000.0d) + "s";
    }

    private void updateReconnectButton() {
        if (this.reconnectButton != null) {
            this.reconnectButton.field_146126_j = "Reconnect" + (AutoReconnect.INSTANCE.isEnabled() ? " (" + ((AutoReconnect.INSTANCE.getDelay() / 1000) - (this.reconnectTimer / 20)) + ")" : "");
        }
    }
}
