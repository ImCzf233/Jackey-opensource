package jdk.internal.dynalink.support;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/support/CompositeGuardingDynamicLinker.class */
public class CompositeGuardingDynamicLinker implements GuardingDynamicLinker, Serializable {
    private static final long serialVersionUID = 1;
    private final GuardingDynamicLinker[] linkers;

    public CompositeGuardingDynamicLinker(Iterable<? extends GuardingDynamicLinker> linkers) {
        List<GuardingDynamicLinker> l = new LinkedList<>();
        for (GuardingDynamicLinker linker : linkers) {
            l.add(linker);
        }
        this.linkers = (GuardingDynamicLinker[]) l.toArray(new GuardingDynamicLinker[l.size()]);
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) throws Exception {
        GuardingDynamicLinker[] guardingDynamicLinkerArr;
        for (GuardingDynamicLinker linker : this.linkers) {
            GuardedInvocation invocation = linker.getGuardedInvocation(linkRequest, linkerServices);
            if (invocation != null) {
                return invocation;
            }
        }
        return null;
    }
}
