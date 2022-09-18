package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.ClickWindowEvent;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C16PacketClientStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: InvMove.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0016\u001a\u00020\u0017J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0007J\b\u0010\u001c\u001a\u00020\u0019H\u0016J\u0010\u0010\u001d\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001eH\u0007J\u0010\u0010\u001f\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020 H\u0007J\u0010\u0010!\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\"H\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n��\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\b¢\u0006\b\n��\u001a\u0004\b\f\u0010\nR\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004¢\u0006\u0002\n��R\u0011\u0010\u0010\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u0011\u0010\u0006R\u0016\u0010\u0012\u001a\u0004\u0018\u00010\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006#"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/InvMove;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "noDetectableValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getNoDetectableValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "noMoveClicksValue", "getNoMoveClicksValue", "playerPackets", "", "Lnet/minecraft/network/play/client/C03PacketPlayer;", "sprintModeValue", "getSprintModeValue", "tag", "", "getTag", "()Ljava/lang/String;", "isAACAP", "", "onClick", "", "event", "Lnet/ccbluex/liquidbounce/event/ClickWindowEvent;", "onDisable", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "InvMove", spacedName = "Inv Move", description = "Allows you to walk while an inventory is opened.", category = ModuleCategory.MOVEMENT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/InvMove.class */
public final class InvMove extends Module {
    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Vanilla", "Silent", "Blink"}, "Vanilla");
    @NotNull
    private final ListValue sprintModeValue = new ListValue("InvSprint", new String[]{"AACAP", "Stop", "Keep"}, "Keep");
    @NotNull
    private final BoolValue noDetectableValue = new BoolValue("NoDetectable", false);
    @NotNull
    private final BoolValue noMoveClicksValue = new BoolValue("NoMoveClicks", false);
    @NotNull
    private final List<C03PacketPlayer> playerPackets = new ArrayList();

    @NotNull
    public final ListValue getModeValue() {
        return this.modeValue;
    }

    @NotNull
    public final ListValue getSprintModeValue() {
        return this.sprintModeValue;
    }

    @NotNull
    public final BoolValue getNoDetectableValue() {
        return this.noDetectableValue;
    }

    @NotNull
    public final BoolValue getNoMoveClicksValue() {
        return this.noMoveClicksValue;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(Speed.class);
        if (module == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.movement.Speed");
        }
        Speed speedModule = (Speed) module;
        if ((MinecraftInstance.f362mc.field_71462_r instanceof GuiChat) || (MinecraftInstance.f362mc.field_71462_r instanceof GuiIngameMenu)) {
            return;
        }
        if (!this.noDetectableValue.get().booleanValue() || !(MinecraftInstance.f362mc.field_71462_r instanceof GuiContainer)) {
            MinecraftInstance.f362mc.field_71474_y.field_74351_w.field_74513_e = GameSettings.func_100015_a(MinecraftInstance.f362mc.field_71474_y.field_74351_w);
            MinecraftInstance.f362mc.field_71474_y.field_74368_y.field_74513_e = GameSettings.func_100015_a(MinecraftInstance.f362mc.field_71474_y.field_74368_y);
            MinecraftInstance.f362mc.field_71474_y.field_74366_z.field_74513_e = GameSettings.func_100015_a(MinecraftInstance.f362mc.field_71474_y.field_74366_z);
            MinecraftInstance.f362mc.field_71474_y.field_74370_x.field_74513_e = GameSettings.func_100015_a(MinecraftInstance.f362mc.field_71474_y.field_74370_x);
            if (!speedModule.getState() || !StringsKt.equals(speedModule.getMode().modeName, "Legit", true)) {
                MinecraftInstance.f362mc.field_71474_y.field_74314_A.field_74513_e = GameSettings.func_100015_a(MinecraftInstance.f362mc.field_71474_y.field_74314_A);
            }
            MinecraftInstance.f362mc.field_71474_y.field_151444_V.field_74513_e = GameSettings.func_100015_a(MinecraftInstance.f362mc.field_71474_y.field_151444_V);
            if (StringsKt.equals(this.sprintModeValue.get(), "stop", true)) {
                MinecraftInstance.f362mc.field_71439_g.func_70031_b(false);
            }
        }
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getEventState() != EventState.PRE || this.playerPackets.size() <= 0) {
            return;
        }
        if (MinecraftInstance.f362mc.field_71462_r == null || (MinecraftInstance.f362mc.field_71462_r instanceof GuiChat) || (MinecraftInstance.f362mc.field_71462_r instanceof GuiIngameMenu) || (this.noDetectableValue.get().booleanValue() && (MinecraftInstance.f362mc.field_71462_r instanceof GuiContainer))) {
            Iterable $this$forEach$iv = this.playerPackets;
            for (Object element$iv : $this$forEach$iv) {
                MinecraftInstance.f362mc.func_147114_u().func_147297_a((C03PacketPlayer) element$iv);
            }
            this.playerPackets.clear();
        }
    }

    @EventTarget
    public final void onClick(@NotNull ClickWindowEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.noMoveClicksValue.get().booleanValue() && MovementUtils.isMoving()) {
            event.cancelEvent();
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        C16PacketClientStatus packet = event.getPacket();
        String lowerCase = this.modeValue.get().toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
        if (Intrinsics.areEqual(lowerCase, "silent")) {
            if (!(packet instanceof C16PacketClientStatus) || packet.func_149435_c() != C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT) {
                return;
            }
            event.cancelEvent();
        } else if (!Intrinsics.areEqual(lowerCase, "blink") || MinecraftInstance.f362mc.field_71462_r == null || (MinecraftInstance.f362mc.field_71462_r instanceof GuiChat) || (MinecraftInstance.f362mc.field_71462_r instanceof GuiIngameMenu)) {
        } else {
            if ((!this.noDetectableValue.get().booleanValue() || !(MinecraftInstance.f362mc.field_71462_r instanceof GuiContainer)) && (packet instanceof C03PacketPlayer)) {
                event.cancelEvent();
                this.playerPackets.add(packet);
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        if (!GameSettings.func_100015_a(MinecraftInstance.f362mc.field_71474_y.field_74351_w) || MinecraftInstance.f362mc.field_71462_r != null) {
            MinecraftInstance.f362mc.field_71474_y.field_74351_w.field_74513_e = false;
        }
        if (!GameSettings.func_100015_a(MinecraftInstance.f362mc.field_71474_y.field_74368_y) || MinecraftInstance.f362mc.field_71462_r != null) {
            MinecraftInstance.f362mc.field_71474_y.field_74368_y.field_74513_e = false;
        }
        if (!GameSettings.func_100015_a(MinecraftInstance.f362mc.field_71474_y.field_74366_z) || MinecraftInstance.f362mc.field_71462_r != null) {
            MinecraftInstance.f362mc.field_71474_y.field_74366_z.field_74513_e = false;
        }
        if (!GameSettings.func_100015_a(MinecraftInstance.f362mc.field_71474_y.field_74370_x) || MinecraftInstance.f362mc.field_71462_r != null) {
            MinecraftInstance.f362mc.field_71474_y.field_74370_x.field_74513_e = false;
        }
        if (!GameSettings.func_100015_a(MinecraftInstance.f362mc.field_71474_y.field_74314_A) || MinecraftInstance.f362mc.field_71462_r != null) {
            MinecraftInstance.f362mc.field_71474_y.field_74314_A.field_74513_e = false;
        }
        if (!GameSettings.func_100015_a(MinecraftInstance.f362mc.field_71474_y.field_151444_V) || MinecraftInstance.f362mc.field_71462_r != null) {
            MinecraftInstance.f362mc.field_71474_y.field_151444_V.field_74513_e = false;
        }
    }

    public final boolean isAACAP() {
        return StringsKt.equals(this.sprintModeValue.get(), "aacap", true) && MinecraftInstance.f362mc.field_71462_r != null && !(MinecraftInstance.f362mc.field_71462_r instanceof GuiChat) && !(MinecraftInstance.f362mc.field_71462_r instanceof GuiIngameMenu) && (!this.noDetectableValue.get().booleanValue() || !(MinecraftInstance.f362mc.field_71462_r instanceof GuiContainer));
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    @Nullable
    public String getTag() {
        return this.modeValue.get();
    }
}
