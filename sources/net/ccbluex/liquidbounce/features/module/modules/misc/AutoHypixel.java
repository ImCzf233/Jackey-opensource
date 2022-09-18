package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.awt.Color;
import java.text.DecimalFormat;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.AnimationUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.Stencil;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.TextValue;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.MathHelper;
import org.apache.log4j.Level;

@ModuleInfo(name = "AutoHypixel", spacedName = "Auto Hypixel", description = "Automatically send you into random games on Hypixel after you die or win.", category = ModuleCategory.MISC)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/misc/AutoHypixel.class */
public class AutoHypixel extends Module {
    public static String gameMode = "NONE";
    public boolean shouldChangeGame;
    private final IntegerValue delayValue = new IntegerValue("Delay", 0, 0, (int) Level.TRACE_INT, "ms");
    private final BoolValue autoGGValue = new BoolValue("Auto-GG", true);
    private final TextValue ggMessageValue = new TextValue("GG-Message", "gOoD GaMe", () -> {
        return this.autoGGValue.get();
    });
    private final BoolValue checkValue = new BoolValue("CheckGameMode", true);
    private final BoolValue antiSnipeValue = new BoolValue("AntiSnipe", true);
    private final BoolValue renderValue = new BoolValue("Render", true);
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Solo", "Teams", "Ranked", "Mega"}, "Solo");
    private final ListValue soloTeamsValue = new ListValue("Solo/Teams-Mode", new String[]{"Normal", "Insane"}, "Insane", () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("solo") || this.modeValue.get().equalsIgnoreCase("teams"));
    });
    private final ListValue megaValue = new ListValue("Mega-Mode", new String[]{"Normal", "Doubles"}, "Normal", () -> {
        return Boolean.valueOf(this.modeValue.get().equalsIgnoreCase("mega"));
    });
    private final MSTimer timer = new MSTimer();
    public boolean useOtherWord = false;
    private final DecimalFormat dFormat = new DecimalFormat("0.0");
    private float posY = -20.0f;
    private final String[] strings = {"1st Killer -", "1st Place -", "died! Want to play again? Click here!", "won! Want to play again? Click here!", "- Damage Dealt -", "1st -", "Winning Team -", "Winners:", "Winner:", "Winning Team:", " win the game!", "1st Place:", "Last team standing!", "Winner #1 (", "Top Survivors", "Winners -"};

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        this.shouldChangeGame = false;
        this.timer.reset();
    }

    @EventTarget
    public void onRender2D(Render2DEvent event) {
        if (this.checkValue.get().booleanValue() && !gameMode.toLowerCase().contains("skywars")) {
            return;
        }
        ScaledResolution sc = new ScaledResolution(f362mc);
        float middleX = sc.func_78326_a() / 2.0f;
        String detail = "Next game in " + this.dFormat.format(((float) this.timer.hasTimeLeft(this.delayValue.get().intValue())) / 1000.0f) + "s...";
        float middleWidth = Fonts.font40.func_78256_a(detail) / 2.0f;
        float strength = MathHelper.func_76131_a(((float) this.timer.hasTimeLeft(this.delayValue.get().intValue())) / this.delayValue.get().intValue(), 0.0f, 1.0f);
        float wid = strength * (5.0f + middleWidth) * 2.0f;
        this.posY = AnimationUtils.animate(this.shouldChangeGame ? 10.0f : -20.0f, this.posY, 0.0125f * RenderUtils.deltaTime);
        if (!this.renderValue.get().booleanValue() || this.posY < -15.0f) {
            return;
        }
        Stencil.write(true);
        RenderUtils.drawRoundedRect((middleX - 5.0f) - middleWidth, this.posY, middleX + 5.0f + middleWidth, this.posY + 15.0f, 3.0f, -1610612736);
        Stencil.erase(true);
        RenderUtils.drawRect((middleX - 5.0f) - middleWidth, this.posY, ((middleX - 5.0f) - middleWidth) + wid, this.posY + 15.0f, new Color(0.4f, 0.8f, 0.4f, 0.35f).getRGB());
        Stencil.dispose();
        GlStateManager.func_179117_G();
        Fonts.fontSFUI40.drawString(detail, (middleX - middleWidth) - 1.0f, this.posY + 4.0f, -1);
    }

    @EventTarget
    public void onMotion(MotionEvent event) {
        if ((!this.checkValue.get().booleanValue() || gameMode.toLowerCase().contains("skywars")) && this.shouldChangeGame && this.timer.hasTimePassed(this.delayValue.get().intValue())) {
            f362mc.field_71439_g.func_71165_d("/play " + this.modeValue.get().toLowerCase() + (this.modeValue.get().equalsIgnoreCase("ranked") ? "_normal" : this.modeValue.get().equalsIgnoreCase("mega") ? "_" + this.megaValue.get().toLowerCase() : "_" + this.soloTeamsValue.get().toLowerCase()));
            this.shouldChangeGame = false;
        }
        if (!this.shouldChangeGame) {
            this.timer.reset();
        }
    }

    @EventTarget
    public void onPacket(PacketEvent event) {
        String[] strArr;
        if (event.getPacket() instanceof S02PacketChat) {
            S02PacketChat chat = event.getPacket();
            if (chat.func_148915_c() != null) {
                if (this.antiSnipeValue.get().booleanValue() && chat.func_148915_c().func_150260_c().contains("Sending you to")) {
                    event.cancelEvent();
                    return;
                }
                for (String s : this.strings) {
                    if (chat.func_148915_c().func_150260_c().contains(s)) {
                        if (this.autoGGValue.get().booleanValue() && chat.func_148915_c().func_150260_c().contains(this.strings[3])) {
                            f362mc.field_71439_g.func_71165_d(this.ggMessageValue.get());
                        }
                        this.shouldChangeGame = true;
                        return;
                    }
                }
            }
        }
    }
}
