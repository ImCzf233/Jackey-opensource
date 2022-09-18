package net.ccbluex.liquidbounce.script.api.global;

import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptUtils;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.value.BlockValue;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.TextValue;
import org.jetbrains.annotations.NotNull;

/* compiled from: Setting.kt */
@Metadata(m51mv = {1, 1, 16}, m55bv = {1, 0, 3}, m52k = 1, m54d1 = {"��6\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\bÆ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0011"}, m53d2 = {"Lnet/ccbluex/liquidbounce/script/api/global/Setting;", "", "()V", "block", "Lnet/ccbluex/liquidbounce/value/BlockValue;", "settingInfo", "Ljdk/nashorn/api/scripting/JSObject;", "boolean", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "float", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "integer", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "list", "Lnet/ccbluex/liquidbounce/value/ListValue;", "text", "Lnet/ccbluex/liquidbounce/value/TextValue;", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/script/api/global/Setting.class */
public final class Setting {
    public static final Setting INSTANCE = new Setting();

    private Setting() {
    }

    @JvmStatic
    @NotNull
    /* renamed from: boolean */
    public static final BoolValue m2822boolean(@NotNull JSObject settingInfo) {
        Intrinsics.checkParameterIsNotNull(settingInfo, "settingInfo");
        Object member = settingInfo.getMember("name");
        if (member == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        String name = (String) member;
        Object member2 = settingInfo.getMember("default");
        if (member2 != null) {
            return new BoolValue(name, ((Boolean) member2).booleanValue());
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Boolean");
    }

    @JvmStatic
    @NotNull
    public static final IntegerValue integer(@NotNull JSObject settingInfo) {
        Intrinsics.checkParameterIsNotNull(settingInfo, "settingInfo");
        Object member = settingInfo.getMember("name");
        if (member == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        String name = (String) member;
        Object member2 = settingInfo.getMember("default");
        if (member2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Number");
        }
        int intValue = ((Number) member2).intValue();
        Object member3 = settingInfo.getMember("min");
        if (member3 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Number");
        }
        int min = ((Number) member3).intValue();
        Object member4 = settingInfo.getMember("max");
        if (member4 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Number");
        }
        int max = ((Number) member4).intValue();
        return new IntegerValue(name, intValue, min, max);
    }

    @JvmStatic
    @NotNull
    /* renamed from: float */
    public static final FloatValue m2823float(@NotNull JSObject settingInfo) {
        Intrinsics.checkParameterIsNotNull(settingInfo, "settingInfo");
        Object member = settingInfo.getMember("name");
        if (member == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        String name = (String) member;
        Object member2 = settingInfo.getMember("default");
        if (member2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Number");
        }
        float floatValue = ((Number) member2).floatValue();
        Object member3 = settingInfo.getMember("min");
        if (member3 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Number");
        }
        float min = ((Number) member3).floatValue();
        Object member4 = settingInfo.getMember("max");
        if (member4 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Number");
        }
        float max = ((Number) member4).floatValue();
        return new FloatValue(name, floatValue, min, max);
    }

    @JvmStatic
    @NotNull
    public static final TextValue text(@NotNull JSObject settingInfo) {
        Intrinsics.checkParameterIsNotNull(settingInfo, "settingInfo");
        Object member = settingInfo.getMember("name");
        if (member == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        String name = (String) member;
        Object member2 = settingInfo.getMember("default");
        if (member2 != null) {
            return new TextValue(name, (String) member2);
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
    }

    @JvmStatic
    @NotNull
    public static final BlockValue block(@NotNull JSObject settingInfo) {
        Intrinsics.checkParameterIsNotNull(settingInfo, "settingInfo");
        Object member = settingInfo.getMember("name");
        if (member == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        String name = (String) member;
        Object member2 = settingInfo.getMember("default");
        if (member2 != null) {
            return new BlockValue(name, ((Number) member2).intValue());
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Number");
    }

    @JvmStatic
    @NotNull
    public static final ListValue list(@NotNull JSObject settingInfo) {
        Intrinsics.checkParameterIsNotNull(settingInfo, "settingInfo");
        Object member = settingInfo.getMember("name");
        if (member == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        String name = (String) member;
        Object convert = ScriptUtils.convert(settingInfo.getMember("values"), String[].class);
        if (convert == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
        }
        String[] values = (String[]) convert;
        Object member2 = settingInfo.getMember("default");
        if (member2 != null) {
            return new ListValue(name, values, (String) member2);
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
    }
}
