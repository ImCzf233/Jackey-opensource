package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.Random;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Level;

@ModuleInfo(name = "Heal", description = "Automatically eats Gapple. (only for some servers with a broken anticheat)", category = ModuleCategory.PLAYER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/player/Heal.class */
public class Heal extends Module {
    private final FloatValue percent = new FloatValue("HealthPercent", 75.0f, 1.0f, 100.0f, "%");
    private final IntegerValue min = new IntegerValue("MinDelay", 75, 1, (int) Level.TRACE_INT, "ms");
    private final IntegerValue max = new IntegerValue("MaxDelay", 125, 1, (int) Level.TRACE_INT, "ms");
    private final FloatValue regenSec = new FloatValue("RegenSec", 4.6f, 0.0f, 10.0f);
    private final BoolValue groundCheck = new BoolValue("GroundCheck", false);
    private final BoolValue voidCheck = new BoolValue("VoidCheck", true);
    private final BoolValue waitRegen = new BoolValue("WaitRegen", true);
    private final BoolValue invCheck = new BoolValue("InvCheck", false);
    private final BoolValue absorpCheck = new BoolValue("AbsorpCheck", true);
    final MSTimer timer = new MSTimer();
    int delay;
    boolean isDisable;

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        super.onEnable();
        this.timer.reset();
        this.isDisable = false;
        this.delay = MathHelper.func_76136_a(new Random(), this.min.get().intValue(), this.max.get().intValue());
    }

    @EventTarget
    public void onPacket(PacketEvent e) {
        if ((e.getPacket() instanceof S02PacketChat) && e.getPacket().func_148915_c().func_150254_d().contains("§r§7 won the game! §r§e✪§r")) {
            ClientUtils.displayChatMessage("§f[§cSLHeal§f] §6Temp Disable Heal");
            this.isDisable = true;
        }
    }

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if (f362mc.field_71439_g.field_70173_aa <= 5 && this.isDisable) {
            this.isDisable = false;
            ClientUtils.displayChatMessage("§f[§cSLHeal§f] §6Enable Heal due to World Changed or Player Respawned");
        }
        int absorp = MathHelper.func_76143_f(f362mc.field_71439_g.func_110139_bj());
        if (!this.groundCheck.get().booleanValue() || f362mc.field_71439_g.field_70122_E) {
            if (this.voidCheck.get().booleanValue() && !MovementUtils.isBlockUnder()) {
                return;
            }
            if (this.invCheck.get().booleanValue() && (f362mc.field_71462_r instanceof GuiContainer)) {
                return;
            }
            if (absorp != 0 && this.absorpCheck.get().booleanValue()) {
                return;
            }
            if (this.waitRegen.get().booleanValue() && f362mc.field_71439_g.func_70644_a(Potion.field_76428_l) && f362mc.field_71439_g.func_70660_b(Potion.field_76428_l).func_76459_b() > this.regenSec.get().floatValue() * 20.0f) {
                return;
            }
            Pair<Integer, ItemStack> pair = getGAppleSlot();
            if (this.isDisable || pair == null) {
                return;
            }
            if ((f362mc.field_71439_g.func_110143_aJ() <= (this.percent.get().floatValue() / 100.0f) * f362mc.field_71439_g.func_110138_aP() || !f362mc.field_71439_g.func_70644_a(Potion.field_76444_x) || (absorp == 0 && f362mc.field_71439_g.func_110143_aJ() == 20.0f && f362mc.field_71439_g.func_70644_a(Potion.field_76444_x))) && this.timer.hasTimePassed(this.delay)) {
                ClientUtils.displayChatMessage("§f[§cSLHeal§f] §6Healed");
                int lastSlot = f362mc.field_71439_g.field_71071_by.field_70461_c;
                int slot = ((Integer) pair.getLeft()).intValue();
                f362mc.func_147114_u().func_147297_a(new C09PacketHeldItemChange(slot));
                ItemStack stack = (ItemStack) pair.getRight();
                f362mc.func_147114_u().func_147297_a(new C08PacketPlayerBlockPlacement(stack));
                for (int i = 0; i < 32; i++) {
                    f362mc.func_147114_u().func_147297_a(new C03PacketPlayer());
                }
                f362mc.func_147114_u().func_147297_a(new C09PacketHeldItemChange(lastSlot));
                f362mc.field_71439_g.field_71071_by.field_70461_c = lastSlot;
                f362mc.field_71442_b.func_78765_e();
                this.delay = MathHelper.func_76136_a(new Random(), this.min.get().intValue(), this.max.get().intValue());
                this.timer.reset();
            }
        }
    }

    private Pair<Integer, ItemStack> getGAppleSlot() {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = f362mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack != null && stack.func_77973_b() == Items.field_151153_ao) {
                return Pair.of(Integer.valueOf(i), stack);
            }
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public String getTag() {
        if (f362mc.field_71439_g == null || f362mc.field_71439_g.func_110143_aJ() == Double.NaN) {
            return null;
        }
        return String.format("%.2f HP", Float.valueOf((this.percent.get().floatValue() / 100.0f) * f362mc.field_71439_g.func_110138_aP()));
    }
}
