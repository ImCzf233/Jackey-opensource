package kotlin.coroutines.jvm.internal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DebugMetadata.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��0\n��\n\u0002\u0010\b\n��\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0002\u001a\u000e\u0010\u0006\u001a\u0004\u0018\u00010\u0007*\u00020\bH\u0002\u001a\f\u0010\t\u001a\u00020\u0001*\u00020\bH\u0002\u001a\u0019\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b*\u00020\bH\u0001¢\u0006\u0002\u0010\r\u001a\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f*\u00020\bH\u0001¢\u0006\u0002\b\u0010\"\u000e\u0010��\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n��¨\u0006\u0011"}, m53d2 = {"COROUTINES_DEBUG_METADATA_VERSION", "", "checkDebugMetadataVersion", "", "expected", "actual", "getDebugMetadataAnnotation", "Lkotlin/coroutines/jvm/internal/DebugMetadata;", "Lkotlin/coroutines/jvm/internal/BaseContinuationImpl;", "getLabel", "getSpilledVariableFieldMapping", "", "", "(Lkotlin/coroutines/jvm/internal/BaseContinuationImpl;)[Ljava/lang/String;", "getStackTraceElementImpl", "Ljava/lang/StackTraceElement;", "getStackTraceElement", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/coroutines/jvm/internal/DebugMetadataKt.class */
public final class DebugMetadataKt {
    private static final int COROUTINES_DEBUG_METADATA_VERSION = 1;

    @SinceKotlin(version = "1.3")
    @JvmName(name = "getStackTraceElement")
    @Nullable
    public static final StackTraceElement getStackTraceElement(@NotNull BaseContinuationImpl $this$getStackTraceElementImpl) {
        Intrinsics.checkNotNullParameter($this$getStackTraceElementImpl, "<this>");
        DebugMetadata debugMetadata = getDebugMetadataAnnotation($this$getStackTraceElementImpl);
        if (debugMetadata == null) {
            return null;
        }
        checkDebugMetadataVersion(1, debugMetadata.m38v());
        int label = getLabel($this$getStackTraceElementImpl);
        int lineNumber = label < 0 ? -1 : debugMetadata.m42l()[label];
        String moduleName = ModuleNameRetriever.INSTANCE.getModuleName($this$getStackTraceElementImpl);
        String moduleAndClass = moduleName == null ? debugMetadata.m45c() : ((Object) moduleName) + '/' + debugMetadata.m45c();
        return new StackTraceElement(moduleAndClass, debugMetadata.m41m(), debugMetadata.m44f(), lineNumber);
    }

    private static final DebugMetadata getDebugMetadataAnnotation(BaseContinuationImpl $this$getDebugMetadataAnnotation) {
        return (DebugMetadata) $this$getDebugMetadataAnnotation.getClass().getAnnotation(DebugMetadata.class);
    }

    private static final int getLabel(BaseContinuationImpl $this$getLabel) {
        int i;
        try {
            Field field = $this$getLabel.getClass().getDeclaredField("label");
            field.setAccessible(true);
            Object obj = field.get($this$getLabel);
            Integer num = obj instanceof Integer ? (Integer) obj : null;
            i = (num == null ? 0 : num.intValue()) - 1;
        } catch (Exception e) {
            i = -1;
        }
        return i;
    }

    private static final void checkDebugMetadataVersion(int expected, int actual) {
        if (actual > expected) {
            throw new IllegalStateException(("Debug metadata version mismatch. Expected: " + expected + ", got " + actual + ". Please update the Kotlin standard library.").toString());
        }
    }

    @SinceKotlin(version = "1.3")
    @JvmName(name = "getSpilledVariableFieldMapping")
    @Nullable
    public static final String[] getSpilledVariableFieldMapping(@NotNull BaseContinuationImpl $this$getSpilledVariableFieldMapping) {
        Intrinsics.checkNotNullParameter($this$getSpilledVariableFieldMapping, "<this>");
        DebugMetadata debugMetadata = getDebugMetadataAnnotation($this$getSpilledVariableFieldMapping);
        if (debugMetadata == null) {
            return null;
        }
        checkDebugMetadataVersion(1, debugMetadata.m38v());
        ArrayList res = new ArrayList();
        int label = getLabel($this$getSpilledVariableFieldMapping);
        int[] m43i = debugMetadata.m43i();
        int i = 0;
        int length = m43i.length;
        while (i < length) {
            int i2 = i;
            int labelOfIndex = m43i[i];
            i++;
            if (labelOfIndex == label) {
                res.add(debugMetadata.m39s()[i2]);
                res.add(debugMetadata.m40n()[i2]);
            }
        }
        ArrayList $this$toTypedArray$iv = res;
        Object[] array = $this$toTypedArray$iv.toArray(new String[0]);
        if (array != null) {
            return (String[]) array;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
    }
}
