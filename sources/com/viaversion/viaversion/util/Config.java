package com.viaversion.viaversion.util;

import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/util/Config.class */
public abstract class Config implements ConfigurationProvider {
    private static final ThreadLocal<Yaml> YAML = ThreadLocal.withInitial(() -> {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(false);
        options.setIndent(2);
        return new Yaml(new YamlConstructor(), new Representer(), options);
    });
    private final CommentStore commentStore = new CommentStore('.', 2);
    private final File configFile;
    private Map<String, Object> config;

    protected abstract void handleConfig(Map<String, Object> map);

    public abstract List<String> getUnsupportedOptions();

    public Config(File configFile) {
        this.configFile = configFile;
    }

    public URL getDefaultConfigURL() {
        return getClass().getClassLoader().getResource("assets/viaversion/config.yml");
    }

    public synchronized Map<String, Object> loadConfig(File location) {
        List<String> unsupported = getUnsupportedOptions();
        URL jarConfigFile = getDefaultConfigURL();
        try {
            this.commentStore.storeComments(jarConfigFile.openStream());
            for (String option : unsupported) {
                List<String> comments = this.commentStore.header(option);
                if (comments != null) {
                    comments.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Object> config = null;
        if (location.exists()) {
            try {
                FileInputStream input = new FileInputStream(location);
                Throwable th = null;
                try {
                    config = (Map) YAML.get().load(input);
                    if (input != null) {
                        if (0 != 0) {
                            try {
                                input.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        } else {
                            input.close();
                        }
                    }
                } finally {
                }
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
        if (config == null) {
            config = new HashMap<>();
        }
        Map<String, Object> defaults = config;
        try {
            InputStream stream = jarConfigFile.openStream();
            defaults = (Map) YAML.get().load(stream);
            for (String option2 : unsupported) {
                defaults.remove(option2);
            }
            for (Map.Entry<String, Object> entry : config.entrySet()) {
                if (defaults.containsKey(entry.getKey()) && !unsupported.contains(entry.getKey())) {
                    defaults.put(entry.getKey(), entry.getValue());
                }
            }
            if (stream != null) {
                if (0 != 0) {
                    stream.close();
                } else {
                    stream.close();
                }
            }
        } catch (IOException e4) {
            e4.printStackTrace();
        }
        handleConfig(defaults);
        saveConfig(location, defaults);
        return defaults;
    }

    public synchronized void saveConfig(File location, Map<String, Object> config) {
        try {
            this.commentStore.writeComments(YAML.get().dump(config), location);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override // com.viaversion.viaversion.api.configuration.ConfigurationProvider
    public void set(String path, Object value) {
        this.config.put(path, value);
    }

    @Override // com.viaversion.viaversion.api.configuration.ConfigurationProvider
    public void saveConfig() {
        this.configFile.getParentFile().mkdirs();
        saveConfig(this.configFile, this.config);
    }

    @Override // com.viaversion.viaversion.api.configuration.ConfigurationProvider
    public void reloadConfig() {
        this.configFile.getParentFile().mkdirs();
        this.config = new ConcurrentSkipListMap(loadConfig(this.configFile));
    }

    @Override // com.viaversion.viaversion.api.configuration.ConfigurationProvider
    public Map<String, Object> getValues() {
        return this.config;
    }

    public <T> T get(String key, Class<T> clazz, T def) {
        T t = (T) this.config.get(key);
        if (t != null) {
            return t;
        }
        return def;
    }

    public boolean getBoolean(String key, boolean def) {
        Object o = this.config.get(key);
        if (o != null) {
            return ((Boolean) o).booleanValue();
        }
        return def;
    }

    public String getString(String key, String def) {
        Object o = this.config.get(key);
        if (o != null) {
            return (String) o;
        }
        return def;
    }

    public int getInt(String key, int def) {
        Object o = this.config.get(key);
        if (o != null) {
            if (o instanceof Number) {
                return ((Number) o).intValue();
            }
            return def;
        }
        return def;
    }

    public double getDouble(String key, double def) {
        Object o = this.config.get(key);
        if (o != null) {
            if (o instanceof Number) {
                return ((Number) o).doubleValue();
            }
            return def;
        }
        return def;
    }

    public List<Integer> getIntegerList(String key) {
        Object o = this.config.get(key);
        return o != null ? (List) o : new ArrayList();
    }

    public List<String> getStringList(String key) {
        Object o = this.config.get(key);
        return o != null ? (List) o : new ArrayList();
    }

    public JsonElement getSerializedComponent(String key) {
        Object o = this.config.get(key);
        if (o != null && !((String) o).isEmpty()) {
            return GsonComponentSerializer.gson().serializeToTree(LegacyComponentSerializer.legacySection().deserialize((String) o));
        }
        return null;
    }
}
