package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.BlockStairs;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

/* compiled from: FastStairs.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��2\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��¨\u0006\u0012"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/FastStairs;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "canJump", "", "longJumpValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "tag", "", "getTag", "()Ljava/lang/String;", "walkingDown", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "FastStairs", spacedName = "Fast Stairs", description = "Allows you to climb up stairs faster.", category = ModuleCategory.MOVEMENT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/FastStairs.class */
public final class FastStairs extends Module {
    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Step", "NCP", "AAC3.1.0", "AAC3.3.6", "AAC3.3.13"}, "NCP");
    @NotNull
    private final BoolValue longJumpValue = new BoolValue("LongJump", false);
    private boolean canJump;
    private boolean walkingDown;

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        double d;
        double d2;
        Intrinsics.checkNotNullParameter(event, "event");
        if (MovementUtils.isMoving()) {
            Module module = LiquidBounce.INSTANCE.getModuleManager().get(Speed.class);
            Intrinsics.checkNotNull(module);
            if (module.getState()) {
                return;
            }
            if (MinecraftInstance.f362mc.field_71439_g.field_70143_R > 0.0f && !this.walkingDown) {
                this.walkingDown = true;
            } else if (MinecraftInstance.f362mc.field_71439_g.field_70163_u > MinecraftInstance.f362mc.field_71439_g.field_71096_bN) {
                this.walkingDown = false;
            }
            String mode = this.modeValue.get();
            if (!MinecraftInstance.f362mc.field_71439_g.field_70122_E) {
                return;
            }
            BlockPos blockPos = new BlockPos(MinecraftInstance.f362mc.field_71439_g.field_70165_t, MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().field_72338_b, MinecraftInstance.f362mc.field_71439_g.field_70161_v);
            if ((BlockUtils.getBlock(blockPos) instanceof BlockStairs) && !this.walkingDown) {
                MinecraftInstance.f362mc.field_71439_g.func_70107_b(MinecraftInstance.f362mc.field_71439_g.field_70165_t, MinecraftInstance.f362mc.field_71439_g.field_70163_u + 0.5d, MinecraftInstance.f362mc.field_71439_g.field_70161_v);
                if (StringsKt.equals(mode, "NCP", true)) {
                    d2 = 1.4d;
                } else if (StringsKt.equals(mode, "AAC3.1.0", true)) {
                    d2 = 1.5d;
                } else {
                    d2 = StringsKt.equals(mode, "AAC3.3.13", true) ? 1.2d : 1.0d;
                }
                double motion = d2;
                MinecraftInstance.f362mc.field_71439_g.field_70159_w *= motion;
                MinecraftInstance.f362mc.field_71439_g.field_70179_y *= motion;
            }
            if (BlockUtils.getBlock(blockPos.func_177977_b()) instanceof BlockStairs) {
                if (this.walkingDown) {
                    if (StringsKt.equals(mode, "NCP", true)) {
                        MinecraftInstance.f362mc.field_71439_g.field_70181_x = -1.0d;
                        return;
                    } else if (StringsKt.equals(mode, "AAC3.3.13", true)) {
                        MinecraftInstance.f362mc.field_71439_g.field_70181_x -= 0.014d;
                        return;
                    } else {
                        return;
                    }
                }
                if (StringsKt.equals(mode, "NCP", true)) {
                    d = 1.3d;
                } else if (StringsKt.equals(mode, "AAC3.1.0", true)) {
                    d = 1.3d;
                } else if (StringsKt.equals(mode, "AAC3.3.6", true)) {
                    d = 1.48d;
                } else {
                    d = StringsKt.equals(mode, "AAC3.3.13", true) ? 1.52d : 1.3d;
                }
                double motion2 = d;
                MinecraftInstance.f362mc.field_71439_g.field_70159_w *= motion2;
                MinecraftInstance.f362mc.field_71439_g.field_70179_y *= motion2;
                this.canJump = true;
            } else if (StringsKt.startsWith(mode, "AAC", true) && this.canJump) {
                if (this.longJumpValue.get().booleanValue()) {
                    MinecraftInstance.f362mc.field_71439_g.func_70664_aZ();
                    MinecraftInstance.f362mc.field_71439_g.field_70159_w *= 1.35d;
                    MinecraftInstance.f362mc.field_71439_g.field_70179_y *= 1.35d;
                }
                this.canJump = false;
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    @NotNull
    public String getTag() {
        return this.modeValue.get();
    }
}
