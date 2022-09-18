package jdk.internal.dynalink.support;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import jdk.internal.dynalink.linker.MethodHandleTransformer;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/support/DefaultInternalObjectFilter.class */
public class DefaultInternalObjectFilter implements MethodHandleTransformer {
    private static final MethodHandle FILTER_VARARGS;
    private final MethodHandle parameterFilter;
    private final MethodHandle returnFilter;
    private final MethodHandle varArgFilter;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !DefaultInternalObjectFilter.class.desiredAssertionStatus();
        FILTER_VARARGS = new Lookup(MethodHandles.lookup()).findStatic(DefaultInternalObjectFilter.class, "filterVarArgs", MethodType.methodType(Object[].class, MethodHandle.class, Object[].class));
    }

    public DefaultInternalObjectFilter(MethodHandle parameterFilter, MethodHandle returnFilter) {
        this.parameterFilter = checkHandle(parameterFilter, "parameterFilter");
        this.returnFilter = checkHandle(returnFilter, "returnFilter");
        this.varArgFilter = parameterFilter == null ? null : FILTER_VARARGS.bindTo(parameterFilter);
    }

    @Override // jdk.internal.dynalink.linker.MethodHandleTransformer
    public MethodHandle transform(MethodHandle target) {
        MethodHandle paramsFiltered;
        if ($assertionsDisabled || target != null) {
            MethodHandle[] filters = null;
            MethodType type = target.type();
            boolean isVarArg = target.isVarargsCollector();
            int paramCount = type.parameterCount();
            if (this.parameterFilter != null) {
                int firstFilter = -1;
                int i = 1;
                while (i < paramCount) {
                    Class<?> paramType = type.parameterType(i);
                    boolean filterVarArg = isVarArg && i == paramCount - 1 && paramType == Object[].class;
                    if (filterVarArg || paramType == Object.class) {
                        if (filters == null) {
                            firstFilter = i;
                            filters = new MethodHandle[paramCount - firstFilter];
                        }
                        filters[i - firstFilter] = filterVarArg ? this.varArgFilter : this.parameterFilter;
                    }
                    i++;
                }
                paramsFiltered = filters != null ? MethodHandles.filterArguments(target, firstFilter, filters) : target;
            } else {
                paramsFiltered = target;
            }
            MethodHandle returnFiltered = (this.returnFilter == null || type.returnType() != Object.class) ? paramsFiltered : MethodHandles.filterReturnValue(paramsFiltered, this.returnFilter);
            return (!isVarArg || returnFiltered.isVarargsCollector()) ? returnFiltered : returnFiltered.asVarargsCollector(type.parameterType(paramCount - 1));
        }
        throw new AssertionError();
    }

    private static MethodHandle checkHandle(MethodHandle handle, String handleKind) {
        if (handle != null) {
            MethodType objectObjectType = MethodType.methodType(Object.class, Object.class);
            if (!handle.type().equals(objectObjectType)) {
                throw new IllegalArgumentException("Method type for " + handleKind + " must be " + objectObjectType);
            }
        }
        return handle;
    }

    private static Object[] filterVarArgs(MethodHandle parameterFilter, Object[] args) throws Throwable {
        Object[] newArgs = null;
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            Object newArg = parameterFilter.invokeExact(arg);
            if (arg != newArg) {
                if (newArgs == null) {
                    newArgs = (Object[]) args.clone();
                }
                newArgs[i] = newArg;
            }
        }
        return newArgs == null ? args : newArgs;
    }
}
