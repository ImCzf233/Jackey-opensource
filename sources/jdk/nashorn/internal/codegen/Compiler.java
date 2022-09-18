package jdk.nashorn.internal.codegen;

import java.io.File;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.logging.Level;
import jdk.internal.dynalink.support.NameCodec;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.Expression;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.Optimistic;
import jdk.nashorn.internal.p001ir.debug.ClassHistogramElement;
import jdk.nashorn.internal.p001ir.debug.ObjectSizeCalculator;
import jdk.nashorn.internal.runtime.CodeInstaller;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.FunctionInitializer;
import jdk.nashorn.internal.runtime.ParserException;
import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;

@Logger(name = "compiler")
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/Compiler.class */
public final class Compiler implements Loggable {
    public static final String SCRIPTS_PACKAGE = "jdk/nashorn/internal/scripts";
    public static final String OBJECTS_PACKAGE = "jdk/nashorn/internal/objects";
    private final ScriptEnvironment env;
    private final Source source;
    private final String sourceName;
    private final ErrorManager errors;
    private final boolean optimistic;
    private final Map<String, byte[]> bytecode;
    private final Set<CompileUnit> compileUnits;
    private final ConstantData constantData;
    private final CodeInstaller installer;
    private final DebugLogger log;
    private final Context context;
    private final TypeMap types;
    private final TypeEvaluator typeEvaluator;
    private final boolean strict;
    private final boolean onDemand;
    private final Map<Integer, Type> invalidatedProgramPoints;
    private final Object typeInformationFile;
    private final String firstCompileUnitName;
    private final int[] continuationEntryPoints;
    private RecompilableScriptFunctionData compiledFunction;
    private static final int COMPILE_UNIT_NAME_BUFFER_SIZE = 32;
    private static String[] RESERVED_NAMES;
    private final int compilationId;
    private final AtomicInteger nextCompileUnitId;
    private static final AtomicInteger COMPILATION_ID;
    private static final String DANGEROUS_CHARS = "\\/.;:$[]<>";
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !Compiler.class.desiredAssertionStatus();
        RESERVED_NAMES = new String[]{CompilerConstants.SCOPE.symbolName(), CompilerConstants.THIS.symbolName(), CompilerConstants.RETURN.symbolName(), CompilerConstants.CALLEE.symbolName(), CompilerConstants.VARARGS.symbolName(), CompilerConstants.ARGUMENTS.symbolName()};
        COMPILATION_ID = new AtomicInteger(0);
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/Compiler$CompilationPhases.class */
    public static class CompilationPhases implements Iterable<CompilationPhase> {
        private static final CompilationPhases COMPILE_UPTO_CACHED = new CompilationPhases("Common initial phases", CompilationPhase.CONSTANT_FOLDING_PHASE, CompilationPhase.LOWERING_PHASE, CompilationPhase.APPLY_SPECIALIZATION_PHASE, CompilationPhase.SPLITTING_PHASE, CompilationPhase.PROGRAM_POINT_PHASE, CompilationPhase.SYMBOL_ASSIGNMENT_PHASE, CompilationPhase.SCOPE_DEPTH_COMPUTATION_PHASE, CompilationPhase.CACHE_AST_PHASE);
        private static final CompilationPhases COMPILE_CACHED_UPTO_BYTECODE = new CompilationPhases("After common phases, before bytecode generator", CompilationPhase.OPTIMISTIC_TYPE_ASSIGNMENT_PHASE, CompilationPhase.LOCAL_VARIABLE_TYPE_CALCULATION_PHASE);
        public static final CompilationPhases RECOMPILE_CACHED_UPTO_BYTECODE = new CompilationPhases("Recompile cached function up to bytecode", CompilationPhase.REINITIALIZE_CACHED, COMPILE_CACHED_UPTO_BYTECODE);
        public static final CompilationPhases GENERATE_BYTECODE_AND_INSTALL = new CompilationPhases("Generate bytecode and install", CompilationPhase.BYTECODE_GENERATION_PHASE, CompilationPhase.INSTALL_PHASE);
        public static final CompilationPhases COMPILE_UPTO_BYTECODE = new CompilationPhases("Compile upto bytecode", COMPILE_UPTO_CACHED, COMPILE_CACHED_UPTO_BYTECODE);
        public static final CompilationPhases COMPILE_ALL_NO_INSTALL = new CompilationPhases("Compile without install", COMPILE_UPTO_BYTECODE, CompilationPhase.BYTECODE_GENERATION_PHASE);
        public static final CompilationPhases COMPILE_ALL = new CompilationPhases("Full eager compilation", COMPILE_UPTO_BYTECODE, GENERATE_BYTECODE_AND_INSTALL);
        public static final CompilationPhases COMPILE_ALL_CACHED = new CompilationPhases("Eager compilation from serializaed state", RECOMPILE_CACHED_UPTO_BYTECODE, GENERATE_BYTECODE_AND_INSTALL);
        public static final CompilationPhases GENERATE_BYTECODE_AND_INSTALL_RESTOF = new CompilationPhases("Generate bytecode and install - RestOf method", CompilationPhase.REUSE_COMPILE_UNITS_PHASE, GENERATE_BYTECODE_AND_INSTALL);
        public static final CompilationPhases COMPILE_ALL_RESTOF = new CompilationPhases("Compile all, rest of", COMPILE_UPTO_BYTECODE, GENERATE_BYTECODE_AND_INSTALL_RESTOF);
        public static final CompilationPhases COMPILE_CACHED_RESTOF = new CompilationPhases("Compile serialized, rest of", RECOMPILE_CACHED_UPTO_BYTECODE, GENERATE_BYTECODE_AND_INSTALL_RESTOF);
        private final List<CompilationPhase> phases;
        private final String desc;

        private CompilationPhases(String desc, CompilationPhase... phases) {
            this(desc, Arrays.asList(phases));
        }

        private CompilationPhases(String desc, CompilationPhases base, CompilationPhase... phases) {
            this(desc, concat(base.phases, Arrays.asList(phases)));
        }

        private CompilationPhases(String desc, CompilationPhase first, CompilationPhases rest) {
            this(desc, concat(Collections.singletonList(first), rest.phases));
        }

        private CompilationPhases(String desc, CompilationPhases base) {
            this(desc, base.phases);
        }

        private CompilationPhases(String desc, CompilationPhases... bases) {
            this(desc, concatPhases(bases));
        }

        private CompilationPhases(String desc, List<CompilationPhase> phases) {
            this.desc = desc;
            this.phases = phases;
        }

        private static List<CompilationPhase> concatPhases(CompilationPhases[] bases) {
            ArrayList<CompilationPhase> l = new ArrayList<>();
            for (CompilationPhases base : bases) {
                l.addAll(base.phases);
            }
            l.trimToSize();
            return l;
        }

        private static <T> List<T> concat(List<T> l1, List<T> l2) {
            ArrayList<T> l = new ArrayList<>(l1);
            l.addAll(l2);
            l.trimToSize();
            return l;
        }

        public String toString() {
            return "'" + this.desc + "' " + this.phases.toString();
        }

        public boolean contains(CompilationPhase phase) {
            return this.phases.contains(phase);
        }

        @Override // java.lang.Iterable
        public Iterator<CompilationPhase> iterator() {
            return this.phases.iterator();
        }

        public boolean isRestOfCompilation() {
            return this == COMPILE_ALL_RESTOF || this == GENERATE_BYTECODE_AND_INSTALL_RESTOF || this == COMPILE_CACHED_RESTOF;
        }

        String getDesc() {
            return this.desc;
        }

        String toString(String prefix) {
            StringBuilder sb = new StringBuilder();
            for (CompilationPhase phase : this.phases) {
                sb.append(prefix).append(phase).append('\n');
            }
            return sb.toString();
        }
    }

    public static Compiler forInitialCompilation(CodeInstaller installer, Source source, ErrorManager errors, boolean isStrict) {
        return new Compiler(installer.getContext(), installer, source, errors, isStrict);
    }

    public static Compiler forNoInstallerCompilation(Context context, Source source, boolean isStrict) {
        return new Compiler(context, null, source, context.getErrorManager(), isStrict);
    }

    public static Compiler forOnDemandCompilation(CodeInstaller installer, Source source, boolean isStrict, RecompilableScriptFunctionData compiledFunction, TypeMap types, Map<Integer, Type> invalidatedProgramPoints, Object typeInformationFile, int[] continuationEntryPoints, ScriptObject runtimeScope) {
        Context context = installer.getContext();
        return new Compiler(context, installer, source, context.getErrorManager(), isStrict, true, compiledFunction, types, invalidatedProgramPoints, typeInformationFile, continuationEntryPoints, runtimeScope);
    }

    private Compiler(Context context, CodeInstaller installer, Source source, ErrorManager errors, boolean isStrict) {
        this(context, installer, source, errors, isStrict, false, null, null, null, null, null, null);
    }

    private Compiler(Context context, CodeInstaller installer, Source source, ErrorManager errors, boolean isStrict, boolean isOnDemand, RecompilableScriptFunctionData compiledFunction, TypeMap types, Map<Integer, Type> invalidatedProgramPoints, Object typeInformationFile, int[] continuationEntryPoints, ScriptObject runtimeScope) {
        this.compilationId = COMPILATION_ID.getAndIncrement();
        this.nextCompileUnitId = new AtomicInteger(0);
        this.context = context;
        this.env = context.getEnv();
        this.installer = installer;
        this.constantData = new ConstantData();
        this.compileUnits = CompileUnit.createCompileUnitSet();
        this.bytecode = new LinkedHashMap();
        this.log = initLogger(context);
        this.source = source;
        this.errors = errors;
        this.sourceName = FunctionNode.getSourceName(source);
        this.onDemand = isOnDemand;
        this.compiledFunction = compiledFunction;
        this.types = types;
        this.invalidatedProgramPoints = invalidatedProgramPoints == null ? new HashMap<>() : invalidatedProgramPoints;
        this.typeInformationFile = typeInformationFile;
        this.continuationEntryPoints = continuationEntryPoints == null ? null : (int[]) continuationEntryPoints.clone();
        this.typeEvaluator = new TypeEvaluator(this, runtimeScope);
        this.firstCompileUnitName = firstCompileUnitName();
        this.strict = isStrict;
        this.optimistic = this.env._optimistic_types;
    }

    private String safeSourceName() {
        String baseName = new File(this.source.getName()).getName();
        int index = baseName.lastIndexOf(".js");
        if (index != -1) {
            baseName = baseName.substring(0, index);
        }
        String baseName2 = baseName.replace('.', '_').replace('-', '_');
        if (!this.env._loader_per_compile) {
            baseName2 = baseName2 + this.installer.getUniqueScriptId();
        }
        String mangled = this.env._verify_code ? replaceDangerChars(baseName2) : NameCodec.encode(baseName2);
        return mangled != null ? mangled : baseName2;
    }

    private static String replaceDangerChars(String name) {
        int len = name.length();
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < len; i++) {
            char ch = name.charAt(i);
            if (DANGEROUS_CHARS.indexOf(ch) != -1) {
                buf.append('_');
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }

    private String firstCompileUnitName() {
        StringBuilder sb = new StringBuilder(SCRIPTS_PACKAGE).append('/').append(CompilerConstants.DEFAULT_SCRIPT_NAME.symbolName()).append('$');
        if (isOnDemandCompilation()) {
            sb.append(RecompilableScriptFunctionData.RECOMPILATION_PREFIX);
        }
        if (this.compilationId > 0) {
            sb.append(this.compilationId).append('$');
        }
        if (this.types != null && this.compiledFunction.getFunctionNodeId() > 0) {
            sb.append(this.compiledFunction.getFunctionNodeId());
            Type[] paramTypes = this.types.getParameterTypes(this.compiledFunction.getFunctionNodeId());
            for (Type t : paramTypes) {
                sb.append(Type.getShortSignatureDescriptor(t));
            }
            sb.append('$');
        }
        sb.append(safeSourceName());
        return sb.toString();
    }

    public void declareLocalSymbol(String symbolName) {
        this.typeEvaluator.declareLocalSymbol(symbolName);
    }

    public void setData(RecompilableScriptFunctionData data) {
        if ($assertionsDisabled || this.compiledFunction == null) {
            this.compiledFunction = data;
            return;
        }
        throw new AssertionError(data);
    }

    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger getLogger() {
        return this.log;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger initLogger(Context ctxt) {
        final boolean optimisticTypes = this.env._optimistic_types;
        final boolean lazyCompilation = this.env._lazy_compilation;
        return ctxt.getLogger(getClass(), new Consumer<DebugLogger>() { // from class: jdk.nashorn.internal.codegen.Compiler.1
            public void accept(DebugLogger newLogger) {
                if (!lazyCompilation) {
                    newLogger.warning("WARNING: Running with lazy compilation switched off. This is not a default setting.");
                }
                Object[] objArr = new Object[2];
                objArr[0] = "Optimistic types are ";
                objArr[1] = optimisticTypes ? "ENABLED." : "DISABLED.";
                newLogger.warning(objArr);
            }
        });
    }

    public ScriptEnvironment getScriptEnvironment() {
        return this.env;
    }

    public boolean isOnDemandCompilation() {
        return this.onDemand;
    }

    public boolean useOptimisticTypes() {
        return this.optimistic;
    }

    public Context getContext() {
        return this.context;
    }

    public Type getOptimisticType(Optimistic node) {
        return this.typeEvaluator.getOptimisticType(node);
    }

    public boolean hasStringPropertyIterator(Expression expr) {
        return this.typeEvaluator.hasStringPropertyIterator(expr);
    }

    public void addInvalidatedProgramPoint(int programPoint, Type type) {
        this.invalidatedProgramPoints.put(Integer.valueOf(programPoint), type);
    }

    public Map<Integer, Type> getInvalidatedProgramPoints() {
        if (this.invalidatedProgramPoints.isEmpty()) {
            return null;
        }
        return new TreeMap(this.invalidatedProgramPoints);
    }

    public TypeMap getTypeMap() {
        return this.types;
    }

    public MethodType getCallSiteType(FunctionNode fn) {
        if (this.types == null || !isOnDemandCompilation()) {
            return null;
        }
        return this.types.getCallSiteType(fn);
    }

    public Type getParamType(FunctionNode fn, int pos) {
        if (this.types == null) {
            return null;
        }
        return this.types.get(fn, pos);
    }

    public FunctionNode compile(FunctionNode functionNode, CompilationPhases phases) throws CompilationException {
        String[] strArr;
        if (this.log.isEnabled()) {
            this.log.info(">> Starting compile job for ", DebugLogger.quote(functionNode.getName()), " phases=", DebugLogger.quote(phases.getDesc()));
            this.log.indent();
        }
        String name = DebugLogger.quote(functionNode.getName());
        FunctionNode newFunctionNode = functionNode;
        for (String reservedName : RESERVED_NAMES) {
            newFunctionNode.uniqueName(reservedName);
        }
        boolean info = this.log.isLoggable(Level.INFO);
        DebugLogger timeLogger = this.env.isTimingEnabled() ? this.env._timing.getLogger() : null;
        long time = 0;
        Iterator<CompilationPhase> it = phases.iterator();
        while (it.hasNext()) {
            CompilationPhase phase = it.next();
            this.log.fine(phase, " starting for ", name);
            try {
                newFunctionNode = phase.apply(this, phases, newFunctionNode);
                this.log.fine(phase, " done for function ", DebugLogger.quote(name));
                if (this.env._print_mem_usage) {
                    printMemoryUsage(functionNode, phase.toString());
                }
                time += this.env.isTimingEnabled() ? phase.getEndTime() - phase.getStartTime() : 0L;
            } catch (ParserException error) {
                this.errors.error(error);
                if (this.env._dump_on_error) {
                    error.printStackTrace(this.env.getErr());
                    return null;
                }
                return null;
            }
        }
        if (this.typeInformationFile != null && !phases.isRestOfCompilation()) {
            OptimisticTypesPersistence.store(this.typeInformationFile, this.invalidatedProgramPoints);
        }
        this.log.unindent();
        if (info) {
            StringBuilder sb = new StringBuilder("<< Finished compile job for ");
            sb.append(newFunctionNode.getSource()).append(':').append(DebugLogger.quote(newFunctionNode.getName()));
            if (time > 0 && timeLogger != null) {
                if (!$assertionsDisabled && !this.env.isTimingEnabled()) {
                    throw new AssertionError();
                }
                sb.append(" in ").append(TimeUnit.NANOSECONDS.toMillis(time)).append(" ms");
            }
            this.log.info(sb);
        }
        return newFunctionNode;
    }

    public Source getSource() {
        return this.source;
    }

    public Map<String, byte[]> getBytecode() {
        return Collections.unmodifiableMap(this.bytecode);
    }

    public void clearBytecode() {
        this.bytecode.clear();
    }

    CompileUnit getFirstCompileUnit() {
        if ($assertionsDisabled || !this.compileUnits.isEmpty()) {
            return this.compileUnits.iterator().next();
        }
        throw new AssertionError();
    }

    public Set<CompileUnit> getCompileUnits() {
        return this.compileUnits;
    }

    public ConstantData getConstantData() {
        return this.constantData;
    }

    public CodeInstaller getCodeInstaller() {
        return this.installer;
    }

    public void addClass(String name, byte[] code) {
        this.bytecode.put(name, code);
    }

    public String nextCompileUnitName() {
        StringBuilder sb = new StringBuilder(32);
        sb.append(this.firstCompileUnitName);
        int cuid = this.nextCompileUnitId.getAndIncrement();
        if (cuid > 0) {
            sb.append("$cu").append(cuid);
        }
        return sb.toString();
    }

    public void persistClassInfo(String cacheKey, FunctionNode functionNode) {
        if (cacheKey != null && this.env._persistent_cache) {
            Map<Integer, FunctionInitializer> initializers = new HashMap<>();
            if (isOnDemandCompilation()) {
                initializers.put(Integer.valueOf(functionNode.getId()), new FunctionInitializer(functionNode, getInvalidatedProgramPoints()));
            } else {
                for (CompileUnit compileUnit : getCompileUnits()) {
                    for (FunctionNode fn : compileUnit.getFunctionNodes()) {
                        initializers.put(Integer.valueOf(fn.getId()), new FunctionInitializer(fn));
                    }
                }
            }
            String mainClassName = getFirstCompileUnit().getUnitClassName();
            this.installer.storeScript(cacheKey, this.source, mainClassName, this.bytecode, initializers, this.constantData.toArray(), this.compilationId);
        }
    }

    public static void updateCompilationId(int value) {
        if (value >= COMPILATION_ID.get()) {
            COMPILATION_ID.set(value + 1);
        }
    }

    public CompileUnit addCompileUnit(long initialWeight) {
        CompileUnit compileUnit = createCompileUnit(initialWeight);
        this.compileUnits.add(compileUnit);
        this.log.fine("Added compile unit ", compileUnit);
        return compileUnit;
    }

    public CompileUnit createCompileUnit(String unitClassName, long initialWeight) {
        ClassEmitter classEmitter = new ClassEmitter(this.context, this.sourceName, unitClassName, isStrict());
        CompileUnit compileUnit = new CompileUnit(unitClassName, classEmitter, initialWeight);
        classEmitter.begin();
        return compileUnit;
    }

    private CompileUnit createCompileUnit(long initialWeight) {
        return createCompileUnit(nextCompileUnitName(), initialWeight);
    }

    public boolean isStrict() {
        return this.strict;
    }

    public void replaceCompileUnits(Set<CompileUnit> newUnits) {
        this.compileUnits.clear();
        this.compileUnits.addAll(newUnits);
    }

    public CompileUnit findUnit(long weight) {
        for (CompileUnit unit : this.compileUnits) {
            if (unit.canHold(weight)) {
                unit.addWeight(weight);
                return unit;
            }
        }
        return addCompileUnit(weight);
    }

    public static String binaryName(String name) {
        return name.replace('/', '.');
    }

    public RecompilableScriptFunctionData getScriptFunctionData(int functionId) {
        if ($assertionsDisabled || this.compiledFunction != null) {
            RecompilableScriptFunctionData fn = this.compiledFunction.getScriptFunctionData(functionId);
            if (!$assertionsDisabled && fn == null) {
                throw new AssertionError(functionId);
            }
            return fn;
        }
        throw new AssertionError();
    }

    public boolean isGlobalSymbol(FunctionNode fn, String name) {
        return getScriptFunctionData(fn.getId()).isGlobalSymbol(fn, name);
    }

    public int[] getContinuationEntryPoints() {
        return this.continuationEntryPoints;
    }

    public Type getInvalidatedProgramPointType(int programPoint) {
        return this.invalidatedProgramPoints.get(Integer.valueOf(programPoint));
    }

    private void printMemoryUsage(FunctionNode functionNode, String phaseName) {
        if (!this.log.isEnabled()) {
            return;
        }
        this.log.info(phaseName, "finished. Doing IR size calculation...");
        ObjectSizeCalculator osc = new ObjectSizeCalculator(ObjectSizeCalculator.getEffectiveMemoryLayoutSpecification());
        osc.calculateObjectSize(functionNode);
        List<ClassHistogramElement> list = osc.getClassHistogram();
        StringBuilder sb = new StringBuilder();
        long totalSize = osc.calculateObjectSize(functionNode);
        sb.append(phaseName).append(" Total size = ").append((totalSize / 1024) / 1024).append("MB");
        this.log.info(sb);
        Collections.sort(list, new Comparator<ClassHistogramElement>() { // from class: jdk.nashorn.internal.codegen.Compiler.2
            public int compare(ClassHistogramElement o1, ClassHistogramElement o2) {
                long diff = o1.getBytes() - o2.getBytes();
                if (diff < 0) {
                    return 1;
                }
                if (diff > 0) {
                    return -1;
                }
                return 0;
            }
        });
        for (ClassHistogramElement e : list) {
            String line = String.format("    %-48s %10d bytes (%8d instances)", e.getClazz(), Long.valueOf(e.getBytes()), Long.valueOf(e.getInstances()));
            this.log.info(line);
            if (e.getBytes() < totalSize / 200) {
                this.log.info("    ...");
                return;
            }
        }
    }
}
