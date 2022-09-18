package net.ccbluex.liquidbounce.features.module;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.features.module.modules.misc.AutoDisable;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.Value;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Module.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0018\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\n\b\u0016\u0018��2\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010G\u001a\u00020H2\u0006\u0010I\u001a\u00020\"H\u0004J\u0016\u0010J\u001a\b\u0012\u0002\b\u0003\u0018\u00010E2\u0006\u0010K\u001a\u00020\"H\u0016J\b\u0010L\u001a\u00020\u000bH\u0016J\b\u0010M\u001a\u00020HH\u0016J\b\u0010N\u001a\u00020HH\u0016J\b\u0010O\u001a\u00020HH\u0016J\u0010\u0010P\u001a\u00020H2\u0006\u0010>\u001a\u00020\u000bH\u0016J\u0006\u0010Q\u001a\u00020HR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR$\u0010\n\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b@FX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0011\u0010\u0007\"\u0004\b\u0012\u0010\tR \u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u001a\u0010\u001b\u001a\u00020\u001cX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u000e\u0010'\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u0011\u0010(\u001a\u00020\u0005¢\u0006\b\n��\u001a\u0004\b)\u0010\u0007R$\u0010*\u001a\u00020+2\u0006\u0010*\u001a\u00020+@FX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R\u001a\u00100\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b1\u0010$\"\u0004\b2\u0010&R\u000e\u00103\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u001a\u00104\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b5\u0010\u0007\"\u0004\b6\u0010\tR\u001a\u00107\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b8\u0010\u0007\"\u0004\b9\u0010\tR\u001a\u0010:\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b;\u0010$\"\u0004\b<\u0010&R$\u0010>\u001a\u00020\u000b2\u0006\u0010=\u001a\u00020\u000b@FX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b?\u0010\r\"\u0004\b@\u0010\u000fR\u0016\u0010A\u001a\u0004\u0018\u00010\"8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bB\u0010$R\u001e\u0010C\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030E0D8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bF\u0010\u0017¨\u0006R"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/Module;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "animation", "", "getAnimation", "()F", "setAnimation", "(F)V", "array", "", "getArray", "()Z", "setArray", "(Z)V", "arrayY", "getArrayY", "setArrayY", "autoDisables", "", "Lnet/ccbluex/liquidbounce/features/module/modules/misc/AutoDisable$DisableEvent;", "getAutoDisables", "()Ljava/util/List;", "setAutoDisables", "(Ljava/util/List;)V", "canEnable", "category", "Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;", "getCategory", "()Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;", "setCategory", "(Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;)V", "description", "", "getDescription", "()Ljava/lang/String;", "setDescription", "(Ljava/lang/String;)V", "forceNoSound", "hue", "getHue", "keyBind", "", "getKeyBind", "()I", "setKeyBind", "(I)V", "name", "getName", "setName", "onlyEnable", "slide", "getSlide", "setSlide", "slideStep", "getSlideStep", "setSlideStep", "spacedName", "getSpacedName", "setSpacedName", "value", "state", "getState", "setState", "tag", "getTag", "values", "", "Lnet/ccbluex/liquidbounce/value/Value;", "getValues", "chat", "", "msg", "getValue", "valueName", "handleEvents", "onDisable", "onEnable", "onInitialize", "onToggle", "toggle", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/Module.class */
public class Module extends MinecraftInstance implements Listenable {
    @NotNull
    private String name;
    @NotNull
    private String spacedName;
    @NotNull
    private String description;
    @NotNull
    private ModuleCategory category;
    private int keyBind;
    private final boolean canEnable;
    private final boolean onlyEnable;
    private final boolean forceNoSound;
    private float slideStep;
    private float animation;
    private boolean state;
    private final float hue;
    private float slide;
    private float arrayY;
    private boolean array = true;
    @NotNull
    private List<AutoDisable.DisableEvent> autoDisables = new ArrayList();

    public Module() {
        Annotation annotation = getClass().getAnnotation(ModuleInfo.class);
        Intrinsics.checkNotNull(annotation);
        ModuleInfo moduleInfo = (ModuleInfo) annotation;
        this.name = moduleInfo.name();
        this.spacedName = Intrinsics.areEqual(moduleInfo.spacedName(), "") ? this.name : moduleInfo.spacedName();
        this.description = moduleInfo.description();
        this.category = moduleInfo.category();
        setKeyBind(moduleInfo.keyBind());
        setArray(moduleInfo.array());
        this.canEnable = moduleInfo.canEnable();
        this.onlyEnable = moduleInfo.onlyEnable();
        this.forceNoSound = moduleInfo.forceNoSound();
        this.hue = (float) Math.random();
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    public final void setName(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.name = str;
    }

    @NotNull
    public final String getSpacedName() {
        return this.spacedName;
    }

    public final void setSpacedName(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.spacedName = str;
    }

    @NotNull
    public final String getDescription() {
        return this.description;
    }

    public final void setDescription(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.description = str;
    }

    @NotNull
    public final ModuleCategory getCategory() {
        return this.category;
    }

    public final void setCategory(@NotNull ModuleCategory moduleCategory) {
        Intrinsics.checkNotNullParameter(moduleCategory, "<set-?>");
        this.category = moduleCategory;
    }

    public final int getKeyBind() {
        return this.keyBind;
    }

    public final void setKeyBind(int keyBind) {
        this.keyBind = keyBind;
        if (!LiquidBounce.INSTANCE.isStarting()) {
            LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().modulesConfig);
        }
    }

    public final boolean getArray() {
        return this.array;
    }

    public final void setArray(boolean array) {
        this.array = array;
        if (!LiquidBounce.INSTANCE.isStarting()) {
            LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().modulesConfig);
        }
    }

    public final float getSlideStep() {
        return this.slideStep;
    }

    public final void setSlideStep(float f) {
        this.slideStep = f;
    }

    public final float getAnimation() {
        return this.animation;
    }

    public final void setAnimation(float f) {
        this.animation = f;
    }

    @NotNull
    public final List<AutoDisable.DisableEvent> getAutoDisables() {
        return this.autoDisables;
    }

    public final void setAutoDisables(@NotNull List<AutoDisable.DisableEvent> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.autoDisables = list;
    }

    public final boolean getState() {
        return this.state;
    }

    public final void setState(boolean value) {
        if (this.state == value || !this.canEnable) {
            return;
        }
        onToggle(value);
        if (!LiquidBounce.INSTANCE.isStarting() && !this.forceNoSound) {
            switch (LiquidBounce.INSTANCE.getModuleManager().getToggleSoundMode()) {
                case 1:
                    MinecraftInstance.f362mc.func_147118_V().func_147682_a(PositionedSoundRecord.func_147674_a(new ResourceLocation("random.click"), 1.0f));
                    break;
                case 2:
                    (value ? LiquidBounce.INSTANCE.getTipSoundManager().getEnableSound() : LiquidBounce.INSTANCE.getTipSoundManager().getDisableSound()).asyncPlay(LiquidBounce.INSTANCE.getModuleManager().getToggleVolume());
                    break;
            }
            if (LiquidBounce.INSTANCE.getModuleManager().getShouldNotify()) {
                LiquidBounce.INSTANCE.getHud().addNotification(new Notification((value ? "Enabled" : "Disabled") + " §r" + this.name, value ? Notification.Type.SUCCESS : Notification.Type.ERROR, 1000L));
            }
        }
        if (value) {
            onEnable();
            if (!this.onlyEnable) {
                this.state = true;
            }
        } else {
            onDisable();
            this.state = false;
        }
        LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().modulesConfig);
    }

    public final float getHue() {
        return this.hue;
    }

    public final float getSlide() {
        return this.slide;
    }

    public final void setSlide(float f) {
        this.slide = f;
    }

    public final float getArrayY() {
        return this.arrayY;
    }

    public final void setArrayY(float f) {
        this.arrayY = f;
    }

    @Nullable
    public String getTag() {
        return null;
    }

    public final void toggle() {
        setState(!this.state);
    }

    protected final void chat(@NotNull String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        ClientUtils.displayChatMessage(Intrinsics.stringPlus("§8[§9§lJackey§8] §3", msg));
    }

    public void onToggle(boolean state) {
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void onInitialize() {
    }

    @Nullable
    public Value<?> getValue(@NotNull String valueName) {
        Object obj;
        Intrinsics.checkNotNullParameter(valueName, "valueName");
        Iterator<T> it = getValues().iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            Object next = it.next();
            Value it2 = (Value) next;
            if (StringsKt.equals(it2.getName(), valueName, true)) {
                obj = next;
                break;
            }
        }
        return (Value) obj;
    }

    @NotNull
    public List<Value<?>> getValues() {
        Object[] declaredFields = getClass().getDeclaredFields();
        Intrinsics.checkNotNullExpressionValue(declaredFields, "javaClass.declaredFields");
        Object[] $this$map$iv = declaredFields;
        Collection destination$iv$iv = new ArrayList($this$map$iv.length);
        int i = 0;
        int length = $this$map$iv.length;
        while (i < length) {
            Object item$iv$iv = $this$map$iv[i];
            i++;
            Field valueField = (Field) item$iv$iv;
            valueField.setAccessible(true);
            destination$iv$iv.add(valueField.get(this));
        }
        Iterable $this$filterIsInstance$iv = (List) destination$iv$iv;
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv : $this$filterIsInstance$iv) {
            if (element$iv$iv instanceof Value) {
                destination$iv$iv2.add(element$iv$iv);
            }
        }
        return (List) destination$iv$iv2;
    }

    @Override // net.ccbluex.liquidbounce.event.Listenable
    public boolean handleEvents() {
        return this.state;
    }
}
