package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.internal.dynalink.support.Guards;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/beans/DynamicMethodLinker.class */
class DynamicMethodLinker implements TypeBasedGuardingDynamicLinker {
    @Override // jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker
    public boolean canLinkType(Class<?> type) {
        return DynamicMethod.class.isAssignableFrom(type);
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) {
        MethodHandle invocation;
        MethodHandle ctorInvocation;
        Object receiver = linkRequest.getReceiver();
        if (!(receiver instanceof DynamicMethod)) {
            return null;
        }
        CallSiteDescriptor desc = linkRequest.getCallSiteDescriptor();
        if (desc.getNameTokenCount() != 2 && desc.getNameToken(0) != "dyn") {
            return null;
        }
        String operator = desc.getNameToken(1);
        DynamicMethod dynMethod = (DynamicMethod) receiver;
        boolean constructor = dynMethod.isConstructor();
        if (operator == "call" && !constructor) {
            invocation = dynMethod.getInvocation(CallSiteDescriptorFactory.dropParameterTypes(desc, 0, 1), linkerServices);
        } else if (operator != "new" || !constructor || (ctorInvocation = dynMethod.getInvocation(desc, linkerServices)) == null) {
            return null;
        } else {
            invocation = MethodHandles.insertArguments(ctorInvocation, 0, null);
        }
        if (invocation != null) {
            return new GuardedInvocation(MethodHandles.dropArguments(invocation, 0, desc.getMethodType().parameterType(0)), Guards.getIdentityGuard(receiver));
        }
        return null;
    }
}
