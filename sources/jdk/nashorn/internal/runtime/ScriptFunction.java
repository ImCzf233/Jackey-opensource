package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.support.Guards;
import jdk.nashorn.internal.codegen.ApplySpecialization;
import jdk.nashorn.internal.codegen.Compiler;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.objects.NativeFunction;
import jdk.nashorn.internal.objects.annotations.SpecializedFunction;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import jdk.nashorn.internal.runtime.linker.NashornCallSiteDescriptor;
import jdk.nashorn.internal.runtime.logging.DebugLogger;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/ScriptFunction.class */
public class ScriptFunction extends ScriptObject {
    public static final MethodHandle G$PROTOTYPE;
    public static final MethodHandle S$PROTOTYPE;
    public static final MethodHandle G$LENGTH;
    public static final MethodHandle G$NAME;
    public static final MethodHandle INVOKE_SYNC;
    static final MethodHandle ALLOCATE;
    private static final MethodHandle WRAPFILTER;
    private static final MethodHandle SCRIPTFUNCTION_GLOBALFILTER;
    public static final CompilerConstants.Call GET_SCOPE;
    private static final MethodHandle IS_FUNCTION_MH;
    private static final MethodHandle IS_APPLY_FUNCTION;
    private static final MethodHandle IS_NONSTRICT_FUNCTION;
    private static final MethodHandle ADD_ZEROTH_ELEMENT;
    private static final MethodHandle WRAP_THIS;
    private static final PropertyMap anonmap$;
    private static final PropertyMap strictmodemap$;
    private static final PropertyMap boundfunctionmap$;
    private static final PropertyMap map$;
    private static final Object LAZY_PROTOTYPE;
    private final ScriptObject scope;
    private final ScriptFunctionData data;
    protected PropertyMap allocatorMap;
    protected Object prototype;
    private static LongAdder constructorCount;
    private static LongAdder invokes;
    private static LongAdder allocations;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !ScriptFunction.class.desiredAssertionStatus();
        G$PROTOTYPE = findOwnMH_S("G$prototype", Object.class, Object.class);
        S$PROTOTYPE = findOwnMH_S("S$prototype", Void.TYPE, Object.class, Object.class);
        G$LENGTH = findOwnMH_S("G$length", Integer.TYPE, Object.class);
        G$NAME = findOwnMH_S("G$name", Object.class, Object.class);
        INVOKE_SYNC = findOwnMH_S("invokeSync", Object.class, ScriptFunction.class, Object.class, Object.class, Object[].class);
        ALLOCATE = findOwnMH_V("allocate", Object.class, new Class[0]);
        WRAPFILTER = findOwnMH_S("wrapFilter", Object.class, Object.class);
        SCRIPTFUNCTION_GLOBALFILTER = findOwnMH_S("globalFilter", Object.class, Object.class);
        GET_SCOPE = CompilerConstants.virtualCallNoLookup(ScriptFunction.class, "getScope", ScriptObject.class, new Class[0]);
        IS_FUNCTION_MH = findOwnMH_S("isFunctionMH", Boolean.TYPE, Object.class, ScriptFunctionData.class);
        IS_APPLY_FUNCTION = findOwnMH_S("isApplyFunction", Boolean.TYPE, Boolean.TYPE, Object.class, Object.class);
        IS_NONSTRICT_FUNCTION = findOwnMH_S("isNonStrictFunction", Boolean.TYPE, Object.class, Object.class, ScriptFunctionData.class);
        ADD_ZEROTH_ELEMENT = findOwnMH_S("addZerothElement", Object[].class, Object[].class, Object.class);
        WRAP_THIS = Lookup.f248MH.findStatic(MethodHandles.lookup(), ScriptFunctionData.class, "wrapThis", Lookup.f248MH.type(Object.class, Object.class));
        LAZY_PROTOTYPE = new Object();
        anonmap$ = PropertyMap.newMap();
        ArrayList<Property> properties = new ArrayList<>(3);
        properties.add(AccessorProperty.create("prototype", 6, G$PROTOTYPE, S$PROTOTYPE));
        properties.add(AccessorProperty.create("length", 7, G$LENGTH, null));
        properties.add(AccessorProperty.create("name", 7, G$NAME, null));
        map$ = PropertyMap.newMap(properties);
        strictmodemap$ = createStrictModeMap(map$);
        boundfunctionmap$ = createBoundFunctionMap(strictmodemap$);
        if (Context.DEBUG) {
            constructorCount = new LongAdder();
            invokes = new LongAdder();
            allocations = new LongAdder();
        }
    }

    private static PropertyMap createStrictModeMap(PropertyMap map) {
        PropertyMap newMap = map.addPropertyNoHistory(map.newUserAccessors("arguments", 6));
        return newMap.addPropertyNoHistory(map.newUserAccessors("caller", 6));
    }

    private static PropertyMap createBoundFunctionMap(PropertyMap strictModeMap) {
        return strictModeMap.deleteProperty(strictModeMap.findProperty("prototype"));
    }

    private static boolean isStrict(int flags) {
        return (flags & 1) != 0;
    }

    private static PropertyMap getMap(boolean strict) {
        return strict ? strictmodemap$ : map$;
    }

    private ScriptFunction(ScriptFunctionData data, PropertyMap map, ScriptObject scope, Global global) {
        super(map);
        if (Context.DEBUG) {
            constructorCount.increment();
        }
        this.data = data;
        this.scope = scope;
        setInitialProto(global.getFunctionPrototype());
        this.prototype = LAZY_PROTOTYPE;
        if ($assertionsDisabled || this.objectSpill == null) {
            if (isStrict() || isBoundFunction()) {
                ScriptFunction typeErrorThrower = global.getTypeErrorThrower();
                initUserAccessors("arguments", 6, typeErrorThrower, typeErrorThrower);
                initUserAccessors("caller", 6, typeErrorThrower, typeErrorThrower);
                return;
            }
            return;
        }
        throw new AssertionError();
    }

    private ScriptFunction(String name, MethodHandle methodHandle, PropertyMap map, ScriptObject scope, Specialization[] specs, int flags, Global global) {
        this(new FinalScriptFunctionData(name, methodHandle, specs, flags), map, scope, global);
    }

    private ScriptFunction(String name, MethodHandle methodHandle, ScriptObject scope, Specialization[] specs, int flags) {
        this(name, methodHandle, getMap(isStrict(flags)), scope, specs, flags, Global.instance());
    }

    public ScriptFunction(String name, MethodHandle invokeHandle, Specialization[] specs) {
        this(name, invokeHandle, map$, null, specs, 6, Global.instance());
    }

    public ScriptFunction(String name, MethodHandle invokeHandle, PropertyMap map, Specialization[] specs) {
        this(name, invokeHandle, map.addAll(map$), null, specs, 6, Global.instance());
    }

    public static ScriptFunction create(Object[] constants, int index, ScriptObject scope) {
        RecompilableScriptFunctionData data = (RecompilableScriptFunctionData) constants[index];
        return new ScriptFunction(data, getMap(data.isStrict()), scope, Global.instance());
    }

    public static ScriptFunction create(Object[] constants, int index) {
        return create(constants, index, null);
    }

    public static ScriptFunction createAnonymous() {
        return new ScriptFunction("", GlobalFunctions.ANONYMOUS, anonmap$, (Specialization[]) null);
    }

    private static ScriptFunction createBuiltin(String name, MethodHandle methodHandle, Specialization[] specs, int flags) {
        ScriptFunction func = new ScriptFunction(name, methodHandle, (ScriptObject) null, specs, flags);
        func.setPrototype(ScriptRuntime.UNDEFINED);
        func.deleteOwnProperty(func.getMap().findProperty("prototype"));
        return func;
    }

    public static ScriptFunction createBuiltin(String name, MethodHandle methodHandle, Specialization[] specs) {
        return createBuiltin(name, methodHandle, specs, 2);
    }

    public static ScriptFunction createBuiltin(String name, MethodHandle methodHandle) {
        return createBuiltin(name, methodHandle, null);
    }

    public static ScriptFunction createStrictBuiltin(String name, MethodHandle methodHandle) {
        return createBuiltin(name, methodHandle, null, 3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/ScriptFunction$Bound.class */
    public static class Bound extends ScriptFunction {
        private final ScriptFunction target;

        Bound(ScriptFunctionData boundData, ScriptFunction target) {
            super(boundData, ScriptFunction.boundfunctionmap$, (ScriptObject) null, Global.instance());
            setPrototype(ScriptRuntime.UNDEFINED);
            this.target = target;
        }

        @Override // jdk.nashorn.internal.runtime.ScriptFunction
        protected ScriptFunction getTargetFunction() {
            return this.target;
        }
    }

    public final ScriptFunction createBound(Object self, Object[] args) {
        return new Bound(this.data.makeBoundFunctionData(this, self, args), getTargetFunction());
    }

    public final ScriptFunction createSynchronized(Object sync) {
        MethodHandle mh = Lookup.f248MH.insertArguments(INVOKE_SYNC, 0, this, sync);
        return createBuiltin(getName(), mh);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public String getClassName() {
        return "Function";
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public boolean isInstance(ScriptObject instance) {
        Object basePrototype = getTargetFunction().getPrototype();
        if (!(basePrototype instanceof ScriptObject)) {
            throw ECMAErrors.typeError("prototype.not.an.object", ScriptRuntime.safeToString(getTargetFunction()), ScriptRuntime.safeToString(basePrototype));
        }
        ScriptObject proto = instance.getProto();
        while (true) {
            ScriptObject proto2 = proto;
            if (proto2 != null) {
                if (proto2 != basePrototype) {
                    proto = proto2.getProto();
                } else {
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    protected ScriptFunction getTargetFunction() {
        return this;
    }

    public final boolean isBoundFunction() {
        return getTargetFunction() != this;
    }

    public final void setArity(int arity) {
        this.data.setArity(arity);
    }

    public final boolean isStrict() {
        return this.data.isStrict();
    }

    public final boolean needsWrappedThis() {
        return this.data.needsWrappedThis();
    }

    private static boolean needsWrappedThis(Object fn) {
        if (fn instanceof ScriptFunction) {
            return ((ScriptFunction) fn).needsWrappedThis();
        }
        return false;
    }

    public final Object invoke(Object self, Object... arguments) throws Throwable {
        if (Context.DEBUG) {
            invokes.increment();
        }
        return this.data.invoke(this, self, arguments);
    }

    public final Object construct(Object... arguments) throws Throwable {
        return this.data.construct(this, arguments);
    }

    private Object allocate() {
        if (Context.DEBUG) {
            allocations.increment();
        }
        if ($assertionsDisabled || !isBoundFunction()) {
            ScriptObject prototype = getAllocatorPrototype();
            ScriptObject object = this.data.allocate(getAllocatorMap(prototype));
            if (object != null) {
                object.setInitialProto(prototype);
            }
            return object;
        }
        throw new AssertionError();
    }

    private synchronized PropertyMap getAllocatorMap(ScriptObject prototype) {
        if (this.allocatorMap == null || this.allocatorMap.isInvalidSharedMapFor(prototype)) {
            this.allocatorMap = this.data.getAllocatorMap(prototype);
        }
        return this.allocatorMap;
    }

    private ScriptObject getAllocatorPrototype() {
        Object prototype = getPrototype();
        if (prototype instanceof ScriptObject) {
            return (ScriptObject) prototype;
        }
        return Global.objectPrototype();
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public final String safeToString() {
        return toSource();
    }

    public final String toString() {
        return this.data.toString();
    }

    public final String toSource() {
        return this.data.toSource();
    }

    public final Object getPrototype() {
        if (this.prototype == LAZY_PROTOTYPE) {
            this.prototype = new PrototypeObject(this);
        }
        return this.prototype;
    }

    public final synchronized void setPrototype(Object newPrototype) {
        if ((newPrototype instanceof ScriptObject) && newPrototype != this.prototype && this.allocatorMap != null) {
            this.allocatorMap = null;
        }
        this.prototype = newPrototype;
    }

    public final MethodHandle getBoundInvokeHandle(Object self) {
        return Lookup.f248MH.bindTo(bindToCalleeIfNeeded(this.data.getGenericInvoker(this.scope)), self);
    }

    private MethodHandle bindToCalleeIfNeeded(MethodHandle methodHandle) {
        return ScriptFunctionData.needsCallee(methodHandle) ? Lookup.f248MH.bindTo(methodHandle, this) : methodHandle;
    }

    public final String getName() {
        return this.data.getName();
    }

    public final ScriptObject getScope() {
        return this.scope;
    }

    public static Object G$prototype(Object self) {
        return self instanceof ScriptFunction ? ((ScriptFunction) self).getPrototype() : ScriptRuntime.UNDEFINED;
    }

    public static void S$prototype(Object self, Object prototype) {
        if (self instanceof ScriptFunction) {
            ((ScriptFunction) self).setPrototype(prototype);
        }
    }

    public static int G$length(Object self) {
        if (self instanceof ScriptFunction) {
            return ((ScriptFunction) self).data.getArity();
        }
        return 0;
    }

    public static Object G$name(Object self) {
        if (self instanceof ScriptFunction) {
            return ((ScriptFunction) self).getName();
        }
        return ScriptRuntime.UNDEFINED;
    }

    public static ScriptObject getPrototype(ScriptFunction constructor) {
        if (constructor != null) {
            Object proto = constructor.getPrototype();
            if (proto instanceof ScriptObject) {
                return (ScriptObject) proto;
            }
            return null;
        }
        return null;
    }

    public static long getConstructorCount() {
        return constructorCount.longValue();
    }

    public static long getInvokes() {
        return invokes.longValue();
    }

    public static long getAllocations() {
        return allocations.longValue();
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    protected GuardedInvocation findNewMethod(CallSiteDescriptor desc, LinkRequest request) {
        MethodType type = desc.getMethodType();
        if ($assertionsDisabled || (desc.getMethodType().returnType() == Object.class && !NashornCallSiteDescriptor.isOptimistic(desc))) {
            CompiledFunction cf = this.data.getBestConstructor(type, this.scope, CompiledFunction.NO_FUNCTIONS);
            GuardedInvocation bestCtorInv = cf.createConstructorInvocation();
            return new GuardedInvocation(pairArguments(bestCtorInv.getInvocation(), type), getFunctionGuard(this, cf.getFlags()), bestCtorInv.getSwitchPoints(), (Class<? extends Throwable>) null);
        }
        throw new AssertionError();
    }

    private static Object wrapFilter(Object obj) {
        if ((obj instanceof ScriptObject) || !ScriptFunctionData.isPrimitiveThis(obj)) {
            return obj;
        }
        return Context.getGlobal().wrapAsObject(obj);
    }

    private static Object globalFilter(Object object) {
        return Context.getGlobal();
    }

    private static SpecializedFunction.LinkLogic getLinkLogic(Object self, Class<? extends SpecializedFunction.LinkLogic> linkLogicClass) {
        if (linkLogicClass == null) {
            return SpecializedFunction.LinkLogic.EMPTY_INSTANCE;
        }
        if (!Context.getContextTrusted().getEnv()._optimistic_types) {
            return null;
        }
        Object wrappedSelf = wrapFilter(self);
        if (wrappedSelf instanceof OptimisticBuiltins) {
            if (wrappedSelf != self && ((OptimisticBuiltins) wrappedSelf).hasPerInstanceAssumptions()) {
                return null;
            }
            return ((OptimisticBuiltins) wrappedSelf).getLinkLogic(linkLogicClass);
        }
        return null;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    protected GuardedInvocation findCallMethod(CallSiteDescriptor desc, LinkRequest request) {
        MethodHandle boundHandle;
        MethodHandle handle;
        MethodType type = desc.getMethodType();
        String name = getName();
        boolean isUnstable = request.isCallSiteUnstable();
        boolean scopeCall = NashornCallSiteDescriptor.isScope(desc);
        boolean isCall = !scopeCall && this.data.isBuiltin() && "call".equals(name);
        boolean isApply = !scopeCall && this.data.isBuiltin() && "apply".equals(name);
        boolean isApplyOrCall = isCall | isApply;
        if (isUnstable && !isApplyOrCall) {
            if (type.parameterCount() == 3 && type.parameterType(2) == Object[].class) {
                handle = ScriptRuntime.APPLY.methodHandle();
            } else {
                handle = Lookup.f248MH.asCollector(ScriptRuntime.APPLY.methodHandle(), Object[].class, type.parameterCount() - 2);
            }
            return new GuardedInvocation(handle, (MethodHandle) null, (SwitchPoint) null, ClassCastException.class);
        }
        MethodHandle guard = null;
        if (isApplyOrCall && !isUnstable) {
            Object[] args = request.getArguments();
            if (Bootstrap.isCallable(args[1])) {
                return createApplyOrCallCall(isApply, desc, request, args);
            }
        }
        int programPoint = -1;
        if (NashornCallSiteDescriptor.isOptimistic(desc)) {
            programPoint = NashornCallSiteDescriptor.getProgramPoint(desc);
        }
        CompiledFunction cf = this.data.getBestInvoker(type, this.scope, CompiledFunction.NO_FUNCTIONS);
        Object self = request.getArguments()[1];
        Collection<CompiledFunction> forbidden = new HashSet<>();
        List<SwitchPoint> sps = new ArrayList<>();
        Class<? extends Throwable> exceptionGuard = null;
        while (true) {
            if (!cf.isSpecialization()) {
                break;
            }
            Class<? extends SpecializedFunction.LinkLogic> linkLogicClass = cf.getLinkLogicClass();
            SpecializedFunction.LinkLogic linkLogic = getLinkLogic(self, linkLogicClass);
            if (linkLogic != null && linkLogic.checkLinkable(self, desc, request)) {
                DebugLogger log = Context.getContextTrusted().getLogger(Compiler.class);
                if (log.isEnabled()) {
                    log.info("Linking optimistic builtin function: '", name, "' args=", Arrays.toString(request.getArguments()), " desc=", desc);
                }
                exceptionGuard = linkLogic.getRelinkException();
            } else {
                forbidden.add(cf);
                CompiledFunction oldCf = cf;
                cf = this.data.getBestInvoker(type, this.scope, forbidden);
                if (!$assertionsDisabled && oldCf == cf) {
                    throw new AssertionError();
                }
            }
        }
        GuardedInvocation bestInvoker = cf.createFunctionInvocation(type.returnType(), programPoint);
        MethodHandle callHandle = bestInvoker.getInvocation();
        if (this.data.needsCallee()) {
            if (scopeCall && needsWrappedThis()) {
                boundHandle = Lookup.f248MH.filterArguments(callHandle, 1, SCRIPTFUNCTION_GLOBALFILTER);
            } else {
                boundHandle = callHandle;
            }
        } else if (this.data.isBuiltin() && "extend".equals(this.data.getName())) {
            boundHandle = Lookup.f248MH.dropArguments(Lookup.f248MH.bindTo(callHandle, desc.getLookup()), 0, type.parameterType(0), type.parameterType(1));
        } else if (scopeCall && needsWrappedThis()) {
            boundHandle = Lookup.f248MH.dropArguments(Lookup.f248MH.filterArguments(callHandle, 0, SCRIPTFUNCTION_GLOBALFILTER), 0, type.parameterType(0));
        } else {
            boundHandle = Lookup.f248MH.dropArguments(callHandle, 0, type.parameterType(0));
        }
        if (!scopeCall && needsWrappedThis()) {
            if (ScriptFunctionData.isPrimitiveThis(request.getArguments()[1])) {
                boundHandle = Lookup.f248MH.filterArguments(boundHandle, 1, WRAPFILTER);
            } else {
                guard = getNonStrictFunctionGuard(this);
            }
        }
        if (isUnstable && NashornCallSiteDescriptor.isApplyToCall(desc)) {
            boundHandle = Lookup.f248MH.asCollector(boundHandle, Object[].class, type.parameterCount() - 2);
        }
        MethodHandle boundHandle2 = pairArguments(boundHandle, type);
        if (bestInvoker.getSwitchPoints() != null) {
            sps.addAll(Arrays.asList(bestInvoker.getSwitchPoints()));
        }
        SwitchPoint[] spsArray = sps.isEmpty() ? null : (SwitchPoint[]) sps.toArray(new SwitchPoint[sps.size()]);
        return new GuardedInvocation(boundHandle2, guard == null ? getFunctionGuard(this, cf.getFlags()) : guard, spsArray, exceptionGuard);
    }

    private GuardedInvocation createApplyOrCallCall(boolean isApply, CallSiteDescriptor desc, LinkRequest request, Object[] args) {
        MethodType descType = desc.getMethodType();
        int paramCount = descType.parameterCount();
        if (descType.parameterType(paramCount - 1).isArray()) {
            return createVarArgApplyOrCallCall(isApply, desc, request, args);
        }
        boolean passesThis = paramCount > 2;
        boolean passesArgs = paramCount > 3;
        int realArgCount = passesArgs ? paramCount - 3 : 0;
        Object appliedFn = args[1];
        boolean appliedFnNeedsWrappedThis = needsWrappedThis(appliedFn);
        SwitchPoint applyToCallSwitchPoint = Global.getBuiltinFunctionApplySwitchPoint();
        boolean isApplyToCall = NashornCallSiteDescriptor.isApplyToCall(desc);
        boolean isFailedApplyToCall = isApplyToCall && applyToCallSwitchPoint.hasBeenInvalidated();
        MethodType appliedType = descType.dropParameterTypes(0, 1);
        if (!passesThis) {
            appliedType = appliedType.insertParameterTypes(1, Object.class);
        } else if (appliedFnNeedsWrappedThis) {
            appliedType = appliedType.changeParameterType(1, Object.class);
        }
        MethodType dropArgs = Lookup.f248MH.type(Void.TYPE, new Class[0]);
        if (isApply && !isFailedApplyToCall) {
            int pc = appliedType.parameterCount();
            for (int i = 3; i < pc; i++) {
                dropArgs = dropArgs.appendParameterTypes(appliedType.parameterType(i));
            }
            if (pc > 3) {
                appliedType = appliedType.dropParameterTypes(3, pc);
            }
        }
        if (isApply || isFailedApplyToCall) {
            if (passesArgs) {
                appliedType = appliedType.changeParameterType(2, Object[].class);
                if (isFailedApplyToCall) {
                    appliedType = appliedType.dropParameterTypes(3, paramCount - 1);
                }
            } else {
                appliedType = appliedType.insertParameterTypes(2, Object[].class);
            }
        }
        CallSiteDescriptor appliedDesc = desc.changeMethodType(appliedType);
        Object[] appliedArgs = new Object[isApply ? 3 : appliedType.parameterCount()];
        appliedArgs[0] = appliedFn;
        appliedArgs[1] = passesThis ? appliedFnNeedsWrappedThis ? ScriptFunctionData.wrapThis(args[2]) : args[2] : ScriptRuntime.UNDEFINED;
        if (isApply && !isFailedApplyToCall) {
            appliedArgs[2] = passesArgs ? NativeFunction.toApplyArgs(args[3]) : ScriptRuntime.EMPTY_ARRAY;
        } else if (passesArgs) {
            if (isFailedApplyToCall) {
                Object[] tmp = new Object[args.length - 3];
                System.arraycopy(args, 3, tmp, 0, tmp.length);
                appliedArgs[2] = NativeFunction.toApplyArgs(tmp);
            } else if (!$assertionsDisabled && isApply) {
                throw new AssertionError();
            } else {
                System.arraycopy(args, 3, appliedArgs, 2, args.length - 3);
            }
        } else if (isFailedApplyToCall) {
            appliedArgs[2] = ScriptRuntime.EMPTY_ARRAY;
        }
        LinkRequest appliedRequest = request.replaceArguments(appliedDesc, appliedArgs);
        try {
            GuardedInvocation appliedInvocation = Bootstrap.getLinkerServices().getGuardedInvocation(appliedRequest);
            if (!$assertionsDisabled && appliedRequest == null) {
                throw new AssertionError();
            }
            Class<?> applyFnType = descType.parameterType(0);
            MethodHandle inv = appliedInvocation.getInvocation();
            if (isApply && !isFailedApplyToCall) {
                inv = passesArgs ? Lookup.f248MH.filterArguments(inv, 2, NativeFunction.TO_APPLY_ARGS) : Lookup.f248MH.insertArguments(inv, 2, ScriptRuntime.EMPTY_ARRAY);
            }
            if (isApplyToCall) {
                if (isFailedApplyToCall) {
                    Context.getContextTrusted().getLogger(ApplySpecialization.class).info("Collection arguments to revert call to apply in " + appliedFn);
                    inv = Lookup.f248MH.asCollector(inv, Object[].class, realArgCount);
                } else {
                    appliedInvocation = appliedInvocation.addSwitchPoint(applyToCallSwitchPoint);
                }
            }
            if (!passesThis) {
                inv = bindImplicitThis(appliedFn, inv);
            } else if (appliedFnNeedsWrappedThis) {
                inv = Lookup.f248MH.filterArguments(inv, 1, WRAP_THIS);
            }
            MethodHandle inv2 = Lookup.f248MH.dropArguments(inv, 0, applyFnType);
            for (int i2 = 0; i2 < dropArgs.parameterCount(); i2++) {
                inv2 = Lookup.f248MH.dropArguments(inv2, 4 + i2, dropArgs.parameterType(i2));
            }
            MethodHandle guard = appliedInvocation.getGuard();
            if (!passesThis && guard.type().parameterCount() > 1) {
                guard = bindImplicitThis(appliedFn, guard);
            }
            MethodType guardType = guard.type();
            MethodHandle guard2 = Lookup.f248MH.dropArguments(guard, 0, descType.parameterType(0));
            MethodHandle applyFnGuard = Lookup.f248MH.insertArguments(IS_APPLY_FUNCTION, 2, this);
            return appliedInvocation.replaceMethods(inv2, Lookup.f248MH.foldArguments(Lookup.f248MH.dropArguments(applyFnGuard, 2, guardType.parameterArray()), guard2));
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    private GuardedInvocation createVarArgApplyOrCallCall(boolean isApply, CallSiteDescriptor desc, LinkRequest request, Object[] args) {
        MethodType descType = desc.getMethodType();
        int paramCount = descType.parameterCount();
        Object[] varArgs = (Object[]) args[paramCount - 1];
        int copiedArgCount = args.length - 1;
        int varArgCount = varArgs.length;
        Object[] spreadArgs = new Object[copiedArgCount + varArgCount];
        System.arraycopy(args, 0, spreadArgs, 0, copiedArgCount);
        System.arraycopy(varArgs, 0, spreadArgs, copiedArgCount, varArgCount);
        MethodType spreadType = descType.dropParameterTypes(paramCount - 1, paramCount).appendParameterTypes(Collections.nCopies(varArgCount, Object.class));
        CallSiteDescriptor spreadDesc = desc.changeMethodType(spreadType);
        LinkRequest spreadRequest = request.replaceArguments(spreadDesc, spreadArgs);
        GuardedInvocation spreadInvocation = createApplyOrCallCall(isApply, spreadDesc, spreadRequest, spreadArgs);
        return spreadInvocation.replaceMethods(pairArguments(spreadInvocation.getInvocation(), descType), spreadGuardArguments(spreadInvocation.getGuard(), descType));
    }

    private static MethodHandle spreadGuardArguments(MethodHandle guard, MethodType descType) {
        MethodHandle arrayConvertingGuard;
        MethodType guardType = guard.type();
        int guardParamCount = guardType.parameterCount();
        int descParamCount = descType.parameterCount();
        int spreadCount = (guardParamCount - descParamCount) + 1;
        if (spreadCount <= 0) {
            return guard;
        }
        if (guardType.parameterType(guardParamCount - 1).isArray()) {
            arrayConvertingGuard = Lookup.f248MH.filterArguments(guard, guardParamCount - 1, NativeFunction.TO_APPLY_ARGS);
        } else {
            arrayConvertingGuard = guard;
        }
        return ScriptObject.adaptHandleToVarArgCallSite(arrayConvertingGuard, descParamCount);
    }

    private static MethodHandle bindImplicitThis(Object fn, MethodHandle mh) {
        MethodHandle bound;
        if ((fn instanceof ScriptFunction) && ((ScriptFunction) fn).needsWrappedThis()) {
            bound = Lookup.f248MH.filterArguments(mh, 1, SCRIPTFUNCTION_GLOBALFILTER);
        } else {
            bound = mh;
        }
        return Lookup.f248MH.insertArguments(bound, 1, ScriptRuntime.UNDEFINED);
    }

    public MethodHandle getCallMethodHandle(MethodType type, String bindName) {
        return pairArguments(bindToNameIfNeeded(bindToCalleeIfNeeded(this.data.getGenericInvoker(this.scope)), bindName), type);
    }

    private static MethodHandle bindToNameIfNeeded(MethodHandle methodHandle, String bindName) {
        if (bindName == null) {
            return methodHandle;
        }
        MethodType methodType = methodHandle.type();
        int parameterCount = methodType.parameterCount();
        boolean isVarArg = parameterCount > 0 && methodType.parameterType(parameterCount - 1).isArray();
        return isVarArg ? Lookup.f248MH.filterArguments(methodHandle, 1, Lookup.f248MH.insertArguments(ADD_ZEROTH_ELEMENT, 1, bindName)) : Lookup.f248MH.insertArguments(methodHandle, 1, bindName);
    }

    private static MethodHandle getFunctionGuard(ScriptFunction function, int flags) {
        if ($assertionsDisabled || function.data != null) {
            return function.data.isBuiltin() ? Guards.getIdentityGuard(function) : Lookup.f248MH.insertArguments(IS_FUNCTION_MH, 1, function.data);
        }
        throw new AssertionError();
    }

    private static MethodHandle getNonStrictFunctionGuard(ScriptFunction function) {
        if ($assertionsDisabled || function.data != null) {
            return Lookup.f248MH.insertArguments(IS_NONSTRICT_FUNCTION, 2, function.data);
        }
        throw new AssertionError();
    }

    private static boolean isFunctionMH(Object self, ScriptFunctionData data) {
        return (self instanceof ScriptFunction) && ((ScriptFunction) self).data == data;
    }

    private static boolean isNonStrictFunction(Object self, Object arg, ScriptFunctionData data) {
        return (self instanceof ScriptFunction) && ((ScriptFunction) self).data == data && (arg instanceof ScriptObject);
    }

    private static boolean isApplyFunction(boolean appliedFnCondition, Object self, Object expectedSelf) {
        return appliedFnCondition && self == expectedSelf;
    }

    private static Object[] addZerothElement(Object[] args, Object value) {
        Object[] src = args == null ? ScriptRuntime.EMPTY_ARRAY : args;
        Object[] result = new Object[src.length + 1];
        System.arraycopy(src, 0, result, 1, src.length);
        result[0] = value;
        return result;
    }

    private static Object invokeSync(ScriptFunction func, Object sync, Object self, Object... args) throws Throwable {
        Object invoke;
        Object syncObj = sync == ScriptRuntime.UNDEFINED ? self : sync;
        synchronized (syncObj) {
            invoke = func.invoke(self, args);
        }
        return invoke;
    }

    private static MethodHandle findOwnMH_S(String name, Class<?> rtype, Class<?>... types) {
        return Lookup.f248MH.findStatic(MethodHandles.lookup(), ScriptFunction.class, name, Lookup.f248MH.type(rtype, types));
    }

    private static MethodHandle findOwnMH_V(String name, Class<?> rtype, Class<?>... types) {
        return Lookup.f248MH.findVirtual(MethodHandles.lookup(), ScriptFunction.class, name, Lookup.f248MH.type(rtype, types));
    }
}
