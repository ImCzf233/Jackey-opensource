package net.ccbluex.liquidbounce.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import kotlin.Metadata;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: Value.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\b\u0016\u0018��2\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002¢\u0006\u0002\u0010\u0005B#\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016¨\u0006\u0010"}, m53d2 = {"Lnet/ccbluex/liquidbounce/value/TextValue;", "Lnet/ccbluex/liquidbounce/value/Value;", "", "name", "value", "(Ljava/lang/String;Ljava/lang/String;)V", "displayable", "Lkotlin/Function0;", "", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function0;)V", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", "toJson", "Lcom/google/gson/JsonPrimitive;", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/value/TextValue.class */
public class TextValue extends Value<String> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TextValue(@NotNull String name, @NotNull String value, @NotNull Functions<Boolean> displayable) {
        super(name, value, displayable);
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(displayable, "displayable");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Value.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\n\n��\n\u0002\u0010\u000b\n\u0002\b\u0002\u0010��\u001a\u00020\u0001H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, m53d2 = {"<anonymous>", "", "invoke", "()Ljava/lang/Boolean;"})
    /* renamed from: net.ccbluex.liquidbounce.value.TextValue$1 */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/value/TextValue$1.class */
    public static final class C17031 extends Lambda implements Functions<Boolean> {
        public static final C17031 INSTANCE = new C17031();

        C17031() {
            super(0);
        }

        @Override // kotlin.jvm.functions.Functions
        @NotNull
        public final Boolean invoke() {
            return true;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TextValue(@NotNull String name, @NotNull String value) {
        this(name, value, C17031.INSTANCE);
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(value, "value");
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
            setValue(asString);
        }
    }
}
