package net.ccbluex.liquidbounce.features.module.modules.render;

import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;

@ModuleInfo(name = "EnchantEffect", spacedName = "Enchant Effect", description = "qwq", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/EnchantEffect.class */
public class EnchantEffect extends Module {
    public IntegerValue redValue = new IntegerValue("Red", 255, 0, 255);
    public IntegerValue greenValue = new IntegerValue("Green", 0, 0, 255);
    public IntegerValue blueValue = new IntegerValue("Blue", 0, 0, 255);
    public ListValue modeValue = new ListValue("Mode", new String[]{"Custom", "Rainbow", "Sky", "Mixer"}, "Custom");
    public IntegerValue rainbowSpeedValue = new IntegerValue("Seconds", 1, 1, 6);
    public IntegerValue rainbowDelayValue = new IntegerValue("Delay", 5, 0, 10);
    public FloatValue rainbowSatValue = new FloatValue("Saturation", 1.0f, 0.0f, 1.0f);
    public FloatValue rainbowBrgValue = new FloatValue("Brightness", 1.0f, 0.0f, 1.0f);
}
