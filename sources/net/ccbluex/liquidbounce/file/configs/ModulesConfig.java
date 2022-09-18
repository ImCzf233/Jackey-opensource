package net.ccbluex.liquidbounce.file.configs;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.misc.AutoDisable;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/file/configs/ModulesConfig.class */
public class ModulesConfig extends FileConfig {
    public ModulesConfig(File file) {
        super(file);
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    public void loadConfig() throws IOException {
        JsonElement jsonElement = new JsonParser().parse(new BufferedReader(new FileReader(getFile())));
        if (jsonElement instanceof JsonNull) {
            return;
        }
        for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
            Module module = LiquidBounce.moduleManager.getModule(entry.getKey());
            if (module != null) {
                JsonObject jsonModule = (JsonObject) entry.getValue();
                module.setState(jsonModule.get("State").getAsBoolean());
                module.setKeyBind(jsonModule.get("KeyBind").getAsInt());
                if (jsonModule.has("Array")) {
                    module.setArray(jsonModule.get("Array").getAsBoolean());
                }
                if (jsonModule.has("AutoDisable")) {
                    module.getAutoDisables().clear();
                    try {
                        JsonArray jsonAD = jsonModule.getAsJsonArray("AutoDisable");
                        if (jsonAD.size() > 0) {
                            for (int i = 0; i <= jsonAD.size() - 1; i++) {
                                try {
                                    AutoDisable.DisableEvent disableEvent = AutoDisable.DisableEvent.valueOf(jsonAD.get(i).getAsString());
                                    module.getAutoDisables().add(disableEvent);
                                } catch (Exception e) {
                                }
                            }
                        }
                    } catch (Exception e2) {
                    }
                }
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    public void saveConfig() throws IOException {
        JsonObject jsonObject = new JsonObject();
        Iterator<Module> it = LiquidBounce.moduleManager.getModules().iterator();
        while (it.hasNext()) {
            Module module = it.next();
            JsonObject jsonMod = new JsonObject();
            jsonMod.addProperty("State", Boolean.valueOf(module.getState()));
            jsonMod.addProperty("KeyBind", Integer.valueOf(module.getKeyBind()));
            jsonMod.addProperty("Array", Boolean.valueOf(module.getArray()));
            JsonArray jsonAD = new JsonArray();
            for (AutoDisable.DisableEvent e : module.getAutoDisables()) {
                jsonAD.add(new JsonPrimitive(e.toString()));
            }
            jsonMod.add("AutoDisable", jsonAD);
            jsonObject.add(module.getName(), jsonMod);
        }
        PrintWriter printWriter = new PrintWriter(new FileWriter(getFile()));
        printWriter.println(FileManager.PRETTY_GSON.toJson(jsonObject));
        printWriter.close();
    }
}
