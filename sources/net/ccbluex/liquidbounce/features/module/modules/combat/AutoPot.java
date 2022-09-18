package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.world.Scaffold;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.InventoryHelper;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.apache.log4j.Level;
import org.jetbrains.annotations.NotNull;

/* compiled from: AutoPot.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n��\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020\u001dH\u0002J\u0018\u0010*\u001a\u00020\u00112\u0006\u0010+\u001a\u00020\u00112\u0006\u0010,\u001a\u00020\u0011H\u0002J\u0010\u0010-\u001a\u00020\u00152\u0006\u0010.\u001a\u00020\u0011H\u0002J\u0018\u0010/\u001a\n\u0012\u0004\u0012\u000201\u0018\u0001002\u0006\u0010.\u001a\u00020\u0011H\u0002J\u0010\u00102\u001a\u00020\u00152\u0006\u00103\u001a\u00020\u0011H\u0002J\b\u00104\u001a\u00020(H\u0016J\b\u00105\u001a\u00020(H\u0016J\u0010\u00106\u001a\u00020(2\u0006\u00107\u001a\u000208H\u0007J\u0010\u00109\u001a\u00020(2\u0006\u00107\u001a\u000208H\u0007J\u0010\u0010:\u001a\u00020(2\u0006\u00107\u001a\u00020;H\u0007J\b\u0010<\u001a\u00020(H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0012\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0013\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.¢\u0006\u0002\n��R\u000e\u0010\u0018\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0019\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001a\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u0014\u0010\u001c\u001a\u00020\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u001e\u0010 \u001a\u0012\u0012\u0004\u0012\u00020\u00110!j\b\u0012\u0004\u0012\u00020\u0011`\"X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010#\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010$\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010%\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010&\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��¨\u0006="}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/AutoPot;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "debugValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "healthValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "invTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "killAura", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/KillAura;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "noCombatValue", "potIndex", "", "regenValue", "resetTimer", "rotated", "", "scaffold", "Lnet/ccbluex/liquidbounce/features/module/modules/world/Scaffold;", "smartTimeoutValue", "smartValue", "spoofDelayValue", "spoofInvValue", "tag", "", "getTag", "()Ljava/lang/String;", "throwQueue", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "throwTimer", "throwing", "timeoutTimer", "utilityValue", "debug", "", "s", "findPotion", "startSlot", "endSlot", "findSinglePotion", "slot", "getPotionFromSlot", "", "Lnet/minecraft/potion/PotionEffect;", "isUsefulPotion", "id", "onEnable", "onInitialize", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMotionPost", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "resetAll", "LiquidBounce"})
@ModuleInfo(name = "AutoPot", spacedName = "Auto Pot", description = "Automatically throw pots for you.", category = ModuleCategory.COMBAT)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/combat/AutoPot.class */
public final class AutoPot extends Module {
    private boolean throwing;
    private boolean rotated;
    private KillAura killAura;
    private Scaffold scaffold;
    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Jump", "Floor"}, "Floor");
    @NotNull
    private final FloatValue healthValue = new FloatValue("Health", 75.0f, 0.0f, 100.0f, "%");
    @NotNull
    private final IntegerValue delayValue = new IntegerValue("Delay", 500, 500, (int) Level.TRACE_INT, "ms");
    @NotNull
    private final BoolValue regenValue = new BoolValue("Heal", true);
    @NotNull
    private final BoolValue utilityValue = new BoolValue("Utility", true);
    @NotNull
    private final BoolValue smartValue = new BoolValue("Smart", true);
    @NotNull
    private final IntegerValue smartTimeoutValue = new IntegerValue("SmartTimeout", 2000, 500, Level.TRACE_INT, "ms", new AutoPot$smartTimeoutValue$1(this));
    @NotNull
    private final BoolValue spoofInvValue = new BoolValue("InvSpoof", false);
    @NotNull
    private final IntegerValue spoofDelayValue = new IntegerValue("InvDelay", 500, 500, Level.TRACE_INT, "ms", new AutoPot$spoofDelayValue$1(this));
    @NotNull
    private final BoolValue noCombatValue = new BoolValue("NoCombat", true);
    @NotNull
    private final BoolValue debugValue = new BoolValue("Debug", false);
    private int potIndex = -1;
    @NotNull
    private MSTimer throwTimer = new MSTimer();
    @NotNull
    private MSTimer resetTimer = new MSTimer();
    @NotNull
    private MSTimer invTimer = new MSTimer();
    @NotNull
    private MSTimer timeoutTimer = new MSTimer();
    @NotNull
    private final ArrayList<Integer> throwQueue = new ArrayList<>();

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onInitialize() {
        Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(KillAura.class);
        Intrinsics.checkNotNull(module);
        this.killAura = (KillAura) module;
        Module module2 = LiquidBounce.INSTANCE.getModuleManager().getModule(Scaffold.class);
        Intrinsics.checkNotNull(module2);
        this.scaffold = (Scaffold) module2;
    }

    private final void resetAll() {
        this.throwing = false;
        this.rotated = false;
        this.throwTimer.reset();
        this.resetTimer.reset();
        this.timeoutTimer.reset();
        this.invTimer.reset();
        this.throwQueue.clear();
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        resetAll();
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        resetAll();
    }

    private final void debug(String s) {
        if (this.debugValue.get().booleanValue()) {
            ClientUtils.displayChatMessage(Intrinsics.stringPlus("[AutoPot] ", s));
        }
    }

    @EventTarget(priority = 2)
    public final void onMotion(@NotNull MotionEvent event) {
        int potion;
        int invPotion;
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getEventState() == EventState.PRE) {
            if (this.smartValue.get().booleanValue() && !this.throwQueue.isEmpty()) {
                boolean foundPot = false;
                int size = this.throwQueue.size() - 1;
                if (0 <= size) {
                    do {
                        int k = size;
                        size--;
                        EntityPlayerSP entityPlayerSP = MinecraftInstance.f362mc.field_71439_g;
                        Integer num = this.throwQueue.get(k);
                        Intrinsics.checkNotNullExpressionValue(num, "throwQueue[k]");
                        if (entityPlayerSP.func_82165_m(num.intValue())) {
                            this.throwQueue.remove(k);
                            this.timeoutTimer.reset();
                            foundPot = true;
                        }
                    } while (0 <= size);
                    if (!foundPot && this.timeoutTimer.hasTimePassed(this.smartTimeoutValue.get().intValue())) {
                        debug("reached timeout, clearing queue");
                        this.throwQueue.clear();
                        this.timeoutTimer.reset();
                    }
                } else if (!foundPot) {
                    debug("reached timeout, clearing queue");
                    this.throwQueue.clear();
                    this.timeoutTimer.reset();
                }
            } else {
                this.timeoutTimer.reset();
            }
            if (this.spoofInvValue.get().booleanValue() && !(MinecraftInstance.f362mc.field_71462_r instanceof GuiContainer) && !this.throwing) {
                if (this.invTimer.hasTimePassed(this.spoofDelayValue.get().intValue()) && (invPotion = findPotion(9, 36)) != -1) {
                    if (InventoryUtils.hasSpaceHotbar()) {
                        InventoryHelper.INSTANCE.openPacket();
                        MinecraftInstance.f362mc.field_71442_b.func_78753_a(0, invPotion, 0, 1, MinecraftInstance.f362mc.field_71439_g);
                        InventoryHelper.INSTANCE.closePacket();
                    } else {
                        int i = 36;
                        while (true) {
                            if (i >= 45) {
                                break;
                            }
                            int i2 = i;
                            i++;
                            ItemStack stack = MinecraftInstance.f362mc.field_71439_g.field_71069_bz.func_75139_a(i2).func_75211_c();
                            if (stack != null && (stack.func_77973_b() instanceof ItemPotion) && ItemPotion.func_77831_g(stack.func_77952_i())) {
                                InventoryHelper.INSTANCE.openPacket();
                                MinecraftInstance.f362mc.field_71442_b.func_78753_a(0, invPotion, 0, 0, MinecraftInstance.f362mc.field_71439_g);
                                MinecraftInstance.f362mc.field_71442_b.func_78753_a(0, i2, 0, 0, MinecraftInstance.f362mc.field_71439_g);
                                InventoryHelper.INSTANCE.closePacket();
                                break;
                            }
                        }
                    }
                    this.invTimer.reset();
                    debug("moved pot");
                    return;
                }
            } else {
                this.invTimer.reset();
            }
            if (!(MinecraftInstance.f362mc.field_71462_r instanceof GuiContainer) && !this.throwing && this.throwTimer.hasTimePassed(this.delayValue.get().intValue()) && (potion = findPotion(36, 45)) != -1) {
                this.potIndex = potion;
                this.throwing = true;
                debug("found pot, queueing");
            }
            if (!this.throwing || (MinecraftInstance.f362mc.field_71462_r instanceof GuiContainer)) {
                return;
            }
            KillAura killAura = this.killAura;
            if (killAura == null) {
                Intrinsics.throwUninitializedPropertyAccessException("killAura");
                killAura = null;
            }
            if (killAura.getState()) {
                KillAura killAura2 = this.killAura;
                if (killAura2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("killAura");
                    killAura2 = null;
                }
                if (killAura2.getTarget() != null) {
                    return;
                }
            }
            Scaffold scaffold = this.scaffold;
            if (scaffold == null) {
                Intrinsics.throwUninitializedPropertyAccessException("scaffold");
                scaffold = null;
            }
            if (!scaffold.getState()) {
                if (MinecraftInstance.f362mc.field_71439_g.field_70122_E && StringsKt.equals(this.modeValue.get(), "jump", true)) {
                    MinecraftInstance.f362mc.field_71439_g.func_70664_aZ();
                    debug("jumped");
                }
                RotationUtils.reset();
                event.setPitch(90.0f);
                debug("silent rotation");
            }
        }
    }

    @EventTarget(priority = -1)
    public final void onMotionPost(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getEventState() == EventState.POST && this.throwing && !(MinecraftInstance.f362mc.field_71462_r instanceof GuiContainer)) {
            if ((MinecraftInstance.f362mc.field_71439_g.field_70122_E && StringsKt.equals(this.modeValue.get(), "floor", true)) || (!MinecraftInstance.f362mc.field_71439_g.field_70122_E && StringsKt.equals(this.modeValue.get(), "jump", true))) {
                if (this.noCombatValue.get().booleanValue()) {
                    KillAura killAura = this.killAura;
                    if (killAura == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("killAura");
                        killAura = null;
                    }
                    if (killAura.getState()) {
                        KillAura killAura2 = this.killAura;
                        if (killAura2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("killAura");
                            killAura2 = null;
                        }
                        if (killAura2.getTarget() != null) {
                            return;
                        }
                    }
                }
                Scaffold scaffold = this.scaffold;
                if (scaffold == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("scaffold");
                    scaffold = null;
                }
                if (!scaffold.getState()) {
                    Iterable potionEffects = getPotionFromSlot(this.potIndex);
                    if (potionEffects != null) {
                        Iterable $this$map$iv = potionEffects;
                        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                        for (Object item$iv$iv : $this$map$iv) {
                            PotionEffect it = (PotionEffect) item$iv$iv;
                            destination$iv$iv.add(Integer.valueOf(it.func_76456_a()));
                        }
                        Iterable potionIds = (List) destination$iv$iv;
                        if (this.smartValue.get().booleanValue()) {
                            Iterable $this$filter$iv = potionIds;
                            Collection destination$iv$iv2 = new ArrayList();
                            for (Object element$iv$iv : $this$filter$iv) {
                                int it2 = ((Number) element$iv$iv).intValue();
                                if (!this.throwQueue.contains(Integer.valueOf(it2))) {
                                    destination$iv$iv2.add(element$iv$iv);
                                }
                            }
                            Iterable $this$forEach$iv = (List) destination$iv$iv2;
                            for (Object element$iv : $this$forEach$iv) {
                                int it3 = ((Number) element$iv).intValue();
                                this.throwQueue.add(Integer.valueOf(it3));
                            }
                        }
                        MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C09PacketHeldItemChange(this.potIndex - 36));
                        MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C08PacketPlayerBlockPlacement(MinecraftInstance.f362mc.field_71439_g.func_70694_bm()));
                        MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C09PacketHeldItemChange(MinecraftInstance.f362mc.field_71439_g.field_71071_by.field_70461_c));
                        this.potIndex = -1;
                        this.throwing = false;
                        this.throwTimer.reset();
                        debug("thrown");
                        return;
                    }
                    this.potIndex = -1;
                    this.throwing = false;
                    debug("failed to retrieve potion info, retrying...");
                }
            }
        }
    }

    private final int findPotion(int startSlot, int endSlot) {
        int i = startSlot;
        while (i < endSlot) {
            int i2 = i;
            i++;
            if (findSinglePotion(i2)) {
                return i2;
            }
        }
        return -1;
    }

    private final List<PotionEffect> getPotionFromSlot(int slot) {
        ItemStack stack = MinecraftInstance.f362mc.field_71439_g.field_71069_bz.func_75139_a(slot).func_75211_c();
        if (stack == null || !(stack.func_77973_b() instanceof ItemPotion) || !ItemPotion.func_77831_g(stack.func_77952_i())) {
            return null;
        }
        ItemPotion func_77973_b = stack.func_77973_b();
        if (func_77973_b == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemPotion");
        }
        ItemPotion itemPotion = func_77973_b;
        return itemPotion.func_77832_l(stack);
    }

    private final boolean findSinglePotion(int slot) {
        ItemStack stack = MinecraftInstance.f362mc.field_71439_g.field_71069_bz.func_75139_a(slot).func_75211_c();
        if (stack == null || !(stack.func_77973_b() instanceof ItemPotion) || !ItemPotion.func_77831_g(stack.func_77952_i())) {
            return false;
        }
        ItemPotion func_77973_b = stack.func_77973_b();
        if (func_77973_b == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemPotion");
        }
        ItemPotion itemPotion = func_77973_b;
        if ((MinecraftInstance.f362mc.field_71439_g.func_110143_aJ() / MinecraftInstance.f362mc.field_71439_g.func_110138_aP()) * 100.0f < this.healthValue.get().floatValue() && this.regenValue.get().booleanValue()) {
            for (PotionEffect potionEffect : itemPotion.func_77832_l(stack)) {
                if (potionEffect.func_76456_a() == Potion.field_76432_h.field_76415_H) {
                    return true;
                }
            }
            if (MinecraftInstance.f362mc.field_71439_g.func_70644_a(Potion.field_76428_l)) {
                return false;
            }
            if (!this.smartValue.get().booleanValue() || !this.throwQueue.contains(Integer.valueOf(Potion.field_76428_l.field_76415_H))) {
                for (PotionEffect potionEffect2 : itemPotion.func_77832_l(stack)) {
                    if (potionEffect2.func_76456_a() == Potion.field_76428_l.field_76415_H) {
                        return true;
                    }
                }
                return false;
            }
            return false;
        } else if (this.utilityValue.get().booleanValue()) {
            for (PotionEffect potionEffect3 : itemPotion.func_77832_l(stack)) {
                if (isUsefulPotion(potionEffect3.func_76456_a())) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    private final boolean isUsefulPotion(int id) {
        return (id == Potion.field_76428_l.field_76415_H || id == Potion.field_76432_h.field_76415_H || id == Potion.field_76436_u.field_76415_H || id == Potion.field_76440_q.field_76415_H || id == Potion.field_76433_i.field_76415_H || id == Potion.field_82731_v.field_76415_H || id == Potion.field_76419_f.field_76415_H || id == Potion.field_76421_d.field_76415_H || id == Potion.field_76437_t.field_76415_H || MinecraftInstance.f362mc.field_71439_g.func_82165_m(id) || (this.smartValue.get().booleanValue() && this.throwQueue.contains(Integer.valueOf(id)))) ? false : true;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    @NotNull
    public String getTag() {
        return String.valueOf(StringsKt.equals(this.modeValue.get(), "Jump", true) ? "Jump Only" : "Floor");
    }
}
