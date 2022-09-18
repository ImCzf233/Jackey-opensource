package net.ccbluex.liquidbounce.features.module.modules.world;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import org.apache.log4j.lf5.viewer.LogBrokerMonitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Ambience.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u001fH\u0007J\u0010\u0010 \u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020!H\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n��\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\f\u0010\u0006R\u0016\u0010\r\u001a\u0004\u0018\u00010\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n��\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n��R\u0011\u0010\u0017\u001a\u00020\u0012¢\u0006\b\n��\u001a\u0004\b\u0018\u0010\u0014R\u0011\u0010\u0019\u001a\u00020\u0012¢\u0006\b\n��\u001a\u0004\b\u001a\u0010\u0014¨\u0006\""}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/Ambience;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "cycleSpeedValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "getCycleSpeedValue", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "rainStrengthValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "getRainStrengthValue", "()Lnet/ccbluex/liquidbounce/value/FloatValue;", "staticTimeValue", "getStaticTimeValue", "tag", "", "getTag", "()Ljava/lang/String;", "tagValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getTagValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "timeCycle", "", "timeModeValue", "getTimeModeValue", "weatherModeValue", "getWeatherModeValue", "onEnable", "", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "Ambience", description = "Change your world time and weather client-side.", category = ModuleCategory.WORLD)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/world/Ambience.class */
public final class Ambience extends Module {
    @NotNull
    private final ListValue timeModeValue = new ListValue("Time", new String[]{"Static", "Cycle"}, "Static");
    @NotNull
    private final IntegerValue cycleSpeedValue = new IntegerValue("CycleSpeed", 1, -24, 24, new Ambience$cycleSpeedValue$1(this));
    @NotNull
    private final IntegerValue staticTimeValue = new IntegerValue("StaticTime", 18000, 0, 24000, new Ambience$staticTimeValue$1(this));
    @NotNull
    private final ListValue weatherModeValue = new ListValue("Weather", new String[]{"Clear", "Rain", "NoModification"}, "Clear");
    @NotNull
    private final FloatValue rainStrengthValue = new FloatValue("RainStrength", 0.1f, 0.01f, 1.0f, new Ambience$rainStrengthValue$1(this));
    @NotNull
    private final ListValue tagValue = new ListValue("Tag", new String[]{"TimeOnly", "Simplified", LogBrokerMonitor.DETAILED_VIEW, "None"}, "TimeOnly");
    private long timeCycle;

    @NotNull
    public final ListValue getTimeModeValue() {
        return this.timeModeValue;
    }

    @NotNull
    public final IntegerValue getCycleSpeedValue() {
        return this.cycleSpeedValue;
    }

    @NotNull
    public final IntegerValue getStaticTimeValue() {
        return this.staticTimeValue;
    }

    @NotNull
    public final ListValue getWeatherModeValue() {
        return this.weatherModeValue;
    }

    @NotNull
    public final FloatValue getRainStrengthValue() {
        return this.rainStrengthValue;
    }

    @NotNull
    public final ListValue getTagValue() {
        return this.tagValue;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        this.timeCycle = 0L;
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getPacket() instanceof S03PacketTimeUpdate) {
            event.cancelEvent();
        }
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (StringsKt.equals(this.timeModeValue.get(), "static", true)) {
            MinecraftInstance.f362mc.field_71441_e.func_72877_b(this.staticTimeValue.get().intValue());
        } else {
            MinecraftInstance.f362mc.field_71441_e.func_72877_b(this.timeCycle);
            this.timeCycle += this.cycleSpeedValue.get().intValue() * 10;
            if (this.timeCycle > 24000) {
                this.timeCycle = 0L;
            }
            if (this.timeCycle < 0) {
                this.timeCycle = 24000L;
            }
        }
        if (!StringsKt.equals(this.weatherModeValue.get(), "nomodification", true)) {
            MinecraftInstance.f362mc.field_71441_e.func_72894_k(StringsKt.equals(this.weatherModeValue.get(), "clear", true) ? 0.0f : this.rainStrengthValue.get().floatValue());
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    @Nullable
    public String getTag() {
        String lowerCase = this.tagValue.get().toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -2076556295:
                if (lowerCase.equals("timeonly")) {
                    return StringsKt.equals(this.timeModeValue.get(), "static", true) ? String.valueOf(this.staticTimeValue.get().intValue()) : String.valueOf(this.timeCycle);
                }
                break;
            case -1427350696:
                if (lowerCase.equals("simplified")) {
                    return (StringsKt.equals(this.timeModeValue.get(), "static", true) ? String.valueOf(this.staticTimeValue.get().intValue()) : String.valueOf(this.timeCycle)) + ", " + this.weatherModeValue.get();
                }
                break;
            case 1044731056:
                if (lowerCase.equals("detailed")) {
                    return "Time: " + (StringsKt.equals(this.timeModeValue.get(), "static", true) ? String.valueOf(this.staticTimeValue.get().intValue()) : Intrinsics.stringPlus("Cycle, ", Long.valueOf(this.timeCycle))) + ", Weather: " + this.weatherModeValue.get();
                }
                break;
        }
        return null;
    }
}
