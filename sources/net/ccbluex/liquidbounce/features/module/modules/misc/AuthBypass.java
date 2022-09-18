package net.ccbluex.liquidbounce.features.module.modules.misc;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Level;
import org.apache.log4j.spi.Configurator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AuthBypass.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n��\n\u0002\u0018\u0002\n\u0002\u0010\b\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0010\u0010\u001a\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\b\u0010\u001b\u001a\u00020\u0015H\u0016J\u0010\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u001eH\u0007J\u0010\u0010\u001f\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020 H\u0007J\u0010\u0010!\u001a\u00020\u00052\u0006\u0010\"\u001a\u00020\u0005H\u0002R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n��R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n��R\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\u0007X\u0082\u0004¢\u0006\u0002\n��R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0013\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n��¨\u0006#"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/AuthBypass;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "brLangMap", "Ljava/util/HashMap;", "", "clickedSlot", "Ljava/util/ArrayList;", "", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "jsonParser", "Lcom/google/gson/JsonParser;", "packets", "Lnet/minecraft/network/Packet;", "Lnet/minecraft/network/play/INetHandlerPlayServer;", "skull", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "type", "click", "", "windowId", "slot", "item", "Lnet/minecraft/item/ItemStack;", "getItemLocalName", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "process", "data", "LiquidBounce"})
@ModuleInfo(name = "AuthBypass", spacedName = "Auth Bypass", description = "Bypass auth when join server.", category = ModuleCategory.MISC)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/misc/AuthBypass.class */
public final class AuthBypass extends Module {
    @Nullable
    private String skull;
    @NotNull
    private final IntegerValue delayValue = new IntegerValue("Delay", 1500, 100, (int) Level.TRACE_INT, "ms");
    @NotNull
    private String type = "none";
    @NotNull
    private final ArrayList<Packet<INetHandlerPlayServer>> packets = new ArrayList<>();
    @NotNull
    private final ArrayList<Integer> clickedSlot = new ArrayList<>();
    @NotNull
    private final MSTimer timer = new MSTimer();
    @NotNull
    private final JsonParser jsonParser = new JsonParser();
    @NotNull
    private final HashMap<String, String> brLangMap = new HashMap<>();

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if ((!this.packets.isEmpty()) && this.timer.hasTimePassed(this.delayValue.get().intValue())) {
            Iterator<Packet<INetHandlerPlayServer>> it = this.packets.iterator();
            while (it.hasNext()) {
                Packet packet = it.next();
                MinecraftInstance.f362mc.func_147114_u().func_147297_a(packet);
            }
            this.packets.clear();
            LiquidBounce.INSTANCE.getHud().addNotification(new Notification("Authentication bypassed.", Notification.Type.INFO));
        }
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        this.skull = null;
        this.type = "none";
        this.packets.clear();
        this.clickedSlot.clear();
        new Thread(() -> {
            m2771onEnable$lambda0(r2);
        }).start();
    }

    /* renamed from: onEnable$lambda-0 */
    private static final void m2771onEnable$lambda0(AuthBypass this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        JsonObject localeJson = new JsonParser().parse(IOUtils.toString(AuthBypass.class.getClassLoader().getResourceAsStream("br_items.json"), "utf-8")).getAsJsonObject();
        this$0.brLangMap.clear();
        for (Map.Entry entry : localeJson.entrySet()) {
            Intrinsics.checkNotNullExpressionValue(entry, "localeJson.entrySet()");
            String key = (String) entry.getKey();
            JsonElement element = (JsonElement) entry.getValue();
            String stringPlus = Intrinsics.stringPlus("item.", key);
            String asString = element.getAsString();
            Intrinsics.checkNotNullExpressionValue(asString, "element.asString");
            String lowerCase = asString.toLowerCase();
            Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
            this$0.brLangMap.put(stringPlus, lowerCase);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:48:0x01f7  */
    @net.ccbluex.liquidbounce.event.EventTarget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onPacket(@org.jetbrains.annotations.NotNull net.ccbluex.liquidbounce.event.PacketEvent r9) {
        /*
            Method dump skipped, instructions count: 772
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.features.module.modules.misc.AuthBypass.onPacket(net.ccbluex.liquidbounce.event.PacketEvent):void");
    }

    private final void click(int windowId, int slot, ItemStack item) {
        this.clickedSlot.add(Integer.valueOf(slot));
        this.packets.add(new C0EPacketClickWindow(windowId, slot, 0, 0, item, (short) RandomUtils.nextInt(114, 514)));
    }

    private final String getItemLocalName(ItemStack item) {
        String str = this.brLangMap.get(item.func_77977_a());
        return str == null ? Configurator.NULL : str;
    }

    private final String process(String data) {
        JsonParser jsonParser = this.jsonParser;
        byte[] decode = Base64.getDecoder().decode(data);
        Intrinsics.checkNotNullExpressionValue(decode, "getDecoder().decode(data)");
        JsonObject jsonObject = jsonParser.parse(new String(decode, Charsets.UTF_8)).getAsJsonObject();
        String asString = jsonObject.getAsJsonObject("textures").getAsJsonObject("SKIN").get("url").getAsString();
        Intrinsics.checkNotNullExpressionValue(asString, "jsonObject\n            .…     .get(\"url\").asString");
        return asString;
    }
}
