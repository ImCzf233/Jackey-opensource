package net.ccbluex.liquidbounce.script.api;

import java.util.HashMap;
import jdk.nashorn.api.scripting.JSObject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;

/* compiled from: ScriptCommand.kt */
@Metadata(m51mv = {1, 1, 16}, m55bv = {1, 0, 3}, m52k = 1, m54d1 = {"��.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��\n\u0002\u0010\u0011\n\u0002\b\u0005\u0018��2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001b\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\fH\u0016¢\u0006\u0002\u0010\rJ\u0016\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u0003R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n��R*\u0010\u0005\u001a\u001e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00030\u0006j\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0003`\bX\u0082\u0004¢\u0006\u0002\n��¨\u0006\u0011"}, m53d2 = {"Lnet/ccbluex/liquidbounce/script/api/ScriptCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "commandObject", "Ljdk/nashorn/api/scripting/JSObject;", "(Ljdk/nashorn/api/scripting/JSObject;)V", "events", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "execute", "", "args", "", "([Ljava/lang/String;)V", "on", "eventName", "handler", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/script/api/ScriptCommand.class */
public final class ScriptCommand extends Command {
    private final HashMap<String, JSObject> events;
    private final JSObject commandObject;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ScriptCommand(@org.jetbrains.annotations.NotNull jdk.nashorn.api.scripting.JSObject r8) {
        /*
            r7 = this;
            r0 = r8
            java.lang.String r1 = "commandObject"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r0, r1)
            r0 = r7
            r1 = r8
            java.lang.String r2 = "name"
            java.lang.Object r1 = r1.getMember(r2)
            r2 = r1
            if (r2 != 0) goto L1d
            kotlin.TypeCastException r2 = new kotlin.TypeCastException
            r3 = r2
            java.lang.String r4 = "null cannot be cast to non-null type kotlin.String"
            r3.<init>(r4)
            throw r2
        L1d:
            java.lang.String r1 = (java.lang.String) r1
            r2 = r8
            java.lang.String r3 = "aliases"
            java.lang.Object r2 = r2.getMember(r3)
            java.lang.Class<java.lang.String[]> r3 = java.lang.String[].class
            java.lang.Object r2 = jdk.nashorn.api.scripting.ScriptUtils.convert(r2, r3)
            r3 = r2
            if (r3 != 0) goto L3b
            kotlin.TypeCastException r3 = new kotlin.TypeCastException
            r4 = r3
            java.lang.String r5 = "null cannot be cast to non-null type kotlin.Array<out kotlin.String>"
            r4.<init>(r5)
            throw r3
        L3b:
            java.lang.String[] r2 = (java.lang.String[]) r2
            r3 = r2
            int r3 = r3.length
            java.lang.Object[] r2 = java.util.Arrays.copyOf(r2, r3)
            java.lang.String[] r2 = (java.lang.String[]) r2
            r0.<init>(r1, r2)
            r0 = r7
            r1 = r8
            r0.commandObject = r1
            r0 = r7
            java.util.HashMap r1 = new java.util.HashMap
            r2 = r1
            r2.<init>()
            r0.events = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.script.api.ScriptCommand.<init>(jdk.nashorn.api.scripting.JSObject):void");
    }

    /* renamed from: on */
    public final void m30on(@NotNull String eventName, @NotNull JSObject handler) {
        Intrinsics.checkParameterIsNotNull(eventName, "eventName");
        Intrinsics.checkParameterIsNotNull(handler, "handler");
        this.events.put(eventName, handler);
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        try {
            JSObject jSObject = this.events.get("execute");
            if (jSObject != null) {
                jSObject.call(this.commandObject, args);
            }
        } catch (Throwable throwable) {
            ClientUtils.getLogger().error("[ScriptAPI] Exception in command '" + getCommand() + "'!", throwable);
        }
    }
}
