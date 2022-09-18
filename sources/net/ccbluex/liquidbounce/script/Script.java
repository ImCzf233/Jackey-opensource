package net.ccbluex.liquidbounce.script;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import javax.script.ScriptEngine;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptUtils;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p002io.FilesKt;
import kotlin.p002io.TextStreamsKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.script.api.ScriptCommand;
import net.ccbluex.liquidbounce.script.api.ScriptModule;
import net.ccbluex.liquidbounce.script.api.ScriptTab;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/* compiled from: Script.kt */
@Metadata(m51mv = {1, 1, 16}, m55bv = {1, 0, 3}, m52k = 1, m54d1 = {"��R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010!\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n��\n\u0002\u0010\u0002\n\u0002\b\u0013\u0018��2\u00020\u0001:\u00018B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u0007H\u0002J\u0012\u0010(\u001a\u0004\u0018\u00010\u00072\u0006\u0010)\u001a\u00020\u0007H\u0002J\u000e\u0010*\u001a\u00020&2\u0006\u0010\u0002\u001a\u00020\u0007J\u0006\u0010+\u001a\u00020&J\u0016\u0010,\u001a\u00020&2\u0006\u0010'\u001a\u00020\u00072\u0006\u0010-\u001a\u00020\bJ\u0006\u0010.\u001a\u00020&J\u0006\u0010/\u001a\u00020&J\u0016\u00100\u001a\u00020&2\u0006\u00101\u001a\u00020\b2\u0006\u00102\u001a\u00020\bJ\u0016\u00103\u001a\u00020&2\u0006\u00104\u001a\u00020\b2\u0006\u00102\u001a\u00020\bJ\u000e\u00105\u001a\u00020&2\u0006\u00106\u001a\u00020\bJ\b\u00107\u001a\u00020&H\u0002R*\u0010\u0005\u001a\u001e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b`\tX\u0082\u0004¢\u0006\u0002\n��R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n��R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u000bX\u0082\u0004¢\u0006\u0002\n��R\"\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0010X\u0086.¢\u0006\u0010\n\u0002\u0010\u0015\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n��R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\u0007X\u0086.¢\u0006\u000e\n��\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u000e\u0010\u001f\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u001a\u0010 \u001a\u00020\u0007X\u0086.¢\u0006\u000e\n��\u001a\u0004\b!\u0010\u001c\"\u0004\b\"\u0010\u001eR\u000e\u0010#\u001a\u00020$X\u0082\u000e¢\u0006\u0002\n��¨\u00069"}, m53d2 = {"Lnet/ccbluex/liquidbounce/script/Script;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "scriptFile", "Ljava/io/File;", "(Ljava/io/File;)V", "events", "Ljava/util/HashMap;", "", "Ljdk/nashorn/api/scripting/JSObject;", "Lkotlin/collections/HashMap;", "registeredCommands", "", "Lnet/ccbluex/liquidbounce/features/command/Command;", "registeredModules", "Lnet/ccbluex/liquidbounce/features/module/Module;", "scriptAuthors", "", "getScriptAuthors", "()[Ljava/lang/String;", "setScriptAuthors", "([Ljava/lang/String;)V", "[Ljava/lang/String;", "scriptEngine", "Ljavax/script/ScriptEngine;", "getScriptFile", "()Ljava/io/File;", "scriptName", "getScriptName", "()Ljava/lang/String;", "setScriptName", "(Ljava/lang/String;)V", "scriptText", "scriptVersion", "getScriptVersion", "setScriptVersion", "state", "", "callEvent", "", "eventName", "getMagicComment", "name", "import", "initScript", "on", "handler", "onDisable", "onEnable", "registerCommand", "commandObject", "callback", "registerModule", "moduleObject", "registerTab", "tabObject", "supportLegacyScripts", "RegisterScript", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/script/Script.class */
public final class Script extends MinecraftInstance {
    private final ScriptEngine scriptEngine;
    private final String scriptText;
    @NotNull
    public String scriptName;
    @NotNull
    public String scriptVersion;
    @NotNull
    public String[] scriptAuthors;
    private boolean state;
    private final HashMap<String, JSObject> events = new HashMap<>();
    private final List<Module> registeredModules = new ArrayList();
    private final List<Command> registeredCommands = new ArrayList();
    @NotNull
    private final File scriptFile;

    @NotNull
    public final File getScriptFile() {
        return this.scriptFile;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x00a6, code lost:
        if (r0 != null) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public Script(@org.jetbrains.annotations.NotNull java.io.File r8) {
        /*
            Method dump skipped, instructions count: 431
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.script.Script.<init>(java.io.File):void");
    }

    @NotNull
    public final String getScriptName() {
        String str = this.scriptName;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scriptName");
        }
        return str;
    }

    public final void setScriptName(@NotNull String str) {
        Intrinsics.checkParameterIsNotNull(str, "<set-?>");
        this.scriptName = str;
    }

    @NotNull
    public final String getScriptVersion() {
        String str = this.scriptVersion;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scriptVersion");
        }
        return str;
    }

    public final void setScriptVersion(@NotNull String str) {
        Intrinsics.checkParameterIsNotNull(str, "<set-?>");
        this.scriptVersion = str;
    }

    @NotNull
    public final String[] getScriptAuthors() {
        String[] strArr = this.scriptAuthors;
        if (strArr == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scriptAuthors");
        }
        return strArr;
    }

    public final void setScriptAuthors(@NotNull String[] strArr) {
        Intrinsics.checkParameterIsNotNull(strArr, "<set-?>");
        this.scriptAuthors = strArr;
    }

    public final void initScript() {
        this.scriptEngine.eval(this.scriptText);
        callEvent("load");
        ClientUtils.getLogger().info("[ScriptAPI] Successfully loaded script '" + this.scriptFile.getName() + "'.");
    }

    /* compiled from: Script.kt */
    @Metadata(m51mv = {1, 1, 16}, m55bv = {1, 0, 3}, m52k = 1, m54d1 = {"��\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0004\u0018��2\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0002H\u0016¨\u0006\u0007"}, m53d2 = {"Lnet/ccbluex/liquidbounce/script/Script$RegisterScript;", "Ljava/util/function/Function;", "Ljdk/nashorn/api/scripting/JSObject;", "Lnet/ccbluex/liquidbounce/script/Script;", "(Lnet/ccbluex/liquidbounce/script/Script;)V", "apply", "scriptObject", "LiquidBounce"})
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/script/Script$RegisterScript.class */
    public final class RegisterScript implements Function<JSObject, Script> {
        public RegisterScript() {
            Script.this = $outer;
        }

        @NotNull
        public Script apply(@NotNull JSObject scriptObject) {
            Intrinsics.checkParameterIsNotNull(scriptObject, "scriptObject");
            Script script = Script.this;
            Object member = scriptObject.getMember("name");
            if (member == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
            }
            script.setScriptName((String) member);
            Script script2 = Script.this;
            Object member2 = scriptObject.getMember("version");
            if (member2 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
            }
            script2.setScriptVersion((String) member2);
            Script script3 = Script.this;
            Object convert = ScriptUtils.convert(scriptObject.getMember("authors"), String[].class);
            if (convert == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
            }
            script3.setScriptAuthors((String[]) convert);
            return Script.this;
        }
    }

    public final void registerModule(@NotNull JSObject moduleObject, @NotNull JSObject callback) {
        Intrinsics.checkParameterIsNotNull(moduleObject, "moduleObject");
        Intrinsics.checkParameterIsNotNull(callback, "callback");
        ScriptModule module = new ScriptModule(moduleObject);
        LiquidBounce.INSTANCE.getModuleManager().registerModule(module);
        this.registeredModules.add(module);
        callback.call(moduleObject, module);
    }

    public final void registerCommand(@NotNull JSObject commandObject, @NotNull JSObject callback) {
        Intrinsics.checkParameterIsNotNull(commandObject, "commandObject");
        Intrinsics.checkParameterIsNotNull(callback, "callback");
        ScriptCommand command = new ScriptCommand(commandObject);
        LiquidBounce.INSTANCE.getCommandManager().registerCommand(command);
        this.registeredCommands.add(command);
        callback.call(commandObject, command);
    }

    public final void registerTab(@NotNull JSObject tabObject) {
        Intrinsics.checkParameterIsNotNull(tabObject, "tabObject");
        new ScriptTab(tabObject);
    }

    private final String getMagicComment(String name) {
        Iterable $this$forEach$iv = StringsKt.lines(this.scriptText);
        for (Object element$iv : $this$forEach$iv) {
            String it = (String) element$iv;
            if (!StringsKt.startsWith$default(it, "///", false, 2, (Object) null)) {
                return null;
            }
            int length = "///".length();
            if (it == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String substring = it.substring(length);
            Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.String).substring(startIndex)");
            List commentData = StringsKt.split$default((CharSequence) substring, new String[]{"="}, false, 2, 2, (Object) null);
            String str = (String) CollectionsKt.first((List<? extends Object>) commentData);
            if (str == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            }
            if (Intrinsics.areEqual(StringsKt.trim((CharSequence) str).toString(), name)) {
                String str2 = (String) CollectionsKt.last((List<? extends Object>) commentData);
                if (str2 != null) {
                    return StringsKt.trim((CharSequence) str2).toString();
                }
                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            }
        }
        return null;
    }

    private final void supportLegacyScripts() {
        if (!Intrinsics.areEqual(getMagicComment("api_version"), "2")) {
            ClientUtils.getLogger().info("[ScriptAPI] Running script '" + this.scriptFile.getName() + "' with legacy support.");
            URL resource = LiquidBounce.class.getResource("/assets/minecraft/liquidbounce/scriptapi/legacy.js");
            Intrinsics.checkExpressionValueIsNotNull(resource, "LiquidBounce::class.java…nce/scriptapi/legacy.js\")");
            String legacyScript = new String(TextStreamsKt.readBytes(resource), Charsets.UTF_8);
            this.scriptEngine.eval(legacyScript);
        }
    }

    /* renamed from: on */
    public final void m31on(@NotNull String eventName, @NotNull JSObject handler) {
        Intrinsics.checkParameterIsNotNull(eventName, "eventName");
        Intrinsics.checkParameterIsNotNull(handler, "handler");
        this.events.put(eventName, handler);
    }

    public final void onEnable() {
        if (this.state) {
            return;
        }
        callEvent("enable");
        this.state = true;
    }

    public final void onDisable() {
        if (!this.state) {
            return;
        }
        Iterable $this$forEach$iv = this.registeredModules;
        for (Object element$iv : $this$forEach$iv) {
            Module it = (Module) element$iv;
            LiquidBounce.INSTANCE.getModuleManager().unregisterModule(it);
        }
        Iterable $this$forEach$iv2 = this.registeredCommands;
        for (Object element$iv2 : $this$forEach$iv2) {
            Command it2 = (Command) element$iv2;
            LiquidBounce.INSTANCE.getCommandManager().unregisterCommand(it2);
        }
        callEvent("disable");
        this.state = false;
    }

    /* renamed from: import */
    public final void m2819import(@NotNull String scriptFile) {
        Intrinsics.checkParameterIsNotNull(scriptFile, "scriptFile");
        String scriptText = FilesKt.readText$default(new File(LiquidBounce.INSTANCE.getScriptManager().getScriptsFolder(), scriptFile), null, 1, null);
        this.scriptEngine.eval(scriptText);
    }

    private final void callEvent(String eventName) {
        try {
            JSObject jSObject = this.events.get(eventName);
            if (jSObject != null) {
                jSObject.call(null, new Object[0]);
            }
        } catch (Throwable throwable) {
            Logger logger = ClientUtils.getLogger();
            StringBuilder append = new StringBuilder().append("[ScriptAPI] Exception in script '");
            String str = this.scriptName;
            if (str == null) {
                Intrinsics.throwUninitializedPropertyAccessException("scriptName");
            }
            logger.error(append.append(str).append("'!").toString(), throwable);
        }
    }
}
