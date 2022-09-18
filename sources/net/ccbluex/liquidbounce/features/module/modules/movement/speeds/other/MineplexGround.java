package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/other/MineplexGround.class */
public class MineplexGround extends SpeedMode {
    private boolean spoofSlot;
    private float speed = 0.0f;

    public MineplexGround() {
        super("MineplexGround");
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMotion() {
        if (!MovementUtils.isMoving() || !f362mc.field_71439_g.field_70122_E || f362mc.field_71439_g.field_71071_by.func_70448_g() == null || f362mc.field_71439_g.func_71039_bw()) {
            return;
        }
        this.spoofSlot = false;
        for (int i = 36; i < 45; i++) {
            ItemStack itemStack = f362mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
            if (itemStack == null) {
                f362mc.func_147114_u().func_147297_a(new C09PacketHeldItemChange(i - 36));
                this.spoofSlot = true;
                return;
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onUpdate() {
        if (!MovementUtils.isMoving() || !f362mc.field_71439_g.field_70122_E || f362mc.field_71439_g.func_71039_bw()) {
            this.speed = 0.0f;
        } else if (!this.spoofSlot && f362mc.field_71439_g.field_71071_by.func_70448_g() != null) {
            ClientUtils.displayChatMessage("§8[§c§lMineplex§aSpeed§8] §cYou need one empty slot.");
        } else {
            BlockPos blockPos = new BlockPos(f362mc.field_71439_g.field_70165_t, f362mc.field_71439_g.func_174813_aQ().field_72338_b - 1.0d, f362mc.field_71439_g.field_70161_v);
            Vec3 vec = new Vec3(blockPos).func_72441_c(0.4000000059604645d, 0.4000000059604645d, 0.4000000059604645d).func_178787_e(new Vec3(EnumFacing.UP.func_176730_m()));
            f362mc.field_71442_b.func_178890_a(f362mc.field_71439_g, f362mc.field_71441_e, (ItemStack) null, blockPos, EnumFacing.UP, new Vec3(vec.field_72450_a * 0.4000000059604645d, vec.field_72448_b * 0.4000000059604645d, vec.field_72449_c * 0.4000000059604645d));
            float targetSpeed = ((Speed) LiquidBounce.moduleManager.getModule(Speed.class)).mineplexGroundSpeedValue.get().floatValue();
            if (targetSpeed > this.speed) {
                this.speed += targetSpeed / 8.0f;
            }
            if (this.speed >= targetSpeed) {
                this.speed = targetSpeed;
            }
            MovementUtils.strafe(this.speed);
            if (!this.spoofSlot) {
                f362mc.func_147114_u().func_147297_a(new C09PacketHeldItemChange(f362mc.field_71439_g.field_71071_by.field_70461_c));
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onMove(MoveEvent event) {
    }

    @Override // net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
    public void onDisable() {
        this.speed = 0.0f;
        f362mc.func_147114_u().func_147297_a(new C09PacketHeldItemChange(f362mc.field_71439_g.field_71071_by.field_70461_c));
    }
}
