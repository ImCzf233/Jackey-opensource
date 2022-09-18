package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.TextReplacementConfig;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jetbrains.annotations.Debug;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Debug.Renderer(text = "this.debuggerString()", childrenArray = "this.children().toArray()", hasChildren = "!this.children().isEmpty()")
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/AbstractComponent.class */
public abstract class AbstractComponent implements Component {
    private static final Predicate<Component> NOT_EMPTY = component -> {
        return component != Component.empty();
    };
    protected final List<Component> children;
    protected final Style style;

    public AbstractComponent(@NotNull final List<? extends ComponentLike> children, @NotNull final Style style) {
        this.children = ComponentLike.asComponents(children, NOT_EMPTY);
        this.style = style;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component
    @NotNull
    public final List<Component> children() {
        return this.children;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component
    @NotNull
    public final Style style() {
        return this.style;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component
    @NotNull
    public Component replaceText(@NotNull final Consumer<TextReplacementConfig.Builder> configurer) {
        Objects.requireNonNull(configurer, "configurer");
        return replaceText((TextReplacementConfig) Buildable.configureAndBuild(TextReplacementConfig.builder(), configurer));
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component
    @NotNull
    public Component replaceText(@NotNull final TextReplacementConfig config) {
        Objects.requireNonNull(config, "replacement");
        if (!(config instanceof TextReplacementConfigImpl)) {
            throw new IllegalArgumentException("Provided replacement was a custom TextReplacementConfig implementation, which is not supported.");
        }
        return TextReplacementRenderer.INSTANCE.render((Component) this, ((TextReplacementConfigImpl) config).createState());
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component
    @NotNull
    public Component compact() {
        return ComponentCompaction.compact(this, null);
    }

    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AbstractComponent)) {
            return false;
        }
        AbstractComponent that = (AbstractComponent) other;
        return Objects.equals(this.children, that.children) && Objects.equals(this.style, that.style);
    }

    public int hashCode() {
        int result = this.children.hashCode();
        return (31 * result) + this.style.hashCode();
    }

    private String debuggerString() {
        return StringExaminer.simpleEscaping().examine(examinableName(), examinablePropertiesWithoutChildren());
    }

    public Stream<? extends ExaminableProperty> examinablePropertiesWithoutChildren() {
        return Stream.of(ExaminableProperty.m91of("style", this.style));
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.concat(examinablePropertiesWithoutChildren(), Stream.of(ExaminableProperty.m91of("children", this.children)));
    }

    public String toString() {
        return (String) examine(StringExaminer.simpleEscaping());
    }
}
