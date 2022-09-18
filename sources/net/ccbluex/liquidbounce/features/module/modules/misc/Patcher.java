package net.ccbluex.liquidbounce.features.module.modules.misc;

import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.BoolValue;

@ModuleInfo(name = "Patcher", description = "improving your experience without bloatware, aka. Essential.", category = ModuleCategory.MISC, canEnable = false)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/misc/Patcher.class */
public class Patcher extends Module {
    public static final BoolValue noHitDelay = new BoolValue("NoHitDelay", false);
    public static final BoolValue jumpPatch = new BoolValue("JumpFix", true);
    public static final BoolValue chatPosition = new BoolValue("ChatPosition1.12", true);
    public static final BoolValue silentNPESP = new BoolValue("SilentNPE-SpawnPlayer", true);
}
