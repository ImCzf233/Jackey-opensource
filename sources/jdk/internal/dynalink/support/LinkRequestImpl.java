package jdk.internal.dynalink.support;

import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.LinkRequest;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/support/LinkRequestImpl.class */
public class LinkRequestImpl implements LinkRequest {
    private final CallSiteDescriptor callSiteDescriptor;
    private final Object callSiteToken;
    private final Object[] arguments;
    private final boolean callSiteUnstable;
    private final int linkCount;

    public LinkRequestImpl(CallSiteDescriptor callSiteDescriptor, Object callSiteToken, int linkCount, boolean callSiteUnstable, Object... arguments) {
        this.callSiteDescriptor = callSiteDescriptor;
        this.callSiteToken = callSiteToken;
        this.linkCount = linkCount;
        this.callSiteUnstable = callSiteUnstable;
        this.arguments = arguments;
    }

    @Override // jdk.internal.dynalink.linker.LinkRequest
    public Object[] getArguments() {
        if (this.arguments != null) {
            return (Object[]) this.arguments.clone();
        }
        return null;
    }

    @Override // jdk.internal.dynalink.linker.LinkRequest
    public Object getReceiver() {
        if (this.arguments == null || this.arguments.length <= 0) {
            return null;
        }
        return this.arguments[0];
    }

    @Override // jdk.internal.dynalink.linker.LinkRequest
    public CallSiteDescriptor getCallSiteDescriptor() {
        return this.callSiteDescriptor;
    }

    @Override // jdk.internal.dynalink.linker.LinkRequest
    public Object getCallSiteToken() {
        return this.callSiteToken;
    }

    @Override // jdk.internal.dynalink.linker.LinkRequest
    public boolean isCallSiteUnstable() {
        return this.callSiteUnstable;
    }

    @Override // jdk.internal.dynalink.linker.LinkRequest
    public int getLinkCount() {
        return this.linkCount;
    }

    @Override // jdk.internal.dynalink.linker.LinkRequest
    public LinkRequest withoutRuntimeContext() {
        return this;
    }

    @Override // jdk.internal.dynalink.linker.LinkRequest
    public LinkRequest replaceArguments(CallSiteDescriptor newCallSiteDescriptor, Object[] newArguments) {
        return new LinkRequestImpl(newCallSiteDescriptor, this.callSiteToken, this.linkCount, this.callSiteUnstable, newArguments);
    }
}
