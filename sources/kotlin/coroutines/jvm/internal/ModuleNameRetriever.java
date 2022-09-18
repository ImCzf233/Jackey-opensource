package kotlin.coroutines.jvm.internal;

import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.JvmPlatformAnnotations;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DebugMetadata.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\"\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n\u0002\b\u0002\bÂ\u0002\u0018��2\u00020\u0001:\u0001\u000bB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0007\u001a\u00020\bR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��¨\u0006\f"}, m53d2 = {"Lkotlin/coroutines/jvm/internal/ModuleNameRetriever;", "", "()V", "cache", "Lkotlin/coroutines/jvm/internal/ModuleNameRetriever$Cache;", "notOnJava9", "buildCache", "continuation", "Lkotlin/coroutines/jvm/internal/BaseContinuationImpl;", "getModuleName", "", "Cache", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/coroutines/jvm/internal/ModuleNameRetriever.class */
public final class ModuleNameRetriever {
    @NotNull
    public static final ModuleNameRetriever INSTANCE = new ModuleNameRetriever();
    @NotNull
    private static final Cache notOnJava9 = new Cache(null, null, null);
    @Nullable
    private static Cache cache;

    /* compiled from: DebugMetadata.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018��2\u00020\u0001B#\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0006R\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u0002\n��R\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u0002\n��R\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u0002\n��¨\u0006\u0007"}, m53d2 = {"Lkotlin/coroutines/jvm/internal/ModuleNameRetriever$Cache;", "", "getModuleMethod", "Ljava/lang/reflect/Method;", "getDescriptorMethod", "nameMethod", "(Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;)V", "kotlin-stdlib"})
    /* loaded from: Jackey Client b2.jar:kotlin/coroutines/jvm/internal/ModuleNameRetriever$Cache.class */
    public static final class Cache {
        @JvmPlatformAnnotations
        @Nullable
        public final Method getModuleMethod;
        @JvmPlatformAnnotations
        @Nullable
        public final Method getDescriptorMethod;
        @JvmPlatformAnnotations
        @Nullable
        public final Method nameMethod;

        public Cache(@Nullable Method getModuleMethod, @Nullable Method getDescriptorMethod, @Nullable Method nameMethod) {
            this.getModuleMethod = getModuleMethod;
            this.getDescriptorMethod = getDescriptorMethod;
            this.nameMethod = nameMethod;
        }
    }

    private ModuleNameRetriever() {
    }

    @Nullable
    public final String getModuleName(@NotNull BaseContinuationImpl continuation) {
        Intrinsics.checkNotNullParameter(continuation, "continuation");
        Cache cache2 = cache;
        Cache cache3 = cache2 == null ? buildCache(continuation) : cache2;
        if (cache3 == notOnJava9) {
            return null;
        }
        Method method = cache3.getModuleMethod;
        Object module = method == null ? null : method.invoke(continuation.getClass(), new Object[0]);
        if (module == null) {
            return null;
        }
        Method method2 = cache3.getDescriptorMethod;
        Object descriptor = method2 == null ? null : method2.invoke(module, new Object[0]);
        if (descriptor == null) {
            return null;
        }
        Method method3 = cache3.nameMethod;
        Object invoke = method3 == null ? null : method3.invoke(descriptor, new Object[0]);
        if (!(invoke instanceof String)) {
            return null;
        }
        return (String) invoke;
    }

    private final Cache buildCache(BaseContinuationImpl continuation) {
        try {
            Method getModuleMethod = Class.class.getDeclaredMethod("getModule", new Class[0]);
            Class methodClass = continuation.getClass().getClassLoader().loadClass("java.lang.Module");
            Method getDescriptorMethod = methodClass.getDeclaredMethod("getDescriptor", new Class[0]);
            Class moduleDescriptorClass = continuation.getClass().getClassLoader().loadClass("java.lang.module.ModuleDescriptor");
            Method nameMethod = moduleDescriptorClass.getDeclaredMethod("name", new Class[0]);
            Cache it = new Cache(getModuleMethod, getDescriptorMethod, nameMethod);
            ModuleNameRetriever moduleNameRetriever = INSTANCE;
            cache = it;
            return it;
        } catch (Exception e) {
            Cache it2 = notOnJava9;
            ModuleNameRetriever moduleNameRetriever2 = INSTANCE;
            cache = it2;
            return it2;
        }
    }
}
