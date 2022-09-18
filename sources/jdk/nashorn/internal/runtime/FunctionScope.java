package jdk.nashorn.internal.runtime;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/FunctionScope.class */
public class FunctionScope extends Scope {
    public final ScriptObject arguments;

    public FunctionScope(PropertyMap map, ScriptObject callerScope, ScriptObject arguments) {
        super(callerScope, map);
        this.arguments = arguments;
    }

    public FunctionScope(PropertyMap map, ScriptObject callerScope) {
        super(callerScope, map);
        this.arguments = null;
    }

    public FunctionScope(PropertyMap map, long[] primitiveSpill, Object[] objectSpill) {
        super(map, primitiveSpill, objectSpill);
        this.arguments = null;
    }
}
