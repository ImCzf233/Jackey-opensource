package net.ccbluex.liquidbounce.p004ui.font;

import java.awt.Color;
import java.awt.Font;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.TextEvent;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: GameFontRenderer.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n��\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\f\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018�� 02\u00020\u0001:\u00010B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0014J&\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u000eJ.\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020 J&\u0010!\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u000eJ0\u0010!\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020 H\u0016J(\u0010#\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u000eH\u0016J2\u0010$\u001a\u00020\u000e2\b\u0010\"\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020\u000e2\u0006\u0010&\u001a\u00020 H\u0002J\u0010\u0010'\u001a\u00020\u000e2\u0006\u0010(\u001a\u00020)H\u0016J\u0010\u0010*\u001a\u00020\u000e2\u0006\u0010+\u001a\u00020)H\u0016J\u0010\u0010,\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020\u001aH\u0016J\u0010\u0010-\u001a\u00020\u00152\u0006\u0010.\u001a\u00020/H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��R\u001a\u0010\b\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��R\u0011\u0010\u0012\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0010¨\u00061"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/font/GameFontRenderer;", "Lnet/minecraft/client/gui/FontRenderer;", "font", "Ljava/awt/Font;", "(Ljava/awt/Font;)V", "boldFont", "Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer;", "boldItalicFont", "defaultFont", "getDefaultFont", "()Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer;", "setDefaultFont", "(Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer;)V", "height", "", "getHeight", "()I", "italicFont", "size", "getSize", "bindTexture", "", "location", "Lnet/minecraft/util/ResourceLocation;", "drawCenteredString", "s", "", "x", "", "y", "color", "shadow", "", "drawString", "text", "drawStringWithShadow", "drawText", "colorHex", "ignoreColor", "getCharWidth", "character", "", "getColorCode", "charCode", "getStringWidth", "onResourceManagerReload", "resourceManager", "Lnet/minecraft/client/resources/IResourceManager;", "Companion", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.font.GameFontRenderer */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/font/GameFontRenderer.class */
public final class GameFontRenderer extends FontRenderer {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private AWTFontRenderer defaultFont;
    @NotNull
    private AWTFontRenderer boldFont;
    @NotNull
    private AWTFontRenderer italicFont;
    @NotNull
    private AWTFontRenderer boldItalicFont;

    @JvmStatic
    public static final int getColorIndex(char type) {
        return Companion.getColorIndex(type);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GameFontRenderer(@NotNull Font font) {
        super(Minecraft.func_71410_x().field_71474_y, new ResourceLocation("textures/font/ascii.png"), ClassUtils.INSTANCE.hasForge() ? null : Minecraft.func_71410_x().func_110434_K(), false);
        Intrinsics.checkNotNullParameter(font, "font");
        this.defaultFont = new AWTFontRenderer(font, 0, 0, 6, null);
        Font deriveFont = font.deriveFont(1);
        Intrinsics.checkNotNullExpressionValue(deriveFont, "font.deriveFont(Font.BOLD)");
        this.boldFont = new AWTFontRenderer(deriveFont, 0, 0, 6, null);
        Font deriveFont2 = font.deriveFont(2);
        Intrinsics.checkNotNullExpressionValue(deriveFont2, "font.deriveFont(Font.ITALIC)");
        this.italicFont = new AWTFontRenderer(deriveFont2, 0, 0, 6, null);
        Font deriveFont3 = font.deriveFont(3);
        Intrinsics.checkNotNullExpressionValue(deriveFont3, "font.deriveFont(Font.BOLD or Font.ITALIC)");
        this.boldItalicFont = new AWTFontRenderer(deriveFont3, 0, 0, 6, null);
        this.field_78288_b = getHeight();
    }

    @NotNull
    public final AWTFontRenderer getDefaultFont() {
        return this.defaultFont;
    }

    public final void setDefaultFont(@NotNull AWTFontRenderer aWTFontRenderer) {
        Intrinsics.checkNotNullParameter(aWTFontRenderer, "<set-?>");
        this.defaultFont = aWTFontRenderer;
    }

    public final int getHeight() {
        return this.defaultFont.getHeight() / 2;
    }

    public final int getSize() {
        return this.defaultFont.getFont().getSize();
    }

    public final int drawString(@NotNull String s, float x, float y, int color) {
        Intrinsics.checkNotNullParameter(s, "s");
        return func_175065_a(s, x, y, color, false);
    }

    public int func_175063_a(@NotNull String text, float x, float y, int color) {
        Intrinsics.checkNotNullParameter(text, "text");
        return func_175065_a(text, x, y, color, true);
    }

    public final int drawCenteredString(@NotNull String s, float x, float y, int color, boolean shadow) {
        Intrinsics.checkNotNullParameter(s, "s");
        return func_175065_a(s, x - (func_78256_a(s) / 2.0f), y, color, shadow);
    }

    public final int drawCenteredString(@NotNull String s, float x, float y, int color) {
        Intrinsics.checkNotNullParameter(s, "s");
        return func_175063_a(s, x - (func_78256_a(s) / 2.0f), y, color);
    }

    public int func_175065_a(@NotNull String text, float x, float y, int color, boolean shadow) {
        Intrinsics.checkNotNullParameter(text, "text");
        TextEvent event = new TextEvent(text);
        LiquidBounce.INSTANCE.getEventManager().callEvent(event);
        String currentText = event.getText();
        if (currentText == null) {
            return 0;
        }
        float currY = y - 3.0f;
        if (shadow) {
            drawText(currentText, x + 1.0f, currY + 1.0f, new Color(0, 0, 0, 150).getRGB(), true);
        }
        return drawText(currentText, x, currY, color, false);
    }

    private final int drawText(String text, float x, float y, int colorHex, boolean ignoreColor) {
        boolean z;
        AWTFontRenderer aWTFontRenderer;
        if (text == null) {
            return 0;
        }
        if (text.length() == 0) {
            return (int) x;
        }
        GlStateManager.func_179137_b(x - 1.5d, y + 0.5d, 0.0d);
        GlStateManager.func_179141_d();
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        GlStateManager.func_179098_w();
        int hexColor = colorHex;
        if ((hexColor & (-67108864)) == 0) {
            hexColor |= -16777216;
        }
        int alpha = (hexColor >> 24) & 255;
        if (StringsKt.contains$default((CharSequence) text, (CharSequence) "§", false, 2, (Object) null)) {
            Iterable parts = StringsKt.split$default((CharSequence) text, new String[]{"§"}, false, 0, 6, (Object) null);
            AWTFontRenderer aWTFontRenderer2 = this.defaultFont;
            double width = 0.0d;
            boolean randomCase = false;
            boolean bold = false;
            boolean italic = false;
            boolean strikeThrough = false;
            boolean underline = false;
            Iterable $this$forEachIndexed$iv = parts;
            int index$iv = 0;
            for (Object item$iv : $this$forEachIndexed$iv) {
                int index = index$iv;
                index$iv = index + 1;
                if (index < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                String part = (String) item$iv;
                if (!(part.length() == 0)) {
                    if (index == 0) {
                        aWTFontRenderer2.drawString(part, width, 0.0d, hexColor);
                        width += aWTFontRenderer2.getStringWidth(part);
                    } else {
                        String words = part.substring(1);
                        Intrinsics.checkNotNullExpressionValue(words, "this as java.lang.String).substring(startIndex)");
                        char type = part.charAt(0);
                        int colorIndex = Companion.getColorIndex(type);
                        if (0 <= colorIndex) {
                            z = colorIndex < 16;
                        } else {
                            z = false;
                        }
                        if (z) {
                            if (!ignoreColor) {
                                hexColor = ColorUtils.hexColors[colorIndex] | (alpha << 24);
                            }
                            bold = false;
                            italic = false;
                            randomCase = false;
                            underline = false;
                            strikeThrough = false;
                        } else if (colorIndex == 16) {
                            randomCase = true;
                        } else if (colorIndex == 17) {
                            bold = true;
                        } else if (colorIndex == 18) {
                            strikeThrough = true;
                        } else if (colorIndex == 19) {
                            underline = true;
                        } else if (colorIndex == 20) {
                            italic = true;
                        } else if (colorIndex == 21) {
                            hexColor = colorHex;
                            if ((hexColor & (-67108864)) == 0) {
                                hexColor |= -16777216;
                            }
                            bold = false;
                            italic = false;
                            randomCase = false;
                            underline = false;
                            strikeThrough = false;
                        }
                        if (bold && italic) {
                            aWTFontRenderer = this.boldItalicFont;
                        } else if (bold) {
                            aWTFontRenderer = this.boldFont;
                        } else if (italic) {
                            aWTFontRenderer = this.italicFont;
                        } else {
                            aWTFontRenderer = getDefaultFont();
                        }
                        aWTFontRenderer2 = aWTFontRenderer;
                        aWTFontRenderer2.drawString(randomCase ? ColorUtils.INSTANCE.randomMagicText(words) : words, width, 0.0d, hexColor);
                        if (strikeThrough) {
                            RenderUtils.drawLine((width / 2.0d) + 1, aWTFontRenderer2.getHeight() / 3.0d, ((width + aWTFontRenderer2.getStringWidth(words)) / 2.0d) + 1, aWTFontRenderer2.getHeight() / 3.0d, this.field_78288_b / 16.0f);
                        }
                        if (underline) {
                            RenderUtils.drawLine((width / 2.0d) + 1, aWTFontRenderer2.getHeight() / 2.0d, ((width + aWTFontRenderer2.getStringWidth(words)) / 2.0d) + 1, aWTFontRenderer2.getHeight() / 2.0d, this.field_78288_b / 16.0f);
                        }
                        width += aWTFontRenderer2.getStringWidth(words);
                    }
                }
            }
        } else {
            this.defaultFont.drawString(text, 0.0d, 0.0d, hexColor);
        }
        GlStateManager.func_179084_k();
        GlStateManager.func_179137_b(-(x - 1.5d), -(y + 0.5d), 0.0d);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        return (int) (x + func_78256_a(text));
    }

    public int func_175064_b(char charCode) {
        return ColorUtils.hexColors[Companion.getColorIndex(charCode)];
    }

    public int func_78256_a(@NotNull String text) {
        AWTFontRenderer aWTFontRenderer;
        Intrinsics.checkNotNullParameter(text, "text");
        TextEvent event = new TextEvent(text);
        LiquidBounce.INSTANCE.getEventManager().callEvent(event);
        String currentText = event.getText();
        if (currentText == null) {
            return 0;
        }
        if (StringsKt.contains$default((CharSequence) currentText, (CharSequence) "§", false, 2, (Object) null)) {
            Iterable parts = StringsKt.split$default((CharSequence) currentText, new String[]{"§"}, false, 0, 6, (Object) null);
            AWTFontRenderer aWTFontRenderer2 = this.defaultFont;
            int width = 0;
            boolean bold = false;
            boolean italic = false;
            Iterable $this$forEachIndexed$iv = parts;
            int index$iv = 0;
            for (Object item$iv : $this$forEachIndexed$iv) {
                int index = index$iv;
                index$iv = index + 1;
                if (index < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                String part = (String) item$iv;
                if (!(part.length() == 0)) {
                    if (index == 0) {
                        width += aWTFontRenderer2.getStringWidth(part);
                    } else {
                        String words = part.substring(1);
                        Intrinsics.checkNotNullExpressionValue(words, "this as java.lang.String).substring(startIndex)");
                        char type = part.charAt(0);
                        int colorIndex = Companion.getColorIndex(type);
                        if (colorIndex < 16) {
                            bold = false;
                            italic = false;
                        } else if (colorIndex == 17) {
                            bold = true;
                        } else if (colorIndex == 20) {
                            italic = true;
                        } else if (colorIndex == 21) {
                            bold = false;
                            italic = false;
                        }
                        if (bold && italic) {
                            aWTFontRenderer = this.boldItalicFont;
                        } else if (bold) {
                            aWTFontRenderer = this.boldFont;
                        } else if (italic) {
                            aWTFontRenderer = this.italicFont;
                        } else {
                            aWTFontRenderer = getDefaultFont();
                        }
                        aWTFontRenderer2 = aWTFontRenderer;
                        width += aWTFontRenderer2.getStringWidth(words);
                    }
                }
            }
            return width / 2;
        }
        return this.defaultFont.getStringWidth(currentText) / 2;
    }

    public int func_78263_a(char character) {
        return func_78256_a(String.valueOf(character));
    }

    public void func_110549_a(@NotNull IResourceManager resourceManager) {
        Intrinsics.checkNotNullParameter(resourceManager, "resourceManager");
    }

    protected void bindTexture(@Nullable ResourceLocation location) {
    }

    /* compiled from: GameFontRenderer.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0018\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\b\n��\n\u0002\u0010\f\n��\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/font/GameFontRenderer$Companion;", "", "()V", "getColorIndex", "", "type", "", "LiquidBounce"})
    /* renamed from: net.ccbluex.liquidbounce.ui.font.GameFontRenderer$Companion */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/font/GameFontRenderer$Companion.class */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final int getColorIndex(char type) {
            boolean z;
            boolean z2;
            boolean z3;
            if ('0' <= type) {
                z = type < ':';
            } else {
                z = false;
            }
            if (z) {
                return type - '0';
            }
            if ('a' <= type) {
                z2 = type < 'g';
            } else {
                z2 = false;
            }
            if (z2) {
                return (type - 'a') + 10;
            }
            if ('k' <= type) {
                z3 = type < 'p';
            } else {
                z3 = false;
            }
            return z3 ? (type - 'k') + 16 : type == 'r' ? 21 : -1;
        }
    }
}
