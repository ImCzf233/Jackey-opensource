package de.enzaxd.viaforge.util;

import de.enzaxd.viaforge.ViaForge;
import de.enzaxd.viaforge.protocol.ProtocolCollection;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;

/* loaded from: Jackey Client b2.jar:de/enzaxd/viaforge/util/AttackOrder.class */
public class AttackOrder {

    /* renamed from: mc */
    private static final Minecraft f209mc = Minecraft.func_71410_x();
    private static final int VER_1_8_ID = 47;

    public static void sendConditionalSwing(MovingObjectPosition mop) {
        if (mop != null && mop.field_72313_a != MovingObjectPosition.MovingObjectType.ENTITY) {
            f209mc.field_71439_g.func_71038_i();
        }
    }

    public static void sendFixedAttack(EntityPlayer entityIn, Entity target) {
        if (ViaForge.getInstance().getVersion() <= ProtocolCollection.getProtocolById(47).getVersion()) {
            send1_8Attack(entityIn, target);
        } else {
            send1_9Attack(entityIn, target);
        }
    }

    private static void send1_8Attack(EntityPlayer entityIn, Entity target) {
        f209mc.field_71439_g.func_71038_i();
        f209mc.field_71442_b.func_78764_a(entityIn, target);
    }

    private static void send1_9Attack(EntityPlayer entityIn, Entity target) {
        f209mc.field_71442_b.func_78764_a(entityIn, target);
        f209mc.field_71439_g.func_71038_i();
    }
}
