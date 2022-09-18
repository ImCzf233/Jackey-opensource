package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.JoinConfiguration;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/JoinConfigurationImpl.class */
public final class JoinConfigurationImpl implements JoinConfiguration {
    static final Function<ComponentLike, Component> DEFAULT_CONVERTOR = (v0) -> {
        return v0.asComponent();
    };
    static final Predicate<ComponentLike> DEFAULT_PREDICATE = componentLike -> {
        return true;
    };
    static final JoinConfigurationImpl NULL = new JoinConfigurationImpl();
    private final Component prefix;
    private final Component suffix;
    private final Component separator;
    private final Component lastSeparator;
    private final Component lastSeparatorIfSerial;
    private final Function<ComponentLike, Component> convertor;
    private final Predicate<ComponentLike> predicate;

    private JoinConfigurationImpl() {
        this.prefix = null;
        this.suffix = null;
        this.separator = null;
        this.lastSeparator = null;
        this.lastSeparatorIfSerial = null;
        this.convertor = DEFAULT_CONVERTOR;
        this.predicate = DEFAULT_PREDICATE;
    }

    private JoinConfigurationImpl(@NotNull final BuilderImpl builder) {
        this.prefix = builder.prefix == null ? null : builder.prefix.asComponent();
        this.suffix = builder.suffix == null ? null : builder.suffix.asComponent();
        this.separator = builder.separator == null ? null : builder.separator.asComponent();
        this.lastSeparator = builder.lastSeparator == null ? null : builder.lastSeparator.asComponent();
        this.lastSeparatorIfSerial = builder.lastSeparatorIfSerial == null ? null : builder.lastSeparatorIfSerial.asComponent();
        this.convertor = builder.convertor;
        this.predicate = builder.predicate;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.JoinConfiguration
    @Nullable
    public Component prefix() {
        return this.prefix;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.JoinConfiguration
    @Nullable
    public Component suffix() {
        return this.suffix;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.JoinConfiguration
    @Nullable
    public Component separator() {
        return this.separator;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.JoinConfiguration
    @Nullable
    public Component lastSeparator() {
        return this.lastSeparator;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.JoinConfiguration
    @Nullable
    public Component lastSeparatorIfSerial() {
        return this.lastSeparatorIfSerial;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.JoinConfiguration
    @NotNull
    public Function<ComponentLike, Component> convertor() {
        return this.convertor;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.JoinConfiguration
    @NotNull
    public Predicate<ComponentLike> predicate() {
        return this.predicate;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.util.Buildable
    public JoinConfiguration.Builder toBuilder() {
        return new BuilderImpl();
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m91of("prefix", this.prefix), ExaminableProperty.m91of("suffix", this.suffix), ExaminableProperty.m91of("separator", this.separator), ExaminableProperty.m91of("lastSeparator", this.lastSeparator), ExaminableProperty.m91of("lastSeparatorIfSerial", this.lastSeparatorIfSerial), ExaminableProperty.m91of("convertor", this.convertor), ExaminableProperty.m91of("predicate", this.predicate)});
    }

    public String toString() {
        return (String) examine(StringExaminer.simpleEscaping());
    }

    @Contract(pure = true)
    @NotNull
    public static Component join(@NotNull final JoinConfiguration config, @NotNull final Iterable<? extends ComponentLike> components) {
        Objects.requireNonNull(config, "config");
        Objects.requireNonNull(components, "components");
        Iterator<? extends ComponentLike> it = components.iterator();
        Component prefix = config.prefix();
        Component suffix = config.suffix();
        Function<ComponentLike, Component> convertor = config.convertor();
        Predicate<ComponentLike> predicate = config.predicate();
        if (!it.hasNext()) {
            return singleElementJoin(config, null);
        }
        ComponentLike component = (ComponentLike) Objects.requireNonNull(it.next(), "Null elements in \"components\" are not allowed");
        int componentsSeen = 0;
        if (!it.hasNext()) {
            return singleElementJoin(config, component);
        }
        Component separator = config.separator();
        boolean hasSeparator = separator != null;
        TextComponent.Builder builder = Component.text();
        if (prefix != null) {
            builder.append(prefix);
        }
        while (component != null) {
            if (!predicate.test(component)) {
                if (!it.hasNext()) {
                    break;
                }
                component = it.next();
            } else {
                builder.append((Component) Objects.requireNonNull(convertor.apply(component), "Null output from \"convertor\" is not allowed"));
                componentsSeen++;
                if (!it.hasNext()) {
                    component = null;
                } else {
                    component = (ComponentLike) Objects.requireNonNull(it.next(), "Null elements in \"components\" are not allowed");
                    if (it.hasNext()) {
                        if (hasSeparator) {
                            builder.append(separator);
                        }
                    } else {
                        Component lastSeparator = null;
                        if (componentsSeen > 1) {
                            lastSeparator = config.lastSeparatorIfSerial();
                        }
                        if (lastSeparator == null) {
                            lastSeparator = config.lastSeparator();
                        }
                        if (lastSeparator == null) {
                            lastSeparator = config.separator();
                        }
                        if (lastSeparator != null) {
                            builder.append(lastSeparator);
                        }
                    }
                }
            }
        }
        if (suffix != null) {
            builder.append(suffix);
        }
        return builder.build();
    }

    @NotNull
    static Component singleElementJoin(@NotNull final JoinConfiguration config, @Nullable final ComponentLike component) {
        Component prefix = config.prefix();
        Component suffix = config.suffix();
        Function<ComponentLike, Component> convertor = config.convertor();
        Predicate<ComponentLike> predicate = config.predicate();
        if (prefix == null && suffix == null) {
            if (component == null || !predicate.test(component)) {
                return Component.empty();
            }
            return convertor.apply(component);
        }
        TextComponent.Builder builder = Component.text();
        if (prefix != null) {
            builder.append(prefix);
        }
        if (component != null && predicate.test(component)) {
            builder.append(convertor.apply(component));
        }
        if (suffix != null) {
            builder.append(suffix);
        }
        return builder.build();
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/JoinConfigurationImpl$BuilderImpl.class */
    public static final class BuilderImpl implements JoinConfiguration.Builder {
        private ComponentLike prefix;
        private ComponentLike suffix;
        private ComponentLike separator;
        private ComponentLike lastSeparator;
        private ComponentLike lastSeparatorIfSerial;
        private Function<ComponentLike, Component> convertor;
        private Predicate<ComponentLike> predicate;

        public BuilderImpl() {
            this(JoinConfigurationImpl.NULL);
        }

        private BuilderImpl(@NotNull final JoinConfigurationImpl joinConfig) {
            this.separator = joinConfig.separator;
            this.lastSeparator = joinConfig.lastSeparator;
            this.prefix = joinConfig.prefix;
            this.suffix = joinConfig.suffix;
            this.convertor = joinConfig.convertor;
            this.lastSeparatorIfSerial = joinConfig.lastSeparatorIfSerial;
            this.predicate = joinConfig.predicate;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.JoinConfiguration.Builder
        @NotNull
        public JoinConfiguration.Builder prefix(@Nullable final ComponentLike prefix) {
            this.prefix = prefix;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.JoinConfiguration.Builder
        @NotNull
        public JoinConfiguration.Builder suffix(@Nullable final ComponentLike suffix) {
            this.suffix = suffix;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.JoinConfiguration.Builder
        @NotNull
        public JoinConfiguration.Builder separator(@Nullable final ComponentLike separator) {
            this.separator = separator;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.JoinConfiguration.Builder
        @NotNull
        public JoinConfiguration.Builder lastSeparator(@Nullable final ComponentLike lastSeparator) {
            this.lastSeparator = lastSeparator;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.JoinConfiguration.Builder
        @NotNull
        public JoinConfiguration.Builder lastSeparatorIfSerial(@Nullable final ComponentLike lastSeparatorIfSerial) {
            this.lastSeparatorIfSerial = lastSeparatorIfSerial;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.JoinConfiguration.Builder
        @NotNull
        public JoinConfiguration.Builder convertor(@NotNull final Function<ComponentLike, Component> convertor) {
            this.convertor = (Function) Objects.requireNonNull(convertor, "convertor");
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.JoinConfiguration.Builder
        @NotNull
        public JoinConfiguration.Builder predicate(@NotNull final Predicate<ComponentLike> predicate) {
            this.predicate = (Predicate) Objects.requireNonNull(predicate, "predicate");
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.util.Buildable.Builder
        @NotNull
        public JoinConfiguration build() {
            return new JoinConfigurationImpl(this);
        }
    }
}
