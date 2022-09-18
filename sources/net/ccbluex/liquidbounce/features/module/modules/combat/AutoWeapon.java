package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Tuples;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.item.ItemUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import org.jetbrains.annotations.NotNull;

/* compiled from: AutoWeapon.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\b\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0007J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0010H\u0007J\u0010\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n��¨\u0006\u0014"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/AutoWeapon;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "attackEnemy", "", "silentValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "spoofedSlot", "", "ticksValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "update", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"})
@ModuleInfo(name = "AutoWeapon", spacedName = "Auto Weapon", description = "Automatically selects the best weapon in your hotbar.", category = ModuleCategory.COMBAT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/combat/AutoWeapon.class */
public final class AutoWeapon extends Module {
    @NotNull
    private final BoolValue silentValue = new BoolValue("SpoofItem", false);
    @NotNull
    private final IntegerValue ticksValue = new IntegerValue("SpoofTicks", 10, 1, 20);
    private boolean attackEnemy;
    private int spoofedSlot;

    @EventTarget
    public final void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.attackEnemy = true;
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Object obj;
        int slot;
        Intrinsics.checkNotNullParameter(event, "event");
        if (!(event.getPacket() instanceof C02PacketUseEntity) || event.getPacket().func_149565_c() != C02PacketUseEntity.Action.ATTACK || !this.attackEnemy) {
            return;
        }
        this.attackEnemy = false;
        Iterable $this$map$iv = new IntRange(0, 8);
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        Iterator<Integer> it = $this$map$iv.iterator();
        while (it.hasNext()) {
            int item$iv$iv = ((IntIterator) it).nextInt();
            destination$iv$iv.add(new Tuples(Integer.valueOf(item$iv$iv), MinecraftInstance.f362mc.field_71439_g.field_71071_by.func_70301_a(item$iv$iv)));
        }
        Iterable $this$filter$iv = (List) destination$iv$iv;
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            Tuples it2 = (Tuples) element$iv$iv;
            if (it2.getSecond() != null && ((((ItemStack) it2.getSecond()).func_77973_b() instanceof ItemSword) || (((ItemStack) it2.getSecond()).func_77973_b() instanceof ItemTool))) {
                destination$iv$iv2.add(element$iv$iv);
            }
        }
        Iterable $this$maxByOrNull$iv = (List) destination$iv$iv2;
        Iterator iterator$iv = $this$maxByOrNull$iv.iterator();
        if (!iterator$iv.hasNext()) {
            obj = null;
        } else {
            Object maxElem$iv = iterator$iv.next();
            if (!iterator$iv.hasNext()) {
                obj = maxElem$iv;
            } else {
                Tuples it3 = (Tuples) maxElem$iv;
                Collection collection = ((ItemStack) it3.getSecond()).func_111283_C().get("generic.attackDamage");
                Intrinsics.checkNotNullExpressionValue(collection, "it.second.attributeModif…s[\"generic.attackDamage\"]");
                AttributeModifier attributeModifier = (AttributeModifier) CollectionsKt.first(collection);
                double maxValue$iv = (attributeModifier == null ? 0.0d : attributeModifier.func_111164_d()) + (1.25d * ItemUtils.getEnchantment((ItemStack) it3.getSecond(), Enchantment.field_180314_l));
                do {
                    Object e$iv = iterator$iv.next();
                    Tuples it4 = (Tuples) e$iv;
                    Collection collection2 = ((ItemStack) it4.getSecond()).func_111283_C().get("generic.attackDamage");
                    Intrinsics.checkNotNullExpressionValue(collection2, "it.second.attributeModif…s[\"generic.attackDamage\"]");
                    AttributeModifier attributeModifier2 = (AttributeModifier) CollectionsKt.first(collection2);
                    double v$iv = (attributeModifier2 == null ? 0.0d : attributeModifier2.func_111164_d()) + (1.25d * ItemUtils.getEnchantment((ItemStack) it4.getSecond(), Enchantment.field_180314_l));
                    if (Double.compare(maxValue$iv, v$iv) < 0) {
                        maxElem$iv = e$iv;
                        maxValue$iv = v$iv;
                    }
                } while (iterator$iv.hasNext());
                obj = maxElem$iv;
            }
        }
        Tuples tuples = (Tuples) obj;
        if (tuples == null || (slot = ((Number) tuples.component1()).intValue()) == MinecraftInstance.f362mc.field_71439_g.field_71071_by.field_70461_c) {
            return;
        }
        if (this.silentValue.get().booleanValue()) {
            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C09PacketHeldItemChange(slot));
            this.spoofedSlot = this.ticksValue.get().intValue();
        } else {
            MinecraftInstance.f362mc.field_71439_g.field_71071_by.field_70461_c = slot;
            MinecraftInstance.f362mc.field_71442_b.func_78765_e();
        }
        MinecraftInstance.f362mc.func_147114_u().func_147297_a(event.getPacket());
        event.cancelEvent();
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent update) {
        Intrinsics.checkNotNullParameter(update, "update");
        if (this.spoofedSlot > 0) {
            if (this.spoofedSlot == 1) {
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C09PacketHeldItemChange(MinecraftInstance.f362mc.field_71439_g.field_71071_by.field_70461_c));
            }
            this.spoofedSlot--;
        }
    }
}
