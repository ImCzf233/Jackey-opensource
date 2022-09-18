package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.nashorn.api.scripting.AbstractJSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.linker.NashornCallSiteDescriptor;
import jdk.nashorn.internal.runtime.linker.NashornGuards;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/WithObject.class */
public final class WithObject extends Scope {
    private static final MethodHandle WITHEXPRESSIONGUARD = findOwnMH("withExpressionGuard", Boolean.TYPE, Object.class, PropertyMap.class, SwitchPoint[].class);
    private static final MethodHandle WITHEXPRESSIONFILTER = findOwnMH("withFilterExpression", Object.class, Object.class);
    private static final MethodHandle WITHSCOPEFILTER = findOwnMH("withFilterScope", Object.class, Object.class);
    private static final MethodHandle BIND_TO_EXPRESSION_OBJ = findOwnMH("bindToExpression", Object.class, Object.class, Object.class);
    private static final MethodHandle BIND_TO_EXPRESSION_FN = findOwnMH("bindToExpression", Object.class, ScriptFunction.class, Object.class);
    private final ScriptObject expression;

    public WithObject(ScriptObject scope, ScriptObject expression) {
        super(scope, null);
        this.expression = expression;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean delete(Object key, boolean strict) {
        ScriptObject self = this.expression;
        String propName = JSType.toString(key);
        FindProperty find = self.findProperty(propName, true);
        if (find != null) {
            return self.delete(propName, strict);
        }
        return false;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public GuardedInvocation lookup(CallSiteDescriptor desc, LinkRequest request) {
        String name;
        boolean isNamedOperation;
        String fallBack;
        if (request.isCallSiteUnstable()) {
            return super.lookup(desc, request);
        }
        NashornCallSiteDescriptor ndesc = (NashornCallSiteDescriptor) desc;
        FindProperty find = null;
        GuardedInvocation link = null;
        if (desc.getNameTokenCount() > 2) {
            isNamedOperation = true;
            name = desc.getNameToken(2);
        } else {
            isNamedOperation = false;
            name = null;
        }
        ScriptObject self = this.expression;
        if (isNamedOperation) {
            find = self.findProperty(name, true);
        }
        if (find != null) {
            link = self.lookup(desc, request);
            if (link != null) {
                return fixExpressionCallSite(ndesc, link);
            }
        }
        ScriptObject scope = getProto();
        if (isNamedOperation) {
            find = scope.findProperty(name, true);
        }
        if (find != null) {
            return fixScopeCallSite(scope.lookup(desc, request), name, find.getOwner());
        }
        if (self != null) {
            String operator = CallSiteDescriptorFactory.tokenizeOperators(desc).get(0);
            boolean z = true;
            switch (operator.hashCode()) {
                case -75566075:
                    if (operator.equals("getElem")) {
                        z = true;
                        break;
                    }
                    break;
                case -75232295:
                    if (operator.equals("getProp")) {
                        z = true;
                        break;
                    }
                    break;
                case 618460119:
                    if (operator.equals("getMethod")) {
                        z = true;
                        break;
                    }
                    break;
                case 1402960095:
                    if (operator.equals("callMethod")) {
                        z = false;
                        break;
                    }
                    break;
            }
            switch (z) {
                case false:
                    throw new AssertionError();
                case true:
                    fallBack = ScriptObject.NO_SUCH_METHOD_NAME;
                    break;
                case true:
                case true:
                    fallBack = ScriptObject.NO_SUCH_PROPERTY_NAME;
                    break;
                default:
                    fallBack = null;
                    break;
            }
            if (fallBack != null) {
                FindProperty find2 = self.findProperty(fallBack, true);
                if (find2 != null) {
                    boolean z2 = true;
                    switch (operator.hashCode()) {
                        case -75566075:
                            if (operator.equals("getElem")) {
                                z2 = true;
                                break;
                            }
                            break;
                        case -75232295:
                            if (operator.equals("getProp")) {
                                z2 = true;
                                break;
                            }
                            break;
                        case 618460119:
                            if (operator.equals("getMethod")) {
                                z2 = false;
                                break;
                            }
                            break;
                    }
                    switch (z2) {
                        case false:
                            link = self.noSuchMethod(desc, request);
                            break;
                        case true:
                        case true:
                            link = self.noSuchProperty(desc, request);
                            break;
                    }
                }
            }
            if (link != null) {
                return fixExpressionCallSite(ndesc, link);
            }
        }
        GuardedInvocation link2 = scope.lookup(desc, request);
        if (link2 != null) {
            return fixScopeCallSite(link2, name, null);
        }
        return null;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public FindProperty findProperty(String key, boolean deep, ScriptObject start) {
        FindProperty exprProperty = this.expression.findProperty(key, true, this.expression);
        if (exprProperty != null) {
            return exprProperty;
        }
        return super.findProperty(key, deep, start);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public Object invokeNoSuchProperty(String name, boolean isScope, int programPoint) {
        FindProperty find = this.expression.findProperty(ScriptObject.NO_SUCH_PROPERTY_NAME, true);
        if (find != null) {
            Object func = find.getObjectValue();
            if (func instanceof ScriptFunction) {
                ScriptFunction sfunc = (ScriptFunction) func;
                Object self = (!isScope || !sfunc.isStrict()) ? this.expression : ScriptRuntime.UNDEFINED;
                return ScriptRuntime.apply(sfunc, self, name);
            }
        }
        return getProto().invokeNoSuchProperty(name, isScope, programPoint);
    }

    @Override // jdk.nashorn.internal.runtime.Scope
    public void setSplitState(int state) {
        ((Scope) getNonWithParent()).setSplitState(state);
    }

    @Override // jdk.nashorn.internal.runtime.Scope
    public int getSplitState() {
        return ((Scope) getNonWithParent()).getSplitState();
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public void addBoundProperties(ScriptObject source, Property[] properties) {
        getNonWithParent().addBoundProperties(source, properties);
    }

    private ScriptObject getNonWithParent() {
        ScriptObject proto;
        ScriptObject proto2 = getProto();
        while (true) {
            proto = proto2;
            if (proto == null || !(proto instanceof WithObject)) {
                break;
            }
            proto2 = proto.getProto();
        }
        return proto;
    }

    private static GuardedInvocation fixReceiverType(GuardedInvocation link, MethodHandle filter) {
        MethodType invType = link.getInvocation().type();
        MethodType newInvType = invType.changeParameterType(0, filter.type().returnType());
        return link.asType(newInvType);
    }

    private static GuardedInvocation fixExpressionCallSite(NashornCallSiteDescriptor desc, GuardedInvocation link) {
        if (!"getMethod".equals(desc.getFirstOperator())) {
            return fixReceiverType(link, WITHEXPRESSIONFILTER).filterArguments(0, WITHEXPRESSIONFILTER);
        }
        MethodHandle linkInvocation = link.getInvocation();
        MethodType linkType = linkInvocation.type();
        boolean linkReturnsFunction = ScriptFunction.class.isAssignableFrom(linkType.returnType());
        return link.replaceMethods(Lookup.f248MH.foldArguments(linkReturnsFunction ? BIND_TO_EXPRESSION_FN : BIND_TO_EXPRESSION_OBJ, filterReceiver(linkInvocation.asType(linkType.changeReturnType(linkReturnsFunction ? ScriptFunction.class : Object.class).changeParameterType(0, Object.class)), WITHEXPRESSIONFILTER)), filterGuardReceiver(link, WITHEXPRESSIONFILTER));
    }

    private GuardedInvocation fixScopeCallSite(GuardedInvocation link, String name, ScriptObject owner) {
        GuardedInvocation newLink = fixReceiverType(link, WITHSCOPEFILTER);
        MethodHandle expressionGuard = expressionGuard(name, owner);
        MethodHandle filterGuardReceiver = filterGuardReceiver(newLink, WITHSCOPEFILTER);
        return link.replaceMethods(filterReceiver(newLink.getInvocation(), WITHSCOPEFILTER), NashornGuards.combineGuards(expressionGuard, filterGuardReceiver));
    }

    private static MethodHandle filterGuardReceiver(GuardedInvocation link, MethodHandle receiverFilter) {
        MethodHandle test = link.getGuard();
        if (test == null) {
            return null;
        }
        Class<?> receiverType = test.type().parameterType(0);
        MethodHandle filter = Lookup.f248MH.asType(receiverFilter, receiverFilter.type().changeParameterType(0, receiverType).changeReturnType(receiverType));
        return filterReceiver(test, filter);
    }

    private static MethodHandle filterReceiver(MethodHandle mh, MethodHandle receiverFilter) {
        return Lookup.f248MH.filterArguments(mh, 0, receiverFilter.asType(receiverFilter.type().changeReturnType(mh.type().parameterType(0))));
    }

    public static Object withFilterExpression(Object receiver) {
        return ((WithObject) receiver).expression;
    }

    private static Object bindToExpression(Object fn, final Object receiver) {
        if (fn instanceof ScriptFunction) {
            return bindToExpression((ScriptFunction) fn, receiver);
        }
        if (fn instanceof ScriptObjectMirror) {
            final ScriptObjectMirror mirror = (ScriptObjectMirror) fn;
            if (mirror.isFunction()) {
                return new AbstractJSObject() { // from class: jdk.nashorn.internal.runtime.WithObject.1
                    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
                    public Object call(Object thiz, Object... args) {
                        return mirror.call(WithObject.withFilterExpression(receiver), args);
                    }
                };
            }
        }
        return fn;
    }

    private static Object bindToExpression(ScriptFunction fn, Object receiver) {
        return fn.createBound(withFilterExpression(receiver), ScriptRuntime.EMPTY_ARRAY);
    }

    private MethodHandle expressionGuard(String name, ScriptObject owner) {
        PropertyMap map = this.expression.getMap();
        SwitchPoint[] sp = this.expression.getProtoSwitchPoints(name, owner);
        return Lookup.f248MH.insertArguments(WITHEXPRESSIONGUARD, 1, map, sp);
    }

    private static boolean withExpressionGuard(Object receiver, PropertyMap map, SwitchPoint[] sp) {
        return ((WithObject) receiver).expression.getMap() == map && !hasBeenInvalidated(sp);
    }

    private static boolean hasBeenInvalidated(SwitchPoint[] switchPoints) {
        if (switchPoints != null) {
            for (SwitchPoint switchPoint : switchPoints) {
                if (switchPoint.hasBeenInvalidated()) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public static Object withFilterScope(Object receiver) {
        return ((WithObject) receiver).getProto();
    }

    public ScriptObject getExpression() {
        return this.expression;
    }

    private static MethodHandle findOwnMH(String name, Class<?> rtype, Class<?>... types) {
        return Lookup.f248MH.findStatic(MethodHandles.lookup(), WithObject.class, name, Lookup.f248MH.type(rtype, types));
    }
}
