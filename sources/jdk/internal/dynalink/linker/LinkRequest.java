package jdk.internal.dynalink.linker;

import jdk.internal.dynalink.CallSiteDescriptor;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/linker/LinkRequest.class */
public interface LinkRequest {
    CallSiteDescriptor getCallSiteDescriptor();

    Object getCallSiteToken();

    Object[] getArguments();

    Object getReceiver();

    int getLinkCount();

    boolean isCallSiteUnstable();

    LinkRequest withoutRuntimeContext();

    LinkRequest replaceArguments(CallSiteDescriptor callSiteDescriptor, Object[] objArr);
}
