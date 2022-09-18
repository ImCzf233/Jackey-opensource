package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextReplacementConfig;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.renderer.ComponentRenderer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementRenderer.class */
final class TextReplacementRenderer implements ComponentRenderer<State> {
    static final TextReplacementRenderer INSTANCE = new TextReplacementRenderer();

    private TextReplacementRenderer() {
    }

    @NotNull
    public Component render(@NotNull final Component component, @NotNull final State state) {
        HoverEvent<?> rendered;
        if (!state.running) {
            return component;
        }
        boolean prevFirstMatch = state.firstMatch;
        state.firstMatch = true;
        List<Component> oldChildren = component.children();
        int oldChildrenSize = oldChildren.size();
        List<Component> children = null;
        Component modified = component;
        if (component instanceof TextComponent) {
            String content = ((TextComponent) component).content();
            Matcher matcher = state.pattern.matcher(content);
            int replacedUntil = 0;
            while (true) {
                if (!matcher.find()) {
                    break;
                }
                TextReplacementConfig.Condition condition = state.continuer;
                int i = state.matchCount + 1;
                state.matchCount = i;
                PatternReplacementResult result = condition.shouldReplace(matcher, i, state.replaceCount);
                if (result != PatternReplacementResult.CONTINUE) {
                    if (result == PatternReplacementResult.STOP) {
                        state.running = false;
                        break;
                    }
                    if (matcher.start() == 0) {
                        if (matcher.end() == content.length()) {
                            ComponentLike replacement = state.replacement.apply(matcher, Component.text().content(matcher.group()).style(component.style()));
                            Component modified2 = replacement == null ? Component.empty() : replacement.asComponent();
                            modified = modified2.style(modified2.style().merge(component.style(), Style.Merge.Strategy.IF_ABSENT_ON_TARGET));
                            if (children == null) {
                                children = new ArrayList<>(oldChildrenSize + modified.children().size());
                                children.addAll(modified.children());
                            }
                        } else {
                            modified = Component.text("", component.style());
                            ComponentLike child = state.replacement.apply(matcher, Component.text().content(matcher.group()));
                            if (child != null) {
                                if (children == null) {
                                    children = new ArrayList<>(oldChildrenSize + 1);
                                }
                                children.add(child.asComponent());
                            }
                        }
                    } else {
                        if (children == null) {
                            children = new ArrayList<>(oldChildrenSize + 2);
                        }
                        if (state.firstMatch) {
                            modified = ((TextComponent) component).content(content.substring(0, matcher.start()));
                        } else if (replacedUntil < matcher.start()) {
                            children.add(Component.text(content.substring(replacedUntil, matcher.start())));
                        }
                        ComponentLike builder = state.replacement.apply(matcher, Component.text().content(matcher.group()));
                        if (builder != null) {
                            children.add(builder.asComponent());
                        }
                    }
                    state.replaceCount++;
                    state.firstMatch = false;
                    replacedUntil = matcher.end();
                }
            }
            if (replacedUntil < content.length() && replacedUntil > 0) {
                if (children == null) {
                    children = new ArrayList<>(oldChildrenSize);
                }
                children.add(Component.text(content.substring(replacedUntil)));
            }
        } else if (modified instanceof TranslatableComponent) {
            List<Component> args = ((TranslatableComponent) modified).args();
            List<Component> newArgs = null;
            int size = args.size();
            for (int i2 = 0; i2 < size; i2++) {
                Component original = args.get(i2);
                Component replaced = render(original, state);
                if (replaced != component && newArgs == null) {
                    newArgs = new ArrayList<>(size);
                    if (i2 > 0) {
                        newArgs.addAll(args.subList(0, i2));
                    }
                }
                if (newArgs != null) {
                    newArgs.add(replaced);
                }
            }
            if (newArgs != null) {
                modified = ((TranslatableComponent) modified).args(newArgs);
            }
        }
        if (state.running) {
            HoverEvent<?> event = modified.style().hoverEvent();
            if (event != null && event != (rendered = event.withRenderedValue(this, state))) {
                modified = modified.style(s -> {
                    s.hoverEvent(rendered);
                });
            }
            boolean first = true;
            for (int i3 = 0; i3 < oldChildrenSize; i3++) {
                Component child2 = oldChildren.get(i3);
                Component replaced2 = render(child2, state);
                if (replaced2 != child2) {
                    if (children == null) {
                        children = new ArrayList<>(oldChildrenSize);
                    }
                    if (first) {
                        children.addAll(oldChildren.subList(0, i3));
                    }
                    first = false;
                }
                if (children != null) {
                    children.add(replaced2);
                }
            }
        } else if (children != null) {
            children.addAll(oldChildren);
        }
        state.firstMatch = prevFirstMatch;
        if (children != null) {
            return modified.children(children);
        }
        return modified;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/TextReplacementRenderer$State.class */
    public static final class State {
        final Pattern pattern;
        final BiFunction<MatchResult, TextComponent.Builder, ComponentLike> replacement;
        final TextReplacementConfig.Condition continuer;
        boolean running = true;
        int matchCount = 0;
        int replaceCount = 0;
        boolean firstMatch = true;

        public State(@NotNull final Pattern pattern, @NotNull final BiFunction<MatchResult, TextComponent.Builder, ComponentLike> replacement, final TextReplacementConfig.Condition continuer) {
            this.pattern = pattern;
            this.replacement = replacement;
            this.continuer = continuer;
        }
    }
}
