package jdk.nashorn.internal.codegen;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.nashorn.internal.codegen.Compiler;
import jdk.nashorn.internal.p001ir.Block;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.LiteralNode;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.Symbol;
import jdk.nashorn.internal.p001ir.debug.ASTWriter;
import jdk.nashorn.internal.p001ir.debug.PrintVisitor;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;
import jdk.nashorn.internal.p001ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.runtime.CodeInstaller;
import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.logging.DebugLogger;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/CompilationPhase.class */
public abstract class CompilationPhase {
    static final CompilationPhase CONSTANT_FOLDING_PHASE;
    static final CompilationPhase LOWERING_PHASE;
    static final CompilationPhase APPLY_SPECIALIZATION_PHASE;
    static final CompilationPhase SPLITTING_PHASE;
    static final CompilationPhase PROGRAM_POINT_PHASE;
    static final CompilationPhase CACHE_AST_PHASE;
    static final CompilationPhase SYMBOL_ASSIGNMENT_PHASE;
    static final CompilationPhase SCOPE_DEPTH_COMPUTATION_PHASE;
    static final CompilationPhase DECLARE_LOCAL_SYMBOLS_PHASE;
    static final CompilationPhase OPTIMISTIC_TYPE_ASSIGNMENT_PHASE;
    static final CompilationPhase LOCAL_VARIABLE_TYPE_CALCULATION_PHASE;
    static final CompilationPhase REUSE_COMPILE_UNITS_PHASE;
    static final CompilationPhase REINITIALIZE_CACHED;
    static final CompilationPhase BYTECODE_GENERATION_PHASE;
    static final CompilationPhase INSTALL_PHASE;
    private long startTime;
    private long endTime;
    private boolean isFinished;
    static final /* synthetic */ boolean $assertionsDisabled;

    abstract FunctionNode transform(Compiler compiler, Compiler.CompilationPhases compilationPhases, FunctionNode functionNode) throws CompilationException;

    static {
        $assertionsDisabled = !CompilationPhase.class.desiredAssertionStatus();
        CONSTANT_FOLDING_PHASE = new ConstantFoldingPhase();
        LOWERING_PHASE = new LoweringPhase();
        APPLY_SPECIALIZATION_PHASE = new ApplySpecializationPhase();
        SPLITTING_PHASE = new SplittingPhase();
        PROGRAM_POINT_PHASE = new ProgramPointPhase();
        CACHE_AST_PHASE = new CacheAstPhase();
        SYMBOL_ASSIGNMENT_PHASE = new SymbolAssignmentPhase();
        SCOPE_DEPTH_COMPUTATION_PHASE = new ScopeDepthComputationPhase();
        DECLARE_LOCAL_SYMBOLS_PHASE = new DeclareLocalSymbolsPhase();
        OPTIMISTIC_TYPE_ASSIGNMENT_PHASE = new OptimisticTypeAssignmentPhase();
        LOCAL_VARIABLE_TYPE_CALCULATION_PHASE = new LocalVariableTypeCalculationPhase();
        REUSE_COMPILE_UNITS_PHASE = new ReuseCompileUnitsPhase();
        REINITIALIZE_CACHED = new ReinitializeCachedPhase();
        BYTECODE_GENERATION_PHASE = new BytecodeGenerationPhase();
        INSTALL_PHASE = new InstallPhase();
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/CompilationPhase$ConstantFoldingPhase.class */
    private static final class ConstantFoldingPhase extends CompilationPhase {
        private ConstantFoldingPhase() {
            super();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(Compiler compiler, Compiler.CompilationPhases phases, FunctionNode fn) {
            return CompilationPhase.transformFunction(fn, new FoldConstants(compiler));
        }

        public String toString() {
            return "'Constant Folding'";
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/CompilationPhase$LoweringPhase.class */
    private static final class LoweringPhase extends CompilationPhase {
        private LoweringPhase() {
            super();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(Compiler compiler, Compiler.CompilationPhases phases, FunctionNode fn) {
            return CompilationPhase.transformFunction(fn, new Lower(compiler));
        }

        public String toString() {
            return "'Control Flow Lowering'";
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/CompilationPhase$ApplySpecializationPhase.class */
    private static final class ApplySpecializationPhase extends CompilationPhase {
        private ApplySpecializationPhase() {
            super();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(Compiler compiler, Compiler.CompilationPhases phases, FunctionNode fn) {
            return CompilationPhase.transformFunction(fn, new ApplySpecialization(compiler));
        }

        public String toString() {
            return "'Builtin Replacement'";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/CompilationPhase$SplittingPhase.class */
    public static final class SplittingPhase extends CompilationPhase {
        static final /* synthetic */ boolean $assertionsDisabled;

        private SplittingPhase() {
            super();
        }

        static {
            $assertionsDisabled = !CompilationPhase.class.desiredAssertionStatus();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(Compiler compiler, Compiler.CompilationPhases phases, FunctionNode fn) {
            CompileUnit outermostCompileUnit = compiler.addCompileUnit(0L);
            FunctionNode newFunctionNode = CompilationPhase.transformFunction(fn, new SimpleNodeVisitor() { // from class: jdk.nashorn.internal.codegen.CompilationPhase.SplittingPhase.1
                @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
                public LiteralNode<?> leaveLiteralNode(LiteralNode<?> literalNode) {
                    return literalNode.initialize(this.f247lc);
                }
            });
            FunctionNode newFunctionNode2 = CompilationPhase.transformFunction(new Splitter(compiler, newFunctionNode, outermostCompileUnit).split(newFunctionNode, true), new SplitIntoFunctions(compiler));
            if ($assertionsDisabled || newFunctionNode2.getCompileUnit() == outermostCompileUnit) {
                if (!$assertionsDisabled && newFunctionNode2.isStrict() != compiler.isStrict()) {
                    throw new AssertionError("functionNode.isStrict() != compiler.isStrict() for " + DebugLogger.quote(newFunctionNode2.getName()));
                }
                return newFunctionNode2;
            }
            throw new AssertionError("fn=" + fn.getName() + ", fn.compileUnit (" + newFunctionNode2.getCompileUnit() + ") != " + outermostCompileUnit);
        }

        public String toString() {
            return "'Code Splitting'";
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/CompilationPhase$ProgramPointPhase.class */
    private static final class ProgramPointPhase extends CompilationPhase {
        private ProgramPointPhase() {
            super();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(Compiler compiler, Compiler.CompilationPhases phases, FunctionNode fn) {
            return CompilationPhase.transformFunction(fn, new ProgramPoints());
        }

        public String toString() {
            return "'Program Point Calculation'";
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/CompilationPhase$CacheAstPhase.class */
    private static final class CacheAstPhase extends CompilationPhase {
        private CacheAstPhase() {
            super();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(Compiler compiler, Compiler.CompilationPhases phases, FunctionNode fn) {
            if (!compiler.isOnDemandCompilation()) {
                CompilationPhase.transformFunction(fn, new CacheAst(compiler));
            }
            return fn;
        }

        public String toString() {
            return "'Cache ASTs'";
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/CompilationPhase$SymbolAssignmentPhase.class */
    private static final class SymbolAssignmentPhase extends CompilationPhase {
        private SymbolAssignmentPhase() {
            super();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(Compiler compiler, Compiler.CompilationPhases phases, FunctionNode fn) {
            return CompilationPhase.transformFunction(fn, new AssignSymbols(compiler));
        }

        public String toString() {
            return "'Symbol Assignment'";
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/CompilationPhase$ScopeDepthComputationPhase.class */
    private static final class ScopeDepthComputationPhase extends CompilationPhase {
        private ScopeDepthComputationPhase() {
            super();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(Compiler compiler, Compiler.CompilationPhases phases, FunctionNode fn) {
            return CompilationPhase.transformFunction(fn, new FindScopeDepths(compiler));
        }

        public String toString() {
            return "'Scope Depth Computation'";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/CompilationPhase$DeclareLocalSymbolsPhase.class */
    public static final class DeclareLocalSymbolsPhase extends CompilationPhase {
        private DeclareLocalSymbolsPhase() {
            super();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(final Compiler compiler, Compiler.CompilationPhases phases, FunctionNode fn) {
            if (compiler.useOptimisticTypes() && compiler.isOnDemandCompilation()) {
                fn.getBody().accept(new SimpleNodeVisitor() { // from class: jdk.nashorn.internal.codegen.CompilationPhase.DeclareLocalSymbolsPhase.1
                    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
                    public boolean enterFunctionNode(FunctionNode functionNode) {
                        return false;
                    }

                    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
                    public boolean enterBlock(Block block) {
                        for (Symbol symbol : block.getSymbols()) {
                            if (!symbol.isScope()) {
                                compiler.declareLocalSymbol(symbol.getName());
                            }
                        }
                        return true;
                    }
                });
            }
            return fn;
        }

        public String toString() {
            return "'Local Symbols Declaration'";
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/CompilationPhase$OptimisticTypeAssignmentPhase.class */
    private static final class OptimisticTypeAssignmentPhase extends CompilationPhase {
        private OptimisticTypeAssignmentPhase() {
            super();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(Compiler compiler, Compiler.CompilationPhases phases, FunctionNode fn) {
            if (compiler.useOptimisticTypes()) {
                return CompilationPhase.transformFunction(fn, new OptimisticTypesCalculator(compiler));
            }
            return fn;
        }

        public String toString() {
            return "'Optimistic Type Assignment'";
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/CompilationPhase$LocalVariableTypeCalculationPhase.class */
    private static final class LocalVariableTypeCalculationPhase extends CompilationPhase {
        private LocalVariableTypeCalculationPhase() {
            super();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(Compiler compiler, Compiler.CompilationPhases phases, FunctionNode fn) {
            FunctionNode newFunctionNode = CompilationPhase.transformFunction(fn, new LocalVariableTypesCalculator(compiler));
            ScriptEnvironment senv = compiler.getScriptEnvironment();
            PrintWriter err = senv.getErr();
            if (senv._print_lower_ast || fn.getFlag(1048576)) {
                err.println("Lower AST for: " + DebugLogger.quote(newFunctionNode.getName()));
                err.println(new ASTWriter(newFunctionNode));
            }
            if (senv._print_lower_parse || fn.getFlag(262144)) {
                err.println("Lower AST for: " + DebugLogger.quote(newFunctionNode.getName()));
                err.println(new PrintVisitor(newFunctionNode));
            }
            return newFunctionNode;
        }

        public String toString() {
            return "'Local Variable Type Calculation'";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/CompilationPhase$ReuseCompileUnitsPhase.class */
    public static final class ReuseCompileUnitsPhase extends CompilationPhase {
        static final /* synthetic */ boolean $assertionsDisabled;

        private ReuseCompileUnitsPhase() {
            super();
        }

        static {
            $assertionsDisabled = !CompilationPhase.class.desiredAssertionStatus();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(Compiler compiler, Compiler.CompilationPhases phases, FunctionNode fn) {
            if ($assertionsDisabled || phases.isRestOfCompilation()) {
                final Map<CompileUnit, CompileUnit> map = new HashMap<>();
                Set<CompileUnit> newUnits = CompileUnit.createCompileUnitSet();
                DebugLogger log = compiler.getLogger();
                log.fine("Clearing bytecode cache");
                compiler.clearBytecode();
                for (CompileUnit oldUnit : compiler.getCompileUnits()) {
                    if (!$assertionsDisabled && map.get(oldUnit) != null) {
                        throw new AssertionError();
                    }
                    CompileUnit newUnit = CompilationPhase.createNewCompileUnit(compiler, phases);
                    log.fine("Creating new compile unit ", oldUnit, " => ", newUnit);
                    map.put(oldUnit, newUnit);
                    if (!$assertionsDisabled && newUnit == null) {
                        throw new AssertionError();
                    }
                    newUnits.add(newUnit);
                }
                log.fine("Replacing compile units in Compiler...");
                compiler.replaceCompileUnits(newUnits);
                log.fine("Done");
                FunctionNode newFunctionNode = CompilationPhase.transformFunction(fn, new ReplaceCompileUnits() { // from class: jdk.nashorn.internal.codegen.CompilationPhase.ReuseCompileUnitsPhase.1
                    @Override // jdk.nashorn.internal.codegen.ReplaceCompileUnits
                    CompileUnit getReplacement(CompileUnit original) {
                        return (CompileUnit) map.get(original);
                    }

                    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
                    public Node leaveDefault(Node node) {
                        return node.ensureUniqueLabels(this.f247lc);
                    }
                });
                return newFunctionNode;
            }
            throw new AssertionError("reuse compile units currently only used for Rest-Of methods");
        }

        public String toString() {
            return "'Reuse Compile Units'";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/CompilationPhase$ReinitializeCachedPhase.class */
    public static final class ReinitializeCachedPhase extends CompilationPhase {
        private ReinitializeCachedPhase() {
            super();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(final Compiler compiler, final Compiler.CompilationPhases phases, FunctionNode fn) {
            final Set<CompileUnit> unitSet = CompileUnit.createCompileUnitSet();
            final Map<CompileUnit, CompileUnit> unitMap = new HashMap<>();
            createCompileUnit(fn.getCompileUnit(), unitSet, unitMap, compiler, phases);
            FunctionNode newFn = CompilationPhase.transformFunction(fn, new ReplaceCompileUnits() { // from class: jdk.nashorn.internal.codegen.CompilationPhase.ReinitializeCachedPhase.1
                @Override // jdk.nashorn.internal.codegen.ReplaceCompileUnits
                CompileUnit getReplacement(CompileUnit oldUnit) {
                    CompileUnit existing = (CompileUnit) unitMap.get(oldUnit);
                    if (existing == null) {
                        return ReinitializeCachedPhase.this.createCompileUnit(oldUnit, unitSet, unitMap, compiler, phases);
                    }
                    return existing;
                }

                @Override // jdk.nashorn.internal.codegen.ReplaceCompileUnits, jdk.nashorn.internal.p001ir.visitor.NodeVisitor
                public Node leaveFunctionNode(FunctionNode fn2) {
                    return super.leaveFunctionNode(compiler.getScriptFunctionData(fn2.getId()).restoreFlags(this.f247lc, fn2));
                }
            });
            compiler.replaceCompileUnits(unitSet);
            return newFn;
        }

        public CompileUnit createCompileUnit(CompileUnit oldUnit, Set<CompileUnit> unitSet, Map<CompileUnit, CompileUnit> unitMap, Compiler compiler, Compiler.CompilationPhases phases) {
            CompileUnit newUnit = CompilationPhase.createNewCompileUnit(compiler, phases);
            unitMap.put(oldUnit, newUnit);
            unitSet.add(newUnit);
            return newUnit;
        }

        public String toString() {
            return "'Reinitialize cached'";
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/CompilationPhase$BytecodeGenerationPhase.class */
    private static final class BytecodeGenerationPhase extends CompilationPhase {
        static final /* synthetic */ boolean $assertionsDisabled;

        private BytecodeGenerationPhase() {
            super();
        }

        static {
            $assertionsDisabled = !CompilationPhase.class.desiredAssertionStatus();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(Compiler compiler, Compiler.CompilationPhases phases, FunctionNode fn) {
            ScriptEnvironment senv = compiler.getScriptEnvironment();
            FunctionNode newFunctionNode = fn;
            fn.getCompileUnit().setUsed();
            compiler.getLogger().fine("Starting bytecode generation for ", DebugLogger.quote(fn.getName()), " - restOf=", Boolean.valueOf(phases.isRestOfCompilation()));
            CodeGenerator codegen = new CodeGenerator(compiler, phases.isRestOfCompilation() ? compiler.getContinuationEntryPoints() : null);
            try {
                newFunctionNode = CompilationPhase.transformFunction(newFunctionNode, codegen);
                codegen.generateScopeCalls();
            } catch (VerifyError e) {
                if (senv._verify_code || senv._print_code) {
                    senv.getErr().println(e.getClass().getSimpleName() + ": " + e.getMessage());
                    if (senv._dump_on_error) {
                        e.printStackTrace(senv.getErr());
                    }
                } else {
                    throw e;
                }
            } catch (Throwable e2) {
                throw new AssertionError("Failed generating bytecode for " + fn.getSourceName() + CallSiteDescriptor.TOKEN_DELIMITER + codegen.getLastLineNumber(), e2);
            }
            for (CompileUnit compileUnit : compiler.getCompileUnits()) {
                ClassEmitter classEmitter = compileUnit.getClassEmitter();
                classEmitter.end();
                if (!compileUnit.isUsed()) {
                    compiler.getLogger().fine("Skipping unused compile unit ", compileUnit);
                } else {
                    byte[] bytecode = classEmitter.toByteArray();
                    if (!$assertionsDisabled && bytecode == null) {
                        throw new AssertionError();
                    }
                    String className = compileUnit.getUnitClassName();
                    compiler.addClass(className, bytecode);
                    CompileUnit.increaseEmitCount();
                    if (senv._verify_code) {
                        compiler.getCodeInstaller().verify(bytecode);
                    }
                    DumpBytecode.dumpBytecode(senv, compiler.getLogger(), bytecode, className);
                }
            }
            return newFunctionNode;
        }

        public String toString() {
            return "'Bytecode Generation'";
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/CompilationPhase$InstallPhase.class */
    private static final class InstallPhase extends CompilationPhase {
        private InstallPhase() {
            super();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(Compiler compiler, Compiler.CompilationPhases phases, FunctionNode fn) {
            DebugLogger log = compiler.getLogger();
            Map<String, Class<?>> installedClasses = new LinkedHashMap<>();
            boolean first = true;
            Class<?> rootClass = null;
            long length = 0;
            CodeInstaller codeInstaller = compiler.getCodeInstaller();
            Map<String, byte[]> bytecode = compiler.getBytecode();
            for (Map.Entry<String, byte[]> entry : bytecode.entrySet()) {
                String className = entry.getKey();
                byte[] code = entry.getValue();
                length += code.length;
                Class<?> clazz = codeInstaller.install(className, code);
                if (first) {
                    rootClass = clazz;
                    first = false;
                }
                installedClasses.put(className, clazz);
            }
            if (rootClass == null) {
                throw new CompilationException("Internal compiler error: root class not found!");
            }
            Object[] constants = compiler.getConstantData().toArray();
            codeInstaller.initialize(installedClasses.values(), compiler.getSource(), constants);
            for (Object constant : constants) {
                if (constant instanceof RecompilableScriptFunctionData) {
                    ((RecompilableScriptFunctionData) constant).initTransients(compiler.getSource(), codeInstaller);
                }
            }
            for (CompileUnit unit : compiler.getCompileUnits()) {
                if (unit.isUsed()) {
                    unit.setCode(installedClasses.get(unit.getUnitClassName()));
                    unit.initializeFunctionsCode();
                }
            }
            if (log.isEnabled()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Installed class '").append(rootClass.getSimpleName()).append('\'').append(" [").append(rootClass.getName()).append(", size=").append(length).append(" bytes, ").append(compiler.getCompileUnits().size()).append(" compile unit(s)]");
                log.fine(sb.toString());
            }
            return fn.setRootClass(null, rootClass);
        }

        public String toString() {
            return "'Class Installation'";
        }
    }

    private CompilationPhase() {
    }

    protected FunctionNode begin(Compiler compiler, FunctionNode functionNode) {
        compiler.getLogger().indent();
        this.startTime = System.nanoTime();
        return functionNode;
    }

    protected FunctionNode end(Compiler compiler, FunctionNode functionNode) {
        compiler.getLogger().unindent();
        this.endTime = System.nanoTime();
        compiler.getScriptEnvironment()._timing.accumulateTime(toString(), this.endTime - this.startTime);
        this.isFinished = true;
        return functionNode;
    }

    boolean isFinished() {
        return this.isFinished;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public final FunctionNode apply(Compiler compiler, Compiler.CompilationPhases phases, FunctionNode functionNode) throws CompilationException {
        if ($assertionsDisabled || phases.contains(this)) {
            return end(compiler, transform(compiler, phases, begin(compiler, functionNode)));
        }
        throw new AssertionError();
    }

    public static FunctionNode transformFunction(FunctionNode fn, NodeVisitor<?> visitor) {
        return (FunctionNode) fn.accept(visitor);
    }

    public static CompileUnit createNewCompileUnit(Compiler compiler, Compiler.CompilationPhases phases) {
        StringBuilder sb = new StringBuilder(compiler.nextCompileUnitName());
        if (phases.isRestOfCompilation()) {
            sb.append("$restOf");
        }
        return compiler.createCompileUnit(sb.toString(), 0L);
    }
}
