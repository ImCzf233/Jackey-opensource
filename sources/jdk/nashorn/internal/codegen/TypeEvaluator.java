package jdk.nashorn.internal.codegen;

import java.lang.invoke.MethodType;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.objects.ArrayBufferView;
import jdk.nashorn.internal.objects.NativeArray;
import jdk.nashorn.internal.p001ir.AccessNode;
import jdk.nashorn.internal.p001ir.CallNode;
import jdk.nashorn.internal.p001ir.Expression;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.IdentNode;
import jdk.nashorn.internal.p001ir.IndexNode;
import jdk.nashorn.internal.p001ir.Optimistic;
import jdk.nashorn.internal.runtime.FindProperty;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.Property;
import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/TypeEvaluator.class */
public final class TypeEvaluator {
    private static final MethodType EMPTY_INVOCATION_TYPE;
    private final Compiler compiler;
    private final ScriptObject runtimeScope;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !TypeEvaluator.class.desiredAssertionStatus();
        EMPTY_INVOCATION_TYPE = MethodType.methodType(Object.class, ScriptFunction.class, Object.class);
    }

    public TypeEvaluator(Compiler compiler, ScriptObject runtimeScope) {
        this.compiler = compiler;
        this.runtimeScope = runtimeScope;
    }

    public boolean hasStringPropertyIterator(Expression expr) {
        return evaluateSafely(expr) instanceof ScriptObject;
    }

    public Type getOptimisticType(Optimistic node) {
        if ($assertionsDisabled || this.compiler.useOptimisticTypes()) {
            int programPoint = node.getProgramPoint();
            Type validType = this.compiler.getInvalidatedProgramPointType(programPoint);
            if (validType != null) {
                return validType;
            }
            Type mostOptimisticType = node.getMostOptimisticType();
            Type evaluatedType = getEvaluatedType(node);
            if (evaluatedType != null) {
                if (evaluatedType.widerThan(mostOptimisticType)) {
                    Type newValidType = (evaluatedType.isObject() || evaluatedType.isBoolean()) ? Type.OBJECT : evaluatedType;
                    this.compiler.addInvalidatedProgramPoint(node.getProgramPoint(), newValidType);
                }
                return evaluatedType;
            }
            return mostOptimisticType;
        }
        throw new AssertionError();
    }

    private static Type getPropertyType(ScriptObject sobj, String name) {
        Property property;
        Class<?> propertyClass;
        FindProperty find = sobj.findProperty(name, true);
        if (find == null || (propertyClass = (property = find.getProperty()).getType()) == null) {
            return null;
        }
        if (propertyClass.isPrimitive()) {
            return Type.typeFor(propertyClass);
        }
        ScriptObject owner = find.getOwner();
        if (property.hasGetterFunction(owner)) {
            return Type.OBJECT;
        }
        Object value = property.needsDeclaration() ? ScriptRuntime.UNDEFINED : property.getObjectValue(owner, owner);
        if (value == ScriptRuntime.UNDEFINED) {
            return null;
        }
        return Type.typeFor(JSType.unboxedFieldType(value));
    }

    public void declareLocalSymbol(String symbolName) {
        if (!$assertionsDisabled && (!this.compiler.useOptimisticTypes() || !this.compiler.isOnDemandCompilation() || this.runtimeScope == null)) {
            throw new AssertionError("useOptimistic=" + this.compiler.useOptimisticTypes() + " isOnDemand=" + this.compiler.isOnDemandCompilation() + " scope=" + this.runtimeScope);
        }
        if (this.runtimeScope.findProperty(symbolName, false) == null) {
            this.runtimeScope.addOwnProperty(symbolName, 7, ScriptRuntime.UNDEFINED);
        }
    }

    private Object evaluateSafely(Expression expr) {
        if (expr instanceof IdentNode) {
            if (this.runtimeScope != null) {
                return evaluatePropertySafely(this.runtimeScope, ((IdentNode) expr).getName());
            }
            return null;
        } else if (expr instanceof AccessNode) {
            AccessNode accessNode = (AccessNode) expr;
            Object base = evaluateSafely(accessNode.getBase());
            if (!(base instanceof ScriptObject)) {
                return null;
            }
            return evaluatePropertySafely((ScriptObject) base, accessNode.getProperty());
        } else {
            return null;
        }
    }

    private static Object evaluatePropertySafely(ScriptObject sobj, String name) {
        FindProperty find = sobj.findProperty(name, true);
        if (find == null) {
            return null;
        }
        Property property = find.getProperty();
        ScriptObject owner = find.getOwner();
        if (property.hasGetterFunction(owner)) {
            return null;
        }
        return property.getObjectValue(owner, owner);
    }

    private Type getEvaluatedType(Optimistic expr) {
        RecompilableScriptFunctionData data;
        if (expr instanceof IdentNode) {
            if (this.runtimeScope == null) {
                return null;
            }
            return getPropertyType(this.runtimeScope, ((IdentNode) expr).getName());
        } else if (expr instanceof AccessNode) {
            AccessNode accessNode = (AccessNode) expr;
            Object base = evaluateSafely(accessNode.getBase());
            if (!(base instanceof ScriptObject)) {
                return null;
            }
            return getPropertyType((ScriptObject) base, accessNode.getProperty());
        } else if (expr instanceof IndexNode) {
            IndexNode indexNode = (IndexNode) expr;
            Object base2 = evaluateSafely(indexNode.getBase());
            if ((base2 instanceof NativeArray) || (base2 instanceof ArrayBufferView)) {
                return ((ScriptObject) base2).getArray().getOptimisticType();
            }
            return null;
        } else if (expr instanceof CallNode) {
            CallNode callExpr = (CallNode) expr;
            Expression fnExpr = callExpr.getFunction();
            if ((fnExpr instanceof FunctionNode) && this.compiler.getContext().getEnv()._lazy_compilation) {
                FunctionNode fn = (FunctionNode) fnExpr;
                if (callExpr.getArgs().isEmpty() && (data = this.compiler.getScriptFunctionData(fn.getId())) != null) {
                    Type returnType = Type.typeFor(data.getReturnType(EMPTY_INVOCATION_TYPE, this.runtimeScope));
                    if (returnType == Type.BOOLEAN) {
                        return Type.OBJECT;
                    }
                    if (!$assertionsDisabled && returnType != Type.INT && returnType != Type.NUMBER && returnType != Type.OBJECT) {
                        throw new AssertionError();
                    }
                    return returnType;
                }
                return null;
            }
            return null;
        } else {
            return null;
        }
    }
}
