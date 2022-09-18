package net.ccbluex.liquidbounce.features.module.modules.render;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.extensions.PlayerExtension;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

/* compiled from: NoRender.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��6\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000b\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u0016H\u0007J\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u0011\u0010\b\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\t\u0010\u0006R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u0011\u0010\u000f\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u0010\u0010\u0006R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��¨\u0006\u001b"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/NoRender;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "allValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getAllValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "animalsValue", "armorStandValue", "getArmorStandValue", "autoResetValue", "itemsValue", "maxRenderRange", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "mobsValue", "nameTagsValue", "getNameTagsValue", "playersValue", "onDisable", "", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "shouldStopRender", "", "entity", "Lnet/minecraft/entity/Entity;", "LiquidBounce"})
@ModuleInfo(name = "NoRender", spacedName = "No Render", description = "Increase FPS by decreasing or stop rendering visible entities.", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/NoRender.class */
public final class NoRender extends Module {
    @NotNull
    private final BoolValue allValue = new BoolValue("All", true);
    @NotNull
    private final BoolValue nameTagsValue = new BoolValue("NameTags", true);
    @NotNull
    private final BoolValue itemsValue = new BoolValue("Items", true, new NoRender$itemsValue$1(this));
    @NotNull
    private final BoolValue playersValue = new BoolValue("Players", true, new NoRender$playersValue$1(this));
    @NotNull
    private final BoolValue mobsValue = new BoolValue("Mobs", true, new NoRender$mobsValue$1(this));
    @NotNull
    private final BoolValue animalsValue = new BoolValue("Animals", true, new NoRender$animalsValue$1(this));
    @NotNull
    private final BoolValue armorStandValue = new BoolValue("ArmorStand", true, new NoRender$armorStandValue$1(this));
    @NotNull
    private final BoolValue autoResetValue = new BoolValue("AutoReset", true);
    @NotNull
    private final FloatValue maxRenderRange = new FloatValue("MaxRenderRange", 4.0f, 0.0f, 16.0f, "m");

    @NotNull
    public final BoolValue getAllValue() {
        return this.allValue;
    }

    @NotNull
    public final BoolValue getNameTagsValue() {
        return this.nameTagsValue;
    }

    @NotNull
    public final BoolValue getArmorStandValue() {
        return this.armorStandValue;
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        for (Entity en : MinecraftInstance.f362mc.field_71441_e.field_72996_f) {
            Intrinsics.checkNotNull(en);
            if (shouldStopRender(en)) {
                en.field_70155_l = 0.0d;
            } else if (this.autoResetValue.get().booleanValue()) {
                en.field_70155_l = 1.0d;
            }
        }
    }

    public final boolean shouldStopRender(@NotNull Entity entity) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        if (this.allValue.get().booleanValue() || ((this.itemsValue.get().booleanValue() && (entity instanceof EntityItem)) || ((this.playersValue.get().booleanValue() && (entity instanceof EntityPlayer)) || ((this.mobsValue.get().booleanValue() && EntityUtils.isMob(entity)) || ((this.animalsValue.get().booleanValue() && EntityUtils.isAnimal(entity)) || (this.armorStandValue.get().booleanValue() && (entity instanceof EntityArmorStand))))))) {
            EntityPlayerSP entityPlayerSP = MinecraftInstance.f362mc.field_71439_g;
            Intrinsics.checkNotNull(entityPlayerSP);
            if (!Intrinsics.areEqual(entity, entityPlayerSP)) {
                Entity entity2 = MinecraftInstance.f362mc.field_71439_g;
                Intrinsics.checkNotNull(entity2);
                if (((float) PlayerExtension.getDistanceToEntityBox(entity2, entity)) > this.maxRenderRange.get().floatValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        for (Entity en : MinecraftInstance.f362mc.field_71441_e.field_72996_f) {
            Intrinsics.checkNotNull(en);
            EntityPlayerSP entityPlayerSP = MinecraftInstance.f362mc.field_71439_g;
            Intrinsics.checkNotNull(entityPlayerSP);
            if (!Intrinsics.areEqual(en, entityPlayerSP) && en.field_70155_l <= 0.0d) {
                en.field_70155_l = 1.0d;
            }
        }
    }
}
