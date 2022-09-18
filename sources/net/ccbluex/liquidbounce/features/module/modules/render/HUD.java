package net.ccbluex.liquidbounce.features.module.modules.render;

import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.KeyEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.p004ui.client.hud.designer.GuiHudDesigner;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.p004ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.AnimationUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.TextValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: HUD.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u00103\u001a\u00020(2\u0006\u00104\u001a\u00020(J\u0010\u00105\u001a\u0002062\u0006\u00107\u001a\u000208H\u0007J\u0010\u00109\u001a\u0002062\u0006\u00107\u001a\u00020:H\u0007J\u0010\u0010;\u001a\u0002062\u0006\u00107\u001a\u00020<H\u0007J\u0012\u0010=\u001a\u0002062\b\u00107\u001a\u0004\u0018\u00010>H\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n��\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u0012\u0010\u0006R\u0011\u0010\u0013\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u0014\u0010\u0006R\u0011\u0010\u0015\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u0016\u0010\u0006R\u0011\u0010\u0017\u001a\u00020\u0018¢\u0006\b\n��\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u001b\u001a\u00020\u001c¢\u0006\b\n��\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u001f\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b \u0010\u0006R\u0011\u0010!\u001a\u00020\"¢\u0006\b\n��\u001a\u0004\b#\u0010$R\u0011\u0010%\u001a\u00020\u0018¢\u0006\b\n��\u001a\u0004\b&\u0010\u001aR\u000e\u0010'\u001a\u00020(X\u0082\u000e¢\u0006\u0002\n��R\u0011\u0010)\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b*\u0010\u0006R\u0011\u0010+\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b,\u0010\u0006R\u0011\u0010-\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b.\u0010\u0006R\u000e\u0010/\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u00100\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n��R\u000e\u00101\u001a\u000202X\u0082\u0004¢\u0006\u0002\n��¨\u0006?"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/HUD;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "animHotbarValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getAnimHotbarValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "blackHotbarValue", "getBlackHotbarValue", "chatAnimationSpeedValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "getChatAnimationSpeedValue", "()Lnet/ccbluex/liquidbounce/value/FloatValue;", "chatAnimationValue", "getChatAnimationValue", "chatCombineValue", "getChatCombineValue", "chatRectValue", "getChatRectValue", "cmdBorderValue", "getCmdBorderValue", "containerBackground", "getContainerBackground", "containerButton", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getContainerButton", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "domainValue", "Lnet/ccbluex/liquidbounce/value/TextValue;", "getDomainValue", "()Lnet/ccbluex/liquidbounce/value/TextValue;", "fontChatValue", "getFontChatValue", "fontType", "Lnet/ccbluex/liquidbounce/value/FontValue;", "getFontType", "()Lnet/ccbluex/liquidbounce/value/FontValue;", "guiButtonStyle", "getGuiButtonStyle", "hotBarX", "", "invEffectOffset", "getInvEffectOffset", "inventoryParticle", "getInventoryParticle", "tabHead", "getTabHead", "toggleMessageValue", "toggleSoundValue", "toggleVolumeValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "getAnimPos", "pos", "onKey", "", "event", "Lnet/ccbluex/liquidbounce/event/KeyEvent;", "onRender2D", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onTick", "Lnet/ccbluex/liquidbounce/event/TickEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "HUD", description = "Toggles visibility of the HUD.", category = ModuleCategory.RENDER, array = false)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/HUD.class */
public final class HUD extends Module {
    @NotNull
    private final FontValue fontType;
    private float hotBarX;
    @NotNull
    private final BoolValue tabHead = new BoolValue("Tab-HeadOverlay", true);
    @NotNull
    private final BoolValue animHotbarValue = new BoolValue("AnimatedHotbar", true);
    @NotNull
    private final BoolValue blackHotbarValue = new BoolValue("BlackHotbar", true);
    @NotNull
    private final BoolValue inventoryParticle = new BoolValue("InventoryParticle", false);
    @NotNull
    private final BoolValue fontChatValue = new BoolValue("FontChat", false);
    @NotNull
    private final BoolValue cmdBorderValue = new BoolValue("CommandChatBorder", true);
    @NotNull
    private final BoolValue chatRectValue = new BoolValue("ChatRect", true);
    @NotNull
    private final BoolValue chatCombineValue = new BoolValue("ChatCombine", true);
    @NotNull
    private final BoolValue chatAnimationValue = new BoolValue("ChatAnimation", true);
    @NotNull
    private final FloatValue chatAnimationSpeedValue = new FloatValue("Chat-AnimationSpeed", 0.1f, 0.01f, 0.1f);
    @NotNull
    private final BoolValue toggleMessageValue = new BoolValue("DisplayToggleMessage", false);
    @NotNull
    private final ListValue toggleSoundValue = new ListValue("ToggleSound", new String[]{"None", "Default", "Custom"}, "Default");
    @NotNull
    private final IntegerValue toggleVolumeValue = new IntegerValue("ToggleVolume", 100, 0, 100, new HUD$toggleVolumeValue$1(this));
    @NotNull
    private final ListValue guiButtonStyle = new ListValue("Button-Style", new String[]{"Minecraft", "LiquidBounce", "Rounded", "LiquidBounce+"}, "Minecraft");
    @NotNull
    private final BoolValue containerBackground = new BoolValue("Container-Background", false);
    @NotNull
    private final ListValue containerButton = new ListValue("Container-Button", new String[]{"TopLeft", "TopRight", "Off"}, "TopLeft");
    @NotNull
    private final BoolValue invEffectOffset = new BoolValue("InvEffect-Offset", false);
    @NotNull
    private final TextValue domainValue = new TextValue("Scoreboard-Domain", ".hud scoreboard-domain <your domain here>");

    public HUD() {
        GameFontRenderer font40 = Fonts.font40;
        Intrinsics.checkNotNullExpressionValue(font40, "font40");
        this.fontType = new FontValue("Font", font40, new HUD$fontType$1(this));
        setState(true);
    }

    @NotNull
    public final BoolValue getTabHead() {
        return this.tabHead;
    }

    @NotNull
    public final BoolValue getAnimHotbarValue() {
        return this.animHotbarValue;
    }

    @NotNull
    public final BoolValue getBlackHotbarValue() {
        return this.blackHotbarValue;
    }

    @NotNull
    public final BoolValue getInventoryParticle() {
        return this.inventoryParticle;
    }

    @NotNull
    public final BoolValue getFontChatValue() {
        return this.fontChatValue;
    }

    @NotNull
    public final BoolValue getCmdBorderValue() {
        return this.cmdBorderValue;
    }

    @NotNull
    public final FontValue getFontType() {
        return this.fontType;
    }

    @NotNull
    public final BoolValue getChatRectValue() {
        return this.chatRectValue;
    }

    @NotNull
    public final BoolValue getChatCombineValue() {
        return this.chatCombineValue;
    }

    @NotNull
    public final BoolValue getChatAnimationValue() {
        return this.chatAnimationValue;
    }

    @NotNull
    public final FloatValue getChatAnimationSpeedValue() {
        return this.chatAnimationSpeedValue;
    }

    @NotNull
    public final ListValue getGuiButtonStyle() {
        return this.guiButtonStyle;
    }

    @NotNull
    public final BoolValue getContainerBackground() {
        return this.containerBackground;
    }

    @NotNull
    public final ListValue getContainerButton() {
        return this.containerButton;
    }

    @NotNull
    public final BoolValue getInvEffectOffset() {
        return this.invEffectOffset;
    }

    @NotNull
    public final TextValue getDomainValue() {
        return this.domainValue;
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.f362mc.field_71462_r instanceof GuiHudDesigner) {
            return;
        }
        LiquidBounce.INSTANCE.getHud().render(false);
    }

    @EventTarget(ignoreCondition = true)
    public final void onTick(@NotNull TickEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (LiquidBounce.INSTANCE.getModuleManager().getShouldNotify() != this.toggleMessageValue.get().booleanValue()) {
            LiquidBounce.INSTANCE.getModuleManager().setShouldNotify(this.toggleMessageValue.get().booleanValue());
        }
        if (LiquidBounce.INSTANCE.getModuleManager().getToggleSoundMode() != ArraysKt.indexOf(this.toggleSoundValue.getValues(), this.toggleSoundValue.get())) {
            LiquidBounce.INSTANCE.getModuleManager().setToggleSoundMode(ArraysKt.indexOf(this.toggleSoundValue.getValues(), this.toggleSoundValue.get()));
        }
        if (!(LiquidBounce.INSTANCE.getModuleManager().getToggleVolume() == ((float) this.toggleVolumeValue.get().intValue()))) {
            LiquidBounce.INSTANCE.getModuleManager().setToggleVolume(this.toggleVolumeValue.get().intValue());
        }
    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent event) {
        LiquidBounce.INSTANCE.getHud().update();
    }

    @EventTarget
    public final void onKey(@NotNull KeyEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        LiquidBounce.INSTANCE.getHud().handleKey('a', event.getKey());
    }

    public final float getAnimPos(float pos) {
        if (!getState() || !this.animHotbarValue.get().booleanValue()) {
            this.hotBarX = pos;
        } else {
            this.hotBarX = AnimationUtils.animate(pos, this.hotBarX, 0.02f * RenderUtils.deltaTime);
        }
        return this.hotBarX;
    }
}
