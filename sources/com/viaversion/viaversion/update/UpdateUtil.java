package com.viaversion.viaversion.update;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.util.GsonUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/update/UpdateUtil.class */
public class UpdateUtil {
    private static final String PREFIX = "§a§l[ViaVersion] §a";
    private static final String URL = "https://api.spiget.org/v2/resources/";
    private static final int PLUGIN = 19254;
    private static final String LATEST_VERSION = "/versions/latest";

    public static void sendUpdateMessage(UUID uuid) {
        Via.getPlatform().runAsync(() -> {
            String message = getUpdateMessage(false);
            if (message != null) {
                Via.getPlatform().runSync(() -> {
                    Via.getPlatform().sendMessage(uuid, PREFIX + message);
                });
            }
        });
    }

    public static void sendUpdateMessage() {
        Via.getPlatform().runAsync(() -> {
            String message = getUpdateMessage(true);
            if (message != null) {
                Via.getPlatform().runSync(() -> {
                    Via.getPlatform().getLogger().warning(message);
                });
            }
        });
    }

    private static String getUpdateMessage(boolean console) {
        if (Via.getPlatform().getPluginVersion().equals("${version}")) {
            return "You are using a debug/custom version, consider updating.";
        }
        String newestString = getNewestVersion();
        if (newestString == null) {
            if (console) {
                return "Could not check for updates, check your connection.";
            }
            return null;
        }
        try {
            Version current = new Version(Via.getPlatform().getPluginVersion());
            Version newest = new Version(newestString);
            if (current.compareTo(newest) < 0) {
                return "There is a newer plugin version available: " + newest + ", you're on: " + current;
            }
            if (console && current.compareTo(newest) != 0) {
                String tag = current.getTag().toLowerCase(Locale.ROOT);
                if (tag.startsWith("dev") || tag.startsWith("snapshot")) {
                    return "You are running a development version of the plugin, please report any bugs to GitHub.";
                }
                return "You are running a newer version of the plugin than is released!";
            }
            return null;
        } catch (IllegalArgumentException e) {
            return "You are using a custom version, consider updating.";
        }
    }

    private static String getNewestVersion() {
        try {
            URL url = new URL("https://api.spiget.org/v2/resources/19254/versions/latest?" + System.currentTimeMillis());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setUseCaches(true);
            connection.addRequestProperty("User-Agent", "ViaVersion " + Via.getPlatform().getPluginVersion() + " " + Via.getPlatform().getPlatformName());
            connection.setDoOutput(true);
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            while (true) {
                String input = br.readLine();
                if (input != null) {
                    builder.append(input);
                } else {
                    br.close();
                    try {
                        JsonObject statistics = (JsonObject) GsonUtil.getGson().fromJson(builder.toString(), (Class<Object>) JsonObject.class);
                        return statistics.get("name").getAsString();
                    } catch (JsonParseException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }
        } catch (MalformedURLException e2) {
            return null;
        } catch (IOException e3) {
            return null;
        }
    }
}
