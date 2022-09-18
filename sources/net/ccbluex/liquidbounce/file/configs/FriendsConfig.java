package net.ccbluex.liquidbounce.file.configs;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jdk.internal.dynalink.CallSiteDescriptor;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.utils.ClientUtils;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/file/configs/FriendsConfig.class */
public class FriendsConfig extends FileConfig {
    private final List<Friend> friends = new ArrayList();

    public FriendsConfig(File file) {
        super(file);
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    public void loadConfig() throws IOException {
        clearFriends();
        try {
            JsonElement jsonElement = new JsonParser().parse(new BufferedReader(new FileReader(getFile())));
            if (jsonElement instanceof JsonNull) {
                return;
            }
            Iterator it = jsonElement.getAsJsonArray().iterator();
            while (it.hasNext()) {
                JsonElement friendElement = (JsonElement) it.next();
                JsonObject friendObject = friendElement.getAsJsonObject();
                addFriend(friendObject.get("playerName").getAsString(), friendObject.get("alias").getAsString());
            }
        } catch (JsonSyntaxException | IllegalStateException e) {
            ClientUtils.getLogger().info("[FileManager] Try to load old Friends config...");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(getFile()));
            while (true) {
                String line = bufferedReader.readLine();
                if (line != null) {
                    if (!line.contains("{") && !line.contains("}")) {
                        String line2 = line.replace(" ", "").replace("\"", "").replace(",", "");
                        if (line2.contains(CallSiteDescriptor.TOKEN_DELIMITER)) {
                            String[] data = line2.split(CallSiteDescriptor.TOKEN_DELIMITER);
                            addFriend(data[0], data[1]);
                        } else {
                            addFriend(line2);
                        }
                    }
                } else {
                    bufferedReader.close();
                    ClientUtils.getLogger().info("[FileManager] Loaded old Friends config...");
                    saveConfig();
                    ClientUtils.getLogger().info("[FileManager] Saved Friends to new config...");
                    return;
                }
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    public void saveConfig() throws IOException {
        JsonArray jsonArray = new JsonArray();
        for (Friend friend : getFriends()) {
            JsonObject friendObject = new JsonObject();
            friendObject.addProperty("playerName", friend.getPlayerName());
            friendObject.addProperty("alias", friend.getAlias());
            jsonArray.add(friendObject);
        }
        PrintWriter printWriter = new PrintWriter(new FileWriter(getFile()));
        printWriter.println(FileManager.PRETTY_GSON.toJson(jsonArray));
        printWriter.close();
    }

    public boolean addFriend(String playerName) {
        return addFriend(playerName, playerName);
    }

    public boolean addFriend(String playerName, String alias) {
        if (isFriend(playerName)) {
            return false;
        }
        this.friends.add(new Friend(playerName, alias));
        return true;
    }

    public boolean removeFriend(String playerName) {
        if (!isFriend(playerName)) {
            return false;
        }
        this.friends.removeIf(friend -> {
            return friend.getPlayerName().equals(playerName);
        });
        return true;
    }

    public boolean isFriend(String playerName) {
        for (Friend friend : this.friends) {
            if (friend.getPlayerName().equals(playerName)) {
                return true;
            }
        }
        return false;
    }

    public void clearFriends() {
        this.friends.clear();
    }

    public List<Friend> getFriends() {
        return this.friends;
    }

    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/file/configs/FriendsConfig$Friend.class */
    public class Friend {
        private final String playerName;
        private final String alias;

        Friend(String playerName, String alias) {
            FriendsConfig.this = this$0;
            this.playerName = playerName;
            this.alias = alias;
        }

        public String getPlayerName() {
            return this.playerName;
        }

        public String getAlias() {
            return this.alias;
        }
    }
}
