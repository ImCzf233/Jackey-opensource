package jdk.nashorn.internal.runtime;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import javax.script.ScriptEngine;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.util.CheckClassAdapter;
import jdk.nashorn.api.scripting.ClassFilter;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.codegen.Compiler;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.lookup.MethodHandleFactory;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.debug.ASTWriter;
import jdk.nashorn.internal.p001ir.debug.PrintVisitor;
import jdk.nashorn.internal.parser.Parser;
import jdk.nashorn.internal.runtime.events.RuntimeEvent;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;
import jdk.nashorn.internal.runtime.options.LoggingOption;
import jdk.nashorn.internal.runtime.options.Options;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/Context.class */
public final class Context {
    public static final String NASHORN_SET_CONFIG = "nashorn.setConfig";
    public static final String NASHORN_CREATE_CONTEXT = "nashorn.createContext";
    public static final String NASHORN_CREATE_GLOBAL = "nashorn.createGlobal";
    public static final String NASHORN_GET_CONTEXT = "nashorn.getContext";
    public static final String NASHORN_JAVA_REFLECTION = "nashorn.JavaReflection";
    public static final String NASHORN_DEBUG_MODE = "nashorn.debugMode";
    private static final String LOAD_CLASSPATH = "classpath:";
    private static final String LOAD_FX = "fx:";
    private static final String LOAD_NASHORN = "nashorn:";
    private static MethodHandles.Lookup LOOKUP;
    private static MethodType CREATE_PROGRAM_FUNCTION_TYPE;
    private final FieldMode fieldMode;
    private final Map<String, SwitchPoint> builtinSwitchPoints;
    public static final boolean DEBUG;
    private static final ThreadLocal<Global> currentGlobal;
    private ClassCache classCache;
    private CodeStore codeStore;
    private final AtomicReference<GlobalConstants> globalConstantsRef;
    private final ScriptEnvironment env;
    final boolean _strict;
    private final ClassLoader appLoader;
    private final ScriptLoader scriptLoader;
    private final ErrorManager errors;
    private final AtomicLong uniqueScriptId;
    private final ClassFilter classFilter;
    private static final ClassLoader myLoader;
    private static final StructureLoader theStructLoader;
    private static final AccessControlContext NO_PERMISSIONS_ACC_CTXT;
    private static final AccessControlContext CREATE_LOADER_ACC_CTXT;
    private static final AccessControlContext CREATE_GLOBAL_ACC_CTXT;
    private final Map<String, DebugLogger> loggers;
    static final /* synthetic */ boolean $assertionsDisabled;

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/Context$BuiltinSwitchPoint.class */
    public static final class BuiltinSwitchPoint extends SwitchPoint {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/Context$FieldMode.class */
    public enum FieldMode {
        AUTO,
        OBJECTS,
        DUAL
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/Context$MultiGlobalCompiledScript.class */
    public interface MultiGlobalCompiledScript {
        ScriptFunction getFunction(Global global);
    }

    static {
        $assertionsDisabled = !Context.class.desiredAssertionStatus();
        LOOKUP = MethodHandles.lookup();
        CREATE_PROGRAM_FUNCTION_TYPE = MethodType.methodType(ScriptFunction.class, ScriptObject.class);
        DebuggerSupport.FORCELOAD = true;
        DEBUG = Options.getBooleanProperty("nashorn.debug");
        currentGlobal = new ThreadLocal<>();
        myLoader = Context.class.getClassLoader();
        NO_PERMISSIONS_ACC_CTXT = createNoPermAccCtxt();
        CREATE_LOADER_ACC_CTXT = createPermAccCtxt("createClassLoader");
        CREATE_GLOBAL_ACC_CTXT = createPermAccCtxt(NASHORN_CREATE_GLOBAL);
        theStructLoader = (StructureLoader) AccessController.doPrivileged(new PrivilegedAction<StructureLoader>() { // from class: jdk.nashorn.internal.runtime.Context.1
            @Override // java.security.PrivilegedAction
            public StructureLoader run() {
                return new StructureLoader(Context.myLoader);
            }
        }, CREATE_LOADER_ACC_CTXT);
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/Context$ContextCodeInstaller.class */
    public static class ContextCodeInstaller implements CodeInstaller {
        private final Context context;
        private final ScriptLoader loader;
        private final CodeSource codeSource;
        private int usageCount;
        private int bytesDefined;
        private static final int MAX_USAGES = 10;
        private static final int MAX_BYTES_DEFINED = 200000;

        private ContextCodeInstaller(Context context, ScriptLoader loader, CodeSource codeSource) {
            this.usageCount = 0;
            this.bytesDefined = 0;
            this.context = context;
            this.loader = loader;
            this.codeSource = codeSource;
        }

        @Override // jdk.nashorn.internal.runtime.CodeInstaller
        public Context getContext() {
            return this.context;
        }

        @Override // jdk.nashorn.internal.runtime.CodeInstaller
        public Class<?> install(String className, byte[] bytecode) {
            this.usageCount++;
            this.bytesDefined += bytecode.length;
            String binaryName = Compiler.binaryName(className);
            return this.loader.installClass(binaryName, bytecode, this.codeSource);
        }

        @Override // jdk.nashorn.internal.runtime.CodeInstaller
        public void initialize(final Collection<Class<?>> classes, final Source source, final Object[] constants) {
            try {
                AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() { // from class: jdk.nashorn.internal.runtime.Context.ContextCodeInstaller.1
                    @Override // java.security.PrivilegedExceptionAction
                    public Void run() throws Exception {
                        for (Class<?> clazz : classes) {
                            Field sourceField = clazz.getDeclaredField(CompilerConstants.SOURCE.symbolName());
                            sourceField.setAccessible(true);
                            sourceField.set(null, source);
                            Field constantsField = clazz.getDeclaredField(CompilerConstants.CONSTANTS.symbolName());
                            constantsField.setAccessible(true);
                            constantsField.set(null, constants);
                        }
                        return null;
                    }
                });
            } catch (PrivilegedActionException e) {
                throw new RuntimeException(e);
            }
        }

        @Override // jdk.nashorn.internal.runtime.CodeInstaller
        public void verify(byte[] code) {
            this.context.verify(code);
        }

        @Override // jdk.nashorn.internal.runtime.CodeInstaller
        public long getUniqueScriptId() {
            return this.context.getUniqueScriptId();
        }

        @Override // jdk.nashorn.internal.runtime.CodeInstaller
        public void storeScript(String cacheKey, Source source, String mainClassName, Map<String, byte[]> classBytes, Map<Integer, FunctionInitializer> initializers, Object[] constants, int compilationId) {
            if (this.context.codeStore != null) {
                this.context.codeStore.store(cacheKey, source, mainClassName, classBytes, initializers, constants, compilationId);
            }
        }

        @Override // jdk.nashorn.internal.runtime.CodeInstaller
        public StoredScript loadScript(Source source, String functionKey) {
            if (this.context.codeStore != null) {
                return this.context.codeStore.load(source, functionKey);
            }
            return null;
        }

        @Override // jdk.nashorn.internal.runtime.CodeInstaller
        public CodeInstaller withNewLoader() {
            if (this.usageCount < 10 && this.bytesDefined < MAX_BYTES_DEFINED) {
                return this;
            }
            return new ContextCodeInstaller(this.context, this.context.createNewLoader(), this.codeSource);
        }

        @Override // jdk.nashorn.internal.runtime.CodeInstaller
        public boolean isCompatibleWith(CodeInstaller other) {
            if (other instanceof ContextCodeInstaller) {
                ContextCodeInstaller cci = (ContextCodeInstaller) other;
                return cci.context == this.context && cci.codeSource == this.codeSource;
            }
            return false;
        }
    }

    public static Global getGlobal() {
        return currentGlobal.get();
    }

    public static void setGlobal(ScriptObject global) {
        if (global != null && !(global instanceof Global)) {
            throw new IllegalArgumentException("not a global!");
        }
        setGlobal((Global) global);
    }

    public static void setGlobal(Global global) {
        GlobalConstants globalConstants;
        if ($assertionsDisabled || getGlobal() != global) {
            if (global != null && (globalConstants = getContext(global).getGlobalConstants()) != null) {
                globalConstants.invalidateAll();
            }
            currentGlobal.set(global);
            return;
        }
        throw new AssertionError();
    }

    public static Context getContext() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new RuntimePermission(NASHORN_GET_CONTEXT));
        }
        return getContextTrusted();
    }

    public static PrintWriter getCurrentErr() {
        ScriptObject global = getGlobal();
        return global != null ? global.getContext().getErr() : new PrintWriter(System.err);
    }

    public static void err(String str) {
        err(str, true);
    }

    public static void err(String str, boolean crlf) {
        PrintWriter err = getCurrentErr();
        if (err != null) {
            if (crlf) {
                err.println(str);
            } else {
                err.print(str);
            }
        }
    }

    public ClassLoader getAppLoader() {
        return this.appLoader;
    }

    public ClassLoader getStructLoader() {
        return theStructLoader;
    }

    private static AccessControlContext createNoPermAccCtxt() {
        return new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain(null, new Permissions())});
    }

    private static AccessControlContext createPermAccCtxt(String permName) {
        Permissions perms = new Permissions();
        perms.add(new RuntimePermission(permName));
        return new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain(null, perms)});
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/Context$ThrowErrorManager.class */
    public static class ThrowErrorManager extends ErrorManager {
        @Override // jdk.nashorn.internal.runtime.ErrorManager
        public void error(String message) {
            throw new ParserException(message);
        }

        @Override // jdk.nashorn.internal.runtime.ErrorManager
        public void error(ParserException e) {
            throw e;
        }
    }

    public Context(Options options, ErrorManager errors, ClassLoader appLoader) {
        this(options, errors, appLoader, null);
    }

    public Context(Options options, ErrorManager errors, ClassLoader appLoader, ClassFilter classFilter) {
        this(options, errors, new PrintWriter((OutputStream) System.out, true), new PrintWriter((OutputStream) System.err, true), appLoader, classFilter);
    }

    public Context(Options options, ErrorManager errors, PrintWriter out, PrintWriter err, ClassLoader appLoader) {
        this(options, errors, out, err, appLoader, null);
    }

    public Context(Options options, ErrorManager errors, PrintWriter out, PrintWriter err, ClassLoader appLoader, ClassFilter classFilter) {
        this.builtinSwitchPoints = new HashMap();
        this.globalConstantsRef = new AtomicReference<>();
        this.loggers = new HashMap();
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new RuntimePermission(NASHORN_CREATE_CONTEXT));
        }
        this.classFilter = classFilter;
        this.env = new ScriptEnvironment(options, out, err);
        this._strict = this.env._strict;
        if (this.env._loader_per_compile) {
            this.scriptLoader = null;
            this.uniqueScriptId = null;
        } else {
            this.scriptLoader = createNewLoader();
            this.uniqueScriptId = new AtomicLong();
        }
        this.errors = errors;
        String classPath = options.getString("classpath");
        if (!this.env._compile_only && classPath != null && !classPath.isEmpty()) {
            if (sm != null) {
                sm.checkCreateClassLoader();
            }
            this.appLoader = NashornLoader.createClassLoader(classPath, appLoader);
        } else {
            this.appLoader = appLoader;
        }
        int cacheSize = this.env._class_cache_size;
        if (cacheSize > 0) {
            this.classCache = new ClassCache(cacheSize);
        }
        if (this.env._persistent_cache) {
            this.codeStore = CodeStore.newCodeStore(this);
        }
        if (this.env._version) {
            getErr().println("nashorn " + Version.version());
        }
        if (this.env._fullversion) {
            getErr().println("nashorn full version " + Version.fullVersion());
        }
        if (Options.getBooleanProperty("nashorn.fields.dual")) {
            this.fieldMode = FieldMode.DUAL;
        } else if (Options.getBooleanProperty("nashorn.fields.objects")) {
            this.fieldMode = FieldMode.OBJECTS;
        } else {
            this.fieldMode = FieldMode.AUTO;
        }
        initLoggers();
    }

    public ClassFilter getClassFilter() {
        return this.classFilter;
    }

    public GlobalConstants getGlobalConstants() {
        return this.globalConstantsRef.get();
    }

    public ErrorManager getErrorManager() {
        return this.errors;
    }

    public ScriptEnvironment getEnv() {
        return this.env;
    }

    public PrintWriter getOut() {
        return this.env.getOut();
    }

    public PrintWriter getErr() {
        return this.env.getErr();
    }

    public boolean useDualFields() {
        return this.fieldMode == FieldMode.DUAL || (this.fieldMode == FieldMode.AUTO && this.env._optimistic_types);
    }

    public static PropertyMap getGlobalMap() {
        return getGlobal().getMap();
    }

    public ScriptFunction compileScript(Source source, ScriptObject scope) {
        return compileScript(source, scope, this.errors);
    }

    public MultiGlobalCompiledScript compileScript(Source source) {
        Class<?> clazz = compile(source, this.errors, this._strict);
        final MethodHandle createProgramFunctionHandle = getCreateProgramFunctionHandle(clazz);
        return new MultiGlobalCompiledScript() { // from class: jdk.nashorn.internal.runtime.Context.2
            @Override // jdk.nashorn.internal.runtime.Context.MultiGlobalCompiledScript
            public ScriptFunction getFunction(Global newGlobal) {
                return Context.invokeCreateProgramFunctionHandle(createProgramFunctionHandle, newGlobal);
            }
        };
    }

    public Object eval(ScriptObject initialScope, String string, Object callThis, Object location) {
        return eval(initialScope, string, callThis, location, false, false);
    }

    public Object eval(ScriptObject initialScope, String string, Object callThis, Object location, boolean strict, boolean evalCall) {
        Object evalThis;
        String file = (location == ScriptRuntime.UNDEFINED || location == null) ? "<eval>" : location.toString();
        Source source = Source.sourceFor(file, string, evalCall);
        boolean directEval = evalCall && location != ScriptRuntime.UNDEFINED;
        Global global = getGlobal();
        ScriptObject scope = initialScope;
        boolean strictFlag = strict || this._strict;
        try {
            Class<?> clazz = compile(source, new ThrowErrorManager(), strictFlag);
            if (!strictFlag) {
                try {
                    strictFlag = clazz.getField(CompilerConstants.STRICT_MODE.symbolName()).getBoolean(null);
                } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
                    strictFlag = false;
                }
            }
            if (strictFlag) {
                scope = newScope(scope);
            }
            ScriptFunction func = getProgramFunction(clazz, scope);
            if (directEval) {
                evalThis = ((callThis == ScriptRuntime.UNDEFINED || callThis == null) && !strictFlag) ? global : callThis;
            } else {
                evalThis = callThis;
            }
            return ScriptRuntime.apply(func, evalThis, new Object[0]);
        } catch (ParserException e2) {
            e2.throwAsEcmaException(global);
            return null;
        }
    }

    private static ScriptObject newScope(ScriptObject callerScope) {
        return new Scope(callerScope, PropertyMap.newMap(Scope.class));
    }

    private static Source loadInternal(final String srcStr, String prefix, String resourcePath) {
        if (srcStr.startsWith(prefix)) {
            final String resource = resourcePath + srcStr.substring(prefix.length());
            return (Source) AccessController.doPrivileged(new PrivilegedAction<Source>() { // from class: jdk.nashorn.internal.runtime.Context.3
                @Override // java.security.PrivilegedAction
                public Source run() {
                    try {
                        URL resURL = Context.class.getResource(resource);
                        if (resURL == null) {
                            return null;
                        }
                        return Source.sourceFor(srcStr, resURL);
                    } catch (IOException e) {
                        return null;
                    }
                }
            });
        }
        return null;
    }

    public Object load(Object scope, Object from) throws IOException {
        URL url;
        Object src = from instanceof ConsString ? from.toString() : from;
        Source source = null;
        if (src instanceof String) {
            String srcStr = (String) src;
            if (srcStr.startsWith(LOAD_CLASSPATH)) {
                URL url2 = getResourceURL(srcStr.substring(LOAD_CLASSPATH.length()));
                source = url2 != null ? Source.sourceFor(url2.toString(), url2) : null;
            } else {
                File file = new File(srcStr);
                if (srcStr.indexOf(58) != -1) {
                    Source loadInternal = loadInternal(srcStr, LOAD_NASHORN, "resources/");
                    source = loadInternal;
                    if (loadInternal == null) {
                        Source loadInternal2 = loadInternal(srcStr, LOAD_FX, "resources/fx/");
                        source = loadInternal2;
                        if (loadInternal2 == null) {
                            try {
                                url = new URL(srcStr);
                            } catch (MalformedURLException e) {
                                url = file.toURI().toURL();
                            }
                            source = Source.sourceFor(url.toString(), url);
                        }
                    }
                } else if (file.isFile()) {
                    source = Source.sourceFor(srcStr, file);
                }
            }
        } else if ((src instanceof File) && ((File) src).isFile()) {
            File file2 = (File) src;
            source = Source.sourceFor(file2.getName(), file2);
        } else if (src instanceof URL) {
            URL url3 = (URL) src;
            source = Source.sourceFor(url3.toString(), url3);
        } else if (src instanceof ScriptObject) {
            ScriptObject sobj = (ScriptObject) src;
            if (sobj.has("script") && sobj.has("name")) {
                String script = JSType.toString(sobj.get("script"));
                String name = JSType.toString(sobj.get("name"));
                source = Source.sourceFor(name, script);
            }
        } else if (src instanceof Map) {
            Map<?, ?> map = (Map) src;
            if (map.containsKey("script") && map.containsKey("name")) {
                String script2 = JSType.toString(map.get("script"));
                String name2 = JSType.toString(map.get("name"));
                source = Source.sourceFor(name2, script2);
            }
        }
        if (source != null) {
            if ((scope instanceof ScriptObject) && ((ScriptObject) scope).isScope()) {
                ScriptObject sobj2 = (ScriptObject) scope;
                if (!$assertionsDisabled && !sobj2.isGlobal()) {
                    throw new AssertionError("non-Global scope object!!");
                }
                return evaluateSource(source, sobj2, sobj2);
            } else if (scope == null || scope == ScriptRuntime.UNDEFINED) {
                Global global = getGlobal();
                return evaluateSource(source, global, global);
            } else {
                Global global2 = getGlobal();
                ScriptObject evalScope = newScope(global2);
                ScriptObject withObj = ScriptRuntime.openWith(evalScope, scope);
                return evaluateSource(source, withObj, global2);
            }
        }
        throw ECMAErrors.typeError("cant.load.script", ScriptRuntime.safeToString(from));
    }

    public Object loadWithNewGlobal(Object from, Object... args) throws IOException {
        Global oldGlobal = getGlobal();
        Global newGlobal = (Global) AccessController.doPrivileged(new PrivilegedAction<Global>() { // from class: jdk.nashorn.internal.runtime.Context.4
            @Override // java.security.PrivilegedAction
            public Global run() {
                try {
                    return Context.this.newGlobal();
                } catch (RuntimeException e) {
                    if (Context.DEBUG) {
                        e.printStackTrace();
                    }
                    throw e;
                }
            }
        }, CREATE_GLOBAL_ACC_CTXT);
        initGlobal(newGlobal);
        setGlobal(newGlobal);
        Object[] wrapped = args == null ? ScriptRuntime.EMPTY_ARRAY : ScriptObjectMirror.wrapArray(args, oldGlobal);
        newGlobal.put("arguments", newGlobal.wrapAsObject(wrapped), this.env._strict);
        try {
            Object unwrap = ScriptObjectMirror.unwrap(ScriptObjectMirror.wrap(load(newGlobal, from), newGlobal), oldGlobal);
            setGlobal(oldGlobal);
            return unwrap;
        } catch (Throwable th) {
            setGlobal(oldGlobal);
            throw th;
        }
    }

    public static Class<? extends ScriptObject> forStructureClass(String fullName) throws ClassNotFoundException {
        if (System.getSecurityManager() != null && !StructureLoader.isStructureClass(fullName)) {
            throw new ClassNotFoundException(fullName);
        }
        return Class.forName(fullName, true, theStructLoader);
    }

    public static void checkPackageAccess(Class<?> clazz) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            Class<?> cls = clazz;
            while (true) {
                Class<?> bottomClazz = cls;
                if (bottomClazz.isArray()) {
                    cls = bottomClazz.getComponentType();
                } else {
                    checkPackageAccess(sm, bottomClazz.getName());
                    return;
                }
            }
        }
    }

    public static void checkPackageAccess(String pkgName) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            checkPackageAccess(sm, pkgName.endsWith(".") ? pkgName : pkgName + ".");
        }
    }

    private static void checkPackageAccess(final SecurityManager sm, String fullName) {
        Objects.requireNonNull(sm);
        int index = fullName.lastIndexOf(46);
        if (index != -1) {
            final String pkgName = fullName.substring(0, index);
            AccessController.doPrivileged(new PrivilegedAction<Void>() { // from class: jdk.nashorn.internal.runtime.Context.5
                @Override // java.security.PrivilegedAction
                public Void run() {
                    sm.checkPackageAccess(pkgName);
                    return null;
                }
            }, NO_PERMISSIONS_ACC_CTXT);
        }
    }

    public static boolean isStructureClass(String className) {
        return StructureLoader.isStructureClass(className);
    }

    private static boolean isAccessiblePackage(Class<?> clazz) {
        try {
            checkPackageAccess(clazz);
            return true;
        } catch (SecurityException e) {
            return false;
        }
    }

    public static boolean isAccessibleClass(Class<?> clazz) {
        return Modifier.isPublic(clazz.getModifiers()) && isAccessiblePackage(clazz);
    }

    public Class<?> findClass(String fullName) throws ClassNotFoundException {
        if (fullName.indexOf(91) != -1 || fullName.indexOf(47) != -1) {
            throw new ClassNotFoundException(fullName);
        }
        if (this.classFilter != null && !this.classFilter.exposeToScripts(fullName)) {
            throw new ClassNotFoundException(fullName);
        }
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            checkPackageAccess(sm, fullName);
        }
        if (this.appLoader != null) {
            return Class.forName(fullName, true, this.appLoader);
        }
        Class<?> cl = Class.forName(fullName);
        if (cl.getClassLoader() == null) {
            return cl;
        }
        throw new ClassNotFoundException(fullName);
    }

    public static void printStackTrace(Throwable t) {
        if (DEBUG) {
            t.printStackTrace(getCurrentErr());
        }
    }

    public void verify(byte[] bytecode) {
        if (this.env._verify_code && System.getSecurityManager() == null) {
            CheckClassAdapter.verify(new ClassReader(bytecode), theStructLoader, false, new PrintWriter((OutputStream) System.err, true));
        }
    }

    public Global createGlobal() {
        return initGlobal(newGlobal());
    }

    public Global newGlobal() {
        createOrInvalidateGlobalConstants();
        return new Global(this);
    }

    private void createOrInvalidateGlobalConstants() {
        GlobalConstants newGlobalConstants;
        do {
            GlobalConstants currentGlobalConstants = getGlobalConstants();
            if (currentGlobalConstants != null) {
                currentGlobalConstants.invalidateForever();
                return;
            }
            newGlobalConstants = new GlobalConstants(getLogger(GlobalConstants.class));
        } while (!this.globalConstantsRef.compareAndSet(null, newGlobalConstants));
    }

    public Global initGlobal(Global global, ScriptEngine engine) {
        if (!this.env._compile_only) {
            Global oldGlobal = getGlobal();
            try {
                setGlobal(global);
                global.initBuiltinObjects(engine);
                setGlobal(oldGlobal);
            } catch (Throwable th) {
                setGlobal(oldGlobal);
                throw th;
            }
        }
        return global;
    }

    public Global initGlobal(Global global) {
        return initGlobal(global, null);
    }

    public static Context getContextTrusted() {
        return getContext(getGlobal());
    }

    public static Context getContextTrustedOrNull() {
        Global global = getGlobal();
        if (global == null) {
            return null;
        }
        return getContext(global);
    }

    private static Context getContext(Global global) {
        return global.getContext();
    }

    public static Context fromClass(Class<?> clazz) {
        ClassLoader loader = clazz.getClassLoader();
        if (loader instanceof ScriptLoader) {
            return ((ScriptLoader) loader).getContext();
        }
        return getContextTrusted();
    }

    private URL getResourceURL(String resName) {
        if (this.appLoader != null) {
            return this.appLoader.getResource(resName);
        }
        return ClassLoader.getSystemResource(resName);
    }

    private Object evaluateSource(Source source, ScriptObject scope, ScriptObject thiz) {
        ScriptFunction script = null;
        try {
            script = compileScript(source, scope, new ThrowErrorManager());
        } catch (ParserException e) {
            e.throwAsEcmaException();
        }
        return ScriptRuntime.apply(script, thiz, new Object[0]);
    }

    private static ScriptFunction getProgramFunction(Class<?> script, ScriptObject scope) {
        if (script == null) {
            return null;
        }
        return invokeCreateProgramFunctionHandle(getCreateProgramFunctionHandle(script), scope);
    }

    private static MethodHandle getCreateProgramFunctionHandle(Class<?> script) {
        try {
            return LOOKUP.findStatic(script, CompilerConstants.CREATE_PROGRAM_FUNCTION.symbolName(), CREATE_PROGRAM_FUNCTION_TYPE);
        } catch (IllegalAccessException | NoSuchMethodException e) {
            throw new AssertionError("Failed to retrieve a handle for the program function for " + script.getName(), e);
        }
    }

    public static ScriptFunction invokeCreateProgramFunctionHandle(MethodHandle createProgramFunctionHandle, ScriptObject scope) {
        try {
            return createProgramFunctionHandle.invokeExact(scope);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable t) {
            throw new AssertionError("Failed to create a program function", t);
        }
    }

    private ScriptFunction compileScript(Source source, ScriptObject scope, ErrorManager errMan) {
        return getProgramFunction(compile(source, errMan, this._strict), scope);
    }

    private synchronized Class<?> compile(Source source, ErrorManager errMan, boolean strict) {
        Class<?> script;
        errMan.reset();
        Class<?> script2 = findCachedClass(source);
        if (script2 != null) {
            DebugLogger log = getLogger(Compiler.class);
            if (log.isEnabled()) {
                log.fine(new RuntimeEvent<>(Level.INFO, source), "Code cache hit for ", source, " avoiding recompile.");
            }
            return script2;
        }
        StoredScript storedScript = null;
        FunctionNode functionNode = null;
        boolean useCodeStore = this.codeStore != null && !this.env._parse_only && (!this.env._optimistic_types || this.env._lazy_compilation);
        String cacheKey = useCodeStore ? CodeStore.getCacheKey("script", null) : null;
        if (useCodeStore) {
            storedScript = this.codeStore.load(source, cacheKey);
        }
        if (storedScript == null) {
            if (this.env._dest_dir != null) {
                source.dump(this.env._dest_dir);
            }
            functionNode = new Parser(this.env, source, errMan, strict, getLogger(Parser.class)).parse();
            if (errMan.hasErrors()) {
                return null;
            }
            if (this.env._print_ast || functionNode.getFlag(524288)) {
                getErr().println(new ASTWriter(functionNode));
            }
            if (this.env._print_parse || functionNode.getFlag(131072)) {
                getErr().println(new PrintVisitor(functionNode, true, false));
            }
        }
        if (this.env._parse_only) {
            return null;
        }
        URL url = source.getURL();
        ScriptLoader loader = this.env._loader_per_compile ? createNewLoader() : this.scriptLoader;
        CodeSource cs = new CodeSource(url, (CodeSigner[]) null);
        CodeInstaller installer = new ContextCodeInstaller(loader, cs);
        if (storedScript == null) {
            Compiler.CompilationPhases phases = Compiler.CompilationPhases.COMPILE_ALL;
            Compiler compiler = Compiler.forInitialCompilation(installer, source, errMan, strict | functionNode.isStrict());
            FunctionNode compiledFunction = compiler.compile(functionNode, phases);
            if (errMan.hasErrors()) {
                return null;
            }
            script = compiledFunction.getRootClass();
            compiler.persistClassInfo(cacheKey, compiledFunction);
        } else {
            Compiler.updateCompilationId(storedScript.getCompilationId());
            script = storedScript.installScript(source, installer);
        }
        cacheClass(source, script);
        return script;
    }

    public ScriptLoader createNewLoader() {
        return (ScriptLoader) AccessController.doPrivileged(new PrivilegedAction<ScriptLoader>() { // from class: jdk.nashorn.internal.runtime.Context.6
            @Override // java.security.PrivilegedAction
            public ScriptLoader run() {
                return new ScriptLoader(Context.this);
            }
        }, CREATE_LOADER_ACC_CTXT);
    }

    public long getUniqueScriptId() {
        return this.uniqueScriptId.getAndIncrement();
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/Context$ClassCache.class */
    public static class ClassCache extends LinkedHashMap<Source, ClassReference> {
        private final int size;
        private final ReferenceQueue<Class<?>> queue = new ReferenceQueue<>();

        ClassCache(int size) {
            super(size, 0.75f, true);
            this.size = size;
        }

        void cache(Source source, Class<?> clazz) {
            put(source, new ClassReference(clazz, this.queue, source));
        }

        @Override // java.util.LinkedHashMap
        protected boolean removeEldestEntry(Map.Entry<Source, ClassReference> eldest) {
            return size() > this.size;
        }

        @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
        public ClassReference get(Object key) {
            while (true) {
                ClassReference ref = (ClassReference) this.queue.poll();
                if (ref != null) {
                    remove(ref.source);
                } else {
                    return (ClassReference) super.get(key);
                }
            }
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/Context$ClassReference.class */
    public static class ClassReference extends SoftReference<Class<?>> {
        private final Source source;

        ClassReference(Class<?> clazz, ReferenceQueue<Class<?>> queue, Source source) {
            super(clazz, queue);
            this.source = source;
        }
    }

    private Class<?> findCachedClass(Source source) {
        ClassReference ref = this.classCache == null ? null : this.classCache.get((Object) source);
        if (ref != null) {
            return ref.get();
        }
        return null;
    }

    private void cacheClass(Source source, Class<?> clazz) {
        if (this.classCache != null) {
            this.classCache.cache(source, clazz);
        }
    }

    private void initLoggers() {
        ((Loggable) MethodHandleFactory.getFunctionality()).initLogger(this);
    }

    public DebugLogger getLogger(Class<? extends Loggable> clazz) {
        return getLogger(clazz, null);
    }

    public DebugLogger getLogger(Class<? extends Loggable> clazz, Consumer<DebugLogger> initHook) {
        String name = getLoggerName(clazz);
        DebugLogger logger = this.loggers.get(name);
        if (logger == null) {
            if (!this.env.hasLogger(name)) {
                return DebugLogger.DISABLED_LOGGER;
            }
            LoggingOption.LoggerInfo info = this.env._loggers.get(name);
            logger = new DebugLogger(name, info.getLevel(), info.isQuiet());
            if (initHook != null) {
                initHook.accept(logger);
            }
            this.loggers.put(name, logger);
        }
        return logger;
    }

    public MethodHandle addLoggingToHandle(Class<? extends Loggable> clazz, MethodHandle mh, Supplier<String> text) {
        return addLoggingToHandle(clazz, Level.INFO, mh, Integer.MAX_VALUE, false, text);
    }

    public MethodHandle addLoggingToHandle(Class<? extends Loggable> clazz, Level level, MethodHandle mh, int paramStart, boolean printReturnValue, Supplier<String> text) {
        DebugLogger log = getLogger(clazz);
        if (log.isEnabled()) {
            return MethodHandleFactory.addDebugPrintout(log, level, mh, paramStart, printReturnValue, text.get());
        }
        return mh;
    }

    private static String getLoggerName(Class<?> clazz) {
        Class<?> cls = clazz;
        while (true) {
            Class<?> current = cls;
            if (current != null) {
                Logger log = (Logger) current.getAnnotation(Logger.class);
                if (log != null) {
                    if (!$assertionsDisabled && "".equals(log.name())) {
                        throw new AssertionError();
                    }
                    return log.name();
                }
                cls = current.getSuperclass();
            } else if ($assertionsDisabled) {
                return null;
            } else {
                throw new AssertionError();
            }
        }
    }

    public SwitchPoint newBuiltinSwitchPoint(String name) {
        if ($assertionsDisabled || this.builtinSwitchPoints.get(name) == null) {
            SwitchPoint sp = new BuiltinSwitchPoint();
            this.builtinSwitchPoints.put(name, sp);
            return sp;
        }
        throw new AssertionError();
    }

    public SwitchPoint getBuiltinSwitchPoint(String name) {
        return this.builtinSwitchPoints.get(name);
    }
}
