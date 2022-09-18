package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import javax.script.Bindings;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.ConversionComparator;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardedTypeConversion;
import jdk.internal.dynalink.linker.GuardingTypeConverterFactory;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.Guards;
import jdk.internal.dynalink.support.LinkerServicesImpl;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.api.scripting.ScriptUtils;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.objects.NativeArray;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ListAdapter;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.Undefined;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/linker/NashornLinker.class */
public final class NashornLinker implements TypeBasedGuardingDynamicLinker, GuardingTypeConverterFactory, ConversionComparator {
    private static final ClassValue<MethodHandle> ARRAY_CONVERTERS;
    private static final MethodHandle IS_SCRIPT_OBJECT;
    private static final MethodHandle IS_SCRIPT_FUNCTION;
    private static final MethodHandle IS_NATIVE_ARRAY;
    private static final MethodHandle IS_NASHORN_OR_UNDEFINED_TYPE;
    private static final MethodHandle CREATE_MIRROR;
    private static final MethodHandle TO_COLLECTION;
    private static final MethodHandle TO_DEQUE;
    private static final MethodHandle TO_LIST;
    private static final MethodHandle TO_QUEUE;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !NashornLinker.class.desiredAssertionStatus();
        ARRAY_CONVERTERS = new ClassValue<MethodHandle>() { // from class: jdk.nashorn.internal.runtime.linker.NashornLinker.1
            protected MethodHandle computeValue(Class<?> type) {
                return NashornLinker.createArrayConverter(type);
            }
        };
        IS_SCRIPT_OBJECT = Guards.isInstance(ScriptObject.class, Lookup.f248MH.type(Boolean.TYPE, Object.class));
        IS_SCRIPT_FUNCTION = Guards.isInstance(ScriptFunction.class, Lookup.f248MH.type(Boolean.TYPE, Object.class));
        IS_NATIVE_ARRAY = Guards.isOfClass((Class<?>) NativeArray.class, Lookup.f248MH.type(Boolean.TYPE, Object.class));
        IS_NASHORN_OR_UNDEFINED_TYPE = findOwnMH("isNashornTypeOrUndefined", Boolean.TYPE, Object.class);
        CREATE_MIRROR = findOwnMH("createMirror", Object.class, Object.class);
        MethodHandle listAdapterCreate = new jdk.internal.dynalink.support.Lookup(MethodHandles.lookup()).findStatic(ListAdapter.class, "create", MethodType.methodType(ListAdapter.class, Object.class));
        TO_COLLECTION = asReturning(listAdapterCreate, Collection.class);
        TO_DEQUE = asReturning(listAdapterCreate, Deque.class);
        TO_LIST = asReturning(listAdapterCreate, List.class);
        TO_QUEUE = asReturning(listAdapterCreate, Queue.class);
    }

    @Override // jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker
    public boolean canLinkType(Class<?> type) {
        return canLinkTypeStatic(type);
    }

    public static boolean canLinkTypeStatic(Class<?> type) {
        return ScriptObject.class.isAssignableFrom(type) || Undefined.class == type;
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest request, LinkerServices linkerServices) throws Exception {
        LinkRequest requestWithoutContext = request.withoutRuntimeContext();
        Object self = requestWithoutContext.getReceiver();
        CallSiteDescriptor desc = requestWithoutContext.getCallSiteDescriptor();
        if (desc.getNameTokenCount() < 2 || !"dyn".equals(desc.getNameToken(0))) {
            return null;
        }
        return Bootstrap.asTypeSafeReturn(getGuardedInvocation(self, request, desc), linkerServices, desc);
    }

    private static GuardedInvocation getGuardedInvocation(Object self, LinkRequest request, CallSiteDescriptor desc) {
        GuardedInvocation inv;
        if (self instanceof ScriptObject) {
            inv = ((ScriptObject) self).lookup(desc, request);
        } else if (self instanceof Undefined) {
            inv = Undefined.lookup(desc);
        } else {
            throw new AssertionError(self.getClass().getName());
        }
        return inv;
    }

    @Override // jdk.internal.dynalink.linker.GuardingTypeConverterFactory
    public GuardedTypeConversion convertToType(Class<?> sourceType, Class<?> targetType) throws Exception {
        GuardedInvocation gi = convertToTypeNoCast(sourceType, targetType);
        if (gi != null) {
            return new GuardedTypeConversion(gi.asType(Lookup.f248MH.type(targetType, sourceType)), true);
        }
        GuardedInvocation gi2 = getSamTypeConverter(sourceType, targetType);
        if (gi2 != null) {
            return new GuardedTypeConversion(gi2.asType(Lookup.f248MH.type(targetType, sourceType)), false);
        }
        return null;
    }

    private static GuardedInvocation convertToTypeNoCast(Class<?> sourceType, Class<?> targetType) throws Exception {
        MethodHandle mh = JavaArgumentConverters.getConverter(targetType);
        if (mh != null) {
            return new GuardedInvocation(mh, canLinkTypeStatic(sourceType) ? null : IS_NASHORN_OR_UNDEFINED_TYPE);
        }
        GuardedInvocation arrayConverter = getArrayConverter(sourceType, targetType);
        if (arrayConverter != null) {
            return arrayConverter;
        }
        return getMirrorConverter(sourceType, targetType);
    }

    private static GuardedInvocation getSamTypeConverter(Class<?> sourceType, Class<?> targetType) throws Exception {
        boolean isSourceTypeGeneric = sourceType.isAssignableFrom(ScriptFunction.class);
        if ((isSourceTypeGeneric || ScriptFunction.class.isAssignableFrom(sourceType)) && isAutoConvertibleFromFunction(targetType)) {
            MethodHandle ctor = JavaAdapterFactory.getConstructor(ScriptFunction.class, targetType, getCurrentLookup());
            if (!$assertionsDisabled && ctor == null) {
                throw new AssertionError();
            }
            return new GuardedInvocation(ctor, isSourceTypeGeneric ? IS_SCRIPT_FUNCTION : null);
        }
        return null;
    }

    private static MethodHandles.Lookup getCurrentLookup() {
        LinkRequest currentRequest = (LinkRequest) AccessController.doPrivileged(new PrivilegedAction<LinkRequest>() { // from class: jdk.nashorn.internal.runtime.linker.NashornLinker.2
            @Override // java.security.PrivilegedAction
            public LinkRequest run() {
                return LinkerServicesImpl.getCurrentLinkRequest();
            }
        });
        return currentRequest == null ? MethodHandles.publicLookup() : currentRequest.getCallSiteDescriptor().getLookup();
    }

    private static GuardedInvocation getArrayConverter(Class<?> sourceType, Class<?> targetType) {
        boolean isSourceTypeNativeArray = sourceType == NativeArray.class;
        boolean isSourceTypeGeneric = !isSourceTypeNativeArray && sourceType.isAssignableFrom(NativeArray.class);
        if (isSourceTypeNativeArray || isSourceTypeGeneric) {
            MethodHandle guard = isSourceTypeGeneric ? IS_NATIVE_ARRAY : null;
            if (targetType.isArray()) {
                return new GuardedInvocation((MethodHandle) ARRAY_CONVERTERS.get(targetType), guard);
            }
            if (targetType == List.class) {
                return new GuardedInvocation(TO_LIST, guard);
            }
            if (targetType == Deque.class) {
                return new GuardedInvocation(TO_DEQUE, guard);
            }
            if (targetType == Queue.class) {
                return new GuardedInvocation(TO_QUEUE, guard);
            }
            if (targetType == Collection.class) {
                return new GuardedInvocation(TO_COLLECTION, guard);
            }
            return null;
        }
        return null;
    }

    public static MethodHandle createArrayConverter(Class<?> type) {
        if ($assertionsDisabled || type.isArray()) {
            MethodHandle converter = Lookup.f248MH.insertArguments(JSType.TO_JAVA_ARRAY.methodHandle(), 1, type.getComponentType());
            return Lookup.f248MH.asType(converter, converter.type().changeReturnType(type));
        }
        throw new AssertionError();
    }

    private static GuardedInvocation getMirrorConverter(Class<?> sourceType, Class<?> targetType) {
        if (targetType == Map.class || targetType == Bindings.class || targetType == JSObject.class || targetType == ScriptObjectMirror.class) {
            if (ScriptObject.class.isAssignableFrom(sourceType)) {
                return new GuardedInvocation(CREATE_MIRROR);
            }
            if (sourceType.isAssignableFrom(ScriptObject.class) || sourceType.isInterface()) {
                return new GuardedInvocation(CREATE_MIRROR, IS_SCRIPT_OBJECT);
            }
            return null;
        }
        return null;
    }

    private static boolean isAutoConvertibleFromFunction(Class<?> clazz) {
        return isAbstractClass(clazz) && !ScriptObject.class.isAssignableFrom(clazz) && JavaAdapterFactory.isAutoConvertibleFromFunction(clazz);
    }

    public static boolean isAbstractClass(Class<?> clazz) {
        return Modifier.isAbstract(clazz.getModifiers()) && !clazz.isArray();
    }

    @Override // jdk.internal.dynalink.linker.ConversionComparator
    public ConversionComparator.Comparison compareConversion(Class<?> sourceType, Class<?> targetType1, Class<?> targetType2) {
        if (sourceType == NativeArray.class) {
            if (isArrayPreferredTarget(targetType1)) {
                if (!isArrayPreferredTarget(targetType2)) {
                    return ConversionComparator.Comparison.TYPE_1_BETTER;
                }
            } else if (isArrayPreferredTarget(targetType2)) {
                return ConversionComparator.Comparison.TYPE_2_BETTER;
            }
            if (targetType1.isArray()) {
                if (!targetType2.isArray()) {
                    return ConversionComparator.Comparison.TYPE_1_BETTER;
                }
            } else if (targetType2.isArray()) {
                return ConversionComparator.Comparison.TYPE_2_BETTER;
            }
        }
        if (ScriptObject.class.isAssignableFrom(sourceType)) {
            if (targetType1.isInterface()) {
                if (!targetType2.isInterface()) {
                    return ConversionComparator.Comparison.TYPE_1_BETTER;
                }
            } else if (targetType2.isInterface()) {
                return ConversionComparator.Comparison.TYPE_2_BETTER;
            }
        }
        return ConversionComparator.Comparison.INDETERMINATE;
    }

    private static boolean isArrayPreferredTarget(Class<?> clazz) {
        return clazz == List.class || clazz == Collection.class || clazz == Queue.class || clazz == Deque.class;
    }

    private static MethodHandle asReturning(MethodHandle mh, Class<?> nrtype) {
        return mh.asType(mh.type().changeReturnType(nrtype));
    }

    private static boolean isNashornTypeOrUndefined(Object obj) {
        return (obj instanceof ScriptObject) || (obj instanceof Undefined);
    }

    private static Object createMirror(Object obj) {
        return obj instanceof ScriptObject ? ScriptUtils.wrap((ScriptObject) obj) : obj;
    }

    private static MethodHandle findOwnMH(String name, Class<?> rtype, Class<?>... types) {
        return Lookup.f248MH.findStatic(MethodHandles.lookup(), NashornLinker.class, name, Lookup.f248MH.type(rtype, types));
    }
}
