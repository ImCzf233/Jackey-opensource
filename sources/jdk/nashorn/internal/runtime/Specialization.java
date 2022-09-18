package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import jdk.nashorn.internal.objects.annotations.SpecializedFunction;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/Specialization.class */
public final class Specialization {

    /* renamed from: mh */
    private final MethodHandle f272mh;
    private final Class<? extends SpecializedFunction.LinkLogic> linkLogicClass;
    private final boolean isOptimistic;

    public Specialization(MethodHandle mh) {
        this(mh, false);
    }

    public Specialization(MethodHandle mh, boolean isOptimistic) {
        this(mh, null, isOptimistic);
    }

    public Specialization(MethodHandle mh, Class<? extends SpecializedFunction.LinkLogic> linkLogicClass, boolean isOptimistic) {
        this.f272mh = mh;
        this.isOptimistic = isOptimistic;
        if (linkLogicClass != null) {
            this.linkLogicClass = SpecializedFunction.LinkLogic.isEmpty(linkLogicClass) ? null : linkLogicClass;
        } else {
            this.linkLogicClass = null;
        }
    }

    public MethodHandle getMethodHandle() {
        return this.f272mh;
    }

    public Class<? extends SpecializedFunction.LinkLogic> getLinkLogicClass() {
        return this.linkLogicClass;
    }

    public boolean isOptimistic() {
        return this.isOptimistic;
    }
}
