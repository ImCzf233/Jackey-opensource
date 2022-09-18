package jdk.internal.dynalink.support;

import jdk.internal.dynalink.GuardedInvocationFilter;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/support/DefaultPrelinkFilter.class */
public class DefaultPrelinkFilter implements GuardedInvocationFilter {
    @Override // jdk.internal.dynalink.GuardedInvocationFilter
    public GuardedInvocation filter(GuardedInvocation inv, LinkRequest request, LinkerServices linkerServices) {
        return inv.asType(linkerServices, request.getCallSiteDescriptor().getMethodType());
    }
}
