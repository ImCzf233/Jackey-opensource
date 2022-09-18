package net.ccbluex.liquidbounce.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.minecraft.client.gui.FontRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Value.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��0\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018��2\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0002\u0010\u0006B#\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\u0002\u0010\nJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\n\u0010\u000f\u001a\u0004\u0018\u00010\u000eH\u0016¨\u0006\u0010"}, m53d2 = {"Lnet/ccbluex/liquidbounce/value/FontValue;", "Lnet/ccbluex/liquidbounce/value/Value;", "Lnet/minecraft/client/gui/FontRenderer;", "valueName", "", "value", "(Ljava/lang/String;Lnet/minecraft/client/gui/FontRenderer;)V", "displayable", "Lkotlin/Function0;", "", "(Ljava/lang/String;Lnet/minecraft/client/gui/FontRenderer;Lkotlin/jvm/functions/Function0;)V", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", "toJson", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/value/FontValue.class */
public final class FontValue extends Value<FontRenderer> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FontValue(@NotNull String valueName, @NotNull FontRenderer value, @NotNull Functions<Boolean> displayable) {
        super(valueName, value, displayable);
        Intrinsics.checkNotNullParameter(valueName, "valueName");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(displayable, "displayable");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Value.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\n\n��\n\u0002\u0010\u000b\n\u0002\b\u0002\u0010��\u001a\u00020\u0001H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, m53d2 = {"<anonymous>", "", "invoke", "()Ljava/lang/Boolean;"})
    /* renamed from: net.ccbluex.liquidbounce.value.FontValue$1 */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/value/FontValue$1.class */
    public static final class C16991 extends Lambda implements Functions<Boolean> {
        public static final C16991 INSTANCE = new C16991();

        C16991() {
            super(0);
        }

        @Override // kotlin.jvm.functions.Functions
        @NotNull
        public final Boolean invoke() {
            return true;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public FontValue(@NotNull String valueName, @NotNull FontRenderer value) {
        this(valueName, value, C16991.INSTANCE);
        Intrinsics.checkNotNullParameter(valueName, "valueName");
        Intrinsics.checkNotNullParameter(value, "value");
    }

    @Override // net.ccbluex.liquidbounce.value.Value
    @Nullable
    public JsonElement toJson() {
        Object[] fontDetails = Fonts.getFontDetails(getValue());
        if (fontDetails == null) {
            return null;
        }
        JsonElement jsonObject = new JsonObject();
        Object obj = fontDetails[0];
        if (obj == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
        }
        jsonObject.addProperty("fontName", (String) obj);
        Object obj2 = fontDetails[1];
        if (obj2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Int");
        }
        jsonObject.addProperty("fontSize", Integer.valueOf(((Integer) obj2).intValue()));
        return jsonObject;
    }

    @Override // net.ccbluex.liquidbounce.value.Value
    public void fromJson(@NotNull JsonElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        if (!element.isJsonObject()) {
            return;
        }
        JsonObject valueObject = element.getAsJsonObject();
        FontRenderer fontRenderer = Fonts.getFontRenderer(valueObject.get("fontName").getAsString(), valueObject.get("fontSize").getAsInt());
        Intrinsics.checkNotNullExpressionValue(fontRenderer, "getFontRenderer(valueObj…Object[\"fontSize\"].asInt)");
        setValue(fontRenderer);
    }
}
