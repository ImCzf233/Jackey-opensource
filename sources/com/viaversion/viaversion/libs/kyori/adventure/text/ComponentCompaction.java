package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/ComponentCompaction.class */
final class ComponentCompaction {
    private static final TextDecoration[] DECORATIONS = TextDecoration.values();

    private ComponentCompaction() {
    }

    public static Component compact(@NotNull final Component self, @Nullable final Style parentStyle) {
        List<Component> children = self.children();
        Component optimized = self.children(Collections.emptyList());
        if (parentStyle != null) {
            optimized = optimized.style(simplifyStyle(self.style(), parentStyle));
        }
        int childrenSize = children.size();
        if (childrenSize == 0) {
            return optimized;
        }
        if (childrenSize == 1 && (self instanceof TextComponent)) {
            TextComponent textComponent = (TextComponent) self;
            if (textComponent.content().isEmpty()) {
                Component child = children.get(0);
                return child.style(child.style().merge(optimized.style(), Style.Merge.Strategy.IF_ABSENT_ON_TARGET)).compact();
            }
        }
        Style childParentStyle = optimized.style();
        if (parentStyle != null) {
            childParentStyle = childParentStyle.merge(parentStyle, Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
        }
        List<Component> childrenToAppend = new ArrayList<>(children.size());
        for (int i = 0; i < children.size(); i++) {
            childrenToAppend.add(compact(children.get(i), childParentStyle));
        }
        while (!childrenToAppend.isEmpty()) {
            Component child2 = childrenToAppend.get(0);
            Style childStyle = child2.style().merge(childParentStyle, Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
            if (!(optimized instanceof TextComponent) || !(child2 instanceof TextComponent) || !Objects.equals(childStyle, childParentStyle)) {
                break;
            }
            optimized = joinText((TextComponent) optimized, (TextComponent) child2);
            childrenToAppend.remove(0);
            childrenToAppend.addAll(0, child2.children());
        }
        int i2 = 0;
        while (i2 + 1 < childrenToAppend.size()) {
            Component child3 = childrenToAppend.get(i2);
            Component neighbor = childrenToAppend.get(i2 + 1);
            Style childStyle2 = child3.style().merge(childParentStyle, Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
            Style neighborStyle = neighbor.style().merge(childParentStyle, Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
            if (child3.children().isEmpty() && (child3 instanceof TextComponent) && (neighbor instanceof TextComponent) && childStyle2.equals(neighborStyle)) {
                Component combined = joinText((TextComponent) child3, (TextComponent) neighbor);
                childrenToAppend.set(i2, combined);
                childrenToAppend.remove(i2 + 1);
            } else {
                i2++;
            }
        }
        return optimized.children(childrenToAppend);
    }

    @NotNull
    private static Style simplifyStyle(@NotNull final Style style, @NotNull final Style parentStyle) {
        TextDecoration[] textDecorationArr;
        if (style.isEmpty()) {
            return style;
        }
        Style.Builder builder = style.toBuilder();
        if (Objects.equals(style.font(), parentStyle.font())) {
            builder.font(null);
        }
        if (Objects.equals(style.color(), parentStyle.color())) {
            builder.color(null);
        }
        for (TextDecoration decoration : DECORATIONS) {
            if (style.decoration(decoration) == parentStyle.decoration(decoration)) {
                builder.decoration(decoration, TextDecoration.State.NOT_SET);
            }
        }
        if (Objects.equals(style.clickEvent(), parentStyle.clickEvent())) {
            builder.clickEvent(null);
        }
        if (Objects.equals(style.hoverEvent(), parentStyle.hoverEvent())) {
            builder.hoverEvent(null);
        }
        if (Objects.equals(style.insertion(), parentStyle.insertion())) {
            builder.insertion(null);
        }
        return builder.build();
    }

    private static TextComponent joinText(final TextComponent one, final TextComponent two) {
        return new TextComponentImpl(two.children(), one.style(), one.content() + two.content());
    }
}
