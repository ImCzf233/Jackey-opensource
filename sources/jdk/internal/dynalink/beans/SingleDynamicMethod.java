package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Array;
import java.util.StringTokenizer;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.support.Guards;
import jdk.internal.dynalink.support.Lookup;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/beans/SingleDynamicMethod.class */
abstract class SingleDynamicMethod extends DynamicMethod {
    private static final MethodHandle CAN_CONVERT_TO;
    static final /* synthetic */ boolean $assertionsDisabled;

    public abstract boolean isVarArgs();

    public abstract MethodType getMethodType();

    public abstract MethodHandle getTarget(MethodHandles.Lookup lookup);

    static {
        $assertionsDisabled = !SingleDynamicMethod.class.desiredAssertionStatus();
        CAN_CONVERT_TO = Lookup.findOwnStatic(MethodHandles.lookup(), "canConvertTo", Boolean.TYPE, LinkerServices.class, Class.class, Object.class);
    }

    public SingleDynamicMethod(String name) {
        super(name);
    }

    @Override // jdk.internal.dynalink.beans.DynamicMethod
    public MethodHandle getInvocation(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices) {
        return getInvocation(getTarget(callSiteDescriptor.getLookup()), callSiteDescriptor.getMethodType(), linkerServices);
    }

    @Override // jdk.internal.dynalink.beans.DynamicMethod
    public SingleDynamicMethod getMethodForExactParamTypes(String paramTypes) {
        if (typeMatchesDescription(paramTypes, getMethodType())) {
            return this;
        }
        return null;
    }

    @Override // jdk.internal.dynalink.beans.DynamicMethod
    public boolean contains(SingleDynamicMethod method) {
        return getMethodType().parameterList().equals(method.getMethodType().parameterList());
    }

    public static String getMethodNameWithSignature(MethodType type, String methodName, boolean withReturnType) {
        String typeStr = type.toString();
        int retTypeIndex = typeStr.lastIndexOf(41) + 1;
        int secondParamIndex = typeStr.indexOf(44) + 1;
        if (secondParamIndex == 0) {
            secondParamIndex = retTypeIndex - 1;
        }
        StringBuilder b = new StringBuilder();
        if (withReturnType) {
            b.append((CharSequence) typeStr, retTypeIndex, typeStr.length()).append(' ');
        }
        return b.append(methodName).append('(').append((CharSequence) typeStr, secondParamIndex, retTypeIndex).toString();
    }

    public static MethodHandle getInvocation(MethodHandle target, MethodType callSiteType, LinkerServices linkerServices) {
        MethodHandle matchedMethod;
        MethodHandle filteredTarget = linkerServices.filterInternalObjects(target);
        MethodType methodType = filteredTarget.type();
        int paramsLen = methodType.parameterCount();
        boolean varArgs = target.isVarargsCollector();
        MethodHandle fixTarget = varArgs ? filteredTarget.asFixedArity() : filteredTarget;
        int fixParamsLen = varArgs ? paramsLen - 1 : paramsLen;
        int argsLen = callSiteType.parameterCount();
        if (argsLen < fixParamsLen) {
            return null;
        }
        if (argsLen == fixParamsLen) {
            if (varArgs) {
                matchedMethod = MethodHandles.insertArguments(fixTarget, fixParamsLen, Array.newInstance(methodType.parameterType(fixParamsLen).getComponentType(), 0));
            } else {
                matchedMethod = fixTarget;
            }
            return createConvertingInvocation(matchedMethod, linkerServices, callSiteType);
        } else if (!varArgs) {
            return null;
        } else {
            Class<?> varArgType = methodType.parameterType(fixParamsLen);
            if (argsLen == paramsLen) {
                Class<?> callSiteLastArgType = callSiteType.parameterType(fixParamsLen);
                if (varArgType.isAssignableFrom(callSiteLastArgType)) {
                    return createConvertingInvocation(filteredTarget, linkerServices, callSiteType).asVarargsCollector(callSiteLastArgType);
                }
                MethodHandle varArgCollectingInvocation = createConvertingInvocation(collectArguments(fixTarget, argsLen), linkerServices, callSiteType);
                boolean isAssignableFromArray = callSiteLastArgType.isAssignableFrom(varArgType);
                boolean isCustomConvertible = linkerServices.canConvert(callSiteLastArgType, varArgType);
                if (!isAssignableFromArray && !isCustomConvertible) {
                    return varArgCollectingInvocation;
                }
                MethodHandle arrayConvertingInvocation = createConvertingInvocation(MethodHandles.filterArguments(fixTarget, fixParamsLen, linkerServices.getTypeConverter(callSiteLastArgType, varArgType)), linkerServices, callSiteType);
                MethodHandle canConvertArgToArray = MethodHandles.insertArguments(CAN_CONVERT_TO, 0, linkerServices, varArgType);
                MethodHandle canConvertLastArgToArray = MethodHandles.dropArguments(canConvertArgToArray, 0, MethodType.genericMethodType(fixParamsLen).parameterList()).asType(callSiteType.changeReturnType(Boolean.TYPE));
                MethodHandle convertToArrayWhenPossible = MethodHandles.guardWithTest(canConvertLastArgToArray, arrayConvertingInvocation, varArgCollectingInvocation);
                if (isAssignableFromArray) {
                    return MethodHandles.guardWithTest(Guards.isInstance(varArgType, fixParamsLen, callSiteType), createConvertingInvocation(fixTarget, linkerServices, callSiteType), isCustomConvertible ? convertToArrayWhenPossible : varArgCollectingInvocation);
                } else if (!$assertionsDisabled && !isCustomConvertible) {
                    throw new AssertionError();
                } else {
                    return convertToArrayWhenPossible;
                }
            }
            return createConvertingInvocation(collectArguments(fixTarget, argsLen), linkerServices, callSiteType);
        }
    }

    private static boolean canConvertTo(LinkerServices linkerServices, Class<?> to, Object obj) {
        if (obj == null) {
            return false;
        }
        return linkerServices.canConvert(obj.getClass(), to);
    }

    public static MethodHandle collectArguments(MethodHandle target, int parameterCount) {
        MethodType methodType = target.type();
        int fixParamsLen = methodType.parameterCount() - 1;
        Class<?> arrayType = methodType.parameterType(fixParamsLen);
        return target.asCollector(arrayType, parameterCount - fixParamsLen);
    }

    private static MethodHandle createConvertingInvocation(MethodHandle sizedMethod, LinkerServices linkerServices, MethodType callSiteType) {
        return linkerServices.asTypeLosslessReturn(sizedMethod, callSiteType);
    }

    private static boolean typeMatchesDescription(String paramTypes, MethodType type) {
        StringTokenizer tok = new StringTokenizer(paramTypes, ", ");
        for (int i = 1; i < type.parameterCount(); i++) {
            if (!tok.hasMoreTokens() || !typeNameMatches(tok.nextToken(), type.parameterType(i))) {
                return false;
            }
        }
        return !tok.hasMoreTokens();
    }

    private static boolean typeNameMatches(String typeName, Class<?> type) {
        return typeName.equals(typeName.indexOf(46) == -1 ? type.getSimpleName() : type.getCanonicalName());
    }
}
