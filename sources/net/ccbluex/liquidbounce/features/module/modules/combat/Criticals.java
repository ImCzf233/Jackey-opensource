package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.Fly;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Criticals.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n��\n\u0002\u0010\b\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0007J\b\u0010\"\u001a\u00020\u001fH\u0016J\u0010\u0010#\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020$H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n��\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000e\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n��R\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n��\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n��\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0019\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u0016\u0010\u001a\u001a\u0004\u0018\u00010\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001d¨\u0006%"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/Criticals;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "canCrits", "", "counter", "", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "getDelayValue", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "downYValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "hurtTimeValue", "jumpHeightValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "msTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "getMsTimer", "()Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onlyAuraValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "readyCrits", "tag", "", "getTag", "()Ljava/lang/String;", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onEnable", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "LiquidBounce"})
@ModuleInfo(name = "Criticals", description = "Automatically deals critical hits.", category = ModuleCategory.COMBAT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/combat/Criticals.class */
public final class Criticals extends Module {
    private boolean readyCrits;
    private int counter;
    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[]{"NewPacket", "Packet", "Packet2", "NCPPacket", "NoGround", "Redesky", "AACv4", "AAC5", "Spartan", "Vulcan", "Hop", "TPHop", "Jump", "Visual", "Edit", "MiniPhase", "NanoPacket", "Non-Calculable", "Invalid", "VerusSmart"}, "Packet");
    @NotNull
    private final IntegerValue delayValue = new IntegerValue("Delay", 0, 0, 500, "ms");
    @NotNull
    private final FloatValue jumpHeightValue = new FloatValue("JumpHeight", 0.42f, 0.1f, 0.42f);
    @NotNull
    private final FloatValue downYValue = new FloatValue("DownY", 0.0f, 0.0f, 0.1f);
    @NotNull
    private final IntegerValue hurtTimeValue = new IntegerValue("HurtTime", 10, 0, 10);
    @NotNull
    private final BoolValue onlyAuraValue = new BoolValue("OnlyAura", false);
    @NotNull
    private final MSTimer msTimer = new MSTimer();
    private boolean canCrits = true;

    @NotNull
    public final ListValue getModeValue() {
        return this.modeValue;
    }

    @NotNull
    public final IntegerValue getDelayValue() {
        return this.delayValue;
    }

    @NotNull
    public final MSTimer getMsTimer() {
        return this.msTimer;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        if (StringsKt.equals(this.modeValue.get(), "NoGround", true)) {
            MinecraftInstance.f362mc.field_71439_g.func_70664_aZ();
        }
        this.canCrits = true;
        this.counter = 0;
    }

    @EventTarget
    public final void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.onlyAuraValue.get().booleanValue()) {
            Module module = LiquidBounce.INSTANCE.getModuleManager().get(KillAura.class);
            Intrinsics.checkNotNull(module);
            if (!module.getState()) {
                Module module2 = LiquidBounce.INSTANCE.getModuleManager().get(TeleportAura.class);
                Intrinsics.checkNotNull(module2);
                if (!module2.getState()) {
                    return;
                }
            }
        }
        if (event.getTargetEntity() instanceof EntityLivingBase) {
            EntityLivingBase targetEntity = event.getTargetEntity();
            if (MinecraftInstance.f362mc.field_71439_g.field_70122_E && !MinecraftInstance.f362mc.field_71439_g.func_70617_f_() && !MinecraftInstance.f362mc.field_71439_g.field_70134_J && !MinecraftInstance.f362mc.field_71439_g.func_70090_H() && !MinecraftInstance.f362mc.field_71439_g.func_180799_ab() && MinecraftInstance.f362mc.field_71439_g.field_70154_o == null && targetEntity.field_70737_aN <= this.hurtTimeValue.get().intValue()) {
                Module module3 = LiquidBounce.INSTANCE.getModuleManager().get(Fly.class);
                Intrinsics.checkNotNull(module3);
                if (module3.getState() || !this.msTimer.hasTimePassed(this.delayValue.get().intValue())) {
                    return;
                }
                double x = MinecraftInstance.f362mc.field_71439_g.field_70165_t;
                double y = MinecraftInstance.f362mc.field_71439_g.field_70163_u;
                double z = MinecraftInstance.f362mc.field_71439_g.field_70161_v;
                String lowerCase = this.modeValue.get().toLowerCase();
                Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
                switch (lowerCase.hashCode()) {
                    case -1362618874:
                        if (lowerCase.equals("non-calculable")) {
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 1.0E-5d, z, false));
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 1.0E-7d, z, false));
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y - 1.0E-6d, z, false));
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y - 1.0E-4d, z, false));
                            break;
                        }
                        break;
                    case -995865464:
                        if (lowerCase.equals("packet")) {
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.0625d, z, true));
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, false));
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 1.1E-5d, z, false));
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, false));
                            MinecraftInstance.f362mc.field_71439_g.func_71009_b(targetEntity);
                            break;
                        }
                        break;
                    case -891664989:
                        if (lowerCase.equals("ncppacket")) {
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.11d, z, false));
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.1100013579d, z, false));
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 1.3579E-6d, z, false));
                            MinecraftInstance.f362mc.field_71439_g.func_71009_b(targetEntity);
                            break;
                        }
                        break;
                    case -816216256:
                        if (lowerCase.equals("visual")) {
                            MinecraftInstance.f362mc.field_71439_g.func_71009_b(targetEntity);
                            break;
                        }
                        break;
                    case -807058262:
                        if (lowerCase.equals("packet2")) {
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.f362mc.field_71439_g.field_70165_t, MinecraftInstance.f362mc.field_71439_g.field_70163_u + 0.0625d, MinecraftInstance.f362mc.field_71439_g.field_70161_v, false));
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.f362mc.field_71439_g.field_70165_t, MinecraftInstance.f362mc.field_71439_g.field_70163_u + 0.09858d, MinecraftInstance.f362mc.field_71439_g.field_70161_v, false));
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.f362mc.field_71439_g.field_70165_t, MinecraftInstance.f362mc.field_71439_g.field_70163_u + 0.04114514d, MinecraftInstance.f362mc.field_71439_g.field_70161_v, false));
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.f362mc.field_71439_g.field_70165_t, MinecraftInstance.f362mc.field_71439_g.field_70163_u + 0.025d, MinecraftInstance.f362mc.field_71439_g.field_70161_v, false));
                            break;
                        }
                        break;
                    case 103497:
                        if (lowerCase.equals("hop")) {
                            MinecraftInstance.f362mc.field_71439_g.field_70181_x = 0.1d;
                            MinecraftInstance.f362mc.field_71439_g.field_70143_R = 0.1f;
                            MinecraftInstance.f362mc.field_71439_g.field_70122_E = false;
                            break;
                        }
                        break;
                    case 3273774:
                        if (lowerCase.equals("jump")) {
                            if (MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
                                MinecraftInstance.f362mc.field_71439_g.field_70181_x = this.jumpHeightValue.get().floatValue();
                                break;
                            } else {
                                MinecraftInstance.f362mc.field_71439_g.field_70181_x -= this.downYValue.get().doubleValue();
                                break;
                            }
                        }
                        break;
                    case 92570113:
                        if (lowerCase.equals("aacv4")) {
                            MinecraftInstance.f362mc.field_71439_g.field_70179_y *= 0;
                            MinecraftInstance.f362mc.field_71439_g.field_70159_w *= 0;
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.f362mc.field_71439_g.field_70165_t, MinecraftInstance.f362mc.field_71439_g.field_70163_u + 3.0E-14d, MinecraftInstance.f362mc.field_71439_g.field_70161_v, true));
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.f362mc.field_71439_g.field_70165_t, MinecraftInstance.f362mc.field_71439_g.field_70163_u + 8.0E-15d, MinecraftInstance.f362mc.field_71439_g.field_70161_v, true));
                            break;
                        }
                        break;
                    case 110568525:
                        if (lowerCase.equals("tphop")) {
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.02d, z, false));
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.01d, z, false));
                            MinecraftInstance.f362mc.field_71439_g.func_70107_b(x, y + 0.01d, z);
                            break;
                        }
                        break;
                    case 216546856:
                        if (lowerCase.equals("newpacket")) {
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.05250000001304d, z, true));
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.00150000001304d, z, false));
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.01400000001304d, z, false));
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.00150000001304d, z, false));
                            MinecraftInstance.f362mc.field_71439_g.func_71009_b(targetEntity);
                            break;
                        }
                        break;
                    case 821298052:
                        if (lowerCase.equals("miniphase")) {
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y - 0.0125d, z, false));
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.01275d, z, false));
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y - 2.5E-4d, z, true));
                            break;
                        }
                        break;
                    case 1394468072:
                        if (lowerCase.equals("verussmart")) {
                            this.counter++;
                            if (this.counter == 1) {
                                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.001d, z, true));
                                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, false));
                            }
                            if (this.counter >= 5) {
                                this.counter = 0;
                                break;
                            }
                        }
                        break;
                    case 1959784951:
                        if (lowerCase.equals("invalid")) {
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 1.0E27d, z, false));
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y - 1.0E68d, z, false));
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 1.0E41d, z, false));
                            break;
                        }
                        break;
                    case 2092298300:
                        if (lowerCase.equals("nanopacket")) {
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.00973333333333d, z, false));
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.001d, z, false));
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y - 0.01200000000007d, z, false));
                            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C04PacketPlayerPosition(x, y - 5.0E-4d, z, false));
                            break;
                        }
                        break;
                }
                this.readyCrits = true;
                this.msTimer.reset();
            }
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.onlyAuraValue.get().booleanValue()) {
            Module module = LiquidBounce.INSTANCE.getModuleManager().get(KillAura.class);
            Intrinsics.checkNotNull(module);
            if (!module.getState()) {
                return;
            }
        }
        C03PacketPlayer packet = event.getPacket();
        String lowerCase = this.modeValue.get().toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case 3108362:
                if (lowerCase.equals("edit") && this.readyCrits) {
                    if (packet instanceof C03PacketPlayer) {
                        packet.field_149474_g = false;
                    }
                    this.readyCrits = false;
                    return;
                }
                return;
            case 1083223725:
                if (lowerCase.equals("redesky")) {
                    if (packet instanceof C03PacketPlayer) {
                        C03PacketPlayer packetPlayer = packet;
                        if (MinecraftInstance.f362mc.field_71439_g.field_70122_E && this.canCrits) {
                            packetPlayer.field_149477_b += 1.0E-6d;
                            packetPlayer.field_149474_g = false;
                        }
                        if (MinecraftInstance.f362mc.field_71441_e.func_72945_a(MinecraftInstance.f362mc.field_71439_g, MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().func_72317_d(0.0d, (MinecraftInstance.f362mc.field_71439_g.field_70181_x - 0.08d) * 0.98d, 0.0d).func_72314_b(0.0d, 0.0d, 0.0d)).isEmpty()) {
                            packetPlayer.field_149474_g = true;
                        }
                    }
                    if (packet instanceof C07PacketPlayerDigging) {
                        if (((C07PacketPlayerDigging) packet).func_180762_c() == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK) {
                            this.canCrits = false;
                            return;
                        } else if (((C07PacketPlayerDigging) packet).func_180762_c() == C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK || ((C07PacketPlayerDigging) packet).func_180762_c() == C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK) {
                            this.canCrits = true;
                            return;
                        } else {
                            return;
                        }
                    }
                    return;
                }
                return;
            case 1221776008:
                if (lowerCase.equals("noground") && (packet instanceof C03PacketPlayer)) {
                    packet.field_149474_g = false;
                    return;
                }
                return;
            default:
                return;
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    @Nullable
    public String getTag() {
        return this.modeValue.get();
    }
}
