package jdk.nashorn.internal.runtime;

import java.util.concurrent.atomic.LongAdder;
import jdk.nashorn.internal.codegen.CompilerConstants;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/Scope.class */
public class Scope extends ScriptObject {
    private int splitState = -1;
    private static final LongAdder count;
    public static final CompilerConstants.Call GET_SPLIT_STATE;
    public static final CompilerConstants.Call SET_SPLIT_STATE;

    static {
        count = Context.DEBUG ? new LongAdder() : null;
        GET_SPLIT_STATE = CompilerConstants.virtualCallNoLookup(Scope.class, "getSplitState", Integer.TYPE, new Class[0]);
        SET_SPLIT_STATE = CompilerConstants.virtualCallNoLookup(Scope.class, "setSplitState", Void.TYPE, Integer.TYPE);
    }

    public Scope(PropertyMap map) {
        super(map);
        incrementCount();
    }

    public Scope(ScriptObject proto, PropertyMap map) {
        super(proto, map);
        incrementCount();
    }

    public Scope(PropertyMap map, long[] primitiveSpill, Object[] objectSpill) {
        super(map, primitiveSpill, objectSpill);
        incrementCount();
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public boolean isScope() {
        return true;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    boolean hasWithScope() {
        ScriptObject scriptObject = this;
        while (true) {
            ScriptObject obj = scriptObject;
            if (obj != null) {
                if (!(obj instanceof WithObject)) {
                    scriptObject = obj.getProto();
                } else {
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    public int getSplitState() {
        return this.splitState;
    }

    public void setSplitState(int state) {
        this.splitState = state;
    }

    public static long getScopeCount() {
        if (count != null) {
            return count.sum();
        }
        return 0L;
    }

    private static void incrementCount() {
        if (Context.DEBUG) {
            count.increment();
        }
    }
}
