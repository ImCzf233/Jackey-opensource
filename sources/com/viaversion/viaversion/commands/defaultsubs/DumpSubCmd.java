package com.viaversion.viaversion.commands.defaultsubs;

import com.github.benmanes.caffeine.cache.LocalCacheFactory;
import com.google.common.io.CharStreams;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.dump.DumpTemplate;
import com.viaversion.viaversion.dump.VersionInfo;
import com.viaversion.viaversion.libs.gson.GsonBuilder;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.util.GsonUtil;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InvalidObjectException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Level;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/commands/defaultsubs/DumpSubCmd.class */
public class DumpSubCmd extends ViaSubCommand {
    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public String name() {
        return "dump";
    }

    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public String description() {
        return "Dump information about your server, this is helpful if you report bugs.";
    }

    @Override // com.viaversion.viaversion.api.command.ViaSubCommand
    public boolean execute(final ViaCommandSender sender, String[] args) {
        final VersionInfo version = new VersionInfo(System.getProperty("java.version"), System.getProperty("os.name"), Via.getAPI().getServerVersion().lowestSupportedVersion(), Via.getManager().getProtocolManager().getSupportedVersions(), Via.getPlatform().getPlatformName(), Via.getPlatform().getPlatformVersion(), Via.getPlatform().getPluginVersion(), "git-ViaVersion-4.2.1:f361bd66", Via.getManager().getSubPlatforms());
        Map<String, Object> configuration = Via.getPlatform().getConfigurationProvider().getValues();
        final DumpTemplate template = new DumpTemplate(version, configuration, Via.getPlatform().getDump(), Via.getManager().getInjector().getDump());
        Via.getPlatform().runAsync(new Runnable() { // from class: com.viaversion.viaversion.commands.defaultsubs.DumpSubCmd.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    HttpURLConnection con = (HttpURLConnection) new URL("https://dump.viaversion.com/documents").openConnection();
                    try {
                        con.setRequestProperty("Content-Type", "application/json");
                        con.addRequestProperty("User-Agent", "ViaVersion/" + version.getPluginVersion());
                        con.setRequestMethod("POST");
                        con.setDoOutput(true);
                        OutputStream out = con.getOutputStream();
                        out.write(new GsonBuilder().setPrettyPrinting().create().toJson(template).getBytes(StandardCharsets.UTF_8));
                        out.close();
                        if (con.getResponseCode() == 429) {
                            sender.sendMessage("§4You can only paste once every minute to protect our systems.");
                            return;
                        }
                        String rawOutput = CharStreams.toString(new InputStreamReader(con.getInputStream()));
                        con.getInputStream().close();
                        JsonObject output = (JsonObject) GsonUtil.getGson().fromJson(rawOutput, (Class<Object>) JsonObject.class);
                        if (output.has(LocalCacheFactory.KEY)) {
                            sender.sendMessage("§2We've made a dump with useful information, report your issue and provide this url: " + DumpSubCmd.this.getUrl(output.get(LocalCacheFactory.KEY).getAsString()));
                            return;
                        }
                        throw new InvalidObjectException("Key is not given in Hastebin output");
                    } catch (Exception e) {
                        sender.sendMessage("§4Failed to dump, please check the console for more information");
                        Via.getPlatform().getLogger().log(Level.WARNING, "Could not paste ViaVersion dump to Hastebin", (Throwable) e);
                        try {
                            if (con.getResponseCode() < 200 || con.getResponseCode() > 400) {
                                String rawOutput2 = CharStreams.toString(new InputStreamReader(con.getErrorStream()));
                                con.getErrorStream().close();
                                Via.getPlatform().getLogger().log(Level.WARNING, "Page returned: " + rawOutput2);
                            }
                        } catch (IOException e1) {
                            Via.getPlatform().getLogger().log(Level.WARNING, "Failed to capture further info", (Throwable) e1);
                        }
                    }
                } catch (IOException e2) {
                    sender.sendMessage("§4Failed to dump, please check the console for more information");
                    Via.getPlatform().getLogger().log(Level.WARNING, "Could not paste ViaVersion dump to ViaVersion Dump", (Throwable) e2);
                }
            }
        });
        return true;
    }

    public String getUrl(String id) {
        return String.format("https://dump.viaversion.com/%s", id);
    }
}
