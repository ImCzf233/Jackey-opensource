package jdk.internal.dynalink.support;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import jdk.internal.dynalink.linker.ConversionComparator;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.MethodHandleTransformer;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/support/LinkerServicesImpl.class */
public class LinkerServicesImpl implements LinkerServices {
    private static final RuntimePermission GET_CURRENT_LINK_REQUEST = new RuntimePermission("dynalink.getCurrentLinkRequest");
    private static final ThreadLocal<LinkRequest> threadLinkRequest = new ThreadLocal<>();
    private final TypeConverterFactory typeConverterFactory;
    private final GuardingDynamicLinker topLevelLinker;
    private final MethodHandleTransformer internalObjectsFilter;

    public LinkerServicesImpl(TypeConverterFactory typeConverterFactory, GuardingDynamicLinker topLevelLinker, MethodHandleTransformer internalObjectsFilter) {
        this.typeConverterFactory = typeConverterFactory;
        this.topLevelLinker = topLevelLinker;
        this.internalObjectsFilter = internalObjectsFilter;
    }

    @Override // jdk.internal.dynalink.linker.LinkerServices
    public boolean canConvert(Class<?> from, Class<?> to) {
        return this.typeConverterFactory.canConvert(from, to);
    }

    @Override // jdk.internal.dynalink.linker.LinkerServices
    public MethodHandle asType(MethodHandle handle, MethodType fromType) {
        return this.typeConverterFactory.asType(handle, fromType);
    }

    @Override // jdk.internal.dynalink.linker.LinkerServices
    public MethodHandle asTypeLosslessReturn(MethodHandle handle, MethodType fromType) {
        return LinkerServices.Implementation.asTypeLosslessReturn(this, handle, fromType);
    }

    @Override // jdk.internal.dynalink.linker.LinkerServices
    public MethodHandle getTypeConverter(Class<?> sourceType, Class<?> targetType) {
        return this.typeConverterFactory.getTypeConverter(sourceType, targetType);
    }

    @Override // jdk.internal.dynalink.linker.LinkerServices
    public ConversionComparator.Comparison compareConversion(Class<?> sourceType, Class<?> targetType1, Class<?> targetType2) {
        return this.typeConverterFactory.compareConversion(sourceType, targetType1, targetType2);
    }

    @Override // jdk.internal.dynalink.linker.LinkerServices
    public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest) throws Exception {
        LinkRequest prevLinkRequest = threadLinkRequest.get();
        threadLinkRequest.set(linkRequest);
        try {
            GuardedInvocation guardedInvocation = this.topLevelLinker.getGuardedInvocation(linkRequest, this);
            threadLinkRequest.set(prevLinkRequest);
            return guardedInvocation;
        } catch (Throwable th) {
            threadLinkRequest.set(prevLinkRequest);
            throw th;
        }
    }

    @Override // jdk.internal.dynalink.linker.LinkerServices
    public MethodHandle filterInternalObjects(MethodHandle target) {
        return this.internalObjectsFilter != null ? this.internalObjectsFilter.transform(target) : target;
    }

    public static LinkRequest getCurrentLinkRequest() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(GET_CURRENT_LINK_REQUEST);
        }
        return threadLinkRequest.get();
    }
}
