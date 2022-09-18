package net.ccbluex.liquidbounce.file.configs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.p004ui.client.hud.Config;
import org.apache.commons.io.FileUtils;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/file/configs/HudConfig.class */
public class HudConfig extends FileConfig {
    public HudConfig(File file) {
        super(file);
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    public void loadConfig() throws IOException {
        LiquidBounce.hud.clearElements();
        LiquidBounce.hud = new Config(FileUtils.readFileToString(getFile())).toHUD();
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    public void saveConfig() throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileWriter(getFile()));
        printWriter.println(new Config(LiquidBounce.hud).toJson());
        printWriter.close();
    }
}
