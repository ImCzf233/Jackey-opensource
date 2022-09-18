package net.ccbluex.liquidbounce.p004ui.font;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.Locale;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

/* renamed from: net.ccbluex.liquidbounce.ui.font.TTFFontRenderer */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/font/TTFFontRenderer.class */
public class TTFFontRenderer {
    private Font font;
    private boolean fractionalMetrics;
    private CharacterData[] regularData;
    private CharacterData[] boldData;
    private CharacterData[] italicsData;
    private int[] colorCodes;
    private static final int MARGIN = 4;
    private static final char COLOR_INVOKER = 167;
    private static int RANDOM_OFFSET = 1;

    public TTFFontRenderer(Font font) {
        this(font, 256);
    }

    public TTFFontRenderer(Font font, int characterCount) {
        this(font, characterCount, true);
    }

    public TTFFontRenderer(Font font, boolean fractionalMetrics) {
        this(font, 256, fractionalMetrics);
    }

    public TTFFontRenderer(Font font, int characterCount, boolean fractionalMetrics) {
        this.fractionalMetrics = false;
        this.colorCodes = new int[32];
        this.font = font;
        this.fractionalMetrics = fractionalMetrics;
        this.regularData = setup(new CharacterData[characterCount], 0);
        this.boldData = setup(new CharacterData[characterCount], 1);
        this.italicsData = setup(new CharacterData[characterCount], 2);
    }

    private CharacterData[] setup(CharacterData[] characterData, int type) {
        generateColors();
        Font font = this.font.deriveFont(type);
        BufferedImage utilityImage = new BufferedImage(1, 1, 2);
        Graphics2D utilityGraphics = utilityImage.getGraphics();
        utilityGraphics.setFont(font);
        FontMetrics fontMetrics = utilityGraphics.getFontMetrics();
        for (int index = 0; index < characterData.length; index++) {
            char character = (char) index;
            Rectangle2D characterBounds = fontMetrics.getStringBounds(character + "", utilityGraphics);
            float width = ((float) characterBounds.getWidth()) + 8.0f;
            float height = (float) characterBounds.getHeight();
            BufferedImage characterImage = new BufferedImage(MathHelper.func_76143_f(width), MathHelper.func_76143_f(height), 2);
            Graphics2D graphics = characterImage.getGraphics();
            graphics.setFont(font);
            graphics.setColor(new Color(255, 255, 255, 0));
            graphics.fillRect(0, 0, characterImage.getWidth(), characterImage.getHeight());
            graphics.setColor(Color.WHITE);
            graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, this.fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
            graphics.drawString(character + "", 4, fontMetrics.getAscent());
            int textureId = GlStateManager.func_179146_y();
            createTexture(textureId, characterImage);
            characterData[index] = new CharacterData(character, characterImage.getWidth(), characterImage.getHeight(), textureId);
        }
        return characterData;
    }

    private void createTexture(int textureId, BufferedImage image) {
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = pixels[(y * image.getWidth()) + x];
                buffer.put((byte) ((pixel >> 16) & 255));
                buffer.put((byte) ((pixel >> 8) & 255));
                buffer.put((byte) (pixel & 255));
                buffer.put((byte) ((pixel >> 24) & 255));
            }
        }
        buffer.flip();
        GlStateManager.func_179144_i(textureId);
        GL11.glTexParameteri(3553, 10241, 9728);
        GL11.glTexParameteri(3553, 10240, 9728);
        GL11.glTexImage2D(3553, 0, 6408, image.getWidth(), image.getHeight(), 0, 6408, 5121, buffer);
        GlStateManager.func_179144_i(0);
    }

    public void drawString(String text, float x, float y, int color) {
        renderString(text, x, y, color, false);
    }

    public void drawStringWithShadow(String text, float x, float y, int color) {
        GL11.glTranslated(0.5d, 0.5d, 0.0d);
        renderString(text, x, y, color, true);
        GL11.glTranslated(-0.5d, -0.5d, 0.0d);
        renderString(text, x, y, color, false);
    }

    private void renderString(String text, float x, float y, int color, boolean shadow) {
        if (text.length() == 0) {
            return;
        }
        GL11.glPushMatrix();
        GlStateManager.func_179139_a(0.5d, 0.5d, 1.0d);
        float x2 = ((x - 2.0f) + 0.5f) * 2.0f;
        float y2 = ((y - 2.0f) + 0.5f) * 2.0f;
        CharacterData[] characterData = this.regularData;
        boolean underlined = false;
        boolean strikethrough = false;
        boolean obfuscated = false;
        int length = text.length();
        float multiplier = shadow ? 4 : 1;
        float a = ((color >> 24) & 255) / 255.0f;
        float r = ((color >> 16) & 255) / 255.0f;
        float g = ((color >> 8) & 255) / 255.0f;
        float b = (color & 255) / 255.0f;
        GL11.glColor4f(r / multiplier, g / multiplier, b / multiplier, a);
        int i = 0;
        while (i < length) {
            char character = text.charAt(i);
            char previous = i > 0 ? text.charAt(i - 1) : '.';
            if (previous != 167) {
                if (character == 167 && i < length) {
                    int index = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));
                    if (index < 16) {
                        obfuscated = false;
                        strikethrough = false;
                        underlined = false;
                        characterData = this.regularData;
                        if (index < 0 || index > 15) {
                            index = 15;
                        }
                        if (shadow) {
                            index += 16;
                        }
                        int textColor = this.colorCodes[index];
                        GL11.glColor4d((textColor >> 16) / 255.0d, ((textColor >> 8) & 255) / 255.0d, (textColor & 255) / 255.0d, a);
                    } else if (index == 16) {
                        obfuscated = true;
                    } else if (index == 17) {
                        characterData = this.boldData;
                    } else if (index == 18) {
                        strikethrough = true;
                    } else if (index == 19) {
                        underlined = true;
                    } else if (index == 20) {
                        characterData = this.italicsData;
                    } else if (index == 21) {
                        obfuscated = false;
                        strikethrough = false;
                        underlined = false;
                        characterData = this.regularData;
                        GL11.glColor4d(1.0d * (shadow ? 0.25d : 1.0d), 1.0d * (shadow ? 0.25d : 1.0d), 1.0d * (shadow ? 0.25d : 1.0d), a);
                    }
                } else if (character <= 255) {
                    if (obfuscated) {
                        character = (char) (character + RANDOM_OFFSET);
                    }
                    drawChar(character, characterData, x2, y2);
                    CharacterData charData = characterData[character];
                    if (strikethrough) {
                        drawLine(new Vector2f(0.0f, charData.height / 2.0f), new Vector2f(charData.width, charData.height / 2.0f), 3.0f);
                    }
                    if (underlined) {
                        drawLine(new Vector2f(0.0f, charData.height - 15.0f), new Vector2f(charData.width, charData.height - 15.0f), 3.0f);
                    }
                    x2 += charData.width - 8.0f;
                }
            }
            i++;
        }
        GL11.glPopMatrix();
        GL11.glColor4d(1.0d, 1.0d, 1.0d, 1.0d);
        GlStateManager.func_179144_i(0);
    }

    public float getWidth(String text) {
        float width = 0.0f;
        CharacterData[] characterData = this.regularData;
        int length = text.length();
        int i = 0;
        while (i < length) {
            char character = text.charAt(i);
            char previous = i > 0 ? text.charAt(i - 1) : '.';
            if (previous != 167) {
                if (character == 167 && i < length) {
                    int index = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));
                    if (index == 17) {
                        characterData = this.boldData;
                    } else if (index == 20) {
                        characterData = this.italicsData;
                    } else if (index == 21) {
                        characterData = this.regularData;
                    }
                } else if (character <= 255) {
                    CharacterData charData = characterData[character];
                    width += (charData.width - 8.0f) / 2.0f;
                }
            }
            i++;
        }
        return width + 2.0f;
    }

    public float getHeight(String text) {
        float height = 0.0f;
        CharacterData[] characterData = this.regularData;
        int length = text.length();
        int i = 0;
        while (i < length) {
            char character = text.charAt(i);
            char previous = i > 0 ? text.charAt(i - 1) : '.';
            if (previous != 167) {
                if (character == 167 && i < length) {
                    int index = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));
                    if (index == 17) {
                        characterData = this.boldData;
                    } else if (index == 20) {
                        characterData = this.italicsData;
                    } else if (index == 21) {
                        characterData = this.regularData;
                    }
                } else if (character <= 255) {
                    CharacterData charData = characterData[character];
                    height = Math.max(height, charData.height);
                }
            }
            i++;
        }
        return (height / 2.0f) - 2.0f;
    }

    private void drawChar(char character, CharacterData[] characterData, float x, float y) {
        CharacterData charData = characterData[character];
        charData.bind();
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex2d(x, y);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex2d(x, y + charData.height);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex2d(x + charData.width, y + charData.height);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex2d(x + charData.width, y);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glBindTexture(3553, 0);
    }

    private void drawLine(Vector2f start, Vector2f end, float width) {
        GL11.glDisable(3553);
        GL11.glLineWidth(width);
        GL11.glBegin(1);
        GL11.glVertex2f(start.x, start.y);
        GL11.glVertex2f(end.x, end.y);
        GL11.glEnd();
        GL11.glEnable(3553);
    }

    private void generateColors() {
        for (int i = 0; i < 32; i++) {
            int thingy = ((i >> 3) & 1) * 85;
            int red = (((i >> 2) & 1) * 170) + thingy;
            int green = (((i >> 1) & 1) * 170) + thingy;
            int blue = (((i >> 0) & 1) * 170) + thingy;
            if (i == 6) {
                red += 85;
            }
            if (i >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }
            this.colorCodes[i] = ((red & 255) << 16) | ((green & 255) << 8) | (blue & 255);
        }
    }

    public Font getFont() {
        return this.font;
    }

    /* renamed from: net.ccbluex.liquidbounce.ui.font.TTFFontRenderer$CharacterData */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/font/TTFFontRenderer$CharacterData.class */
    public class CharacterData {
        public char character;
        public float width;
        public float height;
        private int textureId;

        public CharacterData(char character, float width, float height, int textureId) {
            TTFFontRenderer.this = this$0;
            this.character = character;
            this.width = width;
            this.height = height;
            this.textureId = textureId;
        }

        public void bind() {
            GL11.glBindTexture(3553, this.textureId);
        }
    }
}
