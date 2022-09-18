package net.ccbluex.liquidbounce.script.remapper;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p002io.FilesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.util.Constants;

/* compiled from: Remapper.kt */
@Metadata(m51mv = {1, 1, 16}, m55bv = {1, 0, 3}, m52k = 1, m54d1 = {"��4\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u000b\u001a\u00020\fJ\b\u0010\r\u001a\u00020\fH\u0002J\u001a\u0010\u000e\u001a\u00020\u00052\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u00102\u0006\u0010\u0011\u001a\u00020\u0005J\"\u0010\u0012\u001a\u00020\u00052\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u00102\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005RR\u0010\u0003\u001aF\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00040\u0004j*\u0012\u0004\u0012\u00020\u0005\u0012 \u0012\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005`\u0006`\u0006X\u0082\u0004¢\u0006\u0002\n��RR\u0010\u0007\u001aF\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00040\u0004j*\u0012\u0004\u0012\u00020\u0005\u0012 \u0012\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005`\u0006`\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\n\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n��¨\u0006\u0014"}, m53d2 = {"Lnet/ccbluex/liquidbounce/script/remapper/Remapper;", "", "()V", "fields", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "methods", "srgFile", "Ljava/io/File;", "srgName", "loadSrg", "", "parseSrg", "remapField", "clazz", Constants.CLASS, "name", "remapMethod", "desc", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/script/remapper/Remapper.class */
public final class Remapper {
    private static final String srgName = "stable_22";
    public static final Remapper INSTANCE = new Remapper();
    private static final File srgFile = new File(LiquidBounce.INSTANCE.getFileManager().dir, "Mcp.exe");
    private static final HashMap<String, HashMap<String, String>> fields = new HashMap<>();
    private static final HashMap<String, HashMap<String, String>> methods = new HashMap<>();

    private Remapper() {
    }

    public final void loadSrg() {
        if (!srgFile.exists()) {
            srgFile.createNewFile();
            ClientUtils.getLogger().info("[Remapper] Downloading stable_22 srg...");
            HttpUtils.download("https://cloud.liquidbounce.net/LiquidBounce/srgs/mcp-stable_22.srg", srgFile);
            ClientUtils.getLogger().info("[Remapper] Downloaded stable_22.");
        }
        ClientUtils.getLogger().info("[Remapper] Loading srg...");
        parseSrg();
        ClientUtils.getLogger().info("[Remapper] Loaded srg.");
    }

    private final void parseSrg() {
        Iterable $this$forEach$iv = FilesKt.readLines$default(srgFile, null, 1, null);
        for (Object element$iv : $this$forEach$iv) {
            String it = (String) element$iv;
            List args = StringsKt.split$default((CharSequence) it, new String[]{" "}, false, 0, 6, (Object) null);
            if (StringsKt.startsWith$default(it, "FD:", false, 2, (Object) null)) {
                String name = (String) args.get(1);
                String srg = (String) args.get(2);
                int lastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) name, '/', 0, false, 6, (Object) null);
                if (name == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String substring = name.substring(0, lastIndexOf$default);
                Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                String className = StringsKt.replace$default(substring, '/', '.', false, 4, (Object) null);
                int lastIndexOf$default2 = StringsKt.lastIndexOf$default((CharSequence) name, '/', 0, false, 6, (Object) null) + 1;
                if (name == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String fieldName = name.substring(lastIndexOf$default2);
                Intrinsics.checkExpressionValueIsNotNull(fieldName, "(this as java.lang.String).substring(startIndex)");
                int lastIndexOf$default3 = StringsKt.lastIndexOf$default((CharSequence) srg, '/', 0, false, 6, (Object) null) + 1;
                if (srg == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String fieldSrg = srg.substring(lastIndexOf$default3);
                Intrinsics.checkExpressionValueIsNotNull(fieldSrg, "(this as java.lang.String).substring(startIndex)");
                if (!fields.containsKey(className)) {
                    fields.put(className, new HashMap<>());
                }
                HashMap<String, String> hashMap = fields.get(className);
                if (hashMap == null) {
                    Intrinsics.throwNpe();
                }
                Intrinsics.checkExpressionValueIsNotNull(hashMap, "fields[className]!!");
                hashMap.put(fieldSrg, fieldName);
            } else if (StringsKt.startsWith$default(it, "MD:", false, 2, (Object) null)) {
                String name2 = (String) args.get(1);
                String desc = (String) args.get(2);
                String srg2 = (String) args.get(3);
                int lastIndexOf$default4 = StringsKt.lastIndexOf$default((CharSequence) name2, '/', 0, false, 6, (Object) null);
                if (name2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String substring2 = name2.substring(0, lastIndexOf$default4);
                Intrinsics.checkExpressionValueIsNotNull(substring2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                String className2 = StringsKt.replace$default(substring2, '/', '.', false, 4, (Object) null);
                int lastIndexOf$default5 = StringsKt.lastIndexOf$default((CharSequence) name2, '/', 0, false, 6, (Object) null) + 1;
                if (name2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String methodName = name2.substring(lastIndexOf$default5);
                Intrinsics.checkExpressionValueIsNotNull(methodName, "(this as java.lang.String).substring(startIndex)");
                int lastIndexOf$default6 = StringsKt.lastIndexOf$default((CharSequence) srg2, '/', 0, false, 6, (Object) null) + 1;
                if (srg2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String methodSrg = srg2.substring(lastIndexOf$default6);
                Intrinsics.checkExpressionValueIsNotNull(methodSrg, "(this as java.lang.String).substring(startIndex)");
                if (!methods.containsKey(className2)) {
                    methods.put(className2, new HashMap<>());
                }
                HashMap<String, String> hashMap2 = methods.get(className2);
                if (hashMap2 == null) {
                    Intrinsics.throwNpe();
                }
                Intrinsics.checkExpressionValueIsNotNull(hashMap2, "methods[className]!!");
                hashMap2.put(methodSrg + desc, methodName);
            } else {
                continue;
            }
        }
    }

    @NotNull
    public final String remapField(@NotNull Class<?> clazz, @NotNull String name) {
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        Intrinsics.checkParameterIsNotNull(name, "name");
        if (!fields.containsKey(clazz.getName())) {
            return name;
        }
        HashMap<String, String> hashMap = fields.get(clazz.getName());
        if (hashMap == null) {
            Intrinsics.throwNpe();
        }
        String orDefault = hashMap.getOrDefault(name, name);
        Intrinsics.checkExpressionValueIsNotNull(orDefault, "fields[clazz.name]!!.getOrDefault(name, name)");
        return orDefault;
    }

    @NotNull
    public final String remapMethod(@NotNull Class<?> clazz, @NotNull String name, @NotNull String desc) {
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(desc, "desc");
        if (!methods.containsKey(clazz.getName())) {
            return name;
        }
        HashMap<String, String> hashMap = methods.get(clazz.getName());
        if (hashMap == null) {
            Intrinsics.throwNpe();
        }
        String orDefault = hashMap.getOrDefault(name + desc, name);
        Intrinsics.checkExpressionValueIsNotNull(orDefault, "methods[clazz.name]!!.ge…efault(name + desc, name)");
        return orDefault;
    }
}
