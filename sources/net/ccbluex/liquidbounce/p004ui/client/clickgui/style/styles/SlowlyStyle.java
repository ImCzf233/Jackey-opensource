package net.ccbluex.liquidbounce.p004ui.client.clickgui.style.styles;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.List;
import net.ccbluex.liquidbounce.p004ui.client.clickgui.Panel;
import net.ccbluex.liquidbounce.p004ui.client.clickgui.elements.ButtonElement;
import net.ccbluex.liquidbounce.p004ui.client.clickgui.elements.ModuleElement;
import net.ccbluex.liquidbounce.p004ui.client.clickgui.style.Style;
import net.ccbluex.liquidbounce.p004ui.font.AWTFontRenderer;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.p004ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BlockValue;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.Value;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import org.lwjgl.input.Mouse;
import org.slf4j.Marker;

/* renamed from: net.ccbluex.liquidbounce.ui.client.clickgui.style.styles.SlowlyStyle */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/clickgui/style/styles/SlowlyStyle.class */
public class SlowlyStyle extends Style {
    private boolean mouseDown;
    private boolean rightMouseDown;

    public static float drawSlider(float value, float min, float max, boolean inte, int x, int y, int width, int mouseX, int mouseY, Color color) {
        float displayValue = Math.max(min, Math.min(value, max));
        float sliderValue = x + ((width * (displayValue - min)) / (max - min));
        RenderUtils.drawRect(x, y, x + width, y + 2, Integer.MAX_VALUE);
        RenderUtils.drawRect(x, y, sliderValue, y + 2, color);
        RenderUtils.drawFilledCircle((int) sliderValue, y + 1, 3.0f, color);
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + 3) {
            int dWheel = Mouse.getDWheel();
            if (dWheel != 0) {
                if (dWheel > 0) {
                    return Math.min(value + (inte ? 1.0f : 0.01f), max);
                } else if (dWheel < 0) {
                    return Math.max(value - (inte ? 1.0f : 0.01f), min);
                }
            }
            if (Mouse.isButtonDown(0)) {
                double i = MathHelper.func_151237_a((mouseX - x) / (width - 3.0d), 0.0d, 1.0d);
                BigDecimal bigDecimal = new BigDecimal(Double.toString(min + ((max - min) * i)));
                return bigDecimal.setScale(2, 4).floatValue();
            }
        }
        return value;
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.clickgui.style.Style
    public void drawPanel(int mouseX, int mouseY, Panel panel) {
        RenderUtils.drawBorderedRect(panel.getX(), panel.getY() - 3.0f, panel.getX() + panel.getWidth(), panel.getY() + 17.0f, 3.0f, new Color(0, 0, 0).getRGB(), new Color(0, 0, 0).getRGB());
        if (panel.getFade() > 0) {
            RenderUtils.drawBorderedRect(panel.getX(), panel.getY() + 17.0f, panel.getX() + panel.getWidth(), panel.getY() + 19 + panel.getFade(), 3.0f, new Color(0, 0, 0).getRGB(), new Color(0, 0, 0).getRGB());
            RenderUtils.drawBorderedRect(panel.getX(), panel.getY() + 17 + panel.getFade(), panel.getX() + panel.getWidth(), panel.getY() + 19 + panel.getFade() + 5, 3.0f, new Color(0, 0, 0).getRGB(), new Color(0, 0, 0).getRGB());
        }
        GlStateManager.func_179117_G();
        float textWidth = Fonts.font35.func_78256_a("§f" + StringUtils.func_76338_a(panel.getName()));
        Fonts.font35.func_78276_b(panel.getName(), (int) (panel.getX() - ((textWidth - 100.0f) / 2.0f)), (panel.getY() + 7) - 3, Color.WHITE.getRGB());
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.clickgui.style.Style
    public void drawDescription(int mouseX, int mouseY, String text) {
        int textWidth = Fonts.font35.func_78256_a(text);
        RenderUtils.drawBorderedRect(mouseX + 9, mouseY, mouseX + textWidth + 14, mouseY + Fonts.font35.field_78288_b + 3, 3.0f, new Color(0, 0, 0).getRGB(), new Color(0, 0, 0).getRGB());
        GlStateManager.func_179117_G();
        Fonts.font35.func_78276_b(text, mouseX + 12, mouseY + (Fonts.font35.field_78288_b / 2), Color.WHITE.getRGB());
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.clickgui.style.Style
    public void drawButtonElement(int mouseX, int mouseY, ButtonElement buttonElement) {
        Gui.func_73734_a(buttonElement.getX() - 1, buttonElement.getY() - 1, buttonElement.getX() + buttonElement.getWidth() + 1, buttonElement.getY() + buttonElement.getHeight() + 1, hoverColor(buttonElement.getColor() != Integer.MAX_VALUE ? new Color(10, 10, 10) : new Color(0, 0, 0), buttonElement.hoverTime).getRGB());
        GlStateManager.func_179117_G();
        Fonts.font35.func_78276_b(buttonElement.getDisplayName(), buttonElement.getX() + 5, buttonElement.getY() + 5, Color.WHITE.getRGB());
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.clickgui.style.Style
    public void drawModuleElement(int mouseX, int mouseY, ModuleElement moduleElement) {
        String[] values;
        Gui.func_73734_a(moduleElement.getX() - 1, moduleElement.getY() - 1, moduleElement.getX() + moduleElement.getWidth() + 1, moduleElement.getY() + moduleElement.getHeight() + 1, hoverColor(new Color(40, 40, 40), moduleElement.hoverTime).getRGB());
        Gui.func_73734_a(moduleElement.getX() - 1, moduleElement.getY() - 1, moduleElement.getX() + moduleElement.getWidth() + 1, moduleElement.getY() + moduleElement.getHeight() + 1, hoverColor(new Color(0, 0, 0, moduleElement.slowlyFade), moduleElement.hoverTime).getRGB());
        GlStateManager.func_179117_G();
        Fonts.font35.func_78276_b(moduleElement.getDisplayName(), moduleElement.getX() + 5, moduleElement.getY() + 5, Color.WHITE.getRGB());
        List<Value<?>> moduleValues = moduleElement.getModule().getValues();
        if (!moduleValues.isEmpty()) {
            Fonts.font35.func_78276_b(">", (moduleElement.getX() + moduleElement.getWidth()) - 8, moduleElement.getY() + 5, Color.WHITE.getRGB());
            if (moduleElement.isShowSettings()) {
                if (moduleElement.getSettingsWidth() > 0.0f && moduleElement.slowlySettingsYPos > moduleElement.getY() + 6) {
                    RenderUtils.drawBorderedRect(moduleElement.getX() + moduleElement.getWidth() + 4, moduleElement.getY() + 6, moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth(), moduleElement.slowlySettingsYPos + 2, 3.0f, new Color(0, 0, 0).getRGB(), new Color(0, 0, 0).getRGB());
                }
                moduleElement.slowlySettingsYPos = moduleElement.getY() + 6;
                for (Value value : moduleValues) {
                    if (value.getCanDisplay().invoke().booleanValue()) {
                        boolean isNumber = value.get() instanceof Number;
                        if (isNumber) {
                            AWTFontRenderer.Companion.setAssumeNonVolatile(false);
                        }
                        if (value instanceof BoolValue) {
                            String text = value.getName();
                            float textWidth = Fonts.font35.func_78256_a(text);
                            if (moduleElement.getSettingsWidth() < textWidth + 8.0f) {
                                moduleElement.setSettingsWidth(textWidth + 8.0f);
                            }
                            if (mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && mouseX <= moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth() && mouseY >= moduleElement.slowlySettingsYPos && mouseY <= moduleElement.slowlySettingsYPos + 12 && Mouse.isButtonDown(0) && moduleElement.isntPressed()) {
                                BoolValue boolValue = (BoolValue) value;
                                boolValue.set(Boolean.valueOf(!boolValue.get().booleanValue()));
                                f362mc.func_147118_V().func_147682_a(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                            }
                            Fonts.font35.func_78276_b(text, moduleElement.getX() + moduleElement.getWidth() + 6, moduleElement.slowlySettingsYPos + 2, ((BoolValue) value).get().booleanValue() ? Color.WHITE.getRGB() : Integer.MAX_VALUE);
                            moduleElement.slowlySettingsYPos += 11;
                        } else if (value instanceof ListValue) {
                            ListValue listValue = (ListValue) value;
                            String text2 = value.getName();
                            float textWidth2 = Fonts.font35.func_78256_a(text2);
                            if (moduleElement.getSettingsWidth() < textWidth2 + 16.0f) {
                                moduleElement.setSettingsWidth(textWidth2 + 16.0f);
                            }
                            Fonts.font35.func_78276_b(text2, moduleElement.getX() + moduleElement.getWidth() + 6, moduleElement.slowlySettingsYPos + 2, 16777215);
                            Fonts.font35.func_78276_b(listValue.openList ? "-" : Marker.ANY_NON_NULL_MARKER, (int) (((moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth()) - (listValue.openList ? 5 : 6)), moduleElement.slowlySettingsYPos + 2, 16777215);
                            if (mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && mouseX <= moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth() && mouseY >= moduleElement.slowlySettingsYPos && mouseY <= moduleElement.slowlySettingsYPos + Fonts.font35.field_78288_b && Mouse.isButtonDown(0) && moduleElement.isntPressed()) {
                                listValue.openList = !listValue.openList;
                                f362mc.func_147118_V().func_147682_a(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                            }
                            moduleElement.slowlySettingsYPos += Fonts.font35.field_78288_b + 1;
                            for (String valueOfList : listValue.getValues()) {
                                float textWidth22 = Fonts.font35.func_78256_a("> " + valueOfList);
                                if (moduleElement.getSettingsWidth() < textWidth22 + 12.0f) {
                                    moduleElement.setSettingsWidth(textWidth22 + 12.0f);
                                }
                                if (listValue.openList) {
                                    if (mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && mouseX <= moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth() && mouseY >= moduleElement.slowlySettingsYPos + 2 && mouseY <= moduleElement.slowlySettingsYPos + 14 && Mouse.isButtonDown(0) && moduleElement.isntPressed()) {
                                        listValue.set(valueOfList);
                                        f362mc.func_147118_V().func_147682_a(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                                    }
                                    GlStateManager.func_179117_G();
                                    Fonts.font35.func_78276_b("> " + valueOfList, moduleElement.getX() + moduleElement.getWidth() + 6, moduleElement.slowlySettingsYPos + 2, (listValue.get() == null || !listValue.get().equalsIgnoreCase(valueOfList)) ? Integer.MAX_VALUE : Color.WHITE.getRGB());
                                    moduleElement.slowlySettingsYPos += Fonts.font35.field_78288_b + 1;
                                }
                            }
                            if (!listValue.openList) {
                                moduleElement.slowlySettingsYPos++;
                            }
                        } else if (value instanceof FloatValue) {
                            FloatValue floatValue = (FloatValue) value;
                            String text3 = value.getName() + "§f: " + round(floatValue.get().floatValue()) + floatValue.getSuffix();
                            float textWidth3 = Fonts.font35.func_78256_a(text3);
                            if (moduleElement.getSettingsWidth() < textWidth3 + 8.0f) {
                                moduleElement.setSettingsWidth(textWidth3 + 8.0f);
                            }
                            float valueOfSlide = drawSlider(floatValue.get().floatValue(), floatValue.getMinimum(), floatValue.getMaximum(), false, moduleElement.getX() + moduleElement.getWidth() + 8, moduleElement.slowlySettingsYPos + 14, ((int) moduleElement.getSettingsWidth()) - 12, mouseX, mouseY, new Color(120, 120, 120));
                            if (valueOfSlide != floatValue.get().floatValue()) {
                                floatValue.set((FloatValue) Float.valueOf(valueOfSlide));
                            }
                            Fonts.font35.func_78276_b(text3, moduleElement.getX() + moduleElement.getWidth() + 6, moduleElement.slowlySettingsYPos + 3, 16777215);
                            moduleElement.slowlySettingsYPos += 19;
                        } else if (value instanceof IntegerValue) {
                            IntegerValue integerValue = (IntegerValue) value;
                            String text4 = value.getName() + "§f: " + (value instanceof BlockValue ? BlockUtils.getBlockName(integerValue.get().intValue()) + " (" + integerValue.get() + ")" : integerValue.get() + integerValue.getSuffix());
                            float textWidth4 = Fonts.font35.func_78256_a(text4);
                            if (moduleElement.getSettingsWidth() < textWidth4 + 8.0f) {
                                moduleElement.setSettingsWidth(textWidth4 + 8.0f);
                            }
                            float valueOfSlide2 = drawSlider(integerValue.get().intValue(), integerValue.getMinimum(), integerValue.getMaximum(), true, moduleElement.getX() + moduleElement.getWidth() + 8, moduleElement.slowlySettingsYPos + 14, ((int) moduleElement.getSettingsWidth()) - 12, mouseX, mouseY, new Color(120, 120, 120));
                            if (valueOfSlide2 != integerValue.get().intValue()) {
                                integerValue.set((IntegerValue) Integer.valueOf((int) valueOfSlide2));
                            }
                            Fonts.font35.func_78276_b(text4, moduleElement.getX() + moduleElement.getWidth() + 6, moduleElement.slowlySettingsYPos + 3, 16777215);
                            moduleElement.slowlySettingsYPos += 19;
                        } else if (value instanceof FontValue) {
                            FontValue fontValue = (FontValue) value;
                            FontRenderer fontRenderer = fontValue.get();
                            String displayString = "Font: Unknown";
                            if (fontRenderer instanceof GameFontRenderer) {
                                GameFontRenderer liquidFontRenderer = (GameFontRenderer) fontRenderer;
                                displayString = "Font: " + liquidFontRenderer.getDefaultFont().getFont().getName() + " - " + liquidFontRenderer.getDefaultFont().getFont().getSize();
                            } else if (fontRenderer == Fonts.minecraftFont) {
                                displayString = "Font: Minecraft";
                            } else {
                                Object[] objects = Fonts.getFontDetails(fontRenderer);
                                if (objects != null) {
                                    displayString = objects[0] + (((Integer) objects[1]).intValue() != -1 ? " - " + objects[1] : "");
                                }
                            }
                            Fonts.font35.func_78276_b(displayString, moduleElement.getX() + moduleElement.getWidth() + 6, moduleElement.slowlySettingsYPos + 2, Color.WHITE.getRGB());
                            int stringWidth = Fonts.font35.func_78256_a(displayString);
                            if (moduleElement.getSettingsWidth() < stringWidth + 8) {
                                moduleElement.setSettingsWidth(stringWidth + 8);
                            }
                            if (((Mouse.isButtonDown(0) && !this.mouseDown) || (Mouse.isButtonDown(1) && !this.rightMouseDown)) && mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && mouseX <= moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth() && mouseY >= moduleElement.slowlySettingsYPos && mouseY <= moduleElement.slowlySettingsYPos + 12) {
                                List<FontRenderer> fonts = Fonts.getFonts();
                                if (Mouse.isButtonDown(0)) {
                                    int i = 0;
                                    while (true) {
                                        if (i >= fonts.size()) {
                                            break;
                                        }
                                        FontRenderer font = fonts.get(i);
                                        if (font != fontRenderer) {
                                            i++;
                                        } else {
                                            int i2 = i + 1;
                                            if (i2 >= fonts.size()) {
                                                i2 = 0;
                                            }
                                            fontValue.set(fonts.get(i2));
                                        }
                                    }
                                } else {
                                    int i3 = fonts.size() - 1;
                                    while (true) {
                                        if (i3 < 0) {
                                            break;
                                        }
                                        FontRenderer font2 = fonts.get(i3);
                                        if (font2 != fontRenderer) {
                                            i3--;
                                        } else {
                                            int i4 = i3 - 1;
                                            if (i4 >= fonts.size()) {
                                                i4 = 0;
                                            }
                                            if (i4 < 0) {
                                                i4 = fonts.size() - 1;
                                            }
                                            fontValue.set(fonts.get(i4));
                                        }
                                    }
                                }
                            }
                            moduleElement.slowlySettingsYPos += 11;
                        } else {
                            String text5 = value.getName() + "§f: " + value.get();
                            float textWidth5 = Fonts.font35.func_78256_a(text5);
                            if (moduleElement.getSettingsWidth() < textWidth5 + 8.0f) {
                                moduleElement.setSettingsWidth(textWidth5 + 8.0f);
                            }
                            GlStateManager.func_179117_G();
                            Fonts.font35.func_78276_b(text5, moduleElement.getX() + moduleElement.getWidth() + 6, moduleElement.slowlySettingsYPos + 4, 16777215);
                            moduleElement.slowlySettingsYPos += 12;
                        }
                        if (isNumber) {
                            AWTFontRenderer.Companion.setAssumeNonVolatile(true);
                        }
                    }
                }
                moduleElement.updatePressed();
                this.mouseDown = Mouse.isButtonDown(0);
                this.rightMouseDown = Mouse.isButtonDown(1);
            }
        }
    }

    private BigDecimal round(float v) {
        BigDecimal bigDecimal = new BigDecimal(Float.toString(v));
        return bigDecimal.setScale(2, 4);
    }

    private Color hoverColor(Color color, int hover) {
        int r = color.getRed() - (hover * 2);
        int g = color.getGreen() - (hover * 2);
        int b = color.getBlue() - (hover * 2);
        return new Color(Math.max(r, 0), Math.max(g, 0), Math.max(b, 0), color.getAlpha());
    }
}
