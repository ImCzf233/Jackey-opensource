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
import me.liuli.elixir.account.CrackedAccount;
import me.liuli.elixir.account.MinecraftAccount;
import me.liuli.elixir.account.MojangAccount;
import me.liuli.elixir.manage.AccountSerializer;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/file/configs/AccountsConfig.class */
public class AccountsConfig extends FileConfig {
    private final List<MinecraftAccount> accounts = new ArrayList();

    public AccountsConfig(File file) {
        super(file);
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    public void loadConfig() throws IOException {
        clearAccounts();
        JsonElement jsonElement = new JsonParser().parse(new BufferedReader(new FileReader(getFile())));
        if (jsonElement instanceof JsonNull) {
            return;
        }
        Iterator it = jsonElement.getAsJsonArray().iterator();
        while (it.hasNext()) {
            JsonElement accountElement = (JsonElement) it.next();
            JsonObject accountObject = accountElement.getAsJsonObject();
            try {
                this.accounts.add(AccountSerializer.INSTANCE.fromJson(accountElement.getAsJsonObject()));
            } catch (JsonSyntaxException | IllegalStateException e) {
                JsonElement name = accountObject.get("name");
                JsonElement password = accountObject.get("password");
                JsonElement inGameName = accountObject.get("inGameName");
                if (inGameName.isJsonNull() && password.isJsonNull()) {
                    MojangAccount mojangAccount = new MojangAccount();
                    mojangAccount.setEmail(name.getAsString());
                    mojangAccount.setName(inGameName.getAsString());
                    mojangAccount.setPassword(password.getAsString());
                    this.accounts.add(mojangAccount);
                } else {
                    CrackedAccount crackedAccount = new CrackedAccount();
                    crackedAccount.setName(name.getAsString());
                    this.accounts.add(crackedAccount);
                }
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    public void saveConfig() throws IOException {
        JsonArray jsonArray = new JsonArray();
        for (MinecraftAccount minecraftAccount : this.accounts) {
            jsonArray.add(AccountSerializer.INSTANCE.toJson(minecraftAccount));
        }
        PrintWriter printWriter = new PrintWriter(new FileWriter(getFile()));
        printWriter.println(FileManager.PRETTY_GSON.toJson(jsonArray));
        printWriter.close();
    }

    public void addCrackedAccount(String name) {
        CrackedAccount crackedAccount = new CrackedAccount();
        crackedAccount.setName(name);
        if (accountExists(crackedAccount)) {
            return;
        }
        this.accounts.add(crackedAccount);
    }

    public void addMojangAccount(String name, String password) {
        MojangAccount mojangAccount = new MojangAccount();
        mojangAccount.setName(name);
        mojangAccount.setPassword(password);
        if (accountExists(mojangAccount)) {
            return;
        }
        this.accounts.add(mojangAccount);
    }

    public void addAccount(MinecraftAccount account) {
        this.accounts.add(account);
    }

    public void removeAccount(int selectedSlot) {
        this.accounts.remove(selectedSlot);
    }

    public void removeAccount(MinecraftAccount account) {
        this.accounts.remove(account);
    }

    public boolean accountExists(MinecraftAccount newAccount) {
        for (MinecraftAccount minecraftAccount : this.accounts) {
            if (minecraftAccount.getClass().getName().equals(newAccount.getClass().getName()) && minecraftAccount.getName().equals(newAccount.getName())) {
                return true;
            }
        }
        return false;
    }

    public void clearAccounts() {
        this.accounts.clear();
    }

    public List<MinecraftAccount> getAccounts() {
        return this.accounts;
    }
}
