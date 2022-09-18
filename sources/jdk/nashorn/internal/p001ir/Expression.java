package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.runtime.UnwarrantedOptimismException;

/* renamed from: jdk.nashorn.internal.ir.Expression */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/Expression.class */
public abstract class Expression extends Node {
    private static final long serialVersionUID = 1;
    static final String OPT_IDENTIFIER = "%";

    public abstract Type getType();

    public Expression(long token, int start, int finish) {
        super(token, start, finish);
    }

    public Expression(long token, int finish) {
        super(token, finish);
    }

    public Expression(Expression expr) {
        super(expr);
    }

    public boolean isLocal() {
        return false;
    }

    public boolean isSelfModifying() {
        return false;
    }

    public Type getWidestOperationType() {
        return Type.OBJECT;
    }

    public final boolean isOptimistic() {
        return getType().narrowerThan(getWidestOperationType());
    }

    public void optimisticTypeToString(StringBuilder sb) {
        optimisticTypeToString(sb, isOptimistic());
    }

    public void optimisticTypeToString(StringBuilder sb, boolean optimistic) {
        sb.append('{');
        Type type = getType();
        String desc = type == Type.UNDEFINED ? "U" : type.getDescriptor();
        sb.append(desc.charAt(desc.length() - 1) == ';' ? "O" : desc);
        if (isOptimistic() && optimistic) {
            sb.append(OPT_IDENTIFIER);
            int pp = ((Optimistic) this).getProgramPoint();
            if (UnwarrantedOptimismException.isValid(pp)) {
                sb.append('_').append(pp);
            }
        }
        sb.append('}');
    }

    public boolean isAlwaysFalse() {
        return false;
    }

    public boolean isAlwaysTrue() {
        return false;
    }

    public static boolean isAlwaysFalse(Expression test) {
        return test != null && test.isAlwaysFalse();
    }

    public static boolean isAlwaysTrue(Expression test) {
        return test == null || test.isAlwaysTrue();
    }
}
