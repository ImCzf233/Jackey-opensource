package com.viaversion.viaversion.libs.kyori.adventure.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/text/ComponentLike.class */
public interface ComponentLike {
    @Contract(pure = true)
    @NotNull
    Component asComponent();

    @NotNull
    static List<Component> asComponents(@NotNull final List<? extends ComponentLike> likes) {
        return asComponents(likes, null);
    }

    @NotNull
    static List<Component> asComponents(@NotNull final List<? extends ComponentLike> likes, @Nullable final Predicate<? super Component> filter) {
        int size = likes.size();
        if (size == 0) {
            return Collections.emptyList();
        }
        ArrayList<Component> components = null;
        for (int i = 0; i < size; i++) {
            ComponentLike like = likes.get(i);
            Component component = like.asComponent();
            if (filter == null || filter.test(component)) {
                if (components == null) {
                    components = new ArrayList<>(size);
                }
                components.add(component);
            }
        }
        if (components == null) {
            return Collections.emptyList();
        }
        components.trimToSize();
        return Collections.unmodifiableList(components);
    }

    @Nullable
    static Component unbox(@Nullable final ComponentLike like) {
        if (like != null) {
            return like.asComponent();
        }
        return null;
    }
}
