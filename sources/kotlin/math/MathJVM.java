package kotlin.math;

import kotlin.Metadata;
import kotlin.jvm.JvmPlatformAnnotations;
import org.jetbrains.annotations.NotNull;

@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0014\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0006\bÂ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048��X\u0081\u0004¢\u0006\u0002\n��R\u0010\u0010\u0005\u001a\u00020\u00048��X\u0081\u0004¢\u0006\u0002\n��R\u0010\u0010\u0006\u001a\u00020\u00048��X\u0081\u0004¢\u0006\u0002\n��R\u0010\u0010\u0007\u001a\u00020\u00048��X\u0081\u0004¢\u0006\u0002\n��R\u0010\u0010\b\u001a\u00020\u00048��X\u0081\u0004¢\u0006\u0002\n��R\u0010\u0010\t\u001a\u00020\u00048��X\u0081\u0004¢\u0006\u0002\n��¨\u0006\n"}, m53d2 = {"Lkotlin/math/Constants;", "", "()V", "LN2", "", "epsilon", "taylor_2_bound", "taylor_n_bound", "upper_taylor_2_bound", "upper_taylor_n_bound", "kotlin-stdlib"})
/* renamed from: kotlin.math.Constants */
/* loaded from: Jackey Client b2.jar:kotlin/math/Constants.class */
final class MathJVM {
    @NotNull
    public static final MathJVM INSTANCE = new MathJVM();
    @JvmPlatformAnnotations
    public static final double LN2 = Math.log(2.0d);
    @JvmPlatformAnnotations
    public static final double epsilon = Math.ulp(1.0d);
    @JvmPlatformAnnotations
    public static final double taylor_2_bound = Math.sqrt(epsilon);
    @JvmPlatformAnnotations
    public static final double taylor_n_bound = Math.sqrt(taylor_2_bound);
    @JvmPlatformAnnotations
    public static final double upper_taylor_2_bound = 1 / taylor_2_bound;
    @JvmPlatformAnnotations
    public static final double upper_taylor_n_bound = 1 / taylor_n_bound;

    private MathJVM() {
    }
}
