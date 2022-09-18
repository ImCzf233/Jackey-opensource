package jdk.internal.dynalink.support;

import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/support/BottomGuardingDynamicLinker.class */
public class BottomGuardingDynamicLinker implements TypeBasedGuardingDynamicLinker {
    public static final BottomGuardingDynamicLinker INSTANCE = new BottomGuardingDynamicLinker();

    private BottomGuardingDynamicLinker() {
    }

    @Override // jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker
    public boolean canLinkType(Class<?> type) {
        return false;
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) {
        return null;
    }
}
