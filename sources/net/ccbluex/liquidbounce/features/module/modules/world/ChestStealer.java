package net.ccbluex.liquidbounce.features.module.modules.world;

import com.viaversion.viaversion.libs.javassist.compiler.TokenId;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.player.InvManager;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ChestStealer.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0011\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010,\u001a\u00020\u00162\u0006\u0010-\u001a\u00020.H\u0002J\u0018\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020.2\u0006\u00102\u001a\u000203H\u0002J\b\u00104\u001a\u000200H\u0016J\u0010\u00105\u001a\u0002002\u0006\u00106\u001a\u000207H\u0007J\u0010\u00108\u001a\u0002002\u0006\u00106\u001a\u000209H\u0003J\u0012\u0010:\u001a\u0002002\b\u00106\u001a\u0004\u0018\u00010;H\u0007J\u0010\u0010<\u001a\u0002002\u0006\u00106\u001a\u00020=H\u0007J\u000e\u0010>\u001a\u0002002\u0006\u00101\u001a\u00020?R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n��R\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n��R\u0014\u0010\u0015\u001a\u00020\u00168BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001a\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u001d\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u001e\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n��R\u001a\u0010\u001f\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b \u0010\u0018\"\u0004\b!\u0010\"R\u000e\u0010#\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n��R\u0011\u0010$\u001a\u00020\t¢\u0006\b\n��\u001a\u0004\b%\u0010&R\u0011\u0010'\u001a\u00020\t¢\u0006\b\n��\u001a\u0004\b(\u0010&R\u0011\u0010)\u001a\u00020\t¢\u0006\b\n��\u001a\u0004\b*\u0010&R\u000e\u0010+\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n��¨\u0006@"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/ChestStealer;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "autoCloseMaxDelayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "autoCloseMinDelayValue", "autoCloseTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "autoCloseValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "chestTitleValue", "closeOnFullValue", "contentReceived", "", "getContentReceived", "()I", "setContentReceived", "(I)V", "delayTimer", "eventModeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "fullInventory", "", "getFullInventory", "()Z", "maxDelayValue", "minDelayValue", "nextCloseDelay", "", "nextDelay", "noCompassValue", "once", "getOnce", "setOnce", "(Z)V", "onlyItemsValue", "showStringValue", "getShowStringValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "silenceValue", "getSilenceValue", "stillDisplayValue", "getStillDisplayValue", "takeRandomizedValue", "isEmpty", "chest", "Lnet/minecraft/client/gui/inventory/GuiChest;", "move", "", "screen", "slot", "Lnet/minecraft/inventory/Slot;", "onDisable", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "performStealer", "Lnet/minecraft/client/gui/GuiScreen;", "LiquidBounce"})
@ModuleInfo(name = "ChestStealer", spacedName = "Chest Stealer", description = "Automatically steals all items from a chest.", category = ModuleCategory.WORLD)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/world/ChestStealer.class */
public final class ChestStealer extends Module {
    @NotNull
    private final IntegerValue maxDelayValue = new IntegerValue() { // from class: net.ccbluex.liquidbounce.features.module.modules.world.ChestStealer$maxDelayValue$1
        /* JADX INFO: Access modifiers changed from: package-private */
        {
            super("MaxDelay", 200, 0, (int) TokenId.Identifier, "ms");
        }

        @Override // net.ccbluex.liquidbounce.value.Value
        public /* bridge */ /* synthetic */ void onChanged(Integer num, Integer num2) {
            onChanged(num.intValue(), num2.intValue());
        }

        protected void onChanged(int oldValue, int newValue) {
            IntegerValue integerValue;
            IntegerValue integerValue2;
            integerValue = ChestStealer.this.minDelayValue;
            int i = integerValue.get().intValue();
            if (i > newValue) {
                set((ChestStealer$maxDelayValue$1) Integer.valueOf(i));
            }
            ChestStealer chestStealer = ChestStealer.this;
            integerValue2 = ChestStealer.this.minDelayValue;
            chestStealer.nextDelay = TimeUtils.randomDelay(integerValue2.get().intValue(), get().intValue());
        }
    };
    @NotNull
    private final IntegerValue minDelayValue = new IntegerValue() { // from class: net.ccbluex.liquidbounce.features.module.modules.world.ChestStealer$minDelayValue$1
        /* JADX INFO: Access modifiers changed from: package-private */
        {
            super("MinDelay", 150, 0, (int) TokenId.Identifier, "ms");
        }

        @Override // net.ccbluex.liquidbounce.value.Value
        public /* bridge */ /* synthetic */ void onChanged(Integer num, Integer num2) {
            onChanged(num.intValue(), num2.intValue());
        }

        protected void onChanged(int oldValue, int newValue) {
            IntegerValue integerValue;
            IntegerValue integerValue2;
            integerValue = ChestStealer.this.maxDelayValue;
            int i = integerValue.get().intValue();
            if (i < newValue) {
                set((ChestStealer$minDelayValue$1) Integer.valueOf(i));
            }
            ChestStealer chestStealer = ChestStealer.this;
            int intValue = get().intValue();
            integerValue2 = ChestStealer.this.maxDelayValue;
            chestStealer.nextDelay = TimeUtils.randomDelay(intValue, integerValue2.get().intValue());
        }
    };
    @NotNull
    private final ListValue eventModeValue = new ListValue("OnEvent", new String[]{"Render3D", "Update", "MotionPre", "MotionPost"}, "Render3D");
    @NotNull
    private final BoolValue takeRandomizedValue = new BoolValue("TakeRandomized", false);
    @NotNull
    private final BoolValue onlyItemsValue = new BoolValue("OnlyItems", false);
    @NotNull
    private final BoolValue noCompassValue = new BoolValue("NoCompass", false);
    @NotNull
    private final BoolValue autoCloseValue = new BoolValue("AutoClose", true);
    @NotNull
    private final BoolValue silenceValue = new BoolValue("SilentMode", true);
    @NotNull
    private final BoolValue showStringValue = new BoolValue("Silent-ShowString", false, new ChestStealer$showStringValue$1(this));
    @NotNull
    private final BoolValue stillDisplayValue = new BoolValue("Silent-StillDisplay", false, new ChestStealer$stillDisplayValue$1(this));
    @NotNull
    private final IntegerValue autoCloseMaxDelayValue = new IntegerValue() { // from class: net.ccbluex.liquidbounce.features.module.modules.world.ChestStealer$autoCloseMaxDelayValue$1
        /* JADX INFO: Access modifiers changed from: package-private */
        {
            super("AutoCloseMaxDelay", 0, 0, (int) TokenId.Identifier, "ms");
        }

        @Override // net.ccbluex.liquidbounce.value.Value
        public /* bridge */ /* synthetic */ void onChanged(Integer num, Integer num2) {
            onChanged(num.intValue(), num2.intValue());
        }

        protected void onChanged(int oldValue, int newValue) {
            IntegerValue integerValue;
            IntegerValue integerValue2;
            integerValue = ChestStealer.this.autoCloseMinDelayValue;
            int i = integerValue.get().intValue();
            if (i > newValue) {
                set((ChestStealer$autoCloseMaxDelayValue$1) Integer.valueOf(i));
            }
            ChestStealer chestStealer = ChestStealer.this;
            integerValue2 = ChestStealer.this.autoCloseMinDelayValue;
            chestStealer.nextCloseDelay = TimeUtils.randomDelay(integerValue2.get().intValue(), get().intValue());
        }
    };
    @NotNull
    private final IntegerValue autoCloseMinDelayValue = new IntegerValue() { // from class: net.ccbluex.liquidbounce.features.module.modules.world.ChestStealer$autoCloseMinDelayValue$1
        /* JADX INFO: Access modifiers changed from: package-private */
        {
            super("AutoCloseMinDelay", 0, 0, (int) TokenId.Identifier, "ms");
        }

        @Override // net.ccbluex.liquidbounce.value.Value
        public /* bridge */ /* synthetic */ void onChanged(Integer num, Integer num2) {
            onChanged(num.intValue(), num2.intValue());
        }

        protected void onChanged(int oldValue, int newValue) {
            IntegerValue integerValue;
            IntegerValue integerValue2;
            integerValue = ChestStealer.this.autoCloseMaxDelayValue;
            int i = integerValue.get().intValue();
            if (i < newValue) {
                set((ChestStealer$autoCloseMinDelayValue$1) Integer.valueOf(i));
            }
            ChestStealer chestStealer = ChestStealer.this;
            int intValue = get().intValue();
            integerValue2 = ChestStealer.this.autoCloseMaxDelayValue;
            chestStealer.nextCloseDelay = TimeUtils.randomDelay(intValue, integerValue2.get().intValue());
        }
    };
    @NotNull
    private final BoolValue closeOnFullValue = new BoolValue("CloseOnFull", true);
    @NotNull
    private final BoolValue chestTitleValue = new BoolValue("ChestTitle", false);
    @NotNull
    private final MSTimer delayTimer = new MSTimer();
    private long nextDelay = TimeUtils.randomDelay(this.minDelayValue.get().intValue(), this.maxDelayValue.get().intValue());
    @NotNull
    private final MSTimer autoCloseTimer = new MSTimer();
    private long nextCloseDelay = TimeUtils.randomDelay(this.autoCloseMinDelayValue.get().intValue(), this.autoCloseMaxDelayValue.get().intValue());
    private int contentReceived;
    private boolean once;

    @NotNull
    public final BoolValue getSilenceValue() {
        return this.silenceValue;
    }

    @NotNull
    public final BoolValue getShowStringValue() {
        return this.showStringValue;
    }

    @NotNull
    public final BoolValue getStillDisplayValue() {
        return this.stillDisplayValue;
    }

    public final int getContentReceived() {
        return this.contentReceived;
    }

    public final void setContentReceived(int i) {
        this.contentReceived = i;
    }

    public final boolean getOnce() {
        return this.once;
    }

    public final void setOnce(boolean z) {
        this.once = z;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        this.once = false;
    }

    @EventTarget
    public final void onRender3D(@Nullable Render3DEvent event) {
        GuiScreen screen = MinecraftInstance.f362mc.field_71462_r;
        if (screen != null && StringsKt.equals(this.eventModeValue.get(), "render3d", true)) {
            performStealer(screen);
        }
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        GuiScreen screen = MinecraftInstance.f362mc.field_71462_r;
        if (screen != null && StringsKt.equals(this.eventModeValue.get(), "update", true)) {
            performStealer(screen);
        }
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        GuiScreen screen = MinecraftInstance.f362mc.field_71462_r;
        if (screen != null && StringsKt.equals(this.eventModeValue.get(), Intrinsics.stringPlus("motion", event.getEventState().getStateName()), true)) {
            performStealer(screen);
        }
    }

    public final void performStealer(@NotNull GuiScreen screen) {
        String str;
        Intrinsics.checkNotNullParameter(screen, "screen");
        if (this.once && !(screen instanceof GuiChest)) {
            setState(false);
        } else if (!(screen instanceof GuiChest) || !this.delayTimer.hasTimePassed(this.nextDelay)) {
            this.autoCloseTimer.reset();
        } else {
            if (!this.once && this.noCompassValue.get().booleanValue()) {
                ItemStack func_70448_g = MinecraftInstance.f362mc.field_71439_g.field_71071_by.func_70448_g();
                if (func_70448_g == null) {
                    str = null;
                } else {
                    Item func_77973_b = func_70448_g.func_77973_b();
                    str = func_77973_b == null ? null : func_77973_b.func_77658_a();
                }
                if (Intrinsics.areEqual(str, "item.compass")) {
                    return;
                }
            }
            if (!this.once && this.chestTitleValue.get().booleanValue()) {
                if (((GuiChest) screen).field_147015_w == null) {
                    return;
                }
                String func_70005_c_ = ((GuiChest) screen).field_147015_w.func_70005_c_();
                Intrinsics.checkNotNullExpressionValue(func_70005_c_, "screen.lowerChestInventory.name");
                String func_82833_r = new ItemStack((Item) Item.field_150901_e.func_82594_a(new ResourceLocation("minecraft:chest"))).func_82833_r();
                Intrinsics.checkNotNullExpressionValue(func_82833_r, "ItemStack(Item.itemRegis…aft:chest\"))).displayName");
                if (!StringsKt.contains$default((CharSequence) func_70005_c_, (CharSequence) func_82833_r, false, 2, (Object) null)) {
                    return;
                }
            }
            Module module = LiquidBounce.INSTANCE.getModuleManager().get(InvManager.class);
            if (module == null) {
                throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.player.InvManager");
            }
            InvManager inventoryCleaner = (InvManager) module;
            if (!isEmpty((GuiChest) screen) && (!this.closeOnFullValue.get().booleanValue() || !getFullInventory())) {
                this.autoCloseTimer.reset();
                if (this.takeRandomizedValue.get().booleanValue()) {
                    boolean noLoop = false;
                    do {
                        List items = new ArrayList();
                        int i = 0;
                        int i2 = ((GuiChest) screen).field_147018_x * 9;
                        while (i < i2) {
                            int slotIndex = i;
                            i++;
                            Slot slot = (Slot) ((GuiChest) screen).field_147002_h.field_75151_b.get(slotIndex);
                            if (slot.func_75211_c() != null && (!this.onlyItemsValue.get().booleanValue() || !(slot.func_75211_c().func_77973_b() instanceof ItemBlock))) {
                                if (inventoryCleaner.getState()) {
                                    ItemStack func_75211_c = slot.func_75211_c();
                                    Intrinsics.checkNotNullExpressionValue(func_75211_c, "slot.stack");
                                    if (inventoryCleaner.isUseful(func_75211_c, -1)) {
                                    }
                                }
                                Intrinsics.checkNotNullExpressionValue(slot, "slot");
                                items.add(slot);
                            }
                        }
                        int randomSlot = Random.Default.nextInt(items.size());
                        move((GuiChest) screen, (Slot) items.get(randomSlot));
                        if (this.nextDelay == 0 || this.delayTimer.hasTimePassed(this.nextDelay)) {
                            noLoop = true;
                        }
                        if (this.delayTimer.hasTimePassed(this.nextDelay)) {
                            if (!(!items.isEmpty())) {
                                return;
                            }
                        } else {
                            return;
                        }
                    } while (!noLoop);
                    return;
                }
                int i3 = 0;
                int i4 = ((GuiChest) screen).field_147018_x * 9;
                while (i3 < i4) {
                    int slotIndex2 = i3;
                    i3++;
                    Slot slot2 = (Slot) ((GuiChest) screen).field_147002_h.field_75151_b.get(slotIndex2);
                    if (this.delayTimer.hasTimePassed(this.nextDelay) && slot2.func_75211_c() != null && (!this.onlyItemsValue.get().booleanValue() || !(slot2.func_75211_c().func_77973_b() instanceof ItemBlock))) {
                        if (inventoryCleaner.getState()) {
                            ItemStack func_75211_c2 = slot2.func_75211_c();
                            Intrinsics.checkNotNullExpressionValue(func_75211_c2, "slot.stack");
                            if (inventoryCleaner.isUseful(func_75211_c2, -1)) {
                            }
                        }
                        Intrinsics.checkNotNullExpressionValue(slot2, "slot");
                        move((GuiChest) screen, slot2);
                    }
                }
            } else if (this.autoCloseValue.get().booleanValue() && ((GuiChest) screen).field_147002_h.field_75152_c == this.contentReceived && this.autoCloseTimer.hasTimePassed(this.nextCloseDelay)) {
                MinecraftInstance.f362mc.field_71439_g.func_71053_j();
                if (this.silenceValue.get().booleanValue() && !this.stillDisplayValue.get().booleanValue()) {
                    LiquidBounce.INSTANCE.getHud().addNotification(new Notification("Closed chest.", Notification.Type.INFO));
                }
                this.nextCloseDelay = TimeUtils.randomDelay(this.autoCloseMinDelayValue.get().intValue(), this.autoCloseMaxDelayValue.get().intValue());
                if (this.once) {
                    this.once = false;
                    setState(false);
                }
            }
        }
    }

    @EventTarget
    private final void onPacket(PacketEvent event) {
        S30PacketWindowItems packet = event.getPacket();
        if (packet instanceof S30PacketWindowItems) {
            this.contentReceived = packet.func_148911_c();
        }
    }

    private final void move(GuiChest screen, Slot slot) {
        screen.func_146984_a(slot, slot.field_75222_d, 0, 1);
        this.delayTimer.reset();
        this.nextDelay = TimeUtils.randomDelay(this.minDelayValue.get().intValue(), this.maxDelayValue.get().intValue());
    }

    private final boolean isEmpty(GuiChest chest) {
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(InvManager.class);
        if (module == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.player.InvManager");
        }
        InvManager inventoryCleaner = (InvManager) module;
        int i = 0;
        int i2 = chest.field_147018_x * 9;
        while (i < i2) {
            int i3 = i;
            i++;
            Slot slot = (Slot) chest.field_147002_h.field_75151_b.get(i3);
            if (slot.func_75211_c() != null && (!this.onlyItemsValue.get().booleanValue() || !(slot.func_75211_c().func_77973_b() instanceof ItemBlock))) {
                if (!inventoryCleaner.getState()) {
                    return false;
                }
                ItemStack func_75211_c = slot.func_75211_c();
                Intrinsics.checkNotNullExpressionValue(func_75211_c, "slot.stack");
                if (inventoryCleaner.isUseful(func_75211_c, -1)) {
                    return false;
                }
            }
        }
        return true;
    }

    private final boolean getFullInventory() {
        boolean z;
        Object[] objArr = MinecraftInstance.f362mc.field_71439_g.field_71071_by.field_70462_a;
        Intrinsics.checkNotNullExpressionValue(objArr, "mc.thePlayer.inventory.mainInventory");
        Object[] $this$none$iv = objArr;
        int i = 0;
        int length = $this$none$iv.length;
        while (i < length) {
            Object element$iv = $this$none$iv[i];
            i++;
            ItemStack it = (ItemStack) element$iv;
            if (it == null) {
                z = true;
                continue;
            } else {
                z = false;
                continue;
            }
            if (z) {
                return false;
            }
        }
        return true;
    }
}
