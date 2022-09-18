package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.codegen.types.Type;

/* renamed from: jdk.nashorn.internal.ir.Optimistic */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/Optimistic.class */
public interface Optimistic {
    int getProgramPoint();

    Optimistic setProgramPoint(int i);

    boolean canBeOptimistic();

    Type getMostOptimisticType();

    Type getMostPessimisticType();

    Optimistic setType(Type type);
}
