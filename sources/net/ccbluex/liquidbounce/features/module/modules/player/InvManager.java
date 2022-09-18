package net.ccbluex.liquidbounce.features.module.modules.player;

import com.viaversion.viaversion.libs.javassist.compiler.TokenId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.injection.implementations.IItemStack;
import net.ccbluex.liquidbounce.utils.InventoryHelper;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.item.ArmorPart;
import net.ccbluex.liquidbounce.utils.item.ItemHelper;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import org.apache.log4j.Level;
import org.jetbrains.annotations.NotNull;

/* compiled from: InvManager.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010%\n\u0002\u0010\b\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0015\u0010:\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004H\u0002¢\u0006\u0002\u0010;J!\u0010<\u001a\u0004\u0018\u00010\u00102\u0006\u0010=\u001a\u00020\u00102\b\u0010>\u001a\u0004\u0018\u00010\u0011H\u0002¢\u0006\u0002\u0010?J\b\u0010@\u001a\u00020AH\u0002J\u0016\u0010B\u001a\u0002062\u0006\u0010C\u001a\u00020\u00112\u0006\u0010D\u001a\u00020\u0010J(\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000f2\b\b\u0002\u0010E\u001a\u00020\u00102\b\b\u0002\u0010F\u001a\u00020\u0010H\u0002J\u0016\u0010G\u001a\u0002062\u0006\u0010H\u001a\u00020\u00102\u0006\u0010I\u001a\u000206J\b\u0010J\u001a\u00020AH\u0016J\u0010\u0010K\u001a\u00020A2\u0006\u0010L\u001a\u00020MH\u0007J\u0010\u0010N\u001a\u00020A2\u0006\u0010L\u001a\u00020OH\u0007J\u0010\u0010P\u001a\u00020A2\u0006\u0010L\u001a\u00020QH\u0007J\u0006\u0010R\u001a\u00020AJ\b\u0010S\u001a\u00020AH\u0002J\u0010\u0010T\u001a\u00020\u001e2\u0006\u0010=\u001a\u00020\u0010H\u0002R\u0018\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u001a\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000fX\u0082\u000e¢\u0006\u0002\n��R\u0014\u0010\u0012\u001a\u00020\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0017\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0018\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0019\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001a\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n��R\u0016\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u0004X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001fR\u000e\u0010 \u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010!\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\"\u001a\u00020#X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010$\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010%\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010&\u001a\u00020#X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010'\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010(\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010)\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010*\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010+\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010,\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010-\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010.\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010/\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u00100\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u00101\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u00102\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u00103\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u00104\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u001e\u00107\u001a\u0002062\u0006\u00105\u001a\u000206@BX\u0082\u000e¢\u0006\b\n��\"\u0004\b8\u00109¨\u0006U"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/InvManager;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "armorQueue", "", "Lnet/ccbluex/liquidbounce/utils/item/ArmorPart;", "[Lnet/ccbluex/liquidbounce/utils/item/ArmorPart;", "armorsValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "cleanGarbageValue", "delay", "", "eventModeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "garbageQueue", "", "", "Lnet/minecraft/item/ItemStack;", "goal", "Lnet/ccbluex/liquidbounce/utils/item/ItemHelper$EnumNBTPriorityType;", "getGoal", "()Lnet/ccbluex/liquidbounce/utils/item/ItemHelper$EnumNBTPriorityType;", "hotbarValue", "ignoreVehiclesValue", "invOpenValue", "invSpoof", "invSpoofOld", "itemDelayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "items", "", "[Ljava/lang/String;", "maxDelayValue", "minDelayValue", "nbtArmorPriority", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "nbtGoalValue", "nbtItemNotGarbage", "nbtWeaponPriority", "noMoveValue", "noScaffoldValue", "onlyPositivePotionValue", "randomSlotValue", "sortSlot1Value", "sortSlot2Value", "sortSlot3Value", "sortSlot4Value", "sortSlot5Value", "sortSlot6Value", "sortSlot7Value", "sortSlot8Value", "sortSlot9Value", "sortValue", "value", "", "spoofInventory", "setSpoofInventory", "(Z)V", "findBestArmor", "()[Lnet/ccbluex/liquidbounce/utils/item/ArmorPart;", "findBetterItem", "targetSlot", "slotStack", "(ILnet/minecraft/item/ItemStack;)Ljava/lang/Integer;", "findQueueItems", "", "isUseful", "itemStack", "slot", "start", AsmConstants.END, "move", "item", "isArmorSlot", "onEnable", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "performManager", "sortHotbar", "type", "LiquidBounce"})
@ModuleInfo(name = "InvManager", spacedName = "Inv Manager", description = "Automatically throws away useless items, and also equips armors for you.", category = ModuleCategory.PLAYER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/player/InvManager.class */
public final class InvManager extends Module {
    @NotNull
    private final IntegerValue maxDelayValue = new IntegerValue() { // from class: net.ccbluex.liquidbounce.features.module.modules.player.InvManager$maxDelayValue$1
        /* JADX INFO: Access modifiers changed from: package-private */
        {
            super("MaxDelay", 600, 0, 1000, "ms");
        }

        @Override // net.ccbluex.liquidbounce.value.Value
        public /* bridge */ /* synthetic */ void onChanged(Integer num, Integer num2) {
            onChanged(num.intValue(), num2.intValue());
        }

        protected void onChanged(int oldValue, int newValue) {
            IntegerValue integerValue;
            integerValue = InvManager.this.minDelayValue;
            int minCPS = integerValue.get().intValue();
            if (minCPS > newValue) {
                set((InvManager$maxDelayValue$1) Integer.valueOf(minCPS));
            }
        }
    };
    @NotNull
    private final IntegerValue minDelayValue = new IntegerValue() { // from class: net.ccbluex.liquidbounce.features.module.modules.player.InvManager$minDelayValue$1
        /* JADX INFO: Access modifiers changed from: package-private */
        {
            super("MinDelay", (int) TokenId.Identifier, 0, 1000, "ms");
        }

        @Override // net.ccbluex.liquidbounce.value.Value
        public /* bridge */ /* synthetic */ void onChanged(Integer num, Integer num2) {
            onChanged(num.intValue(), num2.intValue());
        }

        protected void onChanged(int oldValue, int newValue) {
            IntegerValue integerValue;
            integerValue = InvManager.this.maxDelayValue;
            int maxDelay = integerValue.get().intValue();
            if (maxDelay < newValue) {
                set((InvManager$minDelayValue$1) Integer.valueOf(maxDelay));
            }
        }
    };
    @NotNull
    private final ListValue eventModeValue = new ListValue("OnEvent", new String[]{"Update", "MotionPre", "MotionPost"}, "Update");
    @NotNull
    private final BoolValue invOpenValue = new BoolValue("InvOpen", false);
    @NotNull
    private final BoolValue invSpoof = new BoolValue("InvSpoof", true);
    @NotNull
    private final BoolValue invSpoofOld = new BoolValue("InvSpoof-Old", false, new InvManager$invSpoofOld$1(this));
    @NotNull
    private final BoolValue armorsValue = new BoolValue("WearArmors", true);
    @NotNull
    private final BoolValue noMoveValue = new BoolValue("NoMove", false);
    @NotNull
    private final BoolValue noScaffoldValue = new BoolValue("NoScaffold", true);
    @NotNull
    private final BoolValue hotbarValue = new BoolValue("Hotbar", true);
    @NotNull
    private final BoolValue randomSlotValue = new BoolValue("RandomSlot", false);
    @NotNull
    private final BoolValue sortValue = new BoolValue("Sort", true);
    @NotNull
    private final BoolValue cleanGarbageValue = new BoolValue("CleanGarbage", true);
    @NotNull
    private final IntegerValue itemDelayValue = new IntegerValue("ItemDelay", 0, 0, (int) Level.TRACE_INT, "ms");
    @NotNull
    private final BoolValue ignoreVehiclesValue = new BoolValue("IgnoreVehicles", false);
    @NotNull
    private final BoolValue onlyPositivePotionValue = new BoolValue("OnlyPositivePotion", false);
    @NotNull
    private final ListValue nbtGoalValue;
    @NotNull
    private final BoolValue nbtItemNotGarbage;
    @NotNull
    private final FloatValue nbtArmorPriority;
    @NotNull
    private final FloatValue nbtWeaponPriority;
    @NotNull
    private final String[] items;
    @NotNull
    private final ListValue sortSlot1Value;
    @NotNull
    private final ListValue sortSlot2Value;
    @NotNull
    private final ListValue sortSlot3Value;
    @NotNull
    private final ListValue sortSlot4Value;
    @NotNull
    private final ListValue sortSlot5Value;
    @NotNull
    private final ListValue sortSlot6Value;
    @NotNull
    private final ListValue sortSlot7Value;
    @NotNull
    private final ListValue sortSlot8Value;
    @NotNull
    private final ListValue sortSlot9Value;
    @NotNull
    private Map<Integer, ItemStack> garbageQueue;
    @NotNull
    private ArmorPart[] armorQueue;
    private boolean spoofInventory;
    private long delay;

    public InvManager() {
        ItemHelper.EnumNBTPriorityType[] values = ItemHelper.EnumNBTPriorityType.values();
        Collection destination$iv$iv = new ArrayList(values.length);
        int i = 0;
        int length = values.length;
        while (i < length) {
            i++;
            destination$iv$iv.add(values[i].toString());
        }
        Collection $this$toTypedArray$iv = (List) destination$iv$iv;
        Object[] array = $this$toTypedArray$iv.toArray(new String[0]);
        if (array == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        }
        this.nbtGoalValue = new ListValue("NBTGoal", (String[]) array, "NONE");
        this.nbtItemNotGarbage = new BoolValue("NBTItemNotGarbage", true, new InvManager$nbtItemNotGarbage$1(this));
        this.nbtArmorPriority = new FloatValue("NBTArmorPriority", 0.0f, 0.0f, 5.0f, new InvManager$nbtArmorPriority$1(this));
        this.nbtWeaponPriority = new FloatValue("NBTWeaponPriority", 0.0f, 0.0f, 5.0f, new InvManager$nbtWeaponPriority$1(this));
        this.items = new String[]{"None", "Ignore", "Sword", "Bow", "Pickaxe", "Axe", "Food", "Block", "Water", "Gapple", "Pearl", "Potion"};
        this.sortSlot1Value = new ListValue("SortSlot-1", this.items, "Sword", new InvManager$sortSlot1Value$1(this));
        this.sortSlot2Value = new ListValue("SortSlot-2", this.items, "Gapple", new InvManager$sortSlot2Value$1(this));
        this.sortSlot3Value = new ListValue("SortSlot-3", this.items, "Potion", new InvManager$sortSlot3Value$1(this));
        this.sortSlot4Value = new ListValue("SortSlot-4", this.items, "Pickaxe", new InvManager$sortSlot4Value$1(this));
        this.sortSlot5Value = new ListValue("SortSlot-5", this.items, "Axe", new InvManager$sortSlot5Value$1(this));
        this.sortSlot6Value = new ListValue("SortSlot-6", this.items, "None", new InvManager$sortSlot6Value$1(this));
        this.sortSlot7Value = new ListValue("SortSlot-7", this.items, "Block", new InvManager$sortSlot7Value$1(this));
        this.sortSlot8Value = new ListValue("SortSlot-8", this.items, "Pearl", new InvManager$sortSlot8Value$1(this));
        this.sortSlot9Value = new ListValue("SortSlot-9", this.items, "Food", new InvManager$sortSlot9Value$1(this));
        this.garbageQueue = new LinkedHashMap();
        this.armorQueue = new ArmorPart[0];
    }

    private final ItemHelper.EnumNBTPriorityType getGoal() {
        return ItemHelper.EnumNBTPriorityType.valueOf(this.nbtGoalValue.get());
    }

    private final void setSpoofInventory(boolean value) {
        if (value != this.spoofInventory && !this.invOpenValue.get().booleanValue()) {
            if (value) {
                InventoryHelper.INSTANCE.openPacket();
            } else {
                InventoryHelper.INSTANCE.closePacket();
            }
        }
        this.spoofInventory = value;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        if (this.invSpoof.get().booleanValue() && !this.invSpoofOld.get().booleanValue()) {
            setSpoofInventory(false);
        }
        this.garbageQueue.clear();
        this.armorQueue = new ArmorPart[0];
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.invSpoof.get().booleanValue() && !this.invSpoofOld.get().booleanValue()) {
            setSpoofInventory(false);
        }
        this.garbageQueue.clear();
        this.armorQueue = new ArmorPart[0];
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (StringsKt.equals(this.eventModeValue.get(), "update", true)) {
            performManager();
        }
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (StringsKt.equals(this.eventModeValue.get(), Intrinsics.stringPlus("motion", event.getEventState().getStateName()), true)) {
            performManager();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:91:0x02a7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void performManager() {
        /*
            Method dump skipped, instructions count: 897
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.features.module.modules.player.InvManager.performManager():void");
    }

    private final void sortHotbar() {
        int i = 0;
        while (i < 9) {
            int index = i;
            i++;
            Integer findBetterItem = findBetterItem(index, MinecraftInstance.f362mc.field_71439_g.field_71071_by.func_70301_a(index));
            if (findBetterItem != null) {
                int bestItem = findBetterItem.intValue();
                if (bestItem != index) {
                    boolean openInventory = !(MinecraftInstance.f362mc.field_71462_r instanceof GuiInventory) && this.invSpoof.get().booleanValue() && this.invSpoofOld.get().booleanValue();
                    if (openInventory) {
                        InventoryHelper.INSTANCE.openPacket();
                    }
                    MinecraftInstance.f362mc.field_71442_b.func_78753_a(0, bestItem < 9 ? bestItem + 36 : bestItem, index, 2, MinecraftInstance.f362mc.field_71439_g);
                    if (openInventory) {
                        InventoryHelper.INSTANCE.closePacket();
                    }
                    this.delay = TimeUtils.randomDelay(this.minDelayValue.get().intValue(), this.maxDelayValue.get().intValue());
                    return;
                }
            }
        }
    }

    private final void findQueueItems() {
        this.garbageQueue.clear();
        Map $this$filter$iv = items(9, 45);
        Map destination$iv$iv = new LinkedHashMap();
        for (Map.Entry element$iv$iv : $this$filter$iv.entrySet()) {
            if (!isUseful(element$iv$iv.getValue(), element$iv$iv.getKey().intValue())) {
                destination$iv$iv.put(element$iv$iv.getKey(), element$iv$iv.getValue());
            }
        }
        this.garbageQueue = MapsKt.toMutableMap(destination$iv$iv);
        if (this.armorsValue.get().booleanValue()) {
            this.armorQueue = findBestArmor();
        }
    }

    private final ArmorPart[] findBestArmor() {
        Map ArmorParts = (Map) IntStream.range(0, 36).filter((v1) -> {
            return m2784findBestArmor$lambda2(r1, v1);
        }).mapToObj(InvManager::m2785findBestArmor$lambda3).collect(Collectors.groupingBy(InvManager::m2786findBestArmor$lambda4));
        ArmorPart[] bestArmor = new ArmorPart[4];
        Intrinsics.checkNotNullExpressionValue(ArmorParts, "ArmorParts");
        for (Map.Entry entry : ArmorParts.entrySet()) {
            Integer key = (Integer) entry.getKey();
            List it = (List) entry.getValue();
            Intrinsics.checkNotNull(key);
            int intValue = key.intValue();
            Intrinsics.checkNotNullExpressionValue(it, "it");
            CollectionsKt.sortWith(it, (v1, v2) -> {
                return m2787findBestArmor$lambda6$lambda5(r1, v1, v2);
            });
            Unit unit = Unit.INSTANCE;
            Intrinsics.checkNotNullExpressionValue(it, "value.also { it.sortWith…Priority.get(), goal) } }");
            bestArmor[intValue] = (ArmorPart) CollectionsKt.lastOrNull((List<? extends Object>) it);
        }
        return bestArmor;
    }

    /* renamed from: findBestArmor$lambda-2 */
    private static final boolean m2784findBestArmor$lambda2(InvManager this$0, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        IItemStack func_70301_a = MinecraftInstance.f362mc.field_71439_g.field_71071_by.func_70301_a(i);
        return func_70301_a != null && (func_70301_a.func_77973_b() instanceof ItemArmor) && (i < 9 || System.currentTimeMillis() - func_70301_a.getItemDelay() >= ((long) this$0.itemDelayValue.get().intValue()));
    }

    /* renamed from: findBestArmor$lambda-3 */
    private static final ArmorPart m2785findBestArmor$lambda3(int i) {
        ItemStack func_70301_a = MinecraftInstance.f362mc.field_71439_g.field_71071_by.func_70301_a(i);
        Intrinsics.checkNotNullExpressionValue(func_70301_a, "mc.thePlayer.inventory.getStackInSlot(i)");
        return new ArmorPart(func_70301_a, i);
    }

    /* renamed from: findBestArmor$lambda-4 */
    private static final Integer m2786findBestArmor$lambda4(ArmorPart obj) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        return Integer.valueOf(obj.getArmorType());
    }

    /* renamed from: findBestArmor$lambda-6$lambda-5 */
    private static final int m2787findBestArmor$lambda6$lambda5(InvManager this$0, ArmorPart ArmorPart, ArmorPart ArmorPart2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ItemHelper itemHelper = ItemHelper.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(ArmorPart, "ArmorPart");
        Intrinsics.checkNotNullExpressionValue(ArmorPart2, "ArmorPart2");
        return itemHelper.compareArmor(ArmorPart, ArmorPart2, this$0.nbtArmorPriority.get().floatValue(), this$0.getGoal());
    }

    public final boolean move(int item, boolean isArmorSlot) {
        if (!isArmorSlot && item < 9 && this.hotbarValue.get().booleanValue() && !(MinecraftInstance.f362mc.field_71462_r instanceof GuiInventory) && !this.spoofInventory) {
            if (item != MinecraftInstance.f362mc.field_71439_g.field_71071_by.field_70461_c) {
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C09PacketHeldItemChange(item));
            }
            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C08PacketPlayerBlockPlacement(MinecraftInstance.f362mc.field_71439_g.field_71069_bz.func_75139_a(item).func_75211_c()));
            if (item != MinecraftInstance.f362mc.field_71439_g.field_71071_by.field_70461_c) {
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C09PacketHeldItemChange(MinecraftInstance.f362mc.field_71439_g.field_71071_by.field_70461_c));
            }
            this.delay = TimeUtils.randomDelay(this.minDelayValue.get().intValue(), this.maxDelayValue.get().intValue());
            return true;
        } else if (this.noMoveValue.get().booleanValue() && MovementUtils.isMoving()) {
            return false;
        } else {
            if ((!this.invOpenValue.get().booleanValue() || (MinecraftInstance.f362mc.field_71462_r instanceof GuiInventory)) && item != -1) {
                boolean openInventory = this.invSpoof.get().booleanValue() && !(MinecraftInstance.f362mc.field_71462_r instanceof GuiInventory) && !this.invSpoofOld.get().booleanValue();
                if (openInventory) {
                    InventoryHelper.INSTANCE.openPacket();
                }
                MinecraftInstance.f362mc.field_71442_b.func_78753_a(MinecraftInstance.f362mc.field_71439_g.field_71069_bz.field_75152_c, isArmorSlot ? item : item < 9 ? item + 36 : item, 0, 1, MinecraftInstance.f362mc.field_71439_g);
                this.delay = TimeUtils.randomDelay(this.minDelayValue.get().intValue(), this.maxDelayValue.get().intValue());
                if (openInventory) {
                    InventoryHelper.INSTANCE.closePacket();
                    return true;
                }
                return true;
            }
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:165:0x01cf A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0281 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isUseful(@org.jetbrains.annotations.NotNull net.minecraft.item.ItemStack r8, int r9) {
        /*
            Method dump skipped, instructions count: 1212
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.features.module.modules.player.InvManager.isUseful(net.minecraft.item.ItemStack, int):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:182:0x062d  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0635 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final java.lang.Integer findBetterItem(int r7, net.minecraft.item.ItemStack r8) {
        /*
            Method dump skipped, instructions count: 2135
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.features.module.modules.player.InvManager.findBetterItem(int, net.minecraft.item.ItemStack):java.lang.Integer");
    }

    static /* synthetic */ Map items$default(InvManager invManager, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = 45;
        }
        return invManager.items(i, i2);
    }

    private final Map<Integer, ItemStack> items(int start, int end) {
        int i;
        Map items = new LinkedHashMap();
        int i2 = end - 1;
        if (start <= i2) {
            do {
                i = i2;
                i2--;
                IItemStack func_75211_c = MinecraftInstance.f362mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
                if (func_75211_c != null && func_75211_c.func_77973_b() != null) {
                    if ((!(36 <= i ? i < 45 : false) || !StringsKt.equals(type(i), "Ignore", true)) && System.currentTimeMillis() - func_75211_c.getItemDelay() >= this.itemDelayValue.get().intValue()) {
                        items.put(Integer.valueOf(i), func_75211_c);
                    }
                }
            } while (i != start);
            return items;
        }
        return items;
    }

    private final String type(int targetSlot) {
        switch (targetSlot) {
            case 0:
                return this.sortSlot1Value.get();
            case 1:
                return this.sortSlot2Value.get();
            case 2:
                return this.sortSlot3Value.get();
            case 3:
                return this.sortSlot4Value.get();
            case 4:
                return this.sortSlot5Value.get();
            case 5:
                return this.sortSlot6Value.get();
            case 6:
                return this.sortSlot7Value.get();
            case 7:
                return this.sortSlot8Value.get();
            case 8:
                return this.sortSlot9Value.get();
            default:
                return "";
        }
    }
}
