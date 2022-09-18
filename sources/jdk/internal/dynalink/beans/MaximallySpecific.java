package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import jdk.internal.dynalink.linker.ConversionComparator;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.support.TypeUtilities;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/beans/MaximallySpecific.class */
class MaximallySpecific {
    private static final MethodTypeGetter<MethodHandle> METHOD_HANDLE_TYPE_GETTER;
    private static final MethodTypeGetter<SingleDynamicMethod> DYNAMIC_METHOD_TYPE_GETTER;
    static final /* synthetic */ boolean $assertionsDisabled;

    MaximallySpecific() {
    }

    static {
        $assertionsDisabled = !MaximallySpecific.class.desiredAssertionStatus();
        METHOD_HANDLE_TYPE_GETTER = new MethodTypeGetter<MethodHandle>() { // from class: jdk.internal.dynalink.beans.MaximallySpecific.1
            public MethodType getMethodType(MethodHandle t) {
                return t.type();
            }
        };
        DYNAMIC_METHOD_TYPE_GETTER = new MethodTypeGetter<SingleDynamicMethod>() { // from class: jdk.internal.dynalink.beans.MaximallySpecific.2
            public MethodType getMethodType(SingleDynamicMethod t) {
                return t.getMethodType();
            }
        };
    }

    public static List<SingleDynamicMethod> getMaximallySpecificMethods(List<SingleDynamicMethod> methods, boolean varArgs) {
        return getMaximallySpecificSingleDynamicMethods(methods, varArgs, null, null);
    }

    /* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/beans/MaximallySpecific$MethodTypeGetter.class */
    public static abstract class MethodTypeGetter<T> {
        abstract MethodType getMethodType(T t);

        private MethodTypeGetter() {
        }
    }

    public static List<MethodHandle> getMaximallySpecificMethodHandles(List<MethodHandle> methods, boolean varArgs, Class<?>[] argTypes, LinkerServices ls) {
        return getMaximallySpecificMethods(methods, varArgs, argTypes, ls, METHOD_HANDLE_TYPE_GETTER);
    }

    static List<SingleDynamicMethod> getMaximallySpecificSingleDynamicMethods(List<SingleDynamicMethod> methods, boolean varArgs, Class<?>[] argTypes, LinkerServices ls) {
        return getMaximallySpecificMethods(methods, varArgs, argTypes, ls, DYNAMIC_METHOD_TYPE_GETTER);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <T> List<T> getMaximallySpecificMethods(List<T> methods, boolean varArgs, Class<?>[] argTypes, LinkerServices ls, MethodTypeGetter<T> methodTypeGetter) {
        if (methods.size() < 2) {
            return methods;
        }
        LinkedList<T> maximals = new LinkedList<>();
        for (T m : methods) {
            methodTypeGetter.getMethodType(m);
            boolean lessSpecific = false;
            Iterator<T> maximal = maximals.iterator();
            while (maximal.hasNext()) {
                maximal.next();
                switch (isMoreSpecific(methodType, methodTypeGetter.getMethodType(max), varArgs, argTypes, ls)) {
                    case TYPE_1_BETTER:
                        maximal.remove();
                        break;
                    case TYPE_2_BETTER:
                        lessSpecific = true;
                        break;
                    case INDETERMINATE:
                        break;
                    default:
                        throw new AssertionError();
                }
            }
            if (!lessSpecific) {
                maximals.addLast(m);
            }
        }
        return maximals;
    }

    private static ConversionComparator.Comparison isMoreSpecific(MethodType t1, MethodType t2, boolean varArgs, Class<?>[] argTypes, LinkerServices ls) {
        int pc1 = t1.parameterCount();
        int pc2 = t2.parameterCount();
        if ($assertionsDisabled || varArgs || (pc1 == pc2 && (argTypes == null || argTypes.length == pc1))) {
            if (!$assertionsDisabled) {
                if ((argTypes == null) != (ls == null)) {
                    throw new AssertionError();
                }
            }
            int maxPc = Math.max(Math.max(pc1, pc2), argTypes == null ? 0 : argTypes.length);
            boolean t1MoreSpecific = false;
            boolean t2MoreSpecific = false;
            for (int i = 1; i < maxPc; i++) {
                Class<?> c1 = getParameterClass(t1, pc1, i, varArgs);
                Class<?> c2 = getParameterClass(t2, pc2, i, varArgs);
                if (c1 != c2) {
                    ConversionComparator.Comparison cmp = compare(c1, c2, argTypes, i, ls);
                    if (cmp == ConversionComparator.Comparison.TYPE_1_BETTER && !t1MoreSpecific) {
                        t1MoreSpecific = true;
                        if (t2MoreSpecific) {
                            return ConversionComparator.Comparison.INDETERMINATE;
                        }
                    }
                    if (cmp == ConversionComparator.Comparison.TYPE_2_BETTER && !t2MoreSpecific) {
                        t2MoreSpecific = true;
                        if (t1MoreSpecific) {
                            return ConversionComparator.Comparison.INDETERMINATE;
                        }
                    }
                }
            }
            if (t1MoreSpecific) {
                return ConversionComparator.Comparison.TYPE_1_BETTER;
            }
            if (t2MoreSpecific) {
                return ConversionComparator.Comparison.TYPE_2_BETTER;
            }
            return ConversionComparator.Comparison.INDETERMINATE;
        }
        throw new AssertionError();
    }

    private static ConversionComparator.Comparison compare(Class<?> c1, Class<?> c2, Class<?>[] argTypes, int i, LinkerServices cmp) {
        ConversionComparator.Comparison c;
        if (cmp != null && (c = cmp.compareConversion(argTypes[i], c1, c2)) != ConversionComparator.Comparison.INDETERMINATE) {
            return c;
        }
        if (TypeUtilities.isSubtype(c1, c2)) {
            return ConversionComparator.Comparison.TYPE_1_BETTER;
        }
        if (TypeUtilities.isSubtype(c2, c1)) {
            return ConversionComparator.Comparison.TYPE_2_BETTER;
        }
        return ConversionComparator.Comparison.INDETERMINATE;
    }

    private static Class<?> getParameterClass(MethodType t, int l, int i, boolean varArgs) {
        return (!varArgs || i < l - 1) ? t.parameterType(i) : t.parameterType(l - 1).getComponentType();
    }
}
