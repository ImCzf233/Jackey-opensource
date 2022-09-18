package jdk.internal.dynalink;

import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/GuardedInvocationFilter.class */
public interface GuardedInvocationFilter {
    GuardedInvocation filter(GuardedInvocation guardedInvocation, LinkRequest linkRequest, LinkerServices linkerServices);
}
