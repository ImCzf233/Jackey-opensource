package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextReplacementConfigImpl;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import com.viaversion.viaversion.libs.kyori.adventure.util.IntFunction2;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementConfig.class */
public interface TextReplacementConfig extends Buildable<TextReplacementConfig, Builder>, Examinable {

    @FunctionalInterface
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementConfig$Condition.class */
    public interface Condition {
        @NotNull
        PatternReplacementResult shouldReplace(@NotNull final MatchResult result, final int matchCount, final int replaced);
    }

    @NotNull
    Pattern matchPattern();

    @NotNull
    static Builder builder() {
        return new TextReplacementConfigImpl.Builder();
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementConfig$Builder.class */
    public interface Builder extends Buildable.Builder<TextReplacementConfig> {
        @Contract("_ -> this")
        @NotNull
        Builder match(@NotNull final Pattern pattern);

        @Contract("_ -> this")
        @NotNull
        Builder condition(@NotNull final Condition condition);

        @Contract("_ -> this")
        @NotNull
        Builder replacement(@NotNull final BiFunction<MatchResult, TextComponent.Builder, ComponentLike> replacement);

        @Contract("_ -> this")
        default Builder matchLiteral(final String literal) {
            return match(Pattern.compile(literal, 16));
        }

        @Contract("_ -> this")
        @NotNull
        default Builder match(@RegExp @NotNull final String pattern) {
            return match(Pattern.compile(pattern));
        }

        @NotNull
        default Builder once() {
            return times(1);
        }

        @Contract("_ -> this")
        @NotNull
        default Builder times(final int times) {
            return condition(index, replaced -> {
                return replaced < times ? PatternReplacementResult.REPLACE : PatternReplacementResult.STOP;
            });
        }

        @Contract("_ -> this")
        @NotNull
        default Builder condition(@NotNull final IntFunction2<PatternReplacementResult> condition) {
            return condition(result, matchCount, replaced -> {
                return (PatternReplacementResult) condition.apply(matchCount, replaced);
            });
        }

        @Contract("_ -> this")
        @NotNull
        default Builder replacement(@NotNull final String replacement) {
            Objects.requireNonNull(replacement, "replacement");
            return replacement(builder -> {
                return builder.content(replacement);
            });
        }

        @Contract("_ -> this")
        @NotNull
        default Builder replacement(@Nullable final ComponentLike replacement) {
            Component baked = replacement == null ? null : replacement.asComponent();
            return replacement(result, input -> {
                return baked;
            });
        }

        @Contract("_ -> this")
        @NotNull
        default Builder replacement(@NotNull final Function<TextComponent.Builder, ComponentLike> replacement) {
            Objects.requireNonNull(replacement, "replacement");
            return replacement(result, input -> {
                return (ComponentLike) replacement.apply(input);
            });
        }
    }
}
