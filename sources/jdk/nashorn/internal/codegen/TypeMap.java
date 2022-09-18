package jdk.nashorn.internal.codegen;

import java.lang.invoke.MethodType;
import java.util.Arrays;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.runtime.ScriptFunction;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/TypeMap.class */
public final class TypeMap {
    private final int functionNodeId;
    private final Type[] paramTypes;
    private final Type returnType;
    private final boolean needsCallee;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !TypeMap.class.desiredAssertionStatus();
    }

    public TypeMap(int functionNodeId, MethodType type, boolean needsCallee) {
        Class<?>[] parameterArray;
        Type[] types = new Type[type.parameterCount()];
        int pos = 0;
        for (Class<?> p : type.parameterArray()) {
            int i = pos;
            pos++;
            types[i] = Type.typeFor(p);
        }
        this.functionNodeId = functionNodeId;
        this.paramTypes = types;
        this.returnType = Type.typeFor(type.returnType());
        this.needsCallee = needsCallee;
    }

    public Type[] getParameterTypes(int functionNodeId) {
        if ($assertionsDisabled || this.functionNodeId == functionNodeId) {
            return (Type[]) this.paramTypes.clone();
        }
        throw new AssertionError();
    }

    public MethodType getCallSiteType(FunctionNode functionNode) {
        if ($assertionsDisabled || this.functionNodeId == functionNode.getId()) {
            Type[] types = this.paramTypes;
            MethodType mt = MethodType.methodType(this.returnType.getTypeClass());
            if (this.needsCallee) {
                mt = mt.appendParameterTypes(ScriptFunction.class);
            }
            MethodType mt2 = mt.appendParameterTypes(Object.class);
            for (Type type : types) {
                if (type == null) {
                    return null;
                }
                mt2 = mt2.appendParameterTypes(type.getTypeClass());
            }
            return mt2;
        }
        throw new AssertionError();
    }

    public boolean needsCallee() {
        return this.needsCallee;
    }

    public Type get(FunctionNode functionNode, int pos) {
        if ($assertionsDisabled || this.functionNodeId == functionNode.getId()) {
            Type[] types = this.paramTypes;
            if (!$assertionsDisabled && types != null && pos >= types.length) {
                throw new AssertionError("fn = " + functionNode.getId() + " types=" + Arrays.toString(types) + " || pos=" + pos + " >= length=" + types.length + " in " + this);
            }
            if (types != null && pos < types.length) {
                return types[pos];
            }
            return null;
        }
        throw new AssertionError();
    }

    public String toString() {
        return toString("");
    }

    String toString(String prefix) {
        StringBuilder sb = new StringBuilder();
        int id = this.functionNodeId;
        sb.append(prefix).append('\t');
        sb.append("function ").append(id).append('\n');
        sb.append(prefix).append("\t\tparamTypes=");
        sb.append(Arrays.toString(this.paramTypes));
        sb.append('\n');
        sb.append(prefix).append("\t\treturnType=");
        Type ret = this.returnType;
        sb.append(ret == null ? "N/A" : ret);
        sb.append('\n');
        return sb.toString();
    }
}
