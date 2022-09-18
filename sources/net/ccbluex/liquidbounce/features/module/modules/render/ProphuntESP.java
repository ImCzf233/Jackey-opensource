package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.BlockPos;

@ModuleInfo(name = "ProphuntESP", spacedName = "Prophunt ESP", description = "Allows you to see disguised players in PropHunt.", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/ProphuntESP.class */
public class ProphuntESP extends Module {
    public final Map<BlockPos, Long> blocks = new HashMap();
    private final IntegerValue colorRedValue = new IntegerValue("R", 0, 0, 255);
    private final IntegerValue colorGreenValue = new IntegerValue("G", 90, 0, 255);
    private final IntegerValue colorBlueValue = new IntegerValue("B", 255, 0, 255);
    private final BoolValue colorRainbow = new BoolValue("Rainbow", false);

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        synchronized (this.blocks) {
            this.blocks.clear();
        }
    }

    @EventTarget
    public void onRender3D(Render3DEvent event) {
        Color color = this.colorRainbow.get().booleanValue() ? ColorUtils.rainbow() : new Color(this.colorRedValue.get().intValue(), this.colorGreenValue.get().intValue(), this.colorBlueValue.get().intValue());
        for (Entity entity : f362mc.field_71441_e.field_72996_f) {
            if (entity instanceof EntityFallingBlock) {
                RenderUtils.drawEntityBox(entity, color, true);
            }
        }
        synchronized (this.blocks) {
            Iterator<Map.Entry<BlockPos, Long>> iterator = this.blocks.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<BlockPos, Long> entry = iterator.next();
                if (System.currentTimeMillis() - entry.getValue().longValue() > 2000) {
                    iterator.remove();
                } else {
                    RenderUtils.drawBlockBox(entry.getKey(), color, true);
                }
            }
        }
    }
}
