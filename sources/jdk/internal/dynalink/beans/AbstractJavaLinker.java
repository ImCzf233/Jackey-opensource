package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.beans.GuardedInvocationComponent;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.internal.dynalink.support.Guards;
import jdk.internal.dynalink.support.Lookup;
import jdk.internal.dynalink.support.TypeUtilities;
import jdk.nashorn.internal.runtime.PropertyDescriptor;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/beans/AbstractJavaLinker.class */
public abstract class AbstractJavaLinker implements GuardingDynamicLinker {
    final Class<?> clazz;
    private final MethodHandle classGuard;
    private final MethodHandle assignableGuard;
    private final Map<String, AnnotatedDynamicMethod> propertyGetters;
    private final Map<String, DynamicMethod> propertySetters;
    private final Map<String, DynamicMethod> methods;
    private static final MethodHandle IS_METHOD_HANDLE_NOT_NULL;
    private static final MethodHandle CONSTANT_NULL_DROP_METHOD_HANDLE;
    private static final Lookup privateLookup;
    private static final MethodHandle IS_ANNOTATED_METHOD_NOT_NULL;
    private static final MethodHandle CONSTANT_NULL_DROP_ANNOTATED_METHOD;
    private static final MethodHandle GET_ANNOTATED_METHOD;
    private static final MethodHandle GETTER_INVOKER;
    private static final MethodHandle IS_DYNAMIC_METHOD;
    private static final MethodHandle OBJECT_IDENTITY;
    private static MethodHandle GET_PROPERTY_GETTER_HANDLE;
    private final MethodHandle getPropertyGetterHandle;
    private static final MethodHandle GET_PROPERTY_SETTER_HANDLE;
    private final MethodHandle getPropertySetterHandle;
    private static MethodHandle GET_DYNAMIC_METHOD;
    private final MethodHandle getDynamicMethod;
    static final /* synthetic */ boolean $assertionsDisabled;

    abstract FacetIntrospector createFacetIntrospector();

    static {
        $assertionsDisabled = !AbstractJavaLinker.class.desiredAssertionStatus();
        IS_METHOD_HANDLE_NOT_NULL = Guards.isNotNull().asType(MethodType.methodType(Boolean.TYPE, MethodHandle.class));
        CONSTANT_NULL_DROP_METHOD_HANDLE = MethodHandles.dropArguments(MethodHandles.constant(Object.class, null), 0, MethodHandle.class);
        privateLookup = new Lookup(MethodHandles.lookup());
        IS_ANNOTATED_METHOD_NOT_NULL = Guards.isNotNull().asType(MethodType.methodType(Boolean.TYPE, AnnotatedDynamicMethod.class));
        CONSTANT_NULL_DROP_ANNOTATED_METHOD = MethodHandles.dropArguments(MethodHandles.constant(Object.class, null), 0, AnnotatedDynamicMethod.class);
        GET_ANNOTATED_METHOD = privateLookup.findVirtual(AnnotatedDynamicMethod.class, "getTarget", MethodType.methodType(MethodHandle.class, MethodHandles.Lookup.class, LinkerServices.class));
        GETTER_INVOKER = MethodHandles.invoker(MethodType.methodType(Object.class, Object.class));
        IS_DYNAMIC_METHOD = Guards.isInstance(DynamicMethod.class, MethodType.methodType(Boolean.TYPE, Object.class));
        OBJECT_IDENTITY = MethodHandles.identity(Object.class);
        GET_PROPERTY_GETTER_HANDLE = MethodHandles.dropArguments(privateLookup.findOwnSpecial("getPropertyGetterHandle", Object.class, Object.class), 1, Object.class);
        GET_PROPERTY_SETTER_HANDLE = MethodHandles.dropArguments(MethodHandles.dropArguments(privateLookup.findOwnSpecial("getPropertySetterHandle", MethodHandle.class, CallSiteDescriptor.class, LinkerServices.class, Object.class), 3, Object.class), 5, Object.class);
        GET_DYNAMIC_METHOD = MethodHandles.dropArguments(privateLookup.findOwnSpecial("getDynamicMethod", Object.class, Object.class), 1, Object.class);
    }

    public AbstractJavaLinker(Class<?> clazz, MethodHandle classGuard) {
        this(clazz, classGuard, classGuard);
    }

    public AbstractJavaLinker(Class<?> clazz, MethodHandle classGuard, MethodHandle assignableGuard) {
        this.propertyGetters = new HashMap();
        this.propertySetters = new HashMap();
        this.methods = new HashMap();
        this.getPropertyGetterHandle = GET_PROPERTY_GETTER_HANDLE.bindTo(this);
        this.getPropertySetterHandle = GET_PROPERTY_SETTER_HANDLE.bindTo(this);
        this.getDynamicMethod = GET_DYNAMIC_METHOD.bindTo(this);
        this.clazz = clazz;
        this.classGuard = classGuard;
        this.assignableGuard = assignableGuard;
        FacetIntrospector introspector = createFacetIntrospector();
        for (Method method : introspector.getMethods()) {
            String name = method.getName();
            addMember(name, method, this.methods);
            if (name.startsWith(PropertyDescriptor.GET) && name.length() > 3 && method.getParameterTypes().length == 0) {
                setPropertyGetter(method, 3);
            } else if (name.startsWith("is") && name.length() > 2 && method.getParameterTypes().length == 0 && method.getReturnType() == Boolean.TYPE) {
                setPropertyGetter(method, 2);
            } else if (name.startsWith(PropertyDescriptor.SET) && name.length() > 3 && method.getParameterTypes().length == 1) {
                addMember(decapitalize(name.substring(3)), method, this.propertySetters);
            }
        }
        for (Field field : introspector.getFields()) {
            String name2 = field.getName();
            if (!this.propertyGetters.containsKey(name2)) {
                setPropertyGetter(name2, introspector.unreflectGetter(field), GuardedInvocationComponent.ValidationType.EXACT_CLASS);
            }
            if (!Modifier.isFinal(field.getModifiers()) && !this.propertySetters.containsKey(name2)) {
                addMember(name2, new SimpleDynamicMethod(introspector.unreflectSetter(field), clazz, name2), this.propertySetters);
            }
        }
        for (Map.Entry<String, MethodHandle> innerClassSpec : introspector.getInnerClassGetters().entrySet()) {
            String name3 = innerClassSpec.getKey();
            if (!this.propertyGetters.containsKey(name3)) {
                setPropertyGetter(name3, innerClassSpec.getValue(), GuardedInvocationComponent.ValidationType.EXACT_CLASS);
            }
        }
    }

    private static String decapitalize(String str) {
        if ($assertionsDisabled || str != null) {
            if (str.isEmpty()) {
                return str;
            }
            char c0 = str.charAt(0);
            if (Character.isLowerCase(c0)) {
                return str;
            }
            if (str.length() > 1 && Character.isUpperCase(str.charAt(1))) {
                return str;
            }
            char[] c = str.toCharArray();
            c[0] = Character.toLowerCase(c0);
            return new String(c);
        }
        throw new AssertionError();
    }

    public Collection<String> getReadablePropertyNames() {
        return getUnmodifiableKeys(this.propertyGetters);
    }

    public Collection<String> getWritablePropertyNames() {
        return getUnmodifiableKeys(this.propertySetters);
    }

    public Collection<String> getMethodNames() {
        return getUnmodifiableKeys(this.methods);
    }

    private static Collection<String> getUnmodifiableKeys(Map<String, ?> m) {
        return Collections.unmodifiableCollection(m.keySet());
    }

    private void setPropertyGetter(String name, SingleDynamicMethod handle, GuardedInvocationComponent.ValidationType validationType) {
        this.propertyGetters.put(name, new AnnotatedDynamicMethod(handle, validationType));
    }

    private void setPropertyGetter(Method getter, int prefixLen) {
        setPropertyGetter(decapitalize(getter.getName().substring(prefixLen)), createDynamicMethod(getMostGenericGetter(getter)), GuardedInvocationComponent.ValidationType.INSTANCE_OF);
    }

    public void setPropertyGetter(String name, MethodHandle handle, GuardedInvocationComponent.ValidationType validationType) {
        setPropertyGetter(name, new SimpleDynamicMethod(handle, this.clazz, name), validationType);
    }

    private void addMember(String name, AccessibleObject ao, Map<String, DynamicMethod> methodMap) {
        addMember(name, createDynamicMethod(ao), methodMap);
    }

    private void addMember(String name, SingleDynamicMethod method, Map<String, DynamicMethod> methodMap) {
        DynamicMethod existingMethod = methodMap.get(name);
        DynamicMethod newMethod = mergeMethods(method, existingMethod, this.clazz, name);
        if (newMethod != existingMethod) {
            methodMap.put(name, newMethod);
        }
    }

    public static DynamicMethod createDynamicMethod(Iterable<? extends AccessibleObject> members, Class<?> clazz, String name) {
        DynamicMethod dynMethod = null;
        for (AccessibleObject method : members) {
            dynMethod = mergeMethods(createDynamicMethod(method), dynMethod, clazz, name);
        }
        return dynMethod;
    }

    private static SingleDynamicMethod createDynamicMethod(AccessibleObject m) {
        if (CallerSensitiveDetector.isCallerSensitive(m)) {
            return new CallerSensitiveDynamicMethod(m);
        }
        try {
            MethodHandle mh = unreflectSafely(m);
            Member member = (Member) m;
            return new SimpleDynamicMethod(mh, member.getDeclaringClass(), member.getName(), m instanceof Constructor);
        } catch (IllegalAccessError e) {
            return new CallerSensitiveDynamicMethod(m);
        }
    }

    private static MethodHandle unreflectSafely(AccessibleObject m) {
        if (m instanceof Method) {
            Method reflMethod = (Method) m;
            MethodHandle handle = Lookup.PUBLIC.unreflect(reflMethod);
            if (Modifier.isStatic(reflMethod.getModifiers())) {
                return StaticClassIntrospector.editStaticMethodHandle(handle);
            }
            return handle;
        }
        return StaticClassIntrospector.editConstructorMethodHandle(Lookup.PUBLIC.unreflectConstructor((Constructor) m));
    }

    private static DynamicMethod mergeMethods(SingleDynamicMethod method, DynamicMethod existing, Class<?> clazz, String name) {
        if (existing == null) {
            return method;
        }
        if (existing.contains(method)) {
            return existing;
        }
        if (existing instanceof SingleDynamicMethod) {
            OverloadedDynamicMethod odm = new OverloadedDynamicMethod(clazz, name);
            odm.addMethod((SingleDynamicMethod) existing);
            odm.addMethod(method);
            return odm;
        } else if (existing instanceof OverloadedDynamicMethod) {
            ((OverloadedDynamicMethod) existing).addMethod(method);
            return existing;
        } else {
            throw new AssertionError();
        }
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest request, LinkerServices linkerServices) throws Exception {
        LinkRequest ncrequest = request.withoutRuntimeContext();
        CallSiteDescriptor callSiteDescriptor = ncrequest.getCallSiteDescriptor();
        String op = callSiteDescriptor.getNameToken(1);
        if ("callMethod" == op) {
            return getCallPropWithThis(callSiteDescriptor, linkerServices);
        }
        List<String> list = CallSiteDescriptorFactory.tokenizeOperators(callSiteDescriptor);
        while (true) {
            List<String> operations = list;
            if (!operations.isEmpty()) {
                GuardedInvocationComponent gic = getGuardedInvocationComponent(callSiteDescriptor, linkerServices, operations);
                if (gic != null) {
                    return gic.getGuardedInvocation();
                }
                list = pop(operations);
            } else {
                return null;
            }
        }
    }

    public GuardedInvocationComponent getGuardedInvocationComponent(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices, List<String> operations) throws Exception {
        if (operations.isEmpty()) {
            return null;
        }
        String op = operations.get(0);
        if ("getProp".equals(op)) {
            return getPropertyGetter(callSiteDescriptor, linkerServices, pop(operations));
        }
        if ("setProp".equals(op)) {
            return getPropertySetter(callSiteDescriptor, linkerServices, pop(operations));
        }
        if ("getMethod".equals(op)) {
            return getMethodGetter(callSiteDescriptor, linkerServices, pop(operations));
        }
        return null;
    }

    public static final <T> List<T> pop(List<T> l) {
        return l.subList(1, l.size());
    }

    MethodHandle getClassGuard(CallSiteDescriptor desc) {
        return getClassGuard(desc.getMethodType());
    }

    public MethodHandle getClassGuard(MethodType type) {
        return Guards.asType(this.classGuard, type);
    }

    public GuardedInvocationComponent getClassGuardedInvocationComponent(MethodHandle invocation, MethodType type) {
        return new GuardedInvocationComponent(invocation, getClassGuard(type), this.clazz, GuardedInvocationComponent.ValidationType.EXACT_CLASS);
    }

    SingleDynamicMethod getConstructorMethod(String signature) {
        return null;
    }

    private MethodHandle getAssignableGuard(MethodType type) {
        return Guards.asType(this.assignableGuard, type);
    }

    private GuardedInvocation getCallPropWithThis(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices) {
        switch (callSiteDescriptor.getNameTokenCount()) {
            case 3:
                return createGuardedDynamicMethodInvocation(callSiteDescriptor, linkerServices, callSiteDescriptor.getNameToken(2), this.methods);
            default:
                return null;
        }
    }

    private GuardedInvocation createGuardedDynamicMethodInvocation(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices, String methodName, Map<String, DynamicMethod> methodMap) {
        MethodHandle inv = getDynamicMethodInvocation(callSiteDescriptor, linkerServices, methodName, methodMap);
        if (inv == null) {
            return null;
        }
        return new GuardedInvocation(inv, getClassGuard(callSiteDescriptor.getMethodType()));
    }

    private MethodHandle getDynamicMethodInvocation(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices, String methodName, Map<String, DynamicMethod> methodMap) {
        DynamicMethod dynaMethod = getDynamicMethod(methodName, methodMap);
        if (dynaMethod != null) {
            return dynaMethod.getInvocation(callSiteDescriptor, linkerServices);
        }
        return null;
    }

    private DynamicMethod getDynamicMethod(String methodName, Map<String, DynamicMethod> methodMap) {
        DynamicMethod dynaMethod = methodMap.get(methodName);
        return dynaMethod != null ? dynaMethod : getExplicitSignatureDynamicMethod(methodName, methodMap);
    }

    private SingleDynamicMethod getExplicitSignatureDynamicMethod(String fullName, Map<String, DynamicMethod> methodsMap) {
        int openBrace;
        int lastChar = fullName.length() - 1;
        if (fullName.charAt(lastChar) != ')' || (openBrace = fullName.indexOf(40)) == -1) {
            return null;
        }
        String name = fullName.substring(0, openBrace);
        String signature = fullName.substring(openBrace + 1, lastChar);
        DynamicMethod simpleNamedMethod = methodsMap.get(name);
        if (simpleNamedMethod == null) {
            if (name.isEmpty()) {
                return getConstructorMethod(signature);
            }
            return null;
        }
        return simpleNamedMethod.getMethodForExactParamTypes(signature);
    }

    private GuardedInvocationComponent getPropertySetter(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices, List<String> operations) throws Exception {
        MethodHandle fallbackFolded;
        switch (callSiteDescriptor.getNameTokenCount()) {
            case 2:
                assertParameterCount(callSiteDescriptor, 3);
                MethodType origType = callSiteDescriptor.getMethodType();
                MethodType type = origType.returnType() == Void.TYPE ? origType : origType.changeReturnType(Object.class);
                MethodType setterType = type.dropParameterTypes(1, 2);
                MethodHandle boundGetter = MethodHandles.insertArguments(this.getPropertySetterHandle, 0, callSiteDescriptor.changeMethodType(setterType), linkerServices);
                MethodHandle typedGetter = linkerServices.asType(boundGetter, type.changeReturnType(MethodHandle.class));
                MethodHandle invokeHandle = MethodHandles.exactInvoker(setterType);
                MethodHandle invokeHandleFolded = MethodHandles.dropArguments(invokeHandle, 2, type.parameterType(1));
                GuardedInvocationComponent nextComponent = getGuardedInvocationComponent(callSiteDescriptor, linkerServices, operations);
                if (nextComponent == null) {
                    fallbackFolded = MethodHandles.dropArguments(CONSTANT_NULL_DROP_METHOD_HANDLE, 1, type.parameterList()).asType(type.insertParameterTypes(0, MethodHandle.class));
                } else {
                    fallbackFolded = MethodHandles.dropArguments(nextComponent.getGuardedInvocation().getInvocation(), 0, MethodHandle.class);
                }
                MethodHandle compositeSetter = MethodHandles.foldArguments(MethodHandles.guardWithTest(IS_METHOD_HANDLE_NOT_NULL, invokeHandleFolded, fallbackFolded), typedGetter);
                if (nextComponent == null) {
                    return getClassGuardedInvocationComponent(compositeSetter, type);
                }
                return nextComponent.compose(compositeSetter, getClassGuard(type), this.clazz, GuardedInvocationComponent.ValidationType.EXACT_CLASS);
            case 3:
                assertParameterCount(callSiteDescriptor, 2);
                GuardedInvocation gi = createGuardedDynamicMethodInvocation(callSiteDescriptor, linkerServices, callSiteDescriptor.getNameToken(2), this.propertySetters);
                if (gi != null) {
                    return new GuardedInvocationComponent(gi, this.clazz, GuardedInvocationComponent.ValidationType.EXACT_CLASS);
                }
                return getGuardedInvocationComponent(callSiteDescriptor, linkerServices, operations);
            default:
                return null;
        }
    }

    private GuardedInvocationComponent getPropertyGetter(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices, List<String> ops) throws Exception {
        MethodHandle fallbackFolded;
        switch (callSiteDescriptor.getNameTokenCount()) {
            case 2:
                MethodType type = callSiteDescriptor.getMethodType().changeReturnType(Object.class);
                assertParameterCount(callSiteDescriptor, 2);
                MethodHandle typedGetter = linkerServices.asType(this.getPropertyGetterHandle, type.changeReturnType(AnnotatedDynamicMethod.class));
                MethodHandle callSiteBoundMethodGetter = MethodHandles.insertArguments(GET_ANNOTATED_METHOD, 1, callSiteDescriptor.getLookup(), linkerServices);
                MethodHandle callSiteBoundInvoker = MethodHandles.filterArguments(GETTER_INVOKER, 0, callSiteBoundMethodGetter);
                MethodHandle invokeHandleTyped = linkerServices.asType(callSiteBoundInvoker, MethodType.methodType(type.returnType(), AnnotatedDynamicMethod.class, type.parameterType(0)));
                MethodHandle invokeHandleFolded = MethodHandles.dropArguments(invokeHandleTyped, 2, type.parameterType(1));
                GuardedInvocationComponent nextComponent = getGuardedInvocationComponent(callSiteDescriptor, linkerServices, ops);
                if (nextComponent == null) {
                    fallbackFolded = MethodHandles.dropArguments(CONSTANT_NULL_DROP_ANNOTATED_METHOD, 1, type.parameterList()).asType(type.insertParameterTypes(0, AnnotatedDynamicMethod.class));
                } else {
                    MethodHandle nextInvocation = nextComponent.getGuardedInvocation().getInvocation();
                    MethodType nextType = nextInvocation.type();
                    fallbackFolded = MethodHandles.dropArguments(nextInvocation.asType(nextType.changeReturnType(Object.class)), 0, AnnotatedDynamicMethod.class);
                }
                MethodHandle compositeGetter = MethodHandles.foldArguments(MethodHandles.guardWithTest(IS_ANNOTATED_METHOD_NOT_NULL, invokeHandleFolded, fallbackFolded), typedGetter);
                if (nextComponent == null) {
                    return getClassGuardedInvocationComponent(compositeGetter, type);
                }
                return nextComponent.compose(compositeGetter, getClassGuard(type), this.clazz, GuardedInvocationComponent.ValidationType.EXACT_CLASS);
            case 3:
                assertParameterCount(callSiteDescriptor, 1);
                AnnotatedDynamicMethod annGetter = this.propertyGetters.get(callSiteDescriptor.getNameToken(2));
                if (annGetter == null) {
                    return getGuardedInvocationComponent(callSiteDescriptor, linkerServices, ops);
                }
                MethodHandle getter = annGetter.getInvocation(callSiteDescriptor, linkerServices);
                GuardedInvocationComponent.ValidationType validationType = annGetter.validationType;
                return new GuardedInvocationComponent(getter, getGuard(validationType, callSiteDescriptor.getMethodType()), this.clazz, validationType);
            default:
                return null;
        }
    }

    private MethodHandle getGuard(GuardedInvocationComponent.ValidationType validationType, MethodType methodType) {
        switch (validationType) {
            case EXACT_CLASS:
                return getClassGuard(methodType);
            case INSTANCE_OF:
                return getAssignableGuard(methodType);
            case IS_ARRAY:
                return Guards.isArray(0, methodType);
            case NONE:
                return null;
            default:
                throw new AssertionError();
        }
    }

    private GuardedInvocationComponent getMethodGetter(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices, List<String> ops) throws Exception {
        MethodType type = callSiteDescriptor.getMethodType().changeReturnType(Object.class);
        switch (callSiteDescriptor.getNameTokenCount()) {
            case 2:
                assertParameterCount(callSiteDescriptor, 2);
                GuardedInvocationComponent nextComponent = getGuardedInvocationComponent(callSiteDescriptor, linkerServices, ops);
                if (nextComponent == null || !TypeUtilities.areAssignable(DynamicMethod.class, nextComponent.getGuardedInvocation().getInvocation().type().returnType())) {
                    return getClassGuardedInvocationComponent(linkerServices.asType(this.getDynamicMethod, type), type);
                }
                MethodHandle typedGetter = linkerServices.asType(this.getDynamicMethod, type);
                MethodHandle returnMethodHandle = linkerServices.asType(MethodHandles.dropArguments(OBJECT_IDENTITY, 1, type.parameterList()), type.insertParameterTypes(0, Object.class));
                MethodHandle nextComponentInvocation = nextComponent.getGuardedInvocation().getInvocation();
                if (!$assertionsDisabled && !nextComponentInvocation.type().changeReturnType(type.returnType()).equals(type)) {
                    throw new AssertionError();
                }
                MethodHandle nextCombinedInvocation = MethodHandles.dropArguments(nextComponentInvocation, 0, Object.class);
                MethodHandle compositeGetter = MethodHandles.foldArguments(MethodHandles.guardWithTest(IS_DYNAMIC_METHOD, returnMethodHandle, nextCombinedInvocation), typedGetter);
                return nextComponent.compose(compositeGetter, getClassGuard(type), this.clazz, GuardedInvocationComponent.ValidationType.EXACT_CLASS);
            case 3:
                assertParameterCount(callSiteDescriptor, 1);
                DynamicMethod method = getDynamicMethod(callSiteDescriptor.getNameToken(2));
                if (method == null) {
                    return getGuardedInvocationComponent(callSiteDescriptor, linkerServices, ops);
                }
                return getClassGuardedInvocationComponent(linkerServices.asType(MethodHandles.dropArguments(MethodHandles.constant(Object.class, method), 0, type.parameterType(0)), type), type);
            default:
                return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/beans/AbstractJavaLinker$MethodPair.class */
    public static class MethodPair {
        final MethodHandle method1;
        final MethodHandle method2;

        MethodPair(MethodHandle method1, MethodHandle method2) {
            this.method1 = method1;
            this.method2 = method2;
        }

        public MethodHandle guardWithTest(MethodHandle test) {
            return MethodHandles.guardWithTest(test, this.method1, this.method2);
        }
    }

    public static MethodPair matchReturnTypes(MethodHandle m1, MethodHandle m2) {
        MethodType type1 = m1.type();
        MethodType type2 = m2.type();
        Class<?> commonRetType = TypeUtilities.getCommonLosslessConversionType(type1.returnType(), type2.returnType());
        return new MethodPair(m1.asType(type1.changeReturnType(commonRetType)), m2.asType(type2.changeReturnType(commonRetType)));
    }

    private static void assertParameterCount(CallSiteDescriptor descriptor, int paramCount) {
        if (descriptor.getMethodType().parameterCount() != paramCount) {
            throw new BootstrapMethodError(descriptor.getName() + " must have exactly " + paramCount + " parameters.");
        }
    }

    private Object getPropertyGetterHandle(Object id) {
        return this.propertyGetters.get(String.valueOf(id));
    }

    private MethodHandle getPropertySetterHandle(CallSiteDescriptor setterDescriptor, LinkerServices linkerServices, Object id) {
        return getDynamicMethodInvocation(setterDescriptor, linkerServices, String.valueOf(id), this.propertySetters);
    }

    private Object getDynamicMethod(Object name) {
        return getDynamicMethod(String.valueOf(name), this.methods);
    }

    DynamicMethod getDynamicMethod(String name) {
        return getDynamicMethod(name, this.methods);
    }

    private static Method getMostGenericGetter(Method getter) {
        return getMostGenericGetter(getter.getName(), getter.getReturnType(), getter.getDeclaringClass());
    }

    private static Method getMostGenericGetter(String name, Class<?> returnType, Class<?> declaringClass) {
        Class<?>[] interfaces;
        if (declaringClass == null) {
            return null;
        }
        for (Class<?> itf : declaringClass.getInterfaces()) {
            Method itfGetter = getMostGenericGetter(name, returnType, itf);
            if (itfGetter != null) {
                return itfGetter;
            }
        }
        Method superGetter = getMostGenericGetter(name, returnType, declaringClass.getSuperclass());
        if (superGetter != null) {
            return superGetter;
        }
        if (!CheckRestrictedPackage.isRestrictedClass(declaringClass)) {
            try {
                return declaringClass.getMethod(name, new Class[0]);
            } catch (NoSuchMethodException e) {
                return null;
            }
        }
        return null;
    }

    /* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/beans/AbstractJavaLinker$AnnotatedDynamicMethod.class */
    public static final class AnnotatedDynamicMethod {
        private final SingleDynamicMethod method;
        final GuardedInvocationComponent.ValidationType validationType;
        static final /* synthetic */ boolean $assertionsDisabled;

        static {
            $assertionsDisabled = !AbstractJavaLinker.class.desiredAssertionStatus();
        }

        AnnotatedDynamicMethod(SingleDynamicMethod method, GuardedInvocationComponent.ValidationType validationType) {
            this.method = method;
            this.validationType = validationType;
        }

        MethodHandle getInvocation(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices) {
            return this.method.getInvocation(callSiteDescriptor, linkerServices);
        }

        MethodHandle getTarget(MethodHandles.Lookup lookup, LinkerServices linkerServices) {
            MethodHandle inv = linkerServices.filterInternalObjects(this.method.getTarget(lookup));
            if ($assertionsDisabled || inv != null) {
                return inv;
            }
            throw new AssertionError();
        }
    }
}
