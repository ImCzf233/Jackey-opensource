package jdk.nashorn.internal.runtime;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import jdk.internal.dynalink.support.NameCodec;
import jdk.nashorn.internal.codegen.Compiler;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.codegen.FunctionSignature;
import jdk.nashorn.internal.codegen.Namespace;
import jdk.nashorn.internal.codegen.OptimisticTypesPersistence;
import jdk.nashorn.internal.codegen.TypeMap;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.p001ir.Block;
import jdk.nashorn.internal.p001ir.ForNode;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.IdentNode;
import jdk.nashorn.internal.p001ir.LexicalContext;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.SwitchNode;
import jdk.nashorn.internal.p001ir.Symbol;
import jdk.nashorn.internal.p001ir.TryNode;
import jdk.nashorn.internal.p001ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.parser.Parser;
import jdk.nashorn.internal.parser.Token;
import jdk.nashorn.internal.parser.TokenType;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;
import jdk.nashorn.internal.runtime.options.Options;

@Logger(name = "recompile")
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/RecompilableScriptFunctionData.class */
public final class RecompilableScriptFunctionData extends ScriptFunctionData implements Loggable {
    public static final String RECOMPILATION_PREFIX = "Recompilation$";
    private static final ExecutorService astSerializerExecutorService;
    private final int functionNodeId;
    private final String functionName;
    private final int lineNumber;
    private transient Source source;
    private volatile Object cachedAst;
    private final long token;
    private final AllocationStrategy allocationStrategy;
    private final Object endParserState;
    private transient CodeInstaller installer;
    private final Map<Integer, RecompilableScriptFunctionData> nestedFunctions;
    private RecompilableScriptFunctionData parent;
    private final int functionFlags;
    private static final MethodHandles.Lookup LOOKUP;
    private transient DebugLogger log;
    private final Map<String, Integer> externalScopeDepths;
    private final Set<String> internalSymbols;
    private static final int GET_SET_PREFIX_LENGTH;
    private static final long serialVersionUID = 4914839316174633726L;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !RecompilableScriptFunctionData.class.desiredAssertionStatus();
        astSerializerExecutorService = createAstSerializerExecutorService();
        LOOKUP = MethodHandles.lookup();
        GET_SET_PREFIX_LENGTH = "*et ".length();
    }

    public RecompilableScriptFunctionData(FunctionNode functionNode, CodeInstaller installer, AllocationStrategy allocationStrategy, Map<Integer, RecompilableScriptFunctionData> nestedFunctions, Map<String, Integer> externalScopeDepths, Set<String> internalSymbols) {
        super(functionName(functionNode), Math.min(functionNode.getParameters().size(), (int) LinkerCallSite.ARGLIMIT), getDataFlags(functionNode));
        this.functionName = functionNode.getName();
        this.lineNumber = functionNode.getLineNumber();
        this.functionFlags = functionNode.getFlags() | (functionNode.needsCallee() ? 67108864 : 0);
        this.functionNodeId = functionNode.getId();
        this.source = functionNode.getSource();
        this.endParserState = functionNode.getEndParserState();
        this.token = tokenFor(functionNode);
        this.installer = installer;
        this.allocationStrategy = allocationStrategy;
        this.nestedFunctions = smallMap(nestedFunctions);
        this.externalScopeDepths = smallMap(externalScopeDepths);
        this.internalSymbols = smallSet(new HashSet(internalSymbols));
        for (RecompilableScriptFunctionData nfn : nestedFunctions.values()) {
            if (!$assertionsDisabled && nfn.getParent() != null) {
                throw new AssertionError();
            }
            nfn.setParent(this);
        }
        createLogger();
    }

    private static <K, V> Map<K, V> smallMap(Map<K, V> map) {
        if (map == null || map.isEmpty()) {
            return Collections.emptyMap();
        }
        if (map.size() == 1) {
            Map.Entry<K, V> entry = map.entrySet().iterator().next();
            return Collections.singletonMap(entry.getKey(), entry.getValue());
        }
        return map;
    }

    private static <T> Set<T> smallSet(Set<T> set) {
        if (set == null || set.isEmpty()) {
            return Collections.emptySet();
        }
        if (set.size() == 1) {
            return Collections.singleton(set.iterator().next());
        }
        return set;
    }

    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger getLogger() {
        return this.log;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger initLogger(Context ctxt) {
        return ctxt.getLogger(getClass());
    }

    public boolean hasInternalSymbol(String symbolName) {
        return this.internalSymbols.contains(symbolName);
    }

    public int getExternalSymbolDepth(String symbolName) {
        Integer depth = this.externalScopeDepths.get(symbolName);
        if (depth == null) {
            return -1;
        }
        return depth.intValue();
    }

    public Set<String> getExternalSymbolNames() {
        return Collections.unmodifiableSet(this.externalScopeDepths.keySet());
    }

    public Object getEndParserState() {
        return this.endParserState;
    }

    public RecompilableScriptFunctionData getParent() {
        return this.parent;
    }

    void setParent(RecompilableScriptFunctionData parent) {
        this.parent = parent;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // jdk.nashorn.internal.runtime.ScriptFunctionData
    public String toSource() {
        if (this.source == null || this.token == 0) {
            return "function " + (this.name == null ? "" : this.name) + "() { [native code] }";
        }
        return this.source.getString(Token.descPosition(this.token), Token.descLength(this.token));
    }

    public void initTransients(Source src, CodeInstaller inst) {
        if (this.source == null && this.installer == null) {
            this.source = src;
            this.installer = inst;
        } else if (this.source != src || !this.installer.isCompatibleWith(inst)) {
            throw new IllegalArgumentException();
        }
    }

    @Override // jdk.nashorn.internal.runtime.ScriptFunctionData
    public String toString() {
        return super.toString() + '@' + this.functionNodeId;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptFunctionData
    public String toStringVerbose() {
        StringBuilder sb = new StringBuilder();
        sb.append("fnId=").append(this.functionNodeId).append(' ');
        if (this.source != null) {
            sb.append(this.source.getName()).append(':').append(this.lineNumber).append(' ');
        }
        return sb.toString() + super.toString();
    }

    @Override // jdk.nashorn.internal.runtime.ScriptFunctionData
    public String getFunctionName() {
        return this.functionName;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptFunctionData
    public boolean inDynamicContext() {
        return getFunctionFlag(65536);
    }

    private static String functionName(FunctionNode fn) {
        if (fn.isAnonymous()) {
            return "";
        }
        FunctionNode.Kind kind = fn.getKind();
        if (kind == FunctionNode.Kind.GETTER || kind == FunctionNode.Kind.SETTER) {
            String name = NameCodec.decode(fn.getIdent().getName());
            return name.substring(GET_SET_PREFIX_LENGTH);
        }
        return fn.getIdent().getName();
    }

    private static long tokenFor(FunctionNode fn) {
        int position = Token.descPosition(fn.getFirstToken());
        long lastToken = Token.withDelimiter(fn.getLastToken());
        int length = (Token.descPosition(lastToken) - position) + (Token.descType(lastToken) == TokenType.EOL ? 0 : Token.descLength(lastToken));
        return Token.toDesc(TokenType.FUNCTION, position, length);
    }

    private static int getDataFlags(FunctionNode functionNode) {
        int flags = 4;
        if (functionNode.isStrict()) {
            flags = 4 | 1;
        }
        if (functionNode.needsCallee()) {
            flags |= 8;
        }
        if (functionNode.usesThis() || functionNode.hasEval()) {
            flags |= 16;
        }
        if (functionNode.isVarArg()) {
            flags |= 32;
        }
        if (functionNode.getKind() == FunctionNode.Kind.GETTER || functionNode.getKind() == FunctionNode.Kind.SETTER) {
            flags |= 64;
        }
        return flags;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // jdk.nashorn.internal.runtime.ScriptFunctionData
    public PropertyMap getAllocatorMap(ScriptObject prototype) {
        return this.allocationStrategy.getAllocatorMap(prototype);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // jdk.nashorn.internal.runtime.ScriptFunctionData
    public ScriptObject allocate(PropertyMap map) {
        return this.allocationStrategy.allocate(map);
    }

    public FunctionNode reparse() {
        FunctionNode cachedFunction = getCachedAst();
        if (cachedFunction != null) {
            if (!$assertionsDisabled && !cachedFunction.isCached()) {
                throw new AssertionError();
            }
            return cachedFunction;
        }
        int descPosition = Token.descPosition(this.token);
        Context context = Context.getContextTrusted();
        Parser parser = new Parser(context.getEnv(), this.source, new Context.ThrowErrorManager(), isStrict(), this.lineNumber - 1, context.getLogger(Parser.class));
        if (getFunctionFlag(1)) {
            parser.setFunctionName(this.functionName);
        }
        parser.setReparsedFunction(this);
        FunctionNode program = parser.parse(CompilerConstants.PROGRAM.symbolName(), descPosition, Token.descLength(this.token), isPropertyAccessor());
        return (isProgram() ? program : extractFunctionFromScript(program)).setName(null, this.functionName);
    }

    private FunctionNode getCachedAst() {
        Object lCachedAst = this.cachedAst;
        if (lCachedAst instanceof Reference) {
            FunctionNode fn = (FunctionNode) ((Reference) lCachedAst).get();
            if (fn != null) {
                return cloneSymbols(fn);
            }
            return null;
        } else if (lCachedAst instanceof SerializedAst) {
            SerializedAst serializedAst = (SerializedAst) lCachedAst;
            FunctionNode cachedFn = (FunctionNode) serializedAst.cachedAst.get();
            if (cachedFn != null) {
                return cloneSymbols(cachedFn);
            }
            FunctionNode deserializedFn = deserialize(serializedAst.serializedAst);
            serializedAst.cachedAst = new SoftReference(deserializedFn);
            return deserializedFn;
        } else {
            return null;
        }
    }

    public void setCachedAst(FunctionNode astToCache) {
        if ($assertionsDisabled || astToCache.getId() == this.functionNodeId) {
            if (!$assertionsDisabled && (this.cachedAst instanceof SerializedAst)) {
                throw new AssertionError();
            }
            boolean isSplit = astToCache.isSplit();
            if (!$assertionsDisabled && isSplit && this.cachedAst != null) {
                throw new AssertionError();
            }
            final FunctionNode symbolClonedAst = cloneSymbols(astToCache);
            final Reference<FunctionNode> ref = new SoftReference<>(symbolClonedAst);
            this.cachedAst = ref;
            if (isSplit) {
                astSerializerExecutorService.execute(new Runnable() { // from class: jdk.nashorn.internal.runtime.RecompilableScriptFunctionData.1
                    @Override // java.lang.Runnable
                    public void run() {
                        RecompilableScriptFunctionData.this.cachedAst = new SerializedAst(symbolClonedAst, ref);
                    }
                });
                return;
            }
            return;
        }
        throw new AssertionError();
    }

    private static ExecutorService createAstSerializerExecutorService() {
        int threads = Math.max(1, Options.getIntProperty("nashorn.serialize.threads", Runtime.getRuntime().availableProcessors() / 2));
        ThreadPoolExecutor service = new ThreadPoolExecutor(threads, threads, 1L, TimeUnit.MINUTES, new LinkedBlockingDeque(), new ThreadFactory() { // from class: jdk.nashorn.internal.runtime.RecompilableScriptFunctionData.2
            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, "Nashorn AST Serializer");
                t.setDaemon(true);
                t.setPriority(4);
                return t;
            }
        });
        service.allowCoreThreadTimeOut(true);
        return service;
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/RecompilableScriptFunctionData$SerializedAst.class */
    public static class SerializedAst {
        private final byte[] serializedAst;
        private volatile Reference<FunctionNode> cachedAst;

        SerializedAst(FunctionNode fn, Reference<FunctionNode> cachedAst) {
            this.serializedAst = AstSerializer.serialize(fn);
            this.cachedAst = cachedAst;
        }
    }

    private FunctionNode deserialize(byte[] serializedAst) {
        ScriptEnvironment env = this.installer.getContext().getEnv();
        Timing timing = env._timing;
        long t1 = System.nanoTime();
        try {
            FunctionNode initializeDeserialized = AstDeserializer.deserialize(serializedAst).initializeDeserialized(this.source, new Namespace(env.getNamespace()));
            timing.accumulateTime("'Deserialize'", System.nanoTime() - t1);
            return initializeDeserialized;
        } catch (Throwable th) {
            timing.accumulateTime("'Deserialize'", System.nanoTime() - t1);
            throw th;
        }
    }

    private FunctionNode cloneSymbols(FunctionNode fn) {
        final IdentityHashMap<Symbol, Symbol> symbolReplacements = new IdentityHashMap<>();
        final boolean cached = fn.isCached();
        final Set<Symbol> blockDefinedSymbols = (!fn.isSplit() || cached) ? null : Collections.newSetFromMap(new IdentityHashMap());
        FunctionNode newFn = (FunctionNode) fn.accept(new SimpleNodeVisitor() { // from class: jdk.nashorn.internal.runtime.RecompilableScriptFunctionData.3
            private Symbol getReplacement(Symbol original) {
                if (original == null) {
                    return null;
                }
                Symbol existingReplacement = (Symbol) symbolReplacements.get(original);
                if (existingReplacement != null) {
                    return existingReplacement;
                }
                Symbol newReplacement = original.clone();
                symbolReplacements.put(original, newReplacement);
                return newReplacement;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveIdentNode(IdentNode identNode) {
                Symbol oldSymbol = identNode.getSymbol();
                if (oldSymbol != null) {
                    Symbol replacement = getReplacement(oldSymbol);
                    return identNode.setSymbol(replacement);
                }
                return identNode;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveForNode(ForNode forNode) {
                return ensureUniqueLabels(forNode.setIterator(this.f247lc, getReplacement(forNode.getIterator())));
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveSwitchNode(SwitchNode switchNode) {
                return ensureUniqueLabels(switchNode.setTag(this.f247lc, getReplacement(switchNode.getTag())));
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveTryNode(TryNode tryNode) {
                return ensureUniqueLabels(tryNode.setException(this.f247lc, getReplacement(tryNode.getException())));
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public boolean enterBlock(Block block) {
                for (Symbol symbol : block.getSymbols()) {
                    Symbol replacement = getReplacement(symbol);
                    if (blockDefinedSymbols != null) {
                        blockDefinedSymbols.add(replacement);
                    }
                }
                return true;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveBlock(Block block) {
                return ensureUniqueLabels(block.replaceSymbols(this.f247lc, symbolReplacements));
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveFunctionNode(FunctionNode functionNode) {
                return functionNode.setParameters(this.f247lc, functionNode.visitParameters(this));
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveDefault(Node node) {
                return ensureUniqueLabels(node);
            }

            private Node ensureUniqueLabels(Node node) {
                return cached ? node.ensureUniqueLabels(this.f247lc) : node;
            }
        });
        if (blockDefinedSymbols != null) {
            Block newBody = null;
            for (Symbol symbol : symbolReplacements.values()) {
                if (!blockDefinedSymbols.contains(symbol)) {
                    if (!$assertionsDisabled && !symbol.isScope()) {
                        throw new AssertionError();
                    }
                    if (!$assertionsDisabled && !this.externalScopeDepths.containsKey(symbol.getName())) {
                        throw new AssertionError();
                    }
                    symbol.setFlags((symbol.getFlags() & (-4)) | 1);
                    if (newBody == null) {
                        newBody = newFn.getBody().copyWithNewSymbols();
                        newFn = newFn.setBody(null, newBody);
                    }
                    if (!$assertionsDisabled && newBody.getExistingSymbol(symbol.getName()) != null) {
                        throw new AssertionError();
                    }
                    newBody.putSymbol(symbol);
                }
            }
        }
        return newFn.setCached(null);
    }

    private boolean getFunctionFlag(int flag) {
        return (this.functionFlags & flag) != 0;
    }

    private boolean isProgram() {
        return getFunctionFlag(8192);
    }

    public TypeMap typeMap(MethodType fnCallSiteType) {
        if (fnCallSiteType == null || CompiledFunction.isVarArgsType(fnCallSiteType)) {
            return null;
        }
        return new TypeMap(this.functionNodeId, explicitParams(fnCallSiteType), needsCallee());
    }

    private static ScriptObject newLocals(ScriptObject runtimeScope) {
        ScriptObject locals = Global.newEmptyInstance();
        locals.setProto(runtimeScope);
        return locals;
    }

    private Compiler getCompiler(FunctionNode fn, MethodType actualCallSiteType, ScriptObject runtimeScope) {
        return getCompiler(fn, actualCallSiteType, newLocals(runtimeScope), null, null);
    }

    private CodeInstaller getInstallerForNewCode() {
        ScriptEnvironment env = this.installer.getContext().getEnv();
        return (env._optimistic_types || env._loader_per_compile) ? this.installer.withNewLoader() : this.installer;
    }

    public Compiler getCompiler(FunctionNode functionNode, MethodType actualCallSiteType, ScriptObject runtimeScope, Map<Integer, Type> invalidatedProgramPoints, int[] continuationEntryPoints) {
        TypeMap typeMap = typeMap(actualCallSiteType);
        Type[] paramTypes = typeMap == null ? null : typeMap.getParameterTypes(this.functionNodeId);
        Object typeInformationFile = OptimisticTypesPersistence.getLocationDescriptor(this.source, this.functionNodeId, paramTypes);
        return Compiler.forOnDemandCompilation(getInstallerForNewCode(), functionNode.getSource(), isStrict() | functionNode.isStrict(), this, typeMap, getEffectiveInvalidatedProgramPoints(invalidatedProgramPoints, typeInformationFile), typeInformationFile, continuationEntryPoints, runtimeScope);
    }

    private static Map<Integer, Type> getEffectiveInvalidatedProgramPoints(Map<Integer, Type> invalidatedProgramPoints, Object typeInformationFile) {
        if (invalidatedProgramPoints != null) {
            return invalidatedProgramPoints;
        }
        Map<Integer, Type> loadedProgramPoints = OptimisticTypesPersistence.load(typeInformationFile);
        return loadedProgramPoints != null ? loadedProgramPoints : new TreeMap();
    }

    private FunctionInitializer compileTypeSpecialization(MethodType actualCallSiteType, ScriptObject runtimeScope, boolean persist) {
        if (this.log.isEnabled()) {
            this.log.info("Parameter type specialization of '", this.functionName, "' signature: ", actualCallSiteType);
        }
        boolean persistentCache = persist && usePersistentCodeCache();
        String cacheKey = null;
        if (persistentCache) {
            TypeMap typeMap = typeMap(actualCallSiteType);
            Type[] paramTypes = typeMap == null ? null : typeMap.getParameterTypes(this.functionNodeId);
            cacheKey = CodeStore.getCacheKey(Integer.valueOf(this.functionNodeId), paramTypes);
            CodeInstaller newInstaller = getInstallerForNewCode();
            StoredScript script = newInstaller.loadScript(this.source, cacheKey);
            if (script != null) {
                Compiler.updateCompilationId(script.getCompilationId());
                return script.installFunction(this, newInstaller);
            }
        }
        FunctionNode fn = reparse();
        Compiler compiler = getCompiler(fn, actualCallSiteType, runtimeScope);
        FunctionNode compiledFn = compiler.compile(fn, fn.isCached() ? Compiler.CompilationPhases.COMPILE_ALL_CACHED : Compiler.CompilationPhases.COMPILE_ALL);
        if (persist && !compiledFn.hasApplyToCallSpecialization()) {
            compiler.persistClassInfo(cacheKey, compiledFn);
        }
        return new FunctionInitializer(compiledFn, compiler.getInvalidatedProgramPoints());
    }

    public boolean usePersistentCodeCache() {
        return this.installer != null && this.installer.getContext().getEnv()._persistent_cache;
    }

    private MethodType explicitParams(MethodType callSiteType) {
        if (CompiledFunction.isVarArgsType(callSiteType)) {
            return null;
        }
        MethodType noCalleeThisType = callSiteType.dropParameterTypes(0, 2);
        int callSiteParamCount = noCalleeThisType.parameterCount();
        Class<?>[] paramTypes = noCalleeThisType.parameterArray();
        boolean changed = false;
        for (int i = 0; i < paramTypes.length; i++) {
            Class<?> paramType = paramTypes[i];
            if (!paramType.isPrimitive() && paramType != Object.class) {
                paramTypes[i] = Object.class;
                changed = true;
            }
        }
        MethodType generalized = changed ? MethodType.methodType(noCalleeThisType.returnType(), paramTypes) : noCalleeThisType;
        if (callSiteParamCount < getArity()) {
            return generalized.appendParameterTypes(Collections.nCopies(getArity() - callSiteParamCount, Object.class));
        }
        return generalized;
    }

    private FunctionNode extractFunctionFromScript(FunctionNode script) {
        final Set<FunctionNode> fns = new HashSet<>();
        script.getBody().accept(new SimpleNodeVisitor() { // from class: jdk.nashorn.internal.runtime.RecompilableScriptFunctionData.4
            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public boolean enterFunctionNode(FunctionNode fn) {
                fns.add(fn);
                return false;
            }
        });
        if ($assertionsDisabled || fns.size() == 1) {
            FunctionNode f = fns.iterator().next();
            if (!$assertionsDisabled && f.getId() != this.functionNodeId) {
                throw new AssertionError();
            }
            if (!getFunctionFlag(2) && f.isDeclared()) {
                return f.clearFlag((LexicalContext) null, 2);
            }
            return f;
        }
        throw new AssertionError("got back more than one method in recompilation");
    }

    private void logLookup(boolean shouldLog, MethodType targetType) {
        if (shouldLog && this.log.isEnabled()) {
            this.log.info("Looking up ", DebugLogger.quote(this.functionName), " type=", targetType);
        }
    }

    private MethodHandle lookup(FunctionInitializer fnInit, boolean shouldLog) {
        MethodType type = fnInit.getMethodType();
        logLookup(shouldLog, type);
        return lookupCodeMethod(fnInit.getCode(), type);
    }

    public MethodHandle lookup(FunctionNode fn) {
        MethodType type = new FunctionSignature(fn).getMethodType();
        logLookup(true, type);
        return lookupCodeMethod(fn.getCompileUnit().getCode(), type);
    }

    public MethodHandle lookupCodeMethod(Class<?> codeClass, MethodType targetType) {
        return Lookup.f248MH.findStatic(LOOKUP, codeClass, this.functionName, targetType);
    }

    public void initializeCode(FunctionNode functionNode) {
        if (!this.code.isEmpty() || functionNode.getId() != this.functionNodeId || !functionNode.getCompileUnit().isInitializing(this, functionNode)) {
            throw new IllegalStateException(this.name);
        }
        addCode(lookup(functionNode), null, null, functionNode.getFlags());
    }

    public void initializeCode(FunctionInitializer initializer) {
        addCode(lookup(initializer, true), null, null, initializer.getFlags());
    }

    private CompiledFunction addCode(MethodHandle target, Map<Integer, Type> invalidatedProgramPoints, MethodType callSiteType, int fnFlags) {
        CompiledFunction cfn = new CompiledFunction(target, this, invalidatedProgramPoints, callSiteType, fnFlags);
        if ($assertionsDisabled || noDuplicateCode(cfn)) {
            this.code.add(cfn);
            return cfn;
        }
        throw new AssertionError("duplicate code");
    }

    private CompiledFunction addCode(FunctionInitializer fnInit, MethodType callSiteType) {
        if (isVariableArity()) {
            return addCode(lookup(fnInit, true), fnInit.getInvalidatedProgramPoints(), callSiteType, fnInit.getFlags());
        }
        MethodHandle handle = lookup(fnInit, true);
        MethodType fromType = handle.type();
        MethodType toType = (needsCallee(fromType) ? callSiteType.changeParameterType(0, ScriptFunction.class) : callSiteType.dropParameterTypes(0, 1)).changeReturnType(fromType.returnType());
        int toCount = toType.parameterCount();
        int fromCount = fromType.parameterCount();
        int minCount = Math.min(fromCount, toCount);
        for (int i = 0; i < minCount; i++) {
            Class<?> fromParam = fromType.parameterType(i);
            Class<?> toParam = toType.parameterType(i);
            if (fromParam != toParam && !fromParam.isPrimitive() && !toParam.isPrimitive()) {
                if (!$assertionsDisabled && !fromParam.isAssignableFrom(toParam)) {
                    throw new AssertionError();
                }
                toType = toType.changeParameterType(i, fromParam);
            }
        }
        if (fromCount > toCount) {
            toType = toType.appendParameterTypes(fromType.parameterList().subList(toCount, fromCount));
        } else if (fromCount < toCount) {
            toType = toType.dropParameterTypes(fromCount, toCount);
        }
        return addCode(lookup(fnInit, false).asType(toType), fnInit.getInvalidatedProgramPoints(), callSiteType, fnInit.getFlags());
    }

    public Class<?> getReturnType(MethodType callSiteType, ScriptObject runtimeScope) {
        return getBest(callSiteType, runtimeScope, CompiledFunction.NO_FUNCTIONS).type().returnType();
    }

    @Override // jdk.nashorn.internal.runtime.ScriptFunctionData
    synchronized CompiledFunction getBest(MethodType callSiteType, ScriptObject runtimeScope, Collection<CompiledFunction> forbidden, boolean linkLogicOkay) {
        if ($assertionsDisabled || isValidCallSite(callSiteType)) {
            CompiledFunction existingBest = pickFunction(callSiteType, false);
            if (existingBest == null) {
                existingBest = pickFunction(callSiteType, true);
            }
            if (existingBest == null) {
                existingBest = addCode(compileTypeSpecialization(callSiteType, runtimeScope, true), callSiteType);
            }
            if (!$assertionsDisabled && existingBest == null) {
                throw new AssertionError();
            }
            if (existingBest.isApplyToCall()) {
                CompiledFunction best = lookupExactApplyToCall(callSiteType);
                if (best != null) {
                    return best;
                }
                existingBest = addCode(compileTypeSpecialization(callSiteType, runtimeScope, false), callSiteType);
            }
            return existingBest;
        }
        throw new AssertionError(callSiteType);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptFunctionData
    public boolean needsCallee() {
        return getFunctionFlag(67108864);
    }

    public int getFunctionFlags() {
        return this.functionFlags;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptFunctionData
    MethodType getGenericType() {
        if (isVariableArity()) {
            return MethodType.genericMethodType(2, true);
        }
        return MethodType.genericMethodType(2 + getArity());
    }

    public int getFunctionNodeId() {
        return this.functionNodeId;
    }

    public Source getSource() {
        return this.source;
    }

    public RecompilableScriptFunctionData getScriptFunctionData(int functionId) {
        if (functionId == this.functionNodeId) {
            return this;
        }
        RecompilableScriptFunctionData data = this.nestedFunctions == null ? null : this.nestedFunctions.get(Integer.valueOf(functionId));
        if (data != null) {
            return data;
        }
        for (RecompilableScriptFunctionData ndata : this.nestedFunctions.values()) {
            RecompilableScriptFunctionData data2 = ndata.getScriptFunctionData(functionId);
            if (data2 != null) {
                return data2;
            }
        }
        return null;
    }

    public boolean isGlobalSymbol(FunctionNode functionNode, String symbolName) {
        RecompilableScriptFunctionData data = getScriptFunctionData(functionNode.getId());
        if ($assertionsDisabled || data != null) {
            while (!data.hasInternalSymbol(symbolName)) {
                data = data.getParent();
                if (data == null) {
                    return true;
                }
            }
            return false;
        }
        throw new AssertionError();
    }

    public FunctionNode restoreFlags(LexicalContext lc, FunctionNode fn) {
        if ($assertionsDisabled || fn.getId() == this.functionNodeId) {
            FunctionNode newFn = fn.setFlags(lc, this.functionFlags);
            if (newFn.hasNestedEval()) {
                if (!$assertionsDisabled && !newFn.hasScopeBlock()) {
                    throw new AssertionError();
                }
                newFn = newFn.setBody(lc, newFn.getBody().setNeedsScope(null));
            }
            return newFn;
        }
        throw new AssertionError();
    }

    private boolean noDuplicateCode(CompiledFunction compiledFunction) {
        Iterator<CompiledFunction> it = this.code.iterator();
        while (it.hasNext()) {
            CompiledFunction cf = it.next();
            if (cf.type().equals(compiledFunction.type())) {
                return false;
            }
        }
        return true;
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        createLogger();
    }

    private void createLogger() {
        this.log = initLogger(Context.getContextTrusted());
    }
}
