package net.ccbluex.liquidbounce.utils;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/PosLookInstance.class */
public class PosLookInstance {

    /* renamed from: x */
    private double f363x;

    /* renamed from: y */
    private double f364y;

    /* renamed from: z */
    private double f365z;
    private float yaw;
    private float pitch;

    public PosLookInstance() {
        this.f363x = 0.0d;
        this.f364y = 0.0d;
        this.f365z = 0.0d;
        this.yaw = 0.0f;
        this.pitch = 0.0f;
    }

    public PosLookInstance(double a, double b, double c, float d, float e) {
        this.f363x = 0.0d;
        this.f364y = 0.0d;
        this.f365z = 0.0d;
        this.yaw = 0.0f;
        this.pitch = 0.0f;
        this.f363x = a;
        this.f364y = b;
        this.f365z = c;
        this.yaw = d;
        this.pitch = e;
    }

    public void reset() {
        set(0.0d, 0.0d, 0.0d, 0.0f, 0.0f);
    }

    public void set(S08PacketPlayerPosLook packet) {
        set(packet.field_148940_a, packet.field_148938_b, packet.field_148939_c, packet.field_148936_d, packet.field_148937_e);
    }

    public void set(double a, double b, double c, float d, float e) {
        this.f363x = a;
        this.f364y = b;
        this.f365z = c;
        this.yaw = d;
        this.pitch = e;
    }

    public boolean equalFlag(C03PacketPlayer.C06PacketPlayerPosLook packet) {
        return packet != null && !packet.field_149474_g && packet.field_149479_a == this.f363x && packet.field_149477_b == this.f364y && packet.field_149478_c == this.f365z && packet.field_149476_e == this.yaw && packet.field_149473_f == this.pitch;
    }
}
