package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AutoSoup.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n��R\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n��¨\u0006\u0016"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/AutoSoup;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "bowlValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "healthValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "openInventoryValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "simulateInventoryValue", "tag", "", "getTag", "()Ljava/lang/String;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "AutoSoup", spacedName = "Auto Soup", description = "Makes you automatically eat soup whenever your health is low.", category = ModuleCategory.COMBAT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/combat/AutoSoup.class */
public final class AutoSoup extends Module {
    @NotNull
    private final FloatValue healthValue = new FloatValue("Health", 15.0f, 0.0f, 20.0f);
    @NotNull
    private final IntegerValue delayValue = new IntegerValue("Delay", 150, 0, 500, "ms");
    @NotNull
    private final BoolValue openInventoryValue = new BoolValue("OpenInv", false);
    @NotNull
    private final BoolValue simulateInventoryValue = new BoolValue("SimulateInventory", true);
    @NotNull
    private final ListValue bowlValue = new ListValue("Bowl", new String[]{"Drop", "Move", "Stay"}, "Drop");
    @NotNull
    private final MSTimer timer = new MSTimer();

    @Override // net.ccbluex.liquidbounce.features.module.Module
    @NotNull
    public String getTag() {
        return String.valueOf(this.healthValue.get().floatValue());
    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent event) {
        if (!this.timer.hasTimePassed(this.delayValue.get().intValue())) {
            return;
        }
        int soupInHotbar = InventoryUtils.findItem(36, 45, Items.field_151009_A);
        if (MinecraftInstance.f362mc.field_71439_g.func_110143_aJ() <= this.healthValue.get().floatValue() && soupInHotbar != -1) {
            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C09PacketHeldItemChange(soupInHotbar - 36));
            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C08PacketPlayerBlockPlacement(MinecraftInstance.f362mc.field_71439_g.field_71069_bz.func_75139_a(soupInHotbar).func_75211_c()));
            if (StringsKt.equals(this.bowlValue.get(), "Drop", true)) {
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.DROP_ITEM, BlockPos.field_177992_a, EnumFacing.DOWN));
            }
            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C09PacketHeldItemChange(MinecraftInstance.f362mc.field_71439_g.field_71071_by.field_70461_c));
            this.timer.reset();
            return;
        }
        int bowlInHotbar = InventoryUtils.findItem(36, 45, Items.field_151054_z);
        if (StringsKt.equals(this.bowlValue.get(), "Move", true) && bowlInHotbar != -1) {
            if (this.openInventoryValue.get().booleanValue() && !(MinecraftInstance.f362mc.field_71462_r instanceof GuiInventory)) {
                return;
            }
            boolean bowlMovable = false;
            int i = 9;
            while (true) {
                if (i >= 37) {
                    break;
                }
                int i2 = i;
                i++;
                ItemStack itemStack = MinecraftInstance.f362mc.field_71439_g.field_71069_bz.func_75139_a(i2).func_75211_c();
                if (itemStack == null) {
                    bowlMovable = true;
                    break;
                } else if (Intrinsics.areEqual(itemStack.func_77973_b(), Items.field_151054_z) && itemStack.field_77994_a < 64) {
                    bowlMovable = true;
                    break;
                }
            }
            if (bowlMovable) {
                if (!(MinecraftInstance.f362mc.field_71462_r instanceof GuiInventory) && this.simulateInventoryValue.get().booleanValue()) {
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                }
                MinecraftInstance.f362mc.field_71442_b.func_78753_a(0, bowlInHotbar, 0, 1, MinecraftInstance.f362mc.field_71439_g);
            }
        }
        int soupInInventory = InventoryUtils.findItem(9, 36, Items.field_151009_A);
        if (soupInInventory != -1 && InventoryUtils.hasSpaceHotbar()) {
            if (this.openInventoryValue.get().booleanValue() && !(MinecraftInstance.f362mc.field_71462_r instanceof GuiInventory)) {
                return;
            }
            boolean openInventory = !(MinecraftInstance.f362mc.field_71462_r instanceof GuiInventory) && this.simulateInventoryValue.get().booleanValue();
            if (openInventory) {
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
            }
            MinecraftInstance.f362mc.field_71442_b.func_78753_a(0, soupInInventory, 0, 1, MinecraftInstance.f362mc.field_71439_g);
            if (openInventory) {
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C0DPacketCloseWindow());
            }
            this.timer.reset();
        }
    }
}
