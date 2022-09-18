package org.spongepowered.asm.launch.platform;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.Constants;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/launch/platform/MixinPlatformManager.class */
public class MixinPlatformManager {
    private static final String DEFAULT_MAIN_CLASS = "net.minecraft.client.main.Main";
    private static final String MIXIN_TWEAKER_CLASS = "org.spongepowered.asm.launch.MixinTweaker";
    private static final Logger logger = LogManager.getLogger("mixin");
    private MixinContainer primaryContainer;
    private boolean injected;
    private final Map<URI, MixinContainer> containers = new LinkedHashMap();
    private boolean prepared = false;

    public void init() {
        logger.debug("Initialising Mixin Platform Manager");
        try {
            URI uri = getClass().getProtectionDomain().getCodeSource().getLocation().toURI();
            if (uri != null) {
                logger.debug("Mixin platform: primary container is {}", new Object[]{uri});
                this.primaryContainer = addContainer(uri);
            }
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        scanClasspath();
    }

    public Collection<String> getPhaseProviderClasses() {
        Collection<String> phaseProviders = this.primaryContainer.getPhaseProviders();
        if (phaseProviders != null) {
            return Collections.unmodifiableCollection(phaseProviders);
        }
        return Collections.emptyList();
    }

    public final MixinContainer addContainer(URI uri) {
        MixinContainer existingContainer = this.containers.get(uri);
        if (existingContainer != null) {
            return existingContainer;
        }
        logger.debug("Adding mixin platform agents for container {}", new Object[]{uri});
        MixinContainer container = new MixinContainer(this, uri);
        this.containers.put(uri, container);
        if (this.prepared) {
            container.prepare();
        }
        return container;
    }

    public final void prepare(List<String> args) {
        this.prepared = true;
        for (MixinContainer container : this.containers.values()) {
            container.prepare();
        }
        if (args != null) {
            parseArgs(args);
            return;
        }
        String argv = System.getProperty("sun.java.command");
        if (argv != null) {
            parseArgs(Arrays.asList(argv.split(" ")));
        }
    }

    private void parseArgs(List<String> args) {
        boolean captureNext = false;
        for (String arg : args) {
            if (captureNext) {
                addConfig(arg);
            }
            captureNext = "--mixin".equals(arg);
        }
    }

    public final void inject() {
        if (this.injected) {
            return;
        }
        this.injected = true;
        if (this.primaryContainer != null) {
            this.primaryContainer.initPrimaryContainer();
        }
        scanClasspath();
        logger.debug("inject() running with {} agents", new Object[]{Integer.valueOf(this.containers.size())});
        for (MixinContainer container : this.containers.values()) {
            try {
                container.inject();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void scanClasspath() {
        URL[] sources = MixinService.getService().getClassProvider().getClassPath();
        for (URL url : sources) {
            try {
                URI uri = url.toURI();
                if (!this.containers.containsKey(uri)) {
                    logger.debug("Scanning {} for mixin tweaker", new Object[]{uri});
                    if ("file".equals(uri.getScheme()) && new File(uri).exists()) {
                        MainAttributes attributes = MainAttributes.m27of(uri);
                        String tweaker = attributes.get(Constants.ManifestAttributes.TWEAKER);
                        if (MIXIN_TWEAKER_CLASS.equals(tweaker)) {
                            logger.debug("{} contains a mixin tweaker, adding agents", new Object[]{uri});
                            addContainer(uri);
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public String getLaunchTarget() {
        for (MixinContainer container : this.containers.values()) {
            String mainClass = container.getLaunchTarget();
            if (mainClass != null) {
                return mainClass;
            }
        }
        return DEFAULT_MAIN_CLASS;
    }

    public final void setCompatibilityLevel(String level) {
        try {
            MixinEnvironment.CompatibilityLevel value = MixinEnvironment.CompatibilityLevel.valueOf(level.toUpperCase());
            logger.debug("Setting mixin compatibility level: {}", new Object[]{value});
            MixinEnvironment.setCompatibilityLevel(value);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid compatibility level specified: {}", new Object[]{level});
        }
    }

    public final void addConfig(String config) {
        if (config.endsWith(".json")) {
            logger.debug("Registering mixin config: {}", new Object[]{config});
            Mixins.addConfiguration(config);
        } else if (config.contains(".json@")) {
            int pos = config.indexOf(".json@");
            String phaseName = config.substring(pos + 6);
            String config2 = config.substring(0, pos + 5);
            MixinEnvironment.Phase phase = MixinEnvironment.Phase.forName(phaseName);
            if (phase != null) {
                logger.warn("Setting config phase via manifest is deprecated: {}. Specify target in config instead", new Object[]{config2});
                logger.debug("Registering mixin config: {}", new Object[]{config2});
                MixinEnvironment.getEnvironment(phase).addConfiguration(config2);
            }
        }
    }

    public final void addTokenProvider(String provider) {
        if (provider.contains("@")) {
            String[] parts = provider.split("@", 2);
            MixinEnvironment.Phase phase = MixinEnvironment.Phase.forName(parts[1]);
            if (phase != null) {
                logger.debug("Registering token provider class: {}", new Object[]{parts[0]});
                MixinEnvironment.getEnvironment(phase).registerTokenProviderClass(parts[0]);
                return;
            }
            return;
        }
        MixinEnvironment.getDefaultEnvironment().registerTokenProviderClass(provider);
    }
}
