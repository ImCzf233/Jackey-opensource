package com.viaversion.viaversion.libs.kyori.adventure.text.flattener;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.KeybindComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.SelectorComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.flattener.ComponentFlattener;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/flattener/ComponentFlattenerImpl.class */
public final class ComponentFlattenerImpl implements ComponentFlattener {
    static final ComponentFlattener BASIC = new BuilderImpl().mapper(KeybindComponent.class, component -> {
        return component.keybind();
    }).mapper(ScoreComponent.class, (v0) -> {
        return v0.value();
    }).mapper(SelectorComponent.class, (v0) -> {
        return v0.pattern();
    }).mapper(TextComponent.class, (v0) -> {
        return v0.content();
    }).mapper(TranslatableComponent.class, (v0) -> {
        return v0.key();
    }).build();
    static final ComponentFlattener TEXT_ONLY = new BuilderImpl().mapper(TextComponent.class, (v0) -> {
        return v0.content();
    }).build();
    private static final int MAX_DEPTH = 512;
    private final Map<Class<?>, Function<?, String>> flatteners;
    private final Map<Class<?>, BiConsumer<?, Consumer<Component>>> complexFlatteners;
    private final ConcurrentMap<Class<?>, Handler> propagatedFlatteners = new ConcurrentHashMap();
    private final Function<Component, String> unknownHandler;

    @FunctionalInterface
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/flattener/ComponentFlattenerImpl$Handler.class */
    public interface Handler {
        public static final Handler NONE = input, listener, depth -> {
        };

        void handle(final Component input, final FlattenerListener listener, final int depth);
    }

    ComponentFlattenerImpl(final Map<Class<?>, Function<?, String>> flatteners, final Map<Class<?>, BiConsumer<?, Consumer<Component>>> complexFlatteners, @Nullable final Function<Component, String> unknownHandler) {
        this.flatteners = Collections.unmodifiableMap(new HashMap(flatteners));
        this.complexFlatteners = Collections.unmodifiableMap(new HashMap(complexFlatteners));
        this.unknownHandler = unknownHandler;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.flattener.ComponentFlattener
    public void flatten(@NotNull final Component input, @NotNull final FlattenerListener listener) {
        flatten0(input, listener, 0);
    }

    private void flatten0(@NotNull final Component input, @NotNull final FlattenerListener listener, final int depth) {
        Objects.requireNonNull(input, "input");
        Objects.requireNonNull(listener, "listener");
        if (input == Component.empty()) {
            return;
        }
        if (depth > 512) {
            throw new IllegalStateException("Exceeded maximum depth of 512 while attempting to flatten components!");
        }
        Handler flattener = flattener(input);
        Style inputStyle = input.style();
        listener.pushStyle(inputStyle);
        if (flattener != null) {
            try {
                flattener.handle(input, listener, depth + 1);
            } finally {
                listener.popStyle(inputStyle);
            }
        }
        if (!input.children().isEmpty()) {
            for (Component child : input.children()) {
                flatten0(child, listener, depth + 1);
            }
        }
    }

    @Nullable
    private <T extends Component> Handler flattener(final T test) {
        Handler flattener = this.propagatedFlatteners.computeIfAbsent(test.getClass(), key -> {
            Function<?, String> function = this.flatteners.get(key);
            if (function != null) {
                return component, listener, depth -> {
                    listener.component((String) function.apply(component));
                };
            }
            for (Map.Entry<Class<?>, Function<?, String>> entry : this.flatteners.entrySet()) {
                if (entry.getKey().isAssignableFrom(key)) {
                    return component2, listener2, depth2 -> {
                        listener2.component((String) ((Function) entry.getValue()).apply(component2));
                    };
                }
            }
            BiConsumer<?, Consumer<Component>> biConsumer = this.complexFlatteners.get(key);
            if (biConsumer != null) {
                return component3, listener3, depth3 -> {
                    complexValue.accept(biConsumer, c -> {
                        flatten0(depth3, listener, listener3);
                    });
                };
            }
            for (Map.Entry<Class<?>, BiConsumer<?, Consumer<Component>>> entry2 : this.complexFlatteners.entrySet()) {
                if (entry2.getKey().isAssignableFrom(key)) {
                    return component4, listener4, depth4 -> {
                        ((BiConsumer) entry.getValue()).accept(entry2, c -> {
                            flatten0(depth4, listener, listener4);
                        });
                    };
                }
            }
            return Handler.NONE;
        });
        if (flattener == Handler.NONE) {
            if (this.unknownHandler != null) {
                return component, listener, depth -> {
                    this.unknownHandler.apply(component);
                };
            }
            return null;
        }
        return flattener;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.util.Buildable
    public ComponentFlattener.Builder toBuilder() {
        return new BuilderImpl(this.flatteners, this.complexFlatteners, this.unknownHandler);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/flattener/ComponentFlattenerImpl$BuilderImpl.class */
    public static final class BuilderImpl implements ComponentFlattener.Builder {
        private final Map<Class<?>, Function<?, String>> flatteners;
        private final Map<Class<?>, BiConsumer<?, Consumer<Component>>> complexFlatteners;
        @Nullable
        private Function<Component, String> unknownHandler;

        public BuilderImpl() {
            this.flatteners = new HashMap();
            this.complexFlatteners = new HashMap();
        }

        BuilderImpl(final Map<Class<?>, Function<?, String>> flatteners, final Map<Class<?>, BiConsumer<?, Consumer<Component>>> complexFlatteners, @Nullable final Function<Component, String> unknownHandler) {
            this.flatteners = new HashMap(flatteners);
            this.complexFlatteners = new HashMap(complexFlatteners);
            this.unknownHandler = unknownHandler;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.util.Buildable.Builder
        @NotNull
        public ComponentFlattener build() {
            return new ComponentFlattenerImpl(this.flatteners, this.complexFlatteners, this.unknownHandler);
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.flattener.ComponentFlattener.Builder
        public <T extends Component> ComponentFlattener.Builder mapper(@NotNull final Class<T> type, @NotNull final Function<T, String> converter) {
            validateNoneInHierarchy((Class) Objects.requireNonNull(type, "type"));
            this.flatteners.put(type, (Function) Objects.requireNonNull(converter, "converter"));
            this.complexFlatteners.remove(type);
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.flattener.ComponentFlattener.Builder
        public <T extends Component> ComponentFlattener.Builder complexMapper(@NotNull final Class<T> type, @NotNull final BiConsumer<T, Consumer<Component>> converter) {
            validateNoneInHierarchy((Class) Objects.requireNonNull(type, "type"));
            this.complexFlatteners.put(type, (BiConsumer) Objects.requireNonNull(converter, "converter"));
            this.flatteners.remove(type);
            return this;
        }

        private void validateNoneInHierarchy(final Class<? extends Component> beingRegistered) {
            for (Class<?> clazz : this.flatteners.keySet()) {
                testHierarchy(clazz, beingRegistered);
            }
            for (Class<?> clazz2 : this.complexFlatteners.keySet()) {
                testHierarchy(clazz2, beingRegistered);
            }
        }

        private static void testHierarchy(final Class<?> existing, final Class<?> beingRegistered) {
            if (!existing.equals(beingRegistered)) {
                if (existing.isAssignableFrom(beingRegistered) || beingRegistered.isAssignableFrom(existing)) {
                    throw new IllegalArgumentException("Conflict detected between already registered type " + existing + " and newly registered type " + beingRegistered + "! Types in a component flattener must not share a common hierachy!");
                }
            }
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.flattener.ComponentFlattener.Builder
        public ComponentFlattener.Builder unknownMapper(@Nullable final Function<Component, String> converter) {
            this.unknownHandler = converter;
            return this;
        }
    }
}
