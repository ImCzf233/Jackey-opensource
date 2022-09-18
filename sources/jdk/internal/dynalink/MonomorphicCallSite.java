package jdk.internal.dynalink;

import java.lang.invoke.MethodHandle;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.support.AbstractRelinkableCallSite;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/MonomorphicCallSite.class */
public class MonomorphicCallSite extends AbstractRelinkableCallSite {
    public MonomorphicCallSite(CallSiteDescriptor descriptor) {
        super(descriptor);
    }

    @Override // jdk.internal.dynalink.RelinkableCallSite
    public void relink(GuardedInvocation guardedInvocation, MethodHandle relink) {
        setTarget(guardedInvocation.compose(relink));
    }

    @Override // jdk.internal.dynalink.RelinkableCallSite
    public void resetAndRelink(GuardedInvocation guardedInvocation, MethodHandle relink) {
        relink(guardedInvocation, relink);
    }
}
