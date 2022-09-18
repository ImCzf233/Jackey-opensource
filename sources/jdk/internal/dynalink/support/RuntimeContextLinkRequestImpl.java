package jdk.internal.dynalink.support;

import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.LinkRequest;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/support/RuntimeContextLinkRequestImpl.class */
public class RuntimeContextLinkRequestImpl extends LinkRequestImpl {
    private final int runtimeContextArgCount;
    private LinkRequestImpl contextStrippedRequest;

    public RuntimeContextLinkRequestImpl(CallSiteDescriptor callSiteDescriptor, Object callSiteToken, int linkCount, boolean callSiteUnstable, Object[] arguments, int runtimeContextArgCount) {
        super(callSiteDescriptor, callSiteToken, linkCount, callSiteUnstable, arguments);
        if (runtimeContextArgCount < 1) {
            throw new IllegalArgumentException("runtimeContextArgCount < 1");
        }
        this.runtimeContextArgCount = runtimeContextArgCount;
    }

    @Override // jdk.internal.dynalink.support.LinkRequestImpl, jdk.internal.dynalink.linker.LinkRequest
    public LinkRequest withoutRuntimeContext() {
        if (this.contextStrippedRequest == null) {
            this.contextStrippedRequest = new LinkRequestImpl(CallSiteDescriptorFactory.dropParameterTypes(getCallSiteDescriptor(), 1, this.runtimeContextArgCount + 1), getCallSiteToken(), getLinkCount(), isCallSiteUnstable(), getTruncatedArguments());
        }
        return this.contextStrippedRequest;
    }

    @Override // jdk.internal.dynalink.support.LinkRequestImpl, jdk.internal.dynalink.linker.LinkRequest
    public LinkRequest replaceArguments(CallSiteDescriptor callSiteDescriptor, Object[] arguments) {
        return new RuntimeContextLinkRequestImpl(callSiteDescriptor, getCallSiteToken(), getLinkCount(), isCallSiteUnstable(), arguments, this.runtimeContextArgCount);
    }

    private Object[] getTruncatedArguments() {
        Object[] args = getArguments();
        Object[] newargs = new Object[args.length - this.runtimeContextArgCount];
        newargs[0] = args[0];
        System.arraycopy(args, this.runtimeContextArgCount + 1, newargs, 1, newargs.length - 1);
        return newargs;
    }
}
