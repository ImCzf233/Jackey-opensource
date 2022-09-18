package net.ccbluex.liquidbounce.file.configs;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.p004ui.client.clickgui.Panel;
import net.ccbluex.liquidbounce.p004ui.client.clickgui.elements.Element;
import net.ccbluex.liquidbounce.p004ui.client.clickgui.elements.ModuleElement;
import net.ccbluex.liquidbounce.utils.ClientUtils;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/file/configs/ClickGuiConfig.class */
public class ClickGuiConfig extends FileConfig {
    public ClickGuiConfig(File file) {
        super(file);
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    public void loadConfig() throws IOException {
        JsonObject parse = new JsonParser().parse(new BufferedReader(new FileReader(getFile())));
        if (parse instanceof JsonNull) {
            return;
        }
        JsonObject jsonObject = parse;
        for (Panel panel : LiquidBounce.clickGui.panels) {
            if (jsonObject.has(panel.getName())) {
                try {
                    JsonObject panelObject = jsonObject.getAsJsonObject(panel.getName());
                    panel.setOpen(panelObject.get("open").getAsBoolean());
                    panel.setVisible(panelObject.get("visible").getAsBoolean());
                    panel.setX(panelObject.get("posX").getAsInt());
                    panel.setY(panelObject.get("posY").getAsInt());
                    for (Element element : panel.getElements()) {
                        if (element instanceof ModuleElement) {
                            ModuleElement moduleElement = (ModuleElement) element;
                            if (panelObject.has(moduleElement.getModule().getName())) {
                                try {
                                    JsonObject elementObject = panelObject.getAsJsonObject(moduleElement.getModule().getName());
                                    moduleElement.setShowSettings(elementObject.get("Settings").getAsBoolean());
                                } catch (Exception e) {
                                    ClientUtils.getLogger().error("Error while loading clickgui module element with the name '" + moduleElement.getModule().getName() + "' (Panel Name: " + panel.getName() + ").", e);
                                }
                            }
                        }
                    }
                } catch (Exception e2) {
                    ClientUtils.getLogger().error("Error while loading clickgui panel with the name '" + panel.getName() + "'.", e2);
                }
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    public void saveConfig() throws IOException {
        JsonObject jsonObject = new JsonObject();
        for (Panel panel : LiquidBounce.clickGui.panels) {
            JsonObject panelObject = new JsonObject();
            panelObject.addProperty("open", Boolean.valueOf(panel.getOpen()));
            panelObject.addProperty("visible", Boolean.valueOf(panel.isVisible()));
            panelObject.addProperty("posX", Integer.valueOf(panel.getX()));
            panelObject.addProperty("posY", Integer.valueOf(panel.getY()));
            for (Element element : panel.getElements()) {
                if (element instanceof ModuleElement) {
                    ModuleElement moduleElement = (ModuleElement) element;
                    JsonObject elementObject = new JsonObject();
                    elementObject.addProperty("Settings", Boolean.valueOf(moduleElement.isShowSettings()));
                    panelObject.add(moduleElement.getModule().getName(), elementObject);
                }
            }
            jsonObject.add(panel.getName(), panelObject);
        }
        PrintWriter printWriter = new PrintWriter(new FileWriter(getFile()));
        printWriter.println(FileManager.PRETTY_GSON.toJson(jsonObject));
        printWriter.close();
    }
}
