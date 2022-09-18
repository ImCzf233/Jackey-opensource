package jdk.internal.dynalink;

import java.lang.invoke.MethodHandle;
import jdk.internal.dynalink.linker.GuardedInvocation;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/RelinkableCallSite.class */
public interface RelinkableCallSite {
    void initialize(MethodHandle methodHandle);

    CallSiteDescriptor getDescriptor();

    void relink(GuardedInvocation guardedInvocation, MethodHandle methodHandle);

    void resetAndRelink(GuardedInvocation guardedInvocation, MethodHandle methodHandle);
}
