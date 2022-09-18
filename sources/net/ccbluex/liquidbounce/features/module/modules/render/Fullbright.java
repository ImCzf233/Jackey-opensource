package net.ccbluex.liquidbounce.features.module.modules.render;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.ClientShutdownEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

@ModuleInfo(name = "Fullbright", description = "Brightens up the world around you.", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/Fullbright.class */
public class Fullbright extends Module {
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Gamma", "NightVision"}, "Gamma");
    private float prevGamma = -1.0f;

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        this.prevGamma = f362mc.field_71474_y.field_74333_Y;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        if (this.prevGamma == -1.0f) {
            return;
        }
        f362mc.field_71474_y.field_74333_Y = this.prevGamma;
        this.prevGamma = -1.0f;
        if (f362mc.field_71439_g != null) {
            f362mc.field_71439_g.func_70618_n(Potion.field_76439_r.field_76415_H);
        }
    }

    @EventTarget(ignoreCondition = true)
    public void onUpdate(UpdateEvent event) {
        if (getState() || LiquidBounce.moduleManager.getModule(XRay.class).getState()) {
            String lowerCase = this.modeValue.get().toLowerCase();
            boolean z = true;
            switch (lowerCase.hashCode()) {
                case -820818432:
                    if (lowerCase.equals("nightvision")) {
                        z = true;
                        break;
                    }
                    break;
                case 98120615:
                    if (lowerCase.equals("gamma")) {
                        z = false;
                        break;
                    }
                    break;
            }
            switch (z) {
                case false:
                    if (f362mc.field_71474_y.field_74333_Y <= 100.0f) {
                        f362mc.field_71474_y.field_74333_Y += 1.0f;
                        return;
                    }
                    return;
                case true:
                    f362mc.field_71439_g.func_70690_d(new PotionEffect(Potion.field_76439_r.field_76415_H, 1337, 1));
                    return;
                default:
                    return;
            }
        } else if (this.prevGamma != -1.0f) {
            f362mc.field_71474_y.field_74333_Y = this.prevGamma;
            this.prevGamma = -1.0f;
        }
    }

    @EventTarget(ignoreCondition = true)
    public void onShutdown(ClientShutdownEvent event) {
        onDisable();
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public String getTag() {
        return this.modeValue.get();
    }
}
