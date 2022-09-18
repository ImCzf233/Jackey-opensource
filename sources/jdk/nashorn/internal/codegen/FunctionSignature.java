package jdk.nashorn.internal.codegen;

import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.List;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.p001ir.Expression;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.runtime.ScriptFunction;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/FunctionSignature.class */
public final class FunctionSignature {
    private final Type[] paramTypes;
    private final Type returnType;
    private final String descriptor;
    private final MethodType methodType;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !FunctionSignature.class.desiredAssertionStatus();
    }

    public FunctionSignature(boolean hasSelf, boolean hasCallee, Type retType, List<? extends Expression> args) {
        this(hasSelf, hasCallee, retType, typeArray(args));
    }

    public FunctionSignature(boolean hasSelf, boolean hasCallee, Type retType, int nArgs) {
        this(hasSelf, hasCallee, retType, objectArgs(nArgs));
    }

    private FunctionSignature(boolean hasSelf, boolean hasCallee, Type retType, Type... argTypes) {
        boolean isVarArg;
        Type[] typeArr;
        int count = 1;
        if (argTypes == null) {
            isVarArg = true;
        } else {
            isVarArg = argTypes.length > 250;
            count = isVarArg ? 1 : argTypes.length;
        }
        count = hasCallee ? count + 1 : count;
        count = hasSelf ? count + 1 : count;
        this.paramTypes = new Type[count];
        int next = 0;
        if (hasCallee) {
            next = 0 + 1;
            this.paramTypes[0] = Type.typeFor(ScriptFunction.class);
        }
        if (hasSelf) {
            int i = next;
            next++;
            this.paramTypes[i] = Type.OBJECT;
        }
        if (isVarArg) {
            this.paramTypes[next] = Type.OBJECT_ARRAY;
        } else if (argTypes != null) {
            int j = 0;
            while (next < count) {
                int i2 = j;
                j++;
                Type type = argTypes[i2];
                int i3 = next;
                next++;
                this.paramTypes[i3] = type.isObject() ? Type.OBJECT : type;
            }
        } else if (!$assertionsDisabled) {
            throw new AssertionError("isVarArgs cannot be false when argTypes are null");
        }
        this.returnType = retType;
        this.descriptor = Type.getMethodDescriptor(this.returnType, this.paramTypes);
        List<Class<?>> paramTypeList = new ArrayList<>();
        for (Type paramType : this.paramTypes) {
            paramTypeList.add(paramType.getTypeClass());
        }
        this.methodType = Lookup.f248MH.type(this.returnType.getTypeClass(), (Class[]) paramTypeList.toArray(new Class[this.paramTypes.length]));
    }

    public FunctionSignature(FunctionNode functionNode) {
        this(true, functionNode.needsCallee(), functionNode.getReturnType(), (List<? extends Expression>) ((!functionNode.isVarArg() || functionNode.isProgram()) ? functionNode.getParameters() : null));
    }

    private static Type[] typeArray(List<? extends Expression> args) {
        if (args == null) {
            return null;
        }
        Type[] typeArray = new Type[args.size()];
        int pos = 0;
        for (Expression arg : args) {
            int i = pos;
            pos++;
            typeArray[i] = arg.getType();
        }
        return typeArray;
    }

    public String toString() {
        return this.descriptor;
    }

    public int size() {
        return this.paramTypes.length;
    }

    public Type[] getParamTypes() {
        return (Type[]) this.paramTypes.clone();
    }

    public MethodType getMethodType() {
        return this.methodType;
    }

    public Type getReturnType() {
        return this.returnType;
    }

    private static Type[] objectArgs(int nArgs) {
        Type[] array = new Type[nArgs];
        for (int i = 0; i < nArgs; i++) {
            array[i] = Type.OBJECT;
        }
        return array;
    }
}
