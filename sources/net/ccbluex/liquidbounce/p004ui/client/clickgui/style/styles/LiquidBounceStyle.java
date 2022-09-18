package net.ccbluex.liquidbounce.p004ui.client.clickgui.style.styles;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.ClickGUI;
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
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import org.lwjgl.input.Mouse;
import org.slf4j.Marker;

/* renamed from: net.ccbluex.liquidbounce.ui.client.clickgui.style.styles.LiquidBounceStyle */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/clickgui/style/styles/LiquidBounceStyle.class */
public class LiquidBounceStyle extends Style {
    private boolean mouseDown;
    private boolean rightMouseDown;

    @Override // net.ccbluex.liquidbounce.p004ui.client.clickgui.style.Style
    public void drawPanel(int mouseX, int mouseY, Panel panel) {
        RenderUtils.drawBorderedRect(panel.getX() - (panel.getScrollbar() ? 4 : 0), panel.getY(), panel.getX() + panel.getWidth(), panel.getY() + 19.0f + panel.getFade(), 1.0f, new Color(255, 255, 255, 90).getRGB(), Integer.MIN_VALUE);
        float textWidth = Fonts.font35.func_78256_a("§f" + StringUtils.func_76338_a(panel.getName()));
        Fonts.font35.func_78276_b("§f" + panel.getName(), (int) (panel.getX() - ((textWidth - 100.0f) / 2.0f)), panel.getY() + 7, -16777216);
        if (panel.getScrollbar() && panel.getFade() > 0) {
            RenderUtils.drawRect(panel.getX() - 1.5f, panel.getY() + 21, panel.getX() - 0.5f, panel.getY() + 16 + panel.getFade(), Integer.MAX_VALUE);
            RenderUtils.drawRect(panel.getX() - 2, ((panel.getY() + 30) + (((panel.getFade() - 24.0f) / (panel.getElements().size() - ((ClickGUI) LiquidBounce.moduleManager.getModule(ClickGUI.class)).maxElementsValue.get().intValue())) * panel.getDragged())) - 10.0f, panel.getX(), panel.getY() + 40 + (((panel.getFade() - 24.0f) / (panel.getElements().size() - ((ClickGUI) LiquidBounce.moduleManager.getModule(ClickGUI.class)).maxElementsValue.get().intValue())) * panel.getDragged()), Integer.MIN_VALUE);
        }
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.clickgui.style.Style
    public void drawDescription(int mouseX, int mouseY, String text) {
        int textWidth = Fonts.font35.func_78256_a(text);
        RenderUtils.drawBorderedRect(mouseX + 9, mouseY, mouseX + textWidth + 14, mouseY + Fonts.font35.field_78288_b + 3, 1.0f, new Color(255, 255, 255, 90).getRGB(), Integer.MIN_VALUE);
        GlStateManager.func_179117_G();
        Fonts.font35.func_78276_b(text, mouseX + 12, mouseY + (Fonts.font35.field_78288_b / 2), Integer.MAX_VALUE);
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.clickgui.style.Style
    public void drawButtonElement(int mouseX, int mouseY, ButtonElement buttonElement) {
        GlStateManager.func_179117_G();
        Fonts.font35.func_78276_b(buttonElement.getDisplayName(), buttonElement.getX() + 5, buttonElement.getY() + 6, buttonElement.getColor());
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.clickgui.style.Style
    public void drawModuleElement(int mouseX, int mouseY, ModuleElement moduleElement) {
        String[] values;
        int guiColor = ClickGUI.generateColor().getRGB();
        GlStateManager.func_179117_G();
        Fonts.font35.func_78276_b(moduleElement.getDisplayName(), moduleElement.getX() + 5, moduleElement.getY() + 6, moduleElement.getModule().getState() ? guiColor : Integer.MAX_VALUE);
        List<Value<?>> moduleValues = moduleElement.getModule().getValues();
        if (!moduleValues.isEmpty()) {
            Fonts.font35.func_78276_b(Marker.ANY_NON_NULL_MARKER, (moduleElement.getX() + moduleElement.getWidth()) - 8, moduleElement.getY() + (moduleElement.getHeight() / 2), Color.WHITE.getRGB());
            if (moduleElement.isShowSettings()) {
                int yPos = moduleElement.getY() + 4;
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
                            RenderUtils.drawRect(moduleElement.getX() + moduleElement.getWidth() + 4, yPos + 2, moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth(), yPos + 14, Integer.MIN_VALUE);
                            if (mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && mouseX <= moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth() && mouseY >= yPos + 2 && mouseY <= yPos + 14 && Mouse.isButtonDown(0) && moduleElement.isntPressed()) {
                                BoolValue boolValue = (BoolValue) value;
                                boolValue.set(Boolean.valueOf(!boolValue.get().booleanValue()));
                                f362mc.func_147118_V().func_147682_a(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                            }
                            GlStateManager.func_179117_G();
                            Fonts.font35.func_78276_b(text, moduleElement.getX() + moduleElement.getWidth() + 6, yPos + 4, ((BoolValue) value).get().booleanValue() ? guiColor : Integer.MAX_VALUE);
                            yPos += 12;
                        } else if (value instanceof ListValue) {
                            ListValue listValue = (ListValue) value;
                            String text2 = value.getName();
                            float textWidth2 = Fonts.font35.func_78256_a(text2);
                            if (moduleElement.getSettingsWidth() < textWidth2 + 16.0f) {
                                moduleElement.setSettingsWidth(textWidth2 + 16.0f);
                            }
                            RenderUtils.drawRect(moduleElement.getX() + moduleElement.getWidth() + 4, yPos + 2, moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth(), yPos + 14, Integer.MIN_VALUE);
                            GlStateManager.func_179117_G();
                            Fonts.font35.func_78276_b("§c" + text2, moduleElement.getX() + moduleElement.getWidth() + 6, yPos + 4, 16777215);
                            Fonts.font35.func_78276_b(listValue.openList ? "-" : Marker.ANY_NON_NULL_MARKER, (int) (((moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth()) - (listValue.openList ? 5 : 6)), yPos + 4, 16777215);
                            if (mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && mouseX <= moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth() && mouseY >= yPos + 2 && mouseY <= yPos + 14 && Mouse.isButtonDown(0) && moduleElement.isntPressed()) {
                                listValue.openList = !listValue.openList;
                                f362mc.func_147118_V().func_147682_a(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                            }
                            yPos += 12;
                            for (String valueOfList : listValue.getValues()) {
                                float textWidth22 = Fonts.font35.func_78256_a(">" + valueOfList);
                                if (moduleElement.getSettingsWidth() < textWidth22 + 8.0f) {
                                    moduleElement.setSettingsWidth(textWidth22 + 8.0f);
                                }
                                if (listValue.openList) {
                                    RenderUtils.drawRect(moduleElement.getX() + moduleElement.getWidth() + 4, yPos + 2, moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth(), yPos + 14, Integer.MIN_VALUE);
                                    if (mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && mouseX <= moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth() && mouseY >= yPos + 2 && mouseY <= yPos + 14 && Mouse.isButtonDown(0) && moduleElement.isntPressed()) {
                                        listValue.set(valueOfList);
                                        f362mc.func_147118_V().func_147682_a(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                                    }
                                    GlStateManager.func_179117_G();
                                    Fonts.font35.func_78276_b(">", moduleElement.getX() + moduleElement.getWidth() + 6, yPos + 4, Integer.MAX_VALUE);
                                    Fonts.font35.func_78276_b(valueOfList, moduleElement.getX() + moduleElement.getWidth() + 14, yPos + 4, (listValue.get() == null || !listValue.get().equalsIgnoreCase(valueOfList)) ? Integer.MAX_VALUE : guiColor);
                                    yPos += 12;
                                }
                            }
                        } else if (value instanceof FloatValue) {
                            FloatValue floatValue = (FloatValue) value;
                            String text3 = value.getName() + "§f: §c" + round(floatValue.get().floatValue()) + floatValue.getSuffix();
                            float textWidth3 = Fonts.font35.func_78256_a(text3);
                            if (moduleElement.getSettingsWidth() < textWidth3 + 8.0f) {
                                moduleElement.setSettingsWidth(textWidth3 + 8.0f);
                            }
                            RenderUtils.drawRect(moduleElement.getX() + moduleElement.getWidth() + 4, yPos + 2, moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth(), yPos + 24, Integer.MIN_VALUE);
                            RenderUtils.drawRect(moduleElement.getX() + moduleElement.getWidth() + 8, yPos + 18, ((moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth()) - 4.0f, yPos + 19, Integer.MAX_VALUE);
                            float sliderValue = moduleElement.getX() + moduleElement.getWidth() + (((moduleElement.getSettingsWidth() - 12.0f) * (floatValue.get().floatValue() - floatValue.getMinimum())) / (floatValue.getMaximum() - floatValue.getMinimum()));
                            RenderUtils.drawRect(8.0f + sliderValue, yPos + 15, sliderValue + 11.0f, yPos + 21, guiColor);
                            if (mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && mouseX <= ((moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth()) - 4.0f && mouseY >= yPos + 15 && mouseY <= yPos + 21) {
                                int dWheel = Mouse.getDWheel();
                                if (dWheel != 0) {
                                    if (dWheel > 0) {
                                        floatValue.set((FloatValue) Float.valueOf(Math.min(floatValue.get().floatValue() + 0.01f, floatValue.getMaximum())));
                                    }
                                    if (dWheel < 0) {
                                        floatValue.set((FloatValue) Float.valueOf(Math.max(floatValue.get().floatValue() - 0.01f, floatValue.getMinimum())));
                                    }
                                }
                                if (Mouse.isButtonDown(0)) {
                                    double i = MathHelper.func_151237_a((((mouseX - moduleElement.getX()) - moduleElement.getWidth()) - 8) / (moduleElement.getSettingsWidth() - 12.0f), 0.0d, 1.0d);
                                    floatValue.set((FloatValue) Float.valueOf(round((float) (floatValue.getMinimum() + ((floatValue.getMaximum() - floatValue.getMinimum()) * i))).floatValue()));
                                }
                            }
                            GlStateManager.func_179117_G();
                            Fonts.font35.func_78276_b(text3, moduleElement.getX() + moduleElement.getWidth() + 6, yPos + 4, 16777215);
                            yPos += 22;
                        } else if (value instanceof IntegerValue) {
                            IntegerValue integerValue = (IntegerValue) value;
                            String text4 = value.getName() + "§f: §c" + (value instanceof BlockValue ? BlockUtils.getBlockName(integerValue.get().intValue()) + " (" + integerValue.get() + ")" : integerValue.get() + integerValue.getSuffix());
                            float textWidth4 = Fonts.font35.func_78256_a(text4);
                            if (moduleElement.getSettingsWidth() < textWidth4 + 8.0f) {
                                moduleElement.setSettingsWidth(textWidth4 + 8.0f);
                            }
                            RenderUtils.drawRect(moduleElement.getX() + moduleElement.getWidth() + 4, yPos + 2, moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth(), yPos + 24, Integer.MIN_VALUE);
                            RenderUtils.drawRect(moduleElement.getX() + moduleElement.getWidth() + 8, yPos + 18, ((moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth()) - 4.0f, yPos + 19, Integer.MAX_VALUE);
                            float sliderValue2 = moduleElement.getX() + moduleElement.getWidth() + (((moduleElement.getSettingsWidth() - 12.0f) * (integerValue.get().intValue() - integerValue.getMinimum())) / (integerValue.getMaximum() - integerValue.getMinimum()));
                            RenderUtils.drawRect(8.0f + sliderValue2, yPos + 15, sliderValue2 + 11.0f, yPos + 21, guiColor);
                            if (mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && mouseX <= moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth() && mouseY >= yPos + 15 && mouseY <= yPos + 21) {
                                int dWheel2 = Mouse.getDWheel();
                                if (dWheel2 != 0) {
                                    if (dWheel2 > 0) {
                                        integerValue.set((IntegerValue) Integer.valueOf(Math.min(integerValue.get().intValue() + 1, integerValue.getMaximum())));
                                    }
                                    if (dWheel2 < 0) {
                                        integerValue.set((IntegerValue) Integer.valueOf(Math.max(integerValue.get().intValue() - 1, integerValue.getMinimum())));
                                    }
                                }
                                if (Mouse.isButtonDown(0)) {
                                    double i2 = MathHelper.func_151237_a((((mouseX - moduleElement.getX()) - moduleElement.getWidth()) - 8) / (moduleElement.getSettingsWidth() - 12.0f), 0.0d, 1.0d);
                                    integerValue.set((IntegerValue) Integer.valueOf((int) (integerValue.getMinimum() + ((integerValue.getMaximum() - integerValue.getMinimum()) * i2))));
                                }
                            }
                            GlStateManager.func_179117_G();
                            Fonts.font35.func_78276_b(text4, moduleElement.getX() + moduleElement.getWidth() + 6, yPos + 4, 16777215);
                            yPos += 22;
                        } else if (value instanceof FontValue) {
                            FontValue fontValue = (FontValue) value;
                            FontRenderer fontRenderer = fontValue.get();
                            RenderUtils.drawRect(moduleElement.getX() + moduleElement.getWidth() + 4, yPos + 2, moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth(), yPos + 14, Integer.MIN_VALUE);
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
                            Fonts.font35.func_78276_b(displayString, moduleElement.getX() + moduleElement.getWidth() + 6, yPos + 4, Color.WHITE.getRGB());
                            int stringWidth = Fonts.font35.func_78256_a(displayString);
                            if (moduleElement.getSettingsWidth() < stringWidth + 8) {
                                moduleElement.setSettingsWidth(stringWidth + 8);
                            }
                            if (((Mouse.isButtonDown(0) && !this.mouseDown) || (Mouse.isButtonDown(1) && !this.rightMouseDown)) && mouseX >= moduleElement.getX() + moduleElement.getWidth() + 4 && mouseX <= moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth() && mouseY >= yPos + 4 && mouseY <= yPos + 12) {
                                List<FontRenderer> fonts = Fonts.getFonts();
                                if (Mouse.isButtonDown(0)) {
                                    int i3 = 0;
                                    while (true) {
                                        if (i3 >= fonts.size()) {
                                            break;
                                        }
                                        FontRenderer font = fonts.get(i3);
                                        if (font != fontRenderer) {
                                            i3++;
                                        } else {
                                            int i4 = i3 + 1;
                                            if (i4 >= fonts.size()) {
                                                i4 = 0;
                                            }
                                            fontValue.set(fonts.get(i4));
                                        }
                                    }
                                } else {
                                    int i5 = fonts.size() - 1;
                                    while (true) {
                                        if (i5 < 0) {
                                            break;
                                        }
                                        FontRenderer font2 = fonts.get(i5);
                                        if (font2 != fontRenderer) {
                                            i5--;
                                        } else {
                                            int i6 = i5 - 1;
                                            if (i6 >= fonts.size()) {
                                                i6 = 0;
                                            }
                                            if (i6 < 0) {
                                                i6 = fonts.size() - 1;
                                            }
                                            fontValue.set(fonts.get(i6));
                                        }
                                    }
                                }
                            }
                            yPos += 11;
                        } else {
                            String text5 = value.getName() + "§f: §c" + value.get();
                            float textWidth5 = Fonts.font35.func_78256_a(text5);
                            if (moduleElement.getSettingsWidth() < textWidth5 + 8.0f) {
                                moduleElement.setSettingsWidth(textWidth5 + 8.0f);
                            }
                            RenderUtils.drawRect(moduleElement.getX() + moduleElement.getWidth() + 4, yPos + 2, moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth(), yPos + 14, Integer.MIN_VALUE);
                            GlStateManager.func_179117_G();
                            Fonts.font35.func_78276_b(text5, moduleElement.getX() + moduleElement.getWidth() + 6, yPos + 4, 16777215);
                            yPos += 12;
                        }
                        if (isNumber) {
                            AWTFontRenderer.Companion.setAssumeNonVolatile(true);
                        }
                    }
                }
                moduleElement.updatePressed();
                this.mouseDown = Mouse.isButtonDown(0);
                this.rightMouseDown = Mouse.isButtonDown(1);
                if (moduleElement.getSettingsWidth() > 0.0f && yPos > moduleElement.getY() + 4) {
                    RenderUtils.drawBorderedRect(moduleElement.getX() + moduleElement.getWidth() + 4, moduleElement.getY() + 6, moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth(), yPos + 2, 1.0f, Integer.MIN_VALUE, 0);
                }
            }
        }
    }

    private BigDecimal round(float f) {
        BigDecimal bd = new BigDecimal(Float.toString(f));
        return bd.setScale(2, 4);
    }
}
