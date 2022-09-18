package jdk.internal.dynalink.support;

import java.lang.invoke.MethodType;
import jdk.internal.dynalink.CallSiteDescriptor;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/support/UnnamedDynCallSiteDescriptor.class */
public class UnnamedDynCallSiteDescriptor extends AbstractCallSiteDescriptor {
    private final MethodType methodType;

    /* renamed from: op */
    private final String f222op;

    public UnnamedDynCallSiteDescriptor(String op, MethodType methodType) {
        this.f222op = op;
        this.methodType = methodType;
    }

    @Override // jdk.internal.dynalink.CallSiteDescriptor
    public int getNameTokenCount() {
        return 2;
    }

    public String getOp() {
        return this.f222op;
    }

    @Override // jdk.internal.dynalink.CallSiteDescriptor
    public String getNameToken(int i) {
        switch (i) {
            case 0:
                return "dyn";
            case 1:
                return this.f222op;
            default:
                throw new IndexOutOfBoundsException(String.valueOf(i));
        }
    }

    @Override // jdk.internal.dynalink.CallSiteDescriptor
    public MethodType getMethodType() {
        return this.methodType;
    }

    @Override // jdk.internal.dynalink.CallSiteDescriptor
    public CallSiteDescriptor changeMethodType(MethodType newMethodType) {
        return CallSiteDescriptorFactory.getCanonicalPublicDescriptor(new UnnamedDynCallSiteDescriptor(this.f222op, newMethodType));
    }
}
