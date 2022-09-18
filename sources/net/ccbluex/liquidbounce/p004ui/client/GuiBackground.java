package net.ccbluex.liquidbounce.p004ui.client;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.List;
import javax.imageio.ImageIO;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/* compiled from: GuiBackground.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��4\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0003\u0018�� \u00172\u00020\u0001:\u0001\u0017B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0005H\u0014J \u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\nH\u0016J\u0018\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u000eH\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n��R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n��R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n��\u001a\u0004\b\u0007\u0010\b¨\u0006\u0018"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiBackground;", "Lnet/minecraft/client/gui/GuiScreen;", "prevGui", "(Lnet/minecraft/client/gui/GuiScreen;)V", "enabledButton", "Lnet/minecraft/client/gui/GuiButton;", "particlesButton", "getPrevGui", "()Lnet/minecraft/client/gui/GuiScreen;", "actionPerformed", "", "button", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "Companion", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.GuiBackground */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/GuiBackground.class */
public final class GuiBackground extends GuiScreen {
    @NotNull
    private final GuiScreen prevGui;
    private GuiButton enabledButton;
    private GuiButton particlesButton;
    private static boolean particles;
    @NotNull
    public static final Companion Companion = new Companion(null);
    private static boolean enabled = true;

    public GuiBackground(@NotNull GuiScreen prevGui) {
        Intrinsics.checkNotNullParameter(prevGui, "prevGui");
        this.prevGui = prevGui;
    }

    @NotNull
    public final GuiScreen getPrevGui() {
        return this.prevGui;
    }

    /* compiled from: GuiBackground.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0014\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\b¨\u0006\f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiBackground$Companion;", "", "()V", "enabled", "", "getEnabled", "()Z", "setEnabled", "(Z)V", "particles", "getParticles", "setParticles", "LiquidBounce"})
    /* renamed from: net.ccbluex.liquidbounce.ui.client.GuiBackground$Companion */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/GuiBackground$Companion.class */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }

        public final boolean getEnabled() {
            return GuiBackground.enabled;
        }

        public final void setEnabled(boolean z) {
            GuiBackground.enabled = z;
        }

        public final boolean getParticles() {
            return GuiBackground.particles;
        }

        public final void setParticles(boolean z) {
            GuiBackground.particles = z;
        }
    }

    public void func_73866_w_() {
        this.enabledButton = new GuiButton(1, (this.field_146294_l / 2) - 100, (this.field_146295_m / 4) + 35, "Enabled (" + (enabled ? "On" : "Off") + ')');
        List list = this.field_146292_n;
        GuiButton guiButton = this.enabledButton;
        if (guiButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("enabledButton");
            guiButton = null;
        }
        list.add(guiButton);
        this.particlesButton = new GuiButton(2, (this.field_146294_l / 2) - 100, (this.field_146295_m / 4) + 50 + 25, "Particles (" + (particles ? "On" : "Off") + ')');
        List list2 = this.field_146292_n;
        GuiButton guiButton2 = this.particlesButton;
        if (guiButton2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("particlesButton");
            guiButton2 = null;
        }
        list2.add(guiButton2);
        this.field_146292_n.add(new GuiButton(3, (this.field_146294_l / 2) - 100, (this.field_146295_m / 4) + 50 + 50, 98, 20, "Change wallpaper"));
        this.field_146292_n.add(new GuiButton(4, (this.field_146294_l / 2) + 2, (this.field_146295_m / 4) + 50 + 50, 98, 20, "Reset wallpaper"));
        this.field_146292_n.add(new GuiButton(0, (this.field_146294_l / 2) - 100, (this.field_146295_m / 4) + 55 + 100 + 5, "Back"));
    }

    protected void func_146284_a(@NotNull GuiButton button) {
        Intrinsics.checkNotNullParameter(button, "button");
        switch (button.field_146127_k) {
            case 0:
                this.field_146297_k.func_147108_a(this.prevGui);
                return;
            case 1:
                Companion companion = Companion;
                enabled = !enabled;
                GuiButton guiButton = this.enabledButton;
                if (guiButton == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("enabledButton");
                    guiButton = null;
                }
                guiButton.field_146126_j = "Enabled (" + (enabled ? "On" : "Off") + ')';
                return;
            case 2:
                Companion companion2 = Companion;
                particles = !particles;
                GuiButton guiButton2 = this.particlesButton;
                if (guiButton2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("particlesButton");
                    guiButton2 = null;
                }
                guiButton2.field_146126_j = "Particles (" + (particles ? "On" : "Off") + ')';
                return;
            case 3:
                File file = MiscUtils.openFileChooser();
                if (file == null || file.isDirectory()) {
                    return;
                }
                try {
                    Files.copy(file.toPath(), new FileOutputStream(LiquidBounce.INSTANCE.getFileManager().backgroundFile));
                    BufferedImage image = ImageIO.read(new FileInputStream(LiquidBounce.INSTANCE.getFileManager().backgroundFile));
                    LiquidBounce liquidBounce = LiquidBounce.INSTANCE;
                    String lowerCase = LiquidBounce.CLIENT_NAME.toLowerCase();
                    Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
                    liquidBounce.setBackground(new ResourceLocation(Intrinsics.stringPlus(lowerCase, "/background.png")));
                    this.field_146297_k.func_110434_K().func_110579_a(LiquidBounce.INSTANCE.getBackground(), new DynamicTexture(image));
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    MiscUtils.showErrorPopup("Error", "Exception class: " + ((Object) e.getClass().getName()) + "\nMessage: " + ((Object) e.getMessage()));
                    LiquidBounce.INSTANCE.getFileManager().backgroundFile.delete();
                    return;
                }
            case 4:
                LiquidBounce.INSTANCE.setBackground(null);
                LiquidBounce.INSTANCE.getFileManager().backgroundFile.delete();
                return;
            default:
                return;
        }
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        func_146278_c(0);
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        if (1 == keyCode) {
            this.field_146297_k.func_147108_a(this.prevGui);
        } else {
            super.func_73869_a(typedChar, keyCode);
        }
    }
}
