package net.ccbluex.liquidbounce.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.util.Constants;

/* compiled from: EventManager.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��6\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0006J\u000e\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u000fRF\u0010\u0003\u001a:\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0004j\u001c\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u0007`\tX\u0082\u0004¢\u0006\u0002\n��¨\u0006\u0012"}, m53d2 = {"Lnet/ccbluex/liquidbounce/event/EventManager;", "", "()V", "registry", "Ljava/util/HashMap;", Constants.CLASS, "Lnet/ccbluex/liquidbounce/event/Event;", "", "Lnet/ccbluex/liquidbounce/event/EventHook;", "Lkotlin/collections/HashMap;", "callEvent", "", "event", "registerListener", "listener", "Lnet/ccbluex/liquidbounce/event/Listenable;", "unregisterListener", "listenable", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/event/EventManager.class */
public final class EventManager {
    @NotNull
    private final HashMap<Class<? extends Event>, List<EventHook>> registry = new HashMap<>();

    /* JADX WARN: Multi-variable type inference failed */
    public final void registerListener(@NotNull Listenable listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        Method[] declaredMethods = listener.getClass().getDeclaredMethods();
        Intrinsics.checkNotNullExpressionValue(declaredMethods, "listener.javaClass.declaredMethods");
        int i = 0;
        int length = declaredMethods.length;
        while (i < length) {
            Method method = declaredMethods[i];
            i++;
            if (method.isAnnotationPresent(EventTarget.class) && method.getParameterTypes().length == 1) {
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                Class eventClass = method.getParameterTypes()[0];
                if (eventClass == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.Class<out net.ccbluex.liquidbounce.event.Event>");
                }
                EventTarget eventTarget = (EventTarget) method.getAnnotation(EventTarget.class);
                List list = this.registry.get(eventClass);
                if (list == null) {
                    list = new ArrayList();
                }
                List invokableEventTargets = list;
                try {
                    Intrinsics.checkNotNullExpressionValue(method, "method");
                    int priority = eventTarget.priority();
                    Intrinsics.checkNotNullExpressionValue(eventTarget, "eventTarget");
                    invokableEventTargets.add(new EventHook(listener, method, priority, eventTarget));
                } catch (Exception e) {
                    e.printStackTrace();
                    Intrinsics.checkNotNullExpressionValue(method, "method");
                    Intrinsics.checkNotNullExpressionValue(eventTarget, "eventTarget");
                    invokableEventTargets.add(new EventHook(listener, method, eventTarget));
                }
                if (invokableEventTargets.size() > 1) {
                    CollectionsKt.sortWith(invokableEventTargets, new Comparator() { // from class: net.ccbluex.liquidbounce.event.EventManager$registerListener$$inlined$sortBy$1
                        @Override // java.util.Comparator
                        public final int compare(T t, T t2) {
                            EventHook it = (EventHook) t;
                            EventHook it2 = (EventHook) t2;
                            return ComparisonsKt.compareValues(Integer.valueOf(it.getPriority()), Integer.valueOf(it2.getPriority()));
                        }
                    });
                }
                this.registry.put(eventClass, invokableEventTargets);
            }
        }
    }

    public final void unregisterListener(@NotNull Listenable listenable) {
        Intrinsics.checkNotNullParameter(listenable, "listenable");
        for (Map.Entry<Class<? extends Event>, List<EventHook>> entry : this.registry.entrySet()) {
            Class key = entry.getKey();
            List targets = entry.getValue();
            targets.removeIf((v1) -> {
                return m2759unregisterListener$lambda2(r1, v1);
            });
            this.registry.put(key, targets);
        }
    }

    /* renamed from: unregisterListener$lambda-2 */
    private static final boolean m2759unregisterListener$lambda2(Listenable listenable, EventHook it) {
        Intrinsics.checkNotNullParameter(listenable, "$listenable");
        Intrinsics.checkNotNullParameter(it, "it");
        return Intrinsics.areEqual(it.getEventClass(), listenable);
    }

    public final void callEvent(@NotNull Event event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Iterable targets = (List) this.registry.get(event.getClass());
        if (targets == null) {
            return;
        }
        Iterable $this$filter$iv = targets;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            EventHook it = (EventHook) element$iv$iv;
            if (it.getEventClass().handleEvents() || it.isIgnoreCondition()) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $this$forEach$iv = (List) destination$iv$iv;
        for (Object element$iv : $this$forEach$iv) {
            EventHook it2 = (EventHook) element$iv;
            try {
                it2.getMethod().invoke(it2.getEventClass(), event);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}
