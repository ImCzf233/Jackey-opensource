package net.ccbluex.liquidbounce.features.module.modules.player;

import com.viaversion.viaversion.libs.javassist.compiler.TokenId;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FastUse.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n��\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\u0012\u0010\u0016\u001a\u00020\u00152\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0007J\u0010\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u001aH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n��¨\u0006\u001b"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/FastUse;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "customSpeedValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "customTimer", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "delayValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "msTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "noMoveValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "tag", "", "getTag", "()Ljava/lang/String;", "usedTimer", "", "onDisable", "", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "FastUse", spacedName = "Fast Use", description = "Allows you to use items faster.", category = ModuleCategory.PLAYER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/player/FastUse.class */
public final class FastUse extends Module {
    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Instant", "NCP", "AAC", "CustomDelay", "AACv4_2"}, "NCP");
    @NotNull
    private final BoolValue noMoveValue = new BoolValue("NoMove", false);
    @NotNull
    private final IntegerValue delayValue = new IntegerValue("CustomDelay", 0, 0, (int) TokenId.ABSTRACT, new FastUse$delayValue$1(this));
    @NotNull
    private final IntegerValue customSpeedValue = new IntegerValue("CustomSpeed", 2, 0, 35, " packet", new FastUse$customSpeedValue$1(this));
    @NotNull
    private final FloatValue customTimer = new FloatValue("CustomTimer", 1.1f, 0.5f, 2.0f, "x", new FastUse$customTimer$1(this));
    @NotNull
    private final MSTimer msTimer = new MSTimer();
    private boolean usedTimer;

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.usedTimer) {
            MinecraftInstance.f362mc.field_71428_T.field_74278_d = 1.0f;
            this.usedTimer = false;
        }
        if (!MinecraftInstance.f362mc.field_71439_g.func_71039_bw()) {
            this.msTimer.reset();
            return;
        }
        Item usingItem = MinecraftInstance.f362mc.field_71439_g.func_71011_bu().func_77973_b();
        if ((usingItem instanceof ItemFood) || (usingItem instanceof ItemBucketMilk) || (usingItem instanceof ItemPotion)) {
            String lowerCase = this.modeValue.get().toLowerCase();
            Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
            switch (lowerCase.hashCode()) {
                case -1773359950:
                    if (lowerCase.equals("customdelay")) {
                        MinecraftInstance.f362mc.field_71428_T.field_74278_d = this.customTimer.get().floatValue();
                        this.usedTimer = true;
                        if (!this.msTimer.hasTimePassed(this.delayValue.get().intValue())) {
                            return;
                        }
                        int intValue = this.customSpeedValue.get().intValue();
                        int i = 0;
                        while (i < intValue) {
                            i++;
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer(MinecraftInstance.f362mc.field_71439_g.field_70122_E));
                        }
                        this.msTimer.reset();
                        return;
                    }
                    return;
                case -1234431628:
                    if (lowerCase.equals("aacv4_2")) {
                        MinecraftInstance.f362mc.field_71428_T.field_74278_d = 0.49f;
                        this.usedTimer = true;
                        if (MinecraftInstance.f362mc.field_71439_g.func_71057_bx() > 13) {
                            int i2 = 0;
                            while (i2 < 23) {
                                i2++;
                                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer(MinecraftInstance.f362mc.field_71439_g.field_70122_E));
                            }
                            MinecraftInstance.f362mc.field_71442_b.func_78766_c(MinecraftInstance.f362mc.field_71439_g);
                            return;
                        }
                        return;
                    }
                    return;
                case 96323:
                    if (lowerCase.equals("aac")) {
                        MinecraftInstance.f362mc.field_71428_T.field_74278_d = 1.1f;
                        this.usedTimer = true;
                        return;
                    }
                    return;
                case 108891:
                    if (lowerCase.equals("ncp") && MinecraftInstance.f362mc.field_71439_g.func_71057_bx() > 14) {
                        int i3 = 0;
                        while (i3 < 20) {
                            i3++;
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer(MinecraftInstance.f362mc.field_71439_g.field_70122_E));
                        }
                        MinecraftInstance.f362mc.field_71442_b.func_78766_c(MinecraftInstance.f362mc.field_71439_g);
                        return;
                    }
                    return;
                case 1957570017:
                    if (lowerCase.equals("instant")) {
                        int i4 = 0;
                        while (i4 < 32) {
                            i4++;
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer(MinecraftInstance.f362mc.field_71439_g.field_70122_E));
                        }
                        MinecraftInstance.f362mc.field_71442_b.func_78766_c(MinecraftInstance.f362mc.field_71439_g);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    @EventTarget
    public final void onMove(@Nullable MoveEvent event) {
        if (event != null && getState() && MinecraftInstance.f362mc.field_71439_g.func_71039_bw() && this.noMoveValue.get().booleanValue()) {
            Item usingItem = MinecraftInstance.f362mc.field_71439_g.func_71011_bu().func_77973_b();
            if ((usingItem instanceof ItemFood) || (usingItem instanceof ItemBucketMilk) || (usingItem instanceof ItemPotion)) {
                event.zero();
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        if (this.usedTimer) {
            MinecraftInstance.f362mc.field_71428_T.field_74278_d = 1.0f;
            this.usedTimer = false;
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    @Nullable
    public String getTag() {
        return this.modeValue.get();
    }
}
