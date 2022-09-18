package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.Random;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import org.jetbrains.annotations.NotNull;

/* compiled from: AutoLeave.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��8\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��\n\u0002\u0010\b\n��\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��¨\u0006\u0012"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/AutoLeave;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "healthValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "keepArmor", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "move", "", "item", "", "isArmorSlot", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "AutoLeave", spacedName = "Auto Leave", description = "Automatically makes you leave the server whenever your health is low.", category = ModuleCategory.COMBAT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/combat/AutoLeave.class */
public final class AutoLeave extends Module {
    @NotNull
    private final FloatValue healthValue = new FloatValue("Health", 8.0f, 0.0f, 20.0f);
    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Lobby", "Quit", "InvalidPacket", "SelfHurt", "IllegalChat"}, "Quit");
    @NotNull
    private final BoolValue keepArmor = new BoolValue("KeepArmor", false);

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.f362mc.field_71439_g.func_110143_aJ() <= this.healthValue.get().floatValue() && !MinecraftInstance.f362mc.field_71439_g.field_71075_bZ.field_75098_d && !MinecraftInstance.f362mc.func_71387_A()) {
            if (this.keepArmor.get().booleanValue()) {
                int i = 0;
                while (i < 4) {
                    int i2 = i;
                    i++;
                    int armorSlot = 3 - i2;
                    move(8 - armorSlot, true);
                }
            }
            String lowerCase = this.modeValue.get().toLowerCase();
            Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
            switch (lowerCase.hashCode()) {
                case -1647903794:
                    if (lowerCase.equals("illegalchat")) {
                        MinecraftInstance.f362mc.field_71439_g.func_71165_d(new Random().nextInt() + "§§§" + new Random().nextInt());
                        break;
                    }
                    break;
                case -367089345:
                    if (lowerCase.equals("invalidpacket")) {
                        MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, !MinecraftInstance.f362mc.field_71439_g.field_70122_E));
                        break;
                    }
                    break;
                case 3482191:
                    if (lowerCase.equals("quit")) {
                        MinecraftInstance.f362mc.field_71441_e.func_72882_A();
                        break;
                    }
                    break;
                case 103144406:
                    if (lowerCase.equals("lobby")) {
                        MinecraftInstance.f362mc.field_71439_g.func_71165_d("/lobby");
                        break;
                    }
                    break;
                case 1192645979:
                    if (lowerCase.equals("selfhurt")) {
                        MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C02PacketUseEntity(MinecraftInstance.f362mc.field_71439_g, C02PacketUseEntity.Action.ATTACK));
                        break;
                    }
                    break;
            }
            setState(false);
        }
    }

    private final void move(int item, boolean isArmorSlot) {
        if (item != -1) {
            boolean openInventory = !(MinecraftInstance.f362mc.field_71462_r instanceof GuiInventory);
            if (openInventory) {
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
            }
            MinecraftInstance.f362mc.field_71442_b.func_78753_a(MinecraftInstance.f362mc.field_71439_g.field_71069_bz.field_75152_c, isArmorSlot ? item : item < 9 ? item + 36 : item, 0, 1, MinecraftInstance.f362mc.field_71439_g);
            if (openInventory) {
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C0DPacketCloseWindow());
            }
        }
    }
}
