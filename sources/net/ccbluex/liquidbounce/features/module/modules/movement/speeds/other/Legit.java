package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.InvMove;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.GameSettings;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/other/Legit.class */
public class Legit extends SpeedMode {
    public Legit() {
        super("Legit");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        InvMove invMove = (InvMove) LiquidBounce.moduleManager.getModule(InvMove.class);
        f362mc.field_71474_y.field_74314_A.field_74513_e = (MovementUtils.isMoving() || GameSettings.func_100015_a(f362mc.field_71474_y.field_74314_A)) && (f362mc.field_71415_G || (invMove.getState() && !(f362mc.field_71462_r instanceof GuiChat) && !(f362mc.field_71462_r instanceof GuiIngameMenu) && (!invMove.getNoDetectableValue().get().booleanValue() || !(f362mc.field_71462_r instanceof GuiContainer))));
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
    }
}
