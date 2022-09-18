package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import org.jetbrains.annotations.NotNull;

/* compiled from: KeepAlive.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n��\u001a\u0004\b\u0007\u0010\b¨\u0006\r"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/KeepAlive;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "maxHealthValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "onMotion", "", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "LiquidBounce"})
@ModuleInfo(name = "KeepAlive", spacedName = "Keep Alive", description = "Tries to prevent you from dying.", category = ModuleCategory.PLAYER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/player/KeepAlive.class */
public final class KeepAlive extends Module {
    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[]{"/heal", "Soup"}, "/heal");
    @NotNull
    private final FloatValue maxHealthValue = new FloatValue("MaxHealth", 10.0f, 1.0f, 20.0f);

    @NotNull
    public final ListValue getModeValue() {
        return this.modeValue;
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        int soupInHotbar;
        Intrinsics.checkNotNullParameter(event, "event");
        EntityPlayerSP entityPlayerSP = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNull(entityPlayerSP);
        if (entityPlayerSP.func_110143_aJ() <= this.maxHealthValue.get().floatValue()) {
            String lowerCase = this.modeValue.get().toLowerCase();
            Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
            if (!Intrinsics.areEqual(lowerCase, "/heal")) {
                if (Intrinsics.areEqual(lowerCase, "soup") && (soupInHotbar = InventoryUtils.findItem(36, 45, Items.field_151009_A)) != -1) {
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C09PacketHeldItemChange(soupInHotbar - 36));
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C08PacketPlayerBlockPlacement(MinecraftInstance.f362mc.field_71439_g.field_71069_bz.func_75139_a(soupInHotbar).func_75211_c()));
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C09PacketHeldItemChange(MinecraftInstance.f362mc.field_71439_g.field_71071_by.field_70461_c));
                    return;
                }
                return;
            }
            MinecraftInstance.f362mc.field_71439_g.func_71165_d("/heal");
        }
    }
}
