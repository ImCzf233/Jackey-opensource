package net.ccbluex.liquidbounce.p004ui.font;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

/* compiled from: AWTFontRenderer.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\\\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0006\u0018�� ,2\u00020\u0001:\u0002+,B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\u001a\u001a\u00020\u001bH\u0002J \u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001fH\u0002J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0002J&\u0010%\u001a\u00020\u001b2\u0006\u0010&\u001a\u00020\n2\u0006\u0010\u001e\u001a\u00020'2\u0006\u0010 \u001a\u00020'2\u0006\u0010(\u001a\u00020\u0005J\u000e\u0010)\u001a\u00020\u00052\u0006\u0010&\u001a\u00020\nJ\u0018\u0010*\u001a\u00020\u001b2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0002R*\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b`\fX\u0082\u0004¢\u0006\u0002\n��R\u0018\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000eX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n��R\u0011\u0010\u0014\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0018\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0019\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n��¨\u0006-"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer;", "", "font", "Ljava/awt/Font;", "startChar", "", "stopChar", "(Ljava/awt/Font;II)V", "cachedStrings", "Ljava/util/HashMap;", "", "Lnet/ccbluex/liquidbounce/ui/font/CachedFont;", "Lkotlin/collections/HashMap;", "charLocations", "", "Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer$CharLocation;", "[Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer$CharLocation;", "getFont", "()Ljava/awt/Font;", "fontHeight", "height", "getHeight", "()I", "textureHeight", "textureID", "textureWidth", "collectGarbage", "", "drawChar", "char", "x", "", "y", "drawCharToImage", "Ljava/awt/image/BufferedImage;", "ch", "", "drawString", "text", "", "color", "getStringWidth", "renderBitmap", "CharLocation", "Companion", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.font.AWTFontRenderer */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/font/AWTFontRenderer.class */
public final class AWTFontRenderer {
    @NotNull
    private final Font font;
    private int fontHeight;
    @NotNull
    private final CharLocation[] charLocations;
    @NotNull
    private final HashMap<String, CachedFont> cachedStrings;
    private int textureID;
    private int textureWidth;
    private int textureHeight;
    private static boolean assumeNonVolatile;
    private static int gcTicks;
    private static final int GC_TICKS = 600;
    private static final int CACHED_FONT_REMOVAL_TIME = 30000;
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final ArrayList<AWTFontRenderer> activeFontRenderers = new ArrayList<>();

    public AWTFontRenderer(@NotNull Font font, int startChar, int stopChar) {
        Intrinsics.checkNotNullParameter(font, "font");
        this.font = font;
        this.fontHeight = -1;
        this.charLocations = new CharLocation[stopChar];
        this.cachedStrings = new HashMap<>();
        renderBitmap(startChar, stopChar);
        activeFontRenderers.add(this);
    }

    public /* synthetic */ AWTFontRenderer(Font font, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(font, (i3 & 2) != 0 ? 0 : i, (i3 & 4) != 0 ? 255 : i2);
    }

    @NotNull
    public final Font getFont() {
        return this.font;
    }

    /* compiled from: AWTFontRenderer.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��2\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n��\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0013\u001a\u00020\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n��R!\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\b0\u0007j\b\u0012\u0004\u0012\u00020\b`\t¢\u0006\b\n��\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��¨\u0006\u0015"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer$Companion;", "", "()V", "CACHED_FONT_REMOVAL_TIME", "", "GC_TICKS", "activeFontRenderers", "Ljava/util/ArrayList;", "Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer;", "Lkotlin/collections/ArrayList;", "getActiveFontRenderers", "()Ljava/util/ArrayList;", "assumeNonVolatile", "", "getAssumeNonVolatile", "()Z", "setAssumeNonVolatile", "(Z)V", "gcTicks", "garbageCollectionTick", "", "LiquidBounce"})
    /* renamed from: net.ccbluex.liquidbounce.ui.font.AWTFontRenderer$Companion */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/font/AWTFontRenderer$Companion.class */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }

        public final boolean getAssumeNonVolatile() {
            return AWTFontRenderer.assumeNonVolatile;
        }

        public final void setAssumeNonVolatile(boolean z) {
            AWTFontRenderer.assumeNonVolatile = z;
        }

        @NotNull
        public final ArrayList<AWTFontRenderer> getActiveFontRenderers() {
            return AWTFontRenderer.activeFontRenderers;
        }

        public final void garbageCollectionTick() {
            int i = AWTFontRenderer.gcTicks;
            AWTFontRenderer.gcTicks = i + 1;
            if (i > AWTFontRenderer.GC_TICKS) {
                Iterable $this$forEach$iv = getActiveFontRenderers();
                for (Object element$iv : $this$forEach$iv) {
                    AWTFontRenderer it = (AWTFontRenderer) element$iv;
                    it.collectGarbage();
                }
                AWTFontRenderer.gcTicks = 0;
            }
        }
    }

    public final void collectGarbage() {
        long currentTime = System.currentTimeMillis();
        Map $this$filter$iv = this.cachedStrings;
        Map destination$iv$iv = new LinkedHashMap();
        for (Map.Entry element$iv$iv : $this$filter$iv.entrySet()) {
            if (currentTime - element$iv$iv.getValue().getLastUsage() > 30000) {
                destination$iv$iv.put(element$iv$iv.getKey(), element$iv$iv.getValue());
            }
        }
        for (Map.Entry element$iv : destination$iv$iv.entrySet()) {
            GL11.glDeleteLists(((CachedFont) element$iv.getValue()).getDisplayList(), 1);
            ((CachedFont) element$iv.getValue()).setDeleted(true);
            this.cachedStrings.remove(element$iv.getKey());
        }
    }

    public final int getHeight() {
        return (this.fontHeight - 8) / 2;
    }

    public final void drawString(@NotNull String text, double x, double y, int color) {
        Intrinsics.checkNotNullParameter(text, "text");
        double reverse = 1 / 0.25d;
        GlStateManager.func_179094_E();
        GlStateManager.func_179139_a(0.25d, 0.25d, 0.25d);
        GL11.glTranslated(x * 2.0f, (y * 2.0d) - 2.0d, 0.0d);
        GlStateManager.func_179144_i(this.textureID);
        float red = ((color >> 16) & 255) / 255.0f;
        float green = ((color >> 8) & 255) / 255.0f;
        float blue = (color & 255) / 255.0f;
        float alpha = ((color >> 24) & 255) / 255.0f;
        GlStateManager.func_179131_c(red, green, blue, alpha);
        double currX = 0.0d;
        CachedFont cached = this.cachedStrings.get(text);
        if (cached != null) {
            GL11.glCallList(cached.getDisplayList());
            cached.setLastUsage(System.currentTimeMillis());
            GlStateManager.func_179121_F();
            return;
        }
        int list = -1;
        if (assumeNonVolatile) {
            list = GL11.glGenLists(1);
            GL11.glNewList(list, 4865);
        }
        GL11.glBegin(7);
        char[] charArray = text.toCharArray();
        Intrinsics.checkNotNullExpressionValue(charArray, "this as java.lang.String).toCharArray()");
        int i = 0;
        int length = charArray.length;
        while (i < length) {
            char c = charArray[i];
            i++;
            if (c >= this.charLocations.length) {
                GL11.glEnd();
                GlStateManager.func_179139_a(reverse, reverse, reverse);
                Minecraft.func_71410_x().field_71466_p.func_175065_a(String.valueOf(c), (((float) currX) * ((float) 0.25d)) + 1, 2.0f, color, false);
                currX += Minecraft.func_71410_x().field_71466_p.func_78256_a(String.valueOf(c)) * reverse;
                GlStateManager.func_179139_a(0.25d, 0.25d, 0.25d);
                GlStateManager.func_179144_i(this.textureID);
                GlStateManager.func_179131_c(red, green, blue, alpha);
                GL11.glBegin(7);
            } else {
                CharLocation fontChar = this.charLocations[c];
                if (fontChar != null) {
                    drawChar(fontChar, (float) currX, 0.0f);
                    currX += fontChar.getWidth() - 8.0d;
                }
            }
        }
        GL11.glEnd();
        if (assumeNonVolatile) {
            this.cachedStrings.put(text, new CachedFont(list, System.currentTimeMillis(), false, 4, null));
            GL11.glEndList();
        }
        GlStateManager.func_179121_F();
    }

    private final void drawChar(CharLocation charLocation, float x, float y) {
        float width = charLocation.getWidth();
        float height = charLocation.getHeight();
        float srcX = charLocation.getX();
        float srcY = charLocation.getY();
        float renderX = srcX / this.textureWidth;
        float renderY = srcY / this.textureHeight;
        float renderWidth = width / this.textureWidth;
        float renderHeight = height / this.textureHeight;
        GL11.glTexCoord2f(renderX, renderY);
        GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(renderX, renderY + renderHeight);
        GL11.glVertex2f(x, y + height);
        GL11.glTexCoord2f(renderX + renderWidth, renderY + renderHeight);
        GL11.glVertex2f(x + width, y + height);
        GL11.glTexCoord2f(renderX + renderWidth, renderY);
        GL11.glVertex2f(x + width, y);
    }

    private final void renderBitmap(int startChar, int stopChar) {
        Image[] imageArr = new BufferedImage[stopChar];
        int rowHeight = 0;
        int charX = 0;
        int charY = 0;
        int i = startChar;
        while (i < stopChar) {
            int targetChar = i;
            i++;
            BufferedImage fontImage = drawCharToImage((char) targetChar);
            CharLocation fontChar = new CharLocation(charX, charY, fontImage.getWidth(), fontImage.getHeight());
            if (fontChar.getHeight() > this.fontHeight) {
                this.fontHeight = fontChar.getHeight();
            }
            if (fontChar.getHeight() > rowHeight) {
                rowHeight = fontChar.getHeight();
            }
            this.charLocations[targetChar] = fontChar;
            imageArr[targetChar] = fontImage;
            charX += fontChar.getWidth();
            if (charX > 2048) {
                if (charX > this.textureWidth) {
                    this.textureWidth = charX;
                }
                charX = 0;
                charY += rowHeight;
                rowHeight = 0;
            }
        }
        this.textureHeight = charY + rowHeight;
        BufferedImage bufferedImage = new BufferedImage(this.textureWidth, this.textureHeight, 2);
        Graphics2D graphics = bufferedImage.getGraphics();
        if (graphics == null) {
            throw new NullPointerException("null cannot be cast to non-null type java.awt.Graphics2D");
        }
        Graphics2D graphics2D = graphics;
        graphics2D.setFont(this.font);
        graphics2D.setColor(new Color(255, 255, 255, 0));
        graphics2D.fillRect(0, 0, this.textureWidth, this.textureHeight);
        graphics2D.setColor(Color.white);
        int i2 = startChar;
        while (i2 < stopChar) {
            int targetChar2 = i2;
            i2++;
            if (imageArr[targetChar2] != null && this.charLocations[targetChar2] != null) {
                CharLocation charLocation = this.charLocations[targetChar2];
                Intrinsics.checkNotNull(charLocation);
                int x = charLocation.getX();
                CharLocation charLocation2 = this.charLocations[targetChar2];
                Intrinsics.checkNotNull(charLocation2);
                graphics2D.drawImage(imageArr[targetChar2], x, charLocation2.getY(), (ImageObserver) null);
            }
        }
        this.textureID = TextureUtil.func_110989_a(TextureUtil.func_110996_a(), bufferedImage, true, true);
    }

    private final BufferedImage drawCharToImage(char ch) {
        Graphics2D graphics = new BufferedImage(1, 1, 2).getGraphics();
        if (graphics == null) {
            throw new NullPointerException("null cannot be cast to non-null type java.awt.Graphics2D");
        }
        Graphics2D graphics2D = graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setFont(this.font);
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int charWidth = fontMetrics.charWidth(ch) + 8;
        if (charWidth <= 0) {
            charWidth = 7;
        }
        int charHeight = fontMetrics.getHeight() + 3;
        if (charHeight <= 0) {
            charHeight = this.font.getSize();
        }
        BufferedImage fontImage = new BufferedImage(charWidth, charHeight, 2);
        Graphics2D graphics2 = fontImage.getGraphics();
        if (graphics2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type java.awt.Graphics2D");
        }
        Graphics2D graphics3 = graphics2;
        graphics3.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics3.setFont(this.font);
        graphics3.setColor(Color.WHITE);
        graphics3.drawString(String.valueOf(ch), 3, 1 + fontMetrics.getAscent());
        return fontImage;
    }

    public final int getStringWidth(@NotNull String text) {
        char c;
        Intrinsics.checkNotNullParameter(text, "text");
        int width = 0;
        char[] charArray = text.toCharArray();
        Intrinsics.checkNotNullExpressionValue(charArray, "this as java.lang.String).toCharArray()");
        int i = 0;
        int length = charArray.length;
        while (i < length) {
            char c2 = charArray[i];
            i++;
            CharLocation[] charLocationArr = this.charLocations;
            if (c2 < this.charLocations.length) {
                c = c2;
            } else {
                c = 3;
            }
            CharLocation fontChar = charLocationArr[c];
            if (fontChar != null) {
                width += fontChar.getWidth() - 8;
            }
        }
        return width / 2;
    }

    /* compiled from: AWTFontRenderer.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"�� \n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0010\b\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n��\b\u0082\b\u0018��2\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J1\u0010\u0016\u001a\u00020��2\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001R\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\f\u0010\t\"\u0004\b\r\u0010\u000bR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u000e\u0010\t\"\u0004\b\u000f\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0010\u0010\t\"\u0004\b\u0011\u0010\u000b¨\u0006\u001d"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer$CharLocation;", "", "x", "", "y", "width", "height", "(IIII)V", "getHeight", "()I", "setHeight", "(I)V", "getWidth", "setWidth", "getX", "setX", "getY", "setY", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", "LiquidBounce"})
    /* renamed from: net.ccbluex.liquidbounce.ui.font.AWTFontRenderer$CharLocation */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/font/AWTFontRenderer$CharLocation.class */
    public static final class CharLocation {

        /* renamed from: x */
        private int f360x;

        /* renamed from: y */
        private int f361y;
        private int width;
        private int height;

        public final int component1() {
            return this.f360x;
        }

        public final int component2() {
            return this.f361y;
        }

        public final int component3() {
            return this.width;
        }

        public final int component4() {
            return this.height;
        }

        @NotNull
        public final CharLocation copy(int x, int y, int width, int height) {
            return new CharLocation(x, y, width, height);
        }

        public static /* synthetic */ CharLocation copy$default(CharLocation charLocation, int i, int i2, int i3, int i4, int i5, Object obj) {
            if ((i5 & 1) != 0) {
                i = charLocation.f360x;
            }
            if ((i5 & 2) != 0) {
                i2 = charLocation.f361y;
            }
            if ((i5 & 4) != 0) {
                i3 = charLocation.width;
            }
            if ((i5 & 8) != 0) {
                i4 = charLocation.height;
            }
            return charLocation.copy(i, i2, i3, i4);
        }

        @NotNull
        public String toString() {
            return "CharLocation(x=" + this.f360x + ", y=" + this.f361y + ", width=" + this.width + ", height=" + this.height + ')';
        }

        public int hashCode() {
            int result = Integer.hashCode(this.f360x);
            return (((((result * 31) + Integer.hashCode(this.f361y)) * 31) + Integer.hashCode(this.width)) * 31) + Integer.hashCode(this.height);
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof CharLocation)) {
                return false;
            }
            CharLocation charLocation = (CharLocation) other;
            return this.f360x == charLocation.f360x && this.f361y == charLocation.f361y && this.width == charLocation.width && this.height == charLocation.height;
        }

        public CharLocation(int x, int y, int width, int height) {
            this.f360x = x;
            this.f361y = y;
            this.width = width;
            this.height = height;
        }

        public final int getX() {
            return this.f360x;
        }

        public final void setX(int i) {
            this.f360x = i;
        }

        public final int getY() {
            return this.f361y;
        }

        public final void setY(int i) {
            this.f361y = i;
        }

        public final int getWidth() {
            return this.width;
        }

        public final void setWidth(int i) {
            this.width = i;
        }

        public final int getHeight() {
            return this.height;
        }

        public final void setHeight(int i) {
            this.height = i;
        }
    }
}
