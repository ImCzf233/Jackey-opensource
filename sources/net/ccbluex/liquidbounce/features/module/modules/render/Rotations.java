package net.ccbluex.liquidbounce.features.module.modules.render;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.BowAimbot;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Disabler;
import net.ccbluex.liquidbounce.features.module.modules.movement.Fly;
import net.ccbluex.liquidbounce.features.module.modules.movement.Sprint;
import net.ccbluex.liquidbounce.features.module.modules.world.ChestAura;
import net.ccbluex.liquidbounce.features.module.modules.world.Fucker;
import net.ccbluex.liquidbounce.features.module.modules.world.Scaffold;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.util.Constants;

/* compiled from: Rotations.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0014\u0010\n\u001a\u00020\u000b2\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\rH\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007J\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0013H\u0007J\u0006\u0010\u0014\u001a\u00020\u000bR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\t¨\u0006\u0015"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/Rotations;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "playerYaw", "", "Ljava/lang/Float;", "getState", "", "module", Constants.CLASS, "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "shouldRotate", "LiquidBounce"})
@ModuleInfo(name = "Rotations", description = "Allows you to see server-sided head and body rotations.", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/Rotations.class */
public final class Rotations extends Module {
    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Head", "Body"}, "Body");
    @Nullable
    private Float playerYaw;

    @NotNull
    public final ListValue getModeValue() {
        return this.modeValue;
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (StringsKt.equals(this.modeValue.get(), "head", true) && RotationUtils.serverRotation != null) {
            MinecraftInstance.f362mc.field_71439_g.field_70759_as = RotationUtils.serverRotation.getYaw();
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (StringsKt.equals(this.modeValue.get(), "head", true) || !shouldRotate() || MinecraftInstance.f362mc.field_71439_g == null) {
            this.playerYaw = null;
            return;
        }
        C03PacketPlayer packet = event.getPacket();
        if ((packet instanceof C03PacketPlayer) && packet.field_149481_i) {
            this.playerYaw = Float.valueOf(packet.field_149476_e);
            MinecraftInstance.f362mc.field_71439_g.field_70761_aq = packet.func_149462_g();
            MinecraftInstance.f362mc.field_71439_g.field_70759_as = packet.func_149462_g();
            return;
        }
        if (this.playerYaw != null) {
            EntityPlayerSP entityPlayerSP = MinecraftInstance.f362mc.field_71439_g;
            Float f = this.playerYaw;
            Intrinsics.checkNotNull(f);
            entityPlayerSP.field_70761_aq = f.floatValue();
        }
        MinecraftInstance.f362mc.field_71439_g.field_70759_as = MinecraftInstance.f362mc.field_71439_g.field_70761_aq;
    }

    private final boolean getState(Class<?> cls) {
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(cls);
        Intrinsics.checkNotNull(module);
        return module.getState();
    }

    public final boolean shouldRotate() {
        Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(KillAura.class);
        if (module == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura");
        }
        KillAura killAura = (KillAura) module;
        Module module2 = LiquidBounce.INSTANCE.getModuleManager().getModule(Disabler.class);
        if (module2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.exploit.Disabler");
        }
        Disabler disabler = (Disabler) module2;
        Module module3 = LiquidBounce.INSTANCE.getModuleManager().getModule(Sprint.class);
        if (module3 == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.movement.Sprint");
        }
        Sprint sprint = (Sprint) module3;
        return getState(Scaffold.class) || (getState(Sprint.class) && sprint.getAllDirectionsValue().get().booleanValue() && sprint.getMoveDirPatchValue().get().booleanValue()) || ((getState(KillAura.class) && killAura.getTarget() != null) || ((getState(Disabler.class) && disabler.getCanRenderInto3D()) || getState(BowAimbot.class) || getState(Fucker.class) || getState(ChestAura.class) || getState(Fly.class)));
    }
}
