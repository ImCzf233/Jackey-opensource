package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.p004ui.client.hud.HUD;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Notification;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.jetbrains.annotations.NotNull;

/* compiled from: AutoDisable.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018�� \f2\u00020\u0001:\u0002\f\rB\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0007J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u000bH\u0007¨\u0006\u000e"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/AutoDisable;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "disableModules", "", "enumDisable", "Lnet/ccbluex/liquidbounce/features/module/modules/misc/AutoDisable$DisableEvent;", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "Companion", "DisableEvent", "LiquidBounce"})
@ModuleInfo(name = "AutoDisable", spacedName = "Auto Disable", description = "Automatically disable modules for you on flag or world respawn.", category = ModuleCategory.MISC, array = false)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/misc/AutoDisable.class */
public final class AutoDisable extends Module {
    @NotNull
    public static final Companion Companion = new Companion(null);

    /* compiled from: AutoDisable.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018��2\b\u0012\u0004\u0012\u00020��0\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/AutoDisable$DisableEvent;", "", "(Ljava/lang/String;I)V", "FLAG", "WORLD_CHANGE", "GAME_END", "LiquidBounce"})
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/misc/AutoDisable$DisableEvent.class */
    public enum DisableEvent {
        FLAG,
        WORLD_CHANGE,
        GAME_END
    }

    /* compiled from: AutoDisable.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48)
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/misc/AutoDisable$WhenMappings.class */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DisableEvent.values().length];
            iArr[DisableEvent.FLAG.ordinal()] = 1;
            iArr[DisableEvent.WORLD_CHANGE.ordinal()] = 2;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            disableModules(DisableEvent.FLAG);
        }
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        disableModules(DisableEvent.WORLD_CHANGE);
    }

    public final void disableModules(@NotNull DisableEvent enumDisable) {
        String str;
        Intrinsics.checkNotNullParameter(enumDisable, "enumDisable");
        int moduleNames = 0;
        Iterable $this$filter$iv = LiquidBounce.INSTANCE.getModuleManager().getModules();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            Module it = (Module) element$iv$iv;
            if (it.getAutoDisables().contains(enumDisable) && it.getState()) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $this$forEach$iv = (List) destination$iv$iv;
        for (Object element$iv : $this$forEach$iv) {
            ((Module) element$iv).toggle();
            moduleNames++;
        }
        if (moduleNames <= 0) {
            return;
        }
        HUD hud = LiquidBounce.INSTANCE.getHud();
        StringBuilder append = new StringBuilder().append("Disabled ").append(moduleNames).append(' ').append(moduleNames > 1 ? "modules" : "module").append(" due to ");
        switch (WhenMappings.$EnumSwitchMapping$0[enumDisable.ordinal()]) {
            case 1:
                str = "unexpected teleport";
                break;
            case 2:
                str = "world change";
                break;
            default:
                str = "game ended";
                break;
        }
        hud.addNotification(new Notification(append.append(str).append('.').toString(), Notification.Type.INFO, 1000L));
    }

    /* compiled from: AutoDisable.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/AutoDisable$Companion;", "", "()V", "handleGameEnd", "", "LiquidBounce"})
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/misc/AutoDisable$Companion.class */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }

        public final void handleGameEnd() {
            Module module = LiquidBounce.INSTANCE.getModuleManager().get(AutoDisable.class);
            Intrinsics.checkNotNull(module);
            AutoDisable autoDisableModule = (AutoDisable) module;
            autoDisableModule.disableModules(DisableEvent.GAME_END);
        }
    }
}
