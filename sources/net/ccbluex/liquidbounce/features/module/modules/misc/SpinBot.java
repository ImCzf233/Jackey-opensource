package net.ccbluex.liquidbounce.features.module.modules.misc;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SpinBot.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u001d\u001a\u00020\u001eH\u0016J\u0010\u0010\u001f\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020!H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u001a\u0010\u0005\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n��\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0014\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n��R\u0016\u0010\u0015\u001a\u0004\u0018\u00010\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001a\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001b\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001c\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n��¨\u0006\""}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/SpinBot;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "lastSpin", "", "pitch", "getPitch", "()F", "setPitch", "(F)V", "pitchJitterTimer", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "pitchMode", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getPitchMode", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "pitchTimer", "", "static_offsetPitch", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "static_offsetYaw", "tag", "", "getTag", "()Ljava/lang/String;", "yawJitterTimer", "yawMode", "yawSpinSpeed", "yawTimer", "onDisable", "", "onRender3D", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "LiquidBounce"})
@ModuleInfo(name = "SpinBot", spacedName = "Spin Bot", description = "Client-sided spin bot like CS:GO hacks.", category = ModuleCategory.MISC)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/misc/SpinBot.class */
public final class SpinBot extends Module {
    @NotNull
    private final ListValue yawMode = new ListValue("Yaw", new String[]{"Static", "Offset", "Random", "Jitter", "Spin", "None"}, "Offset");
    @NotNull
    private final ListValue pitchMode = new ListValue("Pitch", new String[]{"Static", "Offset", "Random", "Jitter", "None"}, "Offset");
    @NotNull
    private final FloatValue static_offsetYaw = new FloatValue("Static/Offset-Yaw", 0.0f, -180.0f, 180.0f, "°");
    @NotNull
    private final FloatValue static_offsetPitch = new FloatValue("Static/Offset-Pitch", 0.0f, -90.0f, 90.0f, "°");
    @NotNull
    private final IntegerValue yawJitterTimer = new IntegerValue("YawJitterTimer", 1, 1, 40, " tick(s)");
    @NotNull
    private final IntegerValue pitchJitterTimer = new IntegerValue("PitchJitterTimer", 1, 1, 40, " tick(s)");
    @NotNull
    private final FloatValue yawSpinSpeed = new FloatValue("YawSpinSpeed", 5.0f, -90.0f, 90.0f, "°");
    private float pitch;
    private float lastSpin;
    private int yawTimer;
    private int pitchTimer;

    @NotNull
    public final ListValue getPitchMode() {
        return this.pitchMode;
    }

    public final float getPitch() {
        return this.pitch;
    }

    public final void setPitch(float f) {
        this.pitch = f;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        this.pitch = -4.9531336E7f;
        this.lastSpin = 0.0f;
        this.yawTimer = 0;
        this.pitchTimer = 0;
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.f362mc.field_71439_g == null) {
            return;
        }
        if (!StringsKt.equals(this.yawMode.get(), "none", true)) {
            float yaw = 0.0f;
            String lowerCase = this.yawMode.get().toLowerCase();
            Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
            switch (lowerCase.hashCode()) {
                case -1159737108:
                    if (lowerCase.equals("jitter")) {
                        int i = this.yawTimer;
                        this.yawTimer = i + 1;
                        if (i % (this.yawJitterTimer.get().intValue() * 2) >= this.yawJitterTimer.get().intValue()) {
                            yaw = MinecraftInstance.f362mc.field_71439_g.field_70177_z;
                            break;
                        } else {
                            yaw = MinecraftInstance.f362mc.field_71439_g.field_70177_z - 180.0f;
                            break;
                        }
                    }
                    break;
                case -1019779949:
                    if (lowerCase.equals("offset")) {
                        yaw = MinecraftInstance.f362mc.field_71439_g.field_70177_z + this.static_offsetYaw.get().floatValue();
                        break;
                    }
                    break;
                case -938285885:
                    if (lowerCase.equals("random")) {
                        yaw = (float) Math.floor((Math.random() * 360.0d) - 180.0d);
                        break;
                    }
                    break;
                case -892481938:
                    if (lowerCase.equals("static")) {
                        yaw = this.static_offsetYaw.get().floatValue();
                        break;
                    }
                    break;
                case 3536962:
                    if (lowerCase.equals("spin")) {
                        this.lastSpin += this.yawSpinSpeed.get().floatValue();
                        yaw = this.lastSpin;
                        break;
                    }
                    break;
            }
            MinecraftInstance.f362mc.field_71439_g.field_70761_aq = yaw;
            MinecraftInstance.f362mc.field_71439_g.field_70759_as = yaw;
            this.lastSpin = yaw;
        }
        String lowerCase2 = this.pitchMode.get().toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase2, "this as java.lang.String).toLowerCase()");
        switch (lowerCase2.hashCode()) {
            case -1159737108:
                if (lowerCase2.equals("jitter")) {
                    int i2 = this.pitchTimer;
                    this.pitchTimer = i2 + 1;
                    if (i2 % (this.pitchJitterTimer.get().intValue() * 2) >= this.pitchJitterTimer.get().intValue()) {
                        this.pitch = 90.0f;
                        return;
                    } else {
                        this.pitch = -90.0f;
                        return;
                    }
                }
                return;
            case -1019779949:
                if (lowerCase2.equals("offset")) {
                    this.pitch = MinecraftInstance.f362mc.field_71439_g.field_70125_A + this.static_offsetPitch.get().floatValue();
                    return;
                }
                return;
            case -938285885:
                if (lowerCase2.equals("random")) {
                    this.pitch = (float) Math.floor((Math.random() * 180.0d) - 90.0d);
                    return;
                }
                return;
            case -892481938:
                if (lowerCase2.equals("static")) {
                    this.pitch = this.static_offsetPitch.get().floatValue();
                    return;
                }
                return;
            default:
                return;
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    @Nullable
    public String getTag() {
        return this.yawMode.get() + ", " + this.pitchMode.get();
    }
}
