package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.Expression;

/* renamed from: jdk.nashorn.internal.ir.Assignment */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/Assignment.class */
public interface Assignment<D extends Expression> {
    D getAssignmentDest();

    Expression getAssignmentSource();

    Node setAssignmentDest(D d);
}
