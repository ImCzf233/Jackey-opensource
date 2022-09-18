package org.spongepowered.asm.mixin.transformer;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinErrorHandler;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.ArgsClassGenerator;
import org.spongepowered.asm.mixin.throwables.ClassAlreadyLoadedException;
import org.spongepowered.asm.mixin.throwables.MixinApplyError;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.mixin.throwables.MixinPrepareError;
import org.spongepowered.asm.mixin.transformer.MixinConfig;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import org.spongepowered.asm.mixin.transformer.ext.IClassGenerator;
import org.spongepowered.asm.mixin.transformer.ext.IHotSwap;
import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionCheckClass;
import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionCheckInterfaces;
import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionClassExporter;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.ITransformer;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.transformers.TreeTransformer;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.ReEntranceLock;
import org.spongepowered.asm.util.perf.Profiler;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/transformer/MixinTransformer.class */
public class MixinTransformer extends TreeTransformer {
    private static final String MIXIN_AGENT_CLASS = "org.spongepowered.tools.agent.MixinAgent";
    private static final String METRONOME_AGENT_CLASS = "org.spongepowered.metronome.Agent";
    static final Logger logger = LogManager.getLogger("mixin");
    private final ReEntranceLock lock;
    private final Extensions extensions;
    private final IHotSwap hotSwapper;
    private final MixinPostProcessor postProcessor;
    private final Profiler profiler;
    private MixinEnvironment currentEnvironment;
    private final IMixinService service = MixinService.getService();
    private final List<MixinConfig> configs = new ArrayList();
    private final List<MixinConfig> pendingConfigs = new ArrayList();
    private final String sessionId = UUID.randomUUID().toString();
    private Level verboseLoggingLevel = Level.DEBUG;
    private boolean errorState = false;
    private int transformedCount = 0;

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/transformer/MixinTransformer$ErrorPhase.class */
    public enum ErrorPhase {
        PREPARE { // from class: org.spongepowered.asm.mixin.transformer.MixinTransformer.ErrorPhase.1
            @Override // org.spongepowered.asm.mixin.transformer.MixinTransformer.ErrorPhase
            IMixinErrorHandler.ErrorAction onError(IMixinErrorHandler handler, String context, InvalidMixinException ex, IMixinInfo mixin, IMixinErrorHandler.ErrorAction action) {
                try {
                    return handler.onPrepareError(mixin.getConfig(), ex, mixin, action);
                } catch (AbstractMethodError e) {
                    return action;
                }
            }

            @Override // org.spongepowered.asm.mixin.transformer.MixinTransformer.ErrorPhase
            protected String getContext(IMixinInfo mixin, String context) {
                return String.format("preparing %s in %s", mixin.getName(), context);
            }
        },
        APPLY { // from class: org.spongepowered.asm.mixin.transformer.MixinTransformer.ErrorPhase.2
            @Override // org.spongepowered.asm.mixin.transformer.MixinTransformer.ErrorPhase
            IMixinErrorHandler.ErrorAction onError(IMixinErrorHandler handler, String context, InvalidMixinException ex, IMixinInfo mixin, IMixinErrorHandler.ErrorAction action) {
                try {
                    return handler.onApplyError(context, ex, mixin, action);
                } catch (AbstractMethodError e) {
                    return action;
                }
            }

            @Override // org.spongepowered.asm.mixin.transformer.MixinTransformer.ErrorPhase
            protected String getContext(IMixinInfo mixin, String context) {
                return String.format("%s -> %s", mixin, context);
            }
        };
        
        private final String text;

        abstract IMixinErrorHandler.ErrorAction onError(IMixinErrorHandler iMixinErrorHandler, String str, InvalidMixinException invalidMixinException, IMixinInfo iMixinInfo, IMixinErrorHandler.ErrorAction errorAction);

        protected abstract String getContext(IMixinInfo iMixinInfo, String str);

        ErrorPhase() {
            this.text = name().toLowerCase();
        }

        public String getLogMessage(String context, InvalidMixinException ex, IMixinInfo mixin) {
            return String.format("Mixin %s failed %s: %s %s", this.text, getContext(mixin, context), ex.getClass().getName(), ex.getMessage());
        }

        public String getErrorMessage(IMixinInfo mixin, IMixinConfig config, MixinEnvironment.Phase phase) {
            return String.format("Mixin [%s] from phase [%s] in config [%s] FAILED during %s", mixin, phase, config, name());
        }
    }

    public MixinTransformer() {
        MixinEnvironment environment = MixinEnvironment.getCurrentEnvironment();
        Object globalMixinTransformer = environment.getActiveTransformer();
        if (globalMixinTransformer instanceof ITransformer) {
            throw new MixinException("Terminating MixinTransformer instance " + this);
        }
        environment.setActiveTransformer(this);
        this.lock = this.service.getReEntranceLock();
        this.extensions = new Extensions(this);
        this.hotSwapper = initHotSwapper(environment);
        this.postProcessor = new MixinPostProcessor();
        this.extensions.add(new ArgsClassGenerator());
        this.extensions.add(new InnerClassGenerator());
        this.extensions.add(new ExtensionClassExporter(environment));
        this.extensions.add(new ExtensionCheckClass());
        this.extensions.add(new ExtensionCheckInterfaces());
        this.profiler = MixinEnvironment.getProfiler();
    }

    private IHotSwap initHotSwapper(MixinEnvironment environment) {
        if (!environment.getOption(MixinEnvironment.Option.HOT_SWAP)) {
            return null;
        }
        try {
            logger.info("Attempting to load Hot-Swap agent");
            return (IHotSwap) Class.forName(MIXIN_AGENT_CLASS).getDeclaredConstructor(MixinTransformer.class).newInstance(this);
        } catch (Throwable th) {
            logger.info("Hot-swap agent could not be loaded, hot swapping of mixins won't work. {}: {}", new Object[]{th.getClass().getSimpleName(), th.getMessage()});
            return null;
        }
    }

    public void audit(MixinEnvironment environment) {
        Set<String> unhandled = new HashSet<>();
        for (MixinConfig config : this.configs) {
            unhandled.addAll(config.getUnhandledTargets());
        }
        Logger auditLogger = LogManager.getLogger("mixin/audit");
        for (String target : unhandled) {
            try {
                auditLogger.info("Force-loading class {}", new Object[]{target});
                this.service.getClassProvider().findClass(target, true);
            } catch (ClassNotFoundException ex) {
                auditLogger.error("Could not force-load " + target, ex);
            }
        }
        for (MixinConfig config2 : this.configs) {
            for (String target2 : config2.getUnhandledTargets()) {
                ClassAlreadyLoadedException ex2 = new ClassAlreadyLoadedException(target2 + " was already classloaded");
                auditLogger.error("Could not force-load " + target2, ex2);
            }
        }
        if (environment.getOption(MixinEnvironment.Option.DEBUG_PROFILER)) {
            printProfilerSummary();
        }
    }

    private void printProfilerSummary() {
        DecimalFormat threedp = new DecimalFormat("(###0.000");
        DecimalFormat onedp = new DecimalFormat("(###0.0");
        PrettyPrinter printer = this.profiler.printer(false, false);
        long prepareTime = this.profiler.get("mixin.prepare").getTotalTime();
        long readTime = this.profiler.get("mixin.read").getTotalTime();
        long applyTime = this.profiler.get("mixin.apply").getTotalTime();
        long writeTime = this.profiler.get("mixin.write").getTotalTime();
        long totalMixinTime = this.profiler.get("mixin").getTotalTime();
        long loadTime = this.profiler.get("class.load").getTotalTime();
        long transformTime = this.profiler.get("class.transform").getTotalTime();
        long exportTime = this.profiler.get("mixin.debug.export").getTotalTime();
        long actualTime = ((totalMixinTime - loadTime) - transformTime) - exportTime;
        double timeSliceMixin = (actualTime / totalMixinTime) * 100.0d;
        double timeSliceLoad = (loadTime / totalMixinTime) * 100.0d;
        double timeSliceTransform = (transformTime / totalMixinTime) * 100.0d;
        double timeSliceExport = (exportTime / totalMixinTime) * 100.0d;
        long worstTransformerTime = 0;
        Profiler.Section worstTransformer = null;
        for (Profiler.Section section : this.profiler.getSections()) {
            long transformerTime = section.getName().startsWith("class.transform.") ? section.getTotalTime() : 0L;
            if (transformerTime > worstTransformerTime) {
                worstTransformerTime = transformerTime;
                worstTransformer = section;
            }
        }
        printer.m11hr().add("Summary").m11hr().add();
        printer.m8kv("Total mixin time", "%9d ms %12s seconds)", Long.valueOf(totalMixinTime), threedp.format(totalMixinTime * 0.001d)).add();
        printer.m8kv("Preparing mixins", "%9d ms %12s seconds)", Long.valueOf(prepareTime), threedp.format(prepareTime * 0.001d));
        printer.m8kv("Reading input", "%9d ms %12s seconds)", Long.valueOf(readTime), threedp.format(readTime * 0.001d));
        printer.m8kv("Applying mixins", "%9d ms %12s seconds)", Long.valueOf(applyTime), threedp.format(applyTime * 0.001d));
        printer.m8kv("Writing output", "%9d ms %12s seconds)", Long.valueOf(writeTime), threedp.format(writeTime * 0.001d)).add();
        printer.m9kv("of which", "");
        printer.m8kv("Time spent loading from disk", "%9d ms %12s seconds)", Long.valueOf(loadTime), threedp.format(loadTime * 0.001d));
        printer.m8kv("Time spent transforming classes", "%9d ms %12s seconds)", Long.valueOf(transformTime), threedp.format(transformTime * 0.001d)).add();
        if (worstTransformer != null) {
            printer.m9kv("Worst transformer", worstTransformer.getName());
            printer.m9kv("Class", worstTransformer.getInfo());
            printer.m8kv("Time spent", "%s seconds", Double.valueOf(worstTransformer.getTotalSeconds()));
            printer.m8kv("called", "%d times", Integer.valueOf(worstTransformer.getTotalCount())).add();
        }
        printer.m8kv("   Time allocation:     Processing mixins", "%9d ms %10s%% of total)", Long.valueOf(actualTime), onedp.format(timeSliceMixin));
        printer.m8kv("Loading classes", "%9d ms %10s%% of total)", Long.valueOf(loadTime), onedp.format(timeSliceLoad));
        printer.m8kv("Running transformers", "%9d ms %10s%% of total)", Long.valueOf(transformTime), onedp.format(timeSliceTransform));
        if (exportTime > 0) {
            printer.m8kv("Exporting classes (debug)", "%9d ms %10s%% of total)", Long.valueOf(exportTime), onedp.format(timeSliceExport));
        }
        printer.add();
        try {
            Class<?> agent = this.service.getClassProvider().findAgentClass(METRONOME_AGENT_CLASS, false);
            Method mdGetTimes = agent.getDeclaredMethod("getTimes", new Class[0]);
            Map<String, Long> times = (Map) mdGetTimes.invoke(null, new Object[0]);
            printer.m11hr().add("Transformer Times").m11hr().add();
            int longest = 10;
            for (Map.Entry<String, Long> entry : times.entrySet()) {
                longest = Math.max(longest, entry.getKey().length());
            }
            for (Map.Entry<String, Long> entry2 : times.entrySet()) {
                String name = entry2.getKey();
                long mixinTime = 0;
                Iterator<Profiler.Section> it = this.profiler.getSections().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Profiler.Section section2 = it.next();
                    if (name.equals(section2.getInfo())) {
                        mixinTime = section2.getTotalTime();
                        break;
                    }
                }
                if (mixinTime > 0) {
                    printer.add("%-" + longest + "s %8s ms %8s ms in mixin)", name, Long.valueOf(entry2.getValue().longValue() + mixinTime), "(" + mixinTime);
                } else {
                    printer.add("%-" + longest + "s %8s ms", name, entry2.getValue());
                }
            }
            printer.add();
        } catch (Throwable th) {
        }
        printer.print();
    }

    @Override // org.spongepowered.asm.service.ILegacyClassTransformer
    public String getName() {
        return getClass().getName();
    }

    @Override // org.spongepowered.asm.service.ILegacyClassTransformer
    public boolean isDelegationExcluded() {
        return true;
    }

    /* JADX WARN: Finally extract failed */
    @Override // org.spongepowered.asm.service.ILegacyClassTransformer
    public synchronized byte[] transformClassBytes(String name, String transformedName, byte[] basicClass) {
        if (transformedName == null || this.errorState) {
            return basicClass;
        }
        MixinEnvironment environment = MixinEnvironment.getCurrentEnvironment();
        if (basicClass == null) {
            for (IClassGenerator generator : this.extensions.getGenerators()) {
                Profiler.Section genTimer = this.profiler.begin("generator", generator.getClass().getSimpleName().toLowerCase());
                basicClass = generator.generate(transformedName);
                genTimer.end();
                if (basicClass != null) {
                    this.extensions.export(environment, transformedName.replace('.', '/'), false, basicClass);
                    return basicClass;
                }
            }
            return basicClass;
        }
        boolean locked = this.lock.push().check();
        Profiler.Section mixinTimer = this.profiler.begin("mixin");
        if (!locked) {
            try {
                checkSelect(environment);
            } catch (Exception ex) {
                this.lock.pop();
                mixinTimer.end();
                throw new MixinException(ex);
            }
        }
        try {
            if (this.postProcessor.canTransform(transformedName)) {
                Profiler.Section postTimer = this.profiler.begin("postprocessor");
                byte[] bytes = this.postProcessor.transformClassBytes(name, transformedName, basicClass);
                postTimer.end();
                this.extensions.export(environment, transformedName, false, bytes);
                this.lock.pop();
                mixinTimer.end();
                return bytes;
            }
            SortedSet<MixinInfo> mixins = null;
            boolean invalidRef = false;
            for (MixinConfig config : this.configs) {
                if (config.packageMatch(transformedName)) {
                    invalidRef = true;
                } else if (config.hasMixinsFor(transformedName)) {
                    if (mixins == null) {
                        mixins = new TreeSet<>();
                    }
                    mixins.addAll(config.getMixinsFor(transformedName));
                }
            }
            if (invalidRef) {
                throw new NoClassDefFoundError(String.format("%s is a mixin class and cannot be referenced directly", transformedName));
            }
            if (mixins != null) {
                if (locked) {
                    logger.warn("Re-entrance detected, this will cause serious problems.", new MixinException());
                    throw new MixinApplyError("Re-entrance error.");
                }
                if (this.hotSwapper != null) {
                    this.hotSwapper.registerTargetClass(transformedName, basicClass);
                }
                try {
                    Profiler.Section timer = this.profiler.begin("read");
                    ClassNode targetClassNode = readClass(basicClass, true);
                    TargetClassContext context = new TargetClassContext(environment, this.extensions, this.sessionId, transformedName, targetClassNode, mixins);
                    timer.end();
                    basicClass = applyMixins(environment, context);
                    this.transformedCount++;
                } catch (InvalidMixinException th) {
                    dumpClassOnFailure(transformedName, basicClass, environment);
                    handleMixinApplyError(transformedName, th, environment);
                }
            }
            byte[] bArr = basicClass;
            this.lock.pop();
            mixinTimer.end();
            return bArr;
        } finally {
        }
    }

    public List<String> reload(String mixinClass, byte[] bytes) {
        if (this.lock.getDepth() > 0) {
            throw new MixinApplyError("Cannot reload mixin if re-entrant lock entered");
        }
        List<String> targets = new ArrayList<>();
        for (MixinConfig config : this.configs) {
            targets.addAll(config.reloadMixin(mixinClass, bytes));
        }
        return targets;
    }

    private void checkSelect(MixinEnvironment environment) {
        if (this.currentEnvironment != environment) {
            select(environment);
            return;
        }
        int unvisitedCount = Mixins.getUnvisitedCount();
        if (unvisitedCount > 0 && this.transformedCount == 0) {
            select(environment);
        }
    }

    private void select(MixinEnvironment environment) {
        this.verboseLoggingLevel = environment.getOption(MixinEnvironment.Option.DEBUG_VERBOSE) ? Level.INFO : Level.DEBUG;
        if (this.transformedCount > 0) {
            logger.log(this.verboseLoggingLevel, "Ending {}, applied {} mixins", new Object[]{this.currentEnvironment, Integer.valueOf(this.transformedCount)});
        }
        String action = this.currentEnvironment == environment ? "Checking for additional" : "Preparing";
        logger.log(this.verboseLoggingLevel, "{} mixins for {}", new Object[]{action, environment});
        this.profiler.setActive(true);
        this.profiler.mark(environment.getPhase().toString() + ":prepare");
        Profiler.Section prepareTimer = this.profiler.begin("prepare");
        selectConfigs(environment);
        this.extensions.select(environment);
        int totalMixins = prepareConfigs(environment);
        this.currentEnvironment = environment;
        this.transformedCount = 0;
        prepareTimer.end();
        long elapsedMs = prepareTimer.getTime();
        double elapsedTime = prepareTimer.getSeconds();
        if (elapsedTime > 0.25d) {
            long loadTime = this.profiler.get("class.load").getTime();
            long transformTime = this.profiler.get("class.transform").getTime();
            long pluginTime = this.profiler.get("mixin.plugin").getTime();
            String elapsed = new DecimalFormat("###0.000").format(elapsedTime);
            String perMixinTime = new DecimalFormat("###0.0").format(elapsedMs / totalMixins);
            logger.log(this.verboseLoggingLevel, "Prepared {} mixins in {} sec ({}ms avg) ({}ms load, {}ms transform, {}ms plugin)", new Object[]{Integer.valueOf(totalMixins), elapsed, perMixinTime, Long.valueOf(loadTime), Long.valueOf(transformTime), Long.valueOf(pluginTime)});
        }
        this.profiler.mark(environment.getPhase().toString() + ":apply");
        this.profiler.setActive(environment.getOption(MixinEnvironment.Option.DEBUG_PROFILER));
    }

    private void selectConfigs(MixinEnvironment environment) {
        Iterator<Config> iter = Mixins.getConfigs().iterator();
        while (iter.hasNext()) {
            Config handle = iter.next();
            try {
                MixinConfig config = handle.get();
                if (config.select(environment)) {
                    iter.remove();
                    logger.log(this.verboseLoggingLevel, "Selecting config {}", new Object[]{config});
                    config.onSelect();
                    this.pendingConfigs.add(config);
                }
            } catch (Exception ex) {
                logger.warn(String.format("Failed to select mixin config: %s", handle), ex);
            }
        }
        Collections.sort(this.pendingConfigs);
    }

    private int prepareConfigs(MixinEnvironment environment) {
        int totalMixins = 0;
        final IHotSwap hotSwapper = this.hotSwapper;
        for (MixinConfig config : this.pendingConfigs) {
            config.addListener(this.postProcessor);
            if (hotSwapper != null) {
                config.addListener(new MixinConfig.IListener() { // from class: org.spongepowered.asm.mixin.transformer.MixinTransformer.1
                    @Override // org.spongepowered.asm.mixin.transformer.MixinConfig.IListener
                    public void onPrepare(MixinInfo mixin) {
                        hotSwapper.registerMixinClass(mixin.getClassName());
                    }

                    @Override // org.spongepowered.asm.mixin.transformer.MixinConfig.IListener
                    public void onInit(MixinInfo mixin) {
                    }
                });
            }
        }
        for (MixinConfig config2 : this.pendingConfigs) {
            try {
                logger.log(this.verboseLoggingLevel, "Preparing {} ({})", new Object[]{config2, Integer.valueOf(config2.getDeclaredMixinCount())});
                config2.prepare();
                totalMixins += config2.getMixinCount();
            } catch (InvalidMixinException ex) {
                handleMixinPrepareError(config2, ex, environment);
            } catch (Exception ex2) {
                String message = ex2.getMessage();
                logger.error("Error encountered whilst initialising mixin config '" + config2.getName() + "': " + message, ex2);
            }
        }
        for (MixinConfig config3 : this.pendingConfigs) {
            IMixinConfigPlugin plugin = config3.getPlugin();
            if (plugin != null) {
                Set<String> otherTargets = new HashSet<>();
                for (MixinConfig otherConfig : this.pendingConfigs) {
                    if (!otherConfig.equals(config3)) {
                        otherTargets.addAll(otherConfig.getTargets());
                    }
                }
                plugin.acceptTargets(config3.getTargets(), Collections.unmodifiableSet(otherTargets));
            }
        }
        for (MixinConfig config4 : this.pendingConfigs) {
            try {
                config4.postInitialise();
            } catch (InvalidMixinException ex3) {
                handleMixinPrepareError(config4, ex3, environment);
            } catch (Exception ex4) {
                String message2 = ex4.getMessage();
                logger.error("Error encountered during mixin config postInit step'" + config4.getName() + "': " + message2, ex4);
            }
        }
        this.configs.addAll(this.pendingConfigs);
        Collections.sort(this.configs);
        this.pendingConfigs.clear();
        return totalMixins;
    }

    private byte[] applyMixins(MixinEnvironment environment, TargetClassContext context) {
        Profiler.Section timer = this.profiler.begin("preapply");
        this.extensions.preApply(context);
        Profiler.Section timer2 = timer.next("apply");
        apply(context);
        Profiler.Section timer3 = timer2.next("postapply");
        try {
            this.extensions.postApply(context);
        } catch (ExtensionCheckClass.ValidationFailedException ex) {
            logger.info(ex.getMessage());
            if (context.isExportForced() || environment.getOption(MixinEnvironment.Option.DEBUG_EXPORT)) {
                writeClass(context);
            }
        }
        timer3.end();
        return writeClass(context);
    }

    private void apply(TargetClassContext context) {
        context.applyMixins();
    }

    private void handleMixinPrepareError(MixinConfig config, InvalidMixinException ex, MixinEnvironment environment) throws MixinPrepareError {
        handleMixinError(config.getName(), ex, environment, ErrorPhase.PREPARE);
    }

    private void handleMixinApplyError(String targetClass, InvalidMixinException ex, MixinEnvironment environment) throws MixinApplyError {
        handleMixinError(targetClass, ex, environment, ErrorPhase.APPLY);
    }

    private void handleMixinError(String context, InvalidMixinException ex, MixinEnvironment environment, ErrorPhase errorPhase) throws Error {
        this.errorState = true;
        IMixinInfo mixin = ex.getMixin();
        if (mixin == null) {
            logger.error("InvalidMixinException has no mixin!", ex);
            throw ex;
        }
        IMixinConfig config = mixin.getConfig();
        MixinEnvironment.Phase phase = mixin.getPhase();
        IMixinErrorHandler.ErrorAction action = config.isRequired() ? IMixinErrorHandler.ErrorAction.ERROR : IMixinErrorHandler.ErrorAction.WARN;
        if (environment.getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
            new PrettyPrinter().add("Invalid Mixin").centre().m10hr('-').kvWidth(10).m9kv("Action", errorPhase.name()).m9kv("Mixin", mixin.getClassName()).m9kv("Config", config.getName()).m9kv("Phase", phase).m10hr('-').add("    %s", ex.getClass().getName()).m10hr('-').addWrapped("    %s", ex.getMessage()).m10hr('-').add((Throwable) ex, 8).trace(action.logLevel);
        }
        for (IMixinErrorHandler handler : getErrorHandlers(mixin.getPhase())) {
            IMixinErrorHandler.ErrorAction newAction = errorPhase.onError(handler, context, ex, mixin, action);
            if (newAction != null) {
                action = newAction;
            }
        }
        logger.log(action.logLevel, errorPhase.getLogMessage(context, ex, mixin), ex);
        this.errorState = false;
        if (action == IMixinErrorHandler.ErrorAction.ERROR) {
            throw new MixinApplyError(errorPhase.getErrorMessage(mixin, config, phase), ex);
        }
    }

    private List<IMixinErrorHandler> getErrorHandlers(MixinEnvironment.Phase phase) {
        List<IMixinErrorHandler> handlers = new ArrayList<>();
        for (String handlerClassName : Mixins.getErrorHandlerClasses()) {
            try {
                logger.info("Instancing error handler class {}", new Object[]{handlerClassName});
                Class<?> handlerClass = this.service.getClassProvider().findClass(handlerClassName, true);
                IMixinErrorHandler handler = (IMixinErrorHandler) handlerClass.newInstance();
                if (handler != null) {
                    handlers.add(handler);
                }
            } catch (Throwable th) {
            }
        }
        return handlers;
    }

    private byte[] writeClass(TargetClassContext context) {
        return writeClass(context.getClassName(), context.getClassNode(), context.isExportForced());
    }

    private byte[] writeClass(String transformedName, ClassNode targetClass, boolean forceExport) {
        Profiler.Section writeTimer = this.profiler.begin("write");
        byte[] bytes = writeClass(targetClass);
        writeTimer.end();
        this.extensions.export(this.currentEnvironment, transformedName, forceExport, bytes);
        return bytes;
    }

    private void dumpClassOnFailure(String className, byte[] bytes, MixinEnvironment env) {
        if (env.getOption(MixinEnvironment.Option.DUMP_TARGET_ON_FAILURE)) {
            ExtensionClassExporter exporter = (ExtensionClassExporter) this.extensions.getExtension(ExtensionClassExporter.class);
            exporter.dumpClass(className.replace('.', '/') + ".target", bytes);
        }
    }
}
