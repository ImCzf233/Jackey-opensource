package jdk.nashorn.internal.runtime;

import jdk.nashorn.internal.objects.annotations.SpecializedFunction;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/OptimisticBuiltins.class */
public interface OptimisticBuiltins {
    SpecializedFunction.LinkLogic getLinkLogic(Class<? extends SpecializedFunction.LinkLogic> cls);

    boolean hasPerInstanceAssumptions();
}
