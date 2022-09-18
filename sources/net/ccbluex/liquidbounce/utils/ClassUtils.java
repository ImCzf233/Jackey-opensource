package net.ccbluex.liquidbounce.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: ClassUtils.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001c\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010\u000b\n\u0002\b\u0004\bÆ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0005H\u0007J\u0006\u0010\t\u001a\u00020\u0006R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n��¨\u0006\n"}, m53d2 = {"Lnet/ccbluex/liquidbounce/utils/ClassUtils;", "", "()V", "cachedClasses", "", "", "", "hasClass", "className", "hasForge", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/ClassUtils.class */
public final class ClassUtils {
    @NotNull
    public static final ClassUtils INSTANCE = new ClassUtils();
    @NotNull
    private static final Map<String, Boolean> cachedClasses = new LinkedHashMap();

    private ClassUtils() {
    }

    @JvmStatic
    public static final boolean hasClass(@NotNull String className) {
        boolean z;
        Intrinsics.checkNotNullParameter(className, "className");
        if (cachedClasses.containsKey(className)) {
            Boolean bool = cachedClasses.get(className);
            Intrinsics.checkNotNull(bool);
            return bool.booleanValue();
        }
        try {
            Class.forName(className);
            cachedClasses.put(className, true);
            z = true;
        } catch (ClassNotFoundException e) {
            cachedClasses.put(className, false);
            z = false;
        }
        return z;
    }

    public final boolean hasForge() {
        return hasClass("net.minecraftforge.common.MinecraftForge");
    }
}
