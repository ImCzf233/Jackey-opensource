package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.client.settings.KeyBinding;
import org.jetbrains.annotations.NotNull;

/* compiled from: AutoClicker.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��4\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0007J\u0010\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0014H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��¨\u0006\u0015"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/AutoClicker;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "jitterValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "leftDelay", "", "leftLastSwing", "leftValue", "maxCPSValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "minCPSValue", "rightDelay", "rightLastSwing", "rightValue", "onRender", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "AutoClicker", spacedName = "Auto Clicker", description = "Constantly clicks when holding down a mouse button.", category = ModuleCategory.COMBAT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/combat/AutoClicker.class */
public final class AutoClicker extends Module {
    private long rightLastSwing;
    private long leftLastSwing;
    @NotNull
    private final IntegerValue maxCPSValue = new IntegerValue() { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.AutoClicker$maxCPSValue$1
        /* JADX INFO: Access modifiers changed from: package-private */
        {
            super("MaxCPS", 8, 1, 20);
        }

        @Override // net.ccbluex.liquidbounce.value.Value
        public /* bridge */ /* synthetic */ void onChanged(Integer num, Integer num2) {
            onChanged(num.intValue(), num2.intValue());
        }

        protected void onChanged(int oldValue, int newValue) {
            IntegerValue integerValue;
            integerValue = AutoClicker.this.minCPSValue;
            int minCPS = integerValue.get().intValue();
            if (minCPS > newValue) {
                set((AutoClicker$maxCPSValue$1) Integer.valueOf(minCPS));
            }
        }
    };
    @NotNull
    private final IntegerValue minCPSValue = new IntegerValue() { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.AutoClicker$minCPSValue$1
        /* JADX INFO: Access modifiers changed from: package-private */
        {
            super("MinCPS", 5, 1, 20);
        }

        @Override // net.ccbluex.liquidbounce.value.Value
        public /* bridge */ /* synthetic */ void onChanged(Integer num, Integer num2) {
            onChanged(num.intValue(), num2.intValue());
        }

        protected void onChanged(int oldValue, int newValue) {
            IntegerValue integerValue;
            integerValue = AutoClicker.this.maxCPSValue;
            int maxCPS = integerValue.get().intValue();
            if (maxCPS < newValue) {
                set((AutoClicker$minCPSValue$1) Integer.valueOf(maxCPS));
            }
        }
    };
    @NotNull
    private final BoolValue rightValue = new BoolValue("Right", true);
    @NotNull
    private final BoolValue leftValue = new BoolValue("Left", true);
    @NotNull
    private final BoolValue jitterValue = new BoolValue("Jitter", false);
    private long rightDelay = TimeUtils.randomClickDelay(this.minCPSValue.get().intValue(), this.maxCPSValue.get().intValue());
    private long leftDelay = TimeUtils.randomClickDelay(this.minCPSValue.get().intValue(), this.maxCPSValue.get().intValue());

    @EventTarget
    public final void onRender(@NotNull Render3DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.f362mc.field_71474_y.field_74312_F.func_151470_d() && this.leftValue.get().booleanValue() && System.currentTimeMillis() - this.leftLastSwing >= this.leftDelay) {
            if (MinecraftInstance.f362mc.field_71442_b.field_78770_f == 0.0f) {
                KeyBinding.func_74507_a(MinecraftInstance.f362mc.field_71474_y.field_74312_F.func_151463_i());
                this.leftLastSwing = System.currentTimeMillis();
                this.leftDelay = TimeUtils.randomClickDelay(this.minCPSValue.get().intValue(), this.maxCPSValue.get().intValue());
            }
        }
        if (MinecraftInstance.f362mc.field_71474_y.field_74313_G.func_151470_d() && !MinecraftInstance.f362mc.field_71439_g.func_71039_bw() && this.rightValue.get().booleanValue() && System.currentTimeMillis() - this.rightLastSwing >= this.rightDelay) {
            KeyBinding.func_74507_a(MinecraftInstance.f362mc.field_71474_y.field_74313_G.func_151463_i());
            this.rightLastSwing = System.currentTimeMillis();
            this.rightDelay = TimeUtils.randomClickDelay(this.minCPSValue.get().intValue(), this.maxCPSValue.get().intValue());
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0048, code lost:
        if ((net.ccbluex.liquidbounce.utils.MinecraftInstance.f362mc.field_71442_b.field_78770_f == 0.0f) == false) goto L13;
     */
    @net.ccbluex.liquidbounce.event.EventTarget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onUpdate(@org.jetbrains.annotations.NotNull net.ccbluex.liquidbounce.event.UpdateEvent r6) {
        /*
            Method dump skipped, instructions count: 270
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.features.module.modules.combat.AutoClicker.onUpdate(net.ccbluex.liquidbounce.event.UpdateEvent):void");
    }
}
