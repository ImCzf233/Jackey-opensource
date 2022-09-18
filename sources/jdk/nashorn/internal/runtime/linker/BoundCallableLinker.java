package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Arrays;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.Guards;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/linker/BoundCallableLinker.class */
final class BoundCallableLinker implements TypeBasedGuardingDynamicLinker {
    @Override // jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker
    public boolean canLinkType(Class<?> type) {
        return type == BoundCallable.class;
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) throws Exception {
        boolean isCall;
        int firstArgIndex;
        MethodHandle droppingHandle;
        Object objBoundCallable = linkRequest.getReceiver();
        if (!(objBoundCallable instanceof BoundCallable)) {
            return null;
        }
        CallSiteDescriptor descriptor = linkRequest.getCallSiteDescriptor();
        if (descriptor.getNameTokenCount() < 2 || !"dyn".equals(descriptor.getNameToken(0))) {
            return null;
        }
        String operation = descriptor.getNameToken(1);
        if ("new".equals(operation)) {
            isCall = false;
        } else if ("call".equals(operation)) {
            isCall = true;
        } else {
            return null;
        }
        BoundCallable boundCallable = (BoundCallable) objBoundCallable;
        Object callable = boundCallable.getCallable();
        Object boundThis = boundCallable.getBoundThis();
        Object[] args = linkRequest.getArguments();
        Object[] boundArgs = boundCallable.getBoundArgs();
        int argsLen = args.length;
        int boundArgsLen = boundArgs.length;
        Object[] newArgs = new Object[argsLen + boundArgsLen];
        newArgs[0] = callable;
        if (isCall) {
            newArgs[1] = boundThis;
            firstArgIndex = 2;
        } else {
            firstArgIndex = 1;
        }
        System.arraycopy(boundArgs, 0, newArgs, firstArgIndex, boundArgsLen);
        System.arraycopy(args, firstArgIndex, newArgs, firstArgIndex + boundArgsLen, argsLen - firstArgIndex);
        MethodType type = descriptor.getMethodType();
        MethodType newMethodType = descriptor.getMethodType().changeParameterType(0, callable.getClass());
        if (isCall) {
            newMethodType = newMethodType.changeParameterType(1, boundThis == null ? Object.class : boundThis.getClass());
        }
        int i = boundArgs.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 <= 0) {
                break;
            }
            MethodType methodType = newMethodType;
            int i3 = firstArgIndex;
            Class<?>[] clsArr = new Class[1];
            clsArr[0] = boundArgs[i] == null ? Object.class : boundArgs[i].getClass();
            newMethodType = methodType.insertParameterTypes(i3, clsArr);
        }
        CallSiteDescriptor newDescriptor = descriptor.changeMethodType(newMethodType);
        GuardedInvocation inv = linkerServices.getGuardedInvocation(linkRequest.replaceArguments(newDescriptor, newArgs));
        if (inv == null) {
            return null;
        }
        MethodHandle boundHandle = MethodHandles.insertArguments(inv.getInvocation(), 0, Arrays.copyOf(newArgs, firstArgIndex + boundArgs.length));
        Class<?> p0Type = type.parameterType(0);
        if (isCall) {
            droppingHandle = MethodHandles.dropArguments(boundHandle, 0, p0Type, type.parameterType(1));
        } else {
            droppingHandle = MethodHandles.dropArguments(boundHandle, 0, p0Type);
        }
        MethodHandle newGuard = Guards.getIdentityGuard(boundCallable);
        return inv.replaceMethods(droppingHandle, newGuard.asType(newGuard.type().changeParameterType(0, p0Type)));
    }
}
