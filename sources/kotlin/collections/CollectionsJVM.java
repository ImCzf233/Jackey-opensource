package kotlin.collections;

import kotlin.Metadata;
import kotlin.jvm.JvmPlatformAnnotations;
import org.jetbrains.annotations.NotNull;

@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\u000b\n��\bÀ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048��X\u0081\u0004¢\u0006\u0002\n��¨\u0006\u0005"}, m53d2 = {"Lkotlin/collections/CollectionSystemProperties;", "", "()V", "brittleContainsOptimizationEnabled", "", "kotlin-stdlib"})
/* renamed from: kotlin.collections.CollectionSystemProperties */
/* loaded from: Jackey Client b2.jar:kotlin/collections/CollectionSystemProperties.class */
public final class CollectionsJVM {
    @NotNull
    public static final CollectionsJVM INSTANCE = new CollectionsJVM();
    @JvmPlatformAnnotations
    public static final boolean brittleContainsOptimizationEnabled;

    private CollectionsJVM() {
    }

    static {
        String property = System.getProperty("kotlin.collections.convert_arg_to_set_in_removeAll");
        brittleContainsOptimizationEnabled = property == null ? false : Boolean.parseBoolean(property);
    }
}
