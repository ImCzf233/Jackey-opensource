package org.spongepowered.asm.service.mojang;

import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.launchwrapper.IClassNameTransformer;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.GlobalProperties;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.service.IClassBytecodeProvider;
import org.spongepowered.asm.service.IClassProvider;
import org.spongepowered.asm.service.ILegacyClassTransformer;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.ITransformer;
import org.spongepowered.asm.util.ReEntranceLock;
import org.spongepowered.asm.util.perf.Profiler;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/service/mojang/MixinServiceLaunchWrapper.class */
public class MixinServiceLaunchWrapper implements IMixinService, IClassProvider, IClassBytecodeProvider {
    public static final String BLACKBOARD_KEY_TWEAKCLASSES = "TweakClasses";
    public static final String BLACKBOARD_KEY_TWEAKS = "Tweaks";
    private static final String LAUNCH_PACKAGE = "org.spongepowered.asm.launch.";
    private static final String MIXIN_PACKAGE = "org.spongepowered.asm.mixin.";
    private static final String STATE_TWEAKER = "org.spongepowered.asm.mixin.EnvironmentStateTweaker";
    private static final String TRANSFORMER_PROXY_CLASS = "org.spongepowered.asm.mixin.transformer.Proxy";
    private static final Logger logger = LogManager.getLogger("mixin");
    private final LaunchClassLoaderUtil classLoaderUtil = new LaunchClassLoaderUtil(Launch.classLoader);
    private final ReEntranceLock lock = new ReEntranceLock(1);
    private IClassNameTransformer nameTransformer;

    @Override // org.spongepowered.asm.service.IMixinService
    public String getName() {
        return "LaunchWrapper";
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public boolean isValid() {
        try {
            Launch.classLoader.hashCode();
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public void prepare() {
        Launch.classLoader.addClassLoaderExclusion(LAUNCH_PACKAGE);
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public MixinEnvironment.Phase getInitialPhase() {
        if (findInStackTrace("net.minecraft.launchwrapper.Launch", "launch") > 132) {
            return MixinEnvironment.Phase.DEFAULT;
        }
        return MixinEnvironment.Phase.PREINIT;
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public void init() {
        if (findInStackTrace("net.minecraft.launchwrapper.Launch", "launch") < 4) {
            logger.error("MixinBootstrap.doInit() called during a tweak constructor!");
        }
        List<String> tweakClasses = (List) GlobalProperties.get(BLACKBOARD_KEY_TWEAKCLASSES);
        if (tweakClasses != null) {
            tweakClasses.add(STATE_TWEAKER);
        }
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public ReEntranceLock getReEntranceLock() {
        return this.lock;
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public Collection<String> getPlatformAgents() {
        return ImmutableList.of("org.spongepowered.asm.launch.platform.MixinPlatformAgentFML");
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public IClassProvider getClassProvider() {
        return this;
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public IClassBytecodeProvider getBytecodeProvider() {
        return this;
    }

    @Override // org.spongepowered.asm.service.IClassProvider
    public Class<?> findClass(String name) throws ClassNotFoundException {
        return Launch.classLoader.findClass(name);
    }

    @Override // org.spongepowered.asm.service.IClassProvider
    public Class<?> findClass(String name, boolean initialize) throws ClassNotFoundException {
        return Class.forName(name, initialize, Launch.classLoader);
    }

    @Override // org.spongepowered.asm.service.IClassProvider
    public Class<?> findAgentClass(String name, boolean initialize) throws ClassNotFoundException {
        return Class.forName(name, initialize, Launch.class.getClassLoader());
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public void beginPhase() {
        Launch.classLoader.registerTransformer(TRANSFORMER_PROXY_CLASS);
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public void checkEnv(Object bootSource) {
        if (bootSource.getClass().getClassLoader() != Launch.class.getClassLoader()) {
            throw new MixinException("Attempted to init the mixin environment in the wrong classloader");
        }
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public InputStream getResourceAsStream(String name) {
        return Launch.classLoader.getResourceAsStream(name);
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public void registerInvalidClass(String className) {
        this.classLoaderUtil.registerInvalidClass(className);
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public boolean isClassLoaded(String className) {
        return this.classLoaderUtil.isClassLoaded(className);
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public String getClassRestrictions(String className) {
        String restrictions = "";
        if (this.classLoaderUtil.isClassClassLoaderExcluded(className, null)) {
            restrictions = "PACKAGE_CLASSLOADER_EXCLUSION";
        }
        if (this.classLoaderUtil.isClassTransformerExcluded(className, null)) {
            restrictions = (restrictions.length() > 0 ? restrictions + "," : "") + "PACKAGE_TRANSFORMER_EXCLUSION";
        }
        return restrictions;
    }

    @Override // org.spongepowered.asm.service.IClassProvider
    public URL[] getClassPath() {
        return (URL[]) Launch.classLoader.getSources().toArray(new URL[0]);
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public Collection<ITransformer> getTransformers() {
        List<IClassTransformer> transformers = Launch.classLoader.getTransformers();
        List<ITransformer> wrapped = new ArrayList<>(transformers.size());
        for (IClassTransformer iClassTransformer : transformers) {
            if (iClassTransformer instanceof ITransformer) {
                wrapped.add((ITransformer) iClassTransformer);
            } else {
                wrapped.add(new LegacyTransformerHandle(iClassTransformer));
            }
            if (iClassTransformer instanceof IClassNameTransformer) {
                logger.debug("Found name transformer: {}", new Object[]{iClassTransformer.getClass().getName()});
                this.nameTransformer = (IClassNameTransformer) iClassTransformer;
            }
        }
        return wrapped;
    }

    @Override // org.spongepowered.asm.service.IClassBytecodeProvider
    public byte[] getClassBytes(String name, String transformedName) throws IOException {
        byte[] classBytes = Launch.classLoader.getClassBytes(name);
        if (classBytes != null) {
            return classBytes;
        }
        URLClassLoader appClassLoader = (URLClassLoader) Launch.class.getClassLoader();
        InputStream classStream = null;
        try {
            String resourcePath = transformedName.replace('.', '/').concat(".class");
            classStream = appClassLoader.getResourceAsStream(resourcePath);
            byte[] byteArray = IOUtils.toByteArray(classStream);
            IOUtils.closeQuietly(classStream);
            return byteArray;
        } catch (Exception e) {
            IOUtils.closeQuietly(classStream);
            return null;
        } catch (Throwable th) {
            IOUtils.closeQuietly(classStream);
            throw th;
        }
    }

    @Override // org.spongepowered.asm.service.IClassBytecodeProvider
    public byte[] getClassBytes(String className, boolean runTransformers) throws ClassNotFoundException, IOException {
        String transformedName = className.replace('/', '.');
        String name = unmapClassName(transformedName);
        Profiler profiler = MixinEnvironment.getProfiler();
        Profiler.Section loadTime = profiler.begin(1, "class.load");
        byte[] classBytes = getClassBytes(name, transformedName);
        loadTime.end();
        if (runTransformers) {
            Profiler.Section transformTime = profiler.begin(1, "class.transform");
            classBytes = applyTransformers(name, transformedName, classBytes, profiler);
            transformTime.end();
        }
        if (classBytes == null) {
            throw new ClassNotFoundException(String.format("The specified class '%s' was not found", transformedName));
        }
        return classBytes;
    }

    private byte[] applyTransformers(String name, String transformedName, byte[] basicClass, Profiler profiler) {
        if (this.classLoaderUtil.isClassExcluded(name, transformedName)) {
            return basicClass;
        }
        MixinEnvironment environment = MixinEnvironment.getCurrentEnvironment();
        for (ILegacyClassTransformer transformer : environment.getTransformers()) {
            this.lock.clear();
            int pos = transformer.getName().lastIndexOf(46);
            String simpleName = transformer.getName().substring(pos + 1);
            Profiler.Section transformTime = profiler.begin(2, simpleName.toLowerCase());
            transformTime.setInfo(transformer.getName());
            basicClass = transformer.transformClassBytes(name, transformedName, basicClass);
            transformTime.end();
            if (this.lock.isSet()) {
                environment.addTransformerExclusion(transformer.getName());
                this.lock.clear();
                logger.info("A re-entrant transformer '{}' was detected and will no longer process meta class data", new Object[]{transformer.getName()});
            }
        }
        return basicClass;
    }

    private String unmapClassName(String className) {
        if (this.nameTransformer == null) {
            findNameTransformer();
        }
        if (this.nameTransformer != null) {
            return this.nameTransformer.unmapClassName(className);
        }
        return className;
    }

    private void findNameTransformer() {
        List<IClassTransformer> transformers = Launch.classLoader.getTransformers();
        for (IClassTransformer iClassTransformer : transformers) {
            if (iClassTransformer instanceof IClassNameTransformer) {
                logger.debug("Found name transformer: {}", new Object[]{iClassTransformer.getClass().getName()});
                this.nameTransformer = (IClassNameTransformer) iClassTransformer;
            }
        }
    }

    @Override // org.spongepowered.asm.service.IClassBytecodeProvider
    public ClassNode getClassNode(String className) throws ClassNotFoundException, IOException {
        return getClassNode(getClassBytes(className, true), 0);
    }

    private ClassNode getClassNode(byte[] classBytes, int flags) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(classBytes);
        classReader.accept(classNode, flags);
        return classNode;
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public final String getSideName() {
        for (ITweaker tweaker : (List) GlobalProperties.get(BLACKBOARD_KEY_TWEAKS)) {
            if (tweaker.getClass().getName().endsWith(".common.launcher.FMLServerTweaker")) {
                return "SERVER";
            }
            if (tweaker.getClass().getName().endsWith(".common.launcher.FMLTweaker")) {
                return "CLIENT";
            }
        }
        String name = getSideName("net.minecraftforge.fml.relauncher.FMLLaunchHandler", "side");
        if (name != null) {
            return name;
        }
        String name2 = getSideName("cpw.mods.fml.relauncher.FMLLaunchHandler", "side");
        if (name2 != null) {
            return name2;
        }
        String name3 = getSideName("com.mumfrey.liteloader.launch.LiteLoaderTweaker", "getEnvironmentType");
        if (name3 != null) {
            return name3;
        }
        return "UNKNOWN";
    }

    private String getSideName(String className, String methodName) {
        try {
            Class<?> clazz = Class.forName(className, false, Launch.classLoader);
            Method method = clazz.getDeclaredMethod(methodName, new Class[0]);
            return ((Enum) method.invoke(null, new Object[0])).name();
        } catch (Exception e) {
            return null;
        }
    }

    private static int findInStackTrace(String className, String methodName) {
        Thread currentThread = Thread.currentThread();
        if (!"main".equals(currentThread.getName())) {
            return 0;
        }
        StackTraceElement[] stackTrace = currentThread.getStackTrace();
        for (StackTraceElement s : stackTrace) {
            if (className.equals(s.getClassName()) && methodName.equals(s.getMethodName())) {
                return s.getLineNumber();
            }
        }
        return 0;
    }
}
