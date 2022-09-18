package net.ccbluex.liquidbounce.value;

import com.google.gson.JsonElement;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.util.Constants;

/* compiled from: Value.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��0\n\u0002\u0018\u0002\n��\n\u0002\u0010��\n��\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b&\u0018��*\u0004\b��\u0010\u00012\u00020\u0002B#\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00028��\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\u0015\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0005\u001a\u00028��H\u0016¢\u0006\u0002\u0010\u0013J\u0010\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019H&J\u000b\u0010\u001a\u001a\u00028��¢\u0006\u0002\u0010\u0011J\u001d\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00028��2\u0006\u0010\u001d\u001a\u00028��H\u0014¢\u0006\u0002\u0010\u001eJ\u001d\u0010\u001f\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00028��2\u0006\u0010\u001d\u001a\u00028��H\u0014¢\u0006\u0002\u0010\u001eJ\u0013\u0010 \u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00028��¢\u0006\u0002\u0010\u0013J\n\u0010!\u001a\u0004\u0018\u00010\u0019H&R \u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0005\u001a\u00028��X\u0084\u000e¢\u0006\u0010\n\u0002\u0010\u0014\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013¨\u0006\""}, m53d2 = {"Lnet/ccbluex/liquidbounce/value/Value;", "T", "", "name", "", "value", "canDisplay", "Lkotlin/Function0;", "", "(Ljava/lang/String;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)V", "getCanDisplay", "()Lkotlin/jvm/functions/Function0;", "setCanDisplay", "(Lkotlin/jvm/functions/Function0;)V", "getName", "()Ljava/lang/String;", "getValue", "()Ljava/lang/Object;", "setValue", "(Ljava/lang/Object;)V", Constants.OBJECT, "changeValue", "", "fromJson", "element", "Lcom/google/gson/JsonElement;", PropertyDescriptor.GET, "onChange", "oldValue", "newValue", "(Ljava/lang/Object;Ljava/lang/Object;)V", "onChanged", PropertyDescriptor.SET, "toJson", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/value/Value.class */
public abstract class Value<T> {
    @NotNull
    private final String name;
    private T value;
    @NotNull
    private Functions<Boolean> canDisplay;

    @Nullable
    public abstract JsonElement toJson();

    public abstract void fromJson(@NotNull JsonElement jsonElement);

    public Value(@NotNull String name, T t, @NotNull Functions<Boolean> canDisplay) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(canDisplay, "canDisplay");
        this.name = name;
        this.value = t;
        this.canDisplay = canDisplay;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    public final T getValue() {
        return this.value;
    }

    public final void setValue(T t) {
        this.value = t;
    }

    @NotNull
    public final Functions<Boolean> getCanDisplay() {
        return this.canDisplay;
    }

    public final void setCanDisplay(@NotNull Functions<Boolean> functions) {
        Intrinsics.checkNotNullParameter(functions, "<set-?>");
        this.canDisplay = functions;
    }

    public final void set(T t) {
        if (Intrinsics.areEqual(t, this.value)) {
            return;
        }
        T t2 = get();
        try {
            onChange(t2, t);
            changeValue(t);
            onChanged(t2, t);
            LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().valuesConfig);
        } catch (Exception e) {
            ClientUtils.getLogger().error("[ValueSystem (" + this.name + ")]: " + ((Object) e.getClass().getName()) + " (" + ((Object) e.getMessage()) + ") [" + t2 + " >> " + t + ']');
        }
    }

    public final T get() {
        return this.value;
    }

    public void changeValue(T t) {
        this.value = t;
    }

    protected void onChange(T t, T t2) {
    }

    protected void onChanged(T t, T t2) {
    }
}
