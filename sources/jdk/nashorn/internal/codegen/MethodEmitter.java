package jdk.nashorn.internal.codegen;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.EnumSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.support.NameCodec;
import jdk.internal.org.objectweb.asm.Handle;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.nashorn.internal.codegen.ClassEmitter;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.codegen.Label;
import jdk.nashorn.internal.codegen.types.ArrayType;
import jdk.nashorn.internal.codegen.types.BitwiseType;
import jdk.nashorn.internal.codegen.types.NumericType;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.objects.NativeArray;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.IdentNode;
import jdk.nashorn.internal.p001ir.JoinPredecessor;
import jdk.nashorn.internal.p001ir.LiteralNode;
import jdk.nashorn.internal.p001ir.LocalVariableConversion;
import jdk.nashorn.internal.p001ir.Symbol;
import jdk.nashorn.internal.p001ir.TryNode;
import jdk.nashorn.internal.runtime.ArgumentSetter;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.Debug;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.RewriteException;
import jdk.nashorn.internal.runtime.Scope;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.UnwarrantedOptimismException;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.options.Options;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/MethodEmitter.class */
public class MethodEmitter {
    private final MethodVisitor method;
    private final ClassEmitter classEmitter;
    protected FunctionNode functionNode;
    private Label.Stack stack;
    private boolean preventUndefinedLoad;
    private final Map<Symbol, LocalVariableDef> localVariableDefs;
    private final Context context;
    static final int LARGE_STRING_THRESHOLD = 32768;
    private final DebugLogger log;
    private final boolean debug;
    private static final int DEBUG_TRACE_LINE;
    private static final Handle LINKERBOOTSTRAP;
    private static final Handle POPULATE_ARRAY_BOOTSTRAP;
    private final CompilerConstants.FieldAccess ERR_STREAM;
    private final CompilerConstants.Call PRINT;
    private final CompilerConstants.Call PRINTLN;
    private final CompilerConstants.Call PRINT_STACKTRACE;
    private static int linePrefix;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !MethodEmitter.class.desiredAssertionStatus();
        String tl = Options.getStringProperty("nashorn.codegen.debug.trace", "-1");
        int line = -1;
        try {
            line = Integer.parseInt(tl);
        } catch (NumberFormatException e) {
        }
        DEBUG_TRACE_LINE = line;
        LINKERBOOTSTRAP = new Handle(6, Bootstrap.BOOTSTRAP.className(), Bootstrap.BOOTSTRAP.name(), Bootstrap.BOOTSTRAP.descriptor());
        POPULATE_ARRAY_BOOTSTRAP = new Handle(6, RewriteException.BOOTSTRAP.className(), RewriteException.BOOTSTRAP.name(), RewriteException.BOOTSTRAP.descriptor());
        linePrefix = 0;
    }

    public MethodEmitter(ClassEmitter classEmitter, MethodVisitor method) {
        this(classEmitter, method, null);
    }

    public MethodEmitter(ClassEmitter classEmitter, MethodVisitor method, FunctionNode functionNode) {
        this.localVariableDefs = new IdentityHashMap();
        this.ERR_STREAM = CompilerConstants.staticField(System.class, "err", PrintStream.class);
        this.PRINT = CompilerConstants.virtualCallNoLookup(PrintStream.class, "print", Void.TYPE, Object.class);
        this.PRINTLN = CompilerConstants.virtualCallNoLookup(PrintStream.class, "println", Void.TYPE, Object.class);
        this.PRINT_STACKTRACE = CompilerConstants.virtualCallNoLookup(Throwable.class, "printStackTrace", Void.TYPE, new Class[0]);
        this.context = classEmitter.getContext();
        this.classEmitter = classEmitter;
        this.method = method;
        this.functionNode = functionNode;
        this.stack = null;
        this.log = this.context.getLogger(CodeGenerator.class);
        this.debug = this.log.isEnabled();
    }

    public void begin() {
        this.classEmitter.beginMethod(this);
        newStack();
        this.method.visitCode();
    }

    public void end() {
        this.method.visitMaxs(0, 0);
        this.method.visitEnd();
        this.classEmitter.endMethod(this);
    }

    public boolean isReachable() {
        return this.stack != null;
    }

    private void doesNotContinueSequentially() {
        this.stack = null;
    }

    private void newStack() {
        this.stack = new Label.Stack();
    }

    public String toString() {
        return "methodEmitter: " + (this.functionNode == null ? this.method : this.functionNode.getName()).toString() + ' ' + Debug.m67id(this);
    }

    void pushType(Type type) {
        if (type != null) {
            this.stack.push(type);
        }
    }

    private Type popType(Type expected) {
        Type type = popType();
        if ($assertionsDisabled || type.isEquivalentTo(expected)) {
            return type;
        }
        throw new AssertionError(type + " is not compatible with " + expected);
    }

    private Type popType() {
        return this.stack.pop();
    }

    private NumericType popNumeric() {
        Type type = popType();
        if (type.isBoolean()) {
            return Type.INT;
        }
        if (!$assertionsDisabled && !type.isNumeric()) {
            throw new AssertionError();
        }
        return (NumericType) type;
    }

    private BitwiseType popBitwise() {
        Type type = popType();
        if (type == Type.BOOLEAN) {
            return Type.INT;
        }
        return (BitwiseType) type;
    }

    private BitwiseType popInteger() {
        Type type = popType();
        if (type == Type.BOOLEAN) {
            return Type.INT;
        }
        if (!$assertionsDisabled && type != Type.INT) {
            throw new AssertionError();
        }
        return (BitwiseType) type;
    }

    private ArrayType popArray() {
        Type type = popType();
        if ($assertionsDisabled || type.isArray()) {
            return (ArrayType) type;
        }
        throw new AssertionError(type);
    }

    public final Type peekType(int pos) {
        return this.stack.peek(pos);
    }

    public final Type peekType() {
        return this.stack.peek();
    }

    public MethodEmitter _new(String classDescriptor, Type type) {
        debug("new", classDescriptor);
        this.method.visitTypeInsn(187, classDescriptor);
        pushType(type);
        return this;
    }

    public MethodEmitter _new(Class<?> clazz) {
        return _new(CompilerConstants.className(clazz), Type.typeFor(clazz));
    }

    public MethodEmitter newInstance(Class<?> clazz) {
        return invoke(CompilerConstants.constructorNoLookup(clazz));
    }

    public MethodEmitter dup(int depth) {
        if (peekType().dup(this.method, depth) == null) {
            return null;
        }
        debug("dup", Integer.valueOf(depth));
        switch (depth) {
            case 0:
                int l0 = this.stack.getTopLocalLoad();
                pushType(peekType());
                this.stack.markLocalLoad(l0);
                break;
            case 1:
                int l02 = this.stack.getTopLocalLoad();
                Type p0 = popType();
                int l1 = this.stack.getTopLocalLoad();
                Type p1 = popType();
                pushType(p0);
                this.stack.markLocalLoad(l02);
                pushType(p1);
                this.stack.markLocalLoad(l1);
                pushType(p0);
                this.stack.markLocalLoad(l02);
                break;
            case 2:
                int l03 = this.stack.getTopLocalLoad();
                Type p02 = popType();
                int l12 = this.stack.getTopLocalLoad();
                Type p12 = popType();
                int l2 = this.stack.getTopLocalLoad();
                Type p2 = popType();
                pushType(p02);
                this.stack.markLocalLoad(l03);
                pushType(p2);
                this.stack.markLocalLoad(l2);
                pushType(p12);
                this.stack.markLocalLoad(l12);
                pushType(p02);
                this.stack.markLocalLoad(l03);
                break;
            default:
                if ($assertionsDisabled) {
                    return null;
                }
                throw new AssertionError("illegal dup depth = " + depth);
        }
        return this;
    }

    public MethodEmitter dup2() {
        debug("dup2");
        if (peekType().isCategory2()) {
            int l0 = this.stack.getTopLocalLoad();
            pushType(peekType());
            this.stack.markLocalLoad(l0);
        } else {
            int l02 = this.stack.getTopLocalLoad();
            Type p0 = popType();
            int l1 = this.stack.getTopLocalLoad();
            Type p1 = popType();
            pushType(p0);
            this.stack.markLocalLoad(l02);
            pushType(p1);
            this.stack.markLocalLoad(l1);
            pushType(p0);
            this.stack.markLocalLoad(l02);
            pushType(p1);
            this.stack.markLocalLoad(l1);
        }
        this.method.visitInsn(92);
        return this;
    }

    public MethodEmitter dup() {
        return dup(0);
    }

    public MethodEmitter pop() {
        debug("pop", peekType());
        popType().pop(this.method);
        return this;
    }

    MethodEmitter pop2() {
        if (peekType().isCategory2()) {
            popType();
        } else {
            get2n();
        }
        return this;
    }

    public MethodEmitter swap() {
        debug("swap");
        int l0 = this.stack.getTopLocalLoad();
        Type p0 = popType();
        int l1 = this.stack.getTopLocalLoad();
        Type p1 = popType();
        p0.swap(this.method, p1);
        pushType(p0);
        this.stack.markLocalLoad(l0);
        pushType(p1);
        this.stack.markLocalLoad(l1);
        return this;
    }

    public void pack() {
        Type type = peekType();
        if (type.isInteger()) {
            convert(ObjectClassGenerator.PRIMITIVE_FIELD_TYPE);
        } else if (!type.isLong()) {
            if (type.isNumber()) {
                invokestatic("java/lang/Double", "doubleToRawLongBits", "(D)J");
            } else if ($assertionsDisabled) {
            } else {
                throw new AssertionError(type + " cannot be packed!");
            }
        }
    }

    public void initializeMethodParameter(Symbol symbol, Type type, Label start) {
        if ($assertionsDisabled || symbol.isBytecodeLocal()) {
            this.localVariableDefs.put(symbol, new LocalVariableDef(start.getLabel(), type));
            return;
        }
        throw new AssertionError();
    }

    MethodEmitter newStringBuilder() {
        return invoke(CompilerConstants.constructorNoLookup(StringBuilder.class)).dup();
    }

    MethodEmitter stringBuilderAppend() {
        convert(Type.STRING);
        return invoke(CompilerConstants.virtualCallNoLookup(StringBuilder.class, "append", StringBuilder.class, String.class));
    }

    public MethodEmitter and() {
        debug("and");
        pushType(get2i().and(this.method));
        return this;
    }

    /* renamed from: or */
    public MethodEmitter m73or() {
        debug("or");
        pushType(get2i().mo72or(this.method));
        return this;
    }

    public MethodEmitter xor() {
        debug("xor");
        pushType(get2i().xor(this.method));
        return this;
    }

    public MethodEmitter shr() {
        debug("shr");
        popInteger();
        pushType(popBitwise().shr(this.method));
        return this;
    }

    public MethodEmitter shl() {
        debug("shl");
        popInteger();
        pushType(popBitwise().shl(this.method));
        return this;
    }

    public MethodEmitter sar() {
        debug("sar");
        popInteger();
        pushType(popBitwise().sar(this.method));
        return this;
    }

    public MethodEmitter neg(int programPoint) {
        debug("neg");
        pushType(popNumeric().neg(this.method, programPoint));
        return this;
    }

    public void _catch(Label recovery) {
        if ($assertionsDisabled || this.stack == null) {
            recovery.onCatch();
            label(recovery);
            beginCatchBlock();
            return;
        }
        throw new AssertionError();
    }

    public void _catch(Collection<Label> recoveries) {
        if ($assertionsDisabled || this.stack == null) {
            for (Label l : recoveries) {
                label(l);
            }
            beginCatchBlock();
            return;
        }
        throw new AssertionError();
    }

    private void beginCatchBlock() {
        if (!isReachable()) {
            newStack();
        }
        pushType(Type.typeFor(Throwable.class));
    }

    private void _try(Label entry, Label exit, Label recovery, String typeDescriptor, boolean isOptimismHandler) {
        recovery.joinFromTry(entry.getStack(), isOptimismHandler);
        this.method.visitTryCatchBlock(entry.getLabel(), exit.getLabel(), recovery.getLabel(), typeDescriptor);
    }

    public void _try(Label entry, Label exit, Label recovery, Class<?> clazz) {
        _try(entry, exit, recovery, CompilerConstants.className(clazz), clazz == UnwarrantedOptimismException.class);
    }

    public void _try(Label entry, Label exit, Label recovery) {
        _try(entry, exit, recovery, null, false);
    }

    public void markLabelAsOptimisticCatchHandler(Label label, int liveLocalCount) {
        label.markAsOptimisticCatchHandler(this.stack, liveLocalCount);
    }

    public MethodEmitter loadConstants() {
        getStatic(this.classEmitter.getUnitClassName(), CompilerConstants.CONSTANTS.symbolName(), CompilerConstants.CONSTANTS.descriptor());
        if ($assertionsDisabled || peekType().isArray()) {
            return this;
        }
        throw new AssertionError(peekType());
    }

    public MethodEmitter loadUndefined(Type type) {
        debug("load undefined ", type);
        pushType(type.loadUndefined(this.method));
        return this;
    }

    MethodEmitter loadForcedInitializer(Type type) {
        debug("load forced initializer ", type);
        pushType(type.loadForcedInitializer(this.method));
        return this;
    }

    public MethodEmitter loadEmpty(Type type) {
        debug("load empty ", type);
        pushType(type.loadEmpty(this.method));
        return this;
    }

    public MethodEmitter loadNull() {
        debug("aconst_null");
        pushType(Type.OBJECT.ldc(this.method, null));
        return this;
    }

    public MethodEmitter loadType(String className) {
        debug("load type", className);
        this.method.visitLdcInsn(jdk.internal.org.objectweb.asm.Type.getObjectType(className));
        pushType(Type.OBJECT);
        return this;
    }

    public MethodEmitter load(boolean b) {
        debug("load boolean", Boolean.valueOf(b));
        pushType(Type.BOOLEAN.ldc(this.method, Boolean.valueOf(b)));
        return this;
    }

    public MethodEmitter load(int i) {
        debug("load int", Integer.valueOf(i));
        pushType(Type.INT.ldc(this.method, Integer.valueOf(i)));
        return this;
    }

    public MethodEmitter load(double d) {
        debug("load double", Double.valueOf(d));
        pushType(Type.NUMBER.ldc(this.method, Double.valueOf(d)));
        return this;
    }

    MethodEmitter load(long l) {
        debug("load long", Long.valueOf(l));
        pushType(Type.LONG.ldc(this.method, Long.valueOf(l)));
        return this;
    }

    MethodEmitter arraylength() {
        debug("arraylength");
        popType(Type.OBJECT);
        pushType(Type.OBJECT_ARRAY.arraylength(this.method));
        return this;
    }

    public MethodEmitter load(String s) {
        debug("load string", s);
        if (s == null) {
            loadNull();
            return this;
        }
        int length = s.length();
        if (length > 32768) {
            _new(StringBuilder.class);
            dup();
            load(length);
            invoke(CompilerConstants.constructorNoLookup(StringBuilder.class, Integer.TYPE));
            int i = 0;
            while (true) {
                int n = i;
                if (n < length) {
                    String part = s.substring(n, Math.min(n + 32768, length));
                    load(part);
                    stringBuilderAppend();
                    i = n + 32768;
                } else {
                    invoke(CompilerConstants.virtualCallNoLookup(StringBuilder.class, "toString", String.class, new Class[0]));
                    return this;
                }
            }
        } else {
            pushType(Type.OBJECT.ldc(this.method, s));
            return this;
        }
    }

    public MethodEmitter load(IdentNode ident) {
        return load(ident.getSymbol(), ident.getType());
    }

    public MethodEmitter load(Symbol symbol, Type type) {
        if ($assertionsDisabled || symbol != null) {
            if (symbol.hasSlot()) {
                int slot = symbol.getSlot(type);
                debug("load symbol", symbol.getName(), " slot=", Integer.valueOf(slot), "type=", type);
                load(type, slot);
            } else if (symbol.isParam()) {
                if (!$assertionsDisabled && !this.functionNode.isVarArg()) {
                    throw new AssertionError("Non-vararg functions have slotted parameters");
                }
                int index = symbol.getFieldIndex();
                if (this.functionNode.needsArguments()) {
                    debug("load symbol", symbol.getName(), " arguments index=", Integer.valueOf(index));
                    loadCompilerConstant(CompilerConstants.ARGUMENTS);
                    load(index);
                    ScriptObject.GET_ARGUMENT.invoke(this);
                } else {
                    debug("load symbol", symbol.getName(), " array index=", Integer.valueOf(index));
                    loadCompilerConstant(CompilerConstants.VARARGS);
                    load(symbol.getFieldIndex());
                    arrayload();
                }
            }
            return this;
        }
        throw new AssertionError();
    }

    public MethodEmitter load(Type type, int slot) {
        debug("explicit load", type, Integer.valueOf(slot));
        Type loadType = type.load(this.method, slot);
        if ($assertionsDisabled || loadType != null) {
            pushType((loadType != Type.OBJECT || !isThisSlot(slot)) ? loadType : Type.THIS);
            if (!$assertionsDisabled && this.preventUndefinedLoad && (slot >= this.stack.localVariableTypes.size() || this.stack.localVariableTypes.get(slot) == Type.UNKNOWN)) {
                throw new AssertionError("Attempted load of uninitialized slot " + slot + " (as type " + type + ")");
            }
            this.stack.markLocalLoad(slot);
            return this;
        }
        throw new AssertionError();
    }

    private boolean isThisSlot(int slot) {
        if (this.functionNode == null) {
            return slot == CompilerConstants.JAVA_THIS.slot();
        }
        int thisSlot = getCompilerConstantSymbol(CompilerConstants.THIS).getSlot(Type.OBJECT);
        if (!$assertionsDisabled && this.functionNode.needsCallee() && thisSlot != 1) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && !this.functionNode.needsCallee() && thisSlot != 0) {
            throw new AssertionError();
        }
        return slot == thisSlot;
    }

    MethodEmitter loadHandle(String className, String methodName, String descName, EnumSet<ClassEmitter.Flag> flags) {
        debug("load handle ");
        pushType(Type.OBJECT.ldc(this.method, new Handle(ClassEmitter.Flag.getValue(flags), className, methodName, descName)));
        return this;
    }

    private Symbol getCompilerConstantSymbol(CompilerConstants cc) {
        return this.functionNode.getBody().getExistingSymbol(cc.symbolName());
    }

    public boolean hasScope() {
        return getCompilerConstantSymbol(CompilerConstants.SCOPE).hasSlot();
    }

    public MethodEmitter loadCompilerConstant(CompilerConstants cc) {
        return loadCompilerConstant(cc, null);
    }

    MethodEmitter loadCompilerConstant(CompilerConstants cc, Type type) {
        if (cc == CompilerConstants.SCOPE && peekType() == Type.SCOPE) {
            dup();
            return this;
        }
        return load(getCompilerConstantSymbol(cc), type != null ? type : getCompilerConstantType(cc));
    }

    public MethodEmitter loadScope() {
        return loadCompilerConstant(CompilerConstants.SCOPE).checkcast(Scope.class);
    }

    public MethodEmitter setSplitState(int state) {
        return loadScope().load(state).invoke(Scope.SET_SPLIT_STATE);
    }

    public void storeCompilerConstant(CompilerConstants cc) {
        storeCompilerConstant(cc, null);
    }

    void storeCompilerConstant(CompilerConstants cc, Type type) {
        Symbol symbol = getCompilerConstantSymbol(cc);
        if (!symbol.hasSlot()) {
            return;
        }
        debug("store compiler constant ", symbol);
        store(symbol, type != null ? type : getCompilerConstantType(cc));
    }

    private static Type getCompilerConstantType(CompilerConstants cc) {
        Class<?> constantType = cc.type();
        if ($assertionsDisabled || constantType != null) {
            return Type.typeFor(constantType);
        }
        throw new AssertionError();
    }

    public MethodEmitter arrayload() {
        debug("Xaload");
        popType(Type.INT);
        pushType(popArray().aload(this.method));
        return this;
    }

    public void arraystore() {
        debug("Xastore");
        Type value = popType();
        Type index = popType(Type.INT);
        if ($assertionsDisabled || index.isInteger()) {
            ArrayType array = popArray();
            if (!$assertionsDisabled && !value.isEquivalentTo(array.getElementType())) {
                throw new AssertionError("Storing " + value + " into " + array);
            }
            if (!$assertionsDisabled && !array.isObject()) {
                throw new AssertionError();
            }
            array.astore(this.method);
            return;
        }
        throw new AssertionError("array index is not integer, but " + index);
    }

    void store(IdentNode ident) {
        Type type = ident.getType();
        Symbol symbol = ident.getSymbol();
        if (type == Type.UNDEFINED) {
            if (!$assertionsDisabled && peekType() != Type.UNDEFINED) {
                throw new AssertionError();
            }
            store(symbol, Type.OBJECT);
            return;
        }
        store(symbol, type);
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/MethodEmitter$LocalVariableDef.class */
    public static class LocalVariableDef {
        private final jdk.internal.org.objectweb.asm.Label label;
        private final Type type;

        LocalVariableDef(jdk.internal.org.objectweb.asm.Label label, Type type) {
            this.label = label;
            this.type = type;
        }
    }

    public void closeLocalVariable(Symbol symbol, Label label) {
        LocalVariableDef def = this.localVariableDefs.get(symbol);
        if (def != null) {
            endLocalValueDef(symbol, def, label.getLabel());
        }
        if (isReachable()) {
            markDeadLocalVariable(symbol);
        }
    }

    public void markDeadLocalVariable(Symbol symbol) {
        if (!symbol.isDead()) {
            markDeadSlots(symbol.getFirstSlot(), symbol.slotCount());
        }
    }

    public void markDeadSlots(int firstSlot, int slotCount) {
        this.stack.markDeadLocalVariables(firstSlot, slotCount);
    }

    private void endLocalValueDef(Symbol symbol, LocalVariableDef def, jdk.internal.org.objectweb.asm.Label label) {
        String name = symbol.getName();
        if (name.equals(CompilerConstants.THIS.symbolName())) {
            name = CompilerConstants.THIS_DEBUGGER.symbolName();
        }
        this.method.visitLocalVariable(name, def.type.getDescriptor(), (String) null, def.label, label, symbol.getSlot(def.type));
    }

    public void store(Symbol symbol, Type type) {
        store(symbol, type, true);
    }

    public void store(Symbol symbol, Type type, boolean onlySymbolLiveValue) {
        if ($assertionsDisabled || symbol != null) {
            if (symbol.hasSlot()) {
                boolean isLiveType = symbol.hasSlotFor(type);
                LocalVariableDef existingDef = this.localVariableDefs.get(symbol);
                if (existingDef == null || existingDef.type != type) {
                    jdk.internal.org.objectweb.asm.Label here = new jdk.internal.org.objectweb.asm.Label();
                    if (isLiveType) {
                        LocalVariableDef newDef = new LocalVariableDef(here, type);
                        this.localVariableDefs.put(symbol, newDef);
                    }
                    this.method.visitLabel(here);
                    if (existingDef != null) {
                        endLocalValueDef(symbol, existingDef, here);
                    }
                }
                if (isLiveType) {
                    int slot = symbol.getSlot(type);
                    debug("store symbol", symbol.getName(), " type=", type, " slot=", Integer.valueOf(slot));
                    storeHidden(type, slot, onlySymbolLiveValue);
                    return;
                }
                if (onlySymbolLiveValue) {
                    markDeadLocalVariable(symbol);
                }
                debug("dead store symbol ", symbol.getName(), " type=", type);
                pop();
                return;
            } else if (symbol.isParam()) {
                if (!$assertionsDisabled && symbol.isScope()) {
                    throw new AssertionError();
                }
                if (!$assertionsDisabled && !this.functionNode.isVarArg()) {
                    throw new AssertionError("Non-vararg functions have slotted parameters");
                }
                int index = symbol.getFieldIndex();
                if (this.functionNode.needsArguments()) {
                    convert(Type.OBJECT);
                    debug("store symbol", symbol.getName(), " arguments index=", Integer.valueOf(index));
                    loadCompilerConstant(CompilerConstants.ARGUMENTS);
                    load(index);
                    ArgumentSetter.SET_ARGUMENT.invoke(this);
                    return;
                }
                convert(Type.OBJECT);
                debug("store symbol", symbol.getName(), " array index=", Integer.valueOf(index));
                loadCompilerConstant(CompilerConstants.VARARGS);
                load(index);
                ArgumentSetter.SET_ARRAY_ELEMENT.invoke(this);
                return;
            } else {
                debug("dead store symbol ", symbol.getName(), " type=", type);
                pop();
                return;
            }
        }
        throw new AssertionError("No symbol to store");
    }

    public void storeHidden(Type type, int slot) {
        storeHidden(type, slot, true);
    }

    public void storeHidden(Type type, int slot, boolean onlyLiveSymbolValue) {
        explicitStore(type, slot);
        this.stack.onLocalStore(type, slot, onlyLiveSymbolValue);
    }

    public void storeTemp(Type type, int slot) {
        explicitStore(type, slot);
        defineTemporaryLocalVariable(slot, slot + type.getSlots());
        onLocalStore(type, slot);
    }

    public void onLocalStore(Type type, int slot) {
        this.stack.onLocalStore(type, slot, true);
    }

    private void explicitStore(Type type, int slot) {
        if ($assertionsDisabled || slot != -1) {
            debug("explicit store", type, Integer.valueOf(slot));
            popType(type);
            type.store(this.method, slot);
            return;
        }
        throw new AssertionError();
    }

    public void defineBlockLocalVariable(int fromSlot, int toSlot) {
        this.stack.defineBlockLocalVariable(fromSlot, toSlot);
    }

    void defineTemporaryLocalVariable(int fromSlot, int toSlot) {
        this.stack.defineTemporaryLocalVariable(fromSlot, toSlot);
    }

    public int defineTemporaryLocalVariable(int width) {
        return this.stack.defineTemporaryLocalVariable(width);
    }

    public void undefineLocalVariables(int fromSlot, boolean canTruncateSymbol) {
        if (isReachable()) {
            this.stack.undefineLocalVariables(fromSlot, canTruncateSymbol);
        }
    }

    public List<Type> getLocalVariableTypes() {
        return this.stack.localVariableTypes;
    }

    public List<Type> getWidestLiveLocals(List<Type> localTypes) {
        return this.stack.getWidestLiveLocals(localTypes);
    }

    public String markSymbolBoundariesInLvarTypesDescriptor(String lvarDescriptor) {
        return this.stack.markSymbolBoundariesInLvarTypesDescriptor(lvarDescriptor);
    }

    public void iinc(int slot, int increment) {
        debug("iinc");
        this.method.visitIincInsn(slot, increment);
    }

    public void athrow() {
        debug("athrow");
        Type receiver = popType(Type.OBJECT);
        if ($assertionsDisabled || Throwable.class.isAssignableFrom(receiver.getTypeClass())) {
            this.method.visitInsn(191);
            doesNotContinueSequentially();
            return;
        }
        throw new AssertionError(receiver.getTypeClass());
    }

    MethodEmitter _instanceof(String classDescriptor) {
        debug("instanceof", classDescriptor);
        popType(Type.OBJECT);
        this.method.visitTypeInsn(193, classDescriptor);
        pushType(Type.INT);
        return this;
    }

    public MethodEmitter _instanceof(Class<?> clazz) {
        return _instanceof(CompilerConstants.className(clazz));
    }

    MethodEmitter checkcast(String classDescriptor) {
        debug("checkcast", classDescriptor);
        if ($assertionsDisabled || peekType().isObject()) {
            this.method.visitTypeInsn(192, classDescriptor);
            return this;
        }
        throw new AssertionError();
    }

    public MethodEmitter checkcast(Class<?> clazz) {
        return checkcast(CompilerConstants.className(clazz));
    }

    public MethodEmitter newarray(ArrayType arrayType) {
        debug("newarray ", "arrayType=", arrayType);
        popType(Type.INT);
        pushType(arrayType.newarray(this.method));
        return this;
    }

    MethodEmitter multinewarray(ArrayType arrayType, int dims) {
        debug("multianewarray ", arrayType, Integer.valueOf(dims));
        for (int i = 0; i < dims; i++) {
            popType(Type.INT);
        }
        pushType(arrayType.newarray(this.method, dims));
        return this;
    }

    private Type fixParamStack(String signature) {
        Type[] params = Type.getMethodArguments(signature);
        for (int i = params.length - 1; i >= 0; i--) {
            popType(params[i]);
        }
        Type returnType = Type.getMethodReturnType(signature);
        return returnType;
    }

    public MethodEmitter invoke(CompilerConstants.Call call) {
        return call.invoke(this);
    }

    private MethodEmitter invoke(int opcode, String className, String methodName, String methodDescriptor, boolean hasReceiver) {
        Type returnType = fixParamStack(methodDescriptor);
        if (hasReceiver) {
            popType(Type.OBJECT);
        }
        this.method.visitMethodInsn(opcode, className, methodName, methodDescriptor, opcode == 185);
        if (returnType != null) {
            pushType(returnType);
        }
        return this;
    }

    public MethodEmitter invokespecial(String className, String methodName, String methodDescriptor) {
        debug("invokespecial", className, ".", methodName, methodDescriptor);
        return invoke(183, className, methodName, methodDescriptor, true);
    }

    public MethodEmitter invokevirtual(String className, String methodName, String methodDescriptor) {
        debug("invokevirtual", className, ".", methodName, methodDescriptor, " ", this.stack);
        return invoke(182, className, methodName, methodDescriptor, true);
    }

    public MethodEmitter invokestatic(String className, String methodName, String methodDescriptor) {
        debug("invokestatic", className, ".", methodName, methodDescriptor);
        invoke(184, className, methodName, methodDescriptor, false);
        return this;
    }

    MethodEmitter invokestatic(String className, String methodName, String methodDescriptor, Type returnType) {
        invokestatic(className, methodName, methodDescriptor);
        popType();
        pushType(returnType);
        return this;
    }

    public MethodEmitter invokeinterface(String className, String methodName, String methodDescriptor) {
        debug("invokeinterface", className, ".", methodName, methodDescriptor);
        return invoke(185, className, methodName, methodDescriptor, true);
    }

    static jdk.internal.org.objectweb.asm.Label[] getLabels(Label... table) {
        jdk.internal.org.objectweb.asm.Label[] internalLabels = new jdk.internal.org.objectweb.asm.Label[table.length];
        for (int i = 0; i < table.length; i++) {
            internalLabels[i] = table[i].getLabel();
        }
        return internalLabels;
    }

    public void lookupswitch(Label defaultLabel, int[] values, Label... table) {
        debug("lookupswitch", peekType());
        adjustStackForSwitch(defaultLabel, table);
        this.method.visitLookupSwitchInsn(defaultLabel.getLabel(), values, getLabels(table));
        doesNotContinueSequentially();
    }

    public void tableswitch(int lo, int hi, Label defaultLabel, Label... table) {
        debug("tableswitch", peekType());
        adjustStackForSwitch(defaultLabel, table);
        this.method.visitTableSwitchInsn(lo, hi, defaultLabel.getLabel(), getLabels(table));
        doesNotContinueSequentially();
    }

    private void adjustStackForSwitch(Label defaultLabel, Label... table) {
        popType(Type.INT);
        joinTo(defaultLabel);
        for (Label label : table) {
            joinTo(label);
        }
    }

    public void conditionalJump(Condition cond, Label trueLabel) {
        conditionalJump(cond, (cond == Condition.GT || cond == Condition.GE) ? false : true, trueLabel);
    }

    public void conditionalJump(Condition cond, boolean isCmpG, Label trueLabel) {
        if (peekType().isCategory2()) {
            debug("[ld]cmp isCmpG=", Boolean.valueOf(isCmpG));
            pushType(get2n().cmp(this.method, isCmpG));
            jump(Condition.toUnary(cond), trueLabel, 1);
            return;
        }
        debug("if", cond);
        jump(Condition.toBinary(cond, peekType().isObject()), trueLabel, 2);
    }

    public void _return(Type type) {
        debug("return", type);
        if ($assertionsDisabled || this.stack.size() == 1) {
            Type stackType = peekType();
            if (!Type.areEquivalent(type, stackType)) {
                convert(type);
            }
            popType(type)._return(this.method);
            doesNotContinueSequentially();
            return;
        }
        throw new AssertionError("Only return value on stack allowed at return point - depth=" + this.stack.size() + " stack = " + this.stack);
    }

    public void _return() {
        _return(peekType());
    }

    public void returnVoid() {
        debug("return [void]");
        if ($assertionsDisabled || this.stack.isEmpty()) {
            this.method.visitInsn(177);
            doesNotContinueSequentially();
            return;
        }
        throw new AssertionError(this.stack);
    }

    MethodEmitter cmp(boolean isCmpG) {
        pushType(get2n().cmp(this.method, isCmpG));
        return this;
    }

    private void jump(int opcode, Label label, int n) {
        for (int i = 0; i < n; i++) {
            if (!$assertionsDisabled && !peekType().isInteger() && !peekType().isBoolean() && !peekType().isObject()) {
                throw new AssertionError("expecting integer type or object for jump, but found " + peekType());
            }
            popType();
        }
        joinTo(label);
        this.method.visitJumpInsn(opcode, label.getLabel());
    }

    public void if_acmpeq(Label label) {
        debug("if_acmpeq", label);
        jump(165, label, 2);
    }

    void if_acmpne(Label label) {
        debug("if_acmpne", label);
        jump(166, label, 2);
    }

    public void ifnull(Label label) {
        debug("ifnull", label);
        jump(198, label, 1);
    }

    public void ifnonnull(Label label) {
        debug("ifnonnull", label);
        jump(199, label, 1);
    }

    public void ifeq(Label label) {
        debug("ifeq ", label);
        jump(153, label, 1);
    }

    void if_icmpeq(Label label) {
        debug("if_icmpeq", label);
        jump(159, label, 2);
    }

    public void ifne(Label label) {
        debug("ifne", label);
        jump(154, label, 1);
    }

    void if_icmpne(Label label) {
        debug("if_icmpne", label);
        jump(160, label, 2);
    }

    void iflt(Label label) {
        debug("iflt", label);
        jump(155, label, 1);
    }

    void if_icmplt(Label label) {
        debug("if_icmplt", label);
        jump(161, label, 2);
    }

    public void ifle(Label label) {
        debug("ifle", label);
        jump(158, label, 1);
    }

    void if_icmple(Label label) {
        debug("if_icmple", label);
        jump(164, label, 2);
    }

    void ifgt(Label label) {
        debug("ifgt", label);
        jump(157, label, 1);
    }

    void if_icmpgt(Label label) {
        debug("if_icmpgt", label);
        jump(163, label, 2);
    }

    void ifge(Label label) {
        debug("ifge", label);
        jump(156, label, 1);
    }

    void if_icmpge(Label label) {
        debug("if_icmpge", label);
        jump(162, label, 2);
    }

    public void _goto(Label label) {
        debug("goto", label);
        jump(167, label, 0);
        doesNotContinueSequentially();
    }

    public void gotoLoopStart(Label loopStart) {
        debug("goto (loop)", loopStart);
        jump(167, loopStart, 0);
    }

    public void uncheckedGoto(Label target) {
        this.method.visitJumpInsn(167, target.getLabel());
    }

    public void canThrow(Label catchLabel) {
        catchLabel.joinFromTry(this.stack, false);
    }

    private void joinTo(Label label) {
        if ($assertionsDisabled || isReachable()) {
            label.joinFrom(this.stack);
            return;
        }
        throw new AssertionError();
    }

    public void label(Label label) {
        breakLabel(label, -1);
    }

    public void breakLabel(Label label, int liveLocals) {
        if (!isReachable()) {
            if (!$assertionsDisabled) {
                if ((label.getStack() == null) == label.isReachable()) {
                    throw new AssertionError();
                }
            }
        } else {
            joinTo(label);
        }
        Label.Stack labelStack = label.getStack();
        this.stack = labelStack == null ? null : labelStack.clone();
        if (this.stack != null && label.isBreakTarget() && liveLocals != -1) {
            if (!$assertionsDisabled && this.stack.firstTemp < liveLocals) {
                throw new AssertionError();
            }
            this.stack.firstTemp = liveLocals;
        }
        debug_label(label);
        this.method.visitLabel(label.getLabel());
    }

    public MethodEmitter convert(Type to) {
        Type from = peekType();
        Type type = from.convert(this.method, to);
        if (type != null) {
            if (!from.isEquivalentTo(to)) {
                debug("convert", from, "->", to);
            }
            if (type != from) {
                int l0 = this.stack.getTopLocalLoad();
                popType();
                pushType(type);
                if (!from.isObject()) {
                    this.stack.markLocalLoad(l0);
                }
            }
        }
        return this;
    }

    private Type get2() {
        Type p0 = popType();
        Type p1 = popType();
        if ($assertionsDisabled || p0.isEquivalentTo(p1)) {
            return p0;
        }
        throw new AssertionError("expecting equivalent types on stack but got " + p0 + " and " + p1);
    }

    private BitwiseType get2i() {
        BitwiseType p0 = popBitwise();
        BitwiseType p1 = popBitwise();
        if ($assertionsDisabled || p0.isEquivalentTo(p1)) {
            return p0;
        }
        throw new AssertionError("expecting equivalent types on stack but got " + p0 + " and " + p1);
    }

    private NumericType get2n() {
        NumericType p0 = popNumeric();
        NumericType p1 = popNumeric();
        if ($assertionsDisabled || p0.isEquivalentTo(p1)) {
            return p0;
        }
        throw new AssertionError("expecting equivalent types on stack but got " + p0 + " and " + p1);
    }

    public MethodEmitter add(int programPoint) {
        debug("add");
        pushType(get2().add(this.method, programPoint));
        return this;
    }

    public MethodEmitter sub(int programPoint) {
        debug("sub");
        pushType(get2n().sub(this.method, programPoint));
        return this;
    }

    public MethodEmitter mul(int programPoint) {
        debug("mul ");
        pushType(get2n().mul(this.method, programPoint));
        return this;
    }

    public MethodEmitter div(int programPoint) {
        debug("div");
        pushType(get2n().div(this.method, programPoint));
        return this;
    }

    public MethodEmitter rem(int programPoint) {
        debug("rem");
        pushType(get2n().rem(this.method, programPoint));
        return this;
    }

    public Type[] getTypesFromStack(int count) {
        return this.stack.getTopTypes(count);
    }

    public int[] getLocalLoadsOnStack(int from, int to) {
        return this.stack.getLocalLoads(from, to);
    }

    public int getStackSize() {
        return this.stack.size();
    }

    public int getFirstTemp() {
        return this.stack.firstTemp;
    }

    public int getUsedSlotsWithLiveTemporaries() {
        return this.stack.getUsedSlotsWithLiveTemporaries();
    }

    private String getDynamicSignature(Type returnType, int argCount) {
        Type[] paramTypes = new Type[argCount];
        int pos = 0;
        for (int i = argCount - 1; i >= 0; i--) {
            int i2 = pos;
            pos++;
            Type pt = this.stack.peek(i2);
            if (ScriptObject.class.isAssignableFrom(pt.getTypeClass()) && !NativeArray.class.isAssignableFrom(pt.getTypeClass())) {
                pt = Type.SCRIPT_OBJECT;
            }
            paramTypes[i] = pt;
        }
        String descriptor = Type.getMethodDescriptor(returnType, paramTypes);
        for (int i3 = 0; i3 < argCount; i3++) {
            popType(paramTypes[(argCount - i3) - 1]);
        }
        return descriptor;
    }

    public MethodEmitter invalidateSpecialName(String name) {
        boolean z = true;
        switch (name.hashCode()) {
            case 3045982:
                if (name.equals("call")) {
                    z = true;
                    break;
                }
                break;
            case 93029230:
                if (name.equals("apply")) {
                    z = false;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
            case true:
                debug("invalidate_name", "name=", name);
                load("Function");
                invoke(ScriptRuntime.INVALIDATE_RESERVED_BUILTIN_NAME);
                break;
        }
        return this;
    }

    MethodEmitter dynamicNew(int argCount, int flags) {
        return dynamicNew(argCount, flags, null);
    }

    public MethodEmitter dynamicNew(int argCount, int flags, String msg) {
        if ($assertionsDisabled || !isOptimistic(flags)) {
            debug("dynamic_new", "argcount=", Integer.valueOf(argCount));
            String signature = getDynamicSignature(Type.OBJECT, argCount);
            this.method.visitInvokeDynamicInsn((msg == null || msg.length() >= 32768) ? "dyn:new" : "dyn:new:" + NameCodec.encode(msg), signature, LINKERBOOTSTRAP, new Object[]{Integer.valueOf(flags)});
            pushType(Type.OBJECT);
            return this;
        }
        throw new AssertionError();
    }

    MethodEmitter dynamicCall(Type returnType, int argCount, int flags) {
        return dynamicCall(returnType, argCount, flags, null);
    }

    public MethodEmitter dynamicCall(Type returnType, int argCount, int flags, String msg) {
        debug("dynamic_call", "args=", Integer.valueOf(argCount), "returnType=", returnType);
        String signature = getDynamicSignature(returnType, argCount);
        debug("   signature", signature);
        this.method.visitInvokeDynamicInsn((msg == null || msg.length() >= 32768) ? "dyn:call" : "dyn:call:" + NameCodec.encode(msg), signature, LINKERBOOTSTRAP, new Object[]{Integer.valueOf(flags)});
        pushType(returnType);
        return this;
    }

    public MethodEmitter dynamicArrayPopulatorCall(int argCount, int startIndex) {
        debug("populate_array", "args=", Integer.valueOf(argCount), "startIndex=", Integer.valueOf(startIndex));
        String signature = getDynamicSignature(Type.OBJECT_ARRAY, argCount);
        this.method.visitInvokeDynamicInsn("populateArray", signature, POPULATE_ARRAY_BOOTSTRAP, new Object[]{Integer.valueOf(startIndex)});
        pushType(Type.OBJECT_ARRAY);
        return this;
    }

    public MethodEmitter dynamicGet(Type valueType, String name, int flags, boolean isMethod, boolean isIndex) {
        if (name.length() > 32768) {
            return load(name).dynamicGetIndex(valueType, flags, isMethod);
        }
        debug("dynamic_get", name, valueType, getProgramPoint(flags));
        Type type = valueType;
        if (type.isObject() || type.isBoolean()) {
            type = Type.OBJECT;
        }
        popType(Type.SCOPE);
        this.method.visitInvokeDynamicInsn(dynGetOperation(isMethod, isIndex) + ':' + NameCodec.encode(name), Type.getMethodDescriptor(type, Type.OBJECT), LINKERBOOTSTRAP, new Object[]{Integer.valueOf(flags)});
        pushType(type);
        convert(valueType);
        return this;
    }

    public void dynamicSet(String name, int flags, boolean isIndex) {
        if (name.length() > 32768) {
            load(name).swap().dynamicSetIndex(flags);
        } else if (!$assertionsDisabled && isOptimistic(flags)) {
            throw new AssertionError();
        } else {
            debug("dynamic_set", name, peekType());
            Type type = peekType();
            if (type.isObject() || type.isBoolean()) {
                type = Type.OBJECT;
                convert(Type.OBJECT);
            }
            popType(type);
            popType(Type.SCOPE);
            this.method.visitInvokeDynamicInsn(dynSetOperation(isIndex) + ':' + NameCodec.encode(name), CompilerConstants.methodDescriptor(Void.TYPE, Object.class, type.getTypeClass()), LINKERBOOTSTRAP, new Object[]{Integer.valueOf(flags)});
        }
    }

    public MethodEmitter dynamicGetIndex(Type result, int flags, boolean isMethod) {
        if ($assertionsDisabled || result.getTypeClass().isPrimitive() || result.getTypeClass() == Object.class) {
            debug("dynamic_get_index", peekType(1), "[", peekType(), "]", getProgramPoint(flags));
            Type resultType = result;
            if (result.isBoolean()) {
                resultType = Type.OBJECT;
            }
            Type index = peekType();
            if (index.isObject() || index.isBoolean()) {
                index = Type.OBJECT;
                convert(Type.OBJECT);
            }
            popType();
            popType(Type.OBJECT);
            String signature = Type.getMethodDescriptor(resultType, Type.OBJECT, index);
            this.method.visitInvokeDynamicInsn(dynGetOperation(isMethod, true), signature, LINKERBOOTSTRAP, new Object[]{Integer.valueOf(flags)});
            pushType(resultType);
            if (result.isBoolean()) {
                convert(Type.BOOLEAN);
            }
            return this;
        }
        throw new AssertionError();
    }

    private static String getProgramPoint(int flags) {
        if ((flags & 8) == 0) {
            return "";
        }
        return "pp=" + String.valueOf((flags & (-2048)) >> 11);
    }

    public void dynamicSetIndex(int flags) {
        if ($assertionsDisabled || !isOptimistic(flags)) {
            debug("dynamic_set_index", peekType(2), "[", peekType(1), "] =", peekType());
            Type value = peekType();
            if (value.isObject() || value.isBoolean()) {
                value = Type.OBJECT;
                convert(Type.OBJECT);
            }
            popType();
            Type index = peekType();
            if (index.isObject() || index.isBoolean()) {
                index = Type.OBJECT;
                convert(Type.OBJECT);
            }
            popType(index);
            Type receiver = popType(Type.OBJECT);
            if (!$assertionsDisabled && !receiver.isObject()) {
                throw new AssertionError();
            }
            this.method.visitInvokeDynamicInsn("dyn:setElem|setProp", CompilerConstants.methodDescriptor(Void.TYPE, receiver.getTypeClass(), index.getTypeClass(), value.getTypeClass()), LINKERBOOTSTRAP, new Object[]{Integer.valueOf(flags)});
            return;
        }
        throw new AssertionError();
    }

    public MethodEmitter loadKey(Object key) {
        if (key instanceof IdentNode) {
            this.method.visitLdcInsn(((IdentNode) key).getName());
        } else if (key instanceof LiteralNode) {
            this.method.visitLdcInsn(((LiteralNode) key).getString());
        } else {
            this.method.visitLdcInsn(JSType.toString(key));
        }
        pushType(Type.OBJECT);
        return this;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static Type fieldType(String desc) {
        boolean z = true;
        switch (desc.hashCode()) {
            case 66:
                if (desc.equals("B")) {
                    z = true;
                    break;
                }
                break;
            case 67:
                if (desc.equals("C")) {
                    z = true;
                    break;
                }
                break;
            case 68:
                if (desc.equals("D")) {
                    z = true;
                    break;
                }
                break;
            case 70:
                if (desc.equals("F")) {
                    z = true;
                    break;
                }
                break;
            case 73:
                if (desc.equals("I")) {
                    z = true;
                    break;
                }
                break;
            case 74:
                if (desc.equals("J")) {
                    z = true;
                    break;
                }
                break;
            case 83:
                if (desc.equals("S")) {
                    z = true;
                    break;
                }
                break;
            case 90:
                if (desc.equals("Z")) {
                    z = false;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
            case true:
            case true:
            case true:
            case true:
                return Type.INT;
            case true:
                if (!$assertionsDisabled) {
                    throw new AssertionError();
                }
                break;
            case true:
                break;
            case true:
                return Type.LONG;
            default:
                if (!$assertionsDisabled && !desc.startsWith("[") && !desc.startsWith("L")) {
                    throw new AssertionError(desc + " is not an object type");
                }
                switch (desc.charAt(0)) {
                    case 'L':
                        return Type.OBJECT;
                    case '[':
                        return Type.typeFor(Array.newInstance(fieldType(desc.substring(1)).getTypeClass(), 0).getClass());
                    default:
                        if ($assertionsDisabled) {
                            return Type.OBJECT;
                        }
                        throw new AssertionError();
                }
        }
        return Type.NUMBER;
    }

    public MethodEmitter getField(CompilerConstants.FieldAccess fa) {
        return fa.get(this);
    }

    void putField(CompilerConstants.FieldAccess fa) {
        fa.put(this);
    }

    public MethodEmitter getField(String className, String fieldName, String fieldDescriptor) {
        debug("getfield", "receiver=", peekType(), className, ".", fieldName, fieldDescriptor);
        Type receiver = popType();
        if ($assertionsDisabled || receiver.isObject()) {
            this.method.visitFieldInsn(180, className, fieldName, fieldDescriptor);
            pushType(fieldType(fieldDescriptor));
            return this;
        }
        throw new AssertionError();
    }

    public MethodEmitter getStatic(String className, String fieldName, String fieldDescriptor) {
        debug("getstatic", className, ".", fieldName, ".", fieldDescriptor);
        this.method.visitFieldInsn(178, className, fieldName, fieldDescriptor);
        pushType(fieldType(fieldDescriptor));
        return this;
    }

    public void putField(String className, String fieldName, String fieldDescriptor) {
        debug("putfield", "receiver=", peekType(1), "value=", peekType());
        popType(fieldType(fieldDescriptor));
        popType(Type.OBJECT);
        this.method.visitFieldInsn(181, className, fieldName, fieldDescriptor);
    }

    public void putStatic(String className, String fieldName, String fieldDescriptor) {
        debug("putfield", "value=", peekType());
        popType(fieldType(fieldDescriptor));
        this.method.visitFieldInsn(179, className, fieldName, fieldDescriptor);
    }

    public void lineNumber(int line) {
        if (this.context.getEnv()._debug_lines) {
            debug_label("[LINE]", Integer.valueOf(line));
            jdk.internal.org.objectweb.asm.Label l = new jdk.internal.org.objectweb.asm.Label();
            this.method.visitLabel(l);
            this.method.visitLineNumber(line, l);
        }
    }

    public void beforeJoinPoint(JoinPredecessor joinPredecessor) {
        LocalVariableConversion localVariableConversion = joinPredecessor.getLocalVariableConversion();
        while (true) {
            LocalVariableConversion next = localVariableConversion;
            if (next != null) {
                Symbol symbol = next.getSymbol();
                if (next.isLive()) {
                    emitLocalVariableConversion(next, true);
                } else {
                    markDeadLocalVariable(symbol);
                }
                localVariableConversion = next.getNext();
            } else {
                return;
            }
        }
    }

    public void beforeTry(TryNode tryNode, Label recovery) {
        LocalVariableConversion localVariableConversion = tryNode.getLocalVariableConversion();
        while (true) {
            LocalVariableConversion next = localVariableConversion;
            if (next != null) {
                if (next.isLive()) {
                    Type to = emitLocalVariableConversion(next, false);
                    recovery.getStack().onLocalStore(to, next.getSymbol().getSlot(to), true);
                }
                localVariableConversion = next.getNext();
            } else {
                return;
            }
        }
    }

    private static String dynGetOperation(boolean isMethod, boolean isIndex) {
        return isMethod ? isIndex ? "dyn:getMethod|getElem|getProp" : "dyn:getMethod|getProp|getElem" : isIndex ? "dyn:getElem|getProp|getMethod" : "dyn:getProp|getElem|getMethod";
    }

    private static String dynSetOperation(boolean isIndex) {
        return isIndex ? "dyn:setElem|setProp" : "dyn:setProp|setElem";
    }

    private Type emitLocalVariableConversion(LocalVariableConversion conversion, boolean onlySymbolLiveValue) {
        Type from = conversion.getFrom();
        Type to = conversion.getTo();
        Symbol symbol = conversion.getSymbol();
        if ($assertionsDisabled || symbol.isBytecodeLocal()) {
            if (from == Type.UNDEFINED) {
                loadUndefined(to);
            } else {
                load(symbol, from).convert(to);
            }
            store(symbol, to, onlySymbolLiveValue);
            return to;
        }
        throw new AssertionError();
    }

    void print() {
        getField(this.ERR_STREAM);
        swap();
        convert(Type.OBJECT);
        invoke(this.PRINT);
    }

    void println() {
        getField(this.ERR_STREAM);
        swap();
        convert(Type.OBJECT);
        invoke(this.PRINTLN);
    }

    void print(String string) {
        getField(this.ERR_STREAM);
        load(string);
        invoke(this.PRINT);
    }

    void println(String string) {
        getField(this.ERR_STREAM);
        load(string);
        invoke(this.PRINTLN);
    }

    void stacktrace() {
        _new(Throwable.class);
        dup();
        invoke(CompilerConstants.constructorNoLookup(Throwable.class));
        invoke(this.PRINT_STACKTRACE);
    }

    private void debug(Object... args) {
        if (this.debug) {
            debug(30, args);
        }
    }

    private void debug(String arg) {
        if (this.debug) {
            debug((Object) 30, (Object) arg);
        }
    }

    private void debug(Object arg0, Object arg1) {
        if (this.debug) {
            debug(30, arg0, arg1);
        }
    }

    private void debug(Object arg0, Object arg1, Object arg2) {
        if (this.debug) {
            debug(30, arg0, arg1, arg2);
        }
    }

    private void debug(Object arg0, Object arg1, Object arg2, Object arg3) {
        if (this.debug) {
            debug(30, arg0, arg1, arg2, arg3);
        }
    }

    private void debug(Object arg0, Object arg1, Object arg2, Object arg3, Object arg4) {
        if (this.debug) {
            debug(30, arg0, arg1, arg2, arg3, arg4);
        }
    }

    private void debug(Object arg0, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
        if (this.debug) {
            debug(30, arg0, arg1, arg2, arg3, arg4, arg5);
        }
    }

    private void debug(Object arg0, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6) {
        if (this.debug) {
            debug(30, arg0, arg1, arg2, arg3, arg4, arg5, arg6);
        }
    }

    private void debug_label(Object... args) {
        if (this.debug) {
            debug(22, args);
        }
    }

    private void debug(int padConstant, Object... args) {
        if (this.debug) {
            StringBuilder sb = new StringBuilder();
            sb.append('#');
            int i = linePrefix + 1;
            linePrefix = i;
            sb.append(i);
            for (int pad = 5 - sb.length(); pad > 0; pad--) {
                sb.append(' ');
            }
            if (isReachable() && !this.stack.isEmpty()) {
                sb.append("{");
                sb.append(this.stack.size());
                sb.append(CallSiteDescriptor.TOKEN_DELIMITER);
                for (int pos = 0; pos < this.stack.size(); pos++) {
                    Type t = this.stack.peek(pos);
                    if (t == Type.SCOPE) {
                        sb.append("scope");
                    } else if (t == Type.THIS) {
                        sb.append("this");
                    } else if (t.isObject()) {
                        String desc = t.getDescriptor();
                        int i2 = 0;
                        while (desc.charAt(i2) == '[' && i2 < desc.length()) {
                            sb.append('[');
                            i2++;
                        }
                        String desc2 = desc.substring(i2);
                        int slash = desc2.lastIndexOf(47);
                        if (slash != -1) {
                            desc2 = desc2.substring(slash + 1, desc2.length() - 1);
                        }
                        if ("Object".equals(desc2)) {
                            sb.append('O');
                        } else {
                            sb.append(desc2);
                        }
                    } else {
                        sb.append(t.getDescriptor());
                    }
                    int loadIndex = this.stack.localLoads[(this.stack.f232sp - 1) - pos];
                    if (loadIndex != -1) {
                        sb.append('(').append(loadIndex).append(')');
                    }
                    if (pos + 1 < this.stack.size()) {
                        sb.append(' ');
                    }
                }
                sb.append('}');
                sb.append(' ');
            }
            for (int pad2 = padConstant - sb.length(); pad2 > 0; pad2--) {
                sb.append(' ');
            }
            for (Object arg : args) {
                sb.append(arg);
                sb.append(' ');
            }
            if (this.context.getEnv() != null) {
                this.log.info(sb);
                if (DEBUG_TRACE_LINE == linePrefix) {
                    new Throwable().printStackTrace(this.log.getOutputStream());
                }
            }
        }
    }

    public void setFunctionNode(FunctionNode functionNode) {
        this.functionNode = functionNode;
    }

    public void setPreventUndefinedLoad() {
        this.preventUndefinedLoad = true;
    }

    private static boolean isOptimistic(int flags) {
        return (flags & 8) != 0;
    }
}
