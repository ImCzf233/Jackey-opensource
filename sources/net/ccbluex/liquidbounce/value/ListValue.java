package net.ccbluex.liquidbounce.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.JvmPlatformAnnotations;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Value.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��8\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\b\u0016\u0018��2\b\u0012\u0004\u0012\u00020\u00020\u0001B%\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0002¢\u0006\u0002\u0010\u0007B1\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0002\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0006\u001a\u00020\u0002H\u0016J\u0013\u0010\u0012\u001a\u00020\n2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0002H\u0086\u0002J\u0010\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u0012\u0010\f\u001a\u00020\n8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n��R\u0019\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"}, m53d2 = {"Lnet/ccbluex/liquidbounce/value/ListValue;", "Lnet/ccbluex/liquidbounce/value/Value;", "", "name", "values", "", "value", "(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)V", "displayable", "Lkotlin/Function0;", "", "(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function0;)V", "openList", "getValues", "()[Ljava/lang/String;", "[Ljava/lang/String;", "changeValue", "", "contains", "string", "fromJson", "element", "Lcom/google/gson/JsonElement;", "toJson", "Lcom/google/gson/JsonPrimitive;", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/value/ListValue.class */
public class ListValue extends Value<String> {
    @NotNull
    private final String[] values;
    @JvmPlatformAnnotations
    public boolean openList;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ListValue(@NotNull String name, @NotNull String[] values, @NotNull String value, @NotNull Functions<Boolean> displayable) {
        super(name, value, displayable);
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(values, "values");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(displayable, "displayable");
        this.values = values;
        setValue(value);
    }

    @NotNull
    public final String[] getValues() {
        return this.values;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Value.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\n\n��\n\u0002\u0010\u000b\n\u0002\b\u0002\u0010��\u001a\u00020\u0001H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, m53d2 = {"<anonymous>", "", "invoke", "()Ljava/lang/Boolean;"})
    /* renamed from: net.ccbluex.liquidbounce.value.ListValue$1 */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/value/ListValue$1.class */
    public static final class C17021 extends Lambda implements Functions<Boolean> {
        public static final C17021 INSTANCE = new C17021();

        C17021() {
            super(0);
        }

        @Override // kotlin.jvm.functions.Functions
        @NotNull
        public final Boolean invoke() {
            return true;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ListValue(@NotNull String name, @NotNull String[] values, @NotNull String value) {
        this(name, values, value, C17021.INSTANCE);
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(values, "values");
        Intrinsics.checkNotNullParameter(value, "value");
    }

    public final boolean contains(@Nullable String string) {
        return Arrays.stream(this.values).anyMatch((v1) -> {
            return m2916contains$lambda0(r1, v1);
        });
    }

    /* renamed from: contains$lambda-0 */
    private static final boolean m2916contains$lambda0(String $string, String s) {
        Intrinsics.checkNotNullParameter(s, "s");
        return StringsKt.equals(s, $string, true);
    }

    public void changeValue(@NotNull String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        String[] strArr = this.values;
        int i = 0;
        int length = strArr.length;
        while (i < length) {
            String element = strArr[i];
            i++;
            if (StringsKt.equals(element, value, true)) {
                setValue(element);
                return;
            }
        }
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
            String asString = element.getAsString();
            Intrinsics.checkNotNullExpressionValue(asString, "element.asString");
            changeValue(asString);
        }
    }
}
