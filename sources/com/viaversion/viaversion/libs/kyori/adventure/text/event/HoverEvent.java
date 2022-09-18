package com.viaversion.viaversion.libs.kyori.adventure.text.event;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.key.Keyed;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.api.BinaryTagHolder;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleBuilderApplicable;
import com.viaversion.viaversion.libs.kyori.adventure.text.renderer.ComponentRenderer;
import com.viaversion.viaversion.libs.kyori.adventure.util.Index;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer;
import java.util.Objects;
import java.util.UUID;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/event/HoverEvent.class */
public final class HoverEvent<V> implements Examinable, HoverEventSource<V>, StyleBuilderApplicable {
    private final Action<V> action;
    private final V value;

    @NotNull
    public static HoverEvent<Component> showText(@NotNull final ComponentLike text) {
        return showText(text.asComponent());
    }

    @NotNull
    public static HoverEvent<Component> showText(@NotNull final Component text) {
        return new HoverEvent<>(Action.SHOW_TEXT, text);
    }

    @NotNull
    public static HoverEvent<ShowItem> showItem(@NotNull final Key item, final int count) {
        return showItem(item, count, (BinaryTagHolder) null);
    }

    @NotNull
    public static HoverEvent<ShowItem> showItem(@NotNull final Keyed item, final int count) {
        return showItem(item, count, (BinaryTagHolder) null);
    }

    @NotNull
    public static HoverEvent<ShowItem> showItem(@NotNull final Key item, final int count, @Nullable final BinaryTagHolder nbt) {
        return showItem(ShowItem.m110of(item, count, nbt));
    }

    @NotNull
    public static HoverEvent<ShowItem> showItem(@NotNull final Keyed item, final int count, @Nullable final BinaryTagHolder nbt) {
        return showItem(ShowItem.m108of(item, count, nbt));
    }

    @NotNull
    public static HoverEvent<ShowItem> showItem(@NotNull final ShowItem item) {
        return new HoverEvent<>(Action.SHOW_ITEM, item);
    }

    @NotNull
    public static HoverEvent<ShowEntity> showEntity(@NotNull final Key type, @NotNull final UUID id) {
        return showEntity(type, id, (Component) null);
    }

    @NotNull
    public static HoverEvent<ShowEntity> showEntity(@NotNull final Keyed type, @NotNull final UUID id) {
        return showEntity(type, id, (Component) null);
    }

    @NotNull
    public static HoverEvent<ShowEntity> showEntity(@NotNull final Key type, @NotNull final UUID id, @Nullable final Component name) {
        return showEntity(ShowEntity.m114of(type, id, name));
    }

    @NotNull
    public static HoverEvent<ShowEntity> showEntity(@NotNull final Keyed type, @NotNull final UUID id, @Nullable final Component name) {
        return showEntity(ShowEntity.m112of(type, id, name));
    }

    @NotNull
    public static HoverEvent<ShowEntity> showEntity(@NotNull final ShowEntity entity) {
        return new HoverEvent<>(Action.SHOW_ENTITY, entity);
    }

    @NotNull
    public static <V> HoverEvent<V> hoverEvent(@NotNull final Action<V> action, @NotNull final V value) {
        return new HoverEvent<>(action, value);
    }

    private HoverEvent(@NotNull final Action<V> action, @NotNull final V value) {
        this.action = (Action) Objects.requireNonNull(action, "action");
        this.value = (V) Objects.requireNonNull(value, "value");
    }

    @NotNull
    public Action<V> action() {
        return this.action;
    }

    @NotNull
    public V value() {
        return this.value;
    }

    @NotNull
    public HoverEvent<V> value(@NotNull final V value) {
        return new HoverEvent<>(this.action, value);
    }

    @NotNull
    public <C> HoverEvent<V> withRenderedValue(@NotNull final ComponentRenderer<C> renderer, @NotNull final C context) {
        V oldValue = this.value;
        Object render = ((Action) this.action).renderer.render(renderer, context, oldValue);
        return render != oldValue ? new HoverEvent<>(this.action, render) : this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource
    @NotNull
    public HoverEvent<V> asHoverEvent() {
        return this;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource
    @NotNull
    public HoverEvent<V> asHoverEvent(@NotNull final UnaryOperator<V> op) {
        return op == UnaryOperator.identity() ? this : new HoverEvent<>(this.action, op.apply(this.value));
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleBuilderApplicable
    public void styleApply(final Style.Builder style) {
        style.hoverEvent(this);
    }

    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        HoverEvent<?> that = (HoverEvent) other;
        return this.action == that.action && this.value.equals(that.value);
    }

    public int hashCode() {
        int result = this.action.hashCode();
        return (31 * result) + this.value.hashCode();
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m91of("action", this.action), ExaminableProperty.m91of("value", this.value)});
    }

    public String toString() {
        return (String) examine(StringExaminer.simpleEscaping());
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/event/HoverEvent$ShowItem.class */
    public static final class ShowItem implements Examinable {
        private final Key item;
        private final int count;
        @Nullable
        private final BinaryTagHolder nbt;

        @NotNull
        /* renamed from: of */
        public static ShowItem m111of(@NotNull final Key item, final int count) {
            return m110of(item, count, (BinaryTagHolder) null);
        }

        @NotNull
        /* renamed from: of */
        public static ShowItem m109of(@NotNull final Keyed item, final int count) {
            return m108of(item, count, (BinaryTagHolder) null);
        }

        @NotNull
        /* renamed from: of */
        public static ShowItem m110of(@NotNull final Key item, final int count, @Nullable final BinaryTagHolder nbt) {
            return new ShowItem((Key) Objects.requireNonNull(item, "item"), count, nbt);
        }

        @NotNull
        /* renamed from: of */
        public static ShowItem m108of(@NotNull final Keyed item, final int count, @Nullable final BinaryTagHolder nbt) {
            return new ShowItem(((Keyed) Objects.requireNonNull(item, "item")).key(), count, nbt);
        }

        private ShowItem(@NotNull final Key item, final int count, @Nullable final BinaryTagHolder nbt) {
            this.item = item;
            this.count = count;
            this.nbt = nbt;
        }

        @NotNull
        public Key item() {
            return this.item;
        }

        @NotNull
        public ShowItem item(@NotNull final Key item) {
            return ((Key) Objects.requireNonNull(item, "item")).equals(this.item) ? this : new ShowItem(item, this.count, this.nbt);
        }

        public int count() {
            return this.count;
        }

        @NotNull
        public ShowItem count(final int count) {
            return count == this.count ? this : new ShowItem(this.item, count, this.nbt);
        }

        @Nullable
        public BinaryTagHolder nbt() {
            return this.nbt;
        }

        @NotNull
        public ShowItem nbt(@Nullable final BinaryTagHolder nbt) {
            return Objects.equals(nbt, this.nbt) ? this : new ShowItem(this.item, this.count, nbt);
        }

        public boolean equals(@Nullable final Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            ShowItem that = (ShowItem) other;
            return this.item.equals(that.item) && this.count == that.count && Objects.equals(this.nbt, that.nbt);
        }

        public int hashCode() {
            int result = this.item.hashCode();
            return (31 * ((31 * result) + Integer.hashCode(this.count))) + Objects.hashCode(this.nbt);
        }

        @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
        @NotNull
        public Stream<? extends ExaminableProperty> examinableProperties() {
            return Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m91of("item", this.item), ExaminableProperty.m93of("count", this.count), ExaminableProperty.m91of("nbt", this.nbt)});
        }

        public String toString() {
            return (String) examine(StringExaminer.simpleEscaping());
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/event/HoverEvent$ShowEntity.class */
    public static final class ShowEntity implements Examinable {
        private final Key type;

        /* renamed from: id */
        private final UUID f183id;
        private final Component name;

        @NotNull
        /* renamed from: of */
        public static ShowEntity m115of(@NotNull final Key type, @NotNull final UUID id) {
            return m114of(type, id, (Component) null);
        }

        @NotNull
        /* renamed from: of */
        public static ShowEntity m113of(@NotNull final Keyed type, @NotNull final UUID id) {
            return m112of(type, id, (Component) null);
        }

        @NotNull
        /* renamed from: of */
        public static ShowEntity m114of(@NotNull final Key type, @NotNull final UUID id, @Nullable final Component name) {
            return new ShowEntity((Key) Objects.requireNonNull(type, "type"), (UUID) Objects.requireNonNull(id, "id"), name);
        }

        @NotNull
        /* renamed from: of */
        public static ShowEntity m112of(@NotNull final Keyed type, @NotNull final UUID id, @Nullable final Component name) {
            return new ShowEntity(((Keyed) Objects.requireNonNull(type, "type")).key(), (UUID) Objects.requireNonNull(id, "id"), name);
        }

        private ShowEntity(@NotNull final Key type, @NotNull final UUID id, @Nullable final Component name) {
            this.type = type;
            this.f183id = id;
            this.name = name;
        }

        @NotNull
        public Key type() {
            return this.type;
        }

        @NotNull
        public ShowEntity type(@NotNull final Key type) {
            return ((Key) Objects.requireNonNull(type, "type")).equals(this.type) ? this : new ShowEntity(type, this.f183id, this.name);
        }

        @NotNull
        public ShowEntity type(@NotNull final Keyed type) {
            return type(((Keyed) Objects.requireNonNull(type, "type")).key());
        }

        @NotNull
        /* renamed from: id */
        public UUID m117id() {
            return this.f183id;
        }

        @NotNull
        /* renamed from: id */
        public ShowEntity m116id(@NotNull final UUID id) {
            return ((UUID) Objects.requireNonNull(id)).equals(this.f183id) ? this : new ShowEntity(this.type, id, this.name);
        }

        @Nullable
        public Component name() {
            return this.name;
        }

        @NotNull
        public ShowEntity name(@Nullable final Component name) {
            return Objects.equals(name, this.name) ? this : new ShowEntity(this.type, this.f183id, name);
        }

        public boolean equals(@Nullable final Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            ShowEntity that = (ShowEntity) other;
            return this.type.equals(that.type) && this.f183id.equals(that.f183id) && Objects.equals(this.name, that.name);
        }

        public int hashCode() {
            int result = this.type.hashCode();
            return (31 * ((31 * result) + this.f183id.hashCode())) + Objects.hashCode(this.name);
        }

        @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
        @NotNull
        public Stream<? extends ExaminableProperty> examinableProperties() {
            return Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m91of("type", this.type), ExaminableProperty.m91of("id", this.f183id), ExaminableProperty.m91of("name", this.name)});
        }

        public String toString() {
            return (String) examine(StringExaminer.simpleEscaping());
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/event/HoverEvent$Action.class */
    public static final class Action<V> {
        public static final Action<Component> SHOW_TEXT = new Action<>("show_text", Component.class, true, new Renderer<Component>() { // from class: com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent.Action.1
            @Override // com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent.Action.Renderer
            @NotNull
            public /* bridge */ /* synthetic */ Component render(@NotNull final ComponentRenderer renderer, @NotNull final Object context, @NotNull final Component value) {
                return render2((ComponentRenderer<ComponentRenderer>) renderer, (ComponentRenderer) context, value);
            }

            @NotNull
            /* renamed from: render */
            public <C> Component render2(@NotNull final ComponentRenderer<C> renderer, @NotNull final C context, @NotNull final Component value) {
                return renderer.render(value, context);
            }
        });
        public static final Action<ShowItem> SHOW_ITEM = new Action<>("show_item", ShowItem.class, true, new Renderer<ShowItem>() { // from class: com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent.Action.2
            @Override // com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent.Action.Renderer
            @NotNull
            public /* bridge */ /* synthetic */ ShowItem render(@NotNull final ComponentRenderer renderer, @NotNull final Object context, @NotNull final ShowItem value) {
                return render2((ComponentRenderer<ComponentRenderer>) renderer, (ComponentRenderer) context, value);
            }

            @NotNull
            /* renamed from: render */
            public <C> ShowItem render2(@NotNull final ComponentRenderer<C> renderer, @NotNull final C context, @NotNull final ShowItem value) {
                return value;
            }
        });
        public static final Action<ShowEntity> SHOW_ENTITY = new Action<>("show_entity", ShowEntity.class, true, new Renderer<ShowEntity>() { // from class: com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent.Action.3
            @Override // com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent.Action.Renderer
            @NotNull
            public /* bridge */ /* synthetic */ ShowEntity render(@NotNull final ComponentRenderer renderer, @NotNull final Object context, @NotNull final ShowEntity value) {
                return render2((ComponentRenderer<ComponentRenderer>) renderer, (ComponentRenderer) context, value);
            }

            @NotNull
            /* renamed from: render */
            public <C> ShowEntity render2(@NotNull final ComponentRenderer<C> renderer, @NotNull final C context, @NotNull final ShowEntity value) {
                return value.name == null ? value : value.name(renderer.render(value.name, context));
            }
        });
        public static final Index<String, Action<?>> NAMES = Index.create(constant -> {
            return constant.name;
        }, SHOW_TEXT, SHOW_ITEM, SHOW_ENTITY);
        private final String name;
        private final Class<V> type;
        private final boolean readable;
        private final Renderer<V> renderer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @FunctionalInterface
        /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/event/HoverEvent$Action$Renderer.class */
        public interface Renderer<V> {
            @NotNull
            <C> V render(@NotNull final ComponentRenderer<C> renderer, @NotNull final C context, @NotNull final V value);
        }

        Action(final String name, final Class<V> type, final boolean readable, final Renderer<V> renderer) {
            this.name = name;
            this.type = type;
            this.readable = readable;
            this.renderer = renderer;
        }

        @NotNull
        public Class<V> type() {
            return this.type;
        }

        public boolean readable() {
            return this.readable;
        }

        @NotNull
        public String toString() {
            return this.name;
        }
    }
}
