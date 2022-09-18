package net.ccbluex.liquidbounce.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.awt.Color;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import net.ccbluex.liquidbounce.utils.extensions.ColorExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Value.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\b\u0016\u0018��2\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bB+\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n¢\u0006\u0002\u0010\u000bJ\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J&\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u0014J\n\u0010\u0018\u001a\u0004\u0018\u00010\u0011H\u0016R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n��\u001a\u0004\b\f\u0010\r¨\u0006\u0019"}, m53d2 = {"Lnet/ccbluex/liquidbounce/value/ColorValue;", "Lnet/ccbluex/liquidbounce/value/Value;", "Ljava/awt/Color;", "name", "", "value", "transparent", "", "(Ljava/lang/String;Ljava/awt/Color;Z)V", "displayable", "Lkotlin/Function0;", "(Ljava/lang/String;Ljava/awt/Color;ZLkotlin/jvm/functions/Function0;)V", "getTransparent", "()Z", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", PropertyDescriptor.SET, "hue", "", "saturation", "brightness", "alpha", "toJson", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/value/ColorValue.class */
public class ColorValue extends Value<Color> {
    private final boolean transparent;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ColorValue(@NotNull String name, @NotNull Color value, boolean transparent, @NotNull Functions<Boolean> displayable) {
        super(name, value, displayable);
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(displayable, "displayable");
        this.transparent = transparent;
    }

    public final boolean getTransparent() {
        return this.transparent;
    }

    /* compiled from: Value.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\n\n��\n\u0002\u0010\u000b\n\u0002\b\u0002\u0010��\u001a\u00020\u0001H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, m53d2 = {"<anonymous>", "", "invoke", "()Ljava/lang/Boolean;"})
    /* renamed from: net.ccbluex.liquidbounce.value.ColorValue$1 */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/value/ColorValue$1.class */
    static final class C16961 extends Lambda implements Functions<Boolean> {
        public static final C16961 INSTANCE = new C16961();

        C16961() {
            super(0);
        }

        @Override // kotlin.jvm.functions.Functions
        @NotNull
        public final Boolean invoke() {
            return true;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ColorValue(@NotNull String name, @NotNull Color value, boolean transparent) {
        this(name, value, transparent, C16961.INSTANCE);
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(value, "value");
    }

    public final void set(float hue, float saturation, float brightness, float alpha) {
        set(ColorExtension.setAlpha(new Color(Color.HSBtoRGB(hue, saturation, brightness)), alpha));
    }

    @Override // net.ccbluex.liquidbounce.value.Value
    @Nullable
    public JsonElement toJson() {
        JsonElement jsonObject = new JsonObject();
        jsonObject.addProperty("red", Integer.valueOf(getValue().getRed()));
        jsonObject.addProperty("green", Integer.valueOf(getValue().getGreen()));
        jsonObject.addProperty("blue", Integer.valueOf(getValue().getBlue()));
        jsonObject.addProperty("alpha", Integer.valueOf(getValue().getAlpha()));
        return jsonObject;
    }

    @Override // net.ccbluex.liquidbounce.value.Value
    public void fromJson(@NotNull JsonElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        if (!element.isJsonObject()) {
            return;
        }
        JsonObject valueObject = element.getAsJsonObject();
        setValue(new Color(valueObject.get("red").getAsInt(), valueObject.get("green").getAsInt(), valueObject.get("blue").getAsInt(), valueObject.get("alpha").getAsInt()));
    }
}
