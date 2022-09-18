package net.ccbluex.liquidbounce.features.module.modules.movement;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

@ModuleInfo(name = "AntiFall", description = "Automatically setbacks you after falling a certain distance.", category = ModuleCategory.MOVEMENT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/AntiFall.class */
public class AntiFall extends Module {
    private BoolValue onlyVoid = new BoolValue("Only-Void", false);
    private FloatValue distance = new FloatValue("Void-Distance", 7.0f, 1.0f, 15.0f);

    @EventTarget
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof C03PacketPlayer) {
            C03PacketPlayer packet = event.getPacket();
            if (f362mc.field_71439_g.field_70143_R >= this.distance.get().floatValue()) {
                if (this.onlyVoid.get().booleanValue() && !isBlockUnder()) {
                    packet.field_149477_b += 11.0d;
                } else if (!this.onlyVoid.get().booleanValue()) {
                    packet.field_149477_b += 11.0d;
                }
            }
        }
    }

    private boolean isBlockUnder() {
        for (int i = (int) f362mc.field_71439_g.field_70163_u; i > 0; i--) {
            BlockPos pos = new BlockPos(f362mc.field_71439_g.field_70165_t, i, f362mc.field_71439_g.field_70161_v);
            if (!(f362mc.field_71441_e.func_180495_p(pos).func_177230_c() instanceof BlockAir)) {
                return true;
            }
        }
        return false;
    }
}
