package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.injection.access.StaticStorage;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;

/* compiled from: Ignite.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��¨\u0006\f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/Ignite;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "lavaBucketValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "lighterValue", "msTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "Ignite", description = "Automatically sets targets around you on fire.", category = ModuleCategory.COMBAT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/combat/Ignite.class */
public final class Ignite extends Module {
    @NotNull
    private final BoolValue lighterValue = new BoolValue("Lighter", true);
    @NotNull
    private final BoolValue lavaBucketValue = new BoolValue("Lava", true);
    @NotNull
    private final MSTimer msTimer = new MSTimer();

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.msTimer.hasTimePassed(500L)) {
            return;
        }
        int lighterInHotbar = this.lighterValue.get().booleanValue() ? InventoryUtils.findItem(36, 45, Items.field_151033_d) : -1;
        int lavaInHotbar = this.lavaBucketValue.get().booleanValue() ? InventoryUtils.findItem(36, 45, Items.field_151129_at) : -1;
        if (lighterInHotbar == -1 && lavaInHotbar == -1) {
            return;
        }
        int fireInHotbar = lighterInHotbar != -1 ? lighterInHotbar : lavaInHotbar;
        for (Entity entity : MinecraftInstance.f362mc.field_71441_e.field_72996_f) {
            if (EntityUtils.isSelected(entity, true) && !entity.func_70027_ad()) {
                BlockPos blockPos = entity.func_180425_c();
                if (MinecraftInstance.f362mc.field_71439_g.func_174818_b(blockPos) < 22.3d && BlockUtils.isReplaceable(blockPos) && (BlockUtils.getBlock(blockPos) instanceof BlockAir)) {
                    RotationUtils.keepCurrentRotation = true;
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C09PacketHeldItemChange(fireInHotbar - 36));
                    ItemStack itemStack = MinecraftInstance.f362mc.field_71439_g.field_71069_bz.func_75139_a(fireInHotbar).func_75211_c();
                    if (itemStack == null) {
                        return;
                    }
                    Item func_77973_b = itemStack.func_77973_b();
                    Intrinsics.checkNotNull(func_77973_b);
                    if (func_77973_b instanceof ItemBucket) {
                        double diffX = (blockPos.func_177958_n() + 0.5d) - MinecraftInstance.f362mc.field_71439_g.field_70165_t;
                        double diffY = (blockPos.func_177956_o() + 0.5d) - (MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().field_72338_b + MinecraftInstance.f362mc.field_71439_g.eyeHeight);
                        double diffZ = (blockPos.func_177952_p() + 0.5d) - MinecraftInstance.f362mc.field_71439_g.field_70161_v;
                        double sqrtz = Math.sqrt((diffX * diffX) + (diffZ * diffZ));
                        float yaw = ((float) ((Math.atan2(diffZ, diffX) * 180.0d) / 3.141592653589793d)) - 90.0f;
                        float pitch = -((float) ((Math.atan2(diffY, sqrtz) * 180.0d) / 3.141592653589793d));
                        MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C05PacketPlayerLook(MinecraftInstance.f362mc.field_71439_g.field_70177_z + MathHelper.func_76142_g(yaw - MinecraftInstance.f362mc.field_71439_g.field_70177_z), MinecraftInstance.f362mc.field_71439_g.field_70125_A + MathHelper.func_76142_g(pitch - MinecraftInstance.f362mc.field_71439_g.field_70125_A), MinecraftInstance.f362mc.field_71439_g.field_70122_E));
                        MinecraftInstance.f362mc.field_71442_b.func_78769_a(MinecraftInstance.f362mc.field_71439_g, MinecraftInstance.f362mc.field_71441_e, itemStack);
                    } else {
                        EnumFacing[] facings = StaticStorage.facings();
                        Intrinsics.checkNotNullExpressionValue(facings, "facings()");
                        int i = 0;
                        int length = facings.length;
                        while (true) {
                            if (i >= length) {
                                break;
                            }
                            EnumFacing side = facings[i];
                            i++;
                            BlockPos neighbor = blockPos.func_177972_a(side);
                            if (BlockUtils.canBeClicked(neighbor)) {
                                double diffX2 = (neighbor.func_177958_n() + 0.5d) - MinecraftInstance.f362mc.field_71439_g.field_70165_t;
                                double diffY2 = (neighbor.func_177956_o() + 0.5d) - (MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().field_72338_b + MinecraftInstance.f362mc.field_71439_g.eyeHeight);
                                double diffZ2 = (neighbor.func_177952_p() + 0.5d) - MinecraftInstance.f362mc.field_71439_g.field_70161_v;
                                double sqrtz2 = Math.sqrt((diffX2 * diffX2) + (diffZ2 * diffZ2));
                                float yaw2 = ((float) ((Math.atan2(diffZ2, diffX2) * 180.0d) / 3.141592653589793d)) - 90.0f;
                                float pitch2 = -((float) ((Math.atan2(diffY2, sqrtz2) * 180.0d) / 3.141592653589793d));
                                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C05PacketPlayerLook(MinecraftInstance.f362mc.field_71439_g.field_70177_z + MathHelper.func_76142_g(yaw2 - MinecraftInstance.f362mc.field_71439_g.field_70177_z), MinecraftInstance.f362mc.field_71439_g.field_70125_A + MathHelper.func_76142_g(pitch2 - MinecraftInstance.f362mc.field_71439_g.field_70125_A), MinecraftInstance.f362mc.field_71439_g.field_70122_E));
                                if (MinecraftInstance.f362mc.field_71442_b.func_178890_a(MinecraftInstance.f362mc.field_71439_g, MinecraftInstance.f362mc.field_71441_e, itemStack, neighbor, side.func_176734_d(), new Vec3(side.func_176730_m()))) {
                                    MinecraftInstance.f362mc.field_71439_g.func_71038_i();
                                    break;
                                }
                            }
                        }
                    }
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C09PacketHeldItemChange(MinecraftInstance.f362mc.field_71439_g.field_71071_by.field_70461_c));
                    RotationUtils.keepCurrentRotation = false;
                    MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C05PacketPlayerLook(MinecraftInstance.f362mc.field_71439_g.field_70177_z, MinecraftInstance.f362mc.field_71439_g.field_70125_A, MinecraftInstance.f362mc.field_71439_g.field_70122_E));
                    this.msTimer.reset();
                    return;
                }
            }
        }
    }
}
