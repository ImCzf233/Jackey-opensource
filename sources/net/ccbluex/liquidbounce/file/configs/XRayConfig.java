package net.ccbluex.liquidbounce.file.configs;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.XRay;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.minecraft.block.Block;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/file/configs/XRayConfig.class */
public class XRayConfig extends FileConfig {
    public XRayConfig(File file) {
        super(file);
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    public void loadConfig() throws IOException {
        XRay xRay = (XRay) LiquidBounce.moduleManager.getModule(XRay.class);
        if (xRay == null) {
            ClientUtils.getLogger().error("[FileManager] Failed to find xray module.");
            return;
        }
        JsonArray jsonArray = new JsonParser().parse(new BufferedReader(new FileReader(getFile()))).getAsJsonArray();
        xRay.getXrayBlocks().clear();
        Iterator it = jsonArray.iterator();
        while (it.hasNext()) {
            JsonElement jsonElement = (JsonElement) it.next();
            try {
                Block block = Block.func_149684_b(jsonElement.getAsString());
                if (xRay.getXrayBlocks().contains(block)) {
                    ClientUtils.getLogger().error("[FileManager] Skipped xray block '" + block.getRegistryName() + "' because the block is already added.");
                } else {
                    xRay.getXrayBlocks().add(block);
                }
            } catch (Throwable throwable) {
                ClientUtils.getLogger().error("[FileManager] Failed to add block to xray.", throwable);
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    public void saveConfig() throws IOException {
        XRay xRay = (XRay) LiquidBounce.moduleManager.getModule(XRay.class);
        if (xRay == null) {
            ClientUtils.getLogger().error("[FileManager] Failed to find xray module.");
            return;
        }
        JsonArray jsonArray = new JsonArray();
        for (Block block : xRay.getXrayBlocks()) {
            jsonArray.add(FileManager.PRETTY_GSON.toJsonTree(Integer.valueOf(Block.func_149682_b(block))));
        }
        PrintWriter printWriter = new PrintWriter(new FileWriter(getFile()));
        printWriter.println(FileManager.PRETTY_GSON.toJson(jsonArray));
        printWriter.close();
    }
}
