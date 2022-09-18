package org.spongepowered.asm.mixin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.spongepowered.asm.launch.GlobalProperties;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.extensibility.IEnvironmentTokenProvider;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.mixin.transformer.MixinTransformer;
import org.spongepowered.asm.obfuscation.RemapperChain;
import org.spongepowered.asm.service.ILegacyClassTransformer;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.ITransformer;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.ITokenProvider;
import org.spongepowered.asm.util.JavaVersion;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.perf.Profiler;
import org.spongepowered.tools.obfuscation.mcp.ObfuscationServiceMCP;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/MixinEnvironment.class */
public final class MixinEnvironment implements ITokenProvider {
    private static MixinEnvironment currentEnvironment;
    private final Phase phase;
    private final String configsKey;
    private final boolean[] options;
    private Side side;
    private List<ILegacyClassTransformer> transformers;
    private static final Set<String> excludeTransformers = Sets.newHashSet(new String[]{"net.minecraftforge.fml.common.asm.transformers.EventSubscriptionTransformer", "cpw.mods.fml.common.asm.transformers.EventSubscriptionTransformer", "net.minecraftforge.fml.common.asm.transformers.TerminalTransformer", "cpw.mods.fml.common.asm.transformers.TerminalTransformer"});
    private static Phase currentPhase = Phase.NOT_INITIALISED;
    private static CompatibilityLevel compatibility = (CompatibilityLevel) Option.DEFAULT_COMPATIBILITY_LEVEL.getEnumValue(CompatibilityLevel.JAVA_6);
    private static boolean showHeader = true;
    private static final Logger logger = LogManager.getLogger("mixin");
    private static final Profiler profiler = new Profiler();
    private final Set<String> tokenProviderClasses = new HashSet();
    private final List<TokenProviderWrapper> tokenProviders = new ArrayList();
    private final Map<String, Integer> internalTokens = new HashMap();
    private final RemapperChain remappers = new RemapperChain();
    private String obfuscationContext = null;
    private final IMixinService service = MixinService.getService();

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/MixinEnvironment$Side.class */
    public enum Side {
        UNKNOWN { // from class: org.spongepowered.asm.mixin.MixinEnvironment.Side.1
            @Override // org.spongepowered.asm.mixin.MixinEnvironment.Side
            protected boolean detect() {
                return false;
            }
        },
        CLIENT { // from class: org.spongepowered.asm.mixin.MixinEnvironment.Side.2
            @Override // org.spongepowered.asm.mixin.MixinEnvironment.Side
            protected boolean detect() {
                String sideName = MixinService.getService().getSideName();
                return "CLIENT".equals(sideName);
            }
        },
        SERVER { // from class: org.spongepowered.asm.mixin.MixinEnvironment.Side.3
            @Override // org.spongepowered.asm.mixin.MixinEnvironment.Side
            protected boolean detect() {
                String sideName = MixinService.getService().getSideName();
                return "SERVER".equals(sideName) || "DEDICATEDSERVER".equals(sideName);
            }
        };

        protected abstract boolean detect();
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/MixinEnvironment$Phase.class */
    public static final class Phase {
        static final Phase NOT_INITIALISED = new Phase(-1, "NOT_INITIALISED");
        public static final Phase PREINIT = new Phase(0, "PREINIT");
        public static final Phase INIT = new Phase(1, "INIT");
        public static final Phase DEFAULT = new Phase(2, "DEFAULT");
        static final List<Phase> phases = ImmutableList.of(PREINIT, INIT, DEFAULT);
        final int ordinal;
        final String name;
        private MixinEnvironment environment;

        private Phase(int ordinal, String name) {
            this.ordinal = ordinal;
            this.name = name;
        }

        public String toString() {
            return this.name;
        }

        public static Phase forName(String name) {
            for (Phase phase : phases) {
                if (phase.name.equals(name)) {
                    return phase;
                }
            }
            return null;
        }

        MixinEnvironment getEnvironment() {
            if (this.ordinal < 0) {
                throw new IllegalArgumentException("Cannot access the NOT_INITIALISED environment");
            }
            if (this.environment == null) {
                this.environment = new MixinEnvironment(this);
            }
            return this.environment;
        }
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/MixinEnvironment$Option.class */
    public enum Option {
        DEBUG_ALL("debug"),
        DEBUG_EXPORT(DEBUG_ALL, "export"),
        DEBUG_EXPORT_FILTER(DEBUG_EXPORT, "filter", false),
        DEBUG_EXPORT_DECOMPILE(DEBUG_EXPORT, Inherit.ALLOW_OVERRIDE, "decompile"),
        DEBUG_EXPORT_DECOMPILE_THREADED(DEBUG_EXPORT_DECOMPILE, Inherit.ALLOW_OVERRIDE, "async"),
        DEBUG_EXPORT_DECOMPILE_MERGESIGNATURES(DEBUG_EXPORT_DECOMPILE, Inherit.ALLOW_OVERRIDE, "mergeGenericSignatures"),
        DEBUG_VERIFY(DEBUG_ALL, "verify"),
        DEBUG_VERBOSE(DEBUG_ALL, "verbose"),
        DEBUG_INJECTORS(DEBUG_ALL, "countInjections"),
        DEBUG_STRICT(DEBUG_ALL, Inherit.INDEPENDENT, "strict"),
        DEBUG_UNIQUE(DEBUG_STRICT, "unique"),
        DEBUG_TARGETS(DEBUG_STRICT, "targets"),
        DEBUG_PROFILER(DEBUG_ALL, Inherit.ALLOW_OVERRIDE, "profiler"),
        DUMP_TARGET_ON_FAILURE("dumpTargetOnFailure"),
        CHECK_ALL("checks"),
        CHECK_IMPLEMENTS(CHECK_ALL, "interfaces"),
        CHECK_IMPLEMENTS_STRICT(CHECK_IMPLEMENTS, Inherit.ALLOW_OVERRIDE, "strict"),
        IGNORE_CONSTRAINTS("ignoreConstraints"),
        HOT_SWAP("hotSwap"),
        ENVIRONMENT(Inherit.ALWAYS_FALSE, "env"),
        OBFUSCATION_TYPE(ENVIRONMENT, Inherit.ALWAYS_FALSE, "obf"),
        DISABLE_REFMAP(ENVIRONMENT, Inherit.INDEPENDENT, "disableRefMap"),
        REFMAP_REMAP(ENVIRONMENT, Inherit.INDEPENDENT, "remapRefMap"),
        REFMAP_REMAP_RESOURCE(ENVIRONMENT, Inherit.INDEPENDENT, "refMapRemappingFile", ""),
        REFMAP_REMAP_SOURCE_ENV(ENVIRONMENT, Inherit.INDEPENDENT, "refMapRemappingEnv", ObfuscationServiceMCP.SEARGE),
        REFMAP_REMAP_ALLOW_PERMISSIVE(ENVIRONMENT, Inherit.INDEPENDENT, "allowPermissiveMatch", true, "true"),
        IGNORE_REQUIRED(ENVIRONMENT, Inherit.INDEPENDENT, "ignoreRequired"),
        DEFAULT_COMPATIBILITY_LEVEL(ENVIRONMENT, Inherit.INDEPENDENT, "compatLevel"),
        SHIFT_BY_VIOLATION_BEHAVIOUR(ENVIRONMENT, Inherit.INDEPENDENT, "shiftByViolation", "warn"),
        INITIALISER_INJECTION_MODE("initialiserInjectionMode", "default");
        
        private static final String PREFIX = "mixin";
        final Option parent;
        final Inherit inheritance;
        final String property;
        final String defaultValue;
        final boolean isFlag;
        final int depth;

        /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/MixinEnvironment$Option$Inherit.class */
        public enum Inherit {
            INHERIT,
            ALLOW_OVERRIDE,
            INDEPENDENT,
            ALWAYS_FALSE
        }

        Option(String property) {
            this((Option) null, property, true);
        }

        Option(Inherit inheritance, String property) {
            this((Option) null, inheritance, property, true);
        }

        Option(String property, boolean flag) {
            this((Option) null, property, flag);
        }

        Option(String property, String defaultStringValue) {
            this(null, Inherit.INDEPENDENT, property, false, defaultStringValue);
        }

        Option(Option parent, String property) {
            this(parent, Inherit.INHERIT, property, true);
        }

        Option(Option parent, Inherit inheritance, String property) {
            this(parent, inheritance, property, true);
        }

        Option(Option parent, String property, boolean isFlag) {
            this(parent, Inherit.INHERIT, property, isFlag, null);
        }

        Option(Option parent, Inherit inheritance, String property, boolean isFlag) {
            this(parent, inheritance, property, isFlag, null);
        }

        Option(Option parent, String property, String defaultStringValue) {
            this(parent, Inherit.INHERIT, property, false, defaultStringValue);
        }

        Option(Option parent, Inherit inheritance, String property, String defaultStringValue) {
            this(parent, inheritance, property, false, defaultStringValue);
        }

        Option(Option parent, Inherit inheritance, String property, boolean isFlag, String defaultStringValue) {
            this.parent = parent;
            this.inheritance = inheritance;
            this.property = (parent != null ? parent.property : PREFIX) + "." + property;
            this.defaultValue = defaultStringValue;
            this.isFlag = isFlag;
            int depth = 0;
            while (parent != null) {
                parent = parent.parent;
                depth++;
            }
            this.depth = depth;
        }

        Option getParent() {
            return this.parent;
        }

        String getProperty() {
            return this.property;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.isFlag ? String.valueOf(getBooleanValue()) : getStringValue();
        }

        private boolean getLocalBooleanValue(boolean defaultValue) {
            return Boolean.parseBoolean(System.getProperty(this.property, Boolean.toString(defaultValue)));
        }

        private boolean getInheritedBooleanValue() {
            return this.parent != null && this.parent.getBooleanValue();
        }

        final boolean getBooleanValue() {
            if (this.inheritance == Inherit.ALWAYS_FALSE) {
                return false;
            }
            boolean local = getLocalBooleanValue(false);
            if (this.inheritance == Inherit.INDEPENDENT) {
                return local;
            }
            boolean inherited = local || getInheritedBooleanValue();
            return this.inheritance == Inherit.INHERIT ? inherited : getLocalBooleanValue(inherited);
        }

        final String getStringValue() {
            return (this.inheritance == Inherit.INDEPENDENT || this.parent == null || this.parent.getBooleanValue()) ? System.getProperty(this.property, this.defaultValue) : this.defaultValue;
        }

        <E extends Enum<E>> E getEnumValue(E defaultValue) {
            String value = System.getProperty(this.property, defaultValue.name());
            try {
                return (E) Enum.valueOf(defaultValue.getClass(), value.toUpperCase());
            } catch (IllegalArgumentException e) {
                return defaultValue;
            }
        }
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/MixinEnvironment$CompatibilityLevel.class */
    public enum CompatibilityLevel {
        JAVA_6(6, 50, false),
        JAVA_7(7, 51, false) { // from class: org.spongepowered.asm.mixin.MixinEnvironment.CompatibilityLevel.1
            @Override // org.spongepowered.asm.mixin.MixinEnvironment.CompatibilityLevel
            boolean isSupported() {
                return JavaVersion.current() >= 1.7d;
            }
        },
        JAVA_8(8, 52, true) { // from class: org.spongepowered.asm.mixin.MixinEnvironment.CompatibilityLevel.2
            @Override // org.spongepowered.asm.mixin.MixinEnvironment.CompatibilityLevel
            boolean isSupported() {
                return JavaVersion.current() >= 1.8d;
            }
        },
        JAVA_9(9, 53, true) { // from class: org.spongepowered.asm.mixin.MixinEnvironment.CompatibilityLevel.3
            @Override // org.spongepowered.asm.mixin.MixinEnvironment.CompatibilityLevel
            boolean isSupported() {
                return false;
            }
        };
        
        private static final int CLASS_V1_9 = 53;
        private final int ver;
        private final int classVersion;
        private final boolean supportsMethodsInInterfaces;
        private CompatibilityLevel maxCompatibleLevel;

        CompatibilityLevel(int ver, int classVersion, boolean resolveMethodsInInterfaces) {
            this.ver = ver;
            this.classVersion = classVersion;
            this.supportsMethodsInInterfaces = resolveMethodsInInterfaces;
        }

        private void setMaxCompatibleLevel(CompatibilityLevel maxCompatibleLevel) {
            this.maxCompatibleLevel = maxCompatibleLevel;
        }

        boolean isSupported() {
            return true;
        }

        public int classVersion() {
            return this.classVersion;
        }

        public boolean supportsMethodsInInterfaces() {
            return this.supportsMethodsInInterfaces;
        }

        public boolean isAtLeast(CompatibilityLevel level) {
            return level == null || this.ver >= level.ver;
        }

        public boolean canElevateTo(CompatibilityLevel level) {
            return level == null || this.maxCompatibleLevel == null || level.ver <= this.maxCompatibleLevel.ver;
        }

        public boolean canSupport(CompatibilityLevel level) {
            if (level == null) {
                return true;
            }
            return level.canElevateTo(this);
        }
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/MixinEnvironment$TokenProviderWrapper.class */
    public static class TokenProviderWrapper implements Comparable<TokenProviderWrapper> {
        private static int nextOrder = 0;
        private final int priority;
        private final int order;
        private final IEnvironmentTokenProvider provider;
        private final MixinEnvironment environment;

        public TokenProviderWrapper(IEnvironmentTokenProvider provider, MixinEnvironment environment) {
            this.provider = provider;
            this.environment = environment;
            int i = nextOrder;
            nextOrder = i + 1;
            this.order = i;
            this.priority = provider.getPriority();
        }

        public int compareTo(TokenProviderWrapper other) {
            if (other == null) {
                return 0;
            }
            if (other.priority == this.priority) {
                return other.order - this.order;
            }
            return other.priority - this.priority;
        }

        public IEnvironmentTokenProvider getProvider() {
            return this.provider;
        }

        Integer getToken(String token) {
            return this.provider.getToken(token, this.environment);
        }
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/MixinEnvironment$MixinLogWatcher.class */
    public static class MixinLogWatcher {
        static org.apache.logging.log4j.core.Logger log;
        static MixinAppender appender = new MixinAppender();
        static Level oldLevel = null;

        MixinLogWatcher() {
        }

        static void begin() {
            org.apache.logging.log4j.core.Logger logger = LogManager.getLogger("FML");
            if (!(logger instanceof org.apache.logging.log4j.core.Logger)) {
                return;
            }
            log = logger;
            oldLevel = log.getLevel();
            appender.start();
            log.addAppender(appender);
            log.setLevel(Level.ALL);
        }

        static void end() {
            if (log != null) {
                log.removeAppender(appender);
            }
        }

        /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/MixinEnvironment$MixinLogWatcher$MixinAppender.class */
        public static class MixinAppender extends AbstractAppender {
            MixinAppender() {
                super("MixinLogWatcherAppender", (Filter) null, (Layout) null);
            }

            public void append(LogEvent event) {
                if (event.getLevel() != Level.DEBUG || !"Validating minecraft".equals(event.getMessage().getFormattedMessage())) {
                    return;
                }
                MixinEnvironment.gotoPhase(Phase.INIT);
                if (MixinLogWatcher.log.getLevel() == Level.ALL) {
                    MixinLogWatcher.log.setLevel(MixinLogWatcher.oldLevel);
                }
            }
        }
    }

    MixinEnvironment(Phase phase) {
        Option[] values;
        this.phase = phase;
        this.configsKey = "mixin.configs." + this.phase.name.toLowerCase();
        Object version = getVersion();
        if (version == null || !MixinBootstrap.VERSION.equals(version)) {
            throw new MixinException("Environment conflict, mismatched versions or you didn't call MixinBootstrap.init()");
        }
        this.service.checkEnv(this);
        this.options = new boolean[Option.values().length];
        for (Option option : Option.values()) {
            this.options[option.ordinal()] = option.getBooleanValue();
        }
        if (showHeader) {
            showHeader = false;
            printHeader(version);
        }
    }

    private void printHeader(Object version) {
        Option[] values;
        String codeSource = getCodeSource();
        String serviceName = this.service.getName();
        Side side = getSide();
        logger.info("SpongePowered MIXIN Subsystem Version={} Source={} Service={} Env={}", new Object[]{version, codeSource, serviceName, side});
        boolean verbose = getOption(Option.DEBUG_VERBOSE);
        if (verbose || getOption(Option.DEBUG_EXPORT) || getOption(Option.DEBUG_PROFILER)) {
            PrettyPrinter printer = new PrettyPrinter(32);
            Object[] objArr = new Object[1];
            objArr[0] = verbose ? " (Verbose debugging enabled)" : "";
            printer.add("SpongePowered MIXIN%s", objArr).centre().m11hr();
            printer.m9kv("Code source", codeSource);
            printer.m9kv("Internal Version", version);
            printer.m9kv("Java 8 Supported", Boolean.valueOf(CompatibilityLevel.JAVA_8.isSupported())).m11hr();
            printer.m9kv("Service Name", serviceName);
            printer.m9kv("Service Class", this.service.getClass().getName()).m11hr();
            for (Option option : Option.values()) {
                StringBuilder indent = new StringBuilder();
                for (int i = 0; i < option.depth; i++) {
                    indent.append("- ");
                }
                printer.m8kv(option.property, "%s<%s>", indent, option);
            }
            printer.m11hr().m9kv("Detected Side", side);
            printer.print(System.err);
        }
    }

    private String getCodeSource() {
        try {
            return getClass().getProtectionDomain().getCodeSource().getLocation().toString();
        } catch (Throwable th) {
            return "Unknown";
        }
    }

    public Phase getPhase() {
        return this.phase;
    }

    @Deprecated
    public List<String> getMixinConfigs() {
        List<String> mixinConfigs = (List) GlobalProperties.get(this.configsKey);
        if (mixinConfigs == null) {
            mixinConfigs = new ArrayList<>();
            GlobalProperties.put(this.configsKey, mixinConfigs);
        }
        return mixinConfigs;
    }

    @Deprecated
    public MixinEnvironment addConfiguration(String config) {
        logger.warn("MixinEnvironment::addConfiguration is deprecated and will be removed. Use Mixins::addConfiguration instead!");
        Mixins.addConfiguration(config, this);
        return this;
    }

    public void registerConfig(String config) {
        List<String> configs = getMixinConfigs();
        if (!configs.contains(config)) {
            configs.add(config);
        }
    }

    @Deprecated
    public MixinEnvironment registerErrorHandlerClass(String handlerName) {
        Mixins.registerErrorHandlerClass(handlerName);
        return this;
    }

    public MixinEnvironment registerTokenProviderClass(String providerName) {
        if (!this.tokenProviderClasses.contains(providerName)) {
            try {
                IEnvironmentTokenProvider provider = (IEnvironmentTokenProvider) this.service.getClassProvider().findClass(providerName, true).newInstance();
                registerTokenProvider(provider);
            } catch (Throwable th) {
                logger.error("Error instantiating " + providerName, th);
            }
        }
        return this;
    }

    public MixinEnvironment registerTokenProvider(IEnvironmentTokenProvider provider) {
        if (provider != null && !this.tokenProviderClasses.contains(provider.getClass().getName())) {
            String providerName = provider.getClass().getName();
            TokenProviderWrapper wrapper = new TokenProviderWrapper(provider, this);
            logger.info("Adding new token provider {} to {}", new Object[]{providerName, this});
            this.tokenProviders.add(wrapper);
            this.tokenProviderClasses.add(providerName);
            Collections.sort(this.tokenProviders);
        }
        return this;
    }

    @Override // org.spongepowered.asm.util.ITokenProvider
    public Integer getToken(String token) {
        String token2 = token.toUpperCase();
        for (TokenProviderWrapper provider : this.tokenProviders) {
            Integer value = provider.getToken(token2);
            if (value != null) {
                return value;
            }
        }
        return this.internalTokens.get(token2);
    }

    @Deprecated
    public Set<String> getErrorHandlerClasses() {
        return Mixins.getErrorHandlerClasses();
    }

    public Object getActiveTransformer() {
        return GlobalProperties.get(GlobalProperties.Keys.TRANSFORMER);
    }

    public void setActiveTransformer(ITransformer transformer) {
        if (transformer != null) {
            GlobalProperties.put(GlobalProperties.Keys.TRANSFORMER, transformer);
        }
    }

    public MixinEnvironment setSide(Side side) {
        if (side != null && getSide() == Side.UNKNOWN && side != Side.UNKNOWN) {
            this.side = side;
        }
        return this;
    }

    public Side getSide() {
        if (this.side == null) {
            Side[] values = Side.values();
            int length = values.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                Side side = values[i];
                if (!side.detect()) {
                    i++;
                } else {
                    this.side = side;
                    break;
                }
            }
        }
        return this.side != null ? this.side : Side.UNKNOWN;
    }

    public String getVersion() {
        return (String) GlobalProperties.get(GlobalProperties.Keys.INIT);
    }

    public boolean getOption(Option option) {
        return this.options[option.ordinal()];
    }

    public void setOption(Option option, boolean value) {
        this.options[option.ordinal()] = value;
    }

    public String getOptionValue(Option option) {
        return option.getStringValue();
    }

    public <E extends Enum<E>> E getOption(Option option, E defaultValue) {
        return (E) option.getEnumValue(defaultValue);
    }

    public void setObfuscationContext(String context) {
        this.obfuscationContext = context;
    }

    public String getObfuscationContext() {
        return this.obfuscationContext;
    }

    public String getRefmapObfuscationContext() {
        String overrideObfuscationType = Option.OBFUSCATION_TYPE.getStringValue();
        if (overrideObfuscationType != null) {
            return overrideObfuscationType;
        }
        return this.obfuscationContext;
    }

    public RemapperChain getRemappers() {
        return this.remappers;
    }

    public void audit() {
        Object activeTransformer = getActiveTransformer();
        if (activeTransformer instanceof MixinTransformer) {
            MixinTransformer transformer = (MixinTransformer) activeTransformer;
            transformer.audit(this);
        }
    }

    public List<ILegacyClassTransformer> getTransformers() {
        if (this.transformers == null) {
            buildTransformerDelegationList();
        }
        return Collections.unmodifiableList(this.transformers);
    }

    public void addTransformerExclusion(String name) {
        excludeTransformers.add(name);
        this.transformers = null;
    }

    private void buildTransformerDelegationList() {
        logger.debug("Rebuilding transformer delegation list:");
        this.transformers = new ArrayList();
        for (ITransformer transformer : this.service.getTransformers()) {
            if (transformer instanceof ILegacyClassTransformer) {
                ILegacyClassTransformer legacyTransformer = (ILegacyClassTransformer) transformer;
                String transformerName = legacyTransformer.getName();
                boolean include = true;
                Iterator<String> it = excludeTransformers.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    String excludeClass = it.next();
                    if (transformerName.contains(excludeClass)) {
                        include = false;
                        break;
                    }
                }
                if (include && !legacyTransformer.isDelegationExcluded()) {
                    logger.debug("  Adding:    {}", new Object[]{transformerName});
                    this.transformers.add(legacyTransformer);
                } else {
                    logger.debug("  Excluding: {}", new Object[]{transformerName});
                }
            }
        }
        logger.debug("Transformer delegation list created with {} entries", new Object[]{Integer.valueOf(this.transformers.size())});
    }

    public String toString() {
        return String.format("%s[%s]", getClass().getSimpleName(), this.phase);
    }

    private static Phase getCurrentPhase() {
        if (currentPhase == Phase.NOT_INITIALISED) {
            init(Phase.PREINIT);
        }
        return currentPhase;
    }

    public static void init(Phase phase) {
        if (currentPhase == Phase.NOT_INITIALISED) {
            currentPhase = phase;
            MixinEnvironment env = getEnvironment(phase);
            getProfiler().setActive(env.getOption(Option.DEBUG_PROFILER));
            MixinLogWatcher.begin();
        }
    }

    public static MixinEnvironment getEnvironment(Phase phase) {
        if (phase == null) {
            return Phase.DEFAULT.getEnvironment();
        }
        return phase.getEnvironment();
    }

    public static MixinEnvironment getDefaultEnvironment() {
        return getEnvironment(Phase.DEFAULT);
    }

    public static MixinEnvironment getCurrentEnvironment() {
        if (currentEnvironment == null) {
            currentEnvironment = getEnvironment(getCurrentPhase());
        }
        return currentEnvironment;
    }

    public static CompatibilityLevel getCompatibilityLevel() {
        return compatibility;
    }

    @Deprecated
    public static void setCompatibilityLevel(CompatibilityLevel level) throws IllegalArgumentException {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (!"org.spongepowered.asm.mixin.transformer.MixinConfig".equals(stackTrace[2].getClassName())) {
            logger.warn("MixinEnvironment::setCompatibilityLevel is deprecated and will be removed. Set level via config instead!");
        }
        if (level != compatibility && level.isAtLeast(compatibility)) {
            if (!level.isSupported()) {
                throw new IllegalArgumentException("The requested compatibility level " + level + " could not be set. Level is not supported");
            }
            compatibility = level;
            logger.info("Compatibility level set to {}", new Object[]{level});
        }
    }

    public static Profiler getProfiler() {
        return profiler;
    }

    public static void gotoPhase(Phase phase) {
        if (phase == null || phase.ordinal < 0) {
            throw new IllegalArgumentException("Cannot go to the specified phase, phase is null or invalid");
        }
        if (phase.ordinal > getCurrentPhase().ordinal) {
            MixinService.getService().beginPhase();
        }
        if (phase == Phase.DEFAULT) {
            MixinLogWatcher.end();
        }
        currentPhase = phase;
        currentEnvironment = getEnvironment(getCurrentPhase());
    }
}
