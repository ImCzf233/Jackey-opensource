package net.ccbluex.liquidbounce.value;

import com.github.benmanes.caffeine.cache.LocalCacheFactory;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: Value.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n��\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0004\n��\n\u0002\u0018\u0002\n��\b\u0016\u0018��2\b\u0012\u0004\u0012\u00020\u00020\u0001B5\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0002\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bB/\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0002\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u0012\u0006\u0010\f\u001a\u00020\u0004¢\u0006\u0002\u0010\rB'\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0002\u0012\u0006\u0010\u0007\u001a\u00020\u0002¢\u0006\u0002\u0010\u000eB?\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0002\u0012\u0006\u0010\f\u001a\u00020\u0004\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000fJ\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u000e\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001bJ\b\u0010\u001c\u001a\u00020\u001dH\u0016R\u0011\u0010\u0007\u001a\u00020\u0002¢\u0006\b\n��\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0002¢\u0006\b\n��\u001a\u0004\b\u0012\u0010\u0011R\u0011\u0010\f\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u001e"}, m53d2 = {"Lnet/ccbluex/liquidbounce/value/IntegerValue;", "Lnet/ccbluex/liquidbounce/value/Value;", "", "name", "", "value", "minimum", LocalCacheFactory.MAXIMUM, "displayable", "Lkotlin/Function0;", "", "(Ljava/lang/String;IIILkotlin/jvm/functions/Function0;)V", "suffix", "(Ljava/lang/String;IIILjava/lang/String;)V", "(Ljava/lang/String;III)V", "(Ljava/lang/String;IIILjava/lang/String;Lkotlin/jvm/functions/Function0;)V", "getMaximum", "()I", "getMinimum", "getSuffix", "()Ljava/lang/String;", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", PropertyDescriptor.SET, "newValue", "", "toJson", "Lcom/google/gson/JsonPrimitive;", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/value/IntegerValue.class */
public class IntegerValue extends Value<Integer> {
    private final int minimum;
    private final int maximum;
    @NotNull
    private final String suffix;

    public /* synthetic */ IntegerValue(String str, int i, int i2, int i3, String str2, Functions functions, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, (i4 & 4) != 0 ? 0 : i2, (i4 & 8) != 0 ? Integer.MAX_VALUE : i3, str2, functions);
    }

    public final int getMinimum() {
        return this.minimum;
    }

    public final int getMaximum() {
        return this.maximum;
    }

    @NotNull
    public final String getSuffix() {
        return this.suffix;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IntegerValue(@NotNull String name, int value, int minimum, int maximum, @NotNull String suffix, @NotNull Functions<Boolean> displayable) {
        super(name, Integer.valueOf(value), displayable);
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(suffix, "suffix");
        Intrinsics.checkNotNullParameter(displayable, "displayable");
        this.minimum = minimum;
        this.maximum = maximum;
        this.suffix = suffix;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Value.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\n\n��\n\u0002\u0010\u000b\n\u0002\b\u0002\u0010��\u001a\u00020\u0001H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, m53d2 = {"<anonymous>", "", "invoke", "()Ljava/lang/Boolean;"})
    /* renamed from: net.ccbluex.liquidbounce.value.IntegerValue$1 */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/value/IntegerValue$1.class */
    public static final class C17001 extends Lambda implements Functions<Boolean> {
        public static final C17001 INSTANCE = new C17001();

        C17001() {
            super(0);
        }

        @Override // kotlin.jvm.functions.Functions
        @NotNull
        public final Boolean invoke() {
            return true;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public IntegerValue(@NotNull String name, int value, int minimum, int maximum, @NotNull Functions<Boolean> displayable) {
        this(name, value, minimum, maximum, "", displayable);
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(displayable, "displayable");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Value.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\n\n��\n\u0002\u0010\u000b\n\u0002\b\u0002\u0010��\u001a\u00020\u0001H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, m53d2 = {"<anonymous>", "", "invoke", "()Ljava/lang/Boolean;"})
    /* renamed from: net.ccbluex.liquidbounce.value.IntegerValue$2 */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/value/IntegerValue$2.class */
    public static final class C17012 extends Lambda implements Functions<Boolean> {
        public static final C17012 INSTANCE = new C17012();

        C17012() {
            super(0);
        }

        @Override // kotlin.jvm.functions.Functions
        @NotNull
        public final Boolean invoke() {
            return true;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public IntegerValue(@NotNull String name, int value, int minimum, int maximum, @NotNull String suffix) {
        this(name, value, minimum, maximum, suffix, C17001.INSTANCE);
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(suffix, "suffix");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public IntegerValue(@NotNull String name, int value, int minimum, int maximum) {
        this(name, value, minimum, maximum, C17012.INSTANCE);
        Intrinsics.checkNotNullParameter(name, "name");
    }

    public final void set(@NotNull Number newValue) {
        Intrinsics.checkNotNullParameter(newValue, "newValue");
        set((IntegerValue) Integer.valueOf(newValue.intValue()));
    }

    @Override // net.ccbluex.liquidbounce.value.Value
    @NotNull
    public JsonPrimitive toJson() {
        return new JsonPrimitive(getValue());
    }

    @Override // net.ccbluex.liquidbounce.value.Value
    public void fromJson(@NotNull JsonElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        if (element.isJsonPrimitive()) {
            setValue(Integer.valueOf(element.getAsInt()));
        }
    }
}
