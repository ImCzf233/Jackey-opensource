package net.ccbluex.liquidbounce.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.modules.misc.NameProtect;
import net.ccbluex.liquidbounce.features.module.modules.misc.Spammer;
import net.ccbluex.liquidbounce.features.special.MacroManager;
import net.ccbluex.liquidbounce.value.Value;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

/* compiled from: SettingsUtils.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\"\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\bÆ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u001e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t¨\u0006\f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/utils/SettingsUtils;", "", "()V", "executeScript", "", "script", "", "generateScript", "values", "", "binds", "states", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/SettingsUtils.class */
public final class SettingsUtils {
    @NotNull
    public static final SettingsUtils INSTANCE = new SettingsUtils();

    private SettingsUtils() {
    }

    /* JADX WARN: Removed duplicated region for block: B:148:0x04d5 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:155:0x04b1 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void executeScript(@org.jetbrains.annotations.NotNull java.lang.String r8) {
        /*
            Method dump skipped, instructions count: 1819
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.utils.SettingsUtils.executeScript(java.lang.String):void");
    }

    @NotNull
    public final String generateScript(boolean values, boolean binds, boolean states) {
        StringBuilder stringBuilder = new StringBuilder();
        Map $this$filter$iv = MacroManager.INSTANCE.getMacroMapping();
        Map destination$iv$iv = new LinkedHashMap();
        for (Map.Entry element$iv$iv : $this$filter$iv.entrySet()) {
            if (element$iv$iv.getKey().intValue() != 0) {
                destination$iv$iv.put(element$iv$iv.getKey(), element$iv$iv.getValue());
            }
        }
        for (Map.Entry element$iv : destination$iv$iv.entrySet()) {
            stringBuilder.append("macro " + ((Number) element$iv.getKey()).intValue() + ' ' + ((String) element$iv.getValue())).append("\n");
        }
        Iterable $this$filter$iv2 = LiquidBounce.INSTANCE.getModuleManager().getModules();
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv2 : $this$filter$iv2) {
            Module it = (Module) element$iv$iv2;
            if (it.getCategory() != ModuleCategory.RENDER && !(it instanceof NameProtect) && !(it instanceof Spammer)) {
                destination$iv$iv2.add(element$iv$iv2);
            }
        }
        Iterable $this$forEach$iv = (List) destination$iv$iv2;
        for (Object element$iv2 : $this$forEach$iv) {
            Module it2 = (Module) element$iv2;
            if (values) {
                Iterable $this$forEach$iv2 = it2.getValues();
                for (Object element$iv3 : $this$forEach$iv2) {
                    Value value = (Value) element$iv3;
                    stringBuilder.append(it2.getName() + ' ' + value.getName() + ' ' + value.get()).append("\n");
                }
            }
            if (states) {
                stringBuilder.append(it2.getName() + " toggle " + it2.getState()).append("\n");
            }
            if (binds) {
                stringBuilder.append(it2.getName() + " bind " + ((Object) Keyboard.getKeyName(it2.getKeyBind()))).append("\n");
            }
        }
        String sb = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(sb, "stringBuilder.toString()");
        return sb;
    }
}
