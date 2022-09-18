package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.Adventure;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.util.Nag;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/TextComponentImpl.class */
public final class TextComponentImpl extends AbstractComponent implements TextComponent {
    @VisibleForTesting
    static final char SECTION_CHAR = 167;
    private final String content;
    private static final boolean WARN_WHEN_LEGACY_FORMATTING_DETECTED = Boolean.getBoolean(String.join(".", "net", "kyori", Adventure.NAMESPACE, "text", "warnWhenLegacyFormattingDetected"));
    static final TextComponent EMPTY = createDirect("");
    static final TextComponent NEWLINE = createDirect("\n");
    static final TextComponent SPACE = createDirect(" ");

    @NotNull
    private static TextComponent createDirect(@NotNull final String content) {
        return new TextComponentImpl(Collections.emptyList(), Style.empty(), content);
    }

    public TextComponentImpl(@NotNull final List<? extends ComponentLike> children, @NotNull final Style style, @NotNull final String content) {
        super(children, style);
        LegacyFormattingDetected nag;
        this.content = (String) Objects.requireNonNull(content, "content");
        if (WARN_WHEN_LEGACY_FORMATTING_DETECTED && (nag = warnWhenLegacyFormattingDetected()) != null) {
            Nag.print(nag);
        }
    }

    @VisibleForTesting
    @Nullable
    final LegacyFormattingDetected warnWhenLegacyFormattingDetected() {
        if (this.content.indexOf(167) != -1) {
            return new LegacyFormattingDetected(this);
        }
        return null;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent
    @NotNull
    public String content() {
        return this.content;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent
    @NotNull
    public TextComponent content(@NotNull final String content) {
        return Objects.equals(this.content, content) ? this : new TextComponentImpl(this.children, this.style, (String) Objects.requireNonNull(content, "content"));
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component, com.viaversion.viaversion.libs.kyori.adventure.text.ScopedComponent
    @NotNull
    public TextComponent children(@NotNull final List<? extends ComponentLike> children) {
        return new TextComponentImpl(children, this.style, this.content);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.Component, com.viaversion.viaversion.libs.kyori.adventure.text.ScopedComponent
    @NotNull
    public TextComponent style(@NotNull final Style style) {
        return new TextComponentImpl(this.children, style, this.content);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TextComponentImpl) || !super.equals(other)) {
            return false;
        }
        TextComponentImpl that = (TextComponentImpl) other;
        return Objects.equals(this.content, that.content);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent
    public int hashCode() {
        int result = super.hashCode();
        return (31 * result) + this.content.hashCode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent
    @NotNull
    public Stream<? extends ExaminableProperty> examinablePropertiesWithoutChildren() {
        return Stream.concat(Stream.of(ExaminableProperty.m90of("content", this.content)), super.examinablePropertiesWithoutChildren());
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.BuildableComponent, com.viaversion.viaversion.libs.kyori.adventure.util.Buildable
    @NotNull
    public TextComponent.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/TextComponentImpl$BuilderImpl.class */
    public static final class BuilderImpl extends AbstractComponentBuilder<TextComponent, TextComponent.Builder> implements TextComponent.Builder {
        private String content;

        public BuilderImpl() {
            this.content = "";
        }

        BuilderImpl(@NotNull final TextComponent component) {
            super(component);
            this.content = "";
            this.content = component.content();
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent.Builder
        @NotNull
        public TextComponent.Builder content(@NotNull final String content) {
            this.content = (String) Objects.requireNonNull(content, "content");
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent.Builder
        @NotNull
        public String content() {
            return this.content;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder, com.viaversion.viaversion.libs.kyori.adventure.util.Buildable.Builder
        @NotNull
        public TextComponent build() {
            if (isEmpty()) {
                return Component.empty();
            }
            return new TextComponentImpl(this.children, buildStyle(), this.content);
        }

        private boolean isEmpty() {
            return this.content.isEmpty() && this.children.isEmpty() && !hasStyle();
        }
    }
}
