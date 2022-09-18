package net.ccbluex.liquidbounce.p004ui.client;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.p004ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.AnimationUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

/* compiled from: GuiMainMenu.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u000e\n\u0002\u0010\u0002\n��\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\f\n\u0002\b\n\u0018�� 92\u00020\u00012\u00020\u0002:\u00029:B\u0005¢\u0006\u0002\u0010\u0003J \u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020&2\u0006\u0010(\u001a\u00020\u000fH\u0016J\b\u0010)\u001a\u00020$H\u0016J6\u0010*\u001a\u00020\u00052\u0006\u0010+\u001a\u00020\u000f2\u0006\u0010,\u001a\u00020\u000f2\u0006\u0010-\u001a\u00020\u000f2\u0006\u0010.\u001a\u00020\u000f2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020&J\u0018\u0010/\u001a\u00020$2\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u00020&H\u0014J \u00103\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020&2\u0006\u00104\u001a\u00020&H\u0014J\u001e\u00105\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020&2\u0006\u00106\u001a\u00020\u000fJ\u001e\u00107\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020&2\u0006\u0010(\u001a\u00020\u000fJ\u0006\u00108\u001a\u00020$R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n��\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0015X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u001b\u0010\u0011\"\u0004\b\u001c\u0010\u0013R\u001a\u0010\u001d\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u001e\u0010\u0011\"\u0004\b\u001f\u0010\u0013R\u001a\u0010 \u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b!\u0010\u0011\"\u0004\b\"\u0010\u0013¨\u0006;"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiMainMenu;", "Lnet/minecraft/client/gui/GuiScreen;", "Lnet/minecraft/client/gui/GuiYesNoCallback;", "()V", "alrUpdate", "", "getAlrUpdate", "()Z", "setAlrUpdate", "(Z)V", "bigLogo", "Lnet/minecraft/util/ResourceLocation;", "getBigLogo", "()Lnet/minecraft/util/ResourceLocation;", "fade", "", "getFade", "()F", "setFade", "(F)V", "lastAnimTick", "", "getLastAnimTick", "()J", "setLastAnimTick", "(J)V", "lastXPos", "getLastXPos", "setLastXPos", "slideX", "getSlideX", "setSlideX", "sliderX", "getSliderX", "setSliderX", "drawScreen", "", "mouseX", "", "mouseY", "partialTicks", "initGui", "isMouseHover", "x", "y", "x2", "y2", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "moveMouseEffect", "strength", "renderBar", "renderSwitchButton", "Companion", "ImageButton", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.GuiMainMenu */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/GuiMainMenu.class */
public final class GuiMainMenu extends GuiScreen implements GuiYesNoCallback {
    @NotNull
    private final ResourceLocation bigLogo = new ResourceLocation("liquidbounce+/big.png");
    private float slideX;
    private float fade;
    private float sliderX;
    private long lastAnimTick;
    private boolean alrUpdate;
    private float lastXPos;
    @NotNull
    public static final Companion Companion = new Companion(null);
    private static boolean useParallax = true;

    @NotNull
    public final ResourceLocation getBigLogo() {
        return this.bigLogo;
    }

    public final float getSlideX() {
        return this.slideX;
    }

    public final void setSlideX(float f) {
        this.slideX = f;
    }

    public final float getFade() {
        return this.fade;
    }

    public final void setFade(float f) {
        this.fade = f;
    }

    public final float getSliderX() {
        return this.sliderX;
    }

    public final void setSliderX(float f) {
        this.sliderX = f;
    }

    public final long getLastAnimTick() {
        return this.lastAnimTick;
    }

    public final void setLastAnimTick(long j) {
        this.lastAnimTick = j;
    }

    public final boolean getAlrUpdate() {
        return this.alrUpdate;
    }

    public final void setAlrUpdate(boolean z) {
        this.alrUpdate = z;
    }

    public final float getLastXPos() {
        return this.lastXPos;
    }

    public final void setLastXPos(float f) {
        this.lastXPos = f;
    }

    /* compiled from: GuiMainMenu.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0014\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiMainMenu$Companion;", "", "()V", "useParallax", "", "getUseParallax", "()Z", "setUseParallax", "(Z)V", "LiquidBounce"})
    /* renamed from: net.ccbluex.liquidbounce.ui.client.GuiMainMenu$Companion */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/GuiMainMenu$Companion.class */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }

        public final boolean getUseParallax() {
            return GuiMainMenu.useParallax;
        }

        public final void setUseParallax(boolean z) {
            GuiMainMenu.useParallax = z;
        }
    }

    public void func_73866_w_() {
        this.slideX = 0.0f;
        this.fade = 0.0f;
        this.sliderX = 0.0f;
        super.func_73866_w_();
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        if (!this.alrUpdate) {
            this.lastAnimTick = System.currentTimeMillis();
            this.alrUpdate = true;
        }
        func_146278_c(0);
        GL11.glPushMatrix();
        renderSwitchButton();
        Fonts.font40.func_175063_a("Jackey build b1 | lbpro.ml", 2.0f, this.field_146295_m - 12.0f, -1);
        Fonts.font40.func_175063_a("Copyright Mojang AB. Do not distribute!", (this.field_146294_l - 3.0f) - Fonts.font40.func_78256_a("Copyright Mojang AB. Do not distribute!"), this.field_146295_m - 12.0f, -1);
        if (useParallax) {
            moveMouseEffect(mouseX, mouseY, 10.0f);
        }
        GlStateManager.func_179118_c();
        RenderUtils.drawImage2(this.bigLogo, (this.field_146294_l / 2.0f) - 50.0f, (this.field_146295_m / 2.0f) - 90.0f, 100, 100);
        GlStateManager.func_179141_d();
        renderBar(mouseX, mouseY, partialTicks);
        GL11.glPopMatrix();
        super.func_73863_a(mouseX, mouseY, partialTicks);
        if (!LiquidBounce.INSTANCE.getMainMenuPrep()) {
            float animProgress = RangesKt.coerceIn(((float) (System.currentTimeMillis() - this.lastAnimTick)) / 2000.0f, 0.0f, 1.0f);
            RenderUtils.drawRect(0.0f, 0.0f, this.field_146294_l, this.field_146295_m, new Color(0.0f, 0.0f, 0.0f, 1.0f - animProgress));
            if (animProgress >= 1.0f) {
                LiquidBounce.INSTANCE.setMainMenuPrep(true);
            }
        }
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (!LiquidBounce.INSTANCE.getMainMenuPrep() || mouseButton != 0) {
            return;
        }
        if (isMouseHover(2.0f, this.field_146295_m - 22.0f, 28.0f, this.field_146295_m - 12.0f, mouseX, mouseY)) {
            Companion companion = Companion;
            useParallax = !useParallax;
        }
        float staticX = (this.field_146294_l / 2.0f) - 120.0f;
        float staticY = (this.field_146295_m / 2.0f) + 20.0f;
        int index = 0;
        ImageButton[] values = ImageButton.values();
        int i = 0;
        int length = values.length;
        while (i < length) {
            ImageButton imageButton = values[i];
            i++;
            if (isMouseHover(staticX + (40.0f * index), staticY, staticX + (40.0f * (index + 1)), staticY + 20.0f, mouseX, mouseY)) {
                switch (index) {
                    case 0:
                        this.field_146297_k.func_147108_a(new GuiSelectWorld(this));
                        continue;
                    case 1:
                        this.field_146297_k.func_147108_a(new GuiMultiplayer(this));
                        continue;
                    case 2:
                        this.field_146297_k.func_147108_a(new GuiAltManager(this));
                        continue;
                    case 3:
                        this.field_146297_k.func_147108_a(new GuiOptions(this, this.field_146297_k.field_71474_y));
                        continue;
                    case 4:
                        this.field_146297_k.func_147108_a(new GuiModsMenu(this));
                        continue;
                    case 5:
                        this.field_146297_k.func_71400_g();
                        continue;
                }
            }
            index++;
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public final void moveMouseEffect(int mouseX, int mouseY, float strength) {
        int mX = mouseX - (this.field_146294_l / 2);
        int mY = mouseY - (this.field_146295_m / 2);
        float xDelta = mX / (this.field_146294_l / 2);
        float yDelta = mY / (this.field_146295_m / 2);
        GL11.glTranslatef(xDelta * strength, yDelta * strength, 0.0f);
    }

    public final void renderSwitchButton() {
        this.sliderX += useParallax ? 2.0f : -2.0f;
        if (this.sliderX > 12.0f) {
            this.sliderX = 12.0f;
        } else if (this.sliderX < 0.0f) {
            this.sliderX = 0.0f;
        }
        Fonts.font40.func_175063_a("Parallax", 28.0f, this.field_146295_m - 25.0f, -1);
        RenderUtils.drawRoundedRect(4.0f, this.field_146295_m - 24.0f, 22.0f, this.field_146295_m - 18.0f, 3.0f, useParallax ? new Color(0, 111, 255, 255).getRGB() : new Color(140, 140, 140, 255).getRGB());
        RenderUtils.drawRoundedRect(2.0f + this.sliderX, this.field_146295_m - 26.0f, 12.0f + this.sliderX, this.field_146295_m - 16.0f, 5.0f, Color.white.getRGB());
    }

    public final void renderBar(int mouseX, int mouseY, float partialTicks) {
        float staticX = (this.field_146294_l / 2.0f) - 120.0f;
        float staticY = (this.field_146295_m / 2.0f) + 20.0f;
        RenderUtils.drawRoundedRect(staticX, staticY, staticX + 240.0f, staticY + 20.0f, 10.0f, new Color(255, 255, 255, 100).getRGB());
        int index = 0;
        boolean shouldAnimate = false;
        String displayString = null;
        float moveX = 0.0f;
        ImageButton[] values = ImageButton.values();
        int i = 0;
        int length = values.length;
        while (i < length) {
            ImageButton icon = values[i];
            i++;
            if (isMouseHover(staticX + (40.0f * index), staticY, staticX + (40.0f * (index + 1)), staticY + 20.0f, mouseX, mouseY)) {
                shouldAnimate = true;
                displayString = icon.getButtonName();
                moveX = staticX + (40.0f * index);
            }
            index++;
        }
        if (displayString != null) {
            Fonts.font35.drawCenteredString(displayString, this.field_146294_l / 2.0f, staticY + 30.0f, -1);
        }
        if (shouldAnimate) {
            if (this.fade == 0.0f) {
                this.slideX = moveX;
            } else {
                this.slideX = AnimationUtils.animate(moveX, this.slideX, 0.5f * (1.0f - partialTicks));
            }
            this.lastXPos = moveX;
            this.fade += 10.0f;
            if (this.fade >= 100.0f) {
                this.fade = 100.0f;
            }
        } else {
            this.fade -= 10.0f;
            if (this.fade <= 0.0f) {
                this.fade = 0.0f;
            }
            this.slideX = AnimationUtils.animate(this.lastXPos, this.slideX, 0.5f * (1.0f - partialTicks));
        }
        if (!(this.fade == 0.0f)) {
            RenderUtils.drawRoundedRect(this.slideX, staticY, this.slideX + 40.0f, staticY + 20.0f, 10.0f, new Color(1.0f, 1.0f, 1.0f, (this.fade / 100.0f) * 0.6f).getRGB());
        }
        int index2 = 0;
        GlStateManager.func_179118_c();
        ImageButton[] values2 = ImageButton.values();
        int i2 = 0;
        int length2 = values2.length;
        while (i2 < length2) {
            ImageButton i3 = values2[i2];
            i2++;
            RenderUtils.drawImage2(i3.getTexture(), staticX + (40.0f * index2) + 11.0f, staticY + 1.0f, 18, 18);
            index2++;
        }
        GlStateManager.func_179141_d();
    }

    public final boolean isMouseHover(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return ((float) mouseX) >= x && ((float) mouseX) < x2 && ((float) mouseY) >= y && ((float) mouseY) < y2;
    }

    /* compiled from: GuiMainMenu.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n��\n\u0002\u0010\u000e\n��\n\u0002\u0018\u0002\n\u0002\b\f\b\u0086\u0001\u0018��2\b\u0012\u0004\u0012\u00020��0\u0001B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n��\u001a\u0004\b\t\u0010\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010¨\u0006\u0011"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiMainMenu$ImageButton;", "", "buttonName", "", "texture", "Lnet/minecraft/util/ResourceLocation;", "(Ljava/lang/String;ILjava/lang/String;Lnet/minecraft/util/ResourceLocation;)V", "getButtonName", "()Ljava/lang/String;", "getTexture", "()Lnet/minecraft/util/ResourceLocation;", "Single", "Multi", "Alts", "Settings", "Mods", "Exit", "LiquidBounce"})
    /* renamed from: net.ccbluex.liquidbounce.ui.client.GuiMainMenu$ImageButton */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/GuiMainMenu$ImageButton.class */
    public enum ImageButton {
        Single("Singleplayer", new ResourceLocation("liquidbounce+/menu/singleplayer.png")),
        Multi("Multiplayer", new ResourceLocation("liquidbounce+/menu/multiplayer.png")),
        Alts("Alts", new ResourceLocation("liquidbounce+/menu/alt.png")),
        Settings("Settings", new ResourceLocation("liquidbounce+/menu/settings.png")),
        Mods("Mods/Customize", new ResourceLocation("liquidbounce+/menu/mods.png")),
        Exit("Exit", new ResourceLocation("liquidbounce+/menu/exit.png"));
        
        @NotNull
        private final String buttonName;
        @NotNull
        private final ResourceLocation texture;

        ImageButton(String buttonName, ResourceLocation texture) {
            this.buttonName = buttonName;
            this.texture = texture;
        }

        @NotNull
        public final String getButtonName() {
            return this.buttonName;
        }

        @NotNull
        public final ResourceLocation getTexture() {
            return this.texture;
        }
    }

    protected void func_73869_a(char typedChar, int keyCode) {
    }
}
