package kotlin.coroutines.jvm.internal;

import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.jvm.JvmName;
import org.jetbrains.annotations.NotNull;

@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��T\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000b\n��\n\u0002\u0018\u0002\n\u0002\u0010\u0005\n��\n\u0002\u0018\u0002\n\u0002\u0010\f\n��\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n��\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n��\n\u0002\u0018\u0002\n\u0002\u0010\b\n��\n\u0002\u0018\u0002\n\u0002\u0010\t\n��\n\u0002\u0018\u0002\n\u0002\u0010\n\n��\u001a\u0010\u0010��\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0001\u001a\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u0006H\u0001\u001a\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0002\u001a\u00020\tH\u0001\u001a\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\fH\u0001\u001a\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0002\u001a\u00020\u000fH\u0001\u001a\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0002\u001a\u00020\u0012H\u0001\u001a\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0002\u001a\u00020\u0015H\u0001\u001a\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0002\u001a\u00020\u0018H\u0001¨\u0006\u0019"}, m53d2 = {"boxBoolean", "Ljava/lang/Boolean;", "primitive", "", "boxByte", "Ljava/lang/Byte;", "", "boxChar", "Ljava/lang/Character;", "", "boxDouble", "Ljava/lang/Double;", "", "boxFloat", "Ljava/lang/Float;", "", "boxInt", "Ljava/lang/Integer;", "", "boxLong", "Ljava/lang/Long;", "", "boxShort", "Ljava/lang/Short;", "", "kotlin-stdlib"})
@JvmName(name = "Boxing")
/* renamed from: kotlin.coroutines.jvm.internal.Boxing */
/* loaded from: Jackey Client b2.jar:kotlin/coroutines/jvm/internal/Boxing.class */
public final class boxing {
    @SinceKotlin(version = "1.3")
    @PublishedApi
    @NotNull
    public static final Boolean boxBoolean(boolean primitive) {
        return Boolean.valueOf(primitive);
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    @NotNull
    public static final Byte boxByte(byte primitive) {
        return Byte.valueOf(primitive);
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    @NotNull
    public static final Short boxShort(short primitive) {
        return new Short(primitive);
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    @NotNull
    public static final Integer boxInt(int primitive) {
        return new Integer(primitive);
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    @NotNull
    public static final Long boxLong(long primitive) {
        return new Long(primitive);
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    @NotNull
    public static final Float boxFloat(float primitive) {
        return new Float(primitive);
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    @NotNull
    public static final Double boxDouble(double primitive) {
        return new Double(primitive);
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    @NotNull
    public static final Character boxChar(char primitive) {
        return new Character(primitive);
    }
}
