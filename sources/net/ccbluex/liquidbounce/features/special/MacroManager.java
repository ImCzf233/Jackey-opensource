package net.ccbluex.liquidbounce.features.special;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.KeyEvent;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

/* compiled from: MacroManager.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018��2\u00020\u00012\u00020\u0002B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u0007J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0007J\u000e\u0010\u0014\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0006R-\u0010\u0004\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007`\b¢\u0006\b\n��\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/special/MacroManager;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "macroMapping", "Ljava/util/HashMap;", "", "", "Lkotlin/collections/HashMap;", "getMacroMapping", "()Ljava/util/HashMap;", "addMacro", "", "keyCode", "command", "handleEvents", "", "onKey", "event", "Lnet/ccbluex/liquidbounce/event/KeyEvent;", "removeMacro", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/special/MacroManager.class */
public final class MacroManager extends MinecraftInstance implements Listenable {
    @NotNull
    public static final MacroManager INSTANCE = new MacroManager();
    @NotNull
    private static final HashMap<Integer, String> macroMapping = new HashMap<>();

    private MacroManager() {
    }

    @NotNull
    public final HashMap<Integer, String> getMacroMapping() {
        return macroMapping;
    }

    @EventTarget
    public final void onKey(@NotNull KeyEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.f362mc.field_71439_g == null || LiquidBounce.INSTANCE.getCommandManager() == null) {
            return;
        }
        Map $this$filter$iv = macroMapping;
        Map destination$iv$iv = new LinkedHashMap();
        for (Map.Entry element$iv$iv : $this$filter$iv.entrySet()) {
            if (element$iv$iv.getKey().intValue() == event.getKey()) {
                destination$iv$iv.put(element$iv$iv.getKey(), element$iv$iv.getValue());
            }
        }
        for (Map.Entry element$iv : destination$iv$iv.entrySet()) {
            if (StringsKt.startsWith$default((CharSequence) element$iv.getValue(), LiquidBounce.INSTANCE.getCommandManager().getPrefix(), false, 2, (Object) null)) {
                LiquidBounce.INSTANCE.getCommandManager().executeCommands((String) element$iv.getValue());
            } else {
                MinecraftInstance.f362mc.field_71439_g.func_71165_d((String) element$iv.getValue());
            }
        }
    }

    public final void addMacro(int keyCode, @NotNull String command) {
        Intrinsics.checkNotNullParameter(command, "command");
        macroMapping.put(Integer.valueOf(keyCode), command);
    }

    public final void removeMacro(int keyCode) {
        macroMapping.remove(Integer.valueOf(keyCode));
    }

    @Override // net.ccbluex.liquidbounce.event.Listenable
    public boolean handleEvents() {
        return true;
    }
}
