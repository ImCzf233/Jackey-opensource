package jdk.internal.dynalink;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/DefaultBootstrapper.class */
public class DefaultBootstrapper {
    private static final DynamicLinker dynamicLinker = new DynamicLinkerFactory().createLinker();

    private DefaultBootstrapper() {
    }

    public static CallSite bootstrap(MethodHandles.Lookup caller, String name, MethodType type) {
        return bootstrapInternal(caller, name, type);
    }

    public static CallSite publicBootstrap(MethodHandles.Lookup caller, String name, MethodType type) {
        return bootstrapInternal(MethodHandles.publicLookup(), name, type);
    }

    private static CallSite bootstrapInternal(MethodHandles.Lookup caller, String name, MethodType type) {
        return (CallSite) dynamicLinker.link(new MonomorphicCallSite(CallSiteDescriptorFactory.create(caller, name, type)));
    }
}
