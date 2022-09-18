package net.ccbluex.liquidbounce.p004ui.client.hud.designer;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.p004ui.client.hud.HUD;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.p004ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.Value;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.slf4j.Marker;

/* compiled from: EditorPanel.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��0\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0002\n\u0002\b\b\u0018��2\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\u0018\u0010\u0010\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u0005H\u0002J\u0018\u0010%\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u0005H\u0002J\u0018\u0010&\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u0005H\u0002J\u001e\u0010'\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u00052\u0006\u0010(\u001a\u00020\u0005J\u0018\u0010)\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u0005H\u0002R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0010\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0011\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0012\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n��R\u001e\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005@BX\u0086\u000e¢\u0006\b\n��\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0017\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n��R\u001e\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005@BX\u0086\u000e¢\u0006\b\n��\u001a\u0004\b\u0019\u0010\u0016R\u000e\u0010\u001a\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n��R\u001e\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005@BX\u0086\u000e¢\u0006\b\n��\u001a\u0004\b\u001c\u0010\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u001d\u0010\u0016\"\u0004\b\u001e\u0010\u001fR\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b \u0010\u0016\"\u0004\b!\u0010\u001f¨\u0006*"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/designer/EditorPanel;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "hudDesigner", "Lnet/ccbluex/liquidbounce/ui/client/hud/designer/GuiHudDesigner;", "x", "", "y", "(Lnet/ccbluex/liquidbounce/ui/client/hud/designer/GuiHudDesigner;II)V", "create", "", "getCreate", "()Z", "setCreate", "(Z)V", "currentElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "drag", "dragX", "dragY", "<set-?>", "height", "getHeight", "()I", "mouseDown", "realHeight", "getRealHeight", "scroll", "width", "getWidth", "getX", "setX", "(I)V", "getY", "setY", "", "mouseX", "mouseY", "drawCreate", "drawEditor", "drawPanel", "wheel", "drawSelection", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.designer.EditorPanel */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/designer/EditorPanel.class */
public final class EditorPanel extends MinecraftInstance {
    @NotNull
    private final GuiHudDesigner hudDesigner;

    /* renamed from: x */
    private int f346x;

    /* renamed from: y */
    private int f347y;
    private int width = 80;
    private int height = 20;
    private int realHeight = 20;
    private boolean drag;
    private int dragX;
    private int dragY;
    private boolean mouseDown;
    private int scroll;
    private boolean create;
    @Nullable
    private Element currentElement;

    /* compiled from: EditorPanel.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48)
    /* renamed from: net.ccbluex.liquidbounce.ui.client.hud.designer.EditorPanel$WhenMappings */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/designer/EditorPanel$WhenMappings.class */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[Side.Horizontal.values().length];
            iArr[Side.Horizontal.LEFT.ordinal()] = 1;
            iArr[Side.Horizontal.MIDDLE.ordinal()] = 2;
            iArr[Side.Horizontal.RIGHT.ordinal()] = 3;
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[Side.Vertical.values().length];
            iArr2[Side.Vertical.UP.ordinal()] = 1;
            iArr2[Side.Vertical.MIDDLE.ordinal()] = 2;
            iArr2[Side.Vertical.DOWN.ordinal()] = 3;
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public EditorPanel(@NotNull GuiHudDesigner hudDesigner, int x, int y) {
        Intrinsics.checkNotNullParameter(hudDesigner, "hudDesigner");
        this.hudDesigner = hudDesigner;
        this.f346x = x;
        this.f347y = y;
    }

    public final int getX() {
        return this.f346x;
    }

    public final void setX(int i) {
        this.f346x = i;
    }

    public final int getY() {
        return this.f347y;
    }

    public final void setY(int i) {
        this.f347y = i;
    }

    public final int getWidth() {
        return this.width;
    }

    public final int getHeight() {
        return this.height;
    }

    public final int getRealHeight() {
        return this.realHeight;
    }

    public final boolean getCreate() {
        return this.create;
    }

    public final void setCreate(boolean z) {
        this.create = z;
    }

    public final void drawPanel(int mouseX, int mouseY, int wheel) {
        drag(mouseX, mouseY);
        if (!Intrinsics.areEqual(this.currentElement, this.hudDesigner.getSelectedElement())) {
            this.scroll = 0;
        }
        this.currentElement = this.hudDesigner.getSelectedElement();
        int currMouseY = mouseY;
        boolean shouldScroll = this.realHeight > 200;
        if (shouldScroll) {
            GL11.glPushMatrix();
            RenderUtils.makeScissorBox(this.f346x, this.f347y + 1.0f, this.f346x + this.width, this.f347y + 200.0f);
            GL11.glEnable(3089);
            if (this.f347y + 200 < currMouseY) {
                currMouseY = -1;
            }
            if (mouseX >= this.f346x && mouseX <= this.f346x + this.width && currMouseY >= this.f347y && currMouseY <= this.f347y + 200 && Mouse.hasWheel()) {
                if (wheel < 0 && (-this.scroll) + 205 <= this.realHeight) {
                    this.scroll -= 12;
                } else if (wheel > 0) {
                    this.scroll += 12;
                    if (this.scroll > 0) {
                        this.scroll = 0;
                    }
                }
            }
        }
        Gui.func_73734_a(this.f346x, this.f347y + 12, this.f346x + this.width, this.f347y + this.realHeight, new Color(0, 0, 0, 150).getRGB());
        if (this.create) {
            drawCreate(mouseX, currMouseY);
        } else if (this.currentElement != null) {
            drawEditor(mouseX, currMouseY);
        } else {
            drawSelection(mouseX, currMouseY);
        }
        if (shouldScroll) {
            Gui.func_73734_a((this.f346x + this.width) - 5, this.f347y + 15, (this.f346x + this.width) - 2, this.f347y + 197, new Color(41, 41, 41).getRGB());
            float v = 197 * ((-this.scroll) / (this.realHeight - 170.0f));
            RenderUtils.drawRect((this.f346x + this.width) - 5.0f, this.f347y + 15 + v, (this.f346x + this.width) - 2.0f, this.f347y + 20 + v, new Color(37, 126, 255).getRGB());
            GL11.glDisable(3089);
            GL11.glPopMatrix();
        }
        this.mouseDown = Mouse.isButtonDown(0);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockSplitter
        jadx.core.utils.exceptions.JadxRuntimeException: Unexpected missing predecessor for block: B:39:0x0141
        	at jadx.core.dex.visitors.blocks.BlockSplitter.addTempConnectionsForExcHandlers(BlockSplitter.java:240)
        	at jadx.core.dex.visitors.blocks.BlockSplitter.visit(BlockSplitter.java:54)
        */
    private final void drawCreate(int r12, int r13) {
        /*
            Method dump skipped, instructions count: 486
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.p004ui.client.hud.designer.EditorPanel.drawCreate(int, int):void");
    }

    private final void drawSelection(int mouseX, int mouseY) {
        this.height = 15 + this.scroll;
        this.realHeight = 15;
        this.width = 120;
        Fonts.font35.func_78276_b("§lCreate element", this.f346x + 2, this.f347y + this.height, Color.WHITE.getRGB());
        if (Mouse.isButtonDown(0) && !this.mouseDown && mouseX >= this.f346x && mouseX <= this.f346x + this.width && mouseY >= this.f347y + this.height && mouseY <= this.f347y + this.height + 10) {
            this.create = true;
        }
        this.height += 10;
        this.realHeight += 10;
        Fonts.font35.func_78276_b("§lReset", this.f346x + 2, this.f347y + this.height, Color.WHITE.getRGB());
        if (Mouse.isButtonDown(0) && !this.mouseDown && mouseX >= this.f346x && mouseX <= this.f346x + this.width && mouseY >= this.f347y + this.height && mouseY <= this.f347y + this.height + 10) {
            LiquidBounce.INSTANCE.setHud(HUD.Companion.createDefault());
        }
        this.height += 15;
        this.realHeight += 15;
        Fonts.font35.func_78276_b("§lAvailable Elements", this.f346x + 2, this.f347y + this.height, Color.WHITE.getRGB());
        this.height += 10;
        this.realHeight += 10;
        for (Element element : LiquidBounce.INSTANCE.getHud().getElements()) {
            Fonts.font35.func_78276_b(element.getName(), this.f346x + 2, this.f347y + this.height, Color.WHITE.getRGB());
            int stringWidth = Fonts.font35.func_78256_a(element.getName());
            if (this.width < stringWidth + 8) {
                this.width = stringWidth + 8;
            }
            if (Mouse.isButtonDown(0) && !this.mouseDown && mouseX >= this.f346x && mouseX <= this.f346x + this.width && mouseY >= this.f347y + this.height && mouseY <= this.f347y + this.height + 10) {
                this.hudDesigner.setSelectedElement(element);
            }
            this.height += 10;
            this.realHeight += 10;
        }
        Gui.func_73734_a(this.f346x, this.f347y, this.f346x + this.width, this.f347y + 12, new Color(0, 0, 0, 150).getRGB());
        Fonts.font35.drawString("§lEditor", this.f346x + 2.0f, this.f347y + 3.5f, Color.WHITE.getRGB());
    }

    private final void drawEditor(int mouseX, int mouseY) {
        String str;
        double d;
        double d2;
        this.height = this.scroll + 15;
        this.realHeight = 15;
        int prevWidth = this.width;
        this.width = 100;
        Element element = this.currentElement;
        if (element == null) {
            return;
        }
        GameFontRenderer gameFontRenderer = Fonts.font35;
        StringBuilder append = new StringBuilder().append("X: ");
        Object[] objArr = {Double.valueOf(element.getRenderX())};
        String format = String.format("%.2f", Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkNotNullExpressionValue(format, "format(this, *args)");
        StringBuilder append2 = append.append(format).append(" (");
        Object[] objArr2 = {Double.valueOf(element.getX())};
        String format2 = String.format("%.2f", Arrays.copyOf(objArr2, objArr2.length));
        Intrinsics.checkNotNullExpressionValue(format2, "format(this, *args)");
        gameFontRenderer.func_78276_b(append2.append(format2).append(')').toString(), this.f346x + 2, this.f347y + this.height, Color.WHITE.getRGB());
        this.height += 10;
        this.realHeight += 10;
        GameFontRenderer gameFontRenderer2 = Fonts.font35;
        StringBuilder append3 = new StringBuilder().append("Y: ");
        Object[] objArr3 = {Double.valueOf(element.getRenderY())};
        String format3 = String.format("%.2f", Arrays.copyOf(objArr3, objArr3.length));
        Intrinsics.checkNotNullExpressionValue(format3, "format(this, *args)");
        StringBuilder append4 = append3.append(format3).append(" (");
        Object[] objArr4 = {Double.valueOf(element.getY())};
        String format4 = String.format("%.2f", Arrays.copyOf(objArr4, objArr4.length));
        Intrinsics.checkNotNullExpressionValue(format4, "format(this, *args)");
        gameFontRenderer2.func_78276_b(append4.append(format4).append(')').toString(), this.f346x + 2, this.f347y + this.height, Color.WHITE.getRGB());
        this.height += 10;
        this.realHeight += 10;
        GameFontRenderer gameFontRenderer3 = Fonts.font35;
        Object[] objArr5 = {Float.valueOf(element.getScale())};
        String format5 = String.format("%.2f", Arrays.copyOf(objArr5, objArr5.length));
        Intrinsics.checkNotNullExpressionValue(format5, "format(this, *args)");
        gameFontRenderer3.func_78276_b(Intrinsics.stringPlus("Scale: ", format5), this.f346x + 2, this.f347y + this.height, Color.WHITE.getRGB());
        this.height += 10;
        this.realHeight += 10;
        Fonts.font35.func_78276_b("H:", this.f346x + 2, this.f347y + this.height, Color.WHITE.getRGB());
        Fonts.font35.func_78276_b(element.getSide().getHorizontal().getSideName(), this.f346x + 12, this.f347y + this.height, Color.GRAY.getRGB());
        if (Mouse.isButtonDown(0) && !this.mouseDown && mouseX >= this.f346x && mouseX <= this.f346x + this.width && mouseY >= this.f347y + this.height && mouseY <= this.f347y + this.height + 10) {
            Side.Horizontal[] values = Side.Horizontal.values();
            int currIndex = ArraysKt.indexOf(values, element.getSide().getHorizontal());
            double x = element.getRenderX();
            element.getSide().setHorizontal(values[currIndex + 1 >= values.length ? 0 : currIndex + 1]);
            switch (WhenMappings.$EnumSwitchMapping$0[element.getSide().getHorizontal().ordinal()]) {
                case 1:
                    d2 = x;
                    break;
                case 2:
                    d2 = (new ScaledResolution(MinecraftInstance.f362mc).func_78326_a() / 2) - x;
                    break;
                case 3:
                    d2 = new ScaledResolution(MinecraftInstance.f362mc).func_78326_a() - x;
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            element.setX(d2);
        }
        this.height += 10;
        this.realHeight += 10;
        Fonts.font35.func_78276_b("V:", this.f346x + 2, this.f347y + this.height, Color.WHITE.getRGB());
        Fonts.font35.func_78276_b(element.getSide().getVertical().getSideName(), this.f346x + 12, this.f347y + this.height, Color.GRAY.getRGB());
        if (Mouse.isButtonDown(0) && !this.mouseDown && mouseX >= this.f346x && mouseX <= this.f346x + this.width && mouseY >= this.f347y + this.height && mouseY <= this.f347y + this.height + 10) {
            Side.Vertical[] values2 = Side.Vertical.values();
            int currIndex2 = ArraysKt.indexOf(values2, element.getSide().getVertical());
            double y = element.getRenderY();
            element.getSide().setVertical(values2[currIndex2 + 1 >= values2.length ? 0 : currIndex2 + 1]);
            switch (WhenMappings.$EnumSwitchMapping$1[element.getSide().getVertical().ordinal()]) {
                case 1:
                    d = y;
                    break;
                case 2:
                    d = (new ScaledResolution(MinecraftInstance.f362mc).func_78328_b() / 2) - y;
                    break;
                case 3:
                    d = new ScaledResolution(MinecraftInstance.f362mc).func_78328_b() - y;
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            element.setY(d);
        }
        this.height += 10;
        this.realHeight += 10;
        for (Value value : element.getValues()) {
            if (value.getCanDisplay().invoke().booleanValue()) {
                if (value instanceof BoolValue) {
                    Fonts.font35.func_78276_b(value.getName(), this.f346x + 2, this.f347y + this.height, ((BoolValue) value).get().booleanValue() ? Color.WHITE.getRGB() : Color.GRAY.getRGB());
                    int stringWidth = Fonts.font35.func_78256_a(value.getName());
                    if (this.width < stringWidth + 8) {
                        this.width = stringWidth + 8;
                    }
                    if (Mouse.isButtonDown(0) && !this.mouseDown && mouseX >= this.f346x && mouseX <= this.f346x + this.width && mouseY >= this.f347y + this.height && mouseY <= this.f347y + this.height + 10) {
                        ((BoolValue) value).set(Boolean.valueOf(!((BoolValue) value).get().booleanValue()));
                    }
                    this.height += 10;
                    this.realHeight += 10;
                } else if (value instanceof FloatValue) {
                    float current = ((FloatValue) value).get().floatValue();
                    float min = ((FloatValue) value).getMinimum();
                    float max = ((FloatValue) value).getMaximum();
                    StringBuilder append5 = new StringBuilder().append(value.getName()).append(": §c");
                    Object[] objArr6 = {Float.valueOf(current)};
                    String format6 = String.format("%.2f", Arrays.copyOf(objArr6, objArr6.length));
                    Intrinsics.checkNotNullExpressionValue(format6, "format(this, *args)");
                    String text = append5.append(format6).toString();
                    Fonts.font35.func_78276_b(text, this.f346x + 2, this.f347y + this.height, Color.WHITE.getRGB());
                    int stringWidth2 = Fonts.font35.func_78256_a(text);
                    if (this.width < stringWidth2 + 8) {
                        this.width = stringWidth2 + 8;
                    }
                    RenderUtils.drawRect(this.f346x + 8.0f, this.f347y + this.height + 12.0f, (this.f346x + prevWidth) - 8.0f, this.f347y + this.height + 13.0f, Color.WHITE);
                    float sliderValue = this.f346x + (((prevWidth - 18.0f) * (current - min)) / (max - min));
                    RenderUtils.drawRect(8.0f + sliderValue, this.f347y + this.height + 9.0f, sliderValue + 11.0f, this.f347y + this.height + 15.0f, new Color(37, 126, 255).getRGB());
                    if (mouseX >= this.f346x + 8 && mouseX <= this.f346x + prevWidth && mouseY >= this.f347y + this.height + 9 && mouseY <= this.f347y + this.height + 15 && Mouse.isButtonDown(0)) {
                        float curr = MathHelper.func_76131_a(((mouseX - this.f346x) - 8.0f) / (prevWidth - 18.0f), 0.0f, 1.0f);
                        ((FloatValue) value).set((FloatValue) Float.valueOf(min + ((max - min) * curr)));
                    }
                    this.height += 20;
                    this.realHeight += 20;
                } else if (value instanceof IntegerValue) {
                    int current2 = ((IntegerValue) value).get().intValue();
                    int min2 = ((IntegerValue) value).getMinimum();
                    int max2 = ((IntegerValue) value).getMaximum();
                    String text2 = value.getName() + ": §c" + current2;
                    Fonts.font35.func_78276_b(text2, this.f346x + 2, this.f347y + this.height, Color.WHITE.getRGB());
                    int stringWidth3 = Fonts.font35.func_78256_a(text2);
                    if (this.width < stringWidth3 + 8) {
                        this.width = stringWidth3 + 8;
                    }
                    RenderUtils.drawRect(this.f346x + 8.0f, this.f347y + this.height + 12.0f, (this.f346x + prevWidth) - 8.0f, this.f347y + this.height + 13.0f, Color.WHITE);
                    float sliderValue2 = this.f346x + (((prevWidth - 18.0f) * (current2 - min2)) / (max2 - min2));
                    RenderUtils.drawRect(8.0f + sliderValue2, this.f347y + this.height + 9.0f, sliderValue2 + 11.0f, this.f347y + this.height + 15.0f, new Color(37, 126, 255).getRGB());
                    if (mouseX >= this.f346x + 8 && mouseX <= this.f346x + prevWidth && mouseY >= this.f347y + this.height + 9 && mouseY <= this.f347y + this.height + 15 && Mouse.isButtonDown(0)) {
                        float curr2 = MathHelper.func_76131_a(((mouseX - this.f346x) - 8.0f) / (prevWidth - 18.0f), 0.0f, 1.0f);
                        ((IntegerValue) value).set((IntegerValue) Integer.valueOf((int) (min2 + ((max2 - min2) * curr2))));
                    }
                    this.height += 20;
                    this.realHeight += 20;
                } else if (value instanceof ListValue) {
                    Fonts.font35.func_78276_b(value.getName(), this.f346x + 2, this.f347y + this.height, Color.WHITE.getRGB());
                    Fonts.font35.func_78276_b(String.valueOf(((ListValue) value).openList ? "-" : Marker.ANY_NON_NULL_MARKER), (this.f346x + this.width) - 10, this.f347y + this.height + 1, Color.GRAY.getRGB());
                    if (Mouse.isButtonDown(0) && !this.mouseDown && mouseX >= this.f346x && mouseX <= this.f346x + this.width && mouseY >= this.f347y + this.height && mouseY <= this.f347y + this.height + 10) {
                        ((ListValue) value).openList = !((ListValue) value).openList;
                    }
                    this.height += 10;
                    this.realHeight += 10;
                    if (((ListValue) value).openList) {
                        String[] values3 = ((ListValue) value).getValues();
                        int i = 0;
                        int length = values3.length;
                        while (i < length) {
                            String s = values3[i];
                            i++;
                            String text3 = Intrinsics.stringPlus("§c> §r", s);
                            Fonts.font35.func_78276_b(text3, this.f346x + 2, this.f347y + this.height, Intrinsics.areEqual(s, ((ListValue) value).get()) ? Color.WHITE.getRGB() : Color.GRAY.getRGB());
                            int stringWidth4 = Fonts.font35.func_78256_a(text3);
                            if (this.width < stringWidth4 + 8) {
                                this.width = stringWidth4 + 8;
                            }
                            if (Mouse.isButtonDown(0) && !this.mouseDown && mouseX >= this.f346x && mouseX <= this.f346x + this.width && mouseY >= this.f347y + this.height && mouseY <= this.f347y + this.height + 10) {
                                ((ListValue) value).set(s);
                            }
                            this.height += 10;
                            this.realHeight += 10;
                        }
                    }
                } else if (value instanceof FontValue) {
                    FontRenderer fontRenderer = ((FontValue) value).get();
                    if (fontRenderer instanceof GameFontRenderer) {
                        str = value.getName() + ": " + ((Object) ((GameFontRenderer) fontRenderer).getDefaultFont().getFont().getName()) + " - " + ((GameFontRenderer) fontRenderer).getDefaultFont().getFont().getSize();
                    } else {
                        str = Intrinsics.areEqual(fontRenderer, Fonts.minecraftFont) ? Intrinsics.stringPlus(value.getName(), ": Minecraft") : Intrinsics.stringPlus(value.getName(), ": Unknown");
                    }
                    String text4 = str;
                    Fonts.font35.func_78276_b(text4, this.f346x + 2, this.f347y + this.height, Color.WHITE.getRGB());
                    int stringWidth5 = Fonts.font35.func_78256_a(text4);
                    if (this.width < stringWidth5 + 8) {
                        this.width = stringWidth5 + 8;
                    }
                    if (Mouse.isButtonDown(0) && !this.mouseDown && mouseX >= this.f346x && mouseX <= this.f346x + this.width && mouseY >= this.f347y + this.height && mouseY <= this.f347y + this.height + 10) {
                        List fonts = Fonts.getFonts();
                        Intrinsics.checkNotNullExpressionValue(fonts, "fonts");
                        List $this$forEachIndexed$iv = fonts;
                        int index$iv = 0;
                        for (Object item$iv : $this$forEachIndexed$iv) {
                            int index = index$iv;
                            index$iv = index + 1;
                            if (index < 0) {
                                CollectionsKt.throwIndexOverflow();
                            }
                            FontRenderer font = (FontRenderer) item$iv;
                            if (Intrinsics.areEqual(font, fontRenderer)) {
                                FontValue fontValue = (FontValue) value;
                                FontRenderer fontRenderer2 = fonts.get(index + 1 >= fonts.size() ? 0 : index + 1);
                                Intrinsics.checkNotNullExpressionValue(fontRenderer2, "fonts[if (index + 1 >= f…s.size) 0 else index + 1]");
                                fontValue.set(fontRenderer2);
                            }
                        }
                    }
                    this.height += 10;
                    this.realHeight += 10;
                }
            }
        }
        Gui.func_73734_a(this.f346x, this.f347y, this.f346x + this.width, this.f347y + 12, new Color(0, 0, 0, 150).getRGB());
        Fonts.font35.drawString(Intrinsics.stringPlus("§l", element.getName()), this.f346x + 2.0f, this.f347y + 3.5f, Color.WHITE.getRGB());
        if (!element.getInfo().force()) {
            float deleteWidth = ((this.f346x + this.width) - Fonts.font35.func_78256_a("§lDelete")) - 2.0f;
            Fonts.font35.drawString("§lDelete", deleteWidth, this.f347y + 3.5f, Color.WHITE.getRGB());
            if (Mouse.isButtonDown(0) && !this.mouseDown && mouseX >= deleteWidth && mouseX <= this.f346x + this.width && mouseY >= this.f347y && mouseY <= this.f347y + 10) {
                LiquidBounce.INSTANCE.getHud().removeElement(element);
            }
        }
    }

    private final void drag(int mouseX, int mouseY) {
        if (mouseX >= this.f346x && mouseX <= this.f346x + this.width && mouseY >= this.f347y && mouseY <= this.f347y + 12 && Mouse.isButtonDown(0) && !this.mouseDown) {
            this.drag = true;
            this.dragX = mouseX - this.f346x;
            this.dragY = mouseY - this.f347y;
        }
        if (Mouse.isButtonDown(0) && this.drag) {
            this.f346x = mouseX - this.dragX;
            this.f347y = mouseY - this.dragY;
            return;
        }
        this.drag = false;
    }
}
