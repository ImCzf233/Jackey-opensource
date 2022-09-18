package net.ccbluex.liquidbounce.p004ui.font;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

/* renamed from: net.ccbluex.liquidbounce.ui.font.Fonts */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/font/Fonts.class */
public class Fonts {
    @FontDetails(fontName = "Roboto Medium", fontSize = 35)
    public static GameFontRenderer font35;
    @FontDetails(fontName = "Roboto Medium", fontSize = 40)
    public static GameFontRenderer font40;
    @FontDetails(fontName = "Roboto Medium", fontSize = 72)
    public static GameFontRenderer font72;
    @FontDetails(fontName = "Roboto Medium", fontSize = 30)
    public static GameFontRenderer fontSmall;
    @FontDetails(fontName = "SFUI Regular", fontSize = 35)
    public static GameFontRenderer fontSFUI35;
    @FontDetails(fontName = "SFUI Regular", fontSize = 40)
    public static GameFontRenderer fontSFUI40;
    @FontDetails(fontName = "Roboto Bold", fontSize = 180)
    public static GameFontRenderer fontBold180;
    @FontDetails(fontName = "Tahoma Bold", fontSize = 35)
    public static GameFontRenderer fontTahoma;
    @FontDetails(fontName = "Tahoma Bold", fontSize = 30)
    public static GameFontRenderer fontTahoma30;
    public static TTFFontRenderer fontTahomaSmall;
    @FontDetails(fontName = "Bangers", fontSize = 45)
    public static GameFontRenderer fontBangers;
    @FontDetails(fontName = "Minecraft Font")
    public static final FontRenderer minecraftFont = Minecraft.func_71410_x().field_71466_p;
    private static final List<GameFontRenderer> CUSTOM_FONT_RENDERERS = new ArrayList();

    public static void loadFonts() {
        long l = System.currentTimeMillis();
        ClientUtils.getLogger().info("Loading Fonts.");
        downloadFonts();
        font35 = new GameFontRenderer(getFont("Roboto-Medium.ttf", 35));
        font40 = new GameFontRenderer(getFont("Roboto-Medium.ttf", 40));
        font72 = new GameFontRenderer(getFont("Roboto-Medium.ttf", 72));
        fontSmall = new GameFontRenderer(getFont("Roboto-Medium.ttf", 30));
        fontSFUI35 = new GameFontRenderer(getFont("sfui.ttf", 35));
        fontSFUI40 = new GameFontRenderer(getFont("sfui.ttf", 40));
        fontBold180 = new GameFontRenderer(getFont("Roboto-Bold.ttf", 180));
        fontTahoma = new GameFontRenderer(getFont("TahomaBold.ttf", 35));
        fontTahoma30 = new GameFontRenderer(getFont("TahomaBold.ttf", 30));
        fontTahomaSmall = new TTFFontRenderer(getFont("Tahoma.ttf", 11));
        fontBangers = new GameFontRenderer(getFont("Bangers-Regular.ttf", 45));
        try {
            CUSTOM_FONT_RENDERERS.clear();
            File fontsFile = new File(LiquidBounce.fileManager.fontsDir, "fonts.json");
            if (fontsFile.exists()) {
                JsonArray parse = new JsonParser().parse(new BufferedReader(new FileReader(fontsFile)));
                if (parse instanceof JsonNull) {
                    return;
                }
                JsonArray jsonArray = parse;
                Iterator it = jsonArray.iterator();
                while (it.hasNext()) {
                    JsonObject jsonObject = (JsonElement) it.next();
                    if (jsonObject instanceof JsonNull) {
                        return;
                    }
                    JsonObject fontObject = jsonObject;
                    CUSTOM_FONT_RENDERERS.add(new GameFontRenderer(getFont(fontObject.get("fontFile").getAsString(), fontObject.get("fontSize").getAsInt())));
                }
            } else {
                fontsFile.createNewFile();
                PrintWriter printWriter = new PrintWriter(new FileWriter(fontsFile));
                printWriter.println(new GsonBuilder().setPrettyPrinting().create().toJson(new JsonArray()));
                printWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ClientUtils.getLogger().info("Loaded Fonts. (" + (System.currentTimeMillis() - l) + "ms)");
    }

    private static void downloadFonts() {
        try {
            File outputFile = new File(LiquidBounce.fileManager.fontsDir, "roboto.zip");
            File sfuiFile = new File(LiquidBounce.fileManager.fontsDir, "sfui.ttf");
            File prodSansFile = new File(LiquidBounce.fileManager.fontsDir, "Roboto-Medium.ttf");
            File prodBoldFile = new File(LiquidBounce.fileManager.fontsDir, "Roboto-Bold.ttf");
            File tahomaFile = new File(LiquidBounce.fileManager.fontsDir, "TahomaBold.ttf");
            File tahomaReFile = new File(LiquidBounce.fileManager.fontsDir, "Tahoma.ttf");
            File bangersFile = new File(LiquidBounce.fileManager.fontsDir, "Bangers-Regular.ttf");
            if (!outputFile.exists() || !sfuiFile.exists() || !prodSansFile.exists() || !prodBoldFile.exists() || !tahomaFile.exists() || !tahomaReFile.exists() || !bangersFile.exists()) {
                ClientUtils.getLogger().info("Downloading fonts...");
                HttpUtils.download("https://wysi-foundation.github.io/LiquidCloud/LiquidBounce/fonts/fonts.zip", outputFile);
                ClientUtils.getLogger().info("Extract fonts...");
                extractZip(outputFile.getPath(), LiquidBounce.fileManager.fontsDir.getPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FontRenderer getFontRenderer(String name, int size) {
        Field[] declaredFields;
        for (Field field : Fonts.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object o = field.get(null);
                if (o instanceof FontRenderer) {
                    FontDetails fontDetails = (FontDetails) field.getAnnotation(FontDetails.class);
                    if (fontDetails.fontName().equals(name) && fontDetails.fontSize() == size) {
                        return (FontRenderer) o;
                    }
                } else {
                    continue;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        for (GameFontRenderer liquidFontRenderer : CUSTOM_FONT_RENDERERS) {
            Font font = liquidFontRenderer.getDefaultFont().getFont();
            if (font.getName().equals(name) && font.getSize() == size) {
                return liquidFontRenderer;
            }
        }
        return minecraftFont;
    }

    public static Object[] getFontDetails(FontRenderer fontRenderer) {
        Field[] declaredFields;
        Object o;
        for (Field field : Fonts.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                o = field.get(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (!o.equals(fontRenderer)) {
                continue;
            } else {
                FontDetails fontDetails = (FontDetails) field.getAnnotation(FontDetails.class);
                return new Object[]{fontDetails.fontName(), Integer.valueOf(fontDetails.fontSize())};
            }
        }
        if (fontRenderer instanceof GameFontRenderer) {
            Font font = ((GameFontRenderer) fontRenderer).getDefaultFont().getFont();
            return new Object[]{font.getName(), Integer.valueOf(font.getSize())};
        }
        return null;
    }

    public static List<FontRenderer> getFonts() {
        Field[] declaredFields;
        List<FontRenderer> fonts = new ArrayList<>();
        for (Field fontField : Fonts.class.getDeclaredFields()) {
            try {
                fontField.setAccessible(true);
                Object fontObj = fontField.get(null);
                if (fontObj instanceof FontRenderer) {
                    fonts.add((FontRenderer) fontObj);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        fonts.addAll(CUSTOM_FONT_RENDERERS);
        return fonts;
    }

    private static Font getFont(String fontName, int size) {
        try {
            InputStream inputStream = new FileInputStream(new File(LiquidBounce.fileManager.fontsDir, fontName));
            Font awtClientFont = Font.createFont(0, inputStream);
            Font awtClientFont2 = awtClientFont.deriveFont(0, size);
            inputStream.close();
            return awtClientFont2;
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("default", 0, size);
        }
    }

    private static void extractZip(String zipFile, String outputFolder) {
        byte[] buffer = new byte[1024];
        try {
            File folder = new File(outputFolder);
            if (!folder.exists()) {
                folder.mkdir();
            }
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
            for (ZipEntry zipEntry = zipInputStream.getNextEntry(); zipEntry != null; zipEntry = zipInputStream.getNextEntry()) {
                File newFile = new File(outputFolder + File.separator + zipEntry.getName());
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fileOutputStream = new FileOutputStream(newFile);
                while (true) {
                    int i = zipInputStream.read(buffer);
                    if (i > 0) {
                        fileOutputStream.write(buffer, 0, i);
                    }
                }
                fileOutputStream.close();
            }
            zipInputStream.closeEntry();
            zipInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
