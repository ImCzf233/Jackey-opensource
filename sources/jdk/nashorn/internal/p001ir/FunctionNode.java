package jdk.nashorn.internal.p001ir;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import jdk.nashorn.internal.codegen.CompileUnit;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.codegen.Namespace;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.annotations.Ignore;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.Source;

@Immutable
/* renamed from: jdk.nashorn.internal.ir.FunctionNode */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/FunctionNode.class */
public final class FunctionNode extends LexicalContextExpression implements Flags<FunctionNode>, CompileUnitHolder {
    private static final long serialVersionUID = 1;
    public static final Type FUNCTION_TYPE = Type.typeFor(ScriptFunction.class);
    private final transient Source source;
    private final Object endParserState;
    @Ignore
    private final IdentNode ident;
    private final Block body;
    private final String name;
    private final CompileUnit compileUnit;
    private final Kind kind;
    private final List<IdentNode> parameters;
    private final long firstToken;
    private final long lastToken;
    private final transient Namespace namespace;
    @Ignore
    private final int thisProperties;
    private final int flags;
    private final int lineNumber;
    private final Class<?> rootClass;
    public static final int IS_ANONYMOUS = 1;
    public static final int IS_DECLARED = 2;
    public static final int IS_STRICT = 4;
    public static final int USES_ARGUMENTS = 8;
    public static final int IS_SPLIT = 16;
    public static final int HAS_EVAL = 32;
    public static final int HAS_NESTED_EVAL = 64;
    public static final int HAS_SCOPE_BLOCK = 128;
    public static final int DEFINES_ARGUMENTS = 256;
    public static final int USES_ANCESTOR_SCOPE = 512;
    public static final int HAS_FUNCTION_DECLARATIONS = 1024;
    public static final int IS_DEOPTIMIZABLE = 2048;
    public static final int HAS_APPLY_TO_CALL_SPECIALIZATION = 4096;
    public static final int IS_PROGRAM = 8192;
    public static final int USES_SELF_SYMBOL = 16384;
    public static final int USES_THIS = 32768;
    public static final int IN_DYNAMIC_CONTEXT = 65536;
    public static final int IS_PRINT_PARSE = 131072;
    public static final int IS_PRINT_LOWER_PARSE = 262144;
    public static final int IS_PRINT_AST = 524288;
    public static final int IS_PRINT_LOWER_AST = 1048576;
    public static final int IS_PRINT_SYMBOLS = 2097152;
    public static final int IS_PROFILE = 4194304;
    public static final int IS_TRACE_ENTEREXIT = 8388608;
    public static final int IS_TRACE_MISSES = 16777216;
    public static final int IS_TRACE_VALUES = 33554432;
    public static final int NEEDS_CALLEE = 67108864;
    public static final int IS_CACHED = 134217728;
    public static final int EXTENSION_CALLSITE_FLAGS = 66977792;
    private static final int HAS_DEEP_EVAL = 96;
    private static final int HAS_ALL_VARS_IN_SCOPE = 96;
    private static final int MAYBE_NEEDS_ARGUMENTS = 40;
    public static final int NEEDS_PARENT_SCOPE = 8800;
    private Type returnType;

    /* renamed from: jdk.nashorn.internal.ir.FunctionNode$Kind */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/FunctionNode$Kind.class */
    public enum Kind {
        NORMAL,
        SCRIPT,
        GETTER,
        SETTER
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContextExpression, jdk.nashorn.internal.p001ir.Node
    public /* bridge */ /* synthetic */ Node accept(NodeVisitor nodeVisitor) {
        return super.accept(nodeVisitor);
    }

    public FunctionNode(Source source, int lineNumber, long token, int finish, long firstToken, Namespace namespace, IdentNode ident, String name, List<IdentNode> parameters, Kind kind, int flags) {
        super(token, finish);
        this.returnType = Type.UNKNOWN;
        this.source = source;
        this.lineNumber = lineNumber;
        this.ident = ident;
        this.name = name;
        this.kind = kind;
        this.parameters = parameters;
        this.firstToken = firstToken;
        this.lastToken = token;
        this.namespace = namespace;
        this.flags = flags;
        this.compileUnit = null;
        this.body = null;
        this.thisProperties = 0;
        this.rootClass = null;
        this.endParserState = null;
    }

    private FunctionNode(FunctionNode functionNode, long lastToken, Object endParserState, int flags, String name, Type returnType, CompileUnit compileUnit, Block body, List<IdentNode> parameters, int thisProperties, Class<?> rootClass, Source source, Namespace namespace) {
        super(functionNode);
        this.returnType = Type.UNKNOWN;
        this.endParserState = endParserState;
        this.lineNumber = functionNode.lineNumber;
        this.flags = flags;
        this.name = name;
        this.returnType = returnType;
        this.compileUnit = compileUnit;
        this.lastToken = lastToken;
        this.body = body;
        this.parameters = parameters;
        this.thisProperties = thisProperties;
        this.rootClass = rootClass;
        this.source = source;
        this.namespace = namespace;
        this.ident = functionNode.ident;
        this.kind = functionNode.kind;
        this.firstToken = functionNode.firstToken;
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContextNode
    public Node accept(LexicalContext lc, NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterFunctionNode(this)) {
            return visitor.leaveFunctionNode(setBody(lc, (Block) this.body.accept(visitor)));
        }
        return this;
    }

    public List<IdentNode> visitParameters(NodeVisitor<? extends LexicalContext> visitor) {
        return Node.accept(visitor, this.parameters);
    }

    public int getCallSiteFlags() {
        int callsiteFlags = 0;
        if (getFlag(4)) {
            callsiteFlags = 0 | 2;
        }
        if ((this.flags & EXTENSION_CALLSITE_FLAGS) == 0) {
            return callsiteFlags;
        }
        if (getFlag(4194304)) {
            callsiteFlags |= 64;
        }
        if (getFlag(16777216)) {
            callsiteFlags |= 384;
        }
        if (getFlag(33554432)) {
            callsiteFlags |= 1664;
        }
        if (getFlag(8388608)) {
            callsiteFlags |= 640;
        }
        return callsiteFlags;
    }

    public Source getSource() {
        return this.source;
    }

    public FunctionNode initializeDeserialized(Source source, Namespace namespace) {
        if (source == null || namespace == null) {
            throw new IllegalArgumentException();
        }
        if (this.source == source && this.namespace == namespace) {
            return this;
        }
        if (this.source != null || this.namespace != null) {
            throw new IllegalStateException();
        }
        return new FunctionNode(this, this.lastToken, this.endParserState, this.flags, this.name, this.returnType, this.compileUnit, this.body, this.parameters, this.thisProperties, this.rootClass, source, namespace);
    }

    public int getId() {
        return position();
    }

    public String getSourceName() {
        return getSourceName(this.source);
    }

    public static String getSourceName(Source source) {
        String explicitURL = source.getExplicitURL();
        return explicitURL != null ? explicitURL : source.getName();
    }

    public static int getDirectiveFlag(String directive) {
        boolean z = true;
        switch (directive.hashCode()) {
            case -1523015107:
                if (directive.equals("nashorn callsite trace enterexit")) {
                    z = false;
                    break;
                }
                break;
            case -1374308069:
                if (directive.equals("nashorn callsite trace objects")) {
                    z = true;
                    break;
                }
                break;
            case -262002780:
                if (directive.equals("nashorn print lower parse")) {
                    z = true;
                    break;
                }
                break;
            case 375132083:
                if (directive.equals("nashorn print lower ast")) {
                    z = true;
                    break;
                }
                break;
            case 823663954:
                if (directive.equals("nashorn print ast")) {
                    z = true;
                    break;
                }
                break;
            case 1149106347:
                if (directive.equals("nashorn print symbols")) {
                    z = true;
                    break;
                }
                break;
            case 1280395651:
                if (directive.equals("nashorn print parse")) {
                    z = true;
                    break;
                }
                break;
            case 1402803979:
                if (directive.equals("nashorn callsite profile")) {
                    z = true;
                    break;
                }
                break;
            case 2121912771:
                if (directive.equals("nashorn callsite trace misses")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                return 8388608;
            case true:
                return 16777216;
            case true:
                return 33554432;
            case true:
                return 4194304;
            case true:
                return 131072;
            case true:
                return 262144;
            case true:
                return 524288;
            case true:
                return 1048576;
            case true:
                return 2097152;
            default:
                return 0;
        }
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public String uniqueName(String base) {
        return this.namespace.uniqueName(base);
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean printTypes) {
        sb.append('[').append(this.returnType).append(']').append(' ');
        sb.append("function");
        if (this.ident != null) {
            sb.append(' ');
            this.ident.toString(sb, printTypes);
        }
        sb.append('(');
        Iterator<IdentNode> iter = this.parameters.iterator();
        while (iter.hasNext()) {
            IdentNode parameter = iter.next();
            if (parameter.getSymbol() != null) {
                sb.append('[').append(parameter.getType()).append(']').append(' ');
            }
            parameter.toString(sb, printTypes);
            if (iter.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(')');
    }

    @Override // jdk.nashorn.internal.p001ir.Flags
    public int getFlags() {
        return this.flags;
    }

    @Override // jdk.nashorn.internal.p001ir.Flags
    public boolean getFlag(int flag) {
        return (this.flags & flag) != 0;
    }

    @Override // jdk.nashorn.internal.p001ir.Flags
    public FunctionNode setFlags(LexicalContext lc, int flags) {
        if (this.flags == flags) {
            return this;
        }
        return (FunctionNode) Node.replaceInLexicalContext(lc, this, new FunctionNode(this, this.lastToken, this.endParserState, flags, this.name, this.returnType, this.compileUnit, this.body, this.parameters, this.thisProperties, this.rootClass, this.source, this.namespace));
    }

    @Override // jdk.nashorn.internal.p001ir.Flags
    public FunctionNode clearFlag(LexicalContext lc, int flag) {
        return setFlags(lc, this.flags & (flag ^ (-1)));
    }

    @Override // jdk.nashorn.internal.p001ir.Flags
    public FunctionNode setFlag(LexicalContext lc, int flag) {
        return setFlags(lc, this.flags | flag);
    }

    public boolean isProgram() {
        return getFlag(8192);
    }

    public boolean canBeDeoptimized() {
        return getFlag(2048);
    }

    public boolean hasEval() {
        return getFlag(32);
    }

    public boolean hasNestedEval() {
        return getFlag(64);
    }

    public long getFirstToken() {
        return this.firstToken;
    }

    public boolean hasDeclaredFunctions() {
        return getFlag(1024);
    }

    public boolean needsCallee() {
        return needsParentScope() || usesSelfSymbol() || isSplit() || (needsArguments() && !isStrict()) || hasApplyToCallSpecialization();
    }

    public boolean usesThis() {
        return getFlag(32768);
    }

    public boolean hasApplyToCallSpecialization() {
        return getFlag(4096);
    }

    public IdentNode getIdent() {
        return this.ident;
    }

    public Block getBody() {
        return this.body;
    }

    public FunctionNode setBody(LexicalContext lc, Block body) {
        if (this.body == body) {
            return this;
        }
        return (FunctionNode) Node.replaceInLexicalContext(lc, this, new FunctionNode(this, this.lastToken, this.endParserState, this.flags | (body.needsScope() ? 128 : 0), this.name, this.returnType, this.compileUnit, body, this.parameters, this.thisProperties, this.rootClass, this.source, this.namespace));
    }

    public boolean isVarArg() {
        return needsArguments() || this.parameters.size() > 250;
    }

    public boolean inDynamicContext() {
        return getFlag(65536);
    }

    public boolean needsDynamicScope() {
        return hasEval() && !isStrict();
    }

    public FunctionNode setInDynamicContext(LexicalContext lc) {
        return setFlag(lc, 65536);
    }

    public boolean needsArguments() {
        return getFlag(40) && !getFlag(256) && !isProgram();
    }

    public boolean needsParentScope() {
        return getFlag(8800);
    }

    public FunctionNode setThisProperties(LexicalContext lc, int thisProperties) {
        if (this.thisProperties == thisProperties) {
            return this;
        }
        return (FunctionNode) Node.replaceInLexicalContext(lc, this, new FunctionNode(this, this.lastToken, this.endParserState, this.flags, this.name, this.returnType, this.compileUnit, this.body, this.parameters, thisProperties, this.rootClass, this.source, this.namespace));
    }

    public int getThisProperties() {
        return this.thisProperties;
    }

    public boolean hasScopeBlock() {
        return getFlag(128);
    }

    public Kind getKind() {
        return this.kind;
    }

    public long getLastToken() {
        return this.lastToken;
    }

    public FunctionNode setLastToken(LexicalContext lc, long lastToken) {
        if (this.lastToken == lastToken) {
            return this;
        }
        return (FunctionNode) Node.replaceInLexicalContext(lc, this, new FunctionNode(this, lastToken, this.endParserState, this.flags, this.name, this.returnType, this.compileUnit, this.body, this.parameters, this.thisProperties, this.rootClass, this.source, this.namespace));
    }

    public Object getEndParserState() {
        return this.endParserState;
    }

    public FunctionNode setEndParserState(LexicalContext lc, Object endParserState) {
        if (this.endParserState == endParserState) {
            return this;
        }
        return (FunctionNode) Node.replaceInLexicalContext(lc, this, new FunctionNode(this, this.lastToken, endParserState, this.flags, this.name, this.returnType, this.compileUnit, this.body, this.parameters, this.thisProperties, this.rootClass, this.source, this.namespace));
    }

    public String getName() {
        return this.name;
    }

    public FunctionNode setName(LexicalContext lc, String name) {
        if (this.name.equals(name)) {
            return this;
        }
        return (FunctionNode) Node.replaceInLexicalContext(lc, this, new FunctionNode(this, this.lastToken, this.endParserState, this.flags, name, this.returnType, this.compileUnit, this.body, this.parameters, this.thisProperties, this.rootClass, this.source, this.namespace));
    }

    public boolean allVarsInScope() {
        return getFlag(96);
    }

    public boolean isSplit() {
        return getFlag(16);
    }

    public List<IdentNode> getParameters() {
        return Collections.unmodifiableList(this.parameters);
    }

    public int getNumOfParams() {
        return this.parameters.size();
    }

    public IdentNode getParameter(int index) {
        return this.parameters.get(index);
    }

    public FunctionNode setParameters(LexicalContext lc, List<IdentNode> parameters) {
        if (this.parameters == parameters) {
            return this;
        }
        return (FunctionNode) Node.replaceInLexicalContext(lc, this, new FunctionNode(this, this.lastToken, this.endParserState, this.flags, this.name, this.returnType, this.compileUnit, this.body, parameters, this.thisProperties, this.rootClass, this.source, this.namespace));
    }

    public boolean isDeclared() {
        return getFlag(2);
    }

    public boolean isAnonymous() {
        return getFlag(1);
    }

    public boolean usesSelfSymbol() {
        return getFlag(16384);
    }

    public boolean isNamedFunctionExpression() {
        return !getFlag(8195);
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public Type getType() {
        return FUNCTION_TYPE;
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public Type getWidestOperationType() {
        return FUNCTION_TYPE;
    }

    public Type getReturnType() {
        return this.returnType;
    }

    public FunctionNode setReturnType(LexicalContext lc, Type returnType) {
        Type type = returnType.isObject() ? Type.OBJECT : returnType;
        if (this.returnType == type) {
            return this;
        }
        return (FunctionNode) Node.replaceInLexicalContext(lc, this, new FunctionNode(this, this.lastToken, this.endParserState, this.flags, this.name, type, this.compileUnit, this.body, this.parameters, this.thisProperties, this.rootClass, this.source, this.namespace));
    }

    public boolean isStrict() {
        return getFlag(4);
    }

    public boolean isCached() {
        return getFlag(134217728);
    }

    public FunctionNode setCached(LexicalContext lc) {
        return setFlag(lc, 134217728);
    }

    @Override // jdk.nashorn.internal.p001ir.CompileUnitHolder
    public CompileUnit getCompileUnit() {
        return this.compileUnit;
    }

    public FunctionNode setCompileUnit(LexicalContext lc, CompileUnit compileUnit) {
        if (this.compileUnit == compileUnit) {
            return this;
        }
        return (FunctionNode) Node.replaceInLexicalContext(lc, this, new FunctionNode(this, this.lastToken, this.endParserState, this.flags, this.name, this.returnType, compileUnit, this.body, this.parameters, this.thisProperties, this.rootClass, this.source, this.namespace));
    }

    public Symbol compilerConstant(CompilerConstants cc) {
        return this.body.getExistingSymbol(cc.symbolName());
    }

    public Class<?> getRootClass() {
        return this.rootClass;
    }

    public FunctionNode setRootClass(LexicalContext lc, Class<?> rootClass) {
        if (this.rootClass == rootClass) {
            return this;
        }
        return (FunctionNode) Node.replaceInLexicalContext(lc, this, new FunctionNode(this, this.lastToken, this.endParserState, this.flags, this.name, this.returnType, this.compileUnit, this.body, this.parameters, this.thisProperties, rootClass, this.source, this.namespace));
    }
}
