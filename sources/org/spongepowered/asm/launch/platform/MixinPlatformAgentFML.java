package org.spongepowered.asm.launch.platform;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.launch.GlobalProperties;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IRemapper;
import org.spongepowered.asm.service.mojang.MixinServiceLaunchWrapper;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/launch/platform/MixinPlatformAgentFML.class */
public class MixinPlatformAgentFML extends MixinPlatformAgentAbstract {
    private static final String LOAD_CORE_MOD_METHOD = "loadCoreMod";
    private static final String GET_REPARSEABLE_COREMODS_METHOD = "getReparseableCoremods";
    private static final String CORE_MOD_MANAGER_CLASS = "net.minecraftforge.fml.relauncher.CoreModManager";
    private static final String CORE_MOD_MANAGER_CLASS_LEGACY = "cpw.mods.fml.relauncher.CoreModManager";
    private static final String GET_IGNORED_MODS_METHOD = "getIgnoredMods";
    private static final String GET_IGNORED_MODS_METHOD_LEGACY = "getLoadedCoremods";
    private static final String FML_REMAPPER_ADAPTER_CLASS = "org.spongepowered.asm.bridge.RemapperAdapterFML";
    private static final String FML_CMDLINE_COREMODS = "fml.coreMods.load";
    private static final String FML_PLUGIN_WRAPPER_CLASS = "FMLPluginWrapper";
    private static final String FML_CORE_MOD_INSTANCE_FIELD = "coreModInstance";
    private static final String MFATT_FORCELOADASMOD = "ForceLoadAsMod";
    private static final String MFATT_FMLCOREPLUGIN = "FMLCorePlugin";
    private static final String MFATT_COREMODCONTAINSMOD = "FMLCorePluginContainsFMLMod";
    private static final String FML_TWEAKER_DEOBF = "FMLDeobfTweaker";
    private static final String FML_TWEAKER_INJECTION = "FMLInjectionAndSortingTweaker";
    private static final String FML_TWEAKER_TERMINAL = "TerminalTweaker";
    private static final Set<String> loadedCoreMods = new HashSet();
    private Class<?> clCoreModManager;
    private boolean initInjectionState;
    private final String fileName = this.container.getName();
    private final ITweaker coreModWrapper = initFMLCoreMod();

    static {
        String[] split;
        for (String cmdLineCoreMod : System.getProperty(FML_CMDLINE_COREMODS, "").split(",")) {
            if (!cmdLineCoreMod.isEmpty()) {
                MixinPlatformAgentAbstract.logger.debug("FML platform agent will ignore coremod {} specified on the command line", new Object[]{cmdLineCoreMod});
                loadedCoreMods.add(cmdLineCoreMod);
            }
        }
    }

    public MixinPlatformAgentFML(MixinPlatformManager manager, URI uri) {
        super(manager, uri);
    }

    private ITweaker initFMLCoreMod() {
        try {
            try {
                this.clCoreModManager = getCoreModManagerClass();
                if ("true".equalsIgnoreCase(this.attributes.get(MFATT_FORCELOADASMOD))) {
                    MixinPlatformAgentAbstract.logger.debug("ForceLoadAsMod was specified for {}, attempting force-load", new Object[]{this.fileName});
                    loadAsMod();
                }
                return injectCorePlugin();
            } catch (ClassNotFoundException ex) {
                MixinPlatformAgentAbstract.logger.info("FML platform manager could not load class {}. Proceeding without FML support.", new Object[]{ex.getMessage()});
                return null;
            }
        } catch (Exception ex2) {
            MixinPlatformAgentAbstract.logger.catching(ex2);
            return null;
        }
    }

    private void loadAsMod() {
        try {
            getIgnoredMods(this.clCoreModManager).remove(this.fileName);
        } catch (Exception ex) {
            MixinPlatformAgentAbstract.logger.catching(ex);
        }
        if (this.attributes.get(MFATT_COREMODCONTAINSMOD) != null) {
            if (isIgnoredReparseable()) {
                MixinPlatformAgentAbstract.logger.debug("Ignoring request to add {} to reparseable coremod collection - it is a deobfuscated dependency", new Object[]{this.fileName});
            } else {
                addReparseableJar();
            }
        }
    }

    private boolean isIgnoredReparseable() {
        return this.container.toString().contains("deobfedDeps");
    }

    private void addReparseableJar() {
        try {
            Method mdGetReparsedCoremods = this.clCoreModManager.getDeclaredMethod(GlobalProperties.getString(GlobalProperties.Keys.FML_GET_REPARSEABLE_COREMODS, GET_REPARSEABLE_COREMODS_METHOD), new Class[0]);
            List<String> reparsedCoremods = (List) mdGetReparsedCoremods.invoke(null, new Object[0]);
            if (!reparsedCoremods.contains(this.fileName)) {
                MixinPlatformAgentAbstract.logger.debug("Adding {} to reparseable coremod collection", new Object[]{this.fileName});
                reparsedCoremods.add(this.fileName);
            }
        } catch (Exception ex) {
            MixinPlatformAgentAbstract.logger.catching(ex);
        }
    }

    private ITweaker injectCorePlugin() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String coreModName = this.attributes.get(MFATT_FMLCOREPLUGIN);
        if (coreModName == null) {
            return null;
        }
        if (isAlreadyInjected(coreModName)) {
            MixinPlatformAgentAbstract.logger.debug("{} has core plugin {}. Skipping because it was already injected.", new Object[]{this.fileName, coreModName});
            return null;
        }
        MixinPlatformAgentAbstract.logger.debug("{} has core plugin {}. Injecting it into FML for co-initialisation:", new Object[]{this.fileName, coreModName});
        Method mdLoadCoreMod = this.clCoreModManager.getDeclaredMethod(GlobalProperties.getString(GlobalProperties.Keys.FML_LOAD_CORE_MOD, LOAD_CORE_MOD_METHOD), LaunchClassLoader.class, String.class, File.class);
        mdLoadCoreMod.setAccessible(true);
        ITweaker wrapper = (ITweaker) mdLoadCoreMod.invoke(null, Launch.classLoader, coreModName, this.container);
        if (wrapper == null) {
            MixinPlatformAgentAbstract.logger.debug("Core plugin {} could not be loaded.", new Object[]{coreModName});
            return null;
        }
        this.initInjectionState = isTweakerQueued(FML_TWEAKER_INJECTION);
        loadedCoreMods.add(coreModName);
        return wrapper;
    }

    private boolean isAlreadyInjected(String coreModName) {
        if (loadedCoreMods.contains(coreModName)) {
            return true;
        }
        try {
            List<ITweaker> tweakers = (List) GlobalProperties.get(MixinServiceLaunchWrapper.BLACKBOARD_KEY_TWEAKS);
            if (tweakers == null) {
                return false;
            }
            for (ITweaker tweaker : tweakers) {
                Class<?> cls = tweaker.getClass();
                if (FML_PLUGIN_WRAPPER_CLASS.equals(cls.getSimpleName())) {
                    Field fdCoreModInstance = cls.getField(FML_CORE_MOD_INSTANCE_FIELD);
                    fdCoreModInstance.setAccessible(true);
                    Object coreMod = fdCoreModInstance.get(tweaker);
                    if (coreModName.equals(coreMod.getClass().getName())) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override // org.spongepowered.asm.launch.platform.MixinPlatformAgentAbstract, org.spongepowered.asm.launch.platform.IMixinPlatformAgent
    public String getPhaseProvider() {
        return MixinPlatformAgentFML.class.getName() + "$PhaseProvider";
    }

    @Override // org.spongepowered.asm.launch.platform.IMixinPlatformAgent
    public void prepare() {
        this.initInjectionState |= isTweakerQueued(FML_TWEAKER_INJECTION);
    }

    @Override // org.spongepowered.asm.launch.platform.IMixinPlatformAgent
    public void initPrimaryContainer() {
        if (this.clCoreModManager != null) {
            injectRemapper();
        }
    }

    private void injectRemapper() {
        try {
            MixinPlatformAgentAbstract.logger.debug("Creating FML remapper adapter: {}", new Object[]{FML_REMAPPER_ADAPTER_CLASS});
            Class<?> clFmlRemapperAdapter = Class.forName(FML_REMAPPER_ADAPTER_CLASS, true, Launch.classLoader);
            Method mdCreate = clFmlRemapperAdapter.getDeclaredMethod("create", new Class[0]);
            IRemapper remapper = (IRemapper) mdCreate.invoke(null, new Object[0]);
            MixinEnvironment.getDefaultEnvironment().getRemappers().add(remapper);
        } catch (Exception e) {
            MixinPlatformAgentAbstract.logger.debug("Failed instancing FML remapper adapter, things will probably go horribly for notch-obf'd mods!");
        }
    }

    @Override // org.spongepowered.asm.launch.platform.IMixinPlatformAgent
    public void inject() {
        if (this.coreModWrapper != null && checkForCoInitialisation()) {
            MixinPlatformAgentAbstract.logger.debug("FML agent is co-initiralising coremod instance {} for {}", new Object[]{this.coreModWrapper, this.uri});
            this.coreModWrapper.injectIntoClassLoader(Launch.classLoader);
        }
    }

    @Override // org.spongepowered.asm.launch.platform.IMixinPlatformAgent
    public String getLaunchTarget() {
        return null;
    }

    protected final boolean checkForCoInitialisation() {
        boolean injectionTweaker = isTweakerQueued(FML_TWEAKER_INJECTION);
        boolean terminalTweaker = isTweakerQueued(FML_TWEAKER_TERMINAL);
        if ((!this.initInjectionState || !terminalTweaker) && !injectionTweaker) {
            return !isTweakerQueued(FML_TWEAKER_DEOBF);
        }
        MixinPlatformAgentAbstract.logger.debug("FML agent is skipping co-init for {} because FML will inject it normally", new Object[]{this.coreModWrapper});
        return false;
    }

    private static boolean isTweakerQueued(String tweakerName) {
        for (String tweaker : (List) GlobalProperties.get(MixinServiceLaunchWrapper.BLACKBOARD_KEY_TWEAKCLASSES)) {
            if (tweaker.endsWith(tweakerName)) {
                return true;
            }
        }
        return false;
    }

    private static Class<?> getCoreModManagerClass() throws ClassNotFoundException {
        try {
            return Class.forName(GlobalProperties.getString(GlobalProperties.Keys.FML_CORE_MOD_MANAGER, CORE_MOD_MANAGER_CLASS));
        } catch (ClassNotFoundException e) {
            return Class.forName(CORE_MOD_MANAGER_CLASS_LEGACY);
        }
    }

    private static List<String> getIgnoredMods(Class<?> clCoreModManager) throws IllegalAccessException, InvocationTargetException {
        Method mdGetIgnoredMods;
        try {
            mdGetIgnoredMods = clCoreModManager.getDeclaredMethod(GlobalProperties.getString(GlobalProperties.Keys.FML_GET_IGNORED_MODS, GET_IGNORED_MODS_METHOD), new Class[0]);
        } catch (NoSuchMethodException e) {
            try {
                mdGetIgnoredMods = clCoreModManager.getDeclaredMethod(GET_IGNORED_MODS_METHOD_LEGACY, new Class[0]);
            } catch (NoSuchMethodException ex2) {
                MixinPlatformAgentAbstract.logger.catching(Level.DEBUG, ex2);
                return Collections.emptyList();
            }
        }
        return (List) mdGetIgnoredMods.invoke(null, new Object[0]);
    }
}
