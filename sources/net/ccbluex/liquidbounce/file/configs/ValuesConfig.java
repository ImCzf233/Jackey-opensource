package net.ccbluex.liquidbounce.file.configs;

import com.github.benmanes.caffeine.cache.LocalCacheFactory;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import net.ccbluex.liquidbounce.features.special.AntiForge;
import net.ccbluex.liquidbounce.features.special.AutoReconnect;
import net.ccbluex.liquidbounce.features.special.BungeeCordSpoof;
import net.ccbluex.liquidbounce.features.special.MacroManager;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.p004ui.client.GuiBackground;
import net.ccbluex.liquidbounce.p004ui.client.GuiMainMenu;
import net.ccbluex.liquidbounce.p004ui.client.altmanager.menus.altgenerator.GuiTheAltening;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.value.Value;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/file/configs/ValuesConfig.class */
public class ValuesConfig extends FileConfig {
    public ValuesConfig(File file) {
        super(file);
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    public void loadConfig() throws IOException {
        JsonObject parse = new JsonParser().parse(new BufferedReader(new FileReader(getFile())));
        if (parse instanceof JsonNull) {
            return;
        }
        JsonObject jsonObject = parse;
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            if (entry.getKey().equalsIgnoreCase("CommandPrefix")) {
                LiquidBounce.commandManager.setPrefix(entry.getValue().getAsCharacter());
            } else if (entry.getKey().equalsIgnoreCase("ShowRichPresence")) {
                LiquidBounce.clientRichPresence.setShowRichPresenceValue(entry.getValue().getAsBoolean());
            } else if (entry.getKey().equalsIgnoreCase("targets")) {
                JsonObject jsonValue = (JsonObject) entry.getValue();
                if (jsonValue.has("TargetPlayer")) {
                    EntityUtils.targetPlayer = jsonValue.get("TargetPlayer").getAsBoolean();
                }
                if (jsonValue.has("TargetMobs")) {
                    EntityUtils.targetMobs = jsonValue.get("TargetMobs").getAsBoolean();
                }
                if (jsonValue.has("TargetAnimals")) {
                    EntityUtils.targetAnimals = jsonValue.get("TargetAnimals").getAsBoolean();
                }
                if (jsonValue.has("TargetInvisible")) {
                    EntityUtils.targetInvisible = jsonValue.get("TargetInvisible").getAsBoolean();
                }
                if (jsonValue.has("TargetDead")) {
                    EntityUtils.targetDead = jsonValue.get("TargetDead").getAsBoolean();
                }
            } else if (entry.getKey().equalsIgnoreCase("macros")) {
                Iterator it = entry.getValue().getAsJsonArray().iterator();
                while (it.hasNext()) {
                    JsonElement macroElement = (JsonElement) it.next();
                    JsonObject macroObject = macroElement.getAsJsonObject();
                    JsonElement keyValue = macroObject.get(LocalCacheFactory.KEY);
                    JsonElement commandValue = macroObject.get("command");
                    MacroManager.INSTANCE.addMacro(keyValue.getAsInt(), commandValue.getAsString());
                }
            } else if (entry.getKey().equalsIgnoreCase("features")) {
                JsonObject jsonValue2 = (JsonObject) entry.getValue();
                if (jsonValue2.has("AntiForge")) {
                    AntiForge.enabled = jsonValue2.get("AntiForge").getAsBoolean();
                }
                if (jsonValue2.has("AntiForgeFML")) {
                    AntiForge.blockFML = jsonValue2.get("AntiForgeFML").getAsBoolean();
                }
                if (jsonValue2.has("AntiForgeProxy")) {
                    AntiForge.blockProxyPacket = jsonValue2.get("AntiForgeProxy").getAsBoolean();
                }
                if (jsonValue2.has("AntiForgePayloads")) {
                    AntiForge.blockPayloadPackets = jsonValue2.get("AntiForgePayloads").getAsBoolean();
                }
                if (jsonValue2.has("BungeeSpoof")) {
                    BungeeCordSpoof.enabled = jsonValue2.get("BungeeSpoof").getAsBoolean();
                }
                if (jsonValue2.has("AutoReconnectDelay")) {
                    AutoReconnect.INSTANCE.setDelay(jsonValue2.get("AutoReconnectDelay").getAsInt());
                }
            } else if (entry.getKey().equalsIgnoreCase("thealtening")) {
                JsonObject jsonValue3 = (JsonObject) entry.getValue();
                if (jsonValue3.has("API-Key")) {
                    GuiTheAltening.Companion.setApiKey(jsonValue3.get("API-Key").getAsString());
                }
            } else if (entry.getKey().equalsIgnoreCase("MainMenuParallax")) {
                GuiMainMenu.Companion.setUseParallax(entry.getValue().getAsBoolean());
            } else if (entry.getKey().equalsIgnoreCase("Background")) {
                JsonObject jsonValue4 = (JsonObject) entry.getValue();
                if (jsonValue4.has("Enabled")) {
                    GuiBackground.Companion.setEnabled(jsonValue4.get("Enabled").getAsBoolean());
                }
                if (jsonValue4.has("Particles")) {
                    GuiBackground.Companion.setParticles(jsonValue4.get("Particles").getAsBoolean());
                }
            } else {
                Module module = LiquidBounce.moduleManager.getModule(entry.getKey());
                if (module != null) {
                    JsonObject jsonModule = (JsonObject) entry.getValue();
                    for (Value moduleValue : module.getValues()) {
                        JsonElement element = jsonModule.get(moduleValue.getName());
                        if (element != null) {
                            moduleValue.fromJson(element);
                        }
                    }
                }
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    public void saveConfig() throws IOException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("CommandPrefix", Character.valueOf(LiquidBounce.commandManager.getPrefix()));
        jsonObject.addProperty("ShowRichPresence", Boolean.valueOf(LiquidBounce.clientRichPresence.getShowRichPresenceValue()));
        JsonObject jsonTargets = new JsonObject();
        jsonTargets.addProperty("TargetPlayer", Boolean.valueOf(EntityUtils.targetPlayer));
        jsonTargets.addProperty("TargetMobs", Boolean.valueOf(EntityUtils.targetMobs));
        jsonTargets.addProperty("TargetAnimals", Boolean.valueOf(EntityUtils.targetAnimals));
        jsonTargets.addProperty("TargetInvisible", Boolean.valueOf(EntityUtils.targetInvisible));
        jsonTargets.addProperty("TargetDead", Boolean.valueOf(EntityUtils.targetDead));
        jsonObject.add("targets", jsonTargets);
        JsonArray jsonMacros = new JsonArray();
        MacroManager.INSTANCE.getMacroMapping().forEach(k, v -> {
            JsonObject jsonMacro = new JsonObject();
            jsonMacro.addProperty(LocalCacheFactory.KEY, k);
            jsonMacro.addProperty("command", v);
            jsonMacros.add(jsonMacro);
        });
        jsonObject.add("macros", jsonMacros);
        JsonObject jsonFeatures = new JsonObject();
        jsonFeatures.addProperty("AntiForge", Boolean.valueOf(AntiForge.enabled));
        jsonFeatures.addProperty("AntiForgeFML", Boolean.valueOf(AntiForge.blockFML));
        jsonFeatures.addProperty("AntiForgeProxy", Boolean.valueOf(AntiForge.blockProxyPacket));
        jsonFeatures.addProperty("AntiForgePayloads", Boolean.valueOf(AntiForge.blockPayloadPackets));
        jsonFeatures.addProperty("BungeeSpoof", Boolean.valueOf(BungeeCordSpoof.enabled));
        jsonFeatures.addProperty("AutoReconnectDelay", Integer.valueOf(AutoReconnect.INSTANCE.getDelay()));
        jsonObject.add("features", jsonFeatures);
        JsonObject theAlteningObject = new JsonObject();
        theAlteningObject.addProperty("API-Key", GuiTheAltening.Companion.getApiKey());
        jsonObject.add("thealtening", theAlteningObject);
        jsonObject.addProperty("MainMenuParallax", Boolean.valueOf(GuiMainMenu.Companion.getUseParallax()));
        JsonObject backgroundObject = new JsonObject();
        backgroundObject.addProperty("Enabled", Boolean.valueOf(GuiBackground.Companion.getEnabled()));
        backgroundObject.addProperty("Particles", Boolean.valueOf(GuiBackground.Companion.getParticles()));
        jsonObject.add("Background", backgroundObject);
        LiquidBounce.moduleManager.getModules().stream().filter(module -> {
            return !module.getValues().isEmpty();
        }).forEach(module2 -> {
            JsonObject jsonModule = new JsonObject();
            module2.getValues().forEach(value -> {
                jsonModule.add(value.getName(), value.toJson());
            });
            jsonObject.add(module2.getName(), jsonModule);
        });
        PrintWriter printWriter = new PrintWriter(new FileWriter(getFile()));
        printWriter.println(FileManager.PRETTY_GSON.toJson(jsonObject));
        printWriter.close();
    }
}
