package net.ccbluex.liquidbounce.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import javax.imageio.ImageIO;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.file.configs.AccountsConfig;
import net.ccbluex.liquidbounce.file.configs.ClickGuiConfig;
import net.ccbluex.liquidbounce.file.configs.FriendsConfig;
import net.ccbluex.liquidbounce.file.configs.HudConfig;
import net.ccbluex.liquidbounce.file.configs.ModulesConfig;
import net.ccbluex.liquidbounce.file.configs.ValuesConfig;
import net.ccbluex.liquidbounce.file.configs.XRayConfig;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/file/FileManager.class */
public class FileManager extends MinecraftInstance {
    public final File dir = new File(f362mc.field_71412_D, "Jackey-1.8");
    public final File fontsDir = new File(this.dir, "fonts");
    public final File settingsDir = new File(this.dir, "settings");
    public final File soundsDir = new File(this.dir, "sounds");
    public final File themesDir = new File(this.dir, "themes");
    public final FileConfig modulesConfig = new ModulesConfig(new File(this.dir, "modules.json"));
    public final FileConfig valuesConfig = new ValuesConfig(new File(this.dir, "values.json"));
    public final FileConfig clickGuiConfig = new ClickGuiConfig(new File(this.dir, "clickgui.json"));
    public final AccountsConfig accountsConfig = new AccountsConfig(new File(this.dir, "accounts.json"));
    public final FriendsConfig friendsConfig = new FriendsConfig(new File(this.dir, "friends.json"));
    public final FileConfig xrayConfig = new XRayConfig(new File(this.dir, "xray-blocks.json"));
    public final FileConfig hudConfig = new HudConfig(new File(this.dir, "hud.json"));
    public final File backgroundFile = new File(this.dir, "userbackground.png");
    public static final Gson PRETTY_GSON = new GsonBuilder().setPrettyPrinting().create();

    public FileManager() {
        setupFolder();
        loadBackground();
    }

    public void setupFolder() {
        if (!this.dir.exists()) {
            this.dir.mkdir();
        }
        if (!this.fontsDir.exists()) {
            this.fontsDir.mkdir();
        }
        if (!this.settingsDir.exists()) {
            this.settingsDir.mkdir();
        }
        if (!this.soundsDir.exists()) {
            this.soundsDir.mkdir();
        }
        if (!this.themesDir.exists()) {
            this.themesDir.mkdir();
        }
    }

    public void loadAllConfigs() {
        Field[] declaredFields;
        for (Field field : getClass().getDeclaredFields()) {
            if (field.getType() == FileConfig.class) {
                try {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    FileConfig fileConfig = (FileConfig) field.get(this);
                    loadConfig(fileConfig);
                } catch (IllegalAccessException e) {
                    ClientUtils.getLogger().error("Failed to load config file of field " + field.getName() + ".", e);
                }
            }
        }
    }

    public void loadConfigs(FileConfig... configs) {
        for (FileConfig fileConfig : configs) {
            loadConfig(fileConfig);
        }
    }

    public void loadConfig(FileConfig config) {
        if (!config.hasConfig()) {
            ClientUtils.getLogger().info("[FileManager] Skipped loading config: " + config.getFile().getName() + ".");
            saveConfig(config, true);
            return;
        }
        try {
            config.loadConfig();
            ClientUtils.getLogger().info("[FileManager] Loaded config: " + config.getFile().getName() + ".");
        } catch (Throwable t) {
            ClientUtils.getLogger().error("[FileManager] Failed to load config file: " + config.getFile().getName() + ".", t);
        }
    }

    public void saveAllConfigs() {
        Field[] declaredFields;
        for (Field field : getClass().getDeclaredFields()) {
            if (field.getType() == FileConfig.class) {
                try {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    FileConfig fileConfig = (FileConfig) field.get(this);
                    saveConfig(fileConfig);
                } catch (IllegalAccessException e) {
                    ClientUtils.getLogger().error("[FileManager] Failed to save config file of field " + field.getName() + ".", e);
                }
            }
        }
    }

    public void saveConfigs(FileConfig... configs) {
        for (FileConfig fileConfig : configs) {
            saveConfig(fileConfig);
        }
    }

    public void saveConfig(FileConfig config) {
        saveConfig(config, false);
    }

    private void saveConfig(FileConfig config, boolean ignoreStarting) {
        if (!ignoreStarting && LiquidBounce.INSTANCE.isStarting()) {
            return;
        }
        try {
            if (!config.hasConfig()) {
                config.createConfig();
            }
            config.saveConfig();
            ClientUtils.getLogger().info("[FileManager] Saved config: " + config.getFile().getName() + ".");
        } catch (Throwable t) {
            ClientUtils.getLogger().error("[FileManager] Failed to save config file: " + config.getFile().getName() + ".", t);
        }
    }

    public void loadBackground() {
        if (this.backgroundFile.exists()) {
            try {
                BufferedImage bufferedImage = ImageIO.read(new FileInputStream(this.backgroundFile));
                if (bufferedImage == null) {
                    return;
                }
                LiquidBounce.INSTANCE.setBackground(new ResourceLocation(LiquidBounce.CLIENT_NAME.toLowerCase() + "/userbackground.png"));
                f362mc.func_110434_K().func_110579_a(LiquidBounce.INSTANCE.getBackground(), new DynamicTexture(bufferedImage));
                ClientUtils.getLogger().info("[FileManager] Loaded background.");
            } catch (Exception e) {
                ClientUtils.getLogger().error("[FileManager] Failed to load background.", e);
            }
        }
    }
}
