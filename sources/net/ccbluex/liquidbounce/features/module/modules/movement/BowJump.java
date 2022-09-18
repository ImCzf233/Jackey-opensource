package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.awt.Color;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@ModuleInfo(name = "BowJump", spacedName = "Bow Jump", description = "Allows you to jump further with auto bow shoot.", category = ModuleCategory.MOVEMENT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/BowJump.class */
public class BowJump extends Module {
    private final FloatValue boostValue = new FloatValue("Boost", 4.25f, 0.0f, 10.0f, "x");
    private final FloatValue heightValue = new FloatValue("Height", 0.42f, 0.0f, 10.0f, "m");
    private final FloatValue timerValue = new FloatValue("Timer", 1.0f, 0.1f, 10.0f, "x");
    private final IntegerValue delayBeforeLaunch = new IntegerValue("DelayBeforeArrowLaunch", 1, 1, 20, " tick");
    private final BoolValue autoDisable = new BoolValue("AutoDisable", true);
    private final BoolValue renderValue = new BoolValue("RenderStatus", true);
    private int bowState = 0;
    private long lastPlayerTick = 0;
    private int lastSlot = -1;

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        if (f362mc.field_71439_g == null) {
            return;
        }
        this.bowState = 0;
        this.lastPlayerTick = -1L;
        this.lastSlot = f362mc.field_71439_g.field_71071_by.field_70461_c;
        MovementUtils.strafe(0.0f);
    }

    @EventTarget
    public void onMove(MoveEvent event) {
        if (f362mc.field_71439_g.field_70122_E && this.bowState < 3) {
            event.cancelEvent();
        }
    }

    @EventTarget
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof C09PacketHeldItemChange) {
            C09PacketHeldItemChange c09 = event.getPacket();
            this.lastSlot = c09.func_149614_c();
            event.cancelEvent();
        }
        if (event.getPacket() instanceof C03PacketPlayer) {
            C03PacketPlayer c03 = event.getPacket();
            if (this.bowState < 3) {
                c03.func_149469_a(false);
            }
        }
    }

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        f362mc.field_71428_T.field_74278_d = 1.0f;
        boolean forceDisable = false;
        switch (this.bowState) {
            case 0:
                int slot = getBowSlot();
                if (slot < 0 || !f362mc.field_71439_g.field_71071_by.func_146028_b(Items.field_151032_g)) {
                    LiquidBounce.hud.addNotification(new Notification("No arrows or bow found in your inventory!", Notification.Type.ERROR));
                    forceDisable = true;
                    this.bowState = 5;
                    break;
                } else if (this.lastPlayerTick == -1) {
                    f362mc.field_71439_g.field_71069_bz.func_75139_a(slot + 36).func_75211_c();
                    if (this.lastSlot != slot) {
                        PacketUtils.sendPacketNoEvent(new C09PacketHeldItemChange(slot));
                    }
                    PacketUtils.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, f362mc.field_71439_g.field_71069_bz.func_75139_a(slot + 36).func_75211_c(), 0.0f, 0.0f, 0.0f));
                    this.lastPlayerTick = f362mc.field_71439_g.field_70173_aa;
                    this.bowState = 1;
                    break;
                }
                break;
            case 1:
                int reSlot = getBowSlot();
                if (f362mc.field_71439_g.field_70173_aa - this.lastPlayerTick > this.delayBeforeLaunch.get().intValue()) {
                    PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C05PacketPlayerLook(f362mc.field_71439_g.field_70177_z, -90.0f, f362mc.field_71439_g.field_70122_E));
                    PacketUtils.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, EnumFacing.DOWN));
                    if (this.lastSlot != reSlot) {
                        PacketUtils.sendPacketNoEvent(new C09PacketHeldItemChange(this.lastSlot));
                    }
                    this.bowState = 2;
                    break;
                }
                break;
            case 2:
                if (f362mc.field_71439_g.field_70737_aN > 0) {
                    this.bowState = 3;
                    break;
                }
                break;
            case 3:
                MovementUtils.strafe(this.boostValue.get().floatValue());
                f362mc.field_71439_g.field_70181_x = this.heightValue.get().floatValue();
                this.bowState = 4;
                this.lastPlayerTick = f362mc.field_71439_g.field_70173_aa;
                break;
            case 4:
                f362mc.field_71428_T.field_74278_d = this.timerValue.get().floatValue();
                if (f362mc.field_71439_g.field_70122_E && f362mc.field_71439_g.field_70173_aa - this.lastPlayerTick >= 1) {
                    this.bowState = 5;
                    break;
                }
                break;
        }
        if (this.bowState < 3) {
            f362mc.field_71439_g.field_71158_b.field_78900_b = 0.0f;
            f362mc.field_71439_g.field_71158_b.field_78902_a = 0.0f;
        }
        if (this.bowState == 5) {
            if (this.autoDisable.get().booleanValue() || forceDisable) {
                setState(false);
            }
        }
    }

    @EventTarget
    public void onWorld(WorldEvent event) {
        setState(false);
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        f362mc.field_71428_T.field_74278_d = 1.0f;
        f362mc.field_71439_g.field_71102_ce = 0.02f;
    }

    private int getBowSlot() {
        for (int i = 36; i < 45; i++) {
            ItemStack stack = f362mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
            if (stack != null && (stack.func_77973_b() instanceof ItemBow)) {
                return i - 36;
            }
        }
        return -1;
    }

    @EventTarget
    public void onRender2D(Render2DEvent event) {
        if (!this.renderValue.get().booleanValue()) {
            return;
        }
        ScaledResolution scaledRes = new ScaledResolution(f362mc);
        float width = (this.bowState / 5.0f) * 60.0f;
        Fonts.font40.drawCenteredString(getBowStatus(), scaledRes.func_78326_a() / 2.0f, (scaledRes.func_78328_b() / 2.0f) + 14.0f, -1, true);
        RenderUtils.drawRect((scaledRes.func_78326_a() / 2.0f) - 31.0f, (scaledRes.func_78328_b() / 2.0f) + 25.0f, (scaledRes.func_78326_a() / 2.0f) + 31.0f, (scaledRes.func_78328_b() / 2.0f) + 29.0f, -1610612736);
        RenderUtils.drawRect((scaledRes.func_78326_a() / 2.0f) - 30.0f, (scaledRes.func_78328_b() / 2.0f) + 26.0f, ((scaledRes.func_78326_a() / 2.0f) - 30.0f) + width, (scaledRes.func_78328_b() / 2.0f) + 28.0f, getStatusColor());
    }

    public String getBowStatus() {
        switch (this.bowState) {
            case 0:
                return "Idle...";
            case 1:
                return "Preparing...";
            case 2:
                return "Waiting for damage...";
            case 3:
            case 4:
                return "Boost!";
            default:
                return "Task completed.";
        }
    }

    public Color getStatusColor() {
        switch (this.bowState) {
            case 0:
                return new Color(21, 21, 21);
            case 1:
                return new Color(48, 48, 48);
            case 2:
                return Color.yellow;
            case 3:
            case 4:
                return Color.green;
            default:
                return new Color(0, 111, 255);
        }
    }
}
