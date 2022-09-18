package net.ccbluex.liquidbounce.script.api;

import com.github.benmanes.caffeine.cache.LocalCacheFactory;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import jdk.nashorn.api.scripting.JSObject;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.ClickBlockEvent;
import net.ccbluex.liquidbounce.event.ClientShutdownEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.KeyEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.event.SlowDownEvent;
import net.ccbluex.liquidbounce.event.StepConfirmEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.value.Value;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ScriptModule.kt */
@Metadata(m51mv = {1, 1, 16}, m55bv = {1, 0, 3}, m52k = 1, m54d1 = {"��Ð\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010��\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001c\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00062\n\b\u0002\u0010 \u001a\u0004\u0018\u00010!H\u0002J\u0016\u0010\"\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010#\u001a\u00020\u0003J\u0010\u0010$\u001a\u00020\u001e2\u0006\u0010%\u001a\u00020&H\u0007J\u0010\u0010'\u001a\u00020\u001e2\u0006\u0010(\u001a\u00020)H\u0007J\b\u0010*\u001a\u00020\u001eH\u0016J\b\u0010+\u001a\u00020\u001eH\u0016J\u0010\u0010,\u001a\u00020\u001e2\u0006\u0010-\u001a\u00020.H\u0007J\u0010\u0010/\u001a\u00020\u001e2\u0006\u00100\u001a\u000201H\u0007J\u0010\u00102\u001a\u00020\u001e2\u0006\u00103\u001a\u000204H\u0007J\u0010\u00105\u001a\u00020\u001e2\u0006\u00106\u001a\u000207H\u0007J\u0010\u00108\u001a\u00020\u001e2\u0006\u00109\u001a\u00020:H\u0007J\u0010\u0010;\u001a\u00020\u001e2\u0006\u0010<\u001a\u00020=H\u0007J\u0010\u0010>\u001a\u00020\u001e2\u0006\u0010?\u001a\u00020@H\u0007J\u0010\u0010A\u001a\u00020\u001e2\u0006\u0010B\u001a\u00020CH\u0007J\u0010\u0010D\u001a\u00020\u001e2\u0006\u0010E\u001a\u00020FH\u0007J\u0010\u0010G\u001a\u00020\u001e2\u0006\u0010H\u001a\u00020IH\u0007J\u0010\u0010J\u001a\u00020\u001e2\u0006\u0010K\u001a\u00020LH\u0007J\u0010\u0010M\u001a\u00020\u001e2\u0006\u0010N\u001a\u00020OH\u0007J\u0010\u0010P\u001a\u00020\u001e2\u0006\u0010Q\u001a\u00020RH\u0007J\u0010\u0010S\u001a\u00020\u001e2\u0006\u0010T\u001a\u00020UH\u0007J\u0010\u0010V\u001a\u00020\u001e2\u0006\u0010W\u001a\u00020XH\u0007R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n��R2\u0010\u0007\u001a&\u0012\u0004\u0012\u00020\u0006\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t0\bj\u0012\u0012\u0004\u0012\u00020\u0006\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t`\nX\u0082\u0004¢\u0006\u0002\n��R*\u0010\u000b\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00030\fj\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0003`\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n��R?\u0010\u000e\u001a&\u0012\u0004\u0012\u00020\u0006\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t0\bj\u0012\u0012\u0004\u0012\u00020\u0006\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t`\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R(\u0010\u0014\u001a\u0004\u0018\u00010\u00062\b\u0010\u0013\u001a\u0004\u0018\u00010\u00068V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001e\u0010\u0019\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t0\u001a8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001c¨\u0006Y"}, m53d2 = {"Lnet/ccbluex/liquidbounce/script/api/ScriptModule;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "moduleObject", "Ljdk/nashorn/api/scripting/JSObject;", "(Ljdk/nashorn/api/scripting/JSObject;)V", "_tag", "", "_values", "Ljava/util/LinkedHashMap;", "Lnet/ccbluex/liquidbounce/value/Value;", "Lkotlin/collections/LinkedHashMap;", "events", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "settings", "getSettings", "()Ljava/util/LinkedHashMap;", "settings$delegate", "Lkotlin/Lazy;", "value", "tag", "getTag", "()Ljava/lang/String;", "setTag", "(Ljava/lang/String;)V", "values", "", "getValues", "()Ljava/util/List;", "callEvent", "", "eventName", "payload", "", "on", "handler", "onAttack", "attackEvent", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onClickBlock", "clickBlockEvent", "Lnet/ccbluex/liquidbounce/event/ClickBlockEvent;", "onDisable", "onEnable", "onJump", "jumpEvent", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onKey", "keyEvent", "Lnet/ccbluex/liquidbounce/event/KeyEvent;", "onMotion", "motionEvent", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "moveEvent", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "packetEvent", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender2D", "render2DEvent", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onRender3D", "render3DEvent", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onSession", "sessionEvent", "Lnet/ccbluex/liquidbounce/event/SessionEvent;", "onShutdown", "shutdownEvent", "Lnet/ccbluex/liquidbounce/event/ClientShutdownEvent;", "onSlowDown", "slowDownEvent", "Lnet/ccbluex/liquidbounce/event/SlowDownEvent;", "onStep", "stepEvent", "Lnet/ccbluex/liquidbounce/event/StepEvent;", "onStepConfirm", "stepConfirmEvent", "Lnet/ccbluex/liquidbounce/event/StepConfirmEvent;", "onStrafe", "strafeEvent", "Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "onUpdate", "updateEvent", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "worldEvent", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "LiquidBounce"})
@ModuleInfo(name = "ScriptModule", description = "Empty", category = ModuleCategory.MISC)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/script/api/ScriptModule.class */
public final class ScriptModule extends Module {
    private String _tag;
    private final JSObject moduleObject;
    private final HashMap<String, JSObject> events = new HashMap<>();
    private final LinkedHashMap<String, Value<?>> _values = new LinkedHashMap<>();
    @NotNull
    private final Lazy settings$delegate = LazyKt.lazy(new ScriptModule$settings$2(this));

    @NotNull
    public final LinkedHashMap<String, Value<?>> getSettings() {
        return (LinkedHashMap) this.settings$delegate.getValue();
    }

    public ScriptModule(@NotNull JSObject moduleObject) {
        ModuleCategory[] values;
        Intrinsics.checkParameterIsNotNull(moduleObject, "moduleObject");
        this.moduleObject = moduleObject;
        Object member = this.moduleObject.getMember("name");
        if (member == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        setName((String) member);
        Object member2 = this.moduleObject.getMember("description");
        if (member2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        setDescription((String) member2);
        Object member3 = this.moduleObject.getMember("category");
        if (member3 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        String categoryString = (String) member3;
        for (ModuleCategory category : ModuleCategory.values()) {
            if (StringsKt.equals(categoryString, category.getDisplayName(), true)) {
                setCategory(category);
            }
        }
        if (this.moduleObject.hasMember("settings")) {
            Object member4 = this.moduleObject.getMember("settings");
            if (member4 == null) {
                throw new TypeCastException("null cannot be cast to non-null type jdk.nashorn.api.scripting.JSObject");
            }
            JSObject settings = (JSObject) member4;
            for (String settingName : settings.keySet()) {
                LinkedHashMap<String, Value<?>> linkedHashMap = this._values;
                Intrinsics.checkExpressionValueIsNotNull(settingName, "settingName");
                Object member5 = settings.getMember(settingName);
                if (member5 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.value.Value<*>");
                }
                linkedHashMap.put(settingName, (Value) member5);
            }
        }
        if (!this.moduleObject.hasMember("tag")) {
            return;
        }
        Object member6 = this.moduleObject.getMember("tag");
        if (member6 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        this._tag = (String) member6;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    @NotNull
    public List<Value<?>> getValues() {
        Collection<Value<?>> values = this._values.values();
        Intrinsics.checkExpressionValueIsNotNull(values, "_values.values");
        return CollectionsKt.toList(values);
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    @Nullable
    public String getTag() {
        return this._tag;
    }

    public void setTag(@Nullable String value) {
        this._tag = value;
    }

    /* renamed from: on */
    public final void m29on(@NotNull String eventName, @NotNull JSObject handler) {
        Intrinsics.checkParameterIsNotNull(eventName, "eventName");
        Intrinsics.checkParameterIsNotNull(handler, "handler");
        this.events.put(eventName, handler);
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        callEvent$default(this, "enable", null, 2, null);
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        callEvent$default(this, "disable", null, 2, null);
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent updateEvent) {
        Intrinsics.checkParameterIsNotNull(updateEvent, "updateEvent");
        callEvent$default(this, "update", null, 2, null);
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent motionEvent) {
        Intrinsics.checkParameterIsNotNull(motionEvent, "motionEvent");
        callEvent("motion", motionEvent);
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent render2DEvent) {
        Intrinsics.checkParameterIsNotNull(render2DEvent, "render2DEvent");
        callEvent("render2D", render2DEvent);
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent render3DEvent) {
        Intrinsics.checkParameterIsNotNull(render3DEvent, "render3DEvent");
        callEvent("render3D", render3DEvent);
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent packetEvent) {
        Intrinsics.checkParameterIsNotNull(packetEvent, "packetEvent");
        callEvent("packet", packetEvent);
    }

    @EventTarget
    public final void onJump(@NotNull JumpEvent jumpEvent) {
        Intrinsics.checkParameterIsNotNull(jumpEvent, "jumpEvent");
        callEvent("jump", jumpEvent);
    }

    @EventTarget
    public final void onAttack(@NotNull AttackEvent attackEvent) {
        Intrinsics.checkParameterIsNotNull(attackEvent, "attackEvent");
        callEvent("attack", attackEvent);
    }

    @EventTarget
    public final void onKey(@NotNull KeyEvent keyEvent) {
        Intrinsics.checkParameterIsNotNull(keyEvent, "keyEvent");
        callEvent(LocalCacheFactory.KEY, keyEvent);
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent moveEvent) {
        Intrinsics.checkParameterIsNotNull(moveEvent, "moveEvent");
        callEvent("move", moveEvent);
    }

    @EventTarget
    public final void onStep(@NotNull StepEvent stepEvent) {
        Intrinsics.checkParameterIsNotNull(stepEvent, "stepEvent");
        callEvent("step", stepEvent);
    }

    @EventTarget
    public final void onStepConfirm(@NotNull StepConfirmEvent stepConfirmEvent) {
        Intrinsics.checkParameterIsNotNull(stepConfirmEvent, "stepConfirmEvent");
        callEvent$default(this, "stepConfirm", null, 2, null);
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent worldEvent) {
        Intrinsics.checkParameterIsNotNull(worldEvent, "worldEvent");
        callEvent("world", worldEvent);
    }

    @EventTarget
    public final void onSession(@NotNull SessionEvent sessionEvent) {
        Intrinsics.checkParameterIsNotNull(sessionEvent, "sessionEvent");
        callEvent$default(this, "session", null, 2, null);
    }

    @EventTarget
    public final void onClickBlock(@NotNull ClickBlockEvent clickBlockEvent) {
        Intrinsics.checkParameterIsNotNull(clickBlockEvent, "clickBlockEvent");
        callEvent("clickBlock", clickBlockEvent);
    }

    @EventTarget
    public final void onStrafe(@NotNull StrafeEvent strafeEvent) {
        Intrinsics.checkParameterIsNotNull(strafeEvent, "strafeEvent");
        callEvent("strafe", strafeEvent);
    }

    @EventTarget
    public final void onSlowDown(@NotNull SlowDownEvent slowDownEvent) {
        Intrinsics.checkParameterIsNotNull(slowDownEvent, "slowDownEvent");
        callEvent("slowDown", slowDownEvent);
    }

    @EventTarget
    public final void onShutdown(@NotNull ClientShutdownEvent shutdownEvent) {
        Intrinsics.checkParameterIsNotNull(shutdownEvent, "shutdownEvent");
        callEvent$default(this, "shutdown", null, 2, null);
    }

    static /* synthetic */ void callEvent$default(ScriptModule scriptModule, String str, Object obj, int i, Object obj2) {
        if ((i & 2) != 0) {
            obj = null;
        }
        scriptModule.callEvent(str, obj);
    }

    private final void callEvent(String eventName, Object payload) {
        try {
            JSObject jSObject = this.events.get(eventName);
            if (jSObject != null) {
                jSObject.call(this.moduleObject, payload);
            }
        } catch (Throwable throwable) {
            ClientUtils.getLogger().error("[ScriptAPI] Exception in module '" + getName() + "'!", throwable);
        }
    }
}
