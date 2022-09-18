package org.spongepowered.asm.mixin;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.GlobalProperties;
import org.spongepowered.asm.mixin.transformer.Config;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/Mixins.class */
public final class Mixins {
    private static final String CONFIGS_KEY = "mixin.configs.queue";
    private static final Logger logger = LogManager.getLogger("mixin");
    private static final Set<String> errorHandlers = new LinkedHashSet();

    private Mixins() {
    }

    public static void addConfigurations(String... configFiles) {
        MixinEnvironment fallback = MixinEnvironment.getDefaultEnvironment();
        for (String configFile : configFiles) {
            createConfiguration(configFile, fallback);
        }
    }

    public static void addConfiguration(String configFile) {
        createConfiguration(configFile, MixinEnvironment.getDefaultEnvironment());
    }

    @Deprecated
    public static void addConfiguration(String configFile, MixinEnvironment fallback) {
        createConfiguration(configFile, fallback);
    }

    private static void createConfiguration(String configFile, MixinEnvironment fallback) {
        Config config = null;
        try {
            config = Config.create(configFile, fallback);
        } catch (Exception ex) {
            logger.error("Error encountered reading mixin config " + configFile + ": " + ex.getClass().getName() + " " + ex.getMessage(), ex);
        }
        registerConfiguration(config);
    }

    private static void registerConfiguration(Config config) {
        if (config == null) {
            return;
        }
        MixinEnvironment env = config.getEnvironment();
        if (env != null) {
            env.registerConfig(config.getName());
        }
        getConfigs().add(config);
    }

    public static int getUnvisitedCount() {
        int count = 0;
        for (Config config : getConfigs()) {
            if (!config.isVisited()) {
                count++;
            }
        }
        return count;
    }

    public static Set<Config> getConfigs() {
        Set<Config> mixinConfigs = (Set) GlobalProperties.get(CONFIGS_KEY);
        if (mixinConfigs == null) {
            mixinConfigs = new LinkedHashSet<>();
            GlobalProperties.put(CONFIGS_KEY, mixinConfigs);
        }
        return mixinConfigs;
    }

    public static void registerErrorHandlerClass(String handlerName) {
        if (handlerName != null) {
            errorHandlers.add(handlerName);
        }
    }

    public static Set<String> getErrorHandlerClasses() {
        return Collections.unmodifiableSet(errorHandlers);
    }
}
