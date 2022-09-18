package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextReplacementConfig;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextReplacementRenderer;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementConfigImpl.class */
public final class TextReplacementConfigImpl implements TextReplacementConfig {
    private final Pattern matchPattern;
    private final BiFunction<MatchResult, TextComponent.Builder, ComponentLike> replacement;
    private final TextReplacementConfig.Condition continuer;

    TextReplacementConfigImpl(final Builder builder) {
        this.matchPattern = builder.matchPattern;
        this.replacement = builder.replacement;
        this.continuer = builder.continuer;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.text.TextReplacementConfig
    @NotNull
    public Pattern matchPattern() {
        return this.matchPattern;
    }

    public TextReplacementRenderer.State createState() {
        return new TextReplacementRenderer.State(this.matchPattern, this.replacement, this.continuer);
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.util.Buildable
    public TextReplacementConfig.Builder toBuilder() {
        return new Builder(this);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of((Object[]) new ExaminableProperty[]{ExaminableProperty.m91of("matchPattern", this.matchPattern), ExaminableProperty.m91of("replacement", this.replacement), ExaminableProperty.m91of("continuer", this.continuer)});
    }

    public String toString() {
        return (String) examine(StringExaminer.simpleEscaping());
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementConfigImpl$Builder.class */
    public static final class Builder implements TextReplacementConfig.Builder {
        @Nullable
        Pattern matchPattern;
        @Nullable
        BiFunction<MatchResult, TextComponent.Builder, ComponentLike> replacement;
        TextReplacementConfig.Condition continuer;

        public Builder() {
            this.continuer = matchResult, index, replacement -> {
                return PatternReplacementResult.REPLACE;
            };
        }

        Builder(final TextReplacementConfigImpl instance) {
            this.continuer = matchResult, index, replacement -> {
                return PatternReplacementResult.REPLACE;
            };
            this.matchPattern = instance.matchPattern;
            this.replacement = instance.replacement;
            this.continuer = instance.continuer;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.TextReplacementConfig.Builder
        @NotNull
        public Builder match(@NotNull final Pattern pattern) {
            this.matchPattern = (Pattern) Objects.requireNonNull(pattern, "pattern");
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.TextReplacementConfig.Builder
        @NotNull
        public Builder condition(final TextReplacementConfig.Condition condition) {
            this.continuer = (TextReplacementConfig.Condition) Objects.requireNonNull(condition, "continuation");
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.text.TextReplacementConfig.Builder
        @NotNull
        public Builder replacement(@NotNull final BiFunction<MatchResult, TextComponent.Builder, ComponentLike> replacement) {
            this.replacement = (BiFunction) Objects.requireNonNull(replacement, "replacement");
            return this;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.util.Buildable.Builder
        @NotNull
        public TextReplacementConfig build() {
            if (this.matchPattern == null) {
                throw new IllegalStateException("A pattern must be provided to match against");
            }
            if (this.replacement != null) {
                return new TextReplacementConfigImpl(this);
            }
            throw new IllegalStateException("A replacement action must be provided");
        }
    }
}
