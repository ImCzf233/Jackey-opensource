package net.ccbluex.liquidbounce.features.module.modules.movement;

import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.play.client.C0BPacketEntityAction;

@ModuleInfo(name = "Sneak", description = "Automatically sneaks all the time.", category = ModuleCategory.MOVEMENT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/Sneak.class */
public class Sneak extends Module {
    public final ListValue modeValue = new ListValue("Mode", new String[]{"Legit", "Vanilla", "Switch", "MineSecure", "AAC3.6.4"}, "MineSecure");
    public final BoolValue stopMoveValue = new BoolValue("StopMove", false);
    private boolean sneaked;

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        if (f362mc.field_71439_g != null && "vanilla".equalsIgnoreCase(this.modeValue.get())) {
            f362mc.func_147114_u().func_147297_a(new C0BPacketEntityAction(f362mc.field_71439_g, C0BPacketEntityAction.Action.START_SNEAKING));
        }
    }

    @EventTarget
    public void onMotion(MotionEvent event) {
        if (this.stopMoveValue.get().booleanValue() && MovementUtils.isMoving()) {
            if (this.sneaked) {
                onDisable();
                this.sneaked = false;
                return;
            }
            return;
        }
        this.sneaked = true;
        String lowerCase = this.modeValue.get().toLowerCase();
        boolean z = true;
        switch (lowerCase.hashCode()) {
            case -889473228:
                if (lowerCase.equals("switch")) {
                    z = true;
                    break;
                }
                break;
            case 102851513:
                if (lowerCase.equals("legit")) {
                    z = false;
                    break;
                }
                break;
            case 325231070:
                if (lowerCase.equals("aac3.6.4")) {
                    z = true;
                    break;
                }
                break;
            case 518567306:
                if (lowerCase.equals("minesecure")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                f362mc.field_71474_y.field_74311_E.field_74513_e = true;
                return;
            case true:
                switch (event.getEventState()) {
                    case PRE:
                        if (!MovementUtils.isMoving()) {
                            return;
                        }
                        f362mc.func_147114_u().func_147297_a(new C0BPacketEntityAction(f362mc.field_71439_g, C0BPacketEntityAction.Action.START_SNEAKING));
                        f362mc.func_147114_u().func_147297_a(new C0BPacketEntityAction(f362mc.field_71439_g, C0BPacketEntityAction.Action.STOP_SNEAKING));
                        return;
                    case POST:
                        f362mc.func_147114_u().func_147297_a(new C0BPacketEntityAction(f362mc.field_71439_g, C0BPacketEntityAction.Action.STOP_SNEAKING));
                        f362mc.func_147114_u().func_147297_a(new C0BPacketEntityAction(f362mc.field_71439_g, C0BPacketEntityAction.Action.START_SNEAKING));
                        return;
                    default:
                        return;
                }
            case true:
                if (event.getEventState() != EventState.PRE) {
                    f362mc.func_147114_u().func_147297_a(new C0BPacketEntityAction(f362mc.field_71439_g, C0BPacketEntityAction.Action.START_SNEAKING));
                    return;
                }
                return;
            case true:
                f362mc.field_71474_y.field_74311_E.field_74513_e = true;
                if (f362mc.field_71439_g.field_70122_E) {
                    MovementUtils.strafe(MovementUtils.getSpeed() * 1.251f);
                    return;
                } else {
                    MovementUtils.strafe(MovementUtils.getSpeed() * 1.03f);
                    return;
                }
            default:
                return;
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        if (f362mc.field_71439_g == null) {
            return;
        }
        String lowerCase = this.modeValue.get().toLowerCase();
        boolean z = true;
        switch (lowerCase.hashCode()) {
            case -889473228:
                if (lowerCase.equals("switch")) {
                    z = true;
                    break;
                }
                break;
            case 102851513:
                if (lowerCase.equals("legit")) {
                    z = false;
                    break;
                }
                break;
            case 233102203:
                if (lowerCase.equals("vanilla")) {
                    z = true;
                    break;
                }
                break;
            case 325231070:
                if (lowerCase.equals("aac3.6.4")) {
                    z = true;
                    break;
                }
                break;
            case 518567306:
                if (lowerCase.equals("minesecure")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
            case true:
            case true:
            case true:
                if (!GameSettings.func_100015_a(f362mc.field_71474_y.field_74311_E)) {
                    f362mc.field_71474_y.field_74311_E.field_74513_e = false;
                    break;
                }
                break;
            case true:
                f362mc.func_147114_u().func_147297_a(new C0BPacketEntityAction(f362mc.field_71439_g, C0BPacketEntityAction.Action.STOP_SNEAKING));
                break;
        }
        super.onDisable();
    }
}
