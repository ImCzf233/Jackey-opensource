package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodType;
import java.util.LinkedList;
import java.util.List;
import jdk.internal.dynalink.support.TypeUtilities;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/beans/ApplicableOverloadedMethods.class */
class ApplicableOverloadedMethods {
    private final List<SingleDynamicMethod> methods = new LinkedList();
    private final boolean varArgs;
    static final ApplicabilityTest APPLICABLE_BY_SUBTYPING = new ApplicabilityTest() { // from class: jdk.internal.dynalink.beans.ApplicableOverloadedMethods.1
        @Override // jdk.internal.dynalink.beans.ApplicableOverloadedMethods.ApplicabilityTest
        boolean isApplicable(MethodType callSiteType, SingleDynamicMethod method) {
            MethodType methodType = method.getMethodType();
            int methodArity = methodType.parameterCount();
            if (methodArity != callSiteType.parameterCount()) {
                return false;
            }
            for (int i = 1; i < methodArity; i++) {
                if (!TypeUtilities.isSubtype(callSiteType.parameterType(i), methodType.parameterType(i))) {
                    return false;
                }
            }
            return true;
        }
    };
    static final ApplicabilityTest APPLICABLE_BY_METHOD_INVOCATION_CONVERSION = new ApplicabilityTest() { // from class: jdk.internal.dynalink.beans.ApplicableOverloadedMethods.2
        @Override // jdk.internal.dynalink.beans.ApplicableOverloadedMethods.ApplicabilityTest
        boolean isApplicable(MethodType callSiteType, SingleDynamicMethod method) {
            MethodType methodType = method.getMethodType();
            int methodArity = methodType.parameterCount();
            if (methodArity != callSiteType.parameterCount()) {
                return false;
            }
            for (int i = 1; i < methodArity; i++) {
                if (!TypeUtilities.isMethodInvocationConvertible(callSiteType.parameterType(i), methodType.parameterType(i))) {
                    return false;
                }
            }
            return true;
        }
    };
    static final ApplicabilityTest APPLICABLE_BY_VARIABLE_ARITY = new ApplicabilityTest() { // from class: jdk.internal.dynalink.beans.ApplicableOverloadedMethods.3
        @Override // jdk.internal.dynalink.beans.ApplicableOverloadedMethods.ApplicabilityTest
        boolean isApplicable(MethodType callSiteType, SingleDynamicMethod method) {
            if (!method.isVarArgs()) {
                return false;
            }
            MethodType methodType = method.getMethodType();
            int methodArity = methodType.parameterCount();
            int fixArity = methodArity - 1;
            int callSiteArity = callSiteType.parameterCount();
            if (fixArity > callSiteArity) {
                return false;
            }
            for (int i = 1; i < fixArity; i++) {
                if (!TypeUtilities.isMethodInvocationConvertible(callSiteType.parameterType(i), methodType.parameterType(i))) {
                    return false;
                }
            }
            Class<?> varArgType = methodType.parameterType(fixArity).getComponentType();
            for (int i2 = fixArity; i2 < callSiteArity; i2++) {
                if (!TypeUtilities.isMethodInvocationConvertible(callSiteType.parameterType(i2), varArgType)) {
                    return false;
                }
            }
            return true;
        }
    };

    public ApplicableOverloadedMethods(List<SingleDynamicMethod> methods, MethodType callSiteType, ApplicabilityTest test) {
        for (SingleDynamicMethod m : methods) {
            if (test.isApplicable(callSiteType, m)) {
                this.methods.add(m);
            }
        }
        this.varArgs = test == APPLICABLE_BY_VARIABLE_ARITY;
    }

    public List<SingleDynamicMethod> getMethods() {
        return this.methods;
    }

    public List<SingleDynamicMethod> findMaximallySpecificMethods() {
        return MaximallySpecific.getMaximallySpecificMethods(this.methods, this.varArgs);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/beans/ApplicableOverloadedMethods$ApplicabilityTest.class */
    public static abstract class ApplicabilityTest {
        abstract boolean isApplicable(MethodType methodType, SingleDynamicMethod singleDynamicMethod);

        ApplicabilityTest() {
        }
    }
}
